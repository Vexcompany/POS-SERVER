/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PenjualanParsialDetail {
    private int id;
    private String barangId;
    private String penjualanParsialId;
    private float jumlahBarang;
    private String satuan;
    private long hargaJual;
    private long discount;
    private long totalHarga;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public float getJumlahBarang() {
        return this.jumlahBarang;
    }

    public void setJumlahBarang(float jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public long getHargaJual() {
        return this.hargaJual;
    }

    public void setHargaJual(long hargaJual) {
        this.hargaJual = hargaJual;
    }

    public long getDiscount() {
        return this.discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public long getTotalHarga() {
        return this.totalHarga;
    }

    public void setTotalHarga(long totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getPenjualanParsialId() {
        return this.penjualanParsialId;
    }

    public void setPenjualanParsialId(String penjualanParsialId) {
        this.penjualanParsialId = penjualanParsialId;
    }
}

