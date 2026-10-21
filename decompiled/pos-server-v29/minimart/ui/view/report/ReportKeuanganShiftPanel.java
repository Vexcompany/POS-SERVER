/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.ShiftKasir;
import minimart.engine.service.KasirService;
import minimart.engine.service.KeuanganKasirService;
import minimart.engine.service.PelunasanPiutangService;
import minimart.engine.service.PengembalianBarangService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.ShiftKasirService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportKeuanganShiftPanel
extends JPanel {
    private JFormattedTextField debitField;
    private JLabel jLabel1;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel18;
    private JLabel jLabel19;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel24;
    private JLabel jLabel25;
    private JLabel jLabel26;
    private JLabel jLabel27;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JSeparator jSeparator4;
    private JSeparator jSeparator7;
    private JTabbedPane jTabbedPane1;
    private JTable jTable1;
    private JTable jTable2;
    private JButton keluarBtn;
    private JFormattedTextField netPenjualanField;
    private JFormattedTextField nonTunaiField;
    private JFormattedTextField pelunasanCustField;
    private JFormattedTextField penjualanKreditField;
    private JFormattedTextField qrisField;
    private JTable reportTable;
    private JFormattedTextField returCashField;
    private JFormattedTextField selisihField;
    private JButton tampilkanBtn;
    private JDateChooser tanggalField;
    private JFormattedTextField totalField;
    private JFormattedTextField totalPenjualanField;
    private JFormattedTextField transferField;
    private JFormattedTextField uangSetoranField;
    private JFormattedTextField uangTunaiField;
    private JFormattedTextField voucherField;
    private String[] reportColumn = new String[]{"ID SHIFT", "ID KASIR", "Opened By", "Closed By", "Opening", "Closing", "Total Penjualan", "Penjualan Kredit", "Retur Cash", "Pelunasan Cust", "Transfer", "Debit", "Voucher", "QRIS", "Uang Tunai", "Setoran", "Selisih"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private KeuanganKasirService keuanganKasirService = new KeuanganKasirService();
    private KasirService kasirService = new KasirService();
    private ShiftKasirService shiftKasirService = new ShiftKasirService();
    private DefaultPanelListener listener;
    private PenjualanService penjualanService = new PenjualanService();
    private PelunasanPiutangService pelunasanPiutangService = new PelunasanPiutangService();
    private PengembalianBarangService pengembalianBarangService = new PengembalianBarangService();
    private String[] detailPenjualanCol = new String[]{"ID", "ID CUSTOMER", "TANGGAL", "TOTAL PENJUALAN", "KETERANGAN", "VOUCHER"};
    private DefaultTableModel detailPenjualanTM = new DefaultTableModel(new String[0][1], this.detailPenjualanCol);
    private String[] detailPelunasanPiutangCol = new String[]{"ID PENJUALAN", "TANGGAL PELUNASAN", "JUMLAH PELUNASAN", "KETERANGAN"};
    private DefaultTableModel detailPelunasanPiutangTM = new DefaultTableModel(new String[0][1], this.detailPelunasanPiutangCol);

    public ReportKeuanganShiftPanel() {
        this.initComponents();
        this.tanggalField.setDate(new Date());
        this.jScrollPane1.setHorizontalScrollBarPolicy(32);
        this.reportTable.setAutoResizeMode(0);
    }

    public void setDefault() {
        this.reportTable.requestFocus();
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
        int size1 = this.detailPenjualanTM.getRowCount();
        for (int a = 0; a < size1; ++a) {
            this.detailPenjualanTM.removeRow(0);
        }
        int size2 = this.detailPelunasanPiutangTM.getRowCount();
        for (int a = 0; a < size2; ++a) {
            this.detailPelunasanPiutangTM.removeRow(0);
        }
    }

    private void loadData() {
        this.clearTable();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        List<ShiftKasir> skasirs = this.shiftKasirService.getShifts(format.format(this.tanggalField.getDate()), format.format(this.tanggalField.getDate()));
        long totalPenjualan = 0L;
        long penjualanKredit = 0L;
        long returCash = 0L;
        long netPenjualan = 0L;
        long pelunasanCust = 0L;
        long total = 0L;
        long nonTunai = 0L;
        long uangTunai = 0L;
        long uangSetoran = 0L;
        long selisih = 0L;
        long transfer = 0L;
        long debit = 0L;
        long qris = 0L;
        long voucher = 0L;
        int allshift = 0;
        for (ShiftKasir skasir : skasirs) {
            Object[] rowValue = new Object[17];
            rowValue[0] = skasir.getId();
            rowValue[1] = skasir.getIdKasir();
            rowValue[2] = skasir.getOpenedBy();
            rowValue[3] = skasir.getClosedBy();
            rowValue[4] = skasir.getOpening();
            rowValue[5] = skasir.getClosing();
            if (skasir.getClosing() != null) {
                rowValue[6] = this.penjualanService.getTotalPenjualanByShift(skasir.getId(), "");
                rowValue[7] = this.penjualanService.getTotalPenjualanByShift(skasir.getId(), "KREDIT");
                rowValue[8] = this.pengembalianBarangService.getTotalPengembalianBarang(skasir.getId(), "CASH");
                rowValue[9] = this.pelunasanPiutangService.getTotalPelunasanPiutangShift(skasir.getId(), "");
                rowValue[10] = this.penjualanService.getTotalPenjualanByShift(skasir.getId(), "TRANSFER") - this.penjualanService.getTotalVoucherByIdShift(skasir.getId(), "TRANSFER") + this.pelunasanPiutangService.getTotalPelunasanPiutangShift(skasir.getId(), "TRANSFER");
                rowValue[11] = this.penjualanService.getTotalPenjualanByShift(skasir.getId(), "DEBIT") - this.penjualanService.getTotalVoucherByIdShift(skasir.getId(), "DEBIT") + this.pelunasanPiutangService.getTotalPelunasanPiutangShift(skasir.getId(), "DEBIT");
                rowValue[12] = this.penjualanService.getTotalVoucherByIdShift(skasir.getId(), "");
                rowValue[13] = skasir.getQris();
                rowValue[14] = skasir.getTotalUang();
                rowValue[15] = skasir.getJumlahSetoran();
                rowValue[16] = skasir.getJumlahSetoran() - skasir.getTotalUang();
                totalPenjualan += ((Long)rowValue[6]).longValue();
                penjualanKredit += ((Long)rowValue[7]).longValue();
                returCash += ((Long)rowValue[8]).longValue();
                pelunasanCust += ((Long)rowValue[9]).longValue();
                transfer += ((Long)rowValue[10]).longValue();
                debit += ((Long)rowValue[11]).longValue();
                voucher += ((Long)rowValue[12]).longValue();
                qris += ((Long)rowValue[13]).longValue();
                uangSetoran += skasir.getJumlahSetoran();
                ++allshift;
            }
            netPenjualan = totalPenjualan - penjualanKredit - returCash;
            total = netPenjualan + pelunasanCust;
            nonTunai = transfer + debit + qris + voucher;
            uangTunai = total - nonTunai;
            selisih = uangSetoran - uangTunai;
            this.reportTableModel.addRow(rowValue);
            this.totalPenjualanField.setValue(totalPenjualan);
            this.penjualanKreditField.setValue(penjualanKredit);
            this.returCashField.setValue(returCash);
            this.netPenjualanField.setValue(netPenjualan);
            this.pelunasanCustField.setValue(pelunasanCust);
            this.totalField.setValue(total);
            this.transferField.setValue(transfer);
            this.debitField.setValue(debit);
            this.voucherField.setValue(voucher);
            this.qrisField.setValue(qris);
            this.nonTunaiField.setValue(nonTunai);
            this.uangTunaiField.setValue(uangTunai);
            this.uangSetoranField.setValue(uangSetoran);
            this.selisihField.setValue(selisih);
        }
        List<Object[]> dataPenjualans = this.penjualanService.getPenjualansByShift(0, format.format(this.tanggalField.getDate()));
        List<Object[]> dataPelunasan = this.pelunasanPiutangService.getPelunasanPiutangByShift(0, format.format(this.tanggalField.getDate()));
        if (skasirs.size() == allshift) {
            for (Object[] dataPenjualan : dataPenjualans) {
                this.detailPenjualanTM.addRow(dataPenjualan);
            }
            for (Object[] listDataPelunasan : dataPelunasan) {
                this.detailPelunasanPiutangTM.addRow(listDataPelunasan);
            }
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.tanggalField = new JDateChooser();
        this.tampilkanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.jLabel6 = new JLabel();
        this.jTabbedPane1 = new JTabbedPane();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.jPanel1 = new JPanel();
        this.jLabel8 = new JLabel();
        this.selisihField = new JFormattedTextField();
        this.jLabel4 = new JLabel();
        this.uangSetoranField = new JFormattedTextField();
        this.jLabel23 = new JLabel();
        this.uangTunaiField = new JFormattedTextField();
        this.jLabel21 = new JLabel();
        this.jSeparator3 = new JSeparator();
        this.jLabel22 = new JLabel();
        this.nonTunaiField = new JFormattedTextField();
        this.jLabel20 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jSeparator2 = new JSeparator();
        this.jLabel19 = new JLabel();
        this.pelunasanCustField = new JFormattedTextField();
        this.jLabel18 = new JLabel();
        this.netPenjualanField = new JFormattedTextField();
        this.jLabel17 = new JLabel();
        this.jSeparator1 = new JSeparator();
        this.jLabel16 = new JLabel();
        this.penjualanKreditField = new JFormattedTextField();
        this.jLabel5 = new JLabel();
        this.totalPenjualanField = new JFormattedTextField();
        this.jLabel2 = new JLabel();
        this.jSeparator7 = new JSeparator();
        this.jLabel24 = new JLabel();
        this.jLabel25 = new JLabel();
        this.transferField = new JFormattedTextField();
        this.debitField = new JFormattedTextField();
        this.voucherField = new JFormattedTextField();
        this.jLabel26 = new JLabel();
        this.jLabel27 = new JLabel();
        this.returCashField = new JFormattedTextField();
        this.jLabel3 = new JLabel();
        this.jSeparator4 = new JSeparator();
        this.qrisField = new JFormattedTextField();
        this.jLabel7 = new JLabel();
        this.jScrollPane3 = new JScrollPane();
        this.jTable2 = new JTable();
        this.jScrollPane2 = new JScrollPane();
        this.jTable1 = new JTable();
        this.setMaximumSize(new Dimension(800, 600));
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 11));
        this.jLabel1.setText("Report Keuangan Shift");
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportKeuanganShiftPanel.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportKeuanganShiftPanel.this.keluarBtnActionPerformed(evt);
            }
        });
        this.jLabel6.setText("tanggal");
        this.reportTable.setModel(this.reportTableModel);
        this.reportTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportKeuanganShiftPanel.this.reportTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reportTable);
        this.jTabbedPane1.addTab("Shift", this.jScrollPane1);
        this.jLabel8.setText("Selisih");
        this.jLabel4.setText("-");
        this.jLabel23.setText("Uang Setoran");
        this.jLabel21.setText("Uang Tunai");
        this.jLabel22.setText("-");
        this.jLabel20.setText("Non Tunai");
        this.jLabel19.setText("+");
        this.jLabel18.setText("Pelunasan Cust");
        this.jLabel17.setText("Net penjualan");
        this.jLabel16.setText("-");
        this.jLabel5.setText("Penjualan Kredit");
        this.jLabel2.setText("Total Penjualan");
        this.jSeparator7.setOrientation(1);
        this.jLabel24.setText("Detail Non Tunai");
        this.jLabel25.setText("Transfer");
        this.transferField.setMinimumSize(new Dimension(115, 20));
        this.jLabel26.setText("Debit");
        this.jLabel27.setText("Voucher");
        this.jLabel3.setText("Retur Cash");
        this.jLabel7.setText("Qris");
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jSeparator3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel22)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5).addComponent(this.jLabel2)).addGap(19, 19, 19).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.penjualanKreditField).addComponent(this.totalPenjualanField))).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(this.jLabel20).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.totalField, GroupLayout.Alignment.TRAILING, -1, 97, Short.MAX_VALUE).addComponent(this.nonTunaiField, GroupLayout.Alignment.TRAILING))).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel21, -1, -1, Short.MAX_VALUE).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel23).addComponent(this.jLabel8)).addGap(0, 0, Short.MAX_VALUE))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.uangSetoranField, -1, 98, Short.MAX_VALUE).addComponent(this.uangTunaiField).addComponent(this.selisihField, GroupLayout.Alignment.TRAILING))).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jLabel18, -1, 116, Short.MAX_VALUE).addComponent(this.jLabel17, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel3, GroupLayout.Alignment.LEADING)).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(17, 17, 17).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.returCashField).addComponent(this.netPenjualanField))).addGroup(jPanel1Layout.createSequentialGroup().addGap(18, 18, 18).addComponent(this.pelunasanCustField)))).addComponent(this.jSeparator2).addComponent(this.jSeparator4).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jSeparator1, -2, 228, -2).addGap(0, 7, Short.MAX_VALUE))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel19).addComponent(this.jLabel16).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jSeparator7, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel24).addComponent(this.jLabel25).addComponent(this.jLabel26).addComponent(this.jLabel27).addComponent(this.jLabel7)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.qrisField, -1, 115, Short.MAX_VALUE).addComponent(this.transferField, -1, 115, Short.MAX_VALUE).addComponent(this.debitField).addComponent(this.voucherField)).addGap(234, 234, 234)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jLabel24).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel25).addComponent(this.transferField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.debitField, -2, -1, -2).addComponent(this.jLabel26)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.voucherField, -2, -1, -2).addComponent(this.jLabel27))).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalPenjualanField, -2, -1, -2).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.penjualanKreditField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.returCashField, -2, -1, -2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jSeparator1, GroupLayout.Alignment.TRAILING, -2, 10, -2).addComponent(this.jLabel16, GroupLayout.Alignment.TRAILING)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.netPenjualanField, -2, -1, -2).addComponent(this.jLabel17)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.pelunasanCustField, -2, -1, -2).addComponent(this.jLabel18)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jSeparator2, -2, 3, -2)).addComponent(this.jLabel19)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.nonTunaiField, -2, -1, -2).addComponent(this.jLabel20)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel22).addComponent(this.jSeparator3, -2, 10, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel21).addComponent(this.uangTunaiField, GroupLayout.Alignment.TRAILING, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.uangSetoranField, -2, -1, -2).addComponent(this.jLabel23)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jSeparator4, -2, 10, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.selisihField, -2, -1, -2))).addComponent(this.jLabel4))).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.qrisField, -2, -1, -2).addComponent(this.jLabel7)).addGap(0, 0, Short.MAX_VALUE)))).addComponent(this.jSeparator7)).addContainerGap()));
        this.jTabbedPane1.addTab("Rekap", this.jPanel1);
        this.jTable2.setModel(this.detailPelunasanPiutangTM);
        this.jScrollPane3.setViewportView(this.jTable2);
        this.jTabbedPane1.addTab("Detail Pelunasan Piutang", this.jScrollPane3);
        this.jTable1.setModel(this.detailPenjualanTM);
        this.jScrollPane2.setViewportView(this.jTable1);
        this.jTabbedPane1.addTab("Detail Penjualan", this.jScrollPane2);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(14, 14, 14).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addGroup(layout.createSequentialGroup().addComponent(this.jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent((Component)this.tanggalField, -2, 150, -2))).addGap(18, 18, 18).addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jTabbedPane1))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(20, 20, 20).addComponent(this.jLabel1, -2, 14, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel6).addComponent((Component)this.tanggalField, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanBtn).addComponent(this.keluarBtn))).addGap(18, 18, 18).addComponent(this.jTabbedPane1)));
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.loadData();
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void reportTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.listener.exitClick();
        }
    }
}

