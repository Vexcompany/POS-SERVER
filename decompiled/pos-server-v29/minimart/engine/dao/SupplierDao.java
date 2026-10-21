/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Supplier;

public interface SupplierDao {
    public int createSupplier(Supplier var1);

    public Supplier getSupplier(String var1);

    public List<Supplier> getSuppliers(String var1);

    public int updateSupplier(Supplier var1);

    public int deleteSupplier(String var1);

    public String getLastId();
}

