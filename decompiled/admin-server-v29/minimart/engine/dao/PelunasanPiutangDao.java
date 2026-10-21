/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PelunasanPiutang;
import minimart.engine.model_online.PelunasanPiutangO2O;

public interface PelunasanPiutangDao {
    public int createPelunasanPiutang(PelunasanPiutang var1);

    public int[] createPelunasanPiutangBatch(List<PelunasanPiutang> var1);

    public int updatePelunasanPiutang(PelunasanPiutang var1);

    public List<Object[]> getPelunasanPiutang(String var1, String var2, String var3);

    public int updateStatusSynced(List<PelunasanPiutangO2O> var1);

    public int updateStatusSynced(PelunasanPiutangO2O[] var1);

    public List<PelunasanPiutangO2O> getAllPelunasanPiutangUnsynced();

    public int[] upsertDataPelunasanPiutangDariOnline(PelunasanPiutangO2O[] var1);

    public String getLatestDate();

    public long getTotalPelunasanPiutangShift(int var1, String var2);

    public List<Object[]> getPelunasanPiutangByShift(int var1);

    public List<Object[]> getPelunasanPiutangByShift(int var1, String var2);
}

