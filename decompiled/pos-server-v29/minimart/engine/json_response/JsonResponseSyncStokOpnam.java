/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.StokOpnamO2O;

public class JsonResponseSyncStokOpnam
extends JsonResponses {
    StokOpnamO2O[] data;

    public StokOpnamO2O[] getData() {
        return this.data;
    }

    public void setData(StokOpnamO2O[] data) {
        this.data = data;
    }
}

