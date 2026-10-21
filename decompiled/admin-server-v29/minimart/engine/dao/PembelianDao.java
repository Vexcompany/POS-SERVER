/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Pembelian;
import minimart.engine.model_online.PembelianO2O;

public interface PembelianDao {
    public int createPembelian(Pembelian var1);

    public int createPembelianWithPOId(Pembelian var1);

    public Pembelian getPembelian(String var1);

    public Pembelian getPembelianByStatus(String var1);

    public List<Object[]> getHutang(String var1);

    public List<Pembelian> getPembelianByDate(String var1, String var2, String var3, String var4, String var5);

    public List<Pembelian> getPembelian(String var1, String var2, String var3, String var4);

    public int updatePembelian(Pembelian var1);

    public int deletePembelian(Pembelian var1);

    public int deletePembelianForce(Pembelian var1);

    public String getLastId();

    public List<Object[]> getHutangSudahJatuhTempo(String var1);

    public List<PembelianO2O> getAllPembelianUnsynced();

    public int updateStatusSyncedPembelian(String var1);

    public int[] upsertDataPembelianDariOnline(PembelianO2O[] var1);

    public String getLatestDate();

    public int checkIdPoExist(int var1);
}

