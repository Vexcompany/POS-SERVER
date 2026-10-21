/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.SalesOrder;

public interface SalesOrderDao {
    public SalesOrder getSalesOrderByUniqueCode(String var1);

    public int updateImportedSalesOrder(int var1);
}

