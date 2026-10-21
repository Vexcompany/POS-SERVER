/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class StokOpnamMandiriSementara {
    private long id;
    private long stokOpnamMandiriDetailId;
    private int inputKe;
    private String karyawanId;
    private int stokInput;
    private Date created;
    private String lokasi;
    private int isVoid;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStokOpnamMandiriDetailId() {
        return this.stokOpnamMandiriDetailId;
    }

    public void setStokOpnamMandiriDetailId(long stokOpnamMandiriDetailId) {
        this.stokOpnamMandiriDetailId = stokOpnamMandiriDetailId;
    }

    public int getInputKe() {
        return this.inputKe;
    }

    public void setInputKe(int inputKe) {
        this.inputKe = inputKe;
    }

    public String getKaryawanId() {
        return this.karyawanId;
    }

    public void setKaryawanId(String karyawanId) {
        this.karyawanId = karyawanId;
    }

    public int getStokInput() {
        return this.stokInput;
    }

    public void setStokInput(int stokInput) {
        this.stokInput = stokInput;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getLokasi() {
        return this.lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public int getIsVoid() {
        return this.isVoid;
    }

    public void setIsVoid(int isVoid) {
        this.isVoid = isVoid;
    }
}

