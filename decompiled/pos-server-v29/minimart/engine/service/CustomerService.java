/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.CustomerDao;
import minimart.engine.daoimpl.CustomerDaoImpl;
import minimart.engine.model.Customer;

public class CustomerService {
    private CustomerDao customerDao = new CustomerDaoImpl();

    public int createCustomer(Customer customer) {
        return this.customerDao.createCustomer(customer);
    }

    public int createCustomerSyncFromOnline(Customer customer) {
        return this.customerDao.createCustomerSyncFromOnline(customer);
    }

    public Customer getCustomerById(String id) {
        return this.customerDao.getCustomerById(id);
    }

    public Customer getCustomerByTelp(String telp) {
        return this.customerDao.getCustomerByTelp(telp);
    }

    public List<Customer> getCustomersByTelepon(String telepon) {
        return this.customerDao.getCustomersByTelepon(telepon);
    }

    public List<Customer> getCustomers(String nama) {
        return this.customerDao.getCustomers(nama);
    }

    public List<Customer> getCabangs() {
        return this.customerDao.getCabangs();
    }

    public String getLastId() {
        return this.customerDao.getLastId();
    }

    public int updateCustomer(Customer customer) {
        return this.customerDao.updateCustomer(customer);
    }

    public int updateCustomerSyncFromOnline(Customer customer) {
        return this.customerDao.updateCustomerSyncFromOnline(customer);
    }

    public int deleteCustomer(String id) {
        return this.customerDao.deleteCustomer(id);
    }

    public int updateCustomer2(Customer customer, String newId) {
        return this.customerDao.updateCustomer2(customer, newId);
    }

    public int updatePointCustomer(int jumlah, String idCustomer) {
        return this.customerDao.updatePointCustomer(jumlah, idCustomer);
    }

    public Customer getCustomerByIdCabang(int idCabang) {
        return this.customerDao.getCustomerByIdCabang(idCabang);
    }
}

