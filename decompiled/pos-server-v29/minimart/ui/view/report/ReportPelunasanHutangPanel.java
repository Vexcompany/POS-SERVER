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
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import minimart.engine.model.Karyawan;
import minimart.engine.model.PelunasanHutang;
import minimart.engine.model.Pembelian;
import minimart.engine.model.Supplier;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.PelunasanHutangService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.report.ReportPelunasanHutangDetail;

public class ReportPelunasanHutangPanel
extends JPanel {
    private JButton detailButton;
    private JFormattedTextField endDateField;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JTable reportTable;
    private JFormattedTextField startDateField;
    private JFormattedTextField totalField;
    private String[] reportColumn = new String[]{"ID", "Tanggal", "ID Penjualan", "Supplier", "Input By", "Keterangan", "Jumlah"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PelunasanHutangService pelunasanHutangService = new PelunasanHutangService();
    private PembelianService pembelianService = new PembelianService();
    private SupplierService supplierService = new SupplierService();
    private KaryawanService karyawanService = new KaryawanService();
    private Date startDate;
    private Date endDate;
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private long total = 0L;
    private String supplierId;

    public ReportPelunasanHutangPanel(Date startDate, Date endDate, String supplierId) {
        this.initComponents();
        this.startDate = startDate;
        this.endDate = endDate;
        this.startDateField.setValue(startDate);
        this.endDateField.setValue(endDate);
        this.supplierId = supplierId;
        this.loadData();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void loadData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDate);
        String endDateString = simpleDateFormat.format(this.endDate);
        List<PelunasanHutang> pelunasanHutangs = this.pelunasanHutangService.getPelunasanHutang(this.supplierId, startDateString, endDateString, "");
        for (PelunasanHutang pelunasan : pelunasanHutangs) {
            Object[] rowData = new Object[7];
            Pembelian temPembelian = this.pembelianService.getPembelian(pelunasan.getPembelianId());
            rowData[0] = pelunasan.getId();
            rowData[1] = simpleDateFormat.format(pelunasan.getTanggalPelunasan());
            rowData[2] = pelunasan.getPembelianId();
            Supplier supplier = this.supplierService.getSupplier(temPembelian.getSupplierId());
            rowData[3] = supplier.getNama() + " - " + supplier.getAlamat();
            Karyawan karyawan = this.karyawanService.getKaryawan(pelunasan.getKaryawanId());
            rowData[4] = karyawan.getNama();
            rowData[5] = pelunasan.getKeterangan();
            rowData[6] = pelunasan.getJumlah();
            this.total += pelunasan.getJumlah();
            this.reportTableModel.addRow(rowData);
        }
        this.totalField.setValue(this.total);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.startDateField = new JFormattedTextField();
        this.endDateField = new JFormattedTextField();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.detailButton = new JButton();
        this.exitButton = new JButton();
        this.totalField = new JFormattedTextField();
        this.jLabel3 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal Awal");
        this.jLabel2.setText("Tanggal Akhir");
        this.startDateField.setEditable(false);
        this.startDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.endDateField.setEditable(false);
        this.endDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(83, 23));
        this.detailButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPelunasanHutangPanel.this.detailButtonActionPerformed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(83, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPelunasanHutangPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.jLabel3.setText("Total");
        this.jLabel3.setToolTipText("");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGap(292, 292, 292).addComponent(this.detailButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.exitButton, -2, -1, -2).addGap(0, 312, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2)).addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.startDateField, -1, 122, Short.MAX_VALUE).addComponent(this.endDateField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, 156, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(17, 17, 17).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.startDateField, -2, -1, -2).addComponent(this.totalField, -2, -1, -2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.endDateField, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 440, -2).addGap(46, 46, 46)));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void detailButtonActionPerformed(ActionEvent evt) {
        if (this.reportTable.getSelectedRow() != -1) {
            String id = this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 2).toString();
            ReportPelunasanHutangDetail rpd = new ReportPelunasanHutangDetail(null, true, id);
            int w = rpd.getWidth();
            int h = rpd.getHeight();
            rpd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            rpd.setVisible(true);
        }
    }
}

