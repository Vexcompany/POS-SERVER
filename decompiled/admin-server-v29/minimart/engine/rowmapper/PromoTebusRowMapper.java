/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoTebus;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoTebusRowMapper
implements ParameterizedRowMapper<PromoTebus> {
    public PromoTebus mapRow(ResultSet rs, int i) throws SQLException {
        PromoTebus promoTebus = new PromoTebus();
        promoTebus.setId(rs.getString("id"));
        promoTebus.setKeterangan(rs.getString("keterangan"));
        promoTebus.setStatus(rs.getString("status"));
        promoTebus.setTanggalBerakhir(rs.getDate("tanggal_berakhir"));
        promoTebus.setTanggalMulai(rs.getDate("tanggal_mulai"));
        promoTebus.setCreated(rs.getDate("CREATED"));
        return promoTebus;
    }
}

