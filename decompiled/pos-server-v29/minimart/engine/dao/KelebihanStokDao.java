/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.KelebihanStok;

public interface KelebihanStokDao {
    public int createKelebihanStok(KelebihanStok var1);

    public List<Object[]> getKelebihanStok(String var1, String var2);
}

