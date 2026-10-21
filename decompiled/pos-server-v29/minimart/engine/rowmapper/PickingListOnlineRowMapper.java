/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PickingList;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PickingListOnlineRowMapper
implements ParameterizedRowMapper<PickingList> {
    public PickingList mapRow(ResultSet rs, int rowNum) throws SQLException {
        PickingList pl = new PickingList();
        pl.setId(rs.getInt("id"));
        pl.setIdCabangTerima(rs.getInt("id_cabang_terima"));
        pl.setIdCabangKirim1(rs.getInt("id_cabang_kirim_1"));
        pl.setIdCabangKirim2(rs.getInt("id_cabang_kirim_2"));
        pl.setTanggal(rs.getDate("tanggal"));
        pl.setCreated(rs.getDate("created"));
        pl.setUpdated(rs.getDate("updated"));
        pl.setAvailable(rs.getBoolean("available"));
        pl.setDoGenerated(rs.getBoolean("do_generated"));
        return pl;
    }
}

