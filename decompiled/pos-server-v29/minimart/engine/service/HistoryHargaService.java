/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.HistoryHargaDao;
import minimart.engine.daoimpl.HistoryHargaDaoImpl;
import minimart.engine.model.HistoryHarga;

public class HistoryHargaService {
    private HistoryHargaDao historyHargaDao = new HistoryHargaDaoImpl();

    public int createHistoryHarga(HistoryHarga historyHarga) {
        return this.historyHargaDao.createHistoryHarga(historyHarga);
    }

    public List<Object[]> getHistoryHarga(String startDate, String endDate, String idBarang) {
        return this.historyHargaDao.getHistoryHarga(startDate, endDate, idBarang);
    }

    public List<Object[]> getIdHistoryHarga(String startDate, String endDate) {
        return this.historyHargaDao.getIdHistoryHarga(startDate, endDate);
    }
}

