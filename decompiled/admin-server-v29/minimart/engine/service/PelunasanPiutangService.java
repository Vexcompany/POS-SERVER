/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PelunasanPiutangDao;
import minimart.engine.daoimpl.PelunasanPiutangDaoImpl;
import minimart.engine.model.PelunasanPiutang;

public class PelunasanPiutangService {
    private PelunasanPiutangDao ppd = new PelunasanPiutangDaoImpl();

    public int createPelunasanPiutang(PelunasanPiutang pelunasanHutang) {
        return this.ppd.createPelunasanPiutang(pelunasanHutang);
    }

    public int[] createPelunasanPiutangBatch(List<PelunasanPiutang> pelunasanPiutang) {
        return this.ppd.createPelunasanPiutangBatch(pelunasanPiutang);
    }

    public int updatePelunasanPiutang(PelunasanPiutang pelunasanHutan) {
        return this.ppd.updatePelunasanPiutang(pelunasanHutan);
    }

    public List<Object[]> getPelunasanPiutang(String tanggalAwal, String tanggalAkhir, String customerId) {
        return this.ppd.getPelunasanPiutang(tanggalAwal, tanggalAkhir, customerId);
    }

    public long getTotalPelunasanPiutangShift(int shiftKasirId, String keterangan) {
        return this.ppd.getTotalPelunasanPiutangShift(shiftKasirId, keterangan);
    }

    public List<Object[]> getPelunasanPiutangByShift(int shiftKasirId) {
        return this.ppd.getPelunasanPiutangByShift(shiftKasirId);
    }

    public List<Object[]> getPelunasanPiutangByShift(int shiftKasirId, String tanggal) {
        return this.ppd.getPelunasanPiutangByShift(shiftKasirId, tanggal);
    }
}

