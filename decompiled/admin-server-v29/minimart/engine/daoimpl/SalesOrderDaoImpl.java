/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.SalesOrderDao;
import minimart.engine.model.SalesOrder;
import minimart.engine.rowmapper.SalesOrderRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class SalesOrderDaoImpl
extends SimpleJdbcDaoSupport
implements SalesOrderDao {
    public SalesOrderDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public SalesOrder getSalesOrderByUniqueCode(String uc) {
        String sql = "CALL sp_get_sales_order_by_unique_code(?)";
        SalesOrder so = null;
        try {
            so = (SalesOrder)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new SalesOrderRowMapper(), new Object[]{uc});
        }
        catch (DataAccessException e) {
            System.out.println((Object)e);
        }
        return so;
    }

    @Override
    public int updateImportedSalesOrder(int id) {
        String sql = "UPDATE t_sales_order SET imported = 1, updated = CURRENT_TIMESTAMP WHERE id = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

