/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import minimart.engine.dao.BonusDao;
import minimart.engine.daoimpl.BonusDaoImpl;
import minimart.engine.model.Bonus;

public class BonusService {
    BonusDao bonusDao = new BonusDaoImpl();

    public int createdBonus(Bonus bonus) {
        return this.bonusDao.createdBonus(bonus);
    }
}

