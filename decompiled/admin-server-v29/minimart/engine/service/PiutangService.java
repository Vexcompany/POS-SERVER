/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PiutangDao;
import minimart.engine.daoimpl.PiutangDaoImpl;
import minimart.engine.model.Piutang;

public class PiutangService {
    private PiutangDao dao = new PiutangDaoImpl();

    public int createPiutang(Piutang piutang) {
        return this.dao.createPiutang(piutang);
    }

    public String getLastId() {
        return this.dao.getLastId();
    }

    public List<Piutang> getPiutangByDate(String startDate, String endDate, String tipe, String customerId) {
        return this.dao.getPiutangByDate(startDate, endDate, tipe, customerId);
    }
}

