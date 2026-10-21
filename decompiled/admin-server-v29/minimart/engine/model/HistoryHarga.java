/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class HistoryHarga {
    private long id;
    private String karyawanId;
    private String barangId;
    private String satuan;
    private long hargaLama;
    private long hargaBaru;
    private Date tanggal;
    private Date created;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public long getHargaLama() {
        return this.hargaLama;
    }

    public void setHargaLama(long hargaLama) {
        this.hargaLama = hargaLama;
    }

    public long getHargaBaru() {
        return this.hargaBaru;
    }

    public void setHargaBaru(long hargaBaru) {
        this.hargaBaru = hargaBaru;
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
}

