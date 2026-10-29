-- Run after 00_synthetic_core_benchmark.sql in minimarket_benchmark only.
-- MySQL 8.0.18+: EXPLAIN ANALYZE executes each SELECT and reports actual timing.
-- Profiles DAO query shapes. LOCAL development authentication itself is environment-variable based.
USE minimarket_benchmark;

SELECT 'Exact product ID (primary key)' AS test_name;
EXPLAIN ANALYZE SELECT ID, NAMA, HARGA_JUAL_1, STOK_TOKO FROM m_barang WHERE ID = 'B050000' LIMIT 1;

SELECT 'Product name prefix (index-friendly)' AS test_name;
EXPLAIN ANALYZE SELECT ID, NAMA FROM m_barang WHERE NAMA LIKE 'Produk Uji 050%' ORDER BY NAMA LIMIT 50;

SELECT 'Product name contains (leading wildcard)' AS test_name;
EXPLAIN ANALYZE SELECT ID, M_JENIS_JENIS, NAMA FROM m_barang WHERE M_JENIS_JENIS NOT LIKE 'MAKANAN' AND NAMA LIKE '%Produk Uji 050%' ORDER BY M_JENIS_JENIS, NAMA LIMIT 100;

SELECT 'Barcode exact lookup' AS test_name;
EXPLAIN ANALYZE SELECT M_BARANG_ID, SATUAN, BARCODE FROM m_barcode WHERE BARCODE = '8999000000050000' LIMIT 1;

SELECT 'Barcode lookup with product join' AS test_name;
EXPLAIN ANALYZE SELECT b.ID, b.NAMA, b.HARGA_JUAL_1, bc.SATUAN, bc.BARCODE FROM m_barcode AS bc JOIN m_barang AS b ON b.ID = bc.M_BARANG_ID WHERE bc.BARCODE = '8999000000050000' LIMIT 1;

-- BarangDaoImpl binds the SAME search value to both sides of this OR. Keep the
-- product-ID and barcode cases separate so the benchmark does not test an impossible
-- mixed input that matches two different rows.
SELECT 'DAO combined lookup with product ID input' AS test_name;
EXPLAIN ANALYZE SELECT * FROM m_barang LEFT JOIN m_barcode ON m_barang.ID = m_barcode.M_BARANG_ID WHERE BARCODE = 'B050000' OR m_barang.ID = 'B050000' ORDER BY BARCODE ASC LIMIT 1;

SELECT 'DAO combined lookup with barcode input' AS test_name;
EXPLAIN ANALYZE SELECT * FROM m_barang LEFT JOIN m_barcode ON m_barang.ID = m_barcode.M_BARANG_ID WHERE BARCODE = '8999000000050000' OR m_barang.ID = '8999000000050000' ORDER BY BARCODE ASC LIMIT 1;

-- Candidate comparison only: split the mutually exclusive ID/barcode search paths.
-- This is not a production DAO change; validate result parity before adopting it.
SELECT 'Split-path candidate with product ID input' AS test_name;
EXPLAIN ANALYZE SELECT * FROM m_barang LEFT JOIN m_barcode ON m_barang.ID = m_barcode.M_BARANG_ID WHERE m_barang.ID = 'B050000' ORDER BY BARCODE ASC LIMIT 1;

SELECT 'Split-path candidate with barcode input' AS test_name;
EXPLAIN ANALYZE SELECT * FROM m_barang LEFT JOIN m_barcode ON m_barang.ID = m_barcode.M_BARANG_ID WHERE BARCODE = '8999000000050000' ORDER BY BARCODE ASC LIMIT 1;

SELECT 'Rack prefix lookup' AS test_name;
EXPLAIN ANALYZE SELECT ID, NAMA, RAK_TOKO FROM m_barang WHERE RAK_TOKO LIKE 'T-050%' ORDER BY RAK_TOKO LIMIT 500;

SELECT 'Employee DAO lookup by ID and availability' AS test_name;
EXPLAIN ANALYZE SELECT id, nama, role, available FROM m_karyawan WHERE id = 'GIO_415' AND available = 1;

SELECT 'Employee DAO lookup by password and availability' AS test_name;
EXPLAIN ANALYZE SELECT id, nama, role, available FROM m_karyawan WHERE password = 'GIO_415' AND available = 1;

SELECT 'Exact row counts' AS test_name;
SELECT 'm_barang' AS tabel, COUNT(*) AS jumlah FROM m_barang UNION ALL SELECT 'm_barcode', COUNT(*) FROM m_barcode;

-- TABLE_ROWS is an InnoDB estimate; COUNT(*) above gives exact counts.
SELECT 'Storage estimates (MiB)' AS test_name;
SELECT TABLE_NAME, TABLE_ROWS AS estimated_rows, ROUND(DATA_LENGTH/1024/1024,2) AS data_mb, ROUND(INDEX_LENGTH/1024/1024,2) AS index_mb FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'minimarket_benchmark' ORDER BY DATA_LENGTH DESC;
