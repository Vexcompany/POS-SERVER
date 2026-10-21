/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.PelunasanPiutangO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PelunasanPiutangO2ORowMapper
implements ParameterizedRowMapper<PelunasanPiutangO2O> {
    public PelunasanPiutangO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        PelunasanPiutangO2O pelunasanPiutangO2O = new PelunasanPiutangO2O();
        pelunasanPiutangO2O.setId_pelunasan_piutang_offline(rs.getInt("ID"));
        pelunasanPiutangO2O.setM_karyawan_id(rs.getString("M_KARYAWAN_ID"));
        pelunasanPiutangO2O.setT_penjualan_id(rs.getString("T_PENJUALAN_ID"));
        pelunasanPiutangO2O.setTanggal_pelunasan(rs.getDate("TANGGAL_PELUNASAN"));
        pelunasanPiutangO2O.setKeterangan(rs.getString("KETERANGAN"));
        return pelunasanPiutangO2O;
    }
}

