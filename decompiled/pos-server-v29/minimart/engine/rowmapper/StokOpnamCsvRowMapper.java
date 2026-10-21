/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.StokOpnamCSV;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StokOpnamCsvRowMapper
implements ParameterizedRowMapper<StokOpnamCSV> {
    public StokOpnamCSV mapRow(ResultSet rs, int i) throws SQLException {
        StokOpnamCSV cSV = new StokOpnamCSV();
        cSV.setBarangId(rs.getString("M_BARANG_ID"));
        cSV.setBarcode(rs.getString("BARCODE"));
        cSV.setLokasi(rs.getString("LOKASI"));
        cSV.setPenanggungJawab(rs.getString("PENANGGUNG_JAWAB"));
        cSV.setStok(rs.getFloat("STOK"));
        return cSV;
    }
}

