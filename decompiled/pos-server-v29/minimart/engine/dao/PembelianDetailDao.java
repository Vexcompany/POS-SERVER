/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PembelianDetail;
import minimart.engine.model_online.PembelianDetailO2O;

public interface PembelianDetailDao {
    public int createPembelianDetail(PembelianDetail var1);

    public int[] createPembelianDetailBatch(List<PembelianDetail> var1);

    public List<PembelianDetail> readPembelianDetails(String var1);

    public PembelianDetail readPembelianDetail(String var1, String var2);

    public int updatePembelianDetail(PembelianDetail var1);

    public int deletePembelianDetail(String var1);

    public String getLastId();

    public boolean isLastPembelian(String var1, String var2);

    public PembelianDetail getPembelianTerakhir(String var1);

    public List<Object[]> readPembelianDetailBySupplierBarangId(String var1, String var2, String var3, String var4);

    public List<PembelianDetailO2O> getAllPembelianDetailUnsynced();

    public int updateStatusSyncedPembelianDetail(String var1);

    public int[] upsertDataPembelianDetailDariOnline(PembelianDetailO2O[] var1);
}

