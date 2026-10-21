/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 *  org.springframework.jdbc.core.simple.SimpleJdbcTemplate
 */
package minimart.engine.daoimpl;

import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.OTPUbahHargaDao;
import minimart.engine.model.OTPUbahHarga;
import minimart.engine.rowmapper.OTPUbahHargaRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class OTPUbahHargaDaoImpl
extends SimpleJdbcDaoSupport
implements OTPUbahHargaDao {
    public OTPUbahHargaDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public int requestOTPUbahHarga(OTPUbahHarga ouh) {
        String sql = "INSERT INTO t_otp_ubah_harga (m_customer_id, id_sku, jumlah, satuan, harga, id_cabang, m_karyawan_id) VALUES (:m_customer_id, :id_sku, :jumlah, :satuan, :harga, :id_cabang, :m_karyawan_id)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)ouh);
        SimpleJdbcTemplate db = this.getSimpleJdbcTemplate();
        try {
            result = db.update(sql, (SqlParameterSource)parameterSource);
            System.out.println("Result Update Query : " + result);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public OTPUbahHarga getOTPUbahHargaByUniqueCode(String uc) {
        OTPUbahHarga ouh = null;
        String sql = "CALL sp_get_otp_ubah_harga_by_unique_code(?)";
        try {
            ouh = (OTPUbahHarga)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new OTPUbahHargaRowMapper(), new Object[]{uc});
        }
        catch (DataAccessException e) {
            System.out.println((Object)e);
        }
        return ouh;
    }

    @Override
    public int updateUsedOTPUbahHarga(long ouhId) {
        String sql = "UPDATE t_otp_ubah_harga SET used = 1, updated = CURRENT_TIMESTAMP WHERE id = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{ouhId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

