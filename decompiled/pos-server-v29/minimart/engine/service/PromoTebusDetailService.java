/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PromoTebusDetailDao;
import minimart.engine.daoimpl.PromoTebusDetailDaoImpl;
import minimart.engine.model.PromoTebusDetail;

public class PromoTebusDetailService {
    private PromoTebusDetailDao ptd = new PromoTebusDetailDaoImpl();

    public int createPromoTebusDetail(PromoTebusDetail promoTebusDetail) {
        return this.ptd.createPromoTebusDetail(promoTebusDetail);
    }

    public List<PromoTebusDetail> getPromoTebusDetailByPromoTebusId(String promoTebusId) {
        return this.ptd.getPromoTebusDetailByPromoTebusId(promoTebusId);
    }

    public int deletePromoTebusDetailByPromoTebusId(String promoTebusId) {
        return this.ptd.deletePromoTebusDetailByPromoTebusId(promoTebusId);
    }

    public int updatePromoTebusDetail(PromoTebusDetail promoTebusDetail) {
        return this.ptd.updatePromoTebusDetail(promoTebusDetail);
    }

    public List<PromoTebusDetail> getPromoTebusDetailByIdPromoBarang(String barangId, String satuanPromo) {
        return this.ptd.getPromoTebusDetailByIdPromoBarang(barangId, satuanPromo);
    }

    public PromoTebusDetail getPromoTebusDetailByBarangId(String barangId) {
        return this.ptd.getPromoTebusDetailByBarangId(barangId);
    }

    public PromoTebusDetail getPromoTebusDetailByBarangId(String barangId, String satuan, String startDate, String endDate) {
        return this.ptd.getPromoTebusDetailByBarangId(barangId, satuan, startDate, endDate);
    }

    public List<Object[]> getPromoTebusDetailByBarangId(String barangId, String satuan) {
        return this.ptd.getPromoTebusDetailByBarangId(barangId, satuan);
    }

    public PromoTebusDetail getPromoTebusDetailByBarangId2(String barangId) {
        return this.ptd.getPromoTebusDetailByBarangId2(barangId);
    }
}

