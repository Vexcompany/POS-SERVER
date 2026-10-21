/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.PromoHematDetailDao;
import minimart.engine.dao.PromoHematOnlineDao;
import minimart.engine.daoimpl.PromoHematDetailDaoImpl;
import minimart.engine.daoimpl.PromoHematOnlineDaoImpl;
import minimart.engine.model.PromoHematDetail;

public class PromoHematDetailService {
    private PromoHematDetailDao dao = new PromoHematDetailDaoImpl();
    private PromoHematOnlineDao promoHematOnlineDao = new PromoHematOnlineDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int createPromoHemat(PromoHematDetail promoHematDetail) {
        return this.dao.createPromoHemat(promoHematDetail);
    }

    public List<PromoHematDetail> getPromoHematDetailByPromoHematId(String promoHematId) {
        return this.dao.getPromoHematDetailByPromoHematId(promoHematId);
    }

    public int deletePromoHematDetailByPromoHematId(String promoHematId) {
        return this.dao.deletePromoHematDetailByPromoHematId(promoHematId);
    }

    public int updatePromoHematDetail(PromoHematDetail promoHematDetail) {
        return this.dao.updatePromoHematDetail(promoHematDetail);
    }

    public PromoHematDetail getPromoHematDetailByBarangId(String barangId, String satuan, float minPembelian, String startDate, String endDate) {
        return this.dao.getPromoHematDetailByBarangId(barangId, satuan, minPembelian, startDate, endDate);
    }

    public PromoHematDetail getPromoHematDetailByBarangId(String barangId, String startDate, String endDate) {
        return this.dao.getPromoHematDetailByBarangId(barangId, startDate, endDate);
    }

    public List<PromoHematDetail> getPromoHematDetailOnlineByPromoHematId(String promoHematId) {
        this.ensureOnlineConnection();
        return this.promoHematOnlineDao.getPromoHematDetailOnlineByPromoHematId(promoHematId);
    }

    public int[] upsertPromoHematDetailBatch(List<PromoHematDetail> datas) {
        return this.dao.upsertPromoHematDetailBatch(datas);
    }

    public List<Object[]> getAvailablePromo() {
        return this.dao.getAvailablePromo();
    }
}

