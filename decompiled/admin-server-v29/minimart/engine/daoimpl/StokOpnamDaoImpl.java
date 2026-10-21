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

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.StokOpnamDao;
import minimart.engine.model.StokOpnam;
import minimart.engine.model_online.StokOpnamO2O;
import minimart.engine.rowmapper.StokOpnamO2ORowMapper;
import minimart.engine.rowmapper.StokOpnamRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class StokOpnamDaoImpl
extends SimpleJdbcDaoSupport
implements StokOpnamDao {
    private Helper helper = Helper.getInstance();

    public StokOpnamDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createStokOpnam(StokOpnam stokOpnam) {
        String sql = "INSERT INTO stok_opnam (ID, M_KARYAWAN_ID, STATUS_OPNAM, LOKASI, TIPE, TIPE_NAME, TANGGAL, KETERANGAN, UPDATED) VALUES (:id, :karyawanId, :statusOpnam, :lokasi, :tipe, :tipeName, :tanggal, :keterangan, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)stokOpnam);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public long getTotalOpnamById(int idStok) {
        long total = 0L;
        String sql = "select sum(total) from stok_opnam_detail where stok_opnam_id = ?";
        try {
            total = (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{idStok});
        }
        catch (DataAccessException e) {
            JOptionPane.showMessageDialog(null, "Stok Opnam=" + idStok + " kosong");
            System.out.println("Message: " + e.getMessage());
        }
        return total;
    }

    @Override
    public StokOpnam getStokOpnam(String id) {
        String sql = "SELECT * FROM stok_opnam WHERE ID = ? ";
        StokOpnam stokOpnam = null;
        try {
            stokOpnam = (StokOpnam)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new StokOpnamRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnam;
    }

    @Override
    public int updateStokOpnam(StokOpnam stokOpnam) {
        String sql = "UPDATE stok_opnam SET M_KARYAWAN_ID=:karyawanId, STATUS_OPNAM=:statusOpnam, TANGGAL=:tanggal, LOKASI=:lokasi, TIPE=:tipe, TIPE_NAME=:tipeName, KETERANGAN=:keterangan ,UPDATED=:updated, STATUS_SYNC_ONLINE = false WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)stokOpnam);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteStokOpnam(int id) {
        String sql = "DELETE FROM stok_opnam WHERE ID = ?";
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
    public List<StokOpnam> getStokOpnams(String statusOpnam, String startDate, String endDate) {
        String sql = "SELECT * FROM stok_opnam WHERE STATUS_OPNAM like ? AND TANGGAL>=? AND TANGGAL<=? ORDER BY TANGGAL DESC";
        List stokOpnams = null;
        statusOpnam = '%' + statusOpnam + '%';
        try {
            stokOpnams = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamRowMapper(), new Object[]{statusOpnam, startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnams;
    }

    @Override
    public int getLastId() {
        String sql = "SELECT ID FROM stok_opnam ORDER BY ID DESC LIMIT ?";
        int id = 0;
        try {
            id = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{1});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return id;
    }

    @Override
    public List<StokOpnamO2O> getAllStokOpnamUnsynced() {
        ArrayList<StokOpnamO2O> list = new ArrayList();
        String sql = "SELECT * FROM stok_opnam WHERE status_sync_online = false";
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int updateStokOpnamUnsynced(StokOpnamO2O[] data) {
        int result = -1;
        String sql = "UPDATE stok_opnam SET status_sync_online = true WHERE id = :id_stok_opnam_offline";
        try {
            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])data);
            int[] results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
            result = results.length;
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateStokOpnamUnsynced(String ids) {
        int result = -1;
        String sql = "UPDATE stok_opnam SET status_sync_online = true WHERE id IN (" + ids + ")";
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertDataStokOpnamDariOnline(StokOpnamO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO stok_opnam (id, m_karyawan_id, status_opnam, lokasi, tipe, tipe_name, tanggal, keterangan, updated, status_sync_online, created) VALUES(:id_stok_opnam_offline, :m_karyawan_id, :status_opnam, :lokasi, :tipe, :tipe_name, :tanggal, :keterangan, :updated, true, :created) ON DUPLICATE KEY UPDATE m_karyawan_id = VALUES(m_karyawan_id), status_opnam = VALUES(status_opnam), lokasi = VALUES(lokasi), tipe = VALUES(tipe), tipe_name = VALUES(tipe_name), tanggal = VALUES(tanggal), keterangan = VALUES(keterangan), updated = VALUES(updated), status_sync_online = true, created = VALUES(created)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])datas);
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql_upsert, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public String getLatestDate() {
        String result = null;
        String sql = "SELECT DATE_FORMAT(GREATEST(coalesce(MAX(created), 0), coalesce(MAX(updated), 0)), '%Y-%m-%d') FROM stok_opnam";
        try {
            result = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

