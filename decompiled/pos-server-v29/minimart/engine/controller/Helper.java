/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.controller;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import minimart.engine.model.Barang;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Pembelian;
import minimart.engine.model.PembelianDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;

public class Helper {
    public static Helper instance = new Helper();
    private Karyawan karyawan = null;
    private int kasir = 0;
    private boolean isServer = true;
    private String statusProgram = "";
    private String jenisProgram = "";
    private BarangService barangService = new BarangService();
    private PembelianService pembelianService = new PembelianService();
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();

    public String[] getStartEndDate(Date nowDate) {
        String startDate;
        String yearString;
        String[] result = new String[2];
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        String dayString = day.format(nowDate);
        String monthString = month.format(nowDate);
        String startYearString = yearString = year.format(nowDate);
        int jumlahHari = 0;
        if (Integer.parseInt(monthString) - 1 == 0) {
            jumlahHari = 31;
        } else if (Integer.parseInt(monthString) - 1 == 1) {
            jumlahHari = 31;
        } else if (Integer.parseInt(monthString) - 1 == 2) {
            jumlahHari = 28;
        } else if (Integer.parseInt(monthString) - 1 == 3) {
            jumlahHari = 31;
        } else if (Integer.parseInt(monthString) - 1 == 4) {
            jumlahHari = 30;
        } else if (Integer.parseInt(monthString) - 1 == 5) {
            jumlahHari = 31;
        } else if (Integer.parseInt(monthString) - 1 == 6) {
            jumlahHari = 30;
        } else if (Integer.parseInt(monthString) - 1 == 7) {
            jumlahHari = 31;
        } else if (Integer.parseInt(monthString) - 1 == 8) {
            jumlahHari = 31;
        } else if (Integer.parseInt(monthString) - 1 == 9) {
            jumlahHari = 30;
        } else if (Integer.parseInt(monthString) - 1 == 10) {
            jumlahHari = 31;
        } else if (Integer.parseInt(monthString) - 1 == 11) {
            jumlahHari = 30;
        } else if (Integer.parseInt(monthString) - 1 == 12) {
            jumlahHari = 31;
        }
        int startTanggal = Integer.parseInt(dayString) - 30;
        int endTanggal = Integer.parseInt(dayString) - 1;
        String endTanggalString = endTanggal + "";
        startTanggal = jumlahHari + startTanggal;
        String startTanggalString = startTanggal + "";
        int endBulan = Integer.parseInt(monthString);
        int startBulan = Integer.parseInt(monthString);
        if (endTanggal == 0) {
            endTanggal = jumlahHari;
            endTanggalString = endTanggal + "";
            --endBulan;
        }
        String endBulanString = endBulan + "";
        if (endBulan < 10) {
            endBulanString = "0" + endBulanString;
        }
        if (startTanggal - jumlahHari <= 0 && --startBulan == 0) {
            startBulan = 12;
            startYearString = Integer.parseInt(startYearString) - 1 + "";
        }
        String startBulanString = startBulan + "";
        if (startBulan < 10) {
            startBulanString = "0" + startBulanString;
        }
        if (startTanggal < 10) {
            startTanggalString = "0" + startTanggalString;
        }
        if (endTanggal - 1 < 10) {
            endTanggalString = "0" + endTanggalString;
        }
        String endDate = yearString + "-" + endBulanString + "-" + endTanggalString;
        result[0] = startDate = startYearString + "-" + startBulanString + "-" + startTanggalString;
        result[1] = endDate;
        return result;
    }

