/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.ReturPenjualan;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class ReturPenjualanRowMapper
implements ParameterizedRowMapper<ReturPenjualan> {
    public ReturPenjualan mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReturPenjualan returPenjualan = new ReturPenjualan();
        returPenjualan.setId(rs.getString("ID"));
        returPenjualan.setPenjualanId(rs.getString("T_PENJUALAN_ID"));
        returPenjualan.setBarangId(rs.getString("M_BARANG_ID"));
        returPenjualan.setTanggal(rs.getDate("TANGGAL"));
        returPenjualan.setJumlahBarang(rs.getInt("JUMLAH_BARANG"));
        returPenjualan.setHargaRetur(rs.getBigDecimal("HARGA_RETUR"));
        returPenjualan.setTotalHarga(rs.getBigDecimal("TOTAL_HARGA"));
        returPenjualan.setCreated(rs.getDate("CREATED"));
        returPenjualan.setUpdated(rs.getDate("UPDATED"));
        return returPenjualan;
    }
}

