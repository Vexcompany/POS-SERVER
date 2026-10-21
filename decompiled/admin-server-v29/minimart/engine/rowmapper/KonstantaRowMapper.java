/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Konstanta;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class KonstantaRowMapper
implements ParameterizedRowMapper<Konstanta> {
    public Konstanta mapRow(ResultSet rs, int i) throws SQLException {
        Konstanta konstanta = new Konstanta();
        konstanta.setKonstan(rs.getString("CONSTANT"));
        konstanta.setValue(rs.getString("VALUE"));
        return konstanta;
    }
}

