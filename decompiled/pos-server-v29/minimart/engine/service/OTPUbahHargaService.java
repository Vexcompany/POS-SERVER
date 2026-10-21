/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.OTPUbahHargaDao;
import minimart.engine.daoimpl.OTPUbahHargaDaoImpl;
import minimart.engine.model.OTPUbahHarga;
import minimart.engine.service.KonstantaService;

public class OTPUbahHargaService {
    private OTPUbahHargaDao ouhDao = new OTPUbahHargaDaoImpl();
    private KonstantaService ks = new KonstantaService();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int requestOTPUbahHarga(OTPUbahHarga ouh) {
        this.ensureOnlineConnection();
        return this.ouhDao.requestOTPUbahHarga(ouh);
    }

    public OTPUbahHarga getOTPUbahHargaByUniqueCode(String uc) {
        this.ensureOnlineConnection();
        return this.ouhDao.getOTPUbahHargaByUniqueCode(uc);
    }

    public int updateUserOTPUbahHarga(long ouhId) {
        this.ensureOnlineConnection();
        return this.ouhDao.updateUsedOTPUbahHarga(ouhId);
    }
}

