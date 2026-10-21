/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.view.JasperViewer
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Jenis;
import minimart.engine.model.Karyawan;
import minimart.engine.model.StockCommit;
import minimart.engine.model.StokOpnam;
import minimart.engine.model.StokOpnamDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.JenisService;
import minimart.engine.service.PaketService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.StokOpnamCsvService;
import minimart.engine.service.StokOpnamDetailService;
import minimart.engine.service.StokOpnamService;
import minimart.engine.service.SyncService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;
import minimart.ui.view.report.ReportPoinPenjualanCustomer;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StokOpnamPanelNew
extends JPanel {
    private JButton baruButton;
    private JButton cetakDataButton;
    private JButton excelBtn;
    private JButton hapusButton;
    private JTextField idField;
    private JButton importBtn;
    private JButton importExcelBtn;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane4;
    private JTabbedPane jTabbedPane2;
    private JTable jTable2;
    private JTextField jumlahField;
    private JButton keluarButton;
    private JComboBox lokasiCmb;
    private JComboBox pilihanCmb;
    private JButton prosesStokButton;
    private JTextField rakField;
    private JLabel rakLbl;
    private JButton simpanButton;
    private JTable soTable;
    private JLabel statusLbl;
    private JButton tampilkanDataButton;
    private JButton tampilkanSelisihButton;
    private JDateChooser tanggalField;
    private JComboBox tipeCmb;
    private String[] stokOpnamTMColumn = new String[]{"Kode Barang", "Nama Barang", "Lokasi", "Jenis", "Rak", "Konversi", "Stok Komputer", "Stok Fisik", "Selisih", "Satuan"};
    private DefaultTableModel stokOpnamTM = new DefaultTableModel(new String[0][1], this.stokOpnamTMColumn);
    private DefaultTableModel errorTM = new DefaultTableModel(new String[0][1], this.stokOpnamTMColumn);
    private DefaultPanelListener defaultPanelListener;
    private StokOpnamService stokOpnamService = new StokOpnamService();
    private StokOpnamDetailService stokOpnamDetailService = new StokOpnamDetailService();
    private JenisService jenisService = new JenisService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private SearchDialog sd;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private BarangService barangService = new BarangService();
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private Helper helper = Helper.getInstance();
    private boolean canGoThrough;
    private String status;
    private StokOpnam stokOpnam;
    private boolean selisih = false;
    private StokOpnamCsvService csvService = new StokOpnamCsvService();
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();
    private SyncService syncService = new SyncService();
    private PaketService paketService = new PaketService();
    List<Object[]> nullData;

    public StokOpnamPanelNew() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        ((AbstractDocument)this.rakField.getDocument()).setDocumentFilter(this.filter);
        this.setDefault();
    }

    private void setDefault() {
        this.prosesStokButton.setVisible(false);
        this.tipeCmb.setEnabled(true);
        this.lokasiCmb.setEnabled(true);
        this.tanggalField.setEnabled(true);
        this.rakField.setEnabled(true);
        this.prosesStokButton.setEnabled(false);
        this.simpanButton.setEnabled(false);
        this.tampilkanSelisihButton.setEnabled(false);
        this.hapusButton.setEnabled(false);
        this.excelBtn.setEnabled(false);
        this.importBtn.setEnabled(false);
        this.tanggalField.setDate(new Date());
        this.idField.setText("");
        this.tanggalField.setDate(new Date());
        this.tipeCmb.setSelectedIndex(0);
        this.rakField.setText("");
        this.lokasiCmb.setSelectedIndex(0);
        this.clearTable();
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void clearTable() {
        int size = this.stokOpnamTM.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.stokOpnamTM.removeRow(0);
        }
    }

    private void loadBarangBestSeller(String jenis, int limit) {
        Calendar cal = Calendar.getInstance();
        cal.add(5, -30);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endDateString = simpleDateFormat.format(new Date());
        String startDateString = simpleDateFormat.format(cal.getTime());
        this.clearTable();
        if (this.status.equals("create")) {
            List<Object[]> results = this.penjualanDetailService.getBestSeller(startDateString, endDateString, jenis, limit);
            for (Object[] result : results) {
                Barang barang = this.barangService.getBarang(result[0].toString());
                Object[] rowValue = new Object[10];
                rowValue[0] = barang.getId();
                rowValue[1] = barang.getNama();
                rowValue[2] = this.lokasiCmb.getSelectedItem();
                rowValue[3] = barang.getJenis();
                if (this.lokasiCmb.getSelectedItem().equals("Gudang")) {
                    rowValue[4] = barang.getRakIdGudang();
                    rowValue[5] = barang.getSatuan2() + ":" + barang.getKonversi21() + "/" + barang.getSatuan3() + ":" + barang.getKonversi32();
                    rowValue[6] = Float.valueOf(barang.getStokGudang());
                    rowValue[7] = Float.valueOf(barang.getStokGudang());
                } else if (this.lokasiCmb.getSelectedItem().equals("Toko")) {
                    rowValue[4] = barang.getRakIdToko();
                    rowValue[5] = barang.getSatuan2() + ":" + barang.getKonversi21() + "/" + barang.getSatuan3() + ":" + barang.getKonversi32();
                    int jumlahpaket = this.paketService.getStokBarangByDetailPaket(barang.getId());
                    rowValue[6] = Float.valueOf(barang.getStokToko() + (float)jumlahpaket);
                    rowValue[7] = Float.valueOf(barang.getStokToko() + (float)jumlahpaket);
                }
                rowValue[8] = 0;
                rowValue[9] = barang.getSatuan1();
                this.stokOpnamTM.addRow(rowValue);
            }
        } else if (this.status.equals("update")) {
            this.loadStokOpnamDetail();
        }
    }

    private void loadBarang(String jenis, String rak, String nama) {
        this.clearTable();
        if (this.status.equals("create")) {
            List<Barang> barangs = null;
            boolean allStok = true;
            if (this.pilihanCmb.getSelectedIndex() == 2) {
                allStok = false;
            }
            if (jenis.equals("") && rak.equals("") && nama.equals("")) {
                barangs = this.barangService.getBarangs("", "", true, this.lokasiCmb.getSelectedItem().toString(), allStok);
            } else if (!jenis.equals("")) {
                barangs = this.barangService.getBarangsByJenisRakNama(jenis, rak, nama, this.lokasiCmb.getSelectedItem().toString(), allStok);
            } else if (!rak.equals("")) {
                barangs = this.barangService.getBarangsByMultiRak(rak, this.lokasiCmb.getSelectedItem().toString());
            }
            for (Barang barang : barangs) {
                Object[] rowValue = new Object[10];
                rowValue[0] = barang.getId();
                rowValue[1] = barang.getNama();
                rowValue[2] = this.lokasiCmb.getSelectedItem();
                rowValue[3] = barang.getJenis();
                if (this.lokasiCmb.getSelectedItem().equals("Gudang")) {
                    rowValue[4] = barang.getRakIdGudang();
                    rowValue[5] = barang.getSatuan2() + ":" + barang.getKonversi21() + "/" + barang.getSatuan3() + ":" + barang.getKonversi32();
                    rowValue[6] = Float.valueOf(barang.getStokGudang());
                    rowValue[7] = Float.valueOf(barang.getStokGudang());
                } else if (this.lokasiCmb.getSelectedItem().equals("Toko")) {
                    rowValue[4] = barang.getRakIdToko();
                    rowValue[5] = barang.getSatuan2() + ":" + barang.getKonversi21() + "/" + barang.getSatuan3() + ":" + barang.getKonversi32();
                    int jumlahpaket = this.paketService.getStokBarangByDetailPaket(barang.getId());
                    rowValue[6] = Float.valueOf(barang.getStokToko() + (float)jumlahpaket);
                    rowValue[7] = Float.valueOf(barang.getStokToko() + (float)jumlahpaket);
                }
                rowValue[8] = 0;
                rowValue[9] = barang.getSatuan1();
                this.stokOpnamTM.addRow(rowValue);
            }
        } else if (this.status.equals("update")) {
            this.loadStokOpnamDetail();
        }
    }

    private void idClick() {
        this.setDefault();
        this.status = "update";
        this.idField.setText(this.stokOpnam.getId() + "");
        Karyawan karyawan = this.helper.getKaryawan();
        if (!karyawan.getRole().equals("MANAGER") && !karyawan.getRole().equals("DIREKTUR")) {
            this.prosesStokButton.setEnabled(false);
        } else {
            this.prosesStokButton.setEnabled(true);
        }
        this.simpanButton.setEnabled(true);
        this.hapusButton.setEnabled(true);
        this.excelBtn.setEnabled(true);
        this.importBtn.setEnabled(true);
        this.simpanButton.setText("Simpan Data Stok");
        this.tampilkanSelisihButton.setEnabled(true);
        this.tipeCmb.setSelectedItem(this.stokOpnam.getTipe());
        this.rakField.setText(this.stokOpnam.getTipeName());
        this.tanggalField.setDate(this.stokOpnam.getTanggal());
        this.lokasiCmb.setSelectedItem(this.stokOpnam.getLokasi());
        this.tipeCmb.setEnabled(false);
        this.lokasiCmb.setEnabled(false);
        this.tanggalField.setEnabled(false);
        this.rakField.setEnabled(false);
        if (this.stokOpnam == null) {
            this.tipeCmb.requestFocus();
        } else {
            this.loadStokOpnamDetail();
        }
    }

    private void loadStokOpnamDetail() {
        List<Object[]> stokOpnamDetails = this.stokOpnamDetailService.getStokOpnamDetails(this.stokOpnam.getId() + "");
        System.out.println("isi=" + stokOpnamDetails.size());
        for (Object[] opnamDetail : stokOpnamDetails) {
            Object[] rowValue = new Object[10];
            rowValue[0] = opnamDetail[0];
            rowValue[1] = opnamDetail[1];
            rowValue[2] = this.lokasiCmb.getSelectedItem();
            rowValue[3] = opnamDetail[7];
            rowValue[5] = opnamDetail[9] + ":" + opnamDetail[11] + "/" + opnamDetail[10] + ":" + opnamDetail[12];
            rowValue[6] = opnamDetail[2];
            rowValue[7] = opnamDetail[3];
            rowValue[8] = Float.valueOf(Float.parseFloat(rowValue[7].toString()) - Float.parseFloat(rowValue[6].toString()));
            rowValue[9] = opnamDetail[8];
            this.stokOpnamTM.addRow(rowValue);
        }
    }

    private void loadBarangSelisih() {
        if (this.stokOpnam == null) {
            JOptionPane.showMessageDialog(null, "Simpan Data Terlebih Dahulu");
        } else {
            this.clearTable();
            List<StokOpnamDetail> stokOpnamDetails = this.stokOpnamDetailService.getStokSelisihOpnamDetails(this.stokOpnam.getId() + "");
            for (StokOpnamDetail opnamDetail : stokOpnamDetails) {
                Object[] rowValue = new Object[10];
                rowValue[0] = opnamDetail.getBarangId();
                Barang barang = this.barangService.getBarang(opnamDetail.getBarangId());
                rowValue[1] = barang.getNama();
                rowValue[2] = this.lokasiCmb.getSelectedItem();
                rowValue[3] = barang.getJenis();
                if (this.lokasiCmb.getSelectedItem().equals("Gudang")) {
                    rowValue[4] = barang.getRakIdGudang();
                } else if (this.lokasiCmb.getSelectedItem().equals("Toko")) {
                    rowValue[4] = barang.getRakIdToko();
                }
                rowValue[5] = barang.getSatuan2() + ":" + barang.getKonversi21() + "/" + barang.getSatuan3() + ":" + barang.getKonversi32();
                rowValue[6] = Float.valueOf(opnamDetail.getJumlahKomp());
                rowValue[7] = Float.valueOf(opnamDetail.getJumlahToko());
                rowValue[8] = Float.valueOf(((Float)rowValue[7]).floatValue() - ((Float)rowValue[6]).floatValue());
                rowValue[9] = barang.getSatuan1();
                this.stokOpnamTM.addRow(rowValue);
            }
        }
    }

    private void initComponents() {
        this.baruButton = new JButton();
        this.hapusButton = new JButton();
        this.keluarButton = new JButton();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.tipeCmb = new JComboBox();
        this.idField = new JTextField();
        this.rakLbl = new JLabel();
        this.rakField = new JTextField();
        this.jLabel4 = new JLabel();
        this.tanggalField = new JDateChooser();
        this.cetakDataButton = new JButton();
        this.tampilkanDataButton = new JButton();
        this.simpanButton = new JButton();
        this.statusLbl = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel6 = new JLabel();
        this.lokasiCmb = new JComboBox();
        this.tampilkanSelisihButton = new JButton();
        this.prosesStokButton = new JButton();
        this.importBtn = new JButton();
        this.pilihanCmb = new JComboBox();
        this.jumlahField = new JTextField();
        this.jLabel5 = new JLabel();
        this.excelBtn = new JButton();
        this.importExcelBtn = new JButton();
        this.jTabbedPane2 = new JTabbedPane();
        this.jScrollPane1 = new JScrollPane();
        this.soTable = new JTable();
        this.jScrollPane4 = new JScrollPane();
        this.jTable2 = new JTable();
        this.setPreferredSize(new Dimension(800, 600));
        this.baruButton.setText("Baru");
        this.baruButton.setPreferredSize(new Dimension(73, 23));
        this.baruButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamPanelNew.this.baruButtonActionPerformed(evt);
            }
        });
        this.hapusButton.setText("Hapus");
        this.hapusButton.setPreferredSize(new Dimension(73, 23));
        this.hapusButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamPanelNew.this.hapusButtonActionPerformed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(67, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamPanelNew.this.keluarButtonActionPerformed(evt);
            }
        });
        this.jLabel1.setText("ID Opname");
        this.jLabel2.setText("Tipe");
        this.tipeCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"Full Opname", "Jenis"}));
        this.tipeCmb.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                StokOpnamPanelNew.this.tipeCmbItemStateChanged(evt);
            }
        });
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamPanelNew.this.idFieldKeyPressed(evt);
            }
        });
        this.rakLbl.setText("Rak");
        this.rakField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamPanelNew.this.rakFieldKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Tanggal");
        this.cetakDataButton.setText("Cetak Data");
        this.cetakDataButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamPanelNew.this.cetakDataButtonActionPerformed(evt);
            }
        });
        this.tampilkanDataButton.setText("Tampilkan Data");
        this.tampilkanDataButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamPanelNew.this.tampilkanDataButtonActionPerformed(evt);
            }
        });
        this.tampilkanDataButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamPanelNew.this.tampilkanDataButtonKeyPressed(evt);
            }
        });
        this.simpanButton.setText("Ambil Posisi Stok");
        this.simpanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamPanelNew.this.simpanButtonActionPerformed(evt);
            }
        });
        this.statusLbl.setBackground(new Color(255, 255, 0));
        this.statusLbl.setFont(new Font("Tahoma", 1, 14));
        this.statusLbl.setText("BELUM DIPROSES");
        this.jLabel3.setText("xx,xx,xx");
        this.jLabel6.setText("Lokasi");
        this.lokasiCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"Gudang", "Toko"}));
        this.lokasiCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamPanelNew.this.lokasiCmbKeyPressed(evt);
            }
        });
        this.tampilkanSelisihButton.setText("Tampilkan Selisih");
        this.tampilkanSelisihButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamPanelNew.this.tampilkanSelisihButtonActionPerformed(evt);
            }
        });
        this.prosesStokButton.setText("Proses Stok");
        this.prosesStokButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamPanelNew.this.prosesStokButtonActionPerformed(evt);
            }
        });
        this.importBtn.setText("Import CSV");
        this.importBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamPanelNew.this.importBtnActionPerformed(evt);
            }
        });
        this.pilihanCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"Opnam Penuh", "Opnam Best Seller", "Opnam Sisa Stok"}));
        this.pilihanCmb.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                StokOpnamPanelNew.this.pilihanCmbItemStateChanged(evt);
            }
        });
        this.jumlahField.setEnabled(false);
        this.jLabel5.setText("Keteranga");
        this.excelBtn.setText("Cetak Excel");
        this.excelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamPanelNew.this.excelBtnActionPerformed(evt);
            }
        });
        this.importExcelBtn.setText("Import Excel");
        this.importExcelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamPanelNew.this.importExcelBtnActionPerformed(evt);
            }
        });
        this.soTable.setModel(this.stokOpnamTM);
        this.jScrollPane1.setViewportView(this.soTable);
        this.jTabbedPane2.addTab("Data", this.jScrollPane1);
        this.jTable2.setModel(this.errorTM);
        this.jScrollPane4.setViewportView(this.jTable2);
        this.jTabbedPane2.addTab("Error", this.jScrollPane4);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jTabbedPane2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.idField, -2, 135, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel6).addGap(45, 45, 45)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5, -1, -1, Short.MAX_VALUE).addGap(20, 20, 20))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.pilihanCmb, 0, -1, Short.MAX_VALUE).addComponent(this.lokasiCmb, 0, -1, Short.MAX_VALUE).addComponent(this.jumlahField)))).addComponent(this.tipeCmb, -2, 135, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.importBtn, -1, -1, Short.MAX_VALUE).addComponent(this.importExcelBtn, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tampilkanDataButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tampilkanSelisihButton)).addGroup(layout.createSequentialGroup().addGap(226, 226, 226).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4, -2, 45, -2).addGap(1, 1, 1)).addComponent(this.rakLbl, GroupLayout.Alignment.TRAILING, -2, 45, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(14, 14, 14).addComponent(this.rakField, -2, 129, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel3).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addComponent((Component)this.tanggalField, -2, 148, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE).addComponent(this.statusLbl)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.excelBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetakDataButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton, -2, -1, -2)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addGroup(layout.createSequentialGroup().addComponent(this.baruButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.simpanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.hapusButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.prosesStokButton))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.baruButton, -2, -1, -2).addComponent(this.hapusButton, -2, -1, -2).addComponent(this.simpanButton).addComponent(this.prosesStokButton)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.idField, -2, -1, -2).addComponent(this.jLabel4)).addComponent((Component)this.tanggalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.rakLbl)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.rakField, -2, -1, -2).addComponent(this.jLabel3)).addComponent(this.tipeCmb, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.lokasiCmb, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.pilihanCmb, -2, -1, -2).addComponent(this.tampilkanDataButton).addComponent(this.tampilkanSelisihButton).addComponent(this.importBtn).addComponent(this.jLabel5))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.cetakDataButton).addComponent(this.excelBtn)).addGap(3, 3, 3).addComponent(this.statusLbl))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jumlahField, -2, -1, -2).addComponent(this.importExcelBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jTabbedPane2, -2, 345, -2).addContainerGap(69, Short.MAX_VALUE)));
        this.jTabbedPane2.getAccessibleContext().setAccessibleName("detail");
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.stokOpnam = this.stokOpnamService.getStokOpnam(this.idField.getText());
            if (this.stokOpnam != null) {
                if (this.stokOpnam.getStatusOpnam().equals("BELUM DIPROSES")) {
                    System.out.println("TIPE=" + this.stokOpnam.getTipe());
                    if (this.stokOpnam.getTipe().equals("SOP") || this.stokOpnam.getTipe().equals("SOM")) {
                        PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                        passwordInputDialog2.setJudulText("SOM dan Parsal tidak dapat diubah");
                        passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
                        int w = passwordInputDialog2.getWidth();
                        int h = passwordInputDialog2.getHeight();
                        passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                        passwordInputDialog2.setVisible(true);
                    } else {
                        this.idClick();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Data Stok Opnam Telah Diproses, Lihat Pada Report Stok Opnam");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Data Stok Opnam Tidak Ditemukan");
            }
        }
    }

    private void rakFieldKeyPressed(KeyEvent evt) {
        if (this.tipeCmb.getSelectedIndex() == 1) {
            if (evt.getKeyCode() == 10) {
                if (this.rakField.getText().equals("")) {
                    this.sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
                    this.sd.addSearchDialogListener(new searchJenisDialogEventHandler());
                    int w = this.sd.getWidth();
                    int h = this.sd.getHeight();
                    this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                    this.sd.setVisible(true);
                } else {
                    Jenis jenis = this.jenisService.getJenis(this.rakField.getText());
                    if (jenis == null) {
                        JOptionPane.showMessageDialog(null, "Jenis Barang Tidak Ditemukan");
                    } else {
                        this.lokasiCmb.requestFocus();
                    }
                }
            } else if (evt.getKeyCode() == 113) {
                this.sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
                this.sd.addSearchDialogListener(new searchJenisDialogEventHandler());
                int w = this.sd.getWidth();
                int h = this.sd.getHeight();
                this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                this.sd.setVisible(true);
            } else if (evt.getKeyCode() == 38) {
                this.tipeCmb.requestFocus();
            }
        } else if (evt.getKeyCode() == 10) {
            this.lokasiCmb.requestFocus();
        }
    }

    private void baruButtonActionPerformed(ActionEvent evt) {
        this.setDefault();
        this.idField.setText(this.stokOpnamService.getLastId() + 1 + "");
        this.idField.requestFocus();
        this.simpanButton.setEnabled(true);
        this.tampilkanSelisihButton.setEnabled(false);
        this.hapusButton.setEnabled(false);
        this.simpanButton.setEnabled(false);
        this.simpanButton.setText("Ambil Posisi Stok");
        this.status = "create";
    }

    private void tampilkanDataButtonActionPerformed(ActionEvent evt) {
        this.selisih = false;
        this.simpanButton.setEnabled(true);
        this.excelBtn.setEnabled(true);
        this.tampilkanSelisihButton.setEnabled(true);
        int limit = 0;
        if (this.helper.isInteger(this.jumlahField.getText())) {
            limit = Integer.parseInt(this.jumlahField.getText());
        }
        if (this.pilihanCmb.getSelectedIndex() == 1) {
            this.loadBarangBestSeller(this.rakField.getText(), limit);
        } else if (this.pilihanCmb.getSelectedIndex() == 0 || this.pilihanCmb.getSelectedIndex() == 2) {
            if (this.tipeCmb.getSelectedIndex() == 0) {
                this.loadBarang("", "", "");
            } else if (this.tipeCmb.getSelectedIndex() == 1) {
                this.loadBarang(this.rakField.getText(), "", "");
            } else if (this.tipeCmb.getSelectedIndex() == 2) {
                this.loadBarang("", this.rakField.getText(), "");
            }
        }
    }

    private void lokasiCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.tampilkanDataButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.tipeCmb.requestFocus();
        }
    }

    private void tampilkanDataButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.simpanButton.setEnabled(true);
            if (this.tipeCmb.getSelectedIndex() == 0) {
                this.loadBarang("", "", "");
            } else if (this.tipeCmb.getSelectedIndex() == 1) {
                this.loadBarang(this.rakField.getText(), "", "");
            } else if (this.tipeCmb.getSelectedIndex() == 2) {
                this.loadBarang("", this.rakField.getText(), "");
            }
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.lokasiCmb.requestFocus();
        }
    }

    private void simpanButtonActionPerformed(ActionEvent evt) {
        Karyawan karyawan = this.helper.getKaryawan();
        if (!karyawan.getRole().equals("MANAGER") && !karyawan.getRole().equals("DIREKTUR")) {
            this.prosesStokButton.setEnabled(false);
        } else {
            this.prosesStokButton.setEnabled(true);
        }
        this.excelBtn.setEnabled(true);
        this.simpanData();
        this.tipeCmb.setEnabled(false);
        this.lokasiCmb.setEnabled(false);
        this.tanggalField.setEnabled(false);
        this.rakField.setEnabled(false);
    }

    private void hapusButtonActionPerformed(ActionEvent evt) {
        PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Data SO Tidak Boleh Dihapus");
        passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandlerHapus());
        int w = passwordInputDialog2.getWidth();
        int h = passwordInputDialog2.getHeight();
        passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        passwordInputDialog2.setVisible(true);
    }

    private void tampilkanSelisihButtonActionPerformed(ActionEvent evt) {
        this.canGoThrough = false;
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Pastikan Data Telah Disimpan Terlebih Dahulu! Lanjutkan?");
        confirmationDialog.addListener(new confirmDialogEventHandler());
        int w = confirmationDialog.getWidth();
        int h = confirmationDialog.getHeight();
        confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        confirmationDialog.setVisible(true);
        if (this.canGoThrough) {
            this.selisih = true;
            this.loadBarangSelisih();
        }
    }

    private void cetakDataButtonActionPerformed(ActionEvent evt) {
        if (!this.selisih) {
            try {
                File rear = new File("ireport/StokOpnamReport.jasper");
                String path = rear.getAbsolutePath();
                HashMap<String, Integer> parameter = new HashMap<String, Integer>();
                parameter.put("STOK_OPNAM_ID", this.stokOpnam.getId());
                JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
                JasperViewer.viewReport((JasperPrint)print, (boolean)false);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
            }
        } else {
            try {
                File rear = new File("ireport/StokOpnamSelisihFullReport.jasper");
                String path = rear.getAbsolutePath();
                HashMap<String, Integer> parameter = new HashMap<String, Integer>();
                parameter.put("STOK_OPNAM_ID", this.stokOpnam.getId());
                JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
                JasperViewer.viewReport((JasperPrint)print, (boolean)false);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
            }
        }
    }

    private void prosesStokButtonActionPerformed(ActionEvent evt) {
        this.canGoThrough = false;
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Data Stok Opnam Akan Diproses dan Tidak Dapat Diubah Lagi! Lanjutkan?");
        confirmationDialog.addListener(new confirmDialogEventHandler());
        int w = confirmationDialog.getWidth();
        int h = confirmationDialog.getHeight();
        confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        confirmationDialog.setVisible(true);
        if (this.canGoThrough) {
            List<StokOpnamDetail> stokOpnamDetails = this.stokOpnamDetailService.getStokSelisihOpnamDetails(this.stokOpnam.getId() + "");
            for (StokOpnamDetail stokOpnamDetail : stokOpnamDetails) {
                StockCommit stockCommit2;
                float jumlahSelisih = stokOpnamDetail.getJumlahToko() - stokOpnamDetail.getJumlahKomp();
                if (this.stokOpnam.getLokasi().equalsIgnoreCase("Toko")) {
                    stockCommit2 = new StockCommit();
                    stockCommit2.setIdBarang(stokOpnamDetail.getBarangId());
                    stockCommit2.setIdTransaksi(this.stokOpnam.getId() + "");
                    stockCommit2.setQueryType("tambah");
                    stockCommit2.setStockType("toko");
                    stockCommit2.setTransactionType("Stok Opnam");
                    stockCommit2.setJumlahBarang(jumlahSelisih);
                    this.sinkronisasiBarangService.insertCommit(stockCommit2);
                    this.barangService.updateStokBarangToko(jumlahSelisih, stokOpnamDetail.getBarangId());
                    continue;
                }
                if (!this.stokOpnam.getLokasi().equalsIgnoreCase("Gudang")) continue;
                stockCommit2 = new StockCommit();
                stockCommit2.setIdBarang(stokOpnamDetail.getBarangId());
                stockCommit2.setIdTransaksi(this.stokOpnam.getId() + "");
                stockCommit2.setQueryType("tambah");
                stockCommit2.setStockType("gudang");
                stockCommit2.setTransactionType("Stok Opnam");
                stockCommit2.setJumlahBarang(jumlahSelisih);
                this.sinkronisasiBarangService.insertCommit(stockCommit2);
                this.barangService.updateStokBarangGudang(jumlahSelisih, stokOpnamDetail.getBarangId());
            }
            this.stokOpnam.setStatusOpnam("SUDAH DIPROSES");
            this.stokOpnam.setUpdated(new Date());
            this.stokOpnamService.updateStokOpnam(this.stokOpnam);
            this.syncService.doSyncStokOpnamAndStokOpnamUnsynced();
            JOptionPane.showMessageDialog(null, "Data Stok Opnam Telah Diproses");
            this.setDefault();
        }
    }

    private void importBtnActionPerformed(ActionEvent evt) {
        for (int a = 0; a < this.stokOpnamTM.getRowCount(); ++a) {
            String idBarang = this.stokOpnamTM.getValueAt(a, 0).toString();
            int jumlahStok = this.csvService.getJumlahStokCSVs(idBarang);
            this.stokOpnamTM.setValueAt(jumlahStok, a, 5);
        }
    }

    private void pilihanCmbItemStateChanged(ItemEvent evt) {
        if (this.pilihanCmb.getSelectedIndex() == 0) {
            this.jumlahField.setEnabled(false);
        } else if (this.pilihanCmb.getSelectedIndex() == 1) {
            this.jumlahField.setEnabled(true);
        }
    }

    private void tipeCmbItemStateChanged(ItemEvent evt) {
        if (this.tipeCmb.getSelectedIndex() == 0) {
            this.rakField.setEnabled(false);
        } else if (this.tipeCmb.getSelectedIndex() == 1) {
            this.rakField.setEnabled(true);
        }
    }

    private void excelBtnActionPerformed(ActionEvent evt) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            CreationHelper createhelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");
            Row row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("Kode Barang");
            cell = row.createCell(1);
            cell.setCellValue("Nama Barang");
            cell = row.createCell(2);
            cell.setCellValue("Lokasi");
            cell = row.createCell(3);
            cell.setCellValue("Jenis");
            cell = row.createCell(4);
            cell.setCellValue("Rak");
            cell = row.createCell(5);
            cell.setCellValue("Konversi");
            cell = row.createCell(6);
            cell.setCellValue("Stok Komputer");
            cell = row.createCell(7);
            cell.setCellValue("Stok Fisik");
            cell = row.createCell(8);
            cell.setCellValue("Selisih");
            cell = row.createCell(9);
            cell.setCellValue("Satuan");
            System.out.println("row=" + this.stokOpnamTM.getRowCount());
            System.out.println("coumn=" + this.stokOpnamTM.getColumnCount());
            for (int i = 0; i < this.stokOpnamTM.getRowCount(); ++i) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < this.stokOpnamTM.getColumnCount(); ++j) {
                    cell = row.createCell(j);
                    cell.setCellValue(this.stokOpnamTM.getValueAt(i, j) + "");
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDateString = simpleDateFormat.format(new Date());
            String url = "docOpnam/" + this.stokOpnam.getId() + ".xls";
            FileOutputStream out = new FileOutputStream(url);
            wb.write((OutputStream)out);
            out.close();
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan Pada " + url);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(ReportPoinPenjualanCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(ReportPoinPenjualanCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void importExcelBtnActionPerformed(ActionEvent evt) {
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        File file = jfc.getSelectedFile();
        String nama = file.getName().substring(0, file.getName().length() - 4);
        this.stokOpnam = this.stokOpnamService.getStokOpnam(nama);
        if (this.stokOpnam != null) {
            if (this.stokOpnam.getStatusOpnam().equals("BELUM DIPROSES")) {
                this.setDefault();
                this.status = "update";
                this.idField.setText(this.stokOpnam.getId() + "");
                Karyawan karyawan = this.helper.getKaryawan();
                if (!karyawan.getRole().equals("MANAGER") && !karyawan.getRole().equals("DIREKTUR")) {
                    this.prosesStokButton.setEnabled(false);
                } else {
                    this.prosesStokButton.setEnabled(true);
                }
                this.simpanButton.setEnabled(true);
                this.hapusButton.setEnabled(true);
                this.excelBtn.setEnabled(true);
                this.importBtn.setEnabled(true);
                this.simpanButton.setText("Simpan Data Stok");
                this.tampilkanSelisihButton.setEnabled(true);
                this.tipeCmb.setSelectedItem(this.stokOpnam.getTipe());
                this.rakField.setText(this.stokOpnam.getTipeName());
                this.tanggalField.setDate(this.stokOpnam.getTanggal());
                this.lokasiCmb.setSelectedItem(this.stokOpnam.getLokasi());
                this.tipeCmb.setEnabled(false);
                this.lokasiCmb.setEnabled(false);
                this.tanggalField.setEnabled(false);
                this.rakField.setEnabled(false);
                if (this.stokOpnam == null) {
                    this.tipeCmb.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Data Stok Opnam Telah Diproses, Lihat Pada Report Stok Opnam");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data Stok Opnam Tidak Ditemukan");
        }
        try {
            this.clearTable();
            FileInputStream fir = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fir);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rowIt = sheet.iterator();
            rowIt.next();
            while (rowIt.hasNext()) {
                Row row = (Row)rowIt.next();
                Iterator cellIterator = row.cellIterator();
                int a = 0;
                boolean error = false;
                Object[] rowValue = new Object[10];
                while (cellIterator.hasNext()) {
                    Cell cell = (Cell)cellIterator.next();
                    cell.setCellType(CellType.STRING);
                    rowValue[a] = cell.getStringCellValue();
                    System.out.println("DATA" + a + "=" + rowValue[a]);
                    if (a == 7 && Float.parseFloat(rowValue[a].toString()) < 0.0f) {
                        error = true;
                    }
                    ++a;
                }
                if (error) {
                    this.errorTM.addRow(rowValue);
                    continue;
                }
                this.stokOpnamTM.addRow(rowValue);
            }
            workbook.close();
            fir.close();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(StokOpnamPanelNew.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(StokOpnamPanelNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void simpanData() {
        if (this.status.equals("create")) {
            this.stokOpnam = new StokOpnam();
            this.stokOpnam.setId(this.stokOpnamService.getLastId() + 1);
            this.stokOpnam.setKaryawanId(this.helper.getKaryawan().getId());
            this.stokOpnam.setLokasi(this.lokasiCmb.getSelectedItem().toString());
            this.stokOpnam.setTipe(this.tipeCmb.getSelectedItem() + "");
            this.stokOpnam.setTipeName(this.rakField.getText());
            this.stokOpnam.setStatusOpnam(this.statusLbl.getText());
            this.stokOpnam.setTanggal(new Date());
            this.stokOpnam.setUpdated(new Date());
            int statusOpnamSave = this.stokOpnamService.createStokOpnam(this.stokOpnam);
            if (statusOpnamSave == 1) {
                ArrayList<StokOpnamDetail> data = new ArrayList<StokOpnamDetail>();
                for (int a = 0; a < this.stokOpnamTM.getRowCount(); ++a) {
                    StokOpnamDetail stokOpnamDetail = new StokOpnamDetail();
                    stokOpnamDetail.setStokOpnamId(this.stokOpnam.getId());
                    stokOpnamDetail.setBarangId(this.stokOpnamTM.getValueAt(a, 0).toString());
                    stokOpnamDetail.setJumlahKomp(Float.parseFloat(this.stokOpnamTM.getValueAt(a, 6).toString()));
                    stokOpnamDetail.setJumlahToko(Float.parseFloat(this.stokOpnamTM.getValueAt(a, 7).toString()));
                    Barang barang = this.barangService.getBarang(stokOpnamDetail.getBarangId());
                    stokOpnamDetail.setTotal((long)Float.parseFloat(this.stokOpnamTM.getValueAt(a, 8).toString()) * barang.getHpp());
                    data.add(stokOpnamDetail);
                }
                this.stokOpnamDetailService.createStokOpnamDetailBatch(data);
                JOptionPane.showMessageDialog(null, "Data Stok Opname Berhasil Tersimpan");
                this.hapusButton.setEnabled(true);
                this.simpanButton.setText("Simpan Data Stok");
                this.status = "update";
            }
        } else if (this.status.equals("update")) {
            this.tampilkanSelisihButton.setEnabled(true);
            this.stokOpnamDetailService.deleteStokOpnamDetailByStokOpnamId(this.stokOpnam.getId() + "");
            ArrayList<StokOpnamDetail> data = new ArrayList<StokOpnamDetail>();
            for (int a = 0; a < this.stokOpnamTM.getRowCount(); ++a) {
                StokOpnamDetail stokOpnamDetail = new StokOpnamDetail();
                stokOpnamDetail.setStokOpnamId(this.stokOpnam.getId());
                stokOpnamDetail.setBarangId(this.stokOpnamTM.getValueAt(a, 0).toString());
                stokOpnamDetail.setJumlahKomp(Float.parseFloat(this.stokOpnamTM.getValueAt(a, 6).toString()));
                stokOpnamDetail.setJumlahToko(Float.parseFloat(this.stokOpnamTM.getValueAt(a, 7).toString()));
                Barang barang = this.barangService.getBarang(stokOpnamDetail.getBarangId());
                if (barang == null) {
                    JOptionPane.showMessageDialog(null, "ID Barang " + stokOpnamDetail.getBarangId() + " tidak ditemukan, harap ulangi IMPORT data");
                } else {
                    stokOpnamDetail.setTotal((long)(stokOpnamDetail.getJumlahToko() - stokOpnamDetail.getJumlahKomp()) * barang.getHpp());
                }
                data.add(stokOpnamDetail);
            }
            this.stokOpnamDetailService.createStokOpnamDetailBatch(data);
            JOptionPane.showMessageDialog(null, "Data Stok Opname Berhasil Tersimpan");
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            StokOpnamPanelNew.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            StokOpnamPanelNew.this.canGoThrough = false;
        }
    }

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            StokOpnamPanelNew.this.rakField.setText(chooseValue);
        }
    }

    private class searchJenisDialogEventHandler
    implements SearchDialogListener {
        private searchJenisDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Jenis> jenisses = StokOpnamPanelNew.this.jenisService.getJenisses(searchWord);
            for (Jenis jenis : jenisses) {
                Object[] temp = new String[]{jenis.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Jenis Barang");
            sp.addSearchPanelListener(new searchPanelJenisEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }

    private class passwordInputDialogEventHandlerHapus
    implements PasswordInputListener {
        private passwordInputDialogEventHandlerHapus() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR")) {
                int deleteStatus = StokOpnamPanelNew.this.stokOpnamService.deleteStokOpnam(StokOpnamPanelNew.this.stokOpnam.getId());
                if (deleteStatus == 1) {
                    JOptionPane.showMessageDialog(null, "Data Stok Opname Berhasil Dihapus");
                    StokOpnamPanelNew.this.setDefault();
                } else {
                    JOptionPane.showMessageDialog(null, "Penghapusan Data Gagal");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Anda tidak memiliki otoritas ini");
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR")) {
                StokOpnamPanelNew.this.idClick();
            } else {
                JOptionPane.showMessageDialog(null, "Anda tidak memiliki otoritas ini");
            }
        }

        @Override
        public void passwordFailed(String password) {
            JOptionPane.showMessageDialog(null, "Password Anda Salah");
        }
    }
}

