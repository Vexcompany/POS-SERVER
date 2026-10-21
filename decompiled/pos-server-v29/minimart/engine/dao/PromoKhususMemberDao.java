/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.PromoKhususMember;

public interface PromoKhususMemberDao {
    public int create(PromoKhususMember var1);

    public int update(PromoKhususMember var1);

    public PromoKhususMember get(String var1);

    public PromoKhususMember getPromoById(String var1);

    public String getLastId();
}

