/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.dao.CustomerDao;
import minimart.engine.daoimpl.CustomerDaoImpl;
import minimart.engine.model.Barang;
import minimart.engine.model.Customer;
import minimart.engine.model.Penjualan;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.model.Piutang;
import minimart.engine.model.Sales;
import minimart.engine.model.ShiftKasir;
import minimart.engine.model.StockCommit;
import minimart.engine.model.Voucher;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerPointCommitService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanSementaraService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.PiutangService;
import minimart.engine.service.SalesOrderService;
import minimart.engine.service.SalesService;
import minimart.engine.service.ShiftKasirService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.StokCommitService;
import minimart.engine.service.SyncService;
import minimart.engine.service.TransaksiOnlineService;
import minimart.engine.service.VoucherService;
import minimart.ui.listener.CetakNotaListener;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.view.ConfirmationDialog;

public class CetakNota2
extends JDialog {
    private JButton cancelButton;
    private JToggleButton cashBtn;
    private JTextField customerAlamatField;
    private JTextField customerNameField;
    private JToggleButton debitBtn;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JTextField jumlahBarangField;
    private JFormattedTextField jumlahVoucherField;
    private JTextField karyawanField;
    private JFormattedTextField kembaliField;
    private JFormattedTextField newTotalField;
    private JButton printButton;
    private JFormattedTextField tanggalField;
    private JToggleButton transferBtn;
    private JFormattedTextField tunaiField;
    private JTextField voucherIdField;
    private PenjualanSementaraService penjualanSementaraService = new PenjualanSementaraService();
    private BarangService barangService = new BarangService();
    private CustomerService customerService = new CustomerService();
    private ShiftKasirService shiftKasirService = new ShiftKasirService();
    private Helper helper = Helper.getInstance();
    private Customer customer;
    private PenjualanService penjualanService = new PenjualanService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private StokCommitService stockCommitService = new StokCommitService();
    private List<Object[]> penjualanSementaras;
    private CetakNotaListener listener;
    private Penjualan penjualan;
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    SyncService syncService = new SyncService();
    private PiutangService piutangService = new PiutangService();
    private PembelianService pembelianService = new PembelianService();
    private boolean canGoThrough;
    private int screenHeight;
    private int screenWidth;
    private long totalBelanja = 0L;
    private float totalPoint;
    private KonstantaService konstantaService = new KonstantaService();
    private Sales sales;
    private SalesService salesService = new SalesService();
    private Date jatuhTempo;
    private String keterangan;
    private CommonEngine comm = new CommonEngine();
    private CustomerPointCommitService customerPointCommitService = new CustomerPointCommitService();
    private CustomerDao cd = new CustomerDaoImpl();
    private long totalKeuntungan = 0L;
    private VoucherService voucherService = new VoucherService();
    private Voucher voucher = null;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private String shortcut;
    private ShiftKasir shiftKasir;
    private SalesOrderService soService = new SalesOrderService();
    private TransaksiOnlineService toService = new TransaksiOnlineService();
    private int idSo = 0;
    private int idTo = 0;
    private String kodeMetodePembayaran = "";
    private String statusPembayaranTO = null;
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();

    public CetakNota2(Frame parent, boolean modal, String shortcut, Float value, Date jatuhTempo, String keterangan) {
        super(parent, modal);
        this.initComponents();
        this.shiftKasir = new ShiftKasirService().getOpenedShiftKasir(this.helper.getKasir());
        this.loadData(shortcut, value);
        this.setTitle("Print");
        this.requestDefaultFocus();
        this.jatuhTempo = jatuhTempo;
        this.keterangan = keterangan;
        this.shortcut = shortcut;
        this.kembaliField.setValue(0);
        this.tunaiField.setValue(0);
        this.tunaiField.setText("");
        if (keterangan.equals("KREDIT")) {
            this.voucherIdField.setEnabled(false);
        }
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.printButton.setEnabled(true);
        this.buttonGroup.add(this.cashBtn);
        this.buttonGroup.add(this.transferBtn);
        this.buttonGroup.add(this.debitBtn);
        this.cashBtn.setSelected(true);
    }

    protected void requestDefaultFocus() {
        this.voucherIdField.requestFocus();
        this.voucherIdField.selectAll();
    }

    public void addListener(CetakNotaListener listener) {
        this.listener = listener;
    }

    private void loadData(String shortcut, Float parentValue) {
        this.penjualanSementaras = this.penjualanSementaraService.getPenjualanSementaraByShortcut2OrderByName(shortcut);
        this.jumlahBarangField.setText(this.penjualanSementaras.size() + "");
        if (this.penjualanSementaras.size() > 0) {
            this.customer = this.customerService.getCustomerById(this.penjualanSementaras.get(0)[0].toString());
            this.sales = this.salesService.getSales(this.penjualanSementaras.get(0)[1].toString());
            this.tanggalField.setValue(new Date());
            this.karyawanField.setText(this.shiftKasir.getOpenedBy());
            this.customerNameField.setText(this.customer.getNama());
            this.customerAlamatField.setText(this.customer.getAlamat());
            long total = 0L;
            for (Object[] penjualanSementara : this.penjualanSementaras) {
                total += Long.parseLong(penjualanSementara[8].toString());
            }
            if (parentValue.floatValue() != (float)total) {
                JOptionPane.showMessageDialog(null, "Refresh Data!");
            }
            this.totalBelanja = this.helper.roundPrice100(total);
            this.newTotalField.setValue(this.totalBelanja);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.tanggalField = new JFormattedTextField();
        this.karyawanField = new JTextField();
        this.customerNameField = new JTextField();
        this.customerAlamatField = new JTextField();
        this.jLabel5 = new JLabel();
        this.jumlahBarangField = new JTextField();
        this.printButton = new JButton();
        this.cancelButton = new JButton();
        this.jLabel2 = new JLabel();
        this.jLabel7 = new JLabel();
        this.tunaiField = new JFormattedTextField();
        this.kembaliField = new JFormattedTextField();
        this.voucherIdField = new JTextField();
        this.jumlahVoucherField = new JFormattedTextField();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.newTotalField = new JFormattedTextField();
        this.cashBtn = new JToggleButton();
        this.transferBtn = new JToggleButton();
        this.debitBtn = new JToggleButton();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Tanggal");
        this.jLabel3.setText("Petugas");
        this.jLabel4.setText("Customer");
        this.tanggalField.setEditable(false);
        this.karyawanField.setEditable(false);
        this.customerNameField.setEditable(false);
        this.customerAlamatField.setEditable(false);
        this.jLabel5.setText("Barang");
        this.jumlahBarangField.setEditable(false);
        this.jumlahBarangField.setText("90");
        this.printButton.setText("Print");
        this.printButton.setPreferredSize(new Dimension(73, 23));
        this.printButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakNota2.this.printButtonActionPerformed(evt);
            }
        });
        this.printButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNota2.this.printButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakNota2.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNota2.this.cancelButtonKeyPressed(evt);
            }
        });
        this.jLabel2.setFont(new Font("Tahoma", 1, 24));
        this.jLabel2.setText("Tunai");
        this.jLabel7.setFont(new Font("Tahoma", 1, 24));
        this.jLabel7.setText("Kembali");
        this.tunaiField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.tunaiField.setFont(new Font("Tahoma", 1, 24));
        this.tunaiField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNota2.this.tunaiFieldKeyPressed(evt);
            }
        });
        this.kembaliField.setEditable(false);
        this.kembaliField.setBackground(new Color(153, 255, 204));
        this.kembaliField.setForeground(new Color(0, 0, 153));
        this.kembaliField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.kembaliField.setFont(new Font("Tahoma", 1, 24));
        this.voucherIdField.setFont(new Font("Tahoma", 1, 18));
        this.voucherIdField.setForeground(new Color(0, 51, 255));
        this.voucherIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNota2.this.voucherIdFieldKeyPressed(evt);
            }
        });
        this.jumlahVoucherField.setEditable(false);
        this.jumlahVoucherField.setForeground(new Color(255, 0, 0));
        this.jumlahVoucherField.setFont(new Font("Tahoma", 1, 18));
        this.jLabel8.setFont(new Font("Tahoma", 1, 18));
        this.jLabel8.setText("Voucher ID");
        this.jLabel9.setFont(new Font("Tahoma", 1, 24));
        this.jLabel9.setText("Total");
        this.newTotalField.setEditable(false);
        this.newTotalField.setFont(new Font("Tahoma", 1, 24));
        this.cashBtn.setText("CASH");
        this.cashBtn.setPreferredSize(new Dimension(111, 29));
        this.transferBtn.setText("TRANSFER");
        this.debitBtn.setText("DEBIT");
        this.debitBtn.setPreferredSize(new Dimension(111, 29));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addComponent(this.printButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton, -2, -1, -2).addContainerGap()).addGroup(layout.createSequentialGroup().addGap(20, 20, 20).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.cashBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.transferBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.debitBtn, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel8, -2, 115, -2).addComponent(this.jLabel2).addComponent(this.jLabel7).addComponent(this.jLabel9)).addGap(4, 4, 4).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jumlahVoucherField, GroupLayout.Alignment.TRAILING).addComponent(this.voucherIdField).addComponent(this.tunaiField).addComponent(this.kembaliField).addComponent(this.newTotalField, -2, 222, -2)).addContainerGap(-1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel3).addComponent(this.jLabel4)).addGap(72, 72, 72).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.customerAlamatField, GroupLayout.Alignment.LEADING, -1, 324, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.karyawanField, GroupLayout.Alignment.LEADING, -1, 201, Short.MAX_VALUE).addComponent(this.tanggalField, GroupLayout.Alignment.LEADING, -1, 201, Short.MAX_VALUE).addComponent(this.customerNameField, -1, 201, Short.MAX_VALUE)).addGap(24, 24, 24).addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jumlahBarangField, -2, 61, -2))).addGap(23, 23, 23)))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.tanggalField, -2, -1, -2).addComponent(this.jumlahBarangField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.karyawanField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.customerNameField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerAlamatField, -2, -1, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel8).addGroup(layout.createSequentialGroup().addComponent(this.voucherIdField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jumlahVoucherField, -2, 30, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel9).addComponent(this.newTotalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addGap(20, 20, 20).addComponent(this.jLabel7)).addGroup(layout.createSequentialGroup().addComponent(this.tunaiField, -2, 45, -2).addGap(9, 9, 9).addComponent(this.kembaliField))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cashBtn, -2, -1, -2).addComponent(this.transferBtn).addComponent(this.debitBtn, -2, -1, -2)).addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.printButton, -2, -1, -2).addComponent(this.cancelButton, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    public static String padWithLeadingZeros(int input) {
        return String.format("%010d", input);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void saveClick() {
        int result;
        boolean canSave = true;
        int piutangResult = 0;
        if (!this.customer.getId().equalsIgnoreCase("C") && (Long)this.tunaiField.getValue() != 0L && Integer.parseInt(this.tunaiField.getValue().toString()) < Integer.parseInt(this.newTotalField.getValue() + "")) {
            this.canGoThrough = false;
            ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Masukan Selisih Pembayaran ke Piutang Customer?");
            confirmationDialog.addListener(new confirmDialogEventHandler());
            int w = confirmationDialog.getWidth();
            int h = confirmationDialog.getHeight();
            confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            confirmationDialog.setVisible(true);
            if (this.canGoThrough) {
                long selisih = Long.parseLong(this.newTotalField.getValue().toString()) - Long.parseLong(this.tunaiField.getValue().toString());
                Piutang piutang = new Piutang();
                piutang.setTanggal(new Date());
                piutang.setId(this.helper.generateCodeGeneral(this.piutangService.getLastId()));
                piutang.setKaryawanId(this.shiftKasir.getOpenedBy());
                piutang.setCustomerId(this.customer.getId());
                piutang.setTipe("HUTANG");
                piutang.setJumlah(selisih);
                piutang.setKeterangan("TRANSAKSI");
                piutang.setCreated(new Date());
                piutang.setUpdated(new Date());
                piutangResult = this.piutangService.createPiutang(piutang);
                if (piutangResult == 1) {
                    this.customer.setHutang(this.customer.getHutang() + selisih);
                }
            } else {
                canSave = false;
            }
        }
        if (!canSave) return;
        this.printButton.setEnabled(false);
        this.cancelButton.requestFocus();
        this.penjualan = new Penjualan();
        this.penjualan.setId(this.helper.generateCodeGeneral(this.penjualanService.getLastId()));
        this.penjualan.setKaryawanId(this.shiftKasir.getOpenedBy());
        this.penjualan.setCustomerId(this.customer.getId());
        if (this.sales != null) {
            this.penjualan.setSalesId(this.sales.getId());
        } else {
            this.penjualan.setSalesId("");
        }
        this.penjualan.setKasir(this.helper.getKasir());
        this.penjualan.setTanggal(new Date());
        this.penjualan.setTotalPenjualan(this.totalBelanja);
        this.penjualan.setStatus(this.helper.getStatusProgram());
        this.penjualan.setCreated(new Date());
        this.penjualan.setUpdated(new Date());
        if (this.keterangan.equals("CASH")) {
            if (this.transferBtn.isSelected()) {
                this.keterangan = "TRANSFER";
            } else if (this.debitBtn.isSelected()) {
                this.keterangan = "DEBIT";
            }
        }
        this.penjualan.setKeterangan(this.keterangan);
        if (this.keterangan.equals("KREDIT")) {
            this.penjualan.setStatusPelunasan("BELUM LUNAS");
        } else if (this.keterangan.equals("CASH") || this.keterangan.equals("TRANSFER") || this.keterangan.equals("DEBIT")) {
            this.penjualan.setStatusPelunasan("SUDAH LUNAS");
        }
        this.penjualan.setJatuhTempo(this.jatuhTempo);
        if (this.shortcut == "F10") {
            this.penjualan.setIdTransaksiOnline(CetakNota2.padWithLeadingZeros(this.idTo));
        }
        int penjualanStatus = this.penjualanService.createPenjualan(this.penjualan);
        if (this.shortcut == "F11" ? (result = this.soService.updateImportedSalesOrderById(this.idSo)) > 0 : this.shortcut != "F10" || (result = this.toService.updateIdTransaksiOfflineTransaksiOnlineById(this.idTo, this.penjualan.getId(), this.shiftKasir.getOpenedBy())) <= 0) {
            // empty if block
        }
        if (penjualanStatus != 1) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Transaksi Gagal");
            return;
        }
        if (this.voucher != null) {
            this.voucher.setExchangeDate(new Date());
            this.voucher.setAvailable("FALSE");
            result = this.voucherService.updateVoucher(this.voucher);
            System.out.println(result);
        }
        this.customer.setUpdated(new Date());
        if (this.helper.getJenisProgram().equals("GUDANGTOKO")) {
            if (this.helper.getStatusProgram().equals("TOKO") && (this.penjualan.getKeterangan().equals("CASH") || this.penjualan.getKeterangan().equals("TRANSFER") || this.penjualan.getKeterangan().equals("DEBIT"))) {
                ShiftKasir shiftKasir = this.shiftKasirService.getOpenedShiftKasir(this.helper.getKasir());
                shiftKasir.setTotalUang(shiftKasir.getTotalUang() + this.totalBelanja);
                this.shiftKasirService.update(shiftKasir);
            }
        } else if (this.helper.getJenisProgram().equals("GUDANGSAJA") && (this.penjualan.getKeterangan().equals("CASH") || this.penjualan.getKeterangan().equals("TRANSFER") || this.penjualan.getKeterangan().equals("DEBIT"))) {
            ShiftKasir shiftKasir = this.shiftKasirService.getOpenedShiftKasir(this.helper.getKasir());
            shiftKasir.setTotalUang(shiftKasir.getTotalUang() + this.totalBelanja);
            this.shiftKasirService.update(shiftKasir);
        }
        ArrayList<PenjualanDetail> penjualanDetails = new ArrayList<PenjualanDetail>();
        ArrayList<StockCommit> stockCommits = new ArrayList<StockCommit>();
        for (Object[] penjualanSementara : this.penjualanSementaras) {
            StockCommit stockCommit1;
            Barang barang = this.barangService.getBarang(penjualanSementara[2].toString());
            PenjualanDetail penjualanDetail = new PenjualanDetail();
            penjualanDetail.setPenjualanId(this.penjualan.getId());
            penjualanDetail.setBarangId(penjualanSementara[2].toString());
            penjualanDetail.setJumlahBarang(((Float)penjualanSementara[4]).floatValue());
            penjualanDetail.setSatuan(penjualanSementara[5].toString());
            penjualanDetail.setDiscount2((Long)penjualanSementara[7]);
            penjualanDetail.setHargaJual((Long)penjualanSementara[6]);
            penjualanDetail.setTotalHarga(Long.parseLong(penjualanSementara[8].toString()));
            float totalBarang = 0.0f;
            if (penjualanSementara[5].equals(barang.getSatuan1())) {
                totalBarang = ((Float)penjualanSementara[4]).floatValue();
            } else if (penjualanSementara[5].equals(barang.getSatuan2())) {
                totalBarang = ((Float)penjualanSementara[4]).floatValue() * (float)barang.getKonversi21();
            } else if (penjualanSementara[5].equals(barang.getSatuan3())) {
                totalBarang = ((Float)penjualanSementara[4]).floatValue() * (float)barang.getKonversi21() * (float)barang.getKonversi32();
            }
            if (!this.customer.getId().equalsIgnoreCase("SINAR")) {
                penjualanDetail.setTotalKeuntungan((long)((float)Long.parseLong(penjualanSementara[8].toString()) - (float)barang.getHpp() * totalBarang));
                if (barang.getHpp() > 0L) {
                    this.totalKeuntungan += penjualanDetail.getTotalKeuntungan();
                }
            }
            penjualanDetail.setCreated(new Date());
            penjualanDetail.setUpdated(new Date());
            penjualanDetails.add(penjualanDetail);
            if (this.helper.getStatusProgram().equals("TOKO")) {
                this.barangService.updateStokBarangToko(-totalBarang, barang.getId());
                stockCommit1 = new StockCommit();
                stockCommit1.setIdBarang(barang.getId());
                stockCommit1.setIdTransaksi(this.penjualan.getId());
                stockCommit1.setQueryType("kurang");
                stockCommit1.setStockType("toko");
                stockCommit1.setTransactionType("Penjualan");
                stockCommit1.setJumlahBarang(totalBarang);
                stockCommit1.setStatusCommit(false);
                stockCommit1.setStokAwal(barang.getStokToko());
                stockCommits.add(stockCommit1);
                continue;
            }
            if (!this.helper.getStatusProgram().equals("GUDANG")) continue;
            this.barangService.updateStokBarangGudang(-totalBarang, barang.getId());
            stockCommit1 = new StockCommit();
            stockCommit1.setIdBarang(barang.getId());
            stockCommit1.setIdTransaksi(this.penjualan.getId());
            stockCommit1.setQueryType("kurang");
            stockCommit1.setStockType("gudang");
            stockCommit1.setTransactionType("Penjualan");
            stockCommit1.setJumlahBarang(totalBarang);
            stockCommit1.setStatusCommit(false);
            stockCommit1.setStokAwal(barang.getStokToko());
            stockCommits.add(stockCommit1);
            if (!this.customer.getId().equals("SINAR")) continue;
            this.barangService.updateStokBarangToko(totalBarang, barang.getId());
            StockCommit stockCommit2 = new StockCommit();
            stockCommit1.setIdBarang(barang.getId());
            stockCommit1.setIdTransaksi(this.penjualan.getId());
            stockCommit1.setQueryType("tambah");
            stockCommit1.setStockType("toko");
            stockCommit1.setTransactionType("Penjualan");
            stockCommit1.setJumlahBarang(totalBarang);
            stockCommit1.setStatusCommit(false);
            stockCommit1.setStokAwal(barang.getStokToko());
            stockCommits.add(stockCommit1);
        }
        int[] savePenjualanDetailStatus = this.penjualanDetailService.createdPenjualanDetailBatch(penjualanDetails);
        int[] saveStockCommitStatus = this.stockCommitService.insertStockCommitBatch(stockCommits);
        String aktifkanPoint = this.konstantaService.getConstantValue("aktifkanPoint");
        if (aktifkanPoint.equals("YES") && !this.customer.getId().equalsIgnoreCase("C") && !this.customer.getId().equalsIgnoreCase("SINAR") && !this.customer.getId().equalsIgnoreCase("LUKAS")) {
            if (this.konstantaService.getConstantValue("kategoriPoin").equals("OMSET")) {
                List<Object[]> datas = this.penjualanSementaraService.getPoinPerPenjualan(this.shortcut);
                for (Object[] data : datas) {
                    this.totalPoint += Float.parseFloat(data[3].toString());
                }
                this.customer.setPoint(this.customer.getPoint() + (long)Math.round(this.totalPoint));
                this.customerPointCommitService.insertPointCommit(this.customer.getId(), this.penjualan.getId(), (long)this.totalPoint, "INSERT", "Penjualan");
            } else if (this.konstantaService.getConstantValue("kategoriPoin").equals("PROFIT")) {
                this.totalPoint = this.totalKeuntungan / Long.parseLong(this.konstantaService.getConstantValue("poinProfit"));
                this.customer.setPoint(this.customer.getPoint() + (long)this.totalPoint);
                this.customerPointCommitService.insertPointCommit(this.customer.getId(), this.penjualan.getId(), (long)this.totalPoint, "INSERT", "Penjualan");
            }
            this.customer.setUpdated(new Date());
            int datas = this.customerService.updateCustomer(this.customer);
        }
        this.listener.printClick();
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper p = pf.getPaper();
        p.setSize(p.getWidth(), p.getHeight() + 1500.0);
        p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
        pf.setPaper(p);
        Book book = new Book();
        if (this.penjualan.getCustomerId().equals("SINAR")) {
            book.append(new serverPrinter(), pf);
        } else if (this.penjualan.getKeterangan().equals("KREDIT")) {
            book.append(new printer(), pf);
            book.append(new printerReprint(), pf);
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
        this.syncService.doSyncPenjualanAndPenjualanDetailUnsynced();
        this.syncService.doSyncVoucherUnsynced();
    }

    private void printButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (!this.helper.isInteger(this.tunaiField.getText()) && Long.parseLong(this.tunaiField.getValue().toString()) == 0L) {
                this.tunaiField.setBackground(Color.red);
                JOptionPane.showMessageDialog(null, "Masukkan jumlah pembayaran");
            } else if (Long.parseLong(this.tunaiField.getValue().toString()) == 0L || Long.parseLong(this.tunaiField.getValue().toString()) >= Long.parseLong(this.newTotalField.getValue().toString())) {
                this.saveClick();
            } else {
                JOptionPane.showMessageDialog(null, "Uang Tunai Tidak Mencukupi");
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.tunaiField.setText(this.tunaiField.getValue().toString().replace(".", ""));
            this.tunaiField.selectAll();
            this.tunaiField.requestFocus();
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void tunaiFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.helper.isInteger(this.tunaiField.getText()) && Integer.parseInt(this.tunaiField.getText()) == 0 || this.helper.isInteger(this.tunaiField.getText()) && Integer.parseInt(this.tunaiField.getText()) >= Integer.parseInt(this.newTotalField.getValue() + "")) {
                this.kembaliField.setValue(Integer.parseInt(this.tunaiField.getText()) - Integer.parseInt(this.newTotalField.getValue() + ""));
                this.printButton.requestFocus();
                this.tunaiField.setBackground(Color.white);
            } else {
                this.tunaiField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 38) {
            this.voucherIdField.requestFocus();
            this.voucherIdField.selectAll();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void printButtonActionPerformed(ActionEvent evt) {
        if (!this.helper.isInteger(this.tunaiField.getText()) && Long.parseLong(this.tunaiField.getValue().toString()) == 0L) {
            this.tunaiField.setBackground(Color.red);
            JOptionPane.showMessageDialog(null, "Masukkan jumlah pembayaran");
        } else if (Long.parseLong(this.tunaiField.getValue().toString()) == 0L || Long.parseLong(this.tunaiField.getValue().toString()) >= Long.parseLong(this.newTotalField.getValue().toString())) {
            this.saveClick();
        } else {
            JOptionPane.showMessageDialog(null, "Uang Tunai Tidak Mencukupi");
        }
    }

    private void voucherIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.voucherIdField.getText().equals("")) {
                this.tunaiField.requestFocus();
                this.jumlahVoucherField.setValue(0);
            } else {
                this.voucher = this.voucherService.getVoucher(this.voucherIdField.getText());
                if (this.voucher == null) {
                    JOptionPane.showMessageDialog(null, "KODE VOUCHER TIDAK DITEMUKAN");
                } else if (this.voucher.getAvailable().equals("TRUE")) {
                    Date expDate = this.voucher.getExpiredDate();
                    SimpleDateFormat formatter = new SimpleDateFormat("/yyyy-MM-dd");
                    Date todayWithZeroTime = null;
                    try {
                        todayWithZeroTime = formatter.parse(formatter.format(new Date()));
                    }
                    catch (ParseException ex) {
                        Logger.getLogger(CetakNota2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (expDate.compareTo(todayWithZeroTime) < 0) {
                        JOptionPane.showMessageDialog(null, "VOUCHER TELAH EXPIRED, TIDAK DAPAT DIGUNAKAN");
                    } else {
                        this.jumlahVoucherField.setValue(this.voucher.getJumlah());
                        long totalBaru = this.totalBelanja - Long.parseLong(this.jumlahVoucherField.getValue().toString());
                        if (totalBaru < 0L) {
                            JOptionPane.showMessageDialog(null, "TOTAL BELANJA HARUS LEBIH BESAR DARI VOUCHER");
                            this.voucherIdField.selectAll();
                        } else {
                            this.newTotalField.setValue(totalBaru);
                            this.tunaiField.requestFocus();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "VOUCHER TELAH DIGUNAKAN SEBELUMNYA");
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    public String getKodeMetodePembayaran() {
        return this.kodeMetodePembayaran;
    }

    public void setKodeMetodePembayaran(String kodeMetodePembayaran) {
        this.kodeMetodePembayaran = kodeMetodePembayaran;
        if (kodeMetodePembayaran != null && kodeMetodePembayaran.equals("bayarditempat")) {
            this.cashBtn.setSelected(true);
            this.transferBtn.setSelected(false);
            this.debitBtn.setSelected(false);
        }
        if (kodeMetodePembayaran != null && (kodeMetodePembayaran.equals("bca") || kodeMetodePembayaran.equals("mandiri") || kodeMetodePembayaran.equals("bni") || kodeMetodePembayaran.equals("bri"))) {
            this.cashBtn.setSelected(false);
            this.transferBtn.setSelected(true);
            this.debitBtn.setSelected(false);
        }
    }

    public String getStatusPembayaranTO() {
        return this.statusPembayaranTO;
    }

    public void setStatusPembayaranTO(String statusPembayaranTO) {
        this.statusPembayaranTO = statusPembayaranTO;
    }

    public int getIdSo() {
        return this.idSo;
    }

    public void setIdSo(int idSo) {
        this.idSo = idSo;
    }

    public int getIdTo() {
        return this.idTo;
    }

    public void setIdTo(int idTo) {
        this.idTo = idTo;
    }

    private int rataKanan(String price, int x) {
        int len = price.length();
        for (int i = 0; i < len; ++i) {
            x -= 4;
        }
        return x;
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            CetakNota2.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            CetakNota2.this.canGoThrough = false;
        }
    }

    class printerBesar
    implements Printable {
        printerBesar() {
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
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("namaToko"), x, y + yK);
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("namaGudang"), x, y + yK);
            }
            yK += yP;
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            String telp1 = "";
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = CetakNota2.this.konstantaService.getConstantValue("telpToko1");
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = CetakNota2.this.konstantaService.getConstantValue("telpGudang1");
            }
            if (!telp1.equals("")) {
                g.drawString("TELP: " + telp1, x, y + (yK += yP));
            }
            String telp2 = "";
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = CetakNota2.this.konstantaService.getConstantValue("telpToko2");
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = CetakNota2.this.konstantaService.getConstantValue("telpGudang2");
            }
            if (!telp2.equals("")) {
                g.drawString("TELP: " + telp2, x, y + (yK += yP));
            }
            g.drawString("Cust: " + CetakNota2.this.customer.getId() + " - " + CetakNota2.this.customer.getNama(), x + 0, y + (yK += yP));
            if (CetakNota2.this.penjualan.getSalesId() != null) {
                g.drawString("Sales: " + CetakNota2.this.sales.getId() + " - " + CetakNota2.this.sales.getNama(), x + 0, y + (yK += yP));
            }
            if (CetakNota2.this.penjualan.getKeterangan().equals("KREDIT")) {
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String tanggal = format.format(CetakNota2.this.penjualan.getJatuhTempo());
                g.drawString("Jatuh Tempo: " + tanggal, x + 0, y + (yK += yP));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Bon. " + CetakNota2.this.penjualan.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + CetakNota2.this.shiftKasir.getOpenedBy(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            float totalDiscount = 0.0f;
            long totalBelanja = 0L;
            for (Object[] penjualanSementara : CetakNota2.this.penjualanSementaras) {
                Barang barang = CetakNota2.this.barangService.getBarang(penjualanSementara[2].toString());
                g.drawString(barang.getNama(), x + 0, y + (yK += yP));
                yK = yK + yP - 1;
                Object value = penjualanSementara[4];
                g.drawString(value + penjualanSementara[5].toString(), x + 20, y + yK);
                g.drawString("x", x + 60, y + yK);
                g.drawString(CetakNota2.this.helper.formatNumber(penjualanSementara[6] + ""), x + 70, y + yK);
                g.drawString("=", x + 110, y + yK);
                int xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber((long)(((Float)penjualanSementara[4]).floatValue() * (float)((Long)penjualanSementara[6]).longValue()) + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber((long)((float)((Long)penjualanSementara[6]).longValue() * ((Float)penjualanSementara[4]).floatValue()) + ""), x + xtemp, y + yK);
                if ((Long)penjualanSementara[7] > 0L) {
                    g.drawString("*Disc ( -" + (Long)penjualanSementara[7] + ")", x + 0, y + (yK += yP));
                }
                totalDiscount += (float)((Long)penjualanSementara[7]).longValue();
                totalBelanja = (long)((float)totalBelanja + (float)((Long)penjualanSementara[6]).longValue() * ((Float)penjualanSementara[4]).floatValue());
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total item: " + CetakNota2.this.penjualanSementaras.size(), x + 0, y + (yK += yP));
            int xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(totalBelanja + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber(totalBelanja + ""), x + xtemp, y + yK);
            yK += yP;
            if (totalDiscount > 0.0f) {
                g.drawString("Hemat ", x + 0, y + yK);
                xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber("-" + (long)totalDiscount + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber("-" + (long)totalDiscount + "") + "", x + xtemp, y + yK);
                xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + (yK += yP));
                yK += yP;
            }
            g.drawString("Pembulatan", x + 0, y + yK);
            xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber((float)totalBelanja - totalDiscount + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber((float)totalBelanja - totalDiscount + ""), x + xtemp, y + yK);
            g.drawString("Tunai", x + 0, y + (yK += yP));
            xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.tunaiField.getValue() + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.tunaiField.getValue() + ""), x + xtemp, y + yK);
            if (CetakNota2.this.voucher != null) {
                g.drawString("Voucher", x + 0, y + (yK += yP));
                xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.jumlahVoucherField.getValue() + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.jumlahVoucherField.getValue() + ""), x + xtemp, y + yK);
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Kembalian", x + 0, y + (yK += yP));
            xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.kembaliField.getValue() + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.kembaliField.getValue() + ""), x + xtemp, y + yK);
            if (!(CetakNota2.this.customer.getId().equalsIgnoreCase("C") || CetakNota2.this.customer.getId().equalsIgnoreCase("SINAR") || CetakNota2.this.customer.getId().equalsIgnoreCase("LUKAS"))) {
                if (CetakNota2.this.customer.getHutang() > 0L) {
                    g.drawString("Sisa Hutang", x + 0, y + (yK += yP));
                    xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getHutang() + ""), xRataKanan);
                    g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getHutang() + ""), x + xtemp, y + yK);
                }
                yK += yP;
                String aktifkanPoint = CetakNota2.this.konstantaService.getConstantValue("aktifkanPoint");
                if (aktifkanPoint.equals("YES")) {
                    g.drawString("Point Belanja", x + 0, y + yK);
                    g.drawString("(+" + (long)CetakNota2.this.totalPoint + ")", x + 120, y + yK);
                    xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getPoint() + ""), xRataKanan);
                    g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getPoint() + ""), x + xtemp, y + yK);
                }
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(CetakNota2.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + CetakNota2.this.penjualan.getTanggal().getHours() + ":" + CetakNota2.this.penjualan.getTanggal().getMinutes() + ":" + CetakNota2.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("TERIMA KASIH", x + 57, y + (yK += yP));
            yK += yP;
            String npwp = CetakNota2.this.konstantaService.getConstantValue("npwp");
            if (!npwp.equals("")) {
                g.drawString("npwp: " + npwp, x, y + yK);
                double dpp = 0.9090909090909091 * (double)totalBelanja;
                long ppn = totalBelanja - (long)dpp;
                g.drawString("dpp: " + (long)dpp + ", ppn: " + ppn, x, y + (yK += yP));
                yK += yP;
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + yK);
            yK += yP;
            String aktifkanVoucher = CetakNota2.this.konstantaService.getConstantValue("aktifkanVoucher");
            if (aktifkanVoucher.equals("YES")) {
                int minimumPembelian = Integer.parseInt(CetakNota2.this.konstantaService.getConstantValue("minimumPembelian"));
                long total = totalBelanja;
                long jumlahVoucher = total / (long)minimumPembelian;
                int b = 0;
                while ((long)b < jumlahVoucher) {
                    String ketString = CetakNota2.this.konstantaService.getConstantValue("keteranganVoucher");
                    String[] ketSplit = ketString.split("\n");
                    for (int a = 0; a < ketSplit.length; ++a) {
                        String tes = ketSplit[a];
                        int xPlus = 0;
                        if (tes.equals("%%%")) {
                            yK += 17;
                            g.setFont(new Font("Times New Roman", 0, 30));
                            tes = CetakNota2.this.helper.formatNumber(CetakNota2.this.konstantaService.getConstantValue("voucherBelanja"));
                            xPlus = 30;
                        }
                        g.drawString(tes, x + xPlus, y + yK);
                        g.setFont(new Font("Times New Roman", 0, 8));
                        yK += yP;
                    }
                    g.drawString("Tgl " + tanggal, x + 0, y + yK);
                    g.drawString("Jam " + CetakNota2.this.penjualan.getTanggal().getHours() + ":" + CetakNota2.this.penjualan.getTanggal().getMinutes() + ":" + CetakNota2.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
                    g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += yP));
                    yK += yP;
                    ++b;
                }
            }
            return 0;
        }
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
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("namaToko"), x, y + yK);
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("namaGudang"), x, y + yK);
            }
            yK += yP;
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            String telp1 = "";
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = CetakNota2.this.konstantaService.getConstantValue("telpToko1");
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = CetakNota2.this.konstantaService.getConstantValue("telpGudang1");
            }
            if (!telp1.equals("")) {
                g.drawString("TELP: " + telp1, x, y + (yK += yP));
            }
            String telp2 = "";
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = CetakNota2.this.konstantaService.getConstantValue("telpToko2");
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = CetakNota2.this.konstantaService.getConstantValue("telpGudang2");
            }
            if (!telp2.equals("")) {
                g.drawString("TELP: " + telp2, x, y + (yK += yP));
            }
            g.drawString("Cust: " + CetakNota2.this.customer.getId() + " - " + CetakNota2.this.customer.getNama(), x + 0, y + (yK += yP));
            if (CetakNota2.this.penjualan.getSalesId() != null) {
                g.drawString("Sales: " + CetakNota2.this.sales.getId() + " - " + CetakNota2.this.sales.getNama(), x + 0, y + (yK += yP));
            }
            if (CetakNota2.this.penjualan.getKeterangan().equals("KREDIT")) {
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String tanggal = format.format(CetakNota2.this.penjualan.getJatuhTempo());
                g.drawString("Jatuh Tempo: " + tanggal, x + 0, y + (yK += yP));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Bon. " + CetakNota2.this.penjualan.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + CetakNota2.this.shiftKasir.getOpenedBy(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            float totalDiscount = 0.0f;
            long totalBelanja = 0L;
            for (Object[] penjualanSementara : CetakNota2.this.penjualanSementaras) {
                g.drawString(penjualanSementara[3].toString(), x + 0, y + (yK += yP));
                yK = yK + yP - 1;
                Object value = penjualanSementara[4];
                g.drawString(value + penjualanSementara[5].toString(), x + 20, y + yK);
                g.drawString("x", x + 60, y + yK);
                g.drawString(CetakNota2.this.helper.formatNumber(penjualanSementara[6] + ""), x + 70, y + yK);
                g.drawString("=", x + 110, y + yK);
                int xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber((long)(((Float)penjualanSementara[4]).floatValue() * (float)((Long)penjualanSementara[6]).longValue()) + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber((long)((float)((Long)penjualanSementara[6]).longValue() * ((Float)penjualanSementara[4]).floatValue()) + ""), x + xtemp, y + yK);
                if ((Long)penjualanSementara[7] > 0L) {
                    g.drawString("*Disc ( -" + (Long)penjualanSementara[7] + ")", x + 0, y + (yK += yP));
                }
                totalDiscount += (float)((Long)penjualanSementara[7]).longValue();
                totalBelanja = (long)((float)totalBelanja + (float)((Long)penjualanSementara[6]).longValue() * ((Float)penjualanSementara[4]).floatValue());
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total item: " + CetakNota2.this.penjualanSementaras.size(), x + 0, y + (yK += yP));
            int xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(totalBelanja + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber(totalBelanja + ""), x + xtemp, y + yK);
            yK += yP;
            if (totalDiscount > 0.0f) {
                g.drawString("Hemat ", x + 0, y + yK);
                xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber("-" + (long)totalDiscount + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber("-" + (long)totalDiscount + "") + "", x + xtemp, y + yK);
                xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + (yK += yP));
                yK += yP;
            }
            g.drawString("Pembulatan", x + 0, y + yK);
            xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber((float)totalBelanja - totalDiscount + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber((float)totalBelanja - totalDiscount + ""), x + xtemp, y + yK);
            g.drawString("Tunai", x + 0, y + (yK += yP));
            xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.tunaiField.getValue() + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.tunaiField.getValue() + ""), x + xtemp, y + yK);
            if (CetakNota2.this.voucher != null) {
                g.drawString("Voucher", x + 0, y + (yK += yP));
                xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.jumlahVoucherField.getValue() + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.jumlahVoucherField.getValue() + ""), x + xtemp, y + yK);
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Kembalian", x + 0, y + (yK += yP));
            xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.kembaliField.getValue() + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.kembaliField.getValue() + ""), x + xtemp, y + yK);
            if (!(CetakNota2.this.customer.getId().equalsIgnoreCase("C") || CetakNota2.this.customer.getId().equalsIgnoreCase("SINAR") || CetakNota2.this.customer.getId().equalsIgnoreCase("LUKAS"))) {
                if (CetakNota2.this.customer.getHutang() > 0L) {
                    g.drawString("Sisa Hutang", x + 0, y + (yK += yP));
                    xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getHutang() + ""), xRataKanan);
                    g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getHutang() + ""), x + xtemp, y + yK);
                }
                yK += yP;
                String aktifkanPoint = CetakNota2.this.konstantaService.getConstantValue("aktifkanPoint");
                if (aktifkanPoint.equals("YES")) {
                    g.drawString("Point Belanja", x + 0, y + yK);
                    g.drawString("(+" + (long)CetakNota2.this.totalPoint + ")", x + 120, y + yK);
                    xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getPoint() + ""), xRataKanan);
                    g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getPoint() + ""), x + xtemp, y + yK);
                }
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(CetakNota2.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + CetakNota2.this.penjualan.getTanggal().getHours() + ":" + CetakNota2.this.penjualan.getTanggal().getMinutes() + ":" + CetakNota2.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("TERIMA KASIH", x + 57, y + (yK += yP));
            yK += yP;
            String npwp = CetakNota2.this.konstantaService.getConstantValue("npwp");
            if (!npwp.equals("")) {
                g.drawString("npwp: " + npwp, x, y + yK);
                double dpp = 0.9090909090909091 * (double)totalBelanja;
                long ppn = totalBelanja - (long)dpp;
                g.drawString("dpp: " + (long)dpp + ", ppn: " + ppn, x, y + (yK += yP));
                yK += yP;
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + yK);
            yK += yP;
            String aktifkanVoucher = CetakNota2.this.konstantaService.getConstantValue("aktifkanVoucher");
            if (aktifkanVoucher.equals("YES")) {
                int minimumPembelian = Integer.parseInt(CetakNota2.this.konstantaService.getConstantValue("minimumPembelian"));
                long total = totalBelanja;
                long jumlahVoucher = total / (long)minimumPembelian;
                int b = 0;
                while ((long)b < jumlahVoucher) {
                    String ketString = CetakNota2.this.konstantaService.getConstantValue("keteranganVoucher");
                    String[] ketSplit = ketString.split("\n");
                    for (int a = 0; a < ketSplit.length; ++a) {
                        String tes = ketSplit[a];
                        int xPlus = 0;
                        if (tes.equals("%%%")) {
                            yK += 17;
                            g.setFont(new Font("Times New Roman", 0, 30));
                            tes = CetakNota2.this.helper.formatNumber(CetakNota2.this.konstantaService.getConstantValue("voucherBelanja"));
                            xPlus = 30;
                        }
                        g.drawString(tes, x + xPlus, y + yK);
                        g.setFont(new Font("Times New Roman", 0, 8));
                        yK += yP;
                    }
                    g.drawString("Tgl " + tanggal, x + 0, y + yK);
                    g.drawString("Jam " + CetakNota2.this.penjualan.getTanggal().getHours() + ":" + CetakNota2.this.penjualan.getTanggal().getMinutes() + ":" + CetakNota2.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
                    g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += yP));
                    yK += yP;
                    ++b;
                }
            }
            return 0;
        }
    }

    class printerReprint
    implements Printable {
        printerReprint() {
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
            yK += yP;
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("namaToko"), x, y + yK);
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("namaGudang"), x, y + yK);
            }
            yK += yP;
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota2.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            String telp1 = "";
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = CetakNota2.this.konstantaService.getConstantValue("telpToko1");
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = CetakNota2.this.konstantaService.getConstantValue("telpGudang1");
            }
            if (!telp1.equals("")) {
                g.drawString("TELP: " + telp1, x, y + (yK += yP));
            }
            String telp2 = "";
            if (CetakNota2.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = CetakNota2.this.konstantaService.getConstantValue("telpToko2");
            } else if (CetakNota2.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = CetakNota2.this.konstantaService.getConstantValue("telpGudang2");
            }
            if (!telp2.equals("")) {
                g.drawString("TELP: " + telp2, x, y + (yK += yP));
            }
            g.drawString("Cust: " + CetakNota2.this.customer.getId() + " - " + CetakNota2.this.customer.getNama(), x + 0, y + (yK += yP));
            if (CetakNota2.this.penjualan.getSalesId() != null) {
                g.drawString("Sales: " + CetakNota2.this.sales.getId() + " - " + CetakNota2.this.sales.getNama(), x + 0, y + (yK += yP));
            }
            if (CetakNota2.this.penjualan.getKeterangan().equals("KREDIT")) {
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String tanggal = format.format(CetakNota2.this.penjualan.getJatuhTempo());
                g.drawString("Jatuh Tempo: " + tanggal, x + 0, y + (yK += yP));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Bon. " + CetakNota2.this.penjualan.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + CetakNota2.this.shiftKasir.getOpenedBy(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            float totalDiscount = 0.0f;
            long totalBelanja = 0L;
            for (Object[] penjualanSementara : CetakNota2.this.penjualanSementaras) {
                g.drawString(penjualanSementara[3].toString(), x + 0, y + (yK += yP));
                yK = yK + yP - 1;
                Object value = penjualanSementara[4];
                g.drawString(value + penjualanSementara[5].toString(), x + 20, y + yK);
                g.drawString("x", x + 60, y + yK);
                g.drawString(CetakNota2.this.helper.formatNumber(penjualanSementara[6] + ""), x + 70, y + yK);
                g.drawString("=", x + 110, y + yK);
                int xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber((long)(((Float)penjualanSementara[4]).floatValue() * (float)((Long)penjualanSementara[6]).longValue()) + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber((long)((float)((Long)penjualanSementara[6]).longValue() * ((Float)penjualanSementara[4]).floatValue()) + ""), x + xtemp, y + yK);
                if ((Long)penjualanSementara[7] > 0L) {
                    g.drawString("*Disc ( -" + (Long)penjualanSementara[7] + ")", x + 0, y + (yK += yP));
                }
                totalDiscount += (float)((Long)penjualanSementara[7]).longValue();
                totalBelanja = (long)((float)totalBelanja + (float)((Long)penjualanSementara[6]).longValue() * ((Float)penjualanSementara[4]).floatValue());
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total item: " + CetakNota2.this.penjualanSementaras.size(), x + 0, y + (yK += yP));
            int xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(totalBelanja + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber(totalBelanja + ""), x + xtemp, y + yK);
            yK += yP;
            if (totalDiscount > 0.0f) {
                g.drawString("Hemat ", x + 0, y + yK);
                xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber("-" + (long)totalDiscount + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber("-" + (long)totalDiscount + "") + "", x + xtemp, y + yK);
                xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + (yK += yP));
                yK += yP;
            }
            g.drawString("Pembulatan", x + 0, y + yK);
            xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + yK);
            g.drawString("Tunai", x + 0, y + (yK += yP));
            xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.tunaiField.getValue() + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.tunaiField.getValue() + ""), x + xtemp, y + yK);
            if (CetakNota2.this.voucher != null) {
                g.drawString("Voucher", x + 0, y + (yK += yP));
                xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.jumlahVoucherField.getValue() + ""), xRataKanan);
                g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.jumlahVoucherField.getValue() + ""), x + xtemp, y + yK);
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Kembalian", x + 0, y + (yK += yP));
            xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.kembaliField.getValue() + ""), xRataKanan);
            g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.kembaliField.getValue() + ""), x + xtemp, y + yK);
            if (!(CetakNota2.this.customer.getId().equalsIgnoreCase("C") || CetakNota2.this.customer.getId().equalsIgnoreCase("SINAR") || CetakNota2.this.customer.getId().equalsIgnoreCase("LUKAS"))) {
                if (CetakNota2.this.customer.getHutang() > 0L) {
                    g.drawString("Sisa Hutang", x + 0, y + (yK += yP));
                    xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getHutang() + ""), xRataKanan);
                    g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getHutang() + ""), x + xtemp, y + yK);
                }
                yK += yP;
                String aktifkanPoint = CetakNota2.this.konstantaService.getConstantValue("aktifkanPoint");
                if (aktifkanPoint.equals("YES")) {
                    g.drawString("Point Belanja", x + 0, y + yK);
                    g.drawString("(+" + (long)CetakNota2.this.totalPoint + ")", x + 120, y + yK);
                    xtemp = CetakNota2.this.rataKanan(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getPoint() + ""), xRataKanan);
                    g.drawString(CetakNota2.this.helper.formatNumber(CetakNota2.this.customer.getPoint() + ""), x + xtemp, y + yK);
                }
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(CetakNota2.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + CetakNota2.this.penjualan.getTanggal().getHours() + ":" + CetakNota2.this.penjualan.getTanggal().getMinutes() + ":" + CetakNota2.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("TERIMA KASIH", x + 57, y + (yK += yP));
            yK += yP;
            String npwp = CetakNota2.this.konstantaService.getConstantValue("npwp");
            if (!npwp.equals("")) {
                g.drawString("npwp: " + npwp, x, y + yK);
                double dpp = 0.9090909090909091 * (double)totalBelanja;
                long ppn = totalBelanja - (long)dpp;
                g.drawString("dpp: " + (long)dpp + ", ppn: " + ppn, x, y + (yK += yP));
                yK += yP;
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + yK);
            yK += yP;
            String aktifkanVoucher = CetakNota2.this.konstantaService.getConstantValue("aktifkanVoucher");
            if (aktifkanVoucher.equals("YES")) {
                int minimumPembelian = Integer.parseInt(CetakNota2.this.konstantaService.getConstantValue("minimumPembelian"));
                long total = totalBelanja;
                long jumlahVoucher = total / (long)minimumPembelian;
                int b = 0;
                while ((long)b < jumlahVoucher) {
                    String ketString = CetakNota2.this.konstantaService.getConstantValue("keteranganVoucher");
                    String[] ketSplit = ketString.split("\n");
                    for (int a = 0; a < ketSplit.length; ++a) {
                        String tes = ketSplit[a];
                        int xPlus = 0;
                        if (tes.equals("%%%")) {
                            yK += 17;
                            g.setFont(new Font("Times New Roman", 0, 30));
                            tes = CetakNota2.this.helper.formatNumber(CetakNota2.this.konstantaService.getConstantValue("voucherBelanja"));
                            xPlus = 30;
                        }
                        g.drawString(tes, x + xPlus, y + yK);
                        g.setFont(new Font("Times New Roman", 0, 8));
                        yK += yP;
                    }
                    g.drawString("Tgl " + tanggal, x + 0, y + yK);
                    g.drawString("Jam " + CetakNota2.this.penjualan.getTanggal().getHours() + ":" + CetakNota2.this.penjualan.getTanggal().getMinutes() + ":" + CetakNota2.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
                    g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += yP));
                    yK += yP;
                    ++b;
                }
            }
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
            g.drawString("Permintaan Barang - " + CetakNota2.this.penjualan.getId(), x + 0, y + yK);
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (Object[] penjualanSementara : CetakNota2.this.penjualanSementaras) {
                Barang barang = CetakNota2.this.barangService.getBarang(penjualanSementara[2].toString());
                g.drawString(barang.getNama() + " (" + barang.getRakIdToko() + ")", x + 0, y + yK);
                float tempValue = ((Float)penjualanSementara[4]).floatValue();
                Object value = tempValue == (float)((long)tempValue) ? Long.valueOf((Long)penjualanSementara[4]) : penjualanSementara[4];
                g.drawString(value + penjualanSementara[5].toString() + " / " + barang.getStokToko() + barang.getSatuan1(), x + 20, y + (yK += yP));
                yK += yP;
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(CetakNota2.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + yK);
            g.drawString("Jam " + CetakNota2.this.penjualan.getTanggal().getHours() + ":" + CetakNota2.this.penjualan.getTanggal().getMinutes() + ":" + CetakNota2.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
            return 0;
        }
    }
}

