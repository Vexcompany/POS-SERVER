/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class StokOpnam {
    private int id;
    private String karyawanId;
    private String statusOpnam;
    private String lokasi;
    private String tipe;
    private String tipeName;
    private Date tanggal;
    private String keterangan;
    private Date updated;

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

    public String getStatusOpnam() {
        return this.statusOpnam;
    }

    public void setStatusOpnam(String statusOpnam) {
        this.statusOpnam = statusOpnam;
    }

    public String getLokasi() {
        return this.lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTipe() {
        return this.tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getTipeName() {
        return this.tipeName;
    }

    public void setTipeName(String tipeName) {
        this.tipeName = tipeName;
    }
}

