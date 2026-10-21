/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.PelunasanHutangO2O;
import minimart.engine.service.KonstantaService;

public class PelunasanHutangJSON {
    int idCabang;
    List<PelunasanHutangO2O> listPelunasanHutang;
    final KonstantaService konstantaService = new KonstantaService();

    public PelunasanHutangJSON() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public List<PelunasanHutangO2O> getListPelunasanHutang() {
        return this.listPelunasanHutang;
    }

    public void setListPelunasanHutang(List<PelunasanHutangO2O> listPelunasanHutang) {
        this.listPelunasanHutang = listPelunasanHutang;
    }
}

