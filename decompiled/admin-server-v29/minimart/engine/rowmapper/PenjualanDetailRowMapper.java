/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PenjualanDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenjualanDetailRowMapper
implements ParameterizedRowMapper<PenjualanDetail> {
    public PenjualanDetail mapRow(ResultSet rs, int i) throws SQLException {
        PenjualanDetail penjualanDetail = new PenjualanDetail();
        penjualanDetail.setId(rs.getLong("ID"));
        penjualanDetail.setPenjualanId(rs.getString("T_PENJUALAN_ID"));
        penjualanDetail.setBarangId(rs.getString("M_BARANG_ID"));
        penjualanDetail.setJumlahBarang(rs.getFloat("JUMLAH_BARANG"));
        penjualanDetail.setSatuan(rs.getString("SATUAN"));
        penjualanDetail.setHargaJual(rs.getLong("HARGA_JUAL"));
        penjualanDetail.setDiscount2(rs.getLong("DISCOUNT_2"));
        penjualanDetail.setTotalHarga(rs.getLong("TOTAL_HARGA"));
        penjualanDetail.setTotalKeuntungan(rs.getLong("TOTAL_KEUNTUNGAN"));
        penjualanDetail.setUpdated(rs.getDate("UPDATED"));
        penjualanDetail.setCreated(rs.getDate("CREATED"));
        penjualanDetail.setReferensi(rs.getString("REFERENSI"));
        penjualanDetail.setPromo(rs.getString("PROMO"));
        return penjualanDetail;
    }
}

