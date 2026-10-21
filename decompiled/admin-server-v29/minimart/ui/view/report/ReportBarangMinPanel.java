/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.view.JasperViewer
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.ui.listener.DefaultPanelListener;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ReportBarangMinPanel
extends JPanel {
    private JButton cetakButton;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JButton keluarButton;
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Jenis", "Stok Toko", "Min Stok"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private BarangService barangService = new BarangService();
    private DefaultPanelListener listener;
    private String jenisData;

    public ReportBarangMinPanel(String jenis) {
        this.initComponents();
        this.jenisData = jenis;
        this.loadBarang(jenis);
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void loadBarang(String jenis) {
        List<Barang> barangs = this.barangService.getBarangsMin(jenis);
        for (Barang barang : barangs) {
            Object[] rowValue = new Object[]{barang.getId(), barang.getNama(), barang.getJenis(), barang.getStokToko() + " " + barang.getSatuan1(), barang.getBatasAtasStock() + " " + barang.getSatuan1()};
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.keluarButton = new JButton();
        this.cetakButton = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Barang Dibawah Minimum");
        this.jTable1.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.jTable1);
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(73, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportBarangMinPanel.this.keluarButtonActionPerformed(evt);
            }
        });
        this.cetakButton.setText("Cetak");
        this.cetakButton.setPreferredSize(new Dimension(73, 23));
        this.cetakButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportBarangMinPanel.this.cetakButtonActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 251, Short.MAX_VALUE).addComponent(this.jLabel1).addGap(244, 244, 244)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1)).addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.cetakButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton, -2, -1, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jScrollPane1, -1, 492, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.cetakButton, -2, -1, -2)).addGap(23, 23, 23)));
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void cetakButtonActionPerformed(ActionEvent evt) {
        try {
            File rear = new File("ireport/ReportStokMinus.jasper");
            String path = rear.getAbsolutePath();
            HashMap<String, String> parameter = new HashMap<String, String>();
            parameter.put("M_JENIS_JENIS", '%' + this.jenisData + '%');
            JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
            JasperViewer.viewReport((JasperPrint)print, (boolean)false);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
        }
    }
}

