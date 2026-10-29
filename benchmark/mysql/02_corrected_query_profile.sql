-- Focused repeatable profile for the synthetic benchmark.
-- Run after 00_synthetic_core_benchmark.sql in minimarket_benchmark only.
-- MySQL 8.0.18+: EXPLAIN ANALYZE executes the SELECT and reports actual timing.
--
-- The barcode literal below is verified from the generator formula:
-- CONCAT('899900', LPAD(SUBSTRING('B050000',2),10,'0')) = '8999000000050000'.
-- This file is a focused companion to 01_query_profile.sql, not a schema correction.

USE minimarket_benchmark;

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

SELECT 'Product name prefix baseline' AS test_name;
EXPLAIN ANALYZE
SELECT ID, NAMA
FROM m_barang
WHERE NAMA LIKE 'Produk Uji 050%'
ORDER BY NAMA
LIMIT 50;
