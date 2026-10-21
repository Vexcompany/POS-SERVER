/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoHemat;

public interface PromoHematDao {
    public int createPromoHemat(PromoHemat var1);

    public PromoHemat getPromoHematById(String var1);

    public int updatePromoHemat(PromoHemat var1);

    public int deletePromoHematById(String var1);

    public String getLastId();

    public boolean isPromoAktif(String var1);

    public List<PromoHemat> getPromoHemat(String var1, String var2);

    public int[] upsertPromoHematBatch(List<PromoHemat> var1);

    public int deletePromoHematByIds(String var1);

    public List<PromoHemat> getPromoHematAll();
}

