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
import minimart.engine.dao.JenisDao;
import minimart.engine.model.Jenis;
import minimart.engine.rowmapper.JenisRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JenisDaoImpl
extends SimpleJdbcDaoSupport
implements JenisDao {
    public JenisDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createJenis(Jenis jenis) {
        String sql = "INSERT INTO m_jenis (JENIS, POIN_SALES, CREATED, UPDATED, MARGIN_CABANG, MARGIN_TEMPO) VALUES (:jenis, :poinSales, :created, :updated, :marginCabang, :marginTempo)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)jenis);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Jenis getJenis(String jenis) {
        String sql = "SELECT * FROM m_jenis WHERE JENIS = ?";
        Jenis jenisObj = null;
        try {
            jenisObj = (Jenis)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new JenisRowMapper(), new Object[]{jenis});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return jenisObj;
    }

    @Override
    public List<Jenis> getJenisses(String jenis) {
        String sql = "SELECT * FROM m_jenis WHERE JENIS LIKE ?";
        List barangs = null;
        try {
            jenis = '%' + jenis + '%';
            barangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new JenisRowMapper(), new Object[]{jenis});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public int updateJenis(String jenis, int poinSales) {
        String sql = "UPDATE m_jenis set poin_sales = ? where jenis =?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{poinSales, jenis});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateJenis(Jenis jenis) {
        String sql = "UPDATE m_jenis SET jenis=? , poin_sales = ? , margin_cabang = ?, margin_tempo = ? WHERE jenis = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{jenis.getJenis(), jenis.getPoinSales(), Float.valueOf(jenis.getMarginCabang()), Float.valueOf(jenis.getMarginTempo()), jenis.getJenis()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteJenis(String jenis) {
        String sql = "DELETE FROM m_jenis WHERE JENIS = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{jenis});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

