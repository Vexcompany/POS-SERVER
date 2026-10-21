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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.MutasiBarangDetailDao;
import minimart.engine.model.MutasiBarangDetail;
import minimart.engine.rowmapper.MutasiBarangDetailRowMapper;
import minimart.engine.service.BarangService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class MutasiBarangDetailDaoImpl
extends SimpleJdbcDaoSupport
implements MutasiBarangDetailDao {
    private BarangService barangService = new BarangService();

    public MutasiBarangDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createMutasiBarangDetail(MutasiBarangDetail mutasiBarangDetail) {
        String sql = "INSERT INTO t_mutasi_barang_detail (ID, T_MUTASI_BARANG_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, CREATED, UPDATED) VALUES (:id, :mutasiBarangId, :barangId, :jumlahBarang, :satuan, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)mutasiBarangDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateMutasiBarangDetail(MutasiBarangDetail mutasiBarangDetail) {
        String sql = "UPDATE t_mutasi_barang_detail SET T_MUTASI_BARANG_ID=:mutasiBarangId, M_BARANG_ID=:barangId, JUMLAH_BARANG=:jumlahBarang, SATUAN=:satuan, UPDATED=:updated WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)mutasiBarangDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Object[]> getMutasiBarangDetails(String idBarang, String startDate, String endDate) {
        String sql2 = "SELECT T_MUTASI_BARANG.ID AS T_MUTASI_BARANG_ID, T_MUTASI_BARANG.CREATED AS CREATED, M_BARANG_ID, DARI, MENUJU, JUMLAH_BARANG, SATUAN FROM T_MUTASI_BARANG_DETAIL JOIN T_MUTASI_BARANG ON(T_MUTASI_BARANG_DETAIL.T_MUTASI_BARANG_ID = T_MUTASI_BARANG.ID) WHERE M_BARANG_ID = ? AND T_MUTASI_BARANG.TANGGAL >=? AND T_MUTASI_BARANG.TANGGAL <= ?";
        String sql1 = "SELECT T_MUTASI_BARANG.ID, T_MUTASI_BARANG.CREATED, T_MUTASI_BARANG_DETAIL.M_BARANG_ID, T_MUTASI_BARANG.DARI, T_MUTASI_BARANG.MENUJU, T_MUTASI_BARANG_DETAIL.JUMLAH_BARANG, T_MUTASI_BARANG_DETAIL.SATUAN FROM T_MUTASI_BARANG_DETAIL JOIN T_MUTASI_BARANG ON(T_MUTASI_BARANG_DETAIL.T_MUTASI_BARANG_ID = T_MUTASI_BARANG.ID) WHERE M_BARANG_ID = ? AND T_MUTASI_BARANG.TANGGAL >=? AND T_MUTASI_BARANG.TANGGAL <= ?";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        List rows = this.getJdbcTemplate().queryForList(sql2, new Object[]{idBarang, startDate, endDate});
        try {
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("T_MUTASI_BARANG_ID"), row.get("CREATED"), row.get("M_BARANG_ID"), this.barangService.getBarang(row.get("M_BARANG_ID") + "").getNama(), row.get("DARI"), row.get("MENUJU"), row.get("JUMLAH_BARANG"), row.get("SATUAN")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<MutasiBarangDetail> getMutasiBarangDetails(String mutasiBarangId) {
        String sql = "SELECT * FROM t_mutasi_barang_detail WHERE T_MUTASI_BARANG_ID = ?";
        List mutasiBarangDetails = null;
        try {
            mutasiBarangDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new MutasiBarangDetailRowMapper(), new Object[]{mutasiBarangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return mutasiBarangDetails;
    }

    @Override
    public int deleteMutasiBarangDetail(long id) {
        String sql = "DELETE FROM t_mutasi_barang_detail WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

