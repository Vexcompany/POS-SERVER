/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.ShiftKasirO2O;

public class JsonResponseSyncShiftKasir
extends JsonResponses {
    ShiftKasirO2O[] data;

    public ShiftKasirO2O[] getData() {
        return this.data;
    }

    public void setData(ShiftKasirO2O[] data) {
        this.data = data;
    }
}

