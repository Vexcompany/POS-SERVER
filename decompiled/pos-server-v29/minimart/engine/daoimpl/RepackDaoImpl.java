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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.RepackDao;
import minimart.engine.model.Repack;
import minimart.engine.rowmapper.RepackRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class RepackDaoImpl
extends SimpleJdbcDaoSupport
implements RepackDao {
    public RepackDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createRepack(Repack repack) {
        String sql = "INSERT INTO t_repack (ID, M_KARYAWAN_ID, TANGGAL, CREATED, M_BARANG_ID, JUMLAH) VALUES (:id, :karyawanId, :tanggal, :created, :barangId, :jumlah)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)repack);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Repack getRepack(String id) {
        String sql = "SELECT * FROM t_repack WHERE ID = ?";
        Repack repack = null;
        try {
            repack = (Repack)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new RepackRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return repack;
    }

    @Override
    public List<Object[]> getRepack(String startdate, String enddate) {
        String sql = "SELECT T_REPACK.ID AS ID,M_KARYAWAN_ID,TANGGAL,NAMA,JUMLAH,SATUAN_1 FROM t_repack join m_barang on (t_repack.m_barang_id = m_barang.id) WHERE TANGGAL>=? AND TANGGAL<=? ORDER BY T_REPACK.CREATED DESC";
        List rows = null;
        ArrayList<Object[]> repacks = new ArrayList<Object[]>();
        try {
            rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startdate, enddate});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("id"), row.get("m_karyawan_id"), row.get("tanggal"), row.get("nama"), row.get("jumlah"), row.get("satuan_1")};
                repacks.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return repacks;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM t_repack ORDER BY ID DESC LIMIT ?";
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

