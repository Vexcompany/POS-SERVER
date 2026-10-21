/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.StockCommitDao;
import minimart.engine.model.StockCommit;
import minimart.engine.rowmapper.StockCommitRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class StockCommitDaoImpl
extends SimpleJdbcDaoSupport
implements StockCommitDao {
    public StockCommitDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public List<StockCommit> getAllCommits() {
        String sql = "CALL sp_read_all_stock_commit()";
        List result = null;
        try {
            result = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StockCommitRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<StockCommit> getUncommitted() {
        String sql = "CALL sp_read_uncommitted_stock_commit()";
        List result = null;
        try {
            result = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StockCommitRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int insertStockCommit(StockCommit stockCommit) {
        String sql = "CALL sp_insert_stock_commit(?,?,?,?,?,?)";
        int result = 0;
        try {
            result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{stockCommit.getIdBarang(), stockCommit.getIdTransaksi(), stockCommit.getQueryType(), stockCommit.getStockType(), Float.valueOf(stockCommit.getJumlahBarang()), stockCommit.getTransactionType()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int insertStockCommitWithStokAwal(StockCommit stockCommit) {
        String sql = "CALL sp_insert_stock_commit_with_stok_awal(?,?,?,?,?,?,?)";
        int result = 0;
        try {
            result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{stockCommit.getIdBarang(), stockCommit.getIdTransaksi(), stockCommit.getQueryType(), stockCommit.getStockType(), Float.valueOf(stockCommit.getJumlahBarang()), stockCommit.getTransactionType(), Float.valueOf(stockCommit.getStokAwal())});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateStatusStockCommit(long stockCommitId) {
        String sql = "CALL sp_update_status_commit_stock_by_id(?)";
        int result = 0;
        try {
            result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{stockCommitId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateStatusStockCommitStr(String stockCommitIdStr) {
        String sql = "UPDATE o_stock_transaction_commit SET status_commit = 1, transaction_updated = now() WHERE stock_transaction_commit_id IN (" + stockCommitIdStr + ")";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteStockCommit(long stockCommitId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Object[]> getStockTransaction(String startDate, String endDate, String jenis, String lokasi) {
        String sql = "SELECT id_barang, nama, sum(jumlah_barang), satuan_1, query_type, stock_type FROM o_stock_transaction_commit join m_barang on (o_stock_transaction_commit.id_barang = m_barang.id) where m_jenis_jenis like ? and transaction_created >= ? and transaction_created<= ? and stock_type like ?  group by id_barang, query_type, nama, satuan_1, stock_type";
        LinkedList<Object[]> transactions = new LinkedList<Object[]>();
        jenis = '%' + jenis + '%';
        lokasi = '%' + lokasi + '%';
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{jenis, startDate, endDate, lokasi});
            for (Map row : rows) {
                Object[] returnValue = new Object[9];
                returnValue[0] = row.get("id_barang");
                returnValue[1] = row.get("nama");
                returnValue[2] = row.get("sum(jumlah_barang)");
                returnValue[3] = row.get("satuan_1");
                returnValue[4] = row.get("query_type");
                returnValue[5] = row.get("stock_type");
                transactions.add(returnValue);
            }
        }
        catch (Exception e) {
            System.out.println("Message: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public List<Object[]> getStockTransactionByBarang(String startDate, String endDate, String idBarang, String lokasi) {
        String sql = "SELECT id_barang, nama, jumlah_barang, satuan_1, stock_type, id_transaksi,transaction_type, query_type, transaction_created, stok_awal FROM o_stock_transaction_commit join m_barang on (o_stock_transaction_commit.id_barang = m_barang.id) where id_barang like? and transaction_created >= ? and transaction_created<= ? and stock_type like ?  order by transaction_created";
        LinkedList<Object[]> transactions = new LinkedList<Object[]>();
        lokasi = '%' + lokasi + '%';
        idBarang = '%' + idBarang + '%';
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{idBarang, startDate, endDate, lokasi});
            System.out.println("ROW=" + rows.size());
            for (Map row : rows) {
                Object[] returnValue = new Object[10];
                returnValue[0] = row.get("id_barang");
                System.out.println(returnValue[0]);
                returnValue[1] = row.get("nama");
                returnValue[2] = row.get("jumlah_barang");
                returnValue[3] = row.get("satuan_1");
                returnValue[4] = row.get("stock_type");
                returnValue[5] = row.get("query_type");
                returnValue[6] = row.get("id_transaksi");
                returnValue[7] = row.get("transaction_type");
                returnValue[8] = row.get("transaction_created");
                returnValue[9] = row.get("stok_awal");
                transactions.add(returnValue);
            }
        }
        catch (Exception e) {
            System.out.println("Message: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public int insertStockCommited(StockCommit stockCommit) {
        String sql = "INSERT INTO o_stock_transaction_commit(id_barang,id_transaksi,query_type,stock_type,jumlah_barang,status_commit,transaction_type,transaction_created,transaction_updated,status_delete)VALUES(?,?,?,?,?,1,?,now(),now(),false);";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{stockCommit.getIdBarang(), stockCommit.getIdTransaksi(), stockCommit.getQueryType(), stockCommit.getStockType(), Float.valueOf(stockCommit.getJumlahBarang()), stockCommit.getTransactionType()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] insertStockCommitBatch(List<StockCommit> datas) {
        String sql = "INSERT INTO o_stock_transaction_commit(id_barang,id_transaksi,query_type,stock_type,jumlah_barang,status_commit,transaction_type,transaction_created,transaction_updated,status_delete, stok_awal)VALUES (:idBarang, :idTransaksi, :queryType, :stockType, :jumlahBarang, :statusCommit, :transactionType, NOW(), NOW(), false, :stokAwal)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])datas.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }
}

