/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.List;
import minimart.engine.model_online.POSupplier;
import minimart.engine.model_online.POSupplierDetail;

public class POSupplierObject {
    private POSupplier po;
    private List<POSupplierDetail> pods;

    public POSupplier getPo() {
        return this.po;
    }

    public void setPo(POSupplier po) {
        this.po = po;
    }

    public List<POSupplierDetail> getPods() {
        return this.pods;
    }

    public void setPods(List<POSupplierDetail> pods) {
        this.pods = pods;
    }
}