    public String formatNumber(String number) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String format1 = fmt.format(Long.parseLong(number));
        String format2 = format1.substring(2, format1.length() - 3);
        if (format2.substring(0, 1).equals("p")) {
            format2 = format2.replace('p', '-');
        }
        return format2;
    }

    public boolean isFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public long getHargaBeliTerakhirSatuanTerkecil(String idBarang) {
        Barang barang = this.barangService.getBarang(idBarang);
        PembelianDetail pembelianDetail = this.pembelianDetailService.getPembelianTerakhir(idBarang);
        long hargaBeliTerakhir = 0L;
        if (pembelianDetail != null) {
            Pembelian pembelian = this.pembelianService.getPembelian(pembelianDetail.getPembelianId());
            long hargaBeliTemp = pembelianDetail.getTotalHarga();
            if (pembelian.getDiscount1() > 0.0f) {
                float discountValue = pembelian.getDiscount1() / 100.0f * (float)hargaBeliTemp;
                hargaBeliTemp -= (long)discountValue;
            }
            if (pembelian.getPpn() > 0.0f) {
                float ppnValue = pembelian.getPpn() / 100.0f * (float)hargaBeliTemp;
                hargaBeliTemp += (long)ppnValue;
            }
            if (pembelianDetail != null) {
                if (pembelianDetail.getSatuan().equals(barang.getSatuan3())) {
                    hargaBeliTerakhir = (long)((float)hargaBeliTemp / (pembelianDetail.getJumlahBarang() * (float)barang.getKonversi21() * (float)barang.getKonversi32()));
                } else if (pembelianDetail.getSatuan().equals(barang.getSatuan2())) {
                    hargaBeliTerakhir = (long)((float)hargaBeliTemp / (pembelianDetail.getJumlahBarang() * (float)barang.getKonversi21()));
                } else if (pembelianDetail.getSatuan().equals(barang.getSatuan1())) {
                    hargaBeliTerakhir = (long)((float)hargaBeliTemp / pembelianDetail.getJumlahBarang());
                }
            }
        } else {
            hargaBeliTerakhir = 0L;
        }
        return hargaBeliTerakhir;
    }

    public long roundPrice25(long harga) {
        long roundPrice = 0L;
        long sisa = harga % 25L;
        roundPrice = harga - sisa;
        if (sisa != 0L) {
            roundPrice += 25L;
        }
        return roundPrice;
    }

    public long roundPrice50(long harga) {
        long roundPrice = 0L;
        long sisa = harga % 50L;
        roundPrice = harga - sisa;
        if (sisa != 0L) {
            roundPrice += 50L;
        }
        return roundPrice;
    }

    public long roundPrice100(long harga) {
        long roundPrice = 0L;
        long sisa = harga % 100L;
        roundPrice = harga - sisa;
        if (sisa != 0L) {
            roundPrice += 100L;
        }
        return roundPrice;
    }

    public float round(float Rval, int Rpl) {
        float p = (float)Math.pow(10.0, Rpl);
        float tmp = Math.round(Rval *= p);
        return tmp / p;
    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public String generateCodeGeneral(String currentCode) {
        String newCode = "";
        String pattern = "yyMMdd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String code1 = "";
        String code2 = "";
        code1 = format.format(new Date());
        if (currentCode == null || currentCode.equals("") || currentCode.equals("C")) {
            newCode = code1 + "0001";
        } else {
            String[] strSplit = currentCode.split("_");
            if (strSplit.length <= 1) {
                if (currentCode.substring(0, 6).equals(code1)) {
                    code2 = currentCode.substring(6, 10);
                    String addCode = Integer.parseInt(code2) + 1 + "";
                    int temp = 4 - addCode.length();
                    for (int x = 0; x < temp; ++x) {
                        addCode = "0" + addCode;
                    }
                    code2 = addCode;
                } else {
                    code2 = "0001";
                }
                newCode = code1 + code2;
            } else {
                if (strSplit[0].substring(0, 6).equals(code1)) {
                    code2 = strSplit[0].substring(6, 10);
                    String addCode = Integer.parseInt(code2) + 1 + "";
                    int temp = 4 - addCode.length();
                    for (int x = 0; x < temp; ++x) {
                        addCode = "0" + addCode;
                    }
                    code2 = addCode;
                } else {
                    code2 = "0001";
                }
                newCode = code1 + code2;
            }
        }
        return newCode;
    }

    public String generateCodeBarang(String currentCode) {
        String newCode = "";
        String pattern = "yyyyMMdd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String code1 = "";
        String code2 = "";
        code1 = format.format(new Date());
        if (currentCode == null || currentCode.equals("")) {
            newCode = code1 + "00001";
        } else {
            if (currentCode.substring(0, 8).equals(code1)) {
                code2 = currentCode.substring(8, 13);
                String addCode = Integer.parseInt(code2) + 1 + "";
                int temp = 5 - addCode.length();
                for (int x = 0; x < temp; ++x) {
                    addCode = "0" + addCode;
                }
                code2 = addCode;
            } else {
                code2 = "00001";
            }
            newCode = code1 + code2;
        }
        return newCode;
    }

    public float getHargaJual(float margin, long hpp) {
        float hargaJual = 0.0f;
        hargaJual = (float)hpp * (100.0f + margin) / 100.0f;
        long hargaJualInt = (long)hargaJual;
        long modHpp = hargaJualInt % 100L;
        long selisih = 0L;
        if (modHpp > 0L && modHpp < 50L) {
            selisih = 50L - modHpp;
        } else if (modHpp > 50L) {
            selisih = 100L - modHpp;
        }
        return hargaJualInt += selisih;
    }

    public static Helper getInstance() {
        return instance;
    }

    public Karyawan getKaryawan() {
        return this.karyawan;
    }

    public void setKaryawan(Karyawan karyawan) {
        this.karyawan = karyawan;
    }

    public int getKasir() {
        return this.kasir;
    }

    public void setKasir(int kasir) {
        this.kasir = kasir;
    }

    public String getStatusProgram() {
        return this.statusProgram;
    }

    public void setStatusProgram(String statusProgram) {
        this.statusProgram = statusProgram;
    }

    public String getJenisProgram() {
        return this.jenisProgram;
    }

    public void setJenisProgram(String jenisProgram) {
        this.jenisProgram = jenisProgram;
    }

    public boolean isIsServer() {
        return this.isServer;
    }

    public void setIsServer(boolean isServer) {
        this.isServer = isServer;
    }
}

