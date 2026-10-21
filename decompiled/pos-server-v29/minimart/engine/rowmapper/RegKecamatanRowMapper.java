/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.RegKecamatan;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class RegKecamatanRowMapper
implements ParameterizedRowMapper<RegKecamatan> {
    public RegKecamatan mapRow(ResultSet rs, int rowNum) throws SQLException {
        RegKecamatan regKecamatan = new RegKecamatan();
        regKecamatan.setId(rs.getString("ID"));
        regKecamatan.setKotaId(rs.getString("KOTA_ID"));
        regKecamatan.setName(rs.getString("NAME"));
        return regKecamatan;
    }
}

