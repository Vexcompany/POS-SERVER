/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.StokOpnamCSV;

public interface StokOpnamCSVDao {
    public int createStokOpnamCSV(StokOpnamCSV var1);

    public int deleteStokOpnamCSV();

    public List<Object[]> getStokOpnamCSVs();

    public int getJumlahStokCSVs(String var1);
}

