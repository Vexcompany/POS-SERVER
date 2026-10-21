/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.MutasiBarang;
import minimart.engine.model.MutasiBarangDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.MutasiBarangDetailService;
import minimart.engine.service.MutasiBarangService;

public class ReportMutasiDetail
extends JDialog {
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JTextField keteranganField;
    private JTextField statusField;
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private String idMutasi;
    private MutasiBarangDetailService mutasiBarangDetailService = new MutasiBarangDetailService();
    private MutasiBarangService mutasiBarangService = new MutasiBarangService();
    private BarangService barangService = new BarangService();

    public ReportMutasiDetail(Frame parent, boolean modal, String idMutasi) {
        super(parent, modal);
        this.initComponents();
        this.idMutasi = idMutasi;
        this.statusField.setEditable(false);
        this.keteranganField.setEditable(false);
        this.loadData(idMutasi);
    }

    private void loadData(String idMutasi) {
        MutasiBarang mutasiBarang = this.mutasiBarangService.getMutasiBarang(idMutasi);
        this.statusField.setText(mutasiBarang.getDari() + "-" + mutasiBarang.getMenuju());
        this.keteranganField.setText(mutasiBarang.getKeterangan());
        List<MutasiBarangDetail> mutasiBarangDetails = this.mutasiBarangDetailService.getMutasiBarangDetails(idMutasi);
        for (MutasiBarangDetail mbd : mutasiBarangDetails) {
            Object[] rowData = new Object[]{mbd.getBarangId(), this.barangService.getBarang(mbd.getBarangId()).getNama(), mbd.getJumlahBarang(), mbd.getSatuan()};
            this.reportTableModel.addRow(rowData);
        }
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.keteranganField = new JTextField();
        this.statusField = new JTextField();
        this.setDefaultCloseOperation(2);
        this.jTable1.setModel(this.reportTableModel);
        this.jTable1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportMutasiDetail.this.jTable1KeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.jTable1);
        this.jLabel1.setFont(new Font("Tahoma", 1, 14));
        this.jLabel1.setText("STATUS");
        this.jLabel2.setFont(new Font("Tahoma", 1, 14));
        this.jLabel2.setText("KETERANGAN");
        this.keteranganField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportMutasiDetail.this.keteranganFieldKeyPressed(evt);
            }
        });
        this.statusField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportMutasiDetail.this.statusFieldKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.keteranganField, -1, 189, Short.MAX_VALUE).addComponent(this.statusField)).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.statusField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.keteranganField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -1, 516, Short.MAX_VALUE).addContainerGap()));
        this.pack();
    }

    private void jTable1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void statusFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void keteranganFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }
}

