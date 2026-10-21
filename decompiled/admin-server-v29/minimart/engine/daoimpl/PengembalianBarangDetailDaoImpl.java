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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.PengembalianBarangDetailDao;
import minimart.engine.model.PengembalianBarangDetail;
import minimart.engine.model_online.PengembalianBarangDetailO2O;
import minimart.engine.rowmapper.PengembalianBarangDetailO2ORowMapper;
import minimart.engine.rowmapper.PengembalianBarangDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PengembalianBarangDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PengembalianBarangDetailDao {
    public PengembalianBarangDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPengembalianBarangDetailDao(PengembalianBarangDetail pengembalianBarangDetail) {
        String sql = "INSERT INTO t_pengembalian_barang_detail (ID, T_PENGEMBALIAN_BARANG_ID, M_BARANG_ID, JUMLAH, SATUAN, HARGA, TOTAL_HARGA, TOTAL_KEUNTUNGAN) VALUES (:id, :pengembalianBarangId, :barangId, :jumlah, :satuan, :harga, :totalHarga, :totalKeuntungan)";
        int result = 0;
        System.out.println(pengembalianBarangDetail.getPengembalianBarangId());
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pengembalianBarangDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Object[]> getPengembalianBarangDetail(String startDate, String endDate, String salesId) {
        String sql = "SELECT t_pengembalian_barang.id AS T_PENGEMBALIAN_BARANG_ID, T_PENJUALAN_ID, T_PENGEMBALIAN_BARANG.M_CUSTOMER_ID as CUSTOMER_ID, M_CUSTOMER.NAMA AS NAMA_CUSTOMER, T_PENGEMBALIAN_BARANG.TANGGAL AS TANGGAL ,M_BARANG_ID, M_BARANG.NAMA AS NAMA_BARANG, T_PENGEMBALIAN_BARANG_DETAIL.JUMLAH AS JUMLAH, T_PENGEMBALIAN_BARANG_DETAIL.SATUAN AS SATUAN, T_PENGEMBALIAN_BARANG_DETAIL.HARGA AS HARGA, T_PENGEMBALIAN_BARANG_DETAIL.TOTAL_HARGA AS TOTAL_HARGA, TOTAL_KEUNTUNGAN FROM T_PENGEMBALIAN_BARANG_DETAIL JOIN T_PENGEMBALIAN_BARANG ON (t_pengembalian_barang_detail.t_pengembalian_barang_id = t_pengembalian_barang.id) JOIN M_CUSTOMER ON (T_PENGEMBALIAN_BARANG.M_CUSTOMER_ID = M_CUSTOMER.ID) JOIN M_BARANG ON (T_PENGEMBALIAN_BARANG_DETAIL.M_BARANG_ID = M_BARANG.ID) JOIN T_PENJUALAN ON (T_PENGEMBALIAN_BARANG.T_PENJUALAN_ID = T_PENJUALAN.ID)WHERE T_PENGEMBALIAN_BARANG.TANGGAL >=? AND T_PENGEMBALIAN_BARANG.TANGGAL<=? AND T_PENJUALAN.M_SALES_ID LIKE ?";
        salesId = '%' + salesId + '%';
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, salesId});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("T_PENGEMBALIAN_BARANG_ID"), row.get("T_PENJUALAN_ID"), row.get("CUSTOMER_ID") + " / " + row.get("NAMA_CUSTOMER"), row.get("TANGGAL"), row.get("M_BARANG_ID"), row.get("NAMA_BARANG"), row.get("JUMLAH"), row.get("SATUAN"), row.get("HARGA"), row.get("TOTAL_HARGA"), row.get("TOTAL_KEUNTUNGAN")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public int updatePengembalianBarangDetailDao(PengembalianBarangDetail pengembalianBarangDetail) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PengembalianBarangDetail> getPengembalianBarangDetails(String idPengembalianBarang) {
        String sql = "SELECT * FROM t_pengembalian_barang_detail WHERE T_PENGEMBALIAN_BARANG_ID LIKE ?";
        List pengembalianBarangDetails = null;
        try {
            pengembalianBarangDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PengembalianBarangDetailRowMapper(), new Object[]{idPengembalianBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pengembalianBarangDetails;
    }

    @Override
    public int deletePengembalianBarangDetail(String idPengembalianBarang) {
        String sql = "DELETE FROM t_pengembalian_barang_detail WHERE T_PENGEMBALIAN_BARANG_ID = ?";
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
    public List<PengembalianBarangDetailO2O> getAllPengembalianBarangDetailUnsynced() {
        ArrayList<PengembalianBarangDetailO2O> list = new ArrayList();
        String sql = "SELECT * FROM t_pengembalian_barang_detail WHERE status_sync_online = false";
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PengembalianBarangDetailO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int updateStatusSyncedPengembalianBarangDetail(String ids) {
        int result = -1;
        String sql = "UPDATE t_pengembalian_barang_detail SET status_sync_online = true WHERE id IN (" + ids + ")";
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertDataPengembalianBarangDetailDariOnline(PengembalianBarangDetailO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO t_pengembalian_barang_detail (id, t_pengembalian_barang_id, m_barang_id, jumlah, satuan, harga, total_harga, total_keuntungan, status_sync_online) VALUES(:id_pengembalian_barang_detail_offline, :t_pengembalian_barang_id, :m_barang_id, :jumlah, :satuan, :harga, :total_harga, :total_keuntungan, true) ON DUPLICATE KEY UPDATE t_pengembalian_barang_id = VALUES(t_pengembalian_barang_id), m_barang_id = VALUES(m_barang_id), jumlah = VALUES(jumlah), satuan = VALUES(satuan), harga = VALUES(harga), total_harga = VALUES(total_harga), total_keuntungan = VALUES(total_keuntungan), status_sync_online = true";
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
    public PengembalianBarangDetail getPengembalianBarangDetailByPenjualan(String idPenjualan, String idBarang) {
        String sql = "SELECT * FROM t_pengembalian_barang_detail JOIN t_pengembalian_barang ON (t_pengembalian_barang_detail.T_PENGEMBALIAN_BARANG_ID = t_pengembalian_barang.ID) WHERE t_penjualan_id = ? AND M_BARANG_ID = ? ";
        PengembalianBarangDetail pengembalianBarangDetail = null;
        try {
            pengembalianBarangDetail = (PengembalianBarangDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PengembalianBarangDetailRowMapper(), new Object[]{idPenjualan, idBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pengembalianBarangDetail;
    }
}

