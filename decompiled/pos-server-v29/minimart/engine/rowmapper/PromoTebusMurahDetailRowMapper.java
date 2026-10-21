/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoTebusMurahDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoTebusMurahDetailRowMapper
implements ParameterizedRowMapper<PromoTebusMurahDetail> {
    public PromoTebusMurahDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        PromoTebusMurahDetail promoTebusMurahDetail = new PromoTebusMurahDetail();
        promoTebusMurahDetail.setId(rs.getInt("ID"));
        promoTebusMurahDetail.setPromoTebusMurahId(rs.getString("PROMO_TEBUS_MURAH_ID"));
        promoTebusMurahDetail.setBarangId(rs.getString("M_BARANG_ID"));
        promoTebusMurahDetail.setSatuan(rs.getString("SATUAN"));
        promoTebusMurahDetail.setMinimumBelanja(rs.getLong("MINIMUM_BELANJA"));
        promoTebusMurahDetail.setHargaPromo(rs.getLong("HARGA_PROMO"));
        promoTebusMurahDetail.setMaksimalKuantiti(rs.getFloat("MAKSIMAL_KUANTITI"));
        return promoTebusMurahDetail;
    }
}

