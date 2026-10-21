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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.model.InvoiceReprintLog;
import minimart.engine.service.InvoiceReprintLogService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportReprinInvoiceLog
extends JPanel {
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JScrollPane jScrollPane1;
    private JButton keluarBtn;
    private JTable reportTable;
    private JButton tampilkanBtn;
    private JDateChooser tanggalField;
    private JDateChooser tanggalField2;
    private JFormattedTextField totalTransaksiField;
    private String[] reportColumn = new String[]{"ID Reprint", "ID Penjualan", "ID Karyawan", "Created"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private DefaultPanelListener listener;
    private InvoiceReprintLogService invoiceReprintLogService = new InvoiceReprintLogService();

    public ReportReprinInvoiceLog() {
        this.initComponents();
        this.tanggalField.setDate(new Date());
        this.tanggalField2.setDate(new Date());
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
    }

    private void loadData() {
        this.clearTable();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        List<InvoiceReprintLog> invoiceReprintLogs = this.invoiceReprintLogService.getInvoiceReprintLog(format.format(this.tanggalField.getDate()), format.format(this.tanggalField2.getDate()));
        int totalTransaksi = 0;
        for (InvoiceReprintLog invoiceReprintLog : invoiceReprintLogs) {
            Object[] rowValue = new Object[]{invoiceReprintLog.getId(), invoiceReprintLog.getPenjualanId(), invoiceReprintLog.getKaryawanId(), invoiceReprintLog.getCreated()};
            ++totalTransaksi;
            this.reportTableModel.addRow(rowValue);
        }
        this.totalTransaksiField.setValue(totalTransaksi);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.jLabel3 = new JLabel();
        this.tanggalField = new JDateChooser();
        this.tampilkanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.totalTransaksiField = new JFormattedTextField();
        this.jLabel4 = new JLabel();
        this.tanggalField2 = new JDateChooser();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel2 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Reprint Invoice Log");
        this.reportTable.setModel(this.reportTableModel);
        this.reportTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportReprinInvoiceLog.this.reportTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reportTable);
        this.jLabel3.setText("Enter : Detail");
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportReprinInvoiceLog.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportReprinInvoiceLog.this.keluarBtnActionPerformed(evt);
            }
        });
        this.totalTransaksiField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.totalTransaksiField.setFont(new Font("Tahoma", 0, 18));
        this.jLabel4.setFont(new Font("Tahoma", 0, 14));
        this.jLabel4.setText("Total Transaksi");
        this.jLabel6.setText("Dari");
        this.jLabel7.setText("Hingga");
        this.jLabel2.setText("Masukkan Tanggal H+1");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel3, GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel6).addGap(26, 26, 26).addComponent((Component)this.tanggalField, -2, 150, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 258, Short.MAX_VALUE).addComponent(this.jLabel4).addGap(10, 10, 10).addComponent(this.totalTransaksiField, -2, 179, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel7).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.tanggalField2, -2, 150, -2).addGap(18, 18, 18).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(15, 15, 15).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalTransaksiField, -2, -1, -2).addComponent(this.jLabel4))).addGap(14, 14, 14).addComponent((Component)this.tanggalField, -2, -1, -2)).addComponent(this.jLabel6)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanBtn).addComponent(this.keluarBtn).addComponent(this.jLabel2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel7).addComponent((Component)this.tanggalField2, -2, -1, -2))).addGap(18, 18, 18).addComponent(this.jScrollPane1, -2, 456, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel3).addContainerGap()));
    }

    private void reportTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.listener.exitClick();
        }
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.loadData();
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }
}

