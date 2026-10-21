/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.MutasiBarangDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class MutasiBarangDetailRowMapper
implements ParameterizedRowMapper<MutasiBarangDetail> {
    public MutasiBarangDetail mapRow(ResultSet rs, int i) throws SQLException {
        MutasiBarangDetail mutasiBarangDetail = new MutasiBarangDetail();
        mutasiBarangDetail.setId(rs.getLong("ID"));
        mutasiBarangDetail.setMutasiBarangId(rs.getString("T_MUTASI_BARANG_ID"));
        mutasiBarangDetail.setBarangId(rs.getString("M_BARANG_ID"));
        mutasiBarangDetail.setJumlahBarang(rs.getInt("JUMLAH_BARANG"));
        mutasiBarangDetail.setSatuan(rs.getString("SATUAN"));
        mutasiBarangDetail.setCreated(rs.getDate("CREATED"));
        mutasiBarangDetail.setUpdated(rs.getDate("UPDATED"));
        return mutasiBarangDetail;
    }
}

