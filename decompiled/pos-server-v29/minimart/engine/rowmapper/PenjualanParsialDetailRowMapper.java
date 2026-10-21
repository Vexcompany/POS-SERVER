/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PenjualanParsialDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenjualanParsialDetailRowMapper
implements ParameterizedRowMapper<PenjualanParsialDetail> {
    public PenjualanParsialDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        PenjualanParsialDetail penjualanParsialDetail = new PenjualanParsialDetail();
        penjualanParsialDetail.setId(rs.getInt("ID"));
        penjualanParsialDetail.setPenjualanParsialId(rs.getString("T_PENJUALAN_PARSIAL_ID"));
        penjualanParsialDetail.setBarangId(rs.getString("m_barang_id"));
        penjualanParsialDetail.setJumlahBarang(rs.getFloat("JUMLAH_BARANG"));
        penjualanParsialDetail.setSatuan(rs.getString("SATUAN"));
        penjualanParsialDetail.setHargaJual(rs.getLong("HARGA_JUAL"));
        penjualanParsialDetail.setDiscount(rs.getLong("DISCOUNT"));
        penjualanParsialDetail.setTotalHarga(rs.getLong("TOTAL_HARGA"));
        return penjualanParsialDetail;
    }
}

