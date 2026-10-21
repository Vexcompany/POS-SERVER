/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class OTPPengembalianDetail {
    private long id;
    private int t_otp_pengembalian_id;
    private String id_sku;
    private String satuan;
    private float jumlah;
    private float discount = 0.0f;
    private long harga;
    private int id_cabang;

    public float getDiscount() {
        return this.discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getT_otp_pengembalian_id() {
        return this.t_otp_pengembalian_id;
    }

    public void setT_otp_pengembalian_id(int t_otp_pengembalian_id) {
        this.t_otp_pengembalian_id = t_otp_pengembalian_id;
    }

    public String getId_sku() {
        return this.id_sku;
    }

    public void setId_sku(String id_sku) {
        this.id_sku = id_sku;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public float getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(float jumlah) {
        this.jumlah = jumlah;
    }

    public long getHarga() {
        return this.harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }
}

