/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Sales;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class SalesRowMapper
implements ParameterizedRowMapper<Sales> {
    public Sales mapRow(ResultSet rs, int i) throws SQLException {
        Sales sales = new Sales();
        sales.setId(rs.getString("ID"));
        sales.setAlamat(rs.getString("ALAMAT"));
        sales.setKtp(rs.getString("KTP"));
        sales.setNama(rs.getString("NAMA"));
        sales.setTelepon(rs.getString("TELEPON"));
        sales.setTarget(rs.getInt("TARGET"));
        return sales;
    }
}

