/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class StokOpnamMandiri {
    private int id;
    private Date tanggal;
    private String karyawanId;
    private Date tanggalSubmit;
    private Date created;
    private Date updated;
    private Date tanggalInput1;
    private Date tanggalInput2;
    private Date tanggalInput3;
    private String keterangan;
    private String tipe;
    private String blindSom;

    public Date getTanggalInput1() {
        return this.tanggalInput1;
    }

    public void setTanggalInput1(Date tanggalInput1) {
        this.tanggalInput1 = tanggalInput1;
    }

    public Date getTanggalInput2() {
        return this.tanggalInput2;
    }

    public void setTanggalInput2(Date tanggalInput2) {
        this.tanggalInput2 = tanggalInput2;
    }

    public Date getTanggalInput3() {
        return this.tanggalInput3;
    }

    public void setTanggalInput3(Date tanggalInput3) {
        this.tanggalInput3 = tanggalInput3;
    }

    public Date getTanggalSubmit() {
        return this.tanggalSubmit;
    }

    public void setTanggalSubmit(Date tanggalSubmit) {
        this.tanggalSubmit = tanggalSubmit;
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

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getBlindSom() {
        return this.blindSom;
    }

    public void setBlindSom(String blindSom) {
        this.blindSom = blindSom;
    }
}

