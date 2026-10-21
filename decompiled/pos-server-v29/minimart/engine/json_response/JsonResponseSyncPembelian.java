/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.PembelianO2O;

public class JsonResponseSyncPembelian
extends JsonResponses {
    PembelianO2O[] data;

    public PembelianO2O[] getData() {
        return this.data;
    }

    public void setData(PembelianO2O[] data) {
        this.data = data;
    }
}

