/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.RBarangSupplier;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class RBarangSupplierRowMapper
implements ParameterizedRowMapper<RBarangSupplier> {
    public RBarangSupplier mapRow(ResultSet rs, int i) throws SQLException {
        RBarangSupplier rbs = new RBarangSupplier();
        rbs.setId(rs.getString("ID"));
        rbs.setBarangId(rs.getString("M_BARANG_ID"));
        rbs.setSupplierId(rs.getString("M_SUPPLIER_ID"));
        return rbs;
    }
}

