/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import minimart.engine.dao.PromoPeriodeDao;
import minimart.engine.daoimpl.PromoPeriodeDaoImpl;
import minimart.engine.model.PromoPeriode;

public class PromoPeriodeService {
    private PromoPeriodeDao promoPeriodeDao = new PromoPeriodeDaoImpl();

    public int createPromoPeriode(PromoPeriode promoPeriode) {
        return this.promoPeriodeDao.createPromoPeriode(promoPeriode);
    }

    public boolean isNew() {
        return this.promoPeriodeDao.isNew();
    }

    public int updatePromoPeriode(PromoPeriode promoPeriode) {
        return this.promoPeriodeDao.updatePromoPeriode(promoPeriode);
    }

    public PromoPeriode getPromoPeriode(int id) {
        return this.promoPeriodeDao.getPromoPeriode(id);
    }
}

