/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class TransaksiOnlineDetail {
    private long id;
    private int transaksi_id;
    private int id_cabang;
    private String item_id;
    private String m_barang_id;
    private int jumlah_barang;
    private int jumlah_barang_revisi;
    private String satuan;
    private int harga_jual;
    private int discount_per_item;
    private int total_harga;
    private int total_keuntungan;
    private int harga_promo;
    private String nama_barang;

    public String getNama_barang() {
        return this.nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTransaksi_id() {
        return this.transaksi_id;
    }

    public void setTransaksi_id(int transaksi_id) {
        this.transaksi_id = transaksi_id;
    }

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getItem_id() {
        return this.item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getM_barang_id() {
        return this.m_barang_id;
    }

    public void setM_barang_id(String m_barang_id) {
        this.m_barang_id = m_barang_id;
    }

    public int getJumlah_barang() {
        return this.jumlah_barang;
    }

    public void setJumlah_barang(int jumlah_barang) {
        this.jumlah_barang = jumlah_barang;
    }

    public int getJumlah_barang_revisi() {
        return this.jumlah_barang_revisi;
    }

    public void setJumlah_barang_revisi(int jumlah_barang_revisi) {
        this.jumlah_barang_revisi = jumlah_barang_revisi;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getHarga_jual() {
        return this.harga_jual;
    }

    public void setHarga_jual(int harga_jual) {
        this.harga_jual = harga_jual;
    }

    public int getDiscount_per_item() {
        return this.discount_per_item;
    }

    public void setDiscount_per_item(int discount_per_item) {
        this.discount_per_item = discount_per_item;
    }

    public int getTotal_harga() {
        return this.total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public int getTotal_keuntungan() {
        return this.total_keuntungan;
    }

    public void setTotal_keuntungan(int total_keuntungan) {
        this.total_keuntungan = total_keuntungan;
    }

    public int getHarga_promo() {
        return this.harga_promo;
    }

    public void setHarga_promo(int harga_promo) {
        this.harga_promo = harga_promo;
    }
}

