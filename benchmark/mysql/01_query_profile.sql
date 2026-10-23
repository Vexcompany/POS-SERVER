-- Run after 00_synthetic_core_benchmark.sql in the disposable benchmark DB.
-- MySQL 8.0.18+: EXPLAIN ANALYZE executes the SELECT and reports actual timing.
USE minimarket_benchmark;

SELECT 'Exact product ID' AS test_name;
EXPLAIN ANALYZE SELECT * FROM m_barang WHERE ID = 'B050000' LIMIT 1;

SELECT 'Product name contains search (wildcard at both ends)' AS test_name;
EXPLAIN ANALYZE SELECT * FROM m_barang
WHERE M_JENIS_JENIS NOT LIKE 'MAKANAN'
  AND NAMA LIKE '%Produk Uji 050%'
ORDER BY M_JENIS_JENIS, NAMA
LIMIT 100;

SELECT 'Barcode lookup with product join' AS test_name;
EXPLAIN ANALYZE SELECT *
FROM m_barang
LEFT JOIN m_barcode ON m_barang.ID = m_barcode.M_BARANG_ID
WHERE BARCODE = '89990000000050000'
   OR m_barang.ID = 'B050000'
ORDER BY BARCODE ASC
LIMIT 1;

SELECT 'Rack prefix lookup' AS test_name;
EXPLAIN ANALYZE SELECT * FROM m_barang
WHERE RAK_TOKO LIKE 'T-050%'
ORDER BY RAK_TOKO
LIMIT 500;

SELECT 'Employee login lookup' AS test_name;
EXPLAIN ANALYZE SELECT * FROM m_karyawan
WHERE id = 'GIO_415' AND available = 1;

SELECT 'Rows and storage' AS test_name;
SELECT TABLE_NAME, TABLE_ROWS,
       ROUND(DATA_LENGTH/1024/1024,2) AS data_mb,
       ROUND(INDEX_LENGTH/1024/1024,2) AS index_mb
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'minimarket_benchmark'
ORDER BY DATA_LENGTH DESC;
