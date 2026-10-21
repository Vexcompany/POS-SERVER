/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoHematDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoHematDetailOnlineRowMapper
implements ParameterizedRowMapper<PromoHematDetail> {
    public PromoHematDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

