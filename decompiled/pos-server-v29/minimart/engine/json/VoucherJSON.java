/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json;

import java.util.List;
import minimart.engine.model_online.VoucherO2O;
import minimart.engine.service.KonstantaService;

public class VoucherJSON {
    private int idCabang;
    List<VoucherO2O> listVoucher;
    private final KonstantaService konstantaService = new KonstantaService();

    public List<VoucherO2O> getListVoucher() {
        return this.listVoucher;
    }

    public void setListVoucher(List<VoucherO2O> listVoucher) {
        this.listVoucher = listVoucher;
    }

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int idCabang) {
        this.idCabang = idCabang;
    }

    public VoucherJSON() {
        this.idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
    }
}

