/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class Penjualan {
    private String id;
    private String customerId;
    private String karyawanId;
    private String salesId;
    private int kasir;
    private Date tanggal;
    private long totalPenjualan;
    private String status;
    private int poin;
    private Date created;
    private Date updated;
    private Date jatuhTempo;
    private String keterangan;
    private String statusPelunasan;
    private String referensi;
    private int shiftKasirId;
    private int eligiblePwp;
    private int eligibleTebusMurah;
    private long lamaTransaksi;
    private String idTransaksiOnline;
    private String voucher;

    public int getEligiblePwp() {
        return this.eligiblePwp;
    }

    public void setEligiblePwp(int eligiblePwp) {
        this.eligiblePwp = eligiblePwp;
    }

    public int getEligibleTebusMurah() {
        return this.eligibleTebusMurah;
    }

    public void setEligibleTebusMurah(int eligibleTebusMurah) {
        this.eligibleTebusMurah = eligibleTebusMurah;
    }

    public int getShiftKasirId() {
        return this.shiftKasirId;
    }

    public void setShiftKasirId(int shiftKasirId) {
        this.shiftKasirId = shiftKasirId;
    }

    public String getIdTransaksiOnline() {
        return this.idTransaksiOnline;
    }

    public void setIdTransaksiOnline(String idTransaksiOnline) {
        this.idTransaksiOnline = idTransaksiOnline;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public long getTotalPenjualan() {
        return this.totalPenjualan;
    }

    public void setTotalPenjualan(long totalPenjualan) {
        this.totalPenjualan = totalPenjualan;
    }

    public int getKasir() {
        return this.kasir;
    }

    public void setKasir(int kasir) {
        this.kasir = kasir;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPoin() {
        return this.poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public String getSalesId() {
        return this.salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public Date getJatuhTempo() {
        return this.jatuhTempo;
    }

    public void setJatuhTempo(Date jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatusPelunasan() {
        return this.statusPelunasan;
    }

    public void setStatusPelunasan(String statusPelunasan) {
        this.statusPelunasan = statusPelunasan;
    }

    public String getReferensi() {
        return this.referensi;
    }

    public void setReferensi(String referensi) {
        this.referensi = referensi;
    }

    public String getVoucher() {
        return this.voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public long getLamaTransaksi() {
        return this.lamaTransaksi;
    }

    public void setLamaTransaksi(long lamaTransaksi) {
        this.lamaTransaksi = lamaTransaksi;
    }
}

