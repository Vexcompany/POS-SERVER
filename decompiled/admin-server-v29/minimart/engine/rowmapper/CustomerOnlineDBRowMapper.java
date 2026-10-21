/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Customer;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class CustomerOnlineDBRowMapper
implements ParameterizedRowMapper<Customer> {
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getString("customerId"));
        customer.setKtp(rs.getString("ktp"));
        customer.setNama(rs.getString("nama"));
        customer.setAlamat(rs.getString("alamat"));
        customer.setKota(rs.getString("kota"));
        customer.setTanggalLahir(rs.getDate("tanggal_lahir"));
        customer.setTelepon(rs.getString("telepon"));
        customer.setHutang(rs.getLong("hutang"));
        customer.setPoint(rs.getLong("point"));
        customer.setAvailable(rs.getBoolean("available"));
        customer.setCreated(rs.getDate("created"));
        customer.setUpdated(rs.getDate("updated"));
        customer.setLimitKredit(rs.getInt("limit_kredit"));
        customer.setTop(rs.getInt("top"));
        customer.setTipe(rs.getString("tipe"));
        customer.setKeterangan(rs.getString("keterangan"));
        int idCabang = rs.getInt("idCabang");
        if (idCabang > 0) {
            customer.setIdCabang(idCabang);
        } else {
            customer.setIdCabang(0);
        }
        return customer;
    }
}

