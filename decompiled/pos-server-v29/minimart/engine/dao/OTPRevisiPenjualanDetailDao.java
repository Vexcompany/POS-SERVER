/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.OTPRevisiPenjualanDetail;

public interface OTPRevisiPenjualanDetailDao {
    public List<OTPRevisiPenjualanDetail> getOTPRevisiPenjualanDetailById(int var1);

    public int[] insertOTPRevisiPenjualanDetail(List<OTPRevisiPenjualanDetail> var1);
}

