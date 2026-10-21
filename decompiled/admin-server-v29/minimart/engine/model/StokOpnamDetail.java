/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class StokOpnamDetail {
    private int id;
    private int stokOpnamId;
    private String barangId;
    private float jumlahKomp;
    private float jumlahToko;
    private long total;

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

    public float getJumlahToko() {
        return this.jumlahToko;
    }

    public void setJumlahToko(float jumlahToko) {
        this.jumlahToko = jumlahToko;
    }

    public int getStokOpnamId() {
        return this.stokOpnamId;
    }

    public void setStokOpnamId(int stokOpnamId) {
        this.stokOpnamId = stokOpnamId;
    }

    public float getJumlahKomp() {
        return this.jumlahKomp;
    }

    public void setJumlahKomp(float jumlahKomp) {
        this.jumlahKomp = jumlahKomp;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}

