/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Piutang;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PiutangRowMapper
implements ParameterizedRowMapper<Piutang> {
    public Piutang mapRow(ResultSet rs, int rowNum) throws SQLException {
        Piutang piutang = new Piutang();
        piutang.setId(rs.getString("ID"));
        piutang.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        piutang.setCustomerId(rs.getString("M_CUSTOMER_ID"));
        piutang.setTanggal(rs.getDate("TANGGAL"));
        piutang.setJumlah(rs.getLong("JUMLAH"));
        piutang.setTipe(rs.getString("TIPE"));
        piutang.setKeterangan(rs.getString("KETERANGAN"));
        piutang.setCreated(rs.getDate("CREATED"));
        piutang.setUpdated(rs.getDate("UPDATED"));
        return piutang;
    }
}

