/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Paket;

public interface PaketDao {
    public int create(Paket var1);

    public int update(Paket var1);

    public int ubahStatus(String var1, int var2);

    public Paket getPaket(String var1);

    public List<Object[]> getPaketByNama(String var1);

    public int getStokBarangByDetailPaket(String var1);

    public int[] upsertPaketBatch(List<Paket> var1);

    public int deletePaketByIds(String var1);

    public int deletePaketAll();

    public List<Paket> getPaketAll();

    public List<Paket> getPaketByBarangId(String var1);
}

