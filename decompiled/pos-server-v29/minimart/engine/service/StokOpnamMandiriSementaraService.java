/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.StokOpnamMandiriSementaraDao;
import minimart.engine.daoimpl.StokOpnamMandiriSementaraDaoImpl;

public class StokOpnamMandiriSementaraService {
    private StokOpnamMandiriSementaraDao dao = new StokOpnamMandiriSementaraDaoImpl();

    public List<Object[]> geDetailBySomId(int somId) {
        return this.dao.geDetailBySomId(somId);
    }
}

