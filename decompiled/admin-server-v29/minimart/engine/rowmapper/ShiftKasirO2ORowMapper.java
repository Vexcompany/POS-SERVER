/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.ShiftKasirO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class ShiftKasirO2ORowMapper
implements ParameterizedRowMapper<ShiftKasirO2O> {
    public ShiftKasirO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShiftKasirO2O shiftKasirO2O = new ShiftKasirO2O();
        shiftKasirO2O.setId_offline(rs.getInt("ID"));
        shiftKasirO2O.setId_kasir(rs.getInt("ID_KASIR"));
        shiftKasirO2O.setOpened_by(rs.getString("OPENED_BY"));
        shiftKasirO2O.setClosed_by(rs.getString("CLOSED_BY"));
        shiftKasirO2O.setOpening(rs.getTimestamp("OPENING"));
        shiftKasirO2O.setClosing(rs.getTimestamp("CLOSING"));
        shiftKasirO2O.setTotal_uang(rs.getLong("TOTAL_UANG"));
        shiftKasirO2O.setJumlah_setoran(rs.getLong("JUMLAH_SETORAN"));
        shiftKasirO2O.setTransfer(rs.getLong("TRANSFER"));
        shiftKasirO2O.setDebit(rs.getLong("DEBIT"));
        shiftKasirO2O.setQris(rs.getLong("QRIS"));
        shiftKasirO2O.setVoucher(rs.getLong("VOUCHER"));
        return shiftKasirO2O;
    }
}

