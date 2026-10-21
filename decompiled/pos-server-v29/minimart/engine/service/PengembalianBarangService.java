/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PengembalianBarangDao;
import minimart.engine.daoimpl.PengembalianBarangDaoImpl;
import minimart.engine.model.PengembalianBarang;

public class PengembalianBarangService {
    private PengembalianBarangDao pengembalianBarangDao = new PengembalianBarangDaoImpl();

    public int createPengembalianBarang(PengembalianBarang pengembalianBarang) {
        return this.pengembalianBarangDao.createPengembalianBarang(pengembalianBarang);
    }

    public int updatePengembalianBarang(PengembalianBarang pengembalianBarang) {
        return this.pengembalianBarangDao.updatePengembalianBarang(pengembalianBarang);
    }

    public int deletePengembalianBarangById(long idPengembalianBarang) {
        return this.pengembalianBarangDao.deletePengembalianBarangById(idPengembalianBarang);
    }

    public PengembalianBarang getPengembalianBarangById(long idPengembalianBarang) {
        return this.pengembalianBarangDao.getPengembalianBarangById(idPengembalianBarang);
    }

    public Long getLastId() {
        return this.pengembalianBarangDao.getLastId();
    }

    public List<PengembalianBarang> getPengembalianBarang(String startDate, String endDate, String idCustomer) {
        return this.pengembalianBarangDao.getPengembalianBarang(startDate, endDate, idCustomer);
    }

    public List<PengembalianBarang> getPengembalianBarang(String startDate, String endDate, String idCustomer, int cetakVoucher) {
        return this.pengembalianBarangDao.getPengembalianBarang(startDate, endDate, idCustomer, cetakVoucher);
    }

    public long getTotalPengembalianBarang(int shiftKasirId, String pelunasan) {
        return this.pengembalianBarangDao.getTotalPengembalianBarang(shiftKasirId, pelunasan);
    }
}

