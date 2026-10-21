/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.PenjualanDetailO2O;
import minimart.engine.service.KonstantaService;

public class PenjualanDetailJSON {
    int id_cabang;
    List<PenjualanDetailO2O> list_penjualan_detail;
    private final KonstantaService konstantaService = new KonstantaService();

    public void setIdCabang(int idCabang) {
        this.id_cabang = idCabang;
    }

    public int getIdCabang() {
        return this.id_cabang;
    }

    public void setListPenjualanDetail(List<PenjualanDetailO2O> list) {
        this.list_penjualan_detail = list;
    }

    public List<PenjualanDetailO2O> getListPenjualanDetail() {
        return this.list_penjualan_detail;
    }

    public PenjualanDetailJSON() {
        this.id_cabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

