/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Karyawan;

public interface KaryawanOnlineDao {
    public List<Karyawan> getKaryawanOnline(String var1);

    public int updateKaryawanPassword(Karyawan var1);
}

