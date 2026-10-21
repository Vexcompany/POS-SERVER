/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Customer;
import minimart.engine.model.CustomerCommit;

public interface CustomerCommitDao {
    public List<CustomerCommit> getAllCommits();

    public List<CustomerCommit> getUncommitted();

    public List<CustomerCommit> getQueryTypeCommits(String var1);

    public CustomerCommit insertCustomerCommit(Customer var1, String var2);

    public CustomerCommit insertCustomerCommitLog(Customer var1, String var2);

    public int deleteCustomerCommit(CustomerCommit var1);

    public int commitCustomerCommit(CustomerCommit var1);

    public CustomerCommit updateCustomerCommit(CustomerCommit var1);

    public Customer getUncommittedLog(String var1);

    public Long getPointUncommited(String var1);

    public int updateStatusCommit(String var1);
}

