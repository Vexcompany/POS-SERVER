/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.PelunasanPiutangO2O;

public class JsonResponseSyncPelunasanPiutang
extends JsonResponses {
    PelunasanPiutangO2O[] data;

    public PelunasanPiutangO2O[] getData() {
        return this.data;
    }

    public void setData(PelunasanPiutangO2O[] data) {
        this.data = data;
    }
}

