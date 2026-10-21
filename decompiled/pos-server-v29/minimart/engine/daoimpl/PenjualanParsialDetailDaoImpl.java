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
import minimart.engine.dao.PenjualanParsialDetailDao;
import minimart.engine.model.PenjualanParsialDetail;
import minimart.engine.rowmapper.PenjualanParsialDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PenjualanParsialDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PenjualanParsialDetailDao {
    public PenjualanParsialDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int create(PenjualanParsialDetail penjualanParsialDetail) {
        String sql = "INSERT INTO t_penjualan_parsial_detail (ID, M_BARANG_ID, T_PENJUALAN_PARSIAL_ID, JUMLAH_BARANG, SATUAN, HARGA_JUAL, DISCOUNT, TOTAL_HARGA) VALUES (:id, :barangId, :penjualanParsialId,:jumlahBarang, :satuan, :hargaJual, :discount, :totalHarga)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penjualanParsialDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int update(PenjualanParsialDetail penjualanParsialDetail) {
        String sql = "UPDATE t_penjualan_parsial_detail SET M_BARANG_ID=:barangId, JUMLAH_BARANG=:jumlahBarang, SATUAN=:satuan, HARGA_JUAL=:hargaJual,  DISCOUNT=:discount, TOTAL_HARGA=:totalHarga WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penjualanParsialDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int delete(String penjualanParsialId) {
        String sql = "DELETE FROM t_penjualan_parsial_detail WHERE T_PENJUALAN_PARSIAL_ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{penjualanParsialId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PenjualanParsialDetail> getPenjualanParsials(String penjualanParsialId) {
        String sql = "SELECT * FROM t_penjualan_parsial_detail JOIN m_barang ON (t_penjualan_parsial_detail.M_BARANG_ID = m_barang.ID) WHERE T_PENJUALAN_PARSIAL_ID = ? ORDER BY M_BARANG.NAMA";
        List penjualanParsialDetails = null;
        try {
            penjualanParsialDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenjualanParsialDetailRowMapper(), new Object[]{penjualanParsialId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualanParsialDetails;
    }
}

