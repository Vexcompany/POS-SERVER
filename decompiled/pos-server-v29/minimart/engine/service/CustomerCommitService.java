/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package minimart.engine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import minimart.engine.dao.CustomerCommitDao;
import minimart.engine.dao.CustomerDao;
import minimart.engine.dao.KonstantaDao;
import minimart.engine.daoimpl.CustomerCommitDaoImpl;
import minimart.engine.daoimpl.CustomerDaoImpl;
import minimart.engine.daoimpl.KonstantaDaoImpl;
import minimart.engine.model.Customer;
import minimart.engine.model.CustomerCommit;
import minimart.engine.model.Konstanta;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.KonstantaService;

public class CustomerCommitService {
    private CustomerCommitDao customerCommit = new CustomerCommitDaoImpl();
    private CommonEngine comm = new CommonEngine();
    private KonstantaDao kd = new KonstantaDaoImpl();
    private CustomerDao cd = new CustomerDaoImpl();
    private KonstantaService konstantaService = new KonstantaService();

    public List<CustomerCommit> getAllCommits() {
        return this.customerCommit.getAllCommits();
    }

    public List<CustomerCommit> getUncommited() {
        return this.customerCommit.getUncommitted();
    }

    public List<CustomerCommit> getQueryTypeCommits(String queryType) {
        return this.customerCommit.getQueryTypeCommits(queryType);
    }

    public Boolean insertCustomerCommit(Customer cc, String query) {
        CustomerCommit c1 = this.customerCommit.insertCustomerCommit(cc, query);
        if (cc.getId().equals(c1.getIdCustomer())) {
            CustomerCommit c2 = this.customerCommit.insertCustomerCommitLog(cc, c1.getCustomerTransactionCommitId());
            return cc.getId().equals(c1.getIdCustomer()) && cc.getId().equals(c2.getIdCustomer());
        }
        return false;
    }

    public Boolean deleteCustomerCommit(CustomerCommit cc) {
        return this.customerCommit.deleteCustomerCommit(cc) == 1;
    }

    public Boolean updateCustomerCommit(CustomerCommit cc) {
        if (cc.getCustomerTransactionCommitId() == this.customerCommit.updateCustomerCommit(cc).getCustomerTransactionCommitId()) {
            return true;
        }
        return false;
    }

