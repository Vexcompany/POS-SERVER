/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PelunasanHutang {
    private int id;
    private String pembelianId;
    private String karyawanId;
    private Date tanggalPelunasan;
    private long jumlah;
    private String keterangan;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPembelianId() {
        return this.pembelianId;
    }

    public void setPembelianId(String pembelianId) {
        this.pembelianId = pembelianId;
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

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public long getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(long jumlah) {
        this.jumlah = jumlah;
    }
}

