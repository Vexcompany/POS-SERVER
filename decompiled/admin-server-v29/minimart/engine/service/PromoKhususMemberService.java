/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import minimart.engine.dao.PromoKhususMemberDao;
import minimart.engine.daoimpl.PromoKhususMemberDaoImpl;
import minimart.engine.model.PromoKhususMember;

public class PromoKhususMemberService {
    public PromoKhususMemberDao promoKhususMemberDao = new PromoKhususMemberDaoImpl();

    public int create(PromoKhususMember promoKhususMember) {
        return this.promoKhususMemberDao.create(promoKhususMember);
    }

    public int update(PromoKhususMember promoKhususMember) {
        return this.promoKhususMemberDao.update(promoKhususMember);
    }

    public PromoKhususMember get(String customerId) {
        return this.promoKhususMemberDao.get(customerId);
    }

    public PromoKhususMember getPromoById(String idPromo) {
        return this.promoKhususMemberDao.getPromoById(idPromo);
    }

    public String getLastId() {
        return this.promoKhususMemberDao.getLastId();
    }
}

