/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PromoTebusDao;
import minimart.engine.daoimpl.PromoTebusDaoImpl;
import minimart.engine.model.PromoTebus;

public class PromoTebusService {
    PromoTebusDao promoTebusDao = new PromoTebusDaoImpl();

    public int createPromoTebus(PromoTebus promoTebus) {
        return this.promoTebusDao.createPromoTebus(promoTebus);
    }

    public PromoTebus getPromoTebus(String id) {
        return this.promoTebusDao.getPromoTebus(id);
    }

    public int updatePromotebus(PromoTebus promoTebus) {
        return this.promoTebusDao.updatePromotebus(promoTebus);
    }

    public int deletePromoTebusById(int id) {
        return this.promoTebusDao.deletePromoTebusById(id);
    }

    public boolean isPromoAktif(String nowDate) {
        return this.promoTebusDao.isPromoAktif(nowDate);
    }

    public String getLastId() {
        return this.promoTebusDao.getLastId();
    }

    public List<PromoTebus> getPromoTebus(String startDate, String endDate) {
        return this.promoTebusDao.getPromoTebus(startDate, endDate);
    }
}

