/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.PickingListOnlineDao;
import minimart.engine.daoimpl.PickingListOnlineDaoImpl;
import minimart.engine.model.PickingList;
import minimart.engine.model.PickingListDetail;

public class PickingListService {
    private PickingListOnlineDao pickingListOnlineDao = new PickingListOnlineDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public PickingList getPickingListById(Integer id) {
        this.ensureOnlineConnection();
        return this.pickingListOnlineDao.getPickingListById(id);
    }

    public List<PickingListDetail> getPickingListDetailByIdHeader(Integer id_header) {
        this.ensureOnlineConnection();
        return this.pickingListOnlineDao.getPickingListDetailByHeaderId(id_header);
    }
}

