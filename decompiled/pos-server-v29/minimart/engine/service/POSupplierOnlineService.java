/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import minimart.engine.controller.Helper;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.POSupplierOnlineDao;
import minimart.engine.daoimpl.POSupplierOnlineDaoImpl;
import minimart.engine.model.Pembelian;
import minimart.engine.model.PembelianDetail;
import minimart.engine.model.RBarangSupplier;
import minimart.engine.model_online.POSupplier;
import minimart.engine.model_online.POSupplierDetail;
import minimart.engine.model_online.POSupplierObject;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.RBarangSupplierService;
import minimart.engine.service.SupplierService;
import minimart.engine.service.SyncService;

public class POSupplierOnlineService {
    private KonstantaService ks = new KonstantaService();
    private POSupplierOnlineDao poSupplierOnlineDao = new POSupplierOnlineDaoImpl();
    private Helper helper = Helper.getInstance();
    private PembelianService pembelianService = new PembelianService();
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    private RBarangSupplierService rbss = new RBarangSupplierService();
    private SyncService syncService = new SyncService();
    private SupplierService supplierService = new SupplierService();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public List<POSupplierObject> getPOSupplierObjectUnsynced(int idCabang) {
        this.ensureOnlineConnection();
        ArrayList<POSupplierObject> posbs = new ArrayList<POSupplierObject>();
        List<POSupplier> pos = this.poSupplierOnlineDao.getPOSupplierUnsyncedOffline(idCabang);
        List<POSupplierDetail> pods = this.poSupplierOnlineDao.getPOSupplierDetailUnsyncedOffline(idCabang);
        for (POSupplier po : pos) {
            POSupplierObject posb = new POSupplierObject();
            posb.setPo(po);
            ArrayList<POSupplierDetail> pods1 = new ArrayList<POSupplierDetail>();
            for (POSupplierDetail pod : pods) {
                if (pod.getId_po_header() != po.getId()) continue;
                pods1.add(pod);
            }
            posb.setPods(pods1);
            posbs.add(posb);
        }
        return posbs;
    }

