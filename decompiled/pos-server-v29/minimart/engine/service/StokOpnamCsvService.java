/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.StokOpnamCSVDao;
import minimart.engine.daoimpl.StokOpamCSVDaoImpl;
import minimart.engine.model.StokOpnamCSV;

public class StokOpnamCsvService {
    private StokOpnamCSVDao dao = new StokOpamCSVDaoImpl();

    public int createStokOpnamCSV(StokOpnamCSV cSV) {
        return this.dao.createStokOpnamCSV(cSV);
    }

    public int deleteStokOpnamCSV() {
        return this.dao.deleteStokOpnamCSV();
    }

    public List<Object[]> getStokOpnamCSVs() {
        return this.dao.getStokOpnamCSVs();
    }

    public int getJumlahStokCSVs(String m_barang_id) {
        return this.dao.getJumlahStokCSVs(m_barang_id);
    }
}

