/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Voucher;
import minimart.engine.model_online.VoucherO2O;

public interface VoucherDao {
    public int createVoucher(Voucher var1);

    public Voucher getVoucher(String var1);

    public int updateVoucher(Voucher var1);

    public String getLastId();

    public List<Voucher> getVoucher(String var1, String var2);

    public List<VoucherO2O> getAllVoucherUnsynced();

    public int updateVoucherUnsynced(String var1);

    public int[] upsertDataVoucherDariOnline(VoucherO2O[] var1);

    public String getLatestDate();
}

