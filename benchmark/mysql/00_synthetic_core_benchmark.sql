-- POS-SERVER v29: isolated synthetic query benchmark.
-- WARNING: this script DROPS and recreates six tables in minimarket_benchmark.
-- It is destructive to existing benchmark data. Never point it at minimarket or production.
-- MySQL 8.0+ required for the recursive CTE; EXPLAIN ANALYZE requires 8.0.18+.
--
-- This is not a complete operational schema. Column names and JDBC access patterns are
-- source-backed. m_barang physical types/indexes mirror the SHOW CREATE TABLE / SHOW INDEX
-- output supplied by the user for that benchmark table; the other fixture tables still use
-- explicitly synthetic type choices because their DDL is unavailable. Indexes on those
-- other tables are benchmark candidates, not claims about production. Unproven uniqueness/
-- FK constraints are omitted.

CREATE DATABASE IF NOT EXISTS minimarket_benchmark
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE minimarket_benchmark;

DROP TABLE IF EXISTS m_barcode;
DROP TABLE IF EXISTS m_barang;
DROP TABLE IF EXISTS m_karyawan;
DROP TABLE IF EXISTS m_role;
DROP TABLE IF EXISTS m_jenis;
DROP TABLE IF EXISTS m_cabang;

-- Column names are evidenced by CabangDaoImpl/CabangRowMapper.
CREATE TABLE m_cabang (
  ID INT NOT NULL PRIMARY KEY,
  NAMA VARCHAR(120) NOT NULL,
  ALAMAT VARCHAR(255) NULL,
  TIPE_TOKO VARCHAR(80) NULL,
  TIPE_CABANG VARCHAR(80) NULL
) ENGINE=InnoDB;

-- JenisRowMapper reads CREATED/UPDATED as java.sql.Date. This fixture uses DATETIME;
-- the original physical SQL type is not established by the RowMapper.
CREATE TABLE m_jenis (
  JENIS VARCHAR(120) NOT NULL PRIMARY KEY,
  POIN_SALES INT NOT NULL DEFAULT 0,
  CREATED DATETIME NULL,
  UPDATED DATETIME NULL,
  MARGIN_CABANG DECIMAL(8,2) NOT NULL DEFAULT 0,
  MARGIN_TEMPO DECIMAL(8,2) NOT NULL DEFAULT 0
) ENGINE=InnoDB;

-- KaryawanRowMapper reads CREATED/UPDATED as dates and GAJI as a long. DATETIME/BIGINT
-- are fixture choices; verify physical SQL types against the original DDL.
CREATE TABLE m_karyawan (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  nama VARCHAR(160) NOT NULL,
  password VARCHAR(255) NOT NULL DEFAULT '',
  gaji BIGINT NOT NULL DEFAULT 0,
  role VARCHAR(80) NOT NULL DEFAULT 'KASIR',
  available TINYINT NOT NULL DEFAULT 1,
  created DATETIME NULL,
  updated DATETIME NULL,
  KEY idx_karyawan_password_available (password, available)
) ENGINE=InnoDB;

-- RoleRowMapper reads UPDATED as a date; DATETIME is a fixture choice, not a verified
-- production type. Role/menu uniqueness is not asserted, so use a normal lookup index.
CREATE TABLE m_role (
  id VARCHAR(80) NOT NULL PRIMARY KEY,
  role VARCHAR(80) NOT NULL,
  menu VARCHAR(120) NOT NULL,
  access INT NOT NULL DEFAULT 0,
  updated DATETIME NULL,
  updated_by VARCHAR(80) NULL,
  KEY idx_role_role_menu (role, menu)
) ENGINE=InnoDB;

