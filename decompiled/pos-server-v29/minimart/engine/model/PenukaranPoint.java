/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PenukaranPoint {
    private int id;
    private String customerId;
    private String karyawanId;
    private int jumlahPoint;
    private String keterangan;
    private Date tanggal;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getJumlahPoint() {
        return this.jumlahPoint;
    }

    public void setJumlahPoint(int jumlahPoint) {
        this.jumlahPoint = jumlahPoint;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getKaryawanId() {
        return this.karyawanId;
    }

    public void setKaryawanId(String karyawanId) {
        this.karyawanId = karyawanId;
    }
}

