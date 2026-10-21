/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.HistoryHarga;

public interface HistoryHargaDao {
    public int createHistoryHarga(HistoryHarga var1);

    public List<Object[]> getHistoryHarga(String var1, String var2, String var3);

    public List<Object[]> getIdHistoryHarga(String var1, String var2);
}

