/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.math.BigDecimal;
import java.util.Date;

public class ReturPenjualan {
    private String id;
    private String penjualanId;
    private String barangId;
    private String karyawanId;
    private Date tanggal;
    private int jumlahBarang;
    private BigDecimal hargaRetur;
    private BigDecimal totalHarga;
    private Date created;
    private Date updated;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPenjualanId() {
        return this.penjualanId;
    }

    public void setPenjualanId(String penjualanId) {
        this.penjualanId = penjualanId;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public String getKaryawanId() {
        return this.karyawanId;
    }

    public void setKaryawanId(String karyawanId) {
        this.karyawanId = karyawanId;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getJumlahBarang() {
        return this.jumlahBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public BigDecimal getHargaRetur() {
        return this.hargaRetur;
    }

    public void setHargaRetur(BigDecimal hargaRetur) {
        this.hargaRetur = hargaRetur;
    }

    public BigDecimal getTotalHarga() {
        return this.totalHarga;
    }

    public void setTotalHarga(BigDecimal totalHarga) {
        this.totalHarga = totalHarga;
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

