/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.Customer;

public interface CustomerOnlineDao {
    public Customer getCustomerById(String var1);

    public Customer addCustomerPoint(String var1, Long var2);
}

