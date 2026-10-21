/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.ParentBarang;

public interface ParentBarangDao {
    public int createParent(ParentBarang var1);

    public int update(ParentBarang var1);

    public List<ParentBarang> getParents(String var1);

    public ParentBarang getParentBarang(String var1);
}

