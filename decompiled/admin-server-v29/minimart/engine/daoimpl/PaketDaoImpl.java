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
import minimart.engine.dao.PaketDao;
import minimart.engine.model.Paket;
import minimart.engine.rowmapper.PaketRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PaketDaoImpl
extends SimpleJdbcDaoSupport
implements PaketDao {
    public PaketDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int create(Paket paket) {
        String sql = "INSERT INTO m_paket (ID, M_BARANG_ID, CREATED, UPDATED, STATUS) values (:id, :barangId, :created, :updated, :status)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)paket);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int update(Paket paket) {
        String sql = "UPDATE M_PAKET SET M_BARANG_ID=:barangId, CREATED=:created,UPDATED=:updated, STATUS=:status WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)paket);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int ubahStatus(String idPaket, int status) {
        String sql = "UPDATE M_PAKET SET STATUS = ? WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{idPaket, status});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Paket getPaket(String idPaket) {
        String sql = "SELECT * FROM M_PAKET where ID = ?";
        Paket paket = null;
        try {
            paket = (Paket)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PaketRowMapper(), new Object[]{idPaket});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return paket;
    }

    @Override
    public List<Object[]> getPaketByNama(String namaPaket) {
        String sql = "select m_paket.id as id, m_barang_id, m_barang.nama as nama from m_paket join m_barang on (m_paket.m_barang_id = m_barang.id) where m_barang.nama like ?";
        List rows = null;
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            namaPaket = '%' + namaPaket + '%';
            rows = this.getJdbcTemplate().queryForList(sql, new Object[]{namaPaket});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("id"), row.get("m_barang_id"), row.get("nama")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public int getStokBarangByDetailPaket(String barangId) {
        String sql = "SELECT sum(stok_toko*jumlah_barang) as jumlah_barang FROM minimarket.m_paket join minimarket.m_paket_detail on (m_paket.id = m_paket_detail.m_paket_id) join minimarket.m_barang on (m_paket.m_barang_id = m_barang.id) where \nm_paket_detail.m_barang_id = ? group by m_paket_detail.m_barang_id";
        int jumlahBarang = 0;
        try {
            jumlahBarang = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return jumlahBarang;
    }

    @Override
    public int[] upsertPaketBatch(List<Paket> datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO m_paket (id, m_barang_id, created, updated, status) VALUES (:id, :barangId, :created, :updated, :status) ON DUPLICATE KEY UPDATE m_barang_id = VALUES(m_barang_id), created = VALUES(created), updated = VALUES(updated), status = VALUES(status)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])datas.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql_upsert, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int deletePaketByIds(String ids) {
        String sql = "DELETE FROM m_paket WHERE id in (" + ids + ")";
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
    public int deletePaketAll() {
        String sql = "DELETE FROM m_paket";
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
    public List<Paket> getPaketAll() {
        String sql = "SELECT * FROM m_paket";
        List pakets = null;
        try {
            pakets = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PaketRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pakets;
    }

    @Override
    public List<Paket> getPaketByBarangId(String barangId) {
        String sql = "SELECT * FROM m_paket where m_barang_id = ?";
        List pakets = null;
        try {
            pakets = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PaketRowMapper(), new Object[]{barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pakets;
    }
}

