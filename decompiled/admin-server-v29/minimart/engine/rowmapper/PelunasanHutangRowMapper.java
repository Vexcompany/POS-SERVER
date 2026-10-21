/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PelunasanHutang;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PelunasanHutangRowMapper
implements ParameterizedRowMapper<PelunasanHutang> {
    public PelunasanHutang mapRow(ResultSet rs, int i) throws SQLException {
        PelunasanHutang pelunasanHutang = new PelunasanHutang();
        pelunasanHutang.setId(rs.getInt("ID"));
        pelunasanHutang.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        pelunasanHutang.setPembelianId(rs.getString("T_PEMBELIAN_ID"));
        pelunasanHutang.setTanggalPelunasan(rs.getDate("TANGGAL_PELUNASAN"));
        pelunasanHutang.setJumlah(rs.getLong("JUMLAH"));
        pelunasanHutang.setKeterangan(rs.getString("KETERANGAN"));
        return pelunasanHutang;
    }
}

