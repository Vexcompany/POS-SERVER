/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.KonstantaDao;
import minimart.engine.dao.RepackDetailDao;
import minimart.engine.dao.RepackOnlineDao;
import minimart.engine.daoimpl.KonstantaDaoImpl;
import minimart.engine.daoimpl.RepackDetailDaoImpl;
import minimart.engine.daoimpl.RepackOnlineDaoImpl;
import minimart.engine.model.RepackDetail;

public class RepackDetailService {
    RepackDetailDao repackDetailDao = new RepackDetailDaoImpl();
    RepackOnlineDao rod = new RepackOnlineDaoImpl();
    KonstantaDao kd = new KonstantaDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int createdRepackDetail(RepackDetail repackDetail) {
        return this.repackDetailDao.createdRepackDetail(repackDetail);
    }

    public int[] createRepackDetailBatch(List<RepackDetail> repackDetails) {
        this.ensureOnlineConnection();
        String idCabang = this.kd.getConstantValue("idCabang");
        int[] resOnline = this.rod.createRepackDetailBatchOnline(repackDetails, idCabang);
        int success = 0;
        for (int i = 0; i < resOnline.length; ++i) {
            success += resOnline[i];
        }
        if (success == resOnline.length) {
            return this.repackDetailDao.createRepackDetailBatch(repackDetails);
        }
        return resOnline;
    }

    public List<RepackDetail> getRepackDetail(String idRepack) {
        return this.repackDetailDao.getRepackDetail(idRepack);
    }

    public List<RepackDetail> getRepackDetail(String startDate, String endDate, String m_barang_id) {
        return this.repackDetailDao.getRepackDetail(startDate, endDate, m_barang_id);
    }
}

