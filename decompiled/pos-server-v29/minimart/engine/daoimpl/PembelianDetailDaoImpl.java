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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.PembelianDetailDao;
import minimart.engine.model.PembelianDetail;
import minimart.engine.model_online.PembelianDetailO2O;
import minimart.engine.rowmapper.PembelianDetailO2ORowMapper;
import minimart.engine.rowmapper.PembelianDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PembelianDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PembelianDetailDao {
    private Helper helper = Helper.getInstance();

    public PembelianDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPembelianDetail(PembelianDetail pembelianDetail) {
        String sql = "INSERT INTO t_pembelian_detail (ID, T_PEMBELIAN_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, HARGA_BELI, DISCOUNT_2, TOTAL_HARGA, CREATED, UPDATED) VALUES (:id, :pembelianId, :barangId, :jumlahBarang, :satuan, :hargaBeli, :discount2, :totalHarga, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pembelianDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] createPembelianDetailBatch(List<PembelianDetail> pembelianDetails) {
        String sql = "INSERT INTO t_pembelian_detail (ID, T_PEMBELIAN_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, HARGA_BELI, DISCOUNT_2, TOTAL_HARGA, CREATED, UPDATED) VALUES (:id, :pembelianId, :barangId, :jumlahBarang, :satuan, :hargaBeli, :discount2, :totalHarga, :created, :updated)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])pembelianDetails.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public List<PembelianDetail> readPembelianDetails(String pembelianId) {
        String sql = "SELECT * FROM t_pembelian_detail WHERE T_PEMBELIAN_ID LIKE ?";
        List pembelianDetail = null;
        try {
            pembelianDetail = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PembelianDetailRowMapper(), new Object[]{pembelianId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelianDetail;
    }

    @Override
    public PembelianDetail readPembelianDetail(String pembelianId, String barangId) {
        String sql = "SELECT * FROM t_pembelian_detail WHERE ID = ?";
        PembelianDetail pembelianDetail = null;
        try {
            pembelianDetail = (PembelianDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PembelianDetailRowMapper(), new Object[]{pembelianId, barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelianDetail;
    }

    @Override
    public int updatePembelianDetail(PembelianDetail pembelianDetail) {
        String sql = "UPDATE t_pembelian_detail SET JUMLAH_BARANG=:jumlahBarang, SATUAN=:satuan,HARGA_BELI=:hargaBeli, DISCOUNT_2=:discount2, TOTAL_HARGA=:totalHarga, UPDATED=:updated, STATUS_SYNC_ONLINE = false WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pembelianDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePembelianDetail(String pembelianId) {
        String sql = "DELETE FROM t_pembelian_detail WHERE T_PEMBELIAN_ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{pembelianId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM t_pembelian_detail ORDER BY ID DESC LIMIT ?";
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
    public boolean isLastPembelian(String idBarang, String date) {
        String sql = "SELECT * FROM t_pembelian_detail JOIN t_pembelian ON (t_pembelian_detail.T_PEMBELIAN_ID = t_pembelian.ID) WHERE M_BARANG_ID = ? AND TANGGAL > ?";
        List pembelianDetail = null;
        try {
            pembelianDetail = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PembelianDetailRowMapper(), new Object[]{idBarang, date});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelianDetail == null;
    }

    @Override
    public PembelianDetail getPembelianTerakhir(String idBarang) {
        String sql = "SELECT * FROM t_pembelian_detail JOIN t_pembelian ON (t_pembelian_detail.t_pembelian_id = t_pembelian.id) WHERE m_barang_id = ? AND TOTAL_HARGA <> 0 ORDER BY tanggal DESC, t_pembelian.created DESC LIMIT 1";
        PembelianDetail pembelianDetail = null;
        try {
            pembelianDetail = (PembelianDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PembelianDetailRowMapper(), new Object[]{idBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelianDetail;
    }

    @Override
    public List<Object[]> readPembelianDetailBySupplierBarangId(String startDate, String endDate, String supplierId, String barangId) {
        String sql = "SELECT t_pembelian.id AS t_pembelian_id, m_supplier.nama AS m_supplier_nama, tanggal, m_barang_id,m_barang.nama AS m_barang_nama, jumlah_barang, satuan, harga_beli, discount_2, discount_1, ppn FROM t_pembelian_detail JOIN t_pembelian ON (t_pembelian_detail.t_pembelian_id = t_pembelian.id) JOIN m_barang on (t_pembelian_detail.m_barang_id = m_barang.id) JOIN m_supplier on (t_pembelian.m_supplier_id = m_supplier.id) WHERE t_pembelian.tanggal>=? AND t_pembelian.tanggal<=? AND t_pembelian.m_supplier_id LIKE ? AND t_pembelian_detail.m_barang_id LIKE ?";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            if (supplierId == null) {
                supplierId = "";
            }
            if (barangId == null) {
                barangId = "";
            }
            supplierId = '%' + supplierId + '%';
            barangId = '%' + barangId + '%';
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, supplierId, barangId});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("T_PEMBELIAN_ID"), row.get("M_SUPPLIER_NAMA"), row.get("TANGGAL"), row.get("M_BARANG_ID"), row.get("M_BARANG_NAMA"), row.get("JUMLAH_BARANG"), row.get("SATUAN"), row.get("HARGA_BELI"), row.get("DISCOUNT_2"), row.get("DISCOUNT_1"), row.get("PPN")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<PembelianDetailO2O> getAllPembelianDetailUnsynced() {
        List results = null;
        String sql = "SELECT * FROM t_pembelian_detail WHERE status_sync_online = false";
        try {
            results = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PembelianDetailO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int updateStatusSyncedPembelianDetail(String ids) {
        int result = -1;
        String sql = "UPDATE t_pembelian_detail SET status_sync_online = true WHERE id IN (" + ids + ")";
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertDataPembelianDetailDariOnline(PembelianDetailO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO t_pembelian_detail (id, t_pembelian_id, m_barang_id, jumlah_barang, satuan, harga_beli, discount_2, total_harga, created, updated, status_sync_online) VALUES(:id_pembelian_detail_offline, :t_pembelian_id, :m_barang_id, :jumlah_barang, :satuan, :harga_beli, :discount_2, :total_harga, :created, :updated, true) ON DUPLICATE KEY UPDATE t_pembelian_id = VALUES(t_pembelian_id), m_barang_id = VALUES(m_barang_id), jumlah_barang = VALUES(jumlah_barang), satuan = VALUES(satuan), harga_beli = VALUES(harga_beli), discount_2 = VALUES(discount_2), total_harga = VALUES(total_harga), created = VALUES(created), updated = VALUES(updated), status_sync_online = true";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])datas);
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql_upsert, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }
}

