/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class POSupplier {
    private int id;
    private int id_cabang_terima;
    private Date tanggal_po;
    private Date estimasi_kedatangan;
    private String id_supplier;
    private String status;
    private Date created;
    private Date updated;
    private boolean available;
    private int id_header_order_supplier;
    private String tipe_toko;
    private String pembayaran;
    private Date jatuh_tempo;
    private float discount_1;
    private float ppn;
    private String status_toko;
    private boolean sync_pembelian_online;

    public POSupplier() {
    }

    public POSupplier(int id, int id_cabang_terima, Date tanggal_po, Date estimasi_kedatangan, String id_supplier, String status, Date created, Date updated, boolean available, int id_header_order_supplier, String tipe_toko, String pembayaran, Date jatuh_tempo, float discount_1, float ppn, String status_toko, boolean sync_pembelian_online) {
        this.id = id;
        this.id_cabang_terima = id_cabang_terima;
        this.tanggal_po = tanggal_po;
        this.estimasi_kedatangan = estimasi_kedatangan;
        this.id_supplier = id_supplier;
        this.status = status;
        this.created = created;
        this.updated = updated;
        this.available = available;
        this.id_header_order_supplier = id_header_order_supplier;
        this.tipe_toko = tipe_toko;
        this.pembayaran = pembayaran;
        this.jatuh_tempo = jatuh_tempo;
        this.discount_1 = discount_1;
        this.ppn = ppn;
        this.status_toko = status_toko;
        this.sync_pembelian_online = sync_pembelian_online;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cabang_terima() {
        return this.id_cabang_terima;
    }

    public void setId_cabang_terima(int id_cabang_terima) {
        this.id_cabang_terima = id_cabang_terima;
    }

    public Date getTanggal_po() {
        return this.tanggal_po;
    }

    public void setTanggal_po(Date tanggal_po) {
        this.tanggal_po = tanggal_po;
    }

    public Date getEstimasi_kedatangan() {
        return this.estimasi_kedatangan;
    }

    public void setEstimasi_kedatangan(Date estimasi_kedatangan) {
        this.estimasi_kedatangan = estimasi_kedatangan;
    }

    public String getId_supplier() {
        return this.id_supplier;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getId_header_order_supplier() {
        return this.id_header_order_supplier;
    }

    public void setId_header_order_supplier(int id_header_order_supplier) {
        this.id_header_order_supplier = id_header_order_supplier;
    }

    public String getTipe_toko() {
        return this.tipe_toko;
    }

    public void setTipe_toko(String tipe_toko) {
        this.tipe_toko = tipe_toko;
    }

    public String getPembayaran() {
        return this.pembayaran;
    }

    public void setPembayaran(String pembayaran) {
        this.pembayaran = pembayaran;
    }

    public Date getJatuh_tempo() {
        return this.jatuh_tempo;
    }

    public void setJatuh_tempo(Date jatuh_tempo) {
        this.jatuh_tempo = jatuh_tempo;
    }

    public float getDiscount_1() {
        return this.discount_1;
    }

    public void setDiscount_1(float discount_1) {
        this.discount_1 = discount_1;
    }

    public float getPpn() {
        return this.ppn;
    }

    public void setPpn(float ppn) {
        this.ppn = ppn;
    }

    public String getStatus_toko() {
        return this.status_toko;
    }

    public void setStatus_toko(String status_toko) {
        this.status_toko = status_toko;
    }

    public boolean isSync_pembelian_online() {
        return this.sync_pembelian_online;
    }

    public void setSync_pembelian_online(boolean sync_pembelian_online) {
        this.sync_pembelian_online = sync_pembelian_online;
    }
}

