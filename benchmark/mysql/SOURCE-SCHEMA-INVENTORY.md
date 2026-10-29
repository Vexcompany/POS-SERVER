# POS-SERVER v29 source-derived schema inventory

## Scope and confidence

This inventory was compiled from the decompiled source under decompiled/pos-server-v29/minimart/engine. It records table/column names and dependencies explicitly observed in DAO SQL and RowMappers. It is **not** a replacement for the original DDL: source snippets do not reliably establish all column types, nullability, defaults, indexes, foreign keys, triggers, views, event jobs or stored procedures.

## LOCAL login and core catalogue

| Table | Source evidence | Observed columns / dependency |
|---|---|---|
| m_cabang | daoimpl/CabangDaoImpl.java, rowmapper/CabangRowMapper.java | ID, NAMA, ALAMAT, TIPE_TOKO, TIPE_CABANG |
| m_karyawan | daoimpl/KaryawanDaoImpl.java, rowmapper/KaryawanRowMapper.java, model/Karyawan.java | ID, NAMA, PASSWORD, GAJI, ROLE, AVAILABLE, CREATED, UPDATED; DAO queries by ID/password with AVAILABLE = 1. This is not the LOCAL authentication shortcut. |
| m_role | daoimpl/RoleDaoImpl.java, rowmapper/RoleRowMapper.java, model/Role.java | ID, ROLE, MENU, ACCESS, UPDATED, UPDATED_BY; access lookup/update by ROLE + MENU |
| m_kasir | daoimpl/KasirDaoImpl.java, rowmapper/KasirRowMapper.java | ID, CREATED, UPDATED |
| m_jenis | daoimpl/JenisDaoImpl.java, rowmapper/JenisRowMapper.java | JENIS, POIN_SALES, CREATED, UPDATED, MARGIN_CABANG, MARGIN_TEMPO |
| m_barang | daoimpl/BarangDaoImpl.java, rowmapper/BarangRowMapper.java, model/Barang.java | ID, M_JENIS_JENIS, RAK_GUDANG, RAK_TOKO, NAMA, SATUAN_1, SATUAN_2, SATUAN_3, KONVERSI_21, KONVERSI_32, BATAS_ATAS_STOCK, REORDER_POINT, HARGA_JUAL_1, HARGA_JUAL_2, HARGA_JUAL_3, MIN_PEMBELIAN, MIN_PERSEN, STOK_TOKO, STOK_GUDANG, STOK_BS, UPDATE_HARGA, HPP, HPP_UPDATED, CREATED, UPDATED, STOK_PUSAT, PARENT_ID, TAG, EXPDAY |
| m_barcode | daoimpl/BarcodeDaoImpl.java, rowmapper/BarcodeRowMapper.java, model/Barcode.java | ID, M_BARANG_ID, SATUAN, BARCODE, CREATED, UPDATED; exact barcode lookup and product/unit lookup |
| m_paket | daoimpl/PaketDaoImpl.java, rowmapper/PaketRowMapper.java | ID, M_BARANG_ID, CREATED, UPDATED, STATUS; joins m_barang |
| m_paket_detail | daoimpl/PaketDetailDaoImpl.java, rowmapper/PaketDetailRowMapper.java | ID, M_PAKET_ID, M_BARANG_ID, JUMLAH_BARANG |

### Source-derived query shapes

- m_barang: exact ID lookup; barcode join with m_barcode; category/name search ordered by M_JENIS_JENIS, NAMA; rack prefix search; parent lookup; price-update flag; stock/reorder filters.
- m_barcode: exact BARCODE lookup and lookup by M_BARANG_ID + SATUAN.
- m_karyawan: ID + AVAILABLE, password + AVAILABLE, and name contains search.
- m_role: exact access lookup by ROLE + MENU; role search and ID lookup.

## Sales and cash register

| Table | Source evidence | Observed columns / dependency |
|---|---|---|
| t_penjualan | daoimpl/PenjualanDaoImpl.java, rowmapper/PenjualanRowMapper.java | ID, M_CUSTOMER_ID, M_KARYAWAN_ID, M_SALES_ID, KASIR, TANGGAL, TOTAL_PENJUALAN, STATUS, STATUS_PELUNASAN, POIN, CREATED, UPDATED, JATUH_TEMPO, KETERANGAN, REFERENSI, M_SHIFT_KASIR_ID, VOUCHER, ID_TRANSAKSI_ONLINE, ELIGIBLE_PWP, ELIGIBLE_TEBUS_MURAH, LAMA_TRANSAKSI, STATUS_SYNC_ONLINE; reports join m_customer |
| t_penjualan_detail | daoimpl/PenjualanDetailDaoImpl.java, rowmapper/PenjualanDetailRowMapper.java | ID, T_PENJUALAN_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, DISCOUNT_2, HARGA_JUAL, TOTAL_HARGA, TOTAL_KEUNTUNGAN, CREATED, UPDATED, REFERENSI, PROMO, STATUS_SYNC_ONLINE; joins t_penjualan, m_barang, m_customer |
| t_penjualan_sementara | daoimpl/PenjualanSementaraDaoImpl.java, rowmapper/PenjualanSementaraRowMapper.java | ID, KASIR, STATUS, SHORTCUT, M_CUSTOMER_ID, M_BARANG_ID, M_SALES_ID, JUMLAH_BARANG, SATUAN, HARGA_JUAL, DISCOUNT, TOTAL_HARGA, NOTA_KHUSUS, KETERANGAN, PICKINGLIST_ID, ID_TRANSAKSI_ONLINE, PROMO; joins m_barang, m_jenis |
| m_shift_kasir | daoimpl/ShiftKasirDaoImpl.java, rowmapper/ShiftKasirRowMapper.java | ID, ID_KASIR, OPENED_BY, CLOSED_BY, OPENING, CLOSING, TOTAL_UANG, JUMLAH_SETORAN, TRANSFER, DEBIT, QRIS, VOUCHER, STATUS_SYNC_ONLINE |
| m_customer | daoimpl/PenjualanDaoImpl.java, daoimpl/PenjualanDetailDaoImpl.java | Referenced by ID/name and joined to sales reports; full customer columns require further extraction |
| m_sales, m_voucher | daoimpl/PenjualanDaoImpl.java and related DAOs | Referenced by sales headers and joins/queries; full DDL not yet reconstructed |

