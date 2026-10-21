/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PromoDiskonByJenisDetailDao;
import minimart.engine.daoimpl.PromoDiskonByJenisDetailDaoImpl;
import minimart.engine.model.PromoDiskonByJenisDetail;

public class PromoDiskonByJenisDetailService {
    private PromoDiskonByJenisDetailDao dao = new PromoDiskonByJenisDetailDaoImpl();

    public int createPromoDiscJenisDetail(PromoDiskonByJenisDetail promoDiskonByJenisDetail) {
        return this.dao.createPromoDiscJenisDetail(promoDiskonByJenisDetail);
    }

    public List<PromoDiskonByJenisDetail> getPromoDiscJenisDetail(String promoId) {
        return this.dao.getPromoDiscJenisDetail(promoId);
    }

    public int deletePromoDiscJenisDetail(String promoId) {
        return this.dao.deletePromoDiscJenisDetail(promoId);
    }

    public int updatePromoDiscJenisDetail(PromoDiskonByJenisDetail promoDiskonByJenisDetail) {
        return this.dao.updatePromoDiscJenisDetail(promoDiskonByJenisDetail);
    }

    public PromoDiskonByJenisDetail getPromoDiscJenisDetailByMJenis(String jenisBarang) {
        return this.dao.getPromoDiscJenisDetailByMJenis(jenisBarang);
    }
}

