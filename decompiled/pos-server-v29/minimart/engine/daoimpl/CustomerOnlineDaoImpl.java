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
import minimart.engine.dao.CustomerOnlineDao;
import minimart.engine.model.Customer;
import minimart.engine.rowmapper.CustomerOnlineDBRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class CustomerOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements CustomerOnlineDao {
    public CustomerOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public Customer getCustomerById(String id) {
        String sql = "SELECT * FROM customer WHERE customerId = ?";
        Customer customer = null;
        try {
            customer = (Customer)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new CustomerOnlineDBRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public Customer addCustomerPoint(String id, Long point) {
        String sql = "CALL sp_add_customer_point(?,?)";
        Customer result = null;
        try {
            result = (Customer)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new CustomerOnlineDBRowMapper(), new Object[]{id, point});
        }
        catch (DataAccessException e) {
            System.out.println(result);
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

