/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoVoucherDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoVoucherDetailRowMapper
implements ParameterizedRowMapper<PromoVoucherDetail> {
    public PromoVoucherDetail mapRow(ResultSet rs, int i) throws SQLException {
        PromoVoucherDetail promoVoucherDetail = new PromoVoucherDetail();
        promoVoucherDetail.setBarangId(rs.getString("M_BARANG_ID"));
        promoVoucherDetail.setId(rs.getInt("ID"));
        promoVoucherDetail.setPromoVoucherId(rs.getInt("PROMO_VOUCHER_ID"));
        promoVoucherDetail.setMaxPembelian(rs.getInt("MAX_PEMBELIAN"));
        promoVoucherDetail.setSatuan(rs.getString("SATUAN"));
        return promoVoucherDetail;
    }
}

