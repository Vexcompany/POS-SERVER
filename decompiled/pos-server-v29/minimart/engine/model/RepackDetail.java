/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class RepackDetail {
    private int id;
    private String repackId;
    private String barangId;
    private float jumlahBarang;
    private String satuan;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepackId() {
        return this.repackId;
    }

    public void setRepackId(String repackId) {
        this.repackId = repackId;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public float getJumlahBarang() {
        return this.jumlahBarang;
    }

    public void setJumlahBarang(float jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }
}

