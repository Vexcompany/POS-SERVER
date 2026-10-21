/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.PromoHematDao;
import minimart.engine.dao.PromoHematOnlineDao;
import minimart.engine.daoimpl.PromoHematDaoImpl;
import minimart.engine.daoimpl.PromoHematOnlineDaoImpl;
import minimart.engine.model.PromoHemat;
import minimart.engine.model.PromoHematDetail;

public class PromoHematService {
    private PromoHematDao promoHematDao = new PromoHematDaoImpl();
    private PromoHematOnlineDao promoHematOnlineDao = new PromoHematOnlineDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int createPromoHemat(PromoHemat promoHemat) {
        return this.promoHematDao.createPromoHemat(promoHemat);
    }

    public PromoHemat getPromoHematById(String id) {
        return this.promoHematDao.getPromoHematById(id);
    }

    public int updatePromoHemat(PromoHemat promoHemat) {
        return this.promoHematDao.updatePromoHemat(promoHemat);
    }

    public int deletePromoHematById(String id) {
        return this.promoHematDao.deletePromoHematById(id);
    }

    public String getLastId() {
        return this.promoHematDao.getLastId();
    }

    public boolean isPromoAktif(String nowDate) {
        return this.promoHematDao.isPromoAktif(nowDate);
    }

    public List<PromoHemat> getPromoHemat(String startDate, String endDate) {
        return this.promoHematDao.getPromoHemat(startDate, endDate);
    }

    public List<PromoHemat> getPromoHematOnline(String idCabang) {
        this.ensureOnlineConnection();
        return this.promoHematOnlineDao.getPromoHematOnline(idCabang);
    }

    public List<PromoHematDetail> getPromoHematDetailOnlineByPromoHematId(String promoHematId) {
        this.ensureOnlineConnection();
        return this.promoHematOnlineDao.getPromoHematDetailOnlineByPromoHematId(promoHematId);
    }

    public int[] upsertPromoHematBatch(List<PromoHemat> datas) {
        return this.promoHematDao.upsertPromoHematBatch(datas);
    }
}

