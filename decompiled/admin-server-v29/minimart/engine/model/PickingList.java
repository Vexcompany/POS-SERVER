/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PickingList {
    int id;
    int idCabangTerima;
    int idCabangKirim1;
    int idCabangKirim2;
    Date tanggal;
    Date created;
    Date updated;
    boolean available;
    boolean doGenerated;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCabangTerima() {
        return this.idCabangTerima;
    }

    public void setIdCabangTerima(int idCabangTerima) {
        this.idCabangTerima = idCabangTerima;
    }

    public int getIdCabangKirim1() {
        return this.idCabangKirim1;
    }

    public void setIdCabangKirim1(int idCabangKirim1) {
        this.idCabangKirim1 = idCabangKirim1;
    }

    public int getIdCabangKirim2() {
        return this.idCabangKirim2;
    }

    public void setIdCabangKirim2(int idCabangKirim2) {
        this.idCabangKirim2 = idCabangKirim2;
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

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isDoGenerated() {
        return this.doGenerated;
    }

    public void setDoGenerated(boolean doGenerated) {
        this.doGenerated = doGenerated;
    }
}

