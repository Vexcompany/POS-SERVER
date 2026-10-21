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

import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.BrandDao;
import minimart.engine.model.Brand;
import minimart.engine.rowmapper.BrandRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class BrandDaoImpl
extends SimpleJdbcDaoSupport
implements BrandDao {
    public BrandDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int create(Brand brand) {
        String sql = "INSERT INTO m_brand (ID, NAMA_BRAND, CREATED, UPDATED, AVAILABLE) VALUES (:id, :nama_brand, :created, :updated, :available)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)brand);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Brand getBrand(String id) {
        String sql = "SELECT * FROM m_brand WHERE ID = ?";
        Brand jenisObj = null;
        try {
            jenisObj = (Brand)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new BrandRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return jenisObj;
    }

    @Override
    public int update(Brand brand) {
        String sql = "UPDATE m_brand SET nama_brand=? , created = ? , updated = ? , available = ? WHERE id = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{brand.getNamaBrand(), brand.getCreated(), brand.getUpdated(), brand.getAvailable(), brand.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

