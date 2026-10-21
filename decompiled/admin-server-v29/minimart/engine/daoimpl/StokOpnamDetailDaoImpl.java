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
import minimart.engine.controller.Helper;
import minimart.engine.dao.StokOpnamDetailDao;
import minimart.engine.model.StokOpnamDetail;
import minimart.engine.model_online.StokOpnamDetailO2O;
import minimart.engine.rowmapper.StokOpnamDetailO2ORowMapper;
import minimart.engine.rowmapper.StokOpnamDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class StokOpnamDetailDaoImpl
extends SimpleJdbcDaoSupport
implements StokOpnamDetailDao {
    private Helper helper = Helper.getInstance();

    public StokOpnamDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createStokOpnamDetail(StokOpnamDetail stokOpnamDetail) {
        String sql = "INSERT INTO stok_opnam_detail (ID, M_BARANG_ID, STOK_OPNAM_ID,JUMLAH_KOMPUTER, JUMLAH_TOKO, TOTAL) VALUES (:id, :barangId, :stokOpnamId, :jumlahKomp, :jumlahToko, :total)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)stokOpnamDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateStokOpnamDetail(StokOpnamDetail stokOpnamDetail) {
        String sql = "UPDATE stok_opnam_detail SET JUMLAH_TOKO=:jumlahToko,TOTAL=:total, STATUS_SYNC_ONLINE = false WHERE M_BARANG_ID=:barangId AND STOK_OPNAM_ID =:stokOpnamId";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)stokOpnamDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<StokOpnamDetail> getAllStokOpnamDetails() {
        String sql = "SELECT * FROM STOK_OPNAM_DETAIL JOIN STOK_OPNAM ON (STOK_OPNAM_DETAIL.STOK_OPNAM_ID = STOK_OPNAM.ID) WHERE STATUS_OPNAM = 'BELUM DIPROSES'";
        List stokOpnamDetails = null;
        try {
            stokOpnamDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamDetailRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamDetails;
    }

    @Override
    public List<Object[]> getStokOpnamDetailsRekap(String startDate, String endDate, String lokasi, String idBarang) {
        String sql = "SELECT m_barang.id AS M_BARANG_ID, NAMA, SUM(JUMLAH_KOMPUTER) AS TOTAL_KOMPUTER, SUM(JUMLAH_TOKO) AS TOTAL_TOKO, SUM(JUMLAH_TOKO-JUMLAH_KOMPUTER) AS SELISIH, SUM(TOTAL) AS TOTAL FROM stok_opnam_detail JOIN stok_opnam on (stok_opnam_detail.stok_opnam_id = stok_opnam.id) JOIN m_barang on (stok_opnam_detail.m_barang_id = m_barang.id)  WHERE TANGGAL>=? AND TANGGAL <= ? AND LOKASI = ? AND M_BARANG_ID LIKE ? GROUP BY m_barang.id";
        idBarang = '%' + idBarang + '%';
        LinkedList<Object[]> stokOpnamDetails = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, lokasi, idBarang});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("M_BARANG_ID"), row.get("NAMA"), row.get("TOTAL_KOMPUTER"), row.get("TOTAL_TOKO"), row.get("SELISIH"), row.get("TOTAL")};
                stokOpnamDetails.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamDetails;
    }

    @Override
    public List<Object[]> getStokOpnamDetails(String startDate, String endDate, String lokasi, String idBarang) {
        String sql = "SELECT stok_opnam.id AS STOK_OPNAM_ID, tanggal, m_karyawan_id, m_barang.id AS M_BARANG_ID, NAMA, JUMLAH_KOMPUTER, JUMLAH_TOKO, JUMLAH_TOKO-JUMLAH_KOMPUTER AS SELISIH, TOTAL, STATUS_OPNAM FROM stok_opnam_detail JOIN stok_opnam on (stok_opnam_detail.stok_opnam_id = stok_opnam.id) JOIN m_barang on (stok_opnam_detail.m_barang_id = m_barang.id)  WHERE TANGGAL>=? AND TANGGAL <= ? AND LOKASI = ? AND M_BARANG_ID LIKE ?";
        idBarang = '%' + idBarang + '%';
        LinkedList<Object[]> stokOpnamDetails = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, lokasi, idBarang});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("STOK_OPNAM_ID"), row.get("TANGGAL"), row.get("M_KARYAWAN_ID"), row.get("M_BARANG_ID"), row.get("NAMA"), row.get("JUMLAH_KOMPUTER"), row.get("JUMLAH_TOKO"), row.get("SELISIH"), row.get("TOTAL"), row.get("STATUS_OPNAM")};
                stokOpnamDetails.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamDetails;
    }

    @Override
    public List<Object[]> getStokOpnamDetails(String stokOpnamId) {
        String sql = "SELECT m_barang.id AS M_BARANG_ID, NAMA, M_JENIS_JENIS, JUMLAH_KOMPUTER, JUMLAH_TOKO, JUMLAH_TOKO-JUMLAH_KOMPUTER AS SELISIH, SATUAN_1, TOTAL, SATUAN_1, SATUAN_2, SATUAN_3, KONVERSI_21, KONVERSI_32 FROM stok_opnam_detail JOIN m_barang on (stok_opnam_detail.m_barang_id = m_barang.id)  WHERE STOK_OPNAM_ID = ? ";
        LinkedList<Object[]> stokOpnamDetails = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{stokOpnamId});
            for (Map row : rows) {
                Object[] returnValue = new Object[14];
                returnValue[0] = row.get("M_BARANG_ID");
                returnValue[1] = row.get("NAMA");
                returnValue[2] = row.get("JUMLAH_KOMPUTER");
                returnValue[3] = row.get("JUMLAH_TOKO");
                returnValue[4] = row.get("SELISIH");
                returnValue[5] = row.get("SATUAN_1");
                returnValue[6] = row.get("TOTAL");
                returnValue[7] = row.get("M_JENIS_JENIS");
                returnValue[8] = row.get("SATUAN_1");
                returnValue[9] = row.get("SATUAN_2");
                returnValue[10] = row.get("SATUAN_3");
                returnValue[11] = row.get("KONVERSI_21");
                returnValue[12] = row.get("KONVERSI_32");
                stokOpnamDetails.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamDetails;
    }

    @Override
    public int deleteStokOpnamDetailById(int id) {
        String sql = "DELETE FROM stok_opnam_detail WHERE STOK_OPNAM_ID = ?";
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
    public int deleteStokOpnamDetailByPenjualanId(String stokOpnamId) {
        String sql = "DELETE FROM stok_opnam_detail WHERE STOK_OPNAM_ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{stokOpnamId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<StokOpnamDetail> getStokSelisihOpnamDetails(String stokOpnamId) {
        String sql = "SELECT * FROM stok_opnam_detail JOIN M_BARANG ON (stok_opnam_detail.m_barang_id = m_barang.id) WHERE STOK_OPNAM_ID = ? AND JUMLAH_KOMPUTER <> JUMLAH_TOKO order by m_barang.m_jenis_jenis, m_barang.nama";
        List stokOpnamDetails = null;
        try {
            stokOpnamDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamDetailRowMapper(), new Object[]{stokOpnamId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamDetails;
    }

    @Override
    public List<StokOpnamDetailO2O> getStokOpnamDetailUnsynced() {
        ArrayList<StokOpnamDetailO2O> list = new ArrayList();
        String sql = "SELECT * FROM stok_opnam_detail WHERE status_sync_online = false";
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamDetailO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int updateStokOpnamDetailUnsynced(String ids) {
        int result = -1;
        String sql = "UPDATE stok_opnam_detail SET status_sync_online = true WHERE id IN (" + ids + ")";
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertDataStokOpnamDetailDariOnline(StokOpnamDetailO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO stok_opnam_detail (id, m_barang_id, stok_opnam_id, jumlah_komputer, jumlah_toko, status_sync_online) VALUES(:id_stok_opnam_detail_offline, :m_barang_id, :stok_opnam_id, :jumlah_komputer, :jumlah_toko, true) ON DUPLICATE KEY UPDATE m_barang_id = VALUES(m_barang_id), stok_opnam_id = VALUES(stok_opnam_id), jumlah_komputer = VALUES(jumlah_komputer), jumlah_toko = VALUES(jumlah_toko), status_sync_online = true";
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
    public int[] createStokOpnamDetailBatch(List<StokOpnamDetail> datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO stok_opnam_detail (id, m_barang_id, stok_opnam_id, jumlah_komputer, jumlah_toko, total) VALUES(:id, :barangId, :stokOpnamId, :jumlahKomp, :jumlahToko, :total) ON DUPLICATE KEY UPDATE m_barang_id = VALUES(m_barang_id), stok_opnam_id = VALUES(stok_opnam_id), jumlah_komputer = VALUES(jumlah_komputer), jumlah_toko = VALUES(jumlah_toko), status_sync_online = true";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])datas.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql_upsert, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }
}

