/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PengembalianBarangDetailDao;
import minimart.engine.daoimpl.PengembalianBarangDetailDaoImpl;
import minimart.engine.model.PengembalianBarangDetail;

public class PengembalianBarangDetailService {
    private PengembalianBarangDetailDao pengembalianBarangDetailDao = new PengembalianBarangDetailDaoImpl();

    public int createPengembalianBarangDetailDao(PengembalianBarangDetail pengembalianBarangDetail) {
        return this.pengembalianBarangDetailDao.createPengembalianBarangDetailDao(pengembalianBarangDetail);
    }

    public int updatePengembalianBarangDetailDao(PengembalianBarangDetail pengembalianBarangDetail) {
        return this.pengembalianBarangDetailDao.updatePengembalianBarangDetailDao(pengembalianBarangDetail);
    }

    public List<PengembalianBarangDetail> getPengembalianBarangDetails(String idPengembalianBarang) {
        return this.pengembalianBarangDetailDao.getPengembalianBarangDetails(idPengembalianBarang);
    }

    public int deletePengembalianBarangDetail(String idPengembalianBarangDetail) {
        return this.pengembalianBarangDetailDao.deletePengembalianBarangDetail(idPengembalianBarangDetail);
    }

    public List<Object[]> getPengembalianBarangDetail(String startDate, String endDate, String salesId) {
        return this.pengembalianBarangDetailDao.getPengembalianBarangDetail(startDate, endDate, salesId);
    }

    public PengembalianBarangDetail getPengembalianBarangDetailByPenjualan(String idPenjualan, String idBarang) {
        return this.pengembalianBarangDetailDao.getPengembalianBarangDetailByPenjualan(idPenjualan, idBarang);
    }
}

