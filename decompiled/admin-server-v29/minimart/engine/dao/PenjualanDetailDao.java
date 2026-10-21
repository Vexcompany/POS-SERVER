/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.sql.SQLException;
import java.util.List;
import minimart.engine.model.Barang;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.model_online.PenjualanDetailO2O;

public interface PenjualanDetailDao {
    public int createdPenjualanDetail(PenjualanDetail var1);

    public int[] createdPenjualanDetailBatch(List<PenjualanDetail> var1);

    public PenjualanDetail readPenjualanDetailById(String var1);

    public List<PenjualanDetail> readPenjualanDetailByPenjualanId(String var1);

    public List<Object[]> readPenjualanDetailByCustomerBarangId(String var1, String var2, String var3, String var4);

    public PenjualanDetail getPenjualanTerakhir(String var1, String var2);

    public PenjualanDetail getPenjualanDetail(String var1, String var2);

    public int updatePenjualanDetail(PenjualanDetail var1);

    public int deletePenjualanDetail(PenjualanDetail var1);

    public int deletePenjualanDetailByPenjualanId(String var1);

    public List<Object[]> getTotalKeuntunganByBarang(String var1, String var2, Barang var3, String var4);

    public List<Object[]> getTotalKeuntunganByCustomer(String var1, String var2, int var3);

    public List<Object[]> getBestSeller(String var1, String var2, String var3, int var4);

    public List<Object[]> getTotalKeuntunganByJenisBarang(String var1, String var2, String var3);

    public List<Object[]> getBestSellerByCustomer(String var1, String var2, String var3, int var4);

    public List<Object[]> getBestSellerByCustomer(String var1, int var2);

    public List<PenjualanDetailO2O> getAllPenjualanDetailUnsynced();

    public int[] updateStatusSyncedPenjualanDetail(List<PenjualanDetailO2O> var1);

    public int[] updateStatusSyncedPenjualanDetail(PenjualanDetailO2O[] var1);

    public int[] upsertDataPenjualanDetailDariOnline(PenjualanDetailO2O[] var1);

    public int insertPenjualanDetailDeleted(long var1);

    public int insertPenjualanDetailDeletedByPenjualanId(String var1);

    public List<Long> getPenjualanDetailIdByDate(String var1, String var2) throws SQLException;

    public List<Long> getPenjualanDetailDeletedUnsync() throws SQLException;

    public int updateStatusSyncPenjualanDetailDeleted();

    public int getPenjualanMemberByCustomer(String var1);

    public int sumBarang(String var1, String var2, String var3);

    public List<Object[]> readPenjualanDetailByPenjualanId2(String var1);

    public int jumlahPromoByTanggal(String var1, String var2, String var3);

    public long totalPromoByTanggal(String var1, String var2, String var3);

    public List<Object[]> getReportPromo(String var1, String var2, String var3);
}

