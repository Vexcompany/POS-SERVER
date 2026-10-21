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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
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
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.model.PelunasanHutang;
import minimart.engine.model.Pembelian;
import minimart.engine.model.Penjualan;
import minimart.engine.model.Supplier;
import minimart.engine.model.Voucher;
import minimart.engine.service.CustomerService;
import minimart.engine.service.PelunasanHutangService;
import minimart.engine.service.PelunasanPiutangService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.PengembalianBarangService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.SupplierService;
import minimart.engine.service.VoucherService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportKeuanganGlobal
extends JPanel {
    private JButton cetak2;
    private JButton cetakBtn;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JButton keluarButton;
    private JTable reportTable;
    private JDateChooser startDateField;
    private JButton tampilkanButton;
    private JFormattedTextField totalField;
    private String[] reportColumn = new String[]{"Keterangan", "Uang Masuk", "Uang Keluar"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PenjualanService penjualanService = new PenjualanService();
    private DefaultPanelListener defaultPanelListener;
    private Helper helper = Helper.getInstance();
    private PelunasanPiutangService pelunasanPiutangService = new PelunasanPiutangService();
    private CustomerService customerService = new CustomerService();
    private PengembalianBarangService pengembalianBarangService = new PengembalianBarangService();
    private PembelianService pembelianService = new PembelianService();
    private SupplierService supplierService = new SupplierService();
    private PelunasanHutangService pelunasanHutangService = new PelunasanHutangService();
    private Date transactionDate;
    private VoucherService voucherService = new VoucherService();

    public ReportKeuanganGlobal() {
        this.initComponents();
        this.startDateField.setDate(new Date());
        this.cetakBtn.setEnabled(false);
        TableColumn column = this.reportTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(200);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.startDateField = new JDateChooser();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.jLabel3 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.cetakBtn = new JButton();
        this.cetak2 = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Dari Tanggal");
        this.startDateField.addMouseListener((MouseListener)new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportKeuanganGlobal.this.startDateFieldMouseClicked(evt);
            }
        });
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportKeuanganGlobal.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportKeuanganGlobal.this.keluarButtonActionPerformed(evt);
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
        this.cetakBtn.setText("Cetak");
        this.cetakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportKeuanganGlobal.this.cetakBtnActionPerformed(evt);
            }
        });
        this.cetak2.setText("Cetak2");
        this.cetak2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportKeuanganGlobal.this.cetak2ActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(39, 39, 39).addComponent((Component)this.startDateField, -2, 150, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 239, Short.MAX_VALUE).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, 235, -2)).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetakBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetak2).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.jLabel1).addComponent(this.totalField, -2, -1, -2).addComponent((Component)this.startDateField, -2, -1, -2)).addGap(35, 35, 35).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanButton).addComponent(this.keluarButton).addComponent(this.cetakBtn).addComponent(this.cetak2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 431, -2).addContainerGap(65, Short.MAX_VALUE)));
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.clearTable();
        this.cetakBtn.setEnabled(true);
        this.transactionDate = this.startDateField.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDateField.getDate());
        long jumlahUang = 0L;
        List<Object[]> dataCases = this.penjualanService.getRekapPenjualan("TOKO", startDateString, startDateString);
        Object[] dataPenjualan = new Object[3];
        dataPenjualan[0] = "Total Penjualan";
        if (!dataCases.isEmpty()) {
            dataPenjualan[1] = this.helper.formatNumber(dataCases.get(0)[1] + "");
            jumlahUang += ((Long)dataCases.get(0)[1]).longValue();
        } else {
            dataPenjualan[1] = 0;
        }
        this.reportTableModel.addRow(dataPenjualan);
        List<Object[]> pelunasans = this.pelunasanPiutangService.getPelunasanPiutang(startDateString, startDateString, "");
        for (Object[] objectArray : pelunasans) {
            Object[] objectArray2 = new Object[3];
            objectArray2[0] = "Pelunasan cus: " + objectArray[2] + "-" + objectArray[4];
            objectArray2[1] = this.helper.formatNumber(objectArray[6] + "");
            jumlahUang += Long.parseLong(objectArray[6] + "");
            this.reportTableModel.addRow(objectArray2);
        }
        List<Penjualan> dataKredits = this.penjualanService.getPenjualansKredit(startDateString, startDateString, "");
        for (Penjualan penjualan : dataKredits) {
            Object[] objectArray = new Object[3];
            Customer customer = this.customerService.getCustomerById(penjualan.getCustomerId());
            objectArray[0] = "Kredit - " + customer.getNama();
            objectArray[2] = this.helper.formatNumber(penjualan.getTotalPenjualan() + "");
            this.reportTableModel.addRow(objectArray);
            jumlahUang -= penjualan.getTotalPenjualan();
        }
        List<Pembelian> list = this.pembelianService.getPembelian(startDateString, startDateString, "CASH", "SUDAH DIPROSES");
        for (Pembelian pembelian : list) {
            Object[] objectArray = new Object[3];
            Supplier supplier = this.supplierService.getSupplier(pembelian.getSupplierId());
            objectArray[0] = "Pembelian Cash: " + pembelian.getId() + " (" + supplier.getNama() + " )";
            objectArray[2] = this.helper.formatNumber(pembelian.getTotal() + "");
            jumlahUang -= pembelian.getTotal();
            this.reportTableModel.addRow(objectArray);
        }
        List<PelunasanHutang> list2 = this.pelunasanHutangService.getPelunasanHutang("", startDateString, startDateString, "CASH");
        for (PelunasanHutang pelunasanHutang : list2) {
            Object[] dataPelunasan = new Object[3];
            Pembelian pembelian = this.pembelianService.getPembelian(pelunasanHutang.getPembelianId());
            dataPelunasan[0] = "Pelunasan Sup: " + pelunasanHutang.getPembelianId() + "-" + this.supplierService.getSupplier(pembelian.getSupplierId()).getNama();
            dataPelunasan[2] = this.helper.formatNumber(pelunasanHutang.getJumlah() + "");
            jumlahUang -= pelunasanHutang.getJumlah();
            this.reportTableModel.addRow(dataPelunasan);
        }
        List<Voucher> list3 = this.voucherService.getVoucher(startDateString, startDateString);
        for (Voucher voucher : list3) {
            Object[] dataVoucher = new Object[3];
            dataVoucher[0] = "Penukaran Voucher: " + voucher.getId() + " (" + voucher.getCustomerId() + " )";
            dataVoucher[2] = this.helper.formatNumber(voucher.getJumlah() + "");
            this.reportTableModel.addRow(dataVoucher);
            jumlahUang -= (long)voucher.getJumlah();
        }
        Object[] objectArray = new Object[3];
        objectArray[0] = "TOTAL : " + this.helper.formatNumber(jumlahUang + "");
        this.reportTableModel.addRow(objectArray);
        Object[] tanggalCetak = new Object[3];
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String tanggal = format.format(new Date());
        tanggalCetak[0] = "Tanggal Cetak : " + tanggal;
        this.reportTableModel.addRow(tanggalCetak);
        this.totalField.setValue(jumlahUang);
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void cetakBtnActionPerformed(ActionEvent evt) {
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
    }

    private void startDateFieldMouseClicked(MouseEvent evt) {
        this.cetakBtn.setEnabled(false);
    }

    private void cetak2ActionPerformed(ActionEvent evt) {
        try {
            this.reportTable.print(JTable.PrintMode.FIT_WIDTH);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
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
            g.drawString("Report Keuangan: " + ReportKeuanganGlobal.this.totalField.getText(), x + 0, y + yK);
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(ReportKeuanganGlobal.this.transactionDate);
            g.drawString("Penjualan Tanggal: " + tanggal, x + 0, y + (yK += yP));
            String nowDate = format.format(new Date());
            g.drawString("Dicetak Tanggal: " + nowDate, x + 0, y + (yK += yP));
            g.drawString("Jam " + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds(), x + 120, y + yK);
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (int a = 0; a < ReportKeuanganGlobal.this.reportTableModel.getRowCount(); ++a) {
                g.drawString(ReportKeuanganGlobal.this.reportTableModel.getValueAt(a, 0).toString(), x + 0, y + yK);
                yK += yP;
                if (ReportKeuanganGlobal.this.reportTableModel.getValueAt(a, 1) != null) {
                    g.drawString("+" + ReportKeuanganGlobal.this.reportTableModel.getValueAt(a, 1).toString(), x + 15, y + yK);
                } else if (ReportKeuanganGlobal.this.reportTableModel.getValueAt(a, 2) != null) {
                    g.drawString("-" + ReportKeuanganGlobal.this.reportTableModel.getValueAt(a, 2).toString(), x + 15, y + yK);
                }
                yK += yP;
            }
            return 0;
        }
    }
}

