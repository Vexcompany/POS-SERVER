/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PenjualanSementara {
    private long id;
    private String shortcut;
    private int kasir;
    private String status;
    private String customerId;
    private String salesId;
    private String barangId;
    private float jumlahBarang;
    private String satuan;
    private long hargaJual;
    private long discount;
    private long totalHarga;
    private boolean notaKhusus;
    private String keterangan;
    private String namaBarang;
    private String pickinglistId;
    private String idTransaksiOnline;
    private String promo;
    private Date created;

    public String getIdTransaksiOnline() {
        return this.idTransaksiOnline;
    }

    public void setIdTransaksiOnline(String idTransaksiOnline) {
        this.idTransaksiOnline = idTransaksiOnline;
    }

    public String getNamaBarang() {
        return this.namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getShortcut() {
        return this.shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
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

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHargaJual() {
        return this.hargaJual;
    }

    public void setHargaJual(long hargaJual) {
        this.hargaJual = hargaJual;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getKasir() {
        return this.kasir;
    }

    public void setKasir(int kasir) {
        this.kasir = kasir;
    }

    public boolean isNotaKhusus() {
        return this.notaKhusus;
    }

    public void setNotaKhusus(boolean notaKhusus) {
        this.notaKhusus = notaKhusus;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalesId() {
        return this.salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getPickinglistId() {
        return this.pickinglistId;
    }

    public void setPickinglistId(String pickinglistId) {
        this.pickinglistId = pickinglistId;
    }

    public String getPromo() {
        return this.promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

