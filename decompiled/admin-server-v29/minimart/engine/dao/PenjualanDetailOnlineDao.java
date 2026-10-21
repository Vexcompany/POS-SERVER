/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.sql.SQLException;
import java.util.List;

public interface PenjualanDetailOnlineDao {
    public List<Long> getPenjualanDetailOnlineId(int var1, String var2, String var3) throws SQLException;

    public int deletePenjualanDetailOnline(String var1, String var2);

    public List<Long> getPenjualanDetailOnlineIdAll(int var1, String var2, String var3) throws SQLException;
}

