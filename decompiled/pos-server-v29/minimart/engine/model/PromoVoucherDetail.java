/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PromoVoucherDetail {
    private int id;
    private int promoVoucherId;
    private String barangId;
    private int maxPembelian;
    private String satuan;

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

    public int getMaxPembelian() {
        return this.maxPembelian;
    }

    public void setMaxPembelian(int maxPembelian) {
        this.maxPembelian = maxPembelian;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getPromoVoucherId() {
        return this.promoVoucherId;
    }

    public void setPromoVoucherId(int promoVoucherId) {
        this.promoVoucherId = promoVoucherId;
    }
}

