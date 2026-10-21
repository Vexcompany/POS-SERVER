/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Voucher;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class VoucherRowMapper
implements ParameterizedRowMapper<Voucher> {
    public Voucher mapRow(ResultSet rs, int rowNum) throws SQLException {
        Voucher voucher = new Voucher();
        voucher.setId(rs.getString("ID"));
        voucher.setCustomerId(rs.getString("M_CUSTOMER_ID"));
        voucher.setJumlah(rs.getInt("JUMLAH"));
        voucher.setAvailable(rs.getString("AVAILABLE"));
        voucher.setExpiredDate(rs.getDate("EXPIRED_DATE"));
        voucher.setExchangeDate(rs.getDate("EXCHANGE_DATE"));
        return voucher;
    }
}

