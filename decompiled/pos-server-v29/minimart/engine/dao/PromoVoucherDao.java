/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.PromoVoucher;

public interface PromoVoucherDao {
    public int createPromoVoucherDao(PromoVoucher var1);

    public int updatePromoVoucherDao(PromoVoucher var1);

    public int deletePromoVoucherById(int var1);

    public PromoVoucher getPromoVoucherById(int var1);
}

