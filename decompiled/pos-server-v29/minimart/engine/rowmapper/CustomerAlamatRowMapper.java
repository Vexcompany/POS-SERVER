/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.CustomerAlamat;
import org.springframework.jdbc.core.RowMapper;

public class CustomerAlamatRowMapper
implements RowMapper<CustomerAlamat> {
    public CustomerAlamat mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerAlamat ca = new CustomerAlamat();
        ca.setId(rs.getInt("id"));
        ca.setIdCustomer(rs.getString("id_customer"));
        ca.setJudul(rs.getString("judul"));
        ca.setPenerima(rs.getString("penerima"));
        ca.setAlamat(rs.getString("alamat"));
        ca.setNotesDriver(rs.getString("notes_driver"));
        ca.setTelepon(rs.getString("telepon"));
        ca.setLatitude(rs.getString("latitude"));
        ca.setLongitude(rs.getString("longitude"));
        ca.setDeletedAt(rs.getTimestamp("deleted_at"));
        ca.setCreatedAt(rs.getTimestamp("created_at"));
        ca.setUpdatedAt(rs.getTimestamp("updated_at"));
        ca.setIsDefault(rs.getBoolean("is_default"));
        ca.setIdRegKelurahan(rs.getString("id_reg_kelurahan"));
        return ca;
    }
}

