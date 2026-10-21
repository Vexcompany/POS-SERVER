/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PenjualanParsialDetail;

public interface PenjualanParsialDetailDao {
    public int create(PenjualanParsialDetail var1);

    public int update(PenjualanParsialDetail var1);

    public int delete(String var1);

    public List<PenjualanParsialDetail> getPenjualanParsials(String var1);
}

