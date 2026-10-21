/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.sql.Timestamp;

public class ShiftKasir {
    private int id;
    private int idKasir;
    private String closedBy;
    private String openedBy;
    private Timestamp opening;
    private Timestamp closing;
    private long totalUang;
    private long jumlahSetoran;
    private long transfer;
    private long debit;
    private long qris;
    private long voucher;

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

    public long getTotalUang() {
        return this.totalUang;
    }

    public void setTotalUang(long totalUang) {
        this.totalUang = totalUang;
    }

    public long getJumlahSetoran() {
        return this.jumlahSetoran;
    }

    public void setJumlahSetoran(long jumlahSetoran) {
        this.jumlahSetoran = jumlahSetoran;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdKasir() {
        return this.idKasir;
    }

    public void setIdKasir(int idKasir) {
        this.idKasir = idKasir;
    }

    public String getClosedBy() {
        return this.closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public Timestamp getOpening() {
        return this.opening;
    }

    public void setOpening(Timestamp opening) {
        this.opening = opening;
    }

    public Timestamp getClosing() {
        return this.closing;
    }

    public void setClosing(Timestamp closing) {
        this.closing = closing;
    }

    public String getOpenedBy() {
        return this.openedBy;
    }

    public void setOpenedBy(String openedBy) {
        this.openedBy = openedBy;
    }

    public long getVoucher() {
        return this.voucher;
    }

    public void setVoucher(long voucher) {
        this.voucher = voucher;
    }
}

