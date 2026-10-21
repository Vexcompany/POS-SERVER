/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.JsonResponses;
import minimart.engine.model_online.PenukaranPointO2O;

public class JsonResponseSyncPenukaranPoint
extends JsonResponses {
    PenukaranPointO2O[] data;

    public PenukaranPointO2O[] getData() {
        return this.data;
    }

    public void setData(PenukaranPointO2O[] data) {
        this.data = data;
    }
}

