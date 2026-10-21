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
import minimart.engine.model.PromoTebus;
import minimart.engine.service.PromoTebusService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.report.ReportPromoTebusDetail;

public class ReportPromoTebus
extends JPanel {
    private JButton detail;
    private JDateChooser endDateField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JButton keluarBtn;
    private JTable reportTable;
    private JDateChooser startDateField;
    private JDateChooser startDateField1;
    private JButton tampilkanBtn;
    private DefaultPanelListener listener;
    private SearchDialog sd;
    private String[] reportColumn = new String[]{"ID", "TANGGAL MULAI", "TANGGAL BERAKHIR", "KETERANGAN", "STATUS", "CREATED"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private PromoTebusService promoTebusService = new PromoTebusService();
    private int screenWidth;
    private int screenHeight;

    public ReportPromoTebus() {
        this.initComponents();
        this.startDateField1.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private void initComponents() {
        this.startDateField = new JDateChooser();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.startDateField1 = new JDateChooser();
        this.endDateField = new JDateChooser();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.tampilkanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.detail = new JButton();
        this.startDateField.addMouseListener((MouseListener)new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportPromoTebus.this.startDateFieldMouseClicked(evt);
            }
        });
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Dari Tanggal");
        this.jLabel2.setText("Sampai Tanggal");
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPromoTebus.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.setPreferredSize(new Dimension(73, 23));
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPromoTebus.this.keluarBtnActionPerformed(evt);
            }
        });
        this.detail.setText("Detail");
        this.detail.setPreferredSize(new Dimension(73, 23));
        this.detail.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPromoTebus.this.detailActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.detail, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent((Component)this.startDateField1, -2, 150, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addGap(32, 32, 32).addComponent((Component)this.endDateField, -2, 150, -2))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel1).addComponent((Component)this.startDateField1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, GroupLayout.Alignment.TRAILING).addComponent((Component)this.endDateField, GroupLayout.Alignment.TRAILING, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanBtn).addComponent(this.keluarBtn, -2, -1, -2).addComponent(this.detail, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 457, -2).addContainerGap()));
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void startDateFieldMouseClicked(MouseEvent evt) {
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void clearRow() {
        int row = this.reportTableModel.getRowCount();
        for (int a = 0; a < row; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDateField1.getDate());
        String endDateString = simpleDateFormat.format(this.endDateField.getDate());
        this.reportTableModel.setColumnIdentifiers(this.reportColumn);
        List<PromoTebus> promoTebuses = this.promoTebusService.getPromoTebus(startDateString, endDateString);
        this.clearRow();
        for (PromoTebus promoTebus : promoTebuses) {
            Object[] rowDatas = new Object[]{promoTebus.getId(), promoTebus.getTanggalMulai(), promoTebus.getTanggalBerakhir(), promoTebus.getKeterangan(), promoTebus.getStatus(), promoTebus.getCreated()};
            this.reportTableModel.addRow(rowDatas);
        }
    }

    private void detailActionPerformed(ActionEvent evt) {
        if (this.reportTable.getSelectedRow() != -1) {
            String id = this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString();
            ReportPromoTebusDetail rpd = new ReportPromoTebusDetail(null, true, id);
            int w = rpd.getWidth();
            int h = rpd.getHeight();
            rpd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            rpd.setVisible(true);
        }
    }
}

