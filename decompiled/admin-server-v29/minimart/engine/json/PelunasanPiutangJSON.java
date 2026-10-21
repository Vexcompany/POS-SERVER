/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.PelunasanPiutangO2O;
import minimart.engine.service.KonstantaService;

public class PelunasanPiutangJSON {
    int idCabang;
    List<PelunasanPiutangO2O> listPelunasanPiutang;
    final KonstantaService konstantaService = new KonstantaService();

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public List<PelunasanPiutangO2O> getListPelunasanPiutang() {
        return this.listPelunasanPiutang;
    }

    public void setListPelunasanPiutang(List<PelunasanPiutangO2O> listPelunasanPiutang) {
        this.listPelunasanPiutang = listPelunasanPiutang;
    }

    public PelunasanPiutangJSON() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

