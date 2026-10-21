/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class MutasiCabangDetail {
    private long id;
    private String mutasiCabangId;
    private String barangId;
    private float jumlahBarang;
    private String satuan;
    private long discount2;
    private long hargaJual;
    private long totalHarga;
    private long totalKeuntungan;
    private Date created;
    private Date updated;

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

    public long getTotalHarga() {
        return this.totalHarga;
    }

    public void setTotalHarga(long totalHarga) {
        this.totalHarga = totalHarga;
    }

    public long getTotalKeuntungan() {
        return this.totalKeuntungan;
    }

    public void setTotalKeuntungan(long totalKeuntungan) {
        this.totalKeuntungan = totalKeuntungan;
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

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public long getDiscount2() {
        return this.discount2;
    }

    public void setDiscount2(long discount2) {
        this.discount2 = discount2;
    }

    public long getHargaJual() {
        return this.hargaJual;
    }

    public void setHargaJual(long hargaJual) {
        this.hargaJual = hargaJual;
    }

    public String getMutasiCabangId() {
        return this.mutasiCabangId;
    }

    public void setMutasiCabangId(String mutasiCabangId) {
        this.mutasiCabangId = mutasiCabangId;
    }
}

