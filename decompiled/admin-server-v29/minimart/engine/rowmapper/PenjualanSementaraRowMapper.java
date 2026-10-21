/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PenjualanSementara;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenjualanSementaraRowMapper
implements ParameterizedRowMapper<PenjualanSementara> {
    public PenjualanSementara mapRow(ResultSet rs, int i) throws SQLException {
        PenjualanSementara penjualanSementara = new PenjualanSementara();
        penjualanSementara.setKasir(rs.getInt("KASIR"));
        penjualanSementara.setShortcut(rs.getString("SHORTCUT"));
        penjualanSementara.setCustomerId(rs.getString("M_CUSTOMER_ID"));
        penjualanSementara.setSalesId(rs.getString("M_SALES_ID"));
        penjualanSementara.setBarangId(rs.getString("M_BARANG_ID"));
        penjualanSementara.setJumlahBarang(rs.getFloat("JUMLAH_BARANG"));
        penjualanSementara.setSatuan(rs.getString("SATUAN"));
        penjualanSementara.setHargaJual(rs.getLong("HARGA_JUAL"));
        penjualanSementara.setDiscount(rs.getLong("DISCOUNT"));
        penjualanSementara.setTotalHarga(rs.getLong("TOTAL_HARGA"));
        penjualanSementara.setNotaKhusus(rs.getBoolean("NOTA_KHUSUS"));
        penjualanSementara.setKeterangan(rs.getString("KETERANGAN"));
        penjualanSementara.setNamaBarang(rs.getString("NAMA"));
        penjualanSementara.setPickinglistId(rs.getString("PICKINGLIST_ID"));
        penjualanSementara.setIdTransaksiOnline(rs.getString("ID_TRANSAKSI_ONLINE"));
        penjualanSementara.setPromo(rs.getString("PROMO"));
        return penjualanSementara;
    }
}

