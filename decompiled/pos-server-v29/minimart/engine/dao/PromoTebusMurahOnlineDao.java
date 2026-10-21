/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoTebusMurah;
import minimart.engine.model.PromoTebusMurahDetail;

public interface PromoTebusMurahOnlineDao {
    public List<PromoTebusMurah> getPromoTebusMurahOnline(String var1);

    public List<PromoTebusMurahDetail> getPromoTebusMurahDetailOnlineByPromoTebusMurahId(String var1);
}

