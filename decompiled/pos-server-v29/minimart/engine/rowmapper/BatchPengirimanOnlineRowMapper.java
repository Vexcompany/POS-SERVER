/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.BatchPengiriman;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class BatchPengirimanOnlineRowMapper
implements ParameterizedRowMapper<BatchPengiriman> {
    public BatchPengiriman mapRow(ResultSet rs, int rowNum) throws SQLException {
        BatchPengiriman bp = new BatchPengiriman();
        bp.setId(rs.getInt("id"));
        bp.setKodeBatch(rs.getString("kode_batch"));
        bp.setIdCabangPenerima(rs.getInt("id_cabang_penerima"));
        bp.setCreated_at(rs.getDate("created_at"));
        bp.setNamaDriver(rs.getString("nama_driver"));
        bp.setNopol(rs.getString("nopol"));
        bp.setStatus(rs.getString("status"));
        bp.setTanggal(rs.getDate("tanggal"));
        bp.setUpdated_at(rs.getDate("updated_at"));
        return bp;
    }
}

