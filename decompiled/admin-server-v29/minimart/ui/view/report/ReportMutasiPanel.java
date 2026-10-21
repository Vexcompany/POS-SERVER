/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Barang;
import minimart.engine.model.MutasiBarang;
import minimart.engine.service.BarangService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.MutasiBarangDetailService;
import minimart.engine.service.MutasiBarangService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;
import minimart.ui.view.report.ReportMutasiDetail;

public class ReportMutasiPanel
extends JPanel {
    private JButton detail;
    private JDateChooser endDateField;
    private JTextField idBarangField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JButton keluarBtn;
    private JLabel namaBarangField;
    private JTable reportTable;
    private JDateChooser startDateField;
    private JDateChooser startDateField1;
    private JButton tampilkanBtn;
    private DefaultPanelListener listener;
    private SearchDialog sd;
    private String[] reportColumn = new String[]{"ID", "TANGGAL", "STATUS", "INPUT OLEH", "KETERANGAN"};
    private String[] reportColumnDetail = new String[]{"ID MUTASI", "TANGGAL", "ID BARANG", "NAMA BARANG", "DARI", "MENUJU", "QTY", "SATUAN"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private MutasiBarangDetailService mutasiBarangDetailService = new MutasiBarangDetailService();
    private MutasiBarangService mutasiBarangService = new MutasiBarangService();
    private KaryawanService karyawanService = new KaryawanService();
    private int screenWidth;
    private int screenHeight;
    private BarangService barangService = new BarangService();

    public ReportMutasiPanel() {
        this.initComponents();
        this.startDateField1.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private void initComponents() {
        this.startDateField = new JDateChooser();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.startDateField1 = new JDateChooser();
        this.endDateField = new JDateChooser();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.tampilkanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.detail = new JButton();
        this.jLabel3 = new JLabel();
        this.idBarangField = new JTextField();
        this.namaBarangField = new JLabel();
        this.startDateField.addMouseListener((MouseListener)new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportMutasiPanel.this.startDateFieldMouseClicked(evt);
            }
        });
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Dari Tanggal");
        this.jLabel2.setText("Sampai Tanggal");
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMutasiPanel.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.setPreferredSize(new Dimension(73, 23));
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMutasiPanel.this.keluarBtnActionPerformed(evt);
            }
        });
        this.detail.setText("Detail");
        this.detail.setPreferredSize(new Dimension(73, 23));
        this.detail.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMutasiPanel.this.detailActionPerformed(evt);
            }
        });
        this.jLabel3.setText("ID Barang");
        this.idBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportMutasiPanel.this.idBarangFieldKeyPressed(evt);
            }
        });
        this.namaBarangField.setText("-");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.detail, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE).addComponent((Component)this.startDateField1, -2, 150, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel3)).addGap(32, 32, 32).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent((Component)this.endDateField, -1, 150, Short.MAX_VALUE).addComponent(this.idBarangField)))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.namaBarangField).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel1).addComponent((Component)this.startDateField1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, GroupLayout.Alignment.TRAILING).addComponent((Component)this.endDateField, GroupLayout.Alignment.TRAILING, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.idBarangField, -2, -1, -2).addComponent(this.namaBarangField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanBtn).addComponent(this.keluarBtn, -2, -1, -2).addComponent(this.detail, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 457, -2).addContainerGap()));
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void startDateFieldMouseClicked(MouseEvent evt) {
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void clearRow() {
        int row = this.reportTableModel.getRowCount();
        for (int a = 0; a < row; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDateField1.getDate());
        String endDateString = simpleDateFormat.format(this.endDateField.getDate());
        this.reportTableModel.setColumnIdentifiers(this.reportColumn);
        if (this.idBarangField.getText().equals("")) {
            List<MutasiBarang> mutasiBarangs = this.mutasiBarangService.getPembelian(startDateString, endDateString);
            this.clearRow();
            for (MutasiBarang mutasi : mutasiBarangs) {
                Object[] rowDatas = new Object[]{mutasi.getId(), mutasi.getTanggal(), mutasi.getDari() + "-" + mutasi.getMenuju(), mutasi.getKaryawanId(), mutasi.getKeterangan()};
                this.reportTableModel.addRow(rowDatas);
            }
        } else {
            Barang barang = this.barangService.getBarang(this.idBarangField.getText());
            this.reportTableModel.setColumnIdentifiers(this.reportColumnDetail);
            if (barang == null) {
                JOptionPane.showMessageDialog(null, "Barang Tidak Ditemukan");
            } else {
                this.clearRow();
                List<Object[]> objects = this.mutasiBarangDetailService.getMutasiBarangDetails(barang.getId(), startDateString, endDateString);
                for (Object[] object : objects) {
                    this.reportTableModel.addRow(object);
                    System.out.println("DATA " + object[0]);
                }
            }
        }
    }

    private void detailActionPerformed(ActionEvent evt) {
        if (this.reportTable.getSelectedRow() != -1) {
            String id = this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString();
            ReportMutasiDetail rpd = new ReportMutasiDetail(null, true, id);
            int w = rpd.getWidth();
            int h = rpd.getHeight();
            rpd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            rpd.setVisible(true);
        }
    }

    private void idBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        }
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            Barang barang = ReportMutasiPanel.this.barangService.getBarang(chooseValue);
            ReportMutasiPanel.this.idBarangField.setText(chooseValue);
            ReportMutasiPanel.this.namaBarangField.setText(barang.getNama());
        }
    }

    private class searchBarangDialogEventHandler
    implements SearchDialogListener {
        private searchBarangDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE BARANG", "NAMA BARANG", "SATUAN", "JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Barang> barangs = ReportMutasiPanel.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }
    }
}

