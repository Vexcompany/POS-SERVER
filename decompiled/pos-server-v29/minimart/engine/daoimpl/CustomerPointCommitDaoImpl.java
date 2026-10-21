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
import minimart.engine.dao.CustomerPointCommitDao;
import minimart.engine.model.CustomerPointCommit;
import minimart.engine.rowmapper.CustomerCommitPointRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class CustomerPointCommitDaoImpl
extends SimpleJdbcDaoSupport
implements CustomerPointCommitDao {
    public CustomerPointCommitDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public List<CustomerPointCommit> getAllCommits() {
        String sql = "CALL sp_read_all_point_commit()";
        List result = null;
        try {
            result = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerCommitPointRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<CustomerPointCommit> getUnCommited() {
        String sql = "CALL sp_read_uncommitted_points()";
        List result = null;
        try {
            result = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerCommitPointRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<CustomerPointCommit> getQueryTypeCommits(String queryType) {
        String sql = "CALL sp_read_query_point_commit(?)";
        List result = null;
        try {
            result = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerCommitPointRowMapper(), new Object[]{queryType});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public CustomerPointCommit insertPointCommit(String cc, String p, float point, String query, String kelas) {
        String sql = "CALL sp_insert_point_commit(?,?,?,?,?)";
        List result = null;
        try {
            result = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerCommitPointRowMapper(), new Object[]{cc, p, query, Float.valueOf(point), kelas});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return (CustomerPointCommit)result.get(0);
    }

    @Override
    public int commitPointCommit(CustomerPointCommit element) {
        String sql = "CALL sp_commit_point(?)";
        int result = 0;
        try {
            result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{element.getpointTransactionCommitId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

