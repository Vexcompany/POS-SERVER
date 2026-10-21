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
import minimart.engine.dao.KaryawanDao;
import minimart.engine.model.Karyawan;
import minimart.engine.rowmapper.KaryawanRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class KaryawanDaoImpl
extends SimpleJdbcDaoSupport
implements KaryawanDao {
    public KaryawanDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createKaryawan(Karyawan karyawan) {
        String sql = "INSERT INTO m_karyawan (id, nama, password, gaji, role, available, created, updated) VALUES (:id, :nama, :password, :gaji, :role, :available, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)karyawan);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Karyawan getKaryawan(String karyawanId) {
        String sql = "SELECT * FROM m_karyawan WHERE id = ? and available = 1";
        Karyawan karyawan = null;
        try {
            karyawan = (Karyawan)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new KaryawanRowMapper(), new Object[]{karyawanId});
        }
        catch (DataAccessException e) {
            System.out.println((Object)e);
        }
        return karyawan;
    }

    @Override
    public Karyawan getAllKaryawan(String karyawanId) {
        String sql = "SELECT * FROM m_karyawan WHERE id = ? ";
        Karyawan karyawan = null;
        try {
            karyawan = (Karyawan)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new KaryawanRowMapper(), new Object[]{karyawanId});
        }
        catch (DataAccessException e) {
            System.out.println((Object)e);
        }
        return karyawan;
    }

    @Override
    public Karyawan getKaryawanByPassword(String karyawanPassword) {
        String sql = "SELECT * FROM m_karyawan WHERE password = ? and available = 1";
        Karyawan karyawan = null;
        try {
            karyawan = (Karyawan)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new KaryawanRowMapper(), new Object[]{karyawanPassword});
        }
        catch (DataAccessException e) {
            System.out.println((Object)e);
        }
        return karyawan;
    }

    @Override
    public int updateKaryawan(Karyawan karyawan) {
        String sql = "UPDATE m_karyawan SET nama=:nama, password=:password, gaji=:gaji, role=:role,available=:available, updated=:updated WHERE id=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)karyawan);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteKaryawan(String id) {
        String sql = "DELETE FROM m_karyawan WHERE id = ?";
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
    public List<Karyawan> getKaryawans(String nama) {
        String sql = "SELECT * FROM m_karyawan WHERE nama LIKE ? and available = 1";
        List karyawan = null;
        try {
            nama = '%' + nama + '%';
            karyawan = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new KaryawanRowMapper(), new Object[]{nama});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return karyawan;
    }

    @Override
    public int[] upsertKaryawan(List<Karyawan> datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO m_karyawan (id, nama, password, gaji, role, available, created, updated) VALUES(:id, :nama, :password, :gaji, :role, :available, :created, :updated) ON DUPLICATE KEY UPDATE nama = VALUES(nama), password = VALUES(password), gaji = VALUES(gaji), role = VALUES(role), available = VALUES(available), created = VALUES(created), updated = VALUES(updated)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])datas.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql_upsert, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int softDeleteKaryawan(String ids) {
        String sql = "UPDATE m_karyawan SET available = 0 WHERE id in (" + ids + ")";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

