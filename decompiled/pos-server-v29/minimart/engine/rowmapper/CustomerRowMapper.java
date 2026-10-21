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

public class CustomerRowMapper
implements ParameterizedRowMapper<Customer> {
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getString("ID"));
        customer.setKtp(rs.getString("KTP"));
        customer.setNama(rs.getString("NAMA"));
        customer.setAlamat(rs.getString("ALAMAT"));
        customer.setKota(rs.getString("KOTA"));
        customer.setTanggalLahir(rs.getDate("TANGGAL_LAHIR"));
        customer.setTelepon(rs.getString("TELEPON"));
        customer.setHutang(rs.getLong("HUTANG"));
        customer.setPoint(rs.getLong("POINT"));
        customer.setAvailable(rs.getBoolean("AVAILABLE"));
        customer.setCreated(rs.getDate("CREATED"));
        customer.setUpdated(rs.getDate("UPDATED"));
        customer.setLimitKredit(rs.getInt("LIMIT_KREDIT"));
        customer.setTop(rs.getInt("TOP"));
        customer.setTipe(rs.getString("TIPE"));
        customer.setKeterangan(rs.getString("KETERANGAN"));
        customer.setTag(rs.getString("TAG"));
        customer.setNamaPerusahaan(rs.getString("NAMA_PERUSAHAAN"));
        customer.setCreatedByCabang(rs.getString("createdByCabang"));
        customer.setNomorRekening(rs.getString("nomor_rekening"));
        customer.setBank(rs.getString("bank"));
        int idCabang = rs.getInt("idCabang");
        if (idCabang > 0) {
            customer.setIdCabang(idCabang);
        } else {
            customer.setIdCabang(0);
        }
        return customer;
    }
}

