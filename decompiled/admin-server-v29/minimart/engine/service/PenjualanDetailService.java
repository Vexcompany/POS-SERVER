/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PenjualanDetailDao;
import minimart.engine.daoimpl.PenjualanDetailDaoImpl;
import minimart.engine.model.Barang;
import minimart.engine.model.PenjualanDetail;

public class PenjualanDetailService {
    private PenjualanDetailDao dao = new PenjualanDetailDaoImpl();

    public int createdPenjualanDetail(PenjualanDetail penjualanDetail) {
        return this.dao.createdPenjualanDetail(penjualanDetail);
    }

    public int[] createdPenjualanDetailBatch(List<PenjualanDetail> penjualanDetail) {
        return this.dao.createdPenjualanDetailBatch(penjualanDetail);
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

    public PenjualanDetail getPenjualanDetail(String idPenjualan, String barangId) {
        return this.dao.getPenjualanDetail(idPenjualan, barangId);
    }

    public int updatePenjualanDetail(PenjualanDetail penjualanDetail) {
        return this.dao.updatePenjualanDetail(penjualanDetail);
    }

    public int deletePenjualanDetail(PenjualanDetail penjualanDetail) {
        int result = 0;
        result = this.dao.insertPenjualanDetailDeleted(penjualanDetail.getId());
        if (result >= 1) {
            return this.dao.deletePenjualanDetail(penjualanDetail);
        }
        return result;
    }

    public int deletePenjualanDetailByPenjualanId(String penjualanId) {
        int result = 0;
        result = this.dao.insertPenjualanDetailDeletedByPenjualanId(penjualanId);
        if (result >= 1) {
            return this.dao.deletePenjualanDetailByPenjualanId(penjualanId);
        }
        return result;
    }

    public List<Object[]> getTotalKeuntunganByBarang(String startDate, String endDate, Barang barang, String jenisBarang) {
        return this.dao.getTotalKeuntunganByBarang(startDate, endDate, barang, jenisBarang);
    }

    public List<Object[]> getTotalKeuntunganByCustomer(String startDate, String endDate, int limit) {
        return this.dao.getTotalKeuntunganByCustomer(startDate, endDate, limit);
    }

    public List<Object[]> getBestSeller(String startDate, String endDate, String jenis, int limit) {
        return this.dao.getBestSeller(startDate, endDate, jenis, limit);
    }

    public List<Object[]> getTotalKeuntunganByJenisBarang(String startDate, String endDate, String jenisBarang) {
        return this.dao.getTotalKeuntunganByJenisBarang(startDate, endDate, jenisBarang);
    }

    public List<Object[]> getBestSellerByCustomer(String customerId, String startDate, String endDate, int limit) {
        return this.dao.getBestSellerByCustomer(customerId, startDate, endDate, limit);
    }

    public List<Object[]> getBestSellerByCustomer(String customerId, int limit) {
        return this.dao.getBestSellerByCustomer(customerId, limit);
    }

    public int insertPenjualanDetailDeletedId(long idPenjualanDetail) {
        return this.dao.insertPenjualanDetailDeleted(idPenjualanDetail);
    }

    public int getPenjualanMemberByCustomer(String customerId) {
        return this.dao.getPenjualanMemberByCustomer(customerId);
    }

    public int sumBarang(String penjualanId, String barangId, String satuan) {
        return this.dao.sumBarang(penjualanId, barangId, satuan);
    }

    public List<Object[]> readPenjualanDetailByPenjualanId2(String penjualanId) {
        return this.dao.readPenjualanDetailByPenjualanId2(penjualanId);
    }

    public int jumlahPromoByTanggal(String tanggal, String promo, String karyawan) {
        return this.dao.jumlahPromoByTanggal(tanggal, promo, karyawan);
    }

    public long totalPromoByTanggal(String tanggal, String promo, String karyawan) {
        return this.dao.totalPromoByTanggal(tanggal, promo, karyawan);
    }

    public List<Object[]> getReportPromo(String startDate, String endDate, String jenisPromo) {
        return this.dao.getReportPromo(startDate, endDate, jenisPromo);
    }
}

