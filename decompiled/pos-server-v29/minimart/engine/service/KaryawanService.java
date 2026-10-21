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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.KaryawanDao;
import minimart.engine.dao.KaryawanOnlineDao;
import minimart.engine.daoimpl.KaryawanDaoImpl;
import minimart.engine.daoimpl.KaryawanOnlineDaoImpl;
import minimart.engine.json.LoginOnlineJSON;
import minimart.engine.json_response.JsonResponseLoginOnline;
import minimart.engine.model.Karyawan;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.KonstantaService;

public class KaryawanService {
    private KaryawanDao karyawanDao = new KaryawanDaoImpl();
    private KaryawanOnlineDao karyawanOnlineDao = new KaryawanOnlineDaoImpl();
    private KonstantaService ks = new KonstantaService();
    CommonEngine engine = new CommonEngine("https://api.dev.paperpen.sinarmart.id");
    private ObjectMapper mapper = new ObjectMapper();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public Karyawan authenticate(String karyawanId, String password) {
        Karyawan karyawan = this.karyawanDao.getKaryawan(karyawanId);
        if (karyawan != null && password.equals(karyawan.getPassword())) {
            return karyawan;
        }
        return null;
    }

    public Karyawan getKaryawan(String id) {
        return this.karyawanDao.getKaryawan(id);
    }

    public Karyawan getAllKaryawan(String karyawanId) {
        return this.karyawanDao.getAllKaryawan(karyawanId);
    }

    public Karyawan getKaryawanByPassword(String password) {
        return this.karyawanDao.getKaryawanByPassword(password);
    }

    public int createKaryawan(Karyawan karyawan) {
        JOptionPane.showMessageDialog(null, "Fitur create karyawan dipindahkan ke online");
        return 0;
    }

    public int updateKaryawan(Karyawan karyawan) {
        JOptionPane.showMessageDialog(null, "Fitur update karyawan dipindahkan ke online");
        return 0;
    }

    public int updateKaryawanPassword(Karyawan karyawan) {
        this.ensureOnlineConnection();
        boolean result = false;
        int resultOnline = 0;
        resultOnline = this.karyawanOnlineDao.updateKaryawanPassword(karyawan);
        if (resultOnline == 1) {
            return this.karyawanDao.updateKaryawan(karyawan);
        }
        return resultOnline;
    }

    public int deleteKaryawan(String id) {
        JOptionPane.showMessageDialog(null, "Fitur delete karyawan dipindahkan ke online");
        return 0;
    }

    public List<Karyawan> getKaryawans(String nama) {
        return this.karyawanDao.getKaryawans(nama);
    }

    public int[] upsertKaryawan(List<Karyawan> karyawan) {
        return this.karyawanDao.upsertKaryawan(karyawan);
    }

    public int softDeleteKaryawan(String ids) {
        return this.karyawanDao.softDeleteKaryawan(ids);
    }

    public List<Karyawan> getKaryawanOnline() {
        this.ensureOnlineConnection();
        String idCabang = this.ks.getConstantValue("idCabang");
        return this.karyawanOnlineDao.getKaryawanOnline(idCabang);
    }

    public void sinkroniasaiKaryawanOnlineToOffline() {
        List<Karyawan> karyawanOnlines = this.getKaryawanOnline();
        HashMap<String, Karyawan> mapOnline = new HashMap<String, Karyawan>();
        int[] resultUpsert = this.upsertKaryawan(karyawanOnlines);
        for (Karyawan karyawanOnline : karyawanOnlines) {
            mapOnline.put(karyawanOnline.getId(), karyawanOnline);
        }
        List<Karyawan> karyawanOfflines = this.getKaryawans("");
        StringBuilder idKaryawanDelete = new StringBuilder();
        for (Karyawan karyawanOffline : karyawanOfflines) {
            if (mapOnline.get(karyawanOffline.getId()) != null) continue;
            if (idKaryawanDelete.length() > 0) {
                idKaryawanDelete.append(", '").append(karyawanOffline.getId()).append("'");
                continue;
            }
            idKaryawanDelete.append("'").append(karyawanOffline.getId()).append("'");
        }
        int resultDelete = 0;
        if (idKaryawanDelete.length() > 0) {
            resultDelete = this.softDeleteKaryawan(idKaryawanDelete.toString());
        }
    }

    public JsonResponseLoginOnline authLoginOnline(String username, String password) {
        JsonResponseLoginOnline result = new JsonResponseLoginOnline();
        LoginOnlineJSON jsonLogin = new LoginOnlineJSON();
        jsonLogin.setUsername(username);
        jsonLogin.setPassword(password);
        try {
            String hasil = this.engine.post("/v2/public/karyawan/login_so_apps", this.mapper.writeValueAsString((Object)jsonLogin));
            JsonResponseLoginOnline res = (JsonResponseLoginOnline)this.mapper.readValue(hasil, JsonResponseLoginOnline.class);
            System.out.println(res.getStatus());
            result = res;
        }
        catch (JsonProcessingException ex) {
            Logger.getLogger(KaryawanService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        catch (IOException ex) {
            Logger.getLogger(KaryawanService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}

