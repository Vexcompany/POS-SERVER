/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PromoTebusMurahDetail {
    private int id;
    private String promoTebusMurahId;
    private String barangId;
    private String satuan;
    private long minimumBelanja;
    private long hargaPromo;
    private float maksimalKuantiti;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPromoTebusMurahId() {
        return this.promoTebusMurahId;
    }

    public void setPromoTebusMurahId(String promoTebusMurahId) {
        this.promoTebusMurahId = promoTebusMurahId;
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

    public long getMinimumBelanja() {
        return this.minimumBelanja;
    }

    public void setMinimumBelanja(long minimumBelanja) {
        this.minimumBelanja = minimumBelanja;
    }

    public long getHargaPromo() {
        return this.hargaPromo;
    }

    public void setHargaPromo(long hargaPromo) {
        this.hargaPromo = hargaPromo;
    }

    public float getMaksimalKuantiti() {
        return this.maksimalKuantiti;
    }

    public void setMaksimalKuantiti(float maksimalKuantiti) {
        this.maksimalKuantiti = maksimalKuantiti;
    }
}

