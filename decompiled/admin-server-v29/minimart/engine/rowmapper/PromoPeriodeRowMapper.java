/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoPeriode;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoPeriodeRowMapper
implements ParameterizedRowMapper<PromoPeriode> {
    public PromoPeriode mapRow(ResultSet rs, int i) throws SQLException {
        PromoPeriode promoPeriode = new PromoPeriode();
        promoPeriode.setId(rs.getInt("ID"));
        promoPeriode.setBarangId(rs.getString("M_BARANG_ID"));
        promoPeriode.setPesan(rs.getString("PESAN"));
        promoPeriode.setMinimal(rs.getInt("MINIMAL"));
        promoPeriode.setAktifkan(rs.getString("AKTIFKAN"));
        return promoPeriode;
    }
}

