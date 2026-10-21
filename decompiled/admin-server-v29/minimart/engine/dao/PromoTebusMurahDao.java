/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoTebusMurah;

public interface PromoTebusMurahDao {
    public int create(PromoTebusMurah var1);

    public int update(PromoTebusMurah var1);

    public String getLastId();

    public PromoTebusMurah getPromoTebusMurahById(String var1);

    public List<PromoTebusMurah> getPromoTebusMurah(String var1, String var2);

    public List<PromoTebusMurah> getPromoTebusMurahAll();

    public int[] upsertPromoTebusMurahBatch(List<PromoTebusMurah> var1);

    public int deletePromoTebusMurahByIds(String var1);
}

