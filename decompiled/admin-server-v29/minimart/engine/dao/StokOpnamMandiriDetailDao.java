/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.StokOpnamMandiriDetail;

public interface StokOpnamMandiriDetailDao {
    public int[] createStokOpnamMandiriDetailBatch(List<StokOpnamMandiriDetail> var1);

    public int[] createStokOpnamMandiriDetailBatchIgnoreDuplicate(List<StokOpnamMandiriDetail> var1);

    public int[] updateStokOpnamMandiriDetailBatch(List<StokOpnamMandiriDetail> var1);

    public int updateStokOpnamMandiriDetail(StokOpnamMandiriDetail var1);

    public int updateRevisi1(int var1);

    public int updateRevisi2(int var1);

    public List<Object[]> getStokOpnamMandiriDetails(int var1, int var2, String var3);

    public StokOpnamMandiriDetail getStokOpnamMandiriDetail(long var1);

    public String getStokOpnamMandiriDetailOnProgress(String var1, int var2);

    public List<String> getBarangIdHiddenBySom(int var1);

    public long getSelisihRevisi1(int var1);

    public long getSelisihRevisi2(int var1);

    public long getSelisihRevisi3(int var1);

    public List<StokOpnamMandiriDetail> getStokOpnamMandiriDetailPastSixMonth();

    public List<StokOpnamMandiriDetail> getStokOpnamMandiriDetailByIdSOM(int var1);
}

