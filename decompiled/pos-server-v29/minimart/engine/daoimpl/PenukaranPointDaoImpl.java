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
import minimart.engine.dao.PenukaranPointDao;
import minimart.engine.model.PenukaranPoint;
import minimart.engine.model_online.PenukaranPointO2O;
import minimart.engine.rowmapper.PenukaranPointO2ORowMapper;
import minimart.engine.rowmapper.PenukaranPointRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PenukaranPointDaoImpl
extends SimpleJdbcDaoSupport
implements PenukaranPointDao {
    public PenukaranPointDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPenukaranPoint(PenukaranPoint penukaranPoint) {
        String sql = "INSERT INTO t_penukaran_point (ID,M_CUSTOMER_ID,M_KARYAWAN_ID,JUMLAH_POINT,KETERANGAN,TANGGAL) VALUES (:id, :customerId, :karyawanId, :jumlahPoint, :keterangan, :tanggal)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penukaranPoint);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int getLastId() {
        String sql = "SELECT id FROM t_penukaran_point order by id desc limit ?;";
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
    public List<PenukaranPoint> getPenukaranPoints(String startDate, String endDate, String customerId) {
        String sql = "SELECT * FROM t_penukaran_point WHERE TANGGAL>=? AND TANGGAL<=? AND M_CUSTOMER_ID LIKE ?";
        List penukaranPoints = null;
        customerId = '%' + customerId + '%';
        try {
            penukaranPoints = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenukaranPointRowMapper(), new Object[]{startDate, endDate, customerId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penukaranPoints;
    }

    @Override
    public PenukaranPoint getPenukaranPoint(int id) {
        String sql = "SELECT * FROM t_penjualan WHERE ID = ? AND STATUS = ?";
        PenukaranPoint penukaranPoint = null;
        try {
            penukaranPoint = (PenukaranPoint)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PenukaranPointRowMapper(), new Object[]{id, "TOKO"});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penukaranPoint;
    }

    @Override
    public int updatePenukaranPoint(PenukaranPointO2O[] data) {
        int result = -1;
        int[] results = null;
        String sql = "UPDATE t_penukaran_point SET status_sync_online = true WHERE id = :id_penukaran_point_offline";
        try {
            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])data);
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
            result = results.length;
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePenukaranPoint(String ids) {
        int result = -1;
        String sql = "UPDATE t_penukaran_point SET status_sync_online = true WHERE id IN (" + ids + ")";
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PenukaranPointO2O> getAllPenukaranPointUnsynced() {
        ArrayList<PenukaranPointO2O> list = new ArrayList();
        String sql = "SELECT * FROM t_penukaran_point WHERE status_sync_online = false";
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenukaranPointO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int[] upsertDataPenukaranPointDariOnline(PenukaranPointO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO t_penukaran_point (id, m_customer_id, m_karyawan_id, jumlah_point, keterangan, tanggal, status_sync_online, created, updated) VALUES(:id_penukaran_point_offline, :m_customer_id, :m_karyawan_id, :jumlah_point, :keterangan, :tanggal, true, :created, :updated) ON DUPLICATE KEY UPDATE m_customer_id = VALUES(m_customer_id), m_karyawan_id = VALUES(m_karyawan_id), jumlah_point = VALUES(jumlah_point), keterangan = VALUES(keterangan), tanggal = VALUES(tanggal), status_sync_online = true, created = VALUES(created), updated = VALUES(updated)";
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
        String sql = "SELECT DATE_FORMAT(GREATEST(coalesce(MAX(created), 0), coalesce(MAX(updated), 0)), '%Y-%m-%d') FROM t_penukaran_point";
        try {
            result = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

