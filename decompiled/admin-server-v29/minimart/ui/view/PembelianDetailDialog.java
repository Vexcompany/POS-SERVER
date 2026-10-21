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
import java.text.NumberFormat;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
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
import minimart.engine.model.Pembelian;
import minimart.engine.model.PembelianDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.PembelianDetailDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PembelianDetailDialog
extends JDialog {
    private JFormattedTextField beliTerakhirField;
    private JLabel beliTerakhirLbl;
    private JLabel bonusLbl;
    private JLabel companyLbl;
    private JButton deleteButton;
    private JTextField discount1;
    private JFormattedTextField discount1Field;
    private JTextField discount2;
    private JFormattedTextField discount2Field;
    private JTextField discount3;
    private JFormattedTextField discount3Field;
    private JFormattedTextField discount4;
    private JButton exitButton;
    private JFormattedTextField hargaSatuanField;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel18;
    private JLabel jLabel19;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JSeparator jSeparator1;
    private JFormattedTextField jualField1;
    private JFormattedTextField jualField2;
    private JFormattedTextField jualField3;
    private JTextField kodeBarangField;
    private JTextField konversiField1;
    private JTextField konversiField2;
    private JLabel konversiLbl1;
    private JLabel konversiLbl2;
    private JLabel konversiLbl3;
    private JLabel konversiLbl4;
    private JTextField namaBarangField;
    private JFormattedTextField nettoHargaField;
    private JTextField quantity1;
    private JTextField quantity2;
    private JTextField quantity3;
    private JLabel satuan1;
    private JLabel satuan2;
    private JLabel satuan3;
    private JTextField satuan4;
    private JLabel satuanLbl;
    private JButton saveButton;
    private JFormattedTextField totalHargaField;
    private int screenWidth;
    private int screenHeight;
    private SearchDialog sd;
    private BarangService barangService = new BarangService();
    private Barang barang;
    private JTextField tempField;
    private JTextField tempField2;
    private Helper helper = Helper.getInstance();
    private PembelianDetailDialogListener pembelianDetailDialogListener;
    private DefaultTableModel transactionTableModel;
    private PembelianService pembelianService = new PembelianService();
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    private SupplierService supplierService = new SupplierService();

    public PembelianDetailDialog(Frame parent, boolean modal, DefaultTableModel transactionTable) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.transactionTableModel = transactionTable;
        this.bonusLbl.setVisible(false);
    }

    protected void loadKodeBarang(String kodeBarang) {
        this.setDefaultData();
        this.kodeBarangField.setText(kodeBarang);
        this.barang = this.barangService.getBarang(kodeBarang);
        if (this.barang != null) {
            this.namaBarangField.setText(this.barang.getNama());
            this.satuan1.setText(this.barang.getSatuan1());
            this.satuan2.setText(this.barang.getSatuan2());
            this.satuan3.setText(this.barang.getSatuan3());
            this.jualField1.setValue(this.barang.getHargaJual1());
            this.jualField2.setValue(this.barang.getHargaJual2());
            this.jualField3.setValue(this.barang.getHargaJual3());
            this.konversiLbl1.setText(this.barang.getSatuan2());
            this.konversiLbl2.setText(this.barang.getSatuan1());
            this.konversiLbl3.setText(this.barang.getSatuan3());
            this.konversiLbl4.setText(this.barang.getSatuan2());
            this.konversiField1.setText(this.barang.getKonversi21() + "");
            this.konversiField2.setText(this.barang.getKonversi32() + "");
            this.beliTerakhirField.setValue(0);
            this.companyLbl.setText("-");
            this.quantity1.setBackground(Color.white);
            this.quantity2.setBackground(Color.white);
            this.quantity3.setBackground(Color.white);
            PembelianDetail pembelianDetail = this.pembelianDetailService.getPembelianTerakhir(this.barang.getId());
            if (pembelianDetail != null) {
                Pembelian pembelian = this.pembelianService.getPembelian(pembelianDetail.getPembelianId());
                if (pembelianDetail != null) {
                    this.beliTerakhirField.setValue(this.helper.getHargaBeliTerakhirSatuanTerkecil(this.barang.getId()));
                    this.satuanLbl.setText("/ " + this.barang.getSatuan1());
                    this.companyLbl.setText(this.supplierService.getSupplier(pembelian.getSupplierId()).getNama() + " / " + pembelian.getTanggal());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
    }

    private void setDefaultData() {
        this.namaBarangField.setText("");
        this.quantity1.setText("0");
        this.quantity2.setText("0");
        this.quantity3.setText("0");
        this.hargaSatuanField.setValue(0);
        this.totalHargaField.setValue(0);
        this.discount1.setText("0");
        this.discount2.setText("0");
        this.discount3.setText("0");
        this.discount1Field.setValue(0);
        this.discount2Field.setValue(0);
        this.discount3Field.setValue(0);
        this.discount4.setValue(0);
        this.totalHargaField.setValue(0);
        this.nettoHargaField.setValue(0);
        this.jualField1.setValue(0);
        this.jualField2.setValue(0);
        this.jualField3.setValue(0);
        this.jualField1.setBackground(Color.white);
        this.jualField2.setBackground(Color.white);
        this.jualField3.setBackground(Color.white);
    }

    protected void escapeClick() {
        this.kodeBarangField.requestFocus();
        this.kodeBarangField.selectAll();
    }

    private void saveClick() {
        Object[] rowValue = new Object[]{this.barang.getId(), this.barang.getNama(), this.tempField.getText(), this.satuan4.getText(), this.hargaSatuanField.getValue(), this.totalHargaField.getValue(), Float.parseFloat(this.discount4.getValue().toString()) > 0.0f ? this.discount4.getValue() : Long.valueOf(Long.parseLong(this.totalHargaField.getText().replace(".", "")) - Long.parseLong(this.nettoHargaField.getText().replace(".", ""))), Long.parseLong(this.nettoHargaField.getText().replace(".", ""))};
        this.pembelianDetailDialogListener.saveClick(rowValue);
        this.escapeClick();
    }

    private void initComponents() {
        this.jLabel19 = new JLabel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.kodeBarangField = new JTextField();
        this.namaBarangField = new JTextField();
        this.jSeparator1 = new JSeparator();
        this.beliTerakhirLbl = new JLabel();
        this.jLabel8 = new JLabel();
        this.satuan4 = new JTextField();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel9 = new JLabel();
        this.discount4 = new JFormattedTextField();
        this.jLabel10 = new JLabel();
        this.nettoHargaField = new JFormattedTextField();
        this.saveButton = new JButton();
        this.deleteButton = new JButton();
        this.exitButton = new JButton();
        this.discount1Field = new JFormattedTextField();
        this.jLabel7 = new JLabel();
        this.satuan1 = new JLabel();
        this.satuan2 = new JLabel();
        this.satuan3 = new JLabel();
        this.jualField1 = new JFormattedTextField();
        this.jualField2 = new JFormattedTextField();
        this.jualField3 = new JFormattedTextField();
        this.quantity1 = new JTextField();
        this.quantity2 = new JTextField();
        this.quantity3 = new JTextField();
        this.hargaSatuanField = new JFormattedTextField();
        this.totalHargaField = new JFormattedTextField();
        this.discount2Field = new JFormattedTextField();
        this.discount3Field = new JFormattedTextField();
        this.jLabel12 = new JLabel();
        this.jLabel13 = new JLabel();
        this.discount1 = new JTextField();
        this.discount2 = new JTextField();
        this.jLabel14 = new JLabel();
        this.discount3 = new JTextField();
        this.jLabel15 = new JLabel();
        this.jLabel11 = new JLabel();
        this.jLabel16 = new JLabel();
        this.jLabel17 = new JLabel();
        this.konversiLbl1 = new JLabel();
        this.konversiLbl3 = new JLabel();
        this.jLabel20 = new JLabel();
        this.jLabel21 = new JLabel();
        this.konversiField1 = new JTextField();
        this.konversiField2 = new JTextField();
        this.konversiLbl2 = new JLabel();
        this.konversiLbl4 = new JLabel();
        this.jLabel18 = new JLabel();
        this.beliTerakhirField = new JFormattedTextField();
        this.companyLbl = new JLabel();
        this.satuanLbl = new JLabel();
        this.bonusLbl = new JLabel();
        this.jLabel19.setText("jLabel19");
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.jLabel2.setText("Nama Barang");
        this.jLabel3.setText("Quantity");
        this.kodeBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.kodeBarangFieldKeyPressed(evt);
            }
        });
        this.namaBarangField.setEditable(false);
        this.namaBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.namaBarangFieldKeyPressed(evt);
            }
        });
        this.jSeparator1.setOrientation(1);
        this.beliTerakhirLbl.setText("Jual :");
        this.jLabel8.setText("Harga /");
        this.satuan4.setEditable(false);
        this.jLabel4.setText("Total");
        this.jLabel5.setText("Disc 1");
        this.jLabel6.setText("Discount 4");
        this.jLabel9.setText("%");
        this.discount4.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.discount4.setText("0");
        this.discount4.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.discount4KeyPressed(evt);
            }
        });
        this.jLabel10.setText("Netto");
        this.nettoHargaField.setEditable(false);
        this.nettoHargaField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PembelianDetailDialog.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.saveButtonKeyPressed(evt);
            }
        });
        this.deleteButton.setText("Delete");
        this.deleteButton.setPreferredSize(new Dimension(73, 23));
        this.deleteButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PembelianDetailDialog.this.deleteButtonActionPerformed(evt);
            }
        });
        this.deleteButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.deleteButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PembelianDetailDialog.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.exitButtonKeyPressed(evt);
            }
        });
        this.discount1Field.setEditable(false);
        this.discount1Field.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        this.discount1Field.setText("0");
        this.jLabel7.setText("Cari : F2");
        this.satuan1.setPreferredSize(new Dimension(20, 14));
        this.satuan2.setPreferredSize(new Dimension(20, 14));
        this.satuan3.setPreferredSize(new Dimension(20, 14));
        this.jualField1.setEditable(false);
        this.jualField1.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.jualField2.setEditable(false);
        this.jualField2.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.jualField3.setEditable(false);
        this.jualField3.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.quantity1.setText("0");
        this.quantity1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.quantity1KeyPressed(evt);
            }
        });
        this.quantity2.setText("0");
        this.quantity2.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.quantity2KeyPressed(evt);
            }
        });
        this.quantity3.setText("0");
        this.quantity3.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.quantity3KeyPressed(evt);
            }
        });
        this.hargaSatuanField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.hargaSatuanField.setText("0");
        this.hargaSatuanField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.hargaSatuanFieldKeyPressed(evt);
            }
        });
        this.totalHargaField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.totalHargaField.setText("0");
        this.discount2Field.setEditable(false);
        this.discount2Field.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        this.discount2Field.setText("0");
        this.discount3Field.setEditable(false);
        this.discount3Field.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        this.discount3Field.setText("0");
        this.jLabel12.setText("Disc 2");
        this.jLabel13.setText("Disc 3");
        this.discount1.setText("0");
        this.discount1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.discount1KeyPressed(evt);
            }
        });
        this.discount2.setText("0");
        this.discount2.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.discount2KeyPressed(evt);
            }
        });
        this.jLabel14.setText("%");
        this.discount3.setText("0");
        this.discount3.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianDetailDialog.this.discount3KeyPressed(evt);
            }
        });
        this.jLabel15.setText("%");
        this.jLabel11.setText("Konversi:");
        this.jLabel16.setText("1");
        this.jLabel17.setText("1");
        this.konversiLbl1.setText(" ");
        this.konversiLbl3.setText(" ");
        this.jLabel20.setText("=");
        this.jLabel21.setText("=");
        this.konversiField1.setEditable(false);
        this.konversiField2.setEditable(false);
        this.konversiLbl2.setText(" ");
        this.konversiLbl4.setText(" ");
        this.jLabel18.setText("Beli Terakhir:");
        this.companyLbl.setText("-");
        this.satuanLbl.setText("-");
        this.bonusLbl.setFont(new Font("Tahoma", 1, 18));
        this.bonusLbl.setForeground(new Color(204, 0, 0));
        this.bonusLbl.setText("BONUS");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1).addComponent(this.jLabel3).addComponent(this.jLabel4).addGroup(layout.createSequentialGroup().addComponent(this.jLabel8).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.satuan4, -2, 41, -2)).addComponent(this.jLabel6).addComponent(this.jLabel10).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5).addComponent(this.jLabel12).addComponent(this.jLabel13)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.discount1, -1, 37, Short.MAX_VALUE).addComponent(this.discount2).addComponent(this.discount3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel9).addComponent(this.jLabel14).addComponent(this.jLabel15, GroupLayout.Alignment.LEADING)))).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.totalHargaField, GroupLayout.Alignment.LEADING).addComponent(this.hargaSatuanField, GroupLayout.Alignment.LEADING).addComponent(this.discount1Field, GroupLayout.Alignment.LEADING, -1, 119, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.quantity1, -1, 80, Short.MAX_VALUE).addComponent(this.quantity2).addComponent(this.quantity3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuan3, -1, -1, Short.MAX_VALUE).addComponent(this.satuan1, -1, -1, Short.MAX_VALUE).addComponent(this.satuan2, -2, 40, -2))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.discount4, -1, 119, Short.MAX_VALUE).addComponent(this.nettoHargaField, -1, 119, Short.MAX_VALUE).addComponent(this.discount3Field, GroupLayout.Alignment.TRAILING, -2, 118, -2).addComponent(this.discount2Field))).addGap(18, 18, 18).addComponent(this.jSeparator1, -2, 16, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.jualField2, -2, 152, -2)).addComponent(this.companyLbl, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.beliTerakhirField).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel17).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.konversiLbl3, -1, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel16).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.konversiLbl1, -1, -1, Short.MAX_VALUE)).addComponent(this.jLabel11)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel20).addComponent(this.jLabel21, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.konversiField1, -1, 51, Short.MAX_VALUE).addComponent(this.konversiField2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.konversiLbl4, -1, -1, Short.MAX_VALUE).addComponent(this.konversiLbl2, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(this.satuanLbl).addGap(0, 0, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.beliTerakhirLbl).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jualField1, -2, 152, -2)).addComponent(this.jualField3, GroupLayout.Alignment.TRAILING, -2, 152, -2)).addComponent(this.jLabel18).addComponent(this.bonusLbl, -2, 67, -2)).addGap(0, 0, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.deleteButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.namaBarangField).addGroup(layout.createSequentialGroup().addComponent(this.kodeBarangField, -1, 138, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel7)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeBarangField, -2, -1, -2).addComponent(this.jLabel7)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel3).addComponent(this.satuan1, -1, -1, Short.MAX_VALUE).addComponent(this.quantity1, GroupLayout.Alignment.TRAILING, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.quantity2).addComponent(this.satuan2, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.quantity3, -2, -1, -2).addComponent(this.satuan3, -2, 20, -2)).addGap(26, 26, 26).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.satuan4, -2, -1, -2).addComponent(this.jLabel8).addComponent(this.hargaSatuanField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.totalHargaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.jLabel9).addComponent(this.discount1Field, -2, -1, -2).addComponent(this.discount1, -2, -1, -2))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.beliTerakhirLbl).addComponent(this.jualField1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jualField2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jualField3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel11).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel16).addComponent(this.konversiLbl1).addComponent(this.jLabel20).addComponent(this.konversiField1, -2, -1, -2).addComponent(this.konversiLbl2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel17).addComponent(this.konversiLbl3).addComponent(this.jLabel21).addComponent(this.konversiField2, -2, -1, -2).addComponent(this.konversiLbl4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel18))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.discount2Field, -2, -1, -2).addComponent(this.jLabel12).addComponent(this.discount2, -2, -1, -2).addComponent(this.jLabel14)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.discount3Field, -2, -1, -2).addComponent(this.jLabel13).addComponent(this.discount3, -2, -1, -2).addComponent(this.jLabel15)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.discount4, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.jLabel10)).addGroup(layout.createSequentialGroup().addComponent(this.nettoHargaField, -2, -1, -2).addGap(0, 2, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.beliTerakhirField, -2, -1, -2).addComponent(this.satuanLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.companyLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.bonusLbl)))).addComponent(this.jSeparator1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.deleteButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addContainerGap()));
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
                this.loadKodeBarang(this.kodeBarangField.getText());
                if (this.barang != null) {
                    this.searchPembelian(this.kodeBarangField.getText());
                } else {
                    this.kodeBarangField.setText("");
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void namaBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.quantity1.requestFocus();
        }
    }

    private void quantity1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 40) {
            if (this.quantity1.getText().equals("") || Float.parseFloat(this.quantity1.getText()) == 0.0f) {
                this.quantity2.requestFocus();
                this.quantity2.selectAll();
                this.quantity1.setBackground(Color.white);
                this.quantity1.setText("0");
            } else if (!Helper.getInstance().isFloat(this.quantity1.getText())) {
                this.quantity1.setBackground(Color.red);
            } else {
                this.tempField = this.quantity1;
                this.satuan4.setText(this.barang.getSatuan1());
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.requestFocus();
                this.hargaSatuanField.selectAll();
                this.quantity1.setBackground(Color.white);
                this.quantity2.setText("0");
                this.quantity3.setText("0");
            }
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.escapeClick();
        }
    }

    private void quantity2KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 40) {
            if (this.quantity2.getText().equals("") || Float.parseFloat(this.quantity2.getText()) == 0.0f) {
                this.quantity3.requestFocus();
                this.quantity3.selectAll();
                this.quantity2.setBackground(Color.white);
                this.quantity2.setText("0");
            } else if (!Helper.getInstance().isFloat(this.quantity2.getText())) {
                this.quantity2.setBackground(Color.red);
            } else {
                this.tempField = this.quantity2;
                this.satuan4.setText(this.barang.getSatuan2());
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.requestFocus();
                this.hargaSatuanField.selectAll();
                this.quantity2.setBackground(Color.white);
                this.quantity1.setText("0");
                this.quantity3.setText("0");
            }
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.quantity1.requestFocus();
            this.quantity1.selectAll();
        }
    }

    private void quantity3KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 40) {
            if (this.quantity3.getText().equals("") || Float.parseFloat(this.quantity3.getText()) == 0.0f) {
                this.quantity3.setBackground(Color.white);
            } else if (!Helper.getInstance().isFloat(this.quantity3.getText())) {
                this.quantity3.setBackground(Color.red);
            } else {
                this.tempField = this.quantity3;
                this.satuan4.setText(this.barang.getSatuan3());
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.requestFocus();
                this.hargaSatuanField.selectAll();
                this.quantity3.setBackground(Color.white);
                this.quantity1.setText("0");
                this.quantity2.setText("0");
            }
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.quantity2.requestFocus();
            this.quantity2.selectAll();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (!(Helper.getInstance().isFloat(this.quantity1.getText()) && Helper.getInstance().isFloat(this.quantity2.getText()) && Helper.getInstance().isFloat(this.quantity3.getText()))) {
                JOptionPane.showMessageDialog(null, "Isi Data Dengan Benar");
            } else {
                this.saveClick();
            }
        } else if (evt.getKeyCode() == 39) {
            this.deleteButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.tempField2.selectAll();
            this.tempField2.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void deleteButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.pembelianDetailDialogListener.deleteClick(this.kodeBarangField.getText());
            this.escapeClick();
            this.setDefaultData();
            this.kodeBarangField.setText("");
        }
    }

    private void discount4KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            float priceAfterDisc = Float.parseFloat(this.totalHargaField.getValue().toString()) - Float.parseFloat(this.discount4.getText());
            this.nettoHargaField.setValue(Float.valueOf(priceAfterDisc));
            this.saveButton.requestFocus();
            this.tempField2 = this.discount4;
        } else if (evt.getKeyCode() == 38) {
            this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
            this.hargaSatuanField.requestFocus();
            this.hargaSatuanField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void hargaSatuanFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.helper.isInteger(this.hargaSatuanField.getText().toString())) {
                float totalHarga = Float.parseFloat(this.tempField.getText()) * Float.parseFloat(this.hargaSatuanField.getText());
                this.totalHargaField.setValue(Float.valueOf(totalHarga));
                this.discount1.setText(this.discount1.getText().replace(".", ""));
                this.discount1.requestFocus();
                this.discount1.selectAll();
                this.hargaSatuanField.setBackground(Color.white);
                long hargaSatuanTerkecil = 0L;
                if (this.satuan4.getText().equals(this.barang.getSatuan1())) {
                    hargaSatuanTerkecil = Long.parseLong(this.hargaSatuanField.getText().replace(".", ""));
                } else if (this.satuan4.getText().equals(this.barang.getSatuan2())) {
                    hargaSatuanTerkecil = Long.parseLong(this.hargaSatuanField.getText().replace(".", "")) / (long)this.barang.getKonversi21();
                } else if (this.satuan4.getText().equals(this.barang.getSatuan3())) {
                    hargaSatuanTerkecil = Long.parseLong(this.hargaSatuanField.getText().replace(".", "")) / (long)(this.barang.getKonversi21() * this.barang.getKonversi32());
                }
                if (hargaSatuanTerkecil >= this.barang.getHargaJual1() && !this.barang.getSatuan1().equals("")) {
                    this.jualField1.setBackground(Color.red);
                } else {
                    this.jualField1.setBackground(Color.white);
                }
                if (hargaSatuanTerkecil * (long)this.barang.getKonversi21() >= this.barang.getHargaJual2() && !this.barang.getSatuan2().equals("")) {
                    this.jualField2.setBackground(Color.red);
                } else {
                    this.jualField2.setBackground(Color.white);
                }
                if (hargaSatuanTerkecil * (long)this.barang.getKonversi21() * (long)this.barang.getKonversi32() >= this.barang.getHargaJual3() && !this.barang.getSatuan3().equals("")) {
                    this.jualField3.setBackground(Color.red);
                } else {
                    this.jualField3.setBackground(Color.white);
                }
                if (Long.parseLong(this.hargaSatuanField.getText().replace(".", "")) == 0L) {
                    this.bonusLbl.setVisible(true);
                } else {
                    this.bonusLbl.setVisible(false);
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

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 37) {
            this.deleteButton.requestFocus();
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void deleteButtonActionPerformed(ActionEvent evt) {
        this.pembelianDetailDialogListener.deleteClick(this.kodeBarangField.getText());
        this.escapeClick();
        this.setDefaultData();
        this.kodeBarangField.setText("");
    }

    private void discount1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            float discValue = Float.parseFloat(this.discount1.getText()) / 100.0f * Float.parseFloat(this.totalHargaField.getValue().toString());
            this.discount1Field.setValue(Float.valueOf(discValue));
            float priceAfterDisc = Float.parseFloat(this.totalHargaField.getValue().toString()) - discValue;
            this.nettoHargaField.setValue(Float.valueOf(priceAfterDisc));
            if (this.discount1.getText().equals("") || this.discount1.getText().equals("0")) {
                this.discount4.setText(this.discount4.getText().replace(".", ""));
                this.discount4.requestFocus();
                this.discount4.selectAll();
            } else {
                this.discount2.requestFocus();
                this.discount2.selectAll();
                this.tempField2 = this.discount1;
                this.discount4.setText("0");
            }
        } else if (evt.getKeyCode() == 38) {
            this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
            this.hargaSatuanField.selectAll();
            this.hargaSatuanField.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void discount2KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            float total = ((Float)this.totalHargaField.getValue()).floatValue() - ((Float)this.discount1Field.getValue()).floatValue();
            float discValue = Float.parseFloat(this.discount2.getText()) / 100.0f * Float.parseFloat(total + "");
            this.discount2Field.setValue(Float.valueOf(discValue));
            float priceAfterDisc = total - discValue;
            this.nettoHargaField.setValue(Float.valueOf(priceAfterDisc));
            if (this.discount2.getText().equals("") || this.discount2.getText().equals("0")) {
                this.saveButton.requestFocus();
                this.discount4.setText("0");
            } else {
                this.discount3.requestFocus();
                this.discount3.selectAll();
                this.tempField2 = this.discount2;
                this.discount4.setText("0");
            }
        } else if (evt.getKeyCode() == 38) {
            this.discount1.selectAll();
            this.discount1.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void discount3KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            float total = ((Float)this.totalHargaField.getValue()).floatValue() - ((Float)this.discount1Field.getValue()).floatValue() - ((Float)this.discount2Field.getValue()).floatValue();
            float discValue = Float.parseFloat(this.discount3.getText()) / 100.0f * Float.parseFloat(total + "");
            this.discount3Field.setValue(Float.valueOf(discValue));
            float priceAfterDisc = total - discValue;
            this.nettoHargaField.setValue(Float.valueOf(priceAfterDisc));
            if (this.discount3.getText().equals("") || this.discount3.getText().equals("0")) {
                this.saveButton.requestFocus();
                this.discount4.setText("0");
            } else {
                this.saveButton.requestFocus();
                this.tempField2 = this.discount3;
                this.discount4.setText("0");
            }
        } else if (evt.getKeyCode() == 38) {
            this.discount2.selectAll();
            this.discount2.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    public void addListener(PembelianDetailDialogListener listener) {
        this.pembelianDetailDialogListener = listener;
    }

    protected void loadPembelian(int row) {
        if (this.transactionTableModel.getValueAt(row, 3).equals(this.barang.getSatuan1())) {
            this.quantity1.setText(this.transactionTableModel.getValueAt(row, 2).toString());
            this.quantity1.requestFocus();
            this.quantity1.selectAll();
        } else if (this.transactionTableModel.getValueAt(row, 3).equals(this.barang.getSatuan2())) {
            this.quantity2.setText(this.transactionTableModel.getValueAt(row, 2).toString());
            this.quantity2.requestFocus();
            this.quantity2.selectAll();
        } else if (this.transactionTableModel.getValueAt(row, 3).equals(this.barang.getSatuan3())) {
            this.quantity3.setText(this.transactionTableModel.getValueAt(row, 2).toString());
            this.quantity3.requestFocus();
            this.quantity3.selectAll();
        }
        this.satuan4.setText(this.transactionTableModel.getValueAt(row, 3).toString());
        this.hargaSatuanField.setValue(this.transactionTableModel.getValueAt(row, 4));
        this.totalHargaField.setValue(this.transactionTableModel.getValueAt(row, 5));
        this.discount4.setValue(this.transactionTableModel.getValueAt(row, 6));
        this.nettoHargaField.setValue(this.transactionTableModel.getValueAt(row, 7));
    }

    private void searchPembelian(String idBarang) {
        boolean find = false;
        for (int a = 0; a < this.transactionTableModel.getRowCount(); ++a) {
            String barangId = this.transactionTableModel.getValueAt(a, 0).toString();
            if (!idBarang.equals(barangId)) continue;
            find = true;
            this.loadPembelian(a);
            break;
        }
        if (!find) {
            this.quantity1.requestFocus();
            this.quantity1.selectAll();
        }
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PembelianDetailDialog.this.loadKodeBarang(chooseValue);
            PembelianDetailDialog.this.searchPembelian(chooseValue);
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
            List<Barang> barangs = PembelianDetailDialog.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }
    }
}

