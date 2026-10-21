/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.StokOpnamDetail;
import minimart.engine.model_online.StokOpnamDetailO2O;

public interface StokOpnamDetailDao {
    public int createStokOpnamDetail(StokOpnamDetail var1);

    public List<Object[]> getStokOpnamDetails(String var1);

    public int deleteStokOpnamDetailById(int var1);

    public int deleteStokOpnamDetailByPenjualanId(String var1);

    public List<StokOpnamDetail> getStokSelisihOpnamDetails(String var1);

    public int updateStokOpnamDetail(StokOpnamDetail var1);

    public List<StokOpnamDetail> getAllStokOpnamDetails();

    public List<Object[]> getStokOpnamDetails(String var1, String var2, String var3, String var4);

    public List<Object[]> getStokOpnamDetailsRekap(String var1, String var2, String var3, String var4);

    public List<StokOpnamDetailO2O> getStokOpnamDetailUnsynced();

    public int updateStokOpnamDetailUnsynced(String var1);

    public int[] createStokOpnamDetailBatch(List<StokOpnamDetail> var1);

    public int[] upsertDataStokOpnamDetailDariOnline(StokOpnamDetailO2O[] var1);
}

