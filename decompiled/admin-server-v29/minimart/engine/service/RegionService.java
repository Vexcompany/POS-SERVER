/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.RegionOnlineDao;
import minimart.engine.daoimpl.RegionOnlineDaoImpl;
import minimart.engine.model.CustomerAlamat;
import minimart.engine.model.Region;

public class RegionService {
    RegionOnlineDao regionDao = new RegionOnlineDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public List<Region> getRegion(String searchQuery) {
        this.ensureOnlineConnection();
        return this.regionDao.getRegion(searchQuery);
    }

    public Region getRegionByIdKelurahan(String idKelurahan) {
        this.ensureOnlineConnection();
        return this.regionDao.getRegionByIdKelurahan(idKelurahan);
    }

    public CustomerAlamat getCustomerAlamatPrimaryByIdCustomer(String idCustomer) {
        this.ensureOnlineConnection();
        return this.regionDao.getCustomerAlamatPrimaryByIdCustomer(idCustomer);
    }

    public int insertCustomerAlamat(CustomerAlamat alamat) {
        this.ensureOnlineConnection();
        return this.regionDao.insertCustomerAlamat(alamat);
    }

    public int updateCustomerAlamat(CustomerAlamat alamat) {
        this.ensureOnlineConnection();
        return this.regionDao.insertCustomerAlamat(alamat);
    }
}

