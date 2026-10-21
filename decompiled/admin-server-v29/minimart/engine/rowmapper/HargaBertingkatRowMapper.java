/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import minimart.engine.model.HargaBertingkat;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class HargaBertingkatRowMapper
implements ParameterizedRowMapper<HargaBertingkat> {
    public HargaBertingkat mapRow(ResultSet rs, int i) throws SQLException {
        HargaBertingkat hargaBertingkat = new HargaBertingkat();
        hargaBertingkat.setId(rs.getLong("ID"));
        hargaBertingkat.setBarangId(rs.getString("M_BARANG_ID"));
        hargaBertingkat.setHargaJual(rs.getLong("HARGA_JUAL"));
        hargaBertingkat.setQuantity(rs.getInt("QUANTITY"));
        hargaBertingkat.setCreated(new Date());
        hargaBertingkat.setUpdated(new Date());
        return hargaBertingkat;
    }
}

