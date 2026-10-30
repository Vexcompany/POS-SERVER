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

-- DDL contract based on the m_barang SHOW CREATE TABLE / SHOW INDEX evidence
-- supplied for the user's v29 benchmark database. These checks prevent a future
-- fixture edit from silently drifting back to integer money/quantity or DATETIME
-- timestamps for m_barang.
SELECT 'm_barang KONVERSI_21 DECIMAL(14,3)' AS check_name,
       IF(EXISTS (
         SELECT 1 FROM information_schema.COLUMNS
         WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'm_barang'
           AND COLUMN_NAME = 'KONVERSI_21' AND DATA_TYPE = 'decimal'
           AND NUMERIC_PRECISION = 14 AND NUMERIC_SCALE = 3
       ), 'PASS', 'FAIL') AS status
UNION ALL
SELECT 'm_barang HARGA_JUAL_1 DECIMAL(14,2)',
       IF(EXISTS (
         SELECT 1 FROM information_schema.COLUMNS
         WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'm_barang'
           AND COLUMN_NAME = 'HARGA_JUAL_1' AND DATA_TYPE = 'decimal'
           AND NUMERIC_PRECISION = 14 AND NUMERIC_SCALE = 2
       ), 'PASS', 'FAIL')
UNION ALL
SELECT 'm_barang HPP DECIMAL(14,2)',
       IF(EXISTS (
         SELECT 1 FROM information_schema.COLUMNS
         WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'm_barang'
           AND COLUMN_NAME = 'HPP' AND DATA_TYPE = 'decimal'
           AND NUMERIC_PRECISION = 14 AND NUMERIC_SCALE = 2
       ), 'PASS', 'FAIL')
UNION ALL
SELECT 'm_barang HPP_UPDATED, CREATED and UPDATED BIGINT',
       IF(
         (SELECT COUNT(*) FROM information_schema.COLUMNS
          WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'm_barang'
            AND COLUMN_NAME IN ('HPP_UPDATED','CREATED','UPDATED') AND DATA_TYPE = 'bigint') = 3,
         'PASS', 'FAIL')
UNION ALL
SELECT 'm_barang legacy idx_barang_barcode_search shape',
       IF(
         (SELECT GROUP_CONCAT(COLUMN_NAME ORDER BY SEQ_IN_INDEX SEPARATOR ',')
          FROM information_schema.STATISTICS
          WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'm_barang'
            AND INDEX_NAME = 'idx_barang_barcode_search')
          = 'M_JENIS_JENIS,STOK_TOKO',
         'PASS', 'FAIL');

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

SELECT 'intentional cross-namespace collision row' AS check_name,
       COUNT(*) AS actual_value,
       1 AS expected_value,
       IF(COUNT(*) = 1, 'PASS', 'FAIL') AS status
FROM m_barcode
WHERE M_BARANG_ID = 'B050001'
  AND SATUAN = 'PACK'
  AND BARCODE = '000001';

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

-- Compare the old DAO query with both the naive split and the UNION candidate
-- for ordinary product-ID and barcode inputs.
WITH old_result AS (
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barang AS b
    LEFT JOIN m_barcode AS bc ON b.ID = bc.M_BARANG_ID
    WHERE bc.BARCODE = 'B050000' OR b.ID = 'B050000'
    ORDER BY bc.BARCODE ASC
    LIMIT 1
),
split_result AS (
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barang AS b
    LEFT JOIN m_barcode AS bc ON b.ID = bc.M_BARANG_ID
    WHERE b.ID = 'B050000'
    ORDER BY bc.BARCODE ASC
    LIMIT 1
),
union_candidates AS (
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barang AS b
    LEFT JOIN m_barcode AS bc ON b.ID = bc.M_BARANG_ID
    WHERE b.ID = 'B050000'
    UNION ALL
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barcode AS bc
    JOIN m_barang AS b ON b.ID = bc.M_BARANG_ID
    WHERE bc.BARCODE = 'B050000' AND b.ID <> 'B050000'
),
union_result AS (
    SELECT product_id, barcode
    FROM union_candidates
    ORDER BY barcode ASC
    LIMIT 1
)
SELECT 'product ID input old-vs-candidate parity' AS check_name,
       old_result.product_id AS old_product_id,
       split_result.product_id AS split_product_id,
       union_result.product_id AS union_product_id,
       IF(old_result.product_id <=> split_result.product_id
          AND old_result.barcode <=> split_result.barcode, 'PASS', 'FAIL') AS naive_split_status,
       IF(old_result.product_id <=> union_result.product_id
          AND old_result.barcode <=> union_result.barcode, 'PASS', 'FAIL') AS union_status
