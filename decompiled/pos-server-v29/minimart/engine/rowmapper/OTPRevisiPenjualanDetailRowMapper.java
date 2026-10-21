/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.OTPRevisiPenjualanDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class OTPRevisiPenjualanDetailRowMapper
implements ParameterizedRowMapper<OTPRevisiPenjualanDetail> {
    public OTPRevisiPenjualanDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        OTPRevisiPenjualanDetail detail = new OTPRevisiPenjualanDetail();
        detail.setId(rs.getLong("id"));
        detail.setT_otp_revisi_penjualan_id(rs.getInt("t_otp_revisi_penjualan_id"));
        detail.setId_sku(rs.getString("id_sku"));
        detail.setQty_before(rs.getFloat("qty_before"));
        detail.setSatuan_before(rs.getString("satuan_before"));
        detail.setHarga_before(rs.getLong("harga_before"));
        detail.setDiscount_before(rs.getLong("discount_before"));
        detail.setTotal_before(rs.getLong("total_before"));
        detail.setQty_after(rs.getFloat("qty_after"));
        detail.setSatuan_after(rs.getString("satuan_after"));
        detail.setHarga_after(rs.getLong("harga_after"));
        detail.setDiscount_after(rs.getLong("discount_after"));
        detail.setTotal_after(rs.getLong("total_after"));
        detail.setId_cabang(rs.getInt("id_cabang"));
        return detail;
    }
}

