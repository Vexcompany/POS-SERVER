/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Bonus;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class BonusRowMapper
implements ParameterizedRowMapper<Bonus> {
    public Bonus mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bonus bonus = new Bonus();
        bonus.setId(rs.getLong("ID"));
        bonus.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        bonus.setTanggal(rs.getDate("TANGGAL"));
        bonus.setJumlah(rs.getLong("JUMLAH"));
        bonus.setStatus(rs.getString("STATUS"));
        bonus.setKeterangan(rs.getString("KETERANGAN"));
        bonus.setCreated(rs.getDate("CREATED"));
        bonus.setUpdated(rs.getDate("UPDATED"));
        return bonus;
    }
}

