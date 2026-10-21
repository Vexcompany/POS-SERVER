/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.ArrayList;
import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.OTPPengembalianDao;
import minimart.engine.dao.OTPPengembalianDetailDao;
import minimart.engine.daoimpl.OTPPengembalianDaoImpl;
import minimart.engine.daoimpl.OTPPengembalianDetailDaoImpl;
import minimart.engine.model.OTPPengembalian;
import minimart.engine.model.OTPPengembalianDetail;
import minimart.engine.service.KonstantaService;

public class OTPPengembalianService {
    private OTPPengembalianDao otpPengembalianDao = new OTPPengembalianDaoImpl();
    private OTPPengembalianDetailDao otpPengembalianDetailDao = new OTPPengembalianDetailDaoImpl();
    private KonstantaService ks = new KonstantaService();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int requestOTPPengembalian(OTPPengembalian otpPengembalian, List<OTPPengembalianDetail> otpPengembalianDetails) {
        this.ensureOnlineConnection();
        int result1 = 0;
        int id_cabang = Integer.parseInt(this.ks.getConstantValue("idCabang"));
        otpPengembalian.setId_cabang(id_cabang);
        result1 = this.otpPengembalianDao.requestOTPPengembalian(otpPengembalian);
        if (result1 > 0) {
            ArrayList<OTPPengembalianDetail> opdsnew = new ArrayList<OTPPengembalianDetail>();
            for (OTPPengembalianDetail opd : otpPengembalianDetails) {
                opd.setT_otp_pengembalian_id(result1);
                opd.setId_cabang(id_cabang);
                opdsnew.add(opd);
                System.out.println(opd.getT_otp_pengembalian_id() + " - " + opd.getId_sku());
            }
            System.out.println("ID OTP Pengembalian Header : " + result1);
            int[] nArray = this.otpPengembalianDetailDao.insertOTPPengembalianDetail(opdsnew);
        }
        return result1;
    }

    public OTPPengembalian getOTPPengembalianByUniqueCode(String uc) {
        this.ensureOnlineConnection();
        return this.otpPengembalianDao.getOtpPengembalianByUniqueCode(uc);
    }

    public int updateStatusUsedOTPPengembalian(int idOtpPengembalian) {
        this.ensureOnlineConnection();
        return this.otpPengembalianDao.updateStatusUsedOTPPengembalian(idOtpPengembalian);
    }

    public List<OTPPengembalianDetail> getOTPPengembalianDetailById(int idOtpPengembalian) {
        this.ensureOnlineConnection();
        return this.otpPengembalianDetailDao.getOTPPengembalianDetailById(idOtpPengembalian);
    }
}

