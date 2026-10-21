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
import minimart.engine.dao.PengembalianBarangDao;
import minimart.engine.model.PengembalianBarang;
import minimart.engine.model_online.PengembalianBarangO2O;
import minimart.engine.rowmapper.PengembalianBarangO2ORowMapper;
import minimart.engine.rowmapper.PengembalianBarangRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PengembalianBarangDaoImpl
extends SimpleJdbcDaoSupport
implements PengembalianBarangDao {
    public PengembalianBarangDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPengembalianBarang(PengembalianBarang pengembalianBarang) {
        String sql = "INSERT INTO t_pengembalian_barang (ID, M_CUSTOMER_ID, M_KARYAWAN_ID, T_PENJUALAN_ID ,TANGGAL, TOTAL_PENGEMBALIAN, KETERANGAN, CREATED, PELUNASAN, CETAK_VOUCHER, NO_HP, m_shift_kasir_id) VALUES (:id, :customerId, :karyawanId, :penjualanId, :tanggal, :totalPengembalian, :keterangan, :created, :pelunasan, :cetakVoucher, :noHp, :shiftKasirId)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pengembalianBarang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public long getLastId() {
        String sql = "SELECT ID FROM t_pengembalian_barang ORDER BY ID DESC LIMIT ?";
        long id = 0L;
        try {
            id = (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{1});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return id;
    }

    @Override
    public int updatePengembalianBarang(PengembalianBarang pengembalianBarang) {
        String sql = "UPDATE t_pengembalian_barang SET M_CUSTOMER_ID=:customerId, M_KARYAWAN_ID=:karyawanId, T_PENJUALAN_ID=:penjualanId, TANGGAL=:tanggal, TOTAL_PENGEMBALIAN=:totalPengembalian, KETERANGAN=:keterangan, CREATED=:created, PELUNASAN=:pelunasan, CETAK_VOUCHER=:cetakVoucher, NO_HP=:npHp, status_sync_online = false, m_shift_kasir_id=:shiftKasirId  WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pengembalianBarang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePengembalianBarangById(long idPengembalianBarang) {
        String sql = "DELETE FROM t_pengembalian_barang WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{idPengembalianBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PengembalianBarang getPengembalianBarangById(long idPengembalianBarang) {
        String sql = "SELECT * FROM t_pengembalian_barang WHERE ID = ?";
        PengembalianBarang pengembalianBarang = null;
        try {
            pengembalianBarang = (PengembalianBarang)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PengembalianBarangRowMapper(), new Object[]{idPengembalianBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pengembalianBarang;
    }

    @Override
    public List<PengembalianBarang> getPengembalianBarang(String startDate, String endDate, String idCustomer) {
        String sql = "SELECT * FROM t_pengembalian_barang WHERE TANGGAL >= ? AND TANGGAL <= ? AND M_CUSTOMER_ID LIKE ? ";
        List pengembalianBarangs = null;
        try {
            idCustomer = '%' + idCustomer + '%';
            pengembalianBarangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PengembalianBarangRowMapper(), new Object[]{startDate, endDate, idCustomer});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pengembalianBarangs;
    }

    @Override
    public long getTotalPengembalianBarang(int shiftKasirId, String pelunasan) {
        String sql = "SELECT sum(total_pengembalian) as total_pengembalian FROM t_pengembalian_barang WHERE M_SHIFT_KASIR_ID = ? AND PELUNASAN = ? ";
        long totalPengembalian = 0L;
        try {
            if (this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{shiftKasirId, pelunasan}) != null) {
                totalPengembalian = (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{shiftKasirId, pelunasan});
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return totalPengembalian;
    }

    @Override
    public List<PengembalianBarang> getPengembalianBarang(String startDate, String endDate, String idCustomer, int cetakVoucher) {
        String sql = "SELECT * FROM t_pengembalian_barang WHERE TANGGAL >= ? AND TANGGAL <= ? AND M_CUSTOMER_ID LIKE ? AND CETAK_VOUCHER = ? ";
        List pengembalianBarangs = null;
        try {
            idCustomer = '%' + idCustomer + '%';
            pengembalianBarangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PengembalianBarangRowMapper(), new Object[]{startDate, endDate, idCustomer, cetakVoucher});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pengembalianBarangs;
    }

    @Override
    public List<PengembalianBarangO2O> getAllPengembalianBarangUnsynced() {
        ArrayList<PengembalianBarangO2O> list = new ArrayList();
        String sql = "SELECT * FROM t_pengembalian_barang WHERE status_sync_online = false";
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PengembalianBarangO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int updateStatusSyncedPengembalianBarang(String ids) {
        int result = -1;
        String sql = "UPDATE t_pengembalian_barang SET status_sync_online = true WHERE id IN (" + ids + ")";
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertDataPengembalianBarangDariOnline(PengembalianBarangO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO t_pengembalian_barang (id, m_customer_id, m_karyawan_id, t_penjualan_id, tanggal, total_pengembalian, keterangan, created, pelunasan, status_sync_online, updated, cetak_voucher, no_hp) VALUES(:id_pengembalian_barang_offline, :m_customer_id, :m_karyawan_id, :t_penjualan_id, :tanggal, :total_pengembalian, :keterangan, :created, :pelunasan, true, :updated, :cetak_voucher, :no_hp) ON DUPLICATE KEY UPDATE m_customer_id = VALUES(m_customer_id), m_karyawan_id = VALUES(m_karyawan_id), t_penjualan_id = VALUES(t_penjualan_id),tanggal = VALUES(tanggal), total_pengembalian = VALUES(total_pengembalian), keterangan = VALUES(keterangan), created = VALUES(created), pelunasan = VALUES(pelunasan), status_sync_online = true, updated = VALUES(updated), cetak_voucher = VALUES(cetak_voucher), no_hp = VALUES(no_hp)";
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
        String sql = "SELECT DATE_FORMAT(GREATEST(coalesce(MAX(created), 0), coalesce(MAX(updated), 0)), '%Y-%m-%d') FROM t_pengembalian_barang";
        try {
            result = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

