/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PenukaranPoint;
import minimart.engine.model_online.PenukaranPointO2O;

public interface PenukaranPointDao {
    public int createPenukaranPoint(PenukaranPoint var1);

    public List<PenukaranPoint> getPenukaranPoints(String var1, String var2, String var3);

    public int getLastId();

    public PenukaranPoint getPenukaranPoint(int var1);

    public int updatePenukaranPoint(PenukaranPointO2O[] var1);

    public int updatePenukaranPoint(String var1);

    public List<PenukaranPointO2O> getAllPenukaranPointUnsynced();

    public int[] upsertDataPenukaranPointDariOnline(PenukaranPointO2O[] var1);

    public String getLatestDate();
}

