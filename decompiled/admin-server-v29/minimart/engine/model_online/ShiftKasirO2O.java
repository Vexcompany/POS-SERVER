/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class ShiftKasirO2O {
    private String id;
    private int id_offline;
    private int id_cabang;
    private int id_kasir;
    private String opened_by;
    private String closed_by;
    private Date opening;
    private Date closing;
    private long total_uang;
    private long jumlah_setoran;
    private long transfer;
    private long debit;
    private long qris;
    private long voucher;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getId_offline() {
        return this.id_offline;
    }

    public void setId_offline(int id_offline) {
        this.id_offline = id_offline;
    }

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public int getId_kasir() {
        return this.id_kasir;
    }

    public void setId_kasir(int id_kasir) {
        this.id_kasir = id_kasir;
    }

    public String getOpened_by() {
        return this.opened_by;
    }

    public void setOpened_by(String opened_by) {
        this.opened_by = opened_by;
    }

    public String getClosed_by() {
        return this.closed_by;
    }

    public void setClosed_by(String closed_by) {
        this.closed_by = closed_by;
    }

    public Date getOpening() {
        return this.opening;
    }

    public void setOpening(Date opening) {
        this.opening = opening;
    }

    public Date getClosing() {
        return this.closing;
    }

    public void setClosing(Date closing) {
        this.closing = closing;
    }

    public long getTotal_uang() {
        return this.total_uang;
    }

    public void setTotal_uang(long total_uang) {
        this.total_uang = total_uang;
    }

    public long getJumlah_setoran() {
        return this.jumlah_setoran;
    }

    public void setJumlah_setoran(long jumlah_setoran) {
        this.jumlah_setoran = jumlah_setoran;
    }

    public long getTransfer() {
        return this.transfer;
    }

    public void setTransfer(long transfer) {
        this.transfer = transfer;
    }

    public long getDebit() {
        return this.debit;
    }

    public void setDebit(long debit) {
        this.debit = debit;
    }

    public long getQris() {
        return this.qris;
    }

    public void setQris(long qris) {
        this.qris = qris;
    }

    public long getVoucher() {
        return this.voucher;
    }

    public void setVoucher(long voucher) {
        this.voucher = voucher;
    }
}

