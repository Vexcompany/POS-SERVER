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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.PromoKhususMemberDetailDao;
import minimart.engine.model.PromoKhususMemberDetail;
import minimart.engine.rowmapper.PromoKhususMemberDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoKhususMemberDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PromoKhususMemberDetailDao {
    public PromoKhususMemberDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int create(PromoKhususMemberDetail promoKhususMemberDetail) {
        String sql = "INSERT INTO promo_khusus_member_detail (ID, PROMO_KHUSUS_MEMBER_ID, M_BARANG_ID, SATUAN, MIN_PEMBELIAN, HARGA_PROMO) VALUES (:id, :promoKhususMemberId, :barangId, :satuan, :minPembelian,:hargaPromo)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoKhususMemberDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int update(PromoKhususMemberDetail promoKhususMemberDetail) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int delete(String promoKhususMemberId) {
        String sql = "DELETE FROM promo_khusus_member_detail WHERE PROMO_KHUSUS_MEMBER_ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{promoKhususMemberId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PromoKhususMemberDetail getPromoKhususMemberDetail(String idBarang, String idCustomer) {
        String sql = "SELECT * FROM promo_khusus_member_detail JOIN promo_khusus_member ON (promo_khusus_member_detail.promo_khusus_member_id = promo_khusus_member.id) WHERE M_BARANG_ID = ? AND m_customer_id = ? AND end_date >= ? and start_date <= ?";
        PromoKhususMemberDetail promoKhususMemberDetail = new PromoKhususMemberDetail();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(new Date());
        try {
            promoKhususMemberDetail = (PromoKhususMemberDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoKhususMemberDetailRowMapper(), new Object[]{idBarang, idCustomer, startDateString, startDateString});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoKhususMemberDetail;
    }

    @Override
    public List<PromoKhususMemberDetail> getPromoKhususMemberDetail(String promoKhususMemberId) {
        String sql = "SELECT * FROM promo_khusus_member_detail WHERE PROMO_KHUSUS_MEMBER_ID = ?";
        List promoKhususMemberDetails = null;
        try {
            promoKhususMemberDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoKhususMemberDetailRowMapper(), new Object[]{promoKhususMemberId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoKhususMemberDetails;
    }
}

