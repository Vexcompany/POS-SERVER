/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PenerimaanBsDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenerimaanBsDetailRowMapper
implements ParameterizedRowMapper<PenerimaanBsDetail> {
    public PenerimaanBsDetail mapRow(ResultSet rs, int i) throws SQLException {
        PenerimaanBsDetail penerimaanBsDetail = new PenerimaanBsDetail();
        penerimaanBsDetail.setId(rs.getLong("ID"));
        penerimaanBsDetail.setPenerimaanBsId(rs.getString("T_PENERIMAAN_BS_ID"));
        penerimaanBsDetail.setBarangId(rs.getString("M_BARANG_ID"));
        penerimaanBsDetail.setJumlahBarang(rs.getInt("JUMLAH_BARANG"));
        penerimaanBsDetail.setSatuan(rs.getString("SATUAN"));
        penerimaanBsDetail.setHpp(rs.getLong("HPP"));
        penerimaanBsDetail.setTotalHpp(rs.getLong("TOTAL_HPP"));
        return penerimaanBsDetail;
    }
}

