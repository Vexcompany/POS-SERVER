/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.SalesOrder;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class SalesOrderRowMapper
implements ParameterizedRowMapper<SalesOrder> {
    public SalesOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        SalesOrder so = new SalesOrder();
        so.setId(rs.getInt("id"));
        so.setM_customer_id(rs.getString("m_customer_id"));
        so.setM_sales_id(rs.getString("m_sales_id"));
        so.setTanggal(rs.getDate("tanggal"));
        so.setJatuh_tempo(rs.getDate("jatuh_tempo"));
        so.setKeterangan(rs.getString("keterangan"));
        so.setTotal_penjualan(rs.getLong("total_penjualan"));
        so.setStatus(rs.getString("status"));
        so.setId_cabang(rs.getInt("id_cabang"));
        so.setUnique_code(rs.getString("unique_code"));
        so.setApproved(rs.getBoolean("approved"));
        so.setImported(rs.getBoolean("imported"));
        return so;
    }
}

