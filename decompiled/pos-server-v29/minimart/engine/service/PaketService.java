/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import javax.swing.JOptionPane;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.KonstantaDao;
import minimart.engine.dao.PaketDao;
import minimart.engine.dao.PaketOnlineDao;
import minimart.engine.daoimpl.KonstantaDaoImpl;
import minimart.engine.daoimpl.PaketDaoImpl;
import minimart.engine.daoimpl.PaketOnlineDaoImpl;
import minimart.engine.model.Paket;
import minimart.engine.model.PaketDetail;
import minimart.engine.service.PaketDetailService;

public class PaketService {
    private PaketDao dao = new PaketDaoImpl();
    private PaketOnlineDao odao = new PaketOnlineDaoImpl();
    private KonstantaDao cdao = new KonstantaDaoImpl();
    private PaketDetailService pds = new PaketDetailService();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int create(Paket paket) {
        return this.dao.create(paket);
    }

    public int update(Paket update) {
        return this.dao.update(update);
    }

    public int ubahStatus(String idPaket, int status) {
        return this.dao.ubahStatus(idPaket, status);
    }

    public Paket getPaket(String id) {
        return this.dao.getPaket(id);
    }

    public List<Object[]> getPaketByNama(String namaPaket) {
        return this.dao.getPaketByNama(namaPaket);
    }

    public int getStokBarangByDetailPaket(String barangId) {
        return this.dao.getStokBarangByDetailPaket(barangId);
    }

    public List<Paket> getOnlinePaketThisBranch() {
        this.ensureOnlineConnection();
        String tipeToko = this.cdao.getConstantValue("tipeToko");
        return this.odao.getPaketOnline(tipeToko);
    }

    public List<PaketDetail> getOnlinePaketDetail(String paketIds) {
        this.ensureOnlineConnection();
        return this.odao.getPaketDetailOnline(paketIds);
    }

    public int[] upsertPaketBatch(List<Paket> datas) {
        return this.dao.upsertPaketBatch(datas);
    }

    public int deletePaketAll() {
        return this.dao.deletePaketAll();
    }

    public List<Paket> getPaketByBarangId(String barangId) {
        return this.dao.getPaketByBarangId(barangId);
    }

    public int syncPaketOnlineToOffline() {
        int result = 1;
        try {
            System.out.println("Getting Paket Online");
            List<Paket> paketOnlines = this.getOnlinePaketThisBranch();
            System.out.println("Get Paket Online Success");
            StringBuilder idsPaketOnline = new StringBuilder();
            for (Paket paketOnline : paketOnlines) {
                if (idsPaketOnline.length() > 0) {
                    idsPaketOnline.append(", '").append(paketOnline.getId()).append("'");
                    continue;
                }
                idsPaketOnline.append("'").append(paketOnline.getId()).append("'");
            }
            System.out.println("Building IDs Success: " + idsPaketOnline.toString());
            System.out.println("Getting Paket Detail Online");
            List<PaketDetail> paketDetailOnlines = this.getOnlinePaketDetail(idsPaketOnline.toString());
            System.out.println("Get Paket Detail Online Success");
            System.out.println("Deleting Paket Offline");
            int deletePaketRes = this.deletePaketAll();
            System.out.println("Delete Paket Offline Success");
            System.out.println("Upserting Paket Offline");
            int[] upsertPaketRes = this.upsertPaketBatch(paketOnlines);
            System.out.println("Upsert Paket Offline Success");
            System.out.println("Deleting Paket Detail Offline");
            int deletePaketDetailRes = this.pds.deletePaketDetailAll();
            System.out.println("Delete Paket Detail Offline Success");
            System.out.println("Upserting Paket Detail Offline");
            System.out.println(paketDetailOnlines.get(0).getBarangId());
            int[] upsertPaketDetailRes = this.pds.upsertPaketDetailBatch(paketDetailOnlines);
            System.out.println("Upsert paket Detail Offline Success");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            result = 0;
        }
        return result;
    }

    public void doSyncPaketOnlineToOffline() {
        int result = this.syncPaketOnlineToOffline();
        if (result == 1) {
            JOptionPane.showMessageDialog(null, "Data Paket Berhasil di Sinkronisasi");
        } else {
            JOptionPane.showMessageDialog(null, "Data Paket Gagal di Sinkronisasi, harap lakukan login ulang / sinkronisasi manual");
        }
    }
}