-- Physical types and indexes in this m_barang fixture match the SHOW CREATE TABLE and
-- SHOW INDEX output supplied for the user's v29 benchmark table. This evidence is for
-- m_barang only; it is not a complete operational schema or proof for the other tables.
-- CREATED/UPDATED are BIGINT in that observed DDL even though BarangRowMapper calls
-- ResultSet.getDate(). HPP_UPDATED is BIGINT and BarangDaoImpl writes UNIX_TIMESTAMP().
CREATE TABLE m_barang (
  ID VARCHAR(40) NOT NULL PRIMARY KEY,
  M_JENIS_JENIS VARCHAR(120) NOT NULL DEFAULT '',
  RAK_GUDANG VARCHAR(80) NOT NULL DEFAULT '',
  RAK_TOKO VARCHAR(80) NOT NULL DEFAULT '',
  NAMA VARCHAR(255) NOT NULL,
  SATUAN_1 VARCHAR(40) NOT NULL DEFAULT 'PCS',
  SATUAN_2 VARCHAR(40) NOT NULL DEFAULT '',
  SATUAN_3 VARCHAR(40) NOT NULL DEFAULT '',
  KONVERSI_21 DECIMAL(14,3) NOT NULL DEFAULT 1.000,
  KONVERSI_32 DECIMAL(14,3) NOT NULL DEFAULT 1.000,
  BATAS_ATAS_STOCK DECIMAL(14,3) NOT NULL DEFAULT 0.000,
  REORDER_POINT DECIMAL(14,3) NOT NULL DEFAULT 0.000,
  HARGA_JUAL_1 DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  HARGA_JUAL_2 DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  HARGA_JUAL_3 DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  MIN_PEMBELIAN DECIMAL(14,3) NOT NULL DEFAULT 0,
  MIN_PERSEN DECIMAL(8,2) NOT NULL DEFAULT 0,
  STOK_TOKO DECIMAL(14,3) NOT NULL DEFAULT 0,
  STOK_GUDANG DECIMAL(14,3) NOT NULL DEFAULT 0,
  STOK_BS DECIMAL(14,3) NOT NULL DEFAULT 0,
  UPDATE_HARGA INT NOT NULL DEFAULT 0,
  HPP DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  HPP_UPDATED BIGINT DEFAULT NULL,
  CREATED BIGINT DEFAULT NULL,
  UPDATED BIGINT DEFAULT NULL,
  STOK_PUSAT DECIMAL(14,3) NOT NULL DEFAULT 0,
  PARENT_ID VARCHAR(40) NOT NULL DEFAULT '',
  TAG VARCHAR(120) NULL,
  EXPDAY INT NULL,
  KEY idx_barang_nama (NAMA),
  KEY idx_barang_jenis_nama (M_JENIS_JENIS, NAMA),
  KEY idx_barang_rak_toko (RAK_TOKO),
  KEY idx_barang_rak_gudang (RAK_GUDANG),
  KEY idx_barang_parent (PARENT_ID),
  KEY idx_barang_update_harga (UPDATE_HARGA),
  KEY idx_barang_barcode_search (M_JENIS_JENIS, STOK_TOKO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- BarcodeRowMapper reads ID as int. INT and DATETIME are fixture choices. Barcode
-- uniqueness and a physical FK are not asserted by the DAO source, so omit those constraints.
CREATE TABLE m_barcode (
  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  M_BARANG_ID VARCHAR(40) NOT NULL,
  SATUAN VARCHAR(40) NOT NULL DEFAULT 'PCS',
  BARCODE VARCHAR(80) NOT NULL,
  CREATED DATETIME NULL,
  UPDATED DATETIME NULL,
  KEY idx_barcode_lookup (BARCODE),
  KEY idx_barcode_barang_satuan (M_BARANG_ID, SATUAN)
) ENGINE=InnoDB;

INSERT INTO m_cabang (ID, NAMA, ALAMAT, TIPE_TOKO, TIPE_CABANG)
VALUES (1, 'BENCHMARK - CABANG UJI', 'Alamat Uji', 'TOKO', 'CABANG');

INSERT INTO m_jenis (JENIS, POIN_SALES, CREATED, UPDATED, MARGIN_CABANG, MARGIN_TEMPO)
VALUES
('MAKANAN',1,NOW(),NOW(),5,3),
('MINUMAN',1,NOW(),NOW(),5,3),
('KEBUTUHAN RUMAH',1,NOW(),NOW(),5,3),
('PERAWATAN',1,NOW(),NOW(),5,3),
('LAINNYA',0,NOW(),NOW(),0,0);

INSERT INTO m_karyawan (id,nama,password,gaji,role,available,created,updated)
VALUES ('GIO_415','Pengguna Benchmark','GIO_415',0,'ADMIN',1,NOW(),NOW());

INSERT INTO m_role (id,role,menu,access,updated,updated_by) VALUES
('BENCH-ADMIN-HOME','ADMIN','HOME',1,NOW(),'BENCHMARK'),
('BENCH-ADMIN-BARANG','ADMIN','BARANG',1,NOW(),'BENCHMARK'),
('BENCH-ADMIN-PENJUALAN','ADMIN','PENJUALAN',1,NOW(),'BENCHMARK'),
('BENCH-ADMIN-STOK','ADMIN','STOK',1,NOW(),'BENCHMARK');

-- Generate 100,000 synthetic products. No real customer/employee data.
-- To test smaller scale, change 100000 to 10000.
SET SESSION cte_max_recursion_depth = 100001;
SET @fixture_now = CAST(DATE_FORMAT(NOW(), '%Y%m%d%H%i%s') AS UNSIGNED);

INSERT INTO m_barang
(ID,M_JENIS_JENIS,RAK_GUDANG,RAK_TOKO,NAMA,SATUAN_1,SATUAN_2,SATUAN_3,
 KONVERSI_21,KONVERSI_32,BATAS_ATAS_STOCK,REORDER_POINT,
 HARGA_JUAL_1,HARGA_JUAL_2,HARGA_JUAL_3,MIN_PEMBELIAN,MIN_PERSEN,
 STOK_TOKO,STOK_GUDANG,STOK_BS,UPDATE_HARGA,HPP,HPP_UPDATED,CREATED,UPDATED,
 STOK_PUSAT,PARENT_ID,TAG,EXPDAY)
WITH RECURSIVE seq(n) AS (
  SELECT 1 UNION ALL SELECT n+1 FROM seq WHERE n < 100000
)
SELECT
  CONCAT('B',LPAD(n,6,'0')),
  ELT(1+MOD(n,5),'MAKANAN','MINUMAN','KEBUTUHAN RUMAH','PERAWATAN','LAINNYA'),
  CONCAT('G-',LPAD(1+MOD(n,120),3,'0')),
  CONCAT('T-',LPAD(1+MOD(n,250),3,'0')),
  CONCAT('Produk Uji ',LPAD(n,6,'0')),
  'PCS','BOX','',
  12,1,100,5,
  1000+MOD(n*37,50000),12000+MOD(n*31,90000),0,
  1,5,
  MOD(n*7,400),MOD(n*11,700),MOD(n,15),
  MOD(n,2),500+MOD(n*19,25000),UNIX_TIMESTAMP(),
  @fixture_now,@fixture_now,MOD(n*13,900),
  '',IF(MOD(n,3)=0,'PROMO',NULL),365
FROM seq;

-- Three deterministic barcodes per product; the lookup index is non-unique.
INSERT INTO m_barcode (M_BARANG_ID,SATUAN,BARCODE,CREATED,UPDATED)
SELECT ID,'PCS',CONCAT('899900',LPAD(SUBSTRING(ID,2),10,'0')),NOW(),NOW()
FROM m_barang;
INSERT INTO m_barcode (M_BARANG_ID,SATUAN,BARCODE,CREATED,UPDATED)
SELECT ID,'BOX',CONCAT('899910',LPAD(SUBSTRING(ID,2),10,'0')),NOW(),NOW()
FROM m_barang;
INSERT INTO m_barcode (M_BARANG_ID,SATUAN,BARCODE,CREATED,UPDATED)
SELECT ID,'PACK',CONCAT('899920',LPAD(SUBSTRING(ID,2),10,'0')),NOW(),NOW()
FROM m_barang;

-- Intentional edge case: product ID 000001 also appears as a barcode on B050001.
-- A numeric-leading value sorts before the generated 899... barcodes, making the old
-- DAO ORDER BY choose the barcode match. Keep row counts and three barcodes per product.
UPDATE m_barang SET ID = '000001' WHERE ID = 'B099999';
UPDATE m_barcode SET M_BARANG_ID = '000001' WHERE M_BARANG_ID = 'B099999';
UPDATE m_barcode
SET BARCODE = '000001'
WHERE M_BARANG_ID = 'B050001' AND SATUAN = 'PACK';

ANALYZE TABLE m_barang, m_barcode, m_karyawan, m_role, m_jenis, m_cabang;

-- Exact row counts (unlike information_schema.TABLES.TABLE_ROWS, which is estimated).
SELECT 'm_barang' AS tabel, COUNT(*) AS jumlah FROM m_barang
UNION ALL SELECT 'm_barcode', COUNT(*) FROM m_barcode
UNION ALL SELECT 'm_karyawan', COUNT(*) FROM m_karyawan
UNION ALL SELECT 'm_role', COUNT(*) FROM m_role;
