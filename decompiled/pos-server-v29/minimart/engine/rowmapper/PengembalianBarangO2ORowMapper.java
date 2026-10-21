/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.PengembalianBarangO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PengembalianBarangO2ORowMapper
implements ParameterizedRowMapper<PengembalianBarangO2O> {
    public PengembalianBarangO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        PengembalianBarangO2O pengembalianBarangO2O = new PengembalianBarangO2O();
        pengembalianBarangO2O.setId_pengembalian_barang_offline(rs.getLong("ID"));
        pengembalianBarangO2O.setM_customer_id(rs.getString("M_CUSTOMER_ID"));
        pengembalianBarangO2O.setM_karyawan_id(rs.getString("M_KARYAWAN_ID"));
        pengembalianBarangO2O.setKeterangan(rs.getString("KETERANGAN"));
        pengembalianBarangO2O.setTanggal(rs.getDate("TANGGAL"));
        pengembalianBarangO2O.setTotal_pengembalian(rs.getLong("TOTAL_PENGEMBALIAN"));
        pengembalianBarangO2O.setCreated(rs.getDate("CREATED"));
        pengembalianBarangO2O.setPelunasan(rs.getString("PELUNASAN"));
        pengembalianBarangO2O.setT_penjualan_id(rs.getString("T_PENJUALAN_ID"));
        pengembalianBarangO2O.setCetak_voucher(rs.getInt("CETAK_VOUCHER"));
        pengembalianBarangO2O.setNo_hp("NO_HP");
        return pengembalianBarangO2O;
    }
}

