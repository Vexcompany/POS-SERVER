/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Jenis;

public interface JenisDao {
    public int createJenis(Jenis var1);

    public Jenis getJenis(String var1);

    public List<Jenis> getJenisses(String var1);

    public int updateJenis(Jenis var1);

    public int updateJenis(String var1, int var2);

    public int deleteJenis(String var1);
}

