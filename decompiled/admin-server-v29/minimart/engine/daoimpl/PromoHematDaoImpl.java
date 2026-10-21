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
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.PromoHematDao;
import minimart.engine.model.PromoHemat;
import minimart.engine.rowmapper.PromoHematRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoHematDaoImpl
extends SimpleJdbcDaoSupport
implements PromoHematDao {
    public PromoHematDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPromoHemat(PromoHemat promoHemat) {
        String sql = "INSERT INTO promo_hemat (ID, M_KARYAWAN_ID, TANGGAL_MULAI, TANGGAL_BERAKHIR, KETERANGAN, STATUS, CREATED) VALUES (:id, :karyawanId, :tanggalMulai, :tanggalBerakhir, :keterangan, :status, :created)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoHemat);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean isPromoAktif(String nowDate) {
        String sql = "SELECT * FROM promo_hemat WHERE ? >= TANGGAL_MULAI AND ? <= TANGGAL_BERAKHIR";
        PromoHemat promoHemat = null;
        try {
            promoHemat = (PromoHemat)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoHematRowMapper(), new Object[]{nowDate, nowDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        boolean result = false;
        if (promoHemat == null) {
            result = false;
        } else if (promoHemat != null) {
            result = true;
        }
        return result;
    }

    @Override
    public PromoHemat getPromoHematById(String id) {
        String sql = "SELECT * FROM promo_hemat WHERE ID = ? ";
        PromoHemat promoHemat = null;
        try {
            promoHemat = (PromoHemat)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoHematRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHemat;
    }

    @Override
    public int updatePromoHemat(PromoHemat promoHemat) {
        String sql = "UPDATE promo_hemat SET M_KARYAWAN_ID=:karyawanId, TANGGAL_MULAI=:tanggalMulai, TANGGAL_BERAKHIR=:tanggalBerakhir, KETERANGAN=:keterangan, STATUS=:status, CREATED=:created WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoHemat);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePromoHematById(String id) {
        String sql = "DELETE FROM promo_hemat WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM promo_hemat ORDER BY ID DESC LIMIT ?";
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
    public List<PromoHemat> getPromoHemat(String startDate, String endDate) {
        String sql = "SELECT * FROM promo_hemat WHERE CREATED >= ? AND CREATED <= ? ";
        List promoHemats = null;
        try {
            promoHemats = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoHematRowMapper(), new Object[]{startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHemats;
    }

    @Override
    public int[] upsertPromoHematBatch(List<PromoHemat> datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO promo_hemat (id, m_karyawan_id, tanggal_mulai, tanggal_berakhir, keterangan, status, created) VALUES (:id, :karyawanId, :tanggalMulai, :tanggalBerakhir, :keterangan, :status, :created) ON DUPLICATE KEY UPDATE m_karyawan_id = VALUES(m_karyawan_id), tanggal_mulai = VALUES(tanggal_mulai), tanggal_berakhir = VALUES(tanggal_berakhir), keterangan = VALUES(keterangan), status = VALUES(status), created = VALUES(created)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])datas.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql_upsert, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int deletePromoHematByIds(String ids) {
        String sql = "DELETE FROM promo_hemat WHERE id in (" + ids + ")";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PromoHemat> getPromoHematAll() {
        String sql = "SELECT * FROM promo_hemat";
        List promoHemats = null;
        try {
            promoHemats = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoHematRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHemats;
    }
}

