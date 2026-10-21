/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Karyawan;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class KaryawanRowMapper
implements ParameterizedRowMapper<Karyawan> {
    public Karyawan mapRow(ResultSet rs, int rowNum) throws SQLException {
        Karyawan karyawan = new Karyawan();
        karyawan.setId(rs.getString("ID"));
        karyawan.setNama(rs.getString("NAMA"));
        karyawan.setPassword(rs.getString("PASSWORD"));
        karyawan.setGaji(rs.getLong("GAJI"));
        karyawan.setRole(rs.getString("ROLE"));
        karyawan.setAvailable(rs.getBoolean("AVAILABLE"));
        karyawan.setCreated(rs.getDate("CREATED"));
        karyawan.setUpdated(rs.getDate("UPDATED"));
        return karyawan;
    }
}

