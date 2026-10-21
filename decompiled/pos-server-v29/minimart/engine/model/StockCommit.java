/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class StockCommit {
    private Long stockTransactionCommitId;
    private String idBarang;
    private String idTransaksi;
    private String queryType;
    private String stockType;
    private float jumlahBarang;
    private boolean statusCommit;
    private String transactionType;
    private Date transactionCreated;
    private Date transactionUpdated;
    private Boolean statusDelete;
    private float stokAwal;

    public float getStokAwal() {
        return this.stokAwal;
    }

    public void setStokAwal(float stokAwal) {
        this.stokAwal = stokAwal;
    }

    public StockCommit() {
        this.stokAwal = -1.0f;
    }

    public StockCommit(Long stockTransactionCommitId) {
        this.stockTransactionCommitId = stockTransactionCommitId;
        this.stokAwal = -1.0f;
    }

    public StockCommit(Long stockTransactionCommitId, String idBarang, String idTransaksi, String queryType, String stockType, float jumlahBarang, boolean statusCommit, String transactionType, Date transactionCreated, Date transactionUpdated) {
        this.stockTransactionCommitId = stockTransactionCommitId;
        this.idBarang = idBarang;
        this.idTransaksi = idTransaksi;
        this.queryType = queryType;
        this.stockType = stockType;
        this.jumlahBarang = jumlahBarang;
        this.statusCommit = statusCommit;
        this.transactionType = transactionType;
        this.transactionCreated = transactionCreated;
        this.transactionUpdated = transactionUpdated;
    }

    public StockCommit(Long stockTransactionCommitId, String idBarang, String idTransaksi, String queryType, String stockType, float jumlahBarang, boolean statusCommit, String transactionType, Date transactionCreated, Date transactionUpdated, float stokAwal) {
        this.stockTransactionCommitId = stockTransactionCommitId;
        this.idBarang = idBarang;
        this.idTransaksi = idTransaksi;
        this.queryType = queryType;
        this.stockType = stockType;
        this.jumlahBarang = jumlahBarang;
        this.statusCommit = statusCommit;
        this.transactionType = transactionType;
        this.transactionCreated = transactionCreated;
        this.transactionUpdated = transactionUpdated;
        this.stokAwal = stokAwal;
    }

    public Long getStockTransactionCommitId() {
        return this.stockTransactionCommitId;
    }

    public void setStockTransactionCommitId(Long stockTransactionCommitId) {
        this.stockTransactionCommitId = stockTransactionCommitId;
    }

    public String getIdBarang() {
        return this.idBarang;
    }

    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }

    public String getIdTransaksi() {
        return this.idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getStockType() {
        return this.stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public float getJumlahBarang() {
        return this.jumlahBarang;
    }

    public void setJumlahBarang(float jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public boolean getStatusCommit() {
        return this.statusCommit;
    }

    public void setStatusCommit(boolean statusCommit) {
        this.statusCommit = statusCommit;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionCreated() {
        return this.transactionCreated;
    }

    public void setTransactionCreated(Date transactionCreated) {
        this.transactionCreated = transactionCreated;
    }

    public Date getTransactionUpdated() {
        return this.transactionUpdated;
    }

    public void setTransactionUpdated(Date transactionUpdated) {
        this.transactionUpdated = transactionUpdated;
    }

    public Boolean getStatusDelete() {
        return this.statusDelete;
    }

    public void setStatusDelete(Boolean statusDelete) {
        this.statusDelete = statusDelete;
    }
}

