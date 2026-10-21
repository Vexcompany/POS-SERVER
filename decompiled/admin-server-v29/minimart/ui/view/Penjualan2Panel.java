/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.dao.CustomerDao;
import minimart.engine.daoimpl.CustomerDaoImpl;
import minimart.engine.model.Barang;
import minimart.engine.model.Customer;
import minimart.engine.model.OTPRevisiPenjualanDetail;
import minimart.engine.model.Penjualan;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.model.StockCommit;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerPointCommitService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.OTPRevisiPenjualanService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PenjualanDetailDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.PenjualanDetailDialog1;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class Penjualan2Panel
extends JPanel {
    private JTextField customerIdField;
    private JLabel customerNameField;
    private JButton detailButton;
    private JButton exitButton;
    private JTextField idPenjualanField;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JTextField otpField;
    private JTable penjualanTable;
    private JButton requestOTPButton;
    private JButton saveButton;
    private JFormattedTextField tanggalField;
    private JFormattedTextField totalField;
    private JTextField totalItemField;
    private int screenWidth;
    private int screenHeight;
    private DefaultPanelListener defaultPanelListener;
    private Penjualan penjualan;
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total", "Disc", "Harga Akhir"};
    private PenjualanService penjualanService = new PenjualanService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private OTPRevisiPenjualanService otpRevisiPenjualanService = new OTPRevisiPenjualanService();
    private BarangService barangService = new BarangService();
    private CustomerService customerService = new CustomerService();
    private Customer customer;
    private String lastCustomerId;
    private List<PenjualanDetail> penjualanDetails;
    private Helper helper = Helper.getInstance();
    private KonstantaService konstantaService = new KonstantaService();
    private long jumlahPenjualanLama;
    private float totalPointAwal = 0.0f;
    private long totalRokok = 0L;
    private float totalPoint;
    private boolean processing = false;
    private CommonEngine comm = new CommonEngine();
    private CustomerPointCommitService customerPointCommitService = new CustomerPointCommitService();
    private CustomerDao cd = new CustomerDaoImpl();
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();
    private DefaultTableModel penjualanTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public Penjualan2Panel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        TableColumn col1 = this.penjualanTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.penjualanTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.penjualanTable.getColumnModel().getColumn(3);
        int width = 20;
        col1.setPreferredWidth(130);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
        this.tanggalField.setValue(new Date());
    }

    public void setDefaultFocus() {
        this.idPenjualanField.requestFocusInWindow();
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void saveData() {
        this.refreshSummaryFromTable();
        OTPRevisiPenjualanService.ValidationResult validationResult = this.validateCurrentOtpBeforeSave();
        if (validationResult == null || !validationResult.isValid()) {
            return;
        }
        if (this.saveDataCore()) {
            int result = this.otpRevisiPenjualanService.updateStatusUsedOTPRevisiPenjualan(validationResult.getOtpRevisiPenjualan().getId());
            this.clearOtpState();
            if (result <= 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan, tetapi gagal menandai OTP sebagai used. Harap hubungi IT.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan revisi penjualan. OTP tetap bisa digunakan kembali selama masih berlaku.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean saveDataCore() {
        if (this.processing) {
            return false;
        }
        this.processing = true;
        this.totalRokok = 0L;
        try {
            int a;
            StockCommit stockCommit2;
            StockCommit stockCommit13;
            if (this.penjualan.getStatus().equals("TOKO")) {
                for (PenjualanDetail penjualanDetail : this.penjualanDetails) {
                    float jumlahBarang = 0.0f;
                    Barang tempBarang = this.barangService.getBarang(penjualanDetail.getBarangId());
                    if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan1())) {
                        tempBarang.setStokToko(tempBarang.getStokToko() + penjualanDetail.getJumlahBarang());
                        jumlahBarang = penjualanDetail.getJumlahBarang();
                    } else if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                        tempBarang.setStokToko(tempBarang.getStokToko() + penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21());
                        jumlahBarang = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21();
                    } else if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                        tempBarang.setStokToko(tempBarang.getStokToko() + penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32());
                        jumlahBarang = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32();
                    }
                    StockCommit stockCommit12 = new StockCommit();
                    stockCommit12.setIdBarang(tempBarang.getId());
                    stockCommit12.setIdTransaksi(this.penjualan.getId());
                    stockCommit12.setQueryType("tambah");
                    stockCommit12.setStockType("toko");
                    stockCommit12.setTransactionType("Penjualan II");
                    stockCommit12.setJumlahBarang(jumlahBarang);
                    this.sinkronisasiBarangService.insertCommit(stockCommit12);
                    if (this.barangService.updateBarang(tempBarang) > 0) continue;
                    boolean bl = false;
                    return bl;
                }
            } else if (this.penjualan.getStatus().equals("GUDANG")) {
                for (PenjualanDetail penjualanDetail : this.penjualanDetails) {
                    Barang tempBarang = this.barangService.getBarang(penjualanDetail.getBarangId());
                    float jumlahBarangGudang = 0.0f;
                    float jumlahBarangToko = 0.0f;
                    if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan1())) {
                        tempBarang.setStokGudang(tempBarang.getStokGudang() + penjualanDetail.getJumlahBarang());
                        jumlahBarangGudang = penjualanDetail.getJumlahBarang();
                        if (this.penjualan.getCustomerId().equals("SINAR")) {
                            tempBarang.setStokToko(tempBarang.getStokToko() - penjualanDetail.getJumlahBarang());
                            jumlahBarangToko = penjualanDetail.getJumlahBarang();
                        }
                    } else if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                        tempBarang.setStokGudang(tempBarang.getStokGudang() + penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21());
                        jumlahBarangGudang = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21();
                        if (this.penjualan.getCustomerId().equals("SINAR")) {
                            tempBarang.setStokToko(tempBarang.getStokToko() - penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21());
                            jumlahBarangToko = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21();
                        }
                    } else if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                        jumlahBarangGudang = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32();
                        tempBarang.setStokGudang(tempBarang.getStokGudang() + penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32());
                        if (this.penjualan.getCustomerId().equals("SINAR")) {
                            jumlahBarangToko = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32();
                            tempBarang.setStokToko(tempBarang.getStokToko() - penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32());
                        }
                    }
                    stockCommit13 = new StockCommit();
                    stockCommit13.setIdBarang(tempBarang.getId());
                    stockCommit13.setIdTransaksi(this.penjualan.getId());
                    stockCommit13.setQueryType("tambah");
                    stockCommit13.setStockType("gudang");
                    stockCommit13.setTransactionType("Penjualan II");
                    stockCommit13.setJumlahBarang(jumlahBarangGudang);
                    this.sinkronisasiBarangService.insertCommit(stockCommit13);
                    stockCommit2 = new StockCommit();
                    stockCommit2.setIdBarang(tempBarang.getId());
                    stockCommit2.setIdTransaksi(this.penjualan.getId());
                    stockCommit2.setQueryType("kurang");
                    stockCommit2.setStockType("toko");
                    stockCommit2.setTransactionType("Penjualan II");
                    stockCommit2.setJumlahBarang(jumlahBarangToko);
                    this.sinkronisasiBarangService.insertCommit(stockCommit2);
                    if (this.barangService.updateBarang(tempBarang) > 0) continue;
                    boolean bl = false;
                    return bl;
                }
            }
            if (this.penjualanDetails != null && !this.penjualanDetails.isEmpty() && this.penjualanDetailService.deletePenjualanDetailByPenjualanId(this.penjualan.getId()) <= 0) {
                boolean bl = false;
                return bl;
            }
            for (a = 0; a < this.penjualanTableModel.getRowCount(); ++a) {
                PenjualanDetail penjualanDetail;
                penjualanDetail = new PenjualanDetail();
                Barang barang = this.barangService.getBarang(this.penjualanTableModel.getValueAt(a, 0).toString());
                penjualanDetail.setPenjualanId(this.penjualan.getId());
                penjualanDetail.setBarangId(barang.getId());
                penjualanDetail.setJumlahBarang(Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2).toString()));
                penjualanDetail.setSatuan(this.penjualanTableModel.getValueAt(a, 3).toString());
                penjualanDetail.setDiscount2(Long.parseLong(this.penjualanTableModel.getValueAt(a, 6).toString()));
                penjualanDetail.setHargaJual(Long.parseLong(this.penjualanTableModel.getValueAt(a, 4).toString()));
                penjualanDetail.setTotalHarga(Long.parseLong(this.penjualanTableModel.getValueAt(a, 7).toString()));
                float totalBarang = 0.0f;
                if (this.penjualanTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan1())) {
                    totalBarang = Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2).toString());
                } else if (this.penjualanTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan2())) {
                    totalBarang = Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2).toString()) * (float)barang.getKonversi21();
                } else if (this.penjualanTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan3())) {
                    totalBarang = Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2).toString()) * (float)barang.getKonversi21() * (float)barang.getKonversi32();
                }
                float totalHpp = (float)barang.getHpp() * totalBarang;
                if (!this.customer.getId().equalsIgnoreCase("SINAR")) {
                    penjualanDetail.setTotalKeuntungan((long)((float)penjualanDetail.getTotalHarga() - totalHpp));
                }
                penjualanDetail.setCreated(new Date());
                penjualanDetail.setUpdated(new Date());
                if (this.penjualanDetailService.createdPenjualanDetail(penjualanDetail) <= 0) {
                    boolean stockCommit13 = false;
                    return stockCommit13;
                }
                this.penjualan.setCustomerId(this.customer.getId());
                if (this.penjualan.getStatus().equals("TOKO")) {
                    stockCommit13 = new StockCommit();
                    stockCommit13.setIdBarang(barang.getId());
                    stockCommit13.setIdTransaksi(this.penjualan.getId());
                    stockCommit13.setQueryType("kurang");
                    stockCommit13.setStockType("toko");
                    stockCommit13.setTransactionType("Penjualan II");
                    stockCommit13.setJumlahBarang(totalBarang);
                    this.sinkronisasiBarangService.insertCommit(stockCommit13);
                    barang.setStokToko(barang.getStokToko() - totalBarang);
                } else if (this.penjualan.getStatus().equals("GUDANG")) {
                    stockCommit13 = new StockCommit();
                    stockCommit13.setIdBarang(barang.getId());
                    stockCommit13.setIdTransaksi(this.penjualan.getId());
                    stockCommit13.setQueryType("kurang");
                    stockCommit13.setStockType("gudang");
                    stockCommit13.setTransactionType("Penjualan II");
                    stockCommit13.setJumlahBarang(totalBarang);
                    this.sinkronisasiBarangService.insertCommit(stockCommit13);
                    barang.setStokGudang(barang.getStokGudang() - totalBarang);
                    if (this.penjualan.getCustomerId().equals("SINAR")) {
                        stockCommit2 = new StockCommit();
                        stockCommit2.setIdBarang(barang.getId());
                        stockCommit2.setIdTransaksi(this.penjualan.getId());
                        stockCommit2.setQueryType("tambah");
                        stockCommit2.setStockType("toko");
                        stockCommit2.setTransactionType("Penjualan II");
                        stockCommit2.setJumlahBarang(totalBarang);
                        this.sinkronisasiBarangService.insertCommit(stockCommit2);
                        barang.setStokToko(barang.getStokToko() + totalBarang);
                    }
                }
                if (this.barangService.updateBarang(barang) <= 0) {
                    boolean stockCommit14 = false;
                    return stockCommit14;
                }
                if (!barang.getJenis().equals("ROKOK")) continue;
                this.totalRokok += penjualanDetail.getTotalHarga();
            }
            this.penjualan.setTotalPenjualan(this.getTotalFieldLongValue());
            this.penjualan.setKaryawanId(this.helper.getKaryawan().getId());
            this.penjualan.setUpdated(new Date());
            if (this.penjualanService.updatePenjualan(this.penjualan) <= 0) {
                a = 0;
                return a != 0;
            }
            String aktifkanPoint = this.konstantaService.getConstantValue("aktifkanPoint");
            if (aktifkanPoint.equals("YES") && !this.customer.getId().equals("C")) {
                long total = Math.round(((Long)this.totalField.getValue()).longValue());
                float rokokValue = Float.parseFloat(this.konstantaService.getConstantValue("pointRokok"));
                float lainValue = Float.parseFloat(this.konstantaService.getConstantValue("pointLain"));
                float pointRokok = (float)this.totalRokok / rokokValue;
                float pointLain = (float)(total - this.totalRokok) / lainValue;
                this.totalPoint = pointLain + pointRokok;
                if (this.customer.getId().equals(this.lastCustomerId)) {
                    this.customerService.updatePointCustomer((int)this.totalPoint - (int)this.totalPointAwal, this.customer.getId());
                    this.customerPointCommitService.insertPointCommit(this.customer.getId(), this.penjualan.getId(), (int)this.totalPoint - (int)this.totalPointAwal, "INSERT", "Penjualan");
                } else {
                    this.customerService.updatePointCustomer(-((int)this.totalPointAwal), this.lastCustomerId);
                    this.customerPointCommitService.insertPointCommit(this.cd.getCustomerById(this.lastCustomerId).getId(), this.penjualan.getId(), -((int)this.totalPointAwal), "INSERT", "Pengembalian");
                    this.customerService.updatePointCustomer((int)this.totalPoint, this.customer.getId());
                    this.customerPointCommitService.insertPointCommit(this.customer.getId(), this.penjualan.getId(), (int)this.totalPoint, "INSERT", "Pengembalian");
                }
            }
            this.penjualanDetails = this.penjualanDetailService.readPenjualanDetailByPenjualanId(this.penjualan.getId());
            PrinterJob job = PrinterJob.getPrinterJob();
            PageFormat pf = job.defaultPage();
            Paper p = pf.getPaper();
            p.setSize(p.getWidth(), p.getHeight() + 1500.0);
            p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
            pf.setPaper(p);
            Book book = new Book();
            if (this.penjualan.getCustomerId().equals("SINAR")) {
                book.append(new serverPrinter(), pf);
            } else {
                book.append(new printer(), pf);
            }
            job.setPageable(book);
            try {
                job.print();
            }
            catch (PrinterException printerException) {
                // empty catch block
            }
            boolean bl = true;
            return bl;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex);
            boolean bl = false;
            return bl;
        }
        finally {
            this.processing = false;
        }
    }

    private void saveData2() {
        StockCommit stockCommit2;
        StockCommit stockCommit1;
        float totalBarang;
        Barang tempBarang;
        if (this.penjualan.getStatus().equals("TOKO")) {
            for (PenjualanDetail penjualanDetail : this.penjualanDetails) {
                tempBarang = this.barangService.getBarang(penjualanDetail.getBarangId());
                totalBarang = 0.0f;
                if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan1())) {
                    totalBarang = penjualanDetail.getJumlahBarang();
                } else if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                    totalBarang = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21();
                } else if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                    totalBarang = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32();
                }
                StockCommit stockCommit12 = new StockCommit();
                stockCommit12.setIdBarang(tempBarang.getId());
                stockCommit12.setIdTransaksi(this.penjualan.getId());
                stockCommit12.setQueryType("tambah");
                stockCommit12.setStockType("toko");
                stockCommit12.setTransactionType("Penjualan II");
                stockCommit12.setJumlahBarang(totalBarang);
                this.sinkronisasiBarangService.insertCommit(stockCommit12);
                this.barangService.updateStokBarangToko(totalBarang, tempBarang.getId());
            }
        } else if (this.penjualan.getStatus().equals("GUDANG")) {
            for (PenjualanDetail penjualanDetail : this.penjualanDetails) {
                tempBarang = this.barangService.getBarang(penjualanDetail.getBarangId());
                float totalBarangGudang = 0.0f;
                float totalBarangToko = 0.0f;
                if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan1())) {
                    totalBarangGudang = penjualanDetail.getJumlahBarang();
                    if (this.penjualan.getCustomerId().equals("SINAR")) {
                        totalBarangToko = penjualanDetail.getJumlahBarang();
                    }
                } else if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                    totalBarangGudang = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21();
                    if (this.penjualan.getCustomerId().equals("SINAR")) {
                        totalBarangToko = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21();
                    }
                } else if (penjualanDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                    totalBarangGudang = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32();
                    if (this.penjualan.getCustomerId().equals("SINAR")) {
                        totalBarangToko = penjualanDetail.getJumlahBarang() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32();
                    }
                }
                stockCommit1 = new StockCommit();
                stockCommit1.setIdBarang(tempBarang.getId());
                stockCommit1.setIdTransaksi(this.penjualan.getId());
                stockCommit1.setQueryType("tambah");
                stockCommit1.setStockType("gudang");
                stockCommit1.setTransactionType("Penjualan II");
                stockCommit1.setJumlahBarang(totalBarangGudang);
                this.sinkronisasiBarangService.insertCommit(stockCommit1);
                stockCommit2 = new StockCommit();
                stockCommit2.setIdBarang(tempBarang.getId());
                stockCommit2.setIdTransaksi(this.penjualan.getId());
                stockCommit2.setQueryType("kurang");
                stockCommit2.setStockType("toko");
                stockCommit2.setTransactionType("Penjualan II");
                stockCommit2.setJumlahBarang(totalBarangToko);
                this.sinkronisasiBarangService.insertCommit(stockCommit1);
                this.barangService.updateStokBarangGudang(totalBarangGudang, tempBarang.getId());
                this.barangService.updateStokBarangToko(-1.0f * totalBarangToko, tempBarang.getId());
            }
        }
        this.penjualanDetailService.deletePenjualanDetailByPenjualanId(this.penjualan.getId());
        for (int a = 0; a < this.penjualanTableModel.getRowCount(); ++a) {
            PenjualanDetail penjualanDetail;
            penjualanDetail = new PenjualanDetail();
            Barang barang = this.barangService.getBarang(this.penjualanTableModel.getValueAt(a, 0).toString());
            penjualanDetail.setPenjualanId(this.penjualan.getId());
            penjualanDetail.setBarangId(barang.getId());
            penjualanDetail.setJumlahBarang(Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2).toString()));
            penjualanDetail.setSatuan(this.penjualanTableModel.getValueAt(a, 3).toString());
            penjualanDetail.setDiscount2(Long.parseLong(this.penjualanTableModel.getValueAt(a, 6).toString()));
            penjualanDetail.setHargaJual(Long.parseLong(this.penjualanTableModel.getValueAt(a, 4).toString()));
            penjualanDetail.setTotalHarga(Long.parseLong(this.penjualanTableModel.getValueAt(a, 7).toString()));
            totalBarang = 0.0f;
            if (this.penjualanTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan1())) {
                totalBarang = Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2).toString());
            } else if (this.penjualanTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan2())) {
                totalBarang = Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2).toString()) * (float)barang.getKonversi21();
            } else if (this.penjualanTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan3())) {
                totalBarang = Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2).toString()) * (float)barang.getKonversi21() * (float)barang.getKonversi32();
            }
            float totalHpp = (float)this.helper.getHargaBeliTerakhirSatuanTerkecil(barang.getId()) * totalBarang;
            if (!this.customer.getId().equalsIgnoreCase("SINAR")) {
                penjualanDetail.setTotalKeuntungan((long)((float)penjualanDetail.getTotalHarga() - totalHpp));
            }
            penjualanDetail.setCreated(new Date());
            penjualanDetail.setUpdated(new Date());
            this.penjualanDetailService.createdPenjualanDetail(penjualanDetail);
            this.penjualan.setCustomerId(this.customer.getId());
            if (this.penjualan.getStatus().equals("TOKO")) {
                stockCommit1 = new StockCommit();
                stockCommit1.setIdBarang(barang.getId());
                stockCommit1.setIdTransaksi(this.penjualan.getId());
                stockCommit1.setQueryType("kurang");
                stockCommit1.setStockType("toko");
                stockCommit1.setTransactionType("Penjualan II");
                stockCommit1.setJumlahBarang(totalBarang);
                this.sinkronisasiBarangService.insertCommit(stockCommit1);
                this.barangService.updateStokBarangToko(-totalBarang, barang.getId());
            } else if (this.penjualan.getStatus().equals("GUDANG")) {
                stockCommit1 = new StockCommit();
                stockCommit1.setIdBarang(barang.getId());
                stockCommit1.setIdTransaksi(this.penjualan.getId());
                stockCommit1.setQueryType("kurang");
                stockCommit1.setStockType("gudang");
                stockCommit1.setTransactionType("Penjualan II");
                stockCommit1.setJumlahBarang(totalBarang);
                this.sinkronisasiBarangService.insertCommit(stockCommit1);
                this.barangService.updateStokBarangGudang(-totalBarang, barang.getId());
                if (this.penjualan.getCustomerId().equals("SINAR")) {
                    stockCommit2 = new StockCommit();
                    stockCommit2.setIdBarang(barang.getId());
                    stockCommit2.setIdTransaksi(this.penjualan.getId());
                    stockCommit2.setQueryType("tambah");
                    stockCommit2.setStockType("toko");
                    stockCommit2.setTransactionType("Penjualan II");
                    stockCommit2.setJumlahBarang(totalBarang);
                    this.sinkronisasiBarangService.insertCommit(stockCommit2);
                    this.barangService.updateStokBarangToko(totalBarang, barang.getId());
                }
            }
            if (!barang.getJenis().equals("ROKOK")) continue;
            this.totalRokok += penjualanDetail.getTotalHarga();
        }
        this.penjualan.setTotalPenjualan((long)Float.parseFloat(this.totalField.getValue().toString()));
        this.penjualan.setKaryawanId(this.helper.getKaryawan().getId());
        this.penjualan.setUpdated(new Date());
        this.penjualanService.updatePenjualan(this.penjualan);
        String aktifkanPoint = this.konstantaService.getConstantValue("aktifkanPoint");
        if (aktifkanPoint.equals("YES") && !this.customer.getId().equals("C")) {
            long total = (Long)this.totalField.getValue();
            float rokokValue = Float.parseFloat(this.konstantaService.getConstantValue("pointRokok"));
            float lainValue = Float.parseFloat(this.konstantaService.getConstantValue("pointLain"));
            float pointRokok = (float)this.totalRokok / rokokValue;
            float pointLain = (float)(total - this.totalRokok) / lainValue;
            this.totalPoint = pointLain + pointRokok;
            this.customer.setPoint(this.customer.getPoint() - (long)this.totalPointAwal + (long)this.totalPoint);
            this.customerService.updateCustomer(this.customer);
        }
        this.penjualanDetails = this.penjualanDetailService.readPenjualanDetailByPenjualanId(this.penjualan.getId());
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper p = pf.getPaper();
        p.setSize(p.getWidth(), p.getHeight() + 1500.0);
        p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
        pf.setPaper(p);
        Book book = new Book();
        if (this.penjualan.getCustomerId().equals("SINAR")) {
            book.append(new serverPrinter(), pf);
        } else {
            book.append(new printer(), pf);
        }
        job.setPageable(book);
        try {
            job.print();
        }
        catch (PrinterException printerException) {
            // empty catch block
        }
    }

    private void refreshSummaryFromTable() {
        long total = 0L;
        for (int x = 0; x < this.penjualanTableModel.getRowCount(); ++x) {
            total += (long)Math.round(Float.parseFloat(this.penjualanTableModel.getValueAt(x, 7).toString()));
        }
        this.totalField.setValue(total);
        this.totalItemField.setText(this.penjualanTableModel.getRowCount() + "");
    }

    private void clearOtpState() {
        this.otpField.setText("");
    }

    private long getTotalFieldLongValue() {
        return Math.round(Float.parseFloat(this.totalField.getValue().toString()));
    }

    private void loadPenjualanKredit() {
        this.customer = this.customerService.getCustomerById(this.penjualan.getCustomerId());
        if (this.customer == null) {
            JOptionPane.showMessageDialog(null, "Data customer pada penjualan tidak ditemukan");
            return;
        }
        this.lastCustomerId = this.customer.getId();
        this.jumlahPenjualanLama = this.penjualan.getTotalPenjualan();
        this.loadData();
    }

    private boolean isPenjualanKreditLoaded() {
        return this.penjualan != null && "KREDIT".equalsIgnoreCase(this.penjualan.getKeterangan()) && this.customer != null;
    }

    private List<PenjualanDetail> getCurrentPenjualanDetailsFromTable() {
        ArrayList<PenjualanDetail> details = new ArrayList<PenjualanDetail>();
        for (int a = 0; a < this.penjualanTableModel.getRowCount(); ++a) {
            PenjualanDetail detail = new PenjualanDetail();
            detail.setPenjualanId(this.penjualan.getId());
            detail.setBarangId(this.penjualanTableModel.getValueAt(a, 0).toString());
            detail.setJumlahBarang(Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2).toString()));
            detail.setSatuan(this.penjualanTableModel.getValueAt(a, 3).toString());
            detail.setHargaJual(Long.parseLong(this.penjualanTableModel.getValueAt(a, 4).toString()));
            detail.setDiscount2(Long.parseLong(this.penjualanTableModel.getValueAt(a, 6).toString()));
            detail.setTotalHarga(Long.parseLong(this.penjualanTableModel.getValueAt(a, 7).toString()));
            details.add(detail);
        }
        return details;
    }

    private List<OTPRevisiPenjualanDetail> getCurrentOtpSnapshotDetails() {
        return this.otpRevisiPenjualanService.buildSnapshotDetails(this.penjualanDetails, this.getCurrentPenjualanDetailsFromTable());
    }

    private OTPRevisiPenjualanService.ValidationResult validateCurrentOtpBeforeSave() {
        if (!this.isPenjualanKreditLoaded()) {
            JOptionPane.showMessageDialog(null, "Load data penjualan kredit terlebih dahulu");
            return null;
        }
        if (this.penjualanTableModel.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "Data penjualan tidak boleh kosong");
            return null;
        }
        OTPRevisiPenjualanService.ValidationResult validationResult = this.otpRevisiPenjualanService.validateOTPRevisiPenjualan(this.otpField.getText(), this.penjualan.getId(), this.customer.getId(), this.penjualan.getTotalPenjualan(), this.getTotalFieldLongValue(), this.getCurrentOtpSnapshotDetails());
        if (!validationResult.isValid()) {
            JOptionPane.showMessageDialog(null, validationResult.getMessage());
        }
        return validationResult;
    }

    private void requestOTP() {
        if (!this.isPenjualanKreditLoaded()) {
            JOptionPane.showMessageDialog(null, "Load data penjualan kredit terlebih dahulu");
            return;
        }
        if (this.penjualanTableModel.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "Masukkan data penjualan terlebih dahulu");
            return;
        }
        this.refreshSummaryFromTable();
        OTPRevisiPenjualanService.SnapshotRequest snapshotRequest = this.otpRevisiPenjualanService.createSnapshotRequest(this.penjualan.getId(), this.customer.getId(), this.helper.getKaryawan().getId(), this.penjualan.getTotalPenjualan(), this.getTotalFieldLongValue(), this.penjualanDetails, this.getCurrentPenjualanDetailsFromTable());
        int result = this.otpRevisiPenjualanService.requestOTPRevisiPenjualan(snapshotRequest.getOtpRevisiPenjualan(), snapshotRequest.getOtpRevisiPenjualanDetails());
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "OTP berhasil di-request. Hubungi approver untuk mendapatkan OTP.");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal request OTP, coba lagi");
        }
    }

    private void resetData() {
        int rowSize = this.penjualanTableModel.getRowCount();
        for (int a = 0; a < rowSize; ++a) {
            this.penjualanTableModel.removeRow(0);
        }
        this.clearOtpState();
    }

    private void detailClick() {
        this.penjualanTable.requestFocus();
        if (this.penjualanTableModel.getRowCount() == 0) {
            PenjualanDetailDialog1 penjualanDetailDialog = new PenjualanDetailDialog1(null, true, this.penjualanTableModel, this.customer.getId(), "", false, (Long)this.totalField.getValue(), this.penjualan.getKeterangan());
            penjualanDetailDialog.addListener(new penjualanDetailDialogEventHandler());
            int w = penjualanDetailDialog.getWidth();
            int h = penjualanDetailDialog.getHeight();
            penjualanDetailDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            penjualanDetailDialog.setVisible(true);
        }
    }

    private void loadData() {
        this.resetData();
        this.penjualanDetails = this.penjualanDetailService.readPenjualanDetailByPenjualanId(this.penjualan.getId());
        long totalRokok = 0L;
        for (PenjualanDetail penjualanDetail : this.penjualanDetails) {
            Object[] rowValue = new Object[9];
            rowValue[0] = penjualanDetail.getBarangId();
            Barang barang = this.barangService.getBarang(penjualanDetail.getBarangId());
            if (barang.getJenis().equals("ROKOK")) {
                totalRokok += penjualanDetail.getTotalHarga();
            }
            rowValue[1] = barang.getNama();
            float tempValue = penjualanDetail.getJumlahBarang();
            rowValue[2] = tempValue == (float)((long)tempValue) ? (Number)((long)penjualanDetail.getJumlahBarang()) : (Number)Float.valueOf(penjualanDetail.getJumlahBarang());
            rowValue[3] = penjualanDetail.getSatuan();
            rowValue[4] = penjualanDetail.getHargaJual();
            rowValue[5] = penjualanDetail.getTotalHarga() + penjualanDetail.getDiscount2();
            rowValue[6] = penjualanDetail.getDiscount2();
            rowValue[7] = penjualanDetail.getTotalHarga();
            this.penjualanTableModel.addRow(rowValue);
        }
        String aktifkanPoint = this.konstantaService.getConstantValue("aktifkanPoint");
        if (aktifkanPoint.equals("YES") && !this.customer.getId().equals("C")) {
            long total = this.penjualan.getTotalPenjualan();
            float rokokValue = Float.parseFloat(this.konstantaService.getConstantValue("pointRokok"));
            float lainValue = Float.parseFloat(this.konstantaService.getConstantValue("pointLain"));
            float pointRokok = (float)totalRokok / rokokValue;
            float pointLain = (float)(total - totalRokok) / lainValue;
            this.totalPointAwal = pointLain + pointRokok;
        }
        this.tanggalField.setValue(this.penjualan.getTanggal());
        this.idPenjualanField.setText(this.penjualan.getId());
        this.totalField.setValue(this.penjualan.getTotalPenjualan());
        this.customerIdField.setText(this.customer.getId());
        this.customerIdField.setEditable(false);
        this.customerNameField.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
        this.totalItemField.setText(this.penjualanDetails.size() + "");
        this.penjualanTable.requestFocus();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.idPenjualanField = new JTextField();
        this.jScrollPane1 = new JScrollPane();
        this.penjualanTable = new JTable();
        this.detailButton = new JButton();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.totalField = new JFormattedTextField();
        this.jLabel11 = new JLabel();
        this.tanggalField = new JFormattedTextField();
        this.jLabel3 = new JLabel();
        this.totalItemField = new JTextField();
        this.jLabel4 = new JLabel();
        this.customerIdField = new JTextField();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.customerNameField = new JLabel();
        this.jLabel10 = new JLabel();
        this.jLabel12 = new JLabel();
        this.otpField = new JTextField();
        this.requestOTPButton = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal");
        this.jLabel2.setText("ID Penjualan");
        this.idPenjualanField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                Penjualan2Panel.this.idPenjualanFieldKeyPressed(evt);
            }
        });
        this.penjualanTable.setModel(this.penjualanTableModel);
        this.penjualanTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                Penjualan2Panel.this.penjualanTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.penjualanTable);
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(74, 23));
        this.detailButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                Penjualan2Panel.this.detailButtonKeyPressed(evt);
            }
        });
        this.saveButton.setLabel("Save");
        this.saveButton.setPreferredSize(new Dimension(74, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                Penjualan2Panel.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                Penjualan2Panel.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(74, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                Penjualan2Panel.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                Penjualan2Panel.this.exitButtonKeyPressed(evt);
            }
        });
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setText("0");
        this.totalField.setFont(new Font("Tahoma", 1, 12));
        this.jLabel11.setFont(new Font("Tahoma", 1, 12));
        this.jLabel11.setText("Total");
        this.tanggalField.setEditable(false);
        this.tanggalField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.jLabel3.setText("Total Item");
        this.totalItemField.setText("0");
        this.jLabel4.setText("Customer");
        this.customerIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                Penjualan2Panel.this.customerIdFieldKeyPressed(evt);
            }
        });
        this.jLabel5.setText(":");
        this.jLabel6.setText(":");
        this.jLabel7.setText(":");
        this.jLabel8.setText(":");
        this.jLabel9.setText(":");
        this.customerNameField.setText(" ");
        this.jLabel10.setText("OTP");
        this.jLabel12.setText(":");
        this.otpField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                Penjualan2Panel.this.otpFieldKeyPressed(evt);
            }
        });
        this.requestOTPButton.setText("Request OTP");
        this.requestOTPButton.setAlignmentY(0.0f);
        this.requestOTPButton.setPreferredSize(new Dimension(74, 23));
        this.requestOTPButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                Penjualan2Panel.this.requestOTPButtonActionPerformed(evt);
            }
        });
        this.requestOTPButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                Penjualan2Panel.this.requestOTPButtonKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jLabel1).addGap(41, 41, 41)).addComponent(this.jLabel2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel7).addComponent(this.jLabel6, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel5, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.tanggalField).addComponent(this.idPenjualanField, -2, 137, -2)).addGap(102, 102, 102).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.jLabel11)).addGap(26, 26, 26).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel9).addComponent(this.jLabel8))).addGroup(layout.createSequentialGroup().addComponent(this.customerIdField, -2, 91, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerNameField, -1, -1, Short.MAX_VALUE))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.totalItemField, -2, 68, -2).addComponent(this.totalField, -2, 148, -2))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(79, 79, 79).addComponent(this.jLabel12).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.otpField, -2, 91, -2)).addComponent(this.jLabel10, -2, 61, -2)).addGap(2, 2, 2).addComponent(this.requestOTPButton, -2, 134, -2)).addComponent(this.jLabel4, -2, 61, -2)).addGap(0, 209, Short.MAX_VALUE))).addContainerGap()).addGroup(layout.createSequentialGroup().addGap(279, 279, 279).addComponent(this.detailButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.tanggalField, -2, -1, -2).addComponent(this.jLabel11).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.idPenjualanField, -2, -1, -2).addComponent(this.jLabel3).addComponent(this.jLabel7)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.customerIdField, -2, -1, -2).addComponent(this.jLabel6).addComponent(this.customerNameField))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalField, -2, -1, -2).addComponent(this.jLabel8)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.totalItemField, -2, -1, -2).addComponent(this.jLabel9)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel10).addComponent(this.otpField, -2, -1, -2).addComponent(this.jLabel12)).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.requestOTPButton, -2, -1, -2))).addGap(12, 12, 12).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailButton, -2, -1, -2).addComponent(this.saveButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 381, -2).addGap(73, 73, 73)));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void idPenjualanFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.idPenjualanField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Masukkan ID Penjualan");
            } else if (!this.idPenjualanField.getText().equals("")) {
                this.penjualan = this.penjualanService.getPenjualan(this.idPenjualanField.getText());
                if (this.penjualan == null) {
                    JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
                } else if (this.penjualan.getKeterangan().equals("CASH")) {
                    JOptionPane.showMessageDialog(null, "Nota Cash Tidak Dapat Direvisi");
                } else if (this.penjualan.getKeterangan().equals("KREDIT")) {
                    if (this.customerService.getCustomerById(this.penjualan.getCustomerId()).getIdCabang() == 1) {
                        JOptionPane.showMessageDialog(null, "Penjualan Cabang Tidak Dapat Direvisi");
                    } else {
                        this.loadPenjualanKredit();
                    }
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.defaultPanelListener.exitClick();
        }
    }

    private void detailButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailClick();
        } else if (evt.getKeyCode() == 39) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.customerIdField.requestFocus();
            this.customerIdField.selectAll();
        }
    }

    private void penjualanTableKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 10) {
            PenjualanDetailDialog1 penjualanDetailDialog = new PenjualanDetailDialog1(null, true, this.penjualanTableModel, this.customer.getId(), "", false, (Long)this.totalField.getValue(), this.penjualan.getKeterangan());
            if (this.penjualanTable.getSelectedRow() != -1) {
                penjualanDetailDialog.loadKodeBarang(this.penjualanTableModel.getValueAt(this.penjualanTable.getSelectedRow(), 0).toString());
                penjualanDetailDialog.loadPenjualan(this.penjualanTable.getSelectedRow());
            }
            penjualanDetailDialog.escapeClick();
            penjualanDetailDialog.addListener(new penjualanDetailDialogEventHandler());
            int w = penjualanDetailDialog.getWidth();
            int h = penjualanDetailDialog.getHeight();
            penjualanDetailDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            penjualanDetailDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a2 = JOptionPane.showConfirmDialog(null, "Akhiri Entry Data?", "Akhiri Entry Data", 0, 3);
            if (a2 == 0) {
                this.refreshSummaryFromTable();
                this.saveButton.requestFocus();
            }
        } else if (evt.getKeyCode() == 127 && this.penjualanTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
            this.totalItemField.setText(Integer.parseInt(this.totalItemField.getText()) - 1 + "");
            this.totalField.setValue(Math.round(Float.parseFloat(this.totalField.getValue().toString()) - Float.parseFloat(this.penjualanTableModel.getValueAt(this.penjualanTable.getSelectedRow(), 7).toString())));
            this.penjualanTableModel.removeRow(this.penjualanTable.getSelectedRow());
            this.clearOtpState();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.exitButton.requestFocus();
            this.saveData();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idPenjualanField.requestFocus();
            this.idPenjualanField.selectAll();
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.exitButton.requestFocus();
        this.saveData();
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.defaultPanelListener.exitClick();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idPenjualanField.requestFocus();
            this.idPenjualanField.selectAll();
        }
    }

    private void customerIdFieldKeyPressed(KeyEvent evt) {
        if (!this.customerIdField.isEditable()) {
            if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
                this.idPenjualanField.requestFocus();
                this.idPenjualanField.selectAll();
            }
            return;
        }
        if (evt.getKeyCode() == 10) {
            if (this.customerIdField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
                sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.customer = this.customerService.getCustomerById(this.customerIdField.getText());
                if (this.customer == null) {
                    JOptionPane.showMessageDialog(null, "Data Customer Tidak Ditemukan");
                } else if (this.customer.getTag() != null && this.customer.getTag().equals("D")) {
                    JOptionPane.showMessageDialog(null, "Data Customer Sudah Tidak Aktif");
                } else {
                    this.customerNameField.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
                    this.penjualanTable.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
            this.idPenjualanField.requestFocus();
            this.idPenjualanField.selectAll();
        }
    }

    private void otpFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.requestOTPButton.requestFocus();
        }
    }

    private void requestOTPButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.requestOTP();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.otpField.requestFocus();
            this.otpField.selectAll();
        }
    }

    private void requestOTPButtonActionPerformed(ActionEvent evt) {
        this.requestOTP();
    }

    private int rataKanan(String price, int x) {
        int len = price.length();
        for (int i = 0; i < len; ++i) {
            x -= 4;
        }
        return x;
    }

    class printer
    implements Printable {
        printer() {
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
            if (page > 0) {
                return 1;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            int y = 0;
            int x = 5;
            int yK = 18;
            int yP = 11;
            int xRataKanan = 160;
            g.setFont(new Font("Times New Roman", 0, 8));
            if (Penjualan2Panel.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(Penjualan2Panel.this.konstantaService.getConstantValue("namaToko"), x, y + yK);
            } else if (Penjualan2Panel.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(Penjualan2Panel.this.konstantaService.getConstantValue("namaGudang"), x, y + yK);
            }
            yK += yP;
            if (Penjualan2Panel.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(Penjualan2Panel.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (Penjualan2Panel.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(Penjualan2Panel.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            String telp1 = "";
            if (Penjualan2Panel.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = Penjualan2Panel.this.konstantaService.getConstantValue("telpToko1");
            } else if (Penjualan2Panel.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = Penjualan2Panel.this.konstantaService.getConstantValue("telpGudang1");
            }
            if (!telp1.equals("")) {
                g.drawString("TELP: " + telp1, x, y + (yK += yP));
            }
            String telp2 = "";
            if (Penjualan2Panel.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = Penjualan2Panel.this.konstantaService.getConstantValue("telpToko2");
            } else if (Penjualan2Panel.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = Penjualan2Panel.this.konstantaService.getConstantValue("telpGudang2");
            }
            if (!telp2.equals("")) {
                g.drawString("TELP: " + telp2, x, y + (yK += yP));
            }
            Customer customer = Penjualan2Panel.this.customerService.getCustomerById(Penjualan2Panel.this.penjualan.getCustomerId());
            g.drawString("Cust: " + customer.getId() + " - " + customer.getNama(), x + 0, y + (yK += yP));
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Bon. " + Penjualan2Panel.this.penjualan.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + Penjualan2Panel.this.helper.getKaryawan().getNama(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            float totalDiscount = 0.0f;
            long totalBelanja = 0L;
            for (PenjualanDetail penjualanDetail : Penjualan2Panel.this.penjualanDetails) {
                Barang barang = Penjualan2Panel.this.barangService.getBarang(penjualanDetail.getBarangId());
                g.drawString(barang.getNama(), x + 0, y + (yK += yP));
                yK = yK + yP - 1;
                float tempValue = penjualanDetail.getJumlahBarang();
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)penjualanDetail.getJumlahBarang()) : (Number)Float.valueOf(penjualanDetail.getJumlahBarang());
                g.drawString(value + penjualanDetail.getSatuan(), x + 20, y + yK);
                g.drawString("x", x + 55, y + yK);
                g.drawString(Penjualan2Panel.this.helper.formatNumber(penjualanDetail.getHargaJual() + ""), x + 70, y + yK);
                g.drawString("=", x + 110, y + yK);
                int xtemp = Penjualan2Panel.this.rataKanan(Penjualan2Panel.this.helper.formatNumber((long)(penjualanDetail.getJumlahBarang() * (float)penjualanDetail.getHargaJual()) + ""), xRataKanan);
                g.drawString(Penjualan2Panel.this.helper.formatNumber((long)((float)penjualanDetail.getHargaJual() * penjualanDetail.getJumlahBarang()) + ""), x + xtemp, y + yK);
                if (penjualanDetail.getDiscount2() > 0L) {
                    g.drawString("*Disc ( -" + penjualanDetail.getDiscount2() + ")", x + 0, y + (yK += yP));
                }
                totalDiscount += (float)penjualanDetail.getDiscount2();
                totalBelanja = (long)((float)totalBelanja + (float)penjualanDetail.getHargaJual() * penjualanDetail.getJumlahBarang());
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total item: " + Penjualan2Panel.this.penjualanDetails.size(), x + 0, y + (yK += yP));
            int xtemp = Penjualan2Panel.this.rataKanan(Penjualan2Panel.this.helper.formatNumber(totalBelanja + ""), xRataKanan);
            g.drawString(Penjualan2Panel.this.helper.formatNumber(totalBelanja + ""), x + xtemp, y + yK);
            yK += yP;
            if (totalDiscount > 0.0f) {
                g.drawString("Hemat ", x + 0, y + yK);
                xtemp = Penjualan2Panel.this.rataKanan(Penjualan2Panel.this.helper.formatNumber("-" + (long)totalDiscount + ""), xRataKanan);
                g.drawString(Penjualan2Panel.this.helper.formatNumber("-" + (long)totalDiscount + "") + "", x + xtemp, y + yK);
                xtemp = Penjualan2Panel.this.rataKanan(Penjualan2Panel.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
                g.drawString(Penjualan2Panel.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + (yK += yP));
                yK += yP;
            }
            if (!(customer.getId().equalsIgnoreCase("C") || customer.getId().equalsIgnoreCase("SINAR") || customer.getId().equalsIgnoreCase("LUKAS"))) {
                String aktifkanPoint;
                if (customer.getHutang() > 0L) {
                    g.drawString("Sisa Hutang", x + 0, y + yK);
                    xtemp = Penjualan2Panel.this.rataKanan(Penjualan2Panel.this.helper.formatNumber(customer.getHutang() + ""), xRataKanan);
                    g.drawString(Penjualan2Panel.this.helper.formatNumber(customer.getHutang() + ""), x + xtemp, y + yK);
                }
                if ((aktifkanPoint = Penjualan2Panel.this.konstantaService.getConstantValue("aktifkanPoint")).equals("YES")) {
                    g.drawString("Point Belanja", x + 0, y + (yK += yP));
                    g.drawString("(+" + (long)Penjualan2Panel.this.totalPoint + ")", x + 120, y + yK);
                    xtemp = Penjualan2Panel.this.rataKanan(Penjualan2Panel.this.helper.formatNumber(customer.getPoint() + ""), xRataKanan);
                    g.drawString(Penjualan2Panel.this.helper.formatNumber(customer.getPoint() + ""), x + xtemp, y + yK);
                }
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(Penjualan2Panel.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + Penjualan2Panel.this.penjualan.getCreated().getHours() + ":" + Penjualan2Panel.this.penjualan.getCreated().getMinutes() + ":" + Penjualan2Panel.this.penjualan.getCreated().getSeconds(), x + 120, y + yK);
            g.setFont(new Font("Arial", 0, 8));
            g.drawString("TERIMA KASIH", x + 57, y + (yK += yP));
            g.drawString("Barang yang sudah dibeli", x + 40, y + (yK += yP));
            g.drawString("tidak dapat ditukar/dikembalikan", x + 25, y + (yK += yP));
            return 0;
        }
    }

    class serverPrinter
    implements Printable {
        serverPrinter() {
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
            if (page > 0) {
                return 1;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            int y = 0;
            int x = 5;
            int yK = 18;
            int yP = 11;
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("Permintaan Barang - " + Penjualan2Panel.this.penjualan.getId(), x + 0, y + yK);
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (PenjualanDetail penjualanDetail : Penjualan2Panel.this.penjualanDetails) {
                Barang barang = Penjualan2Panel.this.barangService.getBarang(penjualanDetail.getBarangId());
                g.drawString(barang.getNama() + " (" + barang.getRakIdToko() + ")", x + 0, y + yK);
                float tempValue = penjualanDetail.getJumlahBarang();
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)penjualanDetail.getJumlahBarang()) : (Number)Float.valueOf(penjualanDetail.getJumlahBarang());
                g.drawString(value + penjualanDetail.getSatuan() + " / " + barang.getStokToko() + barang.getSatuan1(), x + 20, y + (yK += yP));
                yK += yP;
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(Penjualan2Panel.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + yK);
            g.drawString("Jam " + Penjualan2Panel.this.penjualan.getCreated().getHours() + ":" + Penjualan2Panel.this.penjualan.getCreated().getMinutes() + ":" + Penjualan2Panel.this.penjualan.getCreated().getSeconds(), x + 120, y + yK);
            return 0;
        }
    }

    private class penjualanDetailDialogEventHandler
    implements PenjualanDetailDialogListener {
        private penjualanDetailDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue, boolean removeRow) {
            int a;
            boolean newData = true;
            for (a = 0; a < Penjualan2Panel.this.penjualanTableModel.getRowCount(); ++a) {
                if (!Penjualan2Panel.this.penjualanTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                Penjualan2Panel.this.penjualanTableModel.addRow(rowValue);
                Penjualan2Panel.this.totalField.setValue(Math.round(Float.parseFloat(Penjualan2Panel.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
                Penjualan2Panel.this.totalItemField.setText(Integer.parseInt(Penjualan2Panel.this.totalItemField.getText()) + 1 + "");
            } else {
                Penjualan2Panel.this.totalField.setValue(Math.round(Float.parseFloat(Penjualan2Panel.this.totalField.getValue().toString()) - Float.parseFloat(Penjualan2Panel.this.penjualanTableModel.getValueAt(a, 7).toString())));
                Penjualan2Panel.this.totalField.setValue(Math.round(Float.parseFloat(Penjualan2Panel.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
                Penjualan2Panel.this.penjualanTableModel.removeRow(a);
                Penjualan2Panel.this.penjualanTableModel.addRow(rowValue);
            }
            Penjualan2Panel.this.clearOtpState();
        }

        @Override
        public void deleteClick(String idBarang) {
            for (int a = 0; a < Penjualan2Panel.this.penjualanTableModel.getRowCount(); ++a) {
                if (!Penjualan2Panel.this.penjualanTableModel.getValueAt(a, 0).equals(idBarang)) continue;
                Penjualan2Panel.this.totalItemField.setText(Integer.parseInt(Penjualan2Panel.this.totalItemField.getText()) - 1 + "");
                Penjualan2Panel.this.totalField.setValue(Math.round(Float.parseFloat(Penjualan2Panel.this.totalField.getValue().toString()) - Float.parseFloat(Penjualan2Panel.this.penjualanTableModel.getValueAt(a, 7).toString())));
                Penjualan2Panel.this.penjualanTableModel.removeRow(a);
                Penjualan2Panel.this.clearOtpState();
                break;
            }
        }

        @Override
        public void refreshData() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DefaultTableModel getPenjualanTableModel() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void changeButtonPromoTebusMurah() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void changeButtonPromoTebus(String state, List<Object[]> data) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class searchPanelPenjualanEventHandler
    implements SearchPanelListener {
        private searchPanelPenjualanEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            Penjualan2Panel.this.penjualan = Penjualan2Panel.this.penjualanService.getPenjualan(chooseValue);
            if (Penjualan2Panel.this.penjualan == null) {
                JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
                return;
            }
            if (Penjualan2Panel.this.penjualan.getKeterangan().equals("CASH")) {
                JOptionPane.showMessageDialog(null, "Nota Cash Tidak Dapat Direvisi");
            } else if (Penjualan2Panel.this.penjualan.getKeterangan().equals("KREDIT")) {
                Penjualan2Panel.this.loadPenjualanKredit();
            }
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            Penjualan2Panel.this.customer = Penjualan2Panel.this.customerService.getCustomerById(chooseValue);
            if (Penjualan2Panel.this.customer.getTag() != null && Penjualan2Panel.this.customer.getTag().equals("D")) {
                JOptionPane.showMessageDialog(null, "Data Customer Sudah Tidak Aktif");
            } else {
                Penjualan2Panel.this.customerIdField.setText(Penjualan2Panel.this.customer.getId());
                Penjualan2Panel.this.customerNameField.setText(Penjualan2Panel.this.customer.getNama() + "-" + Penjualan2Panel.this.customer.getAlamat());
                Penjualan2Panel.this.penjualanTable.requestFocus();
            }
        }
    }

    private class searchCustomerDialogEventHandler
    implements SearchDialogListener {
        private searchCustomerDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE CUSTOMER", "NAMA CUSTOMER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Customer> customers = Penjualan2Panel.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Customer");
            sp.addSearchPanelListener(new searchPanelCustomerEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }
}

