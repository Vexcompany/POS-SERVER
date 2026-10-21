/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.RBarangSupplierDao;
import minimart.engine.dao.RBarangSupplierOnlineDao;
import minimart.engine.daoimpl.RBarangSupplierDaoImpl;
import minimart.engine.daoimpl.RBarangSupplierOnlineDaoImpl;
import minimart.engine.model.RBarangSupplier;
import minimart.engine.model.RBarangSupplierOnline;
import minimart.engine.service.KonstantaService;

public class RBarangSupplierService {
    private RBarangSupplierDao barangSupplierDao = new RBarangSupplierDaoImpl();
    private KonstantaService konstantaService = new KonstantaService();
    private RBarangSupplierOnlineDao barangSupplierOnlineDao = new RBarangSupplierOnlineDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int createRBarangSupplier(RBarangSupplier rbs) {
        this.ensureOnlineConnection();
        RBarangSupplierOnline rbsO = new RBarangSupplierOnline();
        rbsO.setBarangId(rbs.getBarangId());
        rbsO.setSupplierId(rbs.getSupplierId());
        String tipeToko = this.konstantaService.getTipeTokoFirstLetterCap();
        rbsO.setTipeToko(tipeToko);
        rbsO.setId(rbs.getSupplierId() + "_" + rbs.getBarangId() + "_" + tipeToko);
        int result = 0;
        result = this.barangSupplierOnlineDao.createRBarangSupplier(rbsO);
        if (result == 1) {
            return this.barangSupplierDao.createRBarangSupplier(rbs);
        }
        return result;
    }

    public List<RBarangSupplier> readRBSByBarangId(String barangId) {
        return this.barangSupplierDao.readRBSByBarangId(barangId);
    }

    public List<Object[]> readRBSBySupplierId(String supplierId) {
        return this.barangSupplierDao.readRBSBySupplierId(supplierId);
    }

    public int deleteRBSByBarangIdSupplierId(String barangId, String supplierId) {
        this.ensureOnlineConnection();
        String tipeToko = this.konstantaService.getTipeTokoFirstLetterCap();
        int result = 0;
        result = this.barangSupplierOnlineDao.deleteRBSByBarangIdSupplierId(barangId, supplierId, tipeToko);
        if (result == 1) {
            return this.barangSupplierDao.deleteRBSByBarangIdSupplierId(barangId, supplierId);
        }
        return result;
    }

    public boolean isRBSHasCreated(String barangId, String supplierId) {
        return this.barangSupplierDao.isRBSHasCreated(barangId, supplierId);
    }

    public List<RBarangSupplier> readRBSAll() {
        return this.barangSupplierDao.readRBSAll();
    }
}

