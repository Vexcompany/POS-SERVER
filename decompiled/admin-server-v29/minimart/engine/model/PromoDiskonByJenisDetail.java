/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PromoDiskonByJenisDetail {
    private int id;
    private int promoDiscJenisId;
    private String mJenisJenis;
    private float disc;
    private String khususPelanggan;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPromoDiscJenisId() {
        return this.promoDiscJenisId;
    }

    public void setPromoDiscJenisId(int promoDiscJenisId) {
        this.promoDiscJenisId = promoDiscJenisId;
    }

    public String getmJenisJenis() {
        return this.mJenisJenis;
    }

    public void setmJenisJenis(String mJenisJenis) {
        this.mJenisJenis = mJenisJenis;
    }

    public float getDisc() {
        return this.disc;
    }

    public void setDisc(float disc) {
        this.disc = disc;
    }

    public String getKhususPelanggan() {
        return this.khususPelanggan;
    }

    public void setKhususPelanggan(String khususPelanggan) {
        this.khususPelanggan = khususPelanggan;
    }
}

