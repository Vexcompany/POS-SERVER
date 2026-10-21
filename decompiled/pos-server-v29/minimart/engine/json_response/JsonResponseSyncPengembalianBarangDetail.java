/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.PengembalianBarangDetailO2O;

public class JsonResponseSyncPengembalianBarangDetail
extends JsonResponses {
    PengembalianBarangDetailO2O[] data;

    public PengembalianBarangDetailO2O[] getData() {
        return this.data;
    }

    public void setData(PengembalianBarangDetailO2O[] data) {
        this.data = data;
    }
}

