/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.StokOpnamMandiri;
import minimart.engine.model.StokOpnamMandiriDetail;

public interface StokOpnamMandiriOnlineDao {
    public List<StokOpnamMandiri> getStokOpnamMandiriOnline(String var1);

    public List<StokOpnamMandiri> getStokOpnamMandiriOnlineDeleted(String var1);

    public List<StokOpnamMandiriDetail> getStokOpnamMandiriDetailOnline(String var1);

    public int updateStokOpnamMandiri(StokOpnamMandiri var1);

    public int[] createBatchStokOpnamMandiriDetailDuplicateUpdate(List<StokOpnamMandiriDetail> var1);
}

