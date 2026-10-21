/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.model.Karyawan;
import minimart.engine.model.PengembalianBarang;
import minimart.engine.model.Penjualan;
import minimart.engine.model.Sales;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.PengembalianBarangDetailService;
import minimart.engine.service.PengembalianBarangService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.SalesService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;
import minimart.ui.view.report.ReportPengembalianBarangDetail;

public class ReportPengembalianBarang
extends JPanel {
    private JTextField customerField;
    private JLabel customerLbl;
    private JButton detailBtn;
    private JDateChooser endDateField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JScrollPane jScrollPane1;
    private JButton keluarButton;
    private JTable reportTable;
    private JTextField salesField;
    private JLabel salesLbl;
    private JDateChooser startDateField;
    private JButton tampilkanButton;
    private JButton tampilkanDetailBtn;
    private JFormattedTextField totalField;
    private String[] reportColumn = new String[]{"ID Pengembalian", "Tanggal", "Petugas", "Customer", "Total Pengembalian", "ID Penjualan", "Kasir", "Pelunasan", "Shift"};
    private String[] reportDetailColumn = new String[]{"ID Pengembalian", "ID Penjualan", "Nama Customer", "Tanggal", "Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total"};
    private BarangService barangService = new BarangService();
    private int screenHeight;
    private int screenWidth;
    private Sales sales;
    private SalesService salesService = new SalesService();
    private CustomerService customerService = new CustomerService();
    private PengembalianBarangService pengembalianBarangService = new PengembalianBarangService();
    private PengembalianBarangDetailService pengembalianBarangDetailService = new PengembalianBarangDetailService();
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PenjualanService penjualanService = new PenjualanService();
    private DefaultPanelListener defaultPanelListener;
    private Helper helper = Helper.getInstance();

    public ReportPengembalianBarang() {
        this.initComponents();
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.startDateField = new JDateChooser();
        this.endDateField = new JDateChooser();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.jLabel3 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jLabel4 = new JLabel();
        this.customerField = new JTextField();
        this.customerLbl = new JLabel();
        this.detailBtn = new JButton();
        this.tampilkanDetailBtn = new JButton();
        this.salesField = new JTextField();
        this.jLabel6 = new JLabel();
        this.salesLbl = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Dari Tanggal");
        this.jLabel2.setText("Sampai Tanggal");
        this.tampilkanButton.setText("Tampilkan Global");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPengembalianBarang.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPengembalianBarang.this.keluarButtonActionPerformed(evt);
            }
        });
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.jLabel3.setFont(new Font("Tahoma", 0, 14));
        this.jLabel3.setText("Total");
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.totalField.setFont(new Font("Tahoma", 0, 14));
        this.jLabel4.setText("Customer");
        this.customerField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPengembalianBarang.this.customerFieldKeyPressed(evt);
            }
        });
        this.customerLbl.setText("-");
        this.detailBtn.setText("Detail");
        this.detailBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPengembalianBarang.this.detailBtnActionPerformed(evt);
            }
        });
        this.tampilkanDetailBtn.setText("Tampilkan Detail");
        this.tampilkanDetailBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPengembalianBarang.this.tampilkanDetailBtnActionPerformed(evt);
            }
        });
        this.salesField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPengembalianBarang.this.salesFieldKeyPressed(evt);
            }
        });
        this.jLabel6.setText("Salesman");
        this.salesLbl.setText("-");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanDetailBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tampilkanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.detailBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1).addComponent(this.jLabel4).addComponent(this.jLabel6)).addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.salesLbl, -1, -1, Short.MAX_VALUE).addComponent(this.salesField).addComponent(this.customerField).addComponent((Component)this.endDateField, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent((Component)this.startDateField, -2, 144, -2).addGap(0, 0, Short.MAX_VALUE))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGap(245, 245, 245).addComponent(this.jLabel3)).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerLbl, -1, -1, Short.MAX_VALUE).addGap(165, 165, 165))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, 235, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addGroup(layout.createSequentialGroup().addComponent((Component)this.startDateField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.endDateField, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(17, 17, 17).addComponent(this.jLabel2))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(6, 6, 6).addComponent(this.jLabel4)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.customerField, -2, -1, -2).addComponent(this.customerLbl)))).addComponent(this.totalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.salesField, -2, -1, -2).addComponent(this.jLabel6)).addGap(3, 3, 3).addComponent(this.salesLbl).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanButton).addComponent(this.keluarButton).addComponent(this.detailBtn).addComponent(this.tampilkanDetailBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
    }

    private void clearTable() {
        this.totalField.setValue(0);
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.clearTable();
        this.reportTableModel.setColumnIdentifiers(this.reportColumn);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDateField.getDate());
        String endDateString = simpleDateFormat.format(this.endDateField.getDate());
        List<PengembalianBarang> datas = this.pengembalianBarangService.getPengembalianBarang(startDateString, endDateString, this.customerField.getText());
        long totalPengembalian = 0L;
        for (PengembalianBarang pb : datas) {
            Penjualan tempPenjualan;
            Object[] rowDatas = new Object[]{pb.getId(), pb.getTanggal(), pb.getKaryawanId(), pb.getCustomerId() + " / " + this.customerService.getCustomerById(pb.getCustomerId()).getNama(), pb.getTotalPengembalian(), pb.getKeterangan(), (tempPenjualan = this.penjualanService.getPenjualan(pb.getKeterangan())) == null ? "" : tempPenjualan.getKaryawanId(), pb.getPelunasan(), pb.getShiftKasirId()};
            this.reportTableModel.addRow(rowDatas);
            totalPengembalian += pb.getTotalPengembalian();
        }
        this.totalField.setValue(totalPengembalian);
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void customerFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.customerField.getText().equals("")) {
                this.tampilkanButton.requestFocus();
            } else {
                this.loadKodeCustomer();
            }
        }
    }

    private void detailBtnActionPerformed(ActionEvent evt) {
        if (this.reportTable.getSelectedRow() != -1) {
            String id = this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString();
            ReportPengembalianBarangDetail rpd = new ReportPengembalianBarangDetail(null, true, Long.parseLong(id));
            int w = rpd.getWidth();
            int h = rpd.getHeight();
            rpd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            rpd.setVisible(true);
        }
    }

    private void tampilkanDetailBtnActionPerformed(ActionEvent evt) {
        this.loadDetailRetur();
    }

    private void salesFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
            sd.addSearchDialogListener(new searchSalesDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.salesField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
                sd.addSearchDialogListener(new searchSalesDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.sales = this.salesService.getSales(this.salesField.getText());
                if (this.sales == null) {
                    JOptionPane.showMessageDialog(null, "Data Sales Tidak Ditemukan");
                } else {
                    this.salesLbl.setName(this.sales.getNama());
                    this.reportTable.requestFocus();
                }
            }
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void loadDetailRetur() {
        this.clearTable();
        this.reportTableModel.setColumnIdentifiers(this.reportDetailColumn);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDateField.getDate());
        String endDateString = simpleDateFormat.format(this.endDateField.getDate());
        List<Object[]> datas = this.pengembalianBarangDetailService.getPengembalianBarangDetail(startDateString, endDateString, this.salesField.getText());
        long totalPengembalian = 0L;
        long totalMargin = 0L;
        for (Object[] pb : datas) {
            Object[] rowVal = new Object[]{pb[0], pb[1], pb[2], pb[3], pb[4], pb[5], pb[6], pb[7], pb[8], pb[9]};
            this.reportTableModel.addRow(rowVal);
            totalPengembalian += Long.parseLong(pb[9].toString());
        }
        this.totalField.setValue(totalPengembalian);
    }

    private void loadKodeCustomer() {
        Customer customer = this.customerService.getCustomerById(this.customerField.getText());
        if (customer == null) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        } else {
            this.customerLbl.setText(customer.getNama() + "/" + customer.getAlamat());
            this.tampilkanButton.requestFocus();
        }
    }

    private class passwordInputReportDetail
    implements PasswordInputListener {
        private passwordInputReportDetail() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) {
                ReportPengembalianBarang.this.loadDetailRetur();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportPengembalianBarang.this.customerField.setText(chooseValue);
            ReportPengembalianBarang.this.loadKodeCustomer();
        }
    }

    private class searchCustomerDialogEventHandler
    implements SearchDialogListener {
        private searchCustomerDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE CUSTOMER", "NAMA CUSTOMER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Customer> customers = ReportPengembalianBarang.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Customer");
            sp.addSearchPanelListener(new searchPanelCustomerEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class searchSalesDialogEventHandler
    implements SearchDialogListener {
        private searchSalesDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SALES", "NAMA SALES", "ALAMAT", "TARGET"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Sales> saleses = ReportPengembalianBarang.this.salesService.getSaleses(searchWord);
            for (Sales sales : saleses) {
                Object[] temp = new String[]{sales.getId(), sales.getNama(), sales.getAlamat(), sales.getTarget() + ""};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Sales");
            sp.addSearchPanelListener(new searchPanelSalesEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private class searchPanelSalesEventHandler
        implements SearchPanelListener {
            private searchPanelSalesEventHandler() {
            }

            @Override
            public void tableClick(String chooseValue) {
                ReportPengembalianBarang.this.salesField.setText(chooseValue);
                ReportPengembalianBarang.this.sales = ReportPengembalianBarang.this.salesService.getSales(ReportPengembalianBarang.this.salesField.getText());
                ReportPengembalianBarang.this.salesLbl.setText(ReportPengembalianBarang.this.sales.getNama());
            }
        }
    }
}

