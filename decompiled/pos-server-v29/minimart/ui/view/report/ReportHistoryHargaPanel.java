/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.TableColumn;
import minimart.engine.service.BarangService;
import minimart.engine.service.HistoryHargaService;
import minimart.engine.service.KaryawanService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportHistoryHargaPanel
extends JPanel {
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JButton keluarButton;
    private JTable reportTable;
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Satuan", "Harga Lama", "Harga Baru", "Tanggal", "Update By"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private DefaultPanelListener listener;
    private HistoryHargaService historyHargaService = new HistoryHargaService();
    private BarangService barangService = new BarangService();
    private KaryawanService karyawanService = new KaryawanService();

    public ReportHistoryHargaPanel(Date startDate, Date endDate, String idBarang) {
        this.initComponents();
        this.loadHistoryHarga(startDate, endDate, idBarang);
        this.jScrollPane1.setHorizontalScrollBarPolicy(30);
        TableColumn col1 = this.reportTable.getColumnModel().getColumn(2);
        TableColumn col2 = this.reportTable.getColumnModel().getColumn(1);
        col1.setPreferredWidth(25);
        col2.setPreferredWidth(130);
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

    private void loadHistoryHarga(Date startDate, Date endDate, String idBarang) {
        this.clearTable();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(startDate);
        String endDateString = simpleDateFormat.format(endDate);
        List<Object[]> historyHargas = this.historyHargaService.getHistoryHarga(startDateString, endDateString, idBarang);
        for (Object[] historyHarga : historyHargas) {
            this.reportTableModel.addRow(historyHarga);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.keluarButton = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report History Harga");
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(73, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportHistoryHargaPanel.this.keluarButtonActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.keluarButton, -2, -1, -2))).addContainerGap()).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.jLabel1).addGap(304, 304, 304)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jScrollPane1, -1, 492, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.keluarButton, -2, -1, -2).addGap(23, 23, 23)));
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }
}

