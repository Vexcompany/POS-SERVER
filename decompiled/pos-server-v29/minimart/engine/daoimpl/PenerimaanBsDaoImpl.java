/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.PenerimaanBsDao;
import minimart.engine.model.PenerimaanBs;
import minimart.engine.rowmapper.PenerimaanBsRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PenerimaanBsDaoImpl
extends SimpleJdbcDaoSupport
implements PenerimaanBsDao {
    public PenerimaanBsDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPenerimaanBs(PenerimaanBs penerimaanBs) {
        String sql = "INSERT INTO t_penerimaan_bs (ID, M_KARYAWAN_ID, TANGGAL, STATUS, LOKASI, TOTAL, CREATED, UPDATED) VALUES (:id, :karyawanId, :tanggal, :status, :lokasi, :total, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penerimaanBs);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePenerimaanBs(PenerimaanBs penerimaanBs) {
        System.out.println(penerimaanBs.getKaryawanId());
        String sql = "UPDATE t_penerimaan_bs SET M_KARYAWAN_ID=:karyawanId ,TANGGAL=:tanggal, STATUS=:status, LOKASI=:lokasi, TOTAL=:total, UPDATED=:updated WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penerimaanBs);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PenerimaanBs getPenerimaanBs(String id) {
        String sql = "SELECT * FROM t_penerimaan_bs WHERE ID = ?";
        PenerimaanBs penerimaanBs = null;
        try {
            penerimaanBs = (PenerimaanBs)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PenerimaanBsRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Null Data");
        }
        return penerimaanBs;
    }

    @Override
    public int deletePenerimaanBs(PenerimaanBs penerimaanBs) {
        String sql = "DELETE FROM t_penerimaan_bs WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{penerimaanBs.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM t_penerimaan_bs ORDER BY ID DESC LIMIT ?";
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
    public List<PenerimaanBs> getPenerimaanBsByDate(String startDate, String endDate, String lokasi, String status) {
        String sql = "SELECT * FROM t_penerimaan_bs WHERE TANGGAL >= ? AND TANGGAL <= ? AND lokasi like ? AND status like ? ORDER BY CREATED DESC";
        List penerimaanBses = null;
        try {
            lokasi = '%' + lokasi + '%';
            status = '%' + status + '%';
            penerimaanBses = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenerimaanBsRowMapper(), new Object[]{startDate, endDate, lokasi, status});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penerimaanBses;
    }
}

