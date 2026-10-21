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
import minimart.engine.dao.PromoKhususMemberDao;
import minimart.engine.model.PromoKhususMember;
import minimart.engine.rowmapper.PromoKhususMemberRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoKhususMemberDaoImpl
extends SimpleJdbcDaoSupport
implements PromoKhususMemberDao {
    public PromoKhususMemberDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int create(PromoKhususMember promoKhususMember) {
        String sql = "INSERT INTO promo_khusus_member (ID, M_CUSTOMER_ID, START_DATE, END_DATE) VALUES (:id, :customerId, :startDate, :endDate)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoKhususMember);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int update(PromoKhususMember promoKhususMember) {
        String sql = "UPDATE promo_khusus_member SET M_CUSTOMER_ID=:customerId, START_DATE=:startDate, END_DATE=:endDate WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoKhususMember);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PromoKhususMember get(String customerId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PromoKhususMember getPromoById(String idPromo) {
        String sql = "SELECT * FROM promo_khusus_member WHERE ID = ? ";
        PromoKhususMember promoKhususMember = null;
        try {
            promoKhususMember = (PromoKhususMember)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoKhususMemberRowMapper(), new Object[]{idPromo});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoKhususMember;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM promo_khusus_member ORDER BY ID DESC LIMIT ?";
        String id = "";
        try {
            id = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{1});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return id;
    }
}

