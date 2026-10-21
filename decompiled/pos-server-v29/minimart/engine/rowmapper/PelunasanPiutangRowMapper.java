/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PelunasanPiutang;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PelunasanPiutangRowMapper
implements ParameterizedRowMapper<PelunasanPiutang> {
    public PelunasanPiutang mapRow(ResultSet rs, int i) throws SQLException {
        PelunasanPiutang pelunasanPiutang = new PelunasanPiutang();
        pelunasanPiutang.setId(rs.getInt("ID"));
        pelunasanPiutang.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        pelunasanPiutang.setPenjualanId(rs.getString("T_PENJUALAN_ID"));
        pelunasanPiutang.setTanggalPelunasan(rs.getDate("TANGGAL_PELUNASAN"));
        pelunasanPiutang.setKeterangan(rs.getString("KETERANGAN"));
        pelunasanPiutang.setJumlahPelunasan(rs.getLong("JUMLAH_PELUNASAN"));
        pelunasanPiutang.setShiftKasirId(rs.getInt("M_SHIFT_KASIR_ID"));
        return pelunasanPiutang;
    }
}

