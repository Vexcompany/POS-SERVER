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
import minimart.engine.dao.ParentBarangDao;
import minimart.engine.model.ParentBarang;
import minimart.engine.rowmapper.ParentBarangRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class ParentBarangDaoImpl
extends SimpleJdbcDaoSupport
implements ParentBarangDao {
    public ParentBarangDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createParent(ParentBarang parentBarang) {
        String sql = "INSERT INTO m_parent_barang (id, nama) VALUES (:id, :nama)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)parentBarang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int update(ParentBarang parentBarang) {
        String sql = "UPDATE m_parent_barang SET id=:id, nama=:nama WHERE id=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)parentBarang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<ParentBarang> getParents(String nama) {
        String sql = "SELECT * FROM m_parent_barang WHERE nama LIKE ? ORDER BY nama";
        List barangs = null;
        try {
            nama = '%' + nama + '%';
            barangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new ParentBarangRowMapper(), new Object[]{nama});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public ParentBarang getParentBarang(String id) {
        String sql = "SELECT * FROM m_parent_barang WHERE id = ?";
        ParentBarang parentBarang = null;
        try {
            parentBarang = (ParentBarang)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new ParentBarangRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return parentBarang;
    }
}

