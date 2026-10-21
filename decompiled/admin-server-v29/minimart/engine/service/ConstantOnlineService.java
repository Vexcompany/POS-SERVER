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
import javax.swing.SwingWorker;
import minimart.engine.controller.Controller;
import minimart.engine.controller.Helper;
import minimart.engine.daoimpl.StockCommitDaoImpl;
import minimart.engine.model.ConstantO2o;
import minimart.engine.model.JsonResponseConstant;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.HistoryHargaService;
import minimart.engine.service.KonstantaService;

public class ConstantOnlineService
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

    public boolean getConstantOnline() throws JsonProcessingException, IOException {
        try {
            String hasil = this.comm.get("/apis/o2o/constant/all");
            JsonResponseConstant response = (JsonResponseConstant)this.mapper.readValue(hasil, JsonResponseConstant.class);
            System.out.println(response.getStatus());
            if (response.getStatus().equals("success")) {
                ConstantO2o[] listConstant;
                for (ConstantO2o listConstant1 : listConstant = response.getData()) {
                    this.konstantaService.updateConstant(listConstant1.getConstant(), listConstant1.getValue());
                }
                return true;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }

    protected Object doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

