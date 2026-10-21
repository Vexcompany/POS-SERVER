/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PromoTebusMurahDao;
import minimart.engine.daoimpl.PromoTebusMurahDaoImpl;
import minimart.engine.model.PromoTebusMurah;

public class PromoTebusMurahService {
    private PromoTebusMurahDao dao = new PromoTebusMurahDaoImpl();

    public int create(PromoTebusMurah promoTebusMurah) {
        return this.dao.create(promoTebusMurah);
    }

    public int update(PromoTebusMurah promoTebusMurah) {
        return this.dao.update(promoTebusMurah);
    }

    public String getLastId() {
        return this.dao.getLastId();
    }

    public PromoTebusMurah getPromoTebusMurahById(String id) {
        return this.dao.getPromoTebusMurahById(id);
    }

    public List<PromoTebusMurah> getPromoTebusMurah(String startDate, String endDate) {
        return this.dao.getPromoTebusMurah(startDate, endDate);
    }
}

