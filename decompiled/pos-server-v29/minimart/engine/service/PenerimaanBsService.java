/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PenerimaanBsDao;
import minimart.engine.daoimpl.PenerimaanBsDaoImpl;
import minimart.engine.model.PenerimaanBs;

public class PenerimaanBsService {
    private PenerimaanBsDao penerimaanBsDao = new PenerimaanBsDaoImpl();

    public int createPenerimaanBs(PenerimaanBs penerimaanBs) {
        return this.penerimaanBsDao.createPenerimaanBs(penerimaanBs);
    }

    public int updatePenerimaanBs(PenerimaanBs penerimaanBs) {
        return this.penerimaanBsDao.updatePenerimaanBs(penerimaanBs);
    }

    public PenerimaanBs getPenerimaanBs(String id) {
        return this.penerimaanBsDao.getPenerimaanBs(id);
    }

    public int deletePenerimaanBs(PenerimaanBs penerimaanBs) {
        return this.penerimaanBsDao.deletePenerimaanBs(penerimaanBs);
    }

    public String getLastId() {
        return this.penerimaanBsDao.getLastId();
    }

    public List<PenerimaanBs> getPenerimaanBsByDate(String startDate, String endDate, String lokasi, String status) {
        return this.penerimaanBsDao.getPenerimaanBsByDate(startDate, endDate, lokasi, status);
    }
}

