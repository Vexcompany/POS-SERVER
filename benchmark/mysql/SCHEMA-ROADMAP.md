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

- The m_barang fixture now matches the supplied SHOW CREATE TABLE evidence: KONVERSI_*, BATAS_ATAS_STOCK, REORDER_POINT and stock quantities use DECIMAL(14,3); HARGA_JUAL_* and HPP use DECIMAL(14,2); HPP_UPDATED, CREATED and UPDATED use BIGINT. The observed BIGINT date columns override the earlier DATETIME guess based only on ResultSet.getDate(). Other fixture-table date types remain unverified.
- m_barcode.ID is INT in line with BarcodeRowMapper.getInt("ID").
- Removed the benchmark-only UNIQUE constraint on barcode and the foreign key from m_barcode to m_barang; neither constraint is proven by DAO source. Barcode lookup still has a non-unique index.
- Removed the misleading unique constraint on m_role(role, menu); the source uses that pair for lookup/update but does not establish uniqueness.
- Preserved the observed legacy idx_barang_barcode_search index as (M_JENIS_JENIS, STOK_TOKO). Its name is misleading: it does not index BARCODE and is not used as the barcode lookup index.
- Query profiling separates barcode and product-ID access paths. A naive split-by-input-type is fast for normal inputs but is not semantically equivalent if one string exists as both a product ID and another product's barcode. The fixture now includes that collision and tests a UNION ALL candidate that excludes the duplicate product-ID branch while preserving the original ORDER BY result.
- Added a prefix-name query that matches the generated Produk Uji 050000 naming scheme, alongside a deliberate leading-wildcard contains query. Storage metadata labels TABLE_ROWS as an estimate.
- Added 03_benchmark_sanity_checks.sql checks for exact fixture counts, the known B050000 barcode, the prefix-query count, the three-barcode-per-product invariant, the intentional cross-namespace collision, and old-query/UNION candidate parity. It also asserts that the naive split path differs in the collision case.

The m_barang definitions above are backed by the user's supplied SHOW CREATE TABLE / SHOW INDEX output for the benchmark table. This is table-specific evidence, not a complete production DDL. The other five fixture tables still use explicitly labeled synthetic choices. The benchmark remains non-operational and cannot be imported into minimarket as a complete LOCAL schema.

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

The combined DAO query was profiled with the same input value bound to both OR predicates, matching the source shape. The original query scans the full product set and expands all joined barcode rows before filtering/sorting. The initial split-by-input candidate is fast for normal inputs but is not semantically equivalent when a string exists as both a product ID and another product's barcode. A deliberate collision fixture now proves that case; a UNION ALL candidate preserves the original selected product/order while using the primary-key and barcode indexes separately. This remains a benchmark candidate, not a production DAO patch. Null behavior, broader duplicate-barcode cases and full application integration still need review. CI timings are environment/cache dependent and are not a VPS performance guarantee.

## DDL-aligned synthetic CI baseline (2026-10-30)

The updated seed and DDL-contract sanity checks passed in isolated GitHub Actions MySQL 8.4.13 run [#40991006496](https://github.com/Vexcompany/POS-SERVER/actions/runs/40991006496). This run is after aligning m_barang numeric/timestamp types and preserving the observed legacy composite index. It is a GitHub-hosted runner measurement, not a VPS result.

| Query shape | Actual time |
|---|---:|
| Exact product ID | 0.0049 ms |
| Product name prefix, 50 rows | 0.0736 ms |
| Product name contains, leading wildcard | 6.51 ms |
| Exact barcode | 0.0151 ms |
| Barcode + product join | 0.0310 ms |
| Existing DAO OR query, product-ID input | 818 ms |
| Existing DAO OR query, barcode input | 799 ms |
| Split-path product-ID candidate | 0.0428 ms |
| Split-path barcode candidate | 0.0325 ms |
| Rack prefix, 400 rows | 1.79 ms |

All DDL contract checks passed, including DECIMAL precision, BIGINT timestamp columns, and the observed `idx_barang_barcode_search (M_JENIS_JENIS, STOK_TOKO)` shape. Fixture checks also passed: 100,000 products, 300,000 barcodes, 1,000 prefix matches, known B050000 barcode, three barcodes per product, and normal-input old-vs-split result parity.

The OR query still scans all 100,000 products and expands 300,000 joined barcode rows before filtering/sorting. The split candidates remain dramatically faster in this synthetic fixture, but the timings vary by runner/cache and do not prove that a production DAO rewrite is safe. The previous 2026-10-29 baseline is retained above for historical comparison; do not compare these runs as a controlled before/after unless hardware, cache and schema are held constant.

## Collision-safe DAO lookup candidate (2026-10-30)

The collision fixture and full `BarangRowMapper` projection passed in isolated MySQL 8.4.13 GitHub Actions run [#40991590479](https://github.com/Vexcompany/POS-SERVER/actions/runs/40991590479).

The seed keeps 100,000 products and 300,000 barcode rows (three per product). One PACK barcode on product `B050001` is deliberately set to `000001`, while a different product's ID is changed to `000001`. This forces the original DAO's `WHERE BARCODE = ? OR m_barang.ID = ? ORDER BY BARCODE ASC LIMIT 1` to match both branches.

The read-only parity check confirms:

- Original DAO result: product `B050001`, barcode `000001`.
- Naive ID-only split result: product `000001`, barcode `8999000000099999` — different from the original.
- UNION ALL candidate result: product `B050001`, barcode `000001` — same as the original.

| Query shape | Actual time |
|---|---:|
| Existing DAO OR query, product-ID input | 1,139 ms |
| Existing DAO OR query, barcode input | 1,078 ms |
| Naive split, product-ID input | 0.0331 ms |
| Naive split, barcode input | 0.0199 ms |
| UNION candidate, full product row, product-ID input | 0.0652 ms |
| UNION candidate, full product row, barcode input | 0.0375 ms |
| UNION candidate, full product row, overlapping input | 0.0798 ms |

The UNION plan uses the product primary key for the ID branch and `idx_barcode_lookup` for the barcode branch, materializes only three or four candidate rows, then sorts those rows. This preserves the mapped product fields and original barcode ordering for the tested cases. The benchmark does not modify `BarangDaoImpl`; a production patch still requires review for nulls, duplicate barcodes, any hidden caller expectations and a build/runtime test.

## Continuation checkpoint (2026-10-29)

- Repo-side benchmark corrections and source inventory were merged via [PR #9](https://github.com/Vexcompany/POS-SERVER/pull/9).
- The parity-checked MySQL 8.4.13 run passed all fixture checks and uploaded the profile artifact in [GitHub Actions run 40808093371](https://github.com/Vexcompany/POS-SERVER/actions/runs/40808093371). The artifact is retained for seven days from the run.
- Do not repeat the seed/profile cycle without a code or environment change; the current fixture contract and baseline are recorded above.
- The next useful step is either to obtain the original schema-only DDL for the LOCAL database, or to make a separate, build-validated DAO optimization from the measured OR-query bottleneck. Do not invent a full schema or change production DAO behavior from the synthetic result alone.

## Next step to complete a real LOCAL test database

Obtain a schema-only SQL dump/export from the original v29 database (or a vendor-provided migration/DDL package), with secrets and production rows excluded. Compare it against SOURCE-SCHEMA-INVENTORY.md, then import it into an isolated disposable schema and add only synthetic fixture rows. Until that source DDL exists, a full LOCAL schema must not be invented from DAO snippets alone.
