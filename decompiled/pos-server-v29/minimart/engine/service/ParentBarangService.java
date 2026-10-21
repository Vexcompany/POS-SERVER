/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.ParentBarangDao;
import minimart.engine.daoimpl.ParentBarangDaoImpl;
import minimart.engine.model.ParentBarang;

public class ParentBarangService {
    private ParentBarangDao parentBarangDao = new ParentBarangDaoImpl();

    public int createParent(ParentBarang parentBarang) {
        return this.parentBarangDao.createParent(parentBarang);
    }

    public int update(ParentBarang parentBarang) {
        return this.parentBarangDao.update(parentBarang);
    }

    public List<ParentBarang> getParents(String nama) {
        return this.parentBarangDao.getParents(nama);
    }

    public ParentBarang getParentBarang(String id) {
        return this.parentBarangDao.getParentBarang(id);
    }
}

