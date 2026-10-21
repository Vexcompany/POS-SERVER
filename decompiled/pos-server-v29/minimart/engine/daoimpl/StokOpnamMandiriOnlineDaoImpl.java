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

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.StokOpnamMandiriOnlineDao;
import minimart.engine.model.StokOpnamMandiri;
import minimart.engine.model.StokOpnamMandiriDetail;
import minimart.engine.rowmapper.StokOpnamMandiriDetailRowMapper;
import minimart.engine.rowmapper.StokOpnamMandiriRowMapper;
import minimart.engine.service.KonstantaService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class StokOpnamMandiriOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements StokOpnamMandiriOnlineDao {
    KonstantaService ks = new KonstantaService();

    public StokOpnamMandiriOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<StokOpnamMandiri> getStokOpnamMandiriOnline(String ids) {
        List som = null;
        String sql = "SELECT * from t_stok_opnam_mandiri WHERE id_cabang = ? ";
        if (!ids.isEmpty()) {
            sql = sql + "and id NOT IN (" + ids + ") ";
        }
        sql = sql + "and deleted is NULL and tanggal BETWEEN DATE_SUB(NOW(), INTERVAL 180 DAY) AND NOW()";
        System.out.println("SQL SOMO: " + sql);
        int idCabang = Integer.parseInt(this.ks.getConstantValue("idCabang"));
        try {
            som = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamMandiriRowMapper(), new Object[]{idCabang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return som;
    }

    @Override
    public List<StokOpnamMandiri> getStokOpnamMandiriOnlineDeleted(String ids) {
        List som = null;
        String sql = "SELECT * from t_stok_opnam_mandiri WHERE id_cabang = ? ";
        if (!ids.isEmpty()) {
            sql = sql + "and id IN (" + ids + ") ";
        }
        sql = sql + "and deleted is NOT NULL and tanggal BETWEEN DATE_SUB(NOW(), INTERVAL 180 DAY) AND NOW()";
        System.out.println("SQL SOMO Deleted: " + sql);
        int idCabang = Integer.parseInt(this.ks.getConstantValue("idCabang"));
        try {
            som = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamMandiriRowMapper(), new Object[]{idCabang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return som;
    }

    @Override
    public List<StokOpnamMandiriDetail> getStokOpnamMandiriDetailOnline(String ids) {
        List somd = null;
        int idCabang = Integer.parseInt(this.ks.getConstantValue("idCabang"));
        String sql = "SELECT somd.* from t_stok_opnam_mandiri_detail somd inner join t_stok_opnam_mandiri som on som.id = somd.stok_opnam_mandiri_id ";
        sql = sql + "where ";
        if (!ids.isEmpty()) {
            sql = sql + "somd.id not in (" + ids + ") and ";
        }
        sql = sql + "som.id_cabang = ? and som.deleted is NULL and som.tanggal BETWEEN DATE_SUB(NOW(), INTERVAL 180 DAY) AND NOW()";
        System.out.println("SQL SOMDO: " + sql);
        try {
            somd = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamMandiriDetailRowMapper(), new Object[]{idCabang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return somd;
    }

    @Override
    public int updateStokOpnamMandiri(StokOpnamMandiri stokOpnamMandiri) {
        String sql = "UPDATE t_stok_opnam_mandiri SET m_karyawan_id=:karyawanId, tanggal_submit=:tanggalSubmit, tanggal_input_1=:tanggalInput1, tanggal_input_2=:tanggalInput2, tanggal_input_3=:tanggalInput3, updated=:updated, keterangan=:keterangan, tipe=:tipe WHERE id=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)stokOpnamMandiri);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] createBatchStokOpnamMandiriDetailDuplicateUpdate(List<StokOpnamMandiriDetail> somds) {
        String sql = "INSERT INTO t_stok_opnam_mandiri_detail (id, stok_opnam_mandiri_id, m_barang_id, input_1, input_2, input_3, stok_komp_1, stok_komp_2, stok_komp_3, hpp, created) VALUES (:id, :stokOpnamMandiriId, :barangId, :input1, :input2, :input3, :stokKomp1, :stokKomp2, :stokKomp3, :hpp, :created) ON DUPLICATE KEY UPDATE stok_opnam_mandiri_id = VALUES(stok_opnam_mandiri_id), m_barang_id = VALUES(m_barang_id), input_1 = VALUES(input_1), input_2 = VALUES(input_2), input_3 = VALUES(input_3), stok_komp_1 = VALUES(stok_komp_1), stok_komp_2 = VALUES(stok_komp_2), stok_komp_3 = VALUES(stok_komp_3), hpp = VALUES(hpp)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])somds.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }
}

