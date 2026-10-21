/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.OTPPengembalian;

public interface OTPPengembalianDao {
    public int requestOTPPengembalian(OTPPengembalian var1);

    public OTPPengembalian getOtpPengembalianByUniqueCode(String var1);

    public int updateStatusUsedOTPPengembalian(int var1);
}

