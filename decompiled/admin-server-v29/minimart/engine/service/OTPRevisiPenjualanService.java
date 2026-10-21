/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.OTPRevisiPenjualanDao;
import minimart.engine.dao.OTPRevisiPenjualanDetailDao;
import minimart.engine.daoimpl.OTPRevisiPenjualanDaoImpl;
import minimart.engine.daoimpl.OTPRevisiPenjualanDetailDaoImpl;
import minimart.engine.model.OTPRevisiPenjualan;
import minimart.engine.model.OTPRevisiPenjualanDetail;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.service.KonstantaService;

public class OTPRevisiPenjualanService {
    private OTPRevisiPenjualanDao otpRevisiPenjualanDao = new OTPRevisiPenjualanDaoImpl();
    private OTPRevisiPenjualanDetailDao otpRevisiPenjualanDetailDao = new OTPRevisiPenjualanDetailDaoImpl();
    private KonstantaService konstantaService = new KonstantaService();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public SnapshotRequest createSnapshotRequest(String penjualanId, String customerId, String karyawanId, long totalBefore, long totalAfter, List<PenjualanDetail> beforeDetails, List<PenjualanDetail> afterDetails) {
        SnapshotRequest snapshotRequest = new SnapshotRequest();
        OTPRevisiPenjualan header = new OTPRevisiPenjualan();
        header.setT_penjualan_id(penjualanId);
        header.setM_customer_id(customerId);
        header.setM_karyawan_id(karyawanId);
        header.setId_user(null);
        header.setTotal_before(totalBefore);
        header.setTotal_after(totalAfter);
        snapshotRequest.setOtpRevisiPenjualan(header);
        snapshotRequest.setOtpRevisiPenjualanDetails(this.buildSnapshotDetails(beforeDetails, afterDetails));
        return snapshotRequest;
    }

    public List<OTPRevisiPenjualanDetail> buildSnapshotDetails(List<PenjualanDetail> beforeDetails, List<PenjualanDetail> afterDetails) {
        OTPRevisiPenjualanDetail detail;
        LinkedHashMap<String, PenjualanDetail> beforeMap = this.toPenjualanDetailMap(beforeDetails);
        LinkedHashMap<String, PenjualanDetail> afterMap = this.toPenjualanDetailMap(afterDetails);
        LinkedHashMap<String, OTPRevisiPenjualanDetail> resultMap = new LinkedHashMap<String, OTPRevisiPenjualanDetail>();
        for (String idSku : beforeMap.keySet()) {
            detail = this.createBaseSnapshotDetail(idSku);
            PenjualanDetail beforeDetail = beforeMap.get(idSku);
            if (beforeDetail != null) {
                this.fillBefore(detail, beforeDetail);
            }
            resultMap.put(idSku, detail);
        }
        for (String idSku : afterMap.keySet()) {
            PenjualanDetail afterDetail;
            detail = (OTPRevisiPenjualanDetail)resultMap.get(idSku);
            if (detail == null) {
                detail = this.createBaseSnapshotDetail(idSku);
                resultMap.put(idSku, detail);
            }
            if ((afterDetail = afterMap.get(idSku)) == null) continue;
            this.fillAfter(detail, afterDetail);
        }
        return new ArrayList<OTPRevisiPenjualanDetail>(resultMap.values());
    }

    private LinkedHashMap<String, PenjualanDetail> toPenjualanDetailMap(List<PenjualanDetail> details) {
        LinkedHashMap<String, PenjualanDetail> map = new LinkedHashMap<String, PenjualanDetail>();
        if (details != null) {
            for (PenjualanDetail detail : details) {
                if (detail == null || detail.getBarangId() == null) continue;
                map.put(detail.getBarangId(), detail);
            }
        }
        return map;
    }

    private OTPRevisiPenjualanDetail createBaseSnapshotDetail(String idSku) {
        OTPRevisiPenjualanDetail detail = new OTPRevisiPenjualanDetail();
        detail.setId_sku(idSku);
        detail.setQty_before(0.0f);
        detail.setSatuan_before("");
        detail.setHarga_before(0L);
        detail.setDiscount_before(0L);
        detail.setTotal_before(0L);
        detail.setQty_after(0.0f);
        detail.setSatuan_after("");
        detail.setHarga_after(0L);
        detail.setDiscount_after(0L);
        detail.setTotal_after(0L);
        return detail;
    }

