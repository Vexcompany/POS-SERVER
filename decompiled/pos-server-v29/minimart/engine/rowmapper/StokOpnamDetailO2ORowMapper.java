/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.StokOpnamDetailO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StokOpnamDetailO2ORowMapper
implements ParameterizedRowMapper<StokOpnamDetailO2O> {
    public StokOpnamDetailO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        StokOpnamDetailO2O stokOpnamDetailO2O = new StokOpnamDetailO2O();
        stokOpnamDetailO2O.setId_stok_opnam_detail_offline(rs.getInt("ID"));
        stokOpnamDetailO2O.setStok_opnam_id(rs.getInt("STOK_OPNAM_ID"));
        stokOpnamDetailO2O.setM_barang_id(rs.getString("M_BARANG_ID"));
        stokOpnamDetailO2O.setJumlah_komputer(rs.getFloat("JUMLAH_KOMPUTER"));
        stokOpnamDetailO2O.setJumlah_toko(rs.getFloat("JUMLAH_TOKO"));
        stokOpnamDetailO2O.setTotal(rs.getLong("TOTAL"));
        return stokOpnamDetailO2O;
    }
}

