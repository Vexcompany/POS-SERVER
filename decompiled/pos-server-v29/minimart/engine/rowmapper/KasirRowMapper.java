/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Kasir;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class KasirRowMapper
implements ParameterizedRowMapper<Kasir> {
    public Kasir mapRow(ResultSet rs, int i) throws SQLException {
        Kasir kasir = new Kasir();
        kasir.setId(rs.getInt("ID"));
        kasir.setCreated(rs.getDate("CREATED"));
        kasir.setUpdated(rs.getDate("UPDATED"));
        return kasir;
    }
}

