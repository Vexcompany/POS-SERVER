/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PelunasanHutang;
import minimart.engine.model_online.PelunasanHutangO2O;

public interface PelunasanHutangDao {
    public int createPelunasanHutang(PelunasanHutang var1);

    public int updatePelunasanHutang(PelunasanHutang var1);

    public List<PelunasanHutang> getPelunasanHutang(String var1, String var2, String var3, String var4);

    public int updateStatusSynced(List<PelunasanHutangO2O> var1);

    public int updateStatusSynced(PelunasanHutangO2O[] var1);

    public List<PelunasanHutangO2O> getAllPelunasanHutangUnsynced();

    public int[] upsertDataPelunasanHutangDariOnline(PelunasanHutangO2O[] var1);

    public String getLatestDate();
}

