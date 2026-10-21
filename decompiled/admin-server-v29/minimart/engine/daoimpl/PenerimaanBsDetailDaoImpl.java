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
import minimart.engine.dao.PenerimaanBsDetailDao;
import minimart.engine.model.PenerimaanBsDetail;
import minimart.engine.rowmapper.PenerimaanBsDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PenerimaanBsDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PenerimaanBsDetailDao {
    public PenerimaanBsDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPenerimaanBsDetail(PenerimaanBsDetail penerimaanBsDetail) {
        String sql = "INSERT INTO t_penerimaan_bs_detail (ID, T_PENERIMAAN_BS_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, HPP, TOTAL_HPP, CREATED, UPDATED) VALUES (:id, :penerimaanBsId, :barangId, :jumlahBarang, :satuan, :hpp, :totalHpp, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penerimaanBsDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PenerimaanBsDetail> getPenerimaanBsDetailsByPenerimaanBsId(String penerimaanBsId) {
        String sql = "SELECT * FROM t_penerimaan_bs_detail WHERE T_PENERIMAAN_BS_ID LIKE ?";
        List penerimaanBsDetails = null;
        try {
            penerimaanBsDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenerimaanBsDetailRowMapper(), new Object[]{penerimaanBsId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penerimaanBsDetails;
    }

    @Override
    public int updatePenerimaanBsDetail(PenerimaanBsDetail penerimaanBsDetail) {
        String sql = "UPDATE t_penerimaan_bs_detail SET ID=:id, T_PENERIMAAN_BS_ID=:penerimaanBsId, M_BARANG_ID=:barangId, JUMLAH_BARANG=:jumlahBarang,SATUAN=:satuan, HPP=:hpp, TOTAL_HPP=:totalHpp, UPDATED=:updated WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penerimaanBsDetail.getId());
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePenerimaanBsDetail(PenerimaanBsDetail penerimaanBsDetail) {
        String sql = "DELETE FROM t_penerimaan_bs_detail WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{penerimaanBsDetail.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

