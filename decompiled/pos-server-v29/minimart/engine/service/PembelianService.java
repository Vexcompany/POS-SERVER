/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.PembelianDao;
import minimart.engine.daoimpl.PembelianDaoImpl;
import minimart.engine.model.Pembelian;

public class PembelianService {
    public PembelianDao pembelianDao = new PembelianDaoImpl();

    public int createPembelian(Pembelian pembelian) {
        return this.pembelianDao.createPembelian(pembelian);
    }

    public int createPembelianWithPOId(Pembelian pembelian) {
        return this.pembelianDao.createPembelianWithPOId(pembelian);
    }

    public Pembelian getPembelian(String idPembelian) {
        return this.pembelianDao.getPembelian(idPembelian);
    }

    public Pembelian getPembelianByStatus(String idPembelian) {
        return this.pembelianDao.getPembelianByStatus(idPembelian);
    }

    public int updatePembelian(Pembelian pembelian) {
        return this.pembelianDao.updatePembelian(pembelian);
    }

    public int deletePembelian(Pembelian pembelian) {
        return this.pembelianDao.deletePembelian(pembelian);
    }

    public int deletePembelianForce(Pembelian pembelian) {
        return this.pembelianDao.deletePembelianForce(pembelian);
    }

    public String getLastId() {
        return this.pembelianDao.getLastId();
    }

    public List<Object[]> getHutang(String idSupplier) {
        return this.pembelianDao.getHutang(idSupplier);
    }

    public List<Pembelian> getPembelianByDate(String startDate, String endDate, String idSupplier, String idKaryawan, String keterangan) {
        return this.pembelianDao.getPembelianByDate(startDate, endDate, idSupplier, idKaryawan, keterangan);
    }

    public List<Pembelian> getPembelian(String startDate, String endDate, String pembayaran, String keterangan) {
        return this.pembelianDao.getPembelian(startDate, endDate, pembayaran, keterangan);
    }

    public List<Object[]> getHutangSudahJatuhTempo(String idSupplier) {
        return this.pembelianDao.getHutangSudahJatuhTempo(idSupplier);
    }

    public int checkIdPoExist(int idPo) {
        return this.pembelianDao.checkIdPoExist(idPo);
    }
}

