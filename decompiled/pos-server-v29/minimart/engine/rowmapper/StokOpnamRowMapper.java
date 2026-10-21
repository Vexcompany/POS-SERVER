/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.StokOpnam;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StokOpnamRowMapper
implements ParameterizedRowMapper<StokOpnam> {
    public StokOpnam mapRow(ResultSet rs, int i) throws SQLException {
        StokOpnam stokOpnam = new StokOpnam();
        stokOpnam.setId(rs.getInt("ID"));
        stokOpnam.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        stokOpnam.setLokasi(rs.getString("LOKASI"));
        stokOpnam.setTipe(rs.getString("TIPE"));
        stokOpnam.setTipeName(rs.getString("TIPE_NAME"));
        stokOpnam.setStatusOpnam(rs.getString("STATUS_OPNAM"));
        stokOpnam.setTanggal(rs.getDate("TANGGAL"));
        stokOpnam.setKeterangan(rs.getString("KETERANGAN"));
        stokOpnam.setUpdated(rs.getDate("UPDATED"));
        return stokOpnam;
    }
}

