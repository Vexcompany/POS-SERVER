/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.PengembalianBarangO2O;
import minimart.engine.service.KonstantaService;

public class PengembalianBarangJSON {
    int idCabang;
    List<PengembalianBarangO2O> listPengembalianBarang;
    final KonstantaService konstantaService = new KonstantaService();

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public List<PengembalianBarangO2O> getListPengembalianBarang() {
        return this.listPengembalianBarang;
    }

    public void setListPengembalianBarang(List<PengembalianBarangO2O> listPengembalianBarang) {
        this.listPengembalianBarang = listPengembalianBarang;
    }

    public PengembalianBarangJSON() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