FROM old_result CROSS JOIN split_result CROSS JOIN union_result;

WITH old_result AS (
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barang AS b
    LEFT JOIN m_barcode AS bc ON b.ID = bc.M_BARANG_ID
    WHERE bc.BARCODE = '8999000000050000'
       OR b.ID = '8999000000050000'
    ORDER BY bc.BARCODE ASC
    LIMIT 1
),
split_result AS (
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barang AS b
    LEFT JOIN m_barcode AS bc ON b.ID = bc.M_BARANG_ID
    WHERE bc.BARCODE = '8999000000050000'
    ORDER BY bc.BARCODE ASC
    LIMIT 1
),
union_candidates AS (
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barang AS b
    LEFT JOIN m_barcode AS bc ON b.ID = bc.M_BARANG_ID
    WHERE b.ID = '8999000000050000'
    UNION ALL
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barcode AS bc
    JOIN m_barang AS b ON b.ID = bc.M_BARANG_ID
    WHERE bc.BARCODE = '8999000000050000'
      AND b.ID <> '8999000000050000'
),
union_result AS (
    SELECT product_id, barcode
    FROM union_candidates
    ORDER BY barcode ASC
    LIMIT 1
)
SELECT 'barcode input old-vs-candidate parity' AS check_name,
       old_result.product_id AS old_product_id,
       split_result.product_id AS split_product_id,
       union_result.product_id AS union_product_id,
       IF(old_result.product_id <=> split_result.product_id
          AND old_result.barcode <=> split_result.barcode, 'PASS', 'FAIL') AS naive_split_status,
       IF(old_result.product_id <=> union_result.product_id
          AND old_result.barcode <=> union_result.barcode, 'PASS', 'FAIL') AS union_status
FROM old_result CROSS JOIN split_result CROSS JOIN union_result;

-- The original DAO OR query can match both a product ID and a barcode.
-- The fixture deliberately maps BARCODE='000001' to product B050001 while product
-- 000001 also exists. A naive ID-only split changes the result; the UNION candidate
-- must preserve the old query's selected product and sort barcode.
WITH old_result AS (
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barang AS b
    LEFT JOIN m_barcode AS bc ON b.ID = bc.M_BARANG_ID
    WHERE bc.BARCODE = '000001' OR b.ID = '000001'
    ORDER BY bc.BARCODE ASC
    LIMIT 1
),
split_result AS (
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barang AS b
    LEFT JOIN m_barcode AS bc ON b.ID = bc.M_BARANG_ID
    WHERE b.ID = '000001'
    ORDER BY bc.BARCODE ASC
    LIMIT 1
),
union_candidates AS (
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barang AS b
    LEFT JOIN m_barcode AS bc ON b.ID = bc.M_BARANG_ID
    WHERE b.ID = '000001'
    UNION ALL
    SELECT b.ID AS product_id, bc.BARCODE AS barcode
    FROM m_barcode AS bc
    JOIN m_barang AS b ON b.ID = bc.M_BARANG_ID
    WHERE bc.BARCODE = '000001' AND b.ID <> '000001'
),
union_result AS (
    SELECT product_id, barcode
    FROM union_candidates
    ORDER BY barcode ASC
    LIMIT 1
)
SELECT 'overlapping ID/barcode old-vs-UNION parity and split-path guard' AS check_name,
       old_result.product_id AS old_product_id,
       old_result.barcode AS old_barcode,
       split_result.product_id AS naive_split_product_id,
       split_result.barcode AS naive_split_barcode,
       union_result.product_id AS union_product_id,
       union_result.barcode AS union_barcode,
       IF(
         old_result.product_id <=> union_result.product_id
         AND old_result.barcode <=> union_result.barcode
         AND NOT (
           old_result.product_id <=> split_result.product_id
           AND old_result.barcode <=> split_result.barcode
         ),
         'PASS', 'FAIL'
       ) AS status
FROM old_result
CROSS JOIN split_result
CROSS JOIN union_result;

-- This script is read-only and intentionally does not inspect or modify minimarket.
