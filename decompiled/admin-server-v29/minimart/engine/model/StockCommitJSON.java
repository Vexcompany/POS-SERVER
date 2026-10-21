/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.List;
import minimart.engine.model.StockCommit;
import minimart.engine.service.KonstantaService;

public class StockCommitJSON {
    private int idCabang;
    private List<StockCommit> listStockCommit;
    private final KonstantaService konstantaService = new KonstantaService();

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public List<StockCommit> getListStockCommit() {
        return this.listStockCommit;
    }

    public void setListStockCommit(List<StockCommit> listStockCommit) {
        this.listStockCommit = listStockCommit;
    }

    public StockCommitJSON() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

