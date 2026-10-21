/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import minimart.engine.model.BarangO2O;
import minimart.engine.model.Barcode;

public class JsonResponseBarang {
    private String status;
    private BarangO2O[] data1;
    private Barcode[] data2;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BarangO2O[] getData1() {
        return this.data1;
    }

    public void setData1(BarangO2O[] data1) {
        this.data1 = data1;
    }

    public Barcode[] getData2() {
        return this.data2;
    }

    public void setData2(Barcode[] data2) {
        this.data2 = data2;
    }
}

