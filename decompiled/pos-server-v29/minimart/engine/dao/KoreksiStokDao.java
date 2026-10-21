/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Barang;
import minimart.engine.model.KoreksiStok;

public interface KoreksiStokDao {
    public int createKoreksiStok(KoreksiStok var1);

    public List<KoreksiStok> getKoreksiStoks(String var1, String var2, Barang var3, String var4);
}

