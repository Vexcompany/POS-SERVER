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
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.PelunasanHutangDao;
import minimart.engine.model.PelunasanHutang;
import minimart.engine.model_online.PelunasanHutangO2O;
import minimart.engine.rowmapper.PelunasanHutangO2ORowMapper;
import minimart.engine.rowmapper.PelunasanHutangRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PelunasanHutangDaoImpl
extends SimpleJdbcDaoSupport
implements PelunasanHutangDao {
    public PelunasanHutangDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPelunasanHutang(PelunasanHutang pelunasanHutang) {
        String sql = "INSERT INTO t_pelunasan_hutang (ID, T_PEMBELIAN_ID, M_KARYAWAN_ID, TANGGAL_PELUNASAN, JUMLAH, KETERANGAN) VALUES (:id, :pembelianId, :karyawanId, :tanggalPelunasan, :jumlah,:keterangan)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pelunasanHutang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePelunasanHutang(PelunasanHutang pelunasanHutang) {
        String sql = "UPDATE t_pelunasan_hutang SET T_PEMBELIAN_ID =:pembelianId, M_KARYAWAN_ID =:karyawanId, TANGGAL_PELUNASAN =:tanggalPelunasan, JUMLAH =:jumlah,KETERANGAN =:keterangan WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pelunasanHutang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PelunasanHutang> getPelunasanHutang(String supplierId, String tanggalAwal, String tanggalAkhir, String keterangan) {
        String sql = "SELECT * FROM t_pelunasan_hutang join T_PEMBELIAN on (T_PELUNASAN_HUTANG.T_PEMBELIAN_ID = T_PEMBELIAN.ID) WHERE TANGGAL_PELUNASAN >= ? AND TANGGAL_PELUNASAN <= ? AND M_SUPPLIER_ID LIKE ? AND T_PELUNASAN_HUTANG.KETERANGAN LIKE ? ORDER BY TANGGAL_PELUNASAN DESC";
        List pelunasanHutangs = null;
        try {
            supplierId = '%' + supplierId + '%';
            keterangan = '%' + keterangan + '%';
            pelunasanHutangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PelunasanHutangRowMapper(), new Object[]{tanggalAwal, tanggalAkhir, supplierId, keterangan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pelunasanHutangs;
    }

    @Override
    public List<PelunasanHutangO2O> getAllPelunasanHutangUnsynced() {
        ArrayList<PelunasanHutangO2O> list = new ArrayList();
        String sql = "SELECT * FROM t_pelunasan_hutang WHERE status_sync_online = false";
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PelunasanHutangO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int updateStatusSynced(List<PelunasanHutangO2O> list) {
        int result = -1;
        String sql = "UPDATE t_pelunasan_hutang SET status_sync_online = true WHERE id= :id_pelunasan_hutang_offline";
        String sql_updatePelunasanHutang = "UPDATE t_pembelian SET pelunasan = 'LUNAS' WHERE id = :t_pembelian_id";
        try {
            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])list.toArray());
            this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
            this.getSimpleJdbcTemplate().batchUpdate(sql_updatePelunasanHutang, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateStatusSynced(PelunasanHutangO2O[] list) {
        int result = -1;
        int[] results = null;
        int[] resultsUpdatePembelian = null;
        String sql = "UPDATE t_pelunasan_hutang SET status_sync_online = true WHERE id= :id_pelunasan_hutang_offline";
        String sql_updatePelunasanHutang = "UPDATE t_pembelian SET pelunasan = 'LUNAS' WHERE id = :t_pembelian_id";
        try {
            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])list);
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
            resultsUpdatePembelian = this.getSimpleJdbcTemplate().batchUpdate(sql_updatePelunasanHutang, batch);
            System.out.println("Pelunasan Hutang Service -> " + results.length + " / " + resultsUpdatePembelian.length);
            if (results != null && resultsUpdatePembelian != null) {
                result = 1;
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertDataPelunasanHutangDariOnline(PelunasanHutangO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO t_pelunasan_hutang (id, t_pembelian_id, m_karyawan_id, tanggal_pelunasan, keterangan, jumlah, status_sync_online, created, updated) VALUES(:id_pelunasan_hutang_offline, :t_pembelian_id, :m_karyawan_id, :tanggal_pelunasan, :keterangan, :jumlah, true, :created, :updated) ON DUPLICATE KEY UPDATE t_pembelian_id = VALUES(t_pembelian_id), m_karyawan_id = VALUES(m_karyawan_id), tanggal_pelunasan = VALUES(tanggal_pelunasan), keterangan = VALUES(keterangan), jumlah = VALUES(jumlah), status_sync_online = true, created = VALUES(created), updated = VALUES(updated)";
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
        String sql = "SELECT  DATE_FORMAT(GREATEST(coalesce(MAX(created), 0), coalesce(MAX(updated), 0)), '%Y-%m-%d') FROM t_pelunasan_hutang";
        try {
            result = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[0]);
            System.out.println("Result getLatestDate: " + result);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

