/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PenjualanParsial;

public interface PenjualanParsialDao {
    public int create(PenjualanParsial var1);

    public int update(PenjualanParsial var1);

    public PenjualanParsial getPenjualanParsial(String var1);

    public int delete(String var1);

    public String getLastId();

    public List<PenjualanParsial> getPenjualanTerakhir(String var1);
}

