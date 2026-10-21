/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.KasirDao;
import minimart.engine.daoimpl.KasirDaoImpl;
import minimart.engine.model.Kasir;

public class KasirService {
    private KasirDao kasirDao = new KasirDaoImpl();

    public boolean isKasirHasCreated(int id) {
        return this.kasirDao.isKasirHasCreated(id);
    }

    public int createKasir(Kasir kasir) {
        return this.kasirDao.createKasir(kasir);
    }

    public List<Kasir> getKasirs() {
        return this.kasirDao.getKasirs();
    }
}

