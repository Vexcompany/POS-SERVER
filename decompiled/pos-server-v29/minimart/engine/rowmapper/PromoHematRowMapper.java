/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoHemat;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoHematRowMapper
implements ParameterizedRowMapper<PromoHemat> {
    public PromoHemat mapRow(ResultSet rs, int i) throws SQLException {
        PromoHemat promoHemat = new PromoHemat();
        promoHemat.setId(rs.getString("ID"));
        promoHemat.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        promoHemat.setTanggalMulai(rs.getDate("TANGGAL_MULAI"));
        promoHemat.setTanggalBerakhir(rs.getDate("TANGGAL_BERAKHIR"));
        promoHemat.setKeterangan(rs.getString("KETERANGAN"));
        promoHemat.setStatus(rs.getString("STATUS"));
        promoHemat.setCreated(rs.getDate("CREATED"));
        return promoHemat;
    }
}

