/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.Date;
import java.util.List;
import minimart.engine.dao.ShiftKasirDao;
import minimart.engine.daoimpl.ShiftKasirDaoImpl;
import minimart.engine.model.ShiftKasir;
import minimart.engine.service.SyncService;

public class ShiftKasirService {
    private ShiftKasirDao dao = new ShiftKasirDaoImpl();
    private SyncService syncService = new SyncService();

    public int create(ShiftKasir shiftKasir) {
        int result = this.dao.create(shiftKasir);
        if (result > 0) {
            this.syncService.doSyncShiftKasirUnsynced();
        }
        return result;
    }

    public int update(ShiftKasir shiftKasir) {
        int result = this.dao.update(shiftKasir);
        if (result > 0) {
            this.syncService.doSyncShiftKasirUnsynced();
        }
        return result;
    }

    public List<ShiftKasir> getShifts(String startDate, String endDate) {
        return this.dao.getShifts(startDate, endDate);
    }

    public ShiftKasir getOpenedShiftKasir(int idkasir) {
        return this.dao.getOpenedShiftKasir(idkasir);
    }

    public List<ShiftKasir> getIsOpenShifts(String tanggal) {
        return this.dao.getIsOpenShifts(tanggal);
    }

    public List<ShiftKasir> getIsOpenShiftsBeforeDate(String date) {
        return this.dao.getIsOpenShiftsBeforeDate(date);
    }

    public List<ShiftKasir> getIsOpenAnyShifts() {
        return this.dao.getIsOpenAnyShifts();
    }

    public List<ShiftKasir> getShiftsByKasir(String karyawan, String startDate) {
        return this.dao.getShiftsByKasir(karyawan, startDate);
    }

    public Date getKasirDate(int idKasir) {
        return this.dao.getKasirDate(idKasir);
    }
}

