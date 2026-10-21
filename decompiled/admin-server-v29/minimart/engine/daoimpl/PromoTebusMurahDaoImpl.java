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
import minimart.engine.dao.PromoTebusMurahDao;
import minimart.engine.model.PromoTebusMurah;
import minimart.engine.rowmapper.PromoTebusMurahRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoTebusMurahDaoImpl
extends SimpleJdbcDaoSupport
implements PromoTebusMurahDao {
    public PromoTebusMurahDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int create(PromoTebusMurah promoTebusMurah) {
        String sql = "INSERT INTO promo_tebus_murah (ID, M_KARYAWAN_ID, TANGGAL_MULAI, TANGGAL_BERAKHIR, STATUS, CREATED) VALUES (:id, :karyawanId, :tanggalMulai, :tanggalBerakhir, :status, :created)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoTebusMurah);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int update(PromoTebusMurah promoTebusMurah) {
        String sql = "UPDATE promo_tebus_murah SET M_KARYAWAN_ID=:karyawanId, TANGGAL_MULAI=:tanggalMulai, TANGGAL_BERAKHIR=:tanggalBerakhir, STATUS=:status, CREATED=:created WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoTebusMurah);
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
        String sql = "SELECT ID FROM promo_tebus_murah ORDER BY ID DESC LIMIT ?";
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
    public PromoTebusMurah getPromoTebusMurahById(String id) {
        String sql = "SELECT * FROM promo_tebus_murah WHERE ID = ? ";
        PromoTebusMurah promoTebusMurah = null;
        try {
            promoTebusMurah = (PromoTebusMurah)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoTebusMurahRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusMurah;
    }

    @Override
    public List<PromoTebusMurah> getPromoTebusMurah(String startDate, String endDate) {
        String sql = "SELECT * FROM promo_tebus_murah WHERE CREATED >= ? AND CREATED <= ? ";
        List promoTebusMurahs = null;
        try {
            promoTebusMurahs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusMurahRowMapper(), new Object[]{startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusMurahs;
    }

    @Override
    public List<PromoTebusMurah> getPromoTebusMurahAll() {
        String sql = "SELECT * FROM promo_tebus_murah";
        List promoTebusMurahs = null;
        try {
            promoTebusMurahs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusMurahRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusMurahs;
    }

    @Override
    public int[] upsertPromoTebusMurahBatch(List<PromoTebusMurah> datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO promo_tebus_murah (id, m_karyawan_id, tanggal_mulai, tanggal_berakhir, status, created) VALUES (:id, :karyawanId, :tanggalMulai, :tanggalBerakhir, :status, :created) ON DUPLICATE KEY UPDATE m_karyawan_id = VALUES(m_karyawan_id), tanggal_mulai = VALUES(tanggal_mulai), tanggal_berakhir = VALUES(tanggal_berakhir), status = VALUES(status), created = VALUES(created)";
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
    public int deletePromoTebusMurahByIds(String ids) {
        String sql = "DELETE FROM promo_tebus_murah WHERE id in (" + ids + ")";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

