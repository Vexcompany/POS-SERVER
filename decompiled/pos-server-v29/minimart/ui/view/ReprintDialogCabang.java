/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.view.JasperViewer
 */
package minimart.ui.view;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Customer;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Penjualan;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.model.Sales;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanParsialService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.SalesService;
import minimart.ui.listener.PasswordInputListener;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ReprintDialogCabang
extends JDialog {
    private JTextField idPenjualanField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JTable reprintTable;
    private String[] reprintColumn = new String[]{"ID", "Customer", "Tanggal", "Keterangan"};
    private DefaultTableModel reprintTableModel = new DefaultTableModel(new String[0][1], this.reprintColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private int screenHeight;
    private int screenWidth;
    private Sales sales;
    private PenjualanService penjualanService = new PenjualanService();
    private PenjualanParsialService penjualanParsialService = new PenjualanParsialService();
    private KonstantaService konstantaService = new KonstantaService();
    private CustomerService customerService = new CustomerService();
    private Helper helper = Helper.getInstance();
    private Penjualan penjualan;
    private BarangService barangService = new BarangService();
    private List<PenjualanDetail> penjualanDetails;
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private long totalRokok = 0L;
    private float totalPoint = 0.0f;
    private SalesService salesService = new SalesService();

    public ReprintDialogCabang(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.reprintTable.requestFocus();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.loadDataPenjualan();
    }

    private void loadDataPenjualan() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(new Date());
        List<Penjualan> penjualans = this.penjualanService.getPenjualanTerakhir(date);
        System.out.println(penjualans.size());
        for (Penjualan tempPenjualan : penjualans) {
            Object[] object = new Object[]{tempPenjualan.getId(), this.customerService.getCustomerById(tempPenjualan.getCustomerId()).getNama(), tempPenjualan.getCreated().getHours() + ":" + tempPenjualan.getCreated().getMinutes() + ":" + tempPenjualan.getCreated().getSeconds(), tempPenjualan.getKeterangan()};
            this.reprintTableModel.addRow(object);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reprintTable = new JTable();
        this.idPenjualanField = new JTextField();
        this.jLabel2 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Re-Print");
        this.reprintTable.setModel(this.reprintTableModel);
        this.reprintTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReprintDialogCabang.this.reprintTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reprintTable);
        this.idPenjualanField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReprintDialogCabang.this.idPenjualanFieldKeyPressed(evt);
            }
        });
        this.jLabel2.setText("ID PENJUALAN");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 512, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.idPenjualanField, -2, 149, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(16, 16, 16).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.idPenjualanField, -2, -1, -2).addComponent(this.jLabel2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 275, -2).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void reprintTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.reprintTable.getSelectedRow() != -1) {
                String id = this.reprintTable.getValueAt(this.reprintTable.getSelectedRow(), 0).toString();
                try {
                    File rear = new File("ireport/PenjualanCabang.jasper");
                    String path = rear.getAbsolutePath();
                    HashMap<String, String> parameter = new HashMap<String, String>();
                    System.out.println(id);
                    parameter.put("t_penjualan_id", id);
                    JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "andrbrroot"));
                    JasperViewer.viewReport((JasperPrint)print, (boolean)false);
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
                }
                this.dispose();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void idPenjualanFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.penjualan = this.penjualanService.getPenjualan(this.idPenjualanField.getText());
            this.sales = this.salesService.getSales(this.penjualan.getSalesId());
            if (this.penjualan != null) {
                try {
                    File rear = new File("ireport/PenjualanCabang.jasper");
                    String path = rear.getAbsolutePath();
                    HashMap<String, String> parameter = new HashMap<String, String>();
                    parameter.put("t_penjualan_id", this.penjualan.getId());
                    JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
                    JasperViewer.viewReport((JasperPrint)print, (boolean)false);
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
                }
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private int rataKanan(String price, int x) {
        int len = price.length();
        for (int i = 0; i < len; ++i) {
            x -= 4;
        }
        return x;
    }

    class printer
    implements Printable {
        printer() {
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
            String aktifkanPoint;
            if (page > 0) {
                return 1;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            int y = 0;
            int x = 5;
            int yK = 18;
            int yP = 11;
            int xRataKanan = 160;
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("[COPY]", x + 0, y + yK);
            yK += yP;
            if (ReprintDialogCabang.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(ReprintDialogCabang.this.konstantaService.getConstantValue("namaToko"), x, y + yK);
            } else if (ReprintDialogCabang.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(ReprintDialogCabang.this.konstantaService.getConstantValue("namaGudang"), x, y + yK);
            }
            yK += yP;
            if (ReprintDialogCabang.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(ReprintDialogCabang.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (ReprintDialogCabang.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(ReprintDialogCabang.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            String telp1 = "";
            if (ReprintDialogCabang.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = ReprintDialogCabang.this.konstantaService.getConstantValue("telpToko1");
            } else if (ReprintDialogCabang.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = ReprintDialogCabang.this.konstantaService.getConstantValue("telpGudang1");
            }
            if (!telp1.equals("")) {
                g.drawString("TELP: " + telp1, x, y + (yK += yP));
            }
            String telp2 = "";
            if (ReprintDialogCabang.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = ReprintDialogCabang.this.konstantaService.getConstantValue("telpToko2");
            } else if (ReprintDialogCabang.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = ReprintDialogCabang.this.konstantaService.getConstantValue("telpGudang2");
            }
            if (!telp2.equals("")) {
                g.drawString("TELP: " + telp2, x, y + (yK += yP));
            }
            Customer customer = ReprintDialogCabang.this.customerService.getCustomerById(ReprintDialogCabang.this.penjualan.getCustomerId());
            g.drawString("Cust: " + customer.getId() + " - " + customer.getNama(), x + 0, y + (yK += yP));
            if (ReprintDialogCabang.this.penjualan.getSalesId() != null) {
                g.drawString("Sales: " + ReprintDialogCabang.this.sales.getId() + " - " + ReprintDialogCabang.this.sales.getNama(), x + 0, y + (yK += yP));
            }
            if (ReprintDialogCabang.this.penjualan.getKeterangan().equals("KREDIT")) {
                g.drawString("Jatuh Tempo: " + ReprintDialogCabang.this.penjualan.getJatuhTempo(), x + 0, y + (yK += yP));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Bon. " + ReprintDialogCabang.this.penjualan.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + ReprintDialogCabang.this.penjualan.getKaryawanId(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            float totalDiscount = 0.0f;
            long totalBelanja = 0L;
            ReprintDialogCabang.this.totalRokok = 0L;
            ReprintDialogCabang.this.totalPoint = 0.0f;
            for (PenjualanDetail penjualanDetail : ReprintDialogCabang.this.penjualanDetails) {
                yK += yP;
                Barang barang = ReprintDialogCabang.this.barangService.getBarang(penjualanDetail.getBarangId());
                if (barang.getJenis().equals("ROKOK")) {
                    ReprintDialogCabang.this.totalRokok = ReprintDialogCabang.this.totalRokok + penjualanDetail.getTotalHarga();
                }
                if (penjualanDetail.getSatuan().equals(barang.getSatuan1()) && barang.getMinPembelian() > 0.0f && penjualanDetail.getJumlahBarang() >= barang.getMinPembelian()) {
                    barang.setNama(barang.getNama() + " (GROSIR)");
                }
                g.drawString(barang.getNama(), x + 0, y + yK);
                yK = yK + yP - 1;
                float tempValue = penjualanDetail.getJumlahBarang();
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)penjualanDetail.getJumlahBarang()) : (Number)Float.valueOf(penjualanDetail.getJumlahBarang());
                g.drawString(value + penjualanDetail.getSatuan(), x + 20, y + yK);
                g.drawString("x", x + 55, y + yK);
                g.drawString(ReprintDialogCabang.this.helper.formatNumber(penjualanDetail.getHargaJual() + ""), x + 70, y + yK);
                g.drawString("=", x + 120, y + yK);
                int xtemp = ReprintDialogCabang.this.rataKanan(ReprintDialogCabang.this.helper.formatNumber((long)(penjualanDetail.getJumlahBarang() * (float)penjualanDetail.getHargaJual()) + ""), xRataKanan);
                g.drawString(ReprintDialogCabang.this.helper.formatNumber((long)((float)penjualanDetail.getHargaJual() * penjualanDetail.getJumlahBarang()) + ""), x + xtemp, y + yK);
                if (penjualanDetail.getDiscount2() > 0L) {
                    g.drawString("*Disc ( -" + penjualanDetail.getDiscount2() + ")", x + 0, y + (yK += yP));
                }
                totalDiscount += (float)penjualanDetail.getDiscount2();
                totalBelanja = (long)((float)totalBelanja + (float)penjualanDetail.getHargaJual() * penjualanDetail.getJumlahBarang());
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total item: " + ReprintDialogCabang.this.penjualanDetails.size(), x + 0, y + (yK += yP));
            int xtemp = ReprintDialogCabang.this.rataKanan(ReprintDialogCabang.this.helper.formatNumber(totalBelanja + ""), xRataKanan);
            g.drawString(ReprintDialogCabang.this.helper.formatNumber(totalBelanja + ""), x + xtemp, y + yK);
            yK += yP;
            if (totalDiscount > 0.0f) {
                g.drawString("Hemat ", x + 0, y + yK);
                xtemp = ReprintDialogCabang.this.rataKanan(ReprintDialogCabang.this.helper.formatNumber("-" + (long)totalDiscount + ""), xRataKanan);
                g.drawString(ReprintDialogCabang.this.helper.formatNumber("-" + (long)totalDiscount + "") + "", x + xtemp, y + yK);
                xtemp = ReprintDialogCabang.this.rataKanan(ReprintDialogCabang.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
                g.drawString(ReprintDialogCabang.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + (yK += yP));
                yK += yP;
            }
            if ((aktifkanPoint = ReprintDialogCabang.this.konstantaService.getConstantValue("aktifkanPoint")).equals("YES") && !customer.getId().equalsIgnoreCase("C") && !customer.getId().equalsIgnoreCase("SINAR") && !customer.getId().equalsIgnoreCase("LUKAS")) {
                long total = ReprintDialogCabang.this.penjualan.getTotalPenjualan();
                float rokokValue = Float.parseFloat(ReprintDialogCabang.this.konstantaService.getConstantValue("pointRokok"));
                float lainValue = Float.parseFloat(ReprintDialogCabang.this.konstantaService.getConstantValue("pointLain"));
                float pointRokok = (float)ReprintDialogCabang.this.totalRokok / rokokValue;
                float pointLain = (float)(total - ReprintDialogCabang.this.totalRokok) / lainValue;
                ReprintDialogCabang.this.totalPoint = pointLain + pointRokok;
                if (customer.getHutang() > 0L) {
                    g.drawString("Sisa Hutang", x + 0, y + yK);
                    xtemp = ReprintDialogCabang.this.rataKanan(ReprintDialogCabang.this.helper.formatNumber(customer.getHutang() + ""), xRataKanan);
                    g.drawString(ReprintDialogCabang.this.helper.formatNumber(customer.getHutang() + ""), x + xtemp, y + yK);
                    yK += yP;
                }
                g.drawString("Point Belanja", x + 0, y + yK);
                g.drawString("(+" + (long)ReprintDialogCabang.this.totalPoint + ")", x + 120, y + yK);
                xtemp = ReprintDialogCabang.this.rataKanan(ReprintDialogCabang.this.helper.formatNumber(customer.getPoint() + ""), xRataKanan);
                g.drawString(ReprintDialogCabang.this.helper.formatNumber(customer.getPoint() + ""), x + xtemp, y + yK);
                yK += yP;
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + yK);
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(ReprintDialogCabang.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + ReprintDialogCabang.this.penjualan.getCreated().getHours() + ":" + ReprintDialogCabang.this.penjualan.getCreated().getMinutes() + ":" + ReprintDialogCabang.this.penjualan.getCreated().getSeconds(), x + 120, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("TERIMA KASIH", x + 57, y + (yK += yP));
            g.drawString("Barang yang sudah dibeli", x + 40, y + (yK += yP));
            g.drawString("tidak dapat ditukar/dikembalikan", x + 25, y + (yK += yP));
            return 0;
        }
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
            g.drawString("[COPY]", x + 0, y + yK);
            g.drawString("Permintaan Barang - " + ReprintDialogCabang.this.penjualan.getId(), x + 0, y + (yK += yP));
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (PenjualanDetail penjualanDetail : ReprintDialogCabang.this.penjualanDetails) {
                Barang barang = ReprintDialogCabang.this.barangService.getBarang(penjualanDetail.getBarangId());
                g.drawString(barang.getNama() + " (" + barang.getRakIdToko() + ")", x + 0, y + yK);
                float tempValue = penjualanDetail.getJumlahBarang();
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)penjualanDetail.getJumlahBarang()) : (Number)Float.valueOf(penjualanDetail.getJumlahBarang());
                g.drawString(value + penjualanDetail.getSatuan() + " / " + barang.getStokToko() + barang.getSatuan1(), x + 20, y + (yK += yP));
                yK += yP;
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(ReprintDialogCabang.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + yK);
            g.drawString("Jam " + ReprintDialogCabang.this.penjualan.getCreated().getHours() + ":" + ReprintDialogCabang.this.penjualan.getCreated().getMinutes() + ":" + ReprintDialogCabang.this.penjualan.getCreated().getSeconds(), x + 120, y + yK);
            return 0;
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                ReprintDialogCabang.this.penjualan = ReprintDialogCabang.this.penjualanService.getPenjualan(ReprintDialogCabang.this.reprintTableModel.getValueAt(ReprintDialogCabang.this.reprintTable.getSelectedRow(), 0).toString());
                ReprintDialogCabang.this.sales = ReprintDialogCabang.this.salesService.getSales(ReprintDialogCabang.this.penjualan.getSalesId());
                ReprintDialogCabang.this.penjualanDetails = ReprintDialogCabang.this.penjualanDetailService.readPenjualanDetailByPenjualanId(ReprintDialogCabang.this.penjualan.getId());
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                Paper p = pf.getPaper();
                p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                pf.setPaper(p);
                Book book = new Book();
                if (ReprintDialogCabang.this.penjualan.getCustomerId().equals("SINAR")) {
                    book.append(new serverPrinter(), pf);
                } else {
                    book.append(new printer(), pf);
                }
                job.setPageable(book);
                try {
                    job.print();
                }
                catch (PrinterException printerException) {
                    // empty catch block
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputDialogEventHandler2
    implements PasswordInputListener {
        private passwordInputDialogEventHandler2() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                ReprintDialogCabang.this.penjualanDetails = ReprintDialogCabang.this.penjualanDetailService.readPenjualanDetailByPenjualanId(ReprintDialogCabang.this.penjualan.getId());
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                Paper p = pf.getPaper();
                p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                pf.setPaper(p);
                Book book = new Book();
                if (ReprintDialogCabang.this.penjualan.getCustomerId().equals("SINAR")) {
                    book.append(new serverPrinter(), pf);
                } else {
                    book.append(new printer(), pf);
                }
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
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

