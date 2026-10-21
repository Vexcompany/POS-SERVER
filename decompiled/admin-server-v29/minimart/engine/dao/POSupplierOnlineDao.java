/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model_online.POSupplier;
import minimart.engine.model_online.POSupplierDetail;

public interface POSupplierOnlineDao {
    public List<POSupplier> getPOSupplierUnsyncedOffline(int var1);

    public List<POSupplierDetail> getPOSupplierDetailUnsyncedOffline(int var1);

    public void markStatusSyncOffline(int var1);
}

