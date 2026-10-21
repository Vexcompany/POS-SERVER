/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PengembalianBs;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PengembalianBsRowMapper
implements ParameterizedRowMapper<PengembalianBs> {
    public PengembalianBs mapRow(ResultSet rs, int i) throws SQLException {
        PengembalianBs pengembalianBs = new PengembalianBs();
        pengembalianBs.setId(rs.getString("ID"));
        pengembalianBs.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        pengembalianBs.setSupplierId(rs.getString("M_SUPPLIER_ID"));
        pengembalianBs.setTanggal(rs.getDate("TANGGAL"));
        pengembalianBs.setKeterangan(rs.getString("KETERANGAN"));
        pengembalianBs.setTotal(rs.getLong("TOTAL"));
        pengembalianBs.setCreated(rs.getDate("CREATED"));
        pengembalianBs.setUpdated(rs.getDate("UPDATED"));
        return pengembalianBs;
    }
}

