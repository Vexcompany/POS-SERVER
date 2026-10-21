/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PaketDetail {
    private String id;
    private String paketId;
    private String barangId;
    private float jumlahBarang;

    public String getPaketId() {
        return this.paketId;
    }

    public void setPaketId(String paketId) {
        this.paketId = paketId;
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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

