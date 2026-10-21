/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Repack;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class RepackRowMapper
implements ParameterizedRowMapper<Repack> {
    public Repack mapRow(ResultSet rs, int rowNum) throws SQLException {
        Repack repack = new Repack();
        repack.setId(rs.getString("ID"));
        repack.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        repack.setTanggal(rs.getDate("TANGGAL"));
        repack.setCreated(rs.getDate("CREATED"));
        repack.setBarangId(rs.getString("M_BARANG_ID"));
        repack.setJumlah(rs.getInt("JUMLAH"));
        return repack;
    }
}

