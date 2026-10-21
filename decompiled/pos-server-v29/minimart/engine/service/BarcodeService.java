/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.BarcodeDao;
import minimart.engine.daoimpl.BarcodeDaoImpl;
import minimart.engine.model.Barcode;

public class BarcodeService {
    private BarcodeDao barcodeDao = new BarcodeDaoImpl();

    public int createBarcodeNoId(Barcode barcode) {
        return this.barcodeDao.createBarcodeNoId(barcode);
    }

    public int createBarcode(Barcode barcode) {
        return this.barcodeDao.createBarcode(barcode);
    }

    public List<Barcode> getBarcodes(String m_barang_id, String satuan) {
        return this.barcodeDao.getBarcodes(m_barang_id, satuan);
    }

    public List<Barcode> getBarcodesAll() {
        return this.barcodeDao.getBarcodesAll();
    }

    public Barcode getBarcode(String barcode) {
        return this.barcodeDao.getBarcode(barcode);
    }

    public int updateBarcode(Barcode barcode) {
        return this.barcodeDao.updateBarcode(barcode);
    }

    public int deleteBarcode(Barcode barcode, String barangId) {
        return this.barcodeDao.deleteBarcode(barcode, barangId);
    }

    public boolean isBarcodeHasCreated(String barcode) {
        return this.barcodeDao.isBarcodeHasCreated(barcode);
    }
}

