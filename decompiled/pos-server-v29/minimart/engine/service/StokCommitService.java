/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.StockCommitDao;
import minimart.engine.daoimpl.StockCommitDaoImpl;
import minimart.engine.model.StockCommit;

public class StokCommitService {
    private StockCommitDao stockCommitDao = new StockCommitDaoImpl();

    public List<StockCommit> getAllCommits() {
        return this.stockCommitDao.getAllCommits();
    }

    public List<StockCommit> getUncommitted() {
        return this.stockCommitDao.getUncommitted();
    }

    public int insertStockCommit(StockCommit stockCommit) {
        return this.stockCommitDao.insertStockCommit(stockCommit);
    }

    public int insertStockCommitWithStokAwal(StockCommit stockCommit) {
        return this.stockCommitDao.insertStockCommitWithStokAwal(stockCommit);
    }

    public int updateStatusStockCommit(long stockCommitId) {
        return this.stockCommitDao.updateStatusStockCommit(stockCommitId);
    }

    public int deleteStockCommit(long stockCommitId) {
        return this.stockCommitDao.deleteStockCommit(stockCommitId);
    }

    public int updateStatusStockCommitStr(String stockCommitIdStr) {
        return this.stockCommitDao.updateStatusStockCommitStr(stockCommitIdStr);
    }

    public List<Object[]> getStockTransaction(String startDate, String endDate, String jenis, String lokasi) {
        return this.stockCommitDao.getStockTransaction(startDate, endDate, jenis, lokasi);
    }

    public List<Object[]> getStockTransactionByBarang(String startDate, String endDate, String idBarang, String lokasi) {
        return this.stockCommitDao.getStockTransactionByBarang(startDate, endDate, idBarang, lokasi);
    }

    public int[] insertStockCommitBatch(List<StockCommit> datas) {
        return this.stockCommitDao.insertStockCommitBatch(datas);
    }
}

