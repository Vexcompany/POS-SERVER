/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class PenjualanO2O {
    String id;
    String id_penjualan_offline;
    String m_customer_id;
    String m_karyawan_id;
    String m_sales_id;
    String keterangan;
    String status;
    String status_pelunasan;
    String id_cabang;
    String id_transaksi_online;
    int kasir;
    int poin;
    int lama_transaksi;
    long total_penjualan;
    Date tanggal;
    Date created;
    Date updated;
    Date jatuh_tempo;
    boolean eligiblePwp;
    boolean eligibleTebusMurah;

    public String getId_transaksi_online() {
        return this.id_transaksi_online;
    }

    public void setId_transaksi_online(String id_transaksi_online) {
        this.id_transaksi_online = id_transaksi_online;
    }

    public int getLama_transaksi() {
        return this.lama_transaksi;
    }

    public void setLama_transaksi(int lama_transaksi) {
        this.lama_transaksi = lama_transaksi;
    }

    public boolean isEligiblePwp() {
        return this.eligiblePwp;
    }

    public void setEligiblePwp(boolean eligiblePwp) {
        this.eligiblePwp = eligiblePwp;
    }

    public boolean isEligibleTebusMurah() {
        return this.eligibleTebusMurah;
    }

    public void setEligibleTebusMurah(boolean eligibleTebusMurah) {
        this.eligibleTebusMurah = eligibleTebusMurah;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_penjualan_offline() {
        return this.id_penjualan_offline;
    }

    public void setId_penjualan_offline(String id_penjualan_offline) {
        this.id_penjualan_offline = id_penjualan_offline;
    }

    public String getM_customer_id() {
        return this.m_customer_id;
    }

    public void setM_customer_id(String m_customer_id) {
        this.m_customer_id = m_customer_id;
    }

    public String getM_karyawan_id() {
        return this.m_karyawan_id;
    }

    public void setM_karyawan_id(String m_karyawan_id) {
        this.m_karyawan_id = m_karyawan_id;
    }

    public String getM_sales_id() {
        return this.m_sales_id;
    }

    public void setM_sales_id(String m_sales_id) {
        this.m_sales_id = m_sales_id;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_pelunasan() {
        return this.status_pelunasan;
    }

    public void setStatus_pelunasan(String status_pelunasan) {
        this.status_pelunasan = status_pelunasan;
    }

    public String getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(String id_cabang) {
        this.id_cabang = id_cabang;
    }

    public int getKasir() {
        return this.kasir;
    }

    public void setKasir(int kasir) {
        this.kasir = kasir;
    }

    public int getPoin() {
        return this.poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public long getTotal_penjualan() {
        return this.total_penjualan;
    }

    public void setTotal_penjualan(long total_penjualan) {
        this.total_penjualan = total_penjualan;
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

    public Date getJatuh_tempo() {
        return this.jatuh_tempo;
    }

    public void setJatuh_tempo(Date jatuh_tempo) {
        this.jatuh_tempo = jatuh_tempo;
    }
}

