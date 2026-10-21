/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class OTPPengembalian {
    private int id;
    private String t_penjualan_id;
    private String m_customer_id;
    private Date tanggal;
    private String unique_code;
    private boolean used;
    private int id_cabang;
    private String m_karyawan_id;
    private boolean approve_customer;
    private boolean activated;
    private String pembayaran;
    private String nomor_telepon;
    private String nomor_rekening;
    private String bank;
    String alasan_batal;
    private boolean sameDayValid;
    private boolean approve_finance;

    public String getAlasan_batal() {
        return this.alasan_batal;
    }

    public void setAlasan_batal(String alasan_batal) {
        this.alasan_batal = alasan_batal;
    }

    public boolean isSameDayValid() {
        return this.sameDayValid;
    }

    public void setSameDayValid(boolean sameDayValid) {
        this.sameDayValid = sameDayValid;
    }

    public boolean isApprove_finance() {
        return this.approve_finance;
    }

    public void setApprove_finance(boolean approve_finance) {
        this.approve_finance = approve_finance;
    }

    public String getBank() {
        return this.bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNomor_telepon() {
        return this.nomor_telepon;
    }

    public void setNomor_telepon(String nomor_telepon) {
        this.nomor_telepon = nomor_telepon;
    }

    public String getNomor_rekening() {
        return this.nomor_rekening;
    }

    public void setNomor_rekening(String nomor_rekening) {
        this.nomor_rekening = nomor_rekening;
    }

    public String getPembayaran() {
        return this.pembayaran;
    }

    public void setPembayaran(String pembayaran) {
        this.pembayaran = pembayaran;
    }

    public boolean isApprove_customer() {
        return this.approve_customer;
    }

    public void setApprove_customer(boolean approve_customer) {
        this.approve_customer = approve_customer;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getM_karyawan_id() {
        return this.m_karyawan_id;
    }

    public void setM_karyawan_id(String m_karyawan_id) {
        this.m_karyawan_id = m_karyawan_id;
    }

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getT_penjualan_id() {
        return this.t_penjualan_id;
    }

    public void setT_penjualan_id(String t_penjualan_id) {
        this.t_penjualan_id = t_penjualan_id;
    }

    public String getM_customer_id() {
        return this.m_customer_id;
    }

    public void setM_customer_id(String m_customer_id) {
        this.m_customer_id = m_customer_id;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getUnique_code() {
        return this.unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}

