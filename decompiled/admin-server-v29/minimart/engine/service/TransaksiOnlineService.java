/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.TransaksiOnlineDao;
import minimart.engine.dao.TransaksiOnlineDetailDao;
import minimart.engine.daoimpl.TransaksiOnlineDaoImpl;
import minimart.engine.daoimpl.TransaksiOnlineDetailDaoImpl;
import minimart.engine.model.TransaksiOnline;
import minimart.engine.model.TransaksiOnlineDetail;

public class TransaksiOnlineService {
    TransaksiOnlineDao toDao = new TransaksiOnlineDaoImpl();
    TransaksiOnlineDetailDao todDao = new TransaksiOnlineDetailDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public TransaksiOnline getTransaksiOnlineByUc(int idTransaksiOnline) {
        this.ensureOnlineConnection();
        return this.toDao.getTransaksiOnlineByUniqueCode(idTransaksiOnline);
    }

    public int updateIdTransaksiOfflineTransaksiOnlineById(int idto, String idTransaksiOffline, String karyawanId) {
        this.ensureOnlineConnection();
        int result_update = this.toDao.updateIdTransaksiOfflineTransaksiOnline(idto, idTransaksiOffline);
        int result_update_log_1 = 0;
        if (result_update > 0) {
            result_update_log_1 = this.toDao.updateIdTransaksiOnlineStatusLog(idto, "DIPROSES", karyawanId);
            System.out.println("Log Status DIPROSES: " + result_update_log_1);
        }
        return result_update;
    }

    public List<TransaksiOnlineDetail> getTransaksiOnlineDetailById(int idto) {
        this.ensureOnlineConnection();
        return this.todDao.getTransaksiOnlineDetailByIdTransaksiOnline(idto);
    }
}

