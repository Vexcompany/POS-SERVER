/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package minimart.engine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.PelunasanHutangDao;
import minimart.engine.dao.PelunasanPiutangDao;
import minimart.engine.dao.PembelianDao;
import minimart.engine.dao.PembelianDetailDao;
import minimart.engine.dao.PengembalianBarangDao;
import minimart.engine.dao.PengembalianBarangDetailDao;
import minimart.engine.dao.PenjualanDao;
import minimart.engine.dao.PenjualanDetailDao;
import minimart.engine.dao.PenjualanDetailOnlineDao;
import minimart.engine.dao.PenukaranPointDao;
import minimart.engine.dao.ShiftKasirDao;
import minimart.engine.dao.StokOpnamDao;
import minimart.engine.dao.StokOpnamDetailDao;
import minimart.engine.dao.VoucherDao;
import minimart.engine.daoimpl.PelunasanHutangDaoImpl;
import minimart.engine.daoimpl.PelunasanPiutangDaoImpl;
import minimart.engine.daoimpl.PembelianDaoImpl;
import minimart.engine.daoimpl.PembelianDetailDaoImpl;
import minimart.engine.daoimpl.PengembalianBarangDaoImpl;
import minimart.engine.daoimpl.PengembalianBarangDetailDaoImpl;
import minimart.engine.daoimpl.PenjualanDaoImpl;
import minimart.engine.daoimpl.PenjualanDetailDaoImpl;
import minimart.engine.daoimpl.PenjualanDetailOnlineDaoImpl;
import minimart.engine.daoimpl.PenukaranPointDaoImpl;
import minimart.engine.daoimpl.ShiftKasirDaoImpl;
import minimart.engine.daoimpl.StokOpnamDaoImpl;
import minimart.engine.daoimpl.StokOpnamDetailDaoImpl;
import minimart.engine.daoimpl.VoucherDaoImpl;
import minimart.engine.json.PelunasanHutangJSON;
import minimart.engine.json.PelunasanPiutangJSON;
import minimart.engine.json.PembelianDetailJSON;
import minimart.engine.json.PembelianJSON;
import minimart.engine.json.PengembalianBarangDetailJSON;
import minimart.engine.json.PengembalianBarangJSON;
import minimart.engine.json.PenjualanDetailJSON;
import minimart.engine.json.PenjualanJSON;
import minimart.engine.json.PenukaranPointJSON;
import minimart.engine.json.QueryByIdJSON;
import minimart.engine.json.ShiftKasirJSON;
import minimart.engine.json.StokOpnamDetailJSON;
import minimart.engine.json.StokOpnamJSON;
import minimart.engine.json.VoucherJSON;
import minimart.engine.json_response.JsonResponseSyncPelunasanHutang;
import minimart.engine.json_response.JsonResponseSyncPelunasanPiutang;
import minimart.engine.json_response.JsonResponseSyncPembelian;
import minimart.engine.json_response.JsonResponseSyncPembelianDetail;
import minimart.engine.json_response.JsonResponseSyncPengembalianBarang;
import minimart.engine.json_response.JsonResponseSyncPengembalianBarangDetail;
import minimart.engine.json_response.JsonResponseSyncPenjualan;
import minimart.engine.json_response.JsonResponseSyncPenjualanDetail;
import minimart.engine.json_response.JsonResponseSyncPenukaranPoint;
import minimart.engine.json_response.JsonResponseSyncShiftKasir;
import minimart.engine.json_response.JsonResponseSyncStokOpnam;
import minimart.engine.json_response.JsonResponseSyncStokOpnamDetail;
import minimart.engine.json_response.JsonResponseSyncVoucher;
import minimart.engine.model_online.PelunasanHutangO2O;
import minimart.engine.model_online.PelunasanPiutangO2O;
import minimart.engine.model_online.PembelianDetailO2O;
import minimart.engine.model_online.PembelianO2O;
import minimart.engine.model_online.PengembalianBarangDetailO2O;
import minimart.engine.model_online.PengembalianBarangO2O;
import minimart.engine.model_online.PenjualanDetailO2O;
import minimart.engine.model_online.PenjualanO2O;
import minimart.engine.model_online.PenukaranPointO2O;
import minimart.engine.model_online.ShiftKasirO2O;
import minimart.engine.model_online.StokOpnamDetailO2O;
import minimart.engine.model_online.StokOpnamO2O;
import minimart.engine.model_online.VoucherO2O;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.KonstantaService;

