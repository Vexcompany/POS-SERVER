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

import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.PengembalianBsDao;
import minimart.engine.model.PengembalianBs;
import minimart.engine.rowmapper.PengembalianBsRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PengembalianBsDaoImpl
extends SimpleJdbcDaoSupport
implements PengembalianBsDao {
    public PengembalianBsDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPengembalianBs(PengembalianBs pengembalianBs) {
        String sql = "INSERT INTO t_pengembalian_bs (ID, M_KARYAWAN_ID, M_SUPPLIER_ID, TANGGAL, KETERANGAN, TOTAL, CREATED, UPDATED) VALUES (:id, :karyawanId, :supplierId, :tanggal, :keterangan, :total, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pengembalianBs);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePengembalianBs(PengembalianBs pengembalianBs) {
        String sql = "UPDATE t_pengembalian_bs SET ID=:id, M_KARYAWAN_ID=:karyawanId, M_SUPPLIER_ID=:supplierId,TANGGAL=:tanggal, KETERANGAN:=keterangan TOTAL=:total, UPDATED=:updated WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pengembalianBs);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PengembalianBs getPengembalianBs(String id) {
        String sql = "SELECT * FROM t_pengembalian_bs WHERE ID = ?";
        PengembalianBs pengembalianBs = null;
        try {
            pengembalianBs = (PengembalianBs)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PengembalianBsRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pengembalianBs;
    }

    @Override
    public int deletePengembalianBs(PengembalianBs pengembalianBs) {
        String sql = "DELETE FROM t_pengembalian_bs WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{pengembalianBs.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM t_pengembalian_bs ORDER BY ID DESC LIMIT ?";
        String id = "";
        try {
            id = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{1});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return id;
    }
}

