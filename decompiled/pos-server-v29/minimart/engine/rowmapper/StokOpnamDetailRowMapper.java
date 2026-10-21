/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.StokOpnamDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StokOpnamDetailRowMapper
implements ParameterizedRowMapper<StokOpnamDetail> {
    public StokOpnamDetail mapRow(ResultSet rs, int i) throws SQLException {
        StokOpnamDetail stokOpnamDetail = new StokOpnamDetail();
        stokOpnamDetail.setId(rs.getInt("ID"));
        stokOpnamDetail.setStokOpnamId(rs.getInt("STOK_OPNAM_ID"));
        stokOpnamDetail.setBarangId(rs.getString("M_BARANG_ID"));
        stokOpnamDetail.setJumlahKomp(rs.getFloat("JUMLAH_KOMPUTER"));
        stokOpnamDetail.setJumlahToko(rs.getFloat("JUMLAH_TOKO"));
        stokOpnamDetail.setTotal(rs.getLong("TOTAL"));
        return stokOpnamDetail;
    }
}

