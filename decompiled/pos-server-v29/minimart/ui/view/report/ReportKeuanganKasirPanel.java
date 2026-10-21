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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Kasir;
import minimart.engine.service.KasirService;
import minimart.engine.service.KeuanganKasirService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportKeuanganKasirPanel
extends JPanel {
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JButton keluarBtn;
    private JTable reportTable;
    private JButton tampilkanBtn;
    private JDateChooser tanggalField;
    private String[] reportColumn = new String[]{"Kasir", "Jumlah Penjualan"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private KeuanganKasirService keuanganKasirService = new KeuanganKasirService();
    private KasirService kasirService = new KasirService();
    private DefaultPanelListener listener;

    public ReportKeuanganKasirPanel() {
        this.initComponents();
        this.tanggalField.setDate(new Date());
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
        List<Kasir> kasirs = this.kasirService.getKasirs();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        for (Kasir kasir : kasirs) {
            Object[] rowValue = new Object[6];
            long keuanganKasir = this.keuanganKasirService.getKeuanganKasirByTanggal(format.format(this.tanggalField.getDate()), kasir.getId());
            rowValue[0] = kasir.getId();
            rowValue[1] = keuanganKasir;
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.jLabel3 = new JLabel();
        this.tanggalField = new JDateChooser();
        this.tampilkanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Penjualan Kasir");
        this.reportTable.setModel(this.reportTableModel);
        this.reportTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportKeuanganKasirPanel.this.reportTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reportTable);
        this.jLabel3.setText("Enter : Detail");
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportKeuanganKasirPanel.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportKeuanganKasirPanel.this.keluarBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 297, Short.MAX_VALUE).addComponent(this.jLabel1).addGap(293, 293, 293)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addGroup(layout.createSequentialGroup().addComponent((Component)this.tanggalField, -2, 150, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(7, 7, 7).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent((Component)this.tanggalField, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanBtn).addComponent(this.keluarBtn))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -2, 489, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE).addComponent(this.jLabel3).addContainerGap()));
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

