/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.PelunasanHutangO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PelunasanHutangO2ORowMapper
implements ParameterizedRowMapper<PelunasanHutangO2O> {
    public PelunasanHutangO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        PelunasanHutangO2O pelunasanHutangO2O = new PelunasanHutangO2O();
        pelunasanHutangO2O.setId_pelunasan_hutang_offline(rs.getInt("ID"));
        pelunasanHutangO2O.setM_karyawan_id(rs.getString("M_KARYAWAN_ID"));
        pelunasanHutangO2O.setT_pembelian_id(rs.getString("T_PEMBELIAN_ID"));
        pelunasanHutangO2O.setTanggal_pelunasan(rs.getDate("TANGGAL_PELUNASAN"));
        pelunasanHutangO2O.setJumlah(rs.getLong("JUMLAH"));
        pelunasanHutangO2O.setKeterangan(rs.getString("KETERANGAN"));
        return pelunasanHutangO2O;
    }
}

