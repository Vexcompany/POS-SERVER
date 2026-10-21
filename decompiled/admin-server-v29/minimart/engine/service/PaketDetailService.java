/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PaketDetailDao;
import minimart.engine.daoimpl.PaketDetailDaoImpl;
import minimart.engine.model.PaketDetail;

public class PaketDetailService {
    private PaketDetailDao dao = new PaketDetailDaoImpl();

    public int create(PaketDetail paketDetail) {
        return this.dao.create(paketDetail);
    }

    public int delete(String idPaket, String idBarang) {
        return this.dao.delete(idPaket, idBarang);
    }

    public List<PaketDetail> getPaketDetail(String paketID) {
        return this.dao.getPaketDetail(paketID);
    }

    public int[] upsertPaketDetailBatch(List<PaketDetail> datas) {
        return this.dao.upsertPaketDetailBatch(datas);
    }

    public int deletePaketDetailAll() {
        return this.dao.deletePaketDetailAll();
    }
}

