/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.PembelianO2O;
import minimart.engine.service.KonstantaService;

public class PembelianJSON {
    int id_cabang;
    List<PembelianO2O> list_pembelian;
    private final KonstantaService konstantaService = new KonstantaService();

    public void setIdCabang(int idCabang) {
        this.id_cabang = idCabang;
    }

    public int getIdCabang() {
        return this.id_cabang;
    }

    public void setListPembelian(List<PembelianO2O> list) {
        this.list_pembelian = list;
    }

    public List<PembelianO2O> getListPembelian() {
        return this.list_pembelian;
    }

    public PembelianJSON() {
        this.id_cabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

