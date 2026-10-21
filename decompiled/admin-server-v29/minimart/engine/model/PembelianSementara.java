/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PembelianSementara {
    private long id;
    private String shortcut;
    private String status;
    private String supplierId;
    private String barangId;
    private float jumlahBarang;
    private String satuan;
    private long hargaBeli;
    private long discount;
    private long totalHarga;
    private Date tanggalPembelian;
    private String pembayaran;
    private Date jatuhTempo;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortcut() {
        return this.shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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

    public long getHargaBeli() {
        return this.hargaBeli;
    }

    public void setHargaBeli(long hargaBeli) {
        this.hargaBeli = hargaBeli;
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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTanggalPembelian() {
        return this.tanggalPembelian;
    }

    public void setTanggalPembelian(Date tanggalPembelian) {
        this.tanggalPembelian = tanggalPembelian;
    }

    public String getPembayaran() {
        return this.pembayaran;
    }

    public void setPembayaran(String pembayaran) {
        this.pembayaran = pembayaran;
    }

    public Date getJatuhTempo() {
        return this.jatuhTempo;
    }

    public void setJatuhTempo(Date jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }
}

