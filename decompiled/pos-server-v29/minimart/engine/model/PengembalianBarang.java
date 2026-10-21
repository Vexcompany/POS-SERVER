/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PengembalianBarang {
    private long id;
    private String customerId;
    private String karyawanId;
    private String penjualanId;
    private Date tanggal;
    private long totalPengembalian;
    private String keterangan;
    private Date created;
    private String pelunasan;
    private int cetakVoucher;
    private String noHp;
    private int shiftKasirId;

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getKaryawanId() {
        return this.karyawanId;
    }

    public void setKaryawanId(String karyawanId) {
        this.karyawanId = karyawanId;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public long getTotalPengembalian() {
        return this.totalPengembalian;
    }

    public void setTotalPengembalian(long totalPengembalian) {
        this.totalPengembalian = totalPengembalian;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPelunasan() {
        return this.pelunasan;
    }

    public void setPelunasan(String pelunasan) {
        this.pelunasan = pelunasan;
    }

    public String getPenjualanId() {
        return this.penjualanId;
    }

    public void setPenjualanId(String penjualanId) {
        this.penjualanId = penjualanId;
    }

    public int getCetakVoucher() {
        return this.cetakVoucher;
    }

    public void setCetakVoucher(int cetakVoucher) {
        this.cetakVoucher = cetakVoucher;
    }

    public String getNoHp() {
        return this.noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public int getShiftKasirId() {
        return this.shiftKasirId;
    }

    public void setShiftKasirId(int shiftKasirId) {
        this.shiftKasirId = shiftKasirId;
    }
}

