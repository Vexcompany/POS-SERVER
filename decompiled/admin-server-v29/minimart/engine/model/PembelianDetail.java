/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PembelianDetail {
    private long id;
    private String pembelianId;
    private String barangId;
    private float jumlahBarang;
    private long hargaBeli;
    private long discount2;
    private long totalHarga;
    private String satuan;
    private Date created;
    private Date updated;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPembelianId() {
        return this.pembelianId;
    }

    public void setPembelianId(String pembelianId) {
        this.pembelianId = pembelianId;
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

    public long getHargaBeli() {
        return this.hargaBeli;
    }

    public void setHargaBeli(long hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public long getTotalHarga() {
        return this.totalHarga;
    }

    public void setTotalHarga(long totalHarga) {
        this.totalHarga = totalHarga;
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

