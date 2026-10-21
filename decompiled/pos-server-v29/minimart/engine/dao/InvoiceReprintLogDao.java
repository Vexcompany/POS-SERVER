/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.InvoiceReprintLog;

public interface InvoiceReprintLogDao {
    public int create(InvoiceReprintLog var1);

    public List<InvoiceReprintLog> getInvoiceReprintLog(String var1, String var2);
}

