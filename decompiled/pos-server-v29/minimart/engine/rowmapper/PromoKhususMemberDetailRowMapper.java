/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoKhususMemberDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoKhususMemberDetailRowMapper
implements ParameterizedRowMapper<PromoKhususMemberDetail> {
    public PromoKhususMemberDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        PromoKhususMemberDetail promoKhususMemberDetail = new PromoKhususMemberDetail();
        promoKhususMemberDetail.setId(rs.getInt("ID"));
        promoKhususMemberDetail.setPromoKhususMemberId(rs.getString("PROMO_KHUSUS_MEMBER_ID"));
        promoKhususMemberDetail.setBarangId(rs.getString("M_BARANG_ID"));
        promoKhususMemberDetail.setSatuan(rs.getString("SATUAN"));
        promoKhususMemberDetail.setMinPembelian(rs.getInt("MIN_PEMBELIAN"));
        promoKhususMemberDetail.setHargaPromo(rs.getLong("HARGA_PROMO"));
        return promoKhususMemberDetail;
    }
}

