/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoTebusDetail;

public interface PromoTebusDetailDao {
    public int createPromoTebusDetail(PromoTebusDetail var1);

    public List<PromoTebusDetail> getPromoTebusDetailByPromoTebusId(String var1);

    public int deletePromoTebusDetailByPromoTebusId(String var1);

    public int updatePromoTebusDetail(PromoTebusDetail var1);

    public List<PromoTebusDetail> getPromoTebusDetailByIdPromoBarang(String var1, String var2);

    public PromoTebusDetail getPromoTebusDetailByBarangId(String var1);

    public PromoTebusDetail getPromoTebusDetailByBarangId(String var1, String var2, String var3, String var4);

    public int[] upsertPromoTebusDetailBatch(List<PromoTebusDetail> var1);

    public int deletePromoTebusDetailByIds(String var1);

    public List<PromoTebusDetail> getPromoTebusDetailAll();

    public List<Object[]> getPromoTebusDetailByBarangId(String var1, String var2);

    public PromoTebusDetail getPromoTebusDetailByBarangId2(String var1);
}

