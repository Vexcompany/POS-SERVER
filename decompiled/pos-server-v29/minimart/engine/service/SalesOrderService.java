/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.SalesOrderDao;
import minimart.engine.dao.SalesOrderDetailDao;
import minimart.engine.daoimpl.SalesOrderDaoImpl;
import minimart.engine.daoimpl.SalesOrderDetailDaoImpl;
import minimart.engine.model.SalesOrder;
import minimart.engine.model.SalesOrderDetail;

public class SalesOrderService {
    SalesOrderDao soDao = new SalesOrderDaoImpl();
    SalesOrderDetailDao sodDao = new SalesOrderDetailDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public SalesOrder getSalesOrderByUc(String uc) {
        this.ensureOnlineConnection();
        return this.soDao.getSalesOrderByUniqueCode(uc);
    }

    public int updateImportedSalesOrderById(int idso) {
        this.ensureOnlineConnection();
        return this.soDao.updateImportedSalesOrder(idso);
    }

    public List<SalesOrderDetail> getSalesOrderDetailById(int idso) {
        this.ensureOnlineConnection();
        return this.sodDao.getSalesOrderDetailByIdSalesOrder(idso);
    }
}

