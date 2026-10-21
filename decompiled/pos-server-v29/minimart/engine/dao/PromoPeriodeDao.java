/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.PromoPeriode;

public interface PromoPeriodeDao {
    public boolean isNew();

    public int createPromoPeriode(PromoPeriode var1);

    public int updatePromoPeriode(PromoPeriode var1);

    public PromoPeriode getPromoPeriode(int var1);
}

