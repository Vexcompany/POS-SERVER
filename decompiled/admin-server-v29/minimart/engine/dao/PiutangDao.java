/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Piutang;

public interface PiutangDao {
    public int createPiutang(Piutang var1);

    public String getLastId();

    public List<Piutang> getPiutangByDate(String var1, String var2, String var3, String var4);
}

