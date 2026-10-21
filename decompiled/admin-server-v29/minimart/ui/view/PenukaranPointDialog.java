/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.dao.CustomerDao;
import minimart.engine.daoimpl.CustomerDaoImpl;
import minimart.engine.model.Customer;
import minimart.engine.model.PenukaranPoint;
import minimart.engine.model.Voucher;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.CustomerCommitService;
import minimart.engine.service.CustomerOnlineDBService;
import minimart.engine.service.CustomerPointCommitService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.PenukaranPointService;
import minimart.engine.service.SyncService;
import minimart.engine.service.VoucherService;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PenukaranPointDialog
extends JDialog {
    private JLabel alamatField;
    private JButton cancelButton;
    private JTextField customerIdField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel namaCustomerField;
    private JComboBox<String> nominalCmb;
    private JButton okButton;
    private JLabel pointSkrgField;
    private JLabel totalPoin;
    private JTextField voucherDitukarField;
    private int screenWidth;
    private int screenHeight;
    private CustomerService customerService = new CustomerService();
    private Customer customer;
    private PenukaranPointService penukaranPointService = new PenukaranPointService();
    private CommonEngine comm = new CommonEngine();
    private CustomerPointCommitService customerPointCommitService = new CustomerPointCommitService();
    private CustomerOnlineDBService customerOnlineService = new CustomerOnlineDBService();
    private CustomerDao cd = new CustomerDaoImpl();
    private Helper helper = Helper.getInstance();
    private PenukaranPoint penukaranPoint = null;
    private VoucherService voucherService = new VoucherService();
    private SyncService syncService = new SyncService();
    private Voucher voucher;
    private int totalPoinBelanja;
    private CustomerCommitService customerCommitService = new CustomerCommitService();
    static JFrame f;
    static JProgressBar b;

    public PenukaranPointDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private void setDefault() {
        this.customerIdField.requestFocus();
        this.customerIdField.setText("");
        this.namaCustomerField.setText("");
        this.alamatField.setText("");
        this.voucherDitukarField.setText("");
        this.pointSkrgField.setText("");
        this.totalPoin.setText("");
    }

    private void saveClick() {
        int resultPenukaran;
        this.customer.setPoint(this.customer.getPoint() - (long)Integer.parseInt(this.totalPoin.getText()));
        this.customerPointCommitService.insertPointCommit(this.customer.getId(), this.customer.getId(), this.totalPoinBelanja * -1, "INSERT", "Penukaran");
        try {
            Customer customerOnline;
            Long pointCommit = this.customerCommitService.getPointUncommited(this.customer.getId());
            if (pointCommit != 0L) {
                customerOnline = this.customerOnlineService.addCustomerPoint(this.customer.getId(), pointCommit);
                int result = this.customerCommitService.updateStatusCommit(this.customer.getId());
                if (result <= 0) {
                    customerOnline = this.customerOnlineService.addCustomerPoint(this.customer.getId(), -1L * pointCommit);
                }
            } else {
                customerOnline = this.customerOnlineService.getCustomerById(this.customer.getId());
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Commit Point ke Online DB");
        }
        int resultCust = this.customerService.updateCustomer(this.customer);
        this.penukaranPoint = new PenukaranPoint();
        this.penukaranPoint.setCustomerId(this.customer.getId());
        this.penukaranPoint.setKaryawanId(this.helper.getKaryawan().getId());
        this.penukaranPoint.setJumlahPoint(this.totalPoinBelanja);
        this.penukaranPoint.setTanggal(new Date());
        if (resultCust == 1 && (resultPenukaran = this.penukaranPointService.createPenukaranPoint(this.penukaranPoint)) == 1) {
            this.voucher = new Voucher();
            this.voucher.setId(this.helper.generateCodeGeneral(this.voucherService.getLastId()));
            this.voucher.setCustomerId(this.customer.getId());
            this.voucher.setJumlah(Integer.parseInt(this.nominalCmb.getSelectedItem() + "") * Integer.parseInt(this.voucherDitukarField.getText()));
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(5, 7);
            this.voucher.setExpiredDate(c.getTime());
            this.voucher.setAvailable("TRUE");
            int voucherStatue = this.voucherService.createVoucher(this.voucher);
            JOptionPane.showMessageDialog(null, "Penukaran Point Berhasil");
            if (voucherStatue == 1) {
                JOptionPane.showMessageDialog(null, "Voucher telah berhasil dibuat");
            }
            PrinterJob job = PrinterJob.getPrinterJob();
            PageFormat pf = job.defaultPage();
            Paper p = pf.getPaper();
            p.setSize(p.getWidth(), p.getHeight() + 1500.0);
            p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
            pf.setPaper(p);
            Book book = new Book();
            book.append(new serverPrinter(), pf);
            job.setPageable(book);
            try {
                job.print();
            }
            catch (PrinterException printerException) {
                // empty catch block
            }
            this.setDefault();
            this.syncService.doSyncPenukaranPoinUnsynced();
            this.syncService.doSyncVoucherUnsynced();
        }
    }

    private void loadKodeCustomer() {
        this.customer = this.customerService.getCustomerById(this.customerIdField.getText());
        if (this.customer == null) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        } else {
            Customer customerOnline;
            Long pointCommit = this.customerCommitService.getPointUncommited(this.customer.getId());
            if (pointCommit != 0L) {
                customerOnline = this.customerOnlineService.addCustomerPoint(this.customer.getId(), pointCommit);
                int result = this.customerCommitService.updateStatusCommit(this.customer.getId());
                if (result <= 0) {
                    customerOnline = this.customerOnlineService.addCustomerPoint(this.customer.getId(), -1L * pointCommit);
                }
            } else {
                customerOnline = this.customerOnlineService.getCustomerById(this.customer.getId());
            }
            this.customer = customerOnline;
            this.namaCustomerField.setText(this.customer.getNama());
            this.alamatField.setText(this.customer.getAlamat());
            this.voucherDitukarField.requestFocus();
            this.voucherDitukarField.selectAll();
            this.pointSkrgField.setText(this.customer.getPoint() + "");
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.customerIdField = new JTextField();
        this.namaCustomerField = new JLabel();
        this.alamatField = new JLabel();
        this.pointSkrgField = new JLabel();
        this.cancelButton = new JButton();
        this.okButton = new JButton();
        this.voucherDitukarField = new JTextField();
        this.jLabel8 = new JLabel();
        this.nominalCmb = new JComboBox();
        this.totalPoin = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Form Penukaran Point Belanja");
        this.jLabel2.setText("ID Customer");
        this.jLabel3.setText("Nama Customer");
        this.jLabel4.setText("Alamat");
        this.jLabel5.setText("Jumlah Point Sekarang");
        this.jLabel6.setText("Nominal Voucher");
        this.jLabel7.setText("Total Poin yang Ditukar");
        this.customerIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenukaranPointDialog.this.customerIdFieldKeyPressed(evt);
            }
        });
        this.namaCustomerField.setText("-");
        this.alamatField.setText("-");
        this.pointSkrgField.setText("-");
        this.cancelButton.setText("Cancel");
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenukaranPointDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.okButton.setText("Ok");
        this.okButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenukaranPointDialog.this.okButtonKeyPressed(evt);
            }
        });
        this.voucherDitukarField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenukaranPointDialog.this.voucherDitukarFieldKeyPressed(evt);
            }
        });
        this.jLabel8.setText("Jumlah Voucher yang Ditukar");
        this.nominalCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"50000", "100000"}));
        this.totalPoin.setText("-");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(93, 93, 93).addComponent(this.jLabel1)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel8).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel4).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.jLabel7)).addGap(26, 26, 26).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaCustomerField, -1, -1, Short.MAX_VALUE).addComponent(this.alamatField, -1, -1, Short.MAX_VALUE).addComponent(this.pointSkrgField, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.okButton, -2, 68, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.customerIdField, -2, 166, -2).addComponent(this.nominalCmb, -2, 133, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.totalPoin, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.voucherDitukarField, GroupLayout.Alignment.LEADING, -1, 65, Short.MAX_VALUE))).addGap(93, 93, 93))).addGap(0, 19, Short.MAX_VALUE))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.customerIdField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.namaCustomerField).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.alamatField).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.pointSkrgField)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel4).addGap(18, 18, 18).addComponent(this.jLabel5).addGap(8, 8, 8).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.nominalCmb, -2, -1, -2)))).addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.voucherDitukarField, -2, -1, -2)).addGap(17, 17, 17).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.totalPoin)).addGap(41, 41, 41).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelButton).addComponent(this.okButton)).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void customerIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.customerIdField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
                sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.loadKodeCustomer();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void okButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.totalPoinBelanja > Integer.parseInt(this.pointSkrgField.getText())) {
                JOptionPane.showMessageDialog(null, "Poin Tidak Mencukupi");
            } else {
                this.saveClick();
            }
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() != 38 && evt.getKeyCode() == 27) {
            this.customerIdField.requestFocus();
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 37) {
            this.okButton.requestFocus();
        } else if (evt.getKeyCode() != 38 && evt.getKeyCode() == 27) {
            this.customerIdField.requestFocus();
        }
    }

    private void voucherDitukarFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.voucherDitukarField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Masukkan Jumlah Voucher Yang Ingin Ditukar");
            } else if (Integer.parseInt(this.voucherDitukarField.getText()) < 1) {
                JOptionPane.showMessageDialog(null, "Masukkan Jumlah Voucher Dengan Benar");
            } else {
                int point = 0;
                if (this.nominalCmb.getSelectedItem().equals("50000")) {
                    point = 550;
                } else if (this.nominalCmb.getSelectedItem().equals("100000")) {
                    point = 1000;
                }
                this.totalPoinBelanja = Integer.parseInt(this.voucherDitukarField.getText()) * point;
                if (this.totalPoinBelanja > Integer.parseInt(this.pointSkrgField.getText())) {
                    this.voucherDitukarField.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "Poin Tidak Mencukupi");
                } else {
                    this.voucherDitukarField.setBackground(Color.white);
                    this.okButton.requestFocus();
                }
                this.totalPoin.setText(this.totalPoinBelanja + "");
            }
        } else if (evt.getKeyCode() == 27) {
            this.customerIdField.requestFocus();
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (!"Nimbus".equals(info.getName())) continue;
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(PenukaranPointDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(PenukaranPointDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(PenukaranPointDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PenukaranPointDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                PenukaranPointDialog dialog = new PenukaranPointDialog((Frame)new JFrame(), true);
                dialog.addWindowListener(new WindowAdapter(){

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    class serverPrinter
    implements Printable {
        serverPrinter() {
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
            if (page > 0) {
                return 1;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            int y = 0;
            int x = 5;
            int yK = 18;
            int yP = 11;
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("Penukaran Poin Belanja ", x + 0, y + yK);
            g.drawString("Voucher ID " + PenukaranPointDialog.this.voucher.getId(), x + 0, y + (yK += yP));
            g.drawString("ID Pelanggan : " + PenukaranPointDialog.this.customer.getId(), x + 0, y + (yK += yP));
            g.drawString("Nama Pelanggan : " + PenukaranPointDialog.this.customer.getNama(), x + 0, y + (yK += yP));
            g.drawString("Jumlah Poin yang Ditukar : " + PenukaranPointDialog.this.totalPoin.getText(), x + 0, y + (yK += yP));
            int nominal = Integer.parseInt(PenukaranPointDialog.this.nominalCmb.getSelectedItem() + "");
            int totaluang = nominal * Integer.parseInt(PenukaranPointDialog.this.voucherDitukarField.getText());
            g.drawString("Nilai Voucher : " + totaluang, x + 0, y + (yK += yP));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(new Date());
            g.drawString("Tanggal: " + tanggal, x + 0, y + (yK += yP));
            yK += yP;
            return 0;
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PenukaranPointDialog.this.customerIdField.setText(chooseValue);
            PenukaranPointDialog.this.loadKodeCustomer();
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
            List<Customer> customers = PenukaranPointDialog.this.customerService.getCustomers(searchWord);
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
}

