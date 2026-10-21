/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoTebusDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoTebusDetailRowMapper
implements ParameterizedRowMapper<PromoTebusDetail> {
    public PromoTebusDetail mapRow(ResultSet rs, int i) throws SQLException {
        PromoTebusDetail promoTebusDetail = new PromoTebusDetail();
        promoTebusDetail.setId(rs.getInt("id"));
        promoTebusDetail.setPromoTebusId(rs.getString("promo_tebus_Id"));
        promoTebusDetail.setBarangId(rs.getString("m_barang_id"));
        promoTebusDetail.setJumlah(rs.getFloat("jumlah"));
        promoTebusDetail.setSatuan(rs.getString("satuan"));
        promoTebusDetail.setPromoBarangId(rs.getString("promo_barang_id"));
        promoTebusDetail.setDiscount(rs.getLong("discount"));
        promoTebusDetail.setSatuanPromo(rs.getString("satuan_promo"));
        return promoTebusDetail;
    }
}

