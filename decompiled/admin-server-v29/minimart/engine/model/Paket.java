/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class Paket {
    private String id;
    private String barangId;
    private Date created;
    private Date updated;
    private int status;

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

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBarangId() {
        return this.barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

