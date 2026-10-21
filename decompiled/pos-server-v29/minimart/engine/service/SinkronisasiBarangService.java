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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import minimart.engine.controller.Helper;
import minimart.engine.daoimpl.StockCommitDaoImpl;
import minimart.engine.model.Barang;
import minimart.engine.model.BarangHpp;
import minimart.engine.model.BarangO2O;
import minimart.engine.model.BarangOnlineId;
import minimart.engine.model.Barcode;
import minimart.engine.model.HistoryHarga;
import minimart.engine.model.JSONBarang;
import minimart.engine.model.JSONBarangHpp;
import minimart.engine.model.Jenis;
import minimart.engine.model.JsonResponseBarang;
import minimart.engine.model.JsonResponseParent;
import minimart.engine.model.JsonResponses;
import minimart.engine.model.Konstanta;
import minimart.engine.model.ParentBarang;
import minimart.engine.model.StockCommit;
import minimart.engine.model.StockCommitJSON;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.HistoryHargaService;
import minimart.engine.service.JenisService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.ParentBarangService;

public class SinkronisasiBarangService
extends SwingWorker {
    private KonstantaService konstantaService = new KonstantaService();
    private BarangService barangService = new BarangService();
    private JenisService jenisService = new JenisService();
    private BarcodeService barcodeService = new BarcodeService();
    private ParentBarangService parentBarangService = new ParentBarangService();
    private ObjectMapper mapper = new ObjectMapper();
    private CommonEngine comm = new CommonEngine("https://admin.sinarmart.id");
    private Helper helper = Helper.getInstance();
    private HistoryHargaService historyHargaService = new HistoryHargaService();
    private int o2oSinkronBarang;
    private StockCommitDaoImpl stockCommitDao = new StockCommitDaoImpl();
    private boolean statusSyncStok = false;

    public int pushCommit() throws JsonProcessingException, IOException {
        this.statusSyncStok = true;
        this.konstantaService.updateConstant("sinkronStok", "true");
        List<StockCommit> stockCommits = this.stockCommitDao.getUncommitted();
        int returnval = 0;
        if (stockCommits.size() > 0) {
            StockCommitJSON stockCommitJSON = new StockCommitJSON();
            stockCommitJSON.setListStockCommit(stockCommits);
            System.out.println(System.currentTimeMillis());
            String hasil = this.comm.post("/apis/o2o/barang/commit/push", this.mapper.writeValueAsString((Object)stockCommitJSON));
            JsonResponses response = (JsonResponses)this.mapper.readValue(hasil, JsonResponses.class);
            System.out.println(response.getStatus());
            if ("success".equals(response.getStatus())) {
                StringBuilder stockCommitIdStr = new StringBuilder("");
                for (int i = 0; i < stockCommits.size(); ++i) {
                    stockCommitIdStr.append(String.valueOf(stockCommits.get(i).getStockTransactionCommitId()));
                    if (i >= stockCommits.size() - 1) continue;
                    stockCommitIdStr.append(',');
                }
                this.stockCommitDao.updateStatusStockCommitStr(stockCommitIdStr.toString());
                this.konstantaService.updateConstant("sinkronStok", "false");
                this.statusSyncStok = false;
                returnval = 1;
            } else {
                this.konstantaService.updateConstant("sinkronStok", "false");
                this.statusSyncStok = false;
                returnval = 0;
            }
        } else {
            this.konstantaService.updateConstant("sinkronStok", "false");
            this.statusSyncStok = false;
            returnval = 1;
        }
        this.konstantaService.updateConstant("sinkronStok", "false");
        this.statusSyncStok = false;
        return returnval;
    }

    public int pullStok() {
        return 1;
    }

    public int insertCommit(StockCommit stockCommit) {
        int result = this.stockCommitDao.insertStockCommit(stockCommit);
        return result;
    }

    public int insertStockCommited(StockCommit stockCommit) {
        int result = this.stockCommitDao.insertStockCommited(stockCommit);
        return result;
    }

    public int sinkronisasiStockBarang() {
        return 1;
    }

    public int sinkronisasiParentBarang() {
        int status = 0;
        try {
            System.out.println(System.currentTimeMillis());
            String hasilParent = this.comm.get("/apis/o2o/parent/read/all/" + this.konstantaService.getConstantValue("idCabang"));
            JsonResponseParent response = (JsonResponseParent)this.mapper.readValue(hasilParent, JsonResponseParent.class);
            System.out.println(response.getStatus());
            if (response.getStatus().equals("success")) {
                status = 1;
                ParentBarang[] parentBarangsOnline = response.getData();
                List<ParentBarang> parentBarangs = this.parentBarangService.getParents("");
                HashMap<String, ParentBarang> parentBarangMap = new HashMap<String, ParentBarang>();
                for (ParentBarang parentBarangOff : parentBarangs) {
                    parentBarangMap.put(parentBarangOff.getId(), parentBarangOff);
                }
                for (ParentBarang parentBarangTemp : parentBarangsOnline) {
                    ParentBarang parentBarangMapTemp = (ParentBarang)parentBarangMap.get(parentBarangTemp.getId());
                    if (parentBarangMapTemp == null) {
                        this.parentBarangService.createParent(parentBarangTemp);
                        continue;
                    }
                    if (parentBarangMapTemp.getNama().equals(parentBarangTemp.getNama())) continue;
                    parentBarangMapTemp.setNama(parentBarangTemp.getNama());
                    this.parentBarangService.update(parentBarangMapTemp);
                }
            } else {
                status = 0;
            }
        }
        catch (JsonProcessingException ex) {
            Logger.getLogger(SinkronisasiBarangService.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(SinkronisasiBarangService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    /*
     * WARNING - void declaration
     */
    public int sinkronisasiBarang(int withStock) throws IOException {
        System.out.println("https://admin.sinarmart.id");
        System.out.println("https://admin.sinarmart.id");
        System.out.println("https://customer.go.sinarmart.id");
        int o2oSinkronParent = this.sinkronisasiParentBarang();
        if (this.konstantaService.getConstantValue("sinkronStok") == null) {
            System.out.println("NULL CONSTANT SINKRON");
            Konstanta konstanta = new Konstanta();
            konstanta.setKonstan("sinkronStok");
            konstanta.setValue("false");
            this.konstantaService.createConstant(konstanta);
        }
        this.o2oSinkronBarang = 0;
        int o2oSinkronStok = "false".equals(this.konstantaService.getConstantValue("sinkronStok")) && !this.statusSyncStok ? this.pushCommit() : 1;
        boolean part = true;
        int result = 0;
        List<Barang> barangs = this.barangService.getBarangs("", "", false, "", true);
        ArrayList<BarangOnlineId> barangOnline = new ArrayList<BarangOnlineId>();
        for (Barang barang : barangs) {
            BarangOnlineId barangOnline1 = new BarangOnlineId();
            barangOnline1.setId(barang.getId());
            barangOnline1.setHpp(barang.getHpp());
            barangOnline1.setHppUpdated(barang.getHppUpdated());
        }
        JSONBarang barangJson = new JSONBarang();
        barangJson.setListBarang(barangOnline);
        System.out.println(o2oSinkronStok);
        if (o2oSinkronStok == 1) {
            try (InputStream stream = this.comm.postStreamNew("/apis/o2o/barang/read/cabang", this.mapper.writeValueAsString((Object)barangJson));){
                JsonResponseBarang response = (JsonResponseBarang)this.mapper.readValue(stream, JsonResponseBarang.class);
                if (response.getStatus().equals("success")) {
                    void var22_37;
                    void var22_34;
                    System.out.println(System.currentTimeMillis());
                    BarangO2O[] listBarang = response.getData1();
                    Barcode[] listBarcode = response.getData2();
                    HashMap<String, BarangO2O> mapO2O = new HashMap<String, BarangO2O>();
                    HashMap<String, Barang> map = new HashMap<String, Barang>();
                    HashMap<String, Barcode> mapBarcode = new HashMap<String, Barcode>();
                    HashMap<String, Barcode> mapBarcodeOnline = new HashMap<String, Barcode>();
                    for (BarangO2O barangO2O : listBarang) {
                        mapO2O.put(barangO2O.getId_sku(), barangO2O);
                    }
                    for (Barang barang : barangs) {
                        map.put(barang.getId(), barang);
                        if (mapO2O.get(barang.getId()) != null) continue;
                        this.barangService.deleteBarang(barang.getId());
                    }
                    for (BarangO2O barangO2O : listBarang) {
                        Barang barang = (Barang)map.get(barangO2O.getId_sku());
                        if (barang == null) {
                            Barang newBarang = new Barang();
                            newBarang.setId(barangO2O.getId_sku());
                            newBarang.setNama(barangO2O.getNama_item());
                            newBarang.setJenis(barangO2O.getJenis_item());
                            newBarang.setRakIdToko(barangO2O.getRak_toko());
                            newBarang.setRakIdGudang(barangO2O.getRak_gudang());
                            newBarang.setSatuan1(barangO2O.getSatuan_1());
                            newBarang.setSatuan2(barangO2O.getSatuan_2());
                            newBarang.setSatuan3(barangO2O.getSatuan_3());
                            newBarang.setKonversi21(barangO2O.getKonversi_21());
                            newBarang.setKonversi32(barangO2O.getKonversi_32());
                            newBarang.setHargaJual1(barangO2O.getHarga_jual_1());
                            newBarang.setHargaJual2(barangO2O.getHarga_jual_2());
                            newBarang.setHargaJual3(barangO2O.getHarga_jual_3());
                            newBarang.setMinPembelian(barangO2O.getMin_pembelian());
                            newBarang.setMinPersen(barangO2O.getMin_persen());
                            newBarang.setStokToko(barangO2O.getStok_toko());
                            newBarang.setStokGudang(barangO2O.getStok_gudang());
                            newBarang.setStokBs(barangO2O.getStok_bs());
                            newBarang.setBatasAtasStock(barangO2O.getSafety_stok());
                            newBarang.setReorderPoint(barangO2O.getReorder_point());
                            newBarang.setUpdateHarga(barangO2O.isUpdate_harga() ? 1 : 0);
                            newBarang.setCreated(new Date());
                            newBarang.setUpdated(new Date());
                            newBarang.setHpp(barangO2O.getHpp());
                            newBarang.setStokPusat(0.0f);
                            newBarang.setParentId(barangO2O.getParent_id());
                            newBarang.setTag(barangO2O.getTag());
                            newBarang.setExpDay(barangO2O.getExpDay());
                            if (this.jenisService.getJenis(newBarang.getJenis()) == null) {
                                Jenis jenis = new Jenis();
                                jenis.setJenis(newBarang.getJenis());
                                jenis.setPoinSales(0);
                                jenis.setCreated(new Date());
                                jenis.setUpdated(new Date());
                                this.jenisService.createJenis(jenis);
                            }
                            System.out.println(newBarang.getId());
                            result = this.barangService.createBarang(newBarang);
                        } else {
                            HistoryHarga historyHarga;
                            boolean updated = false;
                            boolean hppUpdated = false;
                            long historyHarga1 = barang.getHargaJual1();
                            long historyHarga2 = barang.getHargaJual2();
                            long historyHarga3 = barang.getHargaJual3();
                            Barang newBarang = barang;
                            if (newBarang.getNama() == null ? barangO2O.getNama_item() != null : !newBarang.getNama().equals(barangO2O.getNama_item())) {
                                newBarang.setNama(barangO2O.getNama_item());
                                updated = true;
                            }
                            if (newBarang.getParentId() == null ? barangO2O.getParent_id() != null : !newBarang.getParentId().equals(barangO2O.getParent_id())) {
                                newBarang.setParentId(barangO2O.getParent_id());
                                updated = true;
                            }
                            if (newBarang.getJenis() == null ? barangO2O.getJenis_item() != null : !newBarang.getJenis().equals(barangO2O.getJenis_item())) {
                                newBarang.setJenis(barangO2O.getJenis_item());
                                updated = true;
                            }
                            if (newBarang.getRakIdToko() == null ? barangO2O.getRak_toko() != null : !newBarang.getRakIdToko().equals(barangO2O.getRak_toko())) {
                                newBarang.setRakIdToko(barangO2O.getRak_toko());
                                updated = true;
                            }
                            if (newBarang.getRakIdGudang() == null ? barangO2O.getRak_gudang() != null : !newBarang.getRakIdGudang().equals(barangO2O.getRak_gudang())) {
                                newBarang.setRakIdGudang(barangO2O.getRak_gudang());
                                updated = true;
                            }
                            if (newBarang.getSatuan1() == null ? barangO2O.getSatuan_1() != null : !newBarang.getSatuan1().equals(barangO2O.getSatuan_1())) {
                                newBarang.setSatuan1(barangO2O.getSatuan_1());
                                updated = true;
                            }
                            if (newBarang.getSatuan2() == null ? barangO2O.getSatuan_2() != null : !newBarang.getSatuan2().equals(barangO2O.getSatuan_2())) {
                                newBarang.setSatuan2(barangO2O.getSatuan_2());
                                updated = true;
                            }
                            if (newBarang.getSatuan3() == null ? barangO2O.getSatuan_3() != null : !newBarang.getSatuan3().equals(barangO2O.getSatuan_3())) {
                                newBarang.setSatuan3(barangO2O.getSatuan_3());
                                updated = true;
                            }
                            if (newBarang.getKonversi21() != barangO2O.getKonversi_21()) {
                                newBarang.setKonversi21(barangO2O.getKonversi_21());
                                updated = true;
                            }
                            if (newBarang.getKonversi32() != barangO2O.getKonversi_32()) {
                                newBarang.setKonversi32(barangO2O.getKonversi_32());
                                updated = true;
                            }
                            if (newBarang.getHargaJual1() != barangO2O.getHarga_jual_1()) {
                                newBarang.setHargaJual1(barangO2O.getHarga_jual_1());
                                updated = true;
                            }
                            if (newBarang.getHargaJual2() != barangO2O.getHarga_jual_2()) {
                                newBarang.setHargaJual2(barangO2O.getHarga_jual_2());
                                updated = true;
                            }
                            if (newBarang.getHargaJual3() != barangO2O.getHarga_jual_3()) {
                                newBarang.setHargaJual3(barangO2O.getHarga_jual_3());
                                updated = true;
                            }
                            if (withStock == 1) {
                                if (newBarang.getStokToko() != barangO2O.getStok_toko()) {
                                    newBarang.setStokToko(barangO2O.getStok_toko());
                                    updated = true;
                                }
                                if (newBarang.getStokGudang() != barangO2O.getStok_gudang()) {
                                    newBarang.setStokGudang(barangO2O.getStok_gudang());
                                    updated = true;
                                }
                                if (newBarang.getStokBs() != barangO2O.getStok_bs()) {
                                    newBarang.setStokBs(barangO2O.getStok_bs());
                                    updated = true;
                                }
                            }
                            if (newBarang.getMinPembelian() != barangO2O.getMin_pembelian()) {
                                newBarang.setMinPembelian(barangO2O.getMin_pembelian());
                                updated = true;
                            }
                            if (newBarang.getMinPersen() != barangO2O.getMin_persen()) {
                                newBarang.setMinPersen(barangO2O.getMin_persen());
                                updated = true;
                            }
                            if (newBarang.getBatasAtasStock() != (float)barangO2O.getSafety_stok()) {
                                newBarang.setBatasAtasStock(barangO2O.getSafety_stok());
                                updated = true;
                            }
                            if (newBarang.getReorderPoint() != (float)barangO2O.getReorder_point()) {
                                newBarang.setReorderPoint(barangO2O.getReorder_point());
                                updated = true;
                            }
                            if (newBarang.getUpdateHarga() != (barangO2O.isUpdate_harga() ? 1 : 0)) {
                                newBarang.setUpdateHarga(barangO2O.isUpdate_harga() ? 1 : 0);
                                updated = true;
                            }
                            if (newBarang.getTag() == null ? barangO2O.getTag() != null : !newBarang.getTag().equals(barangO2O.getTag())) {
                                newBarang.setTag(barangO2O.getTag());
                                updated = true;
                            }
                            if (newBarang.getExpDay() != barangO2O.getExpDay()) {
                                newBarang.setExpDay(barangO2O.getExpDay());
                                updated = true;
                            }
                            newBarang.setUpdated(new Date());
                            if (newBarang.getHpp() != barangO2O.getHpp()) {
                                newBarang.setHpp(barangO2O.getHpp());
                                hppUpdated = true;
                                updated = true;
                            }
                            if (updated) {
                                if (this.jenisService.getJenis(newBarang.getJenis()) == null) {
                                    Jenis jenis = new Jenis();
                                    jenis.setJenis(newBarang.getJenis());
                                    jenis.setPoinSales(0);
                                    jenis.setCreated(new Date());
                                    jenis.setUpdated(new Date());
                                    this.jenisService.createJenis(jenis);
                                }
                                if (!hppUpdated) {
                                    result = this.barangService.updateBarang2(newBarang, newBarang.getId());
                                } else if (hppUpdated) {
                                    result = this.barangService.updateBarang2Hpp(newBarang, newBarang.getId());
                                }
                            } else {
                                result = 1;
                            }
                            if (Long.parseLong(barangO2O.getHarga_jual_1() + "") != historyHarga1) {
                                historyHarga = new HistoryHarga();
                                historyHarga.setBarangId(newBarang.getId());
                                historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                                historyHarga.setSatuan(newBarang.getSatuan1());
                                historyHarga.setHargaBaru(newBarang.getHargaJual1());
                                historyHarga.setHargaLama(historyHarga1);
                                historyHarga.setTanggal(new Date());
                                historyHarga.setCreated(new Date());
                                this.historyHargaService.createHistoryHarga(historyHarga);
                            }
                            if (Long.parseLong(barangO2O.getHarga_jual_2() + "") != historyHarga2) {
                                historyHarga = new HistoryHarga();
                                historyHarga.setBarangId(newBarang.getId());
                                historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                                historyHarga.setSatuan(newBarang.getSatuan2());
                                historyHarga.setHargaBaru(newBarang.getHargaJual2());
                                historyHarga.setHargaLama(historyHarga2);
                                historyHarga.setTanggal(new Date());
                                historyHarga.setCreated(new Date());
                                this.historyHargaService.createHistoryHarga(historyHarga);
                            }
                            if (Long.parseLong(barangO2O.getHarga_jual_3() + "") != historyHarga3) {
                                historyHarga = new HistoryHarga();
                                historyHarga.setBarangId(newBarang.getId());
                                historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                                historyHarga.setSatuan(newBarang.getSatuan3());
                                historyHarga.setHargaBaru(newBarang.getHargaJual3());
                                historyHarga.setHargaLama(historyHarga3);
                                historyHarga.setTanggal(new Date());
                                historyHarga.setCreated(new Date());
                                this.historyHargaService.createHistoryHarga(historyHarga);
                            }
                        }
                        if (result == 1) continue;
                        System.out.println(barangO2O.getId_sku());
                        part = false;
                    }
                    List<Barcode> listBarcodeOffline = this.barcodeService.getBarcodesAll();
                    Object object = listBarcode;
                    int n = ((Barcode[])object).length;
                    boolean bl = false;
                    while (var22_34 < n) {
                        Barcode barcode = object[var22_34];
                        mapBarcodeOnline.put(barcode.getBarcode(), barcode);
                        ++var22_34;
                    }
                    for (Barcode barcode1 : listBarcodeOffline) {
                        mapBarcode.put(barcode1.getBarcode(), barcode1);
                        Barcode barcode = (Barcode)mapBarcodeOnline.get(barcode1.getBarcode());
                        if (barcode != null) continue;
                        this.barcodeService.deleteBarcode(barcode1, barcode1.getM_barang_id());
                    }
                    object = listBarcode;
                    n = ((Object)object).length;
                    boolean bl2 = false;
                    while (var22_37 < n) {
                        Object object2 = object[var22_37];
                        Barcode offlineBarcode = (Barcode)mapBarcode.get(((Barcode)object2).getBarcode());
                        if (offlineBarcode != null) {
                            if (((offlineBarcode.getSatuan() != null ? !offlineBarcode.getSatuan().equals(((Barcode)object2).getSatuan()) : ((Barcode)object2).getSatuan() != null) || (offlineBarcode.getM_barang_id() == null ? ((Barcode)object2).getM_barang_id() != null : !offlineBarcode.getM_barang_id().equals(((Barcode)object2).getM_barang_id()))) && mapO2O.get(((Barcode)object2).getM_barang_id()) != null) {
                                offlineBarcode.setSatuan(((Barcode)object2).getSatuan());
                                offlineBarcode.setM_barang_id(((Barcode)object2).getM_barang_id());
                                offlineBarcode.setUpdated(new Date());
                                this.barcodeService.updateBarcode(offlineBarcode);
                            }
                        } else if (mapO2O.get(((Barcode)object2).getM_barang_id()) != null) {
                            System.out.println(String.valueOf(((Barcode)object2).getId()));
                            System.out.println(String.valueOf(((Barcode)object2).getM_barang_id()));
                            System.out.println(((Barcode)object2).getBarcode());
                            System.out.println(((Barcode)object2).getSatuan());
                            System.out.println(((Barcode)object2).getCreated().toString());
                            System.out.println(((Barcode)object2).getUpdated().toString());
                            this.barcodeService.createBarcodeNoId((Barcode)object2);
                        }
                        ++var22_37;
                    }
                    this.o2oSinkronBarang = !part ? 2 : 1;
                }
            }
            catch (JsonProcessingException ex) {
                Logger.getLogger(SinkronisasiBarangService.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex) {
                Logger.getLogger(SinkronisasiBarangService.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.o2oSinkronBarang;
    }

    /*
     * WARNING - void declaration
     */
    public int sinkronisasiBarangHpp(int withStock) throws IOException {
        System.out.println("https://admin.sinarmart.id");
        System.out.println("https://admin.sinarmart.id");
        System.out.println("https://customer.go.sinarmart.id");
        if (this.konstantaService.getConstantValue("sinkronStok") == null) {
            System.out.println("NULL CONSTANT SINKRON");
            Konstanta konstanta = new Konstanta();
            konstanta.setKonstan("sinkronStok");
            konstanta.setValue("false");
            this.konstantaService.createConstant(konstanta);
        }
        this.o2oSinkronBarang = 0;
        int o2oSinkronStok = "false".equals(this.konstantaService.getConstantValue("sinkronStok")) ? this.pushCommit() : 1;
        boolean part = true;
        int result = 0;
        List<Barang> barangs = this.barangService.getBarangs("", "", false, "", true);
        ArrayList<BarangOnlineId> barangOnline = new ArrayList<BarangOnlineId>();
        for (Barang barang : barangs) {
            BarangOnlineId barangOnline1 = new BarangOnlineId();
            Long hppBarang = this.helper.getHargaBeliTerakhirSatuanTerkecil(barang.getId());
            barangOnline1.setId(barang.getId());
            barangOnline1.setHpp(hppBarang);
            barangOnline1.setHppUpdated(System.currentTimeMillis() / 1000L);
            barangOnline.add(barangOnline1);
        }
        JSONBarang barangJson = new JSONBarang();
        barangJson.setListBarang(barangOnline);
        System.out.println(o2oSinkronStok);
        if (o2oSinkronStok == 1) {
            try {
                System.out.println(System.currentTimeMillis());
                String hasil = this.comm.post("/apis/o2o/barang/sinkron/hpp", this.mapper.writeValueAsString((Object)barangJson));
                JsonResponseBarang response = (JsonResponseBarang)this.mapper.readValue(hasil, JsonResponseBarang.class);
                System.out.println(response.getStatus());
                if (response.getStatus().equals("success")) {
                    void var19_32;
                    void var19_29;
                    System.out.println(System.currentTimeMillis());
                    BarangO2O[] listBarang = response.getData1();
                    Barcode[] listBarcode = response.getData2();
                    HashMap<String, BarangO2O> mapO2O = new HashMap<String, BarangO2O>();
                    HashMap<String, Barang> map = new HashMap<String, Barang>();
                    HashMap<String, Barcode> mapBarcode = new HashMap<String, Barcode>();
                    HashMap<String, Barcode> mapBarcodeOnline = new HashMap<String, Barcode>();
                    for (BarangO2O barangO2O : listBarang) {
                        mapO2O.put(barangO2O.getId_sku(), barangO2O);
                    }
                    for (Barang barang : barangs) {
                        map.put(barang.getId(), barang);
                        if (mapO2O.get(barang.getId()) != null) continue;
                        this.barangService.deleteBarang(barang.getId());
                    }
                    for (BarangO2O barangO2O : listBarang) {
                        Barang barang = (Barang)map.get(barangO2O.getId_sku());
                        if (barang == null) {
                            Barang newBarang = new Barang();
                            newBarang.setId(barangO2O.getId_sku());
                            newBarang.setNama(barangO2O.getNama_item());
                            newBarang.setJenis(barangO2O.getJenis_item());
                            newBarang.setRakIdToko(barangO2O.getRak_toko());
                            newBarang.setRakIdGudang(barangO2O.getRak_gudang());
                            newBarang.setSatuan1(barangO2O.getSatuan_1());
                            newBarang.setSatuan2(barangO2O.getSatuan_2());
                            newBarang.setSatuan3(barangO2O.getSatuan_3());
                            newBarang.setKonversi21(barangO2O.getKonversi_21());
                            newBarang.setKonversi32(barangO2O.getKonversi_32());
                            newBarang.setHargaJual1(barangO2O.getHarga_jual_1());
                            newBarang.setHargaJual2(barangO2O.getHarga_jual_2());
                            newBarang.setHargaJual3(barangO2O.getHarga_jual_3());
                            newBarang.setMinPembelian(barangO2O.getMin_pembelian());
                            newBarang.setMinPersen(barangO2O.getMin_persen());
                            newBarang.setStokToko(barangO2O.getStok_toko());
                            newBarang.setStokGudang(barangO2O.getStok_gudang());
                            newBarang.setStokBs(barangO2O.getStok_bs());
                            newBarang.setBatasAtasStock(barangO2O.getSafety_stok());
                            newBarang.setReorderPoint(barangO2O.getReorder_point());
                            newBarang.setUpdateHarga(barangO2O.isUpdate_harga() ? 1 : 0);
                            newBarang.setCreated(new Date());
                            newBarang.setUpdated(new Date());
                            newBarang.setHpp(barangO2O.getHpp());
                            newBarang.setStokPusat(0.0f);
                            result = this.barangService.createBarang(newBarang);
                        } else {
                            HistoryHarga historyHarga;
                            boolean updated = false;
                            boolean hppUpdated = false;
                            long historyHarga1 = barang.getHargaJual1();
                            long historyHarga2 = barang.getHargaJual2();
                            long historyHarga3 = barang.getHargaJual3();
                            Barang newBarang = barang;
                            if (newBarang.getNama() == null ? barangO2O.getNama_item() != null : !newBarang.getNama().equals(barangO2O.getNama_item())) {
                                newBarang.setNama(barangO2O.getNama_item());
                                updated = true;
                            }
                            if (newBarang.getJenis() == null ? barangO2O.getJenis_item() != null : !newBarang.getJenis().equals(barangO2O.getJenis_item())) {
                                newBarang.setJenis(barangO2O.getJenis_item());
                                updated = true;
                            }
                            if (newBarang.getRakIdToko() == null ? barangO2O.getRak_toko() != null : !newBarang.getRakIdToko().equals(barangO2O.getRak_toko())) {
                                newBarang.setRakIdToko(barangO2O.getRak_toko());
                                updated = true;
                            }
                            if (newBarang.getRakIdGudang() == null ? barangO2O.getRak_gudang() != null : !newBarang.getRakIdGudang().equals(barangO2O.getRak_gudang())) {
                                newBarang.setRakIdGudang(barangO2O.getRak_gudang());
                                updated = true;
                            }
                            if (newBarang.getSatuan1() == null ? barangO2O.getSatuan_1() != null : !newBarang.getSatuan1().equals(barangO2O.getSatuan_1())) {
                                newBarang.setSatuan1(barangO2O.getSatuan_1());
                                updated = true;
                            }
                            if (newBarang.getSatuan2() == null ? barangO2O.getSatuan_2() != null : !newBarang.getSatuan2().equals(barangO2O.getSatuan_2())) {
                                newBarang.setSatuan2(barangO2O.getSatuan_2());
                                updated = true;
                            }
                            if (newBarang.getSatuan3() == null ? barangO2O.getSatuan_3() != null : !newBarang.getSatuan3().equals(barangO2O.getSatuan_3())) {
                                newBarang.setSatuan3(barangO2O.getSatuan_3());
                                updated = true;
                            }
                            if (newBarang.getKonversi21() != barangO2O.getKonversi_21()) {
                                newBarang.setKonversi21(barangO2O.getKonversi_21());
                                updated = true;
                            }
                            if (newBarang.getKonversi32() != barangO2O.getKonversi_32()) {
                                newBarang.setKonversi32(barangO2O.getKonversi_32());
                                updated = true;
                            }
                            if (newBarang.getHargaJual1() != barangO2O.getHarga_jual_1()) {
                                newBarang.setHargaJual1(barangO2O.getHarga_jual_1());
                                updated = true;
                            }
                            if (newBarang.getHargaJual2() != barangO2O.getHarga_jual_2()) {
                                newBarang.setHargaJual2(barangO2O.getHarga_jual_2());
                                updated = true;
                            }
                            if (newBarang.getHargaJual3() != barangO2O.getHarga_jual_3()) {
                                newBarang.setHargaJual3(barangO2O.getHarga_jual_3());
                                updated = true;
                            }
                            if (withStock == 1) {
                                if (newBarang.getStokToko() != barangO2O.getStok_toko()) {
                                    newBarang.setStokToko(barangO2O.getStok_toko());
                                    updated = true;
                                }
                                if (newBarang.getStokGudang() != barangO2O.getStok_gudang()) {
                                    newBarang.setStokGudang(barangO2O.getStok_gudang());
                                    updated = true;
                                }
                                if (newBarang.getStokBs() != barangO2O.getStok_bs()) {
                                    newBarang.setStokBs(barangO2O.getStok_bs());
                                    updated = true;
                                }
                            }
                            if (newBarang.getMinPembelian() != barangO2O.getMin_pembelian()) {
                                newBarang.setMinPembelian(barangO2O.getMin_pembelian());
                                updated = true;
                            }
                            if (newBarang.getMinPersen() != barangO2O.getMin_persen()) {
                                newBarang.setMinPersen(barangO2O.getMin_persen());
                                updated = true;
                            }
                            if (newBarang.getBatasAtasStock() != (float)barangO2O.getSafety_stok()) {
                                newBarang.setBatasAtasStock(barangO2O.getSafety_stok());
                                updated = true;
                            }
                            if (newBarang.getReorderPoint() != (float)barangO2O.getReorder_point()) {
                                newBarang.setReorderPoint(barangO2O.getReorder_point());
                                updated = true;
                            }
                            if (newBarang.getUpdateHarga() != (barangO2O.isUpdate_harga() ? 1 : 0)) {
                                newBarang.setUpdateHarga(barangO2O.isUpdate_harga() ? 1 : 0);
                                updated = true;
                            }
                            newBarang.setUpdated(new Date());
                            if (newBarang.getHpp() != barangO2O.getHpp()) {
                                newBarang.setHpp(barangO2O.getHpp());
                                hppUpdated = true;
                                updated = true;
                            }
                            if (updated) {
                                if (!hppUpdated) {
                                    result = this.barangService.updateBarang2(newBarang, newBarang.getId());
                                } else if (hppUpdated) {
                                    result = this.barangService.updateBarang2Hpp(newBarang, newBarang.getId());
                                }
                            } else {
                                result = 1;
                            }
                            if (Long.parseLong(barangO2O.getHarga_jual_1() + "") != historyHarga1) {
                                historyHarga = new HistoryHarga();
                                historyHarga.setBarangId(newBarang.getId());
                                historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                                historyHarga.setSatuan(newBarang.getSatuan1());
                                historyHarga.setHargaBaru(newBarang.getHargaJual1());
                                historyHarga.setHargaLama(historyHarga1);
                                historyHarga.setTanggal(new Date());
                                historyHarga.setCreated(new Date());
                                this.historyHargaService.createHistoryHarga(historyHarga);
                            }
                            if (Long.parseLong(barangO2O.getHarga_jual_2() + "") != historyHarga2) {
                                historyHarga = new HistoryHarga();
                                historyHarga.setBarangId(newBarang.getId());
                                historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                                historyHarga.setSatuan(newBarang.getSatuan2());
                                historyHarga.setHargaBaru(newBarang.getHargaJual2());
                                historyHarga.setHargaLama(historyHarga2);
                                historyHarga.setTanggal(new Date());
                                historyHarga.setCreated(new Date());
                                this.historyHargaService.createHistoryHarga(historyHarga);
                            }
                            if (Long.parseLong(barangO2O.getHarga_jual_3() + "") != historyHarga3) {
                                historyHarga = new HistoryHarga();
                                historyHarga.setBarangId(newBarang.getId());
                                historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                                historyHarga.setSatuan(newBarang.getSatuan3());
                                historyHarga.setHargaBaru(newBarang.getHargaJual3());
                                historyHarga.setHargaLama(historyHarga3);
                                historyHarga.setTanggal(new Date());
                                historyHarga.setCreated(new Date());
                                this.historyHargaService.createHistoryHarga(historyHarga);
                            }
                        }
                        if (result == 1) continue;
                        part = false;
                    }
                    List<Barcode> listBarcodeOffline = this.barcodeService.getBarcodesAll();
                    Object object = listBarcode;
                    int n = ((Barcode[])object).length;
                    boolean bl = false;
                    while (var19_29 < n) {
                        Barcode barcode = object[var19_29];
                        mapBarcodeOnline.put(barcode.getBarcode(), barcode);
                        ++var19_29;
                    }
                    for (Barcode barcode1 : listBarcodeOffline) {
                        mapBarcode.put(barcode1.getBarcode(), barcode1);
                        Barcode barcode = (Barcode)mapBarcodeOnline.get(barcode1.getBarcode());
                        if (barcode != null) continue;
                        this.barcodeService.deleteBarcode(barcode1, barcode1.getM_barang_id());
                    }
                    object = listBarcode;
                    n = ((Object)object).length;
                    boolean bl2 = false;
                    while (var19_32 < n) {
                        Object object2 = object[var19_32];
                        Barcode offlineBarcode = (Barcode)mapBarcode.get(((Barcode)object2).getBarcode());
                        if (offlineBarcode != null) {
                            if ((offlineBarcode.getSatuan() != null ? !offlineBarcode.getSatuan().equals(((Barcode)object2).getSatuan()) : ((Barcode)object2).getSatuan() != null) || (offlineBarcode.getM_barang_id() == null ? ((Barcode)object2).getM_barang_id() != null : !offlineBarcode.getM_barang_id().equals(((Barcode)object2).getM_barang_id()))) {
                                this.barcodeService.updateBarcode((Barcode)object2);
                            }
                        } else {
                            this.barcodeService.createBarcodeNoId((Barcode)object2);
                        }
                        ++var19_32;
                    }
                    this.o2oSinkronBarang = !part ? 2 : 1;
                }
            }
            catch (JsonProcessingException ex) {
                Logger.getLogger(SinkronisasiBarangService.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex) {
                Logger.getLogger(SinkronisasiBarangService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.o2oSinkronBarang;
    }

    public int pushHpp(ArrayList<BarangHpp> listBarang) throws IOException {
        int o2oPushHpp = 0;
        JSONBarangHpp jsonBarangHpp = new JSONBarangHpp();
        jsonBarangHpp.setListBarangHpp(listBarang);
        String hasil = this.comm.post("/apis/o2o/barang/update/hpp", this.mapper.writeValueAsString((Object)jsonBarangHpp));
        JsonResponses response = (JsonResponses)this.mapper.readValue(hasil, JsonResponses.class);
        if (response.getStatus().equals("success")) {
            o2oPushHpp = 1;
        }
        return o2oPushHpp;
    }

    protected Object doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

