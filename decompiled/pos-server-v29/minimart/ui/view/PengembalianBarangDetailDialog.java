/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Karyawan;
import minimart.engine.model.PengembalianBarangDetail;
import minimart.engine.model.Penjualan;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.HargaBertingkatService;
import minimart.engine.service.PengembalianBarangDetailService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.PromoHematDetailService;
import minimart.engine.service.PromoHematService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.PenjualanDetailDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PengembalianBarangDetailDialog
extends JDialog {
    private JFormattedTextField discountField;
    private JButton exitButton;
    private JFormattedTextField hargaSatuanField;
    private JLabel jLabel1;
    private JLabel jLabel11;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
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
    private JTextField quantity1;
    private JTextField quantity2;
    private JTextField quantity3;
    private JLabel satuan1;
    private JLabel satuan2;
    private JLabel satuan3;
    private JTextField satuan4;
    private JButton saveButton;
    private JTextField stokField;
    private JLabel stokLbl;
    private JLabel stokTokoLbl;
    private JFormattedTextField totalHargaField;
    private PenjualanDetailDialogListener listener;
    private SearchDialog sd;
    private Penjualan penjualan;
    private BarangService barangService = new BarangService();
    private HargaBertingkatService hargaBertingkatService = new HargaBertingkatService();
    private Barang barang;
    private DefaultTableModel penjualanTableModel;
    private int screenHeight;
    private int screenWidth;
    private JTextField tempField;
    private Helper helper = Helper.getInstance();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private PenjualanService penjualanService = new PenjualanService();
    private String customerId;
    private boolean canGoThrough;
    private String shortcut;
    private Karyawan karyawan;
    private boolean notaKhusus;
    private PromoHematService promoHematService = new PromoHematService();
    private PromoHematDetailService promoHematDetailService = new PromoHematDetailService();
    private String startDateString;
    private String endDateString;
    private PenjualanDetail penjualanDetail;
    private boolean isInt = true;
    private boolean isPlus = true;
    private boolean isLess = true;
    private PengembalianBarangDetailService pengembalianBarangDetailService = new PengembalianBarangDetailService();
    private int jumlahbarang;
    float discount = 0.0f;

    public PengembalianBarangDetailDialog(Frame parent, boolean modal, DefaultTableModel penjualanTableModel, Penjualan penjualan) {
        super(parent, modal);
        this.initComponents();
        this.shortcut = this.shortcut;
        this.penjualan = penjualan;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.customerId = this.customerId;
        this.penjualanTableModel = penjualanTableModel;
        this.notaKhusus = this.notaKhusus;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.startDateString = simpleDateFormat.format(new Date());
        this.endDateString = simpleDateFormat.format(new Date());
        this.kodeBarangField.setText("");
        this.karyawan = this.helper.getKaryawan();
        if (this.karyawan.getRole().equals("ADMIN") || this.karyawan.equals("MANAGER")) {
            this.hargaSatuanField.setEditable(true);
        }
    }

    public void addListener(PenjualanDetailDialogListener penjualanDetailDialogListener) {
        this.listener = penjualanDetailDialogListener;
    }

    private void setDefaultData() {
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
        this.discountField.setValue(0);
        this.totalHargaField.setValue(0);
        this.hargaSatuanField.setBackground(Color.white);
    }

    protected void loadKodeBarang(String kodeBarang) {
        this.setDefaultData();
        this.barang = this.barangService.getBarang(kodeBarang);
        if (this.barang != null) {
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
            this.stokField.setText(this.barang.getStokToko() + "");
            this.stokLbl.setText(this.barang.getSatuan1());
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
    }

    protected void escapeClick() {
        this.kodeBarangField.requestFocus();
        this.kodeBarangField.selectAll();
    }

    private void saveClick() {
        if (this.isInt && this.isLess && this.isPlus) {
            Object[] rowValue = new Object[7];
            rowValue[0] = this.barang.getId();
            rowValue[1] = this.barang.getNama();
            if (!this.quantity1.getText().equals("0")) {
                rowValue[2] = this.quantity1.getText();
            } else if (!this.quantity2.getText().equals("0")) {
                rowValue[2] = this.quantity2.getText();
            } else if (!this.quantity3.getText().equals("0")) {
                rowValue[2] = this.quantity3.getText();
            }
            rowValue[3] = this.satuan4.getText();
            rowValue[4] = this.hargaSatuanField.getValue();
            rowValue[5] = this.discountField.getValue();
            rowValue[6] = (long)Float.parseFloat(this.totalHargaField.getValue().toString());
            this.listener.saveClick(rowValue, true);
            this.discount = 0.0f;
            this.escapeClick();
        } else if (!this.isInt) {
            JOptionPane.showMessageDialog(null, "Masukkan Angka Dengan Benar");
        } else if (!this.isLess) {
            JOptionPane.showMessageDialog(null, "Jumlah barang tidak boleh melebihi jumlah penjualan");
        } else if (!this.isPlus) {
            JOptionPane.showMessageDialog(null, "Angka tidak boleh nol/ minus");
        }
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
        this.discountField.setValue(this.penjualanTableModel.getValueAt(row, 5));
        this.totalHargaField.setValue(this.penjualanTableModel.getValueAt(row, 6));
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.kodeBarangField = new JTextField();
        this.namaBarangField = new JTextField();
        this.jSeparator1 = new JSeparator();
        this.jLabel8 = new JLabel();
        this.hargaSatuanField = new JFormattedTextField();
        this.satuan4 = new JTextField();
        this.jLabel4 = new JLabel();
        this.totalHargaField = new JFormattedTextField();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.jLabel7 = new JLabel();
        this.satuan1 = new JLabel();
        this.satuan2 = new JLabel();
        this.satuan3 = new JLabel();
        this.quantity1 = new JTextField();
        this.quantity2 = new JTextField();
        this.quantity3 = new JTextField();
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
        this.stokTokoLbl = new JLabel();
        this.stokField = new JTextField();
        this.stokLbl = new JLabel();
        this.jLabel5 = new JLabel();
        this.discountField = new JFormattedTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.jLabel2.setText("Nama Barang");
        this.jLabel3.setText("Quantity");
        this.kodeBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangDetailDialog.this.kodeBarangFieldKeyPressed(evt);
            }
        });
        this.namaBarangField.setEditable(false);
        this.namaBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent evt) {
                PengembalianBarangDetailDialog.this.namaBarangFieldKeyReleased(evt);
            }
        });
        this.jSeparator1.setOrientation(1);
        this.jLabel8.setText("Harga /");
        this.hargaSatuanField.setEditable(false);
        this.hargaSatuanField.setText("0");
        this.hargaSatuanField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangDetailDialog.this.hargaSatuanFieldKeyPressed(evt);
            }
        });
        this.satuan4.setEditable(false);
        this.jLabel4.setText("Discount");
        this.totalHargaField.setEditable(false);
        this.totalHargaField.setText("0");
        this.totalHargaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangDetailDialog.this.totalHargaFieldKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengembalianBarangDetailDialog.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangDetailDialog.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengembalianBarangDetailDialog.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangDetailDialog.this.exitButtonKeyPressed(evt);
            }
        });
        this.jLabel7.setText("Cari : F2");
        this.quantity1.setText("0");
        this.quantity1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangDetailDialog.this.quantity1KeyPressed(evt);
            }
        });
        this.quantity2.setText("0");
        this.quantity2.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangDetailDialog.this.quantity2KeyPressed(evt);
            }
        });
        this.quantity3.setText("0");
        this.quantity3.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangDetailDialog.this.quantity3KeyPressed(evt);
            }
        });
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
        this.stokTokoLbl.setText("Stok Toko");
        this.stokLbl.setText("-");
        this.jLabel5.setText("Total");
        this.discountField.setEditable(false);
        this.discountField.setText("0");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1).addComponent(this.jLabel3).addComponent(this.jLabel4).addGroup(layout.createSequentialGroup().addComponent(this.jLabel8).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.satuan4, -2, 39, -2)).addComponent(this.jLabel5)).addGap(23, 23, 23).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.hargaSatuanField, -2, 119, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.quantity1, -1, 80, Short.MAX_VALUE).addComponent(this.quantity2).addComponent(this.quantity3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuan1, -1, 43, Short.MAX_VALUE).addComponent(this.satuan2, -1, -1, Short.MAX_VALUE).addComponent(this.satuan3, -1, -1, Short.MAX_VALUE))).addComponent(this.discountField, -2, 119, -2)).addGap(18, 18, 18).addComponent(this.jSeparator1, -2, 12, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel17).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.konversiLbl3, -1, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel16).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.konversiLbl1, -1, -1, Short.MAX_VALUE)).addComponent(this.jLabel11)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel20).addComponent(this.jLabel21, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.konversiField1).addComponent(this.konversiField2, -2, 51, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.konversiLbl4, -1, 65, Short.MAX_VALUE).addComponent(this.konversiLbl2, -1, -1, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addComponent(this.stokTokoLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.stokField, -2, 65, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.stokLbl, -1, 66, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.totalHargaField, -2, 119, -2).addGroup(layout.createSequentialGroup().addComponent(this.kodeBarangField, -2, 138, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel7)).addComponent(this.namaBarangField, -2, 215, -2).addGroup(layout.createSequentialGroup().addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeBarangField, -2, -1, -2).addComponent(this.jLabel7)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.quantity1, -2, -1, -2)).addComponent(this.satuan1, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuan2, -1, -1, Short.MAX_VALUE).addComponent(this.quantity2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.quantity3, -2, -1, -2).addComponent(this.satuan3, -2, 20, -2)).addGap(16, 16, 16).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hargaSatuanField, -2, -1, -2).addComponent(this.satuan4, -2, -1, -2).addComponent(this.jLabel8)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.discountField, -2, -1, -2))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel11).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel16).addComponent(this.konversiLbl1).addComponent(this.jLabel20).addComponent(this.konversiField1, -2, -1, -2).addComponent(this.konversiLbl2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel17).addComponent(this.konversiLbl3).addComponent(this.jLabel21).addComponent(this.konversiField2, -2, -1, -2).addComponent(this.konversiLbl4)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.stokTokoLbl).addComponent(this.stokField, -2, -1, -2).addComponent(this.stokLbl))).addComponent(this.jSeparator1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel5).addComponent(this.totalHargaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void kodeBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.kodeBarangField.getText().equals("")) {
                this.discount = 0.0f;
                Object[] colName = new String[]{"KODE BARANG", "NAMA BARANG", "JUMLAH", "SATUAN", "DISCOUNT", "HARGA"};
                DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
                List<PenjualanDetail> pejualanLists = this.penjualanDetailService.readPenjualanDetailByPenjualanId(this.penjualan.getId());
                for (PenjualanDetail penjualanList : pejualanLists) {
                    Object[] temp = new Object[]{penjualanList.getBarangId(), this.barangService.getBarang(penjualanList.getBarangId()).getNama(), Float.valueOf(penjualanList.getJumlahBarang()), penjualanList.getSatuan(), penjualanList.getDiscount2(), penjualanList.getHargaJual() - penjualanList.getDiscount2()};
                    this.discount = (float)penjualanList.getDiscount2() / penjualanList.getJumlahBarang();
                    dtm.addRow(temp);
                }
                SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
                sp.addSearchPanelListener(new searchPanelBarangEventHandler());
                sp.setVisible(true);
            } else {
                this.penjualanDetail = this.penjualanDetailService.getPenjualanDetail(this.penjualan.getId(), this.kodeBarangField.getText());
                this.jumlahbarang = this.penjualanDetailService.sumBarang(this.penjualan.getId(), this.kodeBarangField.getText(), this.penjualanDetail.getSatuan());
                if (this.penjualanDetail == null) {
                    JOptionPane.showMessageDialog(null, "Penjualan ID=" + this.penjualan.getId() + " tidak memiliki data item ini");
                } else {
                    PengembalianBarangDetail pengembalianBarangDetail = this.pengembalianBarangDetailService.getPengembalianBarangDetailByPenjualan(this.penjualan.getId(), this.kodeBarangField.getText());
                    if (pengembalianBarangDetail == null) {
                        this.searchPenjualan(this.barang.getId());
                    } else {
                        JOptionPane.showMessageDialog(null, "Item ini sudah pernah diretur");
                    }
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void quantity1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 40) {
            if (!Helper.getInstance().isInteger(this.quantity1.getText())) {
                this.quantity1.setBackground(Color.red);
                this.isInt = false;
            } else if (Float.parseFloat(this.quantity1.getText()) <= 0.0f) {
                this.quantity1.setBackground(Color.red);
                this.isPlus = false;
            } else if (Float.parseFloat(this.quantity1.getText()) > (float)this.jumlahbarang) {
                JOptionPane.showMessageDialog(null, "Jumlah barang tidak boleh melebihi jumlah penjualan");
                this.isLess = false;
            } else {
                this.tempField = this.quantity1;
                this.satuan4.setText(this.barang.getSatuan1());
                float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText());
                float tempDisc = (float)this.penjualanDetail.getDiscount2() / Float.parseFloat(this.tempField.getText());
                this.discountField.setValue(Float.valueOf(tempDisc));
                this.totalHargaField.setValue((long)tempValue);
                this.hargaSatuanField.requestFocus();
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.selectAll();
                this.quantity1.setBackground(Color.white);
                this.quantity2.setText("0");
                this.quantity3.setText("0");
                this.isInt = true;
                this.isPlus = true;
                this.isLess = true;
            }
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.escapeClick();
        }
    }

    private void hargaSatuanFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 33) {
            long a = 0L;
            if (!this.quantity1.getText().equals("0")) {
                a = this.barang.getHargaJual1();
            } else if (!this.quantity2.getText().equals("0")) {
                a = this.barang.getHargaJual2();
            } else if (!this.quantity3.getText().equals("0")) {
                a = this.barang.getHargaJual3();
            }
            long b = a % 500L;
            long c = 500L - b;
            long d = 0L;
            d = b <= 200L ? (this.barang.getJenis().equals("MINUMAN") && this.barang.getHargaJual1() >= 4000L ? a + c + 500L : (this.barang.getHargaJual1() >= 9000L ? a + c + 500L : a + c)) : (this.barang.getJenis().equals("MINUMAN") && this.barang.getHargaJual1() >= 4000L ? a + c + 1000L : (this.barang.getHargaJual1() >= 9000L ? a + c + 1000L : a + c + 500L));
            this.hargaSatuanField.setValue(d);
        } else if (evt.getKeyCode() == 32) {
            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
            passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
            int w = passwordInputDialog2.getWidth();
            int h = passwordInputDialog2.getHeight();
            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            passwordInputDialog2.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.helper.isFloat(this.hargaSatuanField.getText().toString())) {
                long hargaBeliTerakhir = this.helper.getHargaBeliTerakhirSatuanTerkecil(this.barang.getId());
                long lastPrice = 0L;
                if (this.satuan4.getText().equals(this.barang.getSatuan1())) {
                    lastPrice = hargaBeliTerakhir;
                } else if (this.satuan4.getText().equals(this.barang.getSatuan2())) {
                    lastPrice = hargaBeliTerakhir * (long)this.barang.getKonversi21();
                } else if (this.satuan4.getText().equals(this.barang.getSatuan3())) {
                    lastPrice = hargaBeliTerakhir * (long)this.barang.getKonversi21() * (long)this.barang.getKonversi32();
                }
                float totalHarga = Float.parseFloat(this.tempField.getText()) * Float.parseFloat(this.hargaSatuanField.getText());
                this.totalHargaField.setValue((long)totalHarga - (long)Math.round(((Float)this.discountField.getValue()).floatValue()));
                this.saveButton.requestFocus();
                this.hargaSatuanField.setBackground(Color.white);
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
            if (!Helper.getInstance().isFloat(this.quantity2.getText())) {
                this.quantity2.setBackground(Color.red);
                this.isInt = false;
            } else if (Float.parseFloat(this.quantity2.getText()) <= 0.0f) {
                this.quantity2.setBackground(Color.red);
                this.isPlus = false;
            } else if (Float.parseFloat(this.quantity2.getText()) > (float)this.jumlahbarang) {
                JOptionPane.showMessageDialog(null, "Jumlah barang tidak boleh melebihi jumlah penjualan");
                this.isLess = false;
            } else {
                this.tempField = this.quantity2;
                this.satuan4.setText(this.barang.getSatuan2());
                float tempDisc = this.discount * Float.parseFloat(this.tempField.getText());
                float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText()) - tempDisc;
                this.discountField.setValue(Float.valueOf(tempDisc));
                this.totalHargaField.setValue((long)tempValue);
                this.hargaSatuanField.requestFocus();
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.selectAll();
                this.quantity2.setBackground(Color.white);
                this.quantity1.setText("0");
                this.quantity3.setText("0");
                this.isInt = true;
                this.isPlus = true;
                this.isLess = true;
            }
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.escapeClick();
        }
    }

    private void quantity3KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 40) {
            if (!Helper.getInstance().isFloat(this.quantity3.getText())) {
                this.quantity3.setBackground(Color.red);
                this.isInt = false;
            } else if (Float.parseFloat(this.quantity3.getText()) <= 0.0f) {
                this.quantity3.setBackground(Color.red);
                this.isPlus = false;
            } else if (Float.parseFloat(this.quantity3.getText()) > (float)this.jumlahbarang) {
                JOptionPane.showMessageDialog(null, "Jumlah barang tidak boleh melebihi jumlah penjualan");
                this.isLess = false;
            } else {
                this.tempField = this.quantity3;
                this.satuan4.setText(this.barang.getSatuan3());
                float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText());
                float tempDisc = (float)this.penjualanDetail.getDiscount2() / Float.parseFloat(this.tempField.getText());
                this.discountField.setValue(Float.valueOf(tempDisc));
                this.totalHargaField.setValue((long)tempValue);
                this.hargaSatuanField.requestFocus();
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.selectAll();
                this.quantity3.setBackground(Color.white);
                this.quantity2.setText("0");
                this.quantity1.setText("0");
                this.isInt = true;
                this.isPlus = true;
                this.isLess = true;
            }
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.escapeClick();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (Float.parseFloat(this.quantity1.getText()) < 0.0f || Float.parseFloat(this.quantity2.getText()) < 0.0f || Float.parseFloat(this.quantity3.getText()) < 0.0f || !Helper.getInstance().isFloat(this.quantity1.getText()) || !Helper.getInstance().isFloat(this.quantity2.getText()) || !Helper.getInstance().isFloat(this.quantity3.getText())) {
                JOptionPane.showMessageDialog(null, "Isi Data Dengan Benar");
            } else {
                this.saveClick();
            }
        } else if (evt.getKeyCode() == 38) {
            this.hargaSatuanField.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        if (Integer.parseInt(this.quantity1.getText()) > 0) {
            if (!Helper.getInstance().isInteger(this.quantity1.getText())) {
                this.quantity1.setBackground(Color.red);
                this.isInt = false;
            } else if (Float.parseFloat(this.quantity1.getText()) <= 0.0f) {
                this.quantity1.setBackground(Color.red);
                this.isPlus = false;
            } else if (Float.parseFloat(this.quantity1.getText()) > (float)this.jumlahbarang) {
                JOptionPane.showMessageDialog(null, "Jumlah barang tidak boleh melebihi jumlah penjualan");
                this.isLess = false;
            } else {
                this.tempField = this.quantity1;
                this.satuan4.setText(this.barang.getSatuan1());
                float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText());
                this.totalHargaField.setValue((long)tempValue);
                this.hargaSatuanField.requestFocus();
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.selectAll();
                this.quantity1.setBackground(Color.white);
                this.quantity2.setText("0");
                this.quantity3.setText("0");
                this.isInt = true;
                this.isPlus = true;
                this.isLess = true;
            }
        } else if (Integer.parseInt(this.quantity2.getText()) > 0) {
            if (!Helper.getInstance().isFloat(this.quantity2.getText())) {
                this.quantity2.setBackground(Color.red);
                this.isInt = false;
            } else if (Float.parseFloat(this.quantity2.getText()) <= 0.0f) {
                this.quantity2.setBackground(Color.red);
                this.isPlus = false;
            } else if (Float.parseFloat(this.quantity2.getText()) > (float)this.jumlahbarang) {
                JOptionPane.showMessageDialog(null, "Jumlah barang tidak boleh melebihi jumlah penjualan");
                this.isLess = false;
            } else {
                this.tempField = this.quantity2;
                this.satuan4.setText(this.barang.getSatuan2());
                float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText());
                this.totalHargaField.setValue((long)tempValue);
                this.hargaSatuanField.requestFocus();
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.selectAll();
                this.quantity2.setBackground(Color.white);
                this.quantity1.setText("0");
                this.quantity3.setText("0");
                this.isInt = true;
                this.isPlus = true;
                this.isLess = true;
            }
        } else if (Integer.parseInt(this.quantity3.getText()) > 0) {
            if (!Helper.getInstance().isFloat(this.quantity3.getText())) {
                this.quantity3.setBackground(Color.red);
                this.isInt = false;
            } else if (Float.parseFloat(this.quantity3.getText()) <= 0.0f) {
                this.quantity3.setBackground(Color.red);
                this.isPlus = false;
            } else if (Float.parseFloat(this.quantity3.getText()) > (float)this.jumlahbarang) {
                JOptionPane.showMessageDialog(null, "Jumlah barang tidak boleh melebihi jumlah penjualan");
                this.isLess = false;
            } else {
                this.tempField = this.quantity3;
                this.satuan4.setText(this.barang.getSatuan3());
                float tempValue = Float.parseFloat(this.hargaSatuanField.getValue() + "") * Float.parseFloat(this.tempField.getText());
                this.totalHargaField.setValue((long)tempValue);
                this.hargaSatuanField.requestFocus();
                this.hargaSatuanField.setText(this.hargaSatuanField.getText().replace(".", ""));
                this.hargaSatuanField.selectAll();
                this.quantity3.setBackground(Color.white);
                this.quantity2.setText("0");
                this.quantity1.setText("0");
                this.isInt = true;
                this.isPlus = true;
                this.isLess = true;
            }
        }
        this.saveClick();
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
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
            this.quantity1.requestFocus();
            this.quantity1.selectAll();
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            PengembalianBarangDetailDialog.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            PengembalianBarangDetailDialog.this.canGoThrough = false;
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) {
                PengembalianBarangDetailDialog.this.hargaSatuanField.setEditable(true);
                PengembalianBarangDetailDialog.this.hargaSatuanField.setText(PengembalianBarangDetailDialog.this.hargaSatuanField.getText().replace(".", ""));
                PengembalianBarangDetailDialog.this.hargaSatuanField.selectAll();
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
            PengembalianBarangDetailDialog.this.penjualanDetail = PengembalianBarangDetailDialog.this.penjualanDetailService.getPenjualanDetail(PengembalianBarangDetailDialog.this.penjualan.getId(), chooseValue);
            PengembalianBarangDetailDialog.this.jumlahbarang = PengembalianBarangDetailDialog.this.penjualanDetailService.sumBarang(PengembalianBarangDetailDialog.this.penjualan.getId(), chooseValue, PengembalianBarangDetailDialog.this.penjualanDetail.getSatuan());
            if (PengembalianBarangDetailDialog.this.penjualanDetail == null) {
                JOptionPane.showMessageDialog(null, "Penjualan ID=" + PengembalianBarangDetailDialog.this.penjualan.getId() + " tidak memiliki data item ini");
            } else {
                PengembalianBarangDetail pengembalianBarangDetail = PengembalianBarangDetailDialog.this.pengembalianBarangDetailService.getPengembalianBarangDetailByPenjualan(PengembalianBarangDetailDialog.this.penjualan.getId(), chooseValue);
                if (pengembalianBarangDetail == null) {
                    Barang tempBarang = PengembalianBarangDetailDialog.this.barangService.getBarang(PengembalianBarangDetailDialog.this.penjualanDetail.getBarangId());
                    PengembalianBarangDetailDialog.this.loadKodeBarang(PengembalianBarangDetailDialog.this.penjualanDetail.getBarangId());
                    if (PengembalianBarangDetailDialog.this.penjualanDetail.getSatuan().equals(tempBarang.getSatuan1())) {
                        PengembalianBarangDetailDialog.this.quantity2.setEnabled(false);
                        PengembalianBarangDetailDialog.this.quantity3.setEnabled(false);
                        PengembalianBarangDetailDialog.this.quantity1.setText(Math.round(PengembalianBarangDetailDialog.this.penjualanDetail.getJumlahBarang()) + "");
                        PengembalianBarangDetailDialog.this.satuan4.setText(PengembalianBarangDetailDialog.this.penjualanDetail.getSatuan());
                        PengembalianBarangDetailDialog.this.quantity1.requestFocus();
                        PengembalianBarangDetailDialog.this.quantity1.selectAll();
                    } else if (PengembalianBarangDetailDialog.this.penjualanDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                        PengembalianBarangDetailDialog.this.quantity1.setEnabled(false);
                        PengembalianBarangDetailDialog.this.quantity3.setEnabled(false);
                        PengembalianBarangDetailDialog.this.quantity2.setText(Math.round(PengembalianBarangDetailDialog.this.penjualanDetail.getJumlahBarang()) + "");
                        PengembalianBarangDetailDialog.this.satuan4.setText(PengembalianBarangDetailDialog.this.penjualanDetail.getSatuan());
                        PengembalianBarangDetailDialog.this.quantity2.requestFocus();
                        PengembalianBarangDetailDialog.this.quantity2.selectAll();
                    } else if (PengembalianBarangDetailDialog.this.penjualanDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                        PengembalianBarangDetailDialog.this.quantity1.setEnabled(false);
                        PengembalianBarangDetailDialog.this.quantity2.setEnabled(false);
                        PengembalianBarangDetailDialog.this.quantity3.setText(Math.round(PengembalianBarangDetailDialog.this.penjualanDetail.getJumlahBarang()) + "");
                        PengembalianBarangDetailDialog.this.satuan4.setText(PengembalianBarangDetailDialog.this.penjualanDetail.getSatuan());
                        PengembalianBarangDetailDialog.this.quantity3.requestFocus();
                        PengembalianBarangDetailDialog.this.quantity3.selectAll();
                    }
                    PengembalianBarangDetailDialog.this.hargaSatuanField.setValue(PengembalianBarangDetailDialog.this.penjualanDetail.getHargaJual());
                    PengembalianBarangDetailDialog.this.discountField.setValue(PengembalianBarangDetailDialog.this.penjualanDetail.getDiscount2());
                    PengembalianBarangDetailDialog.this.totalHargaField.setValue(Float.valueOf((float)PengembalianBarangDetailDialog.this.penjualanDetail.getHargaJual() * PengembalianBarangDetailDialog.this.penjualanDetail.getJumlahBarang() - (float)PengembalianBarangDetailDialog.this.penjualanDetail.getDiscount2()));
                } else {
                    JOptionPane.showMessageDialog(null, "Item ini sudah pernah diretur");
                }
            }
        }
    }

    private class searchBarangDialogEventHandler
    implements SearchDialogListener {
        private searchBarangDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE BARANG", "NAMA BARANG", "JUMLAH", "SATUAN", "HARGA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<PenjualanDetail> pejualanLists = PengembalianBarangDetailDialog.this.penjualanDetailService.readPenjualanDetailByPenjualanId(PengembalianBarangDetailDialog.this.penjualan.getId());
            for (PenjualanDetail penjualanList : pejualanLists) {
                Object[] temp = new Object[]{penjualanList.getBarangId(), PengembalianBarangDetailDialog.this.barangService.getBarang(penjualanList.getBarangId()).getNama(), Float.valueOf(penjualanList.getJumlahBarang()), penjualanList.getSatuan(), penjualanList.getDiscount2(), penjualanList.getHargaJual() - penjualanList.getDiscount2()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }
    }
}

