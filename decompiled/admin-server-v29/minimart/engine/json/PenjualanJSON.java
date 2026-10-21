/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.PenjualanO2O;
import minimart.engine.service.KonstantaService;

public class PenjualanJSON {
    private int id_cabang;
    List<PenjualanO2O> list_penjualan;
    private final KonstantaService konstantaService = new KonstantaService();

    public int getIdCabang() {
        return this.id_cabang;
    }

    public void setIdCabang(int idCabang) {
        this.id_cabang = idCabang;
    }

    public List<PenjualanO2O> getListPenjualan() {
        return this.list_penjualan;
    }

    public void setListPenjualan(List<PenjualanO2O> list) {
        this.list_penjualan = list;
    }

    public PenjualanJSON() {
        this.id_cabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

