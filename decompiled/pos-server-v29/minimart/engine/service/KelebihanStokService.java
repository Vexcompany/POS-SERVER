/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.KelebihanStokDao;
import minimart.engine.daoimpl.KelebihanStokDaoImpl;
import minimart.engine.model.KelebihanStok;

public class KelebihanStokService {
    private KelebihanStokDao kelebihanStokDao = new KelebihanStokDaoImpl();

    public int createKelebihanStok(KelebihanStok kelebihanStok) {
        return this.kelebihanStokDao.createKelebihanStok(kelebihanStok);
    }

    public List<Object[]> getKelebihanStok(String startDate, String endDate) {
        return this.kelebihanStokDao.getKelebihanStok(startDate, endDate);
    }
}

