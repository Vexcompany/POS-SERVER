/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import minimart.engine.controller.Helper;
import minimart.engine.service.BarangService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportPembelianDetailPanel
extends JPanel {
    private JLabel barangIdLbl;
    private JLabel barangNameLbl;
    private JFormattedTextField endDateField;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel5;
    private JLabel jLabel8;
    private JScrollPane jScrollPane1;
    private JTable reportTable;
    private JFormattedTextField startDateField;
    private JLabel supplierIdLbl;
    private JLabel supplierNameLbl;
    private String[] reportColumn = new String[]{"ID Pembelian", "Supplier", "Tanggal", "ID Barang", "Nama Barang", "Jumlah Barang", "Satuan", "Net Harga", "Total Harga"};
    private String supplierId;
    private String barangId;
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PembelianDetailService detailService = new PembelianDetailService();
    private KaryawanService karyawanService = new KaryawanService();
    private SupplierService supplierService = new SupplierService();
    private BarangService barangService = new BarangService();
    private Date startDate;
    private Date endDate;
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();

    public ReportPembelianDetailPanel(Date startDate, Date endDate, String supplierId, String barangId) {
        this.initComponents();
        this.startDate = startDate;
        this.endDate = endDate;
        this.startDateField.setValue(startDate);
        this.endDateField.setValue(endDate);
        this.supplierId = supplierId;
        this.barangId = barangId;
        if (supplierId.equals("")) {
            this.supplierNameLbl.setText("SEMUA");
        } else {
            this.supplierIdLbl.setText(supplierId);
            this.supplierNameLbl.setText(this.supplierService.getSupplier(supplierId).getNama());
        }
        if (barangId.equals("")) {
            this.barangNameLbl.setText("SEMUA");
        } else {
            this.barangIdLbl.setText(barangId);
            this.barangNameLbl.setText(this.barangService.getBarang(barangId).getNama());
        }
        this.loadData();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        TableColumn col3 = this.reportTable.getColumnModel().getColumn(4);
        TableColumn col4 = this.reportTable.getColumnModel().getColumn(3);
        col3.setPreferredWidth(140);
        col4.setPreferredWidth(100);
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void loadData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDate);
        String endDateString = simpleDateFormat.format(this.endDate);
        List<Object[]> objects = this.detailService.readPembelianDetailBySupplierBarangId(startDateString, endDateString, this.supplierId, this.barangId);
        for (Object[] object : objects) {
            Object[] rowValue = new Object[9];
            rowValue[0] = object[0];
            rowValue[1] = object[1];
            rowValue[2] = object[2];
            rowValue[3] = object[3];
            rowValue[4] = object[4];
            rowValue[5] = object[5];
            rowValue[6] = object[6];
            long hargaKotor = Long.parseLong(object[7].toString());
            long hargaNet1 = hargaKotor - Long.parseLong(object[8].toString()) / (long)((int)Float.parseFloat(object[5] + ""));
            float hargaNet2 = (float)hargaNet1 - (float)hargaNet1 * Float.parseFloat(object[9].toString()) / 100.0f;
            float hargaNetFinal = hargaNet2 + hargaNet2 * Float.parseFloat(object[10].toString()) / 100.0f;
            rowValue[7] = Float.valueOf(hargaNetFinal);
            rowValue[8] = Float.valueOf(hargaNetFinal * Float.parseFloat(object[5].toString()));
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.startDateField = new JFormattedTextField();
        this.endDateField = new JFormattedTextField();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.exitButton = new JButton();
        this.jLabel5 = new JLabel();
        this.jLabel2 = new JLabel();
        this.supplierIdLbl = new JLabel();
        this.supplierNameLbl = new JLabel();
        this.jLabel8 = new JLabel();
        this.barangIdLbl = new JLabel();
        this.barangNameLbl = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal");
        this.startDateField.setEditable(false);
        this.startDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.endDateField.setEditable(false);
        this.endDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(83, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPembelianDetailPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.jLabel5.setText("-");
        this.jLabel2.setText("Supplier");
        this.supplierIdLbl.setText("-");
        this.supplierNameLbl.setText("-");
        this.jLabel8.setText("Barang");
        this.barangIdLbl.setText("-");
        this.barangNameLbl.setText("-");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(25, 25, 25).addComponent(this.startDateField, -2, 122, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.endDateField, -2, 122, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel8)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.supplierIdLbl, -1, -1, Short.MAX_VALUE).addComponent(this.barangIdLbl, -1, 102, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.barangNameLbl, -1, 191, Short.MAX_VALUE).addComponent(this.supplierNameLbl, -1, -1, Short.MAX_VALUE))))).addGroup(layout.createSequentialGroup().addGap(345, 345, 345).addComponent(this.exitButton, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.startDateField, -2, -1, -2).addComponent(this.endDateField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.supplierIdLbl).addComponent(this.supplierNameLbl)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.barangIdLbl))).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.barangNameLbl))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2).addGap(11, 11, 11).addComponent(this.jScrollPane1, -2, 411, -2).addContainerGap()));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }
}

