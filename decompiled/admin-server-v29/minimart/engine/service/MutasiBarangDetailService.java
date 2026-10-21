/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.MutasiBarangDetailDao;
import minimart.engine.daoimpl.MutasiBarangDetailDaoImpl;
import minimart.engine.model.MutasiBarangDetail;

public class MutasiBarangDetailService {
    private MutasiBarangDetailDao mutasiBarangDetailDao = new MutasiBarangDetailDaoImpl();

    public int createMutasiBarangDetail(MutasiBarangDetail mutasiBarangDetail) {
        return this.mutasiBarangDetailDao.createMutasiBarangDetail(mutasiBarangDetail);
    }

    public int updateMutasiBarangDetail(MutasiBarangDetail mutasiBarangDetail) {
        return this.mutasiBarangDetailDao.updateMutasiBarangDetail(mutasiBarangDetail);
    }

    public List<MutasiBarangDetail> getMutasiBarangDetails(String mutasiBarangId) {
        return this.mutasiBarangDetailDao.getMutasiBarangDetails(mutasiBarangId);
    }

    public int deleteMutasiBarangDetail(long id) {
        return this.mutasiBarangDetailDao.deleteMutasiBarangDetail(id);
    }

    public List<Object[]> getMutasiBarangDetails(String idBarang, String startDate, String endDate) {
        return this.mutasiBarangDetailDao.getMutasiBarangDetails(idBarang, startDate, endDate);
    }
}

