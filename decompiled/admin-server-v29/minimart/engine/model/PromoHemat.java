/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PromoHemat {
    private String id;
    private String karyawanId;
    private Date tanggalMulai;
    private Date tanggalBerakhir;
    private String keterangan;
    private String status;
    private Date created;

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

    public Date getTanggalMulai() {
        return this.tanggalMulai;
    }

    public void setTanggalMulai(Date tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public Date getTanggalBerakhir() {
        return this.tanggalBerakhir;
    }

    public void setTanggalBerakhir(Date tanggalBerakhir) {
        this.tanggalBerakhir = tanggalBerakhir;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

