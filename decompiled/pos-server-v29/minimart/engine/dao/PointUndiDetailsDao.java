/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PointUndiDetail;

public interface PointUndiDetailsDao {
    public int createPointUndiDetails(PointUndiDetail var1);

    public int updatePointUndiDetails(PointUndiDetail var1);

    public int deletePointUndiDetails(int var1);

    public List<PointUndiDetail> getPointUndiDetails(int var1);

    public PointUndiDetail getPointUndiDetailByBarangId(String var1);
}

