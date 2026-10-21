/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.sql.Timestamp;
import minimart.engine.model.Customer;

public class CustomerCommit {
    private String customerTransactionCommitId;
    private String idCustomer;
    private String queryType;
    private Boolean statusCommit;
    private String transactionType;
    private Timestamp transactionCreated;
    private Timestamp transactionUpdated;
    private Boolean statusDelete;
    private Customer data;

    public String getCustomerTransactionCommitId() {
        return this.customerTransactionCommitId;
    }

    public String getIdCustomer() {
        return this.idCustomer;
    }

    public String getQueryType() {
        return this.queryType;
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

    public Customer getCustomer() {
        return this.data;
    }

    public void setCustomerTransactionCommitID(String id) {
        this.customerTransactionCommitId = id;
    }

    public void setIdCustomer(String id) {
        this.idCustomer = id;
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

    public void setTransactionUpdated(Timestamp date) {
        this.transactionUpdated = date;
    }

    public void setCustomerData(Customer data) {
        this.data = data;
    }

    public void setStatusDelete(boolean aBoolean) {
        this.statusDelete = aBoolean;
    }
}

