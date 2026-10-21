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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.engine.service.RepackDetailService;
import minimart.engine.service.RepackService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.report.ReportRepackDetailDialog;

public class ReportRepackPanel
extends JPanel {
    private JButton detailBtn;
    private JDateChooser endDateField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JButton keluarButton;
    private JTable reportTable;
    private JDateChooser startDateField;
    private JButton tampilkanButton;
    private String[] reportColumn = new String[]{"ID", "KARYAWAN", "TANGGAL", "NAMA BARANG", "JUMLAH", "SATUAN"};
    private SearchDialog sd;
    private int screenHeight;
    private int screenWidth;
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultPanelListener defaultPanelListener;
    private Helper helper = Helper.getInstance();
    private BarangService barangService = new BarangService();
    private RepackService repackService = new RepackService();
    private RepackDetailService repackDetailService = new RepackDetailService();
    private Barang barang = new Barang();

    public ReportRepackPanel() {
        this.initComponents();
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.startDateField = new JDateChooser();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.jLabel2 = new JLabel();
        this.endDateField = new JDateChooser();
        this.detailBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Dari Tanggal");
        this.startDateField.addMouseListener((MouseListener)new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportRepackPanel.this.startDateFieldMouseClicked(evt);
            }
        });
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportRepackPanel.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportRepackPanel.this.keluarButtonActionPerformed(evt);
            }
        });
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.jLabel2.setText("Sampai Tanggal");
        this.endDateField.addMouseListener((MouseListener)new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportRepackPanel.this.endDateFieldMouseClicked(evt);
            }
        });
        this.detailBtn.setText("Detail");
        this.detailBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportRepackPanel.this.detailBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel1).addGap(39, 39, 39).addComponent((Component)this.startDateField, -2, 150, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent((Component)this.endDateField, -2, 150, -2))).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.detailBtn, -2, 80, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton, -2, 101, -2))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel2)).addGroup(layout.createSequentialGroup().addComponent((Component)this.startDateField, -2, -1, -2).addGap(5, 5, 5).addComponent((Component)this.endDateField, -2, -1, -2))).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanButton).addComponent(this.keluarButton).addComponent(this.detailBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 437, -2).addContainerGap(60, Short.MAX_VALUE)));
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
        List<Object[]> repackDatas = this.repackService.getRepack(startDateString, endDateString);
        for (Object[] repackData : repackDatas) {
            this.reportTableModel.addRow(repackData);
        }
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void startDateFieldMouseClicked(MouseEvent evt) {
    }

    private void endDateFieldMouseClicked(MouseEvent evt) {
    }

    private void detailBtnActionPerformed(ActionEvent evt) {
        if (this.reportTable.getSelectedRow() != -1) {
            String id = this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString();
            ReportRepackDetailDialog rpd = new ReportRepackDetailDialog(null, true, id);
            int w = rpd.getWidth();
            int h = rpd.getHeight();
            rpd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            rpd.setVisible(true);
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }
}

