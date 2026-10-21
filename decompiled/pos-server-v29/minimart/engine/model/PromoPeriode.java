/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PromoPeriode {
    private int id;
    private String barangId;
    private String pesan;
    private String aktifkan;
    private long minimal;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPesan() {
        return this.pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String isAktifkan() {
        return this.getAktifkan();
    }

    public void setAktifkan(String aktifkan) {
        this.aktifkan = aktifkan;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public String getAktifkan() {
        return this.aktifkan;
    }

    public long getMinimal() {
        return this.minimal;
    }

    public void setMinimal(long minimal) {
        this.minimal = minimal;
    }
}

