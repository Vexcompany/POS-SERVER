/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Paket;
import minimart.engine.model.PaketDetail;

public interface PaketOnlineDao {
    public List<Paket> getPaketOnline(String var1);

    public List<PaketDetail> getPaketDetailOnline(String var1);
}

