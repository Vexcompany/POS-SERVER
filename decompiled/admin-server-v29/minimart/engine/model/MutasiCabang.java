/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class MutasiCabang {
    private String id;
    private String customerId;
    private String karyawanId;
    private int kasir;
    private Date tanggal;
    private Date jatuhTempo;
    private String keterangan;
    private long totalPenjualan;
    private String status;
    private Date created;
    private Date updated;
    private String statusPelunasan;
    private int shiftKasirId;

    public int getShiftKasirId() {
        return this.shiftKasirId;
    }

    public void setShiftKasirId(int shiftKasirId) {
        this.shiftKasirId = shiftKasirId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public long getTotalPenjualan() {
        return this.totalPenjualan;
    }

    public void setTotalPenjualan(long totalPenjualan) {
        this.totalPenjualan = totalPenjualan;
    }

    public int getKasir() {
        return this.kasir;
    }

    public void setKasir(int kasir) {
        this.kasir = kasir;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getJatuhTempo() {
        return this.jatuhTempo;
    }

    public void setJatuhTempo(Date jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatusPelunasan() {
        return this.statusPelunasan;
    }

    public void setStatusPelunasan(String statusPelunasan) {
        this.statusPelunasan = statusPelunasan;
    }
}

