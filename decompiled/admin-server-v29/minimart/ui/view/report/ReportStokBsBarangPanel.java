/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportStokBsBarangPanel
extends JPanel {
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTable reportTable;
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Jenis", "Stok Bs"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private BarangService barangService = new BarangService();
    private DefaultPanelListener listener;
    private String jenis;

    public ReportStokBsBarangPanel(String jenis) {
        this.initComponents();
        this.jenis = jenis;
        this.loadBarang(jenis);
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

    private void loadBarang(String jenis) {
        this.clearTable();
        List<Barang> barangs = this.barangService.getBsBarangs(jenis);
        for (Barang barang : barangs) {
            Object[] rowValue = new Object[]{barang.getId(), barang.getNama(), barang.getJenis(), barang.getStokBs() + " " + barang.getSatuan1()};
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Stok BS Barang");
        this.reportTable.setModel(this.reportTableModel);
        this.reportTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportStokBsBarangPanel.this.reportTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reportTable);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGap(288, 288, 288).addComponent(this.jLabel1).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jScrollPane1, -1, 538, Short.MAX_VALUE).addContainerGap()));
    }

    private void reportTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.listener.exitClick();
        }
    }
}

