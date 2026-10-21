/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class PembelianO2O {
    private String id;
    private String id_pembelian_offline;
    private String m_supplier_id;
    private String m_karyawan_id;
    private String faktur_id;
    private Date tanggal;
    private float discount_1;
    private float ppn;
    private long total;
    private String status;
    private Date created;
    private Date updated;
    private String keterangan;
    private String pembayaran;
    private Date jatuh_tempo;
    private long titipan;
    private String pelunasan;
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

    public String getId_pembelian_offline() {
        return this.id_pembelian_offline;
    }

    public void setId_pembelian_offline(String id_pembelian_offline) {
        this.id_pembelian_offline = id_pembelian_offline;
    }

    public String getM_supplier_id() {
        return this.m_supplier_id;
    }

    public void setM_supplier_id(String m_supplier_id) {
        this.m_supplier_id = m_supplier_id;
    }

    public String getM_karyawan_id() {
        return this.m_karyawan_id;
    }

    public void setM_karyawan_id(String m_karyawan_id) {
        this.m_karyawan_id = m_karyawan_id;
    }

    public String getFaktur_id() {
        return this.faktur_id;
    }

    public void setFaktur_id(String faktur_id) {
        this.faktur_id = faktur_id;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
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

    public Date getJatuh_tempo() {
        return this.jatuh_tempo;
    }

    public void setJatuh_tempo(Date jatuh_tempo) {
        this.jatuh_tempo = jatuh_tempo;
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

