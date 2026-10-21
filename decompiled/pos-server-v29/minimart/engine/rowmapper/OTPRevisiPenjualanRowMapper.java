/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.OTPRevisiPenjualan;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class OTPRevisiPenjualanRowMapper
implements ParameterizedRowMapper<OTPRevisiPenjualan> {
    public OTPRevisiPenjualan mapRow(ResultSet rs, int rowNum) throws SQLException {
        OTPRevisiPenjualan otpRevisiPenjualan = new OTPRevisiPenjualan();
        otpRevisiPenjualan.setId(rs.getInt("id"));
        otpRevisiPenjualan.setT_penjualan_id(rs.getString("t_penjualan_id"));
        otpRevisiPenjualan.setM_customer_id(rs.getString("m_customer_id"));
        otpRevisiPenjualan.setM_karyawan_id(rs.getString("m_karyawan_id"));
        Object idUser = rs.getObject("id_user");
        if (idUser != null) {
            otpRevisiPenjualan.setId_user(rs.getInt("id_user"));
        }
        otpRevisiPenjualan.setTotal_before(rs.getLong("total_before"));
        otpRevisiPenjualan.setTotal_after(rs.getLong("total_after"));
        otpRevisiPenjualan.setUnique_code(rs.getString("unique_code"));
        otpRevisiPenjualan.setApproved(rs.getBoolean("approved"));
        otpRevisiPenjualan.setUsed(rs.getBoolean("used"));
        otpRevisiPenjualan.setId_cabang(rs.getInt("id_cabang"));
        otpRevisiPenjualan.setRequested_at(rs.getTimestamp("requested_at"));
        otpRevisiPenjualan.setApproved_at(rs.getTimestamp("approved_at"));
        otpRevisiPenjualan.setUsed_at(rs.getTimestamp("used_at"));
        otpRevisiPenjualan.setCreated_at(rs.getTimestamp("created_at"));
        otpRevisiPenjualan.setUpdated_at(rs.getTimestamp("updated_at"));
        try {
            otpRevisiPenjualan.setSameDayValid(rs.getBoolean("same_day_valid"));
        }
        catch (SQLException ex) {
            otpRevisiPenjualan.setSameDayValid(false);
        }
        return otpRevisiPenjualan;
    }
}

