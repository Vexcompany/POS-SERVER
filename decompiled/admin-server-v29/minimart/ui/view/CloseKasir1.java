/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
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
import minimart.engine.controller.Helper;
import minimart.engine.model.Karyawan;
import minimart.engine.model.ShiftKasir;
import minimart.engine.service.PelunasanPiutangService;
import minimart.engine.service.PengembalianBarangService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.ShiftKasirService;
import minimart.engine.service.VoucherService;
import minimart.ui.listener.CloseKasirListener;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.PasswordInputDialog;

public class CloseKasir1
extends JDialog {
    private JButton batalBtn;
    private JTextField cashField;
    private JFormattedTextField debitField;
    private JLabel dibukaLbl;
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
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel25;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JSeparator jSeparator1;
    private JSeparator jSeparator3;
    private JSeparator jSeparator4;
    private JSeparator jSeparator5;
    private JSeparator jSeparator6;
    private JSeparator jSeparator7;
    private JTabbedPane jTabbedPane4;
    private JTable jTable1;
    private JTable jTable2;
    private JFormattedTextField kreditField;
    private JFormattedTextField netField;
    private JFormattedTextField nonTunaiField;
    private JLabel openingLbl;
    private JFormattedTextField pelunasanCustField;
    private JFormattedTextField qrisField;
    private JFormattedTextField returField;
    private JFormattedTextField selisihField;
    private JFormattedTextField setoranField;
    private JButton simpanDataBtn;
    private JLabel tanggalLbl;
    private JFormattedTextField totalField;
    private JFormattedTextField totalPenjualanField;
    private JFormattedTextField transferField;
    private JFormattedTextField tunaiField;
    private JFormattedTextField voucherField;
    private ShiftKasir shiftKasir;
    private Helper helper = Helper.getInstance();
    private int screenWidth = 0;
    private int screenHeight = 0;
    private boolean canGoThrough;
    private CloseKasirListener closeKasirListener;
    private ShiftKasirService shiftKasirService = new ShiftKasirService();
    private PenjualanService penjualanService = new PenjualanService();
    private boolean kasirIsClosed = false;
    private VoucherService voucherService = new VoucherService();
    private PelunasanPiutangService pelunasanPiutangService = new PelunasanPiutangService();
    private String[] detailPenjualanCol = new String[]{"ID", "ID CUSTOMER", "TANGGAL", "TOTAL PENJUALAN", "KETERANGAN", "VOUCHER"};
    private DefaultTableModel detailPenjualanTM = new DefaultTableModel(new String[0][1], this.detailPenjualanCol);
    private String[] detailPelunasanPiutangCol = new String[]{"ID PENJUALAN", "TANGGAL PELUNASAN", "JUMLAH PELUNASAN", "KETERANGAN"};
    private DefaultTableModel detailPelunasanPiutangTM = new DefaultTableModel(new String[0][1], this.detailPelunasanPiutangCol);

    public CloseKasir1(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.shiftKasir = this.shiftKasirService.getOpenedShiftKasir(this.helper.getKasir());
        this.dibukaLbl.setText(this.shiftKasir.getOpenedBy());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        this.tanggalLbl.setText(dateFormat.format(this.shiftKasir.getOpening()));
        this.openingLbl.setText(timeFormat.format(this.shiftKasir.getOpening()));
        this.totalPenjualanField.setEditable(false);
        this.kreditField.setEditable(false);
        this.transferField.setEditable(false);
        this.debitField.setEditable(false);
        this.tunaiField.setEditable(false);
        this.setoranField.setEditable(false);
        this.selisihField.setEditable(false);
        this.cashField.requestFocus();
        this.cashField.selectAll();
    }

    private void initComponents() {
        this.jLabel4 = new JLabel();
        this.jSeparator3 = new JSeparator();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel6 = new JLabel();
        this.dibukaLbl = new JLabel();
        this.tanggalLbl = new JLabel();
        this.openingLbl = new JLabel();
        this.cashField = new JTextField();
        this.jLabel9 = new JLabel();
        this.jTabbedPane4 = new JTabbedPane();
        this.jPanel1 = new JPanel();
        this.jLabel11 = new JLabel();
        this.totalPenjualanField = new JFormattedTextField();
        this.jLabel10 = new JLabel();
        this.kreditField = new JFormattedTextField();
        this.jLabel8 = new JLabel();
        this.jLabel7 = new JLabel();
        this.transferField = new JFormattedTextField();
        this.debitField = new JFormattedTextField();
        this.jSeparator1 = new JSeparator();
        this.jLabel12 = new JLabel();
        this.jLabel13 = new JLabel();
        this.setoranField = new JFormattedTextField();
        this.tunaiField = new JFormattedTextField();
        this.jLabel14 = new JLabel();
        this.jLabel15 = new JLabel();
        this.selisihField = new JFormattedTextField();
        this.jLabel16 = new JLabel();
        this.voucherField = new JFormattedTextField();
        this.pelunasanCustField = new JFormattedTextField();
        this.jLabel17 = new JLabel();
        this.jLabel20 = new JLabel();
        this.netField = new JFormattedTextField();
        this.jSeparator4 = new JSeparator();
        this.jLabel19 = new JLabel();
        this.jLabel18 = new JLabel();
        this.nonTunaiField = new JFormattedTextField();
        this.jSeparator5 = new JSeparator();
        this.jLabel21 = new JLabel();
        this.jSeparator6 = new JSeparator();
        this.jLabel22 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jSeparator7 = new JSeparator();
        this.jLabel23 = new JLabel();
        this.jLabel5 = new JLabel();
        this.qrisField = new JFormattedTextField();
        this.returField = new JFormattedTextField();
        this.jLabel25 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.jScrollPane2 = new JScrollPane();
        this.jTable2 = new JTable();
        this.batalBtn = new JButton();
        this.simpanDataBtn = new JButton();
        this.jLabel4.setText("jLabel4");
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Dibuka Oleh");
        this.jLabel2.setText("Tanggal");
        this.jLabel3.setText("Opening");
        this.jLabel6.setFont(new Font("Tahoma", 0, 14));
        this.jLabel6.setText("PENUTUPAN SHIFT KASIR");
        this.dibukaLbl.setText("-");
        this.tanggalLbl.setText("-");
        this.openingLbl.setText("-");
        this.cashField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CloseKasir1.this.cashFieldKeyPressed(evt);
            }
        });
        this.jLabel9.setText("Cash");
        this.jLabel11.setText("Total Penjualan");
        this.jLabel10.setText("Penjualan Kredit");
        this.jLabel8.setText("Transfer");
        this.jLabel7.setText("Debit");
        this.jSeparator1.setOrientation(1);
        this.jLabel12.setText("Uang Tunai");
        this.jLabel13.setText("Uang Setoran");
        this.jLabel14.setFont(new Font("Tahoma", 1, 11));
        this.jLabel14.setText("-");
        this.jLabel15.setText("Selisih");
        this.jLabel16.setText("Voucher");
        this.jLabel17.setText("Pelunasan Cust");
        this.jLabel20.setText("Net Penjualan");
        this.jLabel19.setText("-");
        this.jLabel18.setText("Non Tunai");
        this.jLabel21.setText("-");
        this.jLabel22.setText("+");
        this.jLabel23.setText("Detail Non Tunai");
        this.jLabel5.setText("Qris");
        this.returField.setToolTipText("");
        this.jLabel25.setText("Retur Cash");
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel10).addComponent(this.jLabel11, GroupLayout.Alignment.TRAILING)).addComponent(this.jLabel13).addComponent(this.jLabel15)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.kreditField, -1, 127, Short.MAX_VALUE).addComponent(this.totalPenjualanField).addComponent(this.setoranField).addComponent(this.jSeparator7).addComponent(this.selisihField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel14).addGap(44, 44, 44)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel1Layout.createSequentialGroup().addGap(36, 36, 36).addComponent(this.jLabel12).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.tunaiField)).addComponent(this.jSeparator5).addGroup(jPanel1Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel18, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel17, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel20, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel25, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.pelunasanCustField, -1, 125, Short.MAX_VALUE).addComponent(this.totalField, GroupLayout.Alignment.TRAILING).addComponent(this.nonTunaiField, GroupLayout.Alignment.TRAILING).addComponent(this.jSeparator6).addComponent(this.netField, GroupLayout.Alignment.TRAILING).addComponent(this.jSeparator4, GroupLayout.Alignment.TRAILING).addComponent(this.returField, GroupLayout.Alignment.TRAILING)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel21).addComponent(this.jLabel22).addComponent(this.jLabel19, -2, 18, -2)).addGap(32, 32, 32))).addComponent(this.jSeparator1, -2, -1, -2).addGap(10, 10, 10).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel7).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.debitField, -2, 120, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel8).addComponent(this.jLabel23)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.transferField, -2, 120, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel5).addComponent(this.jLabel16)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.qrisField, GroupLayout.Alignment.TRAILING).addComponent(this.voucherField, GroupLayout.Alignment.TRAILING, -1, 120, Short.MAX_VALUE)))).addGap(43, 43, 43)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jSeparator1, -2, 283, -2).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel23).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.transferField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.debitField, -2, -1, -2).addComponent(this.jLabel7)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.qrisField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.voucherField, -2, -1, -2).addComponent(this.jLabel16))))).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel19).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel11).addComponent(this.totalPenjualanField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel10).addComponent(this.kreditField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.returField, -2, -1, -2).addComponent(this.jLabel25)).addGap(7, 7, 7).addComponent(this.jSeparator4, -2, 10, -2))).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.netField, -2, -1, -2).addComponent(this.jLabel20)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.pelunasanCustField, -2, -1, -2).addComponent(this.jLabel17)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jSeparator6, -2, 10, -2)).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGap(69, 69, 69).addComponent(this.jLabel22))))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.nonTunaiField, -2, -1, -2).addComponent(this.jLabel18)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jSeparator5, -2, 2, -2)).addComponent(this.jLabel21)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tunaiField, -2, -1, -2).addComponent(this.jLabel12)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.setoranField, -2, -1, -2).addComponent(this.jLabel13)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jSeparator7, -2, 10, -2)).addComponent(this.jLabel14)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.selisihField, -2, -1, -2).addComponent(this.jLabel15)).addContainerGap()));
        this.jTabbedPane4.addTab("Selisih Setoran", this.jPanel1);
        this.jTable1.setModel(this.detailPenjualanTM);
        this.jScrollPane1.setViewportView(this.jTable1);
        this.jTabbedPane4.addTab("Detail Penjualan", this.jScrollPane1);
        this.jTable2.setModel(this.detailPelunasanPiutangTM);
        this.jScrollPane2.setViewportView(this.jTable2);
        this.jTabbedPane4.addTab("Detail Pelunasan Piutang", this.jScrollPane2);
        this.batalBtn.setText("Batal");
        this.batalBtn.setPreferredSize(new Dimension(73, 23));
        this.batalBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CloseKasir1.this.batalBtnActionPerformed(evt);
            }
        });
        this.batalBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CloseKasir1.this.batalBtnKeyPressed(evt);
            }
        });
        this.simpanDataBtn.setText("Simpan Data");
        this.simpanDataBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CloseKasir1.this.simpanDataBtnActionPerformed(evt);
            }
        });
        this.simpanDataBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CloseKasir1.this.simpanDataBtnKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jTabbedPane4).addComponent(this.jLabel6).addGroup(layout.createSequentialGroup().addGap(104, 104, 104).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.openingLbl, -2, 174, -2).addGroup(layout.createSequentialGroup().addComponent(this.tanggalLbl, -2, 174, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.simpanDataBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.batalBtn, -2, -1, -2)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3)).addGap(48, 48, 48).addComponent(this.dibukaLbl, -2, 177, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel9).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.cashField, -2, 174, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel6).addGap(9, 9, 9).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.dibukaLbl).addComponent(this.cashField, -2, -1, -2).addComponent(this.jLabel9)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(13, 13, 13).addComponent(this.jLabel2)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tanggalLbl).addComponent(this.batalBtn, -2, -1, -2).addComponent(this.simpanDataBtn)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.openingLbl)).addGap(26, 26, 26).addComponent(this.jTabbedPane4, -1, 473, Short.MAX_VALUE)));
        this.pack();
    }

    private void batalBtnActionPerformed(ActionEvent evt) {
        if (this.kasirIsClosed) {
            this.closeKasirListener.keluarPenjualan();
            this.dispose();
        } else {
            this.dispose();
        }
    }

    private void simpanDataBtnActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void saveClick() {
        this.canGoThrough = false;
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Kasir Akan Ditutup dan Dilanjutkan Dengan hasil keuangan, Lanjutkan?");
        confirmationDialog.addListener(new confirmDialogEventHandler());
        int w = confirmationDialog.getWidth();
        int h = confirmationDialog.getHeight();
        confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        confirmationDialog.setVisible(true);
        if (this.canGoThrough) {
            if (this.helper.isInteger(this.cashField.getText())) {
                this.cashField.setBackground(Color.white);
                this.simpanDataBtn.setEnabled(false);
                long jumlahVoucher = this.penjualanService.getTotalVoucherShift(this.helper.getKasir(), "");
                this.voucherField.setValue(jumlahVoucher);
                this.totalPenjualanField.setValue(this.penjualanService.getTotalOpenedShift(this.helper.getKasir(), ""));
                this.kreditField.setValue(this.penjualanService.getTotalOpenedShift(this.helper.getKasir(), "KREDIT"));
                this.returField.setValue(new PengembalianBarangService().getTotalPengembalianBarang(this.shiftKasir.getId(), "CASH"));
                this.netField.setValue((Long)this.totalPenjualanField.getValue() - (Long)this.kreditField.getValue() - (Long)this.returField.getValue());
                this.transferField.setValue(this.penjualanService.getTotalOpenedShift(this.helper.getKasir(), "TRANSFER") - this.penjualanService.getTotalVoucherShift(this.helper.getKasir(), "TRANSFER") + this.pelunasanPiutangService.getTotalPelunasanPiutangShift(this.shiftKasir.getId(), "TRANSFER"));
                this.debitField.setValue(this.penjualanService.getTotalOpenedShift(this.helper.getKasir(), "DEBIT") - this.penjualanService.getTotalVoucherShift(this.helper.getKasir(), "DEBIT") + this.pelunasanPiutangService.getTotalPelunasanPiutangShift(this.shiftKasir.getId(), "DEBIT"));
                this.qrisField.setValue(this.penjualanService.getTotalOpenedShift(this.helper.getKasir(), "QRIS") - this.penjualanService.getTotalVoucherShift(this.helper.getKasir(), "QRIS") + this.pelunasanPiutangService.getTotalPelunasanPiutangShift(this.shiftKasir.getId(), "QRIS"));
                this.pelunasanCustField.setValue(this.pelunasanPiutangService.getTotalPelunasanPiutangShift(this.shiftKasir.getId(), ""));
                this.totalField.setValue((Long)this.netField.getValue() + (Long)this.pelunasanCustField.getValue());
                this.nonTunaiField.setValue((Long)this.transferField.getValue() + (Long)this.debitField.getValue() + (Long)this.voucherField.getValue() + (Long)this.qrisField.getValue());
                this.tunaiField.setValue(this.penjualanService.getTotalOpenedShift(this.helper.getKasir(), "") - (Long)this.kreditField.getValue() - (Long)this.returField.getValue() - (Long)this.transferField.getValue() - (Long)this.debitField.getValue() - (Long)this.qrisField.getValue() - jumlahVoucher + (Long)this.pelunasanCustField.getValue());
                long setoran = Long.parseLong(this.cashField.getText());
                this.setoranField.setValue(setoran);
                long selisih = setoran - Long.parseLong(this.tunaiField.getValue() + "");
                this.selisihField.setValue(selisih);
                try {
                    this.shiftKasir.setTotalUang((Long)this.tunaiField.getValue());
                }
                catch (Exception e) {
                    this.shiftKasir.setTotalUang(0L);
                }
                this.shiftKasir.setJumlahSetoran(Long.parseLong(this.cashField.getText()));
                this.shiftKasir.setTransfer((Long)this.transferField.getValue());
                this.shiftKasir.setDebit((Long)this.debitField.getValue());
                this.shiftKasir.setQris((Long)this.qrisField.getValue());
                this.shiftKasir.setVoucher((Long)this.voucherField.getValue());
                this.shiftKasir.setClosing(new Timestamp(System.currentTimeMillis()));
                this.shiftKasir.setClosedBy(this.helper.getKaryawan().getId());
                int result = this.shiftKasirService.update(this.shiftKasir);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "Shift Telah Ditutup, data berhasil tersimpan");
                    this.penjualanService.updatePenjualanShift(this.shiftKasir.getId());
                    List<Object[]> dataPenjualans = this.penjualanService.getPenjualansByShift(this.shiftKasir.getId());
                    List<Object[]> dataPelunasan = this.pelunasanPiutangService.getPelunasanPiutangByShift(this.shiftKasir.getId());
                    for (Object[] dataPenjualan : dataPenjualans) {
                        this.detailPenjualanTM.addRow(dataPenjualan);
                    }
                    for (Object[] listDataPelunasan : dataPelunasan) {
                        this.detailPelunasanPiutangTM.addRow(listDataPelunasan);
                    }
                    this.closeKasirListener.keluarPenjualan();
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
                    catch (PrinterException printerException) {}
                } else {
                    JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
                }
            } else if (!this.helper.isInteger(this.cashField.getText())) {
                this.cashField.setBackground(Color.red);
            }
        }
    }

    private void batalBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 37) {
            this.simpanDataBtn.requestFocus();
        }
    }

    private void cashFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 155) {
            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
            passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
            int w = passwordInputDialog2.getWidth();
            int h = passwordInputDialog2.getHeight();
            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            passwordInputDialog2.setJudulText("Input Password");
            passwordInputDialog2.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 10) {
            this.simpanDataBtn.requestFocus();
        }
    }

    private void simpanDataBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 39) {
            this.batalBtn.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.cashField.requestFocus();
            this.cashField.selectAll();
        } else if (evt.getKeyCode() == 10) {
            this.saveClick();
        }
    }

    public void setListener(CloseKasirListener listener) {
        this.closeKasirListener = listener;
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
            g.drawString("ID KASIR - " + CloseKasir1.this.helper.getKasir(), x + 0, y + yK);
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            g.drawString("Opened By : " + CloseKasir1.this.shiftKasir.getOpenedBy(), x + 0, y + (yK += yP));
            g.drawString("Closed By : " + CloseKasir1.this.shiftKasir.getClosedBy(), x + 0, y + (yK += yP));
            g.drawString("Opening : " + CloseKasir1.this.shiftKasir.getOpening().toString(), x + 0, y + (yK += yP));
            g.drawString("Closing : " + CloseKasir1.this.shiftKasir.getClosing().toString(), x + 0, y + (yK += yP));
            g.drawString("Penjualan : " + CloseKasir1.this.totalPenjualanField.getValue(), x + 0, y + (yK += yP));
            g.drawString("Penjualan Kredit : " + CloseKasir1.this.kreditField.getValue(), x + 0, y + (yK += yP));
            g.drawString("Retur Penjualan : " + CloseKasir1.this.returField.getValue(), x + 0, y + (yK += yP));
            g.drawString("Pelunasan Customer : " + CloseKasir1.this.pelunasanCustField.getValue(), x + 0, y + (yK += yP));
            g.drawString("Transfer : " + CloseKasir1.this.shiftKasir.getTransfer(), x + 0, y + (yK += yP));
            g.drawString("Debit : " + CloseKasir1.this.shiftKasir.getDebit(), x + 0, y + (yK += yP));
            g.drawString("Qris : " + CloseKasir1.this.shiftKasir.getQris(), x + 0, y + (yK += yP));
            g.drawString("Voucher : " + CloseKasir1.this.shiftKasir.getVoucher(), x + 0, y + (yK += yP));
            g.drawString("Total Tunai : " + CloseKasir1.this.tunaiField.getValue(), x + 0, y + (yK += yP));
            g.drawString("Total Setoran : " + CloseKasir1.this.shiftKasir.getJumlahSetoran(), x + 0, y + (yK += yP));
            g.drawString("Selisih : " + (CloseKasir1.this.shiftKasir.getJumlahSetoran() - (Long)CloseKasir1.this.tunaiField.getValue()), x + 0, y + (yK += yP));
            yK += yP;
            return 0;
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            CloseKasir1.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            CloseKasir1.this.canGoThrough = false;
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if ((karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) && (CloseKasir1.this.helper.getKaryawan().getRole().equals("DIREKTUR") || CloseKasir1.this.helper.getKaryawan().getRole().equals("ACCOUNTING"))) {
                JOptionPane.showMessageDialog(null, "Cash = " + CloseKasir1.this.penjualanService.getTotalOpenedShift(CloseKasir1.this.helper.getKasir(), "CASH") + "\nDebit = " + CloseKasir1.this.penjualanService.getTotalOpenedShift(CloseKasir1.this.helper.getKasir(), "DEBIT") + "\nKredit = " + CloseKasir1.this.penjualanService.getTotalOpenedShift(CloseKasir1.this.helper.getKasir(), "KREDIT") + "\nTransfer = " + CloseKasir1.this.penjualanService.getTotalOpenedShift(CloseKasir1.this.helper.getKasir(), "TRANSFER"));
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

