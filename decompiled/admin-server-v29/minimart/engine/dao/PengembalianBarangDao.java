/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PengembalianBarang;
import minimart.engine.model_online.PengembalianBarangO2O;

public interface PengembalianBarangDao {
    public int createPengembalianBarang(PengembalianBarang var1);

    public int updatePengembalianBarang(PengembalianBarang var1);

    public int deletePengembalianBarangById(long var1);

    public PengembalianBarang getPengembalianBarangById(long var1);

    public long getLastId();

    public List<PengembalianBarang> getPengembalianBarang(String var1, String var2, String var3);

    public List<PengembalianBarang> getPengembalianBarang(String var1, String var2, String var3, int var4);

    public List<PengembalianBarangO2O> getAllPengembalianBarangUnsynced();

    public int updateStatusSyncedPengembalianBarang(String var1);

    public int[] upsertDataPengembalianBarangDariOnline(PengembalianBarangO2O[] var1);

    public String getLatestDate();

    public long getTotalPengembalianBarang(int var1, String var2);
}

