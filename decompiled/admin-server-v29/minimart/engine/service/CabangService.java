/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.CabangDao;
import minimart.engine.daoimpl.CabangDaoImpl;
import minimart.engine.model.Cabang;

public class CabangService {
    private CabangDao cabangDao = new CabangDaoImpl();

    public Cabang getCabang(int idCabang) {
        return this.cabangDao.getCabang(idCabang);
    }

    public List<Cabang> getCabang(String namaCabang) {
        return this.cabangDao.getCabang(namaCabang);
    }
}

