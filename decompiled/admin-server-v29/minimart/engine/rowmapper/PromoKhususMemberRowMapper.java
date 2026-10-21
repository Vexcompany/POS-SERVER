/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoKhususMember;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoKhususMemberRowMapper
implements ParameterizedRowMapper<PromoKhususMember> {
    public PromoKhususMember mapRow(ResultSet rs, int rowNum) throws SQLException {
        PromoKhususMember promoKhususMember = new PromoKhususMember();
        promoKhususMember.setId(rs.getString("ID"));
        promoKhususMember.setCustomerId(rs.getString("M_CUSTOMER_ID"));
        promoKhususMember.setStartDate(rs.getDate("START_DATE"));
        promoKhususMember.setEndDate(rs.getDate("END_DATE"));
        return promoKhususMember;
    }
}

