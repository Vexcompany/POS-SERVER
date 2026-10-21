/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.OTPPengembalian;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class OTPPengembalianRowMapper
implements ParameterizedRowMapper<OTPPengembalian> {
    public OTPPengembalian mapRow(ResultSet rs, int rowNum) throws SQLException {
        OTPPengembalian otpPengembalian = new OTPPengembalian();
        otpPengembalian.setId(rs.getInt("id"));
        otpPengembalian.setT_penjualan_id(rs.getString("t_penjualan_id"));
        otpPengembalian.setM_customer_id(rs.getString("m_customer_id"));
        otpPengembalian.setTanggal(rs.getDate("tanggal"));
        otpPengembalian.setUnique_code(rs.getString("unique_code"));
        otpPengembalian.setUsed(rs.getBoolean("used"));
        otpPengembalian.setId_cabang(rs.getInt("id_cabang"));
        otpPengembalian.setApprove_customer(rs.getBoolean("approve_customer"));
        otpPengembalian.setActivated(rs.getBoolean("activated"));
        otpPengembalian.setPembayaran(rs.getString("pembayaran"));
        otpPengembalian.setNomor_telepon(rs.getString("nomor_telepon"));
        otpPengembalian.setNomor_rekening(rs.getString("nomor_rekening"));
        otpPengembalian.setBank(rs.getString("bank"));
        otpPengembalian.setAlasan_batal(rs.getString("alasan_batal"));
        try {
            otpPengembalian.setSameDayValid(rs.getBoolean("same_day_valid"));
        }
        catch (SQLException ex) {
            otpPengembalian.setSameDayValid(false);
        }
        try {
            otpPengembalian.setApprove_finance(rs.getBoolean("approve_finance"));
        }
        catch (SQLException ex) {
            otpPengembalian.setApprove_finance(false);
        }
        return otpPengembalian;
    }
}

