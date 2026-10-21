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

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.VoucherDao;
import minimart.engine.model.Voucher;
import minimart.engine.model_online.VoucherO2O;
import minimart.engine.rowmapper.VoucherO2ORowMapper;
import minimart.engine.rowmapper.VoucherRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class VoucherDaoImpl
extends SimpleJdbcDaoSupport
implements VoucherDao {
    public VoucherDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createVoucher(Voucher voucher) {
        String sql = "INSERT INTO m_voucher (ID, M_CUSTOMER_ID, JUMLAH, AVAILABLE, EXPIRED_DATE, EXCHANGE_DATE) VALUES (:id, :customerId, :jumlah, :available, :expiredDate, :exchangeDate)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)voucher);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Voucher getVoucher(String id) {
        String sql = "SELECT * FROM m_voucher WHERE ID = ?";
        Voucher customer = null;
        try {
            customer = (Voucher)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new VoucherRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public int updateVoucher(Voucher voucher) {
        String sql = "UPDATE m_voucher SET M_CUSTOMER_ID=:customerId, JUMLAH=:jumlah, AVAILABLE=:available, EXPIRED_DATE=:expiredDate, EXCHANGE_DATE=:exchangeDate, status_sync_online=0 WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)voucher);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM m_voucher ORDER BY ID DESC LIMIT ?";
        String id = "";
        try {
            id = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{1});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return id;
    }

    @Override
    public List<Voucher> getVoucher(String startDate, String endDate) {
        String sql = "SELECT * FROM m_voucher WHERE exchange_date >= ? AND exchange_date <= ? ";
        List pembelians = null;
        try {
            pembelians = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new VoucherRowMapper(), new Object[]{startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelians;
    }

    @Override
    public List<VoucherO2O> getAllVoucherUnsynced() {
        ArrayList<VoucherO2O> list = new ArrayList();
        String sql = "SELECT * FROM m_voucher WHERE status_sync_online = false";
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new VoucherO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int updateVoucherUnsynced(String ids) {
        int result = -1;
        String sql = "UPDATE m_voucher SET status_sync_online = true WHERE id IN (" + ids + ")";
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertDataVoucherDariOnline(VoucherO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO m_voucher (id, m_customer_id, jumlah, available, expired_date, exchange_date, m_shift_kasir_id, status_sync_online, created, updated) VALUES (:id_voucher_offline, :m_customer_id, :jumlah, :available, :expired_date, :exchange_date, :shiftKasirId, true, :created, :updated) ON DUPLICATE KEY UPDATE m_customer_id = VALUES(m_customer_id), jumlah = VALUES(jumlah), available = VALUES(available), expired_date = VALUES(expired_date), exchange_date = VALUES(exchange_date), status_sync_online = true, created = VALUES(created), updated = VALUES(updated);";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])datas);
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql_upsert, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public String getLatestDate() {
        String result = null;
        String sql = "SELECT DATE_FORMAT(GREATEST(coalesce(MAX(created), 0), coalesce(MAX(updated), 0)), '%Y-%m-%d') FROM m_voucher";
        try {
            result = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

