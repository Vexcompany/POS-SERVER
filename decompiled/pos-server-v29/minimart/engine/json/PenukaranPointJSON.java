/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.PenukaranPointO2O;
import minimart.engine.service.KonstantaService;

public class PenukaranPointJSON {
    private int idCabang;
    List<PenukaranPointO2O> listPenukaranPoint;
    private final KonstantaService konstantaService = new KonstantaService();

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public List<PenukaranPointO2O> getListPenukaranPoint() {
        return this.listPenukaranPoint;
    }

    public void setListPenukaranPoint(List<PenukaranPointO2O> listPenukaranPoint) {
        this.listPenukaranPoint = listPenukaranPoint;
    }

    public PenukaranPointJSON() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

