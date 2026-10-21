/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import minimart.ui.listener.DefaultPanelListener;

public class ReportDibawahHargaBeliPanel
extends JPanel {
    private JButton jButton1;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTable reportTable;
    private DefaultPanelListener listener;
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Harga Jual1 / HargaBeli", "Harga Jual2 / Harga Beli", "Harga Jual3 / Harga Beli"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private BarangService barangService = new BarangService();
    private Helper helper = Helper.getInstance();

    public ReportDibawahHargaBeliPanel(String jenis) {
        this.initComponents();
        this.loadData(jenis);
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void loadData(String jenis) {
        this.clearTable();
        List<Barang> barangs = this.barangService.getBarangsByJenisRakNama(jenis, "", "", "", true);
        for (Barang barang : barangs) {
            long barangSatTerkecil = this.helper.getHargaBeliTerakhirSatuanTerkecil(barang.getId());
            if (!(!barang.getSatuan1().equals("") && barang.getHargaJual1() <= barangSatTerkecil || !barang.getSatuan2().equals("") && barang.getHargaJual2() <= barangSatTerkecil * (long)barang.getKonversi21()) && (barang.getSatuan3().equals("") || barang.getHargaJual3() > barangSatTerkecil * (long)barang.getKonversi21() * (long)barang.getKonversi32())) continue;
            Object[] rowValue = new Object[]{barang.getId(), barang.getNama(), barang.getHargaJual1() + " / " + barangSatTerkecil, barang.getHargaJual2() + " / " + barangSatTerkecil * (long)barang.getKonversi21(), barang.getHargaJual3() + " / " + barangSatTerkecil * (long)barang.getKonversi21() * (long)barang.getKonversi32()};
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.jButton1 = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Barang Dibawah Harga Beli");
        this.reportTable.setModel(this.reportTableModel);
        this.reportTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportDibawahHargaBeliPanel.this.reportTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reportTable);
        this.jButton1.setText("Cancel");
        this.jButton1.setPreferredSize(new Dimension(73, 23));
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportDibawahHargaBeliPanel.this.jButton1ActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(225, 225, 225).addComponent(this.jLabel1).addGap(0, 251, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.jButton1, -2, -1, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jScrollPane1, -2, 503, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton1, -2, -1, -2).addContainerGap(17, Short.MAX_VALUE)));
    }

    private void reportTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.listener.exitClick();
        }
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }
}

