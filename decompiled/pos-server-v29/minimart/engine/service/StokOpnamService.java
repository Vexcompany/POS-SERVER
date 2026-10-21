/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.StokOpnamDao;
import minimart.engine.daoimpl.StokOpnamDaoImpl;
import minimart.engine.model.StokOpnam;

public class StokOpnamService {
    private StokOpnamDao stokOpnamDao = new StokOpnamDaoImpl();

    public int createStokOpnam(StokOpnam stokOpnam) {
        return this.stokOpnamDao.createStokOpnam(stokOpnam);
    }

    public long getTotalOpnamById(int idStok) {
        return this.stokOpnamDao.getTotalOpnamById(idStok);
    }

    public StokOpnam getStokOpnam(String id) {
        return this.stokOpnamDao.getStokOpnam(id);
    }

    public List<StokOpnam> getStokOpnams(String statusOpnam, String startDate, String endDate) {
        return this.stokOpnamDao.getStokOpnams(statusOpnam, startDate, endDate);
    }

    public int updateStokOpnam(StokOpnam stokOpnam) {
        return this.stokOpnamDao.updateStokOpnam(stokOpnam);
    }

    public int deleteStokOpnam(int id) {
        return this.stokOpnamDao.deleteStokOpnam(id);
    }

    public int getLastId() {
        return this.stokOpnamDao.getLastId();
    }
}

