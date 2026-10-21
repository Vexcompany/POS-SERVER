/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.SalesDao;
import minimart.engine.dao.SalesOnlineDao;
import minimart.engine.daoimpl.SalesDaoImpl;
import minimart.engine.daoimpl.SalesOnlineDaoImpl;
import minimart.engine.model.Sales;

public class SalesService {
    private SalesDao salesDao = new SalesDaoImpl();
    private SalesOnlineDao salesOnlineDao = new SalesOnlineDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int createSales(Sales sales) {
        this.ensureOnlineConnection();
        int onlineResult = this.salesOnlineDao.createSales(sales);
        if (onlineResult == 1) {
            return this.salesDao.createSales(sales);
        }
        return onlineResult;
    }

    public Sales getSales(String id) {
        return this.salesDao.getSales(id);
    }

    public int updateSales(Sales sales) {
        this.ensureOnlineConnection();
        int onlineResult = this.salesOnlineDao.updateSales(sales);
        if (onlineResult == 1) {
            return this.salesDao.updateSales(sales);
        }
        return onlineResult;
    }

    public int deleteSales(String id) {
        this.ensureOnlineConnection();
        int onlineResult = this.salesOnlineDao.deleteSales(id);
        if (onlineResult == 1) {
            return this.salesDao.deleteSales(id);
        }
        return onlineResult;
    }

    public int deleteSalesOfflineOnly(String id) {
        return this.salesDao.deleteSales(id);
    }

    public List<Sales> getSaleses(String nama) {
        return this.salesDao.getSaleses(nama);
    }

    public String getLastId() {
        return this.salesDao.getLastId();
    }

    public int[] upsertSalesFromOnline(List<Sales> saleses) {
        return this.salesDao.upsertSalesFromOnline(saleses);
    }

    public int syncOnlineSalesToOffline() {
        this.ensureOnlineConnection();
        try {
            List<Sales> salesOnline = this.salesOnlineDao.getSaleses("");
            List<Sales> salesOffline = this.salesDao.getSaleses("");
            HashMap<String, Sales> mapOnline = new HashMap<String, Sales>();
            HashMap<String, Sales> mapOffline = new HashMap<String, Sales>();
            for (Sales sales : salesOnline) {
                mapOnline.put(sales.getId(), sales);
            }
            for (Sales sales : salesOffline) {
                if (mapOnline.get(sales.getId()) == null) {
                    this.deleteSalesOfflineOnly(sales.getId());
                    continue;
                }
                mapOffline.put(sales.getId(), sales);
            }
            int[] result = this.salesDao.upsertSalesFromOnline(salesOnline);
            return 1;
        }
        catch (Exception ex) {
            System.out.println("SyncOnlineSalesToOffline: Failed -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Sales Online");
            return 0;
        }
    }
}

