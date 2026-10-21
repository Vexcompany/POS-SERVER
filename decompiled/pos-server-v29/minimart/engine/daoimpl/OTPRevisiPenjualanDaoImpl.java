/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 *  org.springframework.jdbc.core.simple.SimpleJdbcTemplate
 */
package minimart.engine.daoimpl;

import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.OTPRevisiPenjualanDao;
import minimart.engine.model.OTPRevisiPenjualan;
import minimart.engine.rowmapper.OTPRevisiPenjualanRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class OTPRevisiPenjualanDaoImpl
extends SimpleJdbcDaoSupport
implements OTPRevisiPenjualanDao {
    public OTPRevisiPenjualanDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public int requestOTPRevisiPenjualan(OTPRevisiPenjualan otpRevisiPenjualan) {
        String sql = "CALL sp_request_otp_revisi_penjualan(?, ?, ?, ?, ?, ?, ?)";
        int result = 0;
        SimpleJdbcTemplate db = this.getSimpleJdbcTemplate();
        try {
            result = (Integer)db.queryForObject(sql, Integer.class, new Object[]{otpRevisiPenjualan.getT_penjualan_id(), otpRevisiPenjualan.getM_customer_id(), otpRevisiPenjualan.getM_karyawan_id(), otpRevisiPenjualan.getId_user(), otpRevisiPenjualan.getTotal_before(), otpRevisiPenjualan.getTotal_after(), otpRevisiPenjualan.getId_cabang()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public OTPRevisiPenjualan getOTPRevisiPenjualanByUniqueCode(String uniqueCode) {
        OTPRevisiPenjualan otpRevisiPenjualan = null;
        String sql = "CALL sp_get_otp_revisi_penjualan_by_unique_code(?)";
        try {
            otpRevisiPenjualan = (OTPRevisiPenjualan)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new OTPRevisiPenjualanRowMapper(), new Object[]{uniqueCode});
        }
        catch (DataAccessException e) {
            System.out.println((Object)e);
        }
        return otpRevisiPenjualan;
    }

    @Override
    public int updateStatusUsedOTPRevisiPenjualan(int idOtpRevisiPenjualan) {
        String sql = "UPDATE t_otp_revisi_penjualan SET used = 1, used_at = CURRENT_TIMESTAMP, updated_at = CURRENT_TIMESTAMP WHERE id = ? AND used = 0 AND DATE(requested_at) = CURDATE()";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{idOtpRevisiPenjualan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

