/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PointUndi {
    private int id;
    private Date tanggalMulai;
    private Date tanggalBerakhir;
    private int minimumTransaksi;
    private String status;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getMinimumTransaksi() {
        return this.minimumTransaksi;
    }

    public void setMinimumTransaksi(int minimumTransaksi) {
        this.minimumTransaksi = minimumTransaksi;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