## Stock, purchasing and package dependencies

| Table | Source evidence | Observed columns / dependency |
|---|---|---|
| stok_opnam | daoimpl/StokOpnamDaoImpl.java, rowmapper/StokOpnamRowMapper.java | ID, M_KARYAWAN_ID, STATUS_OPNAM, LOKASI, TIPE, TIPE_NAME, TANGGAL, KETERANGAN, UPDATED, CREATED, STATUS_SYNC_ONLINE |
| stok_opnam_detail | daoimpl/StokOpnamDetailDaoImpl.java, rowmapper/StokOpnamDetailRowMapper.java | ID, M_BARANG_ID, STOK_OPNAM_ID, JUMLAH_KOMPUTER, JUMLAH_TOKO, TOTAL, STATUS_SYNC_ONLINE; joins stok_opnam and m_barang |
| o_stock_transaction_commit | daoimpl/StockCommitDaoImpl.java | Insert/update fields observed on the table: ID_BARANG, ID_TRANSAKSI, QUERY_TYPE, STOCK_TYPE, JUMLAH_BARANG, STATUS_COMMIT, TRANSACTION_TYPE, TRANSACTION_CREATED, TRANSACTION_UPDATED, STATUS_DELETE, STOK_AWAL. NAMA and SATUAN_1 in reporting queries come from the joined m_barang row. DAO also calls stock-commit stored procedures. |
| t_pembelian | daoimpl/PembelianDaoImpl.java, rowmapper/PembelianRowMapper.java | ID, M_SUPPLIER_ID, M_KARYAWAN_ID, FAKTUR_ID, TANGGAL, PEMBAYARAN, JATUH_TEMPO, DISCOUNT_1, PPN, TOTAL, STATUS, KETERANGAN, PELUNASAN, TITIPAN, CREATED, UPDATED, ID_PO_ONLINE, STATUS_SYNC_ONLINE; joins m_supplier |
| t_pembelian_detail | daoimpl/PembelianDetailDaoImpl.java, rowmapper/PembelianDetailRowMapper.java | ID, T_PEMBELIAN_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, HARGA_BELI, DISCOUNT_2, TOTAL_HARGA, CREATED, UPDATED, STATUS_SYNC_ONLINE; joins t_pembelian, m_barang, m_supplier |
| m_supplier | daoimpl/SupplierDaoImpl.java, rowmapper/SupplierRowMapper.java | ID, NAMA, ALAMAT, KOTA, TELEPON, CONTACT_PERSON, AVAILABLE, CREATED, UPDATED, TOP |
| m_paket, m_paket_detail | daoimpl/PaketDaoImpl.java, daoimpl/PaketDetailDaoImpl.java | Package header/detail; m_paket_detail references package and component-product IDs |

## Runtime migrations and stored procedure gaps

decompiled/pos-server-v29/minimart/engine/controller/Controller.java contains runtime column checks/additions for fields including t_penjualan.id_transaksi_online, t_penjualan_sementara.id_transaksi_online, stok_opnam_mandiri_detail.nomor_urut, payment fields and status_sync_online on m_shift_kasir, and t_pengembalian_barang.m_shift_kasir_id.

StockCommitDaoImpl calls procedures including sp_read_all_stock_commit, sp_read_uncommitted_stock_commit, sp_insert_stock_commit, sp_insert_stock_commit_with_stok_awal, and sp_update_status_commit_stock_by_id. These are additional dependencies for stock workflows; a table-only schema cannot reproduce them.

The online schema minimarket_online is a separate dependency used by online connection/synchronization code. It is not the LOCAL schema and is not created by the six-table benchmark.

## Benchmark schema decisions

The synthetic benchmark is intentionally limited to m_cabang, m_jenis, m_karyawan, m_role, m_barang, and m_barcode.

- Date columns that relevant RowMappers read with ResultSet.getDate(...) are seeded as DATETIME values rather than numeric UNIX timestamps. m_barang.HPP_UPDATED remains numeric because BarangDaoImpl explicitly writes UNIX_TIMESTAMP() to it.
- m_barcode.ID is INT in line with BarcodeRowMapper.getInt("ID").
- Barcode uniqueness and a foreign key are not imposed because they are not established by DAO source evidence.
- m_role(role, menu) is indexed but not declared unique because uniqueness is not established by DAO source evidence.
- The index formerly named idx_barang_barcode_search was removed because its columns did not match the barcode lookup query.

These choices improve consistency with observed source behavior but do not prove that the benchmark DDL equals the original production DDL.

## What is still needed for a full LOCAL test

The repository has no confirmed complete operational v29 dump/schema. The benchmark cannot stand in for that schema. To build a valid LOCAL test database, obtain an original schema-only export or vendor migration/DDL package, then compare it with this inventory. Until then, a full schema generated by guessing column types, constraints, stored procedures or migrations would be unsafe and likely incomplete.
