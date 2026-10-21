/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.PengembalianBarangDetailO2O;
import minimart.engine.service.KonstantaService;

public class PengembalianBarangDetailJSON {
    int idCabang;
    List<PengembalianBarangDetailO2O> listPengembalianBarangDetail;
    final KonstantaService konstantaService = new KonstantaService();

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public List<PengembalianBarangDetailO2O> getListPengembalianBarangDetail() {
        return this.listPengembalianBarangDetail;
    }

    public void setListPengembalianBarangDetail(List<PengembalianBarangDetailO2O> listPengembalianBarangDetail) {
        this.listPengembalianBarangDetail = listPengembalianBarangDetail;
    }

    public PengembalianBarangDetailJSON() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

