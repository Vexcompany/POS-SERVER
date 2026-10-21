/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import minimart.engine.controller.Helper;
import minimart.engine.model.Karyawan;
import minimart.engine.model.ShiftKasir;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.ShiftKasirService;
import minimart.ui.listener.CloseKasirListener;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.PasswordInputDialog;

public class CloseKasir
extends JDialog {
    private JButton batalBtn;
    private JTextField cashField;
    private JLabel cashLbl;
    private JTextField debitField;
    private JLabel debitLbl;
    private JLabel dibukaLbl;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JTextField kreditField;
    private JLabel openingLbl;
    private JButton simpanDataBtn;
    private JLabel statusLbl;
    private JLabel tanggalLbl;
    private JTextField transferField;
    private JLabel transferLbl;
    private ShiftKasir shiftKasir;
    private Helper helper = Helper.getInstance();
    private int screenWidth = 0;
    private int screenHeight = 0;
    private boolean canGoThrough;
    private CloseKasirListener closeKasirListener;
    private ShiftKasirService shiftKasirService = new ShiftKasirService();
    private PenjualanService penjualanService = new PenjualanService();

    public CloseKasir(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.shiftKasir = this.shiftKasirService.getOpenedShiftKasir(this.helper.getKasir());
        this.dibukaLbl.setText(this.shiftKasir.getOpenedBy());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        this.tanggalLbl.setText(dateFormat.format(this.shiftKasir.getOpening()));
        this.openingLbl.setText(timeFormat.format(this.shiftKasir.getOpening()));
        this.statusLbl.setText("Open");
        this.kreditField.setEditable(false);
        this.kreditField.setText(this.penjualanService.getTotalOpenedShift(this.helper.getKasir(), "KREDIT") + "");
        this.cashField.requestFocus();
        this.cashField.selectAll();
    }

    private void initComponents() {
        this.jLabel4 = new JLabel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.batalBtn = new JButton();
        this.simpanDataBtn = new JButton();
        this.dibukaLbl = new JLabel();
        this.tanggalLbl = new JLabel();
        this.openingLbl = new JLabel();
        this.statusLbl = new JLabel();
        this.cashField = new JTextField();
        this.jLabel9 = new JLabel();
        this.cashLbl = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel10 = new JLabel();
        this.debitField = new JTextField();
        this.transferField = new JTextField();
        this.kreditField = new JTextField();
        this.debitLbl = new JLabel();
        this.transferLbl = new JLabel();
        this.jLabel4.setText("jLabel4");
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Dibuka Oleh");
        this.jLabel2.setText("Tanggal");
        this.jLabel3.setText("Opening");
        this.jLabel5.setText("Status");
        this.jLabel6.setFont(new Font("Tahoma", 0, 14));
        this.jLabel6.setText("PENUTUPAN SHIFT KASIR");
        this.batalBtn.setText("Batal");
        this.batalBtn.setPreferredSize(new Dimension(73, 23));
        this.batalBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CloseKasir.this.batalBtnActionPerformed(evt);
            }
        });
        this.batalBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CloseKasir.this.batalBtnKeyPressed(evt);
            }
        });
        this.simpanDataBtn.setText("Simpan Data");
        this.simpanDataBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CloseKasir.this.simpanDataBtnActionPerformed(evt);
            }
        });
        this.dibukaLbl.setText("-");
        this.tanggalLbl.setText("-");
        this.openingLbl.setText("-");
        this.statusLbl.setText("-");
        this.cashField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CloseKasir.this.cashFieldKeyPressed(evt);
            }
        });
        this.jLabel9.setText("Cash");
        this.cashLbl.setText("-");
        this.cashLbl.setToolTipText("");
        this.jLabel7.setText("Debit");
        this.jLabel8.setText("Transfer");
        this.jLabel10.setText("Kredit");
        this.debitLbl.setText("-");
        this.transferLbl.setText("-");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(0, 246, Short.MAX_VALUE).addComponent(this.simpanDataBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.batalBtn, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel6).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3)).addGap(48, 48, 48).addComponent(this.dibukaLbl, -2, 177, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5).addComponent(this.jLabel9).addComponent(this.jLabel7).addComponent(this.jLabel8).addComponent(this.jLabel10)).addGap(63, 63, 63).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.tanggalLbl, -2, 174, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.kreditField, GroupLayout.Alignment.LEADING).addComponent(this.transferField, GroupLayout.Alignment.LEADING).addComponent(this.debitField, GroupLayout.Alignment.LEADING).addComponent(this.cashField, GroupLayout.Alignment.LEADING, -1, 174, Short.MAX_VALUE).addComponent(this.statusLbl, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.openingLbl, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.cashLbl, -1, 103, Short.MAX_VALUE).addComponent(this.debitLbl, -1, -1, Short.MAX_VALUE).addComponent(this.transferLbl, -1, -1, Short.MAX_VALUE)))))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel6).addGap(12, 12, 12).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.dibukaLbl)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(13, 13, 13).addComponent(this.jLabel2)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tanggalLbl))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.openingLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.statusLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9).addComponent(this.cashField, -2, -1, -2).addComponent(this.cashLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.debitField, -2, -1, -2).addComponent(this.debitLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.transferField, -2, -1, -2).addComponent(this.transferLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel10).addComponent(this.kreditField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.batalBtn, -2, -1, -2).addComponent(this.simpanDataBtn)).addContainerGap()));
        this.pack();
    }

    private void batalBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void simpanDataBtnActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void saveClick() {
        this.canGoThrough = false;
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Kasir Akan Ditutup dan Dilanjutkan Dengan hasil keuangan, Lanjutkan?");
        confirmationDialog.addListener(new confirmDialogEventHandler());
        int w = confirmationDialog.getWidth();
        int h = confirmationDialog.getHeight();
        confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        confirmationDialog.setVisible(true);
        if (this.canGoThrough) {
            if (this.helper.isInteger(this.cashField.getText()) && this.helper.isInteger(this.transferField.getText()) && this.helper.isInteger(this.debitField.getText())) {
                this.cashField.setBackground(Color.white);
                this.debitField.setBackground(Color.white);
                this.transferField.setBackground(Color.white);
                this.simpanDataBtn.setEnabled(false);
                try {
                    this.shiftKasir.setTotalUang(this.penjualanService.getTotalOpenedShift(this.helper.getKasir(), "CASH"));
                }
                catch (Exception e) {
                    this.shiftKasir.setTotalUang(0L);
                }
                this.shiftKasir.setJumlahSetoran(Long.parseLong(this.cashField.getText()));
                this.shiftKasir.setTransfer(Long.parseLong(this.transferField.getText()));
                this.shiftKasir.setDebit(Long.parseLong(this.debitField.getText()));
                this.shiftKasir.setQris(0L);
                this.shiftKasir.setVoucher(0L);
                this.shiftKasir.setClosing(new Timestamp(System.currentTimeMillis()));
                this.shiftKasir.setClosedBy(this.helper.getKaryawan().getId());
                int result = this.shiftKasirService.update(this.shiftKasir);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "Shift Telah Ditutup, data berhasil tersimpan");
                    this.penjualanService.updatePenjualanShift(this.shiftKasir.getId());
                    this.closeKasirListener.keluarPenjualan();
                    this.dispose();
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
                    catch (PrinterException printerException) {}
                } else {
                    JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
                }
            } else {
                if (!this.helper.isInteger(this.cashField.getText())) {
                    this.cashField.setBackground(Color.red);
                }
                if (!this.helper.isInteger(this.debitField.getText())) {
                    this.debitField.setBackground(Color.red);
                }
                if (!this.helper.isInteger(this.transferField.getText())) {
                    this.transferField.setBackground(Color.red);
                }
            }
        }
    }

    private void batalBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }

    private void cashFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 155) {
            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
            passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
            int w = passwordInputDialog2.getWidth();
            int h = passwordInputDialog2.getHeight();
            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            passwordInputDialog2.setJudulText("Input Password");
            passwordInputDialog2.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    public void setListener(CloseKasirListener listener) {
        this.closeKasirListener = listener;
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
            g.drawString("ID KASIR - " + CloseKasir.this.helper.getKasir(), x + 0, y + yK);
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            g.drawString("Opened By : " + CloseKasir.this.shiftKasir.getOpenedBy(), x + 0, y + (yK += yP));
            g.drawString("Closed By : " + CloseKasir.this.shiftKasir.getClosedBy(), x + 0, y + (yK += yP));
            g.drawString("Opening : " + CloseKasir.this.shiftKasir.getOpening().toString(), x + 0, y + (yK += yP));
            g.drawString("Closing : " + CloseKasir.this.shiftKasir.getClosing().toString(), x + 0, y + (yK += yP));
            g.drawString("Total Setoran : " + CloseKasir.this.shiftKasir.getJumlahSetoran(), x + 0, y + (yK += yP));
            yK += yP;
            return 0;
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            CloseKasir.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            CloseKasir.this.canGoThrough = false;
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) {
                if (CloseKasir.this.helper.getKaryawan().getRole().equals("DIREKTUR") || CloseKasir.this.helper.getKaryawan().getRole().equals("ACCOUNTING")) {
                    try {
                        CloseKasir.this.cashLbl.setText(CloseKasir.this.penjualanService.getTotalOpenedShift(CloseKasir.this.helper.getKasir(), "CASH") + "");
                    }
                    catch (Exception e) {
                        CloseKasir.this.cashLbl.setText("0");
                    }
                    try {
                        CloseKasir.this.debitLbl.setText(CloseKasir.this.penjualanService.getTotalOpenedShift(CloseKasir.this.helper.getKasir(), "DEBIT") + "");
                    }
                    catch (Exception e) {
                        CloseKasir.this.debitLbl.setText("0");
                    }
                    try {
                        CloseKasir.this.transferLbl.setText(CloseKasir.this.penjualanService.getTotalOpenedShift(CloseKasir.this.helper.getKasir(), "TRANSFER") + "");
                    }
                    catch (Exception e) {
                        CloseKasir.this.transferLbl.setText("0");
                    }
                } else {
                    CloseKasir.this.cashLbl.setText("");
                    CloseKasir.this.debitLbl.setText("");
                    CloseKasir.this.transferLbl.setText("");
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

