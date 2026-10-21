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
import minimart.engine.dao.PaketDetailDao;
import minimart.engine.model.PaketDetail;
import minimart.engine.rowmapper.PaketDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PaketDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PaketDetailDao {
    public PaketDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int create(PaketDetail paketDetail) {
        String sql = "INSERT INTO m_paket_detail (ID, M_PAKET_ID, M_BARANG_ID, JUMLAH_BARANG) values (:id, :paketId, :barangId, :jumlahBarang)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)paketDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int delete(String idPaket, String idBarang) {
        String sql = "DELETE FROM M_PAKET_DETAIL WHERE M_PAKET_ID = ? AND M_BARANG_ID";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{idPaket, idBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PaketDetail> getPaketDetail(String paketID) {
        String sql = "SELECT * FROM M_PAKET_DETAIL WHERE M_PAKET_ID = ?";
        List paketDetails = null;
        try {
            paketDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PaketDetailRowMapper(), new Object[]{paketID});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return paketDetails;
    }

    @Override
    public int[] upsertPaketDetailBatch(List<PaketDetail> datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO m_paket_detail (id, m_paket_id, m_barang_id, jumlah_barang) VALUES (:id, :paketId, :barangId, :jumlahBarang) ON DUPLICATE KEY UPDATE m_paket_id = VALUES(m_paket_id), m_barang_id = VALUES(m_barang_id), jumlah_barang = VALUES(jumlah_barang)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])datas.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql_upsert, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int deletePaketDetailByIds(String ids) {
        String sql = "DELETE FROM m_paket_detail WHERE id in (" + ids + ")";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePaketDetailAll() {
        String sql = "DELETE FROM m_paket_detail";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PaketDetail> getPaketDetailAll() {
        String sql = "SELECT * FROM m_paket_detail";
        List pakets = null;
        try {
            pakets = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PaketDetailRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pakets;
    }
}

