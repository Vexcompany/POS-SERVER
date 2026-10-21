/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.PembelianDetailO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PembelianDetailO2ORowMapper
implements ParameterizedRowMapper<PembelianDetailO2O> {
    public PembelianDetailO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        PembelianDetailO2O pembelianDetailO2O = new PembelianDetailO2O();
        pembelianDetailO2O.setId_pembelian_detail_offline(rs.getLong("ID"));
        pembelianDetailO2O.setT_pembelian_id(rs.getString("T_PEMBELIAN_ID"));
        pembelianDetailO2O.setM_barang_id(rs.getString("M_BARANG_ID"));
        pembelianDetailO2O.setJumlah_barang(rs.getFloat("JUMLAH_BARANG"));
        pembelianDetailO2O.setSatuan(rs.getString("SATUAN"));
        pembelianDetailO2O.setHarga_beli(rs.getLong("HARGA_BELI"));
        pembelianDetailO2O.setDiscount_2(rs.getLong("DISCOUNT_2"));
        pembelianDetailO2O.setTotal_harga(rs.getLong("TOTAL_HARGA"));
        pembelianDetailO2O.setCreated(rs.getTimestamp("CREATED"));
        pembelianDetailO2O.setUpdated(rs.getTimestamp("UPDATED"));
        return pembelianDetailO2O;
    }
}

