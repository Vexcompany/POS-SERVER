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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import minimart.engine.model.Customer;
import minimart.engine.model.PromoPeriode;
import minimart.engine.model.PromoVoucher;
import minimart.engine.model.Sales;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanParsialDetailService;
import minimart.engine.service.PenjualanParsialService;
import minimart.engine.service.PromoPeriodeService;
import minimart.engine.service.PromoVoucherService;
import minimart.engine.service.SalesService;
import minimart.ui.listener.CetakNotaListener;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.PenjualanPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.CetakNotaExcel;
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

public class PenjualanByExcel
extends JPanel {
    private JTextField customerField;
    private JLabel customerLbl;
    private JTable errorTbl;
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
    private JButton uploadBtn;
    private PenjualanPanelListener listener;
    private int screenWidth;
    private int screenHeight;
    private CustomerService customerService = new CustomerService();
    private Customer customer;
    private PromoVoucher promoVoucher;
    private Sales sales;
    private SalesService salesService = new SalesService();
    private PromoVoucherService promoVoucherService = new PromoVoucherService();
    private PromoPeriodeService promoPeriodeService = new PromoPeriodeService();
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total", "Disc", "Harga Akhir", "Referensi"};
    private String[] errorColumn = new String[]{"Nomor Pesanan", "ID Barang"};
    long total = 0L;
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
    private Helper helper = Helper.getInstance();
    private BarangService barangService = new BarangService();
    private PenjualanDetailService detailService = new PenjualanDetailService();
    private PromoPeriode promoPeriode;
    private PenjualanParsialDetailService penjualanParsialDetailService = new PenjualanParsialDetailService();
    private PenjualanParsialService penjualanParsialService = new PenjualanParsialService();
    private boolean canGoThrough;

    public PenjualanByExcel(String shortcut) {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        ((AbstractDocument)this.customerField.getDocument()).setDocumentFilter(this.filter);
        this.karyawanField.setText(this.helper.getKaryawan().getNama());
        this.shortcutLbl.setText(shortcut);
        this.penjualanTable.setSelectionMode(0);
        this.jatuhTempoField.setEnabled(false);
        this.jatuhTempoField.setDate(new Date());
        if (shortcut.equals("F3") || shortcut.equals("F4")) {
            this.salesLbl.setVisible(true);
            this.salesField.setVisible(true);
            this.salesNameField.setVisible(true);
            this.keteranganLbl.setVisible(true);
            this.jatuhTempoField.setVisible(true);
            this.jatuhTempoLbl.setVisible(true);
        } else {
            this.salesLbl.setVisible(false);
            this.salesField.setVisible(false);
            this.salesNameField.setVisible(false);
            this.keteranganLbl.setVisible(false);
            this.jatuhTempoField.setVisible(false);
            this.jatuhTempoLbl.setVisible(false);
        }
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
        this.keteranganCmb.setSelectedIndex(1);
        this.keteranganCmb.setEnabled(false);
        this.customer = this.helper.getStatusProgram().equals("GUDANG") && this.helper.getJenisProgram().equals("GUDANGTOKO") ? this.customerService.getCustomerById("SINAR") : this.customerService.getCustomerById("C");
        this.customerField.setText(this.customer.getId());
        this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
        this.keteranganCmb.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    if (PenjualanByExcel.this.keteranganCmb.getSelectedItem().equals("CASH")) {
                        PenjualanByExcel.this.jatuhTempoField.setDate(new Date());
                    } else if (PenjualanByExcel.this.keteranganCmb.getSelectedItem().equals("KREDIT")) {
                        if (PenjualanByExcel.this.customer.getId().equals("C")) {
                            JOptionPane.showMessageDialog(null, "Pilih Customer Terlebih Dahulu");
                            PenjualanByExcel.this.keteranganCmb.setSelectedIndex(0);
                        } else if (PenjualanByExcel.this.customer.getLimitKredit() == 0 || PenjualanByExcel.this.customer.getTop() == 0) {
                            JOptionPane.showMessageDialog(null, "Customer " + PenjualanByExcel.this.customer.getNama() + " Belum Diberikan Fasilitas Kredit");
                            PenjualanByExcel.this.keteranganCmb.setSelectedIndex(0);
                        } else {
                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            c.add(5, PenjualanByExcel.this.customer.getTop());
                            PenjualanByExcel.this.jatuhTempoField.setDate(c.getTime());
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
        this.uploadBtn = new JButton();
        this.jTabbedPane1 = new JTabbedPane();
        this.jScrollPane1 = new JScrollPane();
        this.penjualanTable = new JTable();
        this.jScrollPane2 = new JScrollPane();
        this.errorTbl = new JTable();
        this.jLabel13.setText("jLabel13");
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
                PenjualanByExcel.this.customerFieldKeyPressed(evt);
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
                PenjualanByExcel.this.salesFieldKeyPressed(evt);
            }
        });
        this.salesNameField.setText("-");
        this.keteranganLbl.setText("Keterangan");
        this.keteranganCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"CASH", "KREDIT"}));
        this.keteranganCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanByExcel.this.keteranganCmbKeyPressed(evt);
            }
        });
        this.jatuhTempoLbl.setText("Jatuh Tempo");
        this.uploadBtn.setText("UPLOAD EXCEL");
        this.uploadBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanByExcel.this.uploadBtnActionPerformed(evt);
            }
        });
        this.penjualanTable.setModel(this.penjualanTableModel);
        this.penjualanTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanByExcel.this.penjualanTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.penjualanTable);
        this.jTabbedPane1.addTab("Penjualan", this.jScrollPane1);
        this.errorTbl.setModel(this.errorTableModel);
        this.jScrollPane2.setViewportView(this.errorTbl);
        this.jTabbedPane1.addTab("Error", this.jScrollPane2);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jTabbedPane1).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel5).addGap(40, 40, 40).addComponent(this.jLabel6).addGap(41, 41, 41).addComponent(this.jLabel10).addGap(43, 43, 43).addComponent(this.jLabel7).addGap(42, 42, 42).addComponent(this.jLabel8).addGap(47, 47, 47).addComponent(this.jLabel12).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE).addComponent(this.jLabel9)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.shortcutLbl))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel3).addComponent(this.jLabel4))).addComponent(this.salesLbl).addComponent(this.keteranganLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.customerField, -1, 124, Short.MAX_VALUE).addComponent(this.karyawanField, -1, -1, Short.MAX_VALUE).addComponent(this.salesField).addComponent(this.keteranganCmb, 0, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.customerLbl, -2, 162, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel11).addGap(18, 18, 18).addComponent(this.totalField, -2, 229, -2)).addGroup(layout.createSequentialGroup().addComponent(this.salesNameField).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jatuhTempoLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.jatuhTempoField, -2, 123, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.uploadBtn))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -1, -1, Short.MAX_VALUE).addComponent(this.shortcutLbl)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.karyawanField))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.customerLbl, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel4).addComponent(this.customerField, -2, -1, -2)).addGap(0, 0, Short.MAX_VALUE)))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalField, -2, 40, -2).addComponent(this.jLabel11, -1, -1, Short.MAX_VALUE))).addGap(5, 5, 5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.salesLbl).addComponent(this.salesField, -2, -1, -2).addComponent(this.salesNameField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keteranganLbl).addComponent(this.keteranganCmb, -2, -1, -2).addComponent(this.jatuhTempoLbl)).addComponent((Component)this.jatuhTempoField, -2, -1, -2).addComponent(this.uploadBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTabbedPane1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.jLabel7).addComponent(this.jLabel8).addComponent(this.jLabel9).addComponent(this.jLabel10).addComponent(this.jLabel12)).addContainerGap()));
    }

    private void f2Press() {
        int error = this.errorTableModel.getRowCount();
        if (error > 0) {
            this.canGoThrough = false;
            ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Masih Terdapat Error, Lanjutkan Penyimpanan Data?");
            confirmationDialog.addListener(new confirmDialogEventHandler());
            int w = confirmationDialog.getWidth();
            int h = confirmationDialog.getHeight();
            confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            confirmationDialog.setVisible(true);
        } else {
            this.canGoThrough = true;
        }
        if (this.canGoThrough) {
            if (this.penjualanTableModel.getRowCount() > 0) {
                LinkedList<Object[]> dataPenjualan = new LinkedList<Object[]>();
                int rowCount = this.penjualanTableModel.getRowCount();
                for (int a = 0; a < rowCount; ++a) {
                    Object[] data = new Object[]{this.penjualanTableModel.getValueAt(a, 0), this.penjualanTableModel.getValueAt(a, 1), this.penjualanTableModel.getValueAt(a, 2), this.penjualanTableModel.getValueAt(a, 3), this.penjualanTableModel.getValueAt(a, 4), this.penjualanTableModel.getValueAt(a, 5), this.penjualanTableModel.getValueAt(a, 6), this.penjualanTableModel.getValueAt(a, 7), this.penjualanTableModel.getValueAt(a, 8)};
                    dataPenjualan.add(data);
                }
                CetakNotaExcel cetakNota = new CetakNotaExcel(null, true, Float.valueOf(Float.parseFloat(this.totalField.getValue().toString())), this.jatuhTempoField.getDate(), this.keteranganCmb.getSelectedItem().toString(), dataPenjualan, this.customerField.getText());
                cetakNota.addListener(new cetakNotaEventHandler());
                int w = cetakNota.getWidth();
                int h = cetakNota.getHeight();
                cetakNota.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                cetakNota.setVisible(true);
                cetakNota.requestDefaultFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Masukkan Data Terlebih Dahulu");
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

    private void uploadBtnActionPerformed(ActionEvent evt) {
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
                rowValue[3] = tempBarang.getSatuan1();
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
            PenjualanByExcel.this.totalField.setValue(0);
            if (PenjualanByExcel.this.helper.getKasir() == 100 && PenjualanByExcel.this.helper.getStatusProgram().equals("GUDANG")) {
                PenjualanByExcel.this.customer = PenjualanByExcel.this.customerService.getCustomerById("SINAR");
            } else {
                PenjualanByExcel.this.customer = PenjualanByExcel.this.customerService.getCustomerById("C");
            }
            PenjualanByExcel.this.customerField.setText(PenjualanByExcel.this.customer.getId());
            PenjualanByExcel.this.customerLbl.setText(PenjualanByExcel.this.customer.getNama() + "-" + PenjualanByExcel.this.customer.getAlamat());
            PenjualanByExcel.this.sales = null;
            PenjualanByExcel.this.salesField.setText("");
            PenjualanByExcel.this.salesNameField.setText("-");
            int rowCount = PenjualanByExcel.this.penjualanTableModel.getRowCount();
            for (int a = 0; a < rowCount; ++a) {
                PenjualanByExcel.this.penjualanTableModel.removeRow(0);
            }
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PenjualanByExcel.this.customer = PenjualanByExcel.this.customerService.getCustomerById(chooseValue);
            PenjualanByExcel.this.customerField.setText(PenjualanByExcel.this.customer.getId());
            PenjualanByExcel.this.customerLbl.setText(PenjualanByExcel.this.customer.getNama() + "-" + PenjualanByExcel.this.customer.getAlamat());
            PenjualanByExcel.this.keteranganCmb.setSelectedIndex(1);
            PenjualanByExcel.this.jatuhTempoField.setDate(new Date());
            PenjualanByExcel.this.penjualanTable.requestFocus();
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
            List<Customer> customers = PenjualanByExcel.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp;
                if (PenjualanByExcel.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO")) {
                    if (PenjualanByExcel.this.helper.getStatusProgram().equalsIgnoreCase("TOKO")) {
                        temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                        dtm.addRow(temp);
                        continue;
                    }
                    if (!PenjualanByExcel.this.helper.getStatusProgram().equalsIgnoreCase("GUDANG") || !customer.getNama().equalsIgnoreCase("SINARMART")) continue;
                    temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                    dtm.addRow(temp);
                    continue;
                }
                if (!PenjualanByExcel.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) continue;
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
            PenjualanByExcel.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            PenjualanByExcel.this.canGoThrough = false;
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
            List<Sales> saleses = PenjualanByExcel.this.salesService.getSaleses(searchWord);
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
                PenjualanByExcel.this.salesField.setText(chooseValue);
                PenjualanByExcel.this.sales = PenjualanByExcel.this.salesService.getSales(PenjualanByExcel.this.salesField.getText());
                PenjualanByExcel.this.salesNameField.setText(PenjualanByExcel.this.sales.getNama());
                PenjualanByExcel.this.penjualanTable.requestFocus();
            }
        }
    }
}

