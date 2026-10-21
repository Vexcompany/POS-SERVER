/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package minimart.ui.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Barcode;
import minimart.engine.model.Customer;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Penjualan;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.model.PromoPeriode;
import minimart.engine.model.PromoVoucher;
import minimart.engine.model.Sales;
import minimart.engine.model.StockCommit;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanParsialDetailService;
import minimart.engine.service.PenjualanParsialService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.PromoPeriodeService;
import minimart.engine.service.PromoVoucherService;
import minimart.engine.service.SalesService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.SyncService;
import minimart.ui.listener.CetakNotaListener;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.PenjualanPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.ReprintDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;
import minimart.ui.view.StokOpnamPanel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PenjualanByExcel2
extends JPanel {
    private JTextField customerField;
    private JLabel customerLbl;
    private JTable errorTbl;
    private JDesktopPane jDesktopPane1;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTabbedPane jTabbedPane1;
    private JDateChooser jatuhTempoField;
    private JLabel jatuhTempoLbl;
    private JLabel karyawanField;
    private JComboBox keteranganCmb;
    private JLabel keteranganLbl;
    private JTable penjualanTable;
    private JTextField salesField;
    private JLabel salesLbl;
    private JLabel salesNameField;
    private JLabel shortcutLbl;
    private JFormattedTextField totalField;
    private JButton uploadBtn1;
    private PenjualanPanelListener listener;
    private KonstantaService konstantaService = new KonstantaService();
    private int screenWidth;
    private int screenHeight;
    private CustomerService customerService = new CustomerService();
    private Customer customer;
    private PromoVoucher promoVoucher;
    private Sales sales;
    private Karyawan karyawan;
    private SalesService salesService = new SalesService();
    private PromoVoucherService promoVoucherService = new PromoVoucherService();
    private PromoPeriodeService promoPeriodeService = new PromoPeriodeService();
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total", "Disc", "Harga Akhir", "Referensi"};
    private String[] errorColumn = new String[]{"Nomor Pesanan", "ID Barang"};
    private SyncService syncService = new SyncService();
    long total = 0L;
    private Helper helper = Helper.getInstance();
    private long totalKeuntungan = 0L;
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();
    private PenjualanService penjualanService = new PenjualanService();
    private BarcodeService barcodeService = new BarcodeService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private String kesalahanData = "";
    private DefaultTableModel errorTableModel = new DefaultTableModel(new String[0][1], this.errorColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel penjualanTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private BarangService barangService = new BarangService();
    private PenjualanDetailService detailService = new PenjualanDetailService();
    private PromoPeriode promoPeriode;
    private PenjualanParsialDetailService penjualanParsialDetailService = new PenjualanParsialDetailService();
    private PenjualanParsialService penjualanParsialService = new PenjualanParsialService();
    private boolean canGoThrough;

    public PenjualanByExcel2(String shortcut) {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        ((AbstractDocument)this.customerField.getDocument()).setDocumentFilter(this.filter);
        this.karyawanField.setText(this.helper.getKaryawan().getNama());
        this.shortcutLbl.setText(shortcut);
        this.karyawan = this.helper.getKaryawan();
        this.penjualanTable.setSelectionMode(0);
        this.jatuhTempoField.setEnabled(false);
        this.jatuhTempoField.setDate(new Date());
        this.salesLbl.setVisible(false);
        this.salesField.setVisible(false);
        this.salesNameField.setVisible(false);
        this.keteranganLbl.setVisible(true);
        this.jatuhTempoLbl.setVisible(true);
        this.promoVoucher = this.promoVoucherService.getPromoVoucherById(1);
        this.promoPeriode = this.promoPeriodeService.getPromoPeriode(1);
        TableColumn col1 = this.penjualanTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.penjualanTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.penjualanTable.getColumnModel().getColumn(3);
        int width = 35;
        col1.setPreferredWidth(170);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
        this.customerField.setEditable(false);
        this.salesField.setEditable(false);
        this.keteranganCmb.setSelectedIndex(0);
        this.customer = this.helper.getStatusProgram().equals("GUDANG") && this.helper.getJenisProgram().equals("GUDANGTOKO") ? this.customerService.getCustomerById("SINAR") : this.customerService.getCustomerById("C");
        this.customerField.setText(this.customer.getId());
        this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
        this.keteranganCmb.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    if (PenjualanByExcel2.this.keteranganCmb.getSelectedItem().equals("CASH")) {
                        PenjualanByExcel2.this.jatuhTempoField.setDate(new Date());
                    } else if (PenjualanByExcel2.this.keteranganCmb.getSelectedItem().equals("KREDIT")) {
                        if (PenjualanByExcel2.this.customer.getId().equals("C")) {
                            JOptionPane.showMessageDialog(null, "Pilih Customer Terlebih Dahulu");
                            PenjualanByExcel2.this.keteranganCmb.setSelectedIndex(0);
                        } else if (PenjualanByExcel2.this.customer.getLimitKredit() == 0 || PenjualanByExcel2.this.customer.getTop() == 0) {
                            JOptionPane.showMessageDialog(null, "Customer " + PenjualanByExcel2.this.customer.getNama() + " Belum Diberikan Fasilitas Kredit");
                            PenjualanByExcel2.this.keteranganCmb.setSelectedIndex(0);
                        } else {
                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            c.add(5, PenjualanByExcel2.this.customer.getTop());
                            PenjualanByExcel2.this.jatuhTempoField.setDate(c.getTime());
                        }
                    }
                } else if (e.getStateChange() == 2) {
                    // empty if block
                }
            }
        });
    }

    private void removeAllRow() {
        int rowCount = this.penjualanTableModel.getRowCount();
        for (int a = 0; a < rowCount; ++a) {
            this.penjualanTableModel.removeRow(0);
        }
    }

    private void removeAllErrorRow() {
        int rowCount = this.errorTableModel.getRowCount();
        for (int a = 0; a < rowCount; ++a) {
            this.errorTableModel.removeRow(0);
        }
    }

    public void setDefaultFocus() {
        this.penjualanTable.requestFocusInWindow();
    }

    private void initComponents() {
        this.jLabel13 = new JLabel();
        this.jDesktopPane1 = new JDesktopPane();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel10 = new JLabel();
        this.customerField = new JTextField();
        this.customerLbl = new JLabel();
        this.karyawanField = new JLabel();
        this.shortcutLbl = new JLabel();
        this.jLabel11 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jLabel12 = new JLabel();
        this.salesLbl = new JLabel();
        this.salesField = new JTextField();
        this.salesNameField = new JLabel();
        this.keteranganLbl = new JLabel();
        this.keteranganCmb = new JComboBox();
        this.jatuhTempoLbl = new JLabel();
        this.jatuhTempoField = new JDateChooser();
        this.jTabbedPane1 = new JTabbedPane();
        this.jScrollPane1 = new JScrollPane();
        this.penjualanTable = new JTable();
        this.jScrollPane2 = new JScrollPane();
        this.errorTbl = new JTable();
        this.uploadBtn1 = new JButton();
        this.jLabel13.setText("jLabel13");
        GroupLayout jDesktopPane1Layout = new GroupLayout(this.jDesktopPane1);
        this.jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));
        jDesktopPane1Layout.setVerticalGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Kasir/");
        this.jLabel2.setText("Customer");
        this.jLabel5.setText("F1:Customer");
        this.jLabel6.setText("F2:Print");
        this.jLabel7.setText("Enter:Edit");
        this.jLabel8.setText("Delete:Hapus");
        this.jLabel9.setText("ESC-Keluar");
        this.jLabel3.setText(":");
        this.jLabel4.setText(":");
        this.jLabel10.setText("F5-F12:PendingTransaksi");
        this.customerField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanByExcel2.this.customerFieldKeyPressed(evt);
            }
        });
        this.karyawanField.setText("-");
        this.shortcutLbl.setFont(new Font("Tahoma", 1, 14));
        this.shortcutLbl.setForeground(new Color(204, 0, 0));
        this.shortcutLbl.setText("F5");
        this.jLabel11.setFont(new Font("Tahoma", 1, 24));
        this.jLabel11.setText("Total");
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setText("0");
        this.totalField.setFont(new Font("Tahoma", 1, 24));
        this.jLabel12.setText("Space: InputBarcode");
        this.salesLbl.setText("Sales");
        this.salesField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanByExcel2.this.salesFieldKeyPressed(evt);
            }
        });
        this.salesNameField.setText("-");
        this.keteranganLbl.setText("Keterangan");
        this.keteranganCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"CASH", "KREDIT"}));
        this.keteranganCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanByExcel2.this.keteranganCmbKeyPressed(evt);
            }
        });
        this.jatuhTempoLbl.setText("Jatuh Tempo");
        this.penjualanTable.setModel(this.penjualanTableModel);
        this.penjualanTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanByExcel2.this.penjualanTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.penjualanTable);
        this.jTabbedPane1.addTab("Penjualan", this.jScrollPane1);
        this.errorTbl.setModel(this.errorTableModel);
        this.jScrollPane2.setViewportView(this.errorTbl);
        this.jTabbedPane1.addTab("Error", this.jScrollPane2);
        this.uploadBtn1.setText("UPLOAD EXCEL");
        this.uploadBtn1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanByExcel2.this.uploadBtn1ActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jTabbedPane1).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel5).addGap(40, 40, 40).addComponent(this.jLabel6).addGap(41, 41, 41).addComponent(this.jLabel10).addGap(43, 43, 43).addComponent(this.jLabel7).addGap(42, 42, 42).addComponent(this.jLabel8).addGap(47, 47, 47).addComponent(this.jLabel12).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE).addComponent(this.jLabel9)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.shortcutLbl))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel3).addComponent(this.jLabel4))).addComponent(this.salesLbl).addComponent(this.keteranganLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.customerField, -1, 124, Short.MAX_VALUE).addComponent(this.karyawanField, -1, -1, Short.MAX_VALUE).addComponent(this.salesField).addComponent(this.keteranganCmb, 0, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.customerLbl, -2, 162, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel11).addGap(18, 18, 18).addComponent(this.totalField, -2, 229, -2)).addGroup(layout.createSequentialGroup().addComponent(this.salesNameField).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.uploadBtn1)).addGroup(layout.createSequentialGroup().addComponent(this.jatuhTempoLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.jatuhTempoField, -2, 123, -2).addGap(0, 0, Short.MAX_VALUE))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -1, -1, Short.MAX_VALUE).addComponent(this.shortcutLbl)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.karyawanField))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.customerLbl, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel4).addComponent(this.customerField, -2, -1, -2)).addGap(0, 12, Short.MAX_VALUE)))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalField, -2, 40, -2).addComponent(this.jLabel11, -1, -1, Short.MAX_VALUE))).addGap(4, 4, 4).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.salesLbl).addComponent(this.salesField, -2, -1, -2).addComponent(this.salesNameField).addComponent(this.uploadBtn1)).addGap(9, 9, 9).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keteranganLbl).addComponent(this.keteranganCmb, -2, -1, -2).addComponent(this.jatuhTempoLbl)).addComponent((Component)this.jatuhTempoField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTabbedPane1, -1, 441, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.jLabel7).addComponent(this.jLabel8).addComponent(this.jLabel9).addComponent(this.jLabel10).addComponent(this.jLabel12)).addContainerGap()));
    }

    private void f2Press() {
        int h;
        int w;
        ConfirmationDialog confirmationDialog;
        int error = this.errorTableModel.getRowCount();
        if (error > 0) {
            this.canGoThrough = false;
            confirmationDialog = new ConfirmationDialog(null, true, "Masih Terdapat Error, Lanjutkan Penyimpanan Data?");
            confirmationDialog.addListener(new confirmDialogEventHandler());
            w = confirmationDialog.getWidth();
            h = confirmationDialog.getHeight();
            confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            confirmationDialog.setVisible(true);
        } else if (this.customer.getId().equals("C")) {
            this.canGoThrough = false;
            JOptionPane.showMessageDialog(null, "Masukkan Customer Terlebih Dahulu");
        } else {
            this.canGoThrough = true;
        }
        if (this.canGoThrough) {
            this.canGoThrough = false;
            confirmationDialog = new ConfirmationDialog(null, true, "Yakin akan menyimpan data ini?");
            confirmationDialog.addListener(new confirmDialogEventHandler());
            w = confirmationDialog.getWidth();
            h = confirmationDialog.getHeight();
            confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            confirmationDialog.setVisible(true);
            if (this.canGoThrough) {
                if (this.penjualanTableModel.getRowCount() > 0) {
                    String idPenjualan;
                    this.konstantaService.updateConstant("runExcelPenjualan", "1");
                    String lastNoPesanan = "";
                    int totalTransaksi = 0;
                    ArrayList<Penjualan> penjualans = new ArrayList<Penjualan>();
                    ArrayList<PenjualanDetail> penjualanDetails = new ArrayList<PenjualanDetail>();
                    String tempId = idPenjualan = this.helper.generateCodeGeneral(this.penjualanService.getLastId());
                    Penjualan tempPenjualan = null;
                    for (int a = 0; a < this.penjualanTableModel.getRowCount(); ++a) {
                        String tempNoPesanan = this.penjualanTableModel.getValueAt(a, 8).toString();
                        if (!tempNoPesanan.equals(lastNoPesanan)) {
                            if (tempPenjualan != null) {
                                tempPenjualan.setTotalPenjualan(totalTransaksi);
                                penjualans.add(tempPenjualan);
                                tempId = this.helper.generateCodeGeneral(tempId);
                            }
                            tempPenjualan = new Penjualan();
                            totalTransaksi = 0;
                            tempPenjualan.setId(tempId);
                            tempPenjualan.setCreated(new Date());
                            tempPenjualan.setCustomerId(this.customer.getId());
                            tempPenjualan.setJatuhTempo(new Date());
                            tempPenjualan.setKaryawanId(this.karyawan.getId());
                            tempPenjualan.setKasir(this.helper.getKasir());
                            tempPenjualan.setKeterangan("KREDIT");
                            tempPenjualan.setPoin(0);
                            tempPenjualan.setReferensi(tempNoPesanan);
                            tempPenjualan.setStatus(this.helper.getStatusProgram());
                            tempPenjualan.setStatusPelunasan("BELUM LUNAS");
                            tempPenjualan.setTanggal(new Date());
                            tempPenjualan.setUpdated(new Date());
                            lastNoPesanan = tempNoPesanan;
                        }
                        totalTransaksi += (int)Float.parseFloat(this.penjualanTableModel.getValueAt(a, 7).toString());
                        if (a == this.penjualanTableModel.getRowCount() - 1) {
                            tempPenjualan.setTotalPenjualan(totalTransaksi);
                            penjualans.add(tempPenjualan);
                        }
                        PenjualanDetail penjualanDetail = new PenjualanDetail();
                        Barang barang = this.barangService.getBarang(this.penjualanTableModel.getValueAt(a, 0) + "");
                        penjualanDetail.setPenjualanId(tempPenjualan.getId());
                        penjualanDetail.setBarangId(barang.getId());
                        penjualanDetail.setJumlahBarang(Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2) + ""));
                        penjualanDetail.setSatuan(this.penjualanTableModel.getValueAt(a, 3) + "");
                        penjualanDetail.setDiscount2(Long.parseLong(this.penjualanTableModel.getValueAt(a, 6) + ""));
                        penjualanDetail.setHargaJual(Long.parseLong(this.penjualanTableModel.getValueAt(a, 4) + ""));
                        penjualanDetail.setTotalHarga((long)Float.parseFloat(this.penjualanTableModel.getValueAt(a, 7) + ""));
                        float totalBarang = 0.0f;
                        if (this.penjualanTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan1())) {
                            totalBarang = Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2) + "");
                        } else if (this.penjualanTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan2())) {
                            totalBarang = Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2) + "") * (float)barang.getKonversi21();
                        } else if (this.penjualanTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan3())) {
                            totalBarang = Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2) + "") * (float)barang.getKonversi21() * (float)barang.getKonversi32();
                        }
                        if (!this.customer.getId().equalsIgnoreCase("SINAR")) {
                            penjualanDetail.setTotalKeuntungan((long)(Float.parseFloat(this.penjualanTableModel.getValueAt(a, 7) + "") - (float)barang.getHpp() * totalBarang));
                            if (barang.getHpp() > 0L) {
                                this.totalKeuntungan += penjualanDetail.getTotalKeuntungan();
                            }
                        }
                        penjualanDetail.setCreated(new Date());
                        penjualanDetail.setUpdated(new Date());
                        penjualanDetails.add(penjualanDetail);
                        StockCommit stockCommit1 = new StockCommit();
                        stockCommit1.setIdBarang(barang.getId());
                        stockCommit1.setIdTransaksi(tempPenjualan.getId());
                        stockCommit1.setQueryType("kurang");
                        stockCommit1.setStockType("toko");
                        stockCommit1.setTransactionType("Penjualan");
                        stockCommit1.setJumlahBarang(totalBarang);
                        this.sinkronisasiBarangService.insertCommit(stockCommit1);
                        this.barangService.updateStokBarangToko(-totalBarang, barang.getId());
                    }
                    this.penjualanService.createPenjualanBatch(penjualans);
                    this.penjualanDetailService.createdPenjualanDetailBatch(penjualanDetails);
                    JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
                    this.konstantaService.updateConstant("runExcelPenjualan", "0");
                    int rowCount = this.penjualanTableModel.getRowCount();
                    for (int a = 0; a < rowCount; ++a) {
                        this.penjualanTableModel.removeRow(0);
                    }
                    this.syncService.doSyncPenjualanAndPenjualanDetailUnsynced();
                } else {
                    JOptionPane.showMessageDialog(null, "Masukkan Data Terlebih Dahulu");
                }
            }
        }
    }

    private void customerFieldKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 10) {
            if (this.customerField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
                sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.customer = this.customerService.getCustomerById(this.customerField.getText());
                if (this.customer == null) {
                    JOptionPane.showMessageDialog(null, "Data Customer Tidak Ditemukan");
                } else if (this.customer.getTag() != null && this.customer.getTag().equals("D")) {
                    JOptionPane.showMessageDialog(null, "Data Customer Sudah Tidak Aktif");
                } else {
                    this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
                    this.penjualanTable.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 112 || evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 27 && (a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3)) == 0) {
            this.listener.escapeClick();
        }
    }

    private void penjualanTableKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 112) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 113) {
            this.f2Press();
        } else if (evt.getKeyCode() == 19 || evt.getKeyCode() == 36) {
            ReprintDialog reprintDialog = new ReprintDialog(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a2 = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3);
            if (a2 == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 127 && this.penjualanTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
            this.removeAllErrorRow();
            this.removeAllRow();
            this.totalField.setValue(0);
        }
    }

    private void salesFieldKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
            sd.addSearchDialogListener(new searchSalesDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.salesField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
                sd.addSearchDialogListener(new searchSalesDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.sales = this.salesService.getSales(this.salesField.getText());
                if (this.sales == null) {
                    JOptionPane.showMessageDialog(null, "Data Sales Tidak Ditemukan");
                } else {
                    this.salesNameField.setName(this.sales.getNama());
                    this.penjualanTable.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 19) {
            ReprintDialog reprintDialog = new ReprintDialog(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27 && (a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3)) == 0) {
            this.listener.escapeClick();
        }
    }

    private void keteranganCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 112) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 113) {
            this.f2Press();
        } else if (evt.getKeyCode() == 19) {
            ReprintDialog reprintDialog = new ReprintDialog(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3);
            if (a == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 10) {
            this.penjualanTable.requestFocus();
        }
    }

    private void uploadBtn1ActionPerformed(ActionEvent evt) {
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        File file = jfc.getSelectedFile();
        try {
            this.removeAllRow();
            FileInputStream fir = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fir);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rowIt = sheet.iterator();
            rowIt.next();
            float totalPenjualan = 0.0f;
            while (rowIt.hasNext()) {
                Row row = (Row)rowIt.next();
                Iterator cellIterator = row.cellIterator();
                Object[] rowValue = new Object[9];
                Object[] errorValue = new Object[2];
                Cell cell = (Cell)cellIterator.next();
                cell.setCellType(CellType.STRING);
                rowValue[8] = cell.getStringCellValue();
                Cell cell1 = (Cell)cellIterator.next();
                cell1.setCellType(CellType.STRING);
                Barang tempBarang = this.barangService.getBarang(cell1.getStringCellValue());
                if (tempBarang == null) {
                    errorValue[0] = rowValue[8];
                    errorValue[1] = cell1.getStringCellValue();
                    this.errorTableModel.addRow(errorValue);
                    continue;
                }
                rowValue[0] = tempBarang.getId();
                rowValue[1] = tempBarang.getNama();
                Barcode barcode = this.barcodeService.getBarcode(cell1.getStringCellValue());
                if (barcode == null) {
                    rowValue[3] = tempBarang.getSatuan1();
                } else if (barcode.getSatuan().equals(tempBarang.getSatuan1())) {
                    rowValue[3] = tempBarang.getSatuan1();
                    this.kesalahanData = "";
                } else if (barcode.getSatuan().equals(tempBarang.getSatuan2())) {
                    rowValue[3] = tempBarang.getSatuan2();
                    this.kesalahanData = "";
                } else if (barcode.getSatuan().equals(tempBarang.getSatuan3())) {
                    rowValue[3] = tempBarang.getSatuan3();
                    this.kesalahanData = "";
                } else {
                    this.kesalahanData = "Ada Kesalahan Data Pada Item " + tempBarang.getNama();
                }
                if (this.kesalahanData.equals("")) {
                    Cell cell2 = (Cell)cellIterator.next();
                    cell2.setCellType(CellType.STRING);
                    rowValue[4] = cell2.getStringCellValue();
                    Cell cell3 = (Cell)cellIterator.next();
                    cell3.setCellType(CellType.STRING);
                    rowValue[2] = cell3.getStringCellValue();
                    rowValue[5] = Float.valueOf(Float.parseFloat(rowValue[2] + "") * (float)Long.parseLong(rowValue[4] + ""));
                    rowValue[6] = 0;
                    rowValue[7] = rowValue[5];
                    totalPenjualan += Float.parseFloat(rowValue[5] + "");
                    this.penjualanTableModel.addRow(rowValue);
                    continue;
                }
                JOptionPane.showMessageDialog(null, this.kesalahanData);
                this.removeAllRow();
            }
            this.totalField.setValue(Float.valueOf(totalPenjualan));
            workbook.close();
            fir.close();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(StokOpnamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(StokOpnamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addTransaksiListener(PenjualanPanelListener transaksiListener) {
        this.listener = transaksiListener;
    }

    private class cetakNotaEventHandler
    implements CetakNotaListener {
        private cetakNotaEventHandler() {
        }

        @Override
        public void printClick() {
            PenjualanByExcel2.this.totalField.setValue(0);
            if (PenjualanByExcel2.this.helper.getKasir() == 100 && PenjualanByExcel2.this.helper.getStatusProgram().equals("GUDANG")) {
                PenjualanByExcel2.this.customer = PenjualanByExcel2.this.customerService.getCustomerById("SINAR");
            } else {
                PenjualanByExcel2.this.customer = PenjualanByExcel2.this.customerService.getCustomerById("C");
            }
            PenjualanByExcel2.this.customerField.setText(PenjualanByExcel2.this.customer.getId());
            PenjualanByExcel2.this.customerLbl.setText(PenjualanByExcel2.this.customer.getNama() + "-" + PenjualanByExcel2.this.customer.getAlamat());
            PenjualanByExcel2.this.sales = null;
            PenjualanByExcel2.this.salesField.setText("");
            PenjualanByExcel2.this.salesNameField.setText("-");
            int rowCount = PenjualanByExcel2.this.penjualanTableModel.getRowCount();
            for (int a = 0; a < rowCount; ++a) {
                PenjualanByExcel2.this.penjualanTableModel.removeRow(0);
            }
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PenjualanByExcel2.this.customer = PenjualanByExcel2.this.customerService.getCustomerById(chooseValue);
            if (PenjualanByExcel2.this.customer.getTag() != null && PenjualanByExcel2.this.customer.getTag().equals("D")) {
                JOptionPane.showMessageDialog(null, "Data Customer Sudah Tidak Aktif");
            } else {
                PenjualanByExcel2.this.customerField.setText(PenjualanByExcel2.this.customer.getId());
                PenjualanByExcel2.this.customerLbl.setText(PenjualanByExcel2.this.customer.getNama() + "-" + PenjualanByExcel2.this.customer.getAlamat());
                if (PenjualanByExcel2.this.customer.getTop() > 0) {
                    PenjualanByExcel2.this.keteranganCmb.setSelectedIndex(1);
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(5, PenjualanByExcel2.this.customer.getTop());
                    PenjualanByExcel2.this.jatuhTempoField.setDate(c.getTime());
                } else {
                    PenjualanByExcel2.this.keteranganCmb.setSelectedIndex(0);
                }
                PenjualanByExcel2.this.penjualanTable.requestFocus();
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
            List<Customer> customers = PenjualanByExcel2.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp;
                if (PenjualanByExcel2.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO")) {
                    if (PenjualanByExcel2.this.helper.getStatusProgram().equalsIgnoreCase("TOKO")) {
                        temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                        dtm.addRow(temp);
                        continue;
                    }
                    if (!PenjualanByExcel2.this.helper.getStatusProgram().equalsIgnoreCase("GUDANG") || !customer.getNama().equalsIgnoreCase("SINARMART")) continue;
                    temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                    dtm.addRow(temp);
                    continue;
                }
                if (!PenjualanByExcel2.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) continue;
                temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Customer");
            sp.addSearchPanelListener(new searchPanelCustomerEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            PenjualanByExcel2.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            PenjualanByExcel2.this.canGoThrough = false;
        }
    }

    private class searchSalesDialogEventHandler
    implements SearchDialogListener {
        private searchSalesDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SALES", "NAMA SALES", "ALAMAT", "TARGET"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Sales> saleses = PenjualanByExcel2.this.salesService.getSaleses(searchWord);
            for (Sales sales : saleses) {
                Object[] temp = new String[]{sales.getId(), sales.getNama(), sales.getAlamat(), sales.getTarget() + ""};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Sales");
            sp.addSearchPanelListener(new searchPanelSalesEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private class searchPanelSalesEventHandler
        implements SearchPanelListener {
            private searchPanelSalesEventHandler() {
            }

            @Override
            public void tableClick(String chooseValue) {
                PenjualanByExcel2.this.salesField.setText(chooseValue);
                PenjualanByExcel2.this.sales = PenjualanByExcel2.this.salesService.getSales(PenjualanByExcel2.this.salesField.getText());
                PenjualanByExcel2.this.salesNameField.setText(PenjualanByExcel2.this.sales.getNama());
                PenjualanByExcel2.this.penjualanTable.requestFocus();
            }
        }
    }
}

