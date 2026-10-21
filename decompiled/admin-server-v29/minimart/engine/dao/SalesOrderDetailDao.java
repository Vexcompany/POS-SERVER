/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.SalesOrderDetail;

public interface SalesOrderDetailDao {
    public List<SalesOrderDetail> getSalesOrderDetailByIdSalesOrder(int var1);
}

