/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.JenisDao;
import minimart.engine.daoimpl.JenisDaoImpl;
import minimart.engine.model.Jenis;

public class JenisService {
    JenisDao jenisDao = new JenisDaoImpl();

    public int createJenis(Jenis jenis) {
        return this.jenisDao.createJenis(jenis);
    }

    public Jenis getJenis(String jenis) {
        return this.jenisDao.getJenis(jenis);
    }

    public List<Jenis> getJenisses(String jenis) {
        return this.jenisDao.getJenisses(jenis);
    }

    public int updateJenis(Jenis jenis) {
        return this.jenisDao.updateJenis(jenis);
    }

    public int deleteJenis(String jenis) {
        return this.jenisDao.deleteJenis(jenis);
    }

    public int updateJenis(String jenis, int poinSales) {
        return this.jenisDao.updateJenis(jenis, poinSales);
    }
}

