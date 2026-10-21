/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.StockCommit;

public interface StockCommitDao {
    public List<StockCommit> getAllCommits();

    public List<StockCommit> getUncommitted();

    public int insertStockCommit(StockCommit var1);

    public int insertStockCommitWithStokAwal(StockCommit var1);

    public int insertStockCommited(StockCommit var1);

    public int updateStatusStockCommit(long var1);

    public int deleteStockCommit(long var1);

    public int updateStatusStockCommitStr(String var1);

    public List<Object[]> getStockTransaction(String var1, String var2, String var3, String var4);

    public List<Object[]> getStockTransactionByBarang(String var1, String var2, String var3, String var4);

    public int[] insertStockCommitBatch(List<StockCommit> var1);
}

