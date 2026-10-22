/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.BarangDao;
import minimart.engine.daoimpl.BarangDaoImpl;
import minimart.engine.model.Barang;
import minimart.engine.service.BarcodeService;

public class BarangService {
    private BarangDao barangDao = new BarangDaoImpl();
    private BarcodeService barcodeService = new BarcodeService();

    public int createBarang(Barang barang) {
        return this.barangDao.createBarang(barang);
    }

    public Barang getBarang(String id) {
        Barang barang = this.barangDao.getBarang(id);
        if (barang != null) {
            barang.setBarcode_1(this.barcodeService.getBarcodes(barang.getId(), barang.getSatuan1()));
            barang.setBarcode_2(this.barcodeService.getBarcodes(barang.getId(), barang.getSatuan2()));
            barang.setBarcode_3(this.barcodeService.getBarcodes(barang.getId(), barang.getSatuan3()));
        }
        return barang;
    }

    public Barang getBarangForPreview(String id) {
        return this.barangDao.getBarangForPreview(id);
    }

    public List<Barang> getBsBarangs(String jenis) {
        return this.barangDao.getBsBarangs(jenis);
    }

    public List<Object[]> geDataLabelByJenis(String jenis) {
        return this.barangDao.geDataLabelByJenis(jenis);
    }

    public List<Barang> getBarangsMin(String jenis) {
        return this.barangDao.getBarangsMinToko(jenis);
    }

    public List<Barang> getBarangs(String jenis, String nama, boolean orderByRak, String lokasi, boolean allStock) {
        return this.barangDao.getBarangs(jenis, nama, orderByRak, lokasi, allStock);
    }

    public List<Barang> getBarangsByJenisRakNama(String jenis, String rak, String nama, String lokasi, boolean allStock) {
        return this.barangDao.getBarangsByJenisRakNama(jenis, rak, nama, lokasi, allStock);
    }

    public int updateBarang(Barang barang) {
        return this.barangDao.updateBarang(barang);
    }

    public int updateBarangHpp(Barang barang) {
        return this.barangDao.updateBarangHpp(barang);
    }

    public int updateBarang2(Barang barang, String idBaru) {
        return this.barangDao.updateBarang2(barang, idBaru);
    }

    public int updateBarang2Hpp(Barang barang, String idBaru) {
        return this.barangDao.updateBarang2Hpp(barang, idBaru);
    }

    public int deleteBarang(String id) {
        return this.barangDao.deleteBarang(id);
    }

    public String getLastId() {
        return this.barangDao.getLastId();
    }

    public List<Barang> getBarangsUpdateHarga(int updateStatus) {
        return this.barangDao.getBarangsUpdateHarga(updateStatus);
    }

    public int updateStokBs(float jumlah, String idBarang) {
        return this.barangDao.updateStokBs(jumlah, idBarang);
    }

    public int updateStokBarangToko(float jumlah, String idBarang) {
        return this.barangDao.updateStokBarangToko(jumlah, idBarang);
    }

    public int updateStokBarangGudang(float jumlah, String idBarang) {
        return this.barangDao.updateStokBarangGudang(jumlah, idBarang);
    }

    public List<Barang> getBarangsByMultiRak(String raks, String lokasi) {
        return this.barangDao.getBarangsByMultiRak(raks, lokasi);
    }

    public List<Barang> getBarangs(String parent) {
        return this.barangDao.getBarangs(parent);
    }

    public List<Barang> getBarangsByRak(String rak) {
        return this.barangDao.getBarangsByRak(rak);
    }
}

