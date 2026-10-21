/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import minimart.engine.dao.PengembalianBsDao;
import minimart.engine.daoimpl.PengembalianBsDaoImpl;
import minimart.engine.model.PengembalianBs;

public class PengembalianBsService {
    private PengembalianBsDao bsDao = new PengembalianBsDaoImpl();

    public int createPengembalianBs(PengembalianBs pengembalianBs) {
        return this.bsDao.createPengembalianBs(pengembalianBs);
    }

    public int updatePengembalianBs(PengembalianBs pengembalianBs) {
        return this.bsDao.updatePengembalianBs(pengembalianBs);
    }

    public PengembalianBs getPengembalianBs(String id) {
        return this.bsDao.getPengembalianBs(id);
    }

    public int deletePengembalianBs(PengembalianBs pengembalianBs) {
        return this.bsDao.deletePengembalianBs(pengembalianBs);
    }

    public String getLastId() {
        return this.bsDao.getLastId();
    }
}

