/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.sql.Timestamp;

public class KeuanganKasir {
    private int idKasir;
    private long jumlahUang;
    private String statusKasir;
    private String openedBy;
    private Timestamp opening;

    public int getIdKasir() {
        return this.idKasir;
    }

    public void setIdKasir(int idKasir) {
        this.idKasir = idKasir;
    }

    public long getJumlahUang() {
        return this.jumlahUang;
    }

    public void setJumlahUang(long jumlahUang) {
        this.jumlahUang = jumlahUang;
    }

    public String getStatusKasir() {
        return this.statusKasir;
    }

    public void setStatusKasir(String statusKasir) {
        this.statusKasir = statusKasir;
    }

    public String getOpenedBy() {
        return this.openedBy;
    }

    public void setOpenedBy(String openedBy) {
        this.openedBy = openedBy;
    }

    public Timestamp getOpening() {
        return this.opening;
    }

    public void setOpening(Timestamp opening) {
        this.opening = opening;
    }
}

