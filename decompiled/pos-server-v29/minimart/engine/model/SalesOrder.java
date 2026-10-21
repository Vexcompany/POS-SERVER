/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class SalesOrder {
    private int id;
    private String m_customer_id;
    private String m_sales_id;
    private Date tanggal;
    private Date jatuh_tempo;
    private String keterangan;
    private long total_penjualan;
    private String status;
    private int id_cabang;
    private String unique_code;
    private boolean approved;
    private boolean imported;

    public boolean isImported() {
        return this.imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    public boolean isApproved() {
        return this.approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getM_customer_id() {
        return this.m_customer_id;
    }

    public void setM_customer_id(String m_customer_id) {
        this.m_customer_id = m_customer_id;
    }

    public String getM_sales_id() {
        return this.m_sales_id;
    }

    public void setM_sales_id(String m_sales_id) {
        this.m_sales_id = m_sales_id;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Date getJatuh_tempo() {
        return this.jatuh_tempo;
    }

    public void setJatuh_tempo(Date jatuh_tempo) {
        this.jatuh_tempo = jatuh_tempo;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public long getTotal_penjualan() {
        return this.total_penjualan;
    }

    public void setTotal_penjualan(long total_penjualan) {
        this.total_penjualan = total_penjualan;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getUnique_code() {
        return this.unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }
}

