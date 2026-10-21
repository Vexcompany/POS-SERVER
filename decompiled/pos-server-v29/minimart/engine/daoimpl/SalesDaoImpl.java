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
import minimart.engine.dao.SalesDao;
import minimart.engine.model.Sales;
import minimart.engine.rowmapper.SalesRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class SalesDaoImpl
extends SimpleJdbcDaoSupport
implements SalesDao {
    public SalesDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createSales(Sales sales) {
        String sql = "INSERT INTO m_sales (id, ktp, nama, alamat, telepon, target) VALUES (:id, :ktp, :nama, :alamat, :telepon, :target)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)sales);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Sales getSales(String id) {
        String sql = "SELECT * FROM m_sales WHERE id = ?";
        Sales sales = null;
        try {
            sales = (Sales)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new SalesRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println((Object)e);
        }
        return sales;
    }

    @Override
    public int updateSales(Sales sales) {
        String sql = "UPDATE m_sales SET ktp=:ktp, nama=:nama, alamat=:alamat, telepon=:telepon, target=:target WHERE id=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)sales);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteSales(String id) {
        String sql = "DELETE FROM m_sales WHERE id = ?";
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
    public List<Sales> getSaleses(String nama) {
        String sql = "SELECT * FROM m_sales WHERE nama LIKE ?";
        List sales = null;
        try {
            nama = '%' + nama + '%';
            sales = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new SalesRowMapper(), new Object[]{nama});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return sales;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM m_sales ORDER BY ID DESC LIMIT ?";
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
    public int[] upsertSalesFromOnline(List<Sales> saleses) {
        int[] results = null;
        String sql_upsert = "INSERT INTO m_sales (id, ktp, nama, alamat, telepon, target) VALUES(:id, :ktp, :nama, :alamat, :telepon, :target) ON DUPLICATE KEY UPDATE ktp = VALUES(ktp), nama = VALUES(nama), alamat = VALUES(alamat), telepon = VALUES(telepon), target = VALUES(target)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])saleses.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql_upsert, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }
}

