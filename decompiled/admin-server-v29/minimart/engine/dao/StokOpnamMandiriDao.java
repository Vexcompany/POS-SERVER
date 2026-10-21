/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.StokOpnamMandiri;

public interface StokOpnamMandiriDao {
    public int[] createBatchStokOpnamMandiri(List<StokOpnamMandiri> var1);

    public int[] createBatchStokOpnamMandiriIgnoreDuplicate(List<StokOpnamMandiri> var1);

    public int updateStokOpnamMandiri(StokOpnamMandiri var1);

    public StokOpnamMandiri getStokOpnamMandiri(int var1);

    public List<StokOpnamMandiri> getStokOpnamMandiriUnSubmit();

    public List<StokOpnamMandiri> getSomByTanggal(String var1, String var2);

    public List<StokOpnamMandiri> getStokOpnamMandiriPastSixMonth();

    public List<StokOpnamMandiri> getStokOpnamMandiriPastWeek();

    public int deleteBatchStokOpnamMandiri(String var1);

    public String getTanggalInput(int var1, int var2);
}

