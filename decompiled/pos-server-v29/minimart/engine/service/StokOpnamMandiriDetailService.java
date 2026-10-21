/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.StokOpnamMandiriDetailDao;
import minimart.engine.daoimpl.StokOpnamMandiriDetailDaoImpl;
import minimart.engine.model.StokOpnamMandiriDetail;

public class StokOpnamMandiriDetailService {
    private final StokOpnamMandiriDetailDao dao = new StokOpnamMandiriDetailDaoImpl();

    public int[] createStokOpnamMandiriDetailBatch(List<StokOpnamMandiriDetail> stokOpnamMandiriDetails) {
        return this.dao.createStokOpnamMandiriDetailBatch(stokOpnamMandiriDetails);
    }

    public int[] createStokOpnamMandiriDetailBatchIgnoreDuplicate(List<StokOpnamMandiriDetail> stokOpnamMandiriDetails) {
        return this.dao.createStokOpnamMandiriDetailBatchIgnoreDuplicate(stokOpnamMandiriDetails);
    }

    public int[] updateStokOpnamMandiriDetailBatch(List<StokOpnamMandiriDetail> stokOpnamMandiriDetails) {
        return this.dao.updateStokOpnamMandiriDetailBatch(stokOpnamMandiriDetails);
    }

    public int updateStokOpnamMandiriDetail(StokOpnamMandiriDetail stokOpnamMandiriDetail) {
        return this.dao.updateStokOpnamMandiriDetail(stokOpnamMandiriDetail);
    }

    public List<Object[]> getStokOpnamMandiriDetails(int stokOpnamMandiriId, int input, String blind) {
        return this.dao.getStokOpnamMandiriDetails(stokOpnamMandiriId, input, blind);
    }

    public StokOpnamMandiriDetail getStokOpnamMandiriDetail(long id) {
        return this.dao.getStokOpnamMandiriDetail(id);
    }

    public int updateRevisi1(int stokOpnamMandiriId) {
        return this.dao.updateRevisi1(stokOpnamMandiriId);
    }

    public int updateRevisi2(int stokOpnamMandiriId) {
        return this.dao.updateRevisi2(stokOpnamMandiriId);
    }

    public long getSelisihRevisi1(int stokOpnamId) {
        return this.dao.getSelisihRevisi1(stokOpnamId);
    }

    public long getSelisihRevisi2(int stokOpnamId) {
        return this.dao.getSelisihRevisi2(stokOpnamId);
    }

    public long getSelisihRevisi3(int stokOpnamId) {
        return this.dao.getSelisihRevisi3(stokOpnamId);
    }

    public String getStokOpnamMandiriDetailOnProgress(String barangId, int nDays) {
        return this.dao.getStokOpnamMandiriDetailOnProgress(barangId, nDays);
    }

    public List<String> getBarangIdHiddenBySom(int nDays) {
        return this.dao.getBarangIdHiddenBySom(nDays);
    }

    public List<StokOpnamMandiriDetail> getStokOpnamMandiriDetailPastSixMonth() {
        return this.dao.getStokOpnamMandiriDetailPastSixMonth();
    }
}

