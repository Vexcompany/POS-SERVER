/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.PenukaranPointO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenukaranPointO2ORowMapper
implements ParameterizedRowMapper<PenukaranPointO2O> {
    public PenukaranPointO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        PenukaranPointO2O penukaranPointO2O = new PenukaranPointO2O();
        penukaranPointO2O.setId_penukaran_point_offline(rs.getInt("ID"));
        penukaranPointO2O.setM_customer_id(rs.getString("M_CUSTOMER_ID"));
        penukaranPointO2O.setM_karyawan_id(rs.getString("M_KARYAWAN_ID"));
        penukaranPointO2O.setJumlah_point(rs.getInt("JUMLAH_POINT"));
        penukaranPointO2O.setKeterangan(rs.getString("KETERANGAN"));
        penukaranPointO2O.setTanggal(rs.getDate("TANGGAL"));
        return penukaranPointO2O;
    }
}

