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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import minimart.engine.controller.Controller;
import minimart.engine.controller.Helper;
import minimart.engine.daoimpl.StockCommitDaoImpl;
import minimart.engine.model.JSONItemMigrasi;
import minimart.engine.model.JSONMigrasiHeader;
import minimart.engine.model.JSONResponseMigrasi;
import minimart.engine.model.JsonResponses;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.HistoryHargaService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PenjualanService;

public class MigrasiOnlineService
extends SwingWorker {
    private KonstantaService konstantaService = new KonstantaService();
    private BarangService barangService = new BarangService();
    private BarcodeService barcodeService = new BarcodeService();
    private ObjectMapper mapper = new ObjectMapper();
    private CommonEngine comm = new CommonEngine("https://admin.sinarmart.id");
    private Helper helper = Helper.getInstance();
    private HistoryHargaService historyHargaService = new HistoryHargaService();
    private int o2oSinkronBarang;
    private StockCommitDaoImpl stockCommitDao = new StockCommitDaoImpl();
    private PenjualanService penjualanService = new PenjualanService();

    public int insertMigrasiHeader(JSONMigrasiHeader jsonMigrasiHeader) throws JsonProcessingException, IOException {
        int id_migrasi_header = -1;
        try {
            String hasil = this.comm.post("/apis/o2o/migrasi/header/insert", this.mapper.writeValueAsString((Object)jsonMigrasiHeader));
            JSONResponseMigrasi response = (JSONResponseMigrasi)this.mapper.readValue(hasil, JSONResponseMigrasi.class);
            id_migrasi_header = "success".equals(response.getStatus()) ? (response.getId_migrasi_header() > 0 ? response.getId_migrasi_header() : -1) : -1;
        }
        catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            this.penjualanService.deletePenjualan(jsonMigrasiHeader.getId_penjualan_offline());
            JOptionPane.showMessageDialog(null, "Gagal Memasukkan data migrasi online, menghapus penjualan offline");
            return id_migrasi_header;
        }
        catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            this.penjualanService.deletePenjualan(jsonMigrasiHeader.getId_penjualan_offline());
            JOptionPane.showMessageDialog(null, "Gagal Memasukkan data migrasi online");
            return id_migrasi_header;
        }
        return id_migrasi_header;
    }

    public int updateMigrasiHeader(JSONMigrasiHeader jsonMigrasiHeader) throws JsonProcessingException, IOException {
        int id_migrasi_header = -1;
        try {
            String hasil = this.comm.post("/apis/o2o/migrasi/header/update", this.mapper.writeValueAsString((Object)jsonMigrasiHeader));
            JSONResponseMigrasi response = (JSONResponseMigrasi)this.mapper.readValue(hasil, JSONResponseMigrasi.class);
            if ("success".equals(response.getStatus())) {
                id_migrasi_header = response.getId_migrasi_header();
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gagal mengubah data migrasi header online");
            return id_migrasi_header;
        }
        return id_migrasi_header;
    }

    public int insertMigrasiDetail(JSONItemMigrasi jsonItemMigrasi) throws JsonProcessingException, IOException {
        int id_migrasi_item = -1;
        try {
            String hasil = this.comm.post("/apis/o2o/migrasi/detail/insert", this.mapper.writeValueAsString((Object)jsonItemMigrasi));
            JsonResponses response = (JsonResponses)this.mapper.readValue(hasil, JsonResponses.class);
            if ("success".equals(response.getStatus())) {
                id_migrasi_item = Integer.parseInt(response.getError());
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gagal Memasukkan data migrasi detail online");
            return id_migrasi_item;
        }
        return id_migrasi_item;
    }

    public boolean deleteMigrasiOnline(int id_migrasi_header) throws IOException {
        boolean success = false;
        try {
            String getURL = "/apis/o2o/migrasi/header/delete/" + String.valueOf(id_migrasi_header);
            String hasil = this.comm.get(getURL);
            JsonResponses response = (JsonResponses)this.mapper.readValue(hasil, JsonResponses.class);
            if ("success".equals(response.getStatus())) {
                success = true;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gagal menghapus data migrasi online");
            return false;
        }
        return success;
    }

    public boolean checkMigrasiUnreceived() {
        boolean success = false;
        String idCabang = this.konstantaService.getConstantValue("idCabang");
        int id_cabang = Integer.parseInt(idCabang);
        String getURL = "/apis/o2o/migrasi/header/check_unreceived/" + idCabang;
        try {
            System.out.println("URL Check Migrasi -> " + getURL);
            String hasil = this.comm.get(getURL);
            JSONResponseMigrasi response = (JSONResponseMigrasi)this.mapper.readValue(hasil, JSONResponseMigrasi.class);
            System.out.println("Status Check Migrasi -> " + response.getStatus());
            System.out.println("Length Check Migrasi -> " + response.getId_migrasi_header());
            if ("success".equals(response.getStatus()) && response.getId_migrasi_header() == 0) {
                success = true;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return success;
    }

    protected Object doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

