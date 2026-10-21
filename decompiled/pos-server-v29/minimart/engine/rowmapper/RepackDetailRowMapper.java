/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.RepackDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class RepackDetailRowMapper
implements ParameterizedRowMapper<RepackDetail> {
    public RepackDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        RepackDetail repackDetail = new RepackDetail();
        repackDetail.setId(rs.getInt("ID"));
        repackDetail.setRepackId(rs.getString("T_REPACK_ID"));
        repackDetail.setBarangId(rs.getString("M_BARANG_ID"));
        repackDetail.setJumlahBarang(rs.getFloat("JUMLAH_BARANG"));
        repackDetail.setSatuan(rs.getString("SATUAN"));
        return repackDetail;
    }
}

