/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PaketDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PaketDetailRowMapper
implements ParameterizedRowMapper<PaketDetail> {
    public PaketDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        PaketDetail paketDetail = new PaketDetail();
        paketDetail.setId(rs.getString("ID"));
        paketDetail.setPaketId(rs.getString("M_PAKET_ID"));
        paketDetail.setBarangId(rs.getString("M_BARANG_ID"));
        paketDetail.setJumlahBarang(rs.getFloat("JUMLAH_BARANG"));
        return paketDetail;
    }
}

