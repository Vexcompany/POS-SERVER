-- Corrected profiling companion for 01_query_profile.sql.
-- Run after 00_synthetic_core_benchmark.sql in the disposable benchmark DB.
-- MySQL 8.0.18+: EXPLAIN ANALYZE executes the SELECT and reports actual timing.
--
-- Why this exists:
-- 01_query_profile.sql used a barcode literal that does not match the value
-- generated for B050000 by the synthetic dataset. This file keeps the original
-- benchmark intact while providing a verified barcode baseline.

USE minimarket_benchmark;

-- Generator formula in 00_synthetic_core_benchmark.sql:
-- CONCAT('899900', LPAD(SUBSTRING(ID,2),10,'0'))
-- For B050000 => 8999000000050000.
SELECT 'Barcode-only indexed lookup' AS test_name;
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

SELECT 'Exact product ID baseline' AS test_name;
EXPLAIN ANALYZE
SELECT ID, NAMA, HARGA_JUAL_1, STOK_TOKO
FROM m_barang
WHERE ID = 'B050000'
LIMIT 1;

-- This intentionally represents a difficult contains-search pattern.
-- idx_barang_nama cannot normally seek into a leading-wildcard LIKE pattern.
SELECT 'Product name contains search' AS test_name;
EXPLAIN ANALYZE
SELECT ID, M_JENIS_JENIS, NAMA
FROM m_barang
WHERE M_JENIS_JENIS <> 'MAKANAN'
  AND NAMA LIKE '%Produk Uji 050%'
ORDER BY M_JENIS_JENIS, NAMA
LIMIT 100;

SELECT 'Rack prefix lookup' AS test_name;
EXPLAIN ANALYZE
SELECT ID, NAMA, RAK_TOKO
FROM m_barang
WHERE RAK_TOKO LIKE 'T-050%'
ORDER BY RAK_TOKO
LIMIT 500;

SELECT 'Employee login lookup' AS test_name;
EXPLAIN ANALYZE
SELECT id, nama, role, available
FROM m_karyawan
WHERE id = 'GIO_415' AND available = 1;

SELECT 'Rows and storage' AS test_name;
SELECT TABLE_NAME, TABLE_ROWS,
       ROUND(DATA_LENGTH/1024/1024,2) AS data_mb,
       ROUND(INDEX_LENGTH/1024/1024,2) AS index_mb
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'minimarket_benchmark'
ORDER BY DATA_LENGTH DESC;
