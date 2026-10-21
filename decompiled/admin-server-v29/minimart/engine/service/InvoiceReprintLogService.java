/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.InvoiceReprintLogDao;
import minimart.engine.daoimpl.InvoiceLogDaoImpl;
import minimart.engine.model.InvoiceReprintLog;

public class InvoiceReprintLogService {
    private InvoiceReprintLogDao dao = new InvoiceLogDaoImpl();

    public int create(InvoiceReprintLog invoiceReprintDialog) {
        return this.dao.create(invoiceReprintDialog);
    }

    public List<InvoiceReprintLog> getInvoiceReprintLog(String startDate, String endDate) {
        return this.dao.getInvoiceReprintLog(startDate, endDate);
    }
}

