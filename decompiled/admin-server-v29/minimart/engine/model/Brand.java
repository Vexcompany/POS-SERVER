/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class Brand {
    private String id;
    private Date created;
    private Date updated;
    private String namaBrand;
    private int available;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNamaBrand() {
        return this.namaBrand;
    }

    public void setNamaBrand(String namaBrand) {
        this.namaBrand = namaBrand;
    }

    public int getAvailable() {
        return this.available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}

