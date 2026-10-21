/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class Repack {
    private String id;
    private String karyawanId;
    private Date tanggal;
    private Date created;
    private String barangId;
    private int jumlah;

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
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

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}

