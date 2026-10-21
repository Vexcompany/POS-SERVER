/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.KelebihanStok;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class KelebihanStokRowMapper
implements ParameterizedRowMapper<KelebihanStok> {
    public KelebihanStok mapRow(ResultSet rs, int rowNum) throws SQLException {
        KelebihanStok kelebihanStok = new KelebihanStok();
        kelebihanStok.setId(rs.getInt("ID"));
        kelebihanStok.setBarangId(rs.getString("M_BARANG_ID"));
        kelebihanStok.setStokInput(rs.getFloat("STOK_INPUT"));
        kelebihanStok.setStokSystem(rs.getFloat("STOK_SYSTEM"));
        kelebihanStok.setInputOleh(rs.getString("INPUT_OLEH"));
        kelebihanStok.setOtorisasiOleh(rs.getString("OTORISASI_OLEH"));
        kelebihanStok.setCreated(rs.getDate("CREATED"));
        kelebihanStok.setKeterangan(rs.getString("KETERANGAN"));
        return null;
    }
}

