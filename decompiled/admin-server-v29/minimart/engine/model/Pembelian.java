/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class Pembelian {
    private String id;
    private String supplierId;
    private String karyawanId;
    private String fakturId;
    private Date tanggal;
    private float discount1;
    private float ppn;
    private long total;
    private String status;
    private Date created;
    private Date updated;
    private String keterangan;
    private String pembayaran;
    private Date jatuhTempo;
    private long titipan;
    private String pelunasan;
    private int id_po_online = -1;

    public int getId_po_online() {
        return this.id_po_online;
    }

    public void setId_po_online(int id_po_online) {
        this.id_po_online = id_po_online;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
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

    public float getDiscount1() {
        return this.discount1;
    }

    public void setDiscount1(float discount) {
        this.discount1 = discount;
    }

    public float getPpn() {
        return this.ppn;
    }

    public void setPpn(float ppn) {
        this.ppn = ppn;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKaryawanId() {
        return this.karyawanId;
    }

    public void setKaryawanId(String karyawanId) {
        this.karyawanId = karyawanId;
    }

    public String getFakturId() {
        return this.fakturId;
    }

    public void setFakturId(String idFaktur) {
        this.fakturId = idFaktur;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getPembayaran() {
        return this.pembayaran;
    }

    public void setPembayaran(String pembayaran) {
        this.pembayaran = pembayaran;
    }

    public Date getJatuhTempo() {
        return this.jatuhTempo;
    }

    public void setJatuhTempo(Date jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }

    public long getTitipan() {
        return this.titipan;
    }

    public void setTitipan(long titipan) {
        this.titipan = titipan;
    }

    public String getPelunasan() {
        return this.pelunasan;
    }

    public void setPelunasan(String pelunasan) {
        this.pelunasan = pelunasan;
    }
}

