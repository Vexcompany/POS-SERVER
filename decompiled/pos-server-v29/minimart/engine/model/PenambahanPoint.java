/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class PenambahanPoint {
    private long id;
    private String customerId;
    private int jumlahPoint;
    private Date created;
    private String status;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getJumlahPoint() {
        return this.jumlahPoint;
    }

    public void setJumlahPoint(int jumlahPoint) {
        this.jumlahPoint = jumlahPoint;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

