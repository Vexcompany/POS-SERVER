/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class PembelianDetailO2O {
    private String id;
    private long id_pembelian_detail_offline;
    private String t_pembelian_id;
    private String m_barang_id;
    private float jumlah_barang;
    private long harga_beli;
    private long discount_2;
    private long total_harga;
    private String satuan;
    private Date created;
    private Date updated;
    private int id_cabang;

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getId_pembelian_detail_offline() {
        return this.id_pembelian_detail_offline;
    }

    public void setId_pembelian_detail_offline(long id_pembelian_detail_offline) {
        this.id_pembelian_detail_offline = id_pembelian_detail_offline;
    }

    public String getT_pembelian_id() {
        return this.t_pembelian_id;
    }

    public void setT_pembelian_id(String t_pembelian_id) {
        this.t_pembelian_id = t_pembelian_id;
    }

    public String getM_barang_id() {
        return this.m_barang_id;
    }

    public void setM_barang_id(String m_barang_id) {
        this.m_barang_id = m_barang_id;
    }

    public float getJumlah_barang() {
        return this.jumlah_barang;
    }

    public void setJumlah_barang(float jumlah_barang) {
        this.jumlah_barang = jumlah_barang;
    }

    public long getHarga_beli() {
        return this.harga_beli;
    }

    public void setHarga_beli(long harga_beli) {
        this.harga_beli = harga_beli;
    }

    public long getDiscount_2() {
        return this.discount_2;
    }

    public void setDiscount_2(long discount_2) {
        this.discount_2 = discount_2;
    }

    public long getTotal_harga() {
        return this.total_harga;
    }

    public void setTotal_harga(long total_harga) {
        this.total_harga = total_harga;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
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
}

