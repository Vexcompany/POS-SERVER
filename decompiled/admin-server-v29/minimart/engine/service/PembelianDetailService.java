/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PembelianDetailDao;
import minimart.engine.daoimpl.PembelianDetailDaoImpl;
import minimart.engine.model.PembelianDetail;

public class PembelianDetailService {
    PembelianDetailDao pembelianDetailDao = new PembelianDetailDaoImpl();

    public int createPembelianDetail(PembelianDetail pembelianDetail) {
        return this.pembelianDetailDao.createPembelianDetail(pembelianDetail);
    }

    public int[] createPembelianDetailbatch(List<PembelianDetail> pembelianDetails) {
        return this.pembelianDetailDao.createPembelianDetailBatch(pembelianDetails);
    }

    public List<PembelianDetail> getPembelianDetails(String pembelianId) {
        return this.pembelianDetailDao.readPembelianDetails(pembelianId);
    }

    public PembelianDetail getPembelianDetail(String pembelianId, String barangId) {
        return this.pembelianDetailDao.readPembelianDetail(pembelianId, barangId);
    }

    public int updatePembelianDetail(PembelianDetail pembelianDetail) {
        return this.pembelianDetailDao.updatePembelianDetail(pembelianDetail);
    }

    public int deletePembelianDetail(String pembelianId) {
        return this.pembelianDetailDao.deletePembelianDetail(pembelianId);
    }

    public boolean isLastPembelian(String idBarang, String date) {
        return this.pembelianDetailDao.isLastPembelian(idBarang, date);
    }

    public PembelianDetail getPembelianTerakhir(String idBarang) {
        return this.pembelianDetailDao.getPembelianTerakhir(idBarang);
    }

    public List<Object[]> readPembelianDetailBySupplierBarangId(String startDate, String endDate, String supplierId, String barangId) {
        return this.pembelianDetailDao.readPembelianDetailBySupplierBarangId(startDate, endDate, supplierId, barangId);
    }
}

