/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PenjualanSementara;

public interface PenjualanSementaraDao {
    public int createPenjualanSementara(PenjualanSementara var1);

    public long getTotalBelanja(String var1, int var2);

    public int[] createPenjualanSementaraBatch(List<PenjualanSementara> var1);

    public int updatePenjualanSementara(PenjualanSementara var1);

    public int deletePenjualanSementara(String var1, String var2, float var3, String var4);

    public int deletePenjualanSementara(String var1, String var2);

    public int deletePenjualanSementaraByShortcut(String var1);

    public int updateSalesPenjualanSementara(String var1, String var2);

    public List<Object[]> getPenjualanSementaraByShortcut2(String var1);

    public List<PenjualanSementara> getPenjualanSementaraByShortcut(String var1, String var2, String var3);

    public List<PenjualanSementara> getPenjualanSementaraByShortcutIdBarang(String var1, String var2, String var3);

    public List<PenjualanSementara> getPenjualanSementaraByShortcut(String var1);

    public int updateCustomerPenjualanSementara(String var1, String var2);

    public int updateKeteranganPenjualanSementara(String var1, String var2);

    public float cekJumlahByParentId(int var1, String var2, String var3, String var4);

    public int updateHargaPenjualanSementara(long var1, long var3, long var5, int var7, String var8, String var9, String var10, Float var11);

    public int updateHargaPenjualanSementara(long var1, long var3, int var5, String var6, String var7, String var8);

    public float getJumlahPenjualanSementara(int var1, String var2, String var3, String var4);

    public int getRecentTransaction(int var1);

    public List<Object[]> getPoinPerPenjualan(String var1);

    public int sumBarang(int var1, String var2, String var3, String var4);

    public int isVoid(int var1, String var2, String var3);

    public List<Object[]> getPenjualanSementaraByShortcut2OrderByName(String var1);
}

