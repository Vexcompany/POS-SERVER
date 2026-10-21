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
import minimart.engine.dao.PromoPeriodeDao;
import minimart.engine.model.PromoPeriode;
import minimart.engine.rowmapper.PromoPeriodeRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoPeriodeDaoImpl
extends SimpleJdbcDaoSupport
implements PromoPeriodeDao {
    public PromoPeriodeDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPromoPeriode(PromoPeriode promoPeriode) {
        String sql = "INSERT INTO promo_periode (ID, M_BARANG_ID, PESAN, MINIMAL, AKTIFKAN) VALUES (:id, :barangId, :pesan, :minimal, :aktifkan)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoPeriode);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean isNew() {
        String sql = "SELECT COUNT(ID)FROM promo_periode";
        boolean isNew = true;
        int count = 0;
        try {
            count = this.getSimpleJdbcTemplate().queryForInt(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        isNew = count == 0;
        return isNew;
    }

    @Override
    public int updatePromoPeriode(PromoPeriode promoPeriode) {
        String sql = "UPDATE promo_periode SET M_BARANG_ID=:barangId, PESAN=:pesan, MINIMAL =:minimal ,AKTIFKAN=:aktifkan WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoPeriode);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PromoPeriode getPromoPeriode(int id) {
        String sql = "SELECT * FROM promo_periode WHERE ID = ? ";
        PromoPeriode promoPeriode = null;
        try {
            promoPeriode = (PromoPeriode)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoPeriodeRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoPeriode;
    }
}

