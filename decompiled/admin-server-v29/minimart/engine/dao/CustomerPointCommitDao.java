/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.CustomerPointCommit;

public interface CustomerPointCommitDao {
    public List<CustomerPointCommit> getAllCommits();

    public List<CustomerPointCommit> getUnCommited();

    public List<CustomerPointCommit> getQueryTypeCommits(String var1);

    public CustomerPointCommit insertPointCommit(String var1, String var2, float var3, String var4, String var5);

    public int commitPointCommit(CustomerPointCommit var1);
}

