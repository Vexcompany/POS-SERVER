/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PickingList;
import minimart.engine.model.PickingListDetail;

public interface PickingListOnlineDao {
    public PickingList getPickingListById(Integer var1);

    public List<PickingListDetail> getPickingListDetailByHeaderId(Integer var1);
}

