/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PromoTebusDetail {
    private int id;
    private String promoTebusId;
    private String barangId;
    private float jumlah;
    private String satuan;
    private String promoBarangId;
    private long discount;
    private String satuanPromo;

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

    public float getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(float jumlah) {
        this.jumlah = jumlah;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getPromoBarangId() {
        return this.promoBarangId;
    }

    public void setPromoBarangId(String promoBarangId) {
        this.promoBarangId = promoBarangId;
    }

    public long getDiscount() {
        return this.discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public String getPromoTebusId() {
        return this.promoTebusId;
    }

    public void setPromoTebusId(String promoTebusId) {
        this.promoTebusId = promoTebusId;
    }

    public String getSatuanPromo() {
        return this.satuanPromo;
    }

    public void setSatuanPromo(String satuanPromo) {
        this.satuanPromo = satuanPromo;
    }
}

