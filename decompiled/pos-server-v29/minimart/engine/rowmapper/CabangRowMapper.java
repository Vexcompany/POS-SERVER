/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Cabang;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class CabangRowMapper
implements ParameterizedRowMapper<Cabang> {
    public Cabang mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cabang cabang = new Cabang();
        cabang.setId(rs.getInt("ID"));
        cabang.setNama(rs.getString("NAMA"));
        cabang.setAlamat(rs.getString("ALAMAT"));
        cabang.setTipeToko(rs.getString("TIPE_TOKO"));
        cabang.setTipeCabang(rs.getString("TIPE_CABANG"));
        return cabang;
    }
}

