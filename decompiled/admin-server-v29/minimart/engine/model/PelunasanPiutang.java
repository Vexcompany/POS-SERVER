/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PelunasanPiutang {
    private int id;
    private String penjualanId;
    private String karyawanId;
    private Date tanggalPelunasan;
    private String keterangan;
    private long jumlahPelunasan;
    private int shiftKasirId;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKaryawanId() {
        return this.karyawanId;
    }

    public void setKaryawanId(String karyawanId) {
        this.karyawanId = karyawanId;
    }

    public Date getTanggalPelunasan() {
        return this.tanggalPelunasan;
    }

    public void setTanggalPelunasan(Date tanggalPelunasan) {
        this.tanggalPelunasan = tanggalPelunasan;
    }

    public String getPenjualanId() {
        return this.penjualanId;
    }

    public void setPenjualanId(String penjualanId) {
        this.penjualanId = penjualanId;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public long getJumlahPelunasan() {
        return this.jumlahPelunasan;
    }

    public void setJumlahPelunasan(long jumlahPelunasan) {
        this.jumlahPelunasan = jumlahPelunasan;
    }

    public int getShiftKasirId() {
        return this.shiftKasirId;
    }

    public void setShiftKasirId(int shiftKasirId) {
        this.shiftKasirId = shiftKasirId;
    }
}

