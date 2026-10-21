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
import minimart.engine.dao.PromoVoucherDao;
import minimart.engine.model.PromoVoucher;
import minimart.engine.rowmapper.PromoVoucherRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoVoucherDaoImpl
extends SimpleJdbcDaoSupport
implements PromoVoucherDao {
    public PromoVoucherDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPromoVoucherDao(PromoVoucher promoVoucher) {
        String sql = "INSERT INTO promo_voucher (ID, MAXIMUM_VOUCHER, MINIMUM_TRANSAKSI, KHUSUS_PELANGGAN, STATUS) VALUES (:id, :maximumVoucher, :minimumTransaksi, :khususPelanggan, :status)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoVoucher);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePromoVoucherDao(PromoVoucher promoVoucher) {
        String sql = "UPDATE promo_voucher SET MAXIMUM_VOUCHER=:maximumVoucher, MINIMUM_TRANSAKSI=:minimumTransaksi, KHUSUS_PELANGGAN=:khususPelanggan, STATUS=:status WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoVoucher);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePromoVoucherById(int id) {
        String sql = "DELETE FROM promo_voucher WHERE ID = ?";
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
    public PromoVoucher getPromoVoucherById(int id) {
        String sql = "SELECT * FROM promo_voucher WHERE ID = ? ";
        PromoVoucher promoVoucher = null;
        try {
            promoVoucher = (PromoVoucher)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoVoucherRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoVoucher;
    }
}

