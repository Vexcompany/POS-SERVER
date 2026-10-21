/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Penjualan;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenjualanRowMapper
implements ParameterizedRowMapper<Penjualan> {
    public Penjualan mapRow(ResultSet rs, int rowNum) throws SQLException {
        Penjualan penjualan = new Penjualan();
        penjualan.setId(rs.getString("ID"));
        penjualan.setCustomerId(rs.getString("M_CUSTOMER_ID"));
        penjualan.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        penjualan.setSalesId(rs.getString("M_SALES_ID"));
        penjualan.setKasir(rs.getInt("KASIR"));
        penjualan.setTanggal(rs.getDate("TANGGAL"));
        penjualan.setTotalPenjualan(rs.getLong("TOTAL_PENJUALAN"));
        penjualan.setStatus(rs.getString("STATUS"));
        penjualan.setPoin(rs.getInt("POIN"));
        penjualan.setCreated(rs.getTimestamp("CREATED"));
        penjualan.setUpdated(rs.getTimestamp("UPDATED"));
        penjualan.setJatuhTempo(rs.getDate("JATUH_TEMPO"));
        penjualan.setKeterangan(rs.getString("KETERANGAN"));
        penjualan.setStatusPelunasan(rs.getString("STATUS_PELUNASAN"));
        penjualan.setReferensi(rs.getString("REFERENSI"));
        penjualan.setShiftKasirId(rs.getInt("M_SHIFT_KASIR_ID"));
        penjualan.setIdTransaksiOnline(rs.getString("ID_TRANSAKSI_ONLINE"));
        penjualan.setVoucher(rs.getString("VOUCHER"));
        penjualan.setEligiblePwp(rs.getInt("ELIGIBLE_PWP"));
        penjualan.setEligibleTebusMurah(rs.getInt("ELIGIBLE_TEBUS_MURAH"));
        penjualan.setLamaTransaksi(rs.getLong("LAMA_TRANSAKSI"));
        return penjualan;
    }
}

