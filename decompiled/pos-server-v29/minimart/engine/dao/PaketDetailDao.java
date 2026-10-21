/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PaketDetail;

public interface PaketDetailDao {
    public int create(PaketDetail var1);

    public int delete(String var1, String var2);

    public List<PaketDetail> getPaketDetail(String var1);

    public int[] upsertPaketDetailBatch(List<PaketDetail> var1);

    public int deletePaketDetailByIds(String var1);

    public int deletePaketDetailAll();

    public List<PaketDetail> getPaketDetailAll();
}

