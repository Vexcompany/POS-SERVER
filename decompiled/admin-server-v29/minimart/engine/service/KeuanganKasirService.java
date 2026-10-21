/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import minimart.engine.dao.KeuanganKasirDao;
import minimart.engine.daoimpl.KeuanganKasirDaoImpl;
import minimart.engine.model.KeuanganKasir;

public class KeuanganKasirService {
    private KeuanganKasirDao keuanganKasirDao = new KeuanganKasirDaoImpl();

    public int createdKeuanganKasir(KeuanganKasir keuanganKasir) {
        return this.keuanganKasirDao.createdKeuanganKasir(keuanganKasir);
    }

    public int updateKeuanganKasir(KeuanganKasir keuanganKasir) {
        return this.keuanganKasirDao.updateKeuanganKasir(keuanganKasir);
    }

    public KeuanganKasir getKeuanganKasirs(int kasir) {
        return this.keuanganKasirDao.getKeuanganKasirs(kasir);
    }

    public long getKeuanganKasirByTanggal(String tanggal, int kasir) {
        return this.keuanganKasirDao.getKeuanganKasirByTanggal(tanggal, kasir);
    }
}

