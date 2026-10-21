/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class MutasiBarangDetail {
    private long id;
    private String mutasiBarangId;
    private String barangId;
    private int jumlahBarang;
    private String satuan;
    private Date created;
    private Date updated;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMutasiBarangId() {
        return this.mutasiBarangId;
    }

    public void setMutasiBarangId(String mutasiBarangId) {
        this.mutasiBarangId = mutasiBarangId;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public int getJumlahBarang() {
        return this.jumlahBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
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
}

