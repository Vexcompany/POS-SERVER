/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.HargaBertingkatDao;
import minimart.engine.daoimpl.HargaBertingkatDaoImpl;
import minimart.engine.model.HargaBertingkat;

public class HargaBertingkatService {
    private HargaBertingkatDao hargaBertingkatDao = new HargaBertingkatDaoImpl();

    public int createHargaBertingkat(HargaBertingkat hargaBertingkat) {
        return this.hargaBertingkatDao.createHargaBertingkat(hargaBertingkat);
    }

    public int updateHargaBertingkat(HargaBertingkat hargaBertingkat) {
        return this.hargaBertingkatDao.updateHargaBertingkat(hargaBertingkat);
    }

    public int deleteHargaBertingkat(HargaBertingkat hargaBertingkat) {
        return this.hargaBertingkatDao.deleteHargaBertingkat(hargaBertingkat);
    }

    public List<HargaBertingkat> getHargaBertingkatsByBarangId(String barangId) {
        return this.hargaBertingkatDao.getHargaBertingkatsByBarangId(barangId);
    }
}

