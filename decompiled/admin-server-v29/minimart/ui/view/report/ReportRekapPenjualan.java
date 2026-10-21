/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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
import minimart.engine.controller.Helper;
import minimart.engine.service.PenjualanService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportRekapPenjualan
extends JPanel {
    private JDateChooser endDateField;
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JButton keluarButton;
    private JTable reportTable;
    private JDateChooser startDateField;
    private JButton tampilkanButton;
    private JFormattedTextField totalField;
    private String[] reportColumn = new String[]{"Tanggal", "Total Penjualan"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PenjualanService penjualanService = new PenjualanService();
    private DefaultPanelListener defaultPanelListener;
    private Helper helper = Helper.getInstance();

    public ReportRekapPenjualan() {
        this.initComponents();
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.startDateField = new JDateChooser();
        this.endDateField = new JDateChooser();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.jLabel3 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jButton1 = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Dari Tanggal");
        this.jLabel2.setText("Sampai Tanggal");
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportRekapPenjualan.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportRekapPenjualan.this.keluarButtonActionPerformed(evt);
            }
        });
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.jLabel3.setFont(new Font("Tahoma", 0, 24));
        this.jLabel3.setText("Total");
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.totalField.setFont(new Font("Tahoma", 0, 18));
        this.jButton1.setText("Cetak");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1)).addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent((Component)this.endDateField, -1, -1, Short.MAX_VALUE).addComponent((Component)this.startDateField, -2, 150, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 239, Short.MAX_VALUE).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, 235, -2)).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton1).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addGroup(layout.createSequentialGroup().addComponent((Component)this.startDateField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.endDateField, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(17, 17, 17).addComponent(this.jLabel2)).addComponent(this.totalField, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanButton).addComponent(this.keluarButton).addComponent(this.jButton1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 476, -2).addContainerGap(20, Short.MAX_VALUE)));
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.clearTable();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDateField.getDate());
        String endDateString = simpleDateFormat.format(this.endDateField.getDate());
        List<Object[]> datas = this.penjualanService.getRekapPenjualan(this.helper.getStatusProgram(), startDateString, endDateString);
        long totalPenjualan = 0L;
        for (Object[] data : datas) {
            this.reportTableModel.addRow(data);
            totalPenjualan += Long.parseLong(data[1].toString());
        }
        this.totalField.setValue(totalPenjualan);
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }
}

