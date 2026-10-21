/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PromoVoucherDetailDao;
import minimart.engine.daoimpl.PromoVoucherDetailDaoImpl;
import minimart.engine.model.PromoVoucherDetail;

public class PromoVoucherDetailService {
    PromoVoucherDetailDao dao = new PromoVoucherDetailDaoImpl();

    public int createPromoVoucherDetail(PromoVoucherDetail promoVoucherDetail) {
        return this.dao.createPromoVoucherDetail(promoVoucherDetail);
    }

    public int updatePromoVoucherDetail(PromoVoucherDetail promoVoucherDetail) {
        return this.dao.updatePromoVoucherDetail(promoVoucherDetail);
    }

    public int deletePromoVoucherDetailByPromoVoucherId(int promoVoucherId) {
        return this.dao.deletePromoVoucherDetailByPromoVoucherId(promoVoucherId);
    }

    public List<PromoVoucherDetail> getPromoVoucherDetailByPromoVoucherId(int promoVoucherId) {
        return this.dao.getPromoVoucherDetailByPromoVoucherId(promoVoucherId);
    }

    public PromoVoucherDetail getPromoVoucherByBarangId(String barangId) {
        return this.dao.getPromoVoucherByBarangId(barangId);
    }

    public List<PromoVoucherDetail> getPromoVoucherDetailByNamaBarang(String namaBarang) {
        return this.dao.getPromoVoucherDetailByNamaBarang(namaBarang);
    }
}

