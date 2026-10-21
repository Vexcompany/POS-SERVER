/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PembelianDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PembelianDetailRowMapper
implements ParameterizedRowMapper<PembelianDetail> {
    public PembelianDetail mapRow(ResultSet rs, int i) throws SQLException {
        PembelianDetail pembelianDetail = new PembelianDetail();
        pembelianDetail.setId(rs.getLong("ID"));
        pembelianDetail.setPembelianId(rs.getString("T_PEMBELIAN_ID"));
        pembelianDetail.setBarangId(rs.getString("M_BARANG_ID"));
        pembelianDetail.setJumlahBarang(rs.getFloat("JUMLAH_BARANG"));
        pembelianDetail.setSatuan(rs.getString("SATUAN"));
        pembelianDetail.setHargaBeli(rs.getLong("HARGA_BELI"));
        pembelianDetail.setDiscount2(rs.getLong("DISCOUNT_2"));
        pembelianDetail.setTotalHarga(rs.getLong("TOTAL_HARGA"));
        return pembelianDetail;
    }
}