    public void pullCustomers() throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(System.currentTimeMillis());
            String pull_result = this.comm.get("/customers");
            if (!pull_result.equals("null")) {
                System.out.println(System.currentTimeMillis());
                Customer[] pulled_list = (Customer[])mapper.readValue(pull_result, Customer[].class);
                List<Customer> customers = this.cd.getCustomers("");
                HashMap<String, Customer> mapCustomer = new HashMap<String, Customer>();
                HashMap<String, Customer> mapCustomerOnline = new HashMap<String, Customer>();
                for (Customer customer2 : pulled_list) {
                    mapCustomerOnline.put(customer2.getId(), customer2);
                }
                for (Customer customer1 : customers) {
                    mapCustomer.put(customer1.getId(), customer1);
                    Customer customerOffObj = (Customer)mapCustomerOnline.get(customer1.getId());
                    if (customerOffObj != null) continue;
                    this.cd.deleteCustomer(customer1.getId());
                    System.out.println(customer1.getId());
                }
                for (int id = 0; id < pulled_list.length; ++id) {
                    Customer specific_customer = pulled_list[id];
                    Customer customerObj = (Customer)mapCustomer.get(specific_customer.getId());
                    specific_customer.setCreatedByCabang(String.valueOf(specific_customer.getIdCabang()));
                    if (customerObj != null) {
                        Boolean isNewer = false;
                        if ((customerObj.getAlamat() != null ? !customerObj.getAlamat().equals(specific_customer.getAlamat()) : specific_customer.getAlamat() != null) || customerObj.getHutang() != specific_customer.getHutang() || (customerObj.getKtp() != null ? !customerObj.getKtp().equals(specific_customer.getKtp()) : specific_customer.getKtp() != null) || (customerObj.getKota() != null ? !customerObj.getKota().equals(specific_customer.getKota()) : specific_customer.getKota() != null) || customerObj.getLimitKredit() != specific_customer.getLimitKredit() || (customerObj.getNama() != null ? !customerObj.getNama().equals(specific_customer.getNama()) : specific_customer.getNama() != null) || customerObj.getPoint() != specific_customer.getPoint() || (customerObj.getTelepon() != null ? !customerObj.getTelepon().equals(specific_customer.getTelepon()) : specific_customer.getTelepon() != null) || (customerObj.getTipe() != null ? !customerObj.getTipe().equals(specific_customer.getTipe()) : specific_customer.getTipe() != null) || customerObj.getTop() != specific_customer.getTop() || (customerObj.getKeterangan() != null ? !customerObj.getKeterangan().equals(specific_customer.getKeterangan()) : specific_customer.getKeterangan() != null) || (customerObj.getTag() != null ? !customerObj.getTag().equals(specific_customer.getTag()) : specific_customer.getTag() != null) || (customerObj.getCreatedByCabang() != null ? !customerObj.getCreatedByCabang().equals(specific_customer.getCreatedByCabang()) : specific_customer.getIdCabang() > 0) || (customerObj.getNamaPerusahaan() != null ? !customerObj.getNamaPerusahaan().equals(specific_customer.getNamaPerusahaan()) : specific_customer.getNamaPerusahaan() != null) || (customerObj.getBank() != null ? !customerObj.getBank().equals(specific_customer.getBank()) : specific_customer.getBank() != null) || (customerObj.getNomorRekening() == null ? specific_customer.getNomorRekening() != null : !customerObj.getNomorRekening().equals(specific_customer.getNomorRekening()))) {
                            isNewer = true;
                        }
                        if (!isNewer.booleanValue()) continue;
                        System.out.println(specific_customer.getId() + " - " + specific_customer.getNama() + " - " + specific_customer.getKeterangan() + " - UPDATE");
                        this.cd.updateCustomerSyncFromOnline(specific_customer);
                        continue;
                    }
                    System.out.println(specific_customer.getId() + " - " + specific_customer.getNama() + " - " + specific_customer.getKeterangan() + " - CREATE");
                    this.cd.createCustomerSyncFromOnline(specific_customer);
                }
            }
        }
        catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("Gagal menarik data dari Database Online \n Coba lagi dengan koneksi internet yang lebih baik");
        }
    }

    public int pushCustomerCommits() throws Exception {
        int status = 1;
        if (this.konstantaService.getConstantValue("sinkronCustomer") == null) {
            System.out.println("NULL CONSTANT SINKRON CUSTOMER");
            Konstanta konstanta = new Konstanta();
            konstanta.setKonstan("sinkronCustomer");
            konstanta.setValue("false");
            this.konstantaService.createConstant(konstanta);
        }
        if ("false".equals(this.konstantaService.getConstantValue("sinkronCustomer"))) {
            try {
                this.konstantaService.updateConstant("sinkronCustomer", "true");
                List<CustomerCommit> customers = this.customerCommit.getUncommitted();
                for (int idx = 0; idx < customers.size(); ++idx) {
                    CustomerCommit element = customers.get(idx);
                    Customer element_detail = this.customerCommit.getUncommittedLog(element.getCustomerTransactionCommitId());
                    Customer cust = this.cd.getCustomerById(element_detail.getId());
                    element_detail.setKeterangan(cust.getKeterangan());
                    element_detail.setTag(cust.getTag());
                    element_detail.setNamaPerusahaan(cust.getNamaPerusahaan());
                    System.out.println(element.getCustomerTransactionCommitId());
                    element_detail.setIdCabang(Integer.parseInt(this.kd.getConstantValue("idCabang")));
                    element_detail.setCreated(null);
                    element_detail.setUpdated(null);
                    ObjectMapper mapper = new ObjectMapper();
                    System.out.println(mapper.writeValueAsString((Object)element_detail));
                    String push_result = this.comm.post("/insert/customer", mapper.writeValueAsString((Object)element_detail));
                    System.out.println("RESULT" + push_result);
                    if (push_result != null && push_result.contains("tipe")) {
                        int ex = this.customerCommit.commitCustomerCommit(element);
                        if (ex == 1) continue;
                        this.konstantaService.updateConstant("sinkronCustomer", "false");
                        throw new Exception("Gagal menghapus commit yang ada di DB local");
                    }
                    this.konstantaService.updateConstant("sinkronCustomer", "false");
                    throw new Exception("Terjadi Error, harap coba lagi...");
                }
            }
            catch (Exception e) {
                status = 0;
                this.konstantaService.updateConstant("sinkronCustomer", "false");
                Logger.getLogger(CustomerCommitService.class.getName()).log(Level.SEVERE, null, e);
                throw new RuntimeException("Gagal mengirim data ke Database Online \n Coba lagi dengan koneksi internet yang lebih baik");
            }
        } else {
            throw new Exception("Sinkronisasi lain sedang berlangsung, hubungi team IT!");
        }
        this.konstantaService.updateConstant("sinkronCustomer", "false");
        return status;
    }

    public Long getPointUncommited(String id) {
        return this.customerCommit.getPointUncommited(id);
    }

    public int updateStatusCommit(String id) {
        int result = this.customerCommit.updateStatusCommit(id);
        return result;
    }
}

