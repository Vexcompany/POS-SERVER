/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.PenjualanDetailO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenjualanDetailO2ORowMapper
implements ParameterizedRowMapper<PenjualanDetailO2O> {
    public PenjualanDetailO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        PenjualanDetailO2O penjualanDetailO2O = new PenjualanDetailO2O();
        penjualanDetailO2O.setId_penjualan_detail_offline(rs.getLong("ID"));
        penjualanDetailO2O.setT_penjualan_id(rs.getString("T_PENJUALAN_ID"));
        penjualanDetailO2O.setM_barang_id(rs.getString("M_BARANG_ID"));
        penjualanDetailO2O.setJumlah_barang(rs.getFloat("JUMLAH_BARANG"));
        penjualanDetailO2O.setSatuan(rs.getString("SATUAN"));
        penjualanDetailO2O.setHarga_jual(rs.getLong("HARGA_JUAL"));
        penjualanDetailO2O.setDiscount_2(rs.getLong("DISCOUNT_2"));
        penjualanDetailO2O.setTotal_harga(rs.getLong("TOTAL_HARGA"));
        penjualanDetailO2O.setTotal_keuntungan(rs.getLong("TOTAL_KEUNTUNGAN"));
        penjualanDetailO2O.setUpdated(rs.getTimestamp("UPDATED"));
        penjualanDetailO2O.setCreated(rs.getTimestamp("CREATED"));
        penjualanDetailO2O.setReferensi(rs.getString("REFERENSI"));
        penjualanDetailO2O.setPromo(rs.getString("PROMO"));
        return penjualanDetailO2O;
    }
}

