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
import minimart.engine.model.Barang;
import minimart.engine.model.KoreksiStok;
import minimart.engine.service.BarangService;
import minimart.engine.service.KoreksiStokService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportHistoryStokPanel
extends JPanel {
    private JFormattedTextField endDateField;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JTable reportTable;
    private JFormattedTextField startDateField;
    private String[] reportColumn = new String[]{"KARYAWAN", "ID BARANG", "NAMA BARANG", "STOK AWAL", "JUMLAH", "SATUAN", "JENIS", "TANGGAL"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private BarangService barangService = new BarangService();
    private KoreksiStokService koreksiStokService = new KoreksiStokService();
    private Date startDate;
    private Date endDate;
    private DefaultPanelListener defaultPanelListener;
    private Barang barang;
    private int screenWidth;
    private int screenHeight;
    private String status;

    public ReportHistoryStokPanel(Date startDate, Date endDate, Barang barang, String status) {
        this.initComponents();
        this.startDate = startDate;
        this.endDate = endDate;
        this.startDateField.setValue(startDate);
        this.endDateField.setValue(endDate);
        this.barang = barang;
        this.status = status;
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
        List<KoreksiStok> koreksiStoks = this.koreksiStokService.getKoreksiStoks(startDateString, endDateString, this.barang, this.status);
        for (KoreksiStok koreksiStok : koreksiStoks) {
            Object[] rowData = new Object[9];
            rowData[0] = koreksiStok.getKaryawanId();
            rowData[1] = koreksiStok.getBarangId();
            rowData[2] = this.barangService.getBarang(koreksiStok.getBarangId()).getNama();
            rowData[3] = Float.valueOf(koreksiStok.getStokAwal());
            rowData[4] = Float.valueOf(koreksiStok.getJumlah());
            rowData[5] = koreksiStok.getSatuan();
            rowData[6] = koreksiStok.getJenis();
            rowData[7] = koreksiStok.getDate();
            this.reportTableModel.addRow(rowData);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.startDateField = new JFormattedTextField();
        this.endDateField = new JFormattedTextField();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.exitButton = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal Awal");
        this.jLabel2.setText("Tanggal Akhir");
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
                ReportHistoryStokPanel.this.exitButtonActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(385, 385, 385).addComponent(this.exitButton, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2)).addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.startDateField, -1, 122, Short.MAX_VALUE).addComponent(this.endDateField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(17, 17, 17).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.startDateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.endDateField, -2, -1, -2)).addGap(18, 18, 18).addComponent(this.exitButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 440, -2).addGap(46, 46, 46)));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }
}