    private void fillBefore(OTPRevisiPenjualanDetail snapshotDetail, PenjualanDetail beforeDetail) {
        snapshotDetail.setQty_before(beforeDetail.getJumlahBarang());
        snapshotDetail.setSatuan_before(beforeDetail.getSatuan() == null ? "" : beforeDetail.getSatuan());
        snapshotDetail.setHarga_before(beforeDetail.getHargaJual());
        snapshotDetail.setDiscount_before(beforeDetail.getDiscount2());
        snapshotDetail.setTotal_before(beforeDetail.getTotalHarga());
    }

    private void fillAfter(OTPRevisiPenjualanDetail snapshotDetail, PenjualanDetail afterDetail) {
        snapshotDetail.setQty_after(afterDetail.getJumlahBarang());
        snapshotDetail.setSatuan_after(afterDetail.getSatuan() == null ? "" : afterDetail.getSatuan());
        snapshotDetail.setHarga_after(afterDetail.getHargaJual());
        snapshotDetail.setDiscount_after(afterDetail.getDiscount2());
        snapshotDetail.setTotal_after(afterDetail.getTotalHarga());
    }

    public int requestOTPRevisiPenjualan(OTPRevisiPenjualan otpRevisiPenjualan, List<OTPRevisiPenjualanDetail> otpRevisiPenjualanDetails) {
        this.ensureOnlineConnection();
        int result = 0;
        int idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
        otpRevisiPenjualan.setId_cabang(idCabang);
        result = this.otpRevisiPenjualanDao.requestOTPRevisiPenjualan(otpRevisiPenjualan);
        if (result > 0) {
            ArrayList<OTPRevisiPenjualanDetail> detailsToInsert = new ArrayList<OTPRevisiPenjualanDetail>();
            for (OTPRevisiPenjualanDetail detail : otpRevisiPenjualanDetails) {
                detail.setT_otp_revisi_penjualan_id(result);
                detail.setId_cabang(idCabang);
                detailsToInsert.add(detail);
            }
            this.otpRevisiPenjualanDetailDao.insertOTPRevisiPenjualanDetail(detailsToInsert);
        }
        return result;
    }

    public OTPRevisiPenjualan getOTPRevisiPenjualanByUniqueCode(String uniqueCode) {
        this.ensureOnlineConnection();
        return this.otpRevisiPenjualanDao.getOTPRevisiPenjualanByUniqueCode(uniqueCode);
    }

    public List<OTPRevisiPenjualanDetail> getOTPRevisiPenjualanDetailById(int idOtpRevisiPenjualan) {
        this.ensureOnlineConnection();
        return this.otpRevisiPenjualanDetailDao.getOTPRevisiPenjualanDetailById(idOtpRevisiPenjualan);
    }

    public int updateStatusUsedOTPRevisiPenjualan(int idOtpRevisiPenjualan) {
        this.ensureOnlineConnection();
        return this.otpRevisiPenjualanDao.updateStatusUsedOTPRevisiPenjualan(idOtpRevisiPenjualan);
    }

