/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.RepackDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.RepackDetailService;

public class ReportRepackDetailDialog
extends JDialog {
    private JButton exitBtn;
    private JScrollPane jScrollPane1;
    private JTable repackDetailTable;
    private RepackDetailService repackDetailService = new RepackDetailService();
    private BarangService barangService = new BarangService();
    private String[] repackTMColumn = new String[]{"ID Barang", "Nama Barang", "Perubahan Stok", "Satuan"};
    private DefaultTableModel repackTM = new DefaultTableModel(new String[0][1], this.repackTMColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ReportRepackDetailDialog(Frame parent, boolean modal, String idRepack) {
        super(parent, modal);
        this.initComponents();
        this.loadData(idRepack);
    }

    private void loadData(String idRepack) {
        List<RepackDetail> repackDetails = this.repackDetailService.getRepackDetail(idRepack);
        for (RepackDetail repackDetail : repackDetails) {
            Object[] data = new Object[]{repackDetail.getBarangId(), this.barangService.getBarang(repackDetail.getBarangId()).getNama(), Float.valueOf(repackDetail.getJumlahBarang()), repackDetail.getSatuan()};
            this.repackTM.addRow(data);
        }
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.repackDetailTable = new JTable();
        this.exitBtn = new JButton();
        this.setDefaultCloseOperation(2);
        this.repackDetailTable.setModel(this.repackTM);
        this.jScrollPane1.setViewportView(this.repackDetailTable);
        this.exitBtn.setText("Keluar");
        this.exitBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportRepackDetailDialog.this.exitBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 561, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.exitBtn))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 382, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.exitBtn).addContainerGap()));
        this.pack();
    }

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }
}

