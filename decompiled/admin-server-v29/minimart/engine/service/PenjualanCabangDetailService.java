/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PenjualanDetailDao;
import minimart.engine.daoimpl.PenjualanCabangDetailDaoImpl;
import minimart.engine.model.Barang;
import minimart.engine.model.PenjualanDetail;

public class PenjualanCabangDetailService {
    private PenjualanDetailDao dao = new PenjualanCabangDetailDaoImpl();

    public int createdPenjualanDetail(PenjualanDetail penjualanDetail) {
        return this.dao.createdPenjualanDetail(penjualanDetail);
    }

    public PenjualanDetail readPenjualanDetailById(String penjualanDetailId) {
        return this.dao.readPenjualanDetailById(penjualanDetailId);
    }

    public List<PenjualanDetail> readPenjualanDetailByPenjualanId(String penjualanId) {
        return this.dao.readPenjualanDetailByPenjualanId(penjualanId);
    }

    public List<Object[]> readPenjualanDetailByCustomerBarangId(String startDate, String endDate, String customerId, String barangId) {
        return this.dao.readPenjualanDetailByCustomerBarangId(startDate, endDate, customerId, barangId);
    }

    public PenjualanDetail getPenjualanTerakhir(String customerId, String barangId) {
        return this.dao.getPenjualanTerakhir(customerId, barangId);
    }

    public int updatePenjualanDetail(PenjualanDetail penjualanDetail) {
        return this.dao.updatePenjualanDetail(penjualanDetail);
    }

    public int deletePenjualanDetail(PenjualanDetail penjualanDetail) {
        return this.dao.deletePenjualanDetail(penjualanDetail);
    }

    public int deletePenjualanDetailByPenjualanId(String penjualanId) {
        return this.dao.deletePenjualanDetailByPenjualanId(penjualanId);
    }

    public List<Object[]> getTotalKeuntunganByBarang(String startDate, String endDate, Barang barang, String statusProgram) {
        return this.dao.getTotalKeuntunganByBarang(startDate, endDate, barang, statusProgram);
    }

    public List<Object[]> getTotalKeuntunganByCustomer(String startDate, String endDate, int limit) {
        return this.dao.getTotalKeuntunganByCustomer(startDate, endDate, limit);
    }

    public List<Object[]> getBestSeller(String startDate, String endDate, String jenis, int limit) {
        return this.dao.getBestSeller(startDate, endDate, jenis, limit);
    }
}

