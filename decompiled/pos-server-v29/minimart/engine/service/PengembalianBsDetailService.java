/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PengembalianBsDetailDao;
import minimart.engine.daoimpl.PengembalianBsDetailDaoImpl;
import minimart.engine.model.PengembalianBsDetail;

public class PengembalianBsDetailService {
    private PengembalianBsDetailDao pengembalianBsDetailDao = new PengembalianBsDetailDaoImpl();

    public int createPengembalianBsDetail(PengembalianBsDetail pengembalianBsDetail) {
        return this.pengembalianBsDetailDao.createPengembalianBsDetail(pengembalianBsDetail);
    }

    public int updatePengembalianBsDetail(PengembalianBsDetail pengembalianBsDetail) {
        return this.pengembalianBsDetailDao.updatePengembalianBsDetail(pengembalianBsDetail);
    }

    public List<PengembalianBsDetail> getPengembalianBsDetails(String pengembalianBsId) {
        return this.pengembalianBsDetailDao.getPengembalianBsDetails(pengembalianBsId);
    }

    public int deletePengembalianBsDetail(PengembalianBsDetail pengembalianBsDetail) {
        return this.pengembalianBsDetailDao.deletePengembalianBsDetail(pengembalianBsDetail);
    }
}

