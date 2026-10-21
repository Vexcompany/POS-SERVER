/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Barcode;
import minimart.engine.model.Customer;
import minimart.engine.model.Karyawan;
import minimart.engine.model.KelebihanStok;
import minimart.engine.model.OTPUbahHarga;
import minimart.engine.model.Paket;
import minimart.engine.model.Pembelian;
import minimart.engine.model.PembelianDetail;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.model.PenjualanSementara;
import minimart.engine.model.PromoDiskonByJenisDetail;
import minimart.engine.model.PromoHematDetail;
import minimart.engine.model.PromoKhususMemberDetail;
import minimart.engine.model.PromoTebusDetail;
import minimart.engine.model.PromoTebusMurahDetail;
import minimart.engine.model.PromoVoucherDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.JenisService;
import minimart.engine.service.KelebihanStokService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PaketService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanSementaraService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.PromoDiskonByJenisDetailService;
import minimart.engine.service.PromoDiskonByJenisService;
import minimart.engine.service.PromoHematDetailService;
import minimart.engine.service.PromoHematService;
import minimart.engine.service.PromoKhususMemberDetailService;
import minimart.engine.service.PromoTebusDetailService;
import minimart.engine.service.PromoTebusMurahDetailService;
import minimart.engine.service.PromoVoucherDetailService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.PenjualanDetailDialogListener;
import minimart.ui.listener.RequestOTPUbahHargaListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.RequestOTPUbahHargaDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanelBarang;

