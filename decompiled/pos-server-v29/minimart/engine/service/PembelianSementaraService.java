/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PembelianSementaraDao;
import minimart.engine.daoimpl.PembelianSementaraDaoImpl;
import minimart.engine.model.PembelianSementara;

public class PembelianSementaraService {
    private PembelianSementaraDao dao = new PembelianSementaraDaoImpl();

    public int createPembelianSementara(PembelianSementara pembelianSementara) {
        return this.dao.createPembelianSementara(pembelianSementara);
    }

    public int updatePembelianSementara(PembelianSementara pembelianSementara) {
        return this.dao.updatePembelianSementara(pembelianSementara);
    }

    public int deletePembelianSementara(String shortcut, String barangId) {
        return this.dao.deletePembelianSementara(shortcut, barangId);
    }

    public int deletePembelianSementaraByShortcut(String shortcut) {
        return this.dao.deletePembelianSementaraByShortcut(shortcut);
    }

    public List<PembelianSementara> getPembelianSementaraByShortcut(String shortcut) {
        return this.dao.getPembelianSementaraByShortcut(shortcut);
    }

    public int updateSupplierPembelianSementara(String supplierId, String shortcut) {
        return this.dao.updateSupplierPembelianSementara(supplierId, shortcut);
    }

    public int updatePembayaranPembelianSementara(String pembayaran, String shortcut) {
        return this.dao.updatePembayaranPembelianSementara(pembayaran, shortcut);
    }
}

