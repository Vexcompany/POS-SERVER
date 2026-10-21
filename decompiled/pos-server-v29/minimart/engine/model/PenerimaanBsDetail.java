/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PenerimaanBsDetail {
    private long id;
    private String penerimaanBsId;
    private String barangId;
    private float jumlahBarang;
    private String satuan;
    private long hpp;
    private long totalHpp;
    private Date created;
    private Date updated;

    public long getHpp() {
        return this.hpp;
    }

    public void setHpp(long hpp) {
        this.hpp = hpp;
    }

    public long getTotalHpp() {
        return this.totalHpp;
    }

    public void setTotalHpp(long totalHpp) {
        this.totalHpp = totalHpp;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPenerimaanBsId() {
        return this.penerimaanBsId;
    }

    public void setPenerimaanBsId(String penerimaanBsId) {
        this.penerimaanBsId = penerimaanBsId;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public float getJumlahBarang() {
        return this.jumlahBarang;
    }

    public void setJumlahBarang(float jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
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

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
}

