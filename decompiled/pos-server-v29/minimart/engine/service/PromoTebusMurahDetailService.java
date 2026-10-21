/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PromoTebusMurahDetailDao;
import minimart.engine.daoimpl.PromoTebusMurahDetailDaoImpl;
import minimart.engine.model.PromoTebusMurahDetail;

public class PromoTebusMurahDetailService {
    PromoTebusMurahDetailDao dao = new PromoTebusMurahDetailDaoImpl();

    public int create(PromoTebusMurahDetail promoTebusMurahDetail) {
        return this.dao.create(promoTebusMurahDetail);
    }

    public int update(PromoTebusMurahDetail promoTebusMurahDetail) {
        return this.dao.update(promoTebusMurahDetail);
    }

    public List<PromoTebusMurahDetail> getPromoTebusMurahDetail(String promoTebusMurahId) {
        return this.dao.getPromoTebusMurahDetail(promoTebusMurahId);
    }

    public int deletePromoTebusMurahDetail(String promoTebusMurahId) {
        return this.dao.deletePromoTebusMurahDetail(promoTebusMurahId);
    }

    public PromoTebusMurahDetail getPromoTebusMurahDetailByBarangId(String barangId, String satuan, String startDate, String endDate) {
        return this.dao.getPromoTebusMurahDetailByBarangId(barangId, satuan, startDate, endDate);
    }

    public PromoTebusMurahDetail getPromoTebusMurahDetailByBarangId(String barangId, String startDate, String endDate) {
        return this.dao.getPromoTebusMurahDetailByBarangId(barangId, startDate, endDate);
    }

    public List<Object[]> getPromoTebusMurahByNominal(String startDate, String endDate, long nominal) {
        return this.dao.getPromoTebusMurahByNominal(startDate, endDate, nominal);
    }

    public List<Object[]> getAvailableTebusMurah(String startDate, String endDate) {
        return this.dao.getAvailableTebusMurah(startDate, endDate);
    }
}

