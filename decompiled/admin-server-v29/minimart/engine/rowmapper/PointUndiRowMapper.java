/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PointUndi;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PointUndiRowMapper
implements ParameterizedRowMapper<PointUndi> {
    public PointUndi mapRow(ResultSet rs, int i) throws SQLException {
        PointUndi pointUndi = new PointUndi();
        pointUndi.setId(rs.getInt("ID"));
        pointUndi.setMinimumTransaksi(rs.getInt("MINIMUM_TRANSAKSI"));
        pointUndi.setTanggalMulai(rs.getDate("TANGGAL_MULAI"));
        pointUndi.setTanggalBerakhir(rs.getDate("TANGGAL_BERAKHIR"));
        pointUndi.setStatus(rs.getString("STATUS"));
        return pointUndi;
    }
}

