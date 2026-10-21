/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PointUndiDetailsDao;
import minimart.engine.daoimpl.PointUndiDetailsDaoImpl;
import minimart.engine.model.PointUndiDetail;

public class PointUndiDetailService {
    private PointUndiDetailsDao pudd = new PointUndiDetailsDaoImpl();

    public int createPointUndiDetails(PointUndiDetail pud) {
        return this.pudd.createPointUndiDetails(pud);
    }

    public int updatePointUndiDetails(PointUndiDetail pud) {
        return this.pudd.updatePointUndiDetails(pud);
    }

    public int deletePointUndiDetails(int pointUndiId) {
        return this.pudd.deletePointUndiDetails(pointUndiId);
    }

    public List<PointUndiDetail> getPointUndiDetails(int pointUndiId) {
        return this.pudd.getPointUndiDetails(pointUndiId);
    }

    public PointUndiDetail getPointUndiDetailByBarangId(String barangId) {
        return this.pudd.getPointUndiDetailByBarangId(barangId);
    }
}

