/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PenjualanSementaraDao;
import minimart.engine.daoimpl.PenjualanSementaraDaoImpl;
import minimart.engine.model.PenjualanSementara;

public class PenjualanSementaraService {
    PenjualanSementaraDao dao = new PenjualanSementaraDaoImpl();

    public long getTotalBelanja(String shortcut, int kasir) {
        return this.dao.getTotalBelanja(shortcut, kasir);
    }

    public int createPenjualanSementara(PenjualanSementara penjualanSementara) {
        return this.dao.createPenjualanSementara(penjualanSementara);
    }

    public int[] createPenjualanSementaraBatch(List<PenjualanSementara> penjualanSementaras) {
        return this.dao.createPenjualanSementaraBatch(penjualanSementaras);
    }

    public int updatePenjualanSementara(PenjualanSementara penjualanSementara) {
        return this.dao.updatePenjualanSementara(penjualanSementara);
    }

    public int deletePenjualanSementara(String shortcut, String barangId, float jumlahBarang, String satuan) {
        return this.dao.deletePenjualanSementara(shortcut, barangId, jumlahBarang, satuan);
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

    public List<PenjualanSementara> getPenjualanSementaraByShortcut(String shortcut, String parent_id, String satuan) {
        return this.dao.getPenjualanSementaraByShortcut(shortcut, parent_id, satuan);
    }

    public List<PenjualanSementara> getPenjualanSementaraByShortcutIdBarang(String shortcut, String barangId, String satuan) {
        return this.dao.getPenjualanSementaraByShortcutIdBarang(shortcut, barangId, satuan);
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

    public float cekJumlahByParentId(int kasir, String shortcut, String satuan, String parent_id) {
        return this.dao.cekJumlahByParentId(kasir, shortcut, satuan, parent_id);
    }

    public int updateHargaPenjualanSementara(long harga, long totalHarga, long discount, int kasir, String shortcut, String satuan, String barang_id, Float jumlahBarang) {
        return this.dao.updateHargaPenjualanSementara(harga, totalHarga, discount, kasir, shortcut, satuan, barang_id, jumlahBarang);
    }

    public int updateHargaPenjualanSementara(long harga, long totalHarga, int kasir, String shortcut, String satuan, String barang_id) {
        return this.dao.updateHargaPenjualanSementara(harga, totalHarga, kasir, shortcut, satuan, barang_id);
    }

    public float getJumlahPenjualanSementara(int kasirId, String shortCut, String barangId, String satuan) {
        return this.dao.getJumlahPenjualanSementara(kasirId, shortCut, barangId, satuan);
    }

    public int getRecentTransaction(int kasirId) {
        return this.dao.getRecentTransaction(kasirId);
    }

    public List<Object[]> getPoinPerPenjualan(String shortcut) {
        return this.dao.getPoinPerPenjualan(shortcut);
    }

    public int sumBarang(int kasirId, String shortCut, String barangId, String satuan) {
        return this.dao.sumBarang(kasirId, shortCut, barangId, satuan);
    }

    public int isVoid(int kasirId, String shortCut, String barangId) {
        return this.dao.isVoid(kasirId, shortCut, barangId);
    }

    public List<Object[]> getPenjualanSementaraByShortcut2OrderByName(String shortcut) {
        return this.dao.getPenjualanSementaraByShortcut2OrderByName(shortcut);
    }
}

