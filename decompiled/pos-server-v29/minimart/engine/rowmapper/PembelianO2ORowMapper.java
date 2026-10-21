/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.PembelianO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PembelianO2ORowMapper
implements ParameterizedRowMapper<PembelianO2O> {
    public PembelianO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        PembelianO2O pembelianO2O = new PembelianO2O();
        pembelianO2O.setId_pembelian_offline(rs.getString("ID"));
        pembelianO2O.setM_karyawan_id(rs.getString("M_KARYAWAN_ID"));
        pembelianO2O.setM_supplier_id(rs.getString("M_SUPPLIER_ID"));
        pembelianO2O.setFaktur_id(rs.getString("FAKTUR_ID"));
        pembelianO2O.setTanggal(rs.getDate("TANGGAL"));
        pembelianO2O.setPembayaran(rs.getString("PEMBAYARAN"));
        pembelianO2O.setJatuh_tempo(rs.getDate("JATUH_TEMPO"));
        pembelianO2O.setDiscount_1(rs.getFloat("DISCOUNT_1"));
        pembelianO2O.setPpn(rs.getFloat("PPN"));
        pembelianO2O.setTotal(rs.getLong("TOTAL"));
        pembelianO2O.setStatus(rs.getString("STATUS"));
        pembelianO2O.setKeterangan(rs.getString("KETERANGAN"));
        pembelianO2O.setCreated(rs.getTimestamp("CREATED"));
        pembelianO2O.setUpdated(rs.getTimestamp("UPDATED"));
        pembelianO2O.setTitipan(rs.getLong("TITIPAN"));
        pembelianO2O.setPelunasan(rs.getString("PELUNASAN"));
        return pembelianO2O;
    }
}

