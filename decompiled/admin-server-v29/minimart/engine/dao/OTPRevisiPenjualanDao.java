/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.OTPRevisiPenjualan;

public interface OTPRevisiPenjualanDao {
    public int requestOTPRevisiPenjualan(OTPRevisiPenjualan var1);

    public OTPRevisiPenjualan getOTPRevisiPenjualanByUniqueCode(String var1);

    public int updateStatusUsedOTPRevisiPenjualan(int var1);
}

