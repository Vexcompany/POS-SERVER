# POS-SERVER v29 MySQL benchmark schema roadmap

## Verified from v29 source

The benchmark core is not a production dump. The decompiled v29 source contains a real `BarangDaoImpl` and `BarangRowMapper` path that directly references the following `m_barang` columns:

`ID`, `M_JENIS_JENIS`, `RAK_TOKO`, `RAK_GUDANG`, `NAMA`, `SATUAN_1`, `SATUAN_2`, `SATUAN_3`, `KONVERSI_21`, `KONVERSI_32`, `HARGA_JUAL_1`, `HARGA_JUAL_2`, `HARGA_JUAL_3`, `MIN_PEMBELIAN`, `MIN_PERSEN`, `BATAS_ATAS_STOCK`, `REORDER_POINT`, `STOK_TOKO`, `STOK_GUDANG`, `STOK_PUSAT`, `STOK_BS`, `UPDATE_HARGA`, `CREATED`, `UPDATED`, `HPP`, `HPP_UPDATED`, `PARENT_ID`, `TAG`, `EXPDAY`.

The same source confirms the production lookup shape:

- product-by-ID: `SELECT * FROM m_barang WHERE ID = ? LIMIT 1`
- product/barcode lookup: `m_barang LEFT JOIN m_barcode ON m_barang.ID = m_barcode.M_BARANG_ID`, filtered by barcode or product ID, ordered by barcode
- product search: `M_JENIS_JENIS NOT LIKE ... AND NAMA LIKE ?`, ordered by category/name
- rack lookup: `RAK_TOKO LIKE ?`, ordered by rack

The barcode row mapper confirms `m_barcode` columns: `ID`, `M_BARANG_ID`, `SATUAN`, `BARCODE`, `CREATED`, `UPDATED`.

The LOCAL login path is also now verified from `KaryawanService`: it defaults to MySQL `127.0.0.1:3306`, database `minimarket`, user `root`, empty password, and branch code/name `LOCAL`. These values are overridable with `pos.local.db.*` system properties. `ConnectionManager` constructs the JDBC URL from those credentials.

## Benchmark-only assumptions

`benchmark/mysql/00_synthetic_core_benchmark.sql` intentionally creates only a small subset of the database: `m_cabang`, `m_jenis`, `m_karyawan`, `m_role`, `m_barang`, and `m_barcode`. It must not be treated as a complete v29 schema.

The synthetic dataset currently generates 100,000 products and 3 barcodes per product. This is suitable for query-performance testing but does not prove that every POS workflow can run against the benchmark database.

## Important correction

The original `01_query_profile.sql` contains a barcode literal that does not match the generator formula in `00_synthetic_core_benchmark.sql`. For `B050000`, the generated PCS barcode is `8999000000050000`. A corrected companion profile is provided as `02_corrected_query_profile.sql`.

## LOCAL test boundary

Keep the databases separate:

```text
minimarket              <- LOCAL POS-SERVER v29 runtime database
minimarket_benchmark    <- disposable performance benchmark
```

The repository does not currently contain a confirmed complete operational v29 SQL dump. Therefore the benchmark must not be copied into `minimarket` as a substitute for the real schema. `benchmark/mysql/LOCAL-TEST-SETUP.md` documents the verified LOCAL connection defaults and the safe way to start the packaged JAR once the required operational schema is available.

## Next implementation target

Continue extracting the complete table/column dependency graph from `decompiled/pos-server-v29/minimart/engine/daoimpl`, `model`, and `rowmapper`. Only promote a table/column into the synthetic schema when it is supported by source evidence. Prioritize tables used by login, product search/barcode lookup, stock, sales/transactions, and synchronization paths.

Do not import this benchmark into a live/production database. Use a disposable MySQL schema only.
