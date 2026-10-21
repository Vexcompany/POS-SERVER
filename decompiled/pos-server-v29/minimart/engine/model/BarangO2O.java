/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class BarangO2O {
    private String itemId;
    private int id_cabang;
    private String jenis_item;
    private String id_sku;
    private String rak_toko;
    private String rak_gudang;
    private String nama_item;
    private String satuan_1;
    private String satuan_2;
    private String satuan_3;
    private int konversi_21;
    private int konversi_32;
    private long harga_jual_1;
    private long harga_jual_2;
    private long harga_jual_3;
    private float min_pembelian;
    private float min_persen;
    private float stok_toko;
    private float stok_gudang;
    private float stok_bs;
    private int safety_stok;
    private int reorder_point;
    private boolean update_harga;
    private boolean available;
    private String created;
    private String updated;
    private long hpp;
    private long hpp_updated;
    private String parent_id;
    private String brand;
    private String tag;
    private int expDay;

    public int getExpDay() {
        return this.expDay;
    }

    public void setExpDay(int expDay) {
        this.expDay = expDay;
    }

    public BarangO2O(String itemId, int id_cabang, String jenis_item, String id_sku, String rak_toko, String rak_gudang, String nama_item, String satuan_1, String satuan_2, String satuan_3, int konversi_21, int konversi_32, long harga_jual_1, long harga_jual_2, long harga_jual_3, float min_pembelian, float min_persen, float stok_toko, float stok_gudang, float stok_bs, int safety_stok, int reorder_point, boolean update_harga, boolean available, String created, String updated, long hpp, long hpp_updated, String parent_id) {
        this.itemId = itemId;
        this.id_cabang = id_cabang;
        this.jenis_item = jenis_item;
        this.id_sku = id_sku;
        this.rak_toko = rak_toko;
        this.rak_gudang = rak_gudang;
        this.nama_item = nama_item;
        this.satuan_1 = satuan_1;
        this.satuan_2 = satuan_2;
        this.satuan_3 = satuan_3;
        this.konversi_21 = konversi_21;
        this.konversi_32 = konversi_32;
        this.harga_jual_1 = harga_jual_1;
        this.harga_jual_2 = harga_jual_2;
        this.harga_jual_3 = harga_jual_3;
        this.min_pembelian = min_pembelian;
        this.min_persen = min_persen;
        this.stok_toko = stok_toko;
        this.stok_gudang = stok_gudang;
        this.stok_bs = stok_bs;
        this.safety_stok = safety_stok;
        this.reorder_point = reorder_point;
        this.update_harga = update_harga;
        this.available = available;
        this.created = created;
        this.updated = updated;
        this.hpp = hpp;
        this.hpp_updated = hpp_updated;
        this.parent_id = parent_id;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getParent_id() {
        return this.parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public long getHpp_updated() {
        return this.hpp_updated;
    }

    public void setHpp_updated(long hpp_updated) {
        this.hpp_updated = hpp_updated;
    }

    public long getHpp() {
        return this.hpp;
    }

    public void setHpp(long hpp) {
        this.hpp = hpp;
    }

    public BarangO2O() {
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getJenis_item() {
        return this.jenis_item;
    }

    public void setJenis_item(String jenis_item) {
        this.jenis_item = jenis_item;
    }

    public String getId_sku() {
        return this.id_sku;
    }

    public void setId_sku(String id_sku) {
        this.id_sku = id_sku;
    }

    public String getRak_toko() {
        return this.rak_toko;
    }

    public void setRak_toko(String rak_toko) {
        this.rak_toko = rak_toko;
    }

    public String getRak_gudang() {
        return this.rak_gudang;
    }

    public void setRak_gudang(String rak_gudang) {
        this.rak_gudang = rak_gudang;
    }

    public String getNama_item() {
        return this.nama_item;
    }

    public void setNama_item(String nama_item) {
        this.nama_item = nama_item;
    }

    public String getSatuan_1() {
        return this.satuan_1;
    }

    public void setSatuan_1(String satuan_1) {
        this.satuan_1 = satuan_1;
    }

    public String getSatuan_2() {
        return this.satuan_2;
    }

    public void setSatuan_2(String satuan_2) {
        this.satuan_2 = satuan_2;
    }

    public String getSatuan_3() {
        return this.satuan_3;
    }

    public void setSatuan_3(String satuan_3) {
        this.satuan_3 = satuan_3;
    }

    public int getKonversi_21() {
        return this.konversi_21;
    }

    public void setKonversi_21(int konversi_21) {
        this.konversi_21 = konversi_21;
    }

    public int getKonversi_32() {
        return this.konversi_32;
    }

    public void setKonversi_32(int konversi_32) {
        this.konversi_32 = konversi_32;
    }

    public long getHarga_jual_1() {
        return this.harga_jual_1;
    }

    public void setHarga_jual_1(long harga_jual_1) {
        this.harga_jual_1 = harga_jual_1;
    }

    public long getHarga_jual_2() {
        return this.harga_jual_2;
    }

    public void setHarga_jual_2(long harga_jual_2) {
        this.harga_jual_2 = harga_jual_2;
    }

    public long getHarga_jual_3() {
        return this.harga_jual_3;
    }

    public void setHarga_jual_3(long harga_jual_3) {
        this.harga_jual_3 = harga_jual_3;
    }

    public float getMin_pembelian() {
        return this.min_pembelian;
    }

    public void setMin_pembelian(float min_pembelian) {
        this.min_pembelian = min_pembelian;
    }

    public float getMin_persen() {
        return this.min_persen;
    }

    public void setMin_persen(float min_persen) {
        this.min_persen = min_persen;
    }

    public float getStok_toko() {
        return this.stok_toko;
    }

    public void setStok_toko(float stok_toko) {
        this.stok_toko = stok_toko;
    }

    public float getStok_gudang() {
        return this.stok_gudang;
    }

    public void setStok_gudang(float stok_gudang) {
        this.stok_gudang = stok_gudang;
    }

    public float getStok_bs() {
        return this.stok_bs;
    }

    public void setStok_bs(float stok_bs) {
        this.stok_bs = stok_bs;
    }

    public int getSafety_stok() {
        return this.safety_stok;
    }

    public void setSafety_stok(int safety_stok) {
        this.safety_stok = safety_stok;
    }

    public int getReorder_point() {
        return this.reorder_point;
    }

    public void setReorder_point(int reorder_point) {
        this.reorder_point = reorder_point;
    }

    public boolean isUpdate_harga() {
        return this.update_harga;
    }

    public void setUpdate_harga(boolean update_harga) {
        this.update_harga = update_harga;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return this.updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}

