/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class StokOpnamMandiriDetail {
    private long id;
    private String stokOpnamMandiriId;
    private String barangId;
    private int input1;
    private int input2;
    private int input3;
    private int stokKomp1;
    private int stokKomp2;
    private int stokKomp3;
    private long hpp;
    private Date created;
    private int nomorUrut;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStokOpnamMandiriId() {
        return this.stokOpnamMandiriId;
    }

    public void setStokOpnamMandiriId(String stokOpnamMandiriId) {
        this.stokOpnamMandiriId = stokOpnamMandiriId;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public int getInput1() {
        return this.input1;
    }

    public void setInput1(int input1) {
        this.input1 = input1;
    }

    public int getInput2() {
        return this.input2;
    }

    public void setInput2(int input2) {
        this.input2 = input2;
    }

    public int getInput3() {
        return this.input3;
    }

    public void setInput3(int input3) {
        this.input3 = input3;
    }

    public int getStokKomp1() {
        return this.stokKomp1;
    }

    public void setStokKomp1(int stokKomp1) {
        this.stokKomp1 = stokKomp1;
    }

    public int getStokKomp2() {
        return this.stokKomp2;
    }

    public void setStokKomp2(int stokKomp2) {
        this.stokKomp2 = stokKomp2;
    }

    public int getStokKomp3() {
        return this.stokKomp3;
    }

    public void setStokKomp3(int stokKomp3) {
        this.stokKomp3 = stokKomp3;
    }

    public long getHpp() {
        return this.hpp;
    }

    public void setHpp(long hpp) {
        this.hpp = hpp;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getNomorUrut() {
        return this.nomorUrut;
    }

    public void setNomorUrut(int nomorUrut) {
        this.nomorUrut = nomorUrut;
    }
}

