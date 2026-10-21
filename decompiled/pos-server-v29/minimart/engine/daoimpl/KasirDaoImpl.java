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
import minimart.engine.dao.KasirDao;
import minimart.engine.model.Kasir;
import minimart.engine.rowmapper.KasirRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class KasirDaoImpl
extends SimpleJdbcDaoSupport
implements KasirDao {
    public KasirDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createKasir(Kasir kasir) {
        String sql = "INSERT INTO m_kasir (ID, CREATED, UPDATED) VALUES (:id, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)kasir);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Kasir> getKasirs() {
        String sql = "SELECT * FROM m_kasir";
        List kasirs = null;
        try {
            kasirs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new KasirRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return kasirs;
    }

    @Override
    public boolean isKasirHasCreated(int id) {
        String sql = "SELECT * FROM m_kasir WHERE ID = ?";
        Kasir kasir = null;
        try {
            kasir = (Kasir)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new KasirRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return kasir != null;
    }
}

