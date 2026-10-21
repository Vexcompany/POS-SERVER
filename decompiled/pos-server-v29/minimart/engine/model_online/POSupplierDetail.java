/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class POSupplierDetail {
    private long id;
    private int id_po_header;
    private String id_sku;
    private float quantity;
    private String satuan;
    private int konversi_satuan;
    private float quantity_terkecil;
    private int id_order_supplier_detail;
    private long hpp_terakhir;
    private long harga_beli;
    private Date created;
    private Date updated;
    private boolean available;

    public POSupplierDetail(long id, int id_po_header, String id_sku, float quantity, String satuan, int konversi_satuan, float quantity_terkecil, int id_order_supplier_detail, long hpp_terakhir, long harga_beli, Date created, Date updated, boolean available) {
        this.id = id;
        this.id_po_header = id_po_header;
        this.id_sku = id_sku;
        this.quantity = quantity;
        this.satuan = satuan;
        this.konversi_satuan = konversi_satuan;
        this.quantity_terkecil = quantity_terkecil;
        this.id_order_supplier_detail = id_order_supplier_detail;
        this.hpp_terakhir = hpp_terakhir;
        this.harga_beli = harga_beli;
        this.created = created;
        this.updated = updated;
        this.available = available;
    }

    public POSupplierDetail() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getId_po_header() {
        return this.id_po_header;
    }

    public void setId_po_header(int id_po_header) {
        this.id_po_header = id_po_header;
    }

    public String getId_sku() {
        return this.id_sku;
    }

    public void setId_sku(String id_sku) {
        this.id_sku = id_sku;
    }

    public float getQuantity() {
        return this.quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getKonversi_satuan() {
        return this.konversi_satuan;
    }

    public void setKonversi_satuan(int konversi_satuan) {
        this.konversi_satuan = konversi_satuan;
    }

    public float getQuantity_terkecil() {
        return this.quantity_terkecil;
    }

    public void setQuantity_terkecil(float quantity_terkecil) {
        this.quantity_terkecil = quantity_terkecil;
    }

    public int getId_order_supplier_detail() {
        return this.id_order_supplier_detail;
    }

    public void setId_order_supplier_detail(int id_order_supplier_detail) {
        this.id_order_supplier_detail = id_order_supplier_detail;
    }

    public long getHpp_terakhir() {
        return this.hpp_terakhir;
    }

    public void setHpp_terakhir(long hpp_terakhir) {
        this.hpp_terakhir = hpp_terakhir;
    }

    public long getHarga_beli() {
        return this.harga_beli;
    }

    public void setHarga_beli(long harga_beli) {
        this.harga_beli = harga_beli;
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

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

