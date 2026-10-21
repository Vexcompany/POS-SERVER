/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class KelebihanStok {
    private int id;
    private String barangId;
    private float stokSystem;
    private float stokInput;
    private String inputOleh;
    private String otorisasiOleh;
    private Date created;
    private String keterangan;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public float getStokSystem() {
        return this.stokSystem;
    }

    public void setStokSystem(float stokSystem) {
        this.stokSystem = stokSystem;
    }

    public float getStokInput() {
        return this.stokInput;
    }

    public void setStokInput(float stokInput) {
        this.stokInput = stokInput;
    }

    public String getInputOleh() {
        return this.inputOleh;
    }

    public void setInputOleh(String inputOleh) {
        this.inputOleh = inputOleh;
    }

    public String getOtorisasiOleh() {
        return this.otorisasiOleh;
    }

    public void setOtorisasiOleh(String otorisasiOleh) {
        this.otorisasiOleh = otorisasiOleh;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}

