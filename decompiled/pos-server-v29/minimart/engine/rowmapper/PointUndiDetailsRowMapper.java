/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PointUndiDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PointUndiDetailsRowMapper
implements ParameterizedRowMapper<PointUndiDetail> {
    public PointUndiDetail mapRow(ResultSet rs, int i) throws SQLException {
        PointUndiDetail pud = new PointUndiDetail();
        pud.setId(rs.getInt("ID"));
        pud.setPointUndiId(rs.getInt("POINT_UNDI_ID"));
        pud.setBarangId(rs.getString("M_BARANG_ID"));
        pud.setWajib(rs.getInt("WAJIB"));
        return pud;
    }
}

