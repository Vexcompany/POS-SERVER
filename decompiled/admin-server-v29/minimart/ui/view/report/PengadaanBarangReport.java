/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Supplier;
import minimart.engine.service.BarangService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.RBarangSupplierService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PengadaanBarangReport
extends JPanel {
    private DefaultPanelListener listener;
    private JButton cetakBtn;
    private JTable dataTabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JButton keluarBtn;
    private JTextField supplierField;
    private JLabel supplierLbl;
    private JButton tampilkanBtn;
    private JTextField tanggalField;
    private SupplierService supplierService = new SupplierService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private RBarangSupplierService rBarangSupplierService = new RBarangSupplierService();
    private BarangService barangService = new BarangService();
    private String[] pengadaanColumn = new String[]{"ID BARANG", "NAMA BARANG", "STOK TOKO", "STOK GUDANG", "PENJUALAN/BULAN"};
    private int screenWidth;
    private int screenHeight;
    private Supplier supplier;
    private Helper helper = Helper.getInstance();
    private DefaultTableModel pengadaanTM = new DefaultTableModel(new String[0][1], this.pengadaanColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public PengadaanBarangReport() {
        this.initComponents();
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        String dayString = date.format(new Date());
        this.tanggalField.setText(dayString);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.tanggalField.setEditable(false);
    }

    private void clearTable() {
        int size = this.pengadaanTM.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.pengadaanTM.removeRow(0);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.tampilkanBtn = new JButton();
        this.cetakBtn = new JButton();
        this.keluarBtn = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.dataTabel = new JTable();
        this.tanggalField = new JTextField();
        this.supplierField = new JTextField();
        this.supplierLbl = new JLabel();
        this.jLabel3 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal");
        this.jLabel2.setText("Supplier");
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengadaanBarangReport.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.cetakBtn.setText("Cetak");
        this.cetakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengadaanBarangReport.this.cetakBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengadaanBarangReport.this.keluarBtnActionPerformed(evt);
            }
        });
        this.dataTabel.setModel(this.pengadaanTM);
        this.jScrollPane1.setViewportView(this.dataTabel);
        this.supplierField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengadaanBarangReport.this.supplierFieldKeyPressed(evt);
            }
        });
        this.supplierLbl.setText("Supplier");
        this.jLabel3.setText("F2: CARI");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.tanggalField)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addGap(18, 18, 18).addComponent(this.supplierField)).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetakBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3)).addGroup(layout.createSequentialGroup().addGap(56, 56, 56).addComponent(this.supplierLbl, -2, 260, -2))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.tanggalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.supplierField, -2, -1, -2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.supplierLbl).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanBtn).addComponent(this.cetakBtn).addComponent(this.keluarBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 382, -2).addContainerGap(96, Short.MAX_VALUE)));
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void supplierFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.supplierField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
                sd.addSearchDialogListener(new searchSupplierEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
                this.tampilkanBtn.requestFocus();
            } else if (!this.supplierField.getText().equals("")) {
                this.supplier = this.supplierService.getSupplier(this.supplierField.getText());
                if (this.supplier == null) {
                    JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
                } else {
                    this.supplierLbl.setText(this.supplier.getNama());
                    this.tampilkanBtn.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
            sd.addSearchDialogListener(new searchSupplierEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
            this.tampilkanBtn.requestFocus();
        }
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.clearTable();
        List<Object[]> datas = this.rBarangSupplierService.readRBSBySupplierId(this.supplier.getId());
        for (Object[] data : datas) {
            Object[] rowValue = new Object[5];
            Barang tempBarang = this.barangService.getBarang(data[0].toString());
            rowValue[0] = tempBarang.getId();
            rowValue[1] = tempBarang.getNama();
            float stokTotal = tempBarang.getStokToko();
            float stok1 = 0.0f;
            float stok2 = 0.0f;
            float stok3 = 0.0f;
            if (tempBarang.getSatuan2().equals("") && tempBarang.getSatuan3().equals("")) {
                stok1 = stokTotal;
            } else if (tempBarang.getSatuan3().equals("")) {
                stok2 = (long)stokTotal / (long)tempBarang.getKonversi21();
                stok1 = this.helper.round(stokTotal % (float)tempBarang.getKonversi21(), 2);
            } else {
                stok3 = (long)stokTotal / (long)(tempBarang.getKonversi21() * tempBarang.getKonversi32());
                stok2 = (long)(stokTotal % (float)(tempBarang.getKonversi21() * tempBarang.getKonversi32())) / (long)tempBarang.getKonversi21();
                stok1 = this.helper.round(stokTotal % (float)(tempBarang.getKonversi21() * tempBarang.getKonversi32()) % (float)tempBarang.getKonversi21(), 2);
            }
            String stokTokoString = stok1 + tempBarang.getSatuan1() + ", " + stok2 + tempBarang.getSatuan2() + ", " + stok3 + tempBarang.getSatuan3();
            rowValue[2] = stokTokoString;
            stokTotal = tempBarang.getStokGudang();
            stok1 = 0.0f;
            stok2 = 0.0f;
            stok3 = 0.0f;
            if (tempBarang.getSatuan2().equals("") && tempBarang.getSatuan3().equals("")) {
                stok1 = stokTotal;
            } else if (tempBarang.getSatuan3().equals("")) {
                stok2 = (long)stokTotal / (long)tempBarang.getKonversi21();
                stok1 = this.helper.round(stokTotal % (float)tempBarang.getKonversi21(), 2);
            } else {
                stok3 = (long)stokTotal / (long)(tempBarang.getKonversi21() * tempBarang.getKonversi32());
                stok2 = (long)(stokTotal % (float)(tempBarang.getKonversi21() * tempBarang.getKonversi32())) / (long)tempBarang.getKonversi21();
                stok1 = this.helper.round(stokTotal % (float)(tempBarang.getKonversi21() * tempBarang.getKonversi32()) % (float)tempBarang.getKonversi21(), 2);
            }
            String stokGudangString = stok1 + tempBarang.getSatuan1() + ", " + stok2 + tempBarang.getSatuan2() + ", " + stok3 + tempBarang.getSatuan3();
            rowValue[3] = stokGudangString;
            String[] dates = this.helper.getStartEndDate(new Date());
            List<Object[]> dataBarangs = this.penjualanDetailService.getTotalKeuntunganByBarang(dates[0], dates[1], tempBarang, "TOKO");
            float totalPenjualan = 0.0f;
            String penjualanString = "";
            for (Object[] dataBarang : dataBarangs) {
                if (dataBarang[3].equals(tempBarang.getSatuan1())) {
                    totalPenjualan += ((Double)dataBarang[2]).floatValue();
                } else if (dataBarang[3].equals(tempBarang.getSatuan2())) {
                    totalPenjualan += ((Double)dataBarang[2]).floatValue() * (float)tempBarang.getKonversi21();
                } else if (dataBarang[3].equals(tempBarang.getSatuan3())) {
                    totalPenjualan += ((Double)dataBarang[2]).floatValue() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32();
                }
                stok1 = 0.0f;
                stok2 = 0.0f;
                stok3 = 0.0f;
                if (tempBarang.getSatuan2().equals("") && tempBarang.getSatuan3().equals("")) {
                    stok1 = totalPenjualan;
                } else if (tempBarang.getSatuan3().equals("")) {
                    stok2 = (long)totalPenjualan / (long)tempBarang.getKonversi21();
                    stok1 = this.helper.round(totalPenjualan % (float)tempBarang.getKonversi21(), 2);
                } else {
                    stok3 = (long)totalPenjualan / (long)(tempBarang.getKonversi21() * tempBarang.getKonversi32());
                    stok2 = (long)(totalPenjualan % (float)(tempBarang.getKonversi21() * tempBarang.getKonversi32())) / (long)tempBarang.getKonversi21();
                    stok1 = this.helper.round(totalPenjualan % (float)(tempBarang.getKonversi21() * tempBarang.getKonversi32()) % (float)tempBarang.getKonversi21(), 2);
                }
                penjualanString = stok1 + tempBarang.getSatuan1() + ", " + stok2 + tempBarang.getSatuan2() + ", " + stok3 + tempBarang.getSatuan3();
            }
            rowValue[4] = penjualanString;
            this.pengadaanTM.addRow(rowValue);
        }
    }

    private void cetakBtnActionPerformed(ActionEvent evt) {
        try {
            this.dataTabel.print(JTable.PrintMode.FIT_WIDTH);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private class searchPanelSupplierEventHandler
    implements SearchPanelListener {
        private searchPanelSupplierEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PengadaanBarangReport.this.supplier = PengadaanBarangReport.this.supplierService.getSupplier(chooseValue);
            PengadaanBarangReport.this.supplierLbl.setText(PengadaanBarangReport.this.supplier.getNama() + " / " + PengadaanBarangReport.this.supplier.getAlamat());
            PengadaanBarangReport.this.supplierField.setText(PengadaanBarangReport.this.supplier.getId());
        }
    }

    private class searchSupplierEventHandler
    implements SearchDialogListener {
        private searchSupplierEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SUPPLIER", "NAMA SUPPLIER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Supplier> suppliers = PengadaanBarangReport.this.supplierService.getSuppliers(searchWord);
            for (Supplier supplier : suppliers) {
                Object[] temp = new String[]{supplier.getId(), supplier.getNama(), supplier.getAlamat(), supplier.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Supplier");
            sp.addSearchPanelListener(new searchPanelSupplierEventHandler());
            sp.setVisible(true);
        }
    }
}

