/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoDiskonByJenisDetail;

public interface PromoDiskonByJenisDetailDao {
    public int createPromoDiscJenisDetail(PromoDiskonByJenisDetail var1);

    public List<PromoDiskonByJenisDetail> getPromoDiscJenisDetail(String var1);

    public int deletePromoDiscJenisDetail(String var1);

    public int updatePromoDiscJenisDetail(PromoDiskonByJenisDetail var1);

    public PromoDiskonByJenisDetail getPromoDiscJenisDetailByMJenis(String var1);
}

