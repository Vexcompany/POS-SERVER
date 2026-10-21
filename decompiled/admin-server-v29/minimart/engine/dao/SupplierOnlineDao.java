/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.SupplierOnline;

public interface SupplierOnlineDao {
    public int createSupplier(SupplierOnline var1);

    public int updateSupplier(SupplierOnline var1);

    public int deleteSupplier(String var1);

    public List<SupplierOnline> getSupplierOnline(String var1);
}

