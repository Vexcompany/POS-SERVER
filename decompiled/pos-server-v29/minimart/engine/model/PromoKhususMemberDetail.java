/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PromoKhususMemberDetail {
    private int id;
    private String promoKhususMemberId;
    private String barangId;
    private String satuan;
    private int minPembelian;
    private long hargaPromo;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPromoKhususMemberId() {
        return this.promoKhususMemberId;
    }

    public void setPromoKhususMemberId(String promoKhususMemberId) {
        this.promoKhususMemberId = promoKhususMemberId;
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

    public int getMinPembelian() {
        return this.minPembelian;
    }

    public void setMinPembelian(int minPembelian) {
        this.minPembelian = minPembelian;
    }

    public long getHargaPromo() {
        return this.hargaPromo;
    }

    public void setHargaPromo(long hargaPromo) {
        this.hargaPromo = hargaPromo;
    }
}

