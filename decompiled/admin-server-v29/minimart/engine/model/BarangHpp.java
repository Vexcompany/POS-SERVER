/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class BarangHpp {
    private String id_sku;
    private long hpp;
    private long hppUpdated;

    public BarangHpp(String id_sku, long hpp, long hppUpdated) {
        this.id_sku = id_sku;
        this.hpp = hpp;
        this.hppUpdated = hppUpdated;
    }

    public BarangHpp() {
    }

    public String getId_sku() {
        return this.id_sku;
    }

    public void setId_sku(String id_sku) {
        this.id_sku = id_sku;
    }

    public long getHpp() {
        return this.hpp;
    }

    public void setHpp(long hpp) {
        this.hpp = hpp;
    }

    public long getHppUpdated() {
        return this.hppUpdated;
    }

    public void setHppUpdated(long hppUpdated) {
        this.hppUpdated = hppUpdated;
    }
}

