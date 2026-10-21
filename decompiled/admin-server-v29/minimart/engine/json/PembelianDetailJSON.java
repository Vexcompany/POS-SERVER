/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.PembelianDetailO2O;
import minimart.engine.service.KonstantaService;

public class PembelianDetailJSON {
    int id_cabang;
    List<PembelianDetailO2O> list_pembelian_detail;
    final KonstantaService konstantaService = new KonstantaService();

    public int getIdCabang() {
        return this.id_cabang;
    }

    public void setIdCabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public List<PembelianDetailO2O> getListPembelianDetail() {
        return this.list_pembelian_detail;
    }

    public void setListPembelianDetail(List<PembelianDetailO2O> list_pembelian_detail) {
        this.list_pembelian_detail = list_pembelian_detail;
    }

    public PembelianDetailJSON() {
        this.id_cabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

