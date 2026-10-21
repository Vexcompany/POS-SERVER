/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.KeuanganKasir;

public interface KeuanganKasirDao {
    public int createdKeuanganKasir(KeuanganKasir var1);

    public int updateKeuanganKasir(KeuanganKasir var1);

    public KeuanganKasir getKeuanganKasirs(int var1);

    public long getKeuanganKasirByTanggal(String var1, int var2);
}

