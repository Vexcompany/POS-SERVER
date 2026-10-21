/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Pembelian;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PembelianRowMapper
implements ParameterizedRowMapper<Pembelian> {
    public Pembelian mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pembelian pembelian = new Pembelian();
        pembelian.setId(rs.getString("ID"));
        pembelian.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        pembelian.setSupplierId(rs.getString("M_SUPPLIER_ID"));
        pembelian.setFakturId(rs.getString("FAKTUR_ID"));
        pembelian.setTanggal(rs.getDate("TANGGAL"));
        pembelian.setPembayaran(rs.getString("PEMBAYARAN"));
        pembelian.setJatuhTempo(rs.getDate("JATUH_TEMPO"));
        pembelian.setDiscount1(rs.getFloat("DISCOUNT_1"));
        pembelian.setPpn(rs.getFloat("PPN"));
        pembelian.setTotal(rs.getLong("TOTAL"));
        pembelian.setStatus(rs.getString("STATUS"));
        pembelian.setKeterangan(rs.getString("KETERANGAN"));
        pembelian.setCreated(rs.getTimestamp("CREATED"));
        pembelian.setUpdated(rs.getTimestamp("UPDATED"));
        pembelian.setTitipan(rs.getLong("TITIPAN"));
        pembelian.setPelunasan(rs.getString("PELUNASAN"));
        return pembelian;
    }
}

