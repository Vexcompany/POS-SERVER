/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.RepackDetailDao;
import minimart.engine.model.RepackDetail;
import minimart.engine.rowmapper.RepackDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class RepackDetailDaoImpl
extends SimpleJdbcDaoSupport
implements RepackDetailDao {
    public RepackDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createdRepackDetail(RepackDetail repackDetail) {
        String sql = "INSERT INTO t_repack_detail (ID, T_REPACK_ID, M_BARANG, JUMLAH, SATUAN) VALUES (:id, :repackId, :barangId, :jumlah, :satuan)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)repackDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] createRepackDetailBatch(List<RepackDetail> repackDetails) {
        String sql = "INSERT INTO t_repack_detail (ID, T_REPACK_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN) VALUES (:id, :repackId, :barangId, :jumlahBarang, :satuan)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])repackDetails.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public List<RepackDetail> getRepackDetail(String idRepack) {
        String sql = "SELECT * FROM t_repack_detail WHERE t_repack_id = ?";
        List repackDetails = null;
        try {
            repackDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new RepackDetailRowMapper(), new Object[]{idRepack});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return repackDetails;
    }

    @Override
    public List<RepackDetail> getRepackDetail(String startDate, String endDate, String m_barang_id) {
        String sql = "SELECT * FROM t_repack_detail join t_repack on (t_repack_detail.t_repack_id = t_repack.id) WHERE tanggal >= ? and tanggal <= ? and m_barang_id = ? ";
        List repackDetails = null;
        try {
            repackDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new RepackDetailRowMapper(), new Object[]{startDate, endDate, m_barang_id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return repackDetails;
    }
}

