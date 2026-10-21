/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import minimart.engine.model.Konstanta;

public interface KonstantaDao {
    public int createConstant(Konstanta var1);

    public int updateConstant(Konstanta var1);

    public int updateConstant(String var1, String var2);

    public String getConstantValue(String var1);

    public int checkColumnExist(String var1, String var2, String var3);

    public void addColumnIfNotExists(String var1, String var2, String var3, String var4, Boolean var5, String var6);
}

