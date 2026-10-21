/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PromoHematDetail {
    private int id;
    private String promoHematId;
    private String barangId;
    private String satuan;
    private float minimumPembelian;
    private long hargaPromo;
    private String khususPelanggan;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPromoHematId() {
        return this.promoHematId;
    }

    public void setPromoHematId(String promoHematId) {
        this.promoHematId = promoHematId;
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

    public float getMinimumPembelian() {
        return this.minimumPembelian;
    }

    public void setMinimumPembelian(float minimumPembelian) {
        this.minimumPembelian = minimumPembelian;
    }

    public long getHargaPromo() {
        return this.hargaPromo;
    }

    public void setHargaPromo(long hargaPromo) {
        this.hargaPromo = hargaPromo;
    }

    public String getKhususPelanggan() {
        return this.khususPelanggan;
    }

    public void setKhususPelanggan(String khususPelanggan) {
        this.khususPelanggan = khususPelanggan;
    }
}

