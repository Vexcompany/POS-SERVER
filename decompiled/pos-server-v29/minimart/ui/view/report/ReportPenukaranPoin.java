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
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.model.PenukaranPoint;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.MutasiBarangService;
import minimart.engine.service.PenukaranPointService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class ReportPenukaranPoin
extends JPanel {
    private JDateChooser endDateField;
    private JTextField idField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JButton keluarBtn;
    private JLabel namaField;
    private JTable reportTable;
    private JDateChooser startDateField;
    private JDateChooser startDateField1;
    private JButton tampilkanBtn;
    private DefaultPanelListener listener;
    private String[] reportColumn = new String[]{"ID", "CUSTOMER", "TANGGAL", "Karyawan", "jumlah point", "KETERANGAN"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private MutasiBarangService mutasiBarangService = new MutasiBarangService();
    private KaryawanService karyawanService = new KaryawanService();
    private int screenWidth;
    private int screenHeight;
    private CustomerService customerService = new CustomerService();
    private PenukaranPointService penukaranPointService = new PenukaranPointService();
    private Helper helper = Helper.getInstance();
    private Customer customer;

    public ReportPenukaranPoin() {
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
        this.jLabel3 = new JLabel();
        this.idField = new JTextField();
        this.namaField = new JLabel();
        this.startDateField.addMouseListener((MouseListener)new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportPenukaranPoin.this.startDateFieldMouseClicked(evt);
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
                ReportPenukaranPoin.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenukaranPoin.this.keluarBtnActionPerformed(evt);
            }
        });
        this.jLabel3.setText("Customer");
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenukaranPoin.this.idFieldKeyPressed(evt);
            }
        });
        this.namaField.setText("nama");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent((Component)this.startDateField1, -2, 120, -2)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel3)).addGap(32, 32, 32).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.idField).addComponent((Component)this.endDateField, -1, 120, Short.MAX_VALUE)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.namaField))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel1).addComponent((Component)this.startDateField1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, GroupLayout.Alignment.TRAILING).addComponent((Component)this.endDateField, GroupLayout.Alignment.TRAILING, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.idField, -2, -1, -2).addComponent(this.namaField).addComponent(this.jLabel3)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanBtn).addComponent(this.keluarBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 454, -2).addContainerGap()));
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
        this.clearRow();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDateField1.getDate());
        String endDateString = simpleDateFormat.format(this.endDateField.getDate());
        List<PenukaranPoint> penukaranPoints = this.idField.getText().equals("") ? this.penukaranPointService.getPenukaranPoints(startDateString, endDateString, "") : this.penukaranPointService.getPenukaranPoints(startDateString, endDateString, this.customer.getId());
        for (PenukaranPoint penukaranPoint : penukaranPoints) {
            Object[] rowData = new Object[]{penukaranPoint.getId(), penukaranPoint.getCustomerId() + "-" + this.customerService.getCustomerById(penukaranPoint.getCustomerId()).getNama(), penukaranPoint.getTanggal(), penukaranPoint.getKaryawanId(), penukaranPoint.getJumlahPoint(), penukaranPoint.getKeterangan()};
            this.reportTableModel.addRow(rowData);
        }
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.idField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
                sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.customer = this.customerService.getCustomerById(this.idField.getText());
                if (this.customer == null) {
                    JOptionPane.showMessageDialog(null, "Data Customer Tidak Ditemukan");
                } else {
                    this.idField.setText(this.customer.getId());
                    this.namaField.setText(this.customer.getNama());
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

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportPenukaranPoin.this.customer = ReportPenukaranPoin.this.customerService.getCustomerById(chooseValue);
            ReportPenukaranPoin.this.idField.setText(ReportPenukaranPoin.this.customer.getId());
            ReportPenukaranPoin.this.namaField.setText(ReportPenukaranPoin.this.customer.getNama() + "-" + ReportPenukaranPoin.this.customer.getAlamat());
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
            List<Customer> customers = ReportPenukaranPoin.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp;
                if (ReportPenukaranPoin.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO")) {
                    if (ReportPenukaranPoin.this.helper.getStatusProgram().equalsIgnoreCase("TOKO")) {
                        temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                        dtm.addRow(temp);
                        continue;
                    }
                    if (!ReportPenukaranPoin.this.helper.getStatusProgram().equalsIgnoreCase("GUDANG") || !customer.getNama().equalsIgnoreCase("SINARMART")) continue;
                    temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                    dtm.addRow(temp);
                    continue;
                }
                if (!ReportPenukaranPoin.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) continue;
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
}

