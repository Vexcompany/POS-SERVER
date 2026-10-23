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
import minimart.engine.json_response.DataResponseLoginOnline;
import minimart.engine.json_response.JsonResponseLoginOnline;
import minimart.engine.model.DbCredential;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Karyawan_1;
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
        if (Boolean.getBoolean("pos.local")) {
            return;
        }
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
        String localUsername = System.getenv("POS_LOCAL_USERNAME");
        String localPassword = System.getenv("POS_LOCAL_PASSWORD");
        if (localUsername != null && localPassword != null && localUsername.equals(username) && localPassword.equals(password)) {
            System.setProperty("pos.local", "true");
            Karyawan_1 user = new Karyawan_1();
            user.setId(username);
            user.setNama(username);
            user.setPassword(password);
            user.setRole("ADMIN");
            user.setId_cabang("LOCAL");
            user.setTipe_toko("Minimarket");

            DbCredential localDb = new DbCredential();
            localDb.setId_cabang(0);
            localDb.setServer_ip_address(System.getProperty("pos.local.db.host", "127.0.0.1"));
            localDb.setMysql_port(System.getProperty("pos.local.db.port", "3306"));
            localDb.setMysql_database(System.getProperty("pos.local.db.name", "minimarket"));
            localDb.setMysql_username(System.getProperty("pos.local.db.user", "root"));
            localDb.setMysql_password(System.getProperty("pos.local.db.password", ""));
            localDb.setNama_cabang("LOCAL");
            localDb.setTipe_cabang("LOCAL");
            localDb.setTipe_toko("Minimarket");
            localDb.setIs_gudang(false);

            DataResponseLoginOnline data = new DataResponseLoginOnline();
            data.setKaryawan(user);
            data.setList_cabang(new DbCredential[]{localDb});
            result.setStatus("success");
            result.setMessage("Local development login");
            result.setCode("LOCAL");
            result.setData(data);
            return result;
        }
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
