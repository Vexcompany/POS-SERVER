/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.RBarangSupplier;

public interface RBarangSupplierDao {
    public int createRBarangSupplier(RBarangSupplier var1);

    public List<RBarangSupplier> readRBSByBarangId(String var1);

    public List<Object[]> readRBSBySupplierId(String var1);

    public int deleteRBSByBarangIdSupplierId(String var1, String var2);

    public boolean isRBSHasCreated(String var1, String var2);

    public List<RBarangSupplier> readRBSAll();
}

