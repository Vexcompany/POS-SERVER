/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.HargaBertingkat;

public interface HargaBertingkatDao {
    public int createHargaBertingkat(HargaBertingkat var1);

    public int updateHargaBertingkat(HargaBertingkat var1);

    public int deleteHargaBertingkat(HargaBertingkat var1);

    public List<HargaBertingkat> getHargaBertingkatsByBarangId(String var1);
}

