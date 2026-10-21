/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.PenjualanO2O;

public class JsonResponseSyncPenjualan
extends JsonResponses {
    PenjualanO2O[] data;

    public PenjualanO2O[] getData() {
        return this.data;
    }

    public void setData(PenjualanO2O[] data) {
        this.data = data;
    }
}

