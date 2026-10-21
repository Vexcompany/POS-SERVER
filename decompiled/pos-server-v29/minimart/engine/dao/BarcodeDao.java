/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Barcode;

public interface BarcodeDao {
    public int createBarcodeNoId(Barcode var1);

    public int createBarcode(Barcode var1);

    public Barcode getBarcode(String var1);

    public List<Barcode> getBarcodes(String var1, String var2);

    public List<Barcode> getBarcodesAll();

    public int updateBarcode(Barcode var1);

    public int deleteBarcode(Barcode var1, String var2);

    public boolean isBarcodeHasCreated(String var1);
}

