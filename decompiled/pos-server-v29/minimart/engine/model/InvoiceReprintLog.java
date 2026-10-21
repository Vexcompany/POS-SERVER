/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class InvoiceReprintLog {
    private int id;
    private String penjualanId;
    private String karyawanId;
    private Date created;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPenjualanId() {
        return this.penjualanId;
    }

    public void setPenjualanId(String penjualanId) {
        this.penjualanId = penjualanId;
    }

    public String getKaryawanId() {
        return this.karyawanId;
    }

    public void setKaryawanId(String karyawanId) {
        this.karyawanId = karyawanId;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

