/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.sql.Timestamp;

public class CustomerPointCommit {
    private String pointTransactionCommitId;
    private String idCustomer;
    private String idTransaksi;
    private String queryType;
    private int poinDelta;
    private Boolean statusCommit;
    private String transactionType;
    private Timestamp transactionCreated;
    private Timestamp transactionUpdated;
    private Boolean statusDelete;

    public String getpointTransactionCommitId() {
        return this.pointTransactionCommitId;
    }

    public String getKtpCustomer() {
        return this.idCustomer;
    }

    public String getIdTransaction() {
        return this.idTransaksi;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public int getPoint() {
        return this.poinDelta;
    }

    public Boolean getStatusCommit() {
        return this.statusCommit;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public Timestamp getTransactionCreated() {
        return this.transactionCreated;
    }

    public Timestamp getTransactionUpdated() {
        return this.transactionUpdated;
    }

    public Boolean getStatusDelete() {
        return this.statusDelete;
    }

    public void setPointTransactionCommitID(String id) {
        this.pointTransactionCommitId = id;
    }

    public void setKtpCustomer(String id) {
        this.idCustomer = id;
    }

    public void setIdTransaksi(String id) {
        this.idTransaksi = id;
    }

    public void setQueryType(String q) {
        this.queryType = q;
    }

    public void setStatusCommit(Boolean b) {
        this.statusCommit = b;
    }

    public void setTransactionType(String tp) {
        this.transactionType = tp;
    }

    public void setTransactionCreated(Timestamp date) {
        this.transactionCreated = date;
    }

    public void setPointChanges(int point) {
        this.poinDelta = point;
    }

    public void setTransactionUpdated(Timestamp date) {
        this.transactionUpdated = date;
    }

    public void setStatusDelete(boolean aBoolean) {
        this.statusDelete = aBoolean;
    }
}

