/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.StokOpnamMandiri;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StokOpnamMandiriRowMapper
implements ParameterizedRowMapper<StokOpnamMandiri> {
    public StokOpnamMandiri mapRow(ResultSet rs, int rowNum) throws SQLException {
        StokOpnamMandiri stokOpnamMandiri = new StokOpnamMandiri();
        stokOpnamMandiri.setId(rs.getInt("ID"));
        stokOpnamMandiri.setTanggal(rs.getDate("TANGGAL"));
        stokOpnamMandiri.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        stokOpnamMandiri.setTanggalSubmit(rs.getTimestamp("TANGGAL_SUBMIT"));
        stokOpnamMandiri.setTanggalInput1(rs.getTimestamp("TANGGAL_INPUT_1"));
        stokOpnamMandiri.setTanggalInput2(rs.getTimestamp("TANGGAL_INPUT_2"));
        stokOpnamMandiri.setTanggalInput3(rs.getTimestamp("TANGGAL_INPUT_3"));
        stokOpnamMandiri.setCreated(rs.getTimestamp("CREATED"));
        stokOpnamMandiri.setUpdated(rs.getTimestamp("UPDATED"));
        stokOpnamMandiri.setKeterangan(rs.getString("KETERANGAN"));
        stokOpnamMandiri.setTipe(rs.getString("TIPE"));
        stokOpnamMandiri.setBlindSom(rs.getString("BLIND_SOM"));
        return stokOpnamMandiri;
    }
}

