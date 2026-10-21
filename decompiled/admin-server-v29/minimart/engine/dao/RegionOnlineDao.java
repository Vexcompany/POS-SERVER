/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.CustomerAlamat;
import minimart.engine.model.Region;

public interface RegionOnlineDao {
    public List<Region> getRegion(String var1);

    public Region getRegionByIdKelurahan(String var1);

    public CustomerAlamat getCustomerAlamatPrimaryByIdCustomer(String var1);

    public int insertCustomerAlamat(CustomerAlamat var1);

    public int updateCustomerAlamat(CustomerAlamat var1);
}

