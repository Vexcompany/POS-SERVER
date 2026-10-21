/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Barcode;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class BarcodeRowMapper
implements ParameterizedRowMapper<Barcode> {
    public Barcode mapRow(ResultSet rs, int i) throws SQLException {
        Barcode barcode = new Barcode();
        barcode.setId(rs.getInt("ID"));
        barcode.setM_barang_id(rs.getString("M_BARANG_ID"));
        barcode.setSatuan(rs.getString("SATUAN"));
        barcode.setBarcode(rs.getString("BARCODE"));
        barcode.setCreated(rs.getDate("CREATED"));
        barcode.setUpdated(rs.getDate("UPDATED"));
        return barcode;
    }
}

