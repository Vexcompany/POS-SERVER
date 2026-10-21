/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Paket;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PaketRowMapper
implements ParameterizedRowMapper<Paket> {
    public Paket mapRow(ResultSet rs, int rowNum) throws SQLException {
        Paket paket = new Paket();
        paket.setId(rs.getString("ID"));
        paket.setBarangId(rs.getString("M_BARANG_ID"));
        paket.setCreated(rs.getDate("CREATED"));
        paket.setUpdated(rs.getDate("UPDATED"));
        paket.setStatus(rs.getInt("STATUS"));
        return paket;
    }
}

