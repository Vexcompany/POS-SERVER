/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.StokOpnamDetailDao;
import minimart.engine.daoimpl.StokOpnamDetailDaoImpl;
import minimart.engine.model.StokOpnamDetail;

public class StokOpnamDetailService {
    private final StokOpnamDetailDao dao = new StokOpnamDetailDaoImpl();

    public int createStokOpnamDetail(StokOpnamDetail stokOpnam) {
        return this.dao.createStokOpnamDetail(stokOpnam);
    }

    public List<Object[]> getStokOpnamDetails(String stokOpnamId) {
        return this.dao.getStokOpnamDetails(stokOpnamId);
    }

    public int deleteStokOpnamDetailById(int id) {
        return this.dao.deleteStokOpnamDetailById(id);
    }

    public int deleteStokOpnamDetailByStokOpnamId(String stokOpnamId) {
        return this.dao.deleteStokOpnamDetailByPenjualanId(stokOpnamId);
    }

    public List<StokOpnamDetail> getStokSelisihOpnamDetails(String stokOpnamId) {
        return this.dao.getStokSelisihOpnamDetails(stokOpnamId);
    }

    public int updateStokOpnamDetail(StokOpnamDetail stokOpnamDetail) {
        return this.dao.updateStokOpnamDetail(stokOpnamDetail);
    }

    public List<StokOpnamDetail> getAllStokOpnamDetails() {
        return this.dao.getAllStokOpnamDetails();
    }

    public List<Object[]> getStokOpnamDetails(String startDate, String endDate, String lokasi, String idBarang) {
        return this.dao.getStokOpnamDetails(startDate, endDate, lokasi, idBarang);
    }

    public List<Object[]> getStokOpnamDetailsRekap(String startDate, String endDate, String lokasi, String idBarang) {
        return this.dao.getStokOpnamDetailsRekap(startDate, endDate, lokasi, idBarang);
    }

    public int[] createStokOpnamDetailBatch(List<StokOpnamDetail> datas) {
        return this.dao.createStokOpnamDetailBatch(datas);
    }
}

