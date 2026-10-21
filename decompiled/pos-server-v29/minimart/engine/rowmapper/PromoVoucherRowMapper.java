/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoVoucher;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoVoucherRowMapper
implements ParameterizedRowMapper<PromoVoucher> {
    public PromoVoucher mapRow(ResultSet rs, int i) throws SQLException {
        PromoVoucher promoVoucher = new PromoVoucher();
        promoVoucher.setId(rs.getInt("ID"));
        promoVoucher.setKhususPelanggan(rs.getString("KHUSUS_PELANGGAN"));
        promoVoucher.setMaximumVoucher(rs.getInt("MAXIMUM_VOUCHER"));
        promoVoucher.setMinimumTransaksi(rs.getInt("MINIMUM_TRANSAKSI"));
        promoVoucher.setStatus(rs.getString("STATUS"));
        return promoVoucher;
    }
}

