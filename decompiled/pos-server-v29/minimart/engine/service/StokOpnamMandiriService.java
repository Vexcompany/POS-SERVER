/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.StokOpnamMandiriDao;
import minimart.engine.daoimpl.StokOpnamMandiriDaoImpl;
import minimart.engine.model.StokOpnamMandiri;

public class StokOpnamMandiriService {
    private final StokOpnamMandiriDao stokOpnamMandiriDao = new StokOpnamMandiriDaoImpl();

    public int[] createBatchStokOpnamMandiri(List<StokOpnamMandiri> stokOpnamMandiris) {
        return this.stokOpnamMandiriDao.createBatchStokOpnamMandiri(stokOpnamMandiris);
    }

    public int[] createBatchStokOpnamMandiriIgnoreDuplicate(List<StokOpnamMandiri> stokOpnamMandiris) {
        return this.stokOpnamMandiriDao.createBatchStokOpnamMandiriIgnoreDuplicate(stokOpnamMandiris);
    }

    public void deleteBatchStokOpnamMandiri(String ids) {
        this.stokOpnamMandiriDao.deleteBatchStokOpnamMandiri(ids);
    }

    public int updateStokOpnamMandiri(StokOpnamMandiri stokOpnamMandiri) {
        return this.stokOpnamMandiriDao.updateStokOpnamMandiri(stokOpnamMandiri);
    }

    public StokOpnamMandiri getStokOpnamMandiri(int id) {
        return this.stokOpnamMandiriDao.getStokOpnamMandiri(id);
    }

    public List<StokOpnamMandiri> getStokOpnamMandiriUnSubmit() {
        return this.stokOpnamMandiriDao.getStokOpnamMandiriUnSubmit();
    }

    public List<StokOpnamMandiri> getStokOpnamMandiriPastSixMonth() {
        return this.stokOpnamMandiriDao.getStokOpnamMandiriPastSixMonth();
    }

    public List<StokOpnamMandiri> getSomByTanggal(String startDate, String endDate) {
        return this.stokOpnamMandiriDao.getSomByTanggal(startDate, endDate);
    }

    public String getTanggalInput(int id, int input) {
        return this.stokOpnamMandiriDao.getTanggalInput(id, input);
    }
}

