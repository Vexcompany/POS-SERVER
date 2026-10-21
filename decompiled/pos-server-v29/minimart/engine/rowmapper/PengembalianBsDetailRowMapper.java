/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PengembalianBsDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PengembalianBsDetailRowMapper
implements ParameterizedRowMapper<PengembalianBsDetail> {
    public PengembalianBsDetail mapRow(ResultSet rs, int i) throws SQLException {
        PengembalianBsDetail pengembalianBsDetail = new PengembalianBsDetail();
        pengembalianBsDetail.setId(rs.getLong("ID"));
        pengembalianBsDetail.setPengembalianBsId(rs.getString("T_PENGEMBALIAN_BS_ID"));
        pengembalianBsDetail.setBarangId(rs.getString("M_BARANG_ID"));
        pengembalianBsDetail.setJumlahBarang(rs.getFloat("JUMLAH_BARANG"));
        pengembalianBsDetail.setSatuan(rs.getString("SATUAN"));
        pengembalianBsDetail.setHpp(rs.getLong("HPP"));
        pengembalianBsDetail.setTotalHpp(rs.getLong("TOTAL_HPP"));
        pengembalianBsDetail.setCreated(rs.getDate("CREATED"));
        pengembalianBsDetail.setUpdated(rs.getDate("UPDATED"));
        return pengembalianBsDetail;
    }
}

