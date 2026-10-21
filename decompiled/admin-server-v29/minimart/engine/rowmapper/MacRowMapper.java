/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.MutasiCabang;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class MacRowMapper
implements ParameterizedRowMapper<MutasiCabang> {
    public MutasiCabang mapRow(ResultSet rs, int rowNum) throws SQLException {
        MutasiCabang mac = new MutasiCabang();
        mac.setId(rs.getString("ID"));
        mac.setCustomerId(rs.getString("M_CUSTOMER_ID"));
        mac.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        mac.setKasir(rs.getInt("KASIR"));
        mac.setTanggal(rs.getDate("TANGGAL"));
        mac.setTotalPenjualan(rs.getLong("TOTAL_PENJUALAN"));
        mac.setStatus(rs.getString("STATUS"));
        mac.setCreated(rs.getTimestamp("CREATED"));
        mac.setUpdated(rs.getTimestamp("UPDATED"));
        mac.setJatuhTempo(rs.getDate("JATUH_TEMPO"));
        mac.setKeterangan(rs.getString("KETERANGAN"));
        mac.setStatusPelunasan(rs.getString("STATUS_PELUNASAN"));
        mac.setShiftKasirId(rs.getInt("M_SHIFT_KASIR_ID"));
        return mac;
    }
}

