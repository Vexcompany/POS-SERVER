/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.MutasiCabang;
import minimart.engine.model_online.PenjualanO2O;

public interface MutasiCabangDao {
    public long getTotalKeuntunganPenjualan(String var1);

    public int createPenjualan(MutasiCabang var1);

    public int[] createPenjualanBatch(List<MutasiCabang> var1);

    public MutasiCabang getPenjualan(String var1);

    public List<Object[]> getPiutangJatuhTempo();

    public List<Object[]> getPiutang(String var1);

    public List<Object[]> getPenjualans(String var1, String var2, String var3, String var4);

    public List<MutasiCabang> getPenjualansKredit(String var1, String var2, String var3);

    public List<Object[]> getRekapPenjualan(String var1, String var2, String var3);

    public List<Object[]> getRekapPenjualanByKeterangan(String var1, String var2, String var3, String var4);

    public List<Object[]> getPoinPenjualanCustomers(String var1, String var2);

    public List<MutasiCabang> getPenjualanTerakhir(String var1);

    public int updatePenjualan(MutasiCabang var1);

    public int[] updatePenjualanBatch(List<MutasiCabang> var1);

    public int deletePenjualan(String var1);

    public String getLastId();

    public long getTotalKreditCustomer(String var1);

    public List<MutasiCabang> getNotaJatuhTempo(String var1);

    public List<Object[]> getPenjualanSales(String var1, String var2, String var3);

    public List<Object[]> getPenjualansWithKeterangan(String var1, String var2, String var3, String var4);

    public List<Object[]> getTopCustomer(String var1, String var2, int var3);

    public List<PenjualanO2O> getAllPenjualanUnsyncedByKaryawan();

    public List<PenjualanO2O> getAllPenjualanUnsynced();

    public int[] updateStatusSyncedPenjualan(List<PenjualanO2O> var1);

    public int updateStatusSyncedPenjualan(String var1);

    public int[] upsertDataPenjualanDariOnline(PenjualanO2O[] var1);

    public String getLatestDate();

    public long getTotalOpenedShift(int var1, String var2);

    public long getTotalPenjualanByShift(int var1, String var2);

    public int updatePenjualanShift(int var1);

    public long getTotalVoucherShift(int var1, String var2);

    public long getTotalVoucherByIdShift(int var1, String var2);

    public List<Object[]> getPenjualansByShift(int var1);

    public List<Object[]> getPenjualansByShift(int var1, String var2);

    public List<Object[]> getPerformaPromo(String var1, String var2);

    public List<Object[]> getPerformaOutlet(String var1, String var2);
}

