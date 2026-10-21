/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PenambahanPoint;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenambahanPointRowMapper
implements ParameterizedRowMapper<PenambahanPoint> {
    public PenambahanPoint mapRow(ResultSet rs, int i) throws SQLException {
        PenambahanPoint penambahanPoint = new PenambahanPoint();
        penambahanPoint.setId(rs.getLong("id"));
        penambahanPoint.setCustomerId(rs.getString("m_customer_id"));
        penambahanPoint.setJumlahPoint(rs.getInt("jumlah_point"));
        penambahanPoint.setStatus(rs.getString("status"));
        penambahanPoint.setCreated(rs.getDate("created"));
        return penambahanPoint;
    }
}

