/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class StokOpnamCSV {
    private String barcode;
    private String barangId;
    private String penanggungJawab;
    private String lokasi;
    private float stok;

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public String getPenanggungJawab() {
        return this.penanggungJawab;
    }

    public void setPenanggungJawab(String penanggungJawab) {
        this.penanggungJawab = penanggungJawab;
    }

    public String getLokasi() {
        return this.lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public float getStok() {
        return this.stok;
    }

    public void setStok(float stok) {
        this.stok = stok;
    }
}

