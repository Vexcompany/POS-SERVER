/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Repack;

public interface RepackDao {
    public int createRepack(Repack var1);

    public Repack getRepack(String var1);

    public List<Object[]> getRepack(String var1, String var2);

    public String getLastId();
}

