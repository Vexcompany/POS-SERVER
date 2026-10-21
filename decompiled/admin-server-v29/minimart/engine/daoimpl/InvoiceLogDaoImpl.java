/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.InvoiceReprintLogDao;
import minimart.engine.model.InvoiceReprintLog;
import minimart.engine.rowmapper.InvoiceReprintLogRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class InvoiceLogDaoImpl
extends SimpleJdbcDaoSupport
implements InvoiceReprintLogDao {
    public InvoiceLogDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int create(InvoiceReprintLog invoiceReprintDialog) {
        String sql = "INSERT INTO o_invoice_reprint_log (ID, T_PENJUALAN_ID, M_KARYAWAN_ID, CREATED) VALUES (:id, :penjualanId,:karyawanId, :created)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)invoiceReprintDialog);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<InvoiceReprintLog> getInvoiceReprintLog(String startDate, String endDate) {
        String sql = "SELECT * FROM o_invoice_reprint_log WHERE CREATED >= ? AND CREATED <= ? ";
        List pembelians = null;
        try {
            pembelians = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new InvoiceReprintLogRowMapper(), new Object[]{startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelians;
    }
}

