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
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportPerformaPromo
extends JPanel {
    private JTextField eligiblePwpField;
    private JTextField eligibleTMField;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JTextField jmlPwpField;
    private JTextField jmlTMField;
    private JButton keluarBtn;
    private JTextField persenPwpField;
    private JTextField persenTMField;
    private JButton tampilkanBtn;
    private JDateChooser tanggalField;
    private JDateChooser tanggalField2;
    private JTextField totalTransakiField;
    private String[] reportColumn = new String[]{"tanggal", "Total Transaksi", "karyawan", "total penjualan", "eligible PWP", "eligible TebusMurah", "Qty PWP", "Qty TebusMurah", "Total PWP", "Total TebusMurah"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private DefaultPanelListener defaultPanelListener;
    private PenjualanService penjualanService = new PenjualanService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private Helper helper = Helper.getInstance();

    public ReportPerformaPromo() {
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
        this.totalTransakiField = new JTextField();
        this.eligiblePwpField = new JTextField();
        this.eligibleTMField = new JTextField();
        this.jmlPwpField = new JTextField();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jmlTMField = new JTextField();
        this.jLabel8 = new JLabel();
        this.persenPwpField = new JTextField();
        this.persenTMField = new JTextField();
        this.jLabel9 = new JLabel();
        this.jLabel10 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Performa Promo");
        this.jLabel2.setText("Dari");
        this.jLabel3.setText("Hingga");
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPerformaPromo.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.setPreferredSize(new Dimension(79, 23));
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPerformaPromo.this.keluarBtnActionPerformed(evt);
            }
        });
        this.jTable1.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.jTable1);
        this.jLabel4.setText("Total Transaksi");
        this.jLabel5.setText("Eligible Pwp");
        this.jLabel6.setText("Eligible Teb Murah");
        this.jLabel7.setText("Jumlah Pwp");
        this.jLabel8.setText("Jumlah Teb Murah");
        this.jLabel9.setText("% Pwp");
        this.jLabel10.setText("% Teb murah");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 770, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent((Component)this.tanggalField, -2, 150, -2)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel3).addGap(18, 18, 18).addComponent((Component)this.tanggalField2, -2, 150, -2))).addComponent(this.jLabel1)).addGap(104, 104, 104).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel10).addComponent(this.jLabel9)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.persenPwpField).addComponent(this.persenTMField)))).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addGap(54, 54, 54).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.eligiblePwpField, GroupLayout.Alignment.TRAILING, -1, 84, Short.MAX_VALUE).addComponent(this.eligibleTMField, GroupLayout.Alignment.TRAILING).addComponent(this.totalTransakiField))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel8).addComponent(this.jLabel7).addComponent(this.jLabel6).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jmlPwpField, GroupLayout.Alignment.TRAILING, -2, 84, -2).addComponent(this.jmlTMField, GroupLayout.Alignment.TRAILING, -2, 84, -2)))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.totalTransakiField, -2, -1, -2).addComponent(this.jLabel4).addComponent(this.persenPwpField, -2, -1, -2).addComponent(this.jLabel9)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.eligiblePwpField, GroupLayout.Alignment.TRAILING, -2, -1, -2).addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.persenTMField, -2, -1, -2).addComponent(this.jLabel10)))).addComponent((Component)this.tanggalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent((Component)this.tanggalField2, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.eligibleTMField, -2, -1, -2).addComponent(this.jLabel6))).addGap(19, 19, 19).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.jmlPwpField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jmlTMField, -2, -1, -2).addComponent(this.jLabel8).addComponent(this.tampilkanBtn).addComponent(this.keluarBtn, -2, -1, -2)).addGap(13, 13, 13).addComponent(this.jScrollPane1, -2, 331, -2).addContainerGap(45, Short.MAX_VALUE)));
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.clearRow();
        long totalTransaksi = 0L;
        float eligiblePwp = 0.0f;
        float eligibleTebusMurah = 0.0f;
        float jumlahPwp = 0.0f;
        float jumlahTebusMurah = 0.0f;
        float persenPwp = 0.0f;
        float persenTM = 0.0f;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.tanggalField.getDate());
        String endDateString = simpleDateFormat.format(this.tanggalField2.getDate());
        List<Object[]> datas = this.penjualanService.getPerformaPromo(startDateString, endDateString);
        for (int a = 0; a < datas.size(); ++a) {
            Object[] row;
            Object[] data = datas.get(a);
            row = new Object[]{data[0], data[1], data[2], data[3], data[4], data[5], this.penjualanDetailService.jumlahPromoByTanggal(row[0].toString(), "PROMOPWP", row[2].toString()), this.penjualanDetailService.jumlahPromoByTanggal(row[0].toString(), "PROMOTEBUSMURAH", row[2].toString()), this.penjualanDetailService.totalPromoByTanggal(row[0].toString(), "PROMOPWP", row[2].toString()), this.penjualanDetailService.totalPromoByTanggal(row[0].toString(), "PROMOTEBUSMURAH", row[2].toString())};
            this.reportTableModel.addRow(row);
            totalTransaksi += Long.parseLong(row[1].toString());
            eligiblePwp += Float.parseFloat(row[4].toString());
            eligibleTebusMurah += Float.parseFloat(row[5].toString());
            jumlahPwp += Float.parseFloat(row[6].toString());
            jumlahTebusMurah += Float.parseFloat(row[7].toString());
        }
        persenPwp = jumlahPwp / eligiblePwp * 100.0f;
        persenTM = jumlahTebusMurah / eligibleTebusMurah * 100.0f;
        this.totalTransakiField.setText(totalTransaksi + "");
        this.eligiblePwpField.setText(eligiblePwp + "");
        this.eligibleTMField.setText(eligibleTebusMurah + "");
        this.jmlPwpField.setText(jumlahPwp + "");
        this.jmlTMField.setText(jumlahTebusMurah + "");
        this.persenPwpField.setText(persenPwp + "");
        this.persenTMField.setText(persenTM + "");
    }
}

