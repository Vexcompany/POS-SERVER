/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Region;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class RegionRowMapper
implements ParameterizedRowMapper<Region> {
    public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
        Region region = new Region();
        region.setIdProvinsi(rs.getString("id_provinsi"));
        region.setProvinsi(rs.getString("provinsi"));
        region.setIdKota(rs.getString("id_kota"));
        region.setKota(rs.getString("kota"));
        region.setIdKecamatan(rs.getString("id_kecamatan"));
        region.setKecamatan(rs.getString("kecamatan"));
        region.setIdKelurahan(rs.getString("id_kelurahan"));
        region.setKelurahan(rs.getString("kelurahan"));
        return region;
    }
}

