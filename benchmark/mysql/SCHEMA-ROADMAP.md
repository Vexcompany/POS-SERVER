# POS-SERVER v29 MySQL benchmark schema roadmap

## Verified source paths

The decompiled v29 source is available under **decompiled/pos-server-v29/minimart/engine**. DAO SQL, model fields and RowMappers provide evidence for column names and query shapes, but they do not provide a complete, authoritative DDL (types, defaults, nullability, indexes, foreign keys, triggers and stored procedures).

See **SOURCE-SCHEMA-INVENTORY.md** for the current table/dependency map and source files used.

## LOCAL runtime boundary

KaryawanService defaults the LOCAL connection to MySQL 127.0.0.1:3306, database **minimarket**, user root, empty password, branch code/name LOCAL. The pos.local.db.* Java system properties can override these defaults. ConnectionManager builds the JDBC URL from the credential object.

Keep the schemas separate:

    minimarket              <- LOCAL application database
    minimarket_benchmark    <- disposable synthetic performance database

Creating an empty database called minimarket does not create the operational schema. The repository still does not contain a confirmed complete v29 SQL dump. Do not copy the benchmark schema into minimarket or treat it as a full POS database.

## Benchmark corrections made in this branch

- CREATED and UPDATED in core tables are now populated as DATETIME values because the corresponding v29 RowMappers read them using ResultSet.getDate(...). m_barang.HPP_UPDATED remains numeric because the DAO writes UNIX_TIMESTAMP() to that field.
- m_barcode.ID is INT in line with BarcodeRowMapper.getInt("ID").
- Removed the benchmark-only UNIQUE constraint on barcode and the foreign key from m_barcode to m_barang; neither constraint is proven by DAO source. Barcode lookup still has a non-unique index.
- Removed the misleading unique constraint on m_role(role, menu); the source uses that pair for lookup/update but does not establish uniqueness.
- Removed the unexplained idx_barang_barcode_search index because it was not aligned with actual barcode lookup SQL.
- Query profiling now separates barcode lookup from product-ID lookup. The prior OR predicate could let the optimizer choose the primary-key branch and conceal barcode-index behavior.
- Added a prefix-name query that matches the generated Produk Uji 050000 naming scheme, alongside a deliberate leading-wildcard contains query. Storage metadata labels TABLE_ROWS as an estimate.
- Added 03_benchmark_sanity_checks.sql to check the exact fixture counts, the known B050000 barcode, the prefix-query count and the three-barcode-per-product invariant before profiling.

These are synthetic benchmark choices informed by source evidence, not a claim that the production DDL has been reconstructed.

## Confirmed query shapes

- Product by ID: SELECT * FROM m_barang WHERE ID = ? LIMIT 1
- Product/barcode: m_barang LEFT JOIN m_barcode ON m_barang.ID = m_barcode.M_BARANG_ID, filtered by barcode or product ID and ordered by barcode
- Product name search: M_JENIS_JENIS NOT LIKE ... AND NAMA LIKE ?, ordered by category/name
- Rack search: RAK_TOKO LIKE ? ORDER BY RAK_TOKO
- Employee login lookup: m_karyawan by ID and available = 1; password lookup uses password = ? AND available = 1
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

## Next step to complete a real LOCAL test database

Obtain a schema-only SQL dump/export from the original v29 database (or a vendor-provided migration/DDL package), with secrets and production rows excluded. Compare it against SOURCE-SCHEMA-INVENTORY.md, then import it into an isolated disposable schema and add only synthetic fixture rows. Until that source DDL exists, a full LOCAL schema must not be invented from DAO snippets alone.
