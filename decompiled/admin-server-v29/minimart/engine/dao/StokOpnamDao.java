/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.StokOpnam;
import minimart.engine.model_online.StokOpnamO2O;

public interface StokOpnamDao {
    public int createStokOpnam(StokOpnam var1);

    public long getTotalOpnamById(int var1);

    public StokOpnam getStokOpnam(String var1);

    public List<StokOpnam> getStokOpnams(String var1, String var2, String var3);

    public int updateStokOpnam(StokOpnam var1);

    public int deleteStokOpnam(int var1);

    public int getLastId();

    public List<StokOpnamO2O> getAllStokOpnamUnsynced();

    public int updateStokOpnamUnsynced(StokOpnamO2O[] var1);

    public int updateStokOpnamUnsynced(String var1);

    public int[] upsertDataStokOpnamDariOnline(StokOpnamO2O[] var1);

    public String getLatestDate();
}

