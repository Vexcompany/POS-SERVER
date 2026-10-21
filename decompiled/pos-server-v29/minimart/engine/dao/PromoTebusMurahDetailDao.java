/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoTebusMurahDetail;

public interface PromoTebusMurahDetailDao {
    public int create(PromoTebusMurahDetail var1);

    public int update(PromoTebusMurahDetail var1);

    public List<PromoTebusMurahDetail> getPromoTebusMurahDetail(String var1);

    public int deletePromoTebusMurahDetail(String var1);

    public PromoTebusMurahDetail getPromoTebusMurahDetailByBarangId(String var1, String var2, String var3, String var4);

    public PromoTebusMurahDetail getPromoTebusMurahDetailByBarangId(String var1, String var2, String var3);

    public List<PromoTebusMurahDetail> getPromoTebusMurahDetailAll();

    public int[] upsertPromoTebusMurahDetailBatch(List<PromoTebusMurahDetail> var1);

    public int deletePromoTebusMurahDetailByIds(String var1);

    public List<Object[]> getPromoTebusMurahByNominal(String var1, String var2, long var3);

    public List<Object[]> getAvailableTebusMurah(String var1, String var2);
}

