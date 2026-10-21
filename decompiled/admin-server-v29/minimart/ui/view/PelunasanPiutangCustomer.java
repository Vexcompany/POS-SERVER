/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.model.Karyawan;
import minimart.engine.model.PelunasanPiutang;
import minimart.engine.model.Penjualan;
import minimart.engine.model.ShiftKasir;
import minimart.engine.service.CustomerService;
import minimart.engine.service.PelunasanPiutangService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.ShiftKasirService;
import minimart.engine.service.SyncService;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.PelunasanPiutangDialogListener;
import minimart.ui.view.PasswordInputDialog;

public class PelunasanPiutangCustomer
extends JDialog {
    private JButton batalBtn;
    private JComboBox<String> caraPembayaranCmb;
    private JLabel idCustomerLbl;
    private JLabel idPenjualanLbl;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel9;
    private JFormattedTextField jumlahPelunasanField;
    private JLabel namaCustomerLbl;
    private JButton simpanBtn;
    private JDateChooser tanggalField;
    private JFormattedTextField totalTransaksiField;
    private Penjualan penjualan;
    private CustomerService customerService = new CustomerService();
    private PelunasanPiutangService pelunasanPiutangService = new PelunasanPiutangService();
    private PelunasanPiutangDialogListener pelunasanPiutangDialogListener;
    private PenjualanService penjualanService = new PenjualanService();
    private SyncService syncService = new SyncService();
    private Helper helper = Helper.getInstance();
    private int row;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private ShiftKasir shiftKasir;

    public PelunasanPiutangCustomer(Frame parent, boolean modal, Penjualan penjualan, int row) {
        super(parent, modal);
        this.initComponents();
        this.penjualan = penjualan;
        this.row = row;
        this.tanggalField.setDate(new Date());
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.caraPembayaranCmb.requestFocus();
        this.shiftKasir = new ShiftKasirService().getOpenedShiftKasir(this.helper.getKasir());
        this.loadData();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.tanggalField = new JDateChooser();
        this.caraPembayaranCmb = new JComboBox();
        this.idCustomerLbl = new JLabel();
        this.namaCustomerLbl = new JLabel();
        this.idPenjualanLbl = new JLabel();
        this.jLabel9 = new JLabel();
        this.totalTransaksiField = new JFormattedTextField();
        this.batalBtn = new JButton();
        this.simpanBtn = new JButton();
        this.jLabel6 = new JLabel();
        this.jumlahPelunasanField = new JFormattedTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("ID Customer");
        this.jLabel2.setText("Nama Customer");
        this.jLabel3.setText("ID Penjualan");
        this.jLabel4.setText("Tanggal Pelunasan");
        this.jLabel5.setText("Cara pembayaran");
        this.caraPembayaranCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"CASH", "TRANSFER", "DEBIT", "QRIS"}));
        this.idCustomerLbl.setText("-");
        this.namaCustomerLbl.setText("-");
        this.idPenjualanLbl.setText("-");
        this.jLabel9.setText("Total Transaksi");
        this.totalTransaksiField.setEditable(false);
        this.batalBtn.setText("Batal");
        this.batalBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PelunasanPiutangCustomer.this.batalBtnActionPerformed(evt);
            }
        });
        this.simpanBtn.setText("Simpan");
        this.simpanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PelunasanPiutangCustomer.this.simpanBtnActionPerformed(evt);
            }
        });
        this.jLabel6.setText("Jumlah Pelunasan");
        this.jumlahPelunasanField.setEditable(false);
        this.jumlahPelunasanField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PelunasanPiutangCustomer.this.jumlahPelunasanFieldKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.simpanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.batalBtn)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addGap(18, 18, 18).addComponent((Component)this.tanggalField, -2, 151, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel9).addComponent(this.jLabel6)).addGap(27, 27, 27).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.totalTransaksiField).addComponent(this.caraPembayaranCmb, 0, 151, Short.MAX_VALUE).addComponent(this.idCustomerLbl, -1, -1, Short.MAX_VALUE).addComponent(this.namaCustomerLbl, -1, -1, Short.MAX_VALUE).addComponent(this.idPenjualanLbl, -1, -1, Short.MAX_VALUE).addComponent(this.jumlahPelunasanField)))).addGap(0, 73, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.idCustomerLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.namaCustomerLbl, -2, 20, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.idPenjualanLbl)).addGap(19, 19, 19).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9).addComponent(this.totalTransaksiField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.jumlahPelunasanField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.caraPembayaranCmb, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel4).addComponent((Component)this.tanggalField, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.batalBtn).addComponent(this.simpanBtn)).addContainerGap()));
        this.pack();
    }

    private void simpanBtnActionPerformed(ActionEvent evt) {
        if (!this.totalTransaksiField.getValue().equals(this.jumlahPelunasanField.getValue())) {
            if (this.caraPembayaranCmb.getSelectedItem().equals("CASH")) {
                JOptionPane.showMessageDialog(null, "Cara Pembayaran Harus Transfer/Debit");
            } else {
                this.saveClick();
            }
        } else {
            this.saveClick();
        }
    }

    private void saveClick() {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = null;
        Date pelunasanDate = null;
        try {
            today = sdformat.parse(sdformat.format(new Date()));
        }
        catch (ParseException ex) {
            Logger.getLogger(PelunasanPiutangCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pelunasanDate = sdformat.parse(sdformat.format(this.tanggalField.getDate()));
        }
        catch (ParseException ex) {
            Logger.getLogger(PelunasanPiutangCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (today.compareTo(pelunasanDate) == 0) {
            Penjualan penjualanCheck = this.penjualanService.getPenjualan(this.penjualan.getId());
            boolean canGo = true;
            if (penjualanCheck.getStatusPelunasan().equals("SUDAH LUNAS")) {
                canGo = false;
            }
            if (canGo) {
                this.penjualan.setStatusPelunasan("SUDAH LUNAS");
                int result = this.penjualanService.updatePenjualan(this.penjualan);
                if (result == 1) {
                    PelunasanPiutang pelunasanPiutang = new PelunasanPiutang();
                    pelunasanPiutang.setKaryawanId(this.helper.getKaryawan().getId());
                    pelunasanPiutang.setPenjualanId(this.penjualan.getId());
                    pelunasanPiutang.setTanggalPelunasan(this.tanggalField.getDate());
                    pelunasanPiutang.setKeterangan(this.caraPembayaranCmb.getSelectedItem().toString());
                    pelunasanPiutang.setJumlahPelunasan(Long.parseLong(this.jumlahPelunasanField.getValue().toString()));
                    pelunasanPiutang.setShiftKasirId(this.shiftKasir.getId());
                    int result2 = this.pelunasanPiutangService.createPelunasanPiutang(pelunasanPiutang);
                    if (result2 == 1) {
                        this.pelunasanPiutangDialogListener.lunasSaveClick(this.row);
                        JOptionPane.showMessageDialog(null, "Status Penjualan Berhasil Diubah");
                        this.syncService.doSyncPelunasanPiutangUnsynced();
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Pelunasan Piutang Gagal");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Penjualan SUDAH LUNAS");
            }
        } else {
            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
            passwordInputDialog2.addPasswordInputDialogListener(new passwordInputPelunasan());
            int w = passwordInputDialog2.getWidth();
            int h = passwordInputDialog2.getHeight();
            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            passwordInputDialog2.setJudulText("Input Password Untuk Melakukan Pelunasan");
            passwordInputDialog2.setVisible(true);
        }
    }

    private void batalClick() {
        this.dispose();
    }

    private void batalBtnActionPerformed(ActionEvent evt) {
        this.batalClick();
    }

    private void jumlahPelunasanFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.simpanBtn.requestFocus();
        }
    }

    private void loadData() {
        this.idCustomerLbl.setText(this.penjualan.getCustomerId());
        Customer customer = this.customerService.getCustomerById(this.idCustomerLbl.getText());
        this.namaCustomerLbl.setText(customer.getNama());
        this.idPenjualanLbl.setText(this.penjualan.getId());
        this.totalTransaksiField.setValue(this.penjualan.getTotalPenjualan());
        this.jumlahPelunasanField.setValue(this.penjualan.getTotalPenjualan());
    }

    public void addListener(PelunasanPiutangDialogListener dialogListener) {
        this.pelunasanPiutangDialogListener = dialogListener;
    }

    private class passwordInputPelunasan
    implements PasswordInputListener {
        private passwordInputPelunasan() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) {
                Penjualan penjualanCheck = PelunasanPiutangCustomer.this.penjualanService.getPenjualan(PelunasanPiutangCustomer.this.penjualan.getId());
                boolean canGo = true;
                if (penjualanCheck.getStatusPelunasan().equals("SUDAH LUNAS")) {
                    canGo = false;
                }
                if (canGo) {
                    PelunasanPiutangCustomer.this.penjualan.setStatusPelunasan("SUDAH LUNAS");
                    int result = PelunasanPiutangCustomer.this.penjualanService.updatePenjualan(PelunasanPiutangCustomer.this.penjualan);
                    if (result == 1) {
                        PelunasanPiutang pelunasanPiutang = new PelunasanPiutang();
                        pelunasanPiutang.setKaryawanId(PelunasanPiutangCustomer.this.shiftKasir.getOpenedBy());
                        pelunasanPiutang.setPenjualanId(PelunasanPiutangCustomer.this.penjualan.getId());
                        pelunasanPiutang.setTanggalPelunasan(PelunasanPiutangCustomer.this.tanggalField.getDate());
                        pelunasanPiutang.setKeterangan(PelunasanPiutangCustomer.this.caraPembayaranCmb.getSelectedItem().toString());
                        pelunasanPiutang.setJumlahPelunasan(Long.parseLong(PelunasanPiutangCustomer.this.jumlahPelunasanField.getValue().toString()));
                        int result2 = PelunasanPiutangCustomer.this.pelunasanPiutangService.createPelunasanPiutang(pelunasanPiutang);
                        if (result2 == 1) {
                            PelunasanPiutangCustomer.this.pelunasanPiutangDialogListener.lunasSaveClick(PelunasanPiutangCustomer.this.row);
                            JOptionPane.showMessageDialog(null, "Status Penjualan Berhasil Diubah");
                            PelunasanPiutangCustomer.this.simpanBtn.setEnabled(false);
                            PelunasanPiutangCustomer.this.syncService.doSyncPelunasanPiutangUnsynced();
                            PelunasanPiutangCustomer.this.batalClick();
                        } else {
                            JOptionPane.showMessageDialog(null, "Pelunasan Piutang Gagal");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Penjualan SUDAH LUNAS");
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR")) {
                // empty if block
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

