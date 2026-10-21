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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.dao.CustomerDao;
import minimart.engine.daoimpl.CustomerDaoImpl;
import minimart.engine.model.Barang;
import minimart.engine.model.Customer;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Penjualan;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.model.Sales;
import minimart.engine.model.ShiftKasir;
import minimart.engine.model.StockCommit;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerPointCommitService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.PiutangService;
import minimart.engine.service.SalesService;
import minimart.engine.service.ShiftKasirService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.ui.listener.CetakNotaListener;
import minimart.ui.listener.ConfirmationDialogListener;

public class CetakNotaExcel
extends JDialog {
    private JButton cancelButton;
    private JTextField customerAlamatField;
    private JTextField customerNameField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JTextField jumlahBarangField;
    private JTextField karyawanField;
    private JFormattedTextField kembaliField;
    private JButton printButton;
    private JFormattedTextField tanggalField;
    private JFormattedTextField totalField;
    private JFormattedTextField tunaiField;
    private BarangService barangService = new BarangService();
    private CustomerService customerService = new CustomerService();
    private ShiftKasirService shiftKasirService = new ShiftKasirService();
    private Helper helper = Helper.getInstance();
    private Karyawan karyawan;
    private Customer customer;
    private PenjualanService penjualanService = new PenjualanService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private CetakNotaListener listener;
    private Penjualan penjualan;
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    private PiutangService piutangService = new PiutangService();
    private PembelianService pembelianService = new PembelianService();
    private boolean canGoThrough;
    private int screenHeight;
    private int screenWidth;
    private long totalRokok = 0L;
    private float totalPoint;
    private KonstantaService konstantaService = new KonstantaService();
    private Sales sales;
    private SalesService salesService = new SalesService();
    private Date jatuhTempo;
    private String keterangan;
    private CommonEngine comm = new CommonEngine();
    private CustomerPointCommitService customerPointCommitService = new CustomerPointCommitService();
    private CustomerDao cd = new CustomerDaoImpl();
    private long totalKeuntungan = 0L;
    private List<Object[]> dataPenjualan = null;
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();

    public CetakNotaExcel(Frame parent, boolean modal, Float value, Date jatuhTempo, String keterangan, List<Object[]> dataPenjualan, String customerId) {
        super(parent, modal);
        this.initComponents();
        this.setTitle("Print");
        this.requestDefaultFocus();
        this.karyawan = this.helper.getKaryawan();
        this.customer = this.customerService.getCustomerById(customerId);
        this.dataPenjualan = dataPenjualan;
        this.jatuhTempo = jatuhTempo;
        this.keterangan = keterangan;
        this.jumlahBarangField.setText(dataPenjualan.size() + "");
        this.kembaliField.setValue(0);
        this.tunaiField.setValue(0);
        this.totalField.setValue(value);
        this.tanggalField.setValue(new Date());
        this.karyawanField.setText(this.karyawan.getNama());
        this.customerNameField.setText(this.customer.getNama());
        this.customerAlamatField.setText(this.customer.getAlamat());
        this.tunaiField.setText("");
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.printButton.setEnabled(true);
    }

    protected void requestDefaultFocus() {
        this.tunaiField.requestFocus();
        this.tunaiField.setText(this.tunaiField.getText().replace(".", ""));
        this.tunaiField.selectAll();
    }

    public void addListener(CetakNotaListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.tanggalField = new JFormattedTextField();
        this.karyawanField = new JTextField();
        this.customerNameField = new JTextField();
        this.customerAlamatField = new JTextField();
        this.jLabel5 = new JLabel();
        this.jumlahBarangField = new JTextField();
        this.jLabel6 = new JLabel();
        this.printButton = new JButton();
        this.cancelButton = new JButton();
        this.totalField = new JFormattedTextField();
        this.jLabel2 = new JLabel();
        this.jLabel7 = new JLabel();
        this.tunaiField = new JFormattedTextField();
        this.kembaliField = new JFormattedTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Tanggal");
        this.jLabel3.setText("Petugas");
        this.jLabel4.setText("Customer");
        this.tanggalField.setEditable(false);
        this.karyawanField.setEditable(false);
        this.customerNameField.setEditable(false);
        this.customerAlamatField.setEditable(false);
        this.jLabel5.setText("Barang");
        this.jumlahBarangField.setEditable(false);
        this.jumlahBarangField.setText("90");
        this.jLabel6.setFont(new Font("Tahoma", 1, 24));
        this.jLabel6.setText("Total");
        this.printButton.setText("Print");
        this.printButton.setPreferredSize(new Dimension(73, 23));
        this.printButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakNotaExcel.this.printButtonActionPerformed(evt);
            }
        });
        this.printButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNotaExcel.this.printButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakNotaExcel.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNotaExcel.this.cancelButtonKeyPressed(evt);
            }
        });
        this.totalField.setEditable(false);
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setFont(new Font("Tahoma", 1, 24));
        this.jLabel2.setFont(new Font("Tahoma", 1, 24));
        this.jLabel2.setText("Tunai");
        this.jLabel7.setFont(new Font("Tahoma", 1, 24));
        this.jLabel7.setText("Kembali");
        this.tunaiField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.tunaiField.setFont(new Font("Tahoma", 1, 24));
        this.tunaiField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakNotaExcel.this.tunaiFieldActionPerformed(evt);
            }
        });
        this.tunaiField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakNotaExcel.this.tunaiFieldKeyPressed(evt);
            }
        });
        this.kembaliField.setEditable(false);
        this.kembaliField.setBackground(new Color(153, 255, 204));
        this.kembaliField.setForeground(new Color(0, 0, 153));
        this.kembaliField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.kembaliField.setFont(new Font("Tahoma", 1, 24));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(20, 20, 20).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel3).addComponent(this.jLabel4).addComponent(this.jLabel6).addComponent(this.jLabel2).addComponent(this.jLabel7)).addGap(36, 36, 36).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.customerAlamatField, GroupLayout.Alignment.LEADING, -1, 309, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.karyawanField, GroupLayout.Alignment.LEADING, -1, 186, Short.MAX_VALUE).addComponent(this.tanggalField, GroupLayout.Alignment.LEADING, -1, 186, Short.MAX_VALUE).addComponent(this.customerNameField, -1, 186, Short.MAX_VALUE)).addGap(24, 24, 24).addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jumlahBarangField, -2, 61, -2))).addGap(23, 23, 23)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.totalField, -2, 222, -2).addGap(0, 12, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGap(36, 36, 36).addComponent(this.printButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton, -2, -1, -2))).addComponent(this.tunaiField, -2, 222, -2).addComponent(this.kembaliField, -2, 222, -2)).addContainerGap(-1, Short.MAX_VALUE)))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.tanggalField, -2, -1, -2).addComponent(this.jumlahBarangField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.karyawanField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.customerNameField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerAlamatField, -2, -1, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.totalField, -2, 47, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.tunaiField, -2, 45, -2)).addGap(9, 9, 9).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kembaliField).addComponent(this.jLabel7)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.printButton, -2, -1, -2).addComponent(this.cancelButton, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void saveClick() {
        boolean canSave = true;
        if (canSave) {
            this.printButton.setEnabled(false);
            this.cancelButton.requestFocus();
            this.penjualan = new Penjualan();
            this.penjualan.setId(this.helper.generateCodeGeneral(this.penjualanService.getLastId()));
            this.penjualan.setKaryawanId(this.karyawan.getId());
            this.penjualan.setCustomerId(this.customer.getId());
            if (this.sales != null) {
                this.penjualan.setSalesId(this.sales.getId());
            }
            this.penjualan.setKasir(this.helper.getKasir());
            this.penjualan.setTanggal(new Date());
            this.penjualan.setTotalPenjualan((long)Float.parseFloat(this.totalField.getValue() + ""));
            this.penjualan.setStatus(this.helper.getStatusProgram());
            this.penjualan.setCreated(new Date());
            this.penjualan.setUpdated(new Date());
            this.penjualan.setKeterangan(this.keterangan);
            if (this.keterangan.equals("KREDIT")) {
                this.penjualan.setStatusPelunasan("BELUM LUNAS");
            } else if (this.keterangan.equals("CASH")) {
                this.penjualan.setStatusPelunasan("SUDAH LUNAS");
            }
            this.penjualan.setJatuhTempo(this.jatuhTempo);
            int penjualanStatus = this.penjualanService.createPenjualan(this.penjualan);
            if (penjualanStatus == 1) {
                ShiftKasir shiftKasir;
                this.customer.setUpdated(new Date());
                if (this.helper.getJenisProgram().equals("GUDANGTOKO")) {
                    if (this.helper.getStatusProgram().equals("TOKO") && this.penjualan.getKeterangan().equals("CASH")) {
                        shiftKasir = this.shiftKasirService.getOpenedShiftKasir(this.helper.getKasir());
                        shiftKasir.setTotalUang(shiftKasir.getTotalUang() + (long)Float.parseFloat(this.totalField.getValue() + ""));
                        this.shiftKasirService.update(shiftKasir);
                    }
                } else if (this.helper.getJenisProgram().equals("GUDANGSAJA") && this.penjualan.getKeterangan().equals("CASH")) {
                    shiftKasir = this.shiftKasirService.getOpenedShiftKasir(this.helper.getKasir());
                    shiftKasir.setTotalUang(shiftKasir.getTotalUang() + (Long)this.totalField.getValue());
                    this.shiftKasirService.update(shiftKasir);
                }
                int size = this.dataPenjualan.size();
                ArrayList<PenjualanDetail> penjualanDetailPush = new ArrayList<PenjualanDetail>();
                ArrayList StockCommitsPush = new ArrayList();
                for (int a = 0; a < size; ++a) {
                    Object[] tempData = this.dataPenjualan.get(a);
                    PenjualanDetail penjualanDetail = new PenjualanDetail();
                    Barang barang = this.barangService.getBarang(tempData[0] + "");
                    penjualanDetail.setPenjualanId(this.penjualan.getId());
                    penjualanDetail.setBarangId(barang.getId());
                    penjualanDetail.setJumlahBarang(Float.parseFloat(tempData[2] + ""));
                    penjualanDetail.setSatuan(tempData[3] + "");
                    penjualanDetail.setDiscount2(Long.parseLong(tempData[6] + ""));
                    penjualanDetail.setHargaJual(Long.parseLong(tempData[4] + ""));
                    penjualanDetail.setTotalHarga((long)Float.parseFloat(tempData[7] + ""));
                    float totalBarang = 0.0f;
                    if (tempData[3].toString().equals(barang.getSatuan1())) {
                        totalBarang = Float.parseFloat(tempData[2] + "");
                    } else if (tempData[3].toString().equals(barang.getSatuan2())) {
                        totalBarang = Float.parseFloat(tempData[2] + "") * (float)barang.getKonversi21();
                    } else if (tempData[3].toString().equals(barang.getSatuan3())) {
                        totalBarang = Float.parseFloat(tempData[2] + "") * (float)barang.getKonversi21() * (float)barang.getKonversi32();
                    }
                    if (!this.customer.getId().equalsIgnoreCase("SINAR")) {
                        penjualanDetail.setTotalKeuntungan((long)(Float.parseFloat(tempData[7] + "") - (float)barang.getHpp() * totalBarang));
                        if (barang.getHpp() > 0L) {
                            this.totalKeuntungan += penjualanDetail.getTotalKeuntungan();
                        }
                    }
                    penjualanDetail.setCreated(new Date());
                    penjualanDetail.setUpdated(new Date());
                    penjualanDetailPush.add(penjualanDetail);
                    StockCommit stockCommit1 = new StockCommit();
                    stockCommit1.setIdBarang(barang.getId());
                    stockCommit1.setIdTransaksi(this.penjualan.getId());
                    stockCommit1.setQueryType("kurang");
                    stockCommit1.setStockType("toko");
                    stockCommit1.setTransactionType("Penjualan");
                    stockCommit1.setJumlahBarang(totalBarang);
                    this.sinkronisasiBarangService.insertCommit(stockCommit1);
                    this.barangService.updateStokBarangToko(-totalBarang, barang.getId());
                }
                this.penjualanDetailService.createdPenjualanDetailBatch(penjualanDetailPush);
                String aktifkanPoint = this.konstantaService.getConstantValue("aktifkanPoint");
                if (aktifkanPoint.equals("YES") && !this.customer.getId().equalsIgnoreCase("C") && !this.customer.getId().equalsIgnoreCase("SINAR") && !this.customer.getId().equalsIgnoreCase("LUKAS")) {
                    if (this.konstantaService.getConstantValue("kategoriPoin").equals("OMSET")) {
                        long total = (Long)this.totalField.getValue();
                        float rokokValue = Float.parseFloat(this.konstantaService.getConstantValue("pointRokok"));
                        float lainValue = Float.parseFloat(this.konstantaService.getConstantValue("pointLain"));
                        float pointRokok = (float)this.totalRokok / rokokValue;
                        float pointLain = (float)(total - this.totalRokok) / lainValue;
                        this.totalPoint = pointLain + pointRokok;
                        this.customer.setPoint(this.customer.getPoint() + (long)this.totalPoint);
                        this.customerPointCommitService.insertPointCommit(this.customer.getId(), this.penjualan.getId(), (long)this.totalPoint, "INSERT", "Penjualan");
                        this.penjualan.setPoin((int)this.totalPoint);
                    } else if (this.konstantaService.getConstantValue("kategoriPoin").equals("PROFIT")) {
                        this.totalPoint = this.totalKeuntungan / Long.parseLong(this.konstantaService.getConstantValue("poinProfit"));
                        this.customer.setPoint(this.customer.getPoint() + (long)this.totalPoint);
                        this.customerPointCommitService.insertPointCommit(this.customer.getId(), this.penjualan.getId(), (long)this.totalPoint, "INSERT", "Penjualan");
                    }
                    this.customer.setUpdated(new Date());
                    int total = this.customerService.updateCustomer(this.customer);
                }
                this.listener.printClick();
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                Paper p = pf.getPaper();
                p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                pf.setPaper(p);
                Book book = new Book();
                if (this.penjualan.getCustomerId().equals("SINAR")) {
                    book.append(new serverPrinter(), pf);
                } else {
                    book.append(new printer(), pf);
                }
                job.setPageable(book);
                try {
                    job.print();
                }
                catch (PrinterException printerException) {}
            } else {
                JOptionPane.showMessageDialog(null, "Penyimpanan Transaksi Gagal");
            }
        }
    }

    private void printButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (Long.parseLong(this.tunaiField.getValue().toString()) == 0L || Long.parseLong(this.tunaiField.getValue().toString()) >= Long.parseLong(this.totalField.getValue().toString())) {
                this.saveClick();
            } else {
                JOptionPane.showMessageDialog(null, "Uang Tunai Tidak Mencukupi");
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.tunaiField.setText(this.tunaiField.getValue().toString().replace(".", ""));
            this.tunaiField.selectAll();
            this.tunaiField.requestFocus();
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void tunaiFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.helper.isInteger(this.tunaiField.getText()) && Integer.parseInt(this.tunaiField.getText()) == 0 || this.helper.isInteger(this.tunaiField.getText()) && Integer.parseInt(this.tunaiField.getText()) >= Integer.parseInt(this.totalField.getValue() + "")) {
                this.kembaliField.setValue(Float.valueOf((float)Integer.parseInt(this.tunaiField.getText()) - Float.parseFloat(this.totalField.getValue() + "")));
                this.printButton.requestFocus();
                this.tunaiField.setBackground(Color.white);
            } else {
                this.tunaiField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void printButtonActionPerformed(ActionEvent evt) {
        if (Long.parseLong(this.tunaiField.getValue().toString()) == 0L || Long.parseLong(this.tunaiField.getValue().toString()) >= Long.parseLong(this.totalField.getValue().toString())) {
            this.saveClick();
        } else {
            JOptionPane.showMessageDialog(null, "Uang Tunai Tidak Mencukupi");
        }
    }

    private void tunaiFieldActionPerformed(ActionEvent evt) {
    }

    private int rataKanan(String price, int x) {
        int len = price.length();
        for (int i = 0; i < len; ++i) {
            x -= 4;
        }
        return x;
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            CetakNotaExcel.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            CetakNotaExcel.this.canGoThrough = false;
        }
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
            if (CetakNotaExcel.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNotaExcel.this.konstantaService.getConstantValue("namaToko"), x, y + yK);
            } else if (CetakNotaExcel.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNotaExcel.this.konstantaService.getConstantValue("namaGudang"), x, y + yK);
            }
            yK += yP;
            if (CetakNotaExcel.this.helper.getStatusProgram().equals("TOKO")) {
                g.drawString(CetakNotaExcel.this.konstantaService.getConstantValue("alamatToko"), x, y + yK);
            } else if (CetakNotaExcel.this.helper.getStatusProgram().equals("GUDANG")) {
                g.drawString(CetakNotaExcel.this.konstantaService.getConstantValue("alamatGudang"), x, y + yK);
            }
            String telp1 = "";
            if (CetakNotaExcel.this.helper.getStatusProgram().equals("TOKO")) {
                telp1 = CetakNotaExcel.this.konstantaService.getConstantValue("telpToko1");
            } else if (CetakNotaExcel.this.helper.getStatusProgram().equals("GUDANG")) {
                telp1 = CetakNotaExcel.this.konstantaService.getConstantValue("telpGudang1");
            }
            if (!telp1.equals("")) {
                g.drawString("TELP: " + telp1, x, y + (yK += yP));
            }
            String telp2 = "";
            if (CetakNotaExcel.this.helper.getStatusProgram().equals("TOKO")) {
                telp2 = CetakNotaExcel.this.konstantaService.getConstantValue("telpToko2");
            } else if (CetakNotaExcel.this.helper.getStatusProgram().equals("GUDANG")) {
                telp2 = CetakNotaExcel.this.konstantaService.getConstantValue("telpGudang2");
            }
            if (!telp2.equals("")) {
                g.drawString("TELP: " + telp2, x, y + (yK += yP));
            }
            g.drawString("Cust: " + CetakNotaExcel.this.customer.getId() + " - " + CetakNotaExcel.this.customer.getNama(), x + 0, y + (yK += yP));
            if (CetakNotaExcel.this.penjualan.getSalesId() != null) {
                g.drawString("Sales: " + CetakNotaExcel.this.sales.getId() + " - " + CetakNotaExcel.this.sales.getNama(), x + 0, y + (yK += yP));
            }
            if (CetakNotaExcel.this.penjualan.getKeterangan().equals("KREDIT")) {
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String tanggal = format.format(CetakNotaExcel.this.penjualan.getJatuhTempo());
                g.drawString("Jatuh Tempo: " + tanggal, x + 0, y + (yK += yP));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Bon. " + CetakNotaExcel.this.penjualan.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + CetakNotaExcel.this.helper.getKaryawan().getNama(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            float totalDiscount = 0.0f;
            long totalBelanja = 0L;
            int size = CetakNotaExcel.this.dataPenjualan.size();
            for (int a = 0; a < size; ++a) {
                Object[] tempData = (Object[])CetakNotaExcel.this.dataPenjualan.get(a);
                yK += yP;
                Barang barang = CetakNotaExcel.this.barangService.getBarang(tempData[0].toString());
                if (tempData[3].toString().equals(barang.getSatuan1()) && barang.getMinPembelian() > 0.0f) {
                    StringBuilder stringBuilder = new StringBuilder();
                    if (Float.parseFloat(stringBuilder.append(tempData[2]).append("").toString()) >= barang.getMinPembelian()) {
                        barang.setNama(barang.getNama() + " (GROSIR)");
                    }
                }
                g.drawString(barang.getNama(), x + 0, y + yK);
                yK = yK + yP - 1;
                float tempValue = Float.parseFloat(tempData[2] + "");
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)Float.parseFloat(tempData[2] + "")) : (Number)Float.valueOf(Float.parseFloat(tempData[2] + ""));
                g.drawString(value + tempData[3].toString(), x + 20, y + yK);
                g.drawString("x", x + 60, y + yK);
                g.drawString(CetakNotaExcel.this.helper.formatNumber(tempData[4] + ""), x + 70, y + yK);
                g.drawString("=", x + 110, y + yK);
                int xtemp = CetakNotaExcel.this.rataKanan(CetakNotaExcel.this.helper.formatNumber((long)(Float.parseFloat(tempData[2] + "") * (float)Long.parseLong(tempData[4] + "")) + ""), xRataKanan);
                g.drawString(CetakNotaExcel.this.helper.formatNumber((long)(Float.parseFloat(tempData[2] + "") * (float)Long.parseLong(tempData[4] + "")) + ""), x + xtemp, y + yK);
                if (Long.parseLong(tempData[6] + "") > 0L) {
                    g.drawString("*Disc ( -" + Long.parseLong(tempData[6] + "") + ")", x + 0, y + (yK += yP));
                }
                totalDiscount += (float)Long.parseLong(tempData[6] + "");
                totalBelanja += (long)(Float.parseFloat(tempData[2] + "") * (float)Long.parseLong(tempData[4] + ""));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Total item: " + CetakNotaExcel.this.dataPenjualan.size(), x + 0, y + (yK += yP));
            int xtemp = CetakNotaExcel.this.rataKanan(CetakNotaExcel.this.helper.formatNumber(totalBelanja + ""), xRataKanan);
            g.drawString(CetakNotaExcel.this.helper.formatNumber(totalBelanja + ""), x + xtemp, y + yK);
            yK += yP;
            if (totalDiscount > 0.0f) {
                g.drawString("Hemat ", x + 0, y + yK);
                xtemp = CetakNotaExcel.this.rataKanan(CetakNotaExcel.this.helper.formatNumber("-" + (long)totalDiscount + ""), xRataKanan);
                g.drawString(CetakNotaExcel.this.helper.formatNumber("-" + (long)totalDiscount + "") + "", x + xtemp, y + yK);
                xtemp = CetakNotaExcel.this.rataKanan(CetakNotaExcel.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), xRataKanan);
                g.drawString(CetakNotaExcel.this.helper.formatNumber((long)((float)totalBelanja - totalDiscount) + ""), x + xtemp, y + (yK += yP));
                yK += yP;
            }
            g.drawString("Pembulatan", x + 0, y + yK);
            xtemp = CetakNotaExcel.this.rataKanan(CetakNotaExcel.this.helper.formatNumber((long)Float.parseFloat(CetakNotaExcel.this.totalField.getValue() + "") + ""), xRataKanan);
            g.drawString(CetakNotaExcel.this.helper.formatNumber((long)Float.parseFloat(CetakNotaExcel.this.totalField.getValue() + "") + ""), x + xtemp, y + yK);
            g.drawString("Tunai", x + 0, y + (yK += yP));
            xtemp = CetakNotaExcel.this.rataKanan(CetakNotaExcel.this.helper.formatNumber(CetakNotaExcel.this.tunaiField.getValue() + ""), xRataKanan);
            g.drawString(CetakNotaExcel.this.helper.formatNumber(CetakNotaExcel.this.tunaiField.getValue() + ""), x + xtemp, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("Kembalian", x + 0, y + (yK += yP));
            xtemp = CetakNotaExcel.this.rataKanan(CetakNotaExcel.this.helper.formatNumber(Math.round(((Float)CetakNotaExcel.this.kembaliField.getValue()).floatValue()) + ""), xRataKanan);
            g.drawString(CetakNotaExcel.this.helper.formatNumber(Math.round(((Float)CetakNotaExcel.this.kembaliField.getValue()).floatValue()) + ""), x + xtemp, y + yK);
            if (!(CetakNotaExcel.this.customer.getId().equalsIgnoreCase("C") || CetakNotaExcel.this.customer.getId().equalsIgnoreCase("SINAR") || CetakNotaExcel.this.customer.getId().equalsIgnoreCase("LUKAS"))) {
                if (CetakNotaExcel.this.customer.getHutang() > 0L) {
                    g.drawString("Sisa Hutang", x + 0, y + (yK += yP));
                    xtemp = CetakNotaExcel.this.rataKanan(CetakNotaExcel.this.helper.formatNumber(CetakNotaExcel.this.customer.getHutang() + ""), xRataKanan);
                    g.drawString(CetakNotaExcel.this.helper.formatNumber(CetakNotaExcel.this.customer.getHutang() + ""), x + xtemp, y + yK);
                }
                yK += yP;
                String aktifkanPoint = CetakNotaExcel.this.konstantaService.getConstantValue("aktifkanPoint");
                if (aktifkanPoint.equals("YES")) {
                    g.drawString("Point Belanja", x + 0, y + yK);
                    g.drawString("(+" + (long)CetakNotaExcel.this.totalPoint + ")", x + 120, y + yK);
                    xtemp = CetakNotaExcel.this.rataKanan(CetakNotaExcel.this.helper.formatNumber(CetakNotaExcel.this.customer.getPoint() + ""), xRataKanan);
                    g.drawString(CetakNotaExcel.this.helper.formatNumber(CetakNotaExcel.this.customer.getPoint() + ""), x + xtemp, y + yK);
                }
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(CetakNotaExcel.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += yP));
            g.drawString("Jam " + CetakNotaExcel.this.penjualan.getTanggal().getHours() + ":" + CetakNotaExcel.this.penjualan.getTanggal().getMinutes() + ":" + CetakNotaExcel.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("TERIMA KASIH", x + 57, y + (yK += yP));
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += yP));
            yK += yP;
            String aktifkanVoucher = CetakNotaExcel.this.konstantaService.getConstantValue("aktifkanVoucher");
            if (aktifkanVoucher.equals("YES")) {
                int minimumPembelian = Integer.parseInt(CetakNotaExcel.this.konstantaService.getConstantValue("minimumPembelian"));
                long total = (Long)CetakNotaExcel.this.totalField.getValue();
                long jumlahVoucher = (total - CetakNotaExcel.this.totalRokok) / (long)minimumPembelian;
                int b = 0;
                while ((long)b < jumlahVoucher) {
                    String ketString = CetakNotaExcel.this.konstantaService.getConstantValue("keteranganVoucher");
                    String[] ketSplit = ketString.split("\n");
                    for (int a = 0; a < ketSplit.length; ++a) {
                        String tes = ketSplit[a];
                        int xPlus = 0;
                        if (tes.equals("%%%")) {
                            yK += 17;
                            g.setFont(new Font("Times New Roman", 0, 30));
                            tes = CetakNotaExcel.this.helper.formatNumber(CetakNotaExcel.this.konstantaService.getConstantValue("voucherBelanja"));
                            xPlus = 30;
                        }
                        g.drawString(tes, x + xPlus, y + yK);
                        g.setFont(new Font("Times New Roman", 0, 8));
                        yK += yP;
                    }
                    g.drawString("Tgl " + tanggal, x + 0, y + yK);
                    g.drawString("Jam " + CetakNotaExcel.this.penjualan.getTanggal().getHours() + ":" + CetakNotaExcel.this.penjualan.getTanggal().getMinutes() + ":" + CetakNotaExcel.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
                    g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += yP));
                    yK += yP;
                    ++b;
                }
            }
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
            g.drawString("Permintaan Barang - " + CetakNotaExcel.this.penjualan.getId(), x + 0, y + yK);
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            int size = CetakNotaExcel.this.dataPenjualan.size();
            for (int a = 0; a < size; ++a) {
                Object[] tempData = (Object[])CetakNotaExcel.this.dataPenjualan.get(a);
                Barang barang = CetakNotaExcel.this.barangService.getBarang(tempData[0] + "");
                g.drawString(barang.getNama() + " (" + barang.getRakIdToko() + ")", x + 0, y + yK);
                float tempValue = Float.parseFloat(tempData[2] + "");
                Number value = tempValue == (float)((long)tempValue) ? (Number)((long)Float.parseFloat(tempData[2] + "")) : (Number)Float.valueOf(Float.parseFloat(tempData[2] + ""));
                g.drawString(value + tempData[3].toString() + " / " + barang.getStokToko() + barang.getSatuan1(), x + 20, y + (yK += yP));
                yK += yP;
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(CetakNotaExcel.this.penjualan.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + yK);
            g.drawString("Jam " + CetakNotaExcel.this.penjualan.getTanggal().getHours() + ":" + CetakNotaExcel.this.penjualan.getTanggal().getMinutes() + ":" + CetakNotaExcel.this.penjualan.getTanggal().getSeconds(), x + 120, y + yK);
            return 0;
        }
    }
}

