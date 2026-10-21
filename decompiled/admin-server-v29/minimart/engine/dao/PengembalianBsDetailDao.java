/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PengembalianBsDetail;

public interface PengembalianBsDetailDao {
    public int createPengembalianBsDetail(PengembalianBsDetail var1);

    public int updatePengembalianBsDetail(PengembalianBsDetail var1);

    public List<PengembalianBsDetail> getPengembalianBsDetails(String var1);

    public int deletePengembalianBsDetail(PengembalianBsDetail var1);
}

