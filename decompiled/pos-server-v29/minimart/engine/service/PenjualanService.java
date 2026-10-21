/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PenjualanDao;
import minimart.engine.daoimpl.PenjualanDaoImpl;
import minimart.engine.model.Penjualan;

public class PenjualanService {
    private PenjualanDao dao = new PenjualanDaoImpl();

    public List<Penjualan> getPenjualansKredit(String startDate, String endDate, String customerId) {
        return this.dao.getPenjualansKredit(startDate, endDate, customerId);
    }

    public long getTotalKeuntunganPenjualan(String idPenjualan) {
        return this.dao.getTotalKeuntunganPenjualan(idPenjualan);
    }

    public int createPenjualan(Penjualan penjualan) {
        return this.dao.createPenjualan(penjualan);
    }

    public int[] createPenjualanBatch(List<Penjualan> penjualans) {
        return this.dao.createPenjualanBatch(penjualans);
    }

    public List<Object[]> getPiutangJatuhTempo() {
        return this.dao.getPiutangJatuhTempo();
    }

    public Penjualan getPenjualan(String id) {
        return this.dao.getPenjualan(id);
    }

    public Penjualan getPenjualanByReferensi(String referensi) {
        return this.dao.getPenjualanByReferensi(referensi);
    }

    public List<Object[]> getPiutang(String customerId) {
        return this.dao.getPiutang(customerId);
    }

    public List<Object[]> getPenjualans(String startDate, String endDate, String customerId, String salesId) {
        return this.dao.getPenjualans(startDate, endDate, customerId, salesId);
    }

    public List<Object[]> getPenjualansWithKeterangan(String startDate, String endDate, String customerId, String keterangan) {
        return this.dao.getPenjualansWithKeterangan(startDate, endDate, customerId, keterangan);
    }

    public List<Penjualan> getPenjualanTerakhir(String date) {
        return this.dao.getPenjualanTerakhir(date);
    }

    public int updatePenjualan(Penjualan penjualan) {
        return this.dao.updatePenjualan(penjualan);
    }

    public int[] updatePenjualanBatch(List<Penjualan> dataPenjualan) {
        return this.dao.updatePenjualanBatch(dataPenjualan);
    }

    public int deletePenjualan(String id) {
        return this.dao.deletePenjualan(id);
    }

    public String getLastId() {
        return this.dao.getLastId();
    }

    public List<Object[]> getRekapPenjualan(String status, String startDate, String endDate) {
        return this.dao.getRekapPenjualan(status, startDate, endDate);
    }

    public List<Object[]> getPoinPenjualanCustomers(String startDate, String endDate) {
        return this.dao.getPoinPenjualanCustomers(startDate, endDate);
    }

    public long getTotalKreditCustomer(String idCustomer) {
        return this.dao.getTotalKreditCustomer(idCustomer);
    }

    public List<Penjualan> getNotaJatuhTempo(String idCustomer) {
        return this.dao.getNotaJatuhTempo(idCustomer);
    }

    public List<Object[]> getPenjualanSales(String salesId, String startDate, String endDate) {
        return this.dao.getPenjualanSales(salesId, startDate, endDate);
    }

    public List<Object[]> getRekapPenjualanByKeterangan(String status, String startDate, String endDate, String keterangan) {
        return this.dao.getRekapPenjualanByKeterangan(status, startDate, endDate, keterangan);
    }

    public List<Object[]> getTopCustomer(String startDate, String endDate, int limit) {
        return this.dao.getTopCustomer(startDate, endDate, limit);
    }

    public long getTotalOpenedShift(int kasir, String keterangan) {
        return this.dao.getTotalOpenedShift(kasir, keterangan);
    }

    public int updatePenjualanShift(int shiftKasirId) {
        return this.dao.updatePenjualanShift(shiftKasirId);
    }

    public long getTotalVoucherShift(int kasir, String keterangan) {
        return this.dao.getTotalVoucherShift(kasir, keterangan);
    }

    public long getTotalPenjualanByShift(int shiftKasirId, String keterangan) {
        return this.dao.getTotalPenjualanByShift(shiftKasirId, keterangan);
    }

    public long getTotalVoucherByIdShift(int shiftKasirId, String keterangan) {
        return this.dao.getTotalVoucherByIdShift(shiftKasirId, keterangan);
    }

    public List<Object[]> getPenjualansByShift(int shiftKasirId) {
        return this.dao.getPenjualansByShift(shiftKasirId);
    }

    public List<Object[]> getPenjualansByShift(int shiftKasirId, String tanggal) {
        return this.dao.getPenjualansByShift(shiftKasirId, tanggal);
    }

    public List<Object[]> getPerformaPromo(String startDate, String endDate) {
        return this.dao.getPerformaPromo(startDate, endDate);
    }

    public List<Object[]> getPerformaOutlet(String startDate, String endDate) {
        return this.dao.getPerformaOutlet(startDate, endDate);
    }
}

