/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
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
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.model.Penjualan;
import minimart.engine.model.ShiftKasir;
import minimart.engine.service.CustomerService;
import minimart.engine.service.PelunasanPiutangService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.SalesService;
import minimart.engine.service.ShiftKasirService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PelunasanPiutangDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.PelunasanPiutangCustomer;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;
import minimart.ui.view.report.ReportPenjualanDetail;

public class ReportPiutangCustomer
extends JPanel {
    private JTextField customerField;
    private JLabel customerLbl;
    private JButton detailBtn;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JButton keluarButton;
    private JButton pelunasanBtn;
    private JTable reportTable;
    private JFormattedTextField sisaField;
    private JButton tampilkanButton;
    private JFormattedTextField totalField;
    private String[] reportColumn = new String[]{"ID Penjualan", "Customer", "Tanggal", "Jatuh Tempo", "Sales", "Jumlah", "Keterangan", "STATUS PELUNASAN", "Referensi"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PenjualanService penjualanService = new PenjualanService();
    private DefaultPanelListener defaultPanelListener;
    private Helper helper = Helper.getInstance();
    private CustomerService customerService = new CustomerService();
    private Customer customer = new Customer();
    private int screenWidth;
    private int screenHeight;
    private SalesService salesService = new SalesService();
    private boolean canGoThrough;
    private PelunasanPiutangService pelunasanPiutangService = new PelunasanPiutangService();
    private ShiftKasir shiftKasir;

    public ReportPiutangCustomer() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.shiftKasir = new ShiftKasirService().getOpenedShiftKasir(this.helper.getKasir());
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.jLabel3 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.detailBtn = new JButton();
        this.customerField = new JTextField();
        this.customerLbl = new JLabel();
        this.pelunasanBtn = new JButton();
        this.sisaField = new JFormattedTextField();
        this.jLabel2 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.addAncestorListener(new AncestorListener(){

            @Override
            public void ancestorMoved(AncestorEvent evt) {
            }

            @Override
            public void ancestorAdded(AncestorEvent evt) {
                ReportPiutangCustomer.this.formAncestorAdded(evt);
            }

            @Override
            public void ancestorRemoved(AncestorEvent evt) {
            }
        });
        this.jLabel1.setText("ID Customer");
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPiutangCustomer.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(72, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPiutangCustomer.this.keluarButtonActionPerformed(evt);
            }
        });
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.jLabel3.setFont(new Font("Tahoma", 0, 24));
        this.jLabel3.setText("Total");
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.totalField.setFont(new Font("Tahoma", 0, 18));
        this.detailBtn.setText("Detail");
        this.detailBtn.setPreferredSize(new Dimension(72, 23));
        this.detailBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPiutangCustomer.this.detailBtnActionPerformed(evt);
            }
        });
        this.customerField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPiutangCustomer.this.customerFieldKeyPressed(evt);
            }
        });
        this.customerLbl.setText("-");
        this.pelunasanBtn.setText("Pelunasan");
        this.pelunasanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPiutangCustomer.this.pelunasanBtnActionPerformed(evt);
            }
        });
        this.sisaField.setFont(new Font("Tahoma", 0, 18));
        this.jLabel2.setFont(new Font("Tahoma", 0, 18));
        this.jLabel2.setText("Sisa Limit");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.tampilkanButton).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.customerLbl, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(this.pelunasanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.detailBtn, -2, -1, -2)).addComponent(this.customerField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.keluarButton, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel2, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.sisaField, GroupLayout.Alignment.TRAILING).addComponent(this.totalField, GroupLayout.Alignment.TRAILING, -1, 235, Short.MAX_VALUE)))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.customerField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerLbl))).addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanButton).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.detailBtn, -2, -1, -2).addComponent(this.pelunasanBtn))).addGroup(layout.createSequentialGroup().addComponent(this.totalField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.sisaField, -2, -1, -2).addComponent(this.jLabel2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 444, -2).addContainerGap(52, Short.MAX_VALUE)));
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.clearTable();
        List<Object[]> piutangs = this.penjualanService.getPiutang(this.customerField.getText());
        long totalPiutang = 0L;
        for (Object[] piutang : piutangs) {
            Object[] row = new Object[]{piutang[0], piutang[1], piutang[2], piutang[3], piutang[4], piutang[5], ((Date)piutang[3]).compareTo(new Date()) == 0 || ((Date)piutang[3]).compareTo(new Date()) < 0 ? "JATUH TEMPO" : "", piutang[6], piutang[7]};
            totalPiutang += ((Long)piutang[5]).longValue();
            this.reportTableModel.addRow(row);
        }
        this.totalField.setValue(totalPiutang);
        if (!this.customerField.getText().equals("")) {
            this.sisaField.setValue((long)this.customer.getLimitKredit() - totalPiutang);
        } else {
            this.sisaField.setValue(0);
        }
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void customerFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.customerField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
                sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.customer = this.customerService.getCustomerById(this.customerField.getText());
                if (this.customer == null) {
                    JOptionPane.showMessageDialog(null, "Data Customer Tidak Ditemukan");
                } else {
                    this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
                }
            }
        } else if (evt.getKeyCode() == 112 || evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        }
    }

    private void pelunasanBtnActionPerformed(ActionEvent evt) {
        Penjualan penjualan = this.penjualanService.getPenjualan(this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString());
        if (penjualan.getStatusPelunasan().equals("SUDAH LUNAS")) {
            JOptionPane.showMessageDialog(null, "Piutang Sudah Lunas");
        } else if (this.shiftKasir != null) {
            PelunasanPiutangCustomer pelunasanPiutangCustomer = new PelunasanPiutangCustomer(null, true, penjualan, this.reportTable.getSelectedRow());
            pelunasanPiutangCustomer.addListener(new pelunasanPiutangEventHandler());
            int w = pelunasanPiutangCustomer.getWidth();
            int h = pelunasanPiutangCustomer.getHeight();
            pelunasanPiutangCustomer.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            pelunasanPiutangCustomer.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Aktifkan shift kasir untuk melakukan pelunasan");
        }
    }

    private void formAncestorAdded(AncestorEvent evt) {
    }

    private void detailBtnActionPerformed(ActionEvent evt) {
        if (this.reportTable.getSelectedRow() != -1) {
            String id = this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString();
            ReportPenjualanDetail rpd = new ReportPenjualanDetail(null, true, id);
            int w = rpd.getWidth();
            int h = rpd.getHeight();
            rpd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            rpd.setVisible(true);
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportPiutangCustomer.this.customer = ReportPiutangCustomer.this.customerService.getCustomerById(chooseValue);
            ReportPiutangCustomer.this.customerField.setText(ReportPiutangCustomer.this.customer.getId());
            ReportPiutangCustomer.this.customerLbl.setText(ReportPiutangCustomer.this.customer.getNama() + "-" + ReportPiutangCustomer.this.customer.getAlamat());
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
            List<Customer> customers = ReportPiutangCustomer.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp;
                if (ReportPiutangCustomer.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO")) {
                    if (ReportPiutangCustomer.this.helper.getStatusProgram().equalsIgnoreCase("TOKO")) {
                        temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                        dtm.addRow(temp);
                        continue;
                    }
                    if (!ReportPiutangCustomer.this.helper.getStatusProgram().equalsIgnoreCase("GUDANG") || !customer.getNama().equalsIgnoreCase("SINARMART")) continue;
                    temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                    dtm.addRow(temp);
                    continue;
                }
                if (!ReportPiutangCustomer.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) continue;
                temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Customer");
            sp.addSearchPanelListener(new searchPanelCustomerEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            ReportPiutangCustomer.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            ReportPiutangCustomer.this.canGoThrough = false;
        }
    }

    private class pelunasanPiutangEventHandler
    implements PelunasanPiutangDialogListener {
        private pelunasanPiutangEventHandler() {
        }

        @Override
        public void lunasSaveClick(int row) {
            ReportPiutangCustomer.this.reportTableModel.setValueAt("SUDAH LUNAS", row, 7);
        }
    }
}

