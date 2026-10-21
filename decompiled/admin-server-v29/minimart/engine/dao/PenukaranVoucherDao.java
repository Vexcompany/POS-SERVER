/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.PenukaranVoucher;

public interface PenukaranVoucherDao {
    public int createPenukaranVoucher(PenukaranVoucher var1);

    public List<PenukaranVoucher> getPenukaranVoucher(String var1, String var2);
}

