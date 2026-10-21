/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.PenjualanDetailO2O;

public class JsonResponseSyncPenjualanDetail
extends JsonResponses {
    PenjualanDetailO2O[] data;

    public PenjualanDetailO2O[] getData() {
        return this.data;
    }

    public void setData(PenjualanDetailO2O[] data) {
        this.data = data;
    }
}

