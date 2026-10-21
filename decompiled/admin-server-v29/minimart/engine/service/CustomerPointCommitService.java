/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.json.simple.JSONObject
 */
package minimart.engine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import minimart.engine.dao.CustomerDao;
import minimart.engine.dao.CustomerPointCommitDao;
import minimart.engine.daoimpl.CustomerDaoImpl;
import minimart.engine.daoimpl.CustomerPointCommitDaoImpl;
import minimart.engine.model.Customer;
import minimart.engine.model.CustomerPointCommit;
import minimart.engine.model.Konstanta;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.KonstantaService;
import org.json.simple.JSONObject;

public class CustomerPointCommitService {
    private CustomerPointCommitDao customerPointCommit = new CustomerPointCommitDaoImpl();
    private CommonEngine comm = new CommonEngine();
    private CustomerDao cd = new CustomerDaoImpl();
    private KonstantaService konstantaService = new KonstantaService();

    public List<CustomerPointCommit> getAllCommits() {
        return this.customerPointCommit.getAllCommits();
    }

    public List<CustomerPointCommit> getUncommited() {
        return this.customerPointCommit.getUnCommited();
    }

    public List<CustomerPointCommit> getQueryTypeCommits(String queryType) {
        return this.customerPointCommit.getQueryTypeCommits(queryType);
    }

    public Boolean insertPointCommit(String cc, String p, float point, String query, String kelas) {
        CustomerPointCommit c1 = this.customerPointCommit.insertPointCommit(cc, p, point, query, kelas);
        return cc.equals(c1.getKtpCustomer());
    }

    public void pullCustomerPoints() throws IOException {
        block5: {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String pull_result = this.comm.get("/customers");
                if (pull_result.equals("null")) break block5;
                Customer[] pulled_list = (Customer[])mapper.readValue(pull_result, Customer[].class);
                for (int id = 0; id < pulled_list.length; ++id) {
                    System.out.println("GET");
                    Customer specific_customer = pulled_list[id];
                    System.out.println("1 " + specific_customer.getKtp());
                    System.out.println("2 " + this.cd.getCustomerByKtp(specific_customer.getKtp()));
                    try {
                        int result;
                        Boolean isNewer = this.cd.getCustomerById(specific_customer.getId()).getUpdated().before(specific_customer.getUpdated());
                        if (!isNewer.booleanValue() || (result = this.cd.updateHardPointCustomer((int)specific_customer.getPoint(), specific_customer.getKtp())) != 0) continue;
                        throw new RuntimeException("Gagal lempar poins dalam");
                    }
                    catch (Exception e) {
                        throw new RuntimeException("Gagal lempar poins");
                    }
                }
            }
            catch (RuntimeException e) {
                System.out.println(e);
                throw new RuntimeException("Gagal menarik data dari Database Online \n Coba lagi dengan koneksi internet yang lebih baik");
            }
        }
    }

    public void pushCustomerCommits() throws Exception {
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
                List<CustomerPointCommit> customersPoint = this.customerPointCommit.getUnCommited();
                for (int idx = 0; idx < customersPoint.size(); ++idx) {
                    CustomerPointCommit element = customersPoint.get(idx);
                    JSONObject sampleObject = new JSONObject();
                    sampleObject.put((Object)"customer_id", (Object)element.getKtpCustomer());
                    sampleObject.put((Object)"point", (Object)element.getPoint());
                    System.out.println(sampleObject.toJSONString());
                    String push_result = this.comm.post("/add/point", sampleObject.toJSONString());
                    System.out.println("RESULT POINT" + push_result);
                    if (push_result != null && push_result.contains("tipe")) {
                        int ex = this.customerPointCommit.commitPointCommit(element);
                        if (ex == 1) continue;
                        this.konstantaService.updateConstant("sinkronCustomer", "false");
                        throw new Exception("Gagal menghapus commit yang ada di DB local");
                    }
                    this.konstantaService.updateConstant("sinkronCustomer", "false");
                    throw new Exception("Terjadi Error, harap coba lagi...");
                }
            }
            catch (Exception e) {
                this.konstantaService.updateConstant("sinkronCustomer", "false");
                throw new RuntimeException("Gagal mengirim data ke Database Online \n Coba lagi dengan koneksi internet yang lebih baik");
            }
        } else {
            throw new Exception("Sinkronisasi lain sedang berlangsung, hubungi team IT!");
        }
        this.konstantaService.updateConstant("sinkronCustomer", "false");
    }
}

