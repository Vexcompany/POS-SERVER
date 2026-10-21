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
import minimart.engine.dao.PromoDiskonByJenisDetailDao;
import minimart.engine.model.PromoDiskonByJenisDetail;
import minimart.engine.rowmapper.PromoDiscJenisDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoDiskonByJenisDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PromoDiskonByJenisDetailDao {
    public PromoDiskonByJenisDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPromoDiscJenisDetail(PromoDiskonByJenisDetail promoDiskonByJenisDetail) {
        String sql = "INSERT INTO promo_disc_jenis_detail (ID, PROMO_DISC_JENIS_ID, M_JENIS_JENIS, DISC, KHUSUS_PELANGGAN) VALUES (:id, :promoDiscJenisId, :mJenisJenis, :disc,:khususPelanggan)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoDiskonByJenisDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PromoDiskonByJenisDetail> getPromoDiscJenisDetail(String promoId) {
        String sql = "SELECT * FROM promo_disc_jenis_detail WHERE PROMO_DISC_JENIS_ID = ?";
        List promoDiskonByJenisDetails = null;
        try {
            promoDiskonByJenisDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoDiscJenisDetailRowMapper(), new Object[]{promoId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoDiskonByJenisDetails;
    }

    @Override
    public int deletePromoDiscJenisDetail(String promoId) {
        String sql = "DELETE FROM promo_disc_jenis_detail WHERE PROMO_DISC_JENIS_ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{promoId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePromoDiscJenisDetail(PromoDiskonByJenisDetail promoDiskonByJenisDetail) {
        String sql = "UPDATE promo_disc_jenis_detail SET M_JENIS_JENIS=:mJenisJenis, DISC=:disc, KHUSUS_PELANGGAN=:khususPelanggan WHERE M_JENIS_JENIS=:mJenisJenis";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoDiskonByJenisDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PromoDiskonByJenisDetail getPromoDiscJenisDetailByMJenis(String jenisBarang) {
        String sql = "SELECT * FROM promo_disc_jenis_detail JOIN promo_disc_jenis ON (PROMO_DISC_JENIS_DETAIL.PROMO_DISC_JENIS_ID = PROMO_DISC_JENIS.ID) WHERE M_JENIS_JENIS = ? AND STATUS = 'AKTIF'";
        PromoDiskonByJenisDetail promoDiskonByJenisDetail = null;
        try {
            promoDiskonByJenisDetail = (PromoDiskonByJenisDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoDiscJenisDetailRowMapper(), new Object[]{jenisBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoDiskonByJenisDetail;
    }
}

