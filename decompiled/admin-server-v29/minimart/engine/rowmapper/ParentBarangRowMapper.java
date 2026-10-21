/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.ParentBarang;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class ParentBarangRowMapper
implements ParameterizedRowMapper<ParentBarang> {
    public ParentBarang mapRow(ResultSet rs, int rowNum) throws SQLException {
        ParentBarang parentBarang = new ParentBarang();
        parentBarang.setId(rs.getString("id"));
        parentBarang.setNama(rs.getString("nama"));
        return parentBarang;
    }
}

