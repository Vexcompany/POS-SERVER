/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package minimart.ui.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import minimart.engine.component.JTextFieldLimit;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Barcode;
import minimart.engine.model.HistoryHarga;
import minimart.engine.model.Jenis;
import minimart.engine.model.JsonResponses;
import minimart.engine.model.Karyawan;
import minimart.engine.model.ParentBarang;
import minimart.engine.model.ParentBarangOnline;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.HistoryHargaService;
import minimart.engine.service.JenisService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.ParentBarangService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.RBarangSupplierService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;
import minimart.ui.view.TabelBarangMaster;

public class ParentMasterUpdate
extends JDialog {
    private JButton deleteButton;
    private JTextField discField;
    private JLabel equal1Lbl;
    private JLabel equal2Lbl;
    private JLabel equal3Lbl;
    private JLabel equal4Lbl;
    private JLabel equal5Lbl;
    private JButton exitButton;
    private JFormattedTextField hargaJualField1;
    private JFormattedTextField hargaJualField2;
    private JFormattedTextField hargaJualField3;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JTabbedPane jTabbedPane1;
    private JTable jTable1;
    private JTextField jenisField;
    private JLabel jenisLbl;
    private JTextField kodeBarangField;
    private JLabel kodeBarangLbl;
    private JTextField konversi1Field;
    private JLabel konversi1Lbl;
    private JTextField konversi2Field;
    private JLabel konversi2Lbl;
    private JTextField konversi3Field;
    private JLabel konversi3Lbl;
    private JTextField konversi7Field;
    private JTextField konversi8Field;
    private JLabel konversiLbl;
    private JTextField minField;
    private JFormattedTextField minResultField;
    private JLabel minSatField;
    private JTextField namaBarangField;
    private JLabel namaBarangLbl;
    private JLabel satuan1;
    private JLabel satuan2;
    private JLabel satuan3;
    private JLabel satuan4;
    private JButton saveButton;
    private JLabel statusBarangLbl;
    private BarangService barangService = new BarangService();
    private JenisService jenisService = new JenisService();
    private Barang barang = new Barang();
    private ParentBarang parentBarang = new ParentBarang();
    private long historyHarga1;
    private long historyHarga2;
    private long historyHarga3;
    private long hppBarang;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private SearchDialog sd;
    private String action = "create";
    private Helper helper = Helper.getInstance();
    private RBarangSupplierService rbss = new RBarangSupplierService();
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    private SupplierService supplierService = new SupplierService();
    private PembelianService pembelianService = new PembelianService();
    private boolean canGoThrough;
    private BarcodeService barcodeService = new BarcodeService();
    private List<Barcode> deleteBarcode = new LinkedList<Barcode>();
    private List<String> deleteSupplier = new LinkedList<String>();
    private String tipe;
    private HistoryHargaService historyHargaService = new HistoryHargaService();
    private RBarangSupplierService rBarangSupplierService = new RBarangSupplierService();
    private Karyawan karyawan;
    private KonstantaService konstantaService = new KonstantaService();
    private CommonEngine comm = new CommonEngine("https://admin.sinarmart.id");
    private ObjectMapper mapper = new ObjectMapper();
    private ParentBarangService parentBarangService = new ParentBarangService();
    String[] colBarangName = new String[]{"KODE BARANG", "NAMA BARANG", "SATUAN", "JENIS"};
    DefaultTableModel dtm = new DefaultTableModel(new String[0][1], this.colBarangName);
    private List<Barang> barangs;

    public ParentMasterUpdate(Frame owner, boolean modal, String tipe) {
        super(owner, modal);
        this.initComponents();
        this.initComponents2();
        this.setTitle("Entry Tabel Barang");
        this.tipe = tipe;
        this.kodeBarangField.requestFocus();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.karyawan = this.helper.getKaryawan();
        if (!this.karyawan.getRole().equalsIgnoreCase("DIREKTUR")) {
            this.deleteButton.setEnabled(false);
        }
        if (this.karyawan.getRole().equalsIgnoreCase("KASIR")) {
            this.saveButton.setEnabled(false);
        }
        this.setDefault();
        if (tipe.equalsIgnoreCase("CABANG")) {
            this.saveButton.setEnabled(false);
            this.deleteButton.setEnabled(false);
        } else if (tipe.equalsIgnoreCase("PUSAT")) {
            this.saveButton.setEnabled(true);
            this.deleteButton.setEnabled(true);
        }
    }

    private void initComponents() {
        this.jSeparator1 = new JSeparator();
        this.jLabel6 = new JLabel();
        this.jTabbedPane1 = new JTabbedPane();
        this.jPanel1 = new JPanel();
        this.kodeBarangLbl = new JLabel();
        this.kodeBarangField = new JTextField();
        this.namaBarangLbl = new JLabel();
        this.namaBarangField = new JTextField();
        this.jenisLbl = new JLabel();
        this.jenisField = new JTextField();
        this.jLabel15 = new JLabel();
        this.konversi1Lbl = new JLabel();
        this.equal1Lbl = new JLabel();
        this.hargaJualField1 = new JFormattedTextField();
        this.konversi1Field = new JTextField();
        this.satuan1 = new JLabel();
        this.konversi2Lbl = new JLabel();
        this.konversi3Lbl = new JLabel();
        this.konversi2Field = new JTextField();
        this.konversi3Field = new JTextField();
        this.equal2Lbl = new JLabel();
        this.equal3Lbl = new JLabel();
        this.hargaJualField2 = new JFormattedTextField();
        this.hargaJualField3 = new JFormattedTextField();
        this.statusBarangLbl = new JLabel();
        this.jLabel9 = new JLabel();
        this.konversi7Field = new JTextField();
        this.konversi8Field = new JTextField();
        this.satuan2 = new JLabel();
        this.equal4Lbl = new JLabel();
        this.equal5Lbl = new JLabel();
        this.satuan3 = new JLabel();
        this.satuan4 = new JLabel();
        this.konversiLbl = new JLabel();
        this.jLabel8 = new JLabel();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.deleteButton = new JButton();
        this.jLabel5 = new JLabel();
        this.minField = new JTextField();
        this.discField = new JTextField();
        this.minResultField = new JFormattedTextField();
        this.jLabel14 = new JLabel();
        this.minSatField = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.jLabel6.setText("jLabel6");
        this.setDefaultCloseOperation(2);
        this.kodeBarangLbl.setText("Kode Parent*");
        this.kodeBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.kodeBarangFieldKeyPressed(evt);
            }
        });
        this.namaBarangLbl.setText("Nama Parent*");
        this.namaBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.namaBarangFieldKeyPressed(evt);
            }
        });
        this.jenisLbl.setText("Jenis*");
        this.jenisField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.jenisFieldKeyPressed(evt);
            }
        });
        this.jLabel15.setText("Harga Jual");
        this.konversi1Lbl.setText("Satuan 1");
        this.equal1Lbl.setText("=");
        this.hargaJualField1.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.hargaJualField1.setText("0");
        this.hargaJualField1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.hargaJualField1KeyPressed(evt);
            }
        });
        this.konversi1Field.setDocument(new JTextFieldLimit(3));
        this.konversi1Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.konversi1FieldKeyPressed(evt);
            }
        });
        this.satuan1.setBackground(new Color(255, 255, 255));
        this.konversi2Lbl.setText("Satuan 2");
        this.konversi3Lbl.setText("Satuan 3");
        this.konversi2Field.setDocument(new JTextFieldLimit(3));
        this.konversi2Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.konversi2FieldKeyPressed(evt);
            }
        });
        this.konversi3Field.setDocument(new JTextFieldLimit(3));
        this.konversi3Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.konversi3FieldKeyPressed(evt);
            }
        });
        this.equal2Lbl.setText("=");
        this.equal3Lbl.setText("=");
        this.hargaJualField2.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.hargaJualField2.setText("0");
        this.hargaJualField2.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.hargaJualField2KeyPressed(evt);
            }
        });
        this.hargaJualField3.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.hargaJualField3.setText("0");
        this.hargaJualField3.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.hargaJualField3KeyPressed(evt);
            }
        });
        this.statusBarangLbl.setFont(new Font("Tahoma", 1, 14));
        this.statusBarangLbl.setForeground(new Color(0, 0, 204));
        this.statusBarangLbl.setText("-");
        this.jLabel9.setText("1");
        this.konversi7Field.setText("0");
        this.konversi7Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.konversi7FieldKeyPressed(evt);
            }
        });
        this.konversi8Field.setText("0");
        this.konversi8Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.konversi8FieldKeyPressed(evt);
            }
        });
        this.satuan2.setBackground(new Color(255, 255, 255));
        this.equal4Lbl.setText("=");
        this.equal5Lbl.setText("=");
        this.konversiLbl.setText("Konversi");
        this.jLabel8.setText("1");
        this.saveButton.setText("Save");
        this.saveButton.setMaximumSize(new Dimension(73, 23));
        this.saveButton.setMinimumSize(new Dimension(73, 23));
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ParentMasterUpdate.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setMaximumSize(new Dimension(73, 23));
        this.exitButton.setMinimumSize(new Dimension(73, 23));
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ParentMasterUpdate.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.exitButtonKeyPressed(evt);
            }
        });
        this.deleteButton.setText("Delete");
        this.deleteButton.setPreferredSize(new Dimension(73, 22));
        this.deleteButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ParentMasterUpdate.this.deleteButtonActionPerformed(evt);
            }
        });
        this.deleteButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.deleteButtonKeyPressed(evt);
            }
        });
        this.jLabel5.setText("Min Pembelian");
        this.minField.setText("0");
        this.minField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.minFieldKeyPressed(evt);
            }
        });
        this.discField.setText("0");
        this.discField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ParentMasterUpdate.this.discFieldKeyPressed(evt);
            }
        });
        this.minResultField.setText("0");
        this.jLabel14.setText("%");
        this.minSatField.setText("-");
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.kodeBarangLbl).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.kodeBarangField, -2, 207, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.statusBarangLbl, -1, -1, Short.MAX_VALUE)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.konversi2Lbl).addComponent(this.konversi3Lbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.konversi2Field).addComponent(this.konversi3Field, -2, 72, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.equal2Lbl).addComponent(this.equal3Lbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.hargaJualField2, GroupLayout.Alignment.LEADING).addComponent(this.hargaJualField3, -2, 160, -2))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.deleteButton, -2, 67, -2).addGap(2, 2, 2).addComponent(this.exitButton, -2, 67, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGap(48, 48, 48).addComponent(this.konversiLbl).addGap(36, 36, 36).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel9).addGroup(jPanel1Layout.createSequentialGroup().addGap(16, 16, 16).addComponent(this.satuan2, -2, 40, -2)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel8).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.satuan1, -2, 40, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.equal4Lbl, -1, -1, Short.MAX_VALUE).addComponent(this.equal5Lbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.konversi8Field, -2, 64, -2).addComponent(this.konversi7Field, -2, 64, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.satuan4, -2, 40, -2).addComponent(this.satuan3, -2, 40, -2))).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaBarangLbl).addComponent(this.jenisLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jenisField, -2, 207, -2).addComponent(this.namaBarangField, -2, 207, -2))).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel15).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.konversi1Lbl).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.konversi1Field, -2, 72, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.equal1Lbl).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.hargaJualField1, -2, 160, -2))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.minField).addComponent(this.discField, -1, 44, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel14).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.minResultField, -1, 93, Short.MAX_VALUE).addComponent(this.minSatField, -1, -1, Short.MAX_VALUE)))).addGap(0, 301, Short.MAX_VALUE))).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kodeBarangLbl).addComponent(this.kodeBarangField, -2, -1, -2).addComponent(this.statusBarangLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.namaBarangLbl).addComponent(this.namaBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jenisLbl).addComponent(this.jenisField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel15).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.equal1Lbl).addComponent(this.konversi1Field, -2, -1, -2)).addGap(1, 1, 1)).addComponent(this.hargaJualField1, GroupLayout.Alignment.TRAILING, -2, -1, -2))).addComponent(this.konversi1Lbl, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.konversi2Field, -2, -1, -2).addComponent(this.konversi2Lbl)).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hargaJualField2, -2, -1, -2).addComponent(this.equal2Lbl))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.konversi3Field, -2, -1, -2).addComponent(this.konversi3Lbl)).addGroup(jPanel1Layout.createSequentialGroup().addGap(4, 4, 4).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hargaJualField3, -2, -1, -2).addComponent(this.equal3Lbl)))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.equal4Lbl).addComponent(this.konversi7Field, -2, -1, -2).addComponent(this.konversiLbl)).addComponent(this.satuan1, -2, 20, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9).addComponent(this.satuan2, -2, 20, -2).addComponent(this.equal5Lbl).addComponent(this.konversi8Field, -2, -1, -2))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.satuan3, -2, 20, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.satuan4, -2, 20, -2))).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.minField, -2, -1, -2)).addComponent(this.minSatField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.discField, -2, -1, -2).addComponent(this.jLabel14).addComponent(this.minResultField, -2, -1, -2)).addGap(37, 37, 37).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2).addComponent(this.deleteButton, -2, -1, -2)).addContainerGap()));
        this.jTabbedPane1.addTab("Update General", this.jPanel1);
        this.jTable1.setModel(this.dtm);
        this.jScrollPane1.setViewportView(this.jTable1);
        this.jTabbedPane1.addTab("List Child Item", this.jScrollPane1);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jTabbedPane1, -2, 633, -2).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jTabbedPane1, -1, 403, Short.MAX_VALUE).addContainerGap()));
        this.pack();
    }

    private void initComponents2() {
        ((AbstractDocument)this.kodeBarangField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.namaBarangField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.jenisField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.konversi7Field.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.konversi8Field.getDocument()).setDocumentFilter(this.filter);
        this.namaBarangField.setDocument(new JTextFieldLimit(25));
        this.konversi1Field.setDocument(new JTextFieldLimit(10));
        this.konversi2Field.setDocument(new JTextFieldLimit(10));
        this.konversi3Field.setDocument(new JTextFieldLimit(10));
    }

    private void setDefault() {
        this.statusBarangLbl.setText("-");
        this.namaBarangField.setText("");
        this.jenisField.setText("");
        this.konversi1Field.setText("");
        this.konversi2Field.setText("");
        this.konversi3Field.setText("");
        this.konversi7Field.setText("0");
        this.konversi8Field.setText("0");
        this.satuan1.setText("");
        this.satuan2.setText("");
        this.satuan3.setText("");
        this.satuan4.setText("");
        this.hargaJualField1.setValue(0);
        this.hargaJualField2.setValue(0);
        this.hargaJualField3.setValue(0);
        this.minField.setText("0");
        this.minSatField.setText("-");
        this.discField.setText("0");
        this.minResultField.setValue(0);
    }

    private void escapeClick() {
        this.kodeBarangField.requestFocus();
        this.kodeBarangField.selectAll();
    }

    private void newData() {
        this.action = "create";
        this.setDefault();
    }

    private void removeRow() {
        int row = this.dtm.getRowCount();
        for (int a = 0; a < row; ++a) {
            this.dtm.removeRow(0);
        }
    }

    private void loadChildBarang() {
        this.removeRow();
        this.barangs = this.barangService.getBarangs(this.kodeBarangField.getText());
        for (Barang barang : this.barangs) {
            Object[] rowDetail = new Object[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
            this.dtm.addRow(rowDetail);
        }
    }

    private void loadParentBarang() {
        this.setDefault();
        this.parentBarang = this.parentBarangService.getParentBarang(this.kodeBarangField.getText());
        if (this.parentBarang == null) {
            this.namaBarangField.requestFocus();
            this.namaBarangField.selectAll();
            this.action = "create";
            this.setDefault();
            this.statusBarangLbl.setText("Data Baru");
        } else {
            this.action = "update";
            this.loadChildBarang();
            this.namaBarangField.setText(this.parentBarang.getNama());
            this.statusBarangLbl.setText("Update Data");
            this.namaBarangField.requestFocus();
            this.namaBarangField.selectAll();
            Barang tempBarang = null;
            tempBarang = this.barangs.isEmpty() ? null : this.barangs.get(0);
            if (tempBarang != null) {
                this.jenisField.setText(tempBarang.getJenis());
                this.konversi1Field.setText(tempBarang.getSatuan1());
                this.konversi2Field.setText(tempBarang.getSatuan2());
                this.konversi3Field.setText(tempBarang.getSatuan3());
                this.hargaJualField1.setValue(tempBarang.getHargaJual1());
                this.hargaJualField2.setValue(tempBarang.getHargaJual2());
                this.hargaJualField3.setValue(tempBarang.getHargaJual3());
                this.satuan1.setText(tempBarang.getSatuan2());
                this.satuan2.setText(tempBarang.getSatuan3());
                this.satuan3.setText(tempBarang.getSatuan1());
                this.satuan4.setText(tempBarang.getSatuan2());
                this.minField.setText(tempBarang.getMinPembelian() + "");
                this.minSatField.setText(tempBarang.getSatuan1());
                this.discField.setText(tempBarang.getMinPersen() + "");
                long resultValue = this.helper.roundPrice100((long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1());
                this.minResultField.setValue(resultValue);
                this.konversi7Field.setText(tempBarang.getKonversi21() + "");
                this.konversi8Field.setText(tempBarang.getKonversi32() + "");
            }
        }
    }

    private void saveParent() {
        if (this.action.equals("create")) {
            this.parentBarang = new ParentBarang();
            ParentBarangOnline pbo = new ParentBarangOnline();
            this.parentBarang.setId(this.kodeBarangField.getText());
            this.parentBarang.setNama(this.namaBarangField.getText());
            System.out.println(this.parentBarang.getNama());
            System.out.println(this.parentBarang.getId());
            pbo.setId(this.parentBarang.getId());
            pbo.setNama(this.parentBarang.getNama());
            pbo.setIdCabang(Integer.parseInt(this.konstantaService.getConstantValue("idCabang")));
            boolean resulto2oBarangParent = false;
            try {
                String hasil = this.comm.post("/apis/o2o/parent/insert", this.mapper.writeValueAsString((Object)pbo));
                JsonResponses response = (JsonResponses)this.mapper.readValue(hasil, JsonResponses.class);
                System.out.println(response.getStatus());
                if (response.getStatus().equals("success") && response.getError().equals("null")) {
                    resulto2oBarangParent = true;
                }
            }
            catch (JsonProcessingException ex) {
                System.out.println("Failed" + (Object)((Object)ex));
                JOptionPane.showMessageDialog(null, "Gagal memproses data JSON");
            }
            catch (IOException ex) {
                System.out.println(ex);
                Logger.getLogger(TabelBarangMaster.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Gagal menerima respon dari server online");
            }
            catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Gagal membuat koneksi ke server online");
            }
            if (resulto2oBarangParent) {
                int result = this.parentBarangService.createParent(this.parentBarang);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
                } else {
                    JOptionPane.showMessageDialog(null, "Penyimpanan Data Offline Gagal");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Online Gagal");
            }
        } else if (this.action.equals("update")) {
            this.parentBarang.setNama(this.namaBarangField.getText());
            ParentBarangOnline pbo = new ParentBarangOnline();
            pbo.setId(this.parentBarang.getId());
            pbo.setNama(this.parentBarang.getNama());
            pbo.setIdCabang(Integer.parseInt(this.konstantaService.getConstantValue("idCabang")));
            boolean resulto2oBarangParent = false;
            try {
                String hasil = this.comm.post("/apis/o2o/parent/insert", this.mapper.writeValueAsString((Object)pbo));
                JsonResponses response = (JsonResponses)this.mapper.readValue(hasil, JsonResponses.class);
                System.out.println(response.getStatus());
                if (response.getStatus().equals("success") && response.getError().equals("null")) {
                    resulto2oBarangParent = true;
                }
            }
            catch (JsonProcessingException ex) {
                System.out.println("Failed" + (Object)((Object)ex));
                JOptionPane.showMessageDialog(null, "Gagal memproses data JSON");
            }
            catch (IOException ex) {
                System.out.println(ex);
                Logger.getLogger(TabelBarangMaster.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Gagal menerima respon dari server online");
            }
            catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Gagal membuat koneksi ke server online");
            }
            if (resulto2oBarangParent) {
                int result = this.parentBarangService.update(this.parentBarang);
                if (result == 1) {
                    int totalRow = this.dtm.getRowCount();
                    for (int a = 0; a < totalRow; ++a) {
                        System.out.println(this.dtm.getValueAt(a, 0));
                        Barang barang = this.barangService.getBarang(this.dtm.getValueAt(a, 0) + "");
                        barang.setJenis(this.jenisField.getText());
                        barang.setSatuan1(this.konversi1Field.getText());
                        barang.setSatuan2(this.konversi2Field.getText());
                        barang.setSatuan3(this.konversi3Field.getText());
                        if (!this.hargaJualField1.getText().equals("")) {
                            barang.setHargaJual1(Long.parseLong(this.hargaJualField1.getValue() + ""));
                        }
                        barang.setKonversi21(Integer.parseInt(this.konversi7Field.getText()));
                        if (!this.hargaJualField2.getText().equals("")) {
                            barang.setHargaJual2(Long.parseLong(this.hargaJualField2.getValue() + ""));
                        }
                        barang.setKonversi32(Integer.parseInt(this.konversi8Field.getText()));
                        if (this.hargaJualField3.getValue() != null) {
                            barang.setHargaJual3(Long.parseLong(this.hargaJualField3.getValue() + ""));
                        }
                        barang.setMinPembelian(Float.parseFloat(this.minField.getText()));
                        barang.setMinPersen(Float.parseFloat(this.discField.getText()));
                        barang.setUpdated(new Date());
                        barang.setIdCabang(Integer.parseInt(this.konstantaService.getConstantValue("idCabang")));
                        boolean resulto2oBarang = false;
                        try {
                            System.out.println(barang.toString());
                            String hasil = this.comm.post("/apis/o2o/barang/insert", this.mapper.writeValueAsString((Object)barang));
                            JsonResponses response = (JsonResponses)this.mapper.readValue(hasil, JsonResponses.class);
                            System.out.println(response.getStatus());
                            resulto2oBarang = response.getStatus().equals("success") && response.getError().equals("null");
                        }
                        catch (JsonProcessingException ex) {
                            resulto2oBarang = false;
                            System.out.println("Failed" + (Object)((Object)ex));
                            JOptionPane.showMessageDialog(null, "Gagal memproses data JSON");
                        }
                        catch (IOException ex) {
                            resulto2oBarang = false;
                            System.out.println(ex);
                            Logger.getLogger(TabelBarangMaster.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Gagal menerima respon dari server online");
                        }
                        catch (Exception e) {
                            resulto2oBarang = false;
                            System.out.println(e);
                            JOptionPane.showMessageDialog(null, "Gagal membuat koneksi ke server online");
                        }
                        if (resulto2oBarang) {
                            int resultBarang = this.barangService.updateBarang2(barang, barang.getId());
                            if (resultBarang == 1) {
                                HistoryHarga historyHarga;
                                if (Long.parseLong(this.hargaJualField1.getValue() + "") != this.historyHarga1) {
                                    historyHarga = new HistoryHarga();
                                    historyHarga.setBarangId(barang.getId());
                                    historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                                    historyHarga.setSatuan(barang.getSatuan1());
                                    historyHarga.setHargaBaru(barang.getHargaJual1());
                                    historyHarga.setHargaLama(this.historyHarga1);
                                    historyHarga.setTanggal(new Date());
                                    historyHarga.setCreated(new Date());
                                    this.historyHargaService.createHistoryHarga(historyHarga);
                                }
                                if (Long.parseLong(this.hargaJualField2.getValue() + "") != this.historyHarga2) {
                                    historyHarga = new HistoryHarga();
                                    historyHarga.setBarangId(barang.getId());
                                    historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                                    historyHarga.setSatuan(barang.getSatuan2());
                                    historyHarga.setHargaBaru(barang.getHargaJual2());
                                    historyHarga.setHargaLama(this.historyHarga2);
                                    historyHarga.setTanggal(new Date());
                                    historyHarga.setCreated(new Date());
                                    int n = this.historyHargaService.createHistoryHarga(historyHarga);
                                }
                                if (Long.parseLong(this.hargaJualField3.getValue() + "") != this.historyHarga3) {
                                    historyHarga = new HistoryHarga();
                                    historyHarga.setBarangId(barang.getId());
                                    historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                                    historyHarga.setSatuan(barang.getSatuan3());
                                    historyHarga.setHargaBaru(barang.getHargaJual3());
                                    historyHarga.setHargaLama(this.historyHarga3);
                                    historyHarga.setTanggal(new Date());
                                    historyHarga.setCreated(new Date());
                                    this.historyHargaService.createHistoryHarga(historyHarga);
                                }
                                this.escapeClick();
                                this.kodeBarangField.setText("");
                                continue;
                            }
                            JOptionPane.showMessageDialog(null, "Penyimpanan Data Offline Gagal");
                            continue;
                        }
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data Online untuk id " + barang.getId() + " dan selanjutnya Gagal, harap ulangi");
                        break;
                    }
                    JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
                    this.setDefault();
                } else {
                    JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
                }
            }
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        boolean konversi = true;
        if (!this.konversi2Field.getText().equals("") && (this.konversi7Field.getText().equals("") || this.konversi7Field.getText().equals("0"))) {
            konversi = false;
        }
        if (!this.konversi3Field.getText().equals("") && (this.konversi8Field.getText().equals("") || this.konversi8Field.getText().equals("0"))) {
            konversi = false;
        }
        if (konversi) {
            this.saveParent();
        } else {
            JOptionPane.showMessageDialog(null, "Isi Konversi Terlebih Dahulu");
        }
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
                this.loadParentBarang();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void namaBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            this.jenisField.requestFocus();
            this.jenisField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.escapeClick();
        }
    }

    private void jenisFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.jenisField.getText().equals("")) {
                this.sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
                this.sd.addSearchDialogListener(new searchJenisDialogEventHandler());
                int w = this.sd.getWidth();
                int h = this.sd.getHeight();
                this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                this.sd.setVisible(true);
            } else {
                int confirm;
                Jenis jenis = this.jenisService.getJenis(this.jenisField.getText());
                if (jenis == null && (confirm = JOptionPane.showConfirmDialog(null, "Jenis Barang " + this.jenisField.getText() + " Tidak Ditemukan, Tambahkan Data Jenis?", "Data Warning", 2)) == 0) {
                    jenis = new Jenis();
                    jenis.setJenis(this.jenisField.getText());
                    jenis.setCreated(new Date());
                    jenis.setUpdated(new Date());
                    int result = this.jenisService.createJenis(jenis);
                    if (result == 1) {
                        JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
                        this.konversi1Field.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
                    }
                }
                this.konversi1Field.requestFocus();
                this.konversi1Field.selectAll();
            }
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
            this.sd.addSearchDialogListener(new searchJenisDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        } else if (evt.getKeyCode() == 38) {
            this.namaBarangField.requestFocus();
            this.namaBarangField.selectAll();
        }
    }

    private void konversi1FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            this.satuan3.setText(this.konversi1Field.getText());
            this.hargaJualField1.requestFocus();
            this.hargaJualField1.setText(this.hargaJualField1.getText().replace(".", ""));
            this.hargaJualField1.selectAll();
        }
    }

    private void konversi2FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (!this.konversi2Field.getText().equals("")) {
                this.satuan1.setText(this.konversi2Field.getText());
                this.satuan4.setText(this.konversi2Field.getText());
                this.konversi7Field.setEnabled(true);
            }
            this.hargaJualField2.requestFocus();
            this.hargaJualField2.setText(this.hargaJualField2.getText().replace(".", ""));
            this.hargaJualField2.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.hargaJualField1.requestFocus();
            this.hargaJualField1.setText(this.hargaJualField1.getText().replace(".", ""));
            this.hargaJualField1.selectAll();
        }
    }

    private void konversi3FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (!this.konversi3Field.getText().equals("")) {
                this.satuan2.setText(this.konversi3Field.getText());
                this.konversi8Field.setEnabled(true);
            }
            this.hargaJualField3.requestFocus();
            this.hargaJualField3.setText(this.hargaJualField3.getText().replace(".", ""));
            this.hargaJualField3.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.hargaJualField2.requestFocus();
            this.hargaJualField2.setText(this.hargaJualField1.getText().replace(".", ""));
            this.hargaJualField2.selectAll();
        }
    }

    private void konversi7FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.helper.isInteger(this.konversi7Field.getText())) {
                this.konversi8Field.requestFocus();
                this.konversi8Field.selectAll();
                this.konversi7Field.setBackground(Color.white);
            } else {
                this.konversi7Field.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 38) {
            this.hargaJualField3.requestFocus();
            this.hargaJualField3.setText(this.hargaJualField1.getText().replace(".", ""));
            this.hargaJualField3.selectAll();
        }
    }

    private void konversi8FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.helper.isInteger(this.konversi8Field.getText())) {
                this.konversi8Field.setBackground(Color.white);
                this.minField.requestFocus();
                this.minField.selectAll();
            } else {
                this.konversi8Field.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 38) {
            this.konversi7Field.requestFocus();
            this.konversi7Field.selectAll();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.discField.requestFocus();
            this.discField.selectAll();
        } else if (evt.getKeyCode() == 10) {
            boolean konversi = true;
            if (!this.konversi2Field.getText().equals("") && (this.konversi7Field.getText().equals("") || this.konversi7Field.getText().equals("0"))) {
                konversi = false;
            }
            if (!this.konversi3Field.getText().equals("") && (this.konversi8Field.getText().equals("") || this.konversi8Field.getText().equals("0"))) {
                konversi = false;
            }
            if (konversi) {
                this.saveParent();
            } else {
                JOptionPane.showMessageDialog(null, "Isi Konversi Terlebih Dahulu");
            }
        } else if (evt.getKeyCode() == 39 || evt.getKeyCode() == 40) {
            this.deleteButton.requestFocus();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.deleteButton.requestFocus();
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void hargaJualField1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.minResultField.setValue(Long.parseLong(this.hargaJualField1.getText()));
            this.konversi2Field.requestFocus();
            this.konversi2Field.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.konversi1Field.requestFocus();
            this.konversi1Field.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void hargaJualField3KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.konversi7Field.requestFocus();
            this.konversi7Field.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.konversi3Field.requestFocus();
            this.konversi3Field.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void hargaJualField2KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.konversi3Field.requestFocus();
            this.konversi3Field.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.konversi2Field.requestFocus();
            this.konversi2Field.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void deleteButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.canGoThrough = true;
            ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Data Barang " + this.namaBarangField.getText() + " Akan Dihapus, Lanjutkan?");
            confirmationDialog.addListener(new confirmDialogEventHandler());
            int w = confirmationDialog.getWidth();
            int h = confirmationDialog.getHeight();
            confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            confirmationDialog.setVisible(true);
            if (this.canGoThrough) {
                int status = this.barangService.deleteBarang(this.kodeBarangField.getText());
                if (status == 1) {
                    JOptionPane.showMessageDialog(null, "Data Telah Terhapus");
                    this.setDefault();
                } else {
                    JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
                }
            }
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 39 || evt.getKeyCode() == 40) {
            this.exitButton.requestFocus();
        }
    }

    private void deleteButtonActionPerformed(ActionEvent evt) {
        this.canGoThrough = true;
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Data Barang " + this.namaBarangField.getText() + " Akan Dihapus, Lanjutkan?");
        confirmationDialog.addListener(new confirmDialogEventHandler());
        int w = confirmationDialog.getWidth();
        int h = confirmationDialog.getHeight();
        confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        confirmationDialog.setVisible(true);
        if (this.canGoThrough) {
            boolean resulto2oDeleteBarang = false;
            try {
                String hasil = this.comm.get("/apis/o2o/barang/delete/" + this.kodeBarangField.getText());
                JsonResponses response = (JsonResponses)this.mapper.readValue(hasil, JsonResponses.class);
                System.out.println(response.getStatus());
                if (response.getStatus().equals("success") && response.getError().equals("null")) {
                    resulto2oDeleteBarang = true;
                }
            }
            catch (JsonProcessingException ex) {
                Logger.getLogger(ParentMasterUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex) {
                Logger.getLogger(ParentMasterUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (resulto2oDeleteBarang) {
                int status = this.barangService.deleteBarang(this.kodeBarangField.getText());
                if (status == 1) {
                    JOptionPane.showMessageDialog(null, "Data Telah Terhapus");
                    this.setDefault();
                } else {
                    JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
                }
            }
        }
    }

    private void minFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.helper.isFloat(this.minField.getText())) {
                this.minField.setBackground(Color.white);
                this.discField.requestFocus();
                this.discField.selectAll();
            } else {
                this.minField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 38) {
            this.konversi8Field.requestFocus();
            this.konversi8Field.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void discFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.helper.isFloat(this.discField.getText())) {
                this.discField.setBackground(Color.white);
                long resultValue = this.helper.roundPrice100((long)(Float.parseFloat(this.discField.getText()) / 100.0f * (float)Long.parseLong(this.hargaJualField1.getValue().toString())) + Long.parseLong(this.hargaJualField1.getValue().toString()));
                this.minResultField.setValue(resultValue);
                this.saveButton.requestFocus();
            } else {
                this.discField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 38) {
            this.minField.requestFocus();
            this.minField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            ParentMasterUpdate.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            ParentMasterUpdate.this.canGoThrough = false;
        }
    }

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ParentMasterUpdate.this.jenisField.setText(chooseValue);
            ParentMasterUpdate.this.konversi1Field.requestFocus();
            ParentMasterUpdate.this.konversi1Field.selectAll();
        }
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ParentMasterUpdate.this.kodeBarangField.setText(chooseValue);
            ParentMasterUpdate.this.loadParentBarang();
        }
    }

    private class searchBarangDialogEventHandler
    implements SearchDialogListener {
        private searchBarangDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE PARENT", "NAMA BARANG"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<ParentBarang> parentBarangs = ParentMasterUpdate.this.parentBarangService.getParents(searchWord);
            for (ParentBarang parentBarang : parentBarangs) {
                Object[] temp = new String[4];
                temp[0] = parentBarang.getId();
                temp[1] = parentBarang.getNama();
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Parent Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
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
            List<Jenis> jenisses = ParentMasterUpdate.this.jenisService.getJenisses(searchWord);
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
}

