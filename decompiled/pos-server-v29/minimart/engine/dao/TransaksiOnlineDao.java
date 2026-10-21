/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.TransaksiOnline;

public interface TransaksiOnlineDao {
    public TransaksiOnline getTransaksiOnlineByUniqueCode(int var1);

    public int updateIdTransaksiOfflineTransaksiOnline(int var1, String var2);

    public int updateIdTransaksiOnlineStatusLog(int var1, String var2, String var3);
}

