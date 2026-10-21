/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;
import java.util.List;
import minimart.engine.model.Barcode;

public class Barang {
    private String id;
    private List<Barcode> barcode_1;
    private List<Barcode> barcode_2;
    private List<Barcode> barcode_3;
    private String jenis;
    private String nama;
    private String satuan1;
    private String satuan2;
    private String satuan3;
    private int konversi21;
    private int konversi32;
    private long hargaJual1;
    private long hargaJual2;
    private long hargaJual3;
    private float stokToko;
    private float stokGudang;
    private float stokPusat;
    private float batasAtasStock;
    private float reorderPoint;
    private float stokBs;
    private int updateHarga;
    private Date created;
    private Date updated;
    private String rakIdToko;
    private String rakIdGudang;
    private float minPembelian;
    private float minPersen;
    private long hpp;
    private long hppUpdated;
    private String parentId;
    private String tag;
    private int expDay;
    private int idCabang;
    private String karyawanID;

    public long getHppUpdated() {
        return this.hppUpdated;
    }

    public void setHppUpdated(long hppUpdated) {
        this.hppUpdated = hppUpdated;
    }

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public String getKaryawanID() {
        return this.karyawanID;
    }

    public void setKaryawanID(String karyawanID) {
        this.karyawanID = karyawanID;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenis() {
        return this.jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSatuan1() {
        return this.satuan1;
    }

    public void setSatuan1(String satuan1) {
        this.satuan1 = satuan1;
    }

    public String getSatuan2() {
        return this.satuan2;
    }

    public void setSatuan2(String satuan2) {
        this.satuan2 = satuan2;
    }

    public String getSatuan3() {
        return this.satuan3;
    }

    public void setSatuan3(String satuan3) {
        this.satuan3 = satuan3;
    }

    public int getKonversi21() {
        return this.konversi21;
    }

    public void setKonversi21(int konversi21) {
        this.konversi21 = konversi21;
    }

    public int getKonversi32() {
        return this.konversi32;
    }

    public void setKonversi32(int konversi32) {
        this.konversi32 = konversi32;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public float getStokToko() {
        return this.stokToko;
    }

    public void setStokToko(float stokToko) {
        this.stokToko = stokToko;
    }

    public String getRakIdToko() {
        return this.rakIdToko;
    }

    public void setRakIdToko(String rakIdToko) {
        this.rakIdToko = rakIdToko;
    }

    public long getHargaJual1() {
        return this.hargaJual1;
    }

    public void setHargaJual1(long hargaJual1) {
        this.hargaJual1 = hargaJual1;
    }

    public long getHargaJual2() {
        return this.hargaJual2;
    }

    public void setHargaJual2(long hargaJual2) {
        this.hargaJual2 = hargaJual2;
    }

    public long getHargaJual3() {
        return this.hargaJual3;
    }

    public void setHargaJual3(long hargaJual3) {
        this.hargaJual3 = hargaJual3;
    }

    public int getUpdateHarga() {
        return this.updateHarga;
    }

    public void setUpdateHarga(int updateHarga) {
        this.updateHarga = updateHarga;
    }

    public List<Barcode> getBarcode_2() {
        return this.barcode_2;
    }

    public void setBarcode_2(List<Barcode> barcode_2) {
        this.barcode_2 = barcode_2;
    }

    public List<Barcode> getBarcode_3() {
        return this.barcode_3;
    }

    public void setBarcode_3(List<Barcode> barcode_3) {
        this.barcode_3 = barcode_3;
    }

    public List<Barcode> getBarcode_1() {
        return this.barcode_1;
    }

    public void setBarcode_1(List<Barcode> barcode_1) {
        this.barcode_1 = barcode_1;
    }

    public float getStokBs() {
        return this.stokBs;
    }

    public void setStokBs(float stokBs) {
        this.stokBs = stokBs;
    }

    public float getStokGudang() {
        return this.stokGudang;
    }

    public void setStokGudang(float stokGudang) {
        this.stokGudang = stokGudang;
    }

    public float getReorderPoint() {
        return this.reorderPoint;
    }

    public void setReorderPoint(float reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public String getRakIdGudang() {
        return this.rakIdGudang;
    }

    public void setRakIdGudang(String rakIdGudang) {
        this.rakIdGudang = rakIdGudang;
    }

    public float getMinPembelian() {
        return this.minPembelian;
    }

    public void setMinPembelian(float minPembelian) {
        this.minPembelian = minPembelian;
    }

    public float getMinPersen() {
        return this.minPersen;
    }

    public void setMinPersen(float minPersen) {
        this.minPersen = minPersen;
    }

    public float getBatasAtasStock() {
        return this.batasAtasStock;
    }

    public void setBatasAtasStock(float batasAtasStock) {
        this.batasAtasStock = batasAtasStock;
    }

    public long getHpp() {
        return this.hpp;
    }

    public void setHpp(long hpp) {
        this.hpp = hpp;
    }

    public float getStokPusat() {
        return this.stokPusat;
    }

    public void setStokPusat(float stokPusat) {
        this.stokPusat = stokPusat;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getExpDay() {
        return this.expDay;
    }

    public void setExpDay(int expDay) {
        this.expDay = expDay;
    }
}

