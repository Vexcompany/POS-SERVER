/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.ShiftKasir;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class ShiftKasirRowMapper
implements ParameterizedRowMapper<ShiftKasir> {
    public ShiftKasir mapRow(ResultSet rs, int i) throws SQLException {
        ShiftKasir shiftKasir = new ShiftKasir();
        shiftKasir.setId(rs.getInt("ID"));
        shiftKasir.setIdKasir(rs.getInt("ID_KASIR"));
        shiftKasir.setOpening(rs.getTimestamp("OPENING"));
        shiftKasir.setClosing(rs.getTimestamp("CLOSING"));
        shiftKasir.setClosedBy(rs.getString("CLOSED_BY"));
        shiftKasir.setJumlahSetoran(rs.getLong("JUMLAH_SETORAN"));
        shiftKasir.setTotalUang(rs.getLong("TOTAL_UANG"));
        shiftKasir.setOpenedBy(rs.getString("OPENED_BY"));
        shiftKasir.setTransfer(rs.getLong("TRANSFER"));
        shiftKasir.setDebit(rs.getLong("DEBIT"));
        shiftKasir.setQris(rs.getLong("QRIS"));
        shiftKasir.setVoucher(rs.getLong("VOUCHER"));
        return shiftKasir;
    }
}

