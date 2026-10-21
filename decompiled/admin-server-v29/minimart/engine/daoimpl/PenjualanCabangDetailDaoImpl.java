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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.PenjualanDetailDao;
import minimart.engine.model.Barang;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.model_online.PenjualanDetailO2O;
import minimart.engine.rowmapper.PenjualanDetailO2ORowMapper;
import minimart.engine.rowmapper.PenjualanDetailRowMapper;
import minimart.engine.service.BarangService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PenjualanCabangDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PenjualanDetailDao {
    private Helper helper = Helper.getInstance();
    private BarangService barangService = new BarangService();

    public PenjualanCabangDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createdPenjualanDetail(PenjualanDetail penjualanDetail) {
        String sql = "INSERT INTO t_penjualan_cabang_detail (ID, T_PENJUALAN_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, DISCOUNT_2, HARGA_JUAL, TOTAL_HARGA, TOTAL_KEUNTUNGAN, CREATED, UPDATED) VALUES (:id, :penjualanId, :barangId, :jumlahBarang, :satuan, :discount2, :hargaJual, :totalHarga, :totalKeuntungan, :created, :updated)";
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
        String sql = "INSERT INTO t_penjualan_cabang_detail (ID, T_PENJUALAN_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, DISCOUNT_2, HARGA_JUAL, TOTAL_HARGA, TOTAL_KEUNTUNGAN, CREATED, UPDATED) VALUES (:id, :penjualanId, :barangId, :jumlahBarang, :satuan, :discount2, :hargaJual, :totalHarga, :totalKeuntungan, :created, :updated)";
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
        String sql = "SELECT * FROM t_penjualan_cabang_detail WHERE ID = ?";
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
        String sql = "SELECT * FROM t_penjualan_cabang_detail JOIN m_barang ON (t_penjualan_cabang_detail.M_BARANG_ID = m_barang.ID) WHERE T_PENJUALAN_ID = ? ORDER BY M_BARANG.NAMA";
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
    public List<Object[]> readPenjualanDetailByCustomerBarangId(String startDate, String endDate, String customerId, String barangId) {
        String sql = "SELECT t_penjualan_cabang.id AS T_PENJUALAN_ID, M_CUSTOMER_ID, TANGGAL, M_BARANG_ID, JUMLAH_BARANG, SATUAN, DISCOUNT_2, HARGA_JUAL, TOTAL_HARGA FROM t_penjualan_cabang_detail JOIN t_penjualan_cabang ON (t_penjualan_cabang_detail.T_PENJUALAN_CABANG_ID = t_penjualan_cabang.ID) WHERE t_penjualan.TANGGAL>=? AND t_penjualan.TANGGAL<=? AND t_penjualan.M_CUSTOMER_ID LIKE ? AND t_penjualan_cabang_detail.M_BARANG_ID LIKE ? AND t_penjualan_cabang.STATUS = ?";
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
                Object[] returnValue = new Object[]{row.get("T_PENJUALAN_ID"), row.get("M_CUSTOMER_ID"), row.get("TANGGAL"), row.get("M_BARANG_ID"), row.get("JUMLAH_BARANG"), row.get("SATUAN"), row.get("DISCOUNT_2"), row.get("HARGA_JUAL"), row.get("TOTAL_HARGA")};
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
        String sql = "SELECT * FROM t_penjualan_cabang_detail JOIN t_penjualan_cabang ON (t_penjualan_cabang_detail.T_PENJUALAN_CABANG_ID = t_penjualan_cabang.ID) WHERE M_CUSTOMER_ID = ? AND M_BARANG_ID = ? ORDER BY t_penjualan_cabang_detail.CREATED DESC LIMIT 1";
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
        String sql = "UPDATE t_penjualan_cabang_detail SET T_PENJUALAN_CABANG_ID=:penjualanId, M_BARANG_ID=:barangId, JUMLAH_BARANG=:jumlahBarang, SATUAN=:satuan, DISCOUNT_2=:discount2, HARGA_JUAL=:hargaJual, TOTAL_HARGA=:totalHarga, TOTAL_KEUNTUNGAN=:totalKeuntungan, UPDATED=:updated WHERE ID=:id";
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
        String sql = "DELETE FROM t_penjualan_cabang_detail WHERE ID = ?";
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
        String sql = "SELECT M_CUSTOMER_ID, SUM(TOTAL_HARGA),SUM(TOTAL_KEUNTUNGAN) FROM t_penjualan_cabang_detail JOIN t_penjualan_cabang ON (t_penjualan_cabang_detail.T_PENJUALAN_ID = t_penjualan_cabang.ID) WHERE TANGGAL >= ? AND TANGGAL <= ? AND STATUS = ? GROUP BY M_CUSTOMER_ID ORDER BY SUM(TOTAL_KEUNTUNGAN) DESC ";
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
                Object[] returnValue = new Object[]{row.get("M_CUSTOMER_ID"), row.get("SUM(TOTAL_HARGA)"), ((BigDecimal)row.get("SUM(TOTAL_KEUNTUNGAN)")).longValue()};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getTotalKeuntunganByBarang(String startDate, String endDate, Barang barang, String status) {
        String sql = "SELECT M_BARANG_ID, SUM(JUMLAH_BARANG), SATUAN, SUM(TOTAL_HARGA) FROM t_penjualan_cabang_detail JOIN t_penjualan_cabang ON (t_penjualan_cabang_detail.T_PENJUALAN_ID = t_penjualan_cabang.ID) WHERE TANGGAL >= ? AND TANGGAL <=? AND M_BARANG_ID LIKE ? AND STATUS = ? GROUP BY M_BARANG_ID, SATUAN ORDER BY SUM(TOTAL_KEUNTUNGAN) desc";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        String idBarang = "%%";
        if (barang != null) {
            idBarang = '%' + barang.getId() + '%';
        }
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, idBarang, status});
            for (Map row : rows) {
                Object[] returnValue = new Object[5];
                Barang tempBarang = this.barangService.getBarang(row.get("M_BARANG_ID") + "");
                returnValue[0] = row.get("M_BARANG_ID");
                returnValue[1] = Float.valueOf((float)((Double)row.get("SUM(JUMLAH_BARANG)")).doubleValue());
                returnValue[2] = row.get("SATUAN");
                returnValue[3] = ((BigDecimal)row.get("SUM(TOTAL_HARGA)")).longValue();
                float totalBarang = 0.0f;
                if (row.get("SATUAN").equals(tempBarang.getSatuan1())) {
                    totalBarang = ((Float)returnValue[1]).floatValue();
                } else if (row.get("SATUAN").equals(tempBarang.getSatuan2())) {
                    totalBarang = ((Float)returnValue[1]).floatValue() * (float)tempBarang.getKonversi21();
                } else if (row.get("SATUAN").equals(tempBarang.getSatuan3())) {
                    totalBarang = ((Float)returnValue[1]).floatValue() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32();
                }
                returnValue[4] = Float.valueOf((float)tempBarang.getHpp() * totalBarang);
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
        String sql = "DELETE FROM t_penjualan_cabang_detail WHERE T_PENJUALAN_ID = ?";
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
    public List<Object[]> getBestSeller(String startDate, String endDate, String jenis, int limit) {
        String sql = "select m_barang_id, sum(total_harga) from t_penjualan_cabang_detail join t_penjualan_cabang on(t_penjualan_cabang_detail.t_penjualan_id=t_penjualan_cabang.id) join m_barang on(t_penjualan_cabang_detail.m_barang_id = m_barang.id) where tanggal >= ? and tanggal <= ? AND m_barang_id in (select id from m_barang where m_jenis_jenis like ?) group by m_barang_id order by sum(total_harga) desc";
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
                Object[] returnValue = new Object[5];
                returnValue[0] = row.get("M_BARANG_ID");
                returnValue[1] = row.get("sum(total_harga)");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Object[]> getBestSellerByCustomer(String customerId, String startDate, String endDate, int limit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Object[]> getBestSellerByCustomer(String customerId, int limit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PenjualanDetailO2O> getAllPenjualanDetailUnsynced() {
        String sql = "SELECT * FROM t_penjualan_detail WHERE status_sync_online = false";
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
    public int[] updateStatusSyncedPenjualanDetail(List<PenjualanDetailO2O> data) {
        String sql = "update t_penjualan_detail set status_sync_online = true where id = :id";
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
    public int[] updateStatusSyncedPenjualanDetail(PenjualanDetailO2O[] data) {
        String sql = "update t_penjualan_detail set status_sync_online = true where id = :id";
        int[] results = null;
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
    public int[] upsertDataPenjualanDetailDariOnline(PenjualanDetailO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO t_penjualan_detail (id, t_penjualan_id, m_barang_id, jumlah_barang, satuan, discount_2, harga_jual, total_harga, total_keuntungan, created, updated, status_sync_online) VALUES(:id_penjualan_detail_offline, :t_penjualan_id, :m_barang_id, :jumlah_barang, :satuan, :discount_2, :harga_jual, :total_harga, :total_keuntungan, :created, :updated, true) ON DUPLICATE KEY UPDATE t_penjualan_id = VALUES(t_penjualan_id), m_barang_id = VALUES(m_barang_id), jumlah_barang = VALUES(jumlah_barang), satuan = VALUES(satuan), discount_2 = VALUES(discount_2), harga_jual = VALUES(harga_jual), total_harga = VALUES(total_harga), total_keuntungan = VALUES(total_keuntungan), created = VALUES(created), updated = VALUES(updated), status_sync_online = true";
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int insertPenjualanDetailDeletedByPenjualanId(String idPenjualan) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Long> getPenjualanDetailIdByDate(String start_date, String end_date) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Long> getPenjualanDetailDeletedUnsync() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int updateStatusSyncPenjualanDetailDeleted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PenjualanDetail getPenjualanDetail(String idPenjualan, String barangId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getPenjualanMemberByCustomer(String customerId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int sumBarang(String penjualanId, String barangId, String satuan) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Object[]> readPenjualanDetailByPenjualanId2(String penjualanId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int jumlahPromoByTanggal(String tanggal, String promo, String karyawan) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long totalPromoByTanggal(String tanggal, String promo, String karyawan) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Object[]> getReportPromo(String startDate, String endDate, String jenisPromo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