    public ValidationResult validateOTPRevisiPenjualan(String uniqueCode, String penjualanId, String customerId, long totalBefore, long totalAfter, List<OTPRevisiPenjualanDetail> currentSnapshotDetails) {
        this.ensureOnlineConnection();
        ValidationResult validationResult = new ValidationResult();
        if (uniqueCode == null || uniqueCode.trim().isEmpty()) {
            validationResult.setMessage("Harap isi OTP");
            return validationResult;
        }
        OTPRevisiPenjualan otp = this.getOTPRevisiPenjualanByUniqueCode(uniqueCode.trim());
        if (otp == null) {
            validationResult.setMessage("OTP tidak ditemukan / tidak valid");
            return validationResult;
        }
        validationResult.setOtpRevisiPenjualan(otp);
        int idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
        if (otp.isUsed()) {
            validationResult.setMessage("OTP sudah pernah digunakan");
            return validationResult;
        }
        if (!otp.isApproved()) {
            validationResult.setMessage("OTP belum di-approve");
            return validationResult;
        }
        if (!otp.isSameDayValid()) {
            validationResult.setMessage("OTP sudah melewati batas hari request");
            return validationResult;
        }
        if (otp.getId_cabang() != idCabang) {
            validationResult.setMessage("OTP bukan untuk cabang ini");
            return validationResult;
        }
        if (!penjualanId.equals(otp.getT_penjualan_id())) {
            validationResult.setMessage("OTP tidak dapat digunakan untuk ID Penjualan " + penjualanId);
            return validationResult;
        }
        if (!customerId.equals(otp.getM_customer_id())) {
            validationResult.setMessage("OTP tidak dapat digunakan untuk ID Customer " + customerId);
            return validationResult;
        }
        if (otp.getTotal_before() != totalBefore) {
            validationResult.setMessage("Total sebelum revisi tidak sesuai dengan OTP, harap request OTP baru");
            return validationResult;
        }
        if (otp.getTotal_after() != totalAfter) {
            validationResult.setMessage("Total setelah revisi tidak sesuai dengan OTP, harap request OTP baru");
            return validationResult;
        }
        List<OTPRevisiPenjualanDetail> storedDetails = this.getOTPRevisiPenjualanDetailById(otp.getId());
        if (storedDetails == null || storedDetails.isEmpty()) {
            validationResult.setMessage("Tidak ada detail barang pada OTP ini");
            return validationResult;
        }
        HashMap<String, OTPRevisiPenjualanDetail> storedMap = new HashMap<String, OTPRevisiPenjualanDetail>();
        for (OTPRevisiPenjualanDetail detail : storedDetails) {
            storedMap.put(detail.getId_sku(), detail);
        }
        if (storedMap.size() != currentSnapshotDetails.size()) {
            validationResult.setMessage("Detail barang OTP tidak sesuai, harap request OTP baru");
            return validationResult;
        }
        for (OTPRevisiPenjualanDetail currentDetail : currentSnapshotDetails) {
            OTPRevisiPenjualanDetail storedDetail = (OTPRevisiPenjualanDetail)storedMap.get(currentDetail.getId_sku());
            if (storedDetail == null) {
                validationResult.setMessage("Barang dengan ID " + currentDetail.getId_sku() + " tidak terdaftar pada OTP, harap request OTP baru");
                return validationResult;
            }
            if (this.isSameSnapshotDetail(storedDetail, currentDetail)) continue;
            validationResult.setMessage("Snapshot barang dengan ID " + currentDetail.getId_sku() + " tidak sesuai, harap request OTP baru");
            return validationResult;
        }
        validationResult.setValid(true);
        return validationResult;
    }

    private boolean isSameSnapshotDetail(OTPRevisiPenjualanDetail storedDetail, OTPRevisiPenjualanDetail currentDetail) {
        return Float.compare(storedDetail.getQty_before(), currentDetail.getQty_before()) == 0 && this.equalsString(storedDetail.getSatuan_before(), currentDetail.getSatuan_before()) && storedDetail.getHarga_before() == currentDetail.getHarga_before() && storedDetail.getDiscount_before() == currentDetail.getDiscount_before() && storedDetail.getTotal_before() == currentDetail.getTotal_before() && Float.compare(storedDetail.getQty_after(), currentDetail.getQty_after()) == 0 && this.equalsString(storedDetail.getSatuan_after(), currentDetail.getSatuan_after()) && storedDetail.getHarga_after() == currentDetail.getHarga_after() && storedDetail.getDiscount_after() == currentDetail.getDiscount_after() && storedDetail.getTotal_after() == currentDetail.getTotal_after();
    }

    private boolean equalsString(String left, String right) {
        String leftValue = left == null ? "" : left;
        String rightValue = right == null ? "" : right;
        return leftValue.equals(rightValue);
    }

    public static class ValidationResult {
        private boolean valid;
        private String message;
        private OTPRevisiPenjualan otpRevisiPenjualan;

        public boolean isValid() {
            return this.valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public OTPRevisiPenjualan getOtpRevisiPenjualan() {
            return this.otpRevisiPenjualan;
        }

        public void setOtpRevisiPenjualan(OTPRevisiPenjualan otpRevisiPenjualan) {
            this.otpRevisiPenjualan = otpRevisiPenjualan;
        }
    }

    public static class SnapshotRequest {
        private OTPRevisiPenjualan otpRevisiPenjualan;
        private List<OTPRevisiPenjualanDetail> otpRevisiPenjualanDetails;

        public OTPRevisiPenjualan getOtpRevisiPenjualan() {
            return this.otpRevisiPenjualan;
        }

        public void setOtpRevisiPenjualan(OTPRevisiPenjualan otpRevisiPenjualan) {
            this.otpRevisiPenjualan = otpRevisiPenjualan;
        }

        public List<OTPRevisiPenjualanDetail> getOtpRevisiPenjualanDetails() {
            return this.otpRevisiPenjualanDetails;
        }

        public void setOtpRevisiPenjualanDetails(List<OTPRevisiPenjualanDetail> otpRevisiPenjualanDetails) {
            this.otpRevisiPenjualanDetails = otpRevisiPenjualanDetails;
        }
    }
}

