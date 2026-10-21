/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.RegKelurahan;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class RegKelurahanRowMapper
implements ParameterizedRowMapper<RegKelurahan> {
    public RegKelurahan mapRow(ResultSet rs, int rowNum) throws SQLException {
        RegKelurahan regKelurahan = new RegKelurahan();
        regKelurahan.setId(rs.getString("ID"));
        regKelurahan.setKecamatanId(rs.getString("KECAMATAN_ID"));
        regKelurahan.setName(rs.getString("NAME"));
        return regKelurahan;
    }
}

