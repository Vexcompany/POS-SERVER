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
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Customer;
import minimart.engine.model.Karyawan;
import minimart.engine.model.PenjualanParsial;
import minimart.engine.model.PenjualanParsialDetail;
import minimart.engine.model.Sales;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PenjualanParsialDetailService;
import minimart.engine.service.PenjualanParsialService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.SalesService;
import minimart.ui.listener.PasswordInputListener;

public class ReprintDialogParsial
extends JDialog {
    private JTextField idPenjualanField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JTable reprintTable;
    private String[] reprintColumn = new String[]{"ID", "Customer", "Tanggal", "Total", "Keterangan"};
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
    private PenjualanParsial penjualanParsial;
    private BarangService barangService = new BarangService();
    private List<PenjualanParsialDetail> penjualanParsialDetails;
    private PenjualanParsialDetailService penjualanParsialDetailService = new PenjualanParsialDetailService();
    private long totalRokok = 0L;
    private float totalPoint = 0.0f;
    private SalesService salesService = new SalesService();

    public ReprintDialogParsial(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.reprintTable.requestFocus();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.loadDataParsial();
    }

    public void loadDataParsial() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(new Date());
        Object[] reprintColumn = new String[]{"ID", "Customer", "Tanggal"};
        this.reprintTableModel.setColumnIdentifiers(reprintColumn);
        List<PenjualanParsial> penjualans = this.penjualanParsialService.getPenjualanTerakhir(date);
        for (PenjualanParsial tempPenjualan : penjualans) {
            Customer tempCust = this.customerService.getCustomerById(tempPenjualan.getCustomerId());
            Object[] object = new Object[]{tempPenjualan.getId(), tempCust.getId(), tempPenjualan.getTanggal()};
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
                ReprintDialogParsial.this.reprintTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reprintTable);
        this.idPenjualanField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReprintDialogParsial.this.idPenjualanFieldKeyPressed(evt);
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
                this.penjualanParsial = this.penjualanParsialService.getPenjualanParsial(this.reprintTableModel.getValueAt(this.reprintTable.getSelectedRow(), 0).toString());
                this.penjualanParsialDetails = this.penjualanParsialDetailService.getPenjualanParsials(this.penjualanParsial.getId());
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                Paper p = pf.getPaper();
                p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                pf.setPaper(p);
                Book book = new Book();
                book.append(new printer(), pf);
                job.setPageable(book);
                boolean ok = job.printDialog();
                if (ok) {
                    try {
                        job.print();
                    }
                    catch (PrinterException printerException) {}
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void idPenjualanFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.penjualanParsial = this.penjualanParsialService.getPenjualanParsial(this.idPenjualanField.getText());
            if (this.penjualanParsial != null) {
                this.penjualanParsialDetails = this.penjualanParsialDetailService.getPenjualanParsials(this.penjualanParsial.getId());
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                Paper p = pf.getPaper();
                p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                pf.setPaper(p);
                Book book = new Book();
                book.append(new printer(), pf);
                job.setPageable(book);
                boolean ok = job.printDialog();
                if (ok) {
                    try {
                        job.print();
                    }
                    catch (PrinterException printerException) {}
                }
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
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            int y = 0;
            int x = 5;
            int yK = 18;
            int yP = 11;
            int xRataKanan = 160;
            g.setFont(new Font("Times New Roman", 0, 8));
            Customer customer = ReprintDialogParsial.this.customerService.getCustomerById(ReprintDialogParsial.this.penjualanParsial.getCustomerId());
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(ReprintDialogParsial.this.penjualanParsial.getTanggal());
            g.drawString("Cust: " + customer.getId() + " - " + customer.getNama(), x + 0, y + yK);
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += 8));
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("No Faktur. " + ReprintDialogParsial.this.penjualanParsial.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + ReprintDialogParsial.this.helper.getKaryawan().getNama(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            for (PenjualanParsialDetail penjualanParsialDetail : ReprintDialogParsial.this.penjualanParsialDetails) {
                Barang barang = ReprintDialogParsial.this.barangService.getBarang(penjualanParsialDetail.getBarangId());
                g.drawString("(" + penjualanParsialDetail.getJumlahBarang() + penjualanParsialDetail.getSatuan() + ") " + barang.getNama(), x + 0, y + (yK += yP));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("TERIMA KASIH", x + 0, y + (yK += yP));
            g.drawString("SEGERA BAYARKAN KE KASIR", x + 0, y + (yK += yP));
            g.drawString("MENGGUNAKAN STRUK INI", x + 0, y + (yK += yP));
            return 0;
        }
    }

    private class passwordInputDialogEventHandler2
    implements PasswordInputListener {
        private passwordInputDialogEventHandler2() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER")) {
                // empty if block
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

