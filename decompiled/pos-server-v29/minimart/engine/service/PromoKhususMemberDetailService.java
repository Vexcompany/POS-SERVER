/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PromoKhususMemberDetailDao;
import minimart.engine.daoimpl.PromoKhususMemberDetailDaoImpl;
import minimart.engine.model.PromoKhususMemberDetail;

public class PromoKhususMemberDetailService {
    PromoKhususMemberDetailDao dao = new PromoKhususMemberDetailDaoImpl();

    public int create(PromoKhususMemberDetail promoKhususMemberDetail) {
        return this.dao.create(promoKhususMemberDetail);
    }

    public int update(PromoKhususMemberDetail promoKhususMemberDetail) {
        return this.dao.update(promoKhususMemberDetail);
    }

    public int delete(String promoKhususMemberId) {
        return this.dao.delete(promoKhususMemberId);
    }

    public List<PromoKhususMemberDetail> getPromoKhususMemberDetail(String promoKhususMemberId) {
        return this.dao.getPromoKhususMemberDetail(promoKhususMemberId);
    }

    public PromoKhususMemberDetail getPromoKhususMemberDetail(String idBarang, String idCustomer) {
        return this.dao.getPromoKhususMemberDetail(idBarang, idCustomer);
    }
}

