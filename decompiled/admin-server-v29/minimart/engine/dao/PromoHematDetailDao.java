/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoHematDetail;

public interface PromoHematDetailDao {
    public int createPromoHemat(PromoHematDetail var1);

    public List<PromoHematDetail> getPromoHematDetailByPromoHematId(String var1);

    public int deletePromoHematDetailByPromoHematId(String var1);

    public int updatePromoHematDetail(PromoHematDetail var1);

    public PromoHematDetail getPromoHematDetailByBarangId(String var1, String var2, float var3, String var4, String var5);

    public PromoHematDetail getPromoHematDetailByBarangId(String var1, String var2, String var3);

    public int[] upsertPromoHematDetailBatch(List<PromoHematDetail> var1);

    public int deletePromoHematDetailByIds(String var1);

    public List<PromoHematDetail> getPromoHematDetailAll();

    public List<Object[]> getAvailablePromo();
}

