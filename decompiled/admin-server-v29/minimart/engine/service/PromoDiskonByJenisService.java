/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import minimart.engine.dao.PromoDiskonByJenisDao;
import minimart.engine.daoimpl.PromoDiskonByJenisDaoImpl;
import minimart.engine.model.PromoDiskonByJenis;

public class PromoDiskonByJenisService {
    private PromoDiskonByJenisDao dao = new PromoDiskonByJenisDaoImpl();

    public int createPromoDiscJenis(PromoDiskonByJenis promoDiskonByJenis) {
        return this.dao.createPromoDiscJenis(promoDiskonByJenis);
    }

    public PromoDiskonByJenis getPromoByJenis(String id) {
        return this.dao.getPromoByJenis(id);
    }

    public int updatePromoDiscJenis(PromoDiskonByJenis promoDiskonByJenis) {
        return this.dao.updatePromoDiscJenis(promoDiskonByJenis);
    }

    public int deletePromoDiscJenisById(String id) {
        return this.dao.deletePromoDiscJenisById(id);
    }

    public int getLastId() {
        return this.dao.getLastId();
    }

    public boolean isPromoAktif(String nowDate) {
        return this.dao.isPromoAktif(nowDate);
    }
}