public class PenjualanDetailDialog1
extends JDialog {
    private JCheckBox beliTerakhir;
    private JFormattedTextField beliTerakhirField;
    private JLabel beliTerakhirSatuanLbl;
    private JFormattedTextField discount2Field;
    private JButton exitButton;
    private JFormattedTextField hargaSatuanField;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JSeparator jSeparator1;
    private JTextField kodeBarangField;
    private JTextField konversiField1;
    private JTextField konversiField2;
    private JLabel konversiLbl1;
    private JLabel konversiLbl2;
    private JLabel konversiLbl3;
    private JLabel konversiLbl4;
    private JLabel mixLbl;
    private JTextField namaBarangField;
    private JFormattedTextField nettoHargaField;
    private JLabel promoLbl;
    private JLabel promoLbl2;
    private JTextField quantity1;
    private JTextField quantity2;
    private JTextField quantity3;
    private JLabel satuan1;
    private JLabel satuan2;
    private JLabel satuan3;
    private JTextField satuan4;
    private JFormattedTextField totalHargaField;
    private PenjualanDetailDialogListener listener;
    private SearchDialog sd;
    private BarangService barangService = new BarangService();
    private CustomerService customerService = new CustomerService();
    private Barang barang;
    private DefaultTableModel penjualanTableModel;
    private PromoVoucherDetail promoVoucherDetail;
    private int screenHeight;
    private int screenWidth;
    private JTextField tempField;
    private Helper helper = Helper.getInstance();
    private JTextField tempField2;
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private PenjualanService penjualanService = new PenjualanService();
    private String customerId;
    private boolean canGoThrough;
    private String shortcut;
    private Karyawan karyawan;
    private boolean notaKhusus;
    private String tempSatuan;
    private SupplierService supplierService = new SupplierService();
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    private PembelianService pembelianService = new PembelianService();
    private PromoHematService promoHematService = new PromoHematService();
    private PromoKhususMemberDetailService promoKhususMemberDetailService = new PromoKhususMemberDetailService();
    private PromoHematDetailService promoHematDetailService = new PromoHematDetailService();
    private PromoDiskonByJenisService promoDiskonByJenisService = new PromoDiskonByJenisService();
    private PromoDiskonByJenisDetailService promoDiskonByJenisDetailService = new PromoDiskonByJenisDetailService();
    private PromoTebusDetailService promoTebusDetailService = new PromoTebusDetailService();
    private PromoVoucherDetailService promoVoucherDetailService = new PromoVoucherDetailService();
    private KonstantaService ks = new KonstantaService();
    private long lastPrice = 0L;
    private BarcodeService barcodeService = new BarcodeService();
    private PromoHematDetail promoHematDetail;
    private PromoTebusMurahDetail promoTebusMurahDetail;
    private PromoKhususMemberDetail promoKhususMemberDetail;
    private List<PromoTebusDetail> promoTebusDetails;
    private PromoTebusDetail promoTebusDetailsCek;
    private PromoDiskonByJenisDetail promoDiskonByJenisDetail;
    private KelebihanStokService kelebihanStokService = new KelebihanStokService();
    private PenjualanSementaraService penjualanSementaraService = new PenjualanSementaraService();
    private PromoTebusMurahDetailService promoTebusMurahDetailService = new PromoTebusMurahDetailService();
    private String startDateString;
    private String endDateString;
    private long totalBelanja;
    private float marginTempo = 0.0f;
    private JenisService jenisService = new JenisService();
    private String keterangan = "";
    private boolean hargaPromo = false;
    private int idCabang;
    private float tempQty;
    private String tempSatBarang = "";
    private PaketService paketService = new PaketService();
    private String tempChooseValue;
    private String promoKhususMember = "PROMOKHUSUSMEMBER";
    private String promoHemat = "PROMOHEMAT";
    private String promoPwp = "PROMOPWP";
    private String promoTebusMurah = "PROMOTEBUSMURAH";
    private String promoJenis = "PROMOJENIS";

    public PenjualanDetailDialog1(Frame parent, boolean modal, DefaultTableModel penjualanTableModel, String customerId, String shortcut, boolean notaKhusus, long totalBelanja, String keterangan) {
        super(parent, modal);
        this.initComponents();
        this.keterangan = keterangan;
        this.shortcut = shortcut;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.customerId = customerId;
        this.penjualanTableModel = penjualanTableModel;
        this.notaKhusus = notaKhusus;
        this.totalBelanja = totalBelanja;
        this.kodeBarangField.setText("");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.startDateString = simpleDateFormat.format(new Date());
        this.endDateString = simpleDateFormat.format(new Date());
        this.karyawan = this.helper.getKaryawan();
        this.idCabang = Integer.parseInt(this.ks.getConstantValue("idCabang"));
    }

    public void addListener(PenjualanDetailDialogListener penjualanDetailDialogListener) {
        this.listener = penjualanDetailDialogListener;
    }

    private void setDefaultData() {
        this.beliTerakhirField.setText("");
        this.beliTerakhirSatuanLbl.setText("");
        this.kodeBarangField.setText("");
        this.namaBarangField.setText("");
        this.quantity1.setText("0");
        this.quantity2.setText("0");
        this.quantity3.setText("0");
        this.satuan1.setText("");
        this.satuan2.setText("");
        this.satuan3.setText("");
        this.satuan4.setText("");
        this.promoLbl.setText("");
        this.promoLbl2.setText("");
        this.hargaSatuanField.setValue(0);
        this.totalHargaField.setValue(0);
        this.discount2Field.setValue(0);
        this.nettoHargaField.setValue(0);
        this.hargaSatuanField.setBackground(Color.white);
    }

    private long getDiscountPromo(PromoTebusDetail promoTebusDetail, float jumlahBarang) {
        long discount = promoTebusDetail.getDiscount();
        boolean find = false;
        int row = 0;
        int multiply = 0;
        for (int a = 0; a < this.penjualanTableModel.getRowCount(); ++a) {
            String barangId = this.penjualanTableModel.getValueAt(a, 0).toString();
            if (!promoTebusDetail.getBarangId().equals(barangId)) continue;
            find = true;
            row = a;
            break;
        }
        if (find) {
            String satuan = this.penjualanTableModel.getValueAt(row, 3).toString();
            if (promoTebusDetail.getSatuan().equals(satuan) && Float.parseFloat(this.penjualanTableModel.getValueAt(row, 2).toString()) >= promoTebusDetail.getJumlah() && jumlahBarang < (float)(multiply = (int)(Float.parseFloat(this.penjualanTableModel.getValueAt(row, 2).toString()) / promoTebusDetail.getJumlah()))) {
                multiply = (int)jumlahBarang;
            }
        }
        return discount * (long)multiply;
    }

    protected void loadKodeBarang(String kodeBarang) {
        this.setDefaultData();
        this.keterangan = this.keterangan;
        this.barang = this.barangService.getBarang(kodeBarang);
        if (this.barang != null && !this.barang.getJenis().equals("DISCONTINUE")) {
            this.marginTempo = this.jenisService.getJenis(this.barang.getJenis()).getMarginTempo();
            if (this.keterangan.equals("CASH")) {
                this.marginTempo = 0.0f;
            }
            if (this.barang.getHpp() == 0L) {
                JOptionPane.showMessageDialog(null, "HPP barang belum ditentukan");
            } else {
                if (this.barang.getParentId() != null) {
                    if (this.barang.getParentId().equals("")) {
                        this.mixLbl.setText("-");
                    } else {
                        this.mixLbl.setText("BOLEH MIX");
                    }
                }
                this.promoKhususMemberDetail = this.promoKhususMemberDetailService.getPromoKhususMemberDetail(this.barang.getId(), this.customerId);
                this.promoHematDetail = this.promoHematDetailService.getPromoHematDetailByBarangId(this.barang.getId(), this.startDateString, this.endDateString);
                this.promoTebusDetails = this.promoTebusDetailService.getPromoTebusDetailByIdPromoBarang(this.barang.getId(), this.barang.getSatuan1());
                if (this.promoTebusDetails.size() == 0) {
                    this.promoTebusDetails = this.promoTebusDetailService.getPromoTebusDetailByIdPromoBarang(this.barang.getId(), this.barang.getSatuan2());
                }
                if (this.promoTebusDetails.size() == 0) {
                    this.promoTebusDetails = this.promoTebusDetailService.getPromoTebusDetailByIdPromoBarang(this.barang.getId(), this.barang.getSatuan3());
                }
                this.promoDiskonByJenisDetail = this.promoDiskonByJenisDetailService.getPromoDiscJenisDetailByMJenis(this.barang.getJenis());
                if (this.promoDiskonByJenisDetail != null && this.promoDiskonByJenisDetail.getKhususPelanggan().equals("YA") && this.customerId.equals("C")) {
                    this.promoDiskonByJenisDetail = null;
                }
                this.promoVoucherDetail = this.promoVoucherDetailService.getPromoVoucherByBarangId(this.barang.getId());
                this.promoTebusMurahDetail = this.promoTebusMurahDetailService.getPromoTebusMurahDetailByBarangId(this.barang.getId(), this.startDateString, this.endDateString);
                String promoTxt = "";
                String promoTxt2 = "";
                if (this.promoKhususMemberDetail.getBarangId() != null) {
                    Customer tempCustomer = this.customerService.getCustomerById(this.customerId);
                    promoTxt = "PROMO KHUSUS " + tempCustomer.getNama() + " MIN " + this.promoKhususMemberDetail.getMinPembelian() + " " + this.promoKhususMemberDetail.getSatuan();
                    this.promoLbl.setText(promoTxt);
                } else if (this.promoHematDetail != null) {
                    promoTxt = "PROMO";
                    if (this.promoHematDetail.getKhususPelanggan().equals("YA")) {
                        promoTxt = promoTxt + " KHUSUS PELANGGAN";
                    }
                    promoTxt = promoTxt + " MINIMUM " + this.promoHematDetail.getMinimumPembelian() + this.promoHematDetail.getSatuan();
                    this.promoLbl.setText(promoTxt);
                } else if (this.promoTebusDetails.size() > 0) {
                    promoTxt = this.barangService.getBarang(this.promoTebusDetails.get(0).getPromoBarangId()).getNama() + " DISC " + this.promoTebusDetails.get(0).getDiscount() + "/" + this.promoTebusDetails.get(0).getSatuanPromo();
                    promoTxt2 = "TIAP PEMBELIAN " + this.promoTebusDetails.get(0).getJumlah() + this.promoTebusDetails.get(0).getSatuan() + " " + this.barangService.getBarang(this.promoTebusDetails.get(0).getBarangId()).getNama();
                    this.promoLbl.setText(promoTxt);
                    this.promoLbl2.setText(promoTxt2);
                } else if (this.promoTebusMurahDetail != null) {
                    promoTxt = this.barangService.getBarang(this.promoTebusMurahDetail.getBarangId()).getNama() + " PROMO " + this.promoTebusMurahDetail.getHargaPromo() + "/" + this.promoTebusMurahDetail.getSatuan();
                    promoTxt2 = "TIAP BELANJA " + this.promoTebusMurahDetail.getMinimumBelanja() + " MAX" + this.promoTebusMurahDetail.getMaksimalKuantiti();
                    this.promoLbl.setText(promoTxt);
                    this.promoLbl2.setText(promoTxt2);
                } else if (this.promoDiskonByJenisDetail != null && this.promoDiskonByJenisService.getPromoByJenis("1").getStatus().equalsIgnoreCase("AKTIF")) {
                    promoTxt = this.promoDiskonByJenisDetail.getmJenisJenis() + " ECER Diskon " + this.promoDiskonByJenisDetail.getDisc() + " %";
                    this.promoLbl.setText(promoTxt);
                    if (this.barang.getMinPembelian() > 1.0f) {
                        this.promoLbl2.setText("Grosir minimum pembelian=" + this.barang.getMinPembelian());
                    }
                } else if (this.barang.getMinPembelian() > 1.0f) {
                    this.promoLbl.setText("Grosir minimum pembelian=" + this.barang.getMinPembelian());
                } else {
                    this.promoLbl.setText("");
                }
                PenjualanDetail penjualanDetail = null;
                if (this.beliTerakhir.isSelected() && (penjualanDetail = this.penjualanDetailService.getPenjualanTerakhir(this.customerId, this.barang.getId())) != null) {
                    this.beliTerakhirField.setValue(Float.valueOf((float)penjualanDetail.getHargaJual() - (float)penjualanDetail.getDiscount2() / penjualanDetail.getJumlahBarang()));
                    this.beliTerakhirSatuanLbl.setText("/" + penjualanDetail.getSatuan());
                }
                if (this.barang.getHargaJual1() == 0L) {
                    JOptionPane.showMessageDialog(null, "Harga Jual Belum Ditentukan");
                }
                this.kodeBarangField.setText(kodeBarang);
                this.namaBarangField.setText(this.barang.getNama());
                this.konversiLbl1.setText(this.barang.getSatuan2());
                this.konversiLbl2.setText(this.barang.getSatuan1());
                this.konversiLbl3.setText(this.barang.getSatuan3());
                this.konversiLbl4.setText(this.barang.getSatuan2());
                this.konversiField1.setText(this.barang.getKonversi21() + "");
                this.konversiField2.setText(this.barang.getKonversi32() + "");
                this.satuan1.setText(this.barang.getSatuan1());
                this.satuan2.setText(this.barang.getSatuan2());
                this.satuan3.setText(this.barang.getSatuan3());
                this.quantity1.setEditable(true);
                this.quantity2.setEditable(true);
                this.quantity3.setEditable(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
    }

    protected void escapeClick() {
        this.kodeBarangField.requestFocus();
        this.kodeBarangField.selectAll();
    }

    private void saveClick(boolean remove, String promo) {
        Object[] rowValue = new Object[]{this.barang.getId(), this.barang.getNama(), this.tempField.getText(), this.satuan4.getText(), this.hargaSatuanField.getValue(), (long)Float.parseFloat(this.totalHargaField.getValue().toString()), this.discount2Field.getValue(), (long)Float.parseFloat(this.nettoHargaField.getValue().toString()), this.notaKhusus, promo};
        if (remove) {
            this.tempQty = Float.parseFloat(this.tempField.getText());
            this.tempSatBarang = this.satuan4.getText();
        }
        this.listener.saveClick(rowValue, remove);
        boolean lanjut = true;
        String statusPromo = "";
        if (promo.equals(this.promoJenis)) {
            statusPromo = this.promoJenis;
            promo = "";
        }
        if (this.barang.getParentId() != null && lanjut && promo.equals("")) {
            if (this.tempSatuan.equals("Sat1")) {
                int result = 0;
                long hargaAsli = 0L;
                hargaAsli = Float.parseFloat(this.quantity1.getText()) < this.barang.getMinPembelian() ? this.helper.roundPrice100((long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1()) : this.barang.getHargaJual1();
                if (Long.parseLong(this.hargaSatuanField.getValue() + "") == hargaAsli) {
                    float jumlah = this.penjualanSementaraService.cekJumlahByParentId(this.helper.getKasir(), this.shortcut, this.satuan4.getText(), this.barang.getParentId());
                    if (this.barang.getKonversi21() > 0 && jumlah >= (float)this.barang.getKonversi21()) {
                        long harga = this.barang.getHargaJual2() / (long)this.barang.getKonversi21();
                        List<PenjualanSementara> penjualanSementaras = this.penjualanSementaraService.getPenjualanSementaraByShortcut(this.shortcut, this.barang.getParentId(), this.barang.getSatuan1());
                        for (PenjualanSementara penjualanSementara : penjualanSementaras) {
                            long totalHarga = Math.round((float)harga * penjualanSementara.getJumlahBarang());
                            result = this.penjualanSementaraService.updateHargaPenjualanSementara(harga, totalHarga, 0L, this.helper.getKasir(), this.shortcut, this.satuan4.getText(), penjualanSementara.getBarangId(), Float.valueOf(penjualanSementara.getJumlahBarang()));
                        }
                    } else if (jumlah >= this.barang.getMinPembelian()) {
                        List<PenjualanSementara> penjualanSementaras = this.penjualanSementaraService.getPenjualanSementaraByShortcut(this.shortcut, this.barang.getParentId(), this.barang.getSatuan1());
                        for (PenjualanSementara penjualanSementara : penjualanSementaras) {
                            long totalHarga = Math.round((float)this.barang.getHargaJual1() * penjualanSementara.getJumlahBarang());
                            result = this.penjualanSementaraService.updateHargaPenjualanSementara(this.barang.getHargaJual1(), totalHarga, 0L, this.helper.getKasir(), this.shortcut, this.satuan4.getText(), penjualanSementara.getBarangId(), Float.valueOf(penjualanSementara.getJumlahBarang()));
                        }
                    } else if (this.barang.getMinPembelian() > 0.0f && jumlah < this.barang.getMinPembelian()) {
                        float discount = 0.0f;
                        this.promoDiskonByJenisDetail = this.promoDiskonByJenisDetailService.getPromoDiscJenisDetailByMJenis(this.barang.getJenis());
                        if (this.promoDiskonByJenisDetail != null && this.promoDiskonByJenisDetail.getKhususPelanggan().equals("YA") && this.customerId.equals("C")) {
                            this.promoDiskonByJenisDetail = null;
                        }
                        if (this.promoDiskonByJenisDetail != null) {
                            discount = this.promoDiskonByJenisDetail.getDisc() / 100.0f * (float)hargaAsli * Float.parseFloat(this.quantity1.getText());
                        }
                        List<PenjualanSementara> penjualanSementaras = this.penjualanSementaraService.getPenjualanSementaraByShortcut(this.shortcut, this.barang.getParentId(), this.barang.getSatuan1());
                        for (PenjualanSementara penjualanSementara : penjualanSementaras) {
                            long totalHarga = Math.round((float)hargaAsli * penjualanSementara.getJumlahBarang()) - Math.round(discount);
                            System.out.println("total harga=" + totalHarga);
                            result = this.penjualanSementaraService.updateHargaPenjualanSementara(hargaAsli, totalHarga, Math.round(discount), this.helper.getKasir(), this.shortcut, this.satuan4.getText(), penjualanSementara.getBarangId(), Float.valueOf(penjualanSementara.getJumlahBarang()));
                        }
                    }
                }
                this.listener.refreshData();
            } else if (this.tempSatuan.equals("Sat2")) {
                int result = 0;
                if (Long.parseLong(this.hargaSatuanField.getValue() + "") == this.barang.getHargaJual2()) {
                    float jumlah = this.penjualanSementaraService.cekJumlahByParentId(this.helper.getKasir(), this.shortcut, this.satuan4.getText(), this.barang.getParentId());
                    if (jumlah >= (float)this.barang.getKonversi21()) {
                        long harga = this.barang.getHargaJual3() / (long)this.barang.getKonversi32();
                        List<PenjualanSementara> penjualanSementaras = this.penjualanSementaraService.getPenjualanSementaraByShortcut(this.shortcut, this.barang.getParentId(), this.barang.getSatuan2());
                        for (PenjualanSementara penjualanSementara : penjualanSementaras) {
                            long totalHarga = (long)Math.round((float)harga * penjualanSementara.getJumlahBarang()) - penjualanSementara.getDiscount();
                            result = this.penjualanSementaraService.updateHargaPenjualanSementara(harga, totalHarga, penjualanSementara.getDiscount(), this.helper.getKasir(), this.shortcut, this.satuan4.getText(), penjualanSementara.getBarangId(), Float.valueOf(penjualanSementara.getJumlahBarang()));
                        }
                    } else if (jumlah < (float)this.barang.getKonversi21()) {
                        long harga = this.barang.getHargaJual2();
                        List<PenjualanSementara> penjualanSementaras = this.penjualanSementaraService.getPenjualanSementaraByShortcut(this.shortcut, this.barang.getParentId(), this.barang.getSatuan2());
                        for (PenjualanSementara penjualanSementara : penjualanSementaras) {
                            long totalHarga = (long)Math.round((float)harga * penjualanSementara.getJumlahBarang()) - penjualanSementara.getDiscount();
                            result = this.penjualanSementaraService.updateHargaPenjualanSementara(harga, totalHarga, penjualanSementara.getDiscount(), this.helper.getKasir(), this.shortcut, this.satuan4.getText(), penjualanSementara.getBarangId(), Float.valueOf(penjualanSementara.getJumlahBarang()));
                        }
                    }
                }
                this.listener.refreshData();
            } else if (this.tempSatuan.equals("Sat3")) {
                this.listener.refreshData();
            }
        }
        this.escapeClick();
    }

    private void sat1pres(boolean removeRow) {
        String satPromo;
        String idPromo;
        List<PenjualanSementara> dataPromo;
        String promo = "";
        String message = "";
        this.hargaPromo = false;
        this.tempSatuan = "Sat1";
        this.tempField = this.quantity1;
        this.discount2Field.setValue(0);
        this.satuan4.setText(this.barang.getSatuan1());
        this.promoTebusDetailsCek = this.promoTebusDetailService.getPromoTebusDetailByBarangId2(this.barang.getId());
        boolean allowed = true;
        if (this.promoTebusDetailsCek != null && (dataPromo = this.penjualanSementaraService.getPenjualanSementaraByShortcutIdBarang(this.shortcut, idPromo = this.promoTebusDetailsCek.getPromoBarangId(), satPromo = this.promoTebusDetailsCek.getSatuanPromo())).size() > 0) {
            long qtyPromo = dataPromo.get(0).getDiscount() / this.promoTebusDetailsCek.getDiscount() * (long)Math.round(this.promoTebusDetailsCek.getJumlah());
            if (!this.promoTebusDetailsCek.getSatuan().equals(this.barang.getSatuan1())) {
                allowed = false;
                message = "Tidak boleh merubah satuan saat promo sudah diinput";
            } else if (Float.parseFloat(this.quantity1.getText()) < (float)qtyPromo) {
                allowed = false;
                message = "Jumlah Barang Tidak Dapat Diturunkan, ubah quantity promo dahulu";
            }
        }
        long hargaSatuan = 0L;
        if (this.notaKhusus) {
            hargaSatuan = this.barang.getHpp();
            this.hargaSatuanField.setValue(hargaSatuan);
        } else if (!allowed) {
            JOptionPane.showMessageDialog(null, message);
        } else {
            long resultValue;
            PromoHematDetail promoHematDetail = this.promoHematDetailService.getPromoHematDetailByBarangId(this.barang.getId(), this.barang.getSatuan1(), Float.parseFloat(this.quantity1.getText()), this.startDateString, this.endDateString);
            this.promoTebusDetails = this.promoTebusDetailService.getPromoTebusDetailByIdPromoBarang(this.barang.getId(), this.barang.getSatuan1());
            if (this.promoKhususMemberDetail.getBarangId() != null) {
                if (Float.parseFloat(this.quantity1.getText()) >= (float)this.promoKhususMemberDetail.getMinPembelian() && this.promoKhususMemberDetail.getSatuan().equals(this.barang.getSatuan1())) {
                    hargaSatuan = this.promoKhususMemberDetail.getHargaPromo();
                    promo = this.promoKhususMember;
                } else {
                    hargaSatuan = Float.parseFloat(this.quantity1.getText()) < this.barang.getMinPembelian() ? (resultValue = this.helper.roundPrice100((long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1())) : this.barang.getHargaJual1();
                }
            } else if (promoHematDetail != null) {
                long resultValue2;
                this.hargaPromo = true;
                boolean pass = true;
                if (promoHematDetail.getKhususPelanggan().equals("YA") && this.customerId.equals("C")) {
                    pass = false;
                }
                hargaSatuan = Float.parseFloat(this.quantity1.getText()) < this.barang.getMinPembelian() ? (resultValue2 = this.helper.roundPrice100((long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1())) : this.barang.getHargaJual1();
                if (Float.parseFloat(this.quantity1.getText()) >= promoHematDetail.getMinimumPembelian() && pass && promoHematDetail.getSatuan().equals(this.barang.getSatuan1())) {
                    long hargaPromo = promoHematDetail.getHargaPromo();
                    long discount = (hargaSatuan - hargaPromo) * (long)Integer.parseInt(this.quantity1.getText());
                    this.discount2Field.setValue(discount);
                    promo = this.promoHemat;
                }
            } else if (this.promoTebusDetails.size() > 0) {
                this.hargaPromo = true;
                int qtyPromo = 0;
                for (PromoTebusDetail promoTebusDetail : this.promoTebusDetails) {
                    float jumlahSyarat = this.penjualanSementaraService.getJumlahPenjualanSementara(this.helper.getKasir(), this.shortcut, promoTebusDetail.getBarangId(), promoTebusDetail.getSatuan());
                    qtyPromo += Math.round(jumlahSyarat / promoTebusDetail.getJumlah());
                }
                if (qtyPromo > 0) {
                    long discount = 0L;
                    if (this.promoTebusDetails.get(0).getSatuanPromo().equals(this.barang.getSatuan1())) {
                        int maxPromo = 0;
                        maxPromo = Math.round(Float.parseFloat(this.quantity1.getText())) > qtyPromo ? qtyPromo : Math.round(Float.parseFloat(this.quantity1.getText()));
                        discount = this.promoTebusDetails.get(0).getDiscount() * (long)maxPromo;
                        promo = this.promoPwp;
                    }
                    this.discount2Field.setValue((int)discount);
                    hargaSatuan = this.barang.getHargaJual1();
                } else {
                    this.discount2Field.setValue(0);
                    hargaSatuan = this.barang.getHargaJual1();
                }
            } else if (this.promoTebusMurahDetail != null) {
                this.hargaPromo = true;
                this.totalBelanja = this.penjualanSementaraService.getTotalBelanja(this.shortcut, this.helper.getKasir());
                hargaSatuan = Float.parseFloat(this.quantity1.getText()) < this.barang.getMinPembelian() ? (resultValue = this.helper.roundPrice100((long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1())) : this.barang.getHargaJual1();
                if (this.totalBelanja >= this.promoTebusMurahDetail.getMinimumBelanja() && this.promoTebusMurahDetail.getSatuan().equals(this.barang.getSatuan1())) {
                    if (Float.parseFloat(this.quantity1.getText()) <= this.promoTebusMurahDetail.getMaksimalKuantiti()) {
                        long hargaPromo = this.promoTebusMurahDetail.getHargaPromo();
                        long discount = (hargaSatuan - hargaPromo) * (long)Integer.parseInt(this.quantity1.getText());
                        this.discount2Field.setValue(discount);
                        promo = this.promoTebusMurah;
                    } else {
                        JOptionPane.showMessageDialog(null, "Maksimal kuantiti yg boleh dibeli adalah " + this.promoTebusMurahDetail.getMaksimalKuantiti());
                    }
                }
            } else if (this.promoDiskonByJenisDetail != null) {
                if (this.barang.getMinPembelian() == 0.0f) {
                    float discount = this.promoDiskonByJenisDetail.getDisc() / 100.0f * (float)this.barang.getHargaJual1() * Float.parseFloat(this.quantity1.getText());
                    hargaSatuan = this.barang.getHargaJual1();
                    this.discount2Field.setValue((int)discount);
                    promo = this.promoJenis;
                } else if (this.barang.getMinPembelian() > 0.0f) {
                    if (Float.parseFloat(this.quantity1.getText()) < this.barang.getMinPembelian()) {
                        resultValue = this.helper.roundPrice100((long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1());
                        float discount = this.promoDiskonByJenisDetail.getDisc() / 100.0f * (float)resultValue * Float.parseFloat(this.quantity1.getText());
                        hargaSatuan = resultValue;
                        this.discount2Field.setValue((int)discount);
                        promo = this.promoJenis;
                    } else {
                        hargaSatuan = this.barang.getHargaJual1();
                    }
                }
            } else {
                hargaSatuan = Float.parseFloat(this.quantity1.getText()) < this.barang.getMinPembelian() ? (resultValue = this.helper.roundPrice100((long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1())) : (Float.parseFloat(this.quantity1.getText()) >= (float)(this.barang.getKonversi32() * this.barang.getKonversi21()) && this.barang.getKonversi21() > 0 ? (this.barang.getKonversi32() > 0 ? this.barang.getHargaJual3() / (long)this.barang.getKonversi21() / (long)this.barang.getKonversi32() : (Float.parseFloat(this.quantity1.getText()) >= (float)this.barang.getKonversi21() ? this.barang.getHargaJual2() / (long)this.barang.getKonversi21() : this.barang.getHargaJual1())) : (Float.parseFloat(this.quantity1.getText()) >= (float)this.barang.getKonversi21() ? (this.barang.getKonversi21() > 0 && this.promoTebusDetailsCek == null ? this.barang.getHargaJual2() / (long)this.barang.getKonversi21() : this.barang.getHargaJual1()) : this.barang.getHargaJual1()));
            }
            if (this.keterangan.equals("KREDIT")) {
                hargaSatuan = this.helper.roundPrice50((long)((float)hargaSatuan * (100.0f + this.marginTempo) / 100.0f));
            }
            this.hargaSatuanField.setValue(hargaSatuan);
        }
        if (allowed) {
            float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText());
            this.totalHargaField.setValue((long)tempValue);
            this.nettoHargaField.setValue((long)tempValue - Long.parseLong(this.discount2Field.getValue().toString()));
            this.hargaSatuanField.requestFocus();
            if (this.karyawan.getRole().equals("MANAGER") || this.karyawan.getRole().equals("DIREKTUR")) {
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.selectAll();
            }
            this.quantity1.setBackground(Color.white);
            this.quantity2.setText("0");
            this.quantity3.setText("0");
            this.updateLastPrice();
            if (!(Helper.getInstance().isFloat(this.quantity1.getText()) && Helper.getInstance().isFloat(this.quantity2.getText()) && Helper.getInstance().isFloat(this.quantity3.getText()))) {
                JOptionPane.showMessageDialog(null, "Isi Data Dengan Benar");
            } else if (!(this.hargaPromo || this.notaKhusus || this.lastPrice < Long.parseLong(this.hargaSatuanField.getText().replace(".", "")) || this.barang.getJenis().equals("LAINLAIN"))) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordSaveEventHandler());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Harga Jual Lebih Rendah Dr Harga Beli");
                passwordInputDialog2.setVisible(true);
            } else {
                this.saveClick(removeRow, promo);
            }
        }
    }

    private void sat3pres(boolean removeRow) {
        String satPromo;
        String idPromo;
        List<PenjualanSementara> dataPromo;
        String promo = "";
        String message = "";
        this.hargaPromo = false;
        this.tempSatuan = "Sat3";
        this.tempField = this.quantity3;
        this.discount2Field.setValue(0);
        this.satuan4.setText(this.barang.getSatuan3());
        this.promoTebusDetailsCek = this.promoTebusDetailService.getPromoTebusDetailByBarangId2(this.barang.getId());
        boolean allowed = true;
        if (this.promoTebusDetailsCek != null && (dataPromo = this.penjualanSementaraService.getPenjualanSementaraByShortcutIdBarang(this.shortcut, idPromo = this.promoTebusDetailsCek.getPromoBarangId(), satPromo = this.promoTebusDetailsCek.getSatuanPromo())).size() > 0) {
            long qtyPromo = dataPromo.get(0).getDiscount() / this.promoTebusDetailsCek.getDiscount() * (long)Math.round(this.promoTebusDetailsCek.getJumlah());
            if (!this.promoTebusDetailsCek.getSatuan().equals(this.barang.getSatuan3())) {
                allowed = false;
                message = "Tidak boleh merubah satuan saat promo sudah diinput";
            } else if (Float.parseFloat(this.quantity1.getText()) < (float)qtyPromo) {
                allowed = false;
                message = "Jumlah Barang Tidak Dapat Diturunkan, ubah quantity promo dahulu";
            }
        }
        long hargaSatuan = 0L;
        if (this.notaKhusus) {
            hargaSatuan = this.barang.getHpp() * (long)this.barang.getKonversi32() * (long)this.barang.getKonversi21();
            this.hargaSatuanField.setValue(hargaSatuan);
        } else if (!allowed) {
            JOptionPane.showMessageDialog(null, message);
        } else {
            PromoHematDetail promoHematDetail = this.promoHematDetailService.getPromoHematDetailByBarangId(this.barang.getId(), this.barang.getSatuan3(), Float.parseFloat(this.quantity3.getText()), this.startDateString, this.endDateString);
            this.promoTebusDetails = this.promoTebusDetailService.getPromoTebusDetailByIdPromoBarang(this.barang.getId(), this.barang.getSatuan3());
            if (this.promoKhususMemberDetail.getBarangId() != null) {
                if (Float.parseFloat(this.quantity3.getText()) >= (float)this.promoKhususMemberDetail.getMinPembelian() && this.promoKhususMemberDetail.getSatuan().equals(this.barang.getSatuan3())) {
                    hargaSatuan = this.promoKhususMemberDetail.getHargaPromo();
                    promo = this.promoKhususMember;
                } else {
                    hargaSatuan = this.barang.getHargaJual3();
                }
            } else if (promoHematDetail != null) {
                this.hargaPromo = true;
                boolean pass = true;
                if (promoHematDetail.getKhususPelanggan().equals("YA") && this.customerId.equals("C")) {
                    pass = false;
                }
                hargaSatuan = this.barang.getHargaJual3();
                if (Float.parseFloat(this.quantity3.getText()) >= promoHematDetail.getMinimumPembelian() && pass && promoHematDetail.getSatuan().equals(this.barang.getSatuan3())) {
                    long hargaPromo = promoHematDetail.getHargaPromo();
                    long discount = (hargaSatuan - hargaPromo) * (long)Integer.parseInt(this.quantity3.getText());
                    this.discount2Field.setValue(discount);
                    promo = this.promoHemat;
                }
            } else if (this.promoTebusDetails.size() > 0) {
                this.hargaPromo = true;
                int qtyPromo = 0;
                for (PromoTebusDetail promoTebusDetail : this.promoTebusDetails) {
                    float jumlahSyarat = this.penjualanSementaraService.getJumlahPenjualanSementara(this.helper.getKasir(), this.shortcut, promoTebusDetail.getBarangId(), promoTebusDetail.getSatuan());
                    qtyPromo += Math.round(jumlahSyarat / promoTebusDetail.getJumlah());
                }
                if (qtyPromo > 0) {
                    long discount = 0L;
                    if (this.promoTebusDetails.get(0).getSatuanPromo().equals(this.barang.getSatuan3())) {
                        int maxPromo = 0;
                        maxPromo = Math.round(Float.parseFloat(this.quantity3.getText())) > qtyPromo ? qtyPromo : Math.round(Float.parseFloat(this.quantity3.getText()));
                        discount = this.promoTebusDetails.get(0).getDiscount() * (long)maxPromo;
                        promo = this.promoPwp;
                    }
                    this.discount2Field.setValue((int)discount);
                    hargaSatuan = this.barang.getHargaJual3();
                } else {
                    this.discount2Field.setValue(0);
                    hargaSatuan = this.barang.getHargaJual3();
                }
            } else if (this.promoTebusMurahDetail != null) {
                this.hargaPromo = true;
                this.totalBelanja = this.penjualanSementaraService.getTotalBelanja(this.shortcut, this.helper.getKasir());
                hargaSatuan = this.barang.getHargaJual3();
                if (this.totalBelanja >= this.promoTebusMurahDetail.getMinimumBelanja() && this.promoTebusMurahDetail.getSatuan().equals(this.barang.getSatuan3())) {
                    if (Float.parseFloat(this.quantity3.getText()) <= this.promoTebusMurahDetail.getMaksimalKuantiti()) {
                        long hargaPromo = this.promoTebusMurahDetail.getHargaPromo();
                        long discount = (hargaSatuan - hargaPromo) * (long)Integer.parseInt(this.quantity3.getText());
                        this.discount2Field.setValue(discount);
                        promo = this.promoTebusMurah;
                    } else {
                        JOptionPane.showMessageDialog(null, "Maksimal kuantiti yg boleh dibeli adalah " + this.promoTebusMurahDetail.getMaksimalKuantiti());
                    }
                }
            } else {
                hargaSatuan = this.barang.getHargaJual3();
            }
            if (this.keterangan.equals("KREDIT")) {
                hargaSatuan = this.helper.roundPrice50((long)((float)hargaSatuan * (100.0f + this.marginTempo) / 100.0f));
            }
            this.hargaSatuanField.setValue(hargaSatuan);
        }
        if (allowed) {
            this.hargaSatuanField.requestFocus();
            if (this.karyawan.getRole().equals("MANAGER") || this.karyawan.getRole().equals("DIREKTUR")) {
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.selectAll();
            }
            float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText());
            this.totalHargaField.setValue((long)tempValue);
            this.nettoHargaField.setValue((long)tempValue - Long.parseLong(this.discount2Field.getValue().toString()));
            this.quantity3.setBackground(Color.white);
            this.quantity1.setText("0");
            this.quantity2.setText("0");
            this.updateLastPrice();
            if (!(Helper.getInstance().isFloat(this.quantity1.getText()) && Helper.getInstance().isFloat(this.quantity2.getText()) && Helper.getInstance().isFloat(this.quantity3.getText()))) {
                JOptionPane.showMessageDialog(null, "Isi Data Dengan Benar");
            } else if (!(this.hargaPromo || this.notaKhusus || this.lastPrice < Long.parseLong(this.hargaSatuanField.getText().replace(".", "")) || this.barang.getJenis().equals("LAINLAIN"))) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordSaveEventHandler());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Harga Jual Lebih Rendah Dr Harga Beli");
                passwordInputDialog2.setVisible(true);
            } else {
                this.saveClick(removeRow, promo);
            }
        }
    }

    private void sat2pres(boolean removeRow) {
        String satPromo;
        String idPromo;
        List<PenjualanSementara> dataPromo;
        String promo = "";
        String message = "";
        this.hargaPromo = false;
        this.tempSatuan = "Sat2";
        this.discount2Field.setValue(0);
        this.tempField = this.quantity2;
        this.satuan4.setText(this.barang.getSatuan2());
        long hargaSatuan = 0L;
        this.promoTebusDetailsCek = this.promoTebusDetailService.getPromoTebusDetailByBarangId2(this.barang.getId());
        boolean allowed = true;
        if (this.promoTebusDetailsCek != null && (dataPromo = this.penjualanSementaraService.getPenjualanSementaraByShortcutIdBarang(this.shortcut, idPromo = this.promoTebusDetailsCek.getPromoBarangId(), satPromo = this.promoTebusDetailsCek.getSatuanPromo())).size() > 0) {
            long qtyPromo = dataPromo.get(0).getDiscount() / this.promoTebusDetailsCek.getDiscount() * (long)Math.round(this.promoTebusDetailsCek.getJumlah());
            if (!this.promoTebusDetailsCek.getSatuan().equals(this.barang.getSatuan3())) {
                allowed = false;
                message = "Tidak boleh merubah satuan saat promo sudah diinput";
            } else if (Float.parseFloat(this.quantity2.getText()) < (float)qtyPromo) {
                allowed = false;
                message = "Jumlah Barang Tidak Dapat Diturunkan, ubah quantity promo dahulu";
            }
        }
        if (this.notaKhusus) {
            hargaSatuan = this.barang.getHpp() * (long)this.barang.getKonversi21();
            this.hargaSatuanField.setValue(hargaSatuan);
        } else if (!allowed) {
            JOptionPane.showMessageDialog(null, message);
        } else {
            PromoHematDetail promoHematDetail = this.promoHematDetailService.getPromoHematDetailByBarangId(this.barang.getId(), this.barang.getSatuan2(), Float.parseFloat(this.quantity2.getText()), this.startDateString, this.endDateString);
            this.promoTebusDetails = this.promoTebusDetailService.getPromoTebusDetailByIdPromoBarang(this.barang.getId(), this.barang.getSatuan2());
            if (this.promoKhususMemberDetail.getBarangId() != null) {
                if (Float.parseFloat(this.quantity2.getText()) >= (float)this.promoKhususMemberDetail.getMinPembelian() && this.promoKhususMemberDetail.getSatuan().equals(this.barang.getSatuan2())) {
                    hargaSatuan = this.promoKhususMemberDetail.getHargaPromo();
                    promo = this.promoKhususMember;
                } else {
                    hargaSatuan = this.barang.getHargaJual2();
                }
            } else if (promoHematDetail != null) {
                this.hargaPromo = true;
                boolean pass = true;
                if (promoHematDetail.getKhususPelanggan().equals("YA") && this.customerId.equals("C")) {
                    pass = false;
                }
                hargaSatuan = this.barang.getHargaJual2();
                if (Float.parseFloat(this.quantity2.getText()) >= promoHematDetail.getMinimumPembelian() && pass && promoHematDetail.getSatuan().equals(this.barang.getSatuan2())) {
                    System.out.println("MASUK PROMO HEMAT LO");
                    System.out.println(this.quantity2.getText() + "," + promoHematDetail.getMinimumPembelian() + "," + pass + "," + promoHematDetail.getSatuan() + "," + this.barang.getSatuan2());
                    long hargaPromo = promoHematDetail.getHargaPromo();
                    long discount = (hargaSatuan - hargaPromo) * (long)Integer.parseInt(this.quantity2.getText());
                    this.discount2Field.setValue(discount);
                    promo = this.promoHemat;
                }
            } else if (this.promoTebusDetails.size() > 0) {
                this.hargaPromo = true;
                int qtyPromo = 0;
                for (PromoTebusDetail promoTebusDetail : this.promoTebusDetails) {
                    float jumlahSyarat = this.penjualanSementaraService.getJumlahPenjualanSementara(this.helper.getKasir(), this.shortcut, promoTebusDetail.getBarangId(), promoTebusDetail.getSatuan());
                    qtyPromo += Math.round(jumlahSyarat / promoTebusDetail.getJumlah());
                }
                if (qtyPromo > 0) {
                    long discount = 0L;
                    if (this.promoTebusDetails.get(0).getSatuanPromo().equals(this.barang.getSatuan2())) {
                        int maxPromo = 0;
                        maxPromo = Math.round(Float.parseFloat(this.quantity2.getText())) > qtyPromo ? qtyPromo : Math.round(Float.parseFloat(this.quantity2.getText()));
                        discount = this.promoTebusDetails.get(0).getDiscount() * (long)maxPromo;
                        promo = this.promoPwp;
                    }
                    this.discount2Field.setValue((int)discount);
                    hargaSatuan = this.barang.getHargaJual2();
                } else {
                    this.discount2Field.setValue(0);
                    hargaSatuan = this.barang.getHargaJual2();
                }
            } else if (this.promoTebusMurahDetail != null) {
                this.hargaPromo = true;
                this.totalBelanja = this.penjualanSementaraService.getTotalBelanja(this.shortcut, this.helper.getKasir());
                hargaSatuan = this.barang.getHargaJual2();
                if (this.totalBelanja >= this.promoTebusMurahDetail.getMinimumBelanja() && this.promoTebusMurahDetail.getSatuan().equals(this.barang.getSatuan2())) {
                    if (Float.parseFloat(this.quantity2.getText()) <= this.promoTebusMurahDetail.getMaksimalKuantiti()) {
                        long hargaPromo = this.promoTebusMurahDetail.getHargaPromo();
                        long discount = (hargaSatuan - hargaPromo) * (long)Integer.parseInt(this.quantity2.getText());
                        this.discount2Field.setValue(discount);
                        promo = this.promoTebusMurah;
                    } else {
                        JOptionPane.showMessageDialog(null, "Maksimal kuantiti yg boleh dibeli adalah " + this.promoTebusMurahDetail.getMaksimalKuantiti());
                    }
                }
            } else {
                hargaSatuan = Float.parseFloat(this.quantity2.getText()) >= (float)this.barang.getKonversi32() ? (this.barang.getKonversi32() > 0 && this.promoTebusDetailsCek == null ? this.barang.getHargaJual3() / (long)this.barang.getKonversi32() : this.barang.getHargaJual2()) : this.barang.getHargaJual2();
            }
            if (this.keterangan.equals("KREDIT")) {
                hargaSatuan = this.helper.roundPrice50((long)((float)hargaSatuan * (100.0f + this.marginTempo) / 100.0f));
            }
            this.hargaSatuanField.setValue(hargaSatuan);
        }
        if (allowed) {
            this.hargaSatuanField.requestFocus();
            if (this.karyawan.getRole().equals("MANAGER") || this.karyawan.getRole().equals("DIREKTUR")) {
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.selectAll();
            }
            float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText());
            this.totalHargaField.setValue((long)tempValue);
            this.nettoHargaField.setValue((long)tempValue - Long.parseLong(this.discount2Field.getValue().toString()));
            this.quantity2.setBackground(Color.white);
            this.quantity1.setText("0");
            this.quantity3.setText("0");
            this.updateLastPrice();
            if (!(Helper.getInstance().isFloat(this.quantity1.getText()) && Helper.getInstance().isFloat(this.quantity2.getText()) && Helper.getInstance().isFloat(this.quantity3.getText()))) {
                JOptionPane.showMessageDialog(null, "Isi Data Dengan Benar");
            } else if (!(this.hargaPromo || this.notaKhusus || this.lastPrice < Long.parseLong(this.hargaSatuanField.getText().replace(".", "")) || this.barang.getJenis().equals("LAINLAIN"))) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordSaveEventHandler());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Harga Jual Lebih Rendah Dr Harga Beli");
                passwordInputDialog2.setVisible(true);
            } else {
                this.saveClick(removeRow, promo);
            }
        }
    }

    private void updateLastPrice() {
        String satuan = "";
        if (this.tempSatuan.equals("Sat1")) {
            satuan = this.barang.getSatuan1();
        } else if (this.tempSatuan.equals("Sat2")) {
            satuan = this.barang.getSatuan2();
        } else if (this.tempSatuan.equals("Sat3")) {
            satuan = this.barang.getSatuan3();
        }
        if (satuan.equals(this.barang.getSatuan1())) {
            this.lastPrice = this.barang.getHpp();
            this.tempField = this.quantity1;
        } else if (satuan.equals(this.barang.getSatuan2())) {
            this.lastPrice = this.barang.getHpp() * (long)this.barang.getKonversi21();
            this.tempField = this.quantity2;
        } else if (satuan.equals(this.barang.getSatuan3())) {
            this.lastPrice = this.barang.getHpp() * (long)this.barang.getKonversi21() * (long)this.barang.getKonversi32();
            this.tempField = this.quantity3;
        }
    }

    protected void loadPenjualan(int row) {
        this.tempQty = Float.parseFloat(this.penjualanTableModel.getValueAt(row, 2).toString());
        this.tempSatBarang = this.penjualanTableModel.getValueAt(row, 3).toString();
        this.lastPrice = 0L;
        if (this.tempSatBarang.equals(this.barang.getSatuan1())) {
            this.tempField = this.quantity1;
        } else if (this.tempSatBarang.equals(this.barang.getSatuan2())) {
            this.tempField = this.quantity2;
        } else if (this.tempSatBarang.equals(this.barang.getSatuan3())) {
            this.tempField = this.quantity3;
        }
        if (this.penjualanTableModel.getValueAt(row, 3).equals(this.barang.getSatuan1())) {
            this.quantity1.setText(this.penjualanTableModel.getValueAt(row, 2).toString());
            this.quantity1.requestFocus();
            this.quantity1.selectAll();
            this.tempSatuan = this.barang.getSatuan1();
        } else if (this.penjualanTableModel.getValueAt(row, 3).equals(this.barang.getSatuan2())) {
            this.quantity2.setText(this.penjualanTableModel.getValueAt(row, 2).toString());
            this.quantity2.requestFocus();
            this.quantity2.selectAll();
            this.tempSatuan = this.barang.getSatuan2();
        } else if (this.penjualanTableModel.getValueAt(row, 3).equals(this.barang.getSatuan3())) {
            this.quantity3.setText(this.penjualanTableModel.getValueAt(row, 2).toString());
            this.quantity3.requestFocus();
            this.quantity3.selectAll();
            this.tempSatuan = this.barang.getSatuan3();
        }
        this.satuan4.setText(this.penjualanTableModel.getValueAt(row, 3).toString());
        this.hargaSatuanField.setValue(this.penjualanTableModel.getValueAt(row, 4));
        this.totalHargaField.setValue(this.penjualanTableModel.getValueAt(row, 5));
        this.discount2Field.setValue(this.penjualanTableModel.getValueAt(row, 6));
        this.nettoHargaField.setValue(Long.parseLong(this.penjualanTableModel.getValueAt(row, 7).toString()));
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.kodeBarangField = new JTextField();
        this.namaBarangField = new JTextField();
        this.jSeparator1 = new JSeparator();
        this.beliTerakhirField = new JFormattedTextField();
        this.jLabel8 = new JLabel();
        this.hargaSatuanField = new JFormattedTextField();
        this.satuan4 = new JTextField();
        this.jLabel4 = new JLabel();
        this.totalHargaField = new JFormattedTextField();
        this.jLabel6 = new JLabel();
        this.discount2Field = new JFormattedTextField();
        this.jLabel10 = new JLabel();
        this.nettoHargaField = new JFormattedTextField();
        this.exitButton = new JButton();
        this.jLabel7 = new JLabel();
        this.satuan1 = new JLabel();
        this.satuan2 = new JLabel();
        this.satuan3 = new JLabel();
        this.quantity1 = new JTextField();
        this.quantity2 = new JTextField();
        this.quantity3 = new JTextField();
        this.beliTerakhirSatuanLbl = new JLabel();
        this.jLabel17 = new JLabel();
        this.jLabel16 = new JLabel();
        this.jLabel11 = new JLabel();
        this.konversiLbl1 = new JLabel();
        this.konversiLbl3 = new JLabel();
        this.jLabel20 = new JLabel();
        this.konversiLbl4 = new JLabel();
        this.konversiField1 = new JTextField();
        this.jLabel21 = new JLabel();
        this.konversiLbl2 = new JLabel();
        this.konversiField2 = new JTextField();
        this.promoLbl = new JLabel();
        this.promoLbl2 = new JLabel();
        this.beliTerakhir = new JCheckBox();
        this.mixLbl = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.jLabel2.setText("Nama Barang");
        this.jLabel3.setText("Quantity");
        this.kodeBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanDetailDialog1.this.kodeBarangFieldKeyPressed(evt);
            }
        });
        this.namaBarangField.setEditable(false);
        this.namaBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent evt) {
                PenjualanDetailDialog1.this.namaBarangFieldKeyReleased(evt);
            }
        });
        this.jSeparator1.setOrientation(1);
        this.beliTerakhirField.setEditable(false);
        this.beliTerakhirField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.beliTerakhirField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanDetailDialog1.this.beliTerakhirFieldKeyPressed(evt);
            }
        });
        this.jLabel8.setText("Harga /");
        this.hargaSatuanField.setEditable(false);
        this.hargaSatuanField.setText("0");
        this.hargaSatuanField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanDetailDialog1.this.hargaSatuanFieldKeyPressed(evt);
            }
        });
        this.satuan4.setEditable(false);
        this.jLabel4.setText("Total");
        this.totalHargaField.setEditable(false);
        this.totalHargaField.setText("0");
        this.totalHargaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanDetailDialog1.this.totalHargaFieldKeyPressed(evt);
            }
        });
        this.jLabel6.setText("Discount");
        this.discount2Field.setText("0");
        this.discount2Field.setEnabled(false);
        this.jLabel10.setText("Netto");
        this.nettoHargaField.setEditable(false);
        this.nettoHargaField.setText("0");
        this.nettoHargaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanDetailDialog1.this.nettoHargaFieldKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanDetailDialog1.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanDetailDialog1.this.exitButtonKeyPressed(evt);
            }
        });
        this.jLabel7.setText("Cari : F2");
        this.quantity1.setText("0");
        this.quantity1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanDetailDialog1.this.quantity1KeyPressed(evt);
            }
        });
        this.quantity2.setText("0");
        this.quantity2.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanDetailDialog1.this.quantity2KeyPressed(evt);
            }
        });
        this.quantity3.setText("0");
        this.quantity3.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanDetailDialog1.this.quantity3KeyPressed(evt);
            }
        });
        this.beliTerakhirSatuanLbl.setText("-");
        this.jLabel17.setText("1");
        this.jLabel16.setText("1");
        this.jLabel11.setText("Konversi:");
        this.konversiLbl1.setText(" ");
        this.konversiLbl3.setText(" ");
        this.jLabel20.setText("=");
        this.konversiLbl4.setText(" ");
        this.konversiField1.setEditable(false);
        this.jLabel21.setText("=");
        this.konversiLbl2.setText(" ");
        this.konversiField2.setEditable(false);
        this.promoLbl.setFont(new Font("Tahoma", 1, 14));
        this.promoLbl.setForeground(new Color(255, 0, 0));
        this.promoLbl.setText("-");
        this.promoLbl2.setFont(new Font("Tahoma", 1, 14));
        this.promoLbl2.setForeground(new Color(255, 0, 0));
        this.promoLbl2.setText("-");
        this.beliTerakhir.setText("Tampilkan Beli Terakhir");
        this.mixLbl.setForeground(new Color(255, 51, 51));
        this.mixLbl.setText("-");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1).addComponent(this.jLabel3).addComponent(this.jLabel4).addGroup(layout.createSequentialGroup().addComponent(this.jLabel8).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.satuan4, -2, 39, -2)).addComponent(this.jLabel6).addComponent(this.jLabel10)).addGap(23, 23, 23).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.quantity1, -1, 80, Short.MAX_VALUE).addComponent(this.quantity2).addComponent(this.quantity3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuan1, -1, 43, Short.MAX_VALUE).addComponent(this.satuan2, -1, -1, Short.MAX_VALUE).addComponent(this.satuan3, -1, -1, Short.MAX_VALUE))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.totalHargaField, GroupLayout.Alignment.LEADING).addComponent(this.hargaSatuanField, GroupLayout.Alignment.LEADING, -1, 119, Short.MAX_VALUE).addComponent(this.discount2Field).addComponent(this.nettoHargaField))).addGap(18, 18, 18).addComponent(this.jSeparator1, -2, 12, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.beliTerakhirField, -2, 124, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.beliTerakhirSatuanLbl, -1, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel17).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.konversiLbl3, -1, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel16).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.konversiLbl1, -1, -1, Short.MAX_VALUE)).addComponent(this.jLabel11)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel20).addComponent(this.jLabel21, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.konversiField1).addComponent(this.konversiField2, -2, 51, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.konversiLbl4, -1, -1, Short.MAX_VALUE).addComponent(this.konversiLbl2, -1, -1, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addComponent(this.beliTerakhir).addGap(0, 69, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addComponent(this.kodeBarangField, -2, 138, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel7).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.mixLbl, -1, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaBarangField, -2, 215, -2).addComponent(this.exitButton, -2, 234, -2)).addGap(0, 131, Short.MAX_VALUE)))).addComponent(this.promoLbl, -1, -1, Short.MAX_VALUE).addComponent(this.promoLbl2, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.promoLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.promoLbl2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeBarangField, -2, -1, -2).addComponent(this.jLabel7).addComponent(this.mixLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaBarangField, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(1, 1, 1).addComponent(this.beliTerakhir).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.beliTerakhirField).addComponent(this.beliTerakhirSatuanLbl, -2, 20, -2)).addGap(18, 18, 18).addComponent(this.jLabel11).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel16).addComponent(this.konversiLbl1).addComponent(this.jLabel20).addComponent(this.konversiField1, -2, -1, -2).addComponent(this.konversiLbl2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel17).addComponent(this.konversiLbl3).addComponent(this.jLabel21).addComponent(this.konversiField2, -2, -1, -2).addComponent(this.konversiLbl4))).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.quantity1, -2, -1, -2)).addComponent(this.satuan1, -2, 20, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuan2, -1, -1, Short.MAX_VALUE).addComponent(this.quantity2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.quantity3, -2, -1, -2).addComponent(this.satuan3, -2, 20, -2)).addGap(16, 16, 16).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hargaSatuanField, -2, -1, -2).addComponent(this.satuan4, -2, -1, -2).addComponent(this.jLabel8)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.totalHargaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.discount2Field, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel10).addComponent(this.nettoHargaField, -2, -1, -2))).addComponent(this.jSeparator1, -2, 202, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE).addComponent(this.exitButton, -2, -1, -2).addContainerGap()));
        this.pack();
    }

    private void kodeBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.kodeBarangField.getText().equals("")) {
                this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
                this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
                int w = this.sd.getWidth();
                int h = this.sd.getHeight();
                this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                this.sd.setVisible(true);
            } else {
                int isvoid = this.penjualanSementaraService.isVoid(this.helper.getKasir(), this.shortcut, this.kodeBarangField.getText());
                if (isvoid == 1) {
                    JOptionPane.showMessageDialog(null, "Item Void Tidak Dapat Diubah");
                } else {
                    this.loadKodeBarang(this.kodeBarangField.getText());
                    if (this.barang != null && !this.barang.getJenis().equals("DISCONTINUE")) {
                        this.searchPenjualan(this.barang.getId());
                    }
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void quantity1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 40) {
            if (this.quantity1.getText().equals("") || Float.parseFloat(this.quantity1.getText()) == 0.0f) {
                if (!this.satuan2.getText().equals("")) {
                    this.quantity2.requestFocus();
                    this.quantity2.selectAll();
                    this.quantity1.setBackground(Color.white);
                    this.quantity1.setText("0");
                    this.quantity2.setEditable(true);
                    this.quantity3.setEditable(true);
                }
            } else if (!Helper.getInstance().isInteger(this.quantity1.getText())) {
                this.quantity1.setBackground(Color.red);
            } else if (Integer.parseInt(this.quantity1.getText()) > 500000) {
                this.quantity1.setBackground(Color.red);
            } else if (Float.parseFloat(this.quantity1.getText()) < 0.0f) {
                JOptionPane.showMessageDialog(null, "Masukkan Angka Dengan Benar");
            } else if (this.kodeBarangField.getText().equals("MEMBER") && Float.parseFloat(this.quantity1.getText()) > 1.0f) {
                JOptionPane.showMessageDialog(null, "Member hanya bisa diinputkan 1pcs");
            } else if (this.kodeBarangField.getText().equals("MEMBER") && this.penjualanDetailService.getPenjualanMemberByCustomer(this.customerId) > 0) {
                JOptionPane.showMessageDialog(null, "Member sudah pernah diinputkan");
            } else {
                boolean opkresek = true;
                if (this.barang.getParentId() != null && this.barang.getParentId().equals("OPKRESEK")) {
                    float jumlahBarang = this.penjualanSementaraService.cekJumlahByParentId(this.helper.getKasir(), this.shortcut, this.barang.getSatuan1(), this.barang.getParentId());
                    float totalBarang = this.penjualanSementaraService.sumBarang(this.helper.getKasir(), this.shortcut, this.barang.getId(), this.barang.getSatuan1());
                    if ((jumlahBarang = jumlahBarang - totalBarang + Float.parseFloat(this.quantity1.getText())) > 4.0f) {
                        opkresek = false;
                        JOptionPane.showMessageDialog(null, "OP Kresek melebihi batas");
                    } else {
                        this.sat1pres(true);
                    }
                } else {
                    this.sat1pres(true);
                }
            }
            this.listener.changeButtonPromoTebusMurah();
            List<Object[]> promos = this.promoTebusDetailService.getPromoTebusDetailByBarangId(this.barang.getId(), this.barang.getSatuan1());
            if (promos.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                if (Float.parseFloat(stringBuilder.append(promos.get(0)[4]).append("").toString()) <= Float.parseFloat(this.quantity1.getText())) {
                    this.listener.changeButtonPromoTebus("add", promos);
                    return;
                }
            }
            this.listener.changeButtonPromoTebus("remove", promos);
            return;
        }
        if (evt.getKeyCode() != 27) {
            if (evt.getKeyCode() != 38) return;
        }
        this.escapeClick();
    }

    private void hargaSatuanFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 33) {
            long a = 0L;
            a = (Long)this.hargaSatuanField.getValue();
            long b = a % 500L;
            long c = 500L - b;
            long d = 0L;
            if (Float.parseFloat(this.quantity1.getText()) > 0.0f) {
                d = b <= 200L ? ((Long)this.hargaSatuanField.getValue() >= 9000L ? a + c + 500L : a + c) : ((Long)this.hargaSatuanField.getValue() >= 9000L ? a + c + 1000L : a + c + 500L);
            } else if ((Long)this.hargaSatuanField.getValue() >= 50000L) {
                d = a + 1000L;
            } else if ((Long)this.hargaSatuanField.getValue() < 50000L) {
                d = a + 500L;
            }
            this.hargaSatuanField.setValue(d);
        } else if (evt.getKeyCode() == 34) {
            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
            passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
            int w = passwordInputDialog2.getWidth();
            int h = passwordInputDialog2.getHeight();
            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            passwordInputDialog2.setJudulText("Input Password Untuk Merubah Harga");
            passwordInputDialog2.setVisible(true);
        } else if (evt.getKeyCode() == 32) {
            String otpMethod = this.ks.getConstantValue("otpUbahHarga");
            if (!"new".equals(otpMethod)) {
                PasswordInputDialog passwordInputDialog3 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog3.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
                int w = passwordInputDialog3.getWidth();
                int h = passwordInputDialog3.getHeight();
                passwordInputDialog3.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog3.setJudulText("Input Password Untuk Merubah Harga");
                passwordInputDialog3.setVisible(true);
            } else {
                boolean canGo = true;
                OTPUbahHarga ouh = new OTPUbahHarga();
                ouh.setM_customer_id(this.customerId);
                ouh.setId_sku(this.barang.getId());
                float q1 = Float.parseFloat(this.quantity1.getText());
                float q2 = Float.parseFloat(this.quantity2.getText());
                float q3 = Float.parseFloat(this.quantity3.getText());
                if (q1 != 0.0f) {
                    ouh.setJumlah(q1);
                    ouh.setSatuan(this.barang.getSatuan1());
                } else if (q2 != 0.0f) {
                    ouh.setJumlah(q2);
                    ouh.setSatuan(this.barang.getSatuan2());
                } else if (q3 != 0.0f) {
                    ouh.setJumlah(q3);
                    ouh.setSatuan(this.barang.getSatuan3());
                } else {
                    canGo = false;
                    JOptionPane.showMessageDialog(null, "Harap isi quantity 1 / quantity 2 / quantity 3");
                }
                ouh.setHarga(0L);
                ouh.setId_cabang(this.idCabang);
                ouh.setM_karyawan_id(this.karyawan.getId());
                if (canGo) {
                    System.out.println(ouh.getM_customer_id());
                    RequestOTPUbahHargaDialog requestOTPUbahHargaDialog = new RequestOTPUbahHargaDialog(null, true, "OTP Ubah Harga");
                    requestOTPUbahHargaDialog.addRequestOTPUbahHargaDialogListener(new RequestOTPUbahHargaDialogEventHandler());
                    requestOTPUbahHargaDialog.setOTPUbahHarga(ouh);
                    int w = requestOTPUbahHargaDialog.getWidth();
                    int h = requestOTPUbahHargaDialog.getHeight();
                    requestOTPUbahHargaDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                    requestOTPUbahHargaDialog.setJudulText("Masukkan Perubahan harga untuk OTP Request");
                    requestOTPUbahHargaDialog.setVisible(true);
                }
            }
        } else if (evt.getKeyCode() == 10) {
            if (this.helper.isFloat(this.hargaSatuanField.getText().toString())) {
                this.lastPrice = 0L;
                if (this.satuan4.getText().equals(this.barang.getSatuan1())) {
                    this.lastPrice = this.barang.getHpp();
                    this.tempField = this.quantity1;
                } else if (this.satuan4.getText().equals(this.barang.getSatuan2())) {
                    this.lastPrice = this.barang.getHpp() * (long)this.barang.getKonversi21();
                    this.tempField = this.quantity2;
                } else if (this.satuan4.getText().equals(this.barang.getSatuan3())) {
                    this.lastPrice = this.barang.getHpp() * (long)this.barang.getKonversi21() * (long)this.barang.getKonversi32();
                    this.tempField = this.quantity3;
                }
                if (this.hargaSatuanField.getText().equals("1")) {
                    PembelianDetail pembelianDetail = this.pembelianDetailService.getPembelianTerakhir(this.barang.getId());
                    if (pembelianDetail != null) {
                        Pembelian pembelian = this.pembelianService.getPembelian(pembelianDetail.getPembelianId());
                        JOptionPane.showMessageDialog(null, "Harga Beli Terakhir " + this.lastPrice + " /" + this.satuan4.getText() + '\n' + this.supplierService.getSupplier(pembelian.getSupplierId()).getNama() + '\n' + pembelian.getTanggal());
                    }
                } else {
                    if (!this.notaKhusus && this.lastPrice >= Long.parseLong(this.hargaSatuanField.getText().replace(".", ""))) {
                        this.hargaSatuanField.setBackground(Color.red);
                    }
                    float totalHarga = Float.parseFloat(this.tempField.getText()) * Float.parseFloat(this.hargaSatuanField.getText());
                    this.totalHargaField.setValue((long)totalHarga);
                    if (Float.parseFloat(this.discount2Field.getValue().toString()) > 0.0f) {
                        float priceAfterDisc = Float.parseFloat(this.totalHargaField.getValue().toString()) - Float.parseFloat(this.discount2Field.getValue().toString());
                        this.nettoHargaField.setValue((long)priceAfterDisc);
                    } else {
                        this.nettoHargaField.setValue((long)totalHarga);
                    }
                }
            } else {
                this.hargaSatuanField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 38) {
            this.tempField.requestFocus();
            this.tempField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void quantity2KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 40) {
            float jumlahBarang = 0.0f;
            float jumlahTempQty = 0.0f;
            if (this.barang.getKonversi21() > 0) {
                jumlahBarang = Float.parseFloat(this.quantity2.getText()) * (float)this.barang.getKonversi21();
            }
            if (this.quantity2.getText().equals("") || Float.parseFloat(this.quantity2.getText()) == 0.0f) {
                if (!this.satuan3.getText().equals("")) {
                    this.quantity3.requestFocus();
                    this.quantity3.selectAll();
                    this.quantity2.setBackground(Color.white);
                    this.quantity2.setText("0");
                    this.quantity3.setEditable(true);
                }
            } else if (!Helper.getInstance().isInteger(this.quantity2.getText())) {
                this.quantity2.setBackground(Color.red);
            } else if (Integer.parseInt(this.quantity2.getText()) > 500000) {
                this.quantity2.setBackground(Color.red);
            } else if (Float.parseFloat(this.quantity2.getText()) < 0.0f) {
                JOptionPane.showMessageDialog(null, "Masukkan Angka Dengan Benar");
            } else if (Float.parseFloat(this.quantity2.getText()) % 1.0f != 0.0f) {
                Float qty1 = Float.valueOf(Float.parseFloat(this.quantity2.getText()) * (float)this.barang.getKonversi21());
                if (qty1.floatValue() % 1.0f != 0.0f) {
                    JOptionPane.showMessageDialog(null, "Masukkan jumlah barang dengan benar");
                } else {
                    this.sat2pres(true);
                }
            } else {
                this.sat2pres(true);
            }
            this.listener.changeButtonPromoTebusMurah();
            List<Object[]> promos = this.promoTebusDetailService.getPromoTebusDetailByBarangId(this.barang.getId(), this.barang.getSatuan2());
            if (promos.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                if (Float.parseFloat(stringBuilder.append(promos.get(0)[4]).append("").toString()) <= Float.parseFloat(this.quantity2.getText())) {
                    this.listener.changeButtonPromoTebus("add", promos);
                    return;
                }
            }
            this.listener.changeButtonPromoTebus("remove", promos);
            return;
        }
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
            return;
        }
        if (evt.getKeyCode() != 38) return;
        this.quantity1.requestFocus();
        this.quantity1.selectAll();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void quantity3KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 40) {
            float jumlahBarang = 0.0f;
            float jumlahTempQty = 0.0f;
            if (this.barang.getKonversi32() > 0) {
                jumlahBarang = Float.parseFloat(this.quantity3.getText()) * (float)this.barang.getKonversi21() * (float)this.barang.getKonversi32();
            }
            if (this.tempSatBarang.equals(this.barang.getSatuan2())) {
                jumlahTempQty = this.tempQty * (float)this.barang.getKonversi21();
            } else if (this.tempSatBarang.equals(this.barang.getSatuan3())) {
                jumlahTempQty = this.tempQty * (float)this.barang.getKonversi21() * (float)this.barang.getKonversi32();
            }
            if (this.quantity3.getText().equals("") || Float.parseFloat(this.quantity3.getText()) == 0.0f) {
                this.quantity3.setBackground(Color.white);
            } else if (!Helper.getInstance().isInteger(this.quantity3.getText())) {
                this.quantity3.setBackground(Color.red);
            } else if (Integer.parseInt(this.quantity3.getText()) > 500000) {
                this.quantity3.setBackground(Color.red);
            } else if (Float.parseFloat(this.quantity3.getText()) < 0.0f) {
                JOptionPane.showMessageDialog(null, "Masukkan Angka Dengan Benar");
            } else {
                this.sat3pres(true);
            }
            this.listener.changeButtonPromoTebusMurah();
            List<Object[]> promos = this.promoTebusDetailService.getPromoTebusDetailByBarangId(this.barang.getId(), this.barang.getSatuan3());
            if (promos.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                if (Float.parseFloat(stringBuilder.append(promos.get(0)[4]).append("").toString()) <= Float.parseFloat(this.quantity3.getText())) {
                    this.listener.changeButtonPromoTebus("add", promos);
                    return;
                }
            }
            this.listener.changeButtonPromoTebus("remove", promos);
            return;
        }
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
            return;
        }
        if (evt.getKeyCode() != 38) return;
        this.quantity2.requestFocus();
        this.quantity2.selectAll();
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void namaBarangFieldKeyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void totalHargaFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void nettoHargaFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void beliTerakhirFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void searchPenjualan(String idBarang) {
        String satuan = "";
        boolean find = false;
        for (int a = 0; a < this.penjualanTableModel.getRowCount(); ++a) {
            String barangId = this.penjualanTableModel.getValueAt(a, 0).toString();
            if (!idBarang.equals(barangId) || !(Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2).toString()) > 0.0f)) continue;
            find = true;
            this.loadPenjualan(a);
            break;
        }
        if (!find) {
            this.tempQty = 0.0f;
            Barcode tempBarcode = this.barcodeService.getBarcode(this.kodeBarangField.getText());
            if (tempBarcode != null) {
                if (tempBarcode.getSatuan().equals(this.barang.getSatuan1())) {
                    this.quantity1.requestFocus();
                    this.quantity1.selectAll();
                } else if (tempBarcode.getSatuan().equals(this.barang.getSatuan2())) {
                    this.quantity2.requestFocus();
                    this.quantity2.selectAll();
                } else if (tempBarcode.getSatuan().equals(this.barang.getSatuan3())) {
                    this.quantity2.requestFocus();
                    this.quantity2.selectAll();
                }
            } else {
                this.quantity1.requestFocus();
                this.quantity1.selectAll();
            }
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            PenjualanDetailDialog1.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            PenjualanDetailDialog1.this.canGoThrough = false;
        }
    }

    private class RequestOTPUbahHargaDialogEventHandler
    implements RequestOTPUbahHargaListener {
        private RequestOTPUbahHargaDialogEventHandler() {
        }

        @Override
        public void requestOTPUbahHargaSucceed(OTPUbahHarga otpUbahHarga) {
            PenjualanDetailDialog1.this.hargaSatuanField.setValue(otpUbahHarga.getHarga());
            PenjualanDetailDialog1.this.discount2Field.setValue(0);
            String str = Integer.toString((int)otpUbahHarga.getJumlah());
            if (PenjualanDetailDialog1.this.barang.getSatuan1() == null ? otpUbahHarga.getSatuan() == null : PenjualanDetailDialog1.this.barang.getSatuan1().equals(otpUbahHarga.getSatuan())) {
                PenjualanDetailDialog1.this.quantity1.setText(str);
            } else if (PenjualanDetailDialog1.this.barang.getSatuan2() == null ? otpUbahHarga.getSatuan() == null : PenjualanDetailDialog1.this.barang.getSatuan2().equals(otpUbahHarga.getSatuan())) {
                PenjualanDetailDialog1.this.quantity2.setText(str);
            } else if (PenjualanDetailDialog1.this.barang.getSatuan3() == null ? otpUbahHarga.getSatuan() == null : PenjualanDetailDialog1.this.barang.getSatuan3().equals(otpUbahHarga.getSatuan())) {
                PenjualanDetailDialog1.this.quantity3.setText(str);
            } else {
                PenjualanDetailDialog1.this.quantity1.setText(str);
            }
            PenjualanDetailDialog1.this.quantity1.setEditable(false);
            PenjualanDetailDialog1.this.quantity2.setEditable(false);
            PenjualanDetailDialog1.this.quantity3.setEditable(false);
            PenjualanDetailDialog1.this.totalHargaField.setValue(otpUbahHarga.getHarga() * (long)Math.round(otpUbahHarga.getJumlah()));
            PenjualanDetailDialog1.this.nettoHargaField.setValue(otpUbahHarga.getHarga() * (long)Math.round(otpUbahHarga.getJumlah()));
            PenjualanDetailDialog1.this.hargaSatuanField.requestFocus();
            if (!(Helper.getInstance().isFloat(PenjualanDetailDialog1.this.quantity1.getText()) && Helper.getInstance().isFloat(PenjualanDetailDialog1.this.quantity2.getText()) && Helper.getInstance().isFloat(PenjualanDetailDialog1.this.quantity3.getText()))) {
                JOptionPane.showMessageDialog(null, "Isi Data Dengan Benar");
            } else if (!PenjualanDetailDialog1.this.hargaPromo && !PenjualanDetailDialog1.this.notaKhusus && PenjualanDetailDialog1.this.lastPrice >= Long.parseLong(PenjualanDetailDialog1.this.hargaSatuanField.getText().replace(".", ""))) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordSaveEventHandler());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((PenjualanDetailDialog1.this.screenWidth - w) / 2, (PenjualanDetailDialog1.this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Harga Jual Lebih Rendah Dr Harga Beli");
                passwordInputDialog2.setVisible(true);
            } else {
                PenjualanDetailDialog1.this.saveClick(true, "");
            }
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR")) {
                PenjualanDetailDialog1.this.hargaSatuanField.setEditable(true);
                PenjualanDetailDialog1.this.hargaSatuanField.setText(PenjualanDetailDialog1.this.hargaSatuanField.getText().replace(".", ""));
                PenjualanDetailDialog1.this.hargaSatuanField.selectAll();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputDialogEventHandler3
    implements ConfirmationDialogListener {
        private passwordInputDialogEventHandler3() {
        }

        @Override
        public void yesClick() {
            PenjualanDetailDialog1.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            PenjualanDetailDialog1.this.canGoThrough = false;
        }
    }

    private class passwordInputDialogForQuantity
    implements PasswordInputListener {
        private passwordInputDialogForQuantity() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                KelebihanStok kelebihanStok = new KelebihanStok();
                float stokInput = 0.0f;
                if (PenjualanDetailDialog1.this.tempSatuan.equalsIgnoreCase("SAT1")) {
                    PenjualanDetailDialog1.this.sat1pres(true);
                    stokInput = Float.parseFloat(PenjualanDetailDialog1.this.quantity1.getText());
                } else if (PenjualanDetailDialog1.this.tempSatuan.equalsIgnoreCase("SAT2")) {
                    PenjualanDetailDialog1.this.sat2pres(true);
                    stokInput = Float.parseFloat(PenjualanDetailDialog1.this.quantity2.getText()) * (float)PenjualanDetailDialog1.this.barang.getKonversi21();
                } else if (PenjualanDetailDialog1.this.tempSatuan.equalsIgnoreCase("SAT3")) {
                    PenjualanDetailDialog1.this.sat3pres(true);
                    stokInput = Float.parseFloat(PenjualanDetailDialog1.this.quantity3.getText()) * (float)PenjualanDetailDialog1.this.barang.getKonversi21() * (float)PenjualanDetailDialog1.this.barang.getKonversi32();
                }
                kelebihanStok.setBarangId(PenjualanDetailDialog1.this.barang.getId());
                kelebihanStok.setStokSystem(PenjualanDetailDialog1.this.barang.getStokToko());
                kelebihanStok.setStokInput(stokInput);
                kelebihanStok.setInputOleh(PenjualanDetailDialog1.this.helper.getKaryawan().getNama());
                kelebihanStok.setOtorisasiOleh(karyawan.getNama());
                kelebihanStok.setCreated(new Date());
                kelebihanStok.setKeterangan("PENJUALAN");
                PenjualanDetailDialog1.this.kelebihanStokService.createKelebihanStok(kelebihanStok);
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordVoidInputDialogEventHandler
    implements PasswordInputListener {
        private passwordVoidInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                if (PenjualanDetailDialog1.this.tempSatuan.equalsIgnoreCase("SAT1")) {
                    PenjualanDetailDialog1.this.sat1pres(true);
                } else if (PenjualanDetailDialog1.this.tempSatuan.equalsIgnoreCase("SAT2")) {
                    PenjualanDetailDialog1.this.sat2pres(true);
                } else if (PenjualanDetailDialog1.this.tempSatuan.equalsIgnoreCase("SAT3")) {
                    PenjualanDetailDialog1.this.sat3pres(true);
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputDialogEventHandler2
    implements PasswordInputListener {
        private passwordInputDialogEventHandler2() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                if (PenjualanDetailDialog1.this.tempSatuan.equalsIgnoreCase("SAT1")) {
                    float cekQty = PenjualanDetailDialog1.this.penjualanSementaraService.sumBarang(PenjualanDetailDialog1.this.helper.getKasir(), PenjualanDetailDialog1.this.shortcut, PenjualanDetailDialog1.this.barang.getId(), PenjualanDetailDialog1.this.barang.getSatuan1());
                    if (Float.parseFloat(PenjualanDetailDialog1.this.quantity1.getText()) * -1.0f <= cekQty) {
                        PenjualanDetailDialog1.this.sat1pres(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Masukkan quantity VOID dengan benar");
                    }
                } else if (PenjualanDetailDialog1.this.tempSatuan.equalsIgnoreCase("SAT2")) {
                    float cekQty = PenjualanDetailDialog1.this.penjualanSementaraService.sumBarang(PenjualanDetailDialog1.this.helper.getKasir(), PenjualanDetailDialog1.this.shortcut, PenjualanDetailDialog1.this.barang.getId(), PenjualanDetailDialog1.this.barang.getSatuan2());
                    System.out.println("cekqty=" + cekQty);
                    System.out.println("quantity2=" + PenjualanDetailDialog1.this.quantity2.getText());
                    if (Float.parseFloat(PenjualanDetailDialog1.this.quantity2.getText()) * -1.0f <= cekQty) {
                        PenjualanDetailDialog1.this.sat2pres(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Masukkan quantity VOID dengan benar");
                    }
                } else if (PenjualanDetailDialog1.this.tempSatuan.equalsIgnoreCase("SAT3")) {
                    float cekQty = PenjualanDetailDialog1.this.penjualanSementaraService.sumBarang(PenjualanDetailDialog1.this.helper.getKasir(), PenjualanDetailDialog1.this.shortcut, PenjualanDetailDialog1.this.barang.getId(), PenjualanDetailDialog1.this.barang.getSatuan3());
                    if (Float.parseFloat(PenjualanDetailDialog1.this.quantity3.getText()) * -1.0f <= cekQty) {
                        PenjualanDetailDialog1.this.sat3pres(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Masukkan quantity VOID dengan benar");
                    }
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordSaveEventHandler
    implements PasswordInputListener {
        private passwordSaveEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR")) {
                PenjualanDetailDialog1.this.saveClick(true, "");
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            int isvoid = PenjualanDetailDialog1.this.penjualanSementaraService.isVoid(PenjualanDetailDialog1.this.helper.getKasir(), PenjualanDetailDialog1.this.shortcut, chooseValue);
            if (isvoid == 1) {
                JOptionPane.showMessageDialog(null, "Item Void Tidak Dapat Diubah");
            } else {
                List<Paket> tempPakets = PenjualanDetailDialog1.this.paketService.getPaketByBarangId(chooseValue);
                if (tempPakets.size() > 0) {
                    Barang tempbarang = PenjualanDetailDialog1.this.barangService.getBarang(tempPakets.get(0).getBarangId());
                    if (tempbarang.getStokToko() <= 0.0f) {
                        PenjualanDetailDialog1.this.tempChooseValue = chooseValue;
                        ConfirmationDialog passwordInputDialog2 = new ConfirmationDialog(null, true, "Stok " + tempbarang.getNama() + " masih kosong, lanjutkan?");
                        passwordInputDialog2.addListener(new passwordInputDialogEventHandler3());
                        int w = passwordInputDialog2.getWidth();
                        int h = passwordInputDialog2.getHeight();
                        passwordInputDialog2.setLocation((PenjualanDetailDialog1.this.screenWidth - w) / 2, (PenjualanDetailDialog1.this.screenHeight - h) / 2);
                        passwordInputDialog2.setVisible(true);
                        if (PenjualanDetailDialog1.this.canGoThrough) {
                            PenjualanDetailDialog1.this.loadKodeBarang(PenjualanDetailDialog1.this.tempChooseValue);
                            PenjualanDetailDialog1.this.searchPenjualan(PenjualanDetailDialog1.this.tempChooseValue);
                            PenjualanDetailDialog1.this.tempChooseValue = "";
                        }
                    } else {
                        PenjualanDetailDialog1.this.loadKodeBarang(chooseValue);
                        PenjualanDetailDialog1.this.searchPenjualan(chooseValue);
                    }
                } else {
                    PenjualanDetailDialog1.this.loadKodeBarang(chooseValue);
                    PenjualanDetailDialog1.this.searchPenjualan(chooseValue);
                }
            }
        }
    }

    private class searchBarangDialogEventHandler
    implements SearchDialogListener {
        private searchBarangDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE BARANG", "NAMA BARANG", "SATUAN", "JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Barang> barangs = PenjualanDetailDialog1.this.barangService.getBarangs("DISCONTINUE", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanelBarang sp = new SearchPanelBarang(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }
    }
}

