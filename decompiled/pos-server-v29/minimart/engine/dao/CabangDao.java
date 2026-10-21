/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Cabang;

public interface CabangDao {
    public Cabang getCabang(int var1);

    public List<Cabang> getCabang(String var1);
}

