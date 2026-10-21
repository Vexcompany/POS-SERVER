/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PromoDiskonByJenis;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PromoDiscJenisRowMapper
implements ParameterizedRowMapper<PromoDiskonByJenis> {
    public PromoDiskonByJenis mapRow(ResultSet rs, int i) throws SQLException {
        PromoDiskonByJenis pdj = new PromoDiskonByJenis();
        pdj.setId(rs.getInt("ID"));
        pdj.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        pdj.setTanggalMulai(rs.getDate("TANGGAL_MULAI"));
        pdj.setTanggalBerakhir(rs.getDate("Tanggal_Berakhir"));
        pdj.setKeterangan(rs.getString("Keterangan"));
        pdj.setStatus(rs.getString("STATUS"));
        return pdj;
    }
}

