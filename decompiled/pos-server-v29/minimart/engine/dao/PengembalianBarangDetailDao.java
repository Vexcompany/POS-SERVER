/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PengembalianBarangDetail;
import minimart.engine.model_online.PengembalianBarangDetailO2O;

public interface PengembalianBarangDetailDao {
    public int createPengembalianBarangDetailDao(PengembalianBarangDetail var1);

    public int updatePengembalianBarangDetailDao(PengembalianBarangDetail var1);

    public List<PengembalianBarangDetail> getPengembalianBarangDetails(String var1);

    public int deletePengembalianBarangDetail(String var1);

    public List<Object[]> getPengembalianBarangDetail(String var1, String var2, String var3);

    public List<PengembalianBarangDetailO2O> getAllPengembalianBarangDetailUnsynced();

    public int updateStatusSyncedPengembalianBarangDetail(String var1);

    public int[] upsertDataPengembalianBarangDetailDariOnline(PengembalianBarangDetailO2O[] var1);

    public PengembalianBarangDetail getPengembalianBarangDetailByPenjualan(String var1, String var2);
}

