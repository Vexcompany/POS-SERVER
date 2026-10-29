-- Run after 00_synthetic_core_benchmark.sql in minimarket_benchmark only.
-- MySQL 8.0.18+: EXPLAIN ANALYZE executes each SELECT and reports actual timing.
-- This profiles query shapes observed in BarangDaoImpl and KaryawanDaoImpl.
USE minimarket_benchmark;

SELECT 'Exact product ID (primary key)' AS test_name;
EXPLAIN ANALYZE
SELECT ID, NAMA, HARGA_JUAL_1, STOK_TOKO
FROM m_barang
WHERE ID = 'B050000'
LIMIT 1;

-- Prefix search should be able to use idx_barang_nama; seed names are "Produk Uji NNNNNN".
SELECT 'Product name prefix (index-friendly)' AS test_name;
EXPLAIN ANALYZE
SELECT ID, NAMA
FROM m_barang
WHERE NAMA LIKE 'Produk Uji 050%'
ORDER BY NAMA
LIMIT 50;

-- Mirrors the contains-search shape used by BarangDaoImpl. Leading wildcard normally
-- prevents a B-tree range seek on NAMA; this is a deliberately harder workload.
SELECT 'Product name contains (leading wildcard)' AS test_name;
EXPLAIN ANALYZE
SELECT ID, M_JENIS_JENIS, NAMA
FROM m_barang
WHERE M_JENIS_JENIS NOT LIKE 'MAKANAN'
  AND NAMA LIKE '%Produk Uji 050%'
ORDER BY M_JENIS_JENIS, NAMA
LIMIT 100;

-- Keep barcode-only and product-ID lookups separate so the optimizer cannot choose
-- the primary-key branch of an OR expression and mask barcode-index performance.
SELECT 'Barcode exact lookup' AS test_name;
EXPLAIN ANALYZE
SELECT M_BARANG_ID, SATUAN, BARCODE
FROM m_barcode
WHERE BARCODE = '8999000000050000'
LIMIT 1;

SELECT 'Barcode lookup with product join' AS test_name;
EXPLAIN ANALYZE
SELECT b.ID, b.NAMA, b.HARGA_JUAL_1, bc.SATUAN, bc.BARCODE
FROM m_barcode AS bc
JOIN m_barang AS b ON b.ID = bc.M_BARANG_ID
WHERE bc.BARCODE = '8999000000050000'
LIMIT 1;

SELECT 'Rack prefix lookup' AS test_name;
EXPLAIN ANALYZE
SELECT ID, NAMA, RAK_TOKO
FROM m_barang
WHERE RAK_TOKO LIKE 'T-050%'
ORDER BY RAK_TOKO
LIMIT 500;

SELECT 'LOCAL employee lookup by ID and availability' AS test_name;
EXPLAIN ANALYZE
SELECT id, nama, role, available
FROM m_karyawan
WHERE id = 'GIO_415' AND available = 1;

SELECT 'Exact row counts' AS test_name;
SELECT 'm_barang' AS tabel, COUNT(*) AS jumlah FROM m_barang
UNION ALL SELECT 'm_barcode', COUNT(*) FROM m_barcode;

-- TABLE_ROWS in information_schema is an InnoDB estimate; use the COUNT(*) above
-- for exact row counts. DATA_LENGTH/INDEX_LENGTH are storage estimates in MiB.
SELECT 'Storage estimates (MiB)' AS test_name;
SELECT TABLE_NAME, TABLE_ROWS AS estimated_rows,
       ROUND(DATA_LENGTH/1024/1024,2) AS data_mb,
       ROUND(INDEX_LENGTH/1024/1024,2) AS index_mb
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'minimarket_benchmark'
ORDER BY DATA_LENGTH DESC;
