/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.VoucherO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class VoucherO2ORowMapper
implements ParameterizedRowMapper<VoucherO2O> {
    public VoucherO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        VoucherO2O voucherO2O = new VoucherO2O();
        voucherO2O.setId_voucher_offline(rs.getString("ID"));
        voucherO2O.setM_customer_id(rs.getString("M_CUSTOMER_ID"));
        voucherO2O.setJumlah(rs.getInt("JUMLAH"));
        voucherO2O.setAvailable(rs.getString("AVAILABLE"));
        voucherO2O.setExchange_date(rs.getDate("EXCHANGE_DATE"));
        voucherO2O.setExpired_date(rs.getDate("EXPIRED_DATE"));
        return voucherO2O;
    }
}

