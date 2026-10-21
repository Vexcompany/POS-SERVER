/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class PenjualanDetailO2O {
    String id;
    String t_penjualan_id;
    String m_barang_id;
    String satuan;
    String referensi;
    String id_cabang;
    String promo;
    long id_penjualan_detail_offline;
    long discount_2;
    long harga_jual;
    long total_harga;
    long total_keuntungan;
    Date created;
    Date updated;
    float jumlah_barang;

    public String getPromo() {
        return this.promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public long getId_penjualan_detail_offline() {
        return this.id_penjualan_detail_offline;
    }

    public void setId_penjualan_detail_offline(long id_penjualan_detail_offline) {
        this.id_penjualan_detail_offline = id_penjualan_detail_offline;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getT_penjualan_id() {
        return this.t_penjualan_id;
    }

    public void setT_penjualan_id(String t_penjualan_id) {
        this.t_penjualan_id = t_penjualan_id;
    }

    public String getM_barang_id() {
        return this.m_barang_id;
    }

    public void setM_barang_id(String m_barang_id) {
        this.m_barang_id = m_barang_id;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getReferensi() {
        return this.referensi;
    }

    public void setReferensi(String referensi) {
        this.referensi = referensi;
    }

    public String getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(String id_cabang) {
        this.id_cabang = id_cabang;
    }

    public long getDiscount_2() {
        return this.discount_2;
    }

    public void setDiscount_2(long discount_2) {
        this.discount_2 = discount_2;
    }

    public long getHarga_jual() {
        return this.harga_jual;
    }

    public void setHarga_jual(long harga_jual) {
        this.harga_jual = harga_jual;
    }

    public long getTotal_harga() {
        return this.total_harga;
    }

    public void setTotal_harga(long total_harga) {
        this.total_harga = total_harga;
    }

    public long getTotal_keuntungan() {
        return this.total_keuntungan;
    }

    public void setTotal_keuntungan(long total_keuntungan) {
        this.total_keuntungan = total_keuntungan;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public float getJumlah_barang() {
        return this.jumlah_barang;
    }

    public void setJumlah_barang(float jumlah_barang) {
        this.jumlah_barang = jumlah_barang;
    }
}

