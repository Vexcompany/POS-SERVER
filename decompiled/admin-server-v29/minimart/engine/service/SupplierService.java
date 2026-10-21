/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.HashMap;
import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.SupplierDao;
import minimart.engine.dao.SupplierOnlineDao;
import minimart.engine.daoimpl.SupplierDaoImpl;
import minimart.engine.daoimpl.SupplierOnlineDaoImpl;
import minimart.engine.model.Supplier;
import minimart.engine.model.SupplierOnline;
import minimart.engine.service.KonstantaService;

public class SupplierService {
    private SupplierDao supplierDao = new SupplierDaoImpl();
    private SupplierOnlineDao supplierOnlineDao = new SupplierOnlineDaoImpl();
    private KonstantaService konstantaService = new KonstantaService();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int createSupplier(Supplier supplier) {
        this.ensureOnlineConnection();
        SupplierOnline supplierOnline = new SupplierOnline();
        supplierOnline.setIdOffline(supplier.getId());
        supplierOnline.setNama(supplier.getNama());
        supplierOnline.setKota(supplier.getKota());
        supplierOnline.setAlamat(supplier.getAlamat());
        supplierOnline.setAvailable(supplier.isAvailable());
        supplierOnline.setContactPerson(supplier.getContactPerson());
        supplierOnline.setTop(supplier.getTop());
        supplierOnline.setCreated(supplier.getCreated());
        supplierOnline.setUpdated(supplier.getUpdated());
        supplierOnline.setTelepon(supplier.getTelepon());
        String tipeToko = this.konstantaService.getTipeTokoFirstLetterCap();
        String idSup = supplier.getId() + "_" + tipeToko;
        supplierOnline.setId(idSup);
        supplierOnline.setTipeToko(tipeToko);
        int result = 0;
        result = this.supplierOnlineDao.createSupplier(supplierOnline);
        if (result == 1) {
            return this.supplierDao.createSupplier(supplier);
        }
        return result;
    }

    public int createSupplierOfflineOnly(Supplier supplier) {
        return this.supplierDao.createSupplier(supplier);
    }

    public Supplier getSupplier(String id) {
        return this.supplierDao.getSupplier(id);
    }

    public List<Supplier> getSuppliers(String nama) {
        return this.supplierDao.getSuppliers(nama);
    }

    public int updateSupplier(Supplier supplier) {
        this.ensureOnlineConnection();
        SupplierOnline supplierOnline = new SupplierOnline();
        supplierOnline.setIdOffline(supplier.getId());
        supplierOnline.setNama(supplier.getNama());
        supplierOnline.setKota(supplier.getKota());
        supplierOnline.setAlamat(supplier.getAlamat());
        supplierOnline.setAvailable(supplier.isAvailable());
        supplierOnline.setContactPerson(supplier.getContactPerson());
        supplierOnline.setTop(supplier.getTop());
        supplierOnline.setCreated(supplier.getCreated());
        supplierOnline.setUpdated(supplier.getUpdated());
        supplierOnline.setTelepon(supplier.getTelepon());
        String tipeToko = this.konstantaService.getTipeTokoFirstLetterCap();
        String idSup = supplier.getId() + "_" + tipeToko;
        supplierOnline.setId(idSup);
        supplierOnline.setTipeToko(tipeToko);
        int result = 0;
        result = this.supplierOnlineDao.updateSupplier(supplierOnline);
        if (result == 1) {
            return this.supplierDao.updateSupplier(supplier);
        }
        return result;
    }

    public int updateSupplierOfflineOnly(Supplier supplier) {
        return this.supplierDao.updateSupplier(supplier);
    }

    public int deleteSupplier(String id) {
        this.ensureOnlineConnection();
        String tipeToko = this.konstantaService.getTipeTokoFirstLetterCap();
        String idSup = id + "_" + tipeToko;
        int result = 0;
        result = this.supplierOnlineDao.deleteSupplier(idSup);
        if (result == 1) {
            return this.supplierDao.deleteSupplier(id);
        }
        return result;
    }

    public int deleteSupplierOfflineOnly(String id) {
        return this.supplierDao.deleteSupplier(id);
    }

    public String getLastId() {
        return this.supplierDao.getLastId();
    }

    public void syncSupplierMaster() {
        this.ensureOnlineConnection();
        String tipeToko = this.konstantaService.getTipeTokoFirstLetterCap();
        List<SupplierOnline> supplierOnlines = this.supplierOnlineDao.getSupplierOnline(tipeToko);
        List<Supplier> suppliers = this.supplierDao.getSuppliers("");
        HashMap<String, SupplierOnline> mapSupplierOnline = new HashMap<String, SupplierOnline>();
        HashMap<String, Supplier> mapSupplier = new HashMap<String, Supplier>();
        int result = 0;
        for (Supplier s : suppliers) {
            mapSupplier.put(s.getId(), s);
        }
        for (SupplierOnline so : supplierOnlines) {
            mapSupplierOnline.put(so.getIdOffline(), so);
            Supplier soff = (Supplier)mapSupplier.get(so.getIdOffline());
            if (soff == null) {
                Supplier supplierTemp = new Supplier();
                supplierTemp.setId(so.getIdOffline());
                supplierTemp.setNama(so.getNama());
                supplierTemp.setAlamat(so.getAlamat());
                supplierTemp.setKota(so.getKota());
                supplierTemp.setTelepon(so.getTelepon());
                supplierTemp.setContactPerson(so.getContactPerson());
                supplierTemp.setTop(so.getTop());
                supplierTemp.setCreated(so.getCreated());
                supplierTemp.setUpdated(so.getUpdated());
                supplierTemp.setAvailable(true);
                result = this.createSupplierOfflineOnly(supplierTemp);
                System.out.println(supplierTemp.getId() + " CREATE: " + result);
                continue;
            }
            boolean update = false;
            if (!so.getNama().equals(soff.getNama())) {
                soff.setNama(so.getNama());
                update = true;
            }
            if (!so.getAlamat().equals(soff.getAlamat())) {
                soff.setAlamat(so.getAlamat());
                update = true;
            }
            if (!so.getKota().equals(soff.getKota())) {
                soff.setKota(so.getKota());
                update = true;
            }
            if (!so.getTelepon().equals(soff.getTelepon())) {
                soff.setTelepon(so.getTelepon());
                update = true;
            }
            if (!so.getContactPerson().equals(soff.getContactPerson())) {
                soff.setContactPerson(so.getContactPerson());
                update = true;
            }
            if (so.getTop() != soff.getTop()) {
                soff.setTop(so.getTop());
                update = true;
            }
            if (!update) continue;
            result = this.updateSupplierOfflineOnly(soff);
            System.out.println(soff.getId() + " UPDATE: " + result);
        }
    }
}

