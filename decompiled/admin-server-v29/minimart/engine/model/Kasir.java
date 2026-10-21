/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class Kasir {
    private int id;
    private Date created;
    private Date updated;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
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
}

