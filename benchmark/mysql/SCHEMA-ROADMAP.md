# POS-SERVER v29 MySQL benchmark schema roadmap

## Verified source paths

The decompiled v29 source is available under **decompiled/pos-server-v29/minimart/engine**. DAO SQL, model fields and RowMappers provide evidence for column names and query shapes, but they do not provide a complete, authoritative DDL (types, defaults, nullability, indexes, foreign keys, triggers and stored procedures).

See **SOURCE-SCHEMA-INVENTORY.md** for the current table/dependency map and source files used.

## LOCAL runtime boundary

KaryawanService.authLoginOnline() has a LOCAL development shortcut gated by the POS_LOCAL_USERNAME and POS_LOCAL_PASSWORD environment variables. When those match, it creates a LOCAL credential that defaults to MySQL 127.0.0.1:3306, database minimarket, user root, empty password, branch code/name LOCAL. The pos.local.db.* Java system properties can override the database settings. ConnectionManager builds the JDBC URL from the credential object.

The LOCAL authentication shortcut does not authenticate by looking up m_karyawan. The m_karyawan DAO queries are separate application data access paths.

Keep the schemas separate:

    minimarket              <- LOCAL application database
    minimarket_benchmark    <- disposable synthetic performance database

Creating an empty database called minimarket does not create the operational schema. The repository still does not contain a confirmed complete v29 SQL dump. Do not copy the benchmark schema into minimarket or treat it as a full POS database.

## Benchmark corrections made in this branch

- The fixture populates date-like CREATED/UPDATED fields as DATETIME/NOW() because the Java path binds date values and RowMappers read them with getDate(...). This is a synthetic fixture choice, not proof of the original physical SQL type. m_barang.HPP_UPDATED remains numeric because the DAO writes UNIX_TIMESTAMP() to it.
- m_barcode.ID is INT in line with BarcodeRowMapper.getInt("ID").
- Removed the benchmark-only UNIQUE constraint on barcode and the foreign key from m_barcode to m_barang; neither constraint is proven by DAO source. Barcode lookup still has a non-unique index.
- Removed the misleading unique constraint on m_role(role, menu); the source uses that pair for lookup/update but does not establish uniqueness.
- Removed the unexplained idx_barang_barcode_search index because it was not aligned with actual barcode lookup SQL.
- Query profiling now separates barcode lookup from product-ID lookup. The prior OR predicate could let the optimizer choose the primary-key branch and conceal barcode-index behavior.
- Added a prefix-name query that matches the generated Produk Uji 050000 naming scheme, alongside a deliberate leading-wildcard contains query. Storage metadata labels TABLE_ROWS as an estimate.
- Added 03_benchmark_sanity_checks.sql to check exact fixture counts, the known B050000 barcode, the prefix-query count and the three-barcode-per-product invariant before profiling.

These are synthetic benchmark choices informed by source evidence, not a claim that the production DDL has been reconstructed. Exact physical types and the production index set still require the original schema export. The indexes retained in the seed are workload candidates based on observed DAO query shapes; the original production index set is still unverified.

## Confirmed query shapes

- Product by ID: SELECT * FROM m_barang WHERE ID = ? LIMIT 1
- Product/barcode: m_barang LEFT JOIN m_barcode ON m_barang.ID = m_barcode.M_BARANG_ID, filtered by barcode or product ID and ordered by barcode
- Product name search: M_JENIS_JENIS NOT LIKE ... AND NAMA LIKE ?, ordered by category/name
- Rack search: RAK_TOKO LIKE ? ORDER BY RAK_TOKO
- Employee DAO: m_karyawan by ID/password and AVAILABLE = 1; this is not the LOCAL development login gate
- Stock opname joins: stok_opnam, stok_opnam_detail, and m_barang
- Sales reporting: t_penjualan, t_penjualan_detail, m_barang, and m_customer
- Purchasing: t_pembelian, t_pembelian_detail, m_supplier, and m_barang
- Stock commit additionally calls stored procedures including sp_read_all_stock_commit, sp_read_uncommitted_stock_commit, sp_insert_stock_commit, and sp_update_status_commit_stock_by_id

## Safe execution

The seed script drops and recreates the six tables in minimarket_benchmark. Run it only when that database is disposable and its contents may be discarded. Query profiles require MySQL 8.0.18+ for EXPLAIN ANALYZE; on older MySQL versions use EXPLAIN without ANALYZE and do not compare estimates as actual timings.

Recommended benchmark-only sequence:

    cd ~/POS-SERVER
    sudo mysql < benchmark/mysql/00_synthetic_core_benchmark.sql
    sudo mysql < benchmark/mysql/03_benchmark_sanity_checks.sql
    sudo mysql < benchmark/mysql/01_query_profile.sql

The second and third commands are read-only, but the first one drops/recreates tables in minimarket_benchmark. Review the file and confirm the target schema before running it. Do not run the seed against minimarket or a production database.

The synthetic dataset targets query-path measurements only. It does not validate login, sales finalization, stock commit, purchasing, synchronization or the complete GUI startup sequence.

## Initial synthetic CI performance baseline (2026-10-29)

The GitHub Actions smoke test ran the seed, sanity checks and query profiles successfully against an isolated MySQL 8.4.13 service. Fixture checks passed: 100,000 products, 300,000 barcodes, 1,000 prefix matches, the expected B050000/PCS barcode, and exactly three barcodes per product.

Observed EXPLAIN ANALYZE times on that GitHub-hosted runner (not the VPS):

| Query shape | Actual time | Plan observation |
|---|---:|---|
| Exact product ID | 0.0054 ms | Single-row ID result |
| Product name prefix, 50 rows | 0.0712 ms | Range scan on idx_barang_nama |
| Product name contains, 100 rows | 7.35 ms | Leading wildcard; scans about 10,100 index entries |
| Exact barcode | 0.0146 ms | Lookup via idx_barcode_lookup |
| Barcode + product join | 0.0198 ms | Barcode index, then product primary key |
| Existing DAO OR query, product-ID input | 738 ms | Scans 100,000 products, visits 300,000 joined barcode rows, then sorts |
| Existing DAO OR query, barcode input | 779 ms | Same full join/scan pattern |
| Split-path product-ID candidate | 0.0328 ms | Product primary key then three barcode rows |
| Split-path barcode candidate | 0.0213 ms | Barcode index then product primary key |
| Rack prefix, 400 rows | 2.53 ms | Range scan on idx_barang_rak_toko |

The combined DAO query was profiled with the same input value bound to both OR predicates, matching the source shape. In this run, separate lookup paths were roughly tens of thousands of times faster than the joined OR query. This is a strong optimization candidate, not a production patch. The read-only fixture script now checks result parity for normal product-ID and barcode inputs; unusual overlapping ID/barcode values, null behavior and full application integration still need review before a separate DAO change can be built/tested. CI timings are environment/cache dependent and are not a VPS performance guarantee.

## Next step to complete a real LOCAL test database

Obtain a schema-only SQL dump/export from the original v29 database (or a vendor-provided migration/DDL package), with secrets and production rows excluded. Compare it against SOURCE-SCHEMA-INVENTORY.md, then import it into an isolated disposable schema and add only synthetic fixture rows. Until that source DDL exists, a full LOCAL schema must not be invented from DAO snippets alone.