public class SyncService
extends SwingWorker {
    PenjualanDao penjualanDao = new PenjualanDaoImpl();
    PenjualanDetailDao penjualanDetailDao = new PenjualanDetailDaoImpl();
    PembelianDao pembelianDao = new PembelianDaoImpl();
    PembelianDetailDao pembelianDetailDao = new PembelianDetailDaoImpl();
    PelunasanHutangDao pelunasanHutangDao = new PelunasanHutangDaoImpl();
    PelunasanPiutangDao pelunasanPiutangDao = new PelunasanPiutangDaoImpl();
    PenukaranPointDao penukaranPointDao = new PenukaranPointDaoImpl();
    StokOpnamDao stokOpnamDao = new StokOpnamDaoImpl();
    StokOpnamDetailDao stokOpnamDetailDao = new StokOpnamDetailDaoImpl();
    PengembalianBarangDao pengembalianBarangDao = new PengembalianBarangDaoImpl();
    PengembalianBarangDetailDao pengembalianBarangDetailDao = new PengembalianBarangDetailDaoImpl();
    VoucherDao voucherDao = new VoucherDaoImpl();
    ShiftKasirDao shiftKasirDao = new ShiftKasirDaoImpl();
    PenjualanDetailOnlineDao penjualanDetailOnlineDao = new PenjualanDetailOnlineDaoImpl();
    CommonEngine commonEngine = new CommonEngine("https://api.sinarmart.id/node_api");
    private final KonstantaService konstantaService = new KonstantaService();
    int id_cabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));

    protected Object doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public void doSyncPenjualanAndPenjualanDetailUnsynced() {
        this.doSyncPenjualanUnsynced();
        this.doSyncPenjualanDetailUnsynced();
        this.doDeletePenjualanDetailOnline();
    }

    public void doSyncPembelianAndPembelianDetailUnsynced() {
        this.doSyncPembelianUnsynced();
        this.doSyncPembelianDetailUnsynced();
    }

    public void doSyncPelunasanHutangUnsynced() {
        this.doSyncPelunasanHutang();
        this.doSyncPembelianUnsynced();
    }

    public void doSyncPelunasanPiutangUnsynced() {
        this.doSyncPelunasanPiutang();
        this.doSyncPenjualanUnsyncedForPelunasanPiutang();
    }

    public void doSyncPenukaranPoinUnsynced() {
        this.doSyncPenukaranPoin();
    }

    public void doSyncStokOpnamAndStokOpnamUnsynced() {
        this.doSyncStokOpnamUnsynced();
        this.doSyncStokOpnamDetailUnsynced();
    }

    public void doSyncPengembalianBarangUnsynced() {
        this.doSyncPengembalianBarang();
        this.doSyncPengembalianBarangDetail();
    }

    public void doSyncAllUnsynced() {
        this.doSyncShiftKasirUnsynced();
        this.doSyncPenjualanAndPenjualanDetailUnsynced();
        this.doSyncPembelianAndPembelianDetailUnsynced();
        this.doSyncPelunasanHutangUnsynced();
        this.doSyncPelunasanPiutangUnsynced();
        this.doSyncPenukaranPoinUnsynced();
        this.doSyncStokOpnamAndStokOpnamUnsynced();
        this.doSyncPengembalianBarangUnsynced();
        this.doSyncVoucherUnsynced();
    }

    public int doSyncPenjualanUnsynced() {
        int result = -1;
        try {
            List<PenjualanO2O> listPenjualan = this.penjualanDao.getAllPenjualanUnsyncedByKaryawan();
            PenjualanJSON penjualanJSON = new PenjualanJSON();
            if (!listPenjualan.isEmpty()) {
                String apiSyncPenjualanUrl = "/v1/private/penjualan/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                penjualanJSON.setListPenjualan(listPenjualan);
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync Penjualan JSON ->" + mapper.writeValueAsString((Object)penjualanJSON));
                String hasil = this.commonEngine.post(apiSyncPenjualanUrl, mapper.writeValueAsString((Object)penjualanJSON));
                System.out.println("Sync Penjualan HASIL ->" + hasil);
                JsonResponseSyncPenjualan response = (JsonResponseSyncPenjualan)mapper.readValue(hasil, JsonResponseSyncPenjualan.class);
                if (response.getStatus().equals("success") && response.getData().length > 0) {
                    StringBuilder inQuery = new StringBuilder();
                    for (int x = 0; x < response.getData().length; ++x) {
                        inQuery.append(response.getData()[x].getId_penjualan_offline());
                        if (x >= response.getData().length - 1) continue;
                        inQuery.append(",");
                    }
                    int resultUpdate = this.penjualanDao.updateStatusSyncedPenjualan(inQuery.toString());
                    result = resultUpdate > 0 ? 1 : 0;
                } else {
                    result = 0;
                }
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("SyncPenjualan: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Penjualan - JSON");
        }
        catch (IOException ex) {
            System.out.println("SyncPenjualan: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Penjualan - IO");
        }
        return result;
    }

    public int doSyncPenjualanUnsyncedForPelunasanPiutang() {
        int result = -1;
        try {
            List<PenjualanO2O> listPenjualan = this.penjualanDao.getAllPenjualanUnsynced();
            PenjualanJSON penjualanJSON = new PenjualanJSON();
            if (!listPenjualan.isEmpty()) {
                String apiSyncPenjualanUrl = "/v1/private/penjualan/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                penjualanJSON.setListPenjualan(listPenjualan);
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync Penjualan JSON ->" + mapper.writeValueAsString((Object)penjualanJSON));
                String hasil = this.commonEngine.post(apiSyncPenjualanUrl, mapper.writeValueAsString((Object)penjualanJSON));
                System.out.println("Sync Penjualan HASIL ->" + hasil);
                JsonResponseSyncPenjualan response = (JsonResponseSyncPenjualan)mapper.readValue(hasil, JsonResponseSyncPenjualan.class);
                if (response.getStatus().equals("success") && response.getData().length > 0) {
                    StringBuilder inQuery = new StringBuilder();
                    for (int x = 0; x < response.getData().length; ++x) {
                        inQuery.append(response.getData()[x].getId_penjualan_offline());
                        if (x >= response.getData().length - 1) continue;
                        inQuery.append(",");
                    }
                    int resultUpdate = this.penjualanDao.updateStatusSyncedPenjualan(inQuery.toString());
                    result = resultUpdate > 0 ? 1 : 0;
                } else {
                    result = 0;
                }
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("SyncPenjualanForPiutang: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Penjualan for Piutang - JSON");
        }
        catch (IOException ex) {
            System.out.println("SyncPenjualanForPiutang: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Penjualan for Piutang - IO");
        }
        return result;
    }

    public int doSyncPenjualanDetailUnsynced() {
        int result = -1;
        try {
            List<PenjualanDetailO2O> listPenjualanDetail = this.penjualanDetailDao.getAllPenjualanDetailUnsynced();
            PenjualanDetailJSON penjualanDetailJSON = new PenjualanDetailJSON();
            if (!listPenjualanDetail.isEmpty()) {
                int[] queryLocal;
                String apiSyncPenjualanDetailUrl = "/v1/private/penjualan_detail/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                penjualanDetailJSON.setListPenjualanDetail(listPenjualanDetail);
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync PenjualanDetail JSON ->" + mapper.writeValueAsString((Object)penjualanDetailJSON));
                String hasil = this.commonEngine.post(apiSyncPenjualanDetailUrl, mapper.writeValueAsString((Object)penjualanDetailJSON));
                System.out.println("Sync PenjualanDetail HASIL ->" + hasil);
                JsonResponseSyncPenjualanDetail response = (JsonResponseSyncPenjualanDetail)mapper.readValue(hasil, JsonResponseSyncPenjualanDetail.class);
                result = response.getStatus().equals("success") && response.getData().length > 0 ? ((queryLocal = this.penjualanDetailDao.updateStatusSyncedPenjualanDetail(response.getData())) != null ? 1 : 0) : 0;
            } else {
                result = 0;
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("SyncPenjualanDetail: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Penjualan Detail - JSON");
        }
        catch (IOException ex) {
            System.out.println("SyncPenjualanDetail: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Penjualan Detail - IO");
        }
        return result;
    }

    public int doSyncPembelianUnsynced() {
        int result = -1;
        try {
            List<PembelianO2O> listPembelianO2O = this.pembelianDao.getAllPembelianUnsynced();
            PembelianJSON pembelianJSON = new PembelianJSON();
            pembelianJSON.setListPembelian(listPembelianO2O);
            if (!listPembelianO2O.isEmpty()) {
                String apiSyncPembelianUrl = "/v1/private/pembelian/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync Pembelian JSON ->" + mapper.writeValueAsString((Object)pembelianJSON));
                String hasil = this.commonEngine.post(apiSyncPembelianUrl, mapper.writeValueAsString((Object)pembelianJSON));
                System.out.println("Sync Pembelian Hasil ->" + hasil);
                JsonResponseSyncPembelian response = (JsonResponseSyncPembelian)mapper.readValue(hasil, JsonResponseSyncPembelian.class);
                if (response.getStatus().equals("success") && response.getData().length > 0) {
                    StringBuilder inQuery = new StringBuilder();
                    for (int x = 0; x < response.getData().length; ++x) {
                        inQuery.append(response.getData()[x].getId_pembelian_offline());
                        if (x >= response.getData().length - 1) continue;
                        inQuery.append(",");
                    }
                    int updateSynced = this.pembelianDao.updateStatusSyncedPembelian(inQuery.toString());
                    result = updateSynced > 0 ? updateSynced : 0;
                } else {
                    result = 0;
                }
            } else {
                result = 0;
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("SyncPembelian: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pembelian - JSON");
        }
        catch (IOException ex) {
            System.out.println("SyncPembelian: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pembelian - IO");
        }
        return result;
    }

    public int doSyncPembelianDetailUnsynced() {
        int result = -1;
        try {
            List<PembelianDetailO2O> listPembelianDetailO2O = this.pembelianDetailDao.getAllPembelianDetailUnsynced();
            PembelianDetailJSON pembelianDetailJSON = new PembelianDetailJSON();
            pembelianDetailJSON.setListPembelianDetail(listPembelianDetailO2O);
            if (!listPembelianDetailO2O.isEmpty()) {
                String apiSyncPembelianDetail = "/v1/private/pembelian_detail/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync PembelianDetail JSON ->" + mapper.writeValueAsString((Object)pembelianDetailJSON));
                String hasil = this.commonEngine.post(apiSyncPembelianDetail, mapper.writeValueAsString((Object)pembelianDetailJSON));
                System.out.println("Sync Pembelian Hasil ->" + hasil);
                JsonResponseSyncPembelianDetail response = (JsonResponseSyncPembelianDetail)mapper.readValue(hasil, JsonResponseSyncPembelianDetail.class);
                if (response.getStatus().equals("success") && response.getData().length > 0) {
                    StringBuilder inQuery = new StringBuilder();
                    for (int x = 0; x < response.getData().length; ++x) {
                        inQuery.append(response.getData()[x].getId_pembelian_detail_offline());
                        if (x >= response.getData().length - 1) continue;
                        inQuery.append(",");
                    }
                    int updateSynced = this.pembelianDetailDao.updateStatusSyncedPembelianDetail(inQuery.toString());
                    result = updateSynced > 0 ? updateSynced : 0;
                } else {
                    result = 0;
                }
            } else {
                result = 0;
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("SyncPembelianDetail: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pembelian Detail - JSON");
        }
        catch (IOException ex) {
            System.out.println("SyncPembelianDetail: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pembelian Detail - IO");
        }
        return result;
    }

    public int doSyncPelunasanPiutang() {
        int result = -1;
        try {
            List<PelunasanPiutangO2O> listPelunasanPiutang = this.pelunasanPiutangDao.getAllPelunasanPiutangUnsynced();
            PelunasanPiutangJSON pelunasanPiutangJSON = new PelunasanPiutangJSON();
            if (!listPelunasanPiutang.isEmpty()) {
                int resultUpdate;
                String apiSyncPelunasanPiutang = "/v1/private/pelunasan_piutang/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                pelunasanPiutangJSON.setListPelunasanPiutang(listPelunasanPiutang);
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync Pelunasanpiutang JSON ->" + mapper.writeValueAsString((Object)pelunasanPiutangJSON));
                String hasil = this.commonEngine.post(apiSyncPelunasanPiutang, mapper.writeValueAsString((Object)pelunasanPiutangJSON));
                System.out.println("Sync Pelunasanpiutang HASIL ->" + hasil);
                JsonResponseSyncPelunasanPiutang response = (JsonResponseSyncPelunasanPiutang)mapper.readValue(hasil, JsonResponseSyncPelunasanPiutang.class);
                result = response.getStatus().equals("success") && response.getData().length > 0 ? ((resultUpdate = this.pelunasanPiutangDao.updateStatusSynced(response.getData())) > 0 ? 1 : 0) : 0;
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("SyncPelunasanPiutang: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pelunasan Piutang - JSON");
        }
        catch (IOException ex) {
            System.out.println("SyncPelunasanPiutang: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pelunasan Piutang - IO");
        }
        return result;
    }

    public int doSyncPelunasanHutang() {
        int result = -1;
        try {
            List<PelunasanHutangO2O> listPelunasanHutang = this.pelunasanHutangDao.getAllPelunasanHutangUnsynced();
            PelunasanHutangJSON pelunasanHutangJSON = new PelunasanHutangJSON();
            if (!listPelunasanHutang.isEmpty()) {
                int resultUpdate;
                String apiSyncPelunasanHutang = "/v1/private/pelunasan_hutang/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                pelunasanHutangJSON.setListPelunasanHutang(listPelunasanHutang);
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync Pelunasanpiutang JSON ->" + mapper.writeValueAsString((Object)pelunasanHutangJSON));
                String hasil = this.commonEngine.post(apiSyncPelunasanHutang, mapper.writeValueAsString((Object)pelunasanHutangJSON));
                System.out.println("Sync Pelunasanpiutang HASIL ->" + hasil);
                JsonResponseSyncPelunasanHutang response = (JsonResponseSyncPelunasanHutang)mapper.readValue(hasil, JsonResponseSyncPelunasanHutang.class);
                result = response.getStatus().equals("success") && response.getData().length > 0 ? ((resultUpdate = this.pelunasanHutangDao.updateStatusSynced(response.getData())) > 0 ? 1 : 0) : 0;
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("SyncPenjualanHutang: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pelunasan Hutang - JSON");
        }
        catch (IOException ex) {
            System.out.println("SyncPenjualanHutang: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pelunasan Hutang - IO");
        }
        return result;
    }

    public int doSyncPenukaranPoin() {
        int result = -1;
        try {
            List<PenukaranPointO2O> listPenukaranPoint = this.penukaranPointDao.getAllPenukaranPointUnsynced();
            PenukaranPointJSON penukaranPointJSON = new PenukaranPointJSON();
            if (!listPenukaranPoint.isEmpty()) {
                int resultUpdate;
                String apiSyncPenukaranPoint = "/v1/private/penukaran_point/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                penukaranPointJSON.setListPenukaranPoint(listPenukaranPoint);
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync Penukaran point JSON ->" + mapper.writeValueAsString((Object)penukaranPointJSON));
                String hasil = this.commonEngine.post(apiSyncPenukaranPoint, mapper.writeValueAsString((Object)penukaranPointJSON));
                System.out.println("Sync penukaran point HASIL ->" + hasil);
                JsonResponseSyncPenukaranPoint response = (JsonResponseSyncPenukaranPoint)mapper.readValue(hasil, JsonResponseSyncPenukaranPoint.class);
                result = response.getStatus().equals("success") && response.getData().length > 0 ? ((resultUpdate = this.penukaranPointDao.updatePenukaranPoint(response.getData())) > 0 ? 1 : 0) : 0;
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("SyncPenukaranPoint: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Penukaran Point - JSON");
        }
        catch (IOException ex) {
            System.out.println("SyncPenukaranPoint: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Penukaran Point - IO");
        }
        return result;
    }

    public int doSyncStokOpnamUnsynced() {
        int result = -1;
        try {
            List<StokOpnamO2O> listStokOpnam = this.stokOpnamDao.getAllStokOpnamUnsynced();
            StokOpnamJSON stokOpnamJSON = new StokOpnamJSON();
            if (!listStokOpnam.isEmpty()) {
                int resultUpdate;
                String apiSyncStokOpnam = "/v1/private/stok_opnam/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                stokOpnamJSON.setListStokOpnam(listStokOpnam);
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync stokopnam JSON ->" + mapper.writeValueAsString((Object)stokOpnamJSON));
                String hasil = this.commonEngine.post(apiSyncStokOpnam, mapper.writeValueAsString((Object)stokOpnamJSON));
                System.out.println("Sync stokopnam HASIL ->" + hasil);
                JsonResponseSyncStokOpnam response = (JsonResponseSyncStokOpnam)mapper.readValue(hasil, JsonResponseSyncStokOpnam.class);
                result = response.getStatus().equals("success") && response.getData().length > 0 ? ((resultUpdate = this.stokOpnamDao.updateStokOpnamUnsynced(response.getData())) > 0 ? 1 : 0) : 0;
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("Sync StokOpnam: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Stok Opnam - JSON");
        }
        catch (IOException ex) {
            System.out.println("Sync StokOpnam: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Stok Opnam - IO");
        }
        return result;
    }

    public int doSyncStokOpnamDetailUnsynced() {
        int result = -1;
        try {
            List<StokOpnamDetailO2O> listStokOpnam = this.stokOpnamDetailDao.getStokOpnamDetailUnsynced();
            StokOpnamDetailJSON stokOpnamDetailJSON = new StokOpnamDetailJSON();
            if (!listStokOpnam.isEmpty()) {
                String apiSyncStokOpnam = "/v1/private/stok_opnam_detail/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                stokOpnamDetailJSON.setListStokOpnamDetail(listStokOpnam);
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync stokopnamdetail JSON ->" + mapper.writeValueAsString((Object)stokOpnamDetailJSON));
                String hasil = this.commonEngine.post(apiSyncStokOpnam, mapper.writeValueAsString((Object)stokOpnamDetailJSON));
                System.out.println("Sync stokopnamdetail HASIL ->" + hasil);
                JsonResponseSyncStokOpnamDetail response = (JsonResponseSyncStokOpnamDetail)mapper.readValue(hasil, JsonResponseSyncStokOpnamDetail.class);
                if (response.getStatus().equals("success") && response.getData().length > 0) {
                    StringBuilder inQuery = new StringBuilder();
                    for (int x = 0; x < response.getData().length; ++x) {
                        inQuery.append(response.getData()[x].getId_stok_opnam_detail_offline());
                        if (x >= response.getData().length - 1) continue;
                        inQuery.append(",");
                    }
                    int resultUpdate = this.stokOpnamDetailDao.updateStokOpnamDetailUnsynced(inQuery.toString());
                    result = resultUpdate > 0 ? 1 : 0;
                } else {
                    result = 0;
                }
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("Sync StokOpnam: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Stok Opnam Detail - JSON");
        }
        catch (IOException ex) {
            System.out.println("Sync StokOpnam: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Stok Opnam Detail - IO");
        }
        return result;
    }

    public int doSyncPengembalianBarang() {
        int result = -1;
        try {
            List<PengembalianBarangO2O> listPengembalianBarang = this.pengembalianBarangDao.getAllPengembalianBarangUnsynced();
            PengembalianBarangJSON pengembalianBarangJSON = new PengembalianBarangJSON();
            if (!listPengembalianBarang.isEmpty()) {
                String apiSyncPengembalianBarang = "/v1/private/pengembalian_barang/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                pengembalianBarangJSON.setListPengembalianBarang(listPengembalianBarang);
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync pengembalian JSON ->" + mapper.writeValueAsString((Object)pengembalianBarangJSON));
                String hasil = this.commonEngine.post(apiSyncPengembalianBarang, mapper.writeValueAsString((Object)pengembalianBarangJSON));
                System.out.println("Sync pengembalian HASIL ->" + hasil);
                JsonResponseSyncPengembalianBarang response = (JsonResponseSyncPengembalianBarang)mapper.readValue(hasil, JsonResponseSyncPengembalianBarang.class);
                if (response.getStatus().equals("success") && response.getData().length > 0) {
                    StringBuilder inQuery = new StringBuilder();
                    for (int x = 0; x < response.getData().length; ++x) {
                        inQuery.append(response.getData()[x].getId_pengembalian_barang_offline());
                        if (x >= response.getData().length - 1) continue;
                        inQuery.append(",");
                    }
                    int resultUpdate = this.pengembalianBarangDao.updateStatusSyncedPengembalianBarang(inQuery.toString());
                    result = resultUpdate > 0 ? 1 : 0;
                } else {
                    result = 0;
                }
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("Sync pengembalian: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pengembalian Barang - JSON");
        }
        catch (IOException ex) {
            System.out.println("Sync pengembalian: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pengembalian Barang - IO");
        }
        return result;
    }

    public int doSyncPengembalianBarangDetail() {
        int result = -1;
        try {
            List<PengembalianBarangDetailO2O> listPengembalianBarangDetail = this.pengembalianBarangDetailDao.getAllPengembalianBarangDetailUnsynced();
            PengembalianBarangDetailJSON pengembalianBarangDetailJSON = new PengembalianBarangDetailJSON();
            if (!listPengembalianBarangDetail.isEmpty()) {
                String apiSyncPengembalianBarangDetail = "/v1/private/pengembalian_barang_detail/insert_bulk";
                ObjectMapper mapper = new ObjectMapper();
                pengembalianBarangDetailJSON.setListPengembalianBarangDetail(listPengembalianBarangDetail);
                this.commonEngine.setPrivateToken("sinarmartmjk01");
                System.out.println("Sync pengembalian detail JSON ->" + mapper.writeValueAsString((Object)pengembalianBarangDetailJSON));
                String hasil = this.commonEngine.post(apiSyncPengembalianBarangDetail, mapper.writeValueAsString((Object)pengembalianBarangDetailJSON));
                System.out.println("Sync pengembalian detail HASIL ->" + hasil);
                JsonResponseSyncPengembalianBarangDetail response = (JsonResponseSyncPengembalianBarangDetail)mapper.readValue(hasil, JsonResponseSyncPengembalianBarangDetail.class);
                if (response.getStatus().equals("success") && response.getData().length > 0) {
                    StringBuilder inQuery = new StringBuilder();
                    for (int x = 0; x < response.getData().length; ++x) {
                        inQuery.append(response.getData()[x].getId_pengembalian_barang_detail_offline());
                        if (x >= response.getData().length - 1) continue;
                        inQuery.append(",");
                    }
                    int resultUpdate = this.pengembalianBarangDetailDao.updateStatusSyncedPengembalianBarangDetail(inQuery.toString());
                    result = resultUpdate > 0 ? 1 : 0;
                } else {
                    result = 0;
                }
            }
        }
        catch (JsonProcessingException ex) {
            System.out.println("Sync pengembalian detail: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pengembalian Barang Detail - JSON");
        }
        catch (IOException ex) {
            System.out.println("Sync pengembalian detail: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi Pengembalian Barang Detail - IO");
        }
        return result;
    }

    public String getDateStart(int enums) {
        String result = null;
        switch (enums) {
            case 3: {
                result = this.pelunasanHutangDao.getLatestDate();
                break;
            }
            case 4: {
                result = this.pelunasanPiutangDao.getLatestDate();
                break;
            }
            case 1: {
                result = this.pembelianDao.getLatestDate();
                break;
            }
            case 5: {
                result = this.pengembalianBarangDao.getLatestDate();
                break;
            }
            case 0: {
                result = this.penjualanDao.getLatestDate();
                break;
            }
            case 2: {
                result = this.voucherDao.getLatestDate();
                break;
            }
            case 6: {
                result = this.penukaranPointDao.getLatestDate();
                break;
            }
            case 7: {
                result = this.stokOpnamDao.getLatestDate();
                break;
            }
            case 8: {
                result = this.shiftKasirDao.getLatestDate();
            }
        }
        if (result != null && result.equals("0")) {
            result = null;
        }
        return result;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int doUpsertDataPenjualanDariOnline() {
        int result = -1;
        String date_start = this.getDateStart(0);
        try {
            if (date_start == null) {
                throw new IOException("[Error Upsert_Penjualan] Date start is NULL. Please fill them first in database");
            }
            String apiGetDataPenjualanDariServer = "/v1/private/penjualan/read_range_created_updated?start=" + date_start + "&idCabang=" + this.id_cabang;
            this.commonEngine.setPrivateToken("sinarmartmjk01");
            ObjectMapper mapperPenjualan = new ObjectMapper();
            String hasil = this.commonEngine.get(apiGetDataPenjualanDariServer);
            JsonResponseSyncPenjualan responsePenjualan = (JsonResponseSyncPenjualan)mapperPenjualan.readValue(hasil, JsonResponseSyncPenjualan.class);
            if (!responsePenjualan.getStatus().equals("success")) throw new IOException("[ERROR Upsert_Penjualan]Failed to get penjualan from API penjualan");
            if (responsePenjualan.getData().length <= 0) throw new IOException("[ERROR Upsert_Penjualan]Failed to get penjualan from API penjualan");
            String apiGetDataPenjualanDetailDariServer = "/v1/private/penjualan/read_detail_by_penjualan_ids";
            ObjectMapper mapperPenjualanDetail = new ObjectMapper();
            ArrayList<String> listIds = new ArrayList<String>();
            for (int x = 0; x < responsePenjualan.getData().length; ++x) {
                listIds.add(responsePenjualan.getData()[x].getId_penjualan_offline());
            }
            QueryByIdJSON queryByIdJson = new QueryByIdJSON();
            queryByIdJson.setListIds(listIds);
            String hasilPenjualanDetail = this.commonEngine.post(apiGetDataPenjualanDetailDariServer, mapperPenjualanDetail.writeValueAsString((Object)queryByIdJson));
            JsonResponseSyncPenjualanDetail responsePenjualanDetail = (JsonResponseSyncPenjualanDetail)mapperPenjualanDetail.readValue(hasilPenjualanDetail, JsonResponseSyncPenjualanDetail.class);
            if (!responsePenjualanDetail.getStatus().equals("success")) throw new IOException("[ERROR Upsert_Penjualan]Failed to get penjualan_detail from API penjualan_detail");
            int[] resultUpsertPenjualan = this.penjualanDao.upsertDataPenjualanDariOnline(responsePenjualan.getData());
            int[] resultUpsertPenjualanDetail = this.penjualanDetailDao.upsertDataPenjualanDetailDariOnline(responsePenjualanDetail.getData());
            if (resultUpsertPenjualan == null) {
                throw new IOException("[ERROR Upsert_Penjualan]Failed to run upsert query");
            }
            if (resultUpsertPenjualanDetail == null) {
                throw new IOException("[ERROR Upsert_Penjualan]Failed to run upsert penjualan detail query");
            }
            System.out.println("Upsert Penjualan -> OK");
            return 1;
        }
        catch (JsonProcessingException ex) {
            System.out.println("[ERROR Upsert_Penjualan]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_Penjualan] EXCEPTION Json Processing");
            return result;
        }
        catch (IOException ex) {
            System.out.println("[ERROR Upsert_Penjualan]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_Penjualan] EXCEPTION IO");
        }
        return result;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int doUpsertDataPembelianDariOnline() {
        int result = -1;
        try {
            String date_start = this.getDateStart(1);
            if (date_start == null) {
                throw new IOException("[ERROR Upsert_Pembelian] Date Start is NULL. Please fill them first in database");
            }
            String apiGetDataPembelian = "/v1/private/pembelian/read_pembelian_range_created_updated?start=" + date_start + "&idCabang=" + this.id_cabang;
            this.commonEngine.setPrivateToken("sinarmartmjk01");
            ObjectMapper mapperPembelian = new ObjectMapper();
            String hasilPembelian = this.commonEngine.get(apiGetDataPembelian);
            JsonResponseSyncPembelian responsePembelian = (JsonResponseSyncPembelian)mapperPembelian.readValue(hasilPembelian, JsonResponseSyncPembelian.class);
            if (!responsePembelian.getStatus().equals("success")) throw new IOException("[ERROR Upsert_Pembelian] API call on pembelian failed (not success)");
            String apiGetDataPembelianDetail = "/v1/private/pembelian/read_pembelian_detail_by_pembelian_ids";
            ObjectMapper mapperPembelianDetail = new ObjectMapper();
            QueryByIdJSON queryByIdJson = new QueryByIdJSON();
            ArrayList<String> listIds = new ArrayList<String>();
            for (int x = 0; x < responsePembelian.getData().length; ++x) {
                listIds.add(responsePembelian.getData()[x].getId_pembelian_offline());
            }
            queryByIdJson.setListIds(listIds);
            String hasilPembelianDetail = this.commonEngine.post(apiGetDataPembelianDetail, mapperPembelianDetail.writeValueAsString((Object)queryByIdJson));
            JsonResponseSyncPembelianDetail responsePembelianDetail = (JsonResponseSyncPembelianDetail)mapperPembelianDetail.readValue(hasilPembelianDetail, JsonResponseSyncPembelianDetail.class);
            if (!responsePembelianDetail.getStatus().equals("success")) throw new IOException("[ERROR Upsert_Pembelian] API call on pembelian_detail failed (not success)");
            int[] resultUpsertPembelian = this.pembelianDao.upsertDataPembelianDariOnline(responsePembelian.getData());
            int[] resultUpsertPembelianDetail = this.pembelianDetailDao.upsertDataPembelianDetailDariOnline(responsePembelianDetail.getData());
            if (resultUpsertPembelian == null) {
                throw new IOException("[ERROR Upsert_Pembelian]Failed to run upsert query");
            }
            if (resultUpsertPembelianDetail == null) {
                throw new IOException("[ERROR Upsert_Pembelian_Detail]Failed to run upsert query");
            }
            System.out.println("Upsert Pembelian -> OK");
            return 1;
        }
        catch (JsonProcessingException ex) {
            System.out.println("[ERROR Upsert_Pembelian]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_Pembelian] EXCEPTION Json Processing");
            return result;
        }
        catch (IOException ex) {
            System.out.println("[ERROR Upsert_Pembelian]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_Pembelian] Exception - IO");
        }
        return result;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int doUpsertDataPengembalianBarangDariOnline() {
        int result = -1;
        try {
            String date_start = this.getDateStart(5);
            if (date_start == null) {
                throw new IOException("[ERROR Upsert_Pengembalian] Date Start is NULL. Please fill them first in database");
            }
            String apiGetDataPengembalianBarang = "/v1/private/pengembalian_barang/read_range_created_updated?start=" + date_start + "&idCabang=" + this.id_cabang;
            this.commonEngine.setPrivateToken("sinarmartmjk01");
            ObjectMapper mapperPengembalian = new ObjectMapper();
            String hasilPengembalian = this.commonEngine.get(apiGetDataPengembalianBarang);
            JsonResponseSyncPengembalianBarang responsePengembalian = (JsonResponseSyncPengembalianBarang)mapperPengembalian.readValue(hasilPengembalian, JsonResponseSyncPengembalianBarang.class);
            if (!responsePengembalian.getStatus().equals("success")) throw new IOException("[ERROR Upsert_Pengembalian] failed to upsert pengembalian query");
            String apiGetDataPengembalianBarangDetail = "/v1/private/pengembalian_barang_detail/read_by_pengembalian_barang_id";
            ObjectMapper mapperPengembalianBarangDetail = new ObjectMapper();
            QueryByIdJSON queryByIdJson = new QueryByIdJSON();
            ArrayList<String> listIds = new ArrayList<String>();
            for (int x = 0; x < responsePengembalian.getData().length; ++x) {
                listIds.add(Long.toString(responsePengembalian.getData()[x].getId_pengembalian_barang_offline()));
            }
            queryByIdJson.setListIds(listIds);
            String hasilPengembalianDetail = this.commonEngine.post(apiGetDataPengembalianBarangDetail, mapperPengembalianBarangDetail.writeValueAsString((Object)queryByIdJson));
            JsonResponseSyncPengembalianBarangDetail responsePengembalianDetail = (JsonResponseSyncPengembalianBarangDetail)mapperPengembalianBarangDetail.readValue(hasilPengembalianDetail, JsonResponseSyncPengembalianBarangDetail.class);
            if (!responsePengembalianDetail.getStatus().equals("success")) throw new IOException("[ERROR Upsert_Pengembalian] failed to upsert pengembalian_detail query");
            int[] resultPengembalian = this.pengembalianBarangDao.upsertDataPengembalianBarangDariOnline(responsePengembalian.getData());
            int[] resultPengembalianDetail = this.pengembalianBarangDetailDao.upsertDataPengembalianBarangDetailDariOnline(responsePengembalianDetail.getData());
            if (resultPengembalian == null) {
                throw new IOException("[ERROR Upsert_Pengembalian] failed call API pengembalian");
            }
            if (resultPengembalianDetail == null) {
                throw new IOException("[ERROR Upsert_Pengembalian] failed call API pengembalian_detail");
            }
            System.out.println("Upsert Pengembalian Barang -> OK");
            return 1;
        }
        catch (JsonProcessingException ex) {
            System.out.println("[ERROR Upsert_Pengembalian]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_Pengembalian] EXCEPTION Json Processing");
            return result;
        }
        catch (IOException ex) {
            System.out.println("[ERROR Upsert_Pengembalian]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_Pengembalian] EXCEPTION IO");
        }
        return result;
    }

    public int doUpsertDataPelunasanHutangDariOnline() {
        int result = -1;
        try {
            String date_start = this.getDateStart(3);
            if (date_start == null) {
                throw new IOException("[ERROR Upsert_PelHutang] Date Start is NULL. Please fill them first in database");
            }
            String apiGetPelunasanHutang = "/v1/private/pelunasan_hutang/read_range_created_updated?start=" + date_start + "&idCabang=" + this.id_cabang;
            this.commonEngine.setPrivateToken("sinarmartmjk01");
            ObjectMapper mapperPelHutang = new ObjectMapper();
            String hasilPelHutang = this.commonEngine.get(apiGetPelunasanHutang);
            JsonResponseSyncPelunasanHutang responsePelHutang = (JsonResponseSyncPelunasanHutang)mapperPelHutang.readValue(hasilPelHutang, JsonResponseSyncPelunasanHutang.class);
            if (responsePelHutang.getStatus().equals("success")) {
                int[] resultPelHutang = this.pelunasanHutangDao.upsertDataPelunasanHutangDariOnline(responsePelHutang.getData());
                if (resultPelHutang == null) {
                    throw new IOException("[ERROR]Upsert_PelHutang : failed to upsert data");
                }
            } else {
                throw new IOException("[ERROR] Upsert_PelHutang : failed to get API data");
            }
            System.out.println("Upsert Pelunasan Hutang -> OK");
            result = 1;
        }
        catch (JsonProcessingException ex) {
            System.out.println("[ERROR Upsert_PelHutang]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_PelHutang] EXCEPTION Json Processing");
        }
        catch (IOException ex) {
            System.out.println("[ERROR Upsert_PelHutang]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_PelHutang] EXCEPTION IO");
        }
        return result;
    }

    public int doUpsertDataPelunasanPiutangDariOnline() {
        int result = -1;
        try {
            String date_start = this.getDateStart(4);
            if (date_start == null) {
                throw new IOException("[ERROR Upsert_PelPiutang] Date Start is NULL. Please fill them first in database");
            }
            String apiGetPelunasanPiutang = "/v1/private/pelunasan_piutang/read_range_created_updated?start=" + date_start + "&idCabang=" + this.id_cabang;
            this.commonEngine.setPrivateToken("sinarmartmjk01");
            ObjectMapper mapperPelPiutang = new ObjectMapper();
            String hasilPelPiutang = this.commonEngine.get(apiGetPelunasanPiutang);
            JsonResponseSyncPelunasanPiutang responsePelPiutang = (JsonResponseSyncPelunasanPiutang)mapperPelPiutang.readValue(hasilPelPiutang, JsonResponseSyncPelunasanPiutang.class);
            if (responsePelPiutang.getStatus().equals("success")) {
                int[] resultPelPiutang = this.pelunasanPiutangDao.upsertDataPelunasanPiutangDariOnline(responsePelPiutang.getData());
                if (resultPelPiutang == null) {
                    throw new IOException("[ERROR]Upsert_PelPiutang : failed to upsert data");
                }
            } else {
                throw new IOException("[ERROR]Upsert_PelPiutang : failed to get API data");
            }
            System.out.println("Upsert Pelunasan Piutang -> OK");
            result = 1;
        }
        catch (JsonProcessingException ex) {
            System.out.println("[ERROR Upsert_PelPiutang]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_PelPiutang] EXCEPTION Json Processing");
        }
        catch (IOException ex) {
            System.out.println("[ERROR Upsert_PelPiutang]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_PelPiutang] EXCEPTION IO");
        }
        return result;
    }

    public int doUpsertDataPenukaranPointDariOnline() {
        int result = -1;
        try {
            String date_start = this.getDateStart(6);
            if (date_start == null) {
                throw new IOException("[ERROR Upsert_PenukaranPoint] Date Start is NULL. Please fill them first in database");
            }
            String apiGetDataPenukaranPoint = "/v1/private/penukaran_point/read_range_created_updated?start=" + date_start + "&idCabang=" + this.id_cabang;
            this.commonEngine.setPrivateToken("sinarmartmjk01");
            ObjectMapper mapperPenukaranPoint = new ObjectMapper();
            String hasilPenukaranPoint = this.commonEngine.get(apiGetDataPenukaranPoint);
            JsonResponseSyncPenukaranPoint responsePenukaranPoint = (JsonResponseSyncPenukaranPoint)mapperPenukaranPoint.readValue(hasilPenukaranPoint, JsonResponseSyncPenukaranPoint.class);
            if (responsePenukaranPoint.getStatus().equals("success")) {
                int[] resultPenukaranPoint = this.penukaranPointDao.upsertDataPenukaranPointDariOnline(responsePenukaranPoint.getData());
                if (resultPenukaranPoint == null) {
                    throw new IOException("[ERROR]Upsert_PenukaranPoint: failed to upsert data");
                }
            } else {
                throw new IOException("[ERROR]Upsert_PenukaranPoint : failed to get API data");
            }
            System.out.println("Upsert Penukaran Point -> OK");
            result = 1;
        }
        catch (JsonProcessingException ex) {
            System.out.println("[ERROR Upsert_PenukaranPoint]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_PenukaranPoint] EXCEPTION Json Processing");
        }
        catch (IOException ex) {
            System.out.println("[ERROR Upsert_PenukaranPoint]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_PenukaranPoint] EXCEPTION IO");
        }
        return result;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int doUpsertDataStokOpnamDariOnline() {
        int result = -1;
        try {
            String date_start = this.getDateStart(7);
            if (date_start == null) {
                throw new IOException("[ERROR Upsert_StokOpnam] Date Start is NULL. Please fill them first in database");
            }
            String apiGetDataStokOpnam = "/v1/private/stok_opnam/read_range_created_updated?start=" + date_start + "&idCabang=" + this.id_cabang;
            this.commonEngine.setPrivateToken("sinarmartmjk01");
            ObjectMapper mapperStokOpnam = new ObjectMapper();
            String hasilPengembalian = this.commonEngine.get(apiGetDataStokOpnam);
            JsonResponseSyncStokOpnam responseStokOpnam = (JsonResponseSyncStokOpnam)mapperStokOpnam.readValue(hasilPengembalian, JsonResponseSyncStokOpnam.class);
            if (!responseStokOpnam.getStatus().equals("success")) throw new IOException("[ERROR Upsert_Stok_Opnam] failed to upsert pengembalian query");
            String apiGetDataStokOpnamDetail = "/v1/private/stok_opnam_detail/read_by_stok_opnam_ids";
            ObjectMapper mapperStokOpnamDetail = new ObjectMapper();
            QueryByIdJSON queryByIdJson = new QueryByIdJSON();
            ArrayList<String> listIds = new ArrayList<String>();
            for (int x = 0; x < responseStokOpnam.getData().length; ++x) {
                listIds.add(Long.toString(responseStokOpnam.getData()[x].getId_stok_opnam_offline()));
            }
            queryByIdJson.setListIds(listIds);
            System.out.println("Send request Stok Opnam Detail ->" + mapperStokOpnamDetail.writeValueAsString((Object)queryByIdJson));
            String hasilStokOpnamDetail = this.commonEngine.post(apiGetDataStokOpnamDetail, mapperStokOpnamDetail.writeValueAsString((Object)queryByIdJson));
            JsonResponseSyncStokOpnamDetail responseStokOpnamDetail = (JsonResponseSyncStokOpnamDetail)mapperStokOpnamDetail.readValue(hasilStokOpnamDetail, JsonResponseSyncStokOpnamDetail.class);
            if (!responseStokOpnamDetail.getStatus().equals("success")) throw new IOException("[ERROR Upsert_Stok_Opnam] failed to upsert pengembalian_detail query");
            int[] resultStokOpnam = this.stokOpnamDao.upsertDataStokOpnamDariOnline(responseStokOpnam.getData());
            int[] resultStokOpnamDetail = this.stokOpnamDetailDao.upsertDataStokOpnamDetailDariOnline(responseStokOpnamDetail.getData());
            if (resultStokOpnam == null) {
                throw new IOException("[ERROR]Upsert_Stok_Opnam: failed to upsert data");
            }
            if (resultStokOpnamDetail == null) {
                throw new IOException("[ERROR]Upsert_Stok_Opnam_Detail: failed to upsert data");
            }
            System.out.println("Upsert Stok Opnam -> OK");
            return 1;
        }
        catch (JsonProcessingException ex) {
            System.out.println("[ERROR Upsert_Stok_Opnam]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_Stok_Opnam] EXCEPTION Json Processing");
            return result;
        }
        catch (IOException ex) {
            System.out.println("[ERROR Upsert_Stok_Opnam]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_Stok_Opnam] EXCEPTION IO");
        }
        return result;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int doSyncVoucherUnsynced() {
        int result = -1;
        try {
            List<VoucherO2O> listVoucher = this.voucherDao.getAllVoucherUnsynced();
            VoucherJSON voucherJSON = new VoucherJSON();
            String apiVoucherInsertBulk = "/v1/private/voucher/insert_bulk";
            if (listVoucher.isEmpty()) return 0;
            voucherJSON.setListVoucher(listVoucher);
            ObjectMapper mapper = new ObjectMapper();
            this.commonEngine.setPrivateToken("sinarmartmjk01");
            String hasilInsert = this.commonEngine.post(apiVoucherInsertBulk, mapper.writeValueAsString((Object)voucherJSON));
            JsonResponseSyncVoucher response = (JsonResponseSyncVoucher)mapper.readValue(hasilInsert, JsonResponseSyncVoucher.class);
            if (!response.getStatus().equals("success")) throw new IOException("[ERROR] Sync_Voucher -> failed to insert bulk");
            StringBuilder inQuery = new StringBuilder();
            for (int x = 0; x < response.getData().length; ++x) {
                inQuery.append(response.getData()[x].getId_voucher_offline());
                if (x >= response.getData().length - 1) continue;
                inQuery.append(",");
            }
            int resultUpdate = this.voucherDao.updateVoucherUnsynced(inQuery.toString());
            if (resultUpdate <= 0) return 0;
            return 1;
        }
        catch (JsonProcessingException ex) {
            System.out.println("[ERROR Sync_Voucher]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Sync_Voucher] EXCEPTION Json Processing");
            return result;
        }
        catch (IOException ex) {
            System.out.println("[ERROR Sync_Voucher]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Sync_Voucher] EXCEPTION IO");
        }
        return result;
    }

    public int doUpsertDataVoucherDariOnline() {
        int result = -1;
        try {
            String date_start = this.getDateStart(2);
            if (date_start == null) {
                throw new IOException("[ERROR Upsert_Voucher] Date Start is NULL. Please fill them first in database");
            }
            String apiGetDataVoucher = "/v1/private/voucher/read_range_created_updated?start=" + date_start + "&idCabang=" + this.id_cabang;
            this.commonEngine.setPrivateToken("sinarmartmjk01");
            ObjectMapper mapperVoucher = new ObjectMapper();
            String hasilVoucher = this.commonEngine.get(apiGetDataVoucher);
            JsonResponseSyncVoucher responseVoucher = (JsonResponseSyncVoucher)mapperVoucher.readValue(hasilVoucher, JsonResponseSyncVoucher.class);
            if (responseVoucher.getStatus().equals("success")) {
                int[] resultVoucher = this.voucherDao.upsertDataVoucherDariOnline(responseVoucher.getData());
                if (resultVoucher == null) {
                    throw new IOException("[ERROR Upsert_Voucher] : Failed to upsert data");
                }
            } else {
                throw new IOException("[ERROR Upsert_Voucher] failed to get API data");
            }
            System.out.println("Upsert Voucher -> OK");
            result = 1;
        }
        catch (JsonProcessingException ex) {
            System.out.println("[ERROR Upsert_Voucher]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_Voucher] EXCEPTION Json Processing");
        }
        catch (IOException ex) {
            System.out.println("[ERROR Upsert_Voucher]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_Voucher] EXCEPTION IO");
        }
        return result;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int doSyncShiftKasirUnsynced() {
        int result = -1;
        try {
            List<ShiftKasirO2O> listShiftKasir = this.shiftKasirDao.getAllShiftKasirUnsynced();
            if (listShiftKasir == null) {
                throw new IOException("[ERROR] Sync_ShiftKasir -> failed to load unsynced data");
            }
            ShiftKasirJSON shiftKasirJSON = new ShiftKasirJSON();
            String apiShiftKasirInsertBulk = "/v1/private/shift_kasir/insert_bulk";
            if (listShiftKasir.isEmpty()) return 0;
            shiftKasirJSON.setListShiftKasir(listShiftKasir);
            ObjectMapper mapper = new ObjectMapper();
            this.commonEngine.setPrivateToken("sinarmartmjk01");
            String hasilInsert = this.commonEngine.post(apiShiftKasirInsertBulk, mapper.writeValueAsString((Object)shiftKasirJSON));
            JsonResponseSyncShiftKasir response = (JsonResponseSyncShiftKasir)mapper.readValue(hasilInsert, JsonResponseSyncShiftKasir.class);
            if (!response.getStatus().equals("success")) throw new IOException("[ERROR] Sync_ShiftKasir -> failed to insert bulk");
            StringBuilder inQuery = new StringBuilder();
            for (int x = 0; x < response.getData().length; ++x) {
                inQuery.append(response.getData()[x].getId_offline());
                if (x >= response.getData().length - 1) continue;
                inQuery.append(",");
            }
            int resultUpdate = this.shiftKasirDao.updateShiftKasirUnsynced(inQuery.toString());
            if (resultUpdate <= 0) return 0;
            return 1;
        }
        catch (JsonProcessingException ex) {
            System.out.println("[ERROR Sync_ShiftKasir]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Sync_ShiftKasir] EXCEPTION Json Processing");
            return result;
        }
        catch (IOException ex) {
            System.out.println("[ERROR Sync_ShiftKasir]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Sync_ShiftKasir] EXCEPTION IO");
        }
        return result;
    }

    public int doUpsertDataShiftKasirDariOnline() {
        int result = -1;
        try {
            String date_start = this.getDateStart(8);
            if (date_start == null) {
                throw new IOException("[ERROR Upsert_ShiftKasir] Date Start is NULL. Please fill them first in database");
            }
            String apiGetDataShiftKasir = "/v1/private/shift_kasir/read_range_tanggal?start=" + date_start + "&idCabang=" + this.id_cabang;
            this.commonEngine.setPrivateToken("sinarmartmjk01");
            ObjectMapper mapperShiftKasir = new ObjectMapper();
            String hasilShiftKasir = this.commonEngine.get(apiGetDataShiftKasir);
            JsonResponseSyncShiftKasir responseShiftKasir = (JsonResponseSyncShiftKasir)mapperShiftKasir.readValue(hasilShiftKasir, JsonResponseSyncShiftKasir.class);
            if (responseShiftKasir.getStatus().equals("success")) {
                int[] resultShiftKasir = this.shiftKasirDao.upsertDataShiftKasirDariOnline(responseShiftKasir.getData());
                if (resultShiftKasir == null) {
                    throw new IOException("[ERROR Upsert_ShiftKasir] : Failed to upsert data");
                }
            } else {
                throw new IOException("[ERROR Upsert_ShiftKasir] failed to get API data");
            }
            System.out.println("Upsert ShiftKasir -> OK");
            result = 1;
        }
        catch (JsonProcessingException ex) {
            System.out.println("[ERROR Upsert_ShiftKasir]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_ShiftKasir] EXCEPTION Json Processing");
        }
        catch (IOException ex) {
            System.out.println("[ERROR Upsert_ShiftKasir]: Failed to load -> " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "[ERROR Upsert_ShiftKasir] EXCEPTION IO");
        }
        return result;
    }

    public void doFilterPenjualanDetailOnlineDeleted(String start_date, String end_date, int deleteOnline) throws SQLException {
        this.ensureOnlineConnection();
        int idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
        List<Long> listOnline = this.penjualanDetailOnlineDao.getPenjualanDetailOnlineId(idCabang, start_date, end_date);
        List<Long> listOffline = this.penjualanDetailDao.getPenjualanDetailIdByDate(start_date, end_date);
        int count = 0;
        for (Long idDeleted : listOnline) {
            if (listOffline.contains(idDeleted)) continue;
            this.penjualanDetailDao.insertPenjualanDetailDeleted(idDeleted);
            ++count;
        }
        System.out.println("Count: " + count);
        if (deleteOnline == 1) {
            this.doDeletePenjualanDetailOnline();
        }
    }

    public void doDeletePenjualanDetailOnline() {
        this.ensureOnlineConnection();
        try {
            int idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
            List<Long> idsDeleted = this.penjualanDetailDao.getPenjualanDetailDeletedUnsync();
            int result = 0;
            if (idsDeleted.size() > 0) {
                StringBuilder ids = new StringBuilder("");
                for (int i = 0; i < idsDeleted.size(); ++i) {
                    Long idDeleted = idsDeleted.get(i);
                    ids.append(String.valueOf(idDeleted));
                    if (i >= idsDeleted.size() - 1) continue;
                    ids.append(",");
                }
                System.out.println(ids.toString());
                result = this.penjualanDetailOnlineDao.deletePenjualanDetailOnline(ids.toString(), String.valueOf(idCabang));
                System.out.println(result);
                if (result > 0) {
                    this.penjualanDetailDao.updateStatusSyncPenjualanDetailDeleted();
                }
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(SyncService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

