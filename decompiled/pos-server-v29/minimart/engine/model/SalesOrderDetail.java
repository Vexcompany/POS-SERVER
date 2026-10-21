/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class SalesOrderDetail {
    private long id;
    private int id_sales_order;
    private String id_sku;
    private String nama_barang;
    private float jumlah_barang;
    private String satuan;
    private long harga_jual;
    private long total_harga;
    private int id_cabang;

    public String getNama_barang() {
        return this.nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getId_sales_order() {
        return this.id_sales_order;
    }

    public void setId_sales_order(int id_sales_order) {
        this.id_sales_order = id_sales_order;
    }

    public String getId_sku() {
        return this.id_sku;
    }

    public void setId_sku(String id_sku) {
        this.id_sku = id_sku;
    }

    public float getJumlah_barang() {
        return this.jumlah_barang;
    }

    public void setJumlah_barang(float jumlah_barang) {
        this.jumlah_barang = jumlah_barang;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public long getHarga_jual() {
        return this.harga_jual;
    }

    public void setHarga_jual(long harga_jual) {
        this.harga_jual = harga_jual;
    }

    public long getTotal_harga() {
        return this.total_harga;
    }

    public void setTotal_harga(long total_harga) {
        this.total_harga = total_harga;
    }

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }
}

