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
import minimart.engine.dao.PromoVoucherDetailDao;
import minimart.engine.model.PromoVoucherDetail;
import minimart.engine.rowmapper.PromoVoucherDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoVoucherDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PromoVoucherDetailDao {
    public PromoVoucherDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPromoVoucherDetail(PromoVoucherDetail promoVoucherDetail) {
        String sql = "INSERT INTO promo_voucher_detail (ID, PROMO_VOUCHER_ID ,M_BARANG_ID, MAX_PEMBELIAN, SATUAN) VALUES (:id, :promoVoucherId, :barangId, :maxPembelian, :satuan)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoVoucherDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePromoVoucherDetail(PromoVoucherDetail promoVoucherDetail) {
        String sql = "UPDATE promo_voucher_detail SET MAX_PEMBELIAN =: maxPembelian, SATUAN=:satuanWHERE M_BARANG_ID=:barangId";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoVoucherDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePromoVoucherDetailByPromoVoucherId(int promoVoucherId) {
        String sql = "DELETE FROM promo_voucher_detail WHERE PROMO_VOUCHER_ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{promoVoucherId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PromoVoucherDetail> getPromoVoucherDetailByPromoVoucherId(int promoVoucherId) {
        String sql = "SELECT * FROM promo_voucher_detail WHERE PROMO_VOUCHER_ID = ?";
        List promoHematDetails = null;
        try {
            promoHematDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoVoucherDetailRowMapper(), new Object[]{promoVoucherId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHematDetails;
    }

    @Override
    public List<PromoVoucherDetail> getPromoVoucherDetailByNamaBarang(String namaBarang) {
        String sql = "SELECT * FROM promo_voucher_detail join m_barang on (promo_voucher_detail.m_barang_id = m_barang.id) WHERE m_barang.nama like ? order by m_barang.nama";
        List promoHematDetails = null;
        namaBarang = '%' + namaBarang + '%';
        try {
            promoHematDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoVoucherDetailRowMapper(), new Object[]{namaBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHematDetails;
    }

    @Override
    public PromoVoucherDetail getPromoVoucherByBarangId(String barangId) {
        String sql = "SELECT * FROM promo_voucher_detail JOIN promo_voucher ON (PROMO_VOUCHER_DETAIL.PROMO_VOUCHER_ID = PROMO_VOUCHER.ID) WHERE M_BARANG_ID = ? AND STATUS = 'AKTIF'";
        PromoVoucherDetail promoHematDetail = null;
        try {
            promoHematDetail = (PromoVoucherDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoVoucherDetailRowMapper(), new Object[]{barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHematDetail;
    }
}

