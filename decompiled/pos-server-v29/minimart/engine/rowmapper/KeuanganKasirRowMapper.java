/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.KeuanganKasir;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class KeuanganKasirRowMapper
implements ParameterizedRowMapper<KeuanganKasir> {
    public KeuanganKasir mapRow(ResultSet rs, int i) throws SQLException {
        KeuanganKasir keuanganKasir = new KeuanganKasir();
        keuanganKasir.setIdKasir(rs.getInt("ID_KASIR"));
        keuanganKasir.setJumlahUang(rs.getLong("JUMLAH_UANG"));
        keuanganKasir.setStatusKasir(rs.getString("STATUS_KASIR"));
        keuanganKasir.setOpenedBy(rs.getString("OPENED_BY"));
        keuanganKasir.setOpening(rs.getTimestamp("OPENING"));
        return keuanganKasir;
    }
}

