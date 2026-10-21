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
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class ReportPerformaAllPromo
extends JPanel {
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTable jTable1;
    private JComboBox<String> jenisPromoCmd;
    private JButton keluarBtn;
    private JButton tampilkanBtn;
    private JDateChooser tanggalField;
    private JDateChooser tanggalField2;
    private String[] reportColumn = new String[]{"TANGGAL", "ID PENJUALAN", "KARYAWAN", "ID BARANG", "NAMA BARANG", "QTY", "SATUAN", "HARGA", "DISC", "TOTAL HARGA", "PROMO"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private DefaultPanelListener defaultPanelListener;
    private PenjualanService penjualanService = new PenjualanService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private Helper helper = Helper.getInstance();

    public ReportPerformaAllPromo() {
        this.initComponents();
        this.jScrollPane1.setHorizontalScrollBarPolicy(30);
        this.jScrollPane1.setVerticalScrollBarPolicy(21);
        this.jTable1.setAutoResizeMode(0);
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
        this.jLabel4 = new JLabel();
        this.jenisPromoCmd = new JComboBox();
        this.jScrollPane2 = new JScrollPane();
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
                ReportPerformaAllPromo.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.setPreferredSize(new Dimension(79, 23));
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPerformaAllPromo.this.keluarBtnActionPerformed(evt);
            }
        });
        this.jLabel4.setText("Promo");
        this.jenisPromoCmd.setModel(new DefaultComboBoxModel<String>(new String[]{"PROMOHEMAT", "PROMOTEBUSMURAH", "PROMOPWP", "PROMOJENIS", "SEMUA"}));
        this.jTable1.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.jTable1);
        this.jScrollPane2.setViewportView(this.jScrollPane1);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane2, -1, 770, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel4).addGap(18, 18, Short.MAX_VALUE).addComponent(this.jenisPromoCmd, -2, 145, -2).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent((Component)this.tanggalField, -2, 150, -2)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel3).addGap(18, 18, 18).addComponent((Component)this.tanggalField2, -2, 150, -2))).addComponent(this.jLabel1))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn, -2, -1, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jLabel2).addGap(6, 6, 6)).addComponent((Component)this.tanggalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent((Component)this.tanggalField2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanBtn).addComponent(this.keluarBtn, -2, 29, -2).addComponent(this.jLabel4).addComponent(this.jenisPromoCmd, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane2, -2, 382, -2).addContainerGap(47, Short.MAX_VALUE)));
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.clearRow();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.tanggalField.getDate());
        String endDateString = simpleDateFormat.format(this.tanggalField2.getDate());
        List<Object[]> datas = this.penjualanDetailService.getReportPromo(startDateString, endDateString, this.jenisPromoCmd.getSelectedItem().toString());
        System.out.println("Selecteditem=" + this.jenisPromoCmd.getSelectedItem());
        for (Object[] data : datas) {
            this.reportTableModel.addRow(data);
        }
    }
}

