/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PromoVoucherDetail;

public interface PromoVoucherDetailDao {
    public int createPromoVoucherDetail(PromoVoucherDetail var1);

    public int updatePromoVoucherDetail(PromoVoucherDetail var1);

    public int deletePromoVoucherDetailByPromoVoucherId(int var1);

    public List<PromoVoucherDetail> getPromoVoucherDetailByPromoVoucherId(int var1);

    public PromoVoucherDetail getPromoVoucherByBarangId(String var1);

    public List<PromoVoucherDetail> getPromoVoucherDetailByNamaBarang(String var1);
}

