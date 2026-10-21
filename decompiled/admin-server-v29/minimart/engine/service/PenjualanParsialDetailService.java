/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PenjualanParsialDetailDao;
import minimart.engine.daoimpl.PenjualanParsialDetailDaoImpl;
import minimart.engine.model.PenjualanParsialDetail;

public class PenjualanParsialDetailService {
    private PenjualanParsialDetailDao penjualanParsialDetailDao = new PenjualanParsialDetailDaoImpl();

    public int create(PenjualanParsialDetail penjualanParsialDetail) {
        return this.penjualanParsialDetailDao.create(penjualanParsialDetail);
    }

    public int update(PenjualanParsialDetail penjualanParsialDetail) {
        return this.penjualanParsialDetailDao.update(penjualanParsialDetail);
    }

    public int delete(String penjualanParsialId) {
        return this.penjualanParsialDetailDao.delete(penjualanParsialId);
    }

    public List<PenjualanParsialDetail> getPenjualanParsials(String penjualanParsialId) {
        return this.penjualanParsialDetailDao.getPenjualanParsials(penjualanParsialId);
    }
}

