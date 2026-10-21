/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoDiskonByJenisDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoDiscJenisDetailRowMapper
implements ParameterizedRowMapper<PromoDiskonByJenisDetail> {
    public PromoDiskonByJenisDetail mapRow(ResultSet rs, int i) throws SQLException {
        PromoDiskonByJenisDetail pdjd = new PromoDiskonByJenisDetail();
        pdjd.setId(rs.getInt("ID"));
        pdjd.setPromoDiscJenisId(rs.getInt("PROMO_DISC_JENIS_ID"));
        pdjd.setmJenisJenis(rs.getString("M_JENIS_JENIS"));
        pdjd.setDisc(rs.getFloat("DISC"));
        pdjd.setKhususPelanggan(rs.getString("KHUSUS_PELANGGAN"));
        return pdjd;
    }
}

