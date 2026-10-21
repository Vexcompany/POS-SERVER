/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PenerimaanBs;

public interface PenerimaanBsDao {
    public int createPenerimaanBs(PenerimaanBs var1);

    public int updatePenerimaanBs(PenerimaanBs var1);

    public PenerimaanBs getPenerimaanBs(String var1);

    public int deletePenerimaanBs(PenerimaanBs var1);

    public String getLastId();

    public List<PenerimaanBs> getPenerimaanBsByDate(String var1, String var2, String var3, String var4);
}

