/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import minimart.engine.controller.Helper;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.PromoHematDao;
import minimart.engine.dao.PromoHematDetailDao;
import minimart.engine.dao.PromoHematOnlineDao;
import minimart.engine.dao.PromoTebusDao;
import minimart.engine.dao.PromoTebusDetailDao;
import minimart.engine.dao.PromoTebusMurahDao;
import minimart.engine.dao.PromoTebusMurahDetailDao;
import minimart.engine.dao.PromoTebusMurahOnlineDao;
import minimart.engine.dao.PromoTebusOnlineDao;
import minimart.engine.daoimpl.PromoHematDaoImpl;
import minimart.engine.daoimpl.PromoHematDetailDaoImpl;
import minimart.engine.daoimpl.PromoHematOnlineDaoImpl;
import minimart.engine.daoimpl.PromoTebusDaoImpl;
import minimart.engine.daoimpl.PromoTebusDetailDaoImpl;
import minimart.engine.daoimpl.PromoTebusMurahDaoImpl;
import minimart.engine.daoimpl.PromoTebusMurahDetailDaoImpl;
import minimart.engine.daoimpl.PromoTebusMurahOnlineDaoImpl;
import minimart.engine.daoimpl.PromoTebusOnlineDaoImpl;
import minimart.engine.model.PromoHemat;
import minimart.engine.model.PromoHematDetail;
import minimart.engine.model.PromoTebus;
import minimart.engine.model.PromoTebusDetail;
import minimart.engine.model.PromoTebusMurah;
import minimart.engine.model.PromoTebusMurahDetail;
import minimart.engine.service.KonstantaService;

