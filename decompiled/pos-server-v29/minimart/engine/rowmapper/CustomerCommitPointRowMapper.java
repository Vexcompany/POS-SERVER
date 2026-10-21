/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.CustomerPointCommit;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class CustomerCommitPointRowMapper
implements ParameterizedRowMapper<CustomerPointCommit> {
    public CustomerPointCommit mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerPointCommit poin = new CustomerPointCommit();
        poin.setPointTransactionCommitID(rs.getString("POINT_TRANSACTION_COMMIT_ID"));
        poin.setKtpCustomer(rs.getString("KTP_CUSTOMER"));
        poin.setIdTransaksi(rs.getString("ID_TRANSAKSI"));
        poin.setPointChanges(rs.getInt("POINT"));
        poin.setQueryType(rs.getString("QUERY_TYPE"));
        poin.setStatusCommit(rs.getBoolean("STATUS_COMMIT"));
        poin.setTransactionType(rs.getString("TRANSACTION_TYPE"));
        poin.setTransactionCreated(rs.getTimestamp("TRANSACTION_CREATED"));
        poin.setTransactionUpdated(rs.getTimestamp("TRANSACTION_UPDATED"));
        poin.setStatusDelete(rs.getBoolean("STATUS_DELETE"));
        return poin;
    }
}

