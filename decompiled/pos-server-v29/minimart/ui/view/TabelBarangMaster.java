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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
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
import minimart.engine.model.Pembelian;
import minimart.engine.model.PembelianDetail;
import minimart.engine.model.RBarangSupplier;
import minimart.engine.model.Supplier;
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
import minimart.engine.service.StokOpnamMandiriDetailService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.BarcodeInputDialogListener;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.BarcodeInputDialog;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class TabelBarangMaster
extends JDialog {
    private JComboBox barcodeCmb1;
    private JComboBox barcodeCmb2;
    private JComboBox barcodeCmb3;
    private JTextField batasStockField;
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
    private JLabel hargaTerakhirSatuan;
    private JFormattedTextField hppField;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel2;
    private JLabel jLabel24;
    private JLabel jLabel28;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JSeparator jSeparator1;
    private JTextField jTextField1;
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
    private JLabel labelSO;
    private JTextField minField;
    private JFormattedTextField minResultField;
    private JLabel minSatField;
    private JLabel minStokLbl;
    private JTextField namaBarangField;
    private JLabel namaBarangLbl;
    private JTextField parentField;
    private JLabel parentLbl;
    private JTextField rakTokoField;
    private JTextField reorderField;
    private JLabel reorderLbl;
    private JLabel satuan1;
    private JLabel satuan2;
    private JLabel satuan3;
    private JLabel satuan4;
    private JButton saveButton;
    private JLabel statusBarangLbl;
    private JLabel stokLbl;
    private JTextField stokTokoField1;
    private JTextField stokTokoField2;
    private JTextField stokTokoField3;
    private JLabel stokTokoLbl1;
    private JLabel stokTokoLbl2;
    private JLabel stokTokoLbl3;
    private JComboBox supplierCmb;
    private JTextField tagField;
    private BarangService barangService = new BarangService();
    private JenisService jenisService = new JenisService();
    private Barang barang = new Barang();
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
    private ParentBarangService parentBarangService = new ParentBarangService();
    private boolean canGoThrough;
    private BarcodeService barcodeService = new BarcodeService();
    private List<Barcode> deleteBarcode = new LinkedList<Barcode>();
    private List<String> deleteSupplier = new LinkedList<String>();
    private long historyHarga1;
    private long historyHarga2;
    private long historyHarga3;
    private String tipe;
    private HistoryHargaService historyHargaService = new HistoryHargaService();
    private RBarangSupplierService rBarangSupplierService = new RBarangSupplierService();
    private Karyawan karyawan;
    private KonstantaService konstantaService = new KonstantaService();
    private CommonEngine comm = new CommonEngine("https://admin.sinarmart.id");
    private ObjectMapper mapper = new ObjectMapper();
    private StokOpnamMandiriDetailService stokOpnamMandiriDetailService = new StokOpnamMandiriDetailService();
    private static final int SOM_HIDE_N_DAYS = 1;

    public TabelBarangMaster(Frame owner, boolean modal, String tipe) {
        super(owner, modal);
        this.initComponents();
        this.initComponents2();
        this.setTitle("Entry Tabel Barang");
        this.tipe = tipe;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.karyawan = this.helper.getKaryawan();
        this.setDefault();
        if (tipe.equalsIgnoreCase("CABANG")) {
            this.saveButton.setEnabled(false);
            this.deleteButton.setEnabled(false);
        } else if (tipe.equalsIgnoreCase("PUSAT")) {
            this.saveButton.setEnabled(true);
            this.deleteButton.setEnabled(true);
        }
        if (this.karyawan.getRole().equalsIgnoreCase("KASIR") || this.karyawan.getRole().equalsIgnoreCase("ADMIN")) {
            this.saveButton.setEnabled(false);
            this.deleteButton.setEnabled(false);
        }
    }

    private void initComponents() {
        this.jSeparator1 = new JSeparator();
        this.jLabel6 = new JLabel();
        this.kodeBarangLbl = new JLabel();
        this.namaBarangLbl = new JLabel();
        this.jenisLbl = new JLabel();
        this.kodeBarangField = new JTextField();
        this.namaBarangField = new JTextField();
        this.jenisField = new JTextField();
        this.konversi1Lbl = new JLabel();
        this.konversi2Lbl = new JLabel();
        this.konversi3Lbl = new JLabel();
        this.konversi1Field = new JTextField();
        this.konversi2Field = new JTextField();
        this.konversi3Field = new JTextField();
        this.konversiLbl = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.konversi7Field = new JTextField();
        this.konversi8Field = new JTextField();
        this.satuan1 = new JLabel();
        this.satuan2 = new JLabel();
        this.equal4Lbl = new JLabel();
        this.equal5Lbl = new JLabel();
        this.satuan3 = new JLabel();
        this.satuan4 = new JLabel();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.equal1Lbl = new JLabel();
        this.equal2Lbl = new JLabel();
        this.equal3Lbl = new JLabel();
        this.stokLbl = new JLabel();
        this.stokTokoField1 = new JTextField();
        this.stokTokoLbl1 = new JLabel();
        this.stokTokoLbl2 = new JLabel();
        this.stokTokoField2 = new JTextField();
        this.stokTokoField3 = new JTextField();
        this.stokTokoLbl3 = new JLabel();
        this.reorderLbl = new JLabel();
        this.reorderField = new JTextField();
        this.minStokLbl = new JLabel();
        this.jLabel11 = new JLabel();
        this.jLabel12 = new JLabel();
        this.jLabel10 = new JLabel();
        this.jLabel15 = new JLabel();
        this.hargaJualField1 = new JFormattedTextField();
        this.hargaJualField2 = new JFormattedTextField();
        this.hargaJualField3 = new JFormattedTextField();
        this.jLabel24 = new JLabel();
        this.hppField = new JFormattedTextField();
        this.hargaTerakhirSatuan = new JLabel();
        this.jLabel28 = new JLabel();
        this.deleteButton = new JButton();
        this.statusBarangLbl = new JLabel();
        this.barcodeCmb1 = new JComboBox();
        this.barcodeCmb2 = new JComboBox();
        this.barcodeCmb3 = new JComboBox();
        this.jLabel7 = new JLabel();
        this.jLabel2 = new JLabel();
        this.supplierCmb = new JComboBox();
        this.jLabel4 = new JLabel();
        this.rakTokoField = new JTextField();
        this.jLabel5 = new JLabel();
        this.minField = new JTextField();
        this.discField = new JTextField();
        this.minResultField = new JFormattedTextField();
        this.jLabel14 = new JLabel();
        this.minSatField = new JLabel();
        this.batasStockField = new JTextField();
        this.jLabel1 = new JLabel();
        this.jLabel13 = new JLabel();
        this.parentField = new JTextField();
        this.parentLbl = new JLabel();
        this.jLabel16 = new JLabel();
        this.jTextField1 = new JTextField();
        this.labelSO = new JLabel();
        this.tagField = new JTextField();
        this.jLabel6.setText("jLabel6");
        this.setDefaultCloseOperation(2);
        this.kodeBarangLbl.setText("Kode Barang*");
        this.namaBarangLbl.setText("Nama Barang*");
        this.jenisLbl.setText("Jenis* (Cari:F2)");
        this.kodeBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.kodeBarangFieldKeyPressed(evt);
            }
        });
        this.namaBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.namaBarangFieldKeyPressed(evt);
            }
        });
        this.jenisField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.jenisFieldKeyPressed(evt);
            }
        });
        this.konversi1Lbl.setText("Satuan 1");
        this.konversi2Lbl.setText("Satuan 2");
        this.konversi3Lbl.setText("Satuan 3");
        this.konversi1Field.setDocument(new JTextFieldLimit(3));
        this.konversi1Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.konversi1FieldKeyPressed(evt);
            }
        });
        this.konversi2Field.setDocument(new JTextFieldLimit(3));
        this.konversi2Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.konversi2FieldKeyPressed(evt);
            }
        });
        this.konversi3Field.setDocument(new JTextFieldLimit(3));
        this.konversi3Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.konversi3FieldKeyPressed(evt);
            }
        });
        this.konversiLbl.setText("Konversi");
        this.jLabel8.setText("1");
        this.jLabel9.setText("1");
        this.konversi7Field.setText("0");
        this.konversi7Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.konversi7FieldKeyPressed(evt);
            }
        });
        this.konversi8Field.setText("0");
        this.konversi8Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.konversi8FieldKeyPressed(evt);
            }
        });
        this.satuan1.setBackground(new Color(255, 255, 255));
        this.satuan2.setBackground(new Color(255, 255, 255));
        this.equal4Lbl.setText("=");
        this.equal5Lbl.setText("=");
        this.saveButton.setText("Save");
        this.saveButton.setMaximumSize(new Dimension(73, 23));
        this.saveButton.setMinimumSize(new Dimension(73, 23));
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                TabelBarangMaster.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setMaximumSize(new Dimension(73, 23));
        this.exitButton.setMinimumSize(new Dimension(73, 23));
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                TabelBarangMaster.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.exitButtonKeyPressed(evt);
            }
        });
        this.equal1Lbl.setText("=");
        this.equal2Lbl.setText("=");
        this.equal3Lbl.setText("=");
        this.stokLbl.setText("Stok Toko");
        this.stokTokoField1.setEditable(false);
        this.stokTokoField1.setBackground(new Color(153, 255, 204));
        this.stokTokoField1.setText("0");
        this.stokTokoLbl1.setPreferredSize(new Dimension(34, 14));
        this.stokTokoLbl2.setPreferredSize(new Dimension(34, 14));
        this.stokTokoField2.setEditable(false);
        this.stokTokoField2.setBackground(new Color(153, 255, 204));
        this.stokTokoField2.setText("0");
        this.stokTokoField3.setEditable(false);
        this.stokTokoField3.setBackground(new Color(153, 255, 204));
        this.stokTokoField3.setText("0");
        this.stokTokoLbl3.setPreferredSize(new Dimension(34, 14));
        this.reorderLbl.setText("Reorder Point");
        this.reorderField.setText("0");
        this.reorderField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.reorderFieldKeyPressed(evt);
            }
        });
        this.minStokLbl.setPreferredSize(new Dimension(34, 14));
        this.jLabel11.setText(":");
        this.jLabel12.setText(":");
        this.jLabel10.setText(":");
        this.jLabel15.setText("Harga Jual");
        this.hargaJualField1.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.hargaJualField1.setText("0");
        this.hargaJualField1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.hargaJualField1KeyPressed(evt);
            }
        });
        this.hargaJualField2.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.hargaJualField2.setText("0");
        this.hargaJualField2.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.hargaJualField2KeyPressed(evt);
            }
        });
        this.hargaJualField3.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.hargaJualField3.setText("0");
        this.hargaJualField3.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.hargaJualField3KeyPressed(evt);
            }
        });
        this.jLabel24.setText("Harga Beli Terakhir");
        this.hppField.setBackground(new Color(153, 255, 204));
        this.hppField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter()));
        this.hppField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.hppFieldKeyPressed(evt);
            }
        });
        this.jLabel28.setText("TAG");
        this.deleteButton.setText("Delete");
        this.deleteButton.setPreferredSize(new Dimension(73, 22));
        this.deleteButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                TabelBarangMaster.this.deleteButtonActionPerformed(evt);
            }
        });
        this.deleteButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.deleteButtonKeyPressed(evt);
            }
        });
        this.statusBarangLbl.setFont(new Font("Tahoma", 1, 14));
        this.statusBarangLbl.setForeground(new Color(0, 0, 204));
        this.statusBarangLbl.setText("-");
        this.barcodeCmb1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.barcodeCmb1KeyPressed(evt);
            }
        });
        this.barcodeCmb2.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.barcodeCmb2KeyPressed(evt);
            }
        });
        this.barcodeCmb3.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.barcodeCmb3KeyPressed(evt);
            }
        });
        this.jLabel7.setText("Add Barcode[F2]/remove[DEL]");
        this.jLabel2.setText("Supplier");
        this.supplierCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.supplierCmbKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Rak Toko");
        this.rakTokoField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.rakTokoFieldKeyPressed(evt);
            }
        });
        this.jLabel5.setText("Min Pembelian");
        this.minField.setText("0");
        this.minField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.minFieldKeyPressed(evt);
            }
        });
        this.discField.setText("0");
        this.discField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.discFieldKeyPressed(evt);
            }
        });
        this.minResultField.setText("0");
        this.jLabel14.setText("%");
        this.minSatField.setText("-");
        this.batasStockField.setText("0");
        this.batasStockField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.batasStockFieldKeyPressed(evt);
            }
        });
        this.jLabel1.setText("Batas Stok");
        this.jLabel13.setText("Parent");
        this.parentField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                TabelBarangMaster.this.parentFieldKeyPressed(evt);
            }
        });
        this.parentLbl.setText("-");
        this.jLabel16.setText("Brand (Cari:F2)");
        this.labelSO.setForeground(new Color(255, 0, 51));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaBarangLbl).addComponent(this.jenisLbl).addComponent(this.jLabel2)).addGap(36, 36, 36).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.namaBarangField).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.labelSO, -2, 195, -2)).addGroup(layout.createSequentialGroup().addComponent(this.kodeBarangField, -2, 207, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.statusBarangLbl, -1, -1, Short.MAX_VALUE))).addGap(92, 92, 92)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.supplierCmb, -2, 362, -2).addGroup(layout.createSequentialGroup().addComponent(this.jenisField, -2, 141, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel16).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField1, -2, 171, -2))).addGap(0, 177, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.kodeBarangLbl).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel24).addGap(18, 18, 18).addComponent(this.hppField, -2, 143, -2)).addComponent(this.hargaTerakhirSatuan, -1, 341, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.konversiLbl).addGap(36, 36, 36).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel9).addGroup(layout.createSequentialGroup().addGap(16, 16, 16).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.satuan1, -2, 40, -2).addComponent(this.satuan2, -2, 40, -2))).addComponent(this.jLabel8)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.equal4Lbl, -1, -1, Short.MAX_VALUE).addComponent(this.equal5Lbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.konversi8Field, -2, 64, -2).addComponent(this.konversi7Field, -2, 64, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.satuan4, -2, 40, -2).addComponent(this.satuan3, -2, 40, -2))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel5).addComponent(this.jLabel13)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.parentLbl, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(this.discField, -2, 44, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel14).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.minResultField, -2, 93, -2)).addGroup(layout.createSequentialGroup().addComponent(this.minField, -2, 48, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.minSatField, -2, 86, -2)).addComponent(this.parentField)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel28).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.rakTokoField, -1, 79, Short.MAX_VALUE).addComponent(this.tagField))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.reorderLbl).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.reorderField).addComponent(this.batasStockField, -2, 79, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.minStokLbl, -2, -1, -2)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.deleteButton, -2, 67, -2).addGap(2, 2, 2).addComponent(this.exitButton, -2, 67, -2)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.stokLbl).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.stokTokoField2, GroupLayout.Alignment.LEADING).addComponent(this.stokTokoField1, GroupLayout.Alignment.LEADING).addComponent(this.stokTokoField3, -2, 79, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.stokTokoLbl2, -2, -1, -2).addComponent(this.stokTokoLbl1, -2, -1, -2).addComponent(this.stokTokoLbl3, -2, -1, -2))))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.konversi1Lbl).addComponent(this.konversi2Lbl).addComponent(this.konversi3Lbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.konversi2Field, -1, 72, Short.MAX_VALUE).addComponent(this.konversi1Field).addComponent(this.konversi3Field)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.equal2Lbl).addComponent(this.equal1Lbl).addComponent(this.equal3Lbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.hargaJualField2, GroupLayout.Alignment.LEADING, -1, 160, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGap(16, 16, 16).addComponent(this.jLabel15)).addComponent(this.hargaJualField1, GroupLayout.Alignment.LEADING).addComponent(this.hargaJualField3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel12).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel11).addComponent(this.jLabel10))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.barcodeCmb1, 0, 143, Short.MAX_VALUE).addComponent(this.barcodeCmb3, 0, -1, Short.MAX_VALUE).addComponent(this.barcodeCmb2, 0, -1, Short.MAX_VALUE)).addComponent(this.jLabel7)))).addGap(0, 115, Short.MAX_VALUE)))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.kodeBarangLbl).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(2, 2, 2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kodeBarangField, -2, -1, -2).addComponent(this.statusBarangLbl)))).addGap(2, 2, 2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.labelSO, -1, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.namaBarangLbl).addComponent(this.namaBarangField, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jenisLbl).addComponent(this.jenisField, -2, -1, -2).addComponent(this.jLabel16).addComponent(this.jTextField1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.supplierCmb, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(19, 19, 19).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.equal1Lbl).addComponent(this.konversi1Field, -2, -1, -2).addComponent(this.konversi1Lbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.konversi2Field, -2, -1, -2).addComponent(this.konversi2Lbl))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel15).addGap(2, 2, 2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel7).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel11).addComponent(this.hargaJualField1, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.barcodeCmb1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hargaJualField2, -2, -1, -2).addComponent(this.barcodeCmb2, -2, -1, -2).addComponent(this.jLabel10).addComponent(this.equal2Lbl)))).addGap(5, 5, 5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.konversi3Field, -2, -1, -2).addComponent(this.konversi3Lbl)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hargaJualField3, -2, -1, -2).addComponent(this.equal3Lbl).addComponent(this.barcodeCmb3, -2, -1, -2).addComponent(this.jLabel12)).addGap(2, 2, 2))))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.satuan1, -2, 20, -2).addComponent(this.equal4Lbl).addComponent(this.konversi7Field, -2, -1, -2).addComponent(this.konversiLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9).addComponent(this.satuan2, -2, 20, -2).addComponent(this.equal5Lbl).addComponent(this.konversi8Field, -2, -1, -2))).addGroup(layout.createSequentialGroup().addComponent(this.satuan3, -2, 20, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.satuan4, -2, 20, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.minField, -2, -1, -2).addComponent(this.minSatField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.discField, -2, -1, -2).addComponent(this.jLabel14).addComponent(this.minResultField, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.parentField, -2, -1, -2).addComponent(this.jLabel13)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.parentLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.stokTokoField1, -2, -1, -2).addComponent(this.stokLbl).addComponent(this.stokTokoLbl1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.stokTokoLbl2, -2, -1, -2).addComponent(this.stokTokoField2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.stokTokoField3, -2, -1, -2).addComponent(this.stokTokoLbl3, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(12, 12, 12).addComponent(this.minStokLbl, -1, 75, Short.MAX_VALUE).addGap(20, 20, 20)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.batasStockField, -2, -1, -2).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.reorderField, -2, -1, -2).addComponent(this.reorderLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel28).addComponent(this.tagField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.rakTokoField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2).addComponent(this.deleteButton, -2, -1, -2))).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel24, GroupLayout.Alignment.TRAILING).addComponent(this.hppField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.hargaTerakhirSatuan, -2, 20, -2)))));
        this.pack();
    }

    private void initComponents2() {
        ((AbstractDocument)this.kodeBarangField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.namaBarangField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.jenisField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.tagField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.rakTokoField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.konversi7Field.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.konversi8Field.getDocument()).setDocumentFilter(this.filter);
        this.konversi1Field.setDocument(new JTextFieldLimit(10));
        this.konversi2Field.setDocument(new JTextFieldLimit(10));
        this.konversi3Field.setDocument(new JTextFieldLimit(10));
        if (this.helper.getStatusProgram().equals("TOKO")) {
            this.stokLbl.setText("Stok Toko");
        } else if (this.helper.getStatusProgram().equals("GUDANG")) {
            this.stokLbl.setText("Stok Gudang");
        }
    }

    private void setDefault() {
        this.parentField.setText("");
        this.parentLbl.setText("");
        this.barcodeCmb1.removeAllItems();
        this.barcodeCmb2.removeAllItems();
        this.barcodeCmb3.removeAllItems();
        this.supplierCmb.removeAllItems();
        this.statusBarangLbl.setText("-");
        this.hargaTerakhirSatuan.setText("");
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
        this.stokTokoField1.setText("0");
        this.stokTokoField2.setText("0");
        this.stokTokoField3.setText("0");
        this.stokTokoLbl1.setText("");
        this.stokTokoLbl2.setText("");
        this.stokTokoLbl3.setText("");
        this.reorderField.setText("0");
        this.batasStockField.setText("0");
        this.minStokLbl.setText("");
        this.hargaJualField1.setValue(0);
        this.hargaJualField2.setValue(0);
        this.hargaJualField3.setValue(0);
        this.hppField.setValue(0);
        this.tagField.setText("");
        this.rakTokoField.setText("");
        this.minField.setText("0");
        this.minSatField.setText("-");
        this.discField.setText("0");
        this.minResultField.setValue(0);
    }

    private void loadParentBarang(String id) {
        ParentBarang parentBarang = this.parentBarangService.getParentBarang(id);
        this.parentField.setText(parentBarang.getId());
        this.parentLbl.setText(parentBarang.getNama());
    }

    private void escapeClick() {
        this.kodeBarangField.requestFocus();
        this.kodeBarangField.selectAll();
    }

    private void newData() {
        this.action = "create";
        this.setDefault();
    }

    private void loadKodeBarang() {
        String result = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetailOnProgress(this.kodeBarangField.getText(), 1);
        String hide = this.konstantaService.getConstantValue("hideStock");
        String textHide = "Stok disembunyikan sedang SOM";
        if (hide.equals("YES")) {
            result = "YES";
            textHide = "Stok disembunyikan beberapa saat";
        }
        this.setDefault();
        this.barang = this.barangService.getBarang(this.kodeBarangField.getText());
        if (this.barang == null) {
            this.namaBarangField.requestFocus();
            this.namaBarangField.selectAll();
            this.newData();
            this.statusBarangLbl.setText("Data Baru");
            this.barcodeCmb1.addItem(this.kodeBarangField.getText());
        } else {
            ParentBarang parentBarang;
            this.action = "update";
            if (this.barang.getParentId() == null || this.barang.getParentId().equalsIgnoreCase("")) {
                this.konversi1Field.setEnabled(true);
                this.konversi2Field.setEnabled(true);
                this.konversi3Field.setEnabled(true);
                this.hargaJualField1.setEnabled(true);
                this.hargaJualField2.setEnabled(true);
                this.hargaJualField3.setEnabled(true);
                this.konversi7Field.setEnabled(true);
                this.konversi8Field.setEnabled(true);
                this.minField.setEnabled(true);
                this.minResultField.setEnabled(true);
                this.discField.setEnabled(true);
            } else {
                this.konversi1Field.setEnabled(false);
                this.konversi2Field.setEnabled(false);
                this.konversi3Field.setEnabled(false);
                this.hargaJualField1.setEnabled(false);
                this.hargaJualField2.setEnabled(false);
                this.hargaJualField3.setEnabled(false);
                this.konversi7Field.setEnabled(false);
                this.konversi8Field.setEnabled(false);
                this.minField.setEnabled(false);
                this.minResultField.setEnabled(false);
                this.discField.setEnabled(false);
            }
            this.statusBarangLbl.setText("Update Data");
            this.historyHarga1 = this.barang.getHargaJual1();
            this.historyHarga2 = this.barang.getHargaJual2();
            this.historyHarga3 = this.barang.getHargaJual3();
            this.kodeBarangField.setText(this.barang.getId());
            this.namaBarangField.setText(this.barang.getNama());
            this.jenisField.setText(this.barang.getJenis());
            this.konversi1Field.setText(this.barang.getSatuan1());
            this.konversi2Field.setText(this.barang.getSatuan2());
            this.konversi3Field.setText(this.barang.getSatuan3());
            this.minField.setText(this.barang.getMinPembelian() + "");
            this.minSatField.setText(this.barang.getSatuan1());
            this.discField.setText(this.barang.getMinPersen() + "");
            long resultValue = this.helper.roundPrice100((long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1());
            this.minResultField.setValue(resultValue);
            this.hppBarang = this.barang.getHpp();
            if (this.karyawan.getRole().equalsIgnoreCase("DIREKTUR") || this.karyawan.getRole().equalsIgnoreCase("MANAGER")) {
                this.hppField.setValue(this.barang.getHpp());
                PembelianDetail pembelianDetail = this.pembelianDetailService.getPembelianTerakhir(this.barang.getId());
                if (pembelianDetail != null) {
                    Pembelian pembelian = this.pembelianService.getPembelian(pembelianDetail.getPembelianId());
                    this.hargaTerakhirSatuan.setText(this.supplierService.getSupplier(pembelian.getSupplierId()).getNama());
                }
            }
            if (this.karyawan.getRole().equalsIgnoreCase("KASIR")) {
                this.saveButton.setEnabled(false);
            }
            this.konversi7Field.setText(this.barang.getKonversi21() + "");
            this.konversi8Field.setText(this.barang.getKonversi32() + "");
            this.hargaJualField1.setValue(this.barang.getHargaJual1());
            this.hargaJualField2.setValue(this.barang.getHargaJual2());
            this.hargaJualField3.setValue(this.barang.getHargaJual3());
            this.satuan1.setText(this.barang.getSatuan2());
            this.satuan2.setText(this.barang.getSatuan3());
            this.satuan3.setText(this.barang.getSatuan1());
            this.satuan4.setText(this.barang.getSatuan2());
            this.reorderField.setText(this.barang.getReorderPoint() + "");
            this.batasStockField.setText(this.barang.getBatasAtasStock() + "");
            this.minStokLbl.setText(this.barang.getSatuan1());
            float stokTotal = 0.0f;
            if (!result.equals("")) {
                this.labelSO.setText(textHide);
            } else {
                this.labelSO.setText("");
                stokTotal = this.barang.getStokToko();
            }
            float stok1 = 0.0f;
            float stok2 = 0.0f;
            float stok3 = 0.0f;
            if (this.barang.getSatuan2().equals("") && this.barang.getSatuan3().equals("")) {
                stok1 = stokTotal;
            } else if (this.barang.getSatuan3().equals("")) {
                stok2 = (long)stokTotal / (long)this.barang.getKonversi21();
                stok1 = this.helper.round(stokTotal % (float)this.barang.getKonversi21(), 2);
            } else {
                stok3 = (long)stokTotal / (long)(this.barang.getKonversi21() * this.barang.getKonversi32());
                stok2 = (long)(stokTotal % (float)(this.barang.getKonversi21() * this.barang.getKonversi32())) / (long)this.barang.getKonversi21();
                stok1 = this.helper.round(stokTotal % (float)(this.barang.getKonversi21() * this.barang.getKonversi32()) % (float)this.barang.getKonversi21(), 2);
            }
            this.tagField.setText(this.barang.getTag());
            this.rakTokoField.setText(this.barang.getRakIdToko());
            this.stokTokoField1.setText(stok1 + "");
            this.stokTokoField2.setText((long)stok2 + "");
            this.stokTokoField3.setText((long)stok3 + "");
            this.stokTokoLbl1.setText(this.barang.getSatuan1());
            this.stokTokoLbl2.setText(this.barang.getSatuan2());
            this.stokTokoLbl3.setText(this.barang.getSatuan3());
            for (Barcode tempBarcode : this.barang.getBarcode_1()) {
                this.barcodeCmb1.addItem(tempBarcode.getBarcode());
            }
            for (Barcode tempBarcode : this.barang.getBarcode_2()) {
                this.barcodeCmb2.addItem(tempBarcode.getBarcode());
            }
            for (Barcode tempBarcode : this.barang.getBarcode_3()) {
                this.barcodeCmb3.addItem(tempBarcode.getBarcode());
            }
            if (this.karyawan.getRole().equals("DIREKTUR") || this.karyawan.getRole().equals("MANAGER")) {
                List<RBarangSupplier> rbses = this.rBarangSupplierService.readRBSByBarangId(this.kodeBarangField.getText());
                for (RBarangSupplier rbs : rbses) {
                    String supId = rbs.getSupplierId();
                    Supplier supp = this.supplierService.getSupplier(supId);
                    this.supplierCmb.addItem(supId + "-" + supp.getNama());
                }
            }
            if ((parentBarang = this.parentBarangService.getParentBarang(this.barang.getParentId())) != null) {
                this.parentField.setText(this.barang.getParentId());
                this.parentLbl.setText(parentBarang.getNama());
            }
            this.namaBarangField.requestFocus();
        }
    }

    private void saveSupplier() {
        for (String suppId : this.deleteSupplier) {
            this.rbss.deleteRBSByBarangIdSupplierId(this.barang.getId(), suppId);
        }
        int size = this.supplierCmb.getItemCount();
        for (int a = 0; a < size; ++a) {
            String idSup = this.supplierCmb.getItemAt(a).toString().split("-")[0];
            if (this.rbss.isRBSHasCreated(this.barang.getId(), idSup)) continue;
            RBarangSupplier rbs = new RBarangSupplier();
            rbs.setBarangId(this.barang.getId());
            rbs.setSupplierId(idSup.split("-")[0]);
            this.rBarangSupplierService.createRBarangSupplier(rbs);
        }
    }

    private void saveBarcode() {
        boolean resulto2oBarcode = false;
        ArrayList<Barcode> listBarcode = new ArrayList<Barcode>();
        int size1 = this.barcodeCmb1.getItemCount();
        for (int a = 0; a < size1; ++a) {
            String barcode = this.barcodeCmb1.getItemAt(a).toString();
            if (barcode.equals("")) continue;
            Barcode barcodeData = new Barcode();
            barcodeData.setM_barang_id(this.kodeBarangField.getText());
            barcodeData.setSatuan(this.konversi1Field.getText());
            barcodeData.setBarcode(barcode);
            listBarcode.add(barcodeData);
        }
        ArrayList listBarcode2 = new ArrayList();
        int size2 = this.barcodeCmb2.getItemCount();
        for (int a = 0; a < size2; ++a) {
            String barcode = this.barcodeCmb2.getItemAt(a).toString();
            if (barcode.equals("")) continue;
            Barcode barcodeData = new Barcode();
            barcodeData.setM_barang_id(this.kodeBarangField.getText());
            barcodeData.setSatuan(this.konversi2Field.getText());
            barcodeData.setBarcode(barcode);
            listBarcode.add(barcodeData);
        }
        ArrayList listBarcode3 = new ArrayList();
        int size3 = this.barcodeCmb3.getItemCount();
        for (int a = 0; a < size3; ++a) {
            String barcode = this.barcodeCmb3.getItemAt(a).toString();
            if (barcode.equals("")) continue;
            Barcode barcodeData = new Barcode();
            barcodeData.setM_barang_id(this.kodeBarangField.getText());
            barcodeData.setSatuan(this.konversi3Field.getText());
            barcodeData.setBarcode(barcode);
            listBarcode.add(barcodeData);
        }
        ArrayList<List<Barcode>> barcodeToPush = new ArrayList<List<Barcode>>();
        barcodeToPush.add(listBarcode);
        barcodeToPush.add(this.deleteBarcode);
        try {
            String hasil = this.comm.post("/apis/o2o/barcode/insert", this.mapper.writeValueAsString(barcodeToPush));
            JsonResponses response = (JsonResponses)this.mapper.readValue(hasil, JsonResponses.class);
            System.out.println(response.getStatus());
            if (response.getStatus().equals("success") && response.getError().equals("null")) {
                resulto2oBarcode = true;
            }
        }
        catch (JsonProcessingException ex) {
            Logger.getLogger(TabelBarangMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(TabelBarangMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (resulto2oBarcode) {
            String newBarcode;
            Barcode barcodeData;
            int a;
            for (Barcode barcode : this.deleteBarcode) {
                this.barcodeService.deleteBarcode(barcode, this.kodeBarangField.getText());
            }
            for (a = 0; a < size1; ++a) {
                String string = this.barcodeCmb1.getItemAt(a).toString();
                if (!this.barcodeService.isBarcodeHasCreated(string)) {
                    barcodeData = new Barcode();
                    barcodeData.setM_barang_id(this.kodeBarangField.getText());
                    barcodeData.setSatuan(this.konversi1Field.getText());
                    barcodeData.setBarcode(string);
                    barcodeData.setCreated(new Date());
                    barcodeData.setUpdated(new Date());
                    this.barcodeService.createBarcode(barcodeData);
                    newBarcode = Integer.parseInt(this.konstantaService.getConstantValue("barcode1")) + 1 + "";
                    if (!string.equals(newBarcode)) continue;
                    this.konstantaService.updateConstant("barcode1", newBarcode);
                    continue;
                }
                barcodeData = this.barcodeService.getBarcode(string);
                barcodeData.setSatuan(this.konversi1Field.getText());
                this.barcodeService.updateBarcode(barcodeData);
            }
            for (a = 0; a < size2; ++a) {
                String string = this.barcodeCmb2.getItemAt(a).toString();
                if (!this.barcodeService.isBarcodeHasCreated(string)) {
                    barcodeData = new Barcode();
                    barcodeData.setM_barang_id(this.kodeBarangField.getText());
                    barcodeData.setSatuan(this.konversi2Field.getText());
                    barcodeData.setBarcode(string);
                    barcodeData.setCreated(new Date());
                    barcodeData.setUpdated(new Date());
                    this.barcodeService.createBarcode(barcodeData);
                    newBarcode = Integer.parseInt(this.konstantaService.getConstantValue("barcode2")) + 1 + "";
                    if (!string.equals(newBarcode)) continue;
                    this.konstantaService.updateConstant("barcode2", newBarcode);
                    continue;
                }
                barcodeData = this.barcodeService.getBarcode(string);
                barcodeData.setSatuan(this.konversi2Field.getText());
                this.barcodeService.updateBarcode(barcodeData);
            }
            for (a = 0; a < size3; ++a) {
                String string = this.barcodeCmb3.getItemAt(a).toString();
                if (!this.barcodeService.isBarcodeHasCreated(string)) {
                    barcodeData = new Barcode();
                    barcodeData.setM_barang_id(this.kodeBarangField.getText());
                    barcodeData.setSatuan(this.konversi3Field.getText());
                    barcodeData.setBarcode(string);
                    barcodeData.setCreated(new Date());
                    barcodeData.setUpdated(new Date());
                    this.barcodeService.createBarcode(barcodeData);
                    newBarcode = Integer.parseInt(this.konstantaService.getConstantValue("barcode3")) + 1 + "";
                    if (!string.equals(newBarcode)) continue;
                    this.konstantaService.updateConstant("barcode3", newBarcode);
                    continue;
                }
                barcodeData = this.barcodeService.getBarcode(string);
                barcodeData.setSatuan(this.konversi3Field.getText());
                this.barcodeService.updateBarcode(barcodeData);
            }
        }
    }

    private void saveBarang() {
        boolean hppupdate = false;
        if (this.action.equals("create")) {
            this.barang = new Barang();
            this.barang.setId(this.kodeBarangField.getText());
            this.barang.setStokToko(0.0f);
            this.barang.setStokGudang(0.0f);
        }
        this.barang.setNama(this.namaBarangField.getText());
        this.barang.setJenis(this.jenisField.getText());
        this.barang.setSatuan1(this.konversi1Field.getText());
        this.barang.setSatuan2(this.konversi2Field.getText());
        this.barang.setSatuan3(this.konversi3Field.getText());
        this.barang.setRakIdToko(this.rakTokoField.getText());
        this.barang.setTag(this.tagField.getText());
        this.barang.setParentId(this.parentField.getText());
        if (!this.hargaJualField1.getText().equals("")) {
            this.barang.setHargaJual1(Long.parseLong(this.hargaJualField1.getValue() + ""));
        }
        this.barang.setKonversi21(Integer.parseInt(this.konversi7Field.getText()));
        if (!this.hargaJualField2.getText().equals("")) {
            this.barang.setHargaJual2(Long.parseLong(this.hargaJualField2.getValue() + ""));
        }
        this.barang.setKonversi32(Integer.parseInt(this.konversi8Field.getText()));
        if (this.hargaJualField3.getValue() != null) {
            this.barang.setHargaJual3(Long.parseLong(this.hargaJualField3.getValue() + ""));
        }
        this.barang.setHpp(Long.parseLong(this.hppField.getValue() + ""));
        this.barang.setMinPembelian(Float.parseFloat(this.minField.getText()));
        this.barang.setMinPersen(Float.parseFloat(this.discField.getText()));
        float reorderValue = !this.reorderField.getText().equals("") ? Float.parseFloat(this.reorderField.getText()) : 0.0f;
        this.barang.setReorderPoint(reorderValue);
        this.barang.setBatasAtasStock(Float.parseFloat(this.batasStockField.getText()));
        this.barang.setUpdated(new Date());
        this.barang.setIdCabang(Integer.parseInt(this.konstantaService.getConstantValue("idCabang")));
        int result = 0;
        boolean resulto2oBarang = false;
        try {
            System.out.println(this.barang.toString());
            String hasil = this.comm.post("/apis/o2o/barang/insert", this.mapper.writeValueAsString((Object)this.barang));
            JsonResponses response = (JsonResponses)this.mapper.readValue(hasil, JsonResponses.class);
            System.out.println(response.getStatus());
            if (response.getStatus().equals("success") && response.getError().equals("null")) {
                resulto2oBarang = true;
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
        if (this.action.equals("create")) {
            this.barang.setCreated(new Date());
            if (resulto2oBarang) {
                result = this.barangService.createBarang(this.barang);
            } else {
                System.out.println("Failed");
            }
        } else if (this.action.equals("update")) {
            if (resulto2oBarang) {
                result = this.hppBarang != this.barang.getHpp() ? this.barangService.updateBarang2Hpp(this.barang, this.kodeBarangField.getText()) : this.barangService.updateBarang2(this.barang, this.kodeBarangField.getText());
            } else {
                System.out.println("Failed");
            }
        }
        if (result == 1) {
            HistoryHarga historyHarga;
            this.saveBarcode();
            this.saveSupplier();
            if (Long.parseLong(this.hargaJualField1.getValue() + "") != this.historyHarga1) {
                historyHarga = new HistoryHarga();
                historyHarga.setBarangId(this.barang.getId());
                historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                historyHarga.setSatuan(this.barang.getSatuan1());
                historyHarga.setHargaBaru(this.barang.getHargaJual1());
                historyHarga.setHargaLama(this.historyHarga1);
                historyHarga.setTanggal(new Date());
                historyHarga.setCreated(new Date());
                this.historyHargaService.createHistoryHarga(historyHarga);
            }
            if (Long.parseLong(this.hargaJualField2.getValue() + "") != this.historyHarga2) {
                historyHarga = new HistoryHarga();
                historyHarga.setBarangId(this.barang.getId());
                historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                historyHarga.setSatuan(this.barang.getSatuan2());
                historyHarga.setHargaBaru(this.barang.getHargaJual2());
                historyHarga.setHargaLama(this.historyHarga2);
                historyHarga.setTanggal(new Date());
                historyHarga.setCreated(new Date());
                int n = this.historyHargaService.createHistoryHarga(historyHarga);
            }
            if (Long.parseLong(this.hargaJualField3.getValue() + "") != this.historyHarga3) {
                historyHarga = new HistoryHarga();
                historyHarga.setBarangId(this.barang.getId());
                historyHarga.setKaryawanId(this.helper.getKaryawan().getId());
                historyHarga.setSatuan(this.barang.getSatuan3());
                historyHarga.setHargaBaru(this.barang.getHargaJual3());
                historyHarga.setHargaLama(this.historyHarga3);
                historyHarga.setTanggal(new Date());
                historyHarga.setCreated(new Date());
                this.historyHargaService.createHistoryHarga(historyHarga);
            }
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
            this.escapeClick();
            this.setDefault();
            this.kodeBarangField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
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
            long hargaBeli = this.helper.getHargaBeliTerakhirSatuanTerkecil(this.barang.getId());
            this.saveBarang();
            this.saveSupplier();
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
                this.loadKodeBarang();
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
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.jenisField.getText().equals("")) {
                this.sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
                this.sd.addSearchDialogListener(new searchJenisDialogEventHandler());
                int w = this.sd.getWidth();
                int h = this.sd.getHeight();
                this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                this.sd.setVisible(true);
            } else {
                Jenis jenis = this.jenisService.getJenis(this.jenisField.getText());
                if (jenis == null) {
                    JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
                } else {
                    this.supplierCmb.requestFocus();
                }
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
            this.stokTokoLbl1.setText(this.konversi1Field.getText());
            this.minStokLbl.setText(this.konversi1Field.getText());
            this.stokTokoField1.setEnabled(true);
            this.hargaJualField1.requestFocus();
            this.hargaJualField1.setText(this.hargaJualField1.getText().replace(".", ""));
            this.hargaJualField1.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.supplierCmb.requestFocus();
        }
    }

    private void konversi2FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (!this.konversi2Field.getText().equals("")) {
                this.satuan1.setText(this.konversi2Field.getText());
                this.satuan4.setText(this.konversi2Field.getText());
                this.stokTokoLbl2.setText(this.konversi2Field.getText());
                this.konversi7Field.setEnabled(true);
                this.stokTokoField2.setEnabled(true);
            }
            this.hargaJualField2.requestFocus();
            this.hargaJualField2.setText(this.hargaJualField2.getText().replace(".", ""));
            this.hargaJualField2.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.barcodeCmb1.requestFocus();
        }
    }

    private void konversi3FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (!this.konversi3Field.getText().equals("")) {
                this.satuan2.setText(this.konversi3Field.getText());
                this.stokTokoLbl3.setText(this.konversi3Field.getText());
                this.konversi8Field.setEnabled(true);
                this.stokTokoField3.setEnabled(true);
            }
            this.hargaJualField3.requestFocus();
            this.hargaJualField3.setText(this.hargaJualField3.getText().replace(".", ""));
            this.hargaJualField3.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.barcodeCmb2.requestFocus();
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
            this.barcodeCmb3.requestFocus();
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
        } else if (evt.getKeyCode() == 10) {
            boolean konversi = true;
            if (!this.konversi2Field.getText().equals("") && (this.konversi7Field.getText().equals("") || this.konversi7Field.getText().equals("0"))) {
                konversi = false;
            }
            if (!this.konversi3Field.getText().equals("") && (this.konversi8Field.getText().equals("") || this.konversi8Field.getText().equals("0"))) {
                konversi = false;
            }
            if (konversi) {
                this.saveBarang();
            } else {
                JOptionPane.showMessageDialog(null, "Isi Konversi Terlebih Dahulu");
            }
        } else if (evt.getKeyCode() == 39 || evt.getKeyCode() == 40) {
            this.deleteButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.tagField.requestFocus();
            this.tagField.selectAll();
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

    private void reorderFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.helper.isFloat(this.reorderField.getText())) {
                this.reorderField.setBackground(Color.white);
                this.tagField.requestFocus();
                this.tagField.selectAll();
            } else {
                this.reorderField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.batasStockField.requestFocus();
            this.batasStockField.selectAll();
        }
    }

    private void hargaJualField1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.barcodeCmb1.requestFocus();
            this.minResultField.setValue(Long.parseLong(this.hargaJualField1.getText()));
        } else if (evt.getKeyCode() == 38) {
            this.konversi1Field.requestFocus();
            this.konversi1Field.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void hargaJualField3KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.barcodeCmb3.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.konversi3Field.requestFocus();
            this.konversi3Field.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void hargaJualField2KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.barcodeCmb2.requestFocus();
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

    private void barcodeCmb1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            this.konversi2Field.requestFocus();
            this.konversi2Field.selectAll();
        } else if (evt.getKeyCode() == 113) {
            BarcodeInputDialog barcodeInputDialog = new BarcodeInputDialog(null, true, 1);
            barcodeInputDialog.addBarcodeInputDialogListener(new barcodeInputDialogEventHandler1());
            int w = barcodeInputDialog.getWidth();
            int h = barcodeInputDialog.getHeight();
            barcodeInputDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            barcodeInputDialog.setVisible(true);
        } else if (evt.getKeyCode() == 38) {
            this.hargaJualField1.requestFocus();
            this.hargaJualField1.setText(this.hargaJualField1.getText().replace(".", ""));
            this.hargaJualField1.selectAll();
        } else if (evt.getKeyCode() == 127) {
            Barcode barcode = new Barcode();
            barcode.setBarcode(this.barcodeCmb1.getSelectedItem().toString());
            this.deleteBarcode.add(barcode);
            this.barcodeCmb1.removeItem(this.barcodeCmb1.getSelectedItem());
        }
    }

    private void barcodeCmb2KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            this.konversi3Field.requestFocus();
            this.konversi3Field.selectAll();
        } else if (evt.getKeyCode() == 113) {
            BarcodeInputDialog barcodeInputDialog = new BarcodeInputDialog(null, true, 2);
            barcodeInputDialog.addBarcodeInputDialogListener(new barcodeInputDialogEventHandler2());
            int w = barcodeInputDialog.getWidth();
            int h = barcodeInputDialog.getHeight();
            barcodeInputDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            barcodeInputDialog.setVisible(true);
        } else if (evt.getKeyCode() == 38) {
            this.hargaJualField2.requestFocus();
            this.hargaJualField2.setText(this.hargaJualField2.getText().replace(".", ""));
            this.hargaJualField2.selectAll();
        } else if (evt.getKeyCode() == 127) {
            Barcode barcode = new Barcode();
            barcode.setBarcode(this.barcodeCmb2.getSelectedItem().toString());
            this.deleteBarcode.add(barcode);
            this.barcodeCmb2.removeItem(this.barcodeCmb2.getSelectedItem());
        }
    }

    private void barcodeCmb3KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            this.konversi7Field.requestFocus();
            this.konversi7Field.selectAll();
        } else if (evt.getKeyCode() == 113) {
            BarcodeInputDialog barcodeInputDialog = new BarcodeInputDialog(null, true, 3);
            barcodeInputDialog.addBarcodeInputDialogListener(new barcodeInputDialogEventHandler3());
            int w = barcodeInputDialog.getWidth();
            int h = barcodeInputDialog.getHeight();
            barcodeInputDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            barcodeInputDialog.setVisible(true);
        } else if (evt.getKeyCode() == 38) {
            this.hargaJualField3.requestFocus();
            this.hargaJualField3.setText(this.hargaJualField3.getText().replace(".", ""));
            this.hargaJualField3.selectAll();
        } else if (evt.getKeyCode() == 127) {
            Barcode barcode = new Barcode();
            barcode.setBarcode(this.barcodeCmb3.getSelectedItem().toString());
            this.deleteBarcode.add(barcode);
            this.barcodeCmb3.removeItem(this.barcodeCmb3.getSelectedItem());
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
                Logger.getLogger(TabelBarangMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex) {
                Logger.getLogger(TabelBarangMaster.class.getName()).log(Level.SEVERE, null, ex);
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

    private void rakTokoFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.tagField.requestFocus();
            this.tagField.selectAll();
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
                this.batasStockField.requestFocus();
                this.batasStockField.selectAll();
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

    private void batasStockFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.helper.isFloat(this.batasStockField.getText())) {
                this.batasStockField.setBackground(Color.white);
                this.reorderField.requestFocus();
                this.reorderField.selectAll();
            } else {
                this.batasStockField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 38) {
            this.discField.requestFocus();
            this.discField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void hppFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.konversi1Field.requestFocus();
            this.konversi1Field.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void parentFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchParentDialogEventHandler());
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
                this.loadParentBarang(this.kodeBarangField.getText());
            }
        }
    }

    private void supplierCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
            sd.addSearchDialogListener(new searchSupplierDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 127) {
            this.deleteSupplier.add(this.supplierCmb.getSelectedItem().toString().split("-")[0]);
            this.supplierCmb.removeItem(this.supplierCmb.getSelectedItem());
        } else if (evt.getKeyCode() == 10) {
            this.konversi1Field.requestFocus();
            this.konversi1Field.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.jenisField.requestFocus();
            this.jenisField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private class searchPanelSupplierEventHandler
    implements SearchPanelListener {
        private searchPanelSupplierEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            Supplier supplier = TabelBarangMaster.this.supplierService.getSupplier(chooseValue);
            String value = chooseValue + "-" + supplier.getNama();
            TabelBarangMaster.this.supplierCmb.removeItem(value);
            TabelBarangMaster.this.supplierCmb.addItem(value);
        }
    }

    class searchSupplierDialogEventHandler
    implements SearchDialogListener {
        searchSupplierDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SUPPLIER", "NAMA SUPPLIER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Supplier> suppliers = TabelBarangMaster.this.supplierService.getSuppliers(searchWord);
            for (Supplier supplier : suppliers) {
                Object[] temp = new String[]{supplier.getId(), supplier.getNama(), supplier.getAlamat(), supplier.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Supplier");
            sp.addSearchPanelListener(new searchPanelSupplierEventHandler());
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
            TabelBarangMaster.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            TabelBarangMaster.this.canGoThrough = false;
        }
    }

    private class barcodeInputDialogEventHandler3
    implements BarcodeInputDialogListener {
        private barcodeInputDialogEventHandler3() {
        }

        @Override
        public void okClick(String barcode) {
            TabelBarangMaster.this.barcodeCmb3.removeItem(barcode);
            TabelBarangMaster.this.barcodeCmb3.addItem(barcode);
        }
    }

    private class barcodeInputDialogEventHandler2
    implements BarcodeInputDialogListener {
        private barcodeInputDialogEventHandler2() {
        }

        @Override
        public void okClick(String barcode) {
            TabelBarangMaster.this.barcodeCmb2.removeItem(barcode);
            TabelBarangMaster.this.barcodeCmb2.addItem(barcode);
        }
    }

    private class barcodeInputDialogEventHandler1
    implements BarcodeInputDialogListener {
        private barcodeInputDialogEventHandler1() {
        }

        @Override
        public void okClick(String barcode) {
            TabelBarangMaster.this.barcodeCmb1.removeItem(barcode);
            TabelBarangMaster.this.barcodeCmb1.addItem(barcode);
        }
    }

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            TabelBarangMaster.this.jenisField.setText(chooseValue);
            TabelBarangMaster.this.konversi1Field.requestFocus();
            TabelBarangMaster.this.konversi1Field.selectAll();
        }
    }

    private class searchPanelParentEventHandler
    implements SearchPanelListener {
        private searchPanelParentEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            TabelBarangMaster.this.parentField.setText(chooseValue);
            TabelBarangMaster.this.loadParentBarang(chooseValue);
        }
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            TabelBarangMaster.this.kodeBarangField.setText(chooseValue);
            TabelBarangMaster.this.loadKodeBarang();
        }
    }

    private class searchParentDialogEventHandler
    implements SearchDialogListener {
        private searchParentDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE PARENT", "NAMA BARANG"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<ParentBarang> parentBarangs = TabelBarangMaster.this.parentBarangService.getParents(searchWord);
            for (ParentBarang parentBarang : parentBarangs) {
                Object[] temp = new String[4];
                temp[0] = parentBarang.getId();
                temp[1] = parentBarang.getNama();
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Parent Barang");
            sp.addSearchPanelListener(new searchPanelParentEventHandler());
            sp.setVisible(true);
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
            List<Barang> barangs = TabelBarangMaster.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
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
            List<Jenis> jenisses = TabelBarangMaster.this.jenisService.getJenisses(searchWord);
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

