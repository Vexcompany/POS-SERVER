/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.MutasiBarangDetail;

public interface MutasiBarangDetailDao {
    public int createMutasiBarangDetail(MutasiBarangDetail var1);

    public int updateMutasiBarangDetail(MutasiBarangDetail var1);

    public List<MutasiBarangDetail> getMutasiBarangDetails(String var1);

    public int deleteMutasiBarangDetail(long var1);

    public List<Object[]> getMutasiBarangDetails(String var1, String var2, String var3);
}

