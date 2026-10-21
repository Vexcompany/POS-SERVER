/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Jenis;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class JenisRowMapper
implements ParameterizedRowMapper<Jenis> {
    public Jenis mapRow(ResultSet rs, int rowNum) throws SQLException {
        Jenis jenis = new Jenis();
        jenis.setJenis(rs.getString("JENIS"));
        jenis.setPoinSales(rs.getInt("POIN_SALES"));
        jenis.setCreated(rs.getDate("CREATED"));
        jenis.setUpdated(rs.getDate("UPDATED"));
        jenis.setMarginCabang(rs.getFloat("MARGIN_CABANG"));
        jenis.setMarginTempo(rs.getFloat("MARGIN_TEMPO"));
        return jenis;
    }
}

