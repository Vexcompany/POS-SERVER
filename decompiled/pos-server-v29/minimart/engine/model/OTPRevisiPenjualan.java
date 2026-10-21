/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class OTPRevisiPenjualan {
    private int id;
    private String t_penjualan_id;
    private String m_customer_id;
    private String m_karyawan_id;
    private Integer id_user;
    private long total_before;
    private long total_after;
    private String unique_code;
    private boolean approved;
    private boolean used;
    private boolean sameDayValid;
    private int id_cabang;
    private Date requested_at;
    private Date approved_at;
    private Date used_at;
    private Date created_at;
    private Date updated_at;

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

    public String getM_karyawan_id() {
        return this.m_karyawan_id;
    }

    public void setM_karyawan_id(String m_karyawan_id) {
        this.m_karyawan_id = m_karyawan_id;
    }

    public Integer getId_user() {
        return this.id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public long getTotal_before() {
        return this.total_before;
    }

    public void setTotal_before(long total_before) {
        this.total_before = total_before;
    }

    public long getTotal_after() {
        return this.total_after;
    }

    public void setTotal_after(long total_after) {
        this.total_after = total_after;
    }

    public String getUnique_code() {
        return this.unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }

    public boolean isApproved() {
        return this.approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isSameDayValid() {
        return this.sameDayValid;
    }

    public void setSameDayValid(boolean sameDayValid) {
        this.sameDayValid = sameDayValid;
    }

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public Date getRequested_at() {
        return this.requested_at;
    }

    public void setRequested_at(Date requested_at) {
        this.requested_at = requested_at;
    }

    public Date getApproved_at() {
        return this.approved_at;
    }

    public void setApproved_at(Date approved_at) {
        this.approved_at = approved_at;
    }

    public Date getUsed_at() {
        return this.used_at;
    }

    public void setUsed_at(Date used_at) {
        this.used_at = used_at;
    }

    public Date getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}

