/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import minimart.engine.dao.PointUndiDao;
import minimart.engine.daoimpl.PointUndiDaoImpl;
import minimart.engine.model.PointUndi;

public class PointUndiService {
    private PointUndiDao pud = new PointUndiDaoImpl();

    public int createPointUndi(PointUndi pu) {
        return this.pud.createPointUndi(pu);
    }

    public int updatePointUndi(PointUndi pu) {
        return this.pud.updatePointUndi(pu);
    }

    public int deletePointUndi(int pointUndiId) {
        return this.pud.deletePointUndi(pointUndiId);
    }

    public PointUndi getPointUndi(int pointUndiId) {
        return this.pud.getPointUndi(pointUndiId);
    }
}

