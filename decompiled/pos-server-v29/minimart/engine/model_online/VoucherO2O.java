/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class VoucherO2O {
    private String id;
    private String id_voucher_offline;
    private String m_customer_id;
    private int jumlah;
    private String available;
    Date expired_date;
    Date exchange_date;
    Date created;
    Date updated;
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

    public String getId_voucher_offline() {
        return this.id_voucher_offline;
    }

    public void setId_voucher_offline(String id_voucher_offline) {
        this.id_voucher_offline = id_voucher_offline;
    }

    public String getM_customer_id() {
        return this.m_customer_id;
    }

    public void setM_customer_id(String m_customer_id) {
        this.m_customer_id = m_customer_id;
    }

    public int getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getAvailable() {
        return this.available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Date getExpired_date() {
        return this.expired_date;
    }

    public void setExpired_date(Date expired_date) {
        this.expired_date = expired_date;
    }

    public Date getExchange_date() {
        return this.exchange_date;
    }

    public void setExchange_date(Date exchange_date) {
        this.exchange_date = exchange_date;
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
}

