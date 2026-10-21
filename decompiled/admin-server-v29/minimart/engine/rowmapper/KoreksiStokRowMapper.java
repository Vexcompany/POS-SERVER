/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.KoreksiStok;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class KoreksiStokRowMapper
implements ParameterizedRowMapper<KoreksiStok> {
    public KoreksiStok mapRow(ResultSet rs, int i) throws SQLException {
        KoreksiStok koreksiStok = new KoreksiStok();
        koreksiStok.setId(rs.getInt("ID"));
        koreksiStok.setBarangId(rs.getString("M_BARANG_ID"));
        koreksiStok.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        koreksiStok.setDate(rs.getDate("TANGGAL"));
        koreksiStok.setStokAwal(rs.getFloat("STOK_AWAL"));
        koreksiStok.setJumlah(rs.getFloat("JUMLAH"));
        koreksiStok.setSatuan(rs.getString("SATUAN"));
        koreksiStok.setJenis(rs.getString("JENIS"));
        koreksiStok.setSatuan(rs.getString("STATUS"));
        return koreksiStok;
    }
}

