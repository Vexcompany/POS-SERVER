/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.StokOpnamMandiriSementara;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StokOpnamMandiriSementaraRowMapper
implements ParameterizedRowMapper<StokOpnamMandiriSementara> {
    public StokOpnamMandiriSementara mapRow(ResultSet rs, int rowNum) throws SQLException {
        StokOpnamMandiriSementara soms = new StokOpnamMandiriSementara();
        soms.setId(rs.getLong("ID"));
        soms.setStokOpnamMandiriDetailId(rs.getLong("STOK_OPNAM_MANDIRI_DETAIL_ID"));
        soms.setInputKe(rs.getInt("INPUT_KE"));
        soms.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        soms.setStokInput(rs.getInt("STOK_INPUT"));
        soms.setCreated(rs.getDate("CREATED"));
        soms.setLokasi(rs.getString("LOKASI"));
        soms.setIsVoid(rs.getInt("VOID"));
        return soms;
    }
}

