/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.MutasiBarangDao;
import minimart.engine.daoimpl.MutasiBarangDaoImpl;
import minimart.engine.model.MutasiBarang;

public class MutasiBarangService {
    private MutasiBarangDao mutasiBarangDao = new MutasiBarangDaoImpl();

    public int createMutasiBarang(MutasiBarang mutasiBarang) {
        return this.mutasiBarangDao.createMutasiBarang(mutasiBarang);
    }

    public int updateMutasiBarang(MutasiBarang mutasiBarang) {
        return this.mutasiBarangDao.updateMutasiBarang(mutasiBarang);
    }

    public List<MutasiBarang> getPembelian(String startDate, String endDate) {
        return this.mutasiBarangDao.getPembelian(startDate, endDate);
    }

    public MutasiBarang getMutasiBarang(String id) {
        return this.mutasiBarangDao.getMutasiBarang(id);
    }

    public String getLastId() {
        return this.mutasiBarangDao.getLastId();
    }
}

