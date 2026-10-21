/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class TransaksiOnline {
    private int id;
    private String id_customer;
    private int id_cabang;
    private Date tanggal_transaksi;
    private long subtotal;
    private int nominal_voucher_dipakai;
    private int biaya_kirim;
    private long total;
    private String status;
    private int potongan_dari_point;
    private String id_transaksi_offline;
    private String kode_metode_pembayaran;
    private String status_pembayaran;
    private boolean approval_sales;
    private boolean approval_superuser;
    private String m_sales_id;
    private String nama_sales;

    public String getM_sales_id() {
        return this.m_sales_id;
    }

    public void setM_sales_id(String m_sales_id) {
        this.m_sales_id = m_sales_id;
    }

    public String getNama_sales() {
        return this.nama_sales;
    }

    public void setNama_sales(String nama_sales) {
        this.nama_sales = nama_sales;
    }

    public boolean isApproval_sales() {
        return this.approval_sales;
    }

    public void setApproval_sales(boolean approval_sales) {
        this.approval_sales = approval_sales;
    }

    public boolean isApproval_superuser() {
        return this.approval_superuser;
    }

    public void setApproval_superuser(boolean approval_superuser) {
        this.approval_superuser = approval_superuser;
    }

    public String getKode_metode_pembayaran() {
        return this.kode_metode_pembayaran;
    }

    public void setKode_metode_pembayaran(String kode_metode_pembayaran) {
        this.kode_metode_pembayaran = kode_metode_pembayaran;
    }

    public String getStatus_pembayaran() {
        return this.status_pembayaran;
    }

    public void setStatus_pembayaran(String status_pembayaran) {
        this.status_pembayaran = status_pembayaran;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_customer() {
        return this.id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public Date getTanggal_transaksi() {
        return this.tanggal_transaksi;
    }

    public void setTanggal_transaksi(Date tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public long getSubtotal() {
        return this.subtotal;
    }

    public void setSubtotal(long subtotal) {
        this.subtotal = subtotal;
    }

    public int getNominal_voucher_dipakai() {
        return this.nominal_voucher_dipakai;
    }

    public void setNominal_voucher_dipakai(int nominal_voucher_dipakai) {
        this.nominal_voucher_dipakai = nominal_voucher_dipakai;
    }

    public int getBiaya_kirim() {
        return this.biaya_kirim;
    }

    public void setBiaya_kirim(int biaya_kirim) {
        this.biaya_kirim = biaya_kirim;
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

    public int getPotongan_dari_point() {
        return this.potongan_dari_point;
    }

    public void setPotongan_dari_point(int potongan_dari_point) {
        this.potongan_dari_point = potongan_dari_point;
    }

    public String getId_transaksi_offline() {
        return this.id_transaksi_offline;
    }

    public void setId_transaksi_offline(String id_transaksi_offline) {
        this.id_transaksi_offline = id_transaksi_offline;
    }
}

