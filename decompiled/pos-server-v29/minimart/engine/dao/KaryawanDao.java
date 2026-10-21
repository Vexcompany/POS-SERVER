/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Karyawan;

public interface KaryawanDao {
    public int createKaryawan(Karyawan var1);

    public Karyawan getKaryawan(String var1);

    public Karyawan getAllKaryawan(String var1);

    public Karyawan getKaryawanByPassword(String var1);

    public List<Karyawan> getKaryawans(String var1);

    public int updateKaryawan(Karyawan var1);

    public int deleteKaryawan(String var1);

    public int[] upsertKaryawan(List<Karyawan> var1);

    public int softDeleteKaryawan(String var1);
}

