/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Brand;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class BrandRowMapper
implements ParameterizedRowMapper<Brand> {
    public Brand mapRow(ResultSet rs, int rowNum) throws SQLException {
        Brand brand = new Brand();
        brand.setId(rs.getString("ID"));
        brand.setNamaBrand(rs.getString("NAMA_BRAND"));
        brand.setCreated(rs.getDate("CREATED"));
        brand.setUpdated(rs.getDate("UPDATED"));
        brand.setAvailable(rs.getInt("AVAILABLE"));
        return brand;
    }
}

