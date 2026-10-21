/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.PembelianDetailO2O;

public class JsonResponseSyncPembelianDetail
extends JsonResponses {
    PembelianDetailO2O[] data;

    public PembelianDetailO2O[] getData() {
        return this.data;
    }

    public void setData(PembelianDetailO2O[] data) {
        this.data = data;
    }
}

