/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import minimart.engine.dao.PromoVoucherDao;
import minimart.engine.daoimpl.PromoVoucherDaoImpl;
import minimart.engine.model.PromoVoucher;

public class PromoVoucherService {
    PromoVoucherDao dao = new PromoVoucherDaoImpl();

    public int createPromoVoucher(PromoVoucher promoVoucher) {
        return this.dao.createPromoVoucherDao(promoVoucher);
    }

    public int updatePromoVoucher(PromoVoucher promoVoucher) {
        return this.dao.updatePromoVoucherDao(promoVoucher);
    }

    public int deletePromoVoucherById(int id) {
        return this.dao.deletePromoVoucherById(id);
    }

    public PromoVoucher getPromoVoucherById(int promoVoucherId) {
        return this.dao.getPromoVoucherById(promoVoucherId);
    }
}

