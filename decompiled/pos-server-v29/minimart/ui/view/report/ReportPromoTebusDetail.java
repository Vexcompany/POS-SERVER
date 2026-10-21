/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.PromoTebusDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.PromoTebusDetailService;

public class ReportPromoTebusDetail
extends JDialog {
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Jumlah", "Satuan", "Promo Barang Id", "Diskon", "Satuan Promo"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private String idPromo;
    private BarangService barangService = new BarangService();
    private PromoTebusDetailService promoTebusDetailService = new PromoTebusDetailService();

    public ReportPromoTebusDetail(Frame parent, boolean modal, String idPromoTebus) {
        super(parent, modal);
        this.initComponents();
        this.idPromo = idPromoTebus;
        this.loadData();
    }

    private void loadData() {
        List<PromoTebusDetail> promoTebusDetails = this.promoTebusDetailService.getPromoTebusDetailByPromoTebusId(this.idPromo);
        for (PromoTebusDetail ptd : promoTebusDetails) {
            Object[] rowData = new Object[]{ptd.getBarangId(), this.barangService.getBarang(ptd.getBarangId()).getNama(), Float.valueOf(ptd.getJumlah()), ptd.getSatuan(), ptd.getPromoBarangId(), ptd.getDiscount(), ptd.getSatuanPromo()};
            this.reportTableModel.addRow(rowData);
        }
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.setDefaultCloseOperation(2);
        this.jTable1.setModel(this.reportTableModel);
        this.jTable1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPromoTebusDetail.this.jTable1KeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.jTable1);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(73, 73, 73).addComponent(this.jScrollPane1, -1, 516, Short.MAX_VALUE).addContainerGap()));
        this.pack();
    }

    private void jTable1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }
}

