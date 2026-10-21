/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.PengembalianBs;

public interface PengembalianBsDao {
    public int createPengembalianBs(PengembalianBs var1);

    public int updatePengembalianBs(PengembalianBs var1);

    public PengembalianBs getPengembalianBs(String var1);

    public int deletePengembalianBs(PengembalianBs var1);

    public String getLastId();
}

