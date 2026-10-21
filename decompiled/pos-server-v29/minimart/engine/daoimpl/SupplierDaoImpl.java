/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.SupplierDao;
import minimart.engine.model.Supplier;
import minimart.engine.rowmapper.SupplierRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class SupplierDaoImpl
extends SimpleJdbcDaoSupport
implements SupplierDao {
    public SupplierDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createSupplier(Supplier supplier) {
        String sql = "INSERT INTO m_supplier (ID, NAMA, ALAMAT, KOTA, TELEPON,CONTACT_PERSON, AVAILABLE, CREATED, UPDATED, TOP) VALUES (:id, :nama, :alamat, :kota, :telepon, :contactPerson, :available, :created, :updated, :top)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)supplier);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Supplier getSupplier(String id) {
        String sql = "SELECT * FROM m_supplier WHERE ID = ?";
        Supplier supplier = null;
        try {
            supplier = (Supplier)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new SupplierRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return supplier;
    }

    @Override
    public List<Supplier> getSuppliers(String nama) {
        String sql = "SELECT * FROM m_supplier WHERE NAMA LIKE ?";
        List suppliers = null;
        try {
            nama = '%' + nama + '%';
            suppliers = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new SupplierRowMapper(), new Object[]{nama});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return suppliers;
    }

    @Override
    public int updateSupplier(Supplier supplier) {
        String sql = "UPDATE m_supplier SET NAMA=:nama, ALAMAT=:alamat, KOTA=:kota, TELEPON=:telepon, CONTACT_PERSON=:contactPerson, AVAILABLE=:available, UPDATED=:updated, TOP=:top WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)supplier);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteSupplier(String id) {
        String sql = "DELETE FROM m_supplier WHERE ID = ?";
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
    public String getLastId() {
        String sql = "SELECT ID FROM m_supplier ORDER BY ID DESC LIMIT ?";
        String id = "";
        try {
            id = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{1});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return id;
    }
}

