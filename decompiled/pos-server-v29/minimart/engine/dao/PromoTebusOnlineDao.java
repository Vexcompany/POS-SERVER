/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoTebus;
import minimart.engine.model.PromoTebusDetail;

public interface PromoTebusOnlineDao {
    public List<PromoTebus> getPromoTebusOnline(String var1);

    public List<PromoTebusDetail> getPromoTebusDetailOnlineByPromoTebusId(String var1);
}

