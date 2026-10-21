/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.MutasiCabangDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class MacDetailRowMapper
implements ParameterizedRowMapper<MutasiCabangDetail> {
    public MutasiCabangDetail mapRow(ResultSet rs, int i) throws SQLException {
        MutasiCabangDetail macDetail = new MutasiCabangDetail();
        macDetail.setId(rs.getLong("ID"));
        macDetail.setMutasiCabangId("T_MUTASI_CABANG_ID");
        macDetail.setBarangId(rs.getString("M_BARANG_ID"));
        macDetail.setJumlahBarang(rs.getFloat("JUMLAH_BARANG"));
        macDetail.setSatuan(rs.getString("SATUAN"));
        macDetail.setHargaJual(rs.getLong("HARGA_JUAL"));
        macDetail.setDiscount2(rs.getLong("DISCOUNT_2"));
        macDetail.setTotalHarga(rs.getLong("TOTAL_HARGA"));
        macDetail.setTotalKeuntungan(rs.getLong("TOTAL_KEUNTUNGAN"));
        macDetail.setUpdated(rs.getDate("UPDATED"));
        macDetail.setCreated(rs.getDate("CREATED"));
        return macDetail;
    }
}

