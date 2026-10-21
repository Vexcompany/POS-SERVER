/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.PelunasanHutangO2O;

public class JsonResponseSyncPelunasanHutang
extends JsonResponses {
    PelunasanHutangO2O[] data;

    public PelunasanHutangO2O[] getData() {
        return this.data;
    }

    public void setData(PelunasanHutangO2O[] data) {
        this.data = data;
    }
}

