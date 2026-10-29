-- Read-only fixture checks. Run after 00_synthetic_core_benchmark.sql
-- and before query profiling. Expected result is PASS for all status checks.
USE minimarket_benchmark;

SELECT 'm_barang row count' AS check_name,
       COUNT(*) AS actual_value,
       100000 AS expected_value,
       IF(COUNT(*) = 100000, 'PASS', 'FAIL') AS status
FROM m_barang
UNION ALL
SELECT 'm_barcode row count',
       COUNT(*),
       300000,
       IF(COUNT(*) = 300000, 'PASS', 'FAIL')
FROM m_barcode;

SELECT 'known product and generated PCS barcode' AS check_name,
       expected.ID AS expected_id,
       b.ID AS actual_id,
       b.NAMA,
       bc.BARCODE,
       IF(b.NAMA = 'Produk Uji 050000'
          AND bc.BARCODE = '8999000000050000', 'PASS', 'FAIL') AS status
FROM (SELECT 'B050000' AS ID) AS expected
LEFT JOIN m_barang AS b ON b.ID = expected.ID
LEFT JOIN m_barcode AS bc
  ON bc.M_BARANG_ID = expected.ID AND bc.SATUAN = 'PCS';

SELECT 'name prefix result count' AS check_name,
       COUNT(*) AS actual_value,
       1000 AS expected_value,
       IF(COUNT(*) = 1000, 'PASS', 'FAIL') AS status
FROM m_barang
WHERE NAMA LIKE 'Produk Uji 050%';

SELECT 'products without exactly three barcodes' AS check_name,
       COUNT(*) AS actual_value,
       0 AS expected_value,
       IF(COUNT(*) = 0, 'PASS', 'FAIL') AS status
FROM (
    SELECT b.ID
    FROM m_barang AS b
    LEFT JOIN m_barcode AS bc ON bc.M_BARANG_ID = b.ID
    GROUP BY b.ID
    HAVING COUNT(bc.ID) <> 3
) AS mismatches;

-- This script is read-only and intentionally does not inspect or modify minimarket.
