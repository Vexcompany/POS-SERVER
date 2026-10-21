/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.OTPPengembalianDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class OTPPengembalianDetailRowMapper
implements ParameterizedRowMapper<OTPPengembalianDetail> {
    public OTPPengembalianDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        OTPPengembalianDetail otpPengembalianDetail = new OTPPengembalianDetail();
        otpPengembalianDetail.setId(rs.getLong("id"));
        otpPengembalianDetail.setT_otp_pengembalian_id(rs.getInt("t_otp_pengembalian_id"));
        otpPengembalianDetail.setId_sku(rs.getString("id_sku"));
        otpPengembalianDetail.setJumlah(rs.getFloat("jumlah"));
        otpPengembalianDetail.setSatuan(rs.getString("satuan"));
        otpPengembalianDetail.setHarga(rs.getLong("harga"));
        otpPengembalianDetail.setId_cabang(rs.getInt("id_cabang"));
        otpPengembalianDetail.setDiscount(rs.getFloat("discount"));
        return otpPengembalianDetail;
    }
}

