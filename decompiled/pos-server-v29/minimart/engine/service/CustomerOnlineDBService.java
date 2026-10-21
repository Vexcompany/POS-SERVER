/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.CustomerOnlineDao;
import minimart.engine.daoimpl.CustomerOnlineDaoImpl;
import minimart.engine.model.Customer;

public class CustomerOnlineDBService {
    private CustomerOnlineDao customerOnlineDao = new CustomerOnlineDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public Customer getCustomerById(String id) {
        this.ensureOnlineConnection();
        return this.customerOnlineDao.getCustomerById(id);
    }

    public Customer addCustomerPoint(String id, Long point) {
        this.ensureOnlineConnection();
        return this.customerOnlineDao.addCustomerPoint(id, point);
    }
}