public class SyncPromoService {
    PromoHematDao promoHematDao = new PromoHematDaoImpl();
    PromoHematDetailDao promoHematDetailDao = new PromoHematDetailDaoImpl();
    PromoHematOnlineDao promoHematOnlineDao = new PromoHematOnlineDaoImpl();
    PromoTebusDao promoTebusDao = new PromoTebusDaoImpl();
    PromoTebusDetailDao promoTebusDetailDao = new PromoTebusDetailDaoImpl();
    PromoTebusOnlineDao promoTebusOnlineDao = new PromoTebusOnlineDaoImpl();
    PromoTebusMurahDao promoTebusMurahDao = new PromoTebusMurahDaoImpl();
    PromoTebusMurahDetailDao promoTebusMurahDetailDao = new PromoTebusMurahDetailDaoImpl();
    PromoTebusMurahOnlineDao promoTebusMurahOnlineDao = new PromoTebusMurahOnlineDaoImpl();
    KonstantaService ks = new KonstantaService();
    Helper helper = Helper.getInstance();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int syncPromoHemat() {
        this.ensureOnlineConnection();
        int result = 0;
        try {
            String idCabang = this.ks.getConstantValue("idCabang");
            List<PromoHemat> promoHematOnlines = this.promoHematOnlineDao.getPromoHematOnline(idCabang);
            List<PromoHemat> promoHemats = this.promoHematDao.getPromoHematAll();
            StringBuilder idPromoHematDelete = new StringBuilder();
            StringBuilder idPromoHematOnlines = new StringBuilder();
            StringBuilder idPromoHematDetailDeleted = new StringBuilder();
            HashMap<String, PromoHemat> promoHematOnlineMap = new HashMap<String, PromoHemat>();
            HashMap<String, PromoHemat> promoHematMap = new HashMap<String, PromoHemat>();
            HashMap<Integer, PromoHematDetail> promoHematDetailOnlineMap = new HashMap<Integer, PromoHematDetail>();
            HashMap<Integer, PromoHematDetail> promoHematDetailMap = new HashMap<Integer, PromoHematDetail>();
            for (PromoHemat promoHematOnline : promoHematOnlines) {
                System.out.println(promoHematOnline.getKaryawanId());
                promoHematOnlineMap.put(promoHematOnline.getId(), promoHematOnline);
                if (idPromoHematOnlines.length() > 0) {
                    idPromoHematOnlines.append(", '").append(promoHematOnline.getId()).append("'");
                    continue;
                }
                idPromoHematOnlines.append("'").append(promoHematOnline.getId()).append("'");
            }
            System.out.println(idPromoHematOnlines.toString());
            for (PromoHemat promoHemat : promoHemats) {
                promoHematMap.put(promoHemat.getId(), promoHemat);
                PromoHemat promoHematDelete = (PromoHemat)promoHematOnlineMap.get(promoHemat.getId());
                if (promoHematDelete != null) continue;
                if (idPromoHematDelete.length() > 0) {
                    idPromoHematDelete.append(", '").append(promoHemat.getId()).append("'");
                    continue;
                }
                idPromoHematDelete.append("'").append(promoHemat.getId()).append("'");
            }
            System.out.println(idPromoHematDelete.toString());
            List<PromoHematDetail> promoHematDetailOnlines = this.promoHematOnlineDao.getPromoHematDetailOnlineByPromoHematId(idPromoHematOnlines.toString());
            List<PromoHematDetail> promoHematDetails = this.promoHematDetailDao.getPromoHematDetailAll();
            for (PromoHematDetail promoHematDetailOnline : promoHematDetailOnlines) {
                promoHematDetailOnlineMap.put(promoHematDetailOnline.getId(), promoHematDetailOnline);
                System.out.println(promoHematDetailOnline.getId());
            }
            for (PromoHematDetail promoHematDetail : promoHematDetails) {
                promoHematDetailMap.put(promoHematDetail.getId(), promoHematDetail);
                PromoHematDetail promoHematDetailDeleted = (PromoHematDetail)promoHematDetailOnlineMap.get(promoHematDetail.getId());
                if (promoHematDetailDeleted != null) continue;
                if (idPromoHematDetailDeleted.length() > 0) {
                    idPromoHematDetailDeleted.append(", ").append(promoHematDetail.getId());
                    continue;
                }
                idPromoHematDetailDeleted.append(promoHematDetail.getId());
            }
            System.out.println(idPromoHematDetailDeleted);
            int[] result1 = this.promoHematDao.upsertPromoHematBatch(promoHematOnlines);
            int[] result2 = this.promoHematDetailDao.upsertPromoHematDetailBatch(promoHematDetailOnlines);
            int result3 = 0;
            if (idPromoHematDelete.length() > 0) {
                result3 = this.promoHematDao.deletePromoHematByIds(idPromoHematDelete.toString());
            }
            int result4 = 0;
            if (idPromoHematDetailDeleted.length() > 0) {
                result4 = this.promoHematDetailDao.deletePromoHematDetailByIds(idPromoHematDetailDeleted.toString());
            }
            System.out.println(result1.toString());
            System.out.println(result2.toString());
            System.out.println(result3);
            System.out.println(result4);
            result = 1;
        }
        catch (Exception e) {
            result = 0;
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int syncPromoTebus() {
        this.ensureOnlineConnection();
        int result = 0;
        try {
            String idCabang = this.ks.getConstantValue("idCabang");
            List<PromoTebus> promoTebusOnlines = this.promoTebusOnlineDao.getPromoTebusOnline(idCabang);
            List<PromoTebus> promoTebuss = this.promoTebusDao.getPromoTebusAll();
            StringBuilder idPromoTebusDelete = new StringBuilder();
            StringBuilder idPromoTebusOnlines = new StringBuilder();
            StringBuilder idPromoTebusDetailDeleted = new StringBuilder();
            HashMap<String, PromoTebus> promoTebusOnlineMap = new HashMap<String, PromoTebus>();
            HashMap<String, PromoTebus> promoTebusMap = new HashMap<String, PromoTebus>();
            HashMap<Integer, PromoTebusDetail> promoTebusDetailOnlineMap = new HashMap<Integer, PromoTebusDetail>();
            HashMap<Integer, PromoTebusDetail> promoTebusDetailMap = new HashMap<Integer, PromoTebusDetail>();
            for (PromoTebus promoTebusOnline : promoTebusOnlines) {
                promoTebusOnlineMap.put(promoTebusOnline.getId(), promoTebusOnline);
                if (idPromoTebusOnlines.length() > 0) {
                    idPromoTebusOnlines.append(", '").append(promoTebusOnline.getId()).append("'");
                    continue;
                }
                idPromoTebusOnlines.append("'").append(promoTebusOnline.getId()).append("'");
            }
            System.out.println(idPromoTebusOnlines.toString());
            for (PromoTebus promoTebus : promoTebuss) {
                promoTebusMap.put(promoTebus.getId(), promoTebus);
                PromoTebus promoTebusDelete = (PromoTebus)promoTebusOnlineMap.get(promoTebus.getId());
                if (promoTebusDelete != null) continue;
                if (idPromoTebusDelete.length() > 0) {
                    idPromoTebusDelete.append(", '").append(promoTebus.getId()).append("'");
                    continue;
                }
                idPromoTebusDelete.append("'").append(promoTebus.getId()).append("'");
            }
            System.out.println(idPromoTebusDelete.toString());
            List<PromoTebusDetail> promoTebusDetailOnlines = this.promoTebusOnlineDao.getPromoTebusDetailOnlineByPromoTebusId(idPromoTebusOnlines.toString());
            List<PromoTebusDetail> promoTebusDetails = this.promoTebusDetailDao.getPromoTebusDetailAll();
            for (PromoTebusDetail promoTebusDetailOnline : promoTebusDetailOnlines) {
                promoTebusDetailOnlineMap.put(promoTebusDetailOnline.getId(), promoTebusDetailOnline);
                System.out.println(promoTebusDetailOnline.getId());
            }
            for (PromoTebusDetail promoTebusDetail : promoTebusDetails) {
                promoTebusDetailMap.put(promoTebusDetail.getId(), promoTebusDetail);
                PromoTebusDetail promoTebusDetailDeleted = (PromoTebusDetail)promoTebusDetailOnlineMap.get(promoTebusDetail.getId());
                if (promoTebusDetailDeleted != null) continue;
                if (idPromoTebusDetailDeleted.length() > 0) {
                    idPromoTebusDetailDeleted.append(", ").append(promoTebusDetail.getId());
                    continue;
                }
                idPromoTebusDetailDeleted.append(promoTebusDetail.getId());
            }
            System.out.println(idPromoTebusDetailDeleted);
            int[] result1 = this.promoTebusDao.upsertPromoTebusBatch(promoTebusOnlines);
            int[] result2 = this.promoTebusDetailDao.upsertPromoTebusDetailBatch(promoTebusDetailOnlines);
            int result3 = 0;
            if (idPromoTebusDelete.length() > 0) {
                result3 = this.promoTebusDao.deletePromoTebusByIds(idPromoTebusDelete.toString());
            }
            int result4 = 0;
            if (idPromoTebusDetailDeleted.length() > 0) {
                result4 = this.promoTebusDetailDao.deletePromoTebusDetailByIds(idPromoTebusDetailDeleted.toString());
            }
            System.out.println(result1.toString());
            System.out.println(result2.toString());
            System.out.println(result3);
            System.out.println(result4);
            result = 1;
        }
        catch (Exception e) {
            result = 0;
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int syncPromoTebusMurah() {
        this.ensureOnlineConnection();
        int result = 0;
        try {
            String idCabang = this.ks.getConstantValue("idCabang");
            List<PromoTebusMurah> promoTebusMurahOnlines = this.promoTebusMurahOnlineDao.getPromoTebusMurahOnline(idCabang);
            List<PromoTebusMurah> promoTebusMurahs = this.promoTebusMurahDao.getPromoTebusMurahAll();
            StringBuilder idPromoTebusMurahDelete = new StringBuilder();
            StringBuilder idPromoTebusMurahOnlines = new StringBuilder();
            StringBuilder idPromoTebusMurahDetailDeleted = new StringBuilder();
            HashMap<String, PromoTebusMurah> promoTebusMurahOnlineMap = new HashMap<String, PromoTebusMurah>();
            HashMap<String, PromoTebusMurah> promoTebusMurahMap = new HashMap<String, PromoTebusMurah>();
            HashMap<Integer, PromoTebusMurahDetail> promoTebusMurahDetailOnlineMap = new HashMap<Integer, PromoTebusMurahDetail>();
            HashMap<Integer, PromoTebusMurahDetail> promoTebusMurahDetailMap = new HashMap<Integer, PromoTebusMurahDetail>();
            for (PromoTebusMurah promoTebusMurahOnline : promoTebusMurahOnlines) {
                System.out.println(promoTebusMurahOnline.getKaryawanId());
                promoTebusMurahOnlineMap.put(promoTebusMurahOnline.getId(), promoTebusMurahOnline);
                if (idPromoTebusMurahOnlines.length() > 0) {
                    idPromoTebusMurahOnlines.append(", '").append(promoTebusMurahOnline.getId()).append("'");
                    continue;
                }
                idPromoTebusMurahOnlines.append("'").append(promoTebusMurahOnline.getId()).append("'");
            }
            System.out.println(idPromoTebusMurahOnlines.toString());
            for (PromoTebusMurah promoTebusMurah : promoTebusMurahs) {
                promoTebusMurahMap.put(promoTebusMurah.getId(), promoTebusMurah);
                PromoTebusMurah promoTebusMurahDelete = (PromoTebusMurah)promoTebusMurahOnlineMap.get(promoTebusMurah.getId());
                if (promoTebusMurahDelete != null) continue;
                if (idPromoTebusMurahDelete.length() > 0) {
                    idPromoTebusMurahDelete.append(", '").append(promoTebusMurah.getId()).append("'");
                    continue;
                }
                idPromoTebusMurahDelete.append("'").append(promoTebusMurah.getId()).append("'");
            }
            System.out.println(idPromoTebusMurahDelete.toString());
            List<PromoTebusMurahDetail> promoTebusMurahDetailOnlines = this.promoTebusMurahOnlineDao.getPromoTebusMurahDetailOnlineByPromoTebusMurahId(idPromoTebusMurahOnlines.toString());
            List<PromoTebusMurahDetail> promoTebusMurahDetails = this.promoTebusMurahDetailDao.getPromoTebusMurahDetailAll();
            for (PromoTebusMurahDetail promoTebusMurahDetailOnline : promoTebusMurahDetailOnlines) {
                promoTebusMurahDetailOnlineMap.put(promoTebusMurahDetailOnline.getId(), promoTebusMurahDetailOnline);
                System.out.println(promoTebusMurahDetailOnline.getId());
            }
            for (PromoTebusMurahDetail promoTebusMurahDetail : promoTebusMurahDetails) {
                promoTebusMurahDetailMap.put(promoTebusMurahDetail.getId(), promoTebusMurahDetail);
                PromoTebusMurahDetail promoTebusMurahDetailDeleted = (PromoTebusMurahDetail)promoTebusMurahDetailOnlineMap.get(promoTebusMurahDetail.getId());
                if (promoTebusMurahDetailDeleted != null) continue;
                if (idPromoTebusMurahDetailDeleted.length() > 0) {
                    idPromoTebusMurahDetailDeleted.append(", ").append(promoTebusMurahDetail.getId());
                    continue;
                }
                idPromoTebusMurahDetailDeleted.append(promoTebusMurahDetail.getId());
            }
            System.out.println(idPromoTebusMurahDetailDeleted);
            int[] result1 = this.promoTebusMurahDao.upsertPromoTebusMurahBatch(promoTebusMurahOnlines);
            int[] result2 = this.promoTebusMurahDetailDao.upsertPromoTebusMurahDetailBatch(promoTebusMurahDetailOnlines);
            int result3 = 0;
            if (idPromoTebusMurahDelete.length() > 0) {
                result3 = this.promoTebusMurahDao.deletePromoTebusMurahByIds(idPromoTebusMurahDelete.toString());
            }
            int result4 = 0;
            if (idPromoTebusMurahDetailDeleted.length() > 0) {
                result4 = this.promoTebusMurahDetailDao.deletePromoTebusMurahDetailByIds(idPromoTebusMurahDetailDeleted.toString());
            }
            System.out.println(result1.toString());
            System.out.println(result2.toString());
            System.out.println(result3);
            System.out.println(result4);
            result = 1;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            result = 0;
        }
        return result;
    }

    public int syncAllPromo() {
        int result = 0;
        int resultHemat = this.syncPromoHemat();
        int resultTebus = this.syncPromoTebus();
        int resultTebusMurah = this.syncPromoTebusMurah();
        System.out.println("Status Hemat: " + resultHemat);
        System.out.println("Status Tebus: " + resultTebus);
        System.out.println("Status Tebus Murah: " + resultTebusMurah);
        if (resultHemat == 1 && resultTebus == 1 && resultTebusMurah == 1) {
            result = 1;
            JOptionPane.showMessageDialog(null, "Data Promo Berhasil di Sinkronisasi");
        } else {
            result = 0;
            JOptionPane.showMessageDialog(null, "Ada Promo yang gagal di sinkronisasi");
        }
        return result;
    }
}

