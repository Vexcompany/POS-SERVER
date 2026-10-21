/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.List;
import minimart.engine.model.BarangOnlineId;
import minimart.engine.service.KonstantaService;

public class JSONBarang {
    private int idCabang;
    private List<BarangOnlineId> listBarang;
    private final KonstantaService konstantaService = new KonstantaService();

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public List<BarangOnlineId> getListBarang() {
        return this.listBarang;
    }

    public void setListBarang(List<BarangOnlineId> listBarang) {
        this.listBarang = listBarang;
    }

    public JSONBarang() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

