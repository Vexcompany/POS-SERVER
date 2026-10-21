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
import minimart.engine.dao.OTPRevisiPenjualanDetailDao;
import minimart.engine.model.OTPRevisiPenjualanDetail;
import minimart.engine.rowmapper.OTPRevisiPenjualanDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class OTPRevisiPenjualanDetailDaoImpl
extends SimpleJdbcDaoSupport
implements OTPRevisiPenjualanDetailDao {
    public OTPRevisiPenjualanDetailDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<OTPRevisiPenjualanDetail> getOTPRevisiPenjualanDetailById(int idOTPRevisiPenjualan) {
        String sql = "SELECT * FROM t_otp_revisi_penjualan_detail WHERE t_otp_revisi_penjualan_id = ?";
        List details = null;
        try {
            details = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new OTPRevisiPenjualanDetailRowMapper(), new Object[]{idOTPRevisiPenjualan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return details;
    }

    @Override
    public int[] insertOTPRevisiPenjualanDetail(List<OTPRevisiPenjualanDetail> otpRevisiPenjualanDetails) {
        String sql = "INSERT INTO t_otp_revisi_penjualan_detail (t_otp_revisi_penjualan_id, id_sku, qty_before, satuan_before, harga_before, discount_before, total_before, qty_after, satuan_after, harga_after, discount_after, total_after, id_cabang) VALUES (:t_otp_revisi_penjualan_id, :id_sku, :qty_before, :satuan_before, :harga_before, :discount_before, :total_before, :qty_after, :satuan_after, :harga_after, :discount_after, :total_after, :id_cabang)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])otpRevisiPenjualanDetails.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }
}

