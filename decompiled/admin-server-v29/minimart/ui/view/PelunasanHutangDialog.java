/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import minimart.engine.controller.Helper;
import minimart.engine.model.PelunasanHutang;
import minimart.engine.model.Pembelian;
import minimart.engine.service.PelunasanHutangService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.SupplierService;
import minimart.engine.service.SyncService;
import minimart.ui.listener.PelunasanHutangDialogListener;

public class PelunasanHutangDialog
extends JDialog {
    private ButtonGroup buttonGroup1;
    private JLabel idField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JFormattedTextField jumlahField;
    private JButton keluarBtn;
    private JComboBox keteranganCmb;
    private JButton simpanBtn;
    private JLabel supplierField;
    private JDateChooser tanggalField;
    private JTextField titipField;
    private JLabel titipanLbl;
    private PembelianService pembelianService = new PembelianService();
    private SupplierService supplierService = new SupplierService();
    private PelunasanHutangService pelunasanHutangService = new PelunasanHutangService();
    private SyncService syncService = new SyncService();
    private Pembelian pembelian;
    private Helper helper = Helper.getInstance();
    private int row;
    private PelunasanHutangDialogListener pelunasanDialogListener;

    public PelunasanHutangDialog(Frame parent, boolean modal, String idPembelian, int row) {
        super(parent, modal);
        this.initComponents();
        this.row = row;
        this.tanggalField.setDate(new Date());
        this.idField.setText(idPembelian);
        this.pembelian = this.pembelianService.getPembelian(idPembelian);
        this.idField.setText(this.pembelian.getId());
        this.supplierField.setText(this.supplierService.getSupplier(this.pembelian.getSupplierId()).getNama());
        this.jumlahField.setValue(this.pembelian.getTotal());
        this.titipField.setText(this.pembelian.getTotal() - this.pembelian.getTitipan() + "");
        this.titipanLbl.setText(this.pembelian.getTitipan() + "");
        this.jumlahField.setEditable(false);
        this.keteranganCmb.requestFocus();
        if (this.pembelian.getPelunasan().equals("LUNAS")) {
            this.simpanBtn.setEnabled(false);
        }
    }

    private void initComponents() {
        this.buttonGroup1 = new ButtonGroup();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.tanggalField = new JDateChooser();
        this.simpanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.keteranganCmb = new JComboBox();
        this.jLabel4 = new JLabel();
        this.supplierField = new JLabel();
        this.jLabel6 = new JLabel();
        this.jumlahField = new JFormattedTextField();
        this.idField = new JLabel();
        this.jLabel5 = new JLabel();
        this.titipanLbl = new JLabel();
        this.titipField = new JTextField();
        this.jLabel7 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("ID Pembelian");
        this.jLabel2.setText("Tanggal Pelunasan");
        this.jLabel3.setText("Keterangan");
        this.simpanBtn.setText("Simpan");
        this.simpanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PelunasanHutangDialog.this.simpanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PelunasanHutangDialog.this.keluarBtnActionPerformed(evt);
            }
        });
        this.keteranganCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"CASH", "TRANSFER"}));
        this.jLabel4.setText("Supplier");
        this.supplierField.setText("-");
        this.jLabel6.setText("Jumlah");
        this.idField.setText("-");
        this.jLabel5.setText("Titipan");
        this.titipanLbl.setText("-");
        this.jLabel7.setText("Bayar");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.keteranganCmb, GroupLayout.Alignment.TRAILING, -2, 151, -2).addComponent((Component)this.tanggalField, GroupLayout.Alignment.TRAILING, -2, 151, -2))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel4).addComponent(this.jLabel6).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.titipanLbl, GroupLayout.Alignment.TRAILING, -2, 151, -2).addComponent(this.jumlahField, GroupLayout.Alignment.TRAILING, -2, 151, -2).addComponent(this.supplierField, GroupLayout.Alignment.TRAILING, -2, 151, -2).addComponent(this.idField, GroupLayout.Alignment.TRAILING, -2, 151, -2))).addGroup(layout.createSequentialGroup().addComponent(this.simpanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jLabel7).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.titipField, -2, 152, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.idField)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.supplierField)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.jumlahField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.titipanLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent((Component)this.tanggalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.keteranganCmb, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.titipField, -2, -1, -2).addComponent(this.jLabel7)).addGap(37, 37, 37).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.simpanBtn).addComponent(this.keluarBtn)).addContainerGap()));
        this.pack();
    }

    private void simpanBtnActionPerformed(ActionEvent evt) {
        if (!this.helper.isInteger(this.titipField.getText())) {
            this.titipField.setBackground(Color.red);
        } else if (Integer.parseInt(this.titipField.getText() + "") > Integer.parseInt(this.jumlahField.getValue() + "")) {
            this.titipField.setBackground(Color.red);
        } else {
            PelunasanHutang ph = new PelunasanHutang();
            ph.setKaryawanId(this.helper.getKaryawan().getId());
            ph.setPembelianId(this.pembelian.getId());
            ph.setTanggalPelunasan(this.tanggalField.getDate());
            ph.setJumlah(Integer.parseInt(this.titipField.getText()));
            ph.setKeterangan(this.keteranganCmb.getSelectedItem() + "");
            int result = this.pelunasanHutangService.createPelunasanHutang(ph);
            if (result == 1) {
                if ((long)Integer.parseInt(this.titipField.getText() + "") == this.pembelian.getTotal() - this.pembelian.getTitipan()) {
                    this.pembelian.setPelunasan("LUNAS");
                    this.pembelian.setTitipan(this.pembelian.getTitipan() + Long.parseLong(this.titipField.getText() + ""));
                } else if ((long)Integer.parseInt(this.titipField.getText() + "") < this.pembelian.getTotal() - this.pembelian.getTitipan()) {
                    this.pembelian.setTitipan(this.pembelian.getTitipan() + Long.parseLong(this.titipField.getText() + ""));
                }
                int result2 = this.pembelianService.updatePembelian(this.pembelian);
                if (result2 == 1) {
                    JOptionPane.showMessageDialog(null, "Data Berhasil Tersimpan");
                    if (this.pembelian.getPelunasan().equals("LUNAS")) {
                        this.pelunasanDialogListener.lunasSaveClick(this.row);
                    } else {
                        this.pelunasanDialogListener.sebagianSaveClick(Long.parseLong(this.pembelian.getTitipan() + ""), this.row);
                    }
                    PrinterJob job = PrinterJob.getPrinterJob();
                    PageFormat pf = job.defaultPage();
                    Paper p = pf.getPaper();
                    p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                    p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                    pf.setPaper(p);
                    Book book = new Book();
                    book.append(new pelunasanPrint(), pf);
                    job.setPageable(book);
                    boolean ok = job.printDialog();
                    if (ok) {
                        try {
                            job.print();
                        }
                        catch (PrinterException printerException) {
                            // empty catch block
                        }
                    }
                    this.syncService.doSyncPelunasanHutangUnsynced();
                    this.dispose();
                }
            }
        }
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    public void addListener(PelunasanHutangDialogListener dialogListener) {
        this.pelunasanDialogListener = dialogListener;
    }

    class pelunasanPrint
    implements Printable {
        pelunasanPrint() {
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
            g.drawString("Pelunasan Hutang:" + PelunasanHutangDialog.this.supplierService.getSupplier(PelunasanHutangDialog.this.pembelian.getSupplierId()).getNama(), x + 0, y + yK);
            g.drawString("Oleh:" + PelunasanHutangDialog.this.helper.getKaryawan().getNama(), x + 0, y + (yK += yP));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(PelunasanHutangDialog.this.pembelian.getTanggal());
            g.drawString("Tanggal:" + tanggal, x + 0, y + (yK += yP));
            g.drawString("ID Pembelian:" + PelunasanHutangDialog.this.pembelian.getId(), x + 0, y + (yK += yP));
            g.drawString("Total Pembelian:" + PelunasanHutangDialog.this.pembelian.getTotal(), x + 0, y + (yK += yP));
            g.drawString("Total Dibayarkan:" + PelunasanHutangDialog.this.pembelian.getTitipan(), x + 0, y + (yK += yP));
            g.drawString("Bayar:" + PelunasanHutangDialog.this.titipField.getText(), x + 0, y + (yK += yP));
            g.drawString("Keterangan:" + PelunasanHutangDialog.this.keteranganCmb.getSelectedItem(), x + 0, y + (yK += yP));
            g.drawString("Status:" + PelunasanHutangDialog.this.pembelian.getPelunasan(), x + 0, y + (yK += yP));
            g.drawString("Tanggal Pelunasan: " + format.format(PelunasanHutangDialog.this.tanggalField.getDate()), x + 0, y + (yK += yP));
            return 0;
        }
    }
}

