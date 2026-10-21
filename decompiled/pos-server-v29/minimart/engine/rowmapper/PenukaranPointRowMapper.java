/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PenukaranPoint;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenukaranPointRowMapper
implements ParameterizedRowMapper<PenukaranPoint> {
    public PenukaranPoint mapRow(ResultSet rs, int i) throws SQLException {
        PenukaranPoint penukaranPoint = new PenukaranPoint();
        penukaranPoint.setId(rs.getInt("ID"));
        penukaranPoint.setCustomerId(rs.getString("M_CUSTOMER_ID"));
        penukaranPoint.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        penukaranPoint.setJumlahPoint(rs.getInt("JUMLAH_POINT"));
        penukaranPoint.setKeterangan(rs.getString("KETERANGAN"));
        penukaranPoint.setTanggal(rs.getDate("TANGGAL"));
        return penukaranPoint;
    }
}

