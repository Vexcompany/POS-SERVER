/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.BarangHpp;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Pembelian;
import minimart.engine.model.PembelianDetail;
import minimart.engine.model.Supplier;
import minimart.engine.service.BarangService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.DiscountPembelianDialogListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.PembelianDetailDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.DiscountPembelianDialog;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.PembelianDetailDialog;
import minimart.ui.view.PembelianPanel;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PembelianPanel2
extends JPanel {
    private JDateChooser dateField;
    private JButton detailButton;
    private JButton exitButton;
    private JComboBox<String> inputCmb;
    private JLabel jLabel1;
    private JLabel jLabel11;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JDateChooser jatuhTempoField;
    private JTextField keteranganField;
    private JTextField pelunasanField;
    private JComboBox pembayaranCmb;
    private JTextField pembelianIdField;
    private JButton saveButton;
    private JTextField supplierField;
    private JLabel supplierLbl;
    private JFormattedTextField totalField;
    private JTextField totalItemField;
    private JTable transactionTable;
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total", "Disc", "Harga Akhir"};
    private DefaultTableModel transactionTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private KeyboardFocusManager manager;
    private keyDispatcher keyDispatcher;
    private int screenWidth;
    private int screenHeight;
    private boolean sudahDiproses = true;
    private PembelianService pembelianService = new PembelianService();
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    private SupplierService supplierService = new SupplierService();
    private Supplier supplier = new Supplier();
    private BarangService barangService = new BarangService();
    private KaryawanService karyawanService = new KaryawanService();
    private Pembelian pembelian = new Pembelian();
    private Helper helper = Helper.getInstance();
    private DiscountPembelianDialog discountPembelianDialog;
    private boolean isCreated = true;
    private DefaultPanelListener listener;
    private List<PembelianDetail> pembelianDetails;
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();

    public PembelianPanel2() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.keyDispatcher = new keyDispatcher();
        this.manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        this.manager.addKeyEventDispatcher(this.keyDispatcher);
        this.resetData();
        TableColumn col1 = this.transactionTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.transactionTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.transactionTable.getColumnModel().getColumn(3);
        int width = 20;
        col1.setPreferredWidth(130);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
    }

    private void removeAllRow() {
        int rowCount = this.transactionTableModel.getRowCount();
        for (int a = 0; a < rowCount; ++a) {
            this.transactionTableModel.removeRow(0);
        }
    }

    private void loadData() {
        this.keteranganField.setText(this.pembelian.getKeterangan());
        if (this.pembelian.getKeterangan().equals("SUDAH DIPROSES")) {
            this.sudahDiproses = true;
            this.saveButton.setEnabled(false);
        } else if (this.pembelian.getKeterangan().equals("BELUM DIPROSES")) {
            this.sudahDiproses = false;
            this.saveButton.setEnabled(true);
        }
        this.removeAllRow();
        this.dateField.setDate(this.pembelian.getTanggal());
        this.jatuhTempoField.setDate(this.pembelian.getJatuhTempo());
        this.pembayaranCmb.setSelectedItem(this.pembelian.getPembayaran());
        this.pelunasanField.setText(this.pembelian.getPembayaran());
        if (this.pembelian.getPembayaran().equals("CASH")) {
            this.jatuhTempoField.setEnabled(false);
        } else if (this.pembelian.getPembayaran().equals("KREDIT")) {
            this.jatuhTempoField.setEnabled(true);
        }
        this.pembelianDetails = this.pembelianDetailService.getPembelianDetails(this.pembelianIdField.getText());
        long total = 0L;
        if (this.pembelianDetails.size() > 0) {
            this.supplier = this.supplierService.getSupplier(this.pembelian.getSupplierId());
            this.supplierField.setText(this.supplier.getId());
            this.supplierLbl.setText(this.supplier.getNama() + "-" + this.supplier.getAlamat());
            for (PembelianDetail pembelianDetail : this.pembelianDetails) {
                float tempValue;
                total += pembelianDetail.getTotalHarga();
                Barang barang = this.barangService.getBarang(pembelianDetail.getBarangId());
                if (barang == null) continue;
                Object[] rowValue = new Object[]{barang.getId(), barang.getNama(), (tempValue = pembelianDetail.getJumlahBarang()) == (float)((long)tempValue) ? (Number)((long)pembelianDetail.getJumlahBarang()) : (Number)Float.valueOf(pembelianDetail.getJumlahBarang()), pembelianDetail.getSatuan(), pembelianDetail.getHargaBeli(), (long)((float)pembelianDetail.getHargaBeli() * pembelianDetail.getJumlahBarang()), pembelianDetail.getDiscount2(), pembelianDetail.getTotalHarga()};
                this.transactionTableModel.addRow(rowValue);
            }
        }
        this.totalField.setValue(total);
        this.totalItemField.setText(this.pembelianDetails.size() + "");
    }

    public void setDefault() {
        this.pembelianIdField.requestFocusInWindow();
    }

    private void resetData() {
        this.dateField.setDate(new Date());
        int countRow = this.transactionTableModel.getRowCount();
        for (int a = 0; a < countRow; ++a) {
            this.transactionTableModel.removeRow(0);
        }
        this.totalField.setValue(0);
        this.supplierField.setText("");
        this.supplierLbl.setText("");
        this.keteranganField.setText("");
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.supplierField = new JTextField();
        this.dateField = new JDateChooser();
        this.detailButton = new JButton();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.transactionTable = new JTable();
        this.supplierLbl = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.totalItemField = new JTextField();
        this.jLabel9 = new JLabel();
        this.pembelianIdField = new JTextField();
        this.jLabel11 = new JLabel();
        this.keteranganField = new JTextField();
        this.jLabel13 = new JLabel();
        this.jLabel14 = new JLabel();
        this.jatuhTempoField = new JDateChooser();
        this.pembayaranCmb = new JComboBox();
        this.pelunasanField = new JTextField();
        this.inputCmb = new JComboBox();
        this.setEnabled(false);
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal");
        this.jLabel7.setFont(new Font("Tahoma", 1, 12));
        this.jLabel7.setText("Total");
        this.jLabel8.setText(":");
        this.jLabel5.setText("Supplier");
        this.jLabel6.setText(":");
        this.supplierField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel2.this.supplierFieldKeyPressed(evt);
            }
        });
        this.dateField.addKeyListener((KeyListener)new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel2.this.dateFieldKeyPressed(evt);
            }
        });
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(73, 23));
        this.detailButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel2.this.detailButtonKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PembelianPanel2.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel2.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PembelianPanel2.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel2.this.exitButtonKeyPressed(evt);
            }
        });
        this.transactionTable.setModel(this.transactionTableModel);
        this.transactionTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel2.this.transactionTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.transactionTable);
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setText("0");
        this.totalField.setFont(new Font("Tahoma", 1, 12));
        this.totalField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel2.this.totalFieldKeyPressed(evt);
            }
        });
        this.jLabel2.setText("Total Item");
        this.jLabel3.setText(":");
        this.totalItemField.setEditable(false);
        this.jLabel9.setText("ID Pembelian");
        this.pembelianIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel2.this.pembelianIdFieldKeyPressed(evt);
            }
        });
        this.jLabel11.setText("Input Stok");
        this.keteranganField.setBackground(new Color(153, 255, 255));
        this.jLabel13.setText("Pembayaran");
        this.jLabel14.setText("Jatuh Tempo");
        this.jatuhTempoField.addKeyListener((KeyListener)new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel2.this.jatuhTempoFieldKeyPressed(evt);
            }
        });
        this.pembayaranCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"CASH", "KREDIT", "TRANSFER"}));
        this.pembayaranCmb.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                PembelianPanel2.this.pembayaranCmbItemStateChanged(evt);
            }
        });
        this.inputCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"TOKO", "GUDANG"}));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(46, 46, 46).addComponent((Component)this.dateField, -2, 155, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel9).addComponent(this.jLabel13)).addGap(22, 22, 22).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.pembayaranCmb, 0, -1, Short.MAX_VALUE).addComponent(this.pembelianIdField))).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel14).addComponent(this.jLabel11)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent((Component)this.jatuhTempoField, -1, 155, Short.MAX_VALUE).addComponent(this.inputCmb, 0, -1, Short.MAX_VALUE)))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addGap(14, 14, 14).addComponent(this.jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.supplierField).addGap(103, 103, 103)).addGroup(layout.createSequentialGroup().addComponent(this.supplierLbl, -2, 265, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel3)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel7).addGap(38, 38, 38).addComponent(this.jLabel8))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.totalField, -2, 155, -2).addComponent(this.totalItemField, -2, 68, -2))).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.detailButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2)).addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.pelunasanField, GroupLayout.Alignment.TRAILING).addComponent(this.keteranganField, GroupLayout.Alignment.TRAILING, -1, 155, Short.MAX_VALUE))))).addContainerGap()).addGroup(layout.createSequentialGroup().addComponent(this.jScrollPane1).addGap(29, 29, 29)))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.totalField, -2, -1, -2).addComponent(this.jLabel8).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.supplierField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.supplierLbl, -2, 17, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.totalItemField, -2, -1, -2)))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel9)).addGroup(layout.createSequentialGroup().addComponent((Component)this.dateField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.pembelianIdField, -2, -1, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel13).addComponent(this.pembayaranCmb, -2, -1, -2).addComponent(this.pelunasanField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel14).addComponent((Component)this.jatuhTempoField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel11).addComponent(this.detailButton, -2, -1, -2).addComponent(this.saveButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addComponent(this.inputCmb, -2, -1, -2))).addComponent(this.keteranganField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 363, -2).addGap(109, 109, 109)));
    }

    private void supplierFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.supplierField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
                sd.addSearchDialogListener(new searchSupplierEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
                this.detailButton.requestFocus();
            } else if (!this.supplierField.getText().equals("")) {
                this.supplier = this.supplierService.getSupplier(this.supplierField.getText());
                if (this.supplier == null) {
                    JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
                } else {
                    this.supplierLbl.setText(this.supplier.getNama() + "-" + this.supplier.getAlamat());
                    this.detailButton.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
            sd.addSearchDialogListener(new searchSupplierEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.pembelianIdField.requestFocus();
            this.pembelianIdField.selectAll();
        }
    }

    private void detailClick() {
        this.transactionTable.requestFocus();
        if (this.transactionTableModel.getRowCount() == 0) {
            PembelianDetailDialog pembelianDetailDialog = new PembelianDetailDialog(null, true, this.transactionTableModel);
            pembelianDetailDialog.addListener(new pembelianDialogEventHandler());
            int w = pembelianDetailDialog.getWidth();
            int h = pembelianDetailDialog.getHeight();
            pembelianDetailDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            pembelianDetailDialog.setVisible(true);
        }
    }

    private void detailButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailClick();
        } else if (evt.getKeyCode() == 39) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.pembelianIdField.requestFocus();
            this.pembelianIdField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.supplierField.requestFocus();
            this.supplierField.selectAll();
        }
    }

    private void transactionTableKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 10) {
            PembelianDetailDialog pembelianDetailDialog = new PembelianDetailDialog(null, true, this.transactionTableModel);
            if (this.transactionTable.getSelectedRow() != -1) {
                pembelianDetailDialog.loadKodeBarang(this.transactionTableModel.getValueAt(this.transactionTable.getSelectedRow(), 0).toString());
                pembelianDetailDialog.loadPembelian(this.transactionTable.getSelectedRow());
            }
            pembelianDetailDialog.escapeClick();
            pembelianDetailDialog.addListener(new pembelianDialogEventHandler());
            int w = pembelianDetailDialog.getWidth();
            int h = pembelianDetailDialog.getHeight();
            pembelianDetailDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            pembelianDetailDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a2 = JOptionPane.showConfirmDialog(null, "Akhiri Entry Data?", "Akhiri Entry Data", 0, 3);
            if (a2 == 0) {
                Float total = Float.valueOf(0.0f);
                for (int x = 0; x < this.transactionTableModel.getRowCount(); ++x) {
                    total = Float.valueOf(total.floatValue() + Float.parseFloat(this.transactionTableModel.getValueAt(x, 7).toString()));
                }
                this.saveButton.requestFocus();
                this.totalField.setValue(total);
                this.totalItemField.setText(this.transactionTableModel.getRowCount() + "");
            }
        } else if (evt.getKeyCode() == 127 && this.transactionTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
            this.totalItemField.setText(Integer.parseInt(this.totalItemField.getText()) - 1 + "");
            this.totalField.setValue(Float.valueOf(Float.parseFloat(this.totalField.getValue().toString()) - Float.parseFloat(this.transactionTableModel.getValueAt(this.transactionTable.getSelectedRow(), 7).toString())));
            this.transactionTableModel.removeRow(this.transactionTable.getSelectedRow());
        }
    }

    private void saveClick() {
        float total = 0.0f;
        for (int x = 0; x < this.transactionTableModel.getRowCount(); ++x) {
            total += Float.parseFloat(this.transactionTableModel.getValueAt(x, 7).toString());
        }
        this.discountPembelianDialog = new DiscountPembelianDialog(null, true);
        this.discountPembelianDialog.setData((long)total, this.pembelian.getDiscount1(), this.pembelian.getPpn());
        this.discountPembelianDialog.addListener(new discountPembelianDialogEventHandler());
        int w = this.discountPembelianDialog.getWidth();
        int h = this.discountPembelianDialog.getHeight();
        this.discountPembelianDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        this.discountPembelianDialog.setVisible(true);
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.pembelianIdField.requestFocus();
            this.pembelianIdField.selectAll();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.listener.exitClick();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.pembelianIdField.requestFocus();
            this.pembelianIdField.selectAll();
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void dateFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.listener.exitClick();
        }
    }

    private void totalFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.pembelianIdField.requestFocus();
            this.pembelianIdField.selectAll();
        }
    }

    private void pembelianIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.pembelianIdField.getText().equals("")) {
                Object[] colName = new String[]{"ID", "Jam", "Supplier", "Karyawan", "Disc", "PPN", "Total"};
                DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String startDateString = simpleDateFormat.format(new Date());
                List<Pembelian> pembelians = this.pembelianService.getPembelianByDate(startDateString, startDateString, "", this.helper.getKaryawan().getId(), "");
                for (Pembelian tempPembelian : pembelians) {
                    Object[] rowData = new Object[]{tempPembelian.getId(), tempPembelian.getCreated().getHours() + ":" + tempPembelian.getCreated().getMinutes() + ":" + tempPembelian.getCreated().getSeconds(), this.supplierService.getSupplier(tempPembelian.getSupplierId()).getNama(), this.karyawanService.getKaryawan(tempPembelian.getKaryawanId()).getNama(), Float.valueOf(tempPembelian.getDiscount1()), Float.valueOf(tempPembelian.getPpn()), Helper.getInstance().formatNumber(tempPembelian.getTotal() + "")};
                    dtm.addRow(rowData);
                }
                SearchPanel sp = new SearchPanel(null, true, dtm, "Data Pembelian");
                sp.addSearchPanelListener(new searchPanelPembelianEventHandler());
                sp.setVisible(true);
            } else if (!this.pembelianIdField.getText().equals("")) {
                this.pembelian = this.pembelianService.getPembelianByStatus(this.pembelianIdField.getText());
                if (this.pembelian == null) {
                    JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                    passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
                    int w = passwordInputDialog2.getWidth();
                    int h = passwordInputDialog2.getHeight();
                    passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                    passwordInputDialog2.setVisible(true);
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.listener.exitClick();
        }
    }

    private void jatuhTempoFieldKeyPressed(KeyEvent evt) {
    }

    private void pembayaranCmbItemStateChanged(ItemEvent evt) {
        if (this.pembayaranCmb.getSelectedItem().equals("CASH")) {
            this.jatuhTempoField.setEnabled(false);
        } else if (this.pembayaranCmb.getSelectedItem().equals("KREDIT")) {
            this.jatuhTempoField.setEnabled(true);
            Calendar c = Calendar.getInstance();
            c.setTime(this.dateField.getDate());
            c.add(5, this.supplier.getTop());
            this.jatuhTempoField.setDate(c.getTime());
        }
    }

    private void saveData(Float disc, Float ppn, long total) {
        if (!this.sudahDiproses) {
            this.pembayaranCmb.setEnabled(true);
            this.inputCmb.setEditable(true);
            this.pembelianDetailService.deletePembelianDetail(this.pembelian.getId());
            float totalDiscount = (float)total * (disc.floatValue() / 100.0f);
            float totalPpn = ((float)total - totalDiscount) * (ppn.floatValue() / 100.0f);
            float netPrice = (float)total - totalDiscount + totalPpn;
            this.pembelian.setSupplierId(this.supplier.getId());
            this.pembelian.setKaryawanId(this.helper.getKaryawan().getId());
            this.pembelian.setTanggal(this.dateField.getDate());
            this.pembelian.setDiscount1(disc.floatValue());
            this.pembelian.setPpn(ppn.floatValue());
            this.pembelian.setTotal((long)netPrice);
            this.pembelian.setUpdated(new Date());
            this.pembelian.setPembayaran(this.pembayaranCmb.getSelectedItem() + "");
            if (this.pembayaranCmb.getSelectedItem().equals("CASH") || this.pembayaranCmb.getSelectedItem().equals("TRANSFER")) {
                this.pembelian.setJatuhTempo(new Date());
                this.pembelian.setPelunasan("LUNAS");
            } else if (this.pembayaranCmb.getSelectedItem().equals("KREDIT")) {
                this.pembelian.setJatuhTempo(this.jatuhTempoField.getDate());
                if (this.pembelian.getTitipan() < this.pembelian.getTotal()) {
                    this.pembelian.setPelunasan("BELUM LUNAS");
                }
            }
            if (this.inputCmb.getSelectedItem().equals("TOKO")) {
                this.pembelian.setStatus("TOKO");
            } else if (this.inputCmb.getSelectedItem().equals("GUDANG")) {
                this.pembelian.setStatus("GUDANG");
            }
            int updateStatus = this.pembelianService.updatePembelian(this.pembelian);
            int createPembelianDetailStatus = 0;
            ArrayList<BarangHpp> listBarangHpp = new ArrayList<BarangHpp>();
            for (int a = 0; a < this.transactionTableModel.getRowCount(); ++a) {
                Barang tempBarang;
                PembelianDetail pembelianDetail = new PembelianDetail();
                pembelianDetail.setPembelianId(this.pembelian.getId());
                pembelianDetail.setBarangId(this.transactionTableModel.getValueAt(a, 0).toString());
                pembelianDetail.setJumlahBarang(Float.parseFloat(this.transactionTableModel.getValueAt(a, 2).toString()));
                pembelianDetail.setSatuan(this.transactionTableModel.getValueAt(a, 3).toString());
                pembelianDetail.setHargaBeli(Long.parseLong(this.transactionTableModel.getValueAt(a, 4).toString()));
                pembelianDetail.setDiscount2((long)Float.parseFloat(this.transactionTableModel.getValueAt(a, 6).toString()));
                pembelianDetail.setTotalHarga((long)Float.parseFloat(this.transactionTableModel.getValueAt(a, 7).toString()));
                pembelianDetail.setCreated(new Date());
                pembelianDetail.setUpdated(new Date());
                createPembelianDetailStatus = this.pembelianDetailService.createPembelianDetail(pembelianDetail);
                if (createPembelianDetailStatus != 1) continue;
                Barang tempBarang1 = tempBarang = this.barangService.getBarang(this.transactionTableModel.getValueAt(a, 0).toString());
                BarangHpp tempBarangHpp = new BarangHpp();
                long hargaBeliTerakhir = 0L;
                long hargaBeliTemp = pembelianDetail.getTotalHarga();
                if (this.pembelian.getDiscount1() > 0.0f) {
                    float discountValue = this.pembelian.getDiscount1() / 100.0f * (float)hargaBeliTemp;
                    hargaBeliTemp -= (long)discountValue;
                }
                if (this.pembelian.getPpn() > 0.0f) {
                    float ppnValue = this.pembelian.getPpn() / 100.0f * (float)hargaBeliTemp;
                    hargaBeliTemp += (long)ppnValue;
                }
                if (pembelianDetail != null) {
                    if (pembelianDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                        hargaBeliTerakhir = (long)((float)hargaBeliTemp / (pembelianDetail.getJumlahBarang() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32()));
                    } else if (pembelianDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                        hargaBeliTerakhir = (long)((float)hargaBeliTemp / (pembelianDetail.getJumlahBarang() * (float)tempBarang.getKonversi21()));
                    } else if (pembelianDetail.getSatuan().equals(tempBarang.getSatuan1())) {
                        hargaBeliTerakhir = (long)((float)hargaBeliTemp / pembelianDetail.getJumlahBarang());
                    }
                }
                if (hargaBeliTerakhir != 0L) {
                    tempBarang.setHpp(hargaBeliTerakhir);
                }
                tempBarang.setUpdated(new Date());
                tempBarangHpp.setId_sku(tempBarang.getId());
                tempBarangHpp.setHpp(tempBarang.getHpp());
                tempBarangHpp.setHppUpdated(System.currentTimeMillis() / 1000L);
                listBarangHpp.add(tempBarangHpp);
                if (tempBarang1.getHpp() != tempBarang.getHpp()) {
                    this.barangService.updateBarangHpp(tempBarang);
                    continue;
                }
                this.barangService.updateBarang(tempBarang);
            }
            int resultPushHpp = 0;
            try {
                resultPushHpp = this.sinkronisasiBarangService.pushHpp(listBarangHpp);
            }
            catch (IOException ex) {
                Logger.getLogger(PembelianPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (createPembelianDetailStatus == 1 && updateStatus == 1 && resultPushHpp == 1) {
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                Paper p = pf.getPaper();
                p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                pf.setPaper(p);
                Book book = new Book();
                book.append(new pembelianPrint(), pf);
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
                JOptionPane.showMessageDialog(null, "Penyimpanan Transaksi Berhasil");
                this.pembelian = new Pembelian();
            } else if (resultPushHpp == 1) {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            } else {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Online Gagal, Coba lagi...");
            }
        } else {
            this.pembayaranCmb.setEnabled(false);
            this.inputCmb.setEditable(false);
        }
    }

    class pembelianPrint
    implements Printable {
        pembelianPrint() {
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
            g.drawString("Pembelian:" + PembelianPanel2.this.pembelian.getId(), x + 0, y + yK);
            g.drawString("Supplier:" + PembelianPanel2.this.supplierService.getSupplier(PembelianPanel2.this.pembelian.getSupplierId()).getNama(), x + 0, y + (yK += yP));
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (int a = 0; a < PembelianPanel2.this.transactionTableModel.getRowCount(); ++a) {
                Barang barang = PembelianPanel2.this.barangService.getBarang(PembelianPanel2.this.transactionTableModel.getValueAt(a, 0).toString());
                g.drawString(barang.getNama(), x + 0, y + yK);
                yK += yP;
                float tempValue = Float.parseFloat(PembelianPanel2.this.transactionTableModel.getValueAt(a, 2).toString());
                float tempValue2 = 0.0f;
                if (PembelianPanel2.this.transactionTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan2())) {
                    tempValue2 = tempValue * (float)barang.getKonversi21();
                } else if (PembelianPanel2.this.transactionTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan3())) {
                    tempValue2 = tempValue * (float)barang.getKonversi21() * (float)barang.getKonversi32();
                }
                g.drawString(tempValue + PembelianPanel2.this.transactionTableModel.getValueAt(a, 3).toString() + " / " + (barang.getStokGudang() + tempValue2) + barang.getSatuan1(), x + 20, y + yK);
                yK += yP;
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(PembelianPanel2.this.pembelian.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + yK);
            g.drawString("Jam " + PembelianPanel2.this.pembelian.getTanggal().getHours() + ":" + PembelianPanel2.this.pembelian.getTanggal().getMinutes() + ":" + PembelianPanel2.this.pembelian.getTanggal().getSeconds(), x + 120, y + yK);
            return 0;
        }
    }

    private class discountPembelianDialogEventHandler
    implements DiscountPembelianDialogListener {
        private discountPembelianDialogEventHandler() {
        }

        @Override
        public void saveClick(Float disc, Float ppn, long total) {
            if (PembelianPanel2.this.isCreated) {
                PembelianPanel2.this.saveData(disc, ppn, total);
                PembelianPanel2.this.resetData();
                PembelianPanel2.this.pembelianIdField.setText("");
                PembelianPanel2.this.supplierField.setText("");
                PembelianPanel2.this.setDefault();
            }
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR")) {
                PembelianPanel2.this.supplier = PembelianPanel2.this.supplierService.getSupplier(PembelianPanel2.this.pembelian.getSupplierId());
                PembelianPanel2.this.loadData();
                PembelianPanel2.this.supplierField.requestFocus();
                PembelianPanel2.this.supplierField.selectAll();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class pembelianDialogEventHandler
    implements PembelianDetailDialogListener {
        private pembelianDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            boolean newData = true;
            for (a = 0; a < PembelianPanel2.this.transactionTableModel.getRowCount(); ++a) {
                if (!PembelianPanel2.this.transactionTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PembelianPanel2.this.transactionTableModel.addRow(rowValue);
                PembelianPanel2.this.totalField.setValue(Float.valueOf(Float.parseFloat(PembelianPanel2.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
                PembelianPanel2.this.totalItemField.setText(Integer.parseInt(PembelianPanel2.this.totalItemField.getText()) + 1 + "");
            } else {
                PembelianPanel2.this.totalField.setValue(Float.valueOf(Float.parseFloat(PembelianPanel2.this.totalField.getValue().toString()) - Float.parseFloat(PembelianPanel2.this.transactionTableModel.getValueAt(a, 7).toString())));
                PembelianPanel2.this.totalField.setValue(Float.valueOf(Float.parseFloat(PembelianPanel2.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
                PembelianPanel2.this.transactionTableModel.removeRow(a);
                PembelianPanel2.this.transactionTableModel.addRow(rowValue);
            }
        }

        @Override
        public void deleteClick(String idBarang) {
            PembelianPanel2.this.removeAllRow();
            PembelianPanel2.this.loadData();
        }
    }

    private class searchPanelSupplierEventHandler
    implements SearchPanelListener {
        private searchPanelSupplierEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PembelianPanel2.this.supplier = PembelianPanel2.this.supplierService.getSupplier(chooseValue);
            PembelianPanel2.this.supplierLbl.setText(PembelianPanel2.this.supplier.getNama());
            PembelianPanel2.this.supplierField.setText(PembelianPanel2.this.supplier.getId());
        }
    }

    private class searchSupplierEventHandler
    implements SearchDialogListener {
        private searchSupplierEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SUPPLIER", "NAMA SUPPLIER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Supplier> suppliers = PembelianPanel2.this.supplierService.getSuppliers(searchWord);
            for (Supplier supplier : suppliers) {
                Object[] temp = new String[]{supplier.getId(), supplier.getNama(), supplier.getAlamat(), supplier.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Supplier");
            sp.addSearchPanelListener(new searchPanelSupplierEventHandler());
            sp.setVisible(true);
        }
    }

    private class searchPanelPembelianEventHandler
    implements SearchPanelListener {
        private searchPanelPembelianEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PembelianPanel2.this.pembelian = PembelianPanel2.this.pembelianService.getPembelian(chooseValue);
            PembelianPanel2.this.supplier = PembelianPanel2.this.supplierService.getSupplier(PembelianPanel2.this.pembelian.getSupplierId());
            PembelianPanel2.this.totalField.setValue(PembelianPanel2.this.pembelian.getTotal());
            PembelianPanel2.this.pembelianIdField.setText(PembelianPanel2.this.pembelian.getId());
            PembelianPanel2.this.loadData();
            PembelianPanel2.this.supplierField.requestFocus();
        }
    }

    private class keyDispatcher
    implements KeyEventDispatcher {
        private keyDispatcher() {
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == 401) {
                // empty if block
            }
            return false;
        }
    }
}

