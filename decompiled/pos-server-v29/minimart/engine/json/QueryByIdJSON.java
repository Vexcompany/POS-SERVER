/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.service.KonstantaService;

public class QueryByIdJSON {
    private int idCabang;
    List<String> listIds;
    private final KonstantaService konstantaService = new KonstantaService();

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public List<String> getListIds() {
        return this.listIds;
    }

    public void setListIds(List<String> listIds) {
        this.listIds = listIds;
    }

    public QueryByIdJSON() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

