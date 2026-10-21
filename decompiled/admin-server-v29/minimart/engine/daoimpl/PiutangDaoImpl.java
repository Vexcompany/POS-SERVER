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
import minimart.engine.dao.PiutangDao;
import minimart.engine.model.Piutang;
import minimart.engine.rowmapper.PiutangRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PiutangDaoImpl
extends SimpleJdbcDaoSupport
implements PiutangDao {
    public PiutangDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPiutang(Piutang piutang) {
        String sql = "INSERT INTO t_piutang (ID, M_KARYAWAN_ID, M_CUSTOMER_ID, TANGGAL, JUMLAH, TIPE, KETERANGAN, CREATED, UPDATED) VALUES (:id, :karyawanId, :customerId, :tanggal, :jumlah, :tipe, :keterangan, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)piutang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM t_piutang ORDER BY ID DESC LIMIT ?";
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
    public List<Piutang> getPiutangByDate(String startDate, String endDate, String tipe, String customerId) {
        String sql = "SELECT * FROM t_piutang WHERE TANGGAL >= ? AND TANGGAL <= ? AND TIPE = ? AND M_CUSTOMER_ID LIKE ? ORDER BY CREATED DESC";
        List piutangs = null;
        System.out.println(customerId);
        try {
            customerId = '%' + customerId + '%';
            piutangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PiutangRowMapper(), new Object[]{startDate, endDate, tipe, customerId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return piutangs;
    }
}

