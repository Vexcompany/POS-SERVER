/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.RepackDetail;

public interface RepackDetailDao {
    public int createdRepackDetail(RepackDetail var1);

    public int[] createRepackDetailBatch(List<RepackDetail> var1);

    public List<RepackDetail> getRepackDetail(String var1);

    public List<RepackDetail> getRepackDetail(String var1, String var2, String var3);
}

