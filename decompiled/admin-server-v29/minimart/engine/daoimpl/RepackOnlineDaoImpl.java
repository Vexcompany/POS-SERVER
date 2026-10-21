/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.RepackOnlineDao;
import minimart.engine.model.Repack;
import minimart.engine.model.RepackDetail;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class RepackOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements RepackOnlineDao {
    public RepackOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public int createRepackOnline(Repack repack, String idCabang) {
        String sql = "INSERT INTO t_repack (id, id_repack_offline, m_karyawan_id, tanggal, created, m_barang_id, jumlah, id_cabang) VALUES (CONCAT(:id, '_', '" + idCabang + "'), :id, :karyawanId, :tanggal, :created, :barangId, :jumlah, '" + idCabang + "') ON DUPLICATE KEY UPDATE m_karyawan_id = VALUES(m_karyawan_id), tanggal = VALUES(tanggal), created = VALUES(created), m_barang_id = VALUES(m_barang_id), jumlah = VALUES(jumlah)";
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
    public int createRepackDetailOnline(RepackDetail repackDetail, String idCabang) {
        String sql = "INSERT INTO t_repack_detail (t_repack_id, m_barang, jumlah_barang, satuan, id_cabang) VALUES (:repackId, :barangId, :jumlahBarang, :satuan, '" + idCabang + "')";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)repackDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] createRepackDetailBatchOnline(List<RepackDetail> repackDetails, String idCabang) {
        String sql = "INSERT INTO t_repack_detail (t_repack_id, m_barang_id, jumlah_barang, satuan, id_cabang) VALUES (:repackId, :barangId, :jumlahBarang, :satuan, '" + idCabang + "')";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])repackDetails.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }
}

