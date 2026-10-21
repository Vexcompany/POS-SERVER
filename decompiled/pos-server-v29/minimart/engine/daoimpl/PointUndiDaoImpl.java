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
import minimart.engine.dao.PointUndiDao;
import minimart.engine.model.PointUndi;
import minimart.engine.rowmapper.PointUndiRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PointUndiDaoImpl
extends SimpleJdbcDaoSupport
implements PointUndiDao {
    public PointUndiDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPointUndi(PointUndi pu) {
        String sql = "INSERT INTO promo_undi (ID, TANGGAL_MULAI, TANGGAL_BERAKHIR, MINIMUM_TRANSAKSI, STATUS) VALUES (:id, :tanggalMulai, :tanggalBerakhir, :minimumTransaksi, :status)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pu);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePointUndi(PointUndi pu) {
        String sql = "UPDATE promo_undi SET TANGGAL_MULAI=:tanggalMulai, TANGGAL_BERAKHIR=:tanggalBerakhir, MINIMUM_TRANSAKSI=:minimumTransaksi, STATUS=:status WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pu);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePointUndi(int pointUndiId) {
        String sql = "DELETE FROM promo_undi WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{pointUndiId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PointUndi getPointUndi(int pointUndiId) {
        String sql = "SELECT * FROM promo_hemat WHERE ID = ? ";
        PointUndi pointUndi = null;
        try {
            pointUndi = (PointUndi)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PointUndiRowMapper(), new Object[]{pointUndiId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pointUndi;
    }
}

