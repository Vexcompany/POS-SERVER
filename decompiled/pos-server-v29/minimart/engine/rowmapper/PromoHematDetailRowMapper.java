/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoHematDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoHematDetailRowMapper
implements ParameterizedRowMapper<PromoHematDetail> {
    public PromoHematDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        PromoHematDetail promoHematDetail = new PromoHematDetail();
        promoHematDetail.setId(rs.getInt("ID"));
        promoHematDetail.setPromoHematId(rs.getString("PROMO_HEMAT_ID"));
        promoHematDetail.setBarangId(rs.getString("M_BARANG_ID"));
        promoHematDetail.setSatuan(rs.getString("SATUAN"));
        promoHematDetail.setMinimumPembelian(rs.getFloat("MINIMUM_PEMBELIAN"));
        promoHematDetail.setHargaPromo(rs.getLong("HARGA_PROMO"));
        promoHematDetail.setKhususPelanggan(rs.getString("KHUSUS_PELANGGAN"));
        return promoHematDetail;
    }
}

