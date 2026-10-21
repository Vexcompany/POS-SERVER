/*
 * Decompiled with CFR 0.152.
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.model.InvoiceReprintLog;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Penjualan;
import minimart.engine.model.Sales;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.InvoiceReprintLogService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanParsialService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.SalesService;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.view.PasswordInputDialog;

public class ReprintDialog
extends JDialog {
    private JCheckBox fakturNonPpnCheck;
    private JCheckBox fakturPpnCheck;
    private JTextField idPenjualanField;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JTable reprintTable;
    private JCheckBox suratJalanCheck;
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
    private List<Object[]> penjualanDetails2;
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private long totalRokok = 0L;
    private float totalPoint = 0.0f;
    private SalesService salesService = new SalesService();
    private InvoiceReprintLogService invoiceReprintLogService = new InvoiceReprintLogService();
    private int qtyCounter1;
    private int qtyCounter2;
    private int currentSize;
    private int pageCounter;
    private int printCounter;
    private int jumlahPage;
    private Customer customer;
    private long totalBelanja = 0L;
    private String rolePass;
    private ButtonGroup bg = new ButtonGroup();

    public ReprintDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.reprintTable.requestFocus();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.bg.add(this.fakturPpnCheck);
        this.bg.add(this.suratJalanCheck);
        this.bg.add(this.fakturNonPpnCheck);
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
        this.jScrollPane1 = new JScrollPane();
        this.reprintTable = new JTable();
        this.idPenjualanField = new JTextField();
        this.jLabel2 = new JLabel();
        this.fakturPpnCheck = new JCheckBox();
        this.suratJalanCheck = new JCheckBox();
        this.fakturNonPpnCheck = new JCheckBox();
        this.setDefaultCloseOperation(2);
        this.reprintTable.setModel(this.reprintTableModel);
        this.reprintTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReprintDialog.this.reprintTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reprintTable);
        this.idPenjualanField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReprintDialog.this.idPenjualanFieldKeyPressed(evt);
            }
        });
        this.jLabel2.setText("ID PENJUALAN");
        this.fakturPpnCheck.setFont(new Font("Tahoma", 1, 11));
        this.fakturPpnCheck.setText("FAKTUR BESAR PPN");
        this.suratJalanCheck.setFont(new Font("Tahoma", 1, 11));
        this.suratJalanCheck.setText("PRINT SURAT JALAN");
        this.fakturNonPpnCheck.setFont(new Font("Tahoma", 1, 11));
        this.fakturNonPpnCheck.setText("FAKTUR BESAR NON PPN");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 538, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(this.fakturPpnCheck).addGap(18, 18, 18).addComponent(this.fakturNonPpnCheck).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.suratJalanCheck).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.idPenjualanField, -2, 149, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(15, 15, 15).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.fakturPpnCheck).addComponent(this.fakturNonPpnCheck)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.idPenjualanField, -2, -1, -2).addComponent(this.jLabel2)).addComponent(this.suratJalanCheck)).addGap(7, 7, 7).addComponent(this.jScrollPane1, -2, 251, -2).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void reprintTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.reprintTable.getSelectedRow() != -1) {
                this.penjualan = this.penjualanService.getPenjualan(this.reprintTableModel.getValueAt(this.reprintTable.getSelectedRow(), 0).toString());
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog2.setVisible(true);
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void idPenjualanFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.penjualan = this.penjualanService.getPenjualan(this.idPenjualanField.getText());
            if (this.penjualan != null) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog2.setVisible(true);
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
            if (page > 0) {
                return 1;
            }
            System.out.println("MASUK PRINT KECIL");
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
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(ReprintDialog.this.konstantaService.getConstantValue("namaToko"), x, y + yK);
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(ReprintDialog.this.konstantaService.getConstantValue("namaGudang"), x, y + yK);
            }
            yK += yP;
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(ReprintDialog.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(ReprintDialog.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            String telp1 = "";
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = ReprintDialog.this.konstantaService.getConstantValue("telpToko1");
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = ReprintDialog.this.konstantaService.getConstantValue("telpGudang1");
            }
            if (!telp1.equals("")) {
                g.drawString("TELP: " + telp1, x, y + (yK += yP));
            }
            String telp2 = "";
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = ReprintDialog.this.konstantaService.getConstantValue("telpToko2");
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = ReprintDialog.this.konstantaService.getConstantValue("telpGudang2");
            }
            if (!telp2.equals("")) {
                g.drawString("TELP: " + telp2, x, y + (yK += yP));
            }
            Customer customer = ReprintDialog.this.customerService.getCustomerById(ReprintDialog.this.penjualan.getCustomerId());
            g.drawString("Cust: " + customer.getId() + " - " + customer.getNama(), x + 0, y + (yK += yP));
            if (!ReprintDialog.this.penjualan.getSalesId().equals("")) {
                g.drawString("Sales: " + ReprintDialog.this.sales.getId() + " - " + ReprintDialog.this.sales.getNama(), x + 0, y + (yK += yP));
            }
            if (ReprintDialog.this.penjualan.getKeterangan().equals("KREDIT")) {
                g.drawString("Jatuh Tempo: " + ReprintDialog.this.penjualan.getJatuhTempo(), x + 0, y + (yK += yP));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Bon. " + ReprintDialog.this.penjualan.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + ReprintDialog.this.penjualan.getKaryawanId(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            float totalDiscount = 0.0f;
            long totalBelanja = 0L;
            ReprintDialog.this.totalRokok = 0L;
            ReprintDialog.this.totalPoint = 0.0f;
            for (int a = 0; a < ReprintDialog.this.penjualanDetails2.size(); ++a) {
                Object[] dataPenjualanDetail = (Object[])ReprintDialog.this.penjualanDetails2.get(a);
                g.drawString(dataPenjualanDetail[0].toString(), x + 0, y + (yK += yP));
                yK = yK + yP - 1;
                float tempValue = Float.parseFloat(dataPenjualanDetail[1].toString());
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)Float.parseFloat(dataPenjualanDetail[1].toString())) : (Number)Float.valueOf(Float.parseFloat(dataPenjualanDetail[1].toString()));
                g.drawString(value + dataPenjualanDetail[2].toString(), x + 20, y + yK);
                g.drawString("x", x + 55, y + yK);
                g.drawString(ReprintDialog.this.helper.formatNumber(Long.parseLong(dataPenjualanDetail[3].toString()) + ""), x + 70, y + yK);
                g.drawString("=", x + 120, y + yK);
                int xtemp = ReprintDialog.this.rataKanan(ReprintDialog.this.helper.formatNumber((long)(Float.parseFloat(dataPenjualanDetail[1].toString()) * (float)Long.parseLong(dataPenjualanDetail[3].toString())) + ""), xRataKanan);
                g.drawString(ReprintDialog.this.helper.formatNumber((long)(Float.parseFloat(dataPenjualanDetail[1].toString()) * (float)Long.parseLong(dataPenjualanDetail[3].toString())) + ""), x + xtemp, y + yK);
                if (Long.parseLong(dataPenjualanDetail[4].toString()) > 0L) {
                    g.drawString("*Disc ( -" + Long.parseLong(dataPenjualanDetail[4].toString()) + ")", x + 0, y + (yK += yP));
                }
                totalDiscount += (float)Long.parseLong(dataPenjualanDetail[4].toString());
                totalBelanja = (long)((float)totalBelanja + Float.parseFloat(dataPenjualanDetail[1].toString()) * (float)Long.parseLong(dataPenjualanDetail[3].toString()));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total item: " + ReprintDialog.this.penjualanDetails2.size(), x + 0, y + (yK += yP));
            int xtemp = ReprintDialog.this.rataKanan(ReprintDialog.this.helper.formatNumber(totalBelanja + ""), xRataKanan);
            g.drawString(ReprintDialog.this.helper.formatNumber(totalBelanja + ""), x + xtemp, y + yK);
            if (totalDiscount > 0.0f) {
                g.drawString("Hemat ", x + 0, y + (yK += yP));
                xtemp = ReprintDialog.this.rataKanan(ReprintDialog.this.helper.formatNumber("-" + (long)totalDiscount + ""), xRataKanan);
                g.drawString(ReprintDialog.this.helper.formatNumber("-" + (long)totalDiscount + "") + "", x + xtemp, y + yK);
                xtemp = ReprintDialog.this.rataKanan(ReprintDialog.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
                g.drawString(ReprintDialog.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + (yK += yP));
                yK += yP;
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += yP));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(ReprintDialog.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + ReprintDialog.this.penjualan.getCreated().getHours() + ":" + ReprintDialog.this.penjualan.getCreated().getMinutes() + ":" + ReprintDialog.this.penjualan.getCreated().getSeconds(), x + 120, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("TERIMA KASIH", x + 57, y + (yK += yP));
            g.setFont(new Font("Times New Roman", 2, 8));
            String footerNota1 = ReprintDialog.this.konstantaService.getConstantValue("footerNota1");
            String footerNota2 = ReprintDialog.this.konstantaService.getConstantValue("footerNota2");
            if (footerNota1 != null || !footerNota1.equals("")) {
                g.drawString(footerNota1, x + 35, y + (yK += yP));
            }
            if (footerNota2 != null || !footerNota2.equals("")) {
                g.drawString(footerNota2, x + 32, y + (yK += yP));
            }
            yK += yP;
            return 0;
        }
    }

    class printSuratJalan
    implements Printable {
        printSuratJalan() {
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
            if (page > 0) {
                return 1;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            int y = 10;
            int x = 25;
            int yK = 18;
            int yP = 11;
            g.setFont(new Font("Times New Roman", 0, 8));
            if (ReprintDialog.this.rolePass.equals("MANAGER") || ReprintDialog.this.rolePass.equals("ADMIN")) {
                g.drawString("[COPY]", x + 0, y + yK);
                yK += yP;
            }
            g.drawString("CV SINAR KARUNIA SULUNG", x, y + yK);
            g.setFont(new Font("Times New Roman", 1, 18));
            g.drawString("SURAT JALAN", x + 190, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("Number", x + 340, y + yK);
            g.drawString(": " + ReprintDialog.this.penjualan.getId(), x + 415, y + yK);
            yK += yP;
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(ReprintDialog.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(ReprintDialog.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            g.drawString("Inv Date", x + 340, y + yK);
            g.drawString(": " + ReprintDialog.this.penjualan.getTanggal() + "", x + 415, y + yK);
            String telp1 = "";
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = ReprintDialog.this.konstantaService.getConstantValue("telpToko1");
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = ReprintDialog.this.konstantaService.getConstantValue("telpGudang1");
            }
            g.drawString("TELP: " + telp1, x, y + (yK += yP));
            g.drawString("Due Date", x + 340, y + yK);
            g.drawString(": " + ReprintDialog.this.penjualan.getJatuhTempo(), x + 415, y + yK);
            String telp2 = "";
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = ReprintDialog.this.konstantaService.getConstantValue("telpToko2");
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = ReprintDialog.this.konstantaService.getConstantValue("telpGudang2");
            }
            g.drawString("TELP: " + telp2, x, y + (yK += yP));
            g.drawString("Payment Term", x + 340, y + yK);
            if (ReprintDialog.this.penjualan.getKeterangan().equals("KREDIT")) {
                g.drawString(": " + ReprintDialog.this.customer.getTop() + "", x + 415, y + yK);
            } else {
                g.drawString(": CASH", x + 415, y + yK);
            }
            g.drawString("Customer", x + 0, y + (yK += yP));
            g.drawString(": " + ReprintDialog.this.customer.getId() + " - " + ReprintDialog.this.customer.getNama() + "/ " + ReprintDialog.this.customer.getNamaPerusahaan(), x + 50 + 0, y + yK);
            g.drawString(": " + ReprintDialog.this.customer.getTelepon(), x + 50 + 0, y + (yK += yP));
            g.drawString(": " + ReprintDialog.this.customer.getAlamat(), x + 50 + 0, y + (yK += yP));
            g.drawString("Page: " + ReprintDialog.this.pageCounter + "/" + ReprintDialog.this.jumlahPage, x + 465 + 0, y + yK);
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            yK += yP;
            int kol1 = 0;
            int kol2 = 25;
            int kol3 = 180;
            int kol4 = 215;
            int kol5 = 250;
            int kol6 = 325;
            int kol7 = 400;
            int kol8 = 465;
            int xRataKananKol3 = 200;
            int xRataKananKol8 = 500;
            g.drawString("No. ", x + kol1, y + yK);
            g.drawString("Nama Barang", x + kol2, y + yK);
            g.drawString("Jumlah", x + kol3, y + yK);
            g.drawString("Sat", x + kol4, y + yK);
            g.drawString("Keterangan", x + kol6, y + yK);
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            ReprintDialog.this.totalRokok = 0L;
            ReprintDialog.this.totalPoint = 0.0f;
            for (int b = ReprintDialog.this.qtyCounter1; b < ReprintDialog.this.qtyCounter2; ++b) {
                Object[] dataPenjualanDetail = (Object[])ReprintDialog.this.penjualanDetails2.get(b);
                g.drawString(b + 1 + "", x + kol1, y + (yK += yP));
                g.drawString(dataPenjualanDetail[0].toString(), x + kol2, y + yK);
                float tempValue = Float.parseFloat(dataPenjualanDetail[1].toString());
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)Float.parseFloat(dataPenjualanDetail[1].toString())) : (Number)Float.valueOf(Float.parseFloat(dataPenjualanDetail[1].toString()));
                int xtemp = ReprintDialog.this.rataKanan(value + "", xRataKananKol3);
                g.drawString(value + "", x + xtemp, y + yK);
                g.drawString(dataPenjualanDetail[2].toString(), x + kol4, y + yK);
            }
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total Item: " + ReprintDialog.this.qtyCounter2, x + kol6 + 50, y + (yK += yP));
            yK += yP;
            yK += yP;
            if (ReprintDialog.this.pageCounter == ReprintDialog.this.jumlahPage) {
                g.drawString("Approved By", x, y + yK);
                g.drawString("Driver", x + kol3 - 15, y + yK);
                g.drawString("Received By", x + kol6 + 50, y + yK);
                g.drawString("CV SINAR KARUNIA SULUNG", x, y + (yK += 75));
                g.drawString("________________", x + kol3 - 15, y + yK);
                g.drawString("________________", x + kol6 + 50, y + yK);
            }
            ReprintDialog.this.printCounter = ReprintDialog.this.printCounter + 1;
            return 0;
        }
    }

    class printerBesarNonPpn
    implements Printable {
        printerBesarNonPpn() {
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
            if (page > 0) {
                return 1;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            int y = 10;
            int x = 25;
            int yK = 18;
            int yP = 11;
            g.setFont(new Font("Times New Roman", 0, 8));
            if (ReprintDialog.this.rolePass.equals("MANAGER") || ReprintDialog.this.rolePass.equals("ADMIN")) {
                g.drawString("[COPY]", x + 0, y + yK);
                yK += yP;
            }
            g.drawString("CV SINAR KARUNIA SULUNG", x, y + yK);
            g.setFont(new Font("Times New Roman", 1, 18));
            g.drawString("INVOICE", x + 200, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("Number", x + 325, y + yK);
            g.drawString(": " + ReprintDialog.this.penjualan.getId(), x + 400, y + yK);
            yK += yP;
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(ReprintDialog.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(ReprintDialog.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            g.drawString("Inv Date", x + 325, y + yK);
            g.drawString(": " + ReprintDialog.this.penjualan.getTanggal() + "", x + 400, y + yK);
            String telp1 = "";
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = ReprintDialog.this.konstantaService.getConstantValue("telpToko1");
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = ReprintDialog.this.konstantaService.getConstantValue("telpGudang1");
            }
            g.drawString("TELP: " + telp1, x, y + (yK += yP));
            g.drawString("Due Date", x + 325, y + yK);
            g.drawString(": " + ReprintDialog.this.penjualan.getJatuhTempo(), x + 400, y + yK);
            String telp2 = "";
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = ReprintDialog.this.konstantaService.getConstantValue("telpToko2");
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = ReprintDialog.this.konstantaService.getConstantValue("telpGudang2");
            }
            g.drawString("TELP: " + telp2, x, y + (yK += yP));
            g.drawString("Payment Term", x + 325, y + yK);
            if (ReprintDialog.this.penjualan.getKeterangan().equals("KREDIT")) {
                g.drawString(": " + ReprintDialog.this.customer.getTop() + "", x + 400, y + yK);
            } else {
                g.drawString(": CASH", x + 400, y + yK);
            }
            g.drawString("Customer", x + 0, y + (yK += yP));
            g.drawString(": " + ReprintDialog.this.customer.getId() + " - " + ReprintDialog.this.customer.getNama() + "/ " + ReprintDialog.this.customer.getNamaPerusahaan(), x + 50 + 0, y + yK);
            g.drawString(": " + ReprintDialog.this.customer.getTelepon(), x + 50 + 0, y + (yK += yP));
            g.drawString(": " + ReprintDialog.this.customer.getAlamat(), x + 50 + 0, y + (yK += yP));
            g.drawString("Page: " + ReprintDialog.this.pageCounter + "/" + ReprintDialog.this.jumlahPage, x + 465 + 0, y + yK);
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            yK += yP;
            int kol1 = 0;
            int kol2 = 25;
            int kol3 = 180;
            int kol4 = 215;
            int kol5 = 250;
            int kol6 = 325;
            int kol7 = 400;
            int kol8 = 465;
            int xRataKananKol3 = 200;
            int xRataKananKol8 = 500;
            g.drawString("No. ", x + kol1, y + yK);
            g.drawString("Nama Barang", x + kol2, y + yK);
            g.drawString("Jumlah", x + kol3, y + yK);
            g.drawString("Sat", x + kol4, y + yK);
            g.drawString("Harga", x + kol5, y + yK);
            g.drawString("Total Harga", x + kol6, y + yK);
            g.drawString("Diskon", x + kol7, y + yK);
            g.drawString("Net Harga", x + kol8, y + yK);
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            ReprintDialog.this.totalRokok = 0L;
            ReprintDialog.this.totalPoint = 0.0f;
            for (int b = ReprintDialog.this.qtyCounter1; b < ReprintDialog.this.qtyCounter2; ++b) {
                Object[] dataPenjualanDetail = (Object[])ReprintDialog.this.penjualanDetails2.get(b);
                g.drawString(b + 1 + "", x + kol1, y + (yK += yP));
                g.drawString(dataPenjualanDetail[0].toString(), x + kol2, y + yK);
                float tempValue = Float.parseFloat(dataPenjualanDetail[1].toString());
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)Float.parseFloat(dataPenjualanDetail[1].toString())) : (Number)Float.valueOf(Float.parseFloat(dataPenjualanDetail[1].toString()));
                int xtemp = ReprintDialog.this.rataKanan(value + "", xRataKananKol3);
                g.drawString(value + "", x + xtemp, y + yK);
                g.drawString(dataPenjualanDetail[2].toString(), x + kol4, y + yK);
                g.drawString(ReprintDialog.this.helper.formatNumber(Math.round(Long.parseLong(dataPenjualanDetail[3].toString())) + ""), x + kol5, y + yK);
                g.drawString(ReprintDialog.this.helper.formatNumber(Math.round(Long.parseLong(dataPenjualanDetail[3].toString()) * (long)Float.parseFloat(dataPenjualanDetail[1].toString())) + ""), x + kol6, y + yK);
                if (Long.parseLong(dataPenjualanDetail[4].toString()) > 0L) {
                    g.drawString("-" + Math.round(Long.parseLong(dataPenjualanDetail[4].toString())), x + kol7, y + yK);
                } else {
                    g.drawString("0", x + kol7, y + yK);
                }
                xtemp = ReprintDialog.this.rataKanan(ReprintDialog.this.helper.formatNumber(Math.round(Long.parseLong(dataPenjualanDetail[5].toString())) + ""), xRataKananKol8);
                g.drawString(ReprintDialog.this.helper.formatNumber(Math.round(Long.parseLong(dataPenjualanDetail[5].toString())) + "") + "", x + xtemp, y + yK);
                if (ReprintDialog.this.printCounter != 0) continue;
                ReprintDialog.this.totalBelanja = ReprintDialog.this.totalBelanja + (long)Math.round(Long.parseLong(dataPenjualanDetail[5].toString()));
            }
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total : ", x + kol6 + 50, y + (yK += yP));
            int xtemp = ReprintDialog.this.rataKanan(ReprintDialog.this.helper.formatNumber(ReprintDialog.this.totalBelanja + ""), xRataKananKol8);
            g.drawString(ReprintDialog.this.helper.formatNumber(ReprintDialog.this.totalBelanja + ""), x + xtemp, y + yK);
            g.drawString("Deskripsi: ", x + 0, y + yK);
            g.drawString("Pembayaran: " + ReprintDialog.this.konstantaService.getConstantValue("bank") + " " + ReprintDialog.this.konstantaService.getConstantValue("rekBank") + " a/n " + ReprintDialog.this.konstantaService.getConstantValue("namaBank"), x + 0, y + (yK += yP));
            g.drawString("Harap melakukan KONFIRMASI setelah melakukan pembayaran & melampirkan bukti transfer", x + 0, y + (yK += yP));
            yK += yP;
            yK += yP;
            if (ReprintDialog.this.pageCounter == ReprintDialog.this.jumlahPage) {
                g.drawString("Approved By", x, y + yK);
                g.drawString("CV SINAR KARUNIA SULUNG", x, y + (yK += 75));
            }
            ReprintDialog.this.printCounter = ReprintDialog.this.printCounter + 1;
            return 0;
        }
    }

    class printerBesar
    implements Printable {
        printerBesar() {
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
            if (page > 0) {
                return 1;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            int y = 10;
            int x = 25;
            int yK = 18;
            int yP = 11;
            g.setFont(new Font("Times New Roman", 0, 8));
            if (ReprintDialog.this.rolePass.equals("MANAGER") || ReprintDialog.this.rolePass.equals("ADMIN")) {
                g.drawString("[COPY]", x + 0, y + yK);
                yK += yP;
            }
            g.drawString("CV SINAR KARUNIA SULUNG", x, y + yK);
            g.setFont(new Font("Times New Roman", 1, 18));
            g.drawString("INVOICE", x + 200, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("Number", x + 325, y + yK);
            g.drawString(": " + ReprintDialog.this.penjualan.getId(), x + 400, y + yK);
            yK += yP;
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(ReprintDialog.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(ReprintDialog.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            g.drawString("Inv Date", x + 325, y + yK);
            g.drawString(": " + ReprintDialog.this.penjualan.getTanggal() + "", x + 400, y + yK);
            String telp1 = "";
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = ReprintDialog.this.konstantaService.getConstantValue("telpToko1");
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = ReprintDialog.this.konstantaService.getConstantValue("telpGudang1");
            }
            g.drawString("TELP: " + telp1, x, y + (yK += yP));
            g.drawString("Due Date", x + 325, y + yK);
            g.drawString(": " + ReprintDialog.this.penjualan.getJatuhTempo(), x + 400, y + yK);
            String telp2 = "";
            if (ReprintDialog.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = ReprintDialog.this.konstantaService.getConstantValue("telpToko2");
            } else if (ReprintDialog.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = ReprintDialog.this.konstantaService.getConstantValue("telpGudang2");
            }
            g.drawString("TELP: " + telp2, x, y + (yK += yP));
            g.drawString("Payment Term", x + 325, y + yK);
            if (ReprintDialog.this.penjualan.getKeterangan().equals("KREDIT")) {
                g.drawString(": " + ReprintDialog.this.customer.getTop() + "", x + 400, y + yK);
            } else {
                g.drawString(": CASH", x + 400, y + yK);
            }
            g.drawString("Customer", x + 0, y + (yK += yP));
            g.drawString(": " + ReprintDialog.this.customer.getId() + " - " + ReprintDialog.this.customer.getNama() + "/ " + ReprintDialog.this.customer.getNamaPerusahaan(), x + 50 + 0, y + yK);
            g.drawString(": " + ReprintDialog.this.customer.getTelepon(), x + 50 + 0, y + (yK += yP));
            g.drawString(": " + ReprintDialog.this.customer.getAlamat(), x + 50 + 0, y + (yK += yP));
            g.drawString("Page: " + ReprintDialog.this.pageCounter + "/" + ReprintDialog.this.jumlahPage, x + 465 + 0, y + yK);
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            yK += yP;
            int kol1 = 0;
            int kol2 = 25;
            int kol3 = 180;
            int kol4 = 215;
            int kol5 = 250;
            int kol6 = 325;
            int kol7 = 400;
            int kol8 = 465;
            int xRataKananKol3 = 200;
            int xRataKananKol8 = 500;
            g.drawString("No. ", x + kol1, y + yK);
            g.drawString("Nama Barang", x + kol2, y + yK);
            g.drawString("Jumlah", x + kol3, y + yK);
            g.drawString("Sat", x + kol4, y + yK);
            g.drawString("Harga", x + kol5, y + yK);
            g.drawString("Total Harga", x + kol6, y + yK);
            g.drawString("Diskon", x + kol7, y + yK);
            g.drawString("Net Harga", x + kol8, y + yK);
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            ReprintDialog.this.totalRokok = 0L;
            ReprintDialog.this.totalPoint = 0.0f;
            for (int b = ReprintDialog.this.qtyCounter1; b < ReprintDialog.this.qtyCounter2; ++b) {
                Object[] dataPenjualanDetail = (Object[])ReprintDialog.this.penjualanDetails2.get(b);
                g.drawString(b + 1 + "", x + kol1, y + (yK += yP));
                g.drawString(dataPenjualanDetail[0].toString(), x + kol2, y + yK);
                float tempValue = Float.parseFloat(dataPenjualanDetail[1].toString());
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)Float.parseFloat(dataPenjualanDetail[1].toString())) : (Number)Float.valueOf(Float.parseFloat(dataPenjualanDetail[1].toString()));
                int xtemp = ReprintDialog.this.rataKanan(value + "", xRataKananKol3);
                g.drawString(value + "", x + xtemp, y + yK);
                g.drawString(dataPenjualanDetail[2].toString(), x + kol4, y + yK);
                g.drawString(ReprintDialog.this.helper.formatNumber(Math.round((double)Long.parseLong(dataPenjualanDetail[3].toString()) / 1.11) + ""), x + kol5, y + yK);
                g.drawString(ReprintDialog.this.helper.formatNumber(Math.round((double)(Long.parseLong(dataPenjualanDetail[3].toString()) * (long)Float.parseFloat(dataPenjualanDetail[1].toString())) / 1.11) + ""), x + kol6, y + yK);
                if (Long.parseLong(dataPenjualanDetail[4].toString()) > 0L) {
                    g.drawString("-" + Math.round((double)Long.parseLong(dataPenjualanDetail[4].toString()) / 1.11), x + kol7, y + yK);
                } else {
                    g.drawString("0", x + kol7, y + yK);
                }
                xtemp = ReprintDialog.this.rataKanan(ReprintDialog.this.helper.formatNumber(Math.round((double)Long.parseLong(dataPenjualanDetail[5].toString()) / 1.11) + ""), xRataKananKol8);
                g.drawString(ReprintDialog.this.helper.formatNumber(Math.round((double)Long.parseLong(dataPenjualanDetail[5].toString()) / 1.11) + "") + "", x + xtemp, y + yK);
                if (ReprintDialog.this.printCounter != 0) continue;
                ReprintDialog.this.totalBelanja = ReprintDialog.this.totalBelanja + Math.round((double)Long.parseLong(dataPenjualanDetail[5].toString()) / 1.11);
            }
            g.drawString("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total : ", x + kol6 + 50, y + (yK += yP));
            int xtemp = ReprintDialog.this.rataKanan(ReprintDialog.this.helper.formatNumber(ReprintDialog.this.totalBelanja + ""), xRataKananKol8);
            g.drawString(ReprintDialog.this.helper.formatNumber(ReprintDialog.this.totalBelanja + ""), x + xtemp, y + yK);
            g.drawString("Deskripsi: ", x + 0, y + yK);
            g.drawString("Pembayaran: " + ReprintDialog.this.konstantaService.getConstantValue("bank") + " " + ReprintDialog.this.konstantaService.getConstantValue("rekBank") + " a/n " + ReprintDialog.this.konstantaService.getConstantValue("namaBank"), x + 0, y + (yK += yP));
            if (ReprintDialog.this.pageCounter == ReprintDialog.this.jumlahPage) {
                g.drawString("PPN : ", x + kol6 + 50, y + yK);
                xtemp = ReprintDialog.this.rataKanan(ReprintDialog.this.helper.formatNumber(Math.round((double)ReprintDialog.this.totalBelanja * 0.11) + ""), xRataKananKol8);
                g.drawString(ReprintDialog.this.helper.formatNumber(Math.round((double)ReprintDialog.this.totalBelanja * 0.11) + ""), x + xtemp, y + yK);
            }
            g.drawString("Harap melakukan KONFIRMASI setelah melakukan pembayaran & melampirkan bukti transfer", x + 0, y + (yK += yP));
            if (ReprintDialog.this.pageCounter == ReprintDialog.this.jumlahPage) {
                g.drawString("Total Invoice : ", x + kol6 + 50, y + yK);
                xtemp = ReprintDialog.this.rataKanan(ReprintDialog.this.helper.formatNumber(Math.round((double)ReprintDialog.this.totalBelanja * 1.11) + ""), xRataKananKol8);
                g.drawString(ReprintDialog.this.helper.formatNumber(Math.round((double)ReprintDialog.this.totalBelanja * 1.11) + ""), x + xtemp, y + yK);
            }
            yK += yP;
            yK += yP;
            if (ReprintDialog.this.pageCounter == ReprintDialog.this.jumlahPage) {
                g.drawString("Approved By", x, y + yK);
                g.drawString("CV SINAR KARUNIA SULUNG", x, y + (yK += 75));
            }
            ReprintDialog.this.printCounter = ReprintDialog.this.printCounter + 1;
            return 0;
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            block27: {
                if (!karyawan.getRole().equalsIgnoreCase("DIREKTUR") && !karyawan.getRole().equalsIgnoreCase("MANAGER") && !karyawan.getRole().equalsIgnoreCase("ADMIN") && !karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) break block27;
                ReprintDialog.this.totalBelanja = 0L;
                ReprintDialog.this.rolePass = karyawan.getRole();
                ReprintDialog.this.sales = ReprintDialog.this.salesService.getSales(ReprintDialog.this.penjualan.getSalesId());
                ReprintDialog.this.customer = ReprintDialog.this.customerService.getCustomerById(ReprintDialog.this.penjualan.getCustomerId());
                ReprintDialog.this.penjualanDetails2 = ReprintDialog.this.penjualanDetailService.readPenjualanDetailByPenjualanId2(ReprintDialog.this.penjualan.getId());
                InvoiceReprintLog invoiceReprintLog = new InvoiceReprintLog();
                invoiceReprintLog.setPenjualanId(ReprintDialog.this.penjualan.getId());
                invoiceReprintLog.setKaryawanId(ReprintDialog.this.helper.getKaryawan().getId());
                invoiceReprintLog.setCreated(new Date());
                int result = ReprintDialog.this.invoiceReprintLogService.create(invoiceReprintLog);
                if (result == 1) {
                    if (ReprintDialog.this.fakturPpnCheck.isSelected()) {
                        ReprintDialog.this.qtyCounter1 = 0;
                        ReprintDialog.this.qtyCounter2 = 0;
                        ReprintDialog.this.pageCounter = 1;
                        ReprintDialog.this.currentSize = ReprintDialog.this.penjualanDetails2.size();
                        ReprintDialog.this.jumlahPage = ReprintDialog.this.penjualanDetails2.size() / 50;
                        if (ReprintDialog.this.penjualanDetails2.size() % 50 > 0) {
                            ReprintDialog.this.jumlahPage = ReprintDialog.this.jumlahPage + 1;
                        }
                        if (ReprintDialog.this.jumlahPage > 1) {
                            ReprintDialog.this.qtyCounter2 = 50;
                        } else {
                            ReprintDialog.this.qtyCounter2 = ReprintDialog.this.penjualanDetails2.size();
                        }
                        for (int a = 0; a < ReprintDialog.this.jumlahPage; ++a) {
                            ReprintDialog.this.printCounter = 0;
                            PrinterJob job = PrinterJob.getPrinterJob();
                            PageFormat pf = job.defaultPage();
                            Paper p = pf.getPaper();
                            p.setSize(p.getWidth(), p.getHeight());
                            p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight());
                            pf.setPaper(p);
                            Book book = new Book();
                            book.append(new printerBesar(), pf);
                            job.setPageable(book);
                            try {
                                job.print();
                            }
                            catch (PrinterException printerException) {
                                // empty catch block
                            }
                            ReprintDialog.this.pageCounter = ReprintDialog.this.pageCounter + 1;
                            ReprintDialog.this.qtyCounter1 = ReprintDialog.this.qtyCounter1 + 50;
                            int tempup = 0;
                            ReprintDialog.this.currentSize = ReprintDialog.this.currentSize - 50;
                            tempup = ReprintDialog.this.currentSize > 50 ? 50 : ReprintDialog.this.currentSize;
                            ReprintDialog.this.qtyCounter2 = ReprintDialog.this.qtyCounter2 + tempup;
                        }
                    } else if (ReprintDialog.this.fakturNonPpnCheck.isSelected()) {
                        ReprintDialog.this.qtyCounter1 = 0;
                        ReprintDialog.this.qtyCounter2 = 0;
                        ReprintDialog.this.pageCounter = 1;
                        ReprintDialog.this.currentSize = ReprintDialog.this.penjualanDetails2.size();
                        ReprintDialog.this.jumlahPage = ReprintDialog.this.penjualanDetails2.size() / 50;
                        if (ReprintDialog.this.penjualanDetails2.size() % 50 > 0) {
                            ReprintDialog.this.jumlahPage = ReprintDialog.this.jumlahPage + 1;
                        }
                        if (ReprintDialog.this.jumlahPage > 1) {
                            ReprintDialog.this.qtyCounter2 = 50;
                        } else {
                            ReprintDialog.this.qtyCounter2 = ReprintDialog.this.penjualanDetails2.size();
                        }
                        for (int a = 0; a < ReprintDialog.this.jumlahPage; ++a) {
                            ReprintDialog.this.printCounter = 0;
                            PrinterJob job = PrinterJob.getPrinterJob();
                            PageFormat pf = job.defaultPage();
                            Paper p = pf.getPaper();
                            p.setSize(p.getWidth(), p.getHeight());
                            p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight());
                            pf.setPaper(p);
                            Book book = new Book();
                            book.append(new printerBesarNonPpn(), pf);
                            job.setPageable(book);
                            try {
                                job.print();
                            }
                            catch (PrinterException tempup) {
                                // empty catch block
                            }
                            ReprintDialog.this.pageCounter = ReprintDialog.this.pageCounter + 1;
                            ReprintDialog.this.qtyCounter1 = ReprintDialog.this.qtyCounter1 + 50;
                            int tempup = 0;
                            ReprintDialog.this.currentSize = ReprintDialog.this.currentSize - 50;
                            tempup = ReprintDialog.this.currentSize > 50 ? 50 : ReprintDialog.this.currentSize;
                            ReprintDialog.this.qtyCounter2 = ReprintDialog.this.qtyCounter2 + tempup;
                        }
                    } else if (ReprintDialog.this.suratJalanCheck.isSelected()) {
                        ReprintDialog.this.qtyCounter1 = 0;
                        ReprintDialog.this.qtyCounter2 = 0;
                        ReprintDialog.this.pageCounter = 1;
                        ReprintDialog.this.currentSize = ReprintDialog.this.penjualanDetails2.size();
                        ReprintDialog.this.jumlahPage = ReprintDialog.this.penjualanDetails2.size() / 50;
                        if (ReprintDialog.this.penjualanDetails2.size() % 50 > 0) {
                            ReprintDialog.this.jumlahPage = ReprintDialog.this.jumlahPage + 1;
                        }
                        if (ReprintDialog.this.jumlahPage > 1) {
                            ReprintDialog.this.qtyCounter2 = 50;
                        } else {
                            ReprintDialog.this.qtyCounter2 = ReprintDialog.this.penjualanDetails2.size();
                        }
                        for (int a = 0; a < ReprintDialog.this.jumlahPage; ++a) {
                            ReprintDialog.this.printCounter = 0;
                            PrinterJob job = PrinterJob.getPrinterJob();
                            PageFormat pf = job.defaultPage();
                            Paper p = pf.getPaper();
                            p.setSize(p.getWidth(), p.getHeight());
                            p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight());
                            pf.setPaper(p);
                            Book book = new Book();
                            book.append(new printSuratJalan(), pf);
                            job.setPageable(book);
                            try {
                                job.print();
                            }
                            catch (PrinterException tempup) {
                                // empty catch block
                            }
                            ReprintDialog.this.pageCounter = ReprintDialog.this.pageCounter + 1;
                            ReprintDialog.this.qtyCounter1 = ReprintDialog.this.qtyCounter1 + 50;
                            int tempup = 0;
                            ReprintDialog.this.currentSize = ReprintDialog.this.currentSize - 50;
                            tempup = ReprintDialog.this.currentSize > 50 ? 50 : ReprintDialog.this.currentSize;
                            ReprintDialog.this.qtyCounter2 = ReprintDialog.this.qtyCounter2 + tempup;
                        }
                    } else {
                        PrinterJob job = PrinterJob.getPrinterJob();
                        PageFormat pf = job.defaultPage();
                        Paper p = pf.getPaper();
                        p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                        p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                        pf.setPaper(p);
                        Book book = new Book();
                        book.append(new printer(), pf);
                        job.setPageable(book);
                        try {
                            job.print();
                        }
                        catch (PrinterException printerException) {
                            // empty catch block
                        }
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

