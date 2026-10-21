/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.KonstantaDao;
import minimart.engine.dao.RepackDao;
import minimart.engine.dao.RepackOnlineDao;
import minimart.engine.daoimpl.KonstantaDaoImpl;
import minimart.engine.daoimpl.RepackDaoImpl;
import minimart.engine.daoimpl.RepackOnlineDaoImpl;
import minimart.engine.model.Repack;

public class RepackService {
    RepackDao repackDao = new RepackDaoImpl();
    RepackOnlineDao rod = new RepackOnlineDaoImpl();
    KonstantaDao kd = new KonstantaDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int createRepack(Repack repack) {
        this.ensureOnlineConnection();
        String idCabang = this.kd.getConstantValue("idCabang");
        int resOnline = this.rod.createRepackOnline(repack, idCabang);
        if (resOnline > 0) {
            return this.repackDao.createRepack(repack);
        }
        return 0;
    }

    public Repack getRepack(String id) {
        return this.repackDao.getRepack(id);
    }

    public List<Object[]> getRepack(String startdate, String enddate) {
        return this.repackDao.getRepack(startdate, enddate);
    }

    public String getLastId() {
        return this.repackDao.getLastId();
    }
}

