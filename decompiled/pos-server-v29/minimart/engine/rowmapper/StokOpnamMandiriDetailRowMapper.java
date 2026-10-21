/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.StokOpnamMandiriDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StokOpnamMandiriDetailRowMapper
implements ParameterizedRowMapper<StokOpnamMandiriDetail> {
    public StokOpnamMandiriDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        StokOpnamMandiriDetail stokOpnamMandiriDetail = new StokOpnamMandiriDetail();
        stokOpnamMandiriDetail.setId(rs.getInt("ID"));
        stokOpnamMandiriDetail.setStokOpnamMandiriId(rs.getString("STOK_OPNAM_MANDIRI_ID"));
        stokOpnamMandiriDetail.setBarangId(rs.getString("M_BARANG_ID"));
        stokOpnamMandiriDetail.setInput1(rs.getInt("INPUT_1"));
        stokOpnamMandiriDetail.setInput2(rs.getInt("INPUT_2"));
        stokOpnamMandiriDetail.setInput3(rs.getInt("INPUT_3"));
        stokOpnamMandiriDetail.setStokKomp1(rs.getInt("STOK_KOMP_1"));
        stokOpnamMandiriDetail.setStokKomp2(rs.getInt("STOK_KOMP_2"));
        stokOpnamMandiriDetail.setStokKomp3(rs.getInt("STOK_KOMP_3"));
        stokOpnamMandiriDetail.setHpp(rs.getLong("HPP"));
        stokOpnamMandiriDetail.setCreated(rs.getDate("CREATED"));
        stokOpnamMandiriDetail.setNomorUrut(rs.getInt("NOMOR_URUT"));
        return stokOpnamMandiriDetail;
    }
}

