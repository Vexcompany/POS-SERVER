/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

public class PengembalianBarangDetailO2O {
    private String id;
    private long id_pengembalian_barang_detail_offline;
    private long t_pengembalian_barang_id;
    private String m_barang_id;
    private float jumlah;
    private String satuan;
    private long harga;
    private long total_harga;
    private long total_keuntungan;
    private int id_cabang;

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getId_pengembalian_barang_detail_offline() {
        return this.id_pengembalian_barang_detail_offline;
    }

    public void setId_pengembalian_barang_detail_offline(long id_pengembalian_barang_detail_offline) {
        this.id_pengembalian_barang_detail_offline = id_pengembalian_barang_detail_offline;
    }

    public long getT_pengembalian_barang_id() {
        return this.t_pengembalian_barang_id;
    }

    public void setT_pengembalian_barang_id(long t_pengembalian_barang_id) {
        this.t_pengembalian_barang_id = t_pengembalian_barang_id;
    }

    public String getM_barang_id() {
        return this.m_barang_id;
    }

    public void setM_barang_id(String m_barang_id) {
        this.m_barang_id = m_barang_id;
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

    public long getHarga() {
        return this.harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public long getTotal_harga() {
        return this.total_harga;
    }

    public void setTotal_harga(long total_harga) {
        this.total_harga = total_harga;
    }

    public long getTotal_keuntungan() {
        return this.total_keuntungan;
    }

    public void setTotal_keuntungan(long total_keuntungan) {
        this.total_keuntungan = total_keuntungan;
    }
}

