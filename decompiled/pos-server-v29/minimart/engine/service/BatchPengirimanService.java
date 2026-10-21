/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.BatchPengirimanOnlineDao;
import minimart.engine.daoimpl.BatchPengirimanOnlineDaoImpl;
import minimart.engine.model.BatchPengiriman;
import minimart.engine.model.BatchPengirimanDetail;

public class BatchPengirimanService {
    private BatchPengirimanOnlineDao batchPengirimanDao = new BatchPengirimanOnlineDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public BatchPengiriman getBatchPengirimanByKodeBatch(String kodeBatch) {
        this.ensureOnlineConnection();
        return this.batchPengirimanDao.getBatchPengirimanByKodeBatch(kodeBatch);
    }

    public List<BatchPengirimanDetail> getBatchPengirimanDetailByIdBatchPengiriman(Integer id_batch_pengiriman) {
        this.ensureOnlineConnection();
        return this.batchPengirimanDao.getBatchPengirimanDetailByIdBatchPengiriman(id_batch_pengiriman);
    }
}

