/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class KoreksiStok {
    private int id;
    private Date date;
    private String karyawanId;
    private String barangId;
    private float stokAwal;
    private float jumlah;
    private String satuan;
    private String jenis;
    private String status;

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getKaryawanId() {
        return this.karyawanId;
    }

    public void setKaryawanId(String karyawanId) {
        this.karyawanId = karyawanId;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public float getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(float jumlah) {
        this.jumlah = jumlah;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJenis() {
        return this.jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getStokAwal() {
        return this.stokAwal;
    }

    public void setStokAwal(float stokAwal) {
        this.stokAwal = stokAwal;
    }
}

