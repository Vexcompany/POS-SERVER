/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PenjualanParsial;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenjualanParsialRowMapper
implements ParameterizedRowMapper<PenjualanParsial> {
    public PenjualanParsial mapRow(ResultSet rs, int rowNum) throws SQLException {
        PenjualanParsial penjualanParsial = new PenjualanParsial();
        penjualanParsial.setId(rs.getString("ID"));
        penjualanParsial.setCustomerId(rs.getString("m_customer_id"));
        penjualanParsial.setKaryawanId(rs.getString("m_karyawan_id"));
        penjualanParsial.setTanggal(rs.getDate("TANGGAL"));
        return penjualanParsial;
    }
}

