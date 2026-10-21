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
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Controller;
import minimart.engine.controller.Helper;
import minimart.engine.dao.CustomerDao;
import minimart.engine.daoimpl.CustomerDaoImpl;
import minimart.engine.model.Barang;
import minimart.engine.model.Customer;
import minimart.engine.model.JSONItemMigrasi;
import minimart.engine.model.JSONMigrasiHeader;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Penjualan;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.model.PenjualanSementara;
import minimart.engine.model.Sales;
import minimart.engine.model.ShiftKasir;
import minimart.engine.model.StockCommit;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerPointCommitService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.MigrasiOnlineService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.PenjualanCabangSementaraService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.PiutangService;
import minimart.engine.service.SalesService;
import minimart.engine.service.ShiftKasirService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.StokCommitService;
import minimart.engine.service.SyncService;
import minimart.ui.listener.CetakNotaListener;
import minimart.ui.listener.ConfirmationDialogListener;

public class CetakNotaCabang
extends JDialog {
    private String idPickingList;
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
    private JTextField jumlahBarangField;
    private JTextField karyawanField;
    private JFormattedTextField kembaliField;
    private JButton printButton;
    private JFormattedTextField tanggalField;
    private JFormattedTextField totalField;
    private JFormattedTextField tunaiField;
    private PenjualanCabangSementaraService penjualanSementaraService = new PenjualanCabangSementaraService();
    private BarangService barangService = new BarangService();
    private CustomerService customerService = new CustomerService();
    private ShiftKasirService shiftKasirService = new ShiftKasirService();
    private Helper helper = Helper.getInstance();
    private Karyawan karyawan;
    private Customer customer;
    private PenjualanService penjualanService = new PenjualanService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private MigrasiOnlineService migrasiOnlineService = new MigrasiOnlineService();
    private StokCommitService stockCommitService = new StokCommitService();
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();
    private List<PenjualanSementara> penjualanSementaras;
    private CetakNotaListener listener;
    private SyncService syncService = new SyncService();
    private Penjualan penjualan;
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    private PiutangService piutangService = new PiutangService();
    private PembelianService pembelianService = new PembelianService();
    private boolean canGoThrough;
    private int screenHeight;
    private int screenWidth;
    private long totalRokok = 0L;
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

    public CetakNotaCabang(Frame parent, boolean modal, String shortcut, Float value, Date jatuhTempo, String keterangan, String idPickingList) {
        super(parent, modal);
        this.initComponents();
        this.loadData(shortcut, value);
        this.setTitle("Print");
        this.requestDefaultFocus();
        this.jatuhTempo = jatuhTempo;
        this.keterangan = keterangan;
        this.idPickingList = idPickingList;
        this.kembaliField.setValue(0);
        this.tunaiField.setValue(0);
        this.tunaiField.setText("");
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.printButton.setEnabled(true);
    }

    protected void requestDefaultFocus() {
        this.tunaiField.requestFocus();
        this.tunaiField.setText(this.tunaiField.getText().replace(".", ""));
        this.tunaiField.selectAll();
    }

    public void addListener(CetakNotaListener listener) {
        this.listener = listener;
    }

    private void loadData(String shortcut, Float parentValue) {
        this.penjualanSementaras = this.penjualanSementaraService.getPenjualanSementaraByShortcut(shortcut);
        this.jumlahBarangField.setText(this.penjualanSementaras.size() + "");
        if (this.penjualanSementaras.size() > 0) {
            this.karyawan = this.helper.getKaryawan();
            this.customer = this.customerService.getCustomerById(this.penjualanSementaras.get(0).getCustomerId());
            this.sales = this.salesService.getSales(this.penjualanSementaras.get(0).getSalesId());
            this.tanggalField.setValue(new Date());
            this.karyawanField.setText(this.karyawan.getNama());
            this.customerNameField.setText(this.customer.getNama());
            this.customerAlamatField.setText(this.customer.getAlamat());
            long total = 0L;
            for (PenjualanSementara penjualanSementara : this.penjualanSementaras) {
                total += penjualanSementara.getTotalHarga();
                Barang tempBarang = this.barangService.getBarang(penjualanSementara.getBarangId());
                if (!tempBarang.getJenis().equals("ROKOK")) continue;
                this.totalRokok += penjualanSementara.getTotalHarga();
            }
            if (parentValue.floatValue() != (float)total) {
                this.totalField.setBackground(Color.red);
                JOptionPane.showMessageDialog(null, "Refresh Data!");
            }
            long finalTotal = this.helper.roundPrice100(total);
            this.totalField.setValue(finalTotal);
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
        this.jLabel6 = new JLabel();
        this.printButton = new JButton();
        this.cancelButton = new JButton();
        this.totalField = new JFormattedTextField();
        this.jLabel2 = new JLabel();
        this.jLabel7 = new JLabel();
        this.tunaiField = new JFormattedTextField();
        this.kembaliField = new JFormattedTextField();
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
        this.jLabel6.setFont(new Font("Tahoma", 1, 24));
        this.jLabel6.setText("Total");
        this.printButton.setText("Print");
        this.printButton.setPreferredSize(new Dimension(73, 23));
        this.printButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakNotaCabang.this.printButtonActionPerformed(evt);
            }
        });
        this.printButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNotaCabang.this.printButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakNotaCabang.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNotaCabang.this.cancelButtonKeyPressed(evt);
            }
        });
        this.totalField.setEditable(false);
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setFont(new Font("Tahoma", 1, 24));
        this.jLabel2.setFont(new Font("Tahoma", 1, 24));
        this.jLabel2.setText("Tunai");
        this.jLabel7.setFont(new Font("Tahoma", 1, 24));
        this.jLabel7.setText("Kembali");
        this.tunaiField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.tunaiField.setFont(new Font("Tahoma", 1, 24));
        this.tunaiField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakNotaCabang.this.tunaiFieldActionPerformed(evt);
            }
        });
        this.tunaiField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNotaCabang.this.tunaiFieldKeyPressed(evt);
            }
        });
        this.kembaliField.setEditable(false);
        this.kembaliField.setBackground(new Color(153, 255, 204));
        this.kembaliField.setForeground(new Color(0, 0, 153));
        this.kembaliField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.kembaliField.setFont(new Font("Tahoma", 1, 24));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(20, 20, 20).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel3).addComponent(this.jLabel4).addComponent(this.jLabel6).addComponent(this.jLabel2).addComponent(this.jLabel7)).addGap(36, 36, 36).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.customerAlamatField, GroupLayout.Alignment.LEADING, -1, 309, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.karyawanField, GroupLayout.Alignment.LEADING, -1, 186, Short.MAX_VALUE).addComponent(this.tanggalField, GroupLayout.Alignment.LEADING, -1, 186, Short.MAX_VALUE).addComponent(this.customerNameField, -1, 186, Short.MAX_VALUE)).addGap(24, 24, 24).addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jumlahBarangField, -2, 61, -2))).addGap(23, 23, 23)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.totalField, -2, 222, -2).addGap(0, 12, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGap(36, 36, 36).addComponent(this.printButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton, -2, -1, -2))).addComponent(this.tunaiField, -2, 222, -2).addComponent(this.kembaliField, -2, 222, -2)).addContainerGap(-1, Short.MAX_VALUE)))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.tanggalField, -2, -1, -2).addComponent(this.jumlahBarangField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.karyawanField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.customerNameField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerAlamatField, -2, -1, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.totalField, -2, 47, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.tunaiField, -2, 45, -2)).addGap(9, 9, 9).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kembaliField).addComponent(this.jLabel7)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.printButton, -2, -1, -2).addComponent(this.cancelButton, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void saveClick() throws IOException {
        block36: {
            boolean canSave = true;
            boolean piutangResult = false;
            if (canSave) {
                this.printButton.setEnabled(false);
                this.cancelButton.requestFocus();
                this.penjualan = new Penjualan();
                this.penjualan.setId(this.helper.generateCodeGeneral(this.penjualanService.getLastId()));
                this.penjualan.setKaryawanId(this.karyawan.getId());
                this.penjualan.setCustomerId(this.customer.getId() + "");
                if (this.sales != null) {
                    this.penjualan.setSalesId(this.sales.getId());
                }
                this.penjualan.setKasir(this.helper.getKasir());
                this.penjualan.setTanggal(new Date());
                this.penjualan.setTotalPenjualan((Long)this.totalField.getValue());
                this.penjualan.setStatus(this.helper.getStatusProgram());
                this.penjualan.setCreated(new Date());
                this.penjualan.setUpdated(new Date());
                this.penjualan.setKeterangan(this.keterangan);
                if (this.keterangan.equals("KREDIT")) {
                    this.penjualan.setStatusPelunasan("BELUM LUNAS");
                } else if (this.keterangan.equals("CASH")) {
                    this.penjualan.setStatusPelunasan("SUDAH LUNAS");
                }
                this.penjualan.setJatuhTempo(this.jatuhTempo);
                int penjualanStatus = 0;
                JSONMigrasiHeader jsonMigrasiHeader = new JSONMigrasiHeader();
                jsonMigrasiHeader.setStatus_1("Accepted");
                jsonMigrasiHeader.setId_cabang_pengirim(Integer.parseInt(this.konstantaService.getConstantValue("idCabang")));
                jsonMigrasiHeader.setId_cabang_penerima(this.customer.getIdCabang());
                jsonMigrasiHeader.setId_penjualan_offline(this.penjualan.getId());
                jsonMigrasiHeader.setTotal_penjualan(this.penjualan.getTotalPenjualan());
                jsonMigrasiHeader.setId_picking_list(this.idPickingList);
                System.out.println(String.valueOf(this.customer.getIdCabang()));
                System.out.println(String.valueOf(this.customer.getId()));
                if (this.customer.getIdCabang() != 0) {
                    ArrayList<JSONItemMigrasi> jsonItemMigrasis = new ArrayList<JSONItemMigrasi>();
                    for (PenjualanSementara penjualanSementara : this.penjualanSementaras) {
                        Barang barang = this.barangService.getBarang(penjualanSementara.getBarangId());
                        if (!this.helper.getStatusProgram().equals("TOKO")) continue;
                        JSONItemMigrasi jsonItemMigrasi = new JSONItemMigrasi();
                        jsonItemMigrasi.setId_migration_header(0);
                        jsonItemMigrasi.setId_cabang_penerima(this.customer.getIdCabang());
                        jsonItemMigrasi.setId_cabang_pengirim(Integer.parseInt(this.konstantaService.getConstantValue("idCabang")));
                        jsonItemMigrasi.setId_item(penjualanSementara.getBarangId());
                        jsonItemMigrasi.setJumlah_permintaan(penjualanSementara.getJumlahBarang());
                        jsonItemMigrasi.setSatuan(penjualanSementara.getSatuan());
                        jsonItemMigrasi.setStatus_1("Accepted");
                        jsonItemMigrasi.setTipe("Kirim");
                        jsonItemMigrasi.setHpp(barang.getHpp());
                        jsonItemMigrasis.add(jsonItemMigrasi);
                    }
                    jsonMigrasiHeader.setJsonItemMigrasis(jsonItemMigrasis);
                    penjualanStatus = this.penjualanService.createPenjualan(this.penjualan);
                    int id_migrasi_header = 0;
                    if (penjualanStatus == 1) {
                        try {
                            id_migrasi_header = this.migrasiOnlineService.insertMigrasiHeader(jsonMigrasiHeader);
                        }
                        catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam penyimpanan Migrasi Online, coba lagi.");
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                            this.penjualanService.deletePenjualan(this.penjualan.getId());
                            id_migrasi_header = -1;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Penyimpanan Transaksi Penjualan Gagal");
                    }
                    if (id_migrasi_header > 0) {
                        if (penjualanStatus == 1) {
                            try {
                                boolean statusMigrasiDetail = true;
                                ArrayList<PenjualanDetail> penjualanDetails = new ArrayList<PenjualanDetail>();
                                ArrayList<StockCommit> stockCommits = new ArrayList<StockCommit>();
                                for (PenjualanSementara penjualanSementara : this.penjualanSementaras) {
                                    PenjualanDetail penjualanDetail = new PenjualanDetail();
                                    Barang barang = this.barangService.getBarang(penjualanSementara.getBarangId());
                                    penjualanDetail.setPenjualanId(this.penjualan.getId());
                                    penjualanDetail.setBarangId(penjualanSementara.getBarangId());
                                    penjualanDetail.setJumlahBarang(penjualanSementara.getJumlahBarang());
                                    penjualanDetail.setSatuan(penjualanSementara.getSatuan());
                                    penjualanDetail.setDiscount2(penjualanSementara.getDiscount());
                                    penjualanDetail.setHargaJual(penjualanSementara.getHargaJual());
                                    penjualanDetail.setTotalHarga(penjualanSementara.getTotalHarga());
                                    float totalBarang = 0.0f;
                                    if (penjualanSementara.getSatuan().equals(barang.getSatuan1())) {
                                        totalBarang = penjualanSementara.getJumlahBarang();
                                    } else if (penjualanSementara.getSatuan().equals(barang.getSatuan2())) {
                                        totalBarang = penjualanSementara.getJumlahBarang() * (float)barang.getKonversi21();
                                    } else if (penjualanSementara.getSatuan().equals(barang.getSatuan3())) {
                                        totalBarang = penjualanSementara.getJumlahBarang() * (float)barang.getKonversi21() * (float)barang.getKonversi32();
                                    }
                                    penjualanDetail.setTotalKeuntungan((long)((float)penjualanSementara.getTotalHarga() - (float)barang.getHpp() * totalBarang));
                                    System.out.println("penjualanDetail = " + penjualanDetail.getTotalKeuntungan());
                                    if (barang.getHpp() > 0L) {
                                        this.totalKeuntungan += penjualanDetail.getTotalKeuntungan();
                                    }
                                    penjualanDetail.setCreated(new Date());
                                    penjualanDetail.setUpdated(new Date());
                                    penjualanDetails.add(penjualanDetail);
                                    if (!this.helper.getStatusProgram().equals("TOKO")) continue;
                                    StockCommit stockCommit1 = new StockCommit();
                                    stockCommit1.setIdBarang(barang.getId());
                                    stockCommit1.setIdTransaksi(this.penjualan.getId());
                                    stockCommit1.setQueryType("kurang");
                                    stockCommit1.setStockType("toko");
                                    stockCommit1.setTransactionType("Penjualan Cabang");
                                    stockCommit1.setJumlahBarang(totalBarang);
                                    stockCommit1.setStatusCommit(true);
                                    stockCommit1.setStokAwal(barang.getStokToko());
                                    stockCommits.add(stockCommit1);
                                }
                                boolean statusPenjualanDetailOffline = true;
                                boolean statusCommitDetailOffline = true;
                                int[] savePenjualanDetailStatus = this.penjualanDetailService.createdPenjualanDetailBatch(penjualanDetails);
                                int[] saveStockCommitStatus = this.stockCommitService.insertStockCommitBatch(stockCommits);
                                for (int savePenjualanDetailStatusObj : savePenjualanDetailStatus) {
                                    System.out.println(savePenjualanDetailStatusObj);
                                    if (savePenjualanDetailStatusObj == 1) continue;
                                    statusPenjualanDetailOffline = false;
                                    break;
                                }
                                if (statusMigrasiDetail && statusPenjualanDetailOffline) {
                                    if (this.helper.getJenisProgram().equals("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO") && this.penjualan.getKeterangan().equals("CASH")) {
                                        ShiftKasir shiftKasir = this.shiftKasirService.getOpenedShiftKasir(this.helper.getKasir());
                                        shiftKasir.setTotalUang(shiftKasir.getTotalUang() + (Long)this.totalField.getValue());
                                        this.shiftKasirService.update(shiftKasir);
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
                                    } else {
                                        book.append(new printer(), pf);
                                    }
                                    job.setPageable(book);
                                    boolean ok = job.printDialog();
                                    if (ok) {
                                        try {
                                            job.print();
                                        }
                                        catch (PrinterException printerException) {
                                            // empty catch block
                                        }
                                    }
                                    this.syncService.doSyncPenjualanAndPenjualanDetailUnsynced();
                                    break block36;
                                }
                                this.penjualanDetailService.deletePenjualanDetailByPenjualanId(this.penjualan.getId());
                                this.penjualanService.deletePenjualan(this.penjualan.getId());
                                JOptionPane.showMessageDialog(null, "Penyimpanan Detail Migrasi Online Gagal.. Membatalkan penjualan yang tersimpan");
                                boolean statusDelete = this.migrasiOnlineService.deleteMigrasiOnline(id_migrasi_header);
                                if (!statusDelete) {
                                    String message = "Gagal menghapus data migrasi online dengan id " + String.valueOf(id_migrasi_header) + ", harap lakukan delete manual melalui web";
                                    JOptionPane.showMessageDialog(null, message);
                                }
                                break block36;
                            }
                            catch (Exception e) {
                                System.out.println("Messages:" + e.getMessage());
                                this.penjualanDetailService.deletePenjualanDetailByPenjualanId(this.penjualan.getId());
                                this.penjualanService.deletePenjualan(this.penjualan.getId());
                                JOptionPane.showMessageDialog(null, "Penyimpanan Detail Migrasi Online Gagal.. Membatalkan penjualan yang tersimpan");
                                boolean statusDelete = this.migrasiOnlineService.deleteMigrasiOnline(id_migrasi_header);
                                if (!statusDelete) {
                                    String message = "Gagal menghapus data migrasi online dengan id " + String.valueOf(id_migrasi_header) + ", harap lakukan delete manual melalui web";
                                    JOptionPane.showMessageDialog(null, message);
                                }
                                break block36;
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Penyimpanan Transaksi Penjualan Gagal");
                        boolean statusDelete = this.migrasiOnlineService.deleteMigrasiOnline(id_migrasi_header);
                        if (!statusDelete) {
                            String message = "Gagal menghapus data migrasi online dengan id " + String.valueOf(id_migrasi_header) + ", harap lakukan delete manual melalui web";
                            JOptionPane.showMessageDialog(null, message);
                        }
                    } else if (penjualanStatus == 1) {
                        this.penjualanService.deletePenjualan(this.penjualan.getId());
                        JOptionPane.showMessageDialog(null, "Gagal menambahkan migrasi online, coba lagi!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Cabang Penerima tidak valid");
                }
            }
        }
    }

    private void printButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (Long.parseLong(this.tunaiField.getValue().toString()) == 0L || Long.parseLong(this.tunaiField.getValue().toString()) >= Long.parseLong(this.totalField.getValue().toString())) {
                try {
                    this.saveClick();
                }
                catch (IOException ex) {
                    Logger.getLogger(CetakNotaCabang.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            if (this.helper.isInteger(this.tunaiField.getText()) && Integer.parseInt(this.tunaiField.getText()) == 0 || this.helper.isInteger(this.tunaiField.getText()) && Integer.parseInt(this.tunaiField.getText()) >= Integer.parseInt(this.totalField.getValue() + "")) {
                this.kembaliField.setValue(Integer.parseInt(this.tunaiField.getText()) - Integer.parseInt(this.totalField.getValue() + ""));
                this.printButton.requestFocus();
                this.tunaiField.setBackground(Color.white);
            } else {
                this.tunaiField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void printButtonActionPerformed(ActionEvent evt) {
        if (Long.parseLong(this.tunaiField.getValue().toString()) == 0L || Long.parseLong(this.tunaiField.getValue().toString()) >= Long.parseLong(this.totalField.getValue().toString())) {
            try {
                this.saveClick();
            }
            catch (IOException ex) {
                Logger.getLogger(CetakNotaCabang.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Uang Tunai Tidak Mencukupi");
        }
    }

    private void tunaiFieldActionPerformed(ActionEvent evt) {
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
            CetakNotaCabang.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            CetakNotaCabang.this.canGoThrough = false;
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
            if (CetakNotaCabang.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNotaCabang.this.konstantaService.getConstantValue("namaToko"), x, y + yK);
            } else if (CetakNotaCabang.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNotaCabang.this.konstantaService.getConstantValue("namaGudang"), x, y + yK);
            }
            yK += yP;
            if (CetakNotaCabang.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNotaCabang.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (CetakNotaCabang.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNotaCabang.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            String telp1 = "";
            if (CetakNotaCabang.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = CetakNotaCabang.this.konstantaService.getConstantValue("telpToko1");
            } else if (CetakNotaCabang.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = CetakNotaCabang.this.konstantaService.getConstantValue("telpGudang1");
            }
            if (!telp1.equals("")) {
                g.drawString("TELP: " + telp1, x, y + (yK += yP));
            }
            String telp2 = "";
            if (CetakNotaCabang.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = CetakNotaCabang.this.konstantaService.getConstantValue("telpToko2");
            } else if (CetakNotaCabang.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = CetakNotaCabang.this.konstantaService.getConstantValue("telpGudang2");
            }
            if (!telp2.equals("")) {
                g.drawString("TELP: " + telp2, x, y + (yK += yP));
            }
            g.drawString("Cust: " + CetakNotaCabang.this.customer.getId() + " - " + CetakNotaCabang.this.customer.getNama(), x + 0, y + (yK += yP));
            if (CetakNotaCabang.this.penjualan.getSalesId() != null) {
                g.drawString("Sales: " + CetakNotaCabang.this.sales.getId() + " - " + CetakNotaCabang.this.sales.getNama(), x + 0, y + (yK += yP));
            }
            if (CetakNotaCabang.this.penjualan.getKeterangan().equals("KREDIT")) {
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String tanggal = format.format(CetakNotaCabang.this.penjualan.getJatuhTempo());
                g.drawString("Jatuh Tempo: " + tanggal, x + 0, y + (yK += yP));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Bon. " + CetakNotaCabang.this.penjualan.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + CetakNotaCabang.this.helper.getKaryawan().getNama(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            float totalDiscount = 0.0f;
            long totalBelanja = 0L;
            for (PenjualanSementara penjualanSementara : CetakNotaCabang.this.penjualanSementaras) {
                yK += yP;
                Barang barang = CetakNotaCabang.this.barangService.getBarang(penjualanSementara.getBarangId());
                if (penjualanSementara.getSatuan().equals(barang.getSatuan1()) && barang.getMinPembelian() > 0.0f && penjualanSementara.getJumlahBarang() >= barang.getMinPembelian()) {
                    barang.setNama(barang.getNama() + " (hsp)");
                }
                if (penjualanSementara.isNotaKhusus()) {
                    g.drawString(barang.getNama() + " (" + barang.getHargaJual1() + "/" + barang.getSatuan1() + ")", x + 0, y + yK);
                } else {
                    g.drawString(barang.getNama(), x + 0, y + yK);
                }
                yK = yK + yP - 1;
                float tempValue = penjualanSementara.getJumlahBarang();
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)penjualanSementara.getJumlahBarang()) : (Number)Float.valueOf(penjualanSementara.getJumlahBarang());
                g.drawString(value + penjualanSementara.getSatuan(), x + 20, y + yK);
                g.drawString("x", x + 60, y + yK);
                g.drawString(CetakNotaCabang.this.helper.formatNumber(penjualanSementara.getHargaJual() + ""), x + 70, y + yK);
                g.drawString("=", x + 110, y + yK);
                int xtemp = CetakNotaCabang.this.rataKanan(CetakNotaCabang.this.helper.formatNumber((long)(penjualanSementara.getJumlahBarang() * (float)penjualanSementara.getHargaJual()) + ""), xRataKanan);
                g.drawString(CetakNotaCabang.this.helper.formatNumber((long)((float)penjualanSementara.getHargaJual() * penjualanSementara.getJumlahBarang()) + ""), x + xtemp, y + yK);
                if (penjualanSementara.getDiscount() > 0L) {
                    g.drawString("*Disc ( -" + penjualanSementara.getDiscount() + ")", x + 0, y + (yK += yP));
                }
                totalDiscount += (float)penjualanSementara.getDiscount();
                totalBelanja = (long)((float)totalBelanja + (float)penjualanSementara.getHargaJual() * penjualanSementara.getJumlahBarang());
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total item: " + CetakNotaCabang.this.penjualanSementaras.size(), x + 0, y + (yK += yP));
            int xtemp = CetakNotaCabang.this.rataKanan(CetakNotaCabang.this.helper.formatNumber(totalBelanja + ""), xRataKanan);
            g.drawString(CetakNotaCabang.this.helper.formatNumber(totalBelanja + ""), x + xtemp, y + yK);
            yK += yP;
            if (totalDiscount > 0.0f) {
                g.drawString("Hemat ", x + 0, y + yK);
                xtemp = CetakNotaCabang.this.rataKanan(CetakNotaCabang.this.helper.formatNumber("-" + (long)totalDiscount + ""), xRataKanan);
                g.drawString(CetakNotaCabang.this.helper.formatNumber("-" + (long)totalDiscount + "") + "", x + xtemp, y + yK);
                xtemp = CetakNotaCabang.this.rataKanan(CetakNotaCabang.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
                g.drawString(CetakNotaCabang.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + (yK += yP));
                yK += yP;
            }
            g.drawString("Pembulatan", x + 0, y + yK);
            xtemp = CetakNotaCabang.this.rataKanan(CetakNotaCabang.this.helper.formatNumber(CetakNotaCabang.this.totalField.getValue() + ""), xRataKanan);
            g.drawString(CetakNotaCabang.this.helper.formatNumber(CetakNotaCabang.this.totalField.getValue() + ""), x + xtemp, y + yK);
            g.drawString("Tunai", x + 0, y + (yK += yP));
            xtemp = CetakNotaCabang.this.rataKanan(CetakNotaCabang.this.helper.formatNumber(CetakNotaCabang.this.tunaiField.getValue() + ""), xRataKanan);
            g.drawString(CetakNotaCabang.this.helper.formatNumber(CetakNotaCabang.this.tunaiField.getValue() + ""), x + xtemp, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Kembalian", x + 0, y + (yK += yP));
            xtemp = CetakNotaCabang.this.rataKanan(CetakNotaCabang.this.helper.formatNumber(CetakNotaCabang.this.kembaliField.getValue() + ""), xRataKanan);
            g.drawString(CetakNotaCabang.this.helper.formatNumber(CetakNotaCabang.this.kembaliField.getValue() + ""), x + xtemp, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(CetakNotaCabang.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + CetakNotaCabang.this.penjualan.getTanggal().getHours() + ":" + CetakNotaCabang.this.penjualan.getTanggal().getMinutes() + ":" + CetakNotaCabang.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("TERIMA KASIH", x + 57, y + (yK += yP));
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += yP));
            yK += yP;
            String aktifkanVoucher = CetakNotaCabang.this.konstantaService.getConstantValue("aktifkanVoucher");
            if (aktifkanVoucher.equals("YES")) {
                int minimumPembelian = Integer.parseInt(CetakNotaCabang.this.konstantaService.getConstantValue("minimumPembelian"));
                long total = (Long)CetakNotaCabang.this.totalField.getValue();
                long jumlahVoucher = (total - CetakNotaCabang.this.totalRokok) / (long)minimumPembelian;
                int b = 0;
                while ((long)b < jumlahVoucher) {
                    String ketString = CetakNotaCabang.this.konstantaService.getConstantValue("keteranganVoucher");
                    String[] ketSplit = ketString.split("\n");
                    for (int a = 0; a < ketSplit.length; ++a) {
                        String tes = ketSplit[a];
                        int xPlus = 0;
                        if (tes.equals("%%%")) {
                            yK += 17;
                            g.setFont(new Font("Times New Roman", 0, 30));
                            tes = CetakNotaCabang.this.helper.formatNumber(CetakNotaCabang.this.konstantaService.getConstantValue("voucherBelanja"));
                            xPlus = 30;
                        }
                        g.drawString(tes, x + xPlus, y + yK);
                        g.setFont(new Font("Times New Roman", 0, 8));
                        yK += yP;
                    }
                    g.drawString("Tgl " + tanggal, x + 0, y + yK);
                    g.drawString("Jam " + CetakNotaCabang.this.penjualan.getTanggal().getHours() + ":" + CetakNotaCabang.this.penjualan.getTanggal().getMinutes() + ":" + CetakNotaCabang.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
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
            g.drawString("Permintaan Barang - " + CetakNotaCabang.this.penjualan.getId(), x + 0, y + yK);
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (PenjualanSementara penjualanSementara : CetakNotaCabang.this.penjualanSementaras) {
                Barang barang = CetakNotaCabang.this.barangService.getBarang(penjualanSementara.getBarangId());
                g.drawString(barang.getNama() + " (" + barang.getRakIdToko() + ")", x + 0, y + yK);
                float tempValue = penjualanSementara.getJumlahBarang();
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)penjualanSementara.getJumlahBarang()) : (Number)Float.valueOf(penjualanSementara.getJumlahBarang());
                g.drawString(value + penjualanSementara.getSatuan() + " / " + barang.getStokToko() + barang.getSatuan1(), x + 20, y + (yK += yP));
                yK += yP;
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(CetakNotaCabang.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + yK);
            g.drawString("Jam " + CetakNotaCabang.this.penjualan.getTanggal().getHours() + ":" + CetakNotaCabang.this.penjualan.getTanggal().getMinutes() + ":" + CetakNotaCabang.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
            return 0;
        }
    }
}

