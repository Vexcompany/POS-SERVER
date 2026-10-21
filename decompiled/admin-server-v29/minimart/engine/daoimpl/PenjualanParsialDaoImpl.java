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
import minimart.engine.dao.PenjualanParsialDao;
import minimart.engine.model.PenjualanParsial;
import minimart.engine.rowmapper.PenjualanParsialRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PenjualanParsialDaoImpl
extends SimpleJdbcDaoSupport
implements PenjualanParsialDao {
    public PenjualanParsialDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM t_penjualan_parsial ORDER BY ID DESC LIMIT ?";
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
    public List<PenjualanParsial> getPenjualanTerakhir(String date) {
        System.out.println(date);
        String sql = "SELECT * FROM t_penjualan_parsial WHERE TANGGAL = ?";
        List penjualans = null;
        try {
            penjualans = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenjualanParsialRowMapper(), new Object[]{date});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public int create(PenjualanParsial penjualanParsial) {
        String sql = "INSERT INTO t_penjualan_parsial (ID, m_customer_id, m_karyawan_id, TANGGAL) VALUES (:id, :customerId, :karyawanId, :tanggal)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penjualanParsial);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int update(PenjualanParsial penjualanParsial) {
        String sql = "UPDATE t_penjualan_parsial SET M_CUSTOMER_ID=:customerId, M_KARYAWAN_ID=:karyawanId, TANGGAL=:tanggal WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penjualanParsial);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PenjualanParsial getPenjualanParsial(String penjualanParsialId) {
        String sql = "SELECT * FROM t_penjualan_parsial WHERE ID = ? ";
        PenjualanParsial penjualanParsial = null;
        try {
            penjualanParsial = (PenjualanParsial)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PenjualanParsialRowMapper(), new Object[]{penjualanParsialId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualanParsial;
    }

    @Override
    public int delete(String penjualanParsialId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

