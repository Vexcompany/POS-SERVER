/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PelunasanHutangDao;
import minimart.engine.daoimpl.PelunasanHutangDaoImpl;
import minimart.engine.model.PelunasanHutang;

public class PelunasanHutangService {
    private PelunasanHutangDao pelunasanHutangDao = new PelunasanHutangDaoImpl();

    public int createPelunasanHutang(PelunasanHutang pelunasanHutang) {
        return this.pelunasanHutangDao.createPelunasanHutang(pelunasanHutang);
    }

    public int updatePelunasanHutang(PelunasanHutang pelunasanHutan) {
        return this.pelunasanHutangDao.updatePelunasanHutang(pelunasanHutan);
    }

    public List<PelunasanHutang> getPelunasanHutang(String supplierId, String tanggalAwal, String tanggalAkhir, String keterangan) {
        return this.pelunasanHutangDao.getPelunasanHutang(supplierId, tanggalAwal, tanggalAkhir, keterangan);
    }
}

