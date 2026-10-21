/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class LongRowMapper
implements ParameterizedRowMapper<Long> {
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getLong(1);
    }
}

