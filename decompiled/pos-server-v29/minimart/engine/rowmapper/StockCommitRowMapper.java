/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.StockCommit;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class StockCommitRowMapper
implements ParameterizedRowMapper<StockCommit> {
    public StockCommit mapRow(ResultSet rs, int i) throws SQLException {
        StockCommit stockCommit = new StockCommit();
        stockCommit.setStockTransactionCommitId(rs.getLong("STOCK_TRANSACTION_COMMIT_ID"));
        stockCommit.setIdBarang(rs.getString("ID_BARANG"));
        stockCommit.setIdTransaksi(rs.getString("ID_TRANSAKSI"));
        stockCommit.setQueryType(rs.getString("QUERY_TYPE"));
        stockCommit.setStockType(rs.getString("STOCK_TYPE"));
        stockCommit.setTransactionType(rs.getString("TRANSACTION_TYPE"));
        stockCommit.setStatusCommit(rs.getBoolean("STATUS_COMMIT"));
        stockCommit.setStatusDelete(rs.getBoolean("STATUS_DELETE"));
        stockCommit.setJumlahBarang(rs.getFloat("JUMLAH_BARANG"));
        stockCommit.setTransactionCreated(rs.getTimestamp("TRANSACTION_CREATED"));
        stockCommit.setTransactionUpdated(rs.getTimestamp("TRANSACTION_UPDATED"));
        return stockCommit;
    }
}

