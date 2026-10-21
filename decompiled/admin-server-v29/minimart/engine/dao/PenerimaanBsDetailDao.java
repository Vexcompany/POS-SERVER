/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PenerimaanBsDetail;

public interface PenerimaanBsDetailDao {
    public int createPenerimaanBsDetail(PenerimaanBsDetail var1);

    public List<PenerimaanBsDetail> getPenerimaanBsDetailsByPenerimaanBsId(String var1);

    public int updatePenerimaanBsDetail(PenerimaanBsDetail var1);

    public int deletePenerimaanBsDetail(PenerimaanBsDetail var1);
}

