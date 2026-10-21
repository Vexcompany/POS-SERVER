/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PengembalianBarangDetail {
    private long id;
    private long pengembalianBarangId;
    private String barangId;
    private float jumlah;
    private String satuan;
    private long harga;
    private long totalHarga;
    private long totalKeuntungan;

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
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

    public long getTotalHarga() {
        return this.totalHarga;
    }

    public void setTotalHarga(long totalHarga) {
        this.totalHarga = totalHarga;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPengembalianBarangId() {
        return this.pengembalianBarangId;
    }

    public void setPengembalianBarangId(long pengembalianBarangId) {
        this.pengembalianBarangId = pengembalianBarangId;
    }

    public long getTotalKeuntungan() {
        return this.totalKeuntungan;
    }

    public void setTotalKeuntungan(long totalKeuntungan) {
        this.totalKeuntungan = totalKeuntungan;
    }
}

