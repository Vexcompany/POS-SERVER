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

import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.PromoDiskonByJenisDao;
import minimart.engine.model.PromoDiskonByJenis;
import minimart.engine.rowmapper.PromoDiscJenisRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoDiskonByJenisDaoImpl
extends SimpleJdbcDaoSupport
implements PromoDiskonByJenisDao {
    public PromoDiskonByJenisDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPromoDiscJenis(PromoDiskonByJenis promoDiskonByJenis) {
        String sql = "INSERT INTO promo_disc_jenis (ID, M_KARYAWAN_ID, TANGGAL_MULAI, TANGGAL_BERAKHIR, KETERANGAN, STATUS) VALUES (:id, :karyawanId, :tanggalMulai, :tanggalBerakhir, :keterangan, :status)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoDiskonByJenis);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PromoDiskonByJenis getPromoByJenis(String id) {
        String sql = "SELECT * FROM promo_disc_jenis WHERE ID = ? ";
        PromoDiskonByJenis promoDiscJenis = null;
        try {
            promoDiscJenis = (PromoDiskonByJenis)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoDiscJenisRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoDiscJenis;
    }

    @Override
    public int updatePromoDiscJenis(PromoDiskonByJenis promoDiskonByJenis) {
        String sql = "UPDATE promo_disc_jenis SET M_KARYAWAN_ID=:karyawanId, TANGGAL_MULAI=:tanggalMulai, TANGGAL_BERAKHIR=:tanggalBerakhir, KETERANGAN=:keterangan, STATUS=:status WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoDiskonByJenis);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePromoDiscJenisById(String id) {
        String sql = "DELETE FROM promo_disc_jenis WHERE ID = ?";
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
    public int getLastId() {
        String sql = "SELECT ID FROM promo_disc_jenis ORDER BY ID DESC LIMIT ?";
        int id = 0;
        try {
            id = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{1});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return id;
    }

    @Override
    public boolean isPromoAktif(String nowDate) {
        String sql = "SELECT * FROM promo_disc_jenis WHERE ? >= TANGGAL_MULAI AND ? <= TANGGAL_BERAKHIR";
        PromoDiskonByJenis promoDiscJenis = null;
        try {
            promoDiscJenis = (PromoDiskonByJenis)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoDiscJenisRowMapper(), new Object[]{nowDate, nowDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        boolean result = false;
        if (promoDiscJenis == null) {
            result = false;
        } else if (promoDiscJenis != null) {
            result = true;
        }
        return result;
    }
}

