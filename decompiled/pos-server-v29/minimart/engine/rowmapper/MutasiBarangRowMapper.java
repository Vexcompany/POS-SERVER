/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.MutasiBarang;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class MutasiBarangRowMapper
implements ParameterizedRowMapper<MutasiBarang> {
    public MutasiBarang mapRow(ResultSet rs, int i) throws SQLException {
        MutasiBarang mutasiBarang = new MutasiBarang();
        mutasiBarang.setId(rs.getString("ID"));
        mutasiBarang.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        mutasiBarang.setTanggal(rs.getDate("TANGGAL"));
        mutasiBarang.setDari(rs.getString("DARI"));
        mutasiBarang.setMenuju(rs.getString("MENUJU"));
        mutasiBarang.setKeterangan(rs.getString("KETERANGAN"));
        mutasiBarang.setCreated(rs.getDate("CREATED"));
        mutasiBarang.setUpdated(rs.getDate("UPDATED"));
        return mutasiBarang;
    }
}

