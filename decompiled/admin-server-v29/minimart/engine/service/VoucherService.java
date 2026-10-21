/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.VoucherDao;
import minimart.engine.daoimpl.VoucherDaoImpl;
import minimart.engine.model.Voucher;

public class VoucherService {
    private VoucherDao voucherDao = new VoucherDaoImpl();

    public int createVoucher(Voucher voucher) {
        return this.voucherDao.createVoucher(voucher);
    }

    public Voucher getVoucher(String id) {
        return this.voucherDao.getVoucher(id);
    }

    public int updateVoucher(Voucher voucher) {
        return this.voucherDao.updateVoucher(voucher);
    }

    public String getLastId() {
        return this.voucherDao.getLastId();
    }

    public List<Voucher> getVoucher(String startDate, String endDate) {
        return this.voucherDao.getVoucher(startDate, endDate);
    }
}

