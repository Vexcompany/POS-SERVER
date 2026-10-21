/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.PengembalianBarangDetailO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PengembalianBarangDetailO2ORowMapper
implements ParameterizedRowMapper<PengembalianBarangDetailO2O> {
    public PengembalianBarangDetailO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        PengembalianBarangDetailO2O pengembalianBarangDetailO2O = new PengembalianBarangDetailO2O();
        pengembalianBarangDetailO2O.setM_barang_id(rs.getString("M_BARANG_ID"));
        pengembalianBarangDetailO2O.setHarga(rs.getLong("HARGA"));
        pengembalianBarangDetailO2O.setId_pengembalian_barang_detail_offline(rs.getLong("ID"));
        pengembalianBarangDetailO2O.setJumlah(rs.getFloat("JUMLAH"));
        pengembalianBarangDetailO2O.setT_pengembalian_barang_id(rs.getLong("T_PENGEMBALIAN_BARANG_ID"));
        pengembalianBarangDetailO2O.setSatuan(rs.getString("SATUAN"));
        pengembalianBarangDetailO2O.setTotal_harga(rs.getLong("TOTAL_HARGA"));
        pengembalianBarangDetailO2O.setTotal_keuntungan(rs.getLong("TOTAL_KEUNTUNGAN"));
        return pengembalianBarangDetailO2O;
    }
}

