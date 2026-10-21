/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.InvoiceReprintLog;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class InvoiceReprintLogRowMapper
implements ParameterizedRowMapper<InvoiceReprintLog> {
    public InvoiceReprintLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        InvoiceReprintLog invoiceReprintLog = new InvoiceReprintLog();
        invoiceReprintLog.setId(rs.getInt("ID"));
        invoiceReprintLog.setPenjualanId(rs.getString("T_PENJUALAN_ID"));
        invoiceReprintLog.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        invoiceReprintLog.setCreated(rs.getDate("CREATED"));
        return invoiceReprintLog;
    }
}

