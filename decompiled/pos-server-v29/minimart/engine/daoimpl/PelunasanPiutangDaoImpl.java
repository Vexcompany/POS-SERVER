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
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.PelunasanPiutangDao;
import minimart.engine.model.PelunasanPiutang;
import minimart.engine.model_online.PelunasanPiutangO2O;
import minimart.engine.rowmapper.PelunasanPiutangO2ORowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PelunasanPiutangDaoImpl
extends SimpleJdbcDaoSupport
implements PelunasanPiutangDao {
    public PelunasanPiutangDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPelunasanPiutang(PelunasanPiutang pelunasanPiutang) {
        String sql = "INSERT INTO t_pelunasan_piutang (ID, T_PENJUALAN_ID, M_KARYAWAN_ID, TANGGAL_PELUNASAN, KETERANGAN, JUMLAH_PELUNASAN, M_SHIFT_KASIR_ID) VALUES (:id, :penjualanId, :karyawanId, :tanggalPelunasan, :keterangan, :jumlahPelunasan,:shiftKasirId)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pelunasanPiutang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] createPelunasanPiutangBatch(List<PelunasanPiutang> pelunasanPiutang) {
        String sql = "INSERT INTO t_pelunasan_piutang (ID, T_PENJUALAN_ID, M_KARYAWAN_ID, TANGGAL_PELUNASAN, KETERANGAN, JUMLAH_PELUNASAN, M_SHIFT_KASIR_ID) VALUES (:id, :penjualanId, :karyawanId, :tanggalPelunasan, :keterangan, :jumlahPelunasan, :shiftKasirId)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])pelunasanPiutang.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int updatePelunasanPiutang(PelunasanPiutang pelunasanPiutang) {
        String sql = "UPDATE t_pelunasan_piutang SET T_PENJUALAN_ID =:penjualanId, M_KARYAWAN_ID =:karyawanId, TANGGAL_PELUNASAN =:tanggalPelunasan , KETERANGAN =:keterangan , JUMLAH_PELUNASAN=:jumlahPelunasan, M_SHIFT_KASIR_ID=:shiftKasirId WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pelunasanPiutang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Object[]> getPelunasanPiutang(String tanggalAwal, String tanggalAkhir, String customerId) {
        String sql = "SELECT t_pelunasan_piutang.id as pelunasan_id, t_pelunasan_piutang.tanggal_pelunasan as tanggal_pelunasan, t_penjualan.id as penjualan_id, t_penjualan.m_customer_id as customer_id, m_customer.nama as nama, t_pelunasan_piutang.m_karyawan_id as karyawan, jumlah_pelunasan, t_pelunasan_piutang.keterangan as keterangan FROM t_pelunasan_piutang join t_penjualan on (t_pelunasan_piutang.t_penjualan_id = t_penjualan.id) join m_customer on (t_penjualan.m_customer_id = m_customer.id) WHERE TANGGAL_PELUNASAN >= ? AND TANGGAL_PELUNASAN <= ? AND t_penjualan.M_CUSTOMER_ID LIKE ? ORDER BY TANGGAL_PELUNASAN ";
        ArrayList<Object[]> pelunasans = new ArrayList<Object[]>();
        customerId = '%' + customerId + '%';
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{tanggalAwal, tanggalAkhir, customerId});
            for (Map row : rows) {
                Object[] returnValue = new Object[9];
                returnValue[0] = row.get("pelunasan_id");
                returnValue[1] = row.get("tanggal_pelunasan");
                returnValue[2] = row.get("penjualan_id");
                returnValue[3] = row.get("customer_id");
                returnValue[4] = row.get("nama");
                returnValue[5] = row.get("karyawan");
                returnValue[6] = row.get("jumlah_pelunasan");
                returnValue[7] = row.get("keterangan");
                pelunasans.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pelunasans;
    }

    @Override
    public List<Object[]> getPelunasanPiutangByShift(int shiftKasirId) {
        String sql = "";
        sql = shiftKasirId == 0 ? "SELECT T_PENJUALAN_ID,TANGGAL_PELUNASAN,JUMLAH_PELUNASAN, KETERANGAN FROM T_PELUNASAN_PIUTANG" : "SELECT T_PENJUALAN_ID,TANGGAL_PELUNASAN,JUMLAH_PELUNASAN, KETERANGAN FROM T_PELUNASAN_PIUTANG WHERE M_SHIFT_KASIR_ID = ?";
        ArrayList<Object[]> pelunasans = new ArrayList<Object[]>();
        try {
            List rows = shiftKasirId == 0 ? this.getJdbcTemplate().queryForList(sql) : this.getJdbcTemplate().queryForList(sql, new Object[]{shiftKasirId});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("T_PENJUALAN_ID"), row.get("TANGGAL_PELUNASAN"), row.get("JUMLAH_PELUNASAN"), row.get("KETERANGAN")};
                pelunasans.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pelunasans;
    }

    @Override
    public List<Object[]> getPelunasanPiutangByShift(int shiftKasirId, String tanggal) {
        String sql = "";
        sql = shiftKasirId == 0 ? "SELECT T_PENJUALAN_ID,TANGGAL_PELUNASAN,JUMLAH_PELUNASAN, KETERANGAN FROM T_PELUNASAN_PIUTANG where TANGGAL_PELUNASAN = ?" : "SELECT T_PENJUALAN_ID,TANGGAL_PELUNASAN,JUMLAH_PELUNASAN, KETERANGAN FROM T_PELUNASAN_PIUTANG WHERE M_SHIFT_KASIR_ID = ? and TANGGAL_PELUNASAN = ?";
        ArrayList<Object[]> pelunasans = new ArrayList<Object[]>();
        try {
            List rows = shiftKasirId == 0 ? this.getJdbcTemplate().queryForList(sql, new Object[]{tanggal}) : this.getJdbcTemplate().queryForList(sql, new Object[]{shiftKasirId, tanggal});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("T_PENJUALAN_ID"), row.get("TANGGAL_PELUNASAN"), row.get("JUMLAH_PELUNASAN"), row.get("KETERANGAN")};
                pelunasans.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pelunasans;
    }

    @Override
    public List<PelunasanPiutangO2O> getAllPelunasanPiutangUnsynced() {
        ArrayList<PelunasanPiutangO2O> list = new ArrayList();
        String sql = "SELECT * FROM t_pelunasan_piutang WHERE status_sync_online = false";
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PelunasanPiutangO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int updateStatusSynced(List<PelunasanPiutangO2O> list) {
        int result = -1;
        int[] results = null;
        int[] resultsUpdatePenjualan = null;
        String sql = "UPDATE t_pelunasan_piutang SET status_sync_online = true WHERE id = :id_pelunasan_piutang_offline";
        String sql_updatePenjualan = "UPDATE t_penjualan SET status_pelunasan = 'LUNAS' WHERE id = :t_penjualan_id";
        try {
            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])list.toArray());
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
            resultsUpdatePenjualan = this.getSimpleJdbcTemplate().batchUpdate(sql_updatePenjualan, batch);
            if (results != null && resultsUpdatePenjualan != null) {
                result = 1;
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message Update Synced Pelunasan Piutang: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateStatusSynced(PelunasanPiutangO2O[] ppoos) {
        int result = -1;
        int[] results = null;
        int[] resultsUpdatePenjualan = null;
        String sql = "UPDATE t_pelunasan_piutang SET status_sync_online = true WHERE id = :id_pelunasan_piutang_offline";
        String sql_updatePenjualan = "UPDATE t_penjualan SET status_pelunasan = 'LUNAS' WHERE id = :t_penjualan_id";
        try {
            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])ppoos);
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
            resultsUpdatePenjualan = this.getSimpleJdbcTemplate().batchUpdate(sql_updatePenjualan, batch);
            System.out.println("Pelunasan Piutang Service -> " + results.length + " / " + resultsUpdatePenjualan.length);
            if (results != null && resultsUpdatePenjualan != null) {
                result = 1;
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message Update Synced Pelunasan Piutang: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertDataPelunasanPiutangDariOnline(PelunasanPiutangO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO t_pelunasan_piutang (id, t_penjualan_id, m_karyawan_id, tanggal_pelunasan, keterangan,  status_sync_online, created, updated) VALUES(:id_pelunasan_piutang_offline, :t_penjualan_id, :m_karyawan_id, :tanggal_pelunasan, :keterangan, true, :created, :updated) ON DUPLICATE KEY UPDATE t_penjualan_id = VALUES(t_penjualan_id), m_karyawan_id = VALUES(m_karyawan_id), tanggal_pelunasan = VALUES(tanggal_pelunasan), keterangan = VALUES(keterangan), status_sync_online = true, created = VALUES(created), updated = VALUES(updated)";
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
        String sql = "SELECT  DATE_FORMAT(GREATEST(coalesce(MAX(created), 0), coalesce(MAX(updated), 0)), '%Y-%m-%d') FROM t_pelunasan_piutang";
        try {
            result = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public long getTotalPelunasanPiutangShift(int shiftKasirId, String keterangan) {
        long totalKasir = 0L;
        keterangan = "%" + keterangan + "%";
        String sql = "SELECT SUM(JUMLAH_PELUNASAN) FROM T_PELUNASAN_PIUTANG WHERE M_SHIFT_KASIR_ID  = ? and KETERANGAN like ? ";
        try {
            totalKasir = this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{shiftKasirId, keterangan}) == null ? 0L : (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{shiftKasirId, keterangan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return totalKasir;
    }
}

