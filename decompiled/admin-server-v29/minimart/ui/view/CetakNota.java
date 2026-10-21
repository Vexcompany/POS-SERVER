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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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

public class CetakNota
extends JDialog {
    private JButton cancelButton;
    private JTextField customerAlamatField;
    private JTextField customerNameField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JTextField jumlahBarangField;
    private JFormattedTextField jumlahVoucherField;
    private JTextField karyawanField;
    private JFormattedTextField kembaliField;
    private JFormattedTextField newTotalField;
    private JCheckBox notaBesarNonPpnCheck;
    private JCheckBox notaBesarPpnCheck;
    private JButton printButton;
    private JFormattedTextField tanggalField;
    private JComboBox<String> transaksiKeteranganCmb;
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
    private ButtonGroup bg = new ButtonGroup();
    private String shortcut;
    private ShiftKasir shiftKasir;
    private int qtyCounter1;
    private int qtyCounter2;
    private int currentSize;
    private int pageCounter;
    private int printCounter;
    private int jumlahPage;
    private long totalInvoice = 0L;
    private SalesOrderService soService = new SalesOrderService();
    private TransaksiOnlineService toService = new TransaksiOnlineService();
    private int idSo = 0;
    private int idTo = 0;
    private String kodeMetodePembayaran = "";
    private String statusPembayaranTO = null;
    private boolean eligiblePwp;
    private boolean eligibleTebusMurah;
    private List<Object[]> penjualanDetails2;
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();

    public CetakNota(Frame parent, boolean modal, String shortcut, Float value, Date jatuhTempo, String keterangan, boolean eligiblePwp, boolean eligibleTebusMurah) {
        super(parent, modal);
        this.initComponents();
        this.shiftKasir = new ShiftKasirService().getOpenedShiftKasir(this.helper.getKasir());
        this.loadData(shortcut, value);
        this.setTitle("Print");
        this.requestDefaultFocus();
        this.jatuhTempo = jatuhTempo;
        this.keterangan = keterangan;
        this.shortcut = shortcut;
        this.eligiblePwp = eligiblePwp;
        this.eligibleTebusMurah = eligibleTebusMurah;
        this.kembaliField.setValue(0);
        this.tunaiField.setValue(0);
        this.tunaiField.setText("");
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.printButton.setEnabled(true);
        if (keterangan.equals("KREDIT")) {
            this.voucherIdField.setEnabled(false);
            this.transaksiKeteranganCmb.setEnabled(false);
            this.tunaiField.requestFocus();
            this.tunaiField.selectAll();
        }
        this.bg.add(this.notaBesarNonPpnCheck);
        this.bg.add(this.notaBesarPpnCheck);
        this.shiftKasir = this.shiftKasirService.getOpenedShiftKasir(this.helper.getKasir());
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
        this.notaBesarPpnCheck = new JCheckBox();
        this.notaBesarNonPpnCheck = new JCheckBox();
        this.transaksiKeteranganCmb = new JComboBox();
        this.jLabel6 = new JLabel();
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
                CetakNota.this.printButtonActionPerformed(evt);
            }
        });
        this.printButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNota.this.printButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakNota.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNota.this.cancelButtonKeyPressed(evt);
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
                CetakNota.this.tunaiFieldKeyPressed(evt);
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
                CetakNota.this.voucherIdFieldKeyPressed(evt);
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
        this.notaBesarPpnCheck.setText("NotaBesarPPN");
        this.notaBesarNonPpnCheck.setText("NotaBesarNonPPN");
        this.transaksiKeteranganCmb.setFont(new Font("Tahoma", 0, 14));
        this.transaksiKeteranganCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"CASH", "TRANSFER", "DEBIT", "QRIS"}));
        this.transaksiKeteranganCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNota.this.transaksiKeteranganCmbKeyPressed(evt);
            }
        });
        this.jLabel6.setFont(new Font("Tahoma", 1, 24));
        this.jLabel6.setText("Pembayaran");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGap(20, 20, 20).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel3).addComponent(this.jLabel4)).addGap(72, 72, 72).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.customerAlamatField, GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.karyawanField, GroupLayout.Alignment.LEADING).addComponent(this.tanggalField, GroupLayout.Alignment.LEADING).addComponent(this.customerNameField)).addGap(24, 24, 24).addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jumlahBarangField, -2, 61, -2))).addGap(23, 23, 23)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel8, -2, 115, -2).addComponent(this.jLabel2).addComponent(this.jLabel7).addComponent(this.jLabel9).addComponent(this.jLabel6)).addGap(4, 4, 4).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jumlahVoucherField, GroupLayout.Alignment.TRAILING).addComponent(this.voucherIdField).addComponent(this.tunaiField).addComponent(this.kembaliField).addComponent(this.newTotalField, -1, 222, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(32, 32, 32).addComponent(this.transaksiKeteranganCmb, 0, -1, Short.MAX_VALUE))).addGap(0, 0, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addContainerGap(108, Short.MAX_VALUE).addComponent(this.notaBesarPpnCheck).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.notaBesarNonPpnCheck).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.printButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton, -2, -1, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.tanggalField, -2, -1, -2).addComponent(this.jumlahBarangField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.karyawanField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.customerNameField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerAlamatField, -2, -1, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel8).addGroup(layout.createSequentialGroup().addComponent(this.voucherIdField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jumlahVoucherField, -2, 30, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel9).addComponent(this.newTotalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addGap(20, 20, 20).addComponent(this.jLabel7).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.tunaiField, -2, 45, -2).addGap(9, 9, 9).addComponent(this.kembaliField))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.transaksiKeteranganCmb, -2, -1, -2).addComponent(this.jLabel6)).addGap(16, 16, 16).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.printButton, -2, -1, -2).addComponent(this.cancelButton, -2, -1, -2).addComponent(this.notaBesarNonPpnCheck).addComponent(this.notaBesarPpnCheck)).addContainerGap()));
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
        block54: {
            int b;
            long jumlahVoucher;
            block55: {
                int tempup;
                PageFormat pf;
                PrinterJob job;
                block53: {
                    int tempup2;
                    block51: {
                        block52: {
                            block50: {
                                int result;
                                boolean canSave = true;
                                int piutangResult = 0;
                                if (!this.customer.getId().equalsIgnoreCase("C") && Long.parseLong(this.tunaiField.getValue().toString()) != 0L && Integer.parseInt(this.tunaiField.getValue().toString()) < Integer.parseInt(this.newTotalField.getValue() + "")) {
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
                                    this.keterangan = this.transaksiKeteranganCmb.getSelectedItem().toString();
                                }
                                this.penjualan.setKeterangan(this.keterangan);
                                if (this.keterangan.equals("KREDIT")) {
                                    this.penjualan.setStatusPelunasan("BELUM LUNAS");
                                } else if (this.keterangan.equals("CASH") || this.keterangan.equals("TRANSFER") || this.keterangan.equals("DEBIT") || this.keterangan.equals("QRIS")) {
                                    this.penjualan.setStatusPelunasan("SUDAH LUNAS");
                                }
                                this.penjualan.setJatuhTempo(this.jatuhTempo);
                                if (this.shortcut == "F10") {
                                    this.penjualan.setIdTransaksiOnline(CetakNota.padWithLeadingZeros(this.idTo));
                                }
                                try {
                                    this.penjualan.setVoucher(this.voucher.getId());
                                }
                                catch (Exception e) {
                                    this.penjualan.setVoucher(null);
                                }
                                if (this.eligiblePwp) {
                                    this.penjualan.setEligiblePwp(1);
                                } else {
                                    this.penjualan.setEligiblePwp(0);
                                }
                                if (this.eligibleTebusMurah) {
                                    this.penjualan.setEligibleTebusMurah(1);
                                } else {
                                    this.penjualan.setEligibleTebusMurah(0);
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
                                }
                                this.customer.setUpdated(new Date());
                                ArrayList<PenjualanDetail> penjualanDetails = new ArrayList<PenjualanDetail>();
                                ArrayList<StockCommit> stockCommits = new ArrayList<StockCommit>();
                                int z = 0;
                                LocalDateTime startCount = null;
                                for (Object[] penjualanSementara : this.penjualanSementaras) {
                                    StockCommit stockCommit1;
                                    if (++z == 1) {
                                        startCount = (LocalDateTime)penjualanSementara[12];
                                    }
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
                                    if (penjualanSementara[11] == null) {
                                        penjualanDetail.setPromo("");
                                    } else {
                                        penjualanDetail.setPromo(penjualanSementara[11].toString());
                                    }
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
                                long selisihDetik = Duration.between(startCount, LocalDateTime.now()).getSeconds();
                                System.out.println("Lamatransaksi = " + selisihDetik);
                                this.penjualan.setLamaTransaksi(selisihDetik);
                                int[] savePenjualanDetailStatus = this.penjualanDetailService.createdPenjualanDetailBatch(penjualanDetails);
                                int[] saveStockCommitStatus = this.stockCommitService.insertStockCommitBatch(stockCommits);
                                this.penjualanService.updatePenjualan(this.penjualan);
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
                                if (!this.notaBesarPpnCheck.isSelected()) break block50;
                                this.penjualanDetails2 = this.penjualanDetailService.readPenjualanDetailByPenjualanId2(this.penjualan.getId());
                                this.qtyCounter1 = 0;
                                this.qtyCounter2 = 0;
                                this.pageCounter = 1;
                                this.currentSize = this.penjualanDetails2.size();
                                this.jumlahPage = this.penjualanDetails2.size() / 50;
                                if (this.penjualanDetails2.size() % 50 > 0) {
                                    ++this.jumlahPage;
                                }
                                this.qtyCounter2 = this.jumlahPage > 1 ? 50 : this.penjualanDetails2.size();
                                break block51;
                            }
                            if (!this.notaBesarNonPpnCheck.isSelected()) break block52;
                            this.penjualanDetails2 = this.penjualanDetailService.readPenjualanDetailByPenjualanId2(this.penjualan.getId());
                            this.qtyCounter1 = 0;
                            this.qtyCounter2 = 0;
                            this.pageCounter = 1;
                            this.currentSize = this.penjualanDetails2.size();
                            this.jumlahPage = this.penjualanDetails2.size() / 50;
                            if (this.penjualanDetails2.size() % 50 > 0) {
                                ++this.jumlahPage;
                            }
                            this.qtyCounter2 = this.jumlahPage > 1 ? 50 : this.penjualanDetails2.size();
                            break block53;
                        }
                        PrinterJob job2 = PrinterJob.getPrinterJob();
                        PageFormat pf2 = job2.defaultPage();
                        Paper p = pf2.getPaper();
                        p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                        p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                        pf2.setPaper(p);
                        Book book = new Book();
                        book.append(new printerReprint(), pf2);
                        job2.setPageable(book);
                        try {
                            job2.print();
                        }
                        catch (PrinterException book2) {
                            // empty catch block
                        }
                        String aktifkanVoucher = this.konstantaService.getConstantValue("aktifkanVoucher");
                        if (!aktifkanVoucher.equals("YES")) break block54;
                        int minimumPembelian = Integer.parseInt(this.konstantaService.getConstantValue("minimumPembelian"));
                        long total = this.totalBelanja;
                        jumlahVoucher = total / (long)minimumPembelian;
                        b = 0;
                        break block55;
                    }
                    for (int a = 0; a < this.jumlahPage; ++this.pageCounter, this.qtyCounter1 += 50, this.currentSize -= 50, this.qtyCounter2 += tempup2, ++a) {
                        this.printCounter = 0;
                        job = PrinterJob.getPrinterJob();
                        pf = job.defaultPage();
                        Paper p = pf.getPaper();
                        p.setSize(p.getWidth(), p.getHeight());
                        p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight());
                        pf.setPaper(p);
                        Book book = new Book();
                        book.append(new printerBesar(), pf);
                        job.setPageable(book);
                        try {
                            job.print();
                        }
                        catch (PrinterException printerException) {
                            // empty catch block
                        }
                        tempup2 = 0;
                        tempup2 = this.currentSize > 50 ? 50 : this.currentSize;
                    }
                    break block54;
                }
                for (int a = 0; a < this.jumlahPage; ++this.pageCounter, this.qtyCounter1 += 50, this.currentSize -= 50, this.qtyCounter2 += tempup, ++a) {
                    this.printCounter = 0;
                    job = PrinterJob.getPrinterJob();
                    pf = job.defaultPage();
                    Paper p = pf.getPaper();
                    p.setSize(p.getWidth(), p.getHeight());
                    p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight());
                    pf.setPaper(p);
                    Book book2 = new Book();
                    book2.append(new printerBesarNonPPN(), pf);
                    job.setPageable(book2);
                    try {
                        job.print();
                    }
                    catch (PrinterException tempup2) {
                        // empty catch block
                    }
                    tempup = 0;
                    tempup = this.currentSize > 50 ? 50 : this.currentSize;
                }
                break block54;
            }
            while ((long)b < jumlahVoucher) {
                System.out.println("masuk=" + b);
                PrinterJob voucherJob = PrinterJob.getPrinterJob();
                PageFormat voucherPf = voucherJob.defaultPage();
                Paper voucherP = voucherPf.getPaper();
                voucherP.setSize(voucherP.getWidth(), voucherP.getHeight() + 1500.0);
                voucherP.setImageableArea(15.0, 0.0, voucherP.getWidth(), voucherP.getHeight() + 1500.0);
                voucherPf.setPaper(voucherP);
                Book voucherBook = new Book();
                voucherBook.append(new voucherPrint(), voucherPf);
                voucherJob.setPageable(voucherBook);
                try {
                    voucherJob.print();
                }
                catch (PrinterException printerException) {
                    // empty catch block
                }
                ++b;
            }
        }
        this.syncService.doSyncPenjualanAndPenjualanDetailUnsynced();
        this.syncService.doSyncVoucherUnsynced();
    }

    private void printButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.konstantaService.getConstantValue("runExcelPenjualan").equals("1")) {
                JOptionPane.showMessageDialog(null, "Harap Tunggu Hingga Penjualan Excel Selesai");
            } else if (!this.helper.isInteger(this.tunaiField.getText()) && Long.parseLong(this.tunaiField.getValue().toString()) == 0L) {
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
            this.transaksiKeteranganCmb.requestFocus();
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
                this.tunaiField.setBackground(Color.white);
                if (this.keterangan.equals("KREDIT")) {
                    this.printButton.requestFocus();
                } else {
                    this.transaksiKeteranganCmb.requestFocus();
                }
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
                } else if (!this.voucher.getCustomerId().equals(this.customer.getId())) {
                    JOptionPane.showMessageDialog(null, "Voucher Harus Dipakai Customer Yang Sama");
                } else if (this.voucher.getAvailable().equals("TRUE")) {
                    Date expDate = this.voucher.getExpiredDate();
                    SimpleDateFormat formatter = new SimpleDateFormat("/yyyy-MM-dd");
                    Date todayWithZeroTime = null;
                    try {
                        todayWithZeroTime = formatter.parse(formatter.format(new Date()));
                    }
                    catch (ParseException ex) {
                        Logger.getLogger(CetakNota.class.getName()).log(Level.SEVERE, null, ex);
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

    private void transaksiKeteranganCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.printButton.requestFocus();
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
            this.transaksiKeteranganCmb.setSelectedItem("CASH");
        }
        if (kodeMetodePembayaran != null && (kodeMetodePembayaran.equals("bca") || kodeMetodePembayaran.equals("mandiri") || kodeMetodePembayaran.equals("bni") || kodeMetodePembayaran.equals("bri"))) {
            this.transaksiKeteranganCmb.setSelectedItem("TRANSFER");
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
            CetakNota.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            CetakNota.this.canGoThrough = false;
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
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("namaToko"), x, y + yK);
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("namaGudang"), x, y + yK);
            }
            yK += yP;
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            String telp1 = "";
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = CetakNota.this.konstantaService.getConstantValue("telpToko1");
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = CetakNota.this.konstantaService.getConstantValue("telpGudang1");
            }
            if (!telp1.equals("")) {
                g.drawString("TELP: " + telp1, x, y + (yK += yP));
            }
            String telp2 = "";
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = CetakNota.this.konstantaService.getConstantValue("telpToko2");
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = CetakNota.this.konstantaService.getConstantValue("telpGudang2");
            }
            if (!telp2.equals("")) {
                g.drawString("TELP: " + telp2, x, y + (yK += yP));
            }
            g.drawString("Cust: " + CetakNota.this.customer.getId() + " - " + CetakNota.this.customer.getNama(), x + 0, y + (yK += yP));
            if (!CetakNota.this.penjualan.getSalesId().equals("")) {
                g.drawString("Sales: " + CetakNota.this.sales.getId() + " - " + CetakNota.this.sales.getNama(), x + 0, y + (yK += yP));
            }
            if (CetakNota.this.penjualan.getKeterangan().equals("KREDIT")) {
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String tanggal = format.format(CetakNota.this.penjualan.getJatuhTempo());
                g.drawString("Jatuh Tempo: " + tanggal, x + 0, y + (yK += yP));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Bon. " + CetakNota.this.penjualan.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + CetakNota.this.shiftKasir.getOpenedBy(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            float totalDiscount = 0.0f;
            long totalBelanja = 0L;
            for (Object[] penjualanSementara : CetakNota.this.penjualanSementaras) {
                g.drawString(penjualanSementara[3].toString(), x + 0, y + (yK += yP));
                yK = yK + yP - 1;
                Object value = penjualanSementara[4];
                g.drawString(value + penjualanSementara[5].toString(), x + 20, y + yK);
                g.drawString("x", x + 60, y + yK);
                g.drawString(CetakNota.this.helper.formatNumber(penjualanSementara[6] + ""), x + 70, y + yK);
                g.drawString("=", x + 110, y + yK);
                int xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber((long)(((Float)penjualanSementara[4]).floatValue() * (float)((Long)penjualanSementara[6]).longValue()) + ""), xRataKanan);
                g.drawString(CetakNota.this.helper.formatNumber((long)((float)((Long)penjualanSementara[6]).longValue() * ((Float)penjualanSementara[4]).floatValue()) + ""), x + xtemp, y + yK);
                if ((Long)penjualanSementara[7] > 0L) {
                    g.drawString("*Disc ( -" + (Long)penjualanSementara[7] + ")", x + 0, y + (yK += yP));
                } else if ((Long)penjualanSementara[7] < 0L) {
                    g.drawString("*Disc ( " + -1L * (Long)penjualanSementara[7] + ")", x + 0, y + (yK += yP));
                }
                totalDiscount += (float)((Long)penjualanSementara[7]).longValue();
                totalBelanja = (long)((float)totalBelanja + (float)((Long)penjualanSementara[6]).longValue() * ((Float)penjualanSementara[4]).floatValue());
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total item: " + CetakNota.this.penjualanSementaras.size(), x + 0, y + (yK += yP));
            int xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(totalBelanja + ""), xRataKanan);
            g.drawString(CetakNota.this.helper.formatNumber(totalBelanja + ""), x + xtemp, y + yK);
            yK += yP;
            if (totalDiscount > 0.0f) {
                g.drawString("Hemat ", x + 0, y + yK);
                xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber("-" + (long)totalDiscount + ""), xRataKanan);
                g.drawString(CetakNota.this.helper.formatNumber("-" + (long)totalDiscount + "") + "", x + xtemp, y + yK);
                xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
                g.drawString(CetakNota.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + (yK += yP));
                yK += yP;
            }
            g.drawString("Pembulatan", x + 0, y + yK);
            xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
            g.drawString(CetakNota.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + yK);
            g.drawString("Tunai", x + 0, y + (yK += yP));
            xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.tunaiField.getValue() + ""), xRataKanan);
            g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.tunaiField.getValue() + ""), x + xtemp, y + yK);
            if (CetakNota.this.voucher != null) {
                g.drawString("Voucher", x + 0, y + (yK += yP));
                xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.jumlahVoucherField.getValue() + ""), xRataKanan);
                g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.jumlahVoucherField.getValue() + ""), x + xtemp, y + yK);
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Kembalian", x + 0, y + (yK += yP));
            xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.kembaliField.getValue() + ""), xRataKanan);
            g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.kembaliField.getValue() + ""), x + xtemp, y + yK);
            if (!(CetakNota.this.customer.getId().equalsIgnoreCase("C") || CetakNota.this.customer.getId().equalsIgnoreCase("SINAR") || CetakNota.this.customer.getId().equalsIgnoreCase("LUKAS"))) {
                if (CetakNota.this.customer.getHutang() > 0L) {
                    g.drawString("Sisa Hutang", x + 0, y + (yK += yP));
                    xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.customer.getHutang() + ""), xRataKanan);
                    g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.customer.getHutang() + ""), x + xtemp, y + yK);
                }
                yK += yP;
                String aktifkanPoint = CetakNota.this.konstantaService.getConstantValue("aktifkanPoint");
                if (aktifkanPoint.equals("YES")) {
                    g.drawString("Point Belanja", x + 0, y + yK);
                    g.drawString("(+" + (long)CetakNota.this.totalPoint + ")", x + 120, y + yK);
                    xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.customer.getPoint() + ""), xRataKanan);
                    g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.customer.getPoint() + ""), x + xtemp, y + yK);
                }
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(CetakNota.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + CetakNota.this.penjualan.getTanggal().getHours() + ":" + CetakNota.this.penjualan.getTanggal().getMinutes() + ":" + CetakNota.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("TERIMA KASIH", x + 57, y + (yK += yP));
            g.setFont(new Font("Times New Roman", 2, 8));
            String footerNota1 = CetakNota.this.konstantaService.getConstantValue("footerNota1");
            String footerNota2 = CetakNota.this.konstantaService.getConstantValue("footerNota2");
            if (footerNota1 != null || !footerNota1.equals("")) {
                g.drawString(footerNota1, x + 35, y + (yK += yP));
            }
            if (footerNota2 != null || !footerNota2.equals("")) {
                g.drawString(footerNota2, x + 32, y + (yK += yP));
            }
            yK += yP;
            String npwp = CetakNota.this.konstantaService.getConstantValue("npwp");
            if (!npwp.equals("")) {
                g.drawString("npwp: " + npwp, x, y + yK);
                double dpp = 0.9090909090909091 * (double)totalBelanja;
                long ppn = totalBelanja - (long)dpp;
                g.drawString("dpp: " + (long)dpp + ", ppn: " + ppn, x, y + (yK += yP));
                yK += yP;
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + yK);
            yK += yP;
            long total = totalBelanja;
            return 0;
        }
    }

    class voucherPrint
    implements Printable {
        voucherPrint() {
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
            yK += yP;
            String ketString = CetakNota.this.konstantaService.getConstantValue("keteranganVoucher");
            String[] ketSplit = ketString.split("\n");
            for (int a = 0; a < ketSplit.length; ++a) {
                String tes = ketSplit[a];
                g.drawString(tes, x, y + yK);
                yK += yP;
            }
            g.drawString("ID Penjualan", x, y + yK);
            g.drawString(": " + CetakNota.this.penjualan.getId(), x + 55, y + yK);
            g.drawString("ID Customer", x, y + (yK += yP));
            if (!CetakNota.this.customer.getId().equals("C")) {
                g.drawString(": " + CetakNota.this.customer.getId(), x + 55, y + yK);
            } else {
                g.drawString(":", x + 55, y + yK);
            }
            g.drawString("Nama Customer", x, y + (yK += yP));
            if (!CetakNota.this.customer.getId().equals("C")) {
                g.drawString(": " + CetakNota.this.customer.getNama(), x + 55, y + yK);
            } else {
                g.drawString(":", x + 55, y + yK);
            }
            g.drawString("Nomor Telp", x, y + (yK += yP));
            if (!CetakNota.this.customer.getId().equals("C")) {
                g.drawString(": " + CetakNota.this.customer.getTelepon(), x + 55, y + yK);
            } else {
                g.drawString(":", x + 55, y + yK);
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(CetakNota.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + CetakNota.this.penjualan.getTanggal().getHours() + ":" + CetakNota.this.penjualan.getTanggal().getMinutes() + ":" + CetakNota.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
            return 0;
        }
    }

    class printerBesarNonPPN
    implements Printable {
        printerBesarNonPPN() {
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
            if (page > 0) {
                return 1;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            int y = 10;
            int x = 25;
            int yK = 18;
            int yP = 11;
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("CV SINAR KARUNIA SULUNG", x, y + yK);
            g.setFont(new Font("Times New Roman", 1, 18));
            g.drawString("INVOICE", x + 200, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("Number", x + 325, y + yK);
            g.drawString(": " + CetakNota.this.penjualan.getId(), x + 400, y + yK);
            yK += yP;
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            g.drawString("Inv Date", x + 325, y + yK);
            g.drawString(": " + format.format(CetakNota.this.penjualan.getTanggal()) + "", x + 400, y + yK);
            String telp1 = "";
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = CetakNota.this.konstantaService.getConstantValue("telpToko1");
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = CetakNota.this.konstantaService.getConstantValue("telpGudang1");
            }
            g.drawString("TELP: " + telp1, x, y + (yK += yP));
            g.drawString("Due Date", x + 325, y + yK);
            g.drawString(": " + format.format(CetakNota.this.penjualan.getJatuhTempo()), x + 400, y + yK);
            String telp2 = "";
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = CetakNota.this.konstantaService.getConstantValue("telpToko2");
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = CetakNota.this.konstantaService.getConstantValue("telpGudang2");
            }
            g.drawString("TELP: " + telp2, x, y + (yK += yP));
            g.drawString("Payment Term", x + 325, y + yK);
            if (CetakNota.this.penjualan.getKeterangan().equals("KREDIT")) {
                g.drawString(": " + CetakNota.this.customer.getTop() + "", x + 400, y + yK);
            } else {
                g.drawString(": CASH", x + 400, y + yK);
            }
            g.drawString("Customer", x + 0, y + (yK += yP));
            g.drawString(": " + CetakNota.this.customer.getId() + " - " + CetakNota.this.customer.getNama() + "/ " + CetakNota.this.customer.getNamaPerusahaan(), x + 50 + 0, y + yK);
            g.drawString(": " + CetakNota.this.customer.getTelepon(), x + 50 + 0, y + (yK += yP));
            g.drawString(": " + CetakNota.this.customer.getAlamat(), x + 50 + 0, y + (yK += yP));
            g.drawString("Page: " + CetakNota.this.pageCounter + "/" + CetakNota.this.jumlahPage, x + 465 + 0, y + yK);
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            yK += yP;
            int kol1 = 0;
            int kol2 = 25;
            int kol3 = 180;
            int kol4 = 215;
            int kol5 = 250;
            int kol6 = 325;
            int kol7 = 400;
            int kol8 = 465;
            int xRataKananKol3 = 200;
            int xRataKananKol8 = 500;
            g.drawString("No. ", x + kol1, y + yK);
            g.drawString("Nama Barang", x + kol2, y + yK);
            g.drawString("Jumlah", x + kol3, y + yK);
            g.drawString("Sat", x + kol4, y + yK);
            g.drawString("Harga", x + kol5, y + yK);
            g.drawString("Total Harga", x + kol6, y + yK);
            g.drawString("Diskon", x + kol7, y + yK);
            g.drawString("Net Harga", x + kol8, y + yK);
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            CetakNota.this.totalPoint = 0.0f;
            for (int b = CetakNota.this.qtyCounter1; b < CetakNota.this.qtyCounter2; ++b) {
                Object[] dataPenjualanDetail = (Object[])CetakNota.this.penjualanDetails2.get(b);
                g.drawString(b + 1 + "", x + kol1, y + (yK += yP));
                g.drawString(dataPenjualanDetail[0].toString(), x + kol2, y + yK);
                float tempValue = Float.parseFloat(dataPenjualanDetail[1].toString());
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)Float.parseFloat(dataPenjualanDetail[1].toString())) : (Number)Float.valueOf(Float.parseFloat(dataPenjualanDetail[1].toString()));
                int xtemp = CetakNota.this.rataKanan(value + "", xRataKananKol3);
                g.drawString(value + "", x + xtemp, y + yK);
                g.drawString(dataPenjualanDetail[2].toString(), x + kol4, y + yK);
                g.drawString(CetakNota.this.helper.formatNumber(Math.round(Long.parseLong(dataPenjualanDetail[3].toString())) + ""), x + kol5, y + yK);
                g.drawString(CetakNota.this.helper.formatNumber(Math.round(Long.parseLong(dataPenjualanDetail[3].toString()) * (long)Float.parseFloat(dataPenjualanDetail[1].toString())) + ""), x + kol6, y + yK);
                if (Long.parseLong(dataPenjualanDetail[4].toString()) > 0L) {
                    g.drawString("-" + Math.round(Long.parseLong(dataPenjualanDetail[4].toString())), x + kol7, y + yK);
                } else {
                    g.drawString("0", x + kol7, y + yK);
                }
                xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(Math.round(Long.parseLong(dataPenjualanDetail[5].toString())) + ""), xRataKananKol8);
                g.drawString(CetakNota.this.helper.formatNumber(Math.round(Long.parseLong(dataPenjualanDetail[5].toString())) + "") + "", x + xtemp, y + yK);
                if (CetakNota.this.printCounter != 0) continue;
                CetakNota.this.totalInvoice = CetakNota.this.totalInvoice + (long)Math.round(Long.parseLong(dataPenjualanDetail[5].toString()));
            }
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total : ", x + kol6 + 50, y + (yK += yP));
            int xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.totalInvoice + ""), xRataKananKol8);
            g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.totalInvoice + ""), x + xtemp, y + yK);
            g.drawString("Deskripsi: ", x + 0, y + yK);
            g.drawString("Pembayaran: " + CetakNota.this.konstantaService.getConstantValue("bank") + " " + CetakNota.this.konstantaService.getConstantValue("rekBank") + " a/n " + CetakNota.this.konstantaService.getConstantValue("namaBank"), x + 0, y + (yK += yP));
            g.drawString("Harap melakukan KONFIRMASI setelah melakukan pembayaran & melampirkan bukti transfer", x + 0, y + (yK += yP));
            yK += yP;
            yK += yP;
            if (CetakNota.this.pageCounter == CetakNota.this.jumlahPage) {
                g.drawString("Approved By", x, y + yK);
                g.drawString("CV SINAR KARUNIA SULUNG", x, y + (yK += 75));
            }
            CetakNota.this.printCounter = CetakNota.this.printCounter + 1;
            return 0;
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
            int y = 10;
            int x = 25;
            int yK = 18;
            int yP = 11;
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("CV SINAR KARUNIA SULUNG", x, y + yK);
            g.setFont(new Font("Times New Roman", 1, 18));
            g.drawString("INVOICE", x + 200, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("Number", x + 325, y + yK);
            g.drawString(": " + CetakNota.this.penjualan.getId(), x + 400, y + yK);
            yK += yP;
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            g.drawString("Inv Date", x + 325, y + yK);
            g.drawString(": " + format.format(CetakNota.this.penjualan.getTanggal()) + "", x + 400, y + yK);
            String telp1 = "";
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = CetakNota.this.konstantaService.getConstantValue("telpToko1");
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = CetakNota.this.konstantaService.getConstantValue("telpGudang1");
            }
            g.drawString("TELP: " + telp1, x, y + (yK += yP));
            g.drawString("Due Date", x + 325, y + yK);
            g.drawString(": " + format.format(CetakNota.this.penjualan.getJatuhTempo()), x + 400, y + yK);
            String telp2 = "";
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = CetakNota.this.konstantaService.getConstantValue("telpToko2");
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = CetakNota.this.konstantaService.getConstantValue("telpGudang2");
            }
            g.drawString("TELP: " + telp2, x, y + (yK += yP));
            g.drawString("Payment Term", x + 325, y + yK);
            if (CetakNota.this.penjualan.getKeterangan().equals("KREDIT")) {
                g.drawString(": " + CetakNota.this.customer.getTop() + "", x + 400, y + yK);
            } else {
                g.drawString(": CASH", x + 400, y + yK);
            }
            g.drawString("Customer", x + 0, y + (yK += yP));
            g.drawString(": " + CetakNota.this.customer.getId() + " - " + CetakNota.this.customer.getNama() + "/ " + CetakNota.this.customer.getNamaPerusahaan(), x + 50 + 0, y + yK);
            g.drawString(": " + CetakNota.this.customer.getTelepon(), x + 50 + 0, y + (yK += yP));
            g.drawString(": " + CetakNota.this.customer.getAlamat(), x + 50 + 0, y + (yK += yP));
            g.drawString("Page: " + CetakNota.this.pageCounter + "/" + CetakNota.this.jumlahPage, x + 465 + 0, y + yK);
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            yK += yP;
            int kol1 = 0;
            int kol2 = 25;
            int kol3 = 180;
            int kol4 = 215;
            int kol5 = 250;
            int kol6 = 325;
            int kol7 = 400;
            int kol8 = 465;
            int xRataKananKol3 = 200;
            int xRataKananKol8 = 500;
            g.drawString("No. ", x + kol1, y + yK);
            g.drawString("Nama Barang", x + kol2, y + yK);
            g.drawString("Jumlah", x + kol3, y + yK);
            g.drawString("Sat", x + kol4, y + yK);
            g.drawString("Harga", x + kol5, y + yK);
            g.drawString("Total Harga", x + kol6, y + yK);
            g.drawString("Diskon", x + kol7, y + yK);
            g.drawString("Net Harga", x + kol8, y + yK);
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            CetakNota.this.totalPoint = 0.0f;
            for (int b = CetakNota.this.qtyCounter1; b < CetakNota.this.qtyCounter2; ++b) {
                Object[] dataPenjualanDetail = (Object[])CetakNota.this.penjualanDetails2.get(b);
                g.drawString(b + 1 + "", x + kol1, y + (yK += yP));
                g.drawString(dataPenjualanDetail[0].toString(), x + kol2, y + yK);
                float tempValue = Float.parseFloat(dataPenjualanDetail[1].toString());
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)Float.parseFloat(dataPenjualanDetail[1].toString())) : (Number)Float.valueOf(Float.parseFloat(dataPenjualanDetail[1].toString()));
                int xtemp = CetakNota.this.rataKanan(value + "", xRataKananKol3);
                g.drawString(value + "", x + xtemp, y + yK);
                g.drawString(dataPenjualanDetail[2].toString(), x + kol4, y + yK);
                g.drawString(CetakNota.this.helper.formatNumber(Math.round((double)Long.parseLong(dataPenjualanDetail[3].toString()) / 1.11) + ""), x + kol5, y + yK);
                g.drawString(CetakNota.this.helper.formatNumber(Math.round((double)(Long.parseLong(dataPenjualanDetail[3].toString()) * (long)Float.parseFloat(dataPenjualanDetail[1].toString())) / 1.11) + ""), x + kol6, y + yK);
                if (Long.parseLong(dataPenjualanDetail[4].toString()) > 0L) {
                    g.drawString("-" + Math.round((double)Long.parseLong(dataPenjualanDetail[4].toString()) / 1.11), x + kol7, y + yK);
                } else {
                    g.drawString("0", x + kol7, y + yK);
                }
                xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(Math.round((double)Long.parseLong(dataPenjualanDetail[5].toString()) / 1.11) + ""), xRataKananKol8);
                g.drawString(CetakNota.this.helper.formatNumber(Math.round((double)Long.parseLong(dataPenjualanDetail[5].toString()) / 1.11) + "") + "", x + xtemp, y + yK);
                if (CetakNota.this.printCounter != 0) continue;
                CetakNota.this.totalInvoice = CetakNota.this.totalInvoice + Math.round((double)Long.parseLong(dataPenjualanDetail[5].toString()) / 1.11);
            }
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total : ", x + kol6 + 50, y + (yK += yP));
            int xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.totalInvoice + ""), xRataKananKol8);
            g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.totalInvoice + ""), x + xtemp, y + yK);
            g.drawString("Deskripsi: ", x + 0, y + yK);
            g.drawString("Pembayaran: " + CetakNota.this.konstantaService.getConstantValue("bank") + " " + CetakNota.this.konstantaService.getConstantValue("rekBank") + " a/n " + CetakNota.this.konstantaService.getConstantValue("namaBank"), x + 0, y + (yK += yP));
            if (CetakNota.this.pageCounter == CetakNota.this.jumlahPage) {
                g.drawString("PPN : ", x + kol6 + 50, y + yK);
                xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(Math.round((double)CetakNota.this.totalInvoice * 0.11) + ""), xRataKananKol8);
                g.drawString(CetakNota.this.helper.formatNumber(Math.round((double)CetakNota.this.totalInvoice * 0.11) + ""), x + xtemp, y + yK);
            }
            g.drawString("Harap melakukan KONFIRMASI setelah melakukan pembayaran & melampirkan bukti transfer", x + 0, y + (yK += yP));
            if (CetakNota.this.pageCounter == CetakNota.this.jumlahPage) {
                g.drawString("Total Invoice : ", x + kol6 + 50, y + yK);
                xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(Math.round((double)CetakNota.this.totalInvoice * 1.11) + ""), xRataKananKol8);
                g.drawString(CetakNota.this.helper.formatNumber(Math.round((double)CetakNota.this.totalInvoice * 1.11) + ""), x + xtemp, y + yK);
            }
            yK += yP;
            yK += yP;
            if (CetakNota.this.pageCounter == CetakNota.this.jumlahPage) {
                g.drawString("Approved By", x, y + yK);
                g.drawString("CV SINAR KARUNIA SULUNG", x, y + (yK += 75));
            }
            CetakNota.this.printCounter = CetakNota.this.printCounter + 1;
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
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("namaToko"), x, y + yK);
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("namaGudang"), x, y + yK);
            }
            yK += yP;
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNota.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            String telp1 = "";
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = CetakNota.this.konstantaService.getConstantValue("telpToko1");
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = CetakNota.this.konstantaService.getConstantValue("telpGudang1");
            }
            if (!telp1.equals("")) {
                g.drawString("TELP: " + telp1, x, y + (yK += yP));
            }
            String telp2 = "";
            if (CetakNota.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = CetakNota.this.konstantaService.getConstantValue("telpToko2");
            } else if (CetakNota.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = CetakNota.this.konstantaService.getConstantValue("telpGudang2");
            }
            if (!telp2.equals("")) {
                g.drawString("TELP: " + telp2, x, y + (yK += yP));
            }
            g.drawString("Cust: " + CetakNota.this.customer.getId() + " - " + CetakNota.this.customer.getNama(), x + 0, y + (yK += yP));
            if (!CetakNota.this.penjualan.getSalesId().equals("")) {
                g.drawString("Sales: " + CetakNota.this.sales.getId() + " - " + CetakNota.this.sales.getNama(), x + 0, y + (yK += yP));
            }
            if (CetakNota.this.penjualan.getKeterangan().equals("KREDIT")) {
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String tanggal = format.format(CetakNota.this.penjualan.getJatuhTempo());
                g.drawString("Jatuh Tempo: " + tanggal, x + 0, y + (yK += yP));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Bon. " + CetakNota.this.penjualan.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + CetakNota.this.shiftKasir.getOpenedBy(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            float totalDiscount = 0.0f;
            long totalBelanja = 0L;
            for (Object[] penjualanSementara : CetakNota.this.penjualanSementaras) {
                g.drawString(penjualanSementara[3].toString(), x + 0, y + (yK += yP));
                yK = yK + yP - 1;
                Object value = penjualanSementara[4];
                g.drawString(value + penjualanSementara[5].toString(), x + 20, y + yK);
                g.drawString("x", x + 60, y + yK);
                g.drawString(CetakNota.this.helper.formatNumber(penjualanSementara[6] + ""), x + 70, y + yK);
                g.drawString("=", x + 110, y + yK);
                int xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber((long)(((Float)penjualanSementara[4]).floatValue() * (float)((Long)penjualanSementara[6]).longValue()) + ""), xRataKanan);
                g.drawString(CetakNota.this.helper.formatNumber((long)((float)((Long)penjualanSementara[6]).longValue() * ((Float)penjualanSementara[4]).floatValue()) + ""), x + xtemp, y + yK);
                if ((Long)penjualanSementara[7] > 0L) {
                    g.drawString("*Disc ( -" + (Long)penjualanSementara[7] + ")", x + 0, y + (yK += yP));
                } else if ((Long)penjualanSementara[7] < 0L) {
                    g.drawString("*Disc ( " + -1L * (Long)penjualanSementara[7] + ")", x + 0, y + (yK += yP));
                }
                totalDiscount += (float)((Long)penjualanSementara[7]).longValue();
                totalBelanja = (long)((float)totalBelanja + (float)((Long)penjualanSementara[6]).longValue() * ((Float)penjualanSementara[4]).floatValue());
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total item: " + CetakNota.this.penjualanSementaras.size(), x + 0, y + (yK += yP));
            int xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(totalBelanja + ""), xRataKanan);
            g.drawString(CetakNota.this.helper.formatNumber(totalBelanja + ""), x + xtemp, y + yK);
            yK += yP;
            if (totalDiscount > 0.0f) {
                g.drawString("Hemat ", x + 0, y + yK);
                xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber("-" + (long)totalDiscount + ""), xRataKanan);
                g.drawString(CetakNota.this.helper.formatNumber("-" + (long)totalDiscount + "") + "", x + xtemp, y + yK);
                xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
                g.drawString(CetakNota.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + (yK += yP));
                yK += yP;
            }
            g.drawString("Pembulatan", x + 0, y + yK);
            xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
            g.drawString(CetakNota.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + yK);
            g.drawString("Tunai", x + 0, y + (yK += yP));
            xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.tunaiField.getValue() + ""), xRataKanan);
            g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.tunaiField.getValue() + ""), x + xtemp, y + yK);
            if (CetakNota.this.voucher != null) {
                g.drawString("Voucher", x + 0, y + (yK += yP));
                xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.jumlahVoucherField.getValue() + ""), xRataKanan);
                g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.jumlahVoucherField.getValue() + ""), x + xtemp, y + yK);
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Kembalian", x + 0, y + (yK += yP));
            xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.kembaliField.getValue() + ""), xRataKanan);
            g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.kembaliField.getValue() + ""), x + xtemp, y + yK);
            if (!(CetakNota.this.customer.getId().equalsIgnoreCase("C") || CetakNota.this.customer.getId().equalsIgnoreCase("SINAR") || CetakNota.this.customer.getId().equalsIgnoreCase("LUKAS"))) {
                if (CetakNota.this.customer.getHutang() > 0L) {
                    g.drawString("Sisa Hutang", x + 0, y + (yK += yP));
                    xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.customer.getHutang() + ""), xRataKanan);
                    g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.customer.getHutang() + ""), x + xtemp, y + yK);
                }
                yK += yP;
                String aktifkanPoint = CetakNota.this.konstantaService.getConstantValue("aktifkanPoint");
                if (aktifkanPoint.equals("YES")) {
                    g.drawString("Point Belanja", x + 0, y + yK);
                    g.drawString("(+" + (long)CetakNota.this.totalPoint + ")", x + 120, y + yK);
                    xtemp = CetakNota.this.rataKanan(CetakNota.this.helper.formatNumber(CetakNota.this.customer.getPoint() + ""), xRataKanan);
                    g.drawString(CetakNota.this.helper.formatNumber(CetakNota.this.customer.getPoint() + ""), x + xtemp, y + yK);
                }
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(CetakNota.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + CetakNota.this.penjualan.getTanggal().getHours() + ":" + CetakNota.this.penjualan.getTanggal().getMinutes() + ":" + CetakNota.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("TERIMA KASIH", x + 57, y + (yK += yP));
            g.setFont(new Font("Times New Roman", 2, 8));
            String footerNota1 = CetakNota.this.konstantaService.getConstantValue("footerNota1");
            String footerNota2 = CetakNota.this.konstantaService.getConstantValue("footerNota2");
            if (footerNota1 != null || !footerNota1.equals("")) {
                g.drawString(footerNota1, x + 35, y + (yK += yP));
            }
            if (footerNota2 != null || !footerNota2.equals("")) {
                g.drawString(footerNota2, x + 32, y + (yK += yP));
            }
            yK += yP;
            String npwp = CetakNota.this.konstantaService.getConstantValue("npwp");
            if (!npwp.equals("")) {
                g.drawString("npwp: " + npwp, x, y + yK);
                double dpp = 0.9090909090909091 * (double)totalBelanja;
                long ppn = totalBelanja - (long)dpp;
                g.drawString("dpp: " + (long)dpp + ", ppn: " + ppn, x, y + (yK += yP));
                yK += yP;
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + yK);
            yK += yP;
            String aktifkanVoucher = CetakNota.this.konstantaService.getConstantValue("aktifkanVoucher");
            if (aktifkanVoucher.equals("YES")) {
                int minimumPembelian = Integer.parseInt(CetakNota.this.konstantaService.getConstantValue("minimumPembelian"));
                long total = totalBelanja;
                long jumlahVoucher = total / (long)minimumPembelian;
                int b = 0;
                while ((long)b < jumlahVoucher) {
                    String ketString = CetakNota.this.konstantaService.getConstantValue("keteranganVoucher");
                    String[] ketSplit = ketString.split("\n");
                    for (int a = 0; a < ketSplit.length; ++a) {
                        String tes = ketSplit[a];
                        int xPlus = 0;
                        if (tes.equals("%%%")) {
                            yK += 17;
                            g.setFont(new Font("Times New Roman", 0, 30));
                            tes = CetakNota.this.helper.formatNumber(CetakNota.this.konstantaService.getConstantValue("voucherBelanja"));
                            xPlus = 30;
                        }
                        g.drawString(tes, x + xPlus, y + yK);
                        g.setFont(new Font("Times New Roman", 0, 8));
                        yK += yP;
                    }
                    g.drawString("Tgl " + tanggal, x + 0, y + yK);
                    g.drawString("Jam " + CetakNota.this.penjualan.getTanggal().getHours() + ":" + CetakNota.this.penjualan.getTanggal().getMinutes() + ":" + CetakNota.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
                    g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += yP));
                    yK += yP;
                    ++b;
                }
            }
            return 0;
        }
    }
}

