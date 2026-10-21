/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import javax.swing.JOptionPane;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.StokOpnamMandiriDao;
import minimart.engine.dao.StokOpnamMandiriDetailDao;
import minimart.engine.dao.StokOpnamMandiriOnlineDao;
import minimart.engine.daoimpl.StokOpnamMandiriDaoImpl;
import minimart.engine.daoimpl.StokOpnamMandiriDetailDaoImpl;
import minimart.engine.daoimpl.StokOpnamMandiriOnlineDaoImpl;
import minimart.engine.model.StokOpnamMandiri;
import minimart.engine.model.StokOpnamMandiriDetail;
import minimart.engine.service.StokOpnamMandiriDetailService;
import minimart.engine.service.StokOpnamMandiriService;

public class StokOpnamMandiriOnlineService {
    private final StokOpnamMandiriDao somDao = new StokOpnamMandiriDaoImpl();
    private final StokOpnamMandiriDetailDao somdDao = new StokOpnamMandiriDetailDaoImpl();
    private final StokOpnamMandiriOnlineDao somoDao = new StokOpnamMandiriOnlineDaoImpl();
    private final StokOpnamMandiriService somService = new StokOpnamMandiriService();
    private final StokOpnamMandiriDetailService somdService = new StokOpnamMandiriDetailService();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int downloadSOM() {
        try {
            System.out.println("Upload SOM Past Week");
            this.uploadBatchJSONPassWeek();
            System.out.println("Masuk Download SOM");
            List<StokOpnamMandiri> soms = this.getStokOpnamMandiriPastSixMonth();
            List<StokOpnamMandiriDetail> somds = this.getStokOpnamMandiriDetailPastSixMonth();
            String ids_soms = "";
            String ids_somds = "";
            int i = 0;
            for (StokOpnamMandiri som : soms) {
                ids_soms = ids_soms + som.getId();
                if (i++ >= soms.size() - 1) continue;
                ids_soms = ids_soms + ",";
            }
            i = 0;
            for (StokOpnamMandiriDetail somd : somds) {
                ids_somds = ids_somds + somd.getId();
                if (i++ >= somds.size() - 1) continue;
                ids_somds = ids_somds + ",";
            }
            System.out.println("id SOMs -> " + ids_soms);
            System.out.println("id SOMDs -> " + ids_somds);
            List<StokOpnamMandiri> somos = this.getStokOpnamMandiriOnline(ids_soms);
            List<StokOpnamMandiri> somosdeleted = this.getStokOpnamMandiriOnlineDeleted(ids_soms);
            List<StokOpnamMandiriDetail> somdos = this.getStokOpnamMandiriDetailOnline(ids_somds);
            if (somos.size() > 0) {
                System.out.println("somos 1-> " + somos.get(0).getId() + " - " + somos.get(0).getTanggal());
                this.somService.createBatchStokOpnamMandiriIgnoreDuplicate(somos);
            }
            if (somosdeleted.size() > 0) {
                System.out.println("somosdeleted 1-> " + somosdeleted.get(0).getId() + " - " + somosdeleted.get(0).getTanggal());
                String ids_somosdeleted = "";
                i = 0;
                for (StokOpnamMandiri somodeleted : somosdeleted) {
                    ids_somosdeleted = ids_somosdeleted + somodeleted.getId();
                    if (i++ >= somosdeleted.size() - 1) continue;
                    ids_somosdeleted = ids_somosdeleted + ",";
                }
                this.somService.deleteBatchStokOpnamMandiri(ids_somosdeleted);
            }
            if (somdos.size() > 0) {
                System.out.println("somdos 1-> " + somdos.get(0).getId() + " - " + somdos.get(0).getBarangId());
                this.somdService.createStokOpnamMandiriDetailBatchIgnoreDuplicate(somdos);
            }
            System.out.println("somos.size() -> " + somos.size());
            System.out.println("somdos.size() -> " + somdos.size());
        }
        catch (Exception e) {
            System.out.println("Message: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            return 0;
        }
        return 1;
    }

    public void uploadSOM(int id) {
        StokOpnamMandiri som = this.somDao.getStokOpnamMandiri(id);
        List<StokOpnamMandiriDetail> somds = this.somdDao.getStokOpnamMandiriDetailByIdSOM(id);
        int resultSom = this.updateStokOpnamMandiriOnline(som);
        int[] resultSomd = this.updateStokOpnamMandiriDetailsOnline(somds);
    }

    private void uploadBatchJSONPassWeek() {
        List<StokOpnamMandiri> soms = this.somDao.getStokOpnamMandiriPastWeek();
        for (StokOpnamMandiri som : soms) {
            System.out.println("Uploading ID SOM -> " + som.getId());
            this.uploadSOM(som.getId());
        }
    }

    private List<StokOpnamMandiriDetail> getStokOpnamMandiriDetailPastSixMonth() {
        return this.somdDao.getStokOpnamMandiriDetailPastSixMonth();
    }

    private List<StokOpnamMandiri> getStokOpnamMandiriPastSixMonth() {
        return this.somDao.getStokOpnamMandiriPastSixMonth();
    }

    private List<StokOpnamMandiri> getStokOpnamMandiriOnline(String ids) {
        this.ensureOnlineConnection();
        return this.somoDao.getStokOpnamMandiriOnline(ids);
    }

    private List<StokOpnamMandiri> getStokOpnamMandiriOnlineDeleted(String ids) {
        this.ensureOnlineConnection();
        return this.somoDao.getStokOpnamMandiriOnlineDeleted(ids);
    }

    private List<StokOpnamMandiriDetail> getStokOpnamMandiriDetailOnline(String ids) {
        this.ensureOnlineConnection();
        return this.somoDao.getStokOpnamMandiriDetailOnline(ids);
    }

    private int updateStokOpnamMandiriOnline(StokOpnamMandiri som) {
        this.ensureOnlineConnection();
        return this.somoDao.updateStokOpnamMandiri(som);
    }

    private int[] updateStokOpnamMandiriDetailsOnline(List<StokOpnamMandiriDetail> somds) {
        this.ensureOnlineConnection();
        return this.somoDao.createBatchStokOpnamMandiriDetailDuplicateUpdate(somds);
    }
}

