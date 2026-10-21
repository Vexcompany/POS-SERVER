/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.StokOpnamO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StokOpnamO2ORowMapper
implements ParameterizedRowMapper<StokOpnamO2O> {
    public StokOpnamO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        StokOpnamO2O stokOpnamO2O = new StokOpnamO2O();
        stokOpnamO2O.setId_stok_opnam_offline(rs.getInt("ID"));
        stokOpnamO2O.setM_karyawan_id(rs.getString("M_KARYAWAN_ID"));
        stokOpnamO2O.setLokasi(rs.getString("LOKASI"));
        stokOpnamO2O.setTipe(rs.getString("TIPE"));
        stokOpnamO2O.setTipe_name(rs.getString("TIPE_NAME"));
        stokOpnamO2O.setStatus_opnam(rs.getString("STATUS_OPNAM"));
        stokOpnamO2O.setTanggal(rs.getDate("TANGGAL"));
        stokOpnamO2O.setKeterangan(rs.getString("KETERANGAN"));
        stokOpnamO2O.setUpdated(rs.getDate("UPDATED"));
        return stokOpnamO2O;
    }
}

