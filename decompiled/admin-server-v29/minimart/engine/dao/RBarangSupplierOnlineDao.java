/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.RBarangSupplierOnline;

public interface RBarangSupplierOnlineDao {
    public int createRBarangSupplier(RBarangSupplierOnline var1);

    public int deleteRBSByBarangIdSupplierId(String var1, String var2, String var3);
}

