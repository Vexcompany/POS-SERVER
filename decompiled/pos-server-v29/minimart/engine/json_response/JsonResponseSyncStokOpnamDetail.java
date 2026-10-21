/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.StokOpnamDetailO2O;

public class JsonResponseSyncStokOpnamDetail
extends JsonResponses {
    StokOpnamDetailO2O[] data;

    public StokOpnamDetailO2O[] getData() {
        return this.data;
    }

    public void setData(StokOpnamDetailO2O[] data) {
        this.data = data;
    }
}

