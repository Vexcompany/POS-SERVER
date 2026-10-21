/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PenukaranPointDao;
import minimart.engine.daoimpl.PenukaranPointDaoImpl;
import minimart.engine.model.PenukaranPoint;

public class PenukaranPointService {
    private PenukaranPointDao penukaranPointDao = new PenukaranPointDaoImpl();

    public int createPenukaranPoint(PenukaranPoint penukaranPoint) {
        return this.penukaranPointDao.createPenukaranPoint(penukaranPoint);
    }

    public List<PenukaranPoint> getPenukaranPoints(String startDate, String endDate, String customerId) {
        return this.penukaranPointDao.getPenukaranPoints(startDate, endDate, customerId);
    }

    public int getLastId() {
        return this.penukaranPointDao.getLastId();
    }

    public PenukaranPoint getPenukaranPoint(int id) {
        return this.penukaranPointDao.getPenukaranPoint(id);
    }
}

