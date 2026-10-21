/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Kasir;

public interface KasirDao {
    public int createKasir(Kasir var1);

    public boolean isKasirHasCreated(int var1);

    public List<Kasir> getKasirs();
}

