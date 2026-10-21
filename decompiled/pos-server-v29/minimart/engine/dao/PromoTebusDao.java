/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoTebus;

public interface PromoTebusDao {
    public int createPromoTebus(PromoTebus var1);

    public PromoTebus getPromoTebus(String var1);

    public int updatePromotebus(PromoTebus var1);

    public int deletePromoTebusById(int var1);

    public boolean isPromoAktif(String var1);

    public String getLastId();

    public List<PromoTebus> getPromoTebus(String var1, String var2);

    public List<PromoTebus> getPromoTebusAll();

    public int[] upsertPromoTebusBatch(List<PromoTebus> var1);

    public int deletePromoTebusByIds(String var1);
}

