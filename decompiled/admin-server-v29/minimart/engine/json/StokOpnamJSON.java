/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.StokOpnamO2O;
import minimart.engine.service.KonstantaService;

public class StokOpnamJSON {
    private int idCabang;
    List<StokOpnamO2O> listStokOpnam;
    private final KonstantaService konstantaService = new KonstantaService();

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public List<StokOpnamO2O> getListStokOpnam() {
        return this.listStokOpnam;
    }

    public void setListStokOpnam(List<StokOpnamO2O> listStokOpnam) {
        this.listStokOpnam = listStokOpnam;
    }

    public StokOpnamJSON() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

