/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import minimart.engine.model.ConstantO2o;

public class JsonResponseConstant {
    private String status;
    private ConstantO2o[] data;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ConstantO2o[] getData() {
        return this.data;
    }

    public void setData(ConstantO2o[] data) {
        this.data = data;
    }
}

