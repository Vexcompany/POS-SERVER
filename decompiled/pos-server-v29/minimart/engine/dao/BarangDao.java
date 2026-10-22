/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Barang;

public interface BarangDao {
    public int createBarang(Barang var1);

    public Barang getBarang(String var1);

    public Barang getBarangById(String var1);

    public List<Barang> getBsBarangs(String var1);

    public List<Barang> getBarangsMinToko(String var1);

    public List<Barang> getBarangs(String var1, String var2, boolean var3, String var4, boolean var5);

    public List<Barang> getBarangs(String var1);

    public List<Barang> getBarangsByJenisRakNama(String var1, String var2, String var3, String var4, boolean var5);

    public int updateBarang(Barang var1);

    public int updateBarangHpp(Barang var1);

    public List<Object[]> geDataLabelByJenis(String var1);

    public int updateBarang2(Barang var1, String var2);

    public int updateBarang2Hpp(Barang var1, String var2);

    public int deleteBarang(String var1);

    public String getLastId();

    public List<Barang> getBarangsUpdateHarga(int var1);

    public int updateStokBs(float var1, String var2);

    public int updateStokBarangToko(float var1, String var2);

    public int updateStokBarangGudang(float var1, String var2);

    public List<Barang> getBarangsByMultiRak(String var1, String var2);

    public List<Barang> getBarangsByRak(String var1);
}

