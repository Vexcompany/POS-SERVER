/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class Voucher {
    private String id;
    private String customerId;
    private int jumlah;
    private String available;
    private Date expiredDate;
    private Date exchangeDate;

    public String getAvailable() {
        return this.available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Date getExpiredDate() {
        return this.expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getExchangeDate() {
        return this.exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }
}

