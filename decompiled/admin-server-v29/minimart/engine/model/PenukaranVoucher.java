/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PenukaranVoucher {
    private int id;
    private String karyawanId;
    private String customerId;
    private String penjualanId;
    private int jumlahVoucher;
    private Date tanggal;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKaryawanId() {
        return this.karyawanId;
    }

    public void setKaryawanId(String karyawanId) {
        this.karyawanId = karyawanId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPenjualanId() {
        return this.penjualanId;
    }

    public void setPenjualanId(String penjualanId) {
        this.penjualanId = penjualanId;
    }

    public int getJumlahVoucher() {
        return this.jumlahVoucher;
    }

    public void setJumlahVoucher(int jumlahVoucher) {
        this.jumlahVoucher = jumlahVoucher;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
}

