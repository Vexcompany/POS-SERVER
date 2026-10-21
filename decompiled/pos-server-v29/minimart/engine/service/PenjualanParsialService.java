/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PenjualanParsialDao;
import minimart.engine.daoimpl.PenjualanParsialDaoImpl;
import minimart.engine.model.PenjualanParsial;

public class PenjualanParsialService {
    private PenjualanParsialDao penjualanParsialDao = new PenjualanParsialDaoImpl();

    public int create(PenjualanParsial penjualanParsial) {
        return this.penjualanParsialDao.create(penjualanParsial);
    }

    public int update(PenjualanParsial penjualanParsial) {
        return this.penjualanParsialDao.update(penjualanParsial);
    }

    public String getLastId() {
        return this.penjualanParsialDao.getLastId();
    }

    public PenjualanParsial getPenjualanParsial(String penjualanParsial) {
        return this.penjualanParsialDao.getPenjualanParsial(penjualanParsial);
    }

    public int delete(String penjualanParsialId) {
        return this.penjualanParsialDao.delete(penjualanParsialId);
    }

    public List<PenjualanParsial> getPenjualanTerakhir(String date) {
        return this.penjualanParsialDao.getPenjualanTerakhir(date);
    }
}

