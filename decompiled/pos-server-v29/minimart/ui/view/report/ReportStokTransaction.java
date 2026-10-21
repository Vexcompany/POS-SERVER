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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Jenis;
import minimart.engine.service.BarangService;
import minimart.engine.service.JenisService;
import minimart.engine.service.StokCommitService;
import minimart.engine.service.StokOpnamMandiriDetailService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class ReportStokTransaction
extends JPanel {
    private JLabel barangField;
    private JDateChooser endDateField;
    private JTextField idBarangField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JScrollPane jScrollPane1;
    private JTextField jenisField;
    private JButton keluarButton;
    private JComboBox<String> lokasiCmb;
    private JComboBox<String> pencarianCmb;
    private JTable reportTable;
    private JDateChooser startDateField;
    private JButton tampilkanButton;
    private String[] reportColumn = new String[]{"ID Barang", "Nama Barang", "Jumlah Barang", "Satuan", "Query Tipe", "Stok Type"};
    private String[] reportColumn1 = new String[]{"ID Barang", "Nama Barang", "Jumlah Barang", "Satuan", "Stock Type", "Query Type", "Id Transaksi", "Transaction Type", "Transaction Created", "Stok Awal"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultPanelListener defaultPanelListener;
    private Helper helper = Helper.getInstance();
    private StokCommitService stokCommitService = new StokCommitService();
    private SearchDialog sd;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private JenisService jenisService = new JenisService();
    private BarangService barangService = new BarangService();
    private StokOpnamMandiriDetailService stokOpnamMandiriDetailService = new StokOpnamMandiriDetailService();
    private static final int SOM_HIDE_N_DAYS = 1;

    public ReportStokTransaction() {
        this.initComponents();
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.idBarangField.setEnabled(false);
        this.jenisField.setEnabled(true);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.startDateField = new JDateChooser();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.jLabel2 = new JLabel();
        this.endDateField = new JDateChooser();
        this.lokasiCmb = new JComboBox();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.pencarianCmb = new JComboBox();
        this.jLabel6 = new JLabel();
        this.idBarangField = new JTextField();
        this.jenisField = new JTextField();
        this.barangField = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Dari Tanggal");
        this.startDateField.addMouseListener((MouseListener)new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportStokTransaction.this.startDateFieldMouseClicked(evt);
            }
        });
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokTransaction.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokTransaction.this.keluarButtonActionPerformed(evt);
            }
        });
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.jLabel2.setText("Sampai Tanggal");
        this.endDateField.addMouseListener((MouseListener)new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportStokTransaction.this.endDateFieldMouseClicked(evt);
            }
        });
        this.lokasiCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"gudang", "toko"}));
        this.jLabel4.setText("Lokasi");
        this.jLabel5.setText("Pencarian");
        this.pencarianCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"Jenis", "Barang"}));
        this.pencarianCmb.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                ReportStokTransaction.this.pencarianCmbItemStateChanged(evt);
            }
        });
        this.jLabel6.setText("ID Barang");
        this.idBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportStokTransaction.this.idBarangFieldKeyPressed(evt);
            }
        });
        this.jenisField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportStokTransaction.this.jenisFieldKeyPressed(evt);
            }
        });
        this.barangField.setText("-");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.tampilkanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton, -2, 75, -2)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel1).addGap(39, 39, 39).addComponent((Component)this.startDateField, -2, 150, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4, -1, -1, Short.MAX_VALUE).addGap(20, 20, 20)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.lokasiCmb, 0, -1, Short.MAX_VALUE).addComponent((Component)this.endDateField, -1, 150, Short.MAX_VALUE).addComponent(this.pencarianCmb, 0, -1, Short.MAX_VALUE).addComponent(this.idBarangField)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jenisField, -1, 153, Short.MAX_VALUE).addComponent(this.barangField, -1, -1, Short.MAX_VALUE)).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel2)).addGroup(layout.createSequentialGroup().addComponent((Component)this.startDateField, -2, -1, -2).addGap(5, 5, 5).addComponent((Component)this.endDateField, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.lokasiCmb, -2, -1, -2).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.pencarianCmb, -2, -1, -2).addComponent(this.jenisField, -2, -1, -2)).addGap(6, 6, 6).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.idBarangField, -2, -1, -2).addComponent(this.barangField)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanButton).addComponent(this.keluarButton)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 362, -2).addContainerGap(59, Short.MAX_VALUE)));
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        String result = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetailOnProgress(this.idBarangField.getText(), 1);
        if (result.equals("")) {
            this.clearTable();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDateString = simpleDateFormat.format(this.startDateField.getDate());
            String endDateString = simpleDateFormat.format(this.endDateField.getDate());
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(simpleDateFormat.parse(endDateString));
            }
            catch (ParseException ex) {
                Logger.getLogger(ReportStokTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
            c.add(5, 1);
            endDateString = simpleDateFormat.format(c.getTime());
            HashSet<String> hiddenIds = new HashSet<String>(this.stokOpnamMandiriDetailService.getBarangIdHiddenBySom(1));
            if (this.pencarianCmb.getSelectedItem().toString().equalsIgnoreCase("Jenis")) {
                this.reportTableModel.setColumnIdentifiers(this.reportColumn);
                List<Object[]> stokTransactions = this.stokCommitService.getStockTransaction(startDateString, endDateString, this.jenisField.getText(), this.lokasiCmb.getSelectedItem().toString());
                for (Object[] stokTransaction : stokTransactions) {
                    if (hiddenIds.contains(String.valueOf(stokTransaction[0]))) {
                        stokTransaction[2] = "-";
                    }
                    this.reportTableModel.addRow(stokTransaction);
                }
            } else if (this.pencarianCmb.getSelectedItem().toString().equalsIgnoreCase("Barang")) {
                this.reportTableModel.setColumnIdentifiers(this.reportColumn1);
                List<Object[]> stokTransactions = this.stokCommitService.getStockTransactionByBarang(startDateString, endDateString, this.idBarangField.getText(), this.lokasiCmb.getSelectedItem().toString());
                for (Object[] stokTransaction : stokTransactions) {
                    if (hiddenIds.contains(String.valueOf(stokTransaction[0]))) {
                        stokTransaction[2] = "-";
                    }
                    this.reportTableModel.addRow(stokTransaction);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item Ini Sedang Dilakukan Stok Opnam Mandiri");
        }
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void startDateFieldMouseClicked(MouseEvent evt) {
    }

    private void endDateFieldMouseClicked(MouseEvent evt) {
    }

    private void pencarianCmbItemStateChanged(ItemEvent evt) {
        if (this.pencarianCmb.getSelectedItem().toString().equalsIgnoreCase("Jenis")) {
            this.idBarangField.setEnabled(false);
            this.jenisField.setEnabled(true);
        } else {
            this.idBarangField.setEnabled(true);
            this.jenisField.setEnabled(false);
        }
    }

    private void jenisFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
            this.sd.addSearchDialogListener(new searchJenisDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
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

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportStokTransaction.this.barangField.setText(ReportStokTransaction.this.barangService.getBarang(chooseValue).getNama());
            ReportStokTransaction.this.idBarangField.setText(chooseValue);
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
            List<Barang> barangs = ReportStokTransaction.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }
    }

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportStokTransaction.this.jenisField.setText(chooseValue);
            ReportStokTransaction.this.tampilkanButton.requestFocus();
        }
    }

    private class searchJenisDialogEventHandler
    implements SearchDialogListener {
        private searchJenisDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Jenis> jenisses = ReportStokTransaction.this.jenisService.getJenisses(searchWord);
            for (Jenis jenis : jenisses) {
                Object[] temp = new String[]{jenis.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Jenis Barang");
            sp.addSearchPanelListener(new searchPanelJenisEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }
}

