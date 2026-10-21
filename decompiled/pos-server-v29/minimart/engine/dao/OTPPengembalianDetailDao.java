/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.OTPPengembalianDetail;

public interface OTPPengembalianDetailDao {
    public List<OTPPengembalianDetail> getOTPPengembalianDetailById(int var1);

    public int[] insertOTPPengembalianDetail(List<OTPPengembalianDetail> var1);
}

