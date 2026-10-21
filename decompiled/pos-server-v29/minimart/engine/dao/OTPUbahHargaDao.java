/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.OTPUbahHarga;

public interface OTPUbahHargaDao {
    public int requestOTPUbahHarga(OTPUbahHarga var1);

    public OTPUbahHarga getOTPUbahHargaByUniqueCode(String var1);

    public int updateUsedOTPUbahHarga(long var1);
}

