/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.OTPUbahHarga;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class OTPUbahHargaRowMapper
implements ParameterizedRowMapper<OTPUbahHarga> {
    public OTPUbahHarga mapRow(ResultSet rs, int rowNum) throws SQLException {
        OTPUbahHarga ouh = new OTPUbahHarga();
        ouh.setId(rs.getLong("id"));
        ouh.setM_customer_id(rs.getString("m_customer_id"));
        ouh.setId_sku(rs.getString("id_sku"));
        ouh.setId_cabang(rs.getInt("id_cabang"));
        ouh.setSatuan(rs.getString("satuan"));
        ouh.setJumlah(rs.getFloat("jumlah"));
        ouh.setHarga(rs.getLong("harga"));
        ouh.setUnique_code(rs.getString("unique_code"));
        ouh.setUsed(rs.getBoolean("used"));
        ouh.setM_karyawan_id(rs.getString("m_karyawan_id"));
        return ouh;
    }
}

