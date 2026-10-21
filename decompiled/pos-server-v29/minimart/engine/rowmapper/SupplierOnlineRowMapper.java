/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.SupplierOnline;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class SupplierOnlineRowMapper
implements ParameterizedRowMapper<SupplierOnline> {
    public SupplierOnline mapRow(ResultSet rs, int rowNum) throws SQLException {
        SupplierOnline supplier = new SupplierOnline();
        supplier.setId(rs.getString("ID"));
        supplier.setNama(rs.getString("NAMA"));
        supplier.setAlamat(rs.getString("ALAMAT"));
        supplier.setKota(rs.getString("KOTA"));
        supplier.setTelepon(rs.getString("TELEPON"));
        supplier.setContactPerson(rs.getString("CONTACT_PERSON"));
        supplier.setAvailable(rs.getBoolean("AVAILABLE"));
        supplier.setCreated(rs.getDate("CREATED"));
        supplier.setUpdated(rs.getDate("UPDATED"));
        supplier.setTop(rs.getInt("TOP"));
        supplier.setIdOffline(rs.getString("ID_OFFLINE"));
        supplier.setTipeToko(rs.getString("TIPE_TOKO"));
        return supplier;
    }
}

