/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.CustomerCommitDao;
import minimart.engine.daoimpl.KonstantaDaoImpl;
import minimart.engine.model.Customer;
import minimart.engine.model.CustomerCommit;
import minimart.engine.rowmapper.CustomerCommitRowMapper;
import minimart.engine.rowmapper.CustomerLogRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class CustomerCommitDaoImpl
extends SimpleJdbcDaoSupport
implements CustomerCommitDao {
    public CustomerCommitDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public List<CustomerCommit> getAllCommits() {
        String sql = "CALL sp_read_all_customer_commit()";
        List result = null;
        try {
            result = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerCommitRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<CustomerCommit> getUncommitted() {
        String sql = "CALL sp_read_uncommitted_customer_commit()";
        List result = null;
        try {
            result = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerCommitRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println(result);
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Customer getUncommittedLog(String idCommit) {
        String sql = "CALL sp_read_customer_log(?)";
        Customer result = null;
        try {
            result = (Customer)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new CustomerLogRowMapper(), new Object[]{idCommit});
        }
        catch (DataAccessException e) {
            System.out.println(result);
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<CustomerCommit> getQueryTypeCommits(String queryType) {
        String sql = "CALL sp_read_query_customer_commit(?)";
        List result = null;
        try {
            result = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerCommitRowMapper(), new Object[]{queryType});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public CustomerCommit insertCustomerCommit(Customer customerCommit, String query) {
        String sql = "CALL sp_insert_customer_commit(?,?,?)";
        List result = null;
        try {
            result = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerCommitRowMapper(), new Object[]{customerCommit.getId(), query, "ACTIVE"});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return (CustomerCommit)result.get(0);
    }

    @Override
    public int deleteCustomerCommit(CustomerCommit idCommit) {
        String sql = "CALL sp_delete_commit_customer(?)";
        int result = 0;
        try {
            result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{idCommit.getCustomerTransactionCommitId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Long getPointUncommited(String id) {
        String sql = "SELECT coalesce(sum(point),0) FROM o_point_transaction_commit where status_commit = 0 and ktp_customer = ?";
        Long result = new Long(0L);
        try {
            result = (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateStatusCommit(String id) {
        String sql = "UPDATE o_point_transaction_commit SET status_commit = 1 WHERE ktp_customer = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public CustomerCommit updateCustomerCommit(CustomerCommit idCommit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CustomerCommit insertCustomerCommitLog(Customer customerCommit, String commitId) {
        String sql = "CALL sp_insert_customer_log(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        KonstantaDaoImpl kd = new KonstantaDaoImpl();
        List result = null;
        try {
            result = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerCommitRowMapper(), new Object[]{commitId, customerCommit.getId(), customerCommit.getKtp(), customerCommit.getNama(), customerCommit.getAlamat(), customerCommit.getTelepon(), customerCommit.getHutang(), customerCommit.getKota(), customerCommit.getPoint(), customerCommit.getTanggalLahir(), customerCommit.getLimitKredit(), customerCommit.getTop(), customerCommit.getTipe(), kd.getConstantValue("idCabang")});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return (CustomerCommit)result.get(0);
    }

    @Override
    public int commitCustomerCommit(CustomerCommit idCommit) {
        String sql = "CALL sp_commit_customer(?)";
        int result = 0;
        try {
            result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{idCommit.getCustomerTransactionCommitId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

