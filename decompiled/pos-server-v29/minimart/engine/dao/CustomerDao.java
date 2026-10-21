/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Customer;

public interface CustomerDao {
    public int createCustomer(Customer var1);

    public int createCustomerSyncFromOnline(Customer var1);

    public Customer getCustomerById(String var1);

    public Customer getCustomerByTelp(String var1);

    public List<Customer> getCustomers(String var1);

    public List<Customer> getCabangs();

    public List<Customer> getCustomersByTelepon(String var1);

    public String getLastId();

    public int updateCustomer(Customer var1);

    public int updateCustomerSyncFromOnline(Customer var1);

    public int updateCustomer2(Customer var1, String var2);

    public int deleteCustomer(String var1);

    public int updatePointCustomer(int var1, String var2);

    public int updateHardPointCustomer(int var1, String var2);

    public Customer getCustomerByKtp(String var1);

    public Customer getCustomerByIdCabang(int var1);
}

