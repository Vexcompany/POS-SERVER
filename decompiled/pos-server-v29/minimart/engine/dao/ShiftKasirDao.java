/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.Date;
import java.util.List;
import minimart.engine.model.ShiftKasir;
import minimart.engine.model_online.ShiftKasirO2O;

public interface ShiftKasirDao {
    public int create(ShiftKasir var1);

    public int update(ShiftKasir var1);

    public List<ShiftKasir> getShifts(String var1, String var2);

    public ShiftKasir getOpenedShiftKasir(int var1);

    public List<ShiftKasir> getIsOpenShifts(String var1);

    public List<ShiftKasir> getIsOpenShiftsBeforeDate(String var1);

    public List<ShiftKasir> getIsOpenAnyShifts();

    public List<ShiftKasir> getShiftsByKasir(String var1, String var2);

    public Date getKasirDate(int var1);

    public List<ShiftKasirO2O> getAllShiftKasirUnsynced();

    public int updateShiftKasirUnsynced(String var1);

    public int[] upsertDataShiftKasirDariOnline(ShiftKasirO2O[] var1);

    public String getLatestDate();
}

