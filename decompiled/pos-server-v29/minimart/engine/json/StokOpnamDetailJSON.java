/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.StokOpnamDetailO2O;
import minimart.engine.service.KonstantaService;

public class StokOpnamDetailJSON {
    int idCabang;
    List<StokOpnamDetailO2O> listStokOpnamDetail;
    private final KonstantaService konstantaService = new KonstantaService();

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public List<StokOpnamDetailO2O> getListStokOpnamDetail() {
        return this.listStokOpnamDetail;
    }

    public void setListStokOpnamDetail(List<StokOpnamDetailO2O> listStokOpnamDetail) {
        this.listStokOpnamDetail = listStokOpnamDetail;
    }

    public StokOpnamDetailJSON() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

