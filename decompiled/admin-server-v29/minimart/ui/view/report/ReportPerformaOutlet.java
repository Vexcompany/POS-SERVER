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
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportPerformaOutlet
extends JPanel {
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JButton keluarBtn;
    private JButton tampilkanBtn;
    private JDateChooser tanggalField;
    private JDateChooser tanggalField2;
    private String[] reportColumn = new String[]{"Kriteria", "Result"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private DefaultPanelListener defaultPanelListener;
    private PenjualanService penjualanService = new PenjualanService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private Helper helper = Helper.getInstance();

    public ReportPerformaOutlet() {
        this.initComponents();
    }

    private void clearRow() {
        int row = this.reportTableModel.getRowCount();
        for (int a = 0; a < row; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.tanggalField = new JDateChooser();
        this.tanggalField2 = new JDateChooser();
        this.tampilkanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Performa Promo");
        this.jLabel2.setText("Dari");
        this.jLabel3.setText("Hingga");
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPerformaOutlet.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.setPreferredSize(new Dimension(79, 23));
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPerformaOutlet.this.keluarBtnActionPerformed(evt);
            }
        });
        this.jTable1.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.jTable1);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 770, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent((Component)this.tanggalField, -2, 150, -2)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel3).addGap(18, 18, 18).addComponent((Component)this.tanggalField2, -2, 150, -2))).addComponent(this.jLabel1))).addGap(114, 552, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jLabel2).addGap(6, 6, 6)).addComponent((Component)this.tanggalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent((Component)this.tanggalField2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanBtn).addComponent(this.keluarBtn, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 380, -2).addContainerGap(49, Short.MAX_VALUE)));
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.clearRow();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.tanggalField.getDate());
        String endDateString = simpleDateFormat.format(this.tanggalField2.getDate());
        List<Object[]> data = this.penjualanService.getPerformaOutlet(startDateString, endDateString);
        Object[] row1 = new Object[]{"Sales Per Month (SPM)", data.get(0)[0]};
        Object[] row2 = new Object[]{"Sales Per Day (SPD)", Long.parseLong(row1[1] + "") / (long)Integer.parseInt(data.get(0)[2] + "")};
        Object[] row3 = new Object[]{"Struk Per Day (SPD)", data.get(0)[1]};
        Object[] row4 = new Object[]{"Average Per Customer (APC)", Long.parseLong(row1[1] + "") / (long)Integer.parseInt(row3[1] + "")};
        this.reportTableModel.addRow(row1);
        this.reportTableModel.addRow(row2);
        this.reportTableModel.addRow(row3);
        this.reportTableModel.addRow(row4);
    }
}

