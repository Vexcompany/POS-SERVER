/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.KoreksiStokDao;
import minimart.engine.daoimpl.KoreksiStokDaoImpl;
import minimart.engine.model.Barang;
import minimart.engine.model.KoreksiStok;

public class KoreksiStokService {
    private KoreksiStokDao barangDao = new KoreksiStokDaoImpl();

    public int createKoreksiStok(KoreksiStok koreksiStok) {
        return this.barangDao.createKoreksiStok(koreksiStok);
    }

    public List<KoreksiStok> getKoreksiStoks(String startDate, String endDate, Barang barang, String status) {
        return this.barangDao.getKoreksiStoks(startDate, endDate, barang, status);
    }
}

