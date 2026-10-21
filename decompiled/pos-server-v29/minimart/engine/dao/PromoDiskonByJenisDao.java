/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.PromoDiskonByJenis;

public interface PromoDiskonByJenisDao {
    public int createPromoDiscJenis(PromoDiskonByJenis var1);

    public PromoDiskonByJenis getPromoByJenis(String var1);

    public int updatePromoDiscJenis(PromoDiskonByJenis var1);

    public int deletePromoDiscJenisById(String var1);

    public int getLastId();

    public boolean isPromoAktif(String var1);
}

