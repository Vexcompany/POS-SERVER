/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PenerimaanBsDetailDao;
import minimart.engine.daoimpl.PenerimaanBsDetailDaoImpl;
import minimart.engine.model.PenerimaanBsDetail;

public class PenerimaanBsDetailService {
    private PenerimaanBsDetailDao penerimaanBsDetailDao = new PenerimaanBsDetailDaoImpl();

    public int createPenerimaanBsDetail(PenerimaanBsDetail penerimaanBsDetail) {
        return this.penerimaanBsDetailDao.createPenerimaanBsDetail(penerimaanBsDetail);
    }

    public List<PenerimaanBsDetail> getPenerimaanBsDetailsByPenerimaanBsId(String penerimaanBsId) {
        return this.penerimaanBsDetailDao.getPenerimaanBsDetailsByPenerimaanBsId(penerimaanBsId);
    }

    public int updatePenerimaanBsDetail(PenerimaanBsDetail penerimaanBsDetail) {
        return this.penerimaanBsDetailDao.updatePenerimaanBsDetail(penerimaanBsDetail);
    }

    public int deletePenerimaanBsDetail(PenerimaanBsDetail penerimaanBsDetail) {
        return this.penerimaanBsDetailDao.deletePenerimaanBsDetail(penerimaanBsDetail);
    }
}

