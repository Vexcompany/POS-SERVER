/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.dao.CustomerDao;
import minimart.engine.daoimpl.CustomerDaoImpl;
import minimart.engine.model.Barang;
import minimart.engine.model.Customer;
import minimart.engine.model.Karyawan;
import minimart.engine.model.OTPPengembalian;
import minimart.engine.model.OTPPengembalianDetail;
import minimart.engine.model.PengembalianBarang;
import minimart.engine.model.PengembalianBarangDetail;
import minimart.engine.model.Penjualan;
import minimart.engine.model.ShiftKasir;
import minimart.engine.model.StockCommit;
import minimart.engine.model.Voucher;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerPointCommitService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.OTPPengembalianService;
import minimart.engine.service.PengembalianBarangDetailService;
import minimart.engine.service.PengembalianBarangService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.ShiftKasirService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.SyncService;
import minimart.engine.service.VoucherService;
import minimart.ui.listener.CetakPengembalianListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.PenjualanDetailDialogListener;
import minimart.ui.listener.PenjualanPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.CetakPengembalianBarang;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.PengembalianBarangDetailDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PengembalianBarangPanel
extends JPanel {
    private JLabel alasanReturField;
    private JLabel customerBankLbl;
    private JLabel customerLbl;
    private JLabel customerNameLbl;
    private JLabel customerTelpLbl;
    private JButton jButton1;
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
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JLabel karyawanField;
    private JTextField otpField;
    private JTable pengembalianTable;
    private JTextField penjualanIdField;
    private JButton requestOTPButton;
    private JComboBox<String> selectorPembayaran;
    private JFormattedTextField totalField;
    private PenjualanPanelListener listener;
    private PenjualanService penjualanService = new PenjualanService();
    private Penjualan penjualan = new Penjualan();
    private BarangService barangService = new BarangService();
    private int screenWidth;
    private int screenHeight;
    private CustomerService customerService = new CustomerService();
    private CommonEngine comm = new CommonEngine();
    private CustomerPointCommitService customerPointCommitService = new CustomerPointCommitService();
    private CustomerDao cd = new CustomerDaoImpl();
    private Customer customer;
    private ShiftKasir shiftKasir;
    private PengembalianBarang pengembalianBarang;
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Discount", "Total"};
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();
    private OTPPengembalianService otpPengembalianService = new OTPPengembalianService();
    private DefaultTableModel pengembalianTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private Helper helper = Helper.getInstance();
    private PengembalianBarangService pengembalianBarangService = new PengembalianBarangService();
    private PengembalianBarangDetailService pengembalianBarangDetailService = new PengembalianBarangDetailService();
    private KonstantaService konstantaService = new KonstantaService();
    String tipeToko;
    private SyncService syncService = new SyncService();
    private VoucherService voucherService = new VoucherService();
    private Voucher voucher;
    private String pembayaranGlobal = "VOUCHER";

    public PengembalianBarangPanel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.pengembalianTable.setSelectionMode(0);
        TableColumn col1 = this.pengembalianTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.pengembalianTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.pengembalianTable.getColumnModel().getColumn(3);
        int width = 20;
        col1.setPreferredWidth(170);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
        this.customer = this.customerService.getCustomerById("C");
        this.totalField.setValue(0);
        this.tipeToko = this.konstantaService.getTipeTokoFirstLetterCap();
        if (!this.tipeToko.equals("Minimarket")) {
            this.selectorPembayaran.setEnabled(false);
            this.selectorPembayaran.setEditable(false);
        } else {
            this.selectorPembayaran.setEnabled(true);
            this.selectorPembayaran.setEditable(true);
        }
        this.shiftKasir = new ShiftKasirService().getOpenedShiftKasir(this.helper.getKasir());
    }

    private void removeAllRow() {
        int rowCount = this.pengembalianTableModel.getRowCount();
        for (int a = 0; a < rowCount; ++a) {
            this.pengembalianTableModel.removeRow(0);
        }
    }

    public void setDefaultFocus() {
        this.penjualanIdField.requestFocusInWindow();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.pengembalianTable = new JTable();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel10 = new JLabel();
        this.karyawanField = new JLabel();
        this.jLabel11 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jLabel12 = new JLabel();
        this.jLabel13 = new JLabel();
        this.jLabel14 = new JLabel();
        this.penjualanIdField = new JTextField();
        this.customerLbl = new JLabel();
        this.jButton1 = new JButton();
        this.customerNameLbl = new JLabel();
        this.jLabel15 = new JLabel();
        this.jLabel16 = new JLabel();
        this.otpField = new JTextField();
        this.requestOTPButton = new JButton();
        this.selectorPembayaran = new JComboBox();
        this.jLabel17 = new JLabel();
        this.jLabel18 = new JLabel();
        this.customerTelpLbl = new JLabel();
        this.customerBankLbl = new JLabel();
        this.alasanReturField = new JLabel();
        this.jLabel19 = new JLabel();
        this.jLabel20 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Kasir");
        this.jLabel2.setText("Customer");
        this.pengembalianTable.setModel(this.pengembalianTableModel);
        this.pengembalianTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangPanel.this.pengembalianTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.pengembalianTable);
        this.jLabel5.setText("F1:Customer");
        this.jLabel6.setText("F2:Print");
        this.jLabel7.setText("Enter:Edit");
        this.jLabel8.setText("Delete:Hapus");
        this.jLabel9.setText("ESC-Keluar");
        this.jLabel3.setText(":");
        this.jLabel4.setText(":");
        this.jLabel10.setText("F5-F12:PendingTransaksi");
        this.karyawanField.setText("-");
        this.jLabel11.setFont(new Font("Tahoma", 1, 24));
        this.jLabel11.setText("Total");
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setText("0");
        this.totalField.setFont(new Font("Tahoma", 1, 24));
        this.jLabel12.setText("Space: InputBarcode");
        this.jLabel13.setText("ID Penjualan");
        this.jLabel14.setText(":");
        this.penjualanIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangPanel.this.penjualanIdFieldKeyPressed(evt);
            }
        });
        this.customerLbl.setText("-");
        this.jButton1.setText("Keluar");
        this.jButton1.setMaximumSize(new Dimension(73, 23));
        this.jButton1.setMinimumSize(new Dimension(73, 23));
        this.jButton1.setPreferredSize(new Dimension(73, 23));
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengembalianBarangPanel.this.keluarBtn(evt);
            }
        });
        this.customerNameLbl.setText("-");
        this.jLabel15.setText("OTP");
        this.jLabel16.setText(":");
        this.otpField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangPanel.this.otpFieldKeyPressed(evt);
            }
        });
        this.requestOTPButton.setText("Request OTP");
        this.requestOTPButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengembalianBarangPanel.this.requestOTPButtonActionPerformed(evt);
            }
        });
        this.selectorPembayaran.setModel(new DefaultComboBoxModel<String>(new String[]{"VOUCHER", "TRANSFER", "CASH"}));
        this.selectorPembayaran.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengembalianBarangPanel.this.selectorPembayaranActionPerformed(evt);
            }
        });
        this.selectorPembayaran.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBarangPanel.this.selectorPembayaranKeyPressed(evt);
            }
        });
        this.jLabel17.setText("Pembayaran");
        this.jLabel18.setText(":");
        this.customerTelpLbl.setText("-");
        this.customerBankLbl.setText("-");
        this.alasanReturField.setText("-");
        this.jLabel19.setText(":");
        this.jLabel20.setText("Alasan Retur");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addGap(40, 40, 40).addComponent(this.jLabel6).addGap(41, 41, 41).addComponent(this.jLabel10).addGap(43, 43, 43).addComponent(this.jLabel7).addGap(42, 42, 42).addComponent(this.jLabel8).addGap(47, 47, 47).addComponent(this.jLabel12).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE).addComponent(this.jLabel9)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel13, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel1)).addComponent(this.jLabel17)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel14).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.karyawanField, -2, 124, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel11).addGap(18, 18, 18).addComponent(this.totalField, -2, 229, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel3).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.penjualanIdField, -2, 126, -2).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.customerLbl, -1, -1, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.customerNameLbl, -2, 200, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.customerTelpLbl, -2, 200, -2).addGap(80, 80, 80).addComponent(this.jButton1, -2, -1, -2)).addComponent(this.customerBankLbl, -2, 257, -2))))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel18).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.selectorPembayaran, -2, -1, -2).addGap(0, 607, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel15).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel16).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.otpField, -2, 126, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.requestOTPButton)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel20).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel19).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.alasanReturField, -2, 437, -2))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.penjualanIdField, -2, -1, -2).addComponent(this.jLabel13)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel14).addComponent(this.karyawanField).addComponent(this.jLabel1, -2, 14, -2))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalField, -2, 40, -2).addComponent(this.jLabel11, -1, -1, Short.MAX_VALUE))).addGap(7, 7, 7).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel4).addComponent(this.customerLbl).addComponent(this.jButton1, -2, -1, -2).addComponent(this.customerNameLbl).addComponent(this.customerTelpLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.selectorPembayaran, -2, -1, -2).addComponent(this.jLabel17).addComponent(this.jLabel18).addComponent(this.customerBankLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel19).addComponent(this.alasanReturField).addComponent(this.jLabel20, -2, 14, -2)).addGap(3, 3, 3).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel15).addComponent(this.jLabel16).addComponent(this.otpField, -2, -1, -2).addComponent(this.requestOTPButton)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.jLabel7).addComponent(this.jLabel8).addComponent(this.jLabel9).addComponent(this.jLabel10).addComponent(this.jLabel12)).addContainerGap()));
    }

    private void pengembalianTableKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 112) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 113) {
            if (this.pengembalianTableModel.getRowCount() > 0) {
                String otpMethod = this.konstantaService.getConstantValue("otpPengembalian");
                if (!"new".equals(otpMethod)) {
                    PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                    passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler2());
                    int w = passwordInputDialog2.getWidth();
                    int h = passwordInputDialog2.getHeight();
                    passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                    passwordInputDialog2.setJudulText("Input Password Untuk Melakukan Retur");
                    passwordInputDialog2.setVisible(true);
                } else {
                    this.printRetur();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Masukkan Data Terlebih Dahulu");
            }
        } else if (evt.getKeyCode() == 27) {
            int a2 = JOptionPane.showConfirmDialog(null, "Transaksi Pengembalian Barang Telah Selesai?", "Keluar Transaksi", 0, 3);
            if (a2 == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 10) {
            PengembalianBarangDetailDialog pengembalianBarangDetail = new PengembalianBarangDetailDialog(null, true, this.pengembalianTableModel, this.penjualan);
            if (this.pengembalianTable.getSelectedRow() != -1) {
                pengembalianBarangDetail.loadKodeBarang(this.pengembalianTableModel.getValueAt(this.pengembalianTable.getSelectedRow(), 0).toString());
                pengembalianBarangDetail.loadPenjualan(this.pengembalianTable.getSelectedRow());
            }
            pengembalianBarangDetail.escapeClick();
            pengembalianBarangDetail.addListener(new penjualanDetailDialogEventHandler());
            int w = pengembalianBarangDetail.getWidth();
            int h = pengembalianBarangDetail.getHeight();
            pengembalianBarangDetail.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            pengembalianBarangDetail.setVisible(true);
        } else if (evt.getKeyCode() == 127 && this.pengembalianTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
            this.totalField.setValue(Float.valueOf(Float.parseFloat(this.totalField.getValue().toString()) - Float.parseFloat(this.pengembalianTableModel.getValueAt(this.pengembalianTable.getSelectedRow(), 6).toString())));
            this.pengembalianTableModel.removeRow(this.pengembalianTable.getSelectedRow());
        }
    }

    private void penjualanIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.penjualan = this.penjualanService.getPenjualan(this.penjualanIdField.getText());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = simpleDateFormat.format(new Date());
            String penjualanDate = simpleDateFormat.format(this.penjualan.getTanggal());
            LocalDate dateBefore = LocalDate.parse(penjualanDate);
            LocalDate dateAfter = LocalDate.parse(nowDate);
            long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter) + 1L;
            int jangkaWaktuPB = Integer.parseInt(this.konstantaService.getConstantValue("jangkaWaktuPengembalian"));
            if (this.penjualan == null) {
                JOptionPane.showMessageDialog(null, "ID Penjualan Tidak Ditemukan");
            } else if (noOfDaysBetween > (long)jangkaWaktuPB) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordSaveEventHandler());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Retur Sudah Melewati Jangka Waktu");
                passwordInputDialog2.setVisible(true);
            } else {
                this.penjualanIdField.setText(this.penjualan.getId());
                this.penjualanIdField.setEditable(false);
                this.karyawanField.setText(this.penjualan.getKaryawanId());
                this.customer = this.customerService.getCustomerById(this.penjualan.getCustomerId());
                this.customerLbl.setText(this.customer.getId());
                this.customerNameLbl.setText(this.customer.getNama());
                this.customerTelpLbl.setText(this.customer.getTelepon());
                this.customerBankLbl.setText(this.customer.getBank() + " - " + this.customer.getNomorRekening());
                this.selectorPembayaran.setSelectedItem("VOUCHER");
                this.pengembalianTable.requestFocus();
            }
        }
    }

    private void keluarBtn(ActionEvent evt) {
        int a = JOptionPane.showConfirmDialog(null, "Transaksi Pengembalian Barang Telah Selesai?", "Keluar Transaksi", 0, 3);
        if (a == 0) {
            this.listener.escapeClick();
        }
    }

    private void requestOTPButtonActionPerformed(ActionEvent evt) {
        String t_penjualan_id = this.penjualanIdField.getText();
        String m_customer_id = this.customerLbl.getText();
        String m_karyawan_id = this.helper.getKaryawan().getId();
        String pembayaran = (String)this.selectorPembayaran.getSelectedItem();
        String bank = this.customer.getBank();
        String nomor_rekening = this.customer.getNomorRekening();
        if (t_penjualan_id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID Penjualan Tidak Boleh Kosong");
        } else if (m_customer_id.isEmpty() || m_customer_id.equals("-")) {
            JOptionPane.showMessageDialog(null, "ID Customer Tidak Boleh Kosong");
        } else if (this.pengembalianTableModel.getRowCount() > 0) {
            OTPPengembalian op = new OTPPengembalian();
            op.setT_penjualan_id(t_penjualan_id);
            op.setM_customer_id(m_customer_id);
            op.setM_karyawan_id(m_karyawan_id);
            op.setPembayaran(pembayaran);
            op.setBank(bank);
            op.setNomor_rekening(nomor_rekening);
            ArrayList<OTPPengembalianDetail> opds = new ArrayList<OTPPengembalianDetail>();
            for (int a = 0; a < this.pengembalianTableModel.getRowCount(); ++a) {
                OTPPengembalianDetail opd = new OTPPengembalianDetail();
                opd.setId_sku(this.pengembalianTableModel.getValueAt(a, 0).toString());
                opd.setJumlah(Float.parseFloat(this.pengembalianTableModel.getValueAt(a, 2).toString()));
                opd.setSatuan(this.pengembalianTableModel.getValueAt(a, 3).toString());
                opd.setHarga(Long.parseLong(this.pengembalianTableModel.getValueAt(a, 4).toString()));
                opd.setDiscount(Float.parseFloat(this.pengembalianTableModel.getValueAt(a, 5).toString()));
                opds.add(opd);
            }
            int result = this.otpPengembalianService.requestOTPPengembalian(op, opds);
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Berhasil request OTP dengan ID: " + result);
            } else {
                JOptionPane.showMessageDialog(null, "Gagal Request OTP, Coba lagi...");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Masukkan Data Pengembalian Terlebih Dahulu");
        }
    }

    private void otpFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            String uc = this.otpField.getText();
            if (uc.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap Isi OTP");
            } else {
                OTPPengembalian op = this.otpPengembalianService.getOTPPengembalianByUniqueCode(uc);
                if (op == null) {
                    JOptionPane.showMessageDialog(null, "OTP tidak ditemukan / tidak valid");
                } else {
                    this.selectorPembayaran.setSelectedItem(op.getPembayaran());
                    this.alasanReturField.setText(op.getAlasan_batal());
                    this.selectorPembayaran.setEnabled(false);
                    this.selectorPembayaran.setEditable(false);
                }
            }
            this.pengembalianTable.requestFocus();
        }
    }

    private void selectorPembayaranActionPerformed(ActionEvent evt) {
        String selected = (String)this.selectorPembayaran.getSelectedItem();
        String m_customer_id = this.customerLbl.getText();
        if (m_customer_id.equals("C") && "CASH".equals(selected)) {
            JOptionPane.showMessageDialog(null, "Customer C tidak boleh retur CASH");
            this.selectorPembayaran.setSelectedItem("VOUCHER");
        }
        if (!this.tipeToko.equals("Minimarket") && "TRANSFER".equals(selected) && (this.customer.getBank() == null || this.customer.getNomorRekening() == null || this.customer.getBank().isEmpty() || this.customer.getNomorRekening().isEmpty())) {
            JOptionPane.showMessageDialog(null, "Bank dan Nomor Rekening Customer belum diisi, harap mengisi data Bank dan Nomor Rekening melalui Aplikasi Paperpen");
            this.selectorPembayaran.setSelectedItem("VOUCHER");
        }
    }

    private void selectorPembayaranKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.pengembalianTable.requestFocus();
        }
    }

    private void printRetur() {
        String uc = this.otpField.getText();
        if (uc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap Isi OTP");
        } else {
            OTPPengembalian op = this.otpPengembalianService.getOTPPengembalianByUniqueCode(uc);
            if (op == null) {
                JOptionPane.showMessageDialog(null, "OTP tidak ditemukan / tidak valid");
            } else {
                String t_penjualan_id = this.penjualanIdField.getText();
                String m_customer_id = this.customerLbl.getText();
                int id_cabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
                if (op.isUsed()) {
                    JOptionPane.showMessageDialog(null, "OTP sudah pernah digunakan untuk mencetak pengembalian");
                } else if (id_cabang != op.getId_cabang()) {
                    JOptionPane.showMessageDialog(null, "OTP bukan untuk cabang ini");
                } else if (!t_penjualan_id.equals(op.getT_penjualan_id())) {
                    JOptionPane.showMessageDialog(null, "OTP tidak dapat digunakan untuk ID Penjualan " + t_penjualan_id);
                } else if (m_customer_id == op.getM_customer_id()) {
                    JOptionPane.showMessageDialog(null, "OTP tidak dapat digunakan untuk ID Customer " + m_customer_id);
                } else if (this.konstantaService.getConstantValue("tipeToko").toUpperCase().equals("STATIONERY") && !op.isApprove_customer() && !op.getM_customer_id().equals("C")) {
                    JOptionPane.showMessageDialog(null, "OTP Belum mendapat Approval Customer. Customer dapat melakukan approval melalui aplikasi Paperpen.");
                } else {
                    boolean canGo = true;
                    List<OTPPengembalianDetail> opds = this.otpPengembalianService.getOTPPengembalianDetailById(op.getId());
                    if (opds.isEmpty()) {
                        canGo = false;
                        JOptionPane.showMessageDialog(null, "Tidak ada barang yang didaftarkan pada OTP ini");
                    }
                    HashMap<String, OTPPengembalianDetail> opdsMap = new HashMap<String, OTPPengembalianDetail>();
                    for (int i = 0; i < opds.size(); ++i) {
                        opdsMap.put(opds.get(i).getId_sku(), opds.get(i));
                    }
                    for (int a = 0; a < this.pengembalianTableModel.getRowCount(); ++a) {
                        PengembalianBarangDetail pengembalianBarangDetail = new PengembalianBarangDetail();
                        pengembalianBarangDetail.setBarangId(this.pengembalianTableModel.getValueAt(a, 0).toString());
                        pengembalianBarangDetail.setJumlah(Float.parseFloat(this.pengembalianTableModel.getValueAt(a, 2).toString()));
                        pengembalianBarangDetail.setSatuan(this.pengembalianTableModel.getValueAt(a, 3).toString());
                        pengembalianBarangDetail.setHarga(Long.parseLong(this.pengembalianTableModel.getValueAt(a, 4).toString()));
                        pengembalianBarangDetail.setTotalHarga(Long.parseLong(this.pengembalianTableModel.getValueAt(a, 6).toString()));
                        OTPPengembalianDetail opd = (OTPPengembalianDetail)opdsMap.get(pengembalianBarangDetail.getBarangId());
                        if (opd == null) {
                            canGo = false;
                            JOptionPane.showMessageDialog(null, "Barang dengan ID " + pengembalianBarangDetail.getBarangId() + " tidak terdaftar pada OTP, harap request OTP Baru");
                            break;
                        }
                        if (opd.getJumlah() != pengembalianBarangDetail.getJumlah()) {
                            canGo = false;
                            JOptionPane.showMessageDialog(null, "Barang dengan ID " + pengembalianBarangDetail.getBarangId() + " jumlah tidak sesuai, harap request OTP Baru");
                            break;
                        }
                        if (!opd.getSatuan().equals(pengembalianBarangDetail.getSatuan())) {
                            canGo = false;
                            JOptionPane.showMessageDialog(null, "Barang dengan ID " + pengembalianBarangDetail.getBarangId() + " satuan tidak sesuai, harap request OTP Baru");
                            break;
                        }
                        if (opd.getHarga() != pengembalianBarangDetail.getHarga()) {
                            canGo = false;
                            JOptionPane.showMessageDialog(null, "Barang dengan ID " + pengembalianBarangDetail.getBarangId() + " harga tidak sesuai, harap request OTP Baru");
                            break;
                        }
                        if ((float)opd.getHarga() * opd.getJumlah() - opd.getDiscount() == (float)pengembalianBarangDetail.getTotalHarga()) continue;
                        canGo = false;
                        JOptionPane.showMessageDialog(null, "Barang dengan ID " + pengembalianBarangDetail.getBarangId() + " harga total tidak sesuai, harap request OTP Baru");
                        break;
                    }
                    if (canGo) {
                        this.pembayaranGlobal = op.getPembayaran();
                        this.selectorPembayaran.setSelectedItem(op.getPembayaran());
                        this.selectorPembayaran.setEditable(false);
                        this.alasanReturField.setText(op.getAlasan_batal());
                        int result = this.otpPengembalianService.updateStatusUsedOTPPengembalian(op.getId());
                        if (result > 0) {
                            CetakPengembalianBarang cetakPengembalianBarang = new CetakPengembalianBarang(null, true, Math.round(Float.parseFloat(this.totalField.getValue() + "")), this.customer, this.penjualan, op.getPembayaran());
                            cetakPengembalianBarang.addListener(new cetakPengembalianBarangEventHandler());
                            int w = cetakPengembalianBarang.getWidth();
                            int h = cetakPengembalianBarang.getHeight();
                            cetakPengembalianBarang.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                            cetakPengembalianBarang.setVisible(true);
                            cetakPengembalianBarang.requestDefaultFocus();
                        } else {
                            JOptionPane.showMessageDialog(null, "Gagal menggunakan OTP, coba lagi");
                        }
                    }
                }
            }
        }
    }

    private int rataKanan(String price, int x) {
        int len = price.length();
        for (int i = 0; i < len; ++i) {
            x -= 4;
        }
        return x;
    }

    public void addTransaksiListener(PenjualanPanelListener transaksiListener) {
        this.listener = transaksiListener;
    }

    private class penjualanDetailDialogEventHandler
    implements PenjualanDetailDialogListener {
        private penjualanDetailDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue, boolean removeRow) {
            int a;
            boolean newData = true;
            for (a = 0; a < PengembalianBarangPanel.this.pengembalianTableModel.getRowCount(); ++a) {
                if (!PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PengembalianBarangPanel.this.pengembalianTableModel.addRow(rowValue);
                PengembalianBarangPanel.this.totalField.setValue(Float.valueOf(Float.parseFloat(PengembalianBarangPanel.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[6].toString())));
            } else {
                PengembalianBarangPanel.this.totalField.setValue(Float.valueOf(Float.parseFloat(PengembalianBarangPanel.this.totalField.getValue().toString()) - Float.parseFloat(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 6).toString())));
                PengembalianBarangPanel.this.totalField.setValue(Float.valueOf(Float.parseFloat(PengembalianBarangPanel.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[6].toString())));
                PengembalianBarangPanel.this.pengembalianTableModel.removeRow(a);
                PengembalianBarangPanel.this.pengembalianTableModel.addRow(rowValue);
            }
        }

        @Override
        public void deleteClick(String idBarang) {
            PengembalianBarangPanel.this.removeAllRow();
        }

        @Override
        public void refreshData() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DefaultTableModel getPenjualanTableModel() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void changeButtonPromoTebusMurah() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void changeButtonPromoTebus(String state, List<Object[]> data) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputDialogEventHandler3
    implements PasswordInputListener {
        private passwordInputDialogEventHandler3() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                String uc = PengembalianBarangPanel.this.otpField.getText();
                if (uc.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Harap Isi OTP");
                } else {
                    OTPPengembalian op = PengembalianBarangPanel.this.otpPengembalianService.getOTPPengembalianByUniqueCode(uc);
                    if (op == null) {
                        JOptionPane.showMessageDialog(null, "OTP tidak ditemukan / tidak valid");
                    } else {
                        String t_penjualan_id = PengembalianBarangPanel.this.penjualanIdField.getText();
                        String m_customer_id = PengembalianBarangPanel.this.customerLbl.getText();
                        int id_cabang = Integer.parseInt(PengembalianBarangPanel.this.konstantaService.getConstantValue("idCabang"));
                        if (op.isUsed()) {
                            JOptionPane.showMessageDialog(null, "OTP sudah pernah digunakan untuk mencetak pengembalian");
                        } else if (id_cabang != op.getId_cabang()) {
                            JOptionPane.showMessageDialog(null, "OTP bukan untuk cabang ini");
                        } else if (!t_penjualan_id.equals(op.getT_penjualan_id())) {
                            JOptionPane.showMessageDialog(null, "OTP tidak dapat digunakan untuk ID Penjualan " + t_penjualan_id);
                        } else if (!m_customer_id.equals(op.getM_customer_id())) {
                            JOptionPane.showMessageDialog(null, "OTP tidak dapat digunakan untuk ID Customer " + m_customer_id);
                        } else if (!op.isSameDayValid()) {
                            JOptionPane.showMessageDialog(null, "OTP sudah melewati batas hari request");
                        } else if ("CASH".equals(op.getPembayaran()) && !op.isApprove_finance()) {
                            JOptionPane.showMessageDialog(null, "OTP Belum mendapat approval team FINANCE");
                        } else if (PengembalianBarangPanel.this.tipeToko.equalsIgnoreCase("Stationery") && !op.getM_customer_id().equals("C") && !op.isApprove_customer()) {
                            JOptionPane.showMessageDialog(null, "OTP Belum mendapat Approval Customer. Customer dapat melakukan approval melalui aplikasi Paperpen.");
                        } else {
                            boolean canGo = true;
                            List<OTPPengembalianDetail> opds = PengembalianBarangPanel.this.otpPengembalianService.getOTPPengembalianDetailById(op.getId());
                            if (opds.isEmpty()) {
                                canGo = false;
                                JOptionPane.showMessageDialog(null, "Tidak ada barang yang didaftarkan pada OTP ini");
                            }
                            HashMap<String, OTPPengembalianDetail> opdsMap = new HashMap<String, OTPPengembalianDetail>();
                            for (int i = 0; i < opds.size(); ++i) {
                                opdsMap.put(opds.get(i).getId_sku(), opds.get(i));
                            }
                            for (int a = 0; a < PengembalianBarangPanel.this.pengembalianTableModel.getRowCount(); ++a) {
                                PengembalianBarangDetail pengembalianBarangDetail = new PengembalianBarangDetail();
                                pengembalianBarangDetail.setBarangId(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 0).toString());
                                pengembalianBarangDetail.setJumlah(Float.parseFloat(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 2).toString()));
                                pengembalianBarangDetail.setSatuan(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 3).toString());
                                pengembalianBarangDetail.setHarga(Long.parseLong(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 4).toString()));
                                pengembalianBarangDetail.setTotalHarga(Long.parseLong(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 6).toString()));
                                OTPPengembalianDetail opd = (OTPPengembalianDetail)opdsMap.get(pengembalianBarangDetail.getBarangId());
                                if (opd == null) {
                                    canGo = false;
                                    JOptionPane.showMessageDialog(null, "Barang dengan ID " + pengembalianBarangDetail.getBarangId() + " tidak terdaftar pada OTP, harap request OTP Baru");
                                    break;
                                }
                                if (opd.getJumlah() != pengembalianBarangDetail.getJumlah()) {
                                    canGo = false;
                                    JOptionPane.showMessageDialog(null, "Barang dengan ID " + pengembalianBarangDetail.getBarangId() + " jumlah tidak sesuai, harap request OTP Baru");
                                    break;
                                }
                                if (!opd.getSatuan().equals(pengembalianBarangDetail.getSatuan())) {
                                    canGo = false;
                                    JOptionPane.showMessageDialog(null, "Barang dengan ID " + pengembalianBarangDetail.getBarangId() + " satuan tidak sesuai, harap request OTP Baru");
                                    break;
                                }
                                if (opd.getHarga() != pengembalianBarangDetail.getHarga()) {
                                    canGo = false;
                                    JOptionPane.showMessageDialog(null, "Barang dengan ID " + pengembalianBarangDetail.getBarangId() + " harga tidak sesuai, harap request OTP Baru");
                                    break;
                                }
                                if ((float)opd.getHarga() * opd.getJumlah() - opd.getDiscount() == (float)pengembalianBarangDetail.getTotalHarga()) continue;
                                canGo = false;
                                JOptionPane.showMessageDialog(null, "Barang dengan ID " + pengembalianBarangDetail.getBarangId() + " harga total tidak sesuai, harap request OTP Baru");
                                break;
                            }
                            if (canGo) {
                                PengembalianBarangPanel.this.pembayaranGlobal = op.getPembayaran();
                                PengembalianBarangPanel.this.selectorPembayaran.setSelectedItem(op.getPembayaran());
                                PengembalianBarangPanel.this.selectorPembayaran.setEditable(false);
                                PengembalianBarangPanel.this.alasanReturField.setText(op.getAlasan_batal());
                                int result = PengembalianBarangPanel.this.otpPengembalianService.updateStatusUsedOTPPengembalian(op.getId());
                                if (result > 0) {
                                    CetakPengembalianBarang cetakPengembalianBarang = new CetakPengembalianBarang(null, true, Math.round(Float.parseFloat(PengembalianBarangPanel.this.totalField.getValue() + "")), PengembalianBarangPanel.this.customer, PengembalianBarangPanel.this.penjualan, op.getPembayaran());
                                    cetakPengembalianBarang.addListener(new cetakPengembalianBarangEventHandler());
                                    int w = cetakPengembalianBarang.getWidth();
                                    int h = cetakPengembalianBarang.getHeight();
                                    cetakPengembalianBarang.setLocation((PengembalianBarangPanel.this.screenWidth - w) / 2, (PengembalianBarangPanel.this.screenHeight - h) / 2);
                                    cetakPengembalianBarang.setVisible(true);
                                    cetakPengembalianBarang.requestDefaultFocus();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Gagal menggunakan OTP, coba lagi");
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputDialogEventHandler2
    implements PasswordInputListener {
        private passwordInputDialogEventHandler2() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) {
                CetakPengembalianBarang cetakPengembalianBarang = new CetakPengembalianBarang(null, true, Math.round(Float.parseFloat(PengembalianBarangPanel.this.totalField.getValue() + "")), PengembalianBarangPanel.this.customer, PengembalianBarangPanel.this.penjualan, PengembalianBarangPanel.this.pembayaranGlobal);
                cetakPengembalianBarang.addListener(new cetakPengembalianBarangEventHandler());
                int w = cetakPengembalianBarang.getWidth();
                int h = cetakPengembalianBarang.getHeight();
                cetakPengembalianBarang.setLocation((PengembalianBarangPanel.this.screenWidth - w) / 2, (PengembalianBarangPanel.this.screenHeight - h) / 2);
                cetakPengembalianBarang.setVisible(true);
                cetakPengembalianBarang.requestDefaultFocus();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
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
            g.drawString("RETUR PENJUALAN ", x + 0, y + yK);
            g.drawString("Voucher ID " + PengembalianBarangPanel.this.voucher.getId(), x + 0, y + (yK += yP));
            g.drawString("ID Pelanggan : " + PengembalianBarangPanel.this.customer.getId(), x + 0, y + (yK += yP));
            g.drawString("Nama Pelanggan : " + PengembalianBarangPanel.this.customer.getNama(), x + 0, y + (yK += yP));
            g.drawString("ID PB/ Penjualan : " + PengembalianBarangPanel.this.pengembalianBarang.getId() + "/" + PengembalianBarangPanel.this.pengembalianBarang.getPenjualanId(), x + 0, y + (yK += yP));
            g.drawString("Nilai Voucher : " + PengembalianBarangPanel.this.pengembalianBarang.getTotalPengembalian(), x + 0, y + (yK += yP));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(new Date());
            String jTempo = format.format(PengembalianBarangPanel.this.voucher.getExpiredDate());
            g.drawString("Tanggal: " + tanggal, x + 0, y + (yK += yP));
            g.drawString("ExpDate: " + jTempo, x + 0, y + (yK += yP));
            yK += yP;
            return 0;
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
            g.drawString("RETUR PENJUALAN", x + 0, y + yK);
            g.drawString("Cust: " + PengembalianBarangPanel.this.customer.getId() + " - " + PengembalianBarangPanel.this.customer.getNama(), x + 0, y + (yK += yP));
            g.drawString("ID: " + PengembalianBarangPanel.this.pengembalianBarang.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + PengembalianBarangPanel.this.helper.getKaryawan().getNama(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            float totalDiscount = 0.0f;
            long totalBelanja = 0L;
            for (int a = 0; a < PengembalianBarangPanel.this.pengembalianTableModel.getRowCount(); ++a) {
                g.drawString(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 1).toString(), x + 0, y + (yK += yP));
                yK = yK + yP - 1;
                float tempValue = Float.parseFloat(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 2).toString());
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)tempValue) : (Number)Float.valueOf(tempValue);
                g.drawString(value + PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 3).toString(), x + 20, y + yK);
                g.drawString("x", x + 55, y + yK);
                g.drawString(PengembalianBarangPanel.this.helper.formatNumber(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 4) + ""), x + 70, y + yK);
                g.drawString("=", x + 110, y + yK);
                int xtemp = PengembalianBarangPanel.this.rataKanan(PengembalianBarangPanel.this.helper.formatNumber((long)(Float.parseFloat(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 2).toString()) * Float.parseFloat(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 4).toString())) + ""), xRataKanan);
                g.drawString(PengembalianBarangPanel.this.helper.formatNumber((long)(Float.parseFloat(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 2).toString()) * Float.parseFloat(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 4).toString())) + ""), x + xtemp, y + yK);
                totalBelanja += (long)(Float.parseFloat(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 2).toString()) * Float.parseFloat(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 4).toString()));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            int xtemp = PengembalianBarangPanel.this.rataKanan(PengembalianBarangPanel.this.helper.formatNumber(totalBelanja + ""), xRataKanan);
            g.drawString(PengembalianBarangPanel.this.helper.formatNumber(totalBelanja + ""), x + xtemp, y + (yK += yP));
            g.drawString(PengembalianBarangPanel.this.pengembalianBarang.getKeterangan(), x + 0, y + (yK += yP));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(PengembalianBarangPanel.this.pengembalianBarang.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + PengembalianBarangPanel.this.pengembalianBarang.getTanggal().getHours() + ":" + PengembalianBarangPanel.this.pengembalianBarang.getTanggal().getMinutes() + ":" + PengembalianBarangPanel.this.pengembalianBarang.getTanggal().getSeconds(), x + 120, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            yK += yP;
            return 0;
        }
    }

    private class cetakPengembalianBarangEventHandler
    implements CetakPengembalianListener {
        private cetakPengembalianBarangEventHandler() {
        }

        @Override
        public void printClick(String keterangan, boolean cetakVoucher, String noHp) {
            long tempId = PengembalianBarangPanel.this.pengembalianBarangService.getLastId();
            tempId = tempId == 0L ? 1L : ++tempId;
            PengembalianBarangPanel.this.pengembalianBarang = new PengembalianBarang();
            PengembalianBarangPanel.this.pengembalianBarang.setId(tempId);
            PengembalianBarangPanel.this.pengembalianBarang.setPenjualanId(PengembalianBarangPanel.this.penjualanIdField.getText());
            PengembalianBarangPanel.this.pengembalianBarang.setCreated(new Date());
            PengembalianBarangPanel.this.pengembalianBarang.setCustomerId(PengembalianBarangPanel.this.customer.getId());
            PengembalianBarangPanel.this.pengembalianBarang.setKaryawanId(PengembalianBarangPanel.this.helper.getKaryawan().getId());
            PengembalianBarangPanel.this.pengembalianBarang.setKeterangan(keterangan);
            PengembalianBarangPanel.this.pengembalianBarang.setTanggal(new Date());
            PengembalianBarangPanel.this.pengembalianBarang.setTotalPengembalian(Math.round(Float.parseFloat(PengembalianBarangPanel.this.totalField.getValue() + "")));
            PengembalianBarangPanel.this.pengembalianBarang.setPelunasan(PengembalianBarangPanel.this.selectorPembayaran.getSelectedItem().toString());
            if (PengembalianBarangPanel.this.pengembalianBarang.getPelunasan().equals("CASH")) {
                try {
                    PengembalianBarangPanel.this.pengembalianBarang.setShiftKasirId(PengembalianBarangPanel.this.shiftKasir.getId());
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Shift Kasir Harus Aktif Untuk Retur Cash");
                }
            }
            if (cetakVoucher) {
                PengembalianBarangPanel.this.pengembalianBarang.setCetakVoucher(1);
            } else {
                PengembalianBarangPanel.this.pengembalianBarang.setCetakVoucher(0);
            }
            PengembalianBarangPanel.this.pengembalianBarang.setNoHp(noHp);
            int result = PengembalianBarangPanel.this.pengembalianBarangService.createPengembalianBarang(PengembalianBarangPanel.this.pengembalianBarang);
            int result2 = 0;
            int resultBarang = 0;
            if (result == 1) {
                for (int a = 0; a < PengembalianBarangPanel.this.pengembalianTableModel.getRowCount(); ++a) {
                    StockCommit stockCommit1;
                    PengembalianBarangDetail pengembalianBarangDetail = new PengembalianBarangDetail();
                    pengembalianBarangDetail.setPengembalianBarangId(PengembalianBarangPanel.this.pengembalianBarang.getId());
                    pengembalianBarangDetail.setBarangId(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 0).toString());
                    pengembalianBarangDetail.setJumlah(Float.parseFloat(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 2).toString()));
                    pengembalianBarangDetail.setSatuan(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 3).toString());
                    pengembalianBarangDetail.setHarga(Long.parseLong(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 4).toString()));
                    pengembalianBarangDetail.setTotalHarga(Long.parseLong(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 6).toString()));
                    Barang tempBarang = PengembalianBarangPanel.this.barangService.getBarang(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 0).toString());
                    float jumlahBrg = Float.parseFloat(PengembalianBarangPanel.this.pengembalianTableModel.getValueAt(a, 2).toString());
                    if (pengembalianBarangDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                        jumlahBrg *= (float)tempBarang.getKonversi21();
                    } else if (pengembalianBarangDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                        jumlahBrg = jumlahBrg * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32();
                    }
                    long hppBarang = tempBarang.getHpp() * (long)jumlahBrg;
                    long keuntungan = pengembalianBarangDetail.getTotalHarga() - hppBarang;
                    pengembalianBarangDetail.setTotalKeuntungan(keuntungan);
                    result2 = PengembalianBarangPanel.this.pengembalianBarangDetailService.createPengembalianBarangDetailDao(pengembalianBarangDetail);
                    Barang barang = PengembalianBarangPanel.this.barangService.getBarang(pengembalianBarangDetail.getBarangId());
                    float tempJumlah = pengembalianBarangDetail.getJumlah();
                    if (pengembalianBarangDetail.getSatuan().equals(barang.getSatuan2())) {
                        tempJumlah *= (float)barang.getKonversi21();
                    } else if (pengembalianBarangDetail.getSatuan().equals(barang.getSatuan3())) {
                        tempJumlah = tempJumlah * (float)barang.getKonversi21() * (float)barang.getKonversi32();
                    }
                    if (PengembalianBarangPanel.this.helper.getStatusProgram().equals("GUDANG")) {
                        stockCommit1 = new StockCommit();
                        stockCommit1.setIdBarang(pengembalianBarangDetail.getBarangId());
                        stockCommit1.setIdTransaksi(PengembalianBarangPanel.this.pengembalianBarang.getId() + "");
                        stockCommit1.setQueryType("tambah");
                        stockCommit1.setStockType("gudang");
                        stockCommit1.setTransactionType("Pengembalian Barang");
                        stockCommit1.setJumlahBarang(tempJumlah);
                        PengembalianBarangPanel.this.sinkronisasiBarangService.insertCommit(stockCommit1);
                        resultBarang = PengembalianBarangPanel.this.barangService.updateStokBarangGudang(tempJumlah, pengembalianBarangDetail.getBarangId());
                        continue;
                    }
                    if (!PengembalianBarangPanel.this.helper.getStatusProgram().equals("TOKO")) continue;
                    stockCommit1 = new StockCommit();
                    stockCommit1.setIdBarang(pengembalianBarangDetail.getBarangId());
                    stockCommit1.setIdTransaksi(PengembalianBarangPanel.this.pengembalianBarang.getId() + "");
                    stockCommit1.setQueryType("tambah");
                    stockCommit1.setStockType("toko");
                    stockCommit1.setTransactionType("Pengembalian Barang");
                    stockCommit1.setJumlahBarang(tempJumlah);
                    PengembalianBarangPanel.this.sinkronisasiBarangService.insertCommit(stockCommit1);
                    resultBarang = PengembalianBarangPanel.this.barangService.updateStokBarangToko(tempJumlah, pengembalianBarangDetail.getBarangId());
                }
                int tempPoin = Math.round(-1.0f * Float.parseFloat(PengembalianBarangPanel.this.totalField.getValue() + "") / Float.parseFloat(PengembalianBarangPanel.this.konstantaService.getConstantValue("pointLain")));
                PengembalianBarangPanel.this.customerService.updatePointCustomer(tempPoin, PengembalianBarangPanel.this.customer.getId());
                PengembalianBarangPanel.this.customerPointCommitService.insertPointCommit(PengembalianBarangPanel.this.customer.getId(), Long.toString(PengembalianBarangPanel.this.pengembalianBarang.getId()), tempPoin, "INSERT", "Pengembalian");
            }
            if (result == 1 && result2 == 1 && resultBarang == 1) {
                if (cetakVoucher) {
                    PengembalianBarangPanel.this.voucher = new Voucher();
                    PengembalianBarangPanel.this.voucher.setId(PengembalianBarangPanel.this.helper.generateCodeGeneral(PengembalianBarangPanel.this.voucherService.getLastId()));
                    PengembalianBarangPanel.this.voucher.setCustomerId(PengembalianBarangPanel.this.customer.getId());
                    PengembalianBarangPanel.this.voucher.setJumlah(Math.toIntExact(PengembalianBarangPanel.this.pengembalianBarang.getTotalPengembalian()));
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(5, 0);
                    PengembalianBarangPanel.this.voucher.setExpiredDate(c.getTime());
                    PengembalianBarangPanel.this.voucher.setAvailable("TRUE");
                    int voucherStatue = PengembalianBarangPanel.this.voucherService.createVoucher(PengembalianBarangPanel.this.voucher);
                    JOptionPane.showMessageDialog(null, "Pengembalian barang Berhasil");
                    if (voucherStatue == 1) {
                        JOptionPane.showMessageDialog(null, "Voucher telah berhasil dibuat");
                    }
                    PrinterJob job = PrinterJob.getPrinterJob();
                    PageFormat pf = job.defaultPage();
                    Paper p = pf.getPaper();
                    p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                    p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                    pf.setPaper(p);
                    Book book = new Book();
                    book.append(new serverPrinter(), pf);
                    job.setPageable(book);
                    try {
                        job.print();
                    }
                    catch (PrinterException printerException) {
                        // empty catch block
                    }
                    PengembalianBarangPanel.this.removeAllRow();
                    PengembalianBarangPanel.this.totalField.setValue(0);
                } else {
                    PrinterJob job = PrinterJob.getPrinterJob();
                    PageFormat pf = job.defaultPage();
                    Paper p = pf.getPaper();
                    p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                    p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                    pf.setPaper(p);
                    Book book = new Book();
                    book.append(new printer(), pf);
                    job.setPageable(book);
                    try {
                        job.print();
                    }
                    catch (PrinterException printerException) {
                        // empty catch block
                    }
                    PengembalianBarangPanel.this.removeAllRow();
                    PengembalianBarangPanel.this.totalField.setValue(0);
                }
            }
            PengembalianBarangPanel.this.syncService.doSyncVoucherUnsynced();
            PengembalianBarangPanel.this.syncService.doSyncPengembalianBarang();
            PengembalianBarangPanel.this.syncService.doSyncPengembalianBarangDetail();
        }
    }

    private class passwordSaveEventHandler
    implements PasswordInputListener {
        private passwordSaveEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) {
                PengembalianBarangPanel.this.penjualanIdField.setText(PengembalianBarangPanel.this.penjualan.getId());
                PengembalianBarangPanel.this.penjualanIdField.setEditable(false);
                PengembalianBarangPanel.this.karyawanField.setText(PengembalianBarangPanel.this.penjualan.getKaryawanId());
                PengembalianBarangPanel.this.customer = PengembalianBarangPanel.this.customerService.getCustomerById(PengembalianBarangPanel.this.penjualan.getCustomerId());
                PengembalianBarangPanel.this.customerLbl.setText(PengembalianBarangPanel.this.customer.getId());
                PengembalianBarangPanel.this.customerNameLbl.setText(PengembalianBarangPanel.this.customer.getNama());
                PengembalianBarangPanel.this.customerTelpLbl.setText(PengembalianBarangPanel.this.customer.getTelepon());
                PengembalianBarangPanel.this.customerBankLbl.setText(PengembalianBarangPanel.this.customer.getBank() + " - " + PengembalianBarangPanel.this.customer.getNomorRekening());
                PengembalianBarangPanel.this.pengembalianTable.requestFocus();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PengembalianBarangPanel.this.customer = PengembalianBarangPanel.this.customerService.getCustomerById(chooseValue);
            PengembalianBarangPanel.this.pengembalianTable.requestFocus();
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
            List<Customer> customers = PengembalianBarangPanel.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Customer");
            sp.addSearchPanelListener(new searchPanelCustomerEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }
}

