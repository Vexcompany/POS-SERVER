/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class Jenis {
    private String jenis;
    private int poinSales;
    private Date created;
    private Date updated;
    private float marginCabang;
    private float marginTempo;

    public String getJenis() {
        return this.jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public int getPoinSales() {
        return this.poinSales;
    }

    public void setPoinSales(int pointSales) {
        this.poinSales = pointSales;
    }

    public float getMarginCabang() {
        return this.marginCabang;
    }

    public void setMarginCabang(float marginCabang) {
        this.marginCabang = marginCabang;
    }

    public float getMarginTempo() {
        return this.marginTempo;
    }

    public void setMarginTempo(float marginTempo) {
        this.marginTempo = marginTempo;
    }
}

