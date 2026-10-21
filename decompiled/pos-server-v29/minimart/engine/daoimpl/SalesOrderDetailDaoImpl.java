/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.SalesOrderDetailDao;
import minimart.engine.model.SalesOrderDetail;
import minimart.engine.rowmapper.SalesOrderDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class SalesOrderDetailDaoImpl
extends SimpleJdbcDaoSupport
implements SalesOrderDetailDao {
    public SalesOrderDetailDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<SalesOrderDetail> getSalesOrderDetailByIdSalesOrder(int idSalesOrder) {
        String sql = "SELECT sod.*, i.nama_item as nama_barang FROM t_sales_order_detail sod inner join item i on sod.id_sku = i.id_sku and sod.id_cabang = i.id_cabang WHERE id_sales_order = ? and sod.available = 1";
        List sods = null;
        try {
            sods = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new SalesOrderDetailRowMapper(), new Object[]{idSalesOrder});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return sods;
    }
}

