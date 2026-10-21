/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.MutasiBarang;

public interface MutasiBarangDao {
    public int createMutasiBarang(MutasiBarang var1);

    public int updateMutasiBarang(MutasiBarang var1);

    public List<MutasiBarang> getPembelian(String var1, String var2);

    public MutasiBarang getMutasiBarang(String var1);

    public String getLastId();
}

