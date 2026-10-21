/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import minimart.engine.model.ParentBarang;

public class JsonResponseParent {
    private String status;
    private ParentBarang[] data;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ParentBarang[] getData() {
        return this.data;
    }

    public void setData(ParentBarang[] data) {
        this.data = data;
    }
}

