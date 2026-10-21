/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class OTPUbahHarga {
    private long id;
    private String m_customer_id;
    private String id_sku;
    private float jumlah;
    private String satuan;
    private long harga;
    private String unique_code;
    private boolean used;
    private int id_cabang;
    private String m_karyawan_id;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getM_customer_id() {
        return this.m_customer_id;
    }

    public void setM_customer_id(String m_customer_id) {
        this.m_customer_id = m_customer_id;
    }

    public String getId_sku() {
        return this.id_sku;
    }

    public void setId_sku(String id_sku) {
        this.id_sku = id_sku;
    }

    public float getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(float jumlah) {
        this.jumlah = jumlah;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public long getHarga() {
        return this.harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public String getUnique_code() {
        return this.unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getM_karyawan_id() {
        return this.m_karyawan_id;
    }

    public void setM_karyawan_id(String m_karyawan_id) {
        this.m_karyawan_id = m_karyawan_id;
    }
}

