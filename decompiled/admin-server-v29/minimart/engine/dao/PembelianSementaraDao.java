/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PembelianSementara;

public interface PembelianSementaraDao {
    public int createPembelianSementara(PembelianSementara var1);

    public int updatePembelianSementara(PembelianSementara var1);

    public int updatePembayaranPembelianSementara(String var1, String var2);

    public int deletePembelianSementara(String var1, String var2);

    public int deletePembelianSementaraByShortcut(String var1);

    public List<PembelianSementara> getPembelianSementaraByShortcut(String var1);

    public int updateSupplierPembelianSementara(String var1, String var2);
}

