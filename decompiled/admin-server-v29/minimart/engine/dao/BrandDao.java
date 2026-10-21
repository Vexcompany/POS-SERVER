/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.Brand;

public interface BrandDao {
    public int create(Brand var1);

    public Brand getBrand(String var1);

    public int update(Brand var1);
}

