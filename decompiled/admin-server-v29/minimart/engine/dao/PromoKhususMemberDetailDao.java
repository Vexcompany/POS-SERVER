/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoKhususMemberDetail;

public interface PromoKhususMemberDetailDao {
    public int create(PromoKhususMemberDetail var1);

    public int update(PromoKhususMemberDetail var1);

    public int delete(String var1);

    public List<PromoKhususMemberDetail> getPromoKhususMemberDetail(String var1);

    public PromoKhususMemberDetail getPromoKhususMemberDetail(String var1, String var2);
}

