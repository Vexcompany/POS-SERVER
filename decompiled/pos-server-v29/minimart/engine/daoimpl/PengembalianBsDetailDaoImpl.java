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
import minimart.engine.dao.PengembalianBsDetailDao;
import minimart.engine.model.PengembalianBsDetail;
import minimart.engine.rowmapper.PengembalianBsDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PengembalianBsDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PengembalianBsDetailDao {
    public PengembalianBsDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPengembalianBsDetail(PengembalianBsDetail pengembalianBsDetail) {
        String sql = "INSERT INTO t_pengembalian_bs_detail (ID, T_PENGEMBALIAN_BS_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, HPP, TOTAL_HPP, CREATED, UPDATED) VALUES (:id, :pengembalianBsId, :barangId, :jumlahBarang, :satuan, :hpp, :totalHpp,:created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pengembalianBsDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePengembalianBsDetail(PengembalianBsDetail pengembalianBsDetail) {
        String sql = "UPDATE t_pengembalian_bs_detail SET T_PENGEMBALIAN_BS_ID=:pengembalianBsId, M_BARANG_ID=:barangId, JUMLAH_BARANG=:jumlahBarang, SATUAN=:satuan, HPP=:hpp, TOTAL_HPP=:totalHpp, UPDATED=:updated WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pengembalianBsDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PengembalianBsDetail> getPengembalianBsDetails(String pengembalianBsId) {
        String sql = "SELECT * FROM t_pengembalian_bs_detail WHERE T_PENGEMBALIAN_BS_ID = ?";
        List pengembalianBsDetails = null;
        try {
            pengembalianBsDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PengembalianBsDetailRowMapper(), new Object[]{pengembalianBsId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pengembalianBsDetails;
    }

    @Override
    public int deletePengembalianBsDetail(PengembalianBsDetail pengembalianBsDetail) {
        String sql = "DELETE FROM t_pengembalian_bs_detail WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{pengembalianBsDetail.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

