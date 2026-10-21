/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PenerimaanBs;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenerimaanBsRowMapper
implements ParameterizedRowMapper<PenerimaanBs> {
    public PenerimaanBs mapRow(ResultSet rs, int rowNum) throws SQLException {
        PenerimaanBs penerimaanBs = new PenerimaanBs();
        penerimaanBs.setId(rs.getString("ID"));
        penerimaanBs.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        penerimaanBs.setTanggal(rs.getDate("TANGGAL"));
        penerimaanBs.setStatus(rs.getString("STATUS"));
        penerimaanBs.setLokasi(rs.getString("LOKASI"));
        penerimaanBs.setTotal(rs.getLong("TOTAL"));
        penerimaanBs.setCreated(rs.getDate("CREATED"));
        penerimaanBs.setUpdated(rs.getDate("UPDATED"));
        return penerimaanBs;
    }
}

