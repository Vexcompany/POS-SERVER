/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.VoucherO2O;

public class JsonResponseSyncVoucher
extends JsonResponses {
    VoucherO2O[] data;

    public VoucherO2O[] getData() {
        return this.data;
    }

    public void setData(VoucherO2O[] data) {
        this.data = data;
    }
}

