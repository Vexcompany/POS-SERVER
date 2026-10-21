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
import minimart.engine.dao.PromoTebusDao;
import minimart.engine.model.PromoTebus;
import minimart.engine.rowmapper.PromoTebusRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoTebusDaoImpl
extends SimpleJdbcDaoSupport
implements PromoTebusDao {
    public PromoTebusDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPromoTebus(PromoTebus promoTebus) {
        String sql = "INSERT INTO promo_tebus (ID, TANGGAL_MULAI, TANGGAL_BERAKHIR, KETERANGAN, STATUS, CREATED) VALUES (:id, :tanggalMulai, :tanggalBerakhir, :keterangan, :status, :created)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoTebus);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PromoTebus getPromoTebus(String id) {
        String sql = "SELECT * FROM promo_tebus WHERE ID = ? ";
        PromoTebus promoTebus = null;
        try {
            promoTebus = (PromoTebus)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoTebusRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebus;
    }

    @Override
    public int updatePromotebus(PromoTebus promoTebus) {
        String sql = "UPDATE promo_tebus SET TANGGAL_MULAI=:tanggalMulai, TANGGAL_BERAKHIR=:tanggalBerakhir, KETERANGAN=:keterangan, STATUS=:status WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoTebus);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePromoTebusById(int id) {
        String sql = "DELETE FROM promo_tebus WHERE ID = ?";
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
    public boolean isPromoAktif(String nowDate) {
        String sql = "SELECT * FROM promo_tebus WHERE ? >= TANGGAL_MULAI AND ? <= TANGGAL_BERAKHIR";
        PromoTebus promoTebus = null;
        try {
            promoTebus = (PromoTebus)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoTebusRowMapper(), new Object[]{nowDate, nowDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        boolean result = false;
        if (promoTebus == null) {
            result = false;
        } else if (promoTebus != null) {
            result = true;
        }
        return result;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM promo_tebus ORDER BY ID DESC LIMIT ?";
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
    public List<PromoTebus> getPromoTebus(String startDate, String endDate) {
        String sql = "SELECT * FROM promo_tebus WHERE CREATED >= ? AND CREATED <= ? ";
        List promoHemats = null;
        try {
            promoHemats = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusRowMapper(), new Object[]{startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHemats;
    }

    @Override
    public List<PromoTebus> getPromoTebusAll() {
        String sql = "SELECT * FROM promo_tebus";
        List promoHemats = null;
        try {
            promoHemats = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHemats;
    }

    @Override
    public int[] upsertPromoTebusBatch(List<PromoTebus> datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO promo_tebus (id, tanggal_mulai, tanggal_berakhir, keterangan, status, created) VALUES (:id, :tanggalMulai, :tanggalBerakhir, :keterangan, :status, :created) ON DUPLICATE KEY UPDATE tanggal_mulai = VALUES(tanggal_mulai), tanggal_berakhir = VALUES(tanggal_berakhir), keterangan = VALUES(keterangan), status = VALUES(status), created = VALUES(created)";
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
    public int deletePromoTebusByIds(String ids) {
        String sql = "DELETE FROM promo_tebus WHERE id in (" + ids + ")";
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

