/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PenjualanSementaraDao;
import minimart.engine.daoimpl.PenjualanCabangSementaraDaoImpl;
import minimart.engine.model.PenjualanSementara;

public class PenjualanCabangSementaraService {
    PenjualanSementaraDao dao = new PenjualanCabangSementaraDaoImpl();

    public int createPenjualanSementara(PenjualanSementara penjualanSementara) {
        return this.dao.createPenjualanSementara(penjualanSementara);
    }

    public int[] createPenjualanSementaraBatch(List<PenjualanSementara> penjualanSementaras) {
        return this.dao.createPenjualanSementaraBatch(penjualanSementaras);
    }

    public int updatePenjualanSementara(PenjualanSementara penjualanSementara) {
        return this.dao.updatePenjualanSementara(penjualanSementara);
    }

    public int deletePenjualanSementara(String shortcut, String barangId) {
        return this.dao.deletePenjualanSementara(shortcut, barangId);
    }

    public int deletePenjualanSementaraByShortcut(String shortcut) {
        return this.dao.deletePenjualanSementaraByShortcut(shortcut);
    }

    public int updateSalesPenjualanSementara(String customerId, String shortcut) {
        return this.dao.updateSalesPenjualanSementara(customerId, shortcut);
    }

    public List<Object[]> getPenjualanSementaraByShortcut2(String shortcut) {
        return this.dao.getPenjualanSementaraByShortcut2(shortcut);
    }

    public List<PenjualanSementara> getPenjualanSementaraByShortcut(String shortcut) {
        return this.dao.getPenjualanSementaraByShortcut(shortcut);
    }

    public int updateCustomerPenjualanSementara(String customerId, String shortcut) {
        return this.dao.updateCustomerPenjualanSementara(customerId, shortcut);
    }

    public int updateKeteranganPenjualanSementara(String keterangan, String shortcut) {
        return this.dao.updateKeteranganPenjualanSementara(keterangan, shortcut);
    }
}

