/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.HistoryHarga;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class HistoryHargaRowMapper
implements ParameterizedRowMapper<HistoryHarga> {
    public HistoryHarga mapRow(ResultSet rs, int i) throws SQLException {
        HistoryHarga historyHarga = new HistoryHarga();
        historyHarga.setId(rs.getLong("ID"));
        historyHarga.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        historyHarga.setBarangId(rs.getString("M_BARANG_ID"));
        historyHarga.setSatuan(rs.getString("SATUAN"));
        historyHarga.setHargaBaru(rs.getLong("HARGA_BARU"));
        historyHarga.setHargaLama(rs.getLong("HARGA_LAMA"));
        historyHarga.setTanggal(rs.getDate("TANGGAL"));
        historyHarga.setCreated(rs.getTimestamp("CREATED"));
        return historyHarga;
    }
}

