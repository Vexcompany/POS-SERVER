/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PengembalianBarang;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PengembalianBarangRowMapper
implements ParameterizedRowMapper<PengembalianBarang> {
    public PengembalianBarang mapRow(ResultSet rs, int i) throws SQLException {
        PengembalianBarang pengembalianBarang = new PengembalianBarang();
        pengembalianBarang.setId(rs.getLong("ID"));
        pengembalianBarang.setCustomerId(rs.getString("M_CUSTOMER_ID"));
        pengembalianBarang.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        pengembalianBarang.setPenjualanId(rs.getString("T_PENJUALAN_ID"));
        pengembalianBarang.setKeterangan(rs.getString("KETERANGAN"));
        pengembalianBarang.setTanggal(rs.getDate("TANGGAL"));
        pengembalianBarang.setTotalPengembalian(rs.getLong("TOTAL_PENGEMBALIAN"));
        pengembalianBarang.setCreated(rs.getDate("CREATED"));
        pengembalianBarang.setPelunasan(rs.getString("PELUNASAN"));
        pengembalianBarang.setCetakVoucher(rs.getInt("CETAK_VOUCHER"));
        pengembalianBarang.setNoHp(rs.getString("NO_HP"));
        pengembalianBarang.setShiftKasirId(rs.getInt("m_shift_kasir_id"));
        return pengembalianBarang;
    }
}

