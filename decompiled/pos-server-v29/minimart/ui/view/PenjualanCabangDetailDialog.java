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
import minimart.engine.model.Barcode;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Pembelian;
import minimart.engine.model.PembelianDetail;
import minimart.engine.model.PromoTebusDetail;
import minimart.engine.model.PromoVoucherDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.JenisService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.PromoDiskonByJenisDetailService;
import minimart.engine.service.PromoDiskonByJenisService;
import minimart.engine.service.PromoHematDetailService;
import minimart.engine.service.PromoHematService;
import minimart.engine.service.PromoTebusDetailService;
import minimart.engine.service.PromoVoucherDetailService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.PenjualanDetailDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PenjualanCabangDetailDialog
extends JDialog {
    private JFormattedTextField beliTerakhirField;
    private JLabel beliTerakhirLbl;
    private JLabel beliTerakhirSatuanLbl;
    private JButton deleteButton;
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
    private JButton saveButton;
    private JFormattedTextField totalHargaField;
    private PenjualanDetailDialogListener listener;
    private SearchDialog sd;
    private BarangService barangService = new BarangService();
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
    private boolean canGoThrough;
    private String shortcut;
    private Karyawan karyawan;
    private boolean notaKhusus;
    private SupplierService supplierService = new SupplierService();
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    private PembelianService pembelianService = new PembelianService();
    private PromoHematService promoHematService = new PromoHematService();
    private PromoHematDetailService promoHematDetailService = new PromoHematDetailService();
    private PromoDiskonByJenisService promoDiskonByJenisService = new PromoDiskonByJenisService();
    private PromoDiskonByJenisDetailService promoDiskonByJenisDetailService = new PromoDiskonByJenisDetailService();
    private PromoTebusDetailService promoTebusDetailService = new PromoTebusDetailService();
    private PromoVoucherDetailService promoVoucherDetailService = new PromoVoucherDetailService();
    private long lastPrice = 0L;
    private BarcodeService barcodeService = new BarcodeService();
    private JenisService jenisService = new JenisService();
    private float marginCabang = 0.0f;

    public PenjualanCabangDetailDialog(Frame parent, boolean modal, DefaultTableModel penjualanTableModel, String shortcut, boolean notaKhusus) {
        super(parent, modal);
        this.initComponents();
        this.shortcut = shortcut;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.penjualanTableModel = penjualanTableModel;
        this.notaKhusus = notaKhusus;
        this.kodeBarangField.setText("");
        this.karyawan = this.helper.getKaryawan();
        if (this.karyawan.getRole().equals("DIREKTUR") || this.karyawan.equals("MANAGER")) {
            this.hargaSatuanField.setEditable(true);
        }
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
        this.barang = this.barangService.getBarang(kodeBarang);
        if (this.barang != null) {
            if (this.barang.getHargaJual1() == 0L) {
                JOptionPane.showMessageDialog(null, "Harga Jual Belum Ditentukan");
            }
            this.marginCabang = this.jenisService.getJenis(this.barang.getJenis()).getMarginCabang();
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
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
    }

    protected void escapeClick() {
        this.kodeBarangField.requestFocus();
        this.kodeBarangField.selectAll();
    }

    private void saveClick() {
        Object[] rowValue = new Object[]{this.barang.getId(), this.barang.getNama(), this.tempField.getText(), this.satuan4.getText(), this.hargaSatuanField.getValue(), (long)Float.parseFloat(this.totalHargaField.getValue().toString()), this.discount2Field.getValue(), (long)Float.parseFloat(this.nettoHargaField.getValue().toString()), this.notaKhusus};
        this.listener.saveClick(rowValue, true);
        this.escapeClick();
    }

    protected void loadPenjualan(int row) {
        if (this.penjualanTableModel.getValueAt(row, 3).equals(this.barang.getSatuan1())) {
            this.quantity1.setText(this.penjualanTableModel.getValueAt(row, 2).toString());
            this.quantity1.requestFocus();
            this.quantity1.selectAll();
        } else if (this.penjualanTableModel.getValueAt(row, 3).equals(this.barang.getSatuan2())) {
            this.quantity2.setText(this.penjualanTableModel.getValueAt(row, 2).toString());
            this.quantity2.requestFocus();
            this.quantity2.selectAll();
        } else if (this.penjualanTableModel.getValueAt(row, 3).equals(this.barang.getSatuan3())) {
            this.quantity3.setText(this.penjualanTableModel.getValueAt(row, 2).toString());
            this.quantity3.requestFocus();
            this.quantity3.selectAll();
        }
        this.satuan4.setText(this.penjualanTableModel.getValueAt(row, 3).toString());
        this.hargaSatuanField.setValue(this.penjualanTableModel.getValueAt(row, 4));
        this.totalHargaField.setValue(this.penjualanTableModel.getValueAt(row, 5));
        this.discount2Field.setValue(this.penjualanTableModel.getValueAt(row, 6));
        this.nettoHargaField.setValue(this.penjualanTableModel.getValueAt(row, 7));
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.kodeBarangField = new JTextField();
        this.namaBarangField = new JTextField();
        this.jSeparator1 = new JSeparator();
        this.beliTerakhirLbl = new JLabel();
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
        this.saveButton = new JButton();
        this.deleteButton = new JButton();
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
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.jLabel2.setText("Nama Barang");
        this.jLabel3.setText("Quantity");
        this.kodeBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.kodeBarangFieldKeyPressed(evt);
            }
        });
        this.namaBarangField.setEditable(false);
        this.namaBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.namaBarangFieldKeyReleased(evt);
            }
        });
        this.jSeparator1.setOrientation(1);
        this.beliTerakhirLbl.setText("Beli Terakhir");
        this.beliTerakhirField.setEditable(false);
        this.beliTerakhirField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.beliTerakhirField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.beliTerakhirFieldKeyPressed(evt);
            }
        });
        this.jLabel8.setText("Harga /");
        this.hargaSatuanField.setEditable(false);
        this.hargaSatuanField.setText("0");
        this.hargaSatuanField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.hargaSatuanFieldKeyPressed(evt);
            }
        });
        this.satuan4.setEditable(false);
        this.jLabel4.setText("Total");
        this.totalHargaField.setEditable(false);
        this.totalHargaField.setText("0");
        this.totalHargaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.totalHargaFieldKeyPressed(evt);
            }
        });
        this.jLabel6.setText("Discount");
        this.discount2Field.setText("0");
        this.discount2Field.setEnabled(false);
        this.discount2Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.discount2FieldKeyPressed(evt);
            }
        });
        this.jLabel10.setText("Netto");
        this.nettoHargaField.setEditable(false);
        this.nettoHargaField.setText("0");
        this.nettoHargaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.nettoHargaFieldKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanCabangDetailDialog.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.saveButtonKeyPressed(evt);
            }
        });
        this.deleteButton.setText("Delete");
        this.deleteButton.setPreferredSize(new Dimension(73, 23));
        this.deleteButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanCabangDetailDialog.this.deleteButtonActionPerformed(evt);
            }
        });
        this.deleteButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.deleteButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanCabangDetailDialog.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.exitButtonKeyPressed(evt);
            }
        });
        this.jLabel7.setText("Cari : F2");
        this.quantity1.setText("0");
        this.quantity1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.quantity1KeyPressed(evt);
            }
        });
        this.quantity2.setText("0");
        this.quantity2.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.quantity2KeyPressed(evt);
            }
        });
        this.quantity3.setText("0");
        this.quantity3.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangDetailDialog.this.quantity3KeyPressed(evt);
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
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1).addComponent(this.jLabel3).addComponent(this.jLabel4).addGroup(layout.createSequentialGroup().addComponent(this.jLabel8).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.satuan4, -2, 39, -2)).addComponent(this.jLabel6).addComponent(this.jLabel10)).addGap(23, 23, 23).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.kodeBarangField, -2, 138, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel7)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.quantity1, -1, 80, Short.MAX_VALUE).addComponent(this.quantity2).addComponent(this.quantity3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuan1, -1, 43, Short.MAX_VALUE).addComponent(this.satuan2, -1, -1, Short.MAX_VALUE).addComponent(this.satuan3, -1, -1, Short.MAX_VALUE))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.totalHargaField, GroupLayout.Alignment.LEADING).addComponent(this.hargaSatuanField, GroupLayout.Alignment.LEADING, -1, 119, Short.MAX_VALUE).addComponent(this.discount2Field).addComponent(this.nettoHargaField))).addGap(18, 18, 18).addComponent(this.jSeparator1, -2, 12, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.beliTerakhirField, -2, 124, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.beliTerakhirSatuanLbl, -1, 53, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel17).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.konversiLbl3, -1, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel16).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.konversiLbl1, -1, -1, Short.MAX_VALUE)).addComponent(this.jLabel11)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel20).addComponent(this.jLabel21, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.konversiField1).addComponent(this.konversiField2, -2, 51, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.konversiLbl4, -1, -1, Short.MAX_VALUE).addComponent(this.konversiLbl2, -1, -1, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addComponent(this.beliTerakhirLbl).addGap(0, 0, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.deleteButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2)).addComponent(this.namaBarangField, -2, 215, -2))).addComponent(this.promoLbl, -1, -1, Short.MAX_VALUE).addComponent(this.promoLbl2, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.promoLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.promoLbl2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeBarangField, -2, -1, -2).addComponent(this.jLabel7)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.beliTerakhirLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.beliTerakhirField).addComponent(this.beliTerakhirSatuanLbl, -2, 20, -2)).addGap(18, 18, 18).addComponent(this.jLabel11).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel16).addComponent(this.konversiLbl1).addComponent(this.jLabel20).addComponent(this.konversiField1, -2, -1, -2).addComponent(this.konversiLbl2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel17).addComponent(this.konversiLbl3).addComponent(this.jLabel21).addComponent(this.konversiField2, -2, -1, -2).addComponent(this.konversiLbl4))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.quantity1, -2, -1, -2)).addComponent(this.satuan1, -2, 20, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuan2, -1, -1, Short.MAX_VALUE).addComponent(this.quantity2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.quantity3, -2, -1, -2).addComponent(this.satuan3, -2, 20, -2)).addGap(16, 16, 16).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hargaSatuanField, -2, -1, -2).addComponent(this.satuan4, -2, -1, -2).addComponent(this.jLabel8)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.totalHargaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.discount2Field, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel10).addComponent(this.nettoHargaField, -2, -1, -2))).addComponent(this.jSeparator1, -2, 202, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.deleteButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addContainerGap()));
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
                    this.searchPenjualan(this.barang.getId());
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void quantity1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 40) {
            if (this.quantity1.getText().equals("") || Float.parseFloat(this.quantity1.getText()) == 0.0f) {
                this.quantity2.requestFocus();
                this.quantity2.selectAll();
                this.quantity1.setBackground(Color.white);
                this.quantity1.setText("0");
            } else if (!Helper.getInstance().isInteger(this.quantity1.getText())) {
                this.quantity1.setBackground(Color.red);
            } else {
                this.saveButton.setEnabled(true);
                this.tempField = this.quantity1;
                this.discount2Field.setValue(0);
                this.satuan4.setText(this.barang.getSatuan1());
                if (this.notaKhusus) {
                    long harga = (long)((float)this.barang.getHpp() + (float)this.barang.getHpp() * this.marginCabang / 100.0f);
                    this.hargaSatuanField.setValue(harga);
                }
                float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText());
                this.totalHargaField.setValue((long)tempValue);
                this.nettoHargaField.setValue((long)tempValue);
                this.hargaSatuanField.requestFocus();
                if (this.karyawan.getRole().equals("MANAGER") || this.karyawan.getRole().equals("DIREKTUR")) {
                    this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                    this.hargaSatuanField.selectAll();
                }
                this.quantity1.setBackground(Color.white);
                this.quantity2.setText("0");
                this.quantity3.setText("0");
            }
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.escapeClick();
        }
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
        } else if (evt.getKeyCode() == 32) {
            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
            passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
            int w = passwordInputDialog2.getWidth();
            int h = passwordInputDialog2.getHeight();
            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            passwordInputDialog2.setJudulText("Input Password Untuk Merubah Harga");
            passwordInputDialog2.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.helper.isFloat(this.hargaSatuanField.getText().toString())) {
                long hargaBeliTerakhir = this.barang.getHpp();
                this.lastPrice = 0L;
                if (this.satuan4.getText().equals(this.barang.getSatuan1())) {
                    this.lastPrice = hargaBeliTerakhir;
                } else if (this.satuan4.getText().equals(this.barang.getSatuan2())) {
                    this.lastPrice = hargaBeliTerakhir * (long)this.barang.getKonversi21();
                } else if (this.satuan4.getText().equals(this.barang.getSatuan3())) {
                    this.lastPrice = hargaBeliTerakhir * (long)this.barang.getKonversi21() * (long)this.barang.getKonversi32();
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
                    this.saveButton.requestFocus();
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
                this.saveButton.setEnabled(true);
                this.discount2Field.setValue(0);
                this.tempField = this.quantity2;
                this.satuan4.setText(this.barang.getSatuan2());
                if (this.notaKhusus) {
                    long harga = (long)((float)(this.barang.getHpp() * (long)this.barang.getKonversi21()) + (float)(this.barang.getHpp() * (long)this.barang.getKonversi21()) * this.marginCabang / 100.0f);
                    this.hargaSatuanField.setValue(harga);
                }
                this.hargaSatuanField.requestFocus();
                if (this.karyawan.getRole().equals("MANAGER") || this.karyawan.getRole().equals("DIREKTUR")) {
                    this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                    this.hargaSatuanField.selectAll();
                }
                float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText());
                this.totalHargaField.setValue((long)tempValue);
                this.nettoHargaField.setValue((long)tempValue);
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
                this.saveButton.setEnabled(true);
                this.tempField = this.quantity3;
                this.discount2Field.setValue(0);
                this.satuan4.setText(this.barang.getSatuan3());
                if (this.notaKhusus) {
                    long harga = (long)((float)(this.barang.getHpp() * (long)this.barang.getKonversi32() * (long)this.barang.getKonversi21()) + (float)(this.barang.getHpp() * (long)this.barang.getKonversi32() * (long)this.barang.getKonversi21()) * this.marginCabang / 100.0f);
                    this.hargaSatuanField.setValue(harga);
                }
                this.hargaSatuanField.requestFocus();
                if (this.karyawan.getRole().equals("MANAGER") || this.karyawan.getRole().equals("DIREKTUR")) {
                    this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                    this.hargaSatuanField.selectAll();
                }
                float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText());
                this.totalHargaField.setValue((long)tempValue);
                this.nettoHargaField.setValue((long)tempValue);
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

    private void discount2FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            float priceAfterDisc = Float.parseFloat(this.totalHargaField.getValue().toString()) - Float.parseFloat(this.discount2Field.getText());
            this.nettoHargaField.setValue(Float.valueOf(priceAfterDisc));
            this.saveButton.requestFocus();
            this.tempField2 = this.discount2Field;
        } else if (evt.getKeyCode() == 38) {
            this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
            this.hargaSatuanField.requestFocus();
            this.hargaSatuanField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (!(Helper.getInstance().isFloat(this.quantity1.getText()) && Helper.getInstance().isFloat(this.quantity2.getText()) && Helper.getInstance().isFloat(this.quantity3.getText()))) {
                JOptionPane.showMessageDialog(null, "Isi Data Dengan Benar");
            } else if (!this.notaKhusus && this.lastPrice >= Long.parseLong(this.hargaSatuanField.getText().replace(".", ""))) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordSaveEventHandler());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Harga Jual Lebih Tinggi Dr Harga Beli");
                passwordInputDialog2.setVisible(true);
            } else {
                this.saveClick();
            }
        } else if (evt.getKeyCode() == 39) {
            this.deleteButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.hargaSatuanField.requestFocus();
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
            this.listener.deleteClick(this.kodeBarangField.getText());
            this.escapeClick();
            this.setDefaultData();
        }
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

    private void saveButtonActionPerformed(ActionEvent evt) {
        if (!this.notaKhusus && this.lastPrice >= Long.parseLong(this.hargaSatuanField.getText().replace(".", ""))) {
            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
            passwordInputDialog2.addPasswordInputDialogListener(new passwordSaveEventHandler());
            int w = passwordInputDialog2.getWidth();
            int h = passwordInputDialog2.getHeight();
            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            passwordInputDialog2.setJudulText("Harga Jual Lebih Tinggi Dr Harga Beli");
            passwordInputDialog2.setVisible(true);
        }
        if (this.canGoThrough) {
            this.saveClick();
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void deleteButtonActionPerformed(ActionEvent evt) {
        this.listener.deleteClick(this.kodeBarangField.getText());
        this.escapeClick();
        this.setDefaultData();
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
        boolean find = false;
        for (int a = 0; a < this.penjualanTableModel.getRowCount(); ++a) {
            String barangId = this.penjualanTableModel.getValueAt(a, 0).toString();
            if (!idBarang.equals(barangId)) continue;
            find = true;
            this.loadPenjualan(a);
            break;
        }
        if (!find) {
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
            PenjualanCabangDetailDialog.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            PenjualanCabangDetailDialog.this.canGoThrough = false;
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR")) {
                PenjualanCabangDetailDialog.this.hargaSatuanField.setEditable(true);
                PenjualanCabangDetailDialog.this.hargaSatuanField.setText(PenjualanCabangDetailDialog.this.hargaSatuanField.getText().replace(".", ""));
                PenjualanCabangDetailDialog.this.hargaSatuanField.selectAll();
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
                PenjualanCabangDetailDialog.this.saveClick();
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
            PenjualanCabangDetailDialog.this.loadKodeBarang(chooseValue);
            PenjualanCabangDetailDialog.this.searchPenjualan(chooseValue);
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
            List<Barang> barangs = PenjualanCabangDetailDialog.this.barangService.getBarangs("", searchWord, false, "", true);
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

