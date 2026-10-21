/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PembelianSementara;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PembelianSementaraRowMapper
implements ParameterizedRowMapper<PembelianSementara> {
    public PembelianSementara mapRow(ResultSet rs, int i) throws SQLException {
        PembelianSementara pembelianSementara = new PembelianSementara();
        pembelianSementara.setId(rs.getLong("ID"));
        pembelianSementara.setShortcut(rs.getString("SHORTCUT"));
        pembelianSementara.setStatus(rs.getString("STATUS"));
        pembelianSementara.setSupplierId(rs.getString("M_SUPPLIER_ID"));
        pembelianSementara.setBarangId(rs.getString("M_BARANG_ID"));
        pembelianSementara.setJumlahBarang(rs.getFloat("JUMLAH_BARANG"));
        pembelianSementara.setSatuan(rs.getString("SATUAN"));
        pembelianSementara.setHargaBeli(rs.getLong("HARGA_BELI"));
        pembelianSementara.setDiscount(rs.getLong("DISCOUNT"));
        pembelianSementara.setTotalHarga(rs.getLong("TOTAL_HARGA"));
        pembelianSementara.setTanggalPembelian(rs.getDate("TANGGAL_PEMBELIAN"));
        pembelianSementara.setPembayaran(rs.getString("PEMBAYARAN"));
        pembelianSementara.setJatuhTempo(rs.getDate("JATUH_TEMPO"));
        return pembelianSementara;
    }
}

