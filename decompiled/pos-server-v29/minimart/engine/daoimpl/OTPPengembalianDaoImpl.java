/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 *  org.springframework.jdbc.core.simple.SimpleJdbcTemplate
 */
package minimart.engine.daoimpl;

import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.OTPPengembalianDao;
import minimart.engine.model.OTPPengembalian;
import minimart.engine.rowmapper.OTPPengembalianRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class OTPPengembalianDaoImpl
extends SimpleJdbcDaoSupport
implements OTPPengembalianDao {
    public OTPPengembalianDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public int requestOTPPengembalian(OTPPengembalian otpPengembalian) {
        String sql = "CALL sp_request_otp_pengembalian_with_pembayaran_dan_bank(?, ?, ?, ?, ?, ?, ?, ?)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)otpPengembalian);
        SimpleJdbcTemplate db = this.getSimpleJdbcTemplate();
        try {
            result = (Integer)db.queryForObject(sql, Integer.class, new Object[]{otpPengembalian.getT_penjualan_id(), otpPengembalian.getM_customer_id(), otpPengembalian.getId_cabang(), otpPengembalian.getM_karyawan_id(), otpPengembalian.getPembayaran(), otpPengembalian.getNomor_telepon(), otpPengembalian.getBank(), otpPengembalian.getNomor_rekening()});
            System.out.println("Result Update Query : " + result);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public OTPPengembalian getOtpPengembalianByUniqueCode(String uniqueCode) {
        OTPPengembalian otpPengembalian = null;
        String sql = "CALL sp_get_otp_pengembalian_by_unique_code(?)";
        try {
            otpPengembalian = (OTPPengembalian)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new OTPPengembalianRowMapper(), new Object[]{uniqueCode});
        }
        catch (DataAccessException e) {
            System.out.println((Object)e);
        }
        return otpPengembalian;
    }

    @Override
    public int updateStatusUsedOTPPengembalian(int idOtpPengembalian) {
        String sql = "UPDATE t_otp_pengembalian SET used = 1, updated = CURRENT_TIMESTAMP WHERE id = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{idOtpPengembalian});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

