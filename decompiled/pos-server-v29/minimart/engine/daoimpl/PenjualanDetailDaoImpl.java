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

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.PenjualanDetailDao;
import minimart.engine.model.Barang;
import minimart.engine.model.Karyawan;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.model_online.PenjualanDetailO2O;
import minimart.engine.rowmapper.LongRowMapper;
import minimart.engine.rowmapper.PenjualanDetailO2ORowMapper;
import minimart.engine.rowmapper.PenjualanDetailRowMapper;
import minimart.engine.service.BarangService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PenjualanDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PenjualanDetailDao {
    private Helper helper = Helper.getInstance();
    private BarangService barangService = new BarangService();

    public PenjualanDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createdPenjualanDetail(PenjualanDetail penjualanDetail) {
        String sql = "INSERT INTO t_penjualan_detail (ID, T_PENJUALAN_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, DISCOUNT_2, HARGA_JUAL, TOTAL_HARGA, TOTAL_KEUNTUNGAN, CREATED, UPDATED, REFERENSI, PROMO) VALUES (:id, :penjualanId, :barangId, :jumlahBarang, :satuan, :discount2, :hargaJual, :totalHarga, :totalKeuntungan, :created, :updated, :referensi, :promo)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penjualanDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] createdPenjualanDetailBatch(List<PenjualanDetail> penjualanDetails) {
        String sql = "INSERT INTO t_penjualan_detail (ID, T_PENJUALAN_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, DISCOUNT_2, HARGA_JUAL, TOTAL_HARGA, TOTAL_KEUNTUNGAN, CREATED, UPDATED, REFERENSI, PROMO) VALUES (:id, :penjualanId, :barangId, :jumlahBarang, :satuan, :discount2, :hargaJual, :totalHarga, :totalKeuntungan, :created, :updated, :referensi, :promo)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])penjualanDetails.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public PenjualanDetail readPenjualanDetailById(String penjualanDetailId) {
        String sql = "SELECT * FROM t_penjualan_detail WHERE ID = ?";
        PenjualanDetail penjualanDetail = null;
        try {
            penjualanDetail = (PenjualanDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PenjualanDetailRowMapper(), new Object[]{penjualanDetailId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualanDetail;
    }

    @Override
    public List<PenjualanDetail> readPenjualanDetailByPenjualanId(String penjualanId) {
        String sql = "SELECT * FROM t_penjualan_detail JOIN m_barang ON (t_penjualan_detail.M_BARANG_ID = m_barang.ID) WHERE T_PENJUALAN_ID = ? ORDER BY M_BARANG.NAMA";
        List penjualanDetails = null;
        try {
            penjualanDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenjualanDetailRowMapper(), new Object[]{penjualanId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualanDetails;
    }

    @Override
    public List<Object[]> readPenjualanDetailByPenjualanId2(String penjualanId) {
        String sql = "SELECT nama,jumlah_barang,satuan,harga_jual,discount_2,total_harga FROM t_penjualan_detail JOIN m_barang ON (t_penjualan_detail.M_BARANG_ID = m_barang.ID) WHERE T_PENJUALAN_ID = ? ORDER BY M_BARANG.NAMA";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{penjualanId});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("nama"), row.get("jumlah_barang"), row.get("satuan"), row.get("harga_jual"), row.get("discount_2"), row.get("total_harga")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> readPenjualanDetailByCustomerBarangId(String startDate, String endDate, String customerId, String barangId) {
        String sql = "SELECT t_penjualan.id AS T_PENJUALAN_ID, M_KARYAWAN_ID, M_CUSTOMER.NAMA as M_CUSTOMER_NAMA, TANGGAL, M_BARANG_ID,M_BARANG.NAMA AS M_BARANG_NAMA, JUMLAH_BARANG, SATUAN, DISCOUNT_2, HARGA_JUAL, TOTAL_HARGA, T_PENJUALAN.REFERENSI AS REFERENSI, t_penjualan.TOTAL_PENJUALAN as TOTAL_PENJUALAN  FROM t_penjualan_detail JOIN t_penjualan ON (t_penjualan_detail.T_PENJUALAN_ID = t_penjualan.ID)  JOIN m_customer on (m_customer.id = t_penjualan.m_customer_id)  JOIN m_barang on (m_barang.id = t_penjualan_detail.m_barang_id)WHERE t_penjualan.TANGGAL>=? AND t_penjualan.TANGGAL<=? AND t_penjualan.M_CUSTOMER_ID LIKE ? AND t_penjualan_detail.M_BARANG_ID LIKE ? AND t_penjualan.STATUS = ?";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            if (customerId == null) {
                customerId = "";
            }
            if (barangId == null) {
                barangId = "";
            }
            customerId = '%' + customerId + '%';
            barangId = '%' + barangId + '%';
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, customerId, barangId, this.helper.getStatusProgram()});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("T_PENJUALAN_ID"), row.get("M_KARYAWAN_ID"), row.get("M_CUSTOMER_NAMA"), row.get("TANGGAL"), row.get("M_BARANG_ID"), row.get("M_BARANG_NAMA"), row.get("JUMLAH_BARANG"), row.get("SATUAN"), row.get("DISCOUNT_2"), row.get("HARGA_JUAL"), row.get("TOTAL_HARGA"), row.get("TOTAL_PENJUALAN"), row.get("REFERENSI")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public PenjualanDetail getPenjualanTerakhir(String customerId, String barangId) {
        String sql = "SELECT * FROM t_penjualan_detail JOIN t_penjualan ON (t_penjualan_detail.T_PENJUALAN_ID = t_penjualan.ID) WHERE M_CUSTOMER_ID = ? AND M_BARANG_ID = ? ORDER BY t_penjualan_detail.CREATED DESC LIMIT 1";
        PenjualanDetail penjualanDetail = null;
        try {
            penjualanDetail = (PenjualanDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PenjualanDetailRowMapper(), new Object[]{customerId, barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualanDetail;
    }

    @Override
    public int updatePenjualanDetail(PenjualanDetail penjualanDetail) {
        String sql = "UPDATE t_penjualan_detail SET T_PENJUALAN_ID=:penjualanId, M_BARANG_ID=:barangId, JUMLAH_BARANG=:jumlahBarang, SATUAN=:satuan, DISCOUNT_2=:discount2, HARGA_JUAL=:hargaJual, TOTAL_HARGA=:totalHarga, TOTAL_KEUNTUNGAN=:totalKeuntungan, UPDATED=:updated, STATUS_SYNC_ONLINE = false, PROMO=:promo WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penjualanDetail.getId());
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePenjualanDetail(PenjualanDetail penjualanDetail) {
        String sql = "DELETE FROM t_penjualan_detail WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{penjualanDetail.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Object[]> getTotalKeuntunganByCustomer(String startDate, String endDate, int limit) {
        String sql = "SELECT M_CUSTOMER_ID, NAMA, SUM(TOTAL_HARGA),SUM(TOTAL_KEUNTUNGAN) FROM t_penjualan_detail JOIN t_penjualan ON (t_penjualan_detail.T_PENJUALAN_ID = t_penjualan.ID) JOIN M_CUSTOMER ON (T_PENJUALAN.M_CUSTOMER_ID = M_CUSTOMER.ID) WHERE TANGGAL >= ? AND TANGGAL <= ? AND STATUS = ? GROUP BY M_CUSTOMER_ID ORDER BY SUM(TOTAL_KEUNTUNGAN) DESC ";
        String sql2 = "";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows;
            if (limit > 0) {
                sql2 = "LIMIT ?";
                rows = this.getJdbcTemplate().queryForList(sql + sql2, new Object[]{startDate, endDate, this.helper.getStatusProgram(), limit});
            } else {
                rows = this.getJdbcTemplate().queryForList(sql + sql2, new Object[]{startDate, endDate, this.helper.getStatusProgram()});
            }
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("M_CUSTOMER_ID"), row.get("NAMA"), row.get("SUM(TOTAL_HARGA)"), ((BigDecimal)row.get("SUM(TOTAL_KEUNTUNGAN)")).longValue()};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getTotalKeuntunganByJenisBarang(String startDate, String endDate, String jenisBarang) {
        String sql = "SELECT M_BARANG_ID, NAMA, SUM(JUMLAH_BARANG) AS JUMLAH_BARANG, SATUAN, SUM(TOTAL_HARGA) AS TOTAL_PENJUALAN ,CASE SATUAN WHEN SATUAN_1 THEN HPP*SUM(JUMLAH_BARANG) WHEN SATUAN_2 THEN HPP*SUM(JUMLAH_BARANG)*KONVERSI_21 WHEN SATUAN_3 THEN HPP*SUM(JUMLAH_BARANG)*KONVERSI_21*KONVERSI_32 END as TOTAL_HPP FROM t_penjualan_detail JOIN t_penjualan ON (t_penjualan_detail.T_PENJUALAN_ID = t_penjualan.ID) JOIN M_BARANG ON (T_PENJUALAN_DETAIL.M_BARANG_ID = M_BARANG.ID) WHERE TANGGAL >= ? AND TANGGAL <= ? AND M_JENIS_JENIS = ? GROUP BY M_BARANG_ID, SATUAN ORDER BY SUM(TOTAL_KEUNTUNGAN) desc";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, jenisBarang});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("M_BARANG_ID"), row.get("NAMA"), row.get("JUMLAH_BARANG"), row.get("SATUAN"), row.get("TOTAL_PENJUALAN")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getReportPromo(String startDate, String endDate, String jenisPromo) {
        String sql = "";
        sql = jenisPromo.equals("SEMUA") ? "SELECT tanggal, t_penjualan.id as t_penjualan_id, m_karyawan_id, m_barang_id, nama, jumlah_barang, satuan, harga_jual,  discount_2, total_harga, promo FROM minimarket.t_penjualan join minimarket.t_penjualan_detail on (t_penjualan.id = t_penjualan_detail.t_penjualan_id) join minimarket.m_barang on (t_penjualan_detail.m_barang_id = m_barang.id) where tanggal>= ? and tanggal <= ? and promo != ?" : "SELECT tanggal, t_penjualan.id as t_penjualan_id, m_karyawan_id, m_barang_id, nama, jumlah_barang, satuan, harga_jual,  discount_2, total_harga, promo FROM minimarket.t_penjualan join minimarket.t_penjualan_detail on (t_penjualan.id = t_penjualan_detail.t_penjualan_id) join minimarket.m_barang on (t_penjualan_detail.m_barang_id = m_barang.id) where tanggal>= ? and tanggal <= ? and promo =?";
        ArrayList<Object[]> objects = new ArrayList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, jenisPromo});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("tanggal"), row.get("t_penjualan_id"), row.get("m_karyawan_id"), row.get("m_barang_id"), row.get("nama"), row.get("jumlah_barang"), row.get("satuan"), row.get("harga_jual"), row.get("discount_2"), row.get("total_harga"), row.get("promo")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getTotalKeuntunganByBarang(String startDate, String endDate, Barang barang, String jenisBarang) {
        String sql = "SELECT M_BARANG_ID, NAMA, SUM(CASE SATUAN WHEN SATUAN_1 THEN JUMLAH_BARANG WHEN SATUAN_2 THEN JUMLAH_BARANG * KONVERSI_21 WHEN SATUAN_3 THEN JUMLAH_BARANG * KONVERSI_21 * KONVERSI_32  END) AS JUMLAH_BARANG ,SATUAN_1 , SUM(TOTAL_HARGA) ,SUM(CASE SATUAN WHEN SATUAN_1 THEN HPP*JUMLAH_BARANG WHEN SATUAN_2 THEN HPP*JUMLAH_BARANG * KONVERSI_21 WHEN SATUAN_3 THEN HPP*JUMLAH_BARANG * KONVERSI_21 * KONVERSI_32 END) as TOTAL_HPP FROM t_penjualan_detail join t_penjualan on (t_penjualan_detail.t_penjualan_id = t_penjualan.id) join m_barang on (t_penjualan_detail.m_barang_id = m_barang.id) where tanggal >= ? and tanggal <= ? and M_BARANG_ID LIKE ? and M_JENIS_JENIS LIKE ? GROUP BY M_BARANG_ID ORDER BY SUM(TOTAL_HARGA) desc";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        String idBarang = "%%";
        String jenis = '%' + jenisBarang + '%';
        if (barang != null) {
            idBarang = '%' + barang.getId() + '%';
        }
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, idBarang, jenis});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("M_BARANG_ID"), row.get("NAMA"), row.get("JUMLAH_BARANG"), row.get("SATUAN_1"), row.get("SUM(TOTAL_HARGA)"), row.get("TOTAL_HPP")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public int deletePenjualanDetailByPenjualanId(String penjualanId) {
        String sql = "DELETE FROM t_penjualan_detail WHERE T_PENJUALAN_ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{penjualanId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Object[]> getBestSellerByCustomer(String customerId, String startDate, String endDate, int limit) {
        String sql = "select m_barang_id, m_barang.nama as nama, sum(total_harga) as total_harga, satuan_1, harga_jual_1 from t_penjualan_detail join t_penjualan on (t_penjualan.id = t_penjualan_detail.t_penjualan_id) join m_barang on (t_penjualan_detail.m_barang_id = m_barang.id) where m_customer_id = ? and tanggal >= ? and tanggal <= ? group by m_barang_id order by total_harga desc ";
        List rows = null;
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            if (limit > 0) {
                sql = sql + " limit ?";
                rows = this.getJdbcTemplate().queryForList(sql, new Object[]{customerId, startDate, endDate, limit});
            } else if (limit == 0) {
                rows = this.getJdbcTemplate().queryForList(sql, new Object[]{customerId, startDate, endDate});
            }
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_barang_id"), row.get("nama"), row.get("satuan_1"), row.get("harga_jual_1"), 1};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getBestSellerByCustomer(String customerId, int limit) {
        String sql = "select m_barang_id, m_barang.nama as nama, sum(total_harga) as total_harga, satuan_1, harga_jual_1 from t_penjualan_detail join t_penjualan on (t_penjualan.id = t_penjualan_detail.t_penjualan_id) join m_barang on (t_penjualan_detail.m_barang_id = m_barang.id) where m_customer_id = ? group by m_barang_id order by total_harga desc ";
        List rows = null;
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            if (limit > 0) {
                sql = sql + " limit ?";
                rows = this.getJdbcTemplate().queryForList(sql, new Object[]{customerId, limit});
            } else if (limit == 0) {
                rows = this.getJdbcTemplate().queryForList(sql, new Object[]{customerId});
            }
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_barang_id"), row.get("nama"), row.get("satuan_1"), row.get("harga_jual_1"), 1};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getBestSeller(String startDate, String endDate, String jenis, int limit) {
        String sql = "select m_barang_id, nama, sum(total_harga) from t_penjualan_detail join t_penjualan on(t_penjualan_detail.t_penjualan_id=t_penjualan.id) join m_barang on(t_penjualan_detail.m_barang_id = m_barang.id) where tanggal >= ? and tanggal <= ? AND m_barang_id in (select id from m_barang where m_jenis_jenis like ?) group by m_barang_id order by sum(total_harga) desc";
        jenis = jenis != null ? '%' + jenis + '%' : "%%";
        List rows = null;
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            if (limit > 0) {
                sql = sql + " limit ?";
                rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, jenis, limit});
            } else if (limit == 0) {
                rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, jenis});
            }
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("M_BARANG_ID"), row.get("nama"), row.get("sum(total_harga)")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<PenjualanDetailO2O> getAllPenjualanDetailUnsynced() {
        Karyawan karyawan = Helper.getInstance().getKaryawan();
        String sql = "SELECT d.* FROM t_penjualan_detail d INNER JOIN t_penjualan p ON p.id = d.t_penjualan_id WHERE d.status_sync_online = false";
        List objects = null;
        try {
            objects = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenjualanDetailO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public int[] updateStatusSyncedPenjualanDetail(PenjualanDetailO2O[] data) {
        int[] results = null;
        String sql = "update t_penjualan_detail set status_sync_online = true where id = :id_penjualan_detail_offline";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])data);
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int[] updateStatusSyncedPenjualanDetail(List<PenjualanDetailO2O> data) {
        String sql = "update t_penjualan_detail set status_sync_online = true where id = :id_penjualan_detail_offline";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])data.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int[] upsertDataPenjualanDetailDariOnline(PenjualanDetailO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO t_penjualan_detail (id, t_penjualan_id, m_barang_id, jumlah_barang, satuan, discount_2, harga_jual, total_harga, total_keuntungan, created, updated, status_sync_online, referensi) VALUES(:id_penjualan_detail_offline, :t_penjualan_id, :m_barang_id, :jumlah_barang, :satuan, :discount_2, :harga_jual, :total_harga, :total_keuntungan, :created, :updated, true, :referensi) ON DUPLICATE KEY UPDATE t_penjualan_id = VALUES(t_penjualan_id), m_barang_id = VALUES(m_barang_id), jumlah_barang = VALUES(jumlah_barang), satuan = VALUES(satuan), discount_2 = VALUES(discount_2), harga_jual = VALUES(harga_jual), total_harga = VALUES(total_harga), total_keuntungan = VALUES(total_keuntungan), created = VALUES(created), updated = VALUES(updated), status_sync_online = true, referensi = VALUES(referensi)";
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
    public int insertPenjualanDetailDeleted(long idPenjualanDetail) {
        String sql = "INSERT INTO t_penjualan_detail_deleted (id) VALUES (?) ON DUPLICATE KEY UPDATE status_sync_online = false";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{idPenjualanDetail});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int insertPenjualanDetailDeletedByPenjualanId(String idPenjualan) {
        String sql = "INSERT INTO t_penjualan_detail_deleted (id) SELECT id from t_penjualan_detail where t_penjualan_id = ? ON DUPLICATE KEY UPDATE status_sync_online = false";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{idPenjualan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Long> getPenjualanDetailIdByDate(String start_date, String end_date) throws SQLException {
        String sql = "SELECT id from t_penjualan_detail where created >= ? and created < ? order by id asc";
        List<Long> list = new ArrayList<Long>();
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new LongRowMapper(), new Object[]{start_date, end_date});
            return list;
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
            return list;
        }
    }

    @Override
    public List<Long> getPenjualanDetailDeletedUnsync() throws SQLException {
        String sql = "SELECT id from t_penjualan_detail_deleted where status_sync_online = false order by id asc";
        ArrayList<Long> list = new ArrayList();
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new LongRowMapper(), new Object[0]);
            return list;
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
            return list;
        }
    }

    @Override
    public int updateStatusSyncPenjualanDetailDeleted() {
        String sql = "UPDATE t_penjualan_detail_deleted set status_sync_online = true where status_sync_online = false";
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
    public PenjualanDetail getPenjualanDetail(String idPenjualan, String barangId) {
        String sql = "SELECT * FROM t_penjualan_detail JOIN t_penjualan ON (t_penjualan_detail.T_PENJUALAN_ID = t_penjualan.ID) WHERE t_penjualan_id = ? AND M_BARANG_ID = ? ORDER BY t_penjualan_detail.CREATED DESC LIMIT 1";
        PenjualanDetail penjualanDetail = null;
        try {
            penjualanDetail = (PenjualanDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PenjualanDetailRowMapper(), new Object[]{idPenjualan, barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualanDetail;
    }

    @Override
    public int getPenjualanMemberByCustomer(String customerId) {
        int result = 0;
        String sql = "select count(t_penjualan_detail.id) from t_penjualan_detail join t_penjualan on (t_penjualan_detail.t_penjualan_id = t_penjualan.id) where m_barang_id = 'MEMBER' and m_customer_id = ?";
        try {
            result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{customerId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int sumBarang(String penjualanId, String barangId, String satuan) {
        int result = 0;
        String sql = "SELECT sum(jumlah_barang) FROM t_penjualan_detail where t_penjualan_id = ? AND m_barang_id = ? and satuan = ?";
        try {
            result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{penjualanId, barangId, satuan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int jumlahPromoByTanggal(String tanggal, String promo, String karyawan) {
        int result = 0;
        String sql = "select count(distinct t_penjualan_id) as promo_pwp from minimarket.t_penjualan_detail join minimarket.t_penjualan on (t_penjualan_detail.t_penjualan_id = t_penjualan.id) where tanggal = ? and promo = ? and m_karyawan_id = ? group by tanggal";
        try {
            result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{tanggal, promo, karyawan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public long totalPromoByTanggal(String tanggal, String promo, String karyawan) {
        int result = 0;
        String sql = "select sum(total_harga) from minimarket.t_penjualan_detail join minimarket.t_penjualan on (t_penjualan_detail.t_penjualan_id = t_penjualan.id) where tanggal = ? and promo = ? and m_karyawan_id = ?";
        try {
            result = this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{tanggal, promo, karyawan}) == null ? 0 : (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{tanggal, promo, karyawan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

