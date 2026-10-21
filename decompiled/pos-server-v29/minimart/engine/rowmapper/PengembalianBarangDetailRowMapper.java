/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PengembalianBarangDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PengembalianBarangDetailRowMapper
implements ParameterizedRowMapper<PengembalianBarangDetail> {
    public PengembalianBarangDetail mapRow(ResultSet rs, int i) throws SQLException {
        PengembalianBarangDetail pengembalianBarangDetail = new PengembalianBarangDetail();
        pengembalianBarangDetail.setBarangId(rs.getString("M_BARANG_ID"));
        pengembalianBarangDetail.setHarga(rs.getLong("HARGA"));
        pengembalianBarangDetail.setId(rs.getLong("ID"));
        pengembalianBarangDetail.setJumlah(rs.getFloat("JUMLAH"));
        pengembalianBarangDetail.setPengembalianBarangId(rs.getLong("T_PENGEMBALIAN_BARANG_ID"));
        pengembalianBarangDetail.setSatuan(rs.getString("SATUAN"));
        pengembalianBarangDetail.setTotalHarga(rs.getLong("TOTAL_HARGA"));
        pengembalianBarangDetail.setTotalKeuntungan(rs.getLong("TOTAL_KEUNTUNGAN"));
        return pengembalianBarangDetail;
    }
}

