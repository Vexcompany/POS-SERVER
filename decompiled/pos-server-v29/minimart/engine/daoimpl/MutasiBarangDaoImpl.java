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
import minimart.engine.dao.MutasiBarangDao;
import minimart.engine.model.MutasiBarang;
import minimart.engine.rowmapper.MutasiBarangRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class MutasiBarangDaoImpl
extends SimpleJdbcDaoSupport
implements MutasiBarangDao {
    public MutasiBarangDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createMutasiBarang(MutasiBarang mutasiBarang) {
        String sql = "INSERT INTO t_mutasi_barang (ID, M_KARYAWAN_ID, TANGGAL, DARI, MENUJU, KETERANGAN, CREATED, UPDATED) VALUES (:id, :karyawanId, :tanggal, :dari, :menuju, :keterangan, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)mutasiBarang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateMutasiBarang(MutasiBarang mutasiBarang) {
        String sql = "UPDATE t_mutasi_barang SET M_KARYAWAN_ID=:karyawanId, TANGGAL=:tanggal, DARI=:dari, MENUJU=:menuju, KETERANGAN=:keterangan, UPDATED=:updated WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)mutasiBarang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<MutasiBarang> getPembelian(String startDate, String endDate) {
        String sql = "SELECT * FROM t_mutasi_barang WHERE TANGGAL >= ? AND TANGGAL <= ? ";
        List pembelians = null;
        try {
            pembelians = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new MutasiBarangRowMapper(), new Object[]{startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelians;
    }

    @Override
    public MutasiBarang getMutasiBarang(String id) {
        String sql = "SELECT * FROM t_mutasi_barang WHERE ID = ?";
        MutasiBarang mutasiBarang = null;
        try {
            mutasiBarang = (MutasiBarang)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new MutasiBarangRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return mutasiBarang;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM t_mutasi_barang ORDER BY ID DESC LIMIT ?";
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

