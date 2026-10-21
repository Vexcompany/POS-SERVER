/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.CustomerBaru;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class CustomerBaruRowMapper
implements ParameterizedRowMapper<CustomerBaru> {
    public CustomerBaru mapRow(ResultSet rs, int i) throws SQLException {
        CustomerBaru cb = new CustomerBaru();
        cb.setId(rs.getLong("id"));
        cb.setKtp(rs.getString("ktp"));
        return cb;
    }
}

