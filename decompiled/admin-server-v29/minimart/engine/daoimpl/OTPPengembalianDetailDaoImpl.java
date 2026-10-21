/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.OTPPengembalianDetailDao;
import minimart.engine.model.OTPPengembalianDetail;
import minimart.engine.rowmapper.OTPPengembalianDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class OTPPengembalianDetailDaoImpl
extends SimpleJdbcDaoSupport
implements OTPPengembalianDetailDao {
    public OTPPengembalianDetailDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<OTPPengembalianDetail> getOTPPengembalianDetailById(int idOTPPengembalian) {
        String sql = "SELECT * FROM t_otp_pengembalian_detail WHERE t_otp_pengembalian_id = ?";
        List opds = null;
        try {
            opds = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new OTPPengembalianDetailRowMapper(), new Object[]{idOTPPengembalian});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return opds;
    }

    @Override
    public int[] insertOTPPengembalianDetail(List<OTPPengembalianDetail> otpPengembalianDetails) {
        String sql = "INSERT INTO t_otp_pengembalian_detail (t_otp_pengembalian_id, id_sku, satuan, jumlah, harga, id_cabang, discount) VALUES (:t_otp_pengembalian_id, :id_sku, :satuan, :jumlah, :harga, :id_cabang, :discount)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])otpPengembalianDetails.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }
}

