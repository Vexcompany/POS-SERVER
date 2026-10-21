/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.ShiftKasirO2O;
import minimart.engine.service.KonstantaService;

public class ShiftKasirJSON {
    private int idCabang;
    List<ShiftKasirO2O> listShiftKasir;
    private final KonstantaService konstantaService = new KonstantaService();

    public ShiftKasirJSON() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public List<ShiftKasirO2O> getListShiftKasir() {
        return this.listShiftKasir;
    }

    public void setListShiftKasir(List<ShiftKasirO2O> listShiftKasir) {
        this.listShiftKasir = listShiftKasir;
    }
}

