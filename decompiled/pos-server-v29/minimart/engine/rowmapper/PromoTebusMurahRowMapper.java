/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoTebusMurah;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoTebusMurahRowMapper
implements ParameterizedRowMapper<PromoTebusMurah> {
    public PromoTebusMurah mapRow(ResultSet rs, int rowNum) throws SQLException {
        PromoTebusMurah promoTebusMurah = new PromoTebusMurah();
        promoTebusMurah.setId(rs.getString("ID"));
        promoTebusMurah.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        promoTebusMurah.setTanggalMulai(rs.getDate("TANGGAL_MULAI"));
        promoTebusMurah.setTanggalBerakhir(rs.getDate("TANGGAL_BERAKHIR"));
        promoTebusMurah.setStatus(rs.getString("STATUS"));
        promoTebusMurah.setCreated(rs.getDate("CREATED"));
        return promoTebusMurah;
    }
}

