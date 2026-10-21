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
import minimart.engine.dao.StokOpnamMandiriDao;
import minimart.engine.model.StokOpnamMandiri;
import minimart.engine.rowmapper.StokOpnamMandiriRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class StokOpnamMandiriDaoImpl
extends SimpleJdbcDaoSupport
implements StokOpnamMandiriDao {
    public StokOpnamMandiriDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int[] createBatchStokOpnamMandiri(List<StokOpnamMandiri> stokOpnamMandiris) {
        String sql = "INSERT INTO STOK_OPNAM_MANDIRI (ID, TANGGAL, M_KARYAWAN_ID, TANGGAL_SUBMIT, TANGGAL_INPUT_1, TANGGAL_INPUT_2, TANGGAL_INPUT_3, CREATED, UPDATED, KETERANGAN, TIPE, BLIND_SOM) VALUES (:id, :tanggal, :karyawanId, :tanggalSubmit, :tanggalInput1, :tanggalInput2, :tanggalInput3, :created, :updated, :keterangan, :tipe, :blindSom)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])stokOpnamMandiris.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int[] createBatchStokOpnamMandiriIgnoreDuplicate(List<StokOpnamMandiri> stokOpnamMandiris) {
        String sql = "INSERT IGNORE INTO STOK_OPNAM_MANDIRI (ID, TANGGAL, M_KARYAWAN_ID, TANGGAL_SUBMIT, TANGGAL_INPUT_1, TANGGAL_INPUT_2, TANGGAL_INPUT_3, CREATED, UPDATED, KETERANGAN, TIPE, BLIND_SOM) VALUES (:id, :tanggal, :karyawanId, :tanggalSubmit, :tanggalInput1, :tanggalInput2, :tanggalInput3, :created, :updated, :keterangan, :tipe, :blindSom)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])stokOpnamMandiris.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int updateStokOpnamMandiri(StokOpnamMandiri stokOpnamMandiri) {
        System.out.println(stokOpnamMandiri.getTanggalInput1());
        System.out.println(stokOpnamMandiri.getTanggalInput2());
        System.out.println(stokOpnamMandiri.getTanggalInput3());
        String sql = "UPDATE stok_opnam_mandiri SET TANGGAL=:tanggal, M_KARYAWAN_ID=:karyawanId, TANGGAL_SUBMIT=:tanggalSubmit, TANGGAL_INPUT_1=:tanggalInput1, TANGGAL_INPUT_2=:tanggalInput2, TANGGAL_INPUT_3=:tanggalInput3, CREATED=:created, UPDATED=:updated, KETERANGAN=:keterangan, TIPE=:tipe, BLIND_SOM=:blindSom WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)stokOpnamMandiri);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getTanggalInput(int id, int input) {
        String sql = "";
        if (input == 1) {
            sql = "SELECT TANGGAL_INPUT_1 FROM stok_opnam_mandiri where id = ?";
        } else if (input == 2) {
            sql = "SELECT TANGGAL_INPUT_2 FROM stok_opnam_mandiri where id = ?";
        }
        if (input == 3) {
            sql = "SELECT TANGGAL_INPUT_3 FROM stok_opnam_mandiri where id = ?";
        }
        String result = "";
        try {
            result = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public StokOpnamMandiri getStokOpnamMandiri(int id) {
        String sql = "SELECT * FROM stok_opnam_mandiri WHERE ID = ? ";
        StokOpnamMandiri stokOpnamMandiri = null;
        try {
            stokOpnamMandiri = (StokOpnamMandiri)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new StokOpnamMandiriRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamMandiri;
    }

    @Override
    public List<StokOpnamMandiri> getStokOpnamMandiriUnSubmit() {
        String sql = "SELECT * FROM STOK_OPNAM_MANDIRI WHERE TANGGAL_SUBMIT IS NULL";
        List stokOpnamMandiris = null;
        try {
            stokOpnamMandiris = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamMandiriRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamMandiris;
    }

    @Override
    public List<StokOpnamMandiri> getStokOpnamMandiriPastSixMonth() {
        String sql = "SELECT * FROM STOK_OPNAM_MANDIRI WHERE tanggal BETWEEN DATE_SUB(NOW(), INTERVAL 180 DAY) AND NOW()";
        List stokOpnamMandiris = null;
        try {
            stokOpnamMandiris = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamMandiriRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamMandiris;
    }

    @Override
    public List<StokOpnamMandiri> getStokOpnamMandiriPastWeek() {
        String sql = "SELECT * FROM STOK_OPNAM_MANDIRI WHERE tanggal_submit BETWEEN DATE_SUB(NOW(), INTERVAL 7 DAY) AND NOW()";
        List stokOpnamMandiris = null;
        try {
            stokOpnamMandiris = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamMandiriRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamMandiris;
    }

    @Override
    public int deleteBatchStokOpnamMandiri(String ids) {
        String sql = "DELETE FROM stok_opnam_mandiri WHERE ID IN (" + ids + ") and tanggal_submit is NULL";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<StokOpnamMandiri> getSomByTanggal(String startDate, String endDate) {
        String sql = "SELECT * FROM STOK_OPNAM_MANDIRI WHERE tanggal >= ? and tanggal <=?";
        List stokOpnamMandiris = null;
        try {
            stokOpnamMandiris = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamMandiriRowMapper(), new Object[]{startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamMandiris;
    }
}

