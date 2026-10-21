/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Sales;

public interface SalesDao {
    public int createSales(Sales var1);

    public Sales getSales(String var1);

    public int updateSales(Sales var1);

    public int deleteSales(String var1);

    public List<Sales> getSaleses(String var1);

    public String getLastId();

    public int[] upsertSalesFromOnline(List<Sales> var1);
}

