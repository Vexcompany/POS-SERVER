/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Repack;
import minimart.engine.model.RepackDetail;

public interface RepackOnlineDao {
    public int createRepackOnline(Repack var1, String var2);

    public int createRepackDetailOnline(RepackDetail var1, String var2);

    public int[] createRepackDetailBatchOnline(List<RepackDetail> var1, String var2);
}

