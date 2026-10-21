/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.PengembalianBarangO2O;

public class JsonResponseSyncPengembalianBarang
extends JsonResponses {
    PengembalianBarangO2O[] data;

    public PengembalianBarangO2O[] getData() {
        return this.data;
    }

    public void setData(PengembalianBarangO2O[] data) {
        this.data = data;
    }
}

