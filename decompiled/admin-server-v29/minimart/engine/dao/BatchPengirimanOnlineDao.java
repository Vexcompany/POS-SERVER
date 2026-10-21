/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.BatchPengiriman;
import minimart.engine.model.BatchPengirimanDetail;

public interface BatchPengirimanOnlineDao {
    public BatchPengiriman getBatchPengirimanByKodeBatch(String var1);

    public List<BatchPengirimanDetail> getBatchPengirimanDetailByIdBatchPengiriman(Integer var1);
}

