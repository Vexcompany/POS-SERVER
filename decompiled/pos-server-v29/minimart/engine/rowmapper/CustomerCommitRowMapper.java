/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.CustomerCommit;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class CustomerCommitRowMapper
implements ParameterizedRowMapper<CustomerCommit> {
    public CustomerCommit mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerCommit customer = new CustomerCommit();
        customer.setCustomerTransactionCommitID(rs.getString("CUSTOMER_TRANSACTION_COMMIT_ID"));
        customer.setIdCustomer(rs.getString("ID_CUSTOMER"));
        customer.setQueryType(rs.getString("QUERY_TYPE"));
        customer.setStatusCommit(rs.getBoolean("STATUS_COMMIT"));
        customer.setTransactionType(rs.getString("TRANSACTION_TYPE"));
        customer.setTransactionCreated(rs.getTimestamp("TRANSACTION_CREATED"));
        customer.setTransactionUpdated(rs.getTimestamp("TRANSACTION_UPDATED"));
        customer.setStatusDelete(rs.getBoolean("STATUS_DELETE"));
        return customer;
    }
}