    public void insertPembelianFromPO() {
        if (this.ks.checkColumnExist("minimarket", "t_pembelian", "id_po_online") > 0) {
            this.supplierService.syncSupplierMaster();
            int idCabang = Integer.parseInt(this.ks.getConstantValue("idCabang"));
            List<POSupplierObject> posbs = this.getPOSupplierObjectUnsynced(idCabang);
            for (POSupplierObject posb : posbs) {
                System.out.println("ID PO HEADER -> " + posb.getPo().getId());
                try {
                    this.createPembelianFromPOObject(posb);
                }
                catch (Exception e) {
                    Logger.getLogger(POSupplierOnlineService.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            this.syncService.doSyncPembelianAndPembelianDetailUnsynced();
        } else {
            JOptionPane.showMessageDialog(null, "Kolom id_po_online belum ada di tabel t_pembelian... Harap hubungi tim IT untuk sync PO");
        }
    }

    public void createPembelianFromPOObject(POSupplierObject posb) {
        block15: {
            POSupplier po = posb.getPo();
            Pembelian pembelian = new Pembelian();
            ArrayList<PembelianDetail> pembelianDetails = new ArrayList<PembelianDetail>();
            if (this.pembelianService.checkIdPoExist(po.getId()) < 1) {
                float total = 0.0f;
                pembelian.setSupplierId(po.getId_supplier());
                pembelian.setKaryawanId(this.helper.getKaryawan().getId());
                pembelian.setFakturId("");
                pembelian.setId(this.helper.generateCodeGeneral(this.pembelianService.getLastId()));
                pembelian.setTanggal(po.getTanggal_po());
                pembelian.setPembayaran(po.getPembayaran());
                if (pembelian.getPembayaran().equals("CASH") || pembelian.getPembayaran().equals("TRANSFER")) {
                    pembelian.setPelunasan("LUNAS");
                } else if (pembelian.getPembayaran().equals("KREDIT")) {
                    pembelian.setPelunasan("BELUM LUNAS");
                }
                pembelian.setDiscount1(po.getDiscount_1());
                pembelian.setPpn(po.getPpn());
                pembelian.setKeterangan("SUDAH DIPROSES");
                pembelian.setJatuhTempo(po.getJatuh_tempo());
                pembelian.setStatus(po.getStatus_toko());
                pembelian.setCreated(po.getCreated());
                pembelian.setUpdated(po.getUpdated());
                for (POSupplierDetail posd : posb.getPods()) {
                    System.out.println("ID SKU: " + posd.getId_sku() + "; Quantity: " + posd.getQuantity() + "; Satuan: " + posd.getSatuan());
                    PembelianDetail pembelianDetail = new PembelianDetail();
                    pembelianDetail.setPembelianId(pembelian.getId());
                    pembelianDetail.setBarangId(posd.getId_sku());
                    pembelianDetail.setJumlahBarang(posd.getQuantity());
                    pembelianDetail.setSatuan(posd.getSatuan());
                    pembelianDetail.setHargaBeli(posd.getHarga_beli());
                    pembelianDetail.setDiscount2(0L);
                    pembelianDetail.setTotalHarga((long)((float)posd.getHarga_beli() * posd.getQuantity()));
                    pembelianDetail.setCreated(new Date());
                    pembelianDetail.setUpdated(new Date());
                    if (!this.rbss.isRBSHasCreated(pembelianDetail.getBarangId(), pembelian.getSupplierId())) {
                        RBarangSupplier rbs = new RBarangSupplier();
                        rbs.setBarangId(pembelianDetail.getBarangId());
                        rbs.setSupplierId(pembelian.getSupplierId());
                        this.rbss.createRBarangSupplier(rbs);
                    }
                    pembelianDetails.add(pembelianDetail);
                    total += (float)pembelianDetail.getTotalHarga();
                }
                float totalDiscount = total * (po.getDiscount_1() / 100.0f);
                float totalPpn = (total - totalDiscount) * (po.getPpn() / 100.0f);
                float netPrice = total - totalDiscount + totalPpn;
                pembelian.setTotal((long)netPrice);
                try {
                    pembelian.setId_po_online(po.getId());
                    int statusPembelian = this.pembelianService.createPembelianWithPOId(pembelian);
                    if (statusPembelian > 0) {
                        int[] statusPembelianDetail = this.pembelianDetailService.createPembelianDetailbatch(pembelianDetails);
                        int statusDetail = 1;
                        if (statusPembelianDetail != null) {
                            for (int status : statusPembelianDetail) {
                                System.out.println(status);
                                if (status >= 1) continue;
                                statusDetail = 0;
                            }
                            if (statusDetail > 0) {
                                this.markStatusSyncOffline(po.getId());
                            } else {
                                JOptionPane.showMessageDialog(null, "Ada Pembelian Detail yang gagal di insert, menghapus pembelian dengan ID " + pembelian.getId());
                                this.pembelianService.deletePembelianForce(pembelian);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Status Pembelian Detail NULL, menghapus pembelian dengan ID " + pembelian.getId());
                            this.pembelianService.deletePembelianForce(pembelian);
                        }
                        break block15;
                    }
                    JOptionPane.showMessageDialog(null, "Gagal membuat pembelian dengan ID : " + pembelian.getId());
                }
                catch (HeadlessException e) {
                    this.pembelianService.deletePembelianForce(pembelian);
                    Logger.getLogger(POSupplierOnlineService.class.getName()).log(Level.SEVERE, null, e);
                    JOptionPane.showMessageDialog(null, "Exception Called");
                }
            } else {
                this.markStatusSyncOffline(po.getId());
            }
        }
    }

    public void markStatusSyncOffline(int id) {
        this.ensureOnlineConnection();
        this.poSupplierOnlineDao.markStatusSyncOffline(id);
    }
}

