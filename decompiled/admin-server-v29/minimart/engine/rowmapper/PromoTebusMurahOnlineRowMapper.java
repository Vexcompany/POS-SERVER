/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.controller.Helper;
import minimart.engine.model.PromoTebusMurah;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoTebusMurahOnlineRowMapper
implements ParameterizedRowMapper<PromoTebusMurah> {
    Helper helper = Helper.getInstance();

    public PromoTebusMurah mapRow(ResultSet rs, int rowNum) throws SQLException {
        PromoTebusMurah promoTebusMurah = new PromoTebusMurah();
        promoTebusMurah.setId(rs.getString("ID"));
        promoTebusMurah.setKaryawanId(this.helper.getKaryawan().getId());
        promoTebusMurah.setTanggalMulai(rs.getDate("TANGGAL_MULAI"));
        promoTebusMurah.setTanggalBerakhir(rs.getDate("TANGGAL_BERAKHIR"));
        promoTebusMurah.setStatus(rs.getString("STATUS"));
        promoTebusMurah.setCreated(rs.getDate("CREATED"));
        return promoTebusMurah;
    }
}

