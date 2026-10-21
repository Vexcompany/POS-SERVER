/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoHemat;
import minimart.engine.model.PromoHematDetail;

public interface PromoHematOnlineDao {
    public List<PromoHemat> getPromoHematOnline(String var1);

    public List<PromoHematDetail> getPromoHematDetailOnlineByPromoHematId(String var1);
}

