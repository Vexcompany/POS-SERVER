/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class OTPRevisiPenjualanDetail {
    private long id;
    private int t_otp_revisi_penjualan_id;
    private String id_sku;
    private float qty_before;
    private String satuan_before;
    private long harga_before;
    private long discount_before;
    private long total_before;
    private float qty_after;
    private String satuan_after;
    private long harga_after;
    private long discount_after;
    private long total_after;
    private int id_cabang;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getT_otp_revisi_penjualan_id() {
        return this.t_otp_revisi_penjualan_id;
    }

    public void setT_otp_revisi_penjualan_id(int t_otp_revisi_penjualan_id) {
        this.t_otp_revisi_penjualan_id = t_otp_revisi_penjualan_id;
    }

    public String getId_sku() {
        return this.id_sku;
    }

    public void setId_sku(String id_sku) {
        this.id_sku = id_sku;
    }

    public float getQty_before() {
        return this.qty_before;
    }

    public void setQty_before(float qty_before) {
        this.qty_before = qty_before;
    }

    public String getSatuan_before() {
        return this.satuan_before;
    }

    public void setSatuan_before(String satuan_before) {
        this.satuan_before = satuan_before;
    }

    public long getHarga_before() {
        return this.harga_before;
    }

    public void setHarga_before(long harga_before) {
        this.harga_before = harga_before;
    }

    public long getDiscount_before() {
        return this.discount_before;
    }

    public void setDiscount_before(long discount_before) {
        this.discount_before = discount_before;
    }

    public long getTotal_before() {
        return this.total_before;
    }

    public void setTotal_before(long total_before) {
        this.total_before = total_before;
    }

    public float getQty_after() {
        return this.qty_after;
    }

    public void setQty_after(float qty_after) {
        this.qty_after = qty_after;
    }

    public String getSatuan_after() {
        return this.satuan_after;
    }

    public void setSatuan_after(String satuan_after) {
        this.satuan_after = satuan_after;
    }

    public long getHarga_after() {
        return this.harga_after;
    }

    public void setHarga_after(long harga_after) {
        this.harga_after = harga_after;
    }

    public long getDiscount_after() {
        return this.discount_after;
    }

    public void setDiscount_after(long discount_after) {
        this.discount_after = discount_after;
    }

    public long getTotal_after() {
        return this.total_after;
    }

    public void setTotal_after(long total_after) {
        this.total_after = total_after;
    }

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }
}

