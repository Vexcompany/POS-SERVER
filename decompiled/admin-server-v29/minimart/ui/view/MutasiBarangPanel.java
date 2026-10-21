/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package minimart.ui.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Karyawan;
import minimart.engine.model.MutasiBarang;
import minimart.engine.model.MutasiBarangDetail;
import minimart.engine.model.StockCommit;
import minimart.engine.service.BarangService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.MutasiBarangDetailService;
import minimart.engine.service.MutasiBarangService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.MutasiBarangDialogListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.view.MutasiBarangDialog;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.StokOpnamPanel;
import minimart.ui.view.report.ReportPoinPenjualanCustomer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MutasiBarangPanel
extends JPanel {
    private JComboBox dariField;
    private JDateChooser dateField;
    private JButton detailButton;
    private JButton exitButton;
    private JTextField idField;
    private JButton importBtn;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JSeparator jSeparator3;
    private JLabel karyawanField;
    private JTextField keteranganField;
    private JComboBox menujuField;
    private JTable mutasiBarangTable;
    private JButton saveButton;
    private JTextField totalBarangField;
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat"};
    private DefaultTableModel mutasiTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultPanelListener listener;
    private Helper helper = Helper.getInstance();
    private MutasiBarangService mutasiBarangService = new MutasiBarangService();
    private BarangService barangService = new BarangService();
    private MutasiBarang mutasiBarang = new MutasiBarang();
    private MutasiBarangDetailService mutasiBarangDetailService = new MutasiBarangDetailService();
    private KaryawanService karyawanService = new KaryawanService();
    private int screenHeight;
    private int screenWidth;
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();

    public MutasiBarangPanel() {
        this.initComponents();
        this.resetData();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.menujuField.setEnabled(false);
    }

    public void addListener(DefaultPanelListener mutasiBarangPanelListener) {
        this.listener = mutasiBarangPanelListener;
    }

    public void escapeClick() {
        this.idField.requestFocus();
        this.idField.selectAll();
    }

    private void saveClick() {
        MutasiBarang checkData = this.mutasiBarangService.getMutasiBarang(this.idField.getText());
        if (checkData == null) {
            this.mutasiBarang.setId(this.helper.generateCodeGeneral(this.mutasiBarangService.getLastId()));
            this.mutasiBarang.setKaryawanId(this.helper.getKaryawan().getId());
            this.mutasiBarang.setTanggal(this.dateField.getDate());
            this.mutasiBarang.setDari(this.dariField.getSelectedItem().toString());
            this.mutasiBarang.setMenuju(this.menujuField.getSelectedItem().toString());
            this.mutasiBarang.setKeterangan(this.keteranganField.getText());
            this.mutasiBarang.setCreated(new Date());
            this.mutasiBarang.setUpdated(new Date());
            int mutasiBarangStatus = this.mutasiBarangService.createMutasiBarang(this.mutasiBarang);
            int mutasiBarangDetailStatus = 0;
            int barangStatus = 0;
            if (mutasiBarangStatus == 1) {
                for (int a = 0; a < this.mutasiTableModel.getRowCount(); ++a) {
                    MutasiBarangDetail mutasiBarangDetail = new MutasiBarangDetail();
                    mutasiBarangDetail.setMutasiBarangId(this.mutasiBarang.getId());
                    mutasiBarangDetail.setBarangId(this.mutasiTableModel.getValueAt(a, 0) + "");
                    mutasiBarangDetail.setJumlahBarang(Integer.parseInt(this.mutasiTableModel.getValueAt(a, 2) + ""));
                    mutasiBarangDetail.setSatuan(this.mutasiTableModel.getValueAt(a, 3) + "");
                    mutasiBarangDetail.setCreated(new Date());
                    mutasiBarangDetail.setUpdated(new Date());
                    mutasiBarangDetailStatus = this.mutasiBarangDetailService.createMutasiBarangDetail(mutasiBarangDetail);
                    if (mutasiBarangDetailStatus != 1) continue;
                    Barang tempBarang = this.barangService.getBarang(this.mutasiTableModel.getValueAt(a, 0) + "");
                    int jumlahBarang = 0;
                    if (mutasiBarangDetail.getSatuan().equals(tempBarang.getSatuan1())) {
                        jumlahBarang = mutasiBarangDetail.getJumlahBarang();
                    } else if (mutasiBarangDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                        jumlahBarang = mutasiBarangDetail.getJumlahBarang() * tempBarang.getKonversi21();
                    } else if (mutasiBarangDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                        jumlahBarang = mutasiBarangDetail.getJumlahBarang() * tempBarang.getKonversi21() * tempBarang.getKonversi32();
                    }
                    StockCommit stockCommit1 = new StockCommit();
                    StockCommit stockCommit2 = new StockCommit();
                    if (this.mutasiBarang.getDari().equals("GUDANG")) {
                        tempBarang.setStokGudang(tempBarang.getStokGudang() - (float)jumlahBarang);
                        stockCommit1.setIdBarang(tempBarang.getId());
                        stockCommit1.setIdTransaksi(this.mutasiBarang.getId());
                        stockCommit1.setQueryType("kurang");
                        stockCommit1.setStockType("gudang");
                        stockCommit1.setTransactionType("Mutasi");
                        stockCommit1.setJumlahBarang(jumlahBarang);
                    } else if (this.mutasiBarang.getDari().equals("TOKO")) {
                        tempBarang.setStokToko(tempBarang.getStokToko() - (float)jumlahBarang);
                        stockCommit1.setIdBarang(tempBarang.getId());
                        stockCommit1.setIdTransaksi(this.mutasiBarang.getId());
                        stockCommit1.setQueryType("kurang");
                        stockCommit1.setStockType("toko");
                        stockCommit1.setTransactionType("Mutasi");
                        stockCommit1.setJumlahBarang(jumlahBarang);
                    }
                    if (this.mutasiBarang.getMenuju().equals("GUDANG")) {
                        tempBarang.setStokGudang(tempBarang.getStokGudang() + (float)jumlahBarang);
                        stockCommit2.setIdBarang(tempBarang.getId());
                        stockCommit2.setIdTransaksi(this.mutasiBarang.getId());
                        stockCommit2.setQueryType("tambah");
                        stockCommit2.setStockType("gudang");
                        stockCommit2.setTransactionType("Mutasi");
                        stockCommit2.setJumlahBarang(jumlahBarang);
                    } else if (this.mutasiBarang.getMenuju().equals("TOKO")) {
                        tempBarang.setStokToko(tempBarang.getStokToko() + (float)jumlahBarang);
                        stockCommit2.setIdBarang(tempBarang.getId());
                        stockCommit2.setIdTransaksi(this.mutasiBarang.getId());
                        stockCommit2.setQueryType("tambah");
                        stockCommit2.setStockType("toko");
                        stockCommit2.setTransactionType("Mutasi");
                        stockCommit2.setJumlahBarang(jumlahBarang);
                    }
                    this.sinkronisasiBarangService.insertCommit(stockCommit1);
                    this.sinkronisasiBarangService.insertCommit(stockCommit2);
                    barangStatus = this.barangService.updateBarang(tempBarang);
                }
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
            catch (PrinterException stockCommit1) {
                // empty catch block
            }
            if (mutasiBarangDetailStatus == 1 && mutasiBarangStatus == 1 && barangStatus == 1) {
                JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
                this.resetData();
                this.escapeClick();
                this.idField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            }
        } else if (checkData != null) {
            this.mutasiBarang.setKeterangan(this.keteranganField.getText());
            this.mutasiBarang.setUpdated(new Date());
            this.mutasiBarangService.updateMutasiBarang(this.mutasiBarang);
            List<MutasiBarangDetail> mutasiBarangDetails = this.mutasiBarangDetailService.getMutasiBarangDetails(this.mutasiBarang.getId());
            for (MutasiBarangDetail mutasiBarangDetail : mutasiBarangDetails) {
                Barang tempBarang = this.barangService.getBarang(mutasiBarangDetail.getBarangId());
                int jumlahBarang = 0;
                if (mutasiBarangDetail.getSatuan().equals(tempBarang.getSatuan1())) {
                    jumlahBarang = mutasiBarangDetail.getJumlahBarang();
                } else if (mutasiBarangDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                    jumlahBarang = mutasiBarangDetail.getJumlahBarang() * tempBarang.getKonversi21();
                } else if (mutasiBarangDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                    jumlahBarang = mutasiBarangDetail.getJumlahBarang() * tempBarang.getKonversi21() * tempBarang.getKonversi32();
                }
                StockCommit stockCommit1 = new StockCommit();
                StockCommit stockCommit2 = new StockCommit();
                if (this.mutasiBarang.getDari().equals("GUDANG")) {
                    stockCommit1.setIdBarang(tempBarang.getId());
                    stockCommit1.setIdTransaksi(this.mutasiBarang.getId());
                    stockCommit1.setQueryType("tambah");
                    stockCommit1.setStockType("gudang");
                    stockCommit1.setTransactionType("Mutasi");
                    stockCommit1.setJumlahBarang(jumlahBarang);
                    this.sinkronisasiBarangService.insertCommit(stockCommit1);
                    tempBarang.setStokGudang(tempBarang.getStokGudang() + (float)jumlahBarang);
                } else if (this.mutasiBarang.getDari().equals("TOKO")) {
                    stockCommit1.setIdBarang(tempBarang.getId());
                    stockCommit1.setIdTransaksi(this.mutasiBarang.getId());
                    stockCommit1.setQueryType("tambah");
                    stockCommit1.setStockType("toko");
                    stockCommit1.setTransactionType("Mutasi");
                    stockCommit1.setJumlahBarang(jumlahBarang);
                    this.sinkronisasiBarangService.insertCommit(stockCommit1);
                    tempBarang.setStokToko(tempBarang.getStokToko() + (float)jumlahBarang);
                }
                if (this.mutasiBarang.getMenuju().equals("GUDANG")) {
                    stockCommit2.setIdBarang(tempBarang.getId());
                    stockCommit2.setIdTransaksi(this.mutasiBarang.getId());
                    stockCommit2.setQueryType("kurang");
                    stockCommit2.setStockType("gudang");
                    stockCommit2.setTransactionType("Mutasi");
                    stockCommit2.setJumlahBarang(jumlahBarang);
                    this.sinkronisasiBarangService.insertCommit(stockCommit2);
                    tempBarang.setStokGudang(tempBarang.getStokGudang() - (float)jumlahBarang);
                } else if (this.mutasiBarang.getMenuju().equals("TOKO")) {
                    stockCommit2.setIdBarang(tempBarang.getId());
                    stockCommit2.setIdTransaksi(this.mutasiBarang.getId());
                    stockCommit2.setQueryType("kurang");
                    stockCommit2.setStockType("toko");
                    stockCommit2.setTransactionType("Mutasi");
                    stockCommit2.setJumlahBarang(jumlahBarang);
                    this.sinkronisasiBarangService.insertCommit(stockCommit2);
                    tempBarang.setStokToko(tempBarang.getStokToko() - (float)jumlahBarang);
                }
                this.barangService.updateBarang(tempBarang);
                this.mutasiBarangDetailService.deleteMutasiBarangDetail(mutasiBarangDetail.getId());
            }
            int mutasiBarangDetailStatus = 0;
            int updateBarangStatus = 0;
            for (int a = 0; a < this.mutasiTableModel.getRowCount(); ++a) {
                MutasiBarangDetail mutasiBarangDetail = new MutasiBarangDetail();
                mutasiBarangDetail.setMutasiBarangId(this.mutasiBarang.getId());
                mutasiBarangDetail.setBarangId(this.mutasiTableModel.getValueAt(a, 0) + "");
                mutasiBarangDetail.setJumlahBarang(Integer.parseInt(this.mutasiTableModel.getValueAt(a, 2) + ""));
                mutasiBarangDetail.setSatuan(this.mutasiTableModel.getValueAt(a, 3) + "");
                mutasiBarangDetail.setCreated(new Date());
                mutasiBarangDetail.setUpdated(new Date());
                mutasiBarangDetailStatus = this.mutasiBarangDetailService.createMutasiBarangDetail(mutasiBarangDetail);
                if (mutasiBarangDetailStatus != 1) continue;
                Barang tempBarang = this.barangService.getBarang(this.mutasiTableModel.getValueAt(a, 0) + "");
                int jumlahBarang = 0;
                if (mutasiBarangDetail.getSatuan().equals(tempBarang.getSatuan1())) {
                    jumlahBarang = mutasiBarangDetail.getJumlahBarang();
                } else if (mutasiBarangDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                    jumlahBarang = mutasiBarangDetail.getJumlahBarang() * tempBarang.getKonversi21();
                } else if (mutasiBarangDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                    jumlahBarang = mutasiBarangDetail.getJumlahBarang() * tempBarang.getKonversi21() * tempBarang.getKonversi32();
                }
                StockCommit stockCommit1 = new StockCommit();
                if (this.mutasiBarang.getDari().equals("GUDANG")) {
                    tempBarang.setStokGudang(tempBarang.getStokGudang() - (float)jumlahBarang);
                    stockCommit1.setIdBarang(tempBarang.getId());
                    stockCommit1.setIdTransaksi(this.mutasiBarang.getId());
                    stockCommit1.setQueryType("kurang");
                    stockCommit1.setStockType("gudang");
                    stockCommit1.setTransactionType("Mutasi");
                    stockCommit1.setJumlahBarang(jumlahBarang);
                } else if (this.mutasiBarang.getDari().equals("TOKO")) {
                    tempBarang.setStokToko(tempBarang.getStokToko() - (float)jumlahBarang);
                    stockCommit1.setIdBarang(tempBarang.getId());
                    stockCommit1.setIdTransaksi(this.mutasiBarang.getId());
                    stockCommit1.setQueryType("kurang");
                    stockCommit1.setStockType("toko");
                    stockCommit1.setTransactionType("Mutasi");
                    stockCommit1.setJumlahBarang(jumlahBarang);
                }
                StockCommit stockCommit2 = new StockCommit();
                if (this.mutasiBarang.getMenuju().equals("GUDANG")) {
                    tempBarang.setStokGudang(tempBarang.getStokGudang() + (float)jumlahBarang);
                    stockCommit2.setIdBarang(tempBarang.getId());
                    stockCommit2.setIdTransaksi(this.mutasiBarang.getId());
                    stockCommit2.setQueryType("tambah");
                    stockCommit2.setStockType("gudang");
                    stockCommit2.setTransactionType("Mutasi");
                    stockCommit2.setJumlahBarang(jumlahBarang);
                } else if (this.mutasiBarang.getMenuju().equals("TOKO")) {
                    tempBarang.setStokToko(tempBarang.getStokToko() + (float)jumlahBarang);
                    stockCommit2.setIdBarang(tempBarang.getId());
                    stockCommit2.setIdTransaksi(this.mutasiBarang.getId());
                    stockCommit2.setQueryType("tambah");
                    stockCommit2.setStockType("toko");
                    stockCommit2.setTransactionType("Mutasi");
                    stockCommit2.setJumlahBarang(jumlahBarang);
                }
                this.sinkronisasiBarangService.insertCommit(stockCommit1);
                this.sinkronisasiBarangService.insertCommit(stockCommit2);
                updateBarangStatus = this.barangService.updateBarang(tempBarang);
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
            if (updateBarangStatus == 1 && mutasiBarangDetailStatus == 1) {
                JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
                this.resetData();
                this.escapeClick();
                this.idField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            }
        }
    }

    private void resetData() {
        this.dateField.setDate(new Date());
        int countRow = this.mutasiTableModel.getRowCount();
        for (int a = 0; a < countRow; ++a) {
            this.mutasiTableModel.removeRow(0);
        }
        this.karyawanField.setText(this.helper.getKaryawan().getNama());
        this.dariField.setSelectedIndex(0);
        this.menujuField.setSelectedIndex(1);
        this.keteranganField.setText("");
        this.totalBarangField.setText("0");
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.idField = new JTextField();
        this.dateField = new JDateChooser();
        this.jLabel3 = new JLabel();
        this.jSeparator1 = new JSeparator();
        this.karyawanField = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.dariField = new JComboBox();
        this.menujuField = new JComboBox();
        this.jSeparator3 = new JSeparator();
        this.jLabel7 = new JLabel();
        this.keteranganField = new JTextField();
        this.jScrollPane1 = new JScrollPane();
        this.mutasiBarangTable = new JTable();
        this.detailButton = new JButton();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.jLabel8 = new JLabel();
        this.totalBarangField = new JTextField();
        this.importBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("No.Mutasi");
        this.jLabel2.setText("Tanggal");
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangPanel.this.idFieldKeyPressed(evt);
            }
        });
        this.jLabel3.setText("Pembuat Mutasi:");
        this.jSeparator1.setOrientation(1);
        this.karyawanField.setFont(new Font("Tahoma", 1, 12));
        this.jLabel5.setText("Dari");
        this.jLabel6.setText("Menuju");
        this.dariField.setModel(new DefaultComboBoxModel<String>(new String[]{"GUDANG", "TOKO"}));
        this.dariField.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                MutasiBarangPanel.this.dariFieldItemStateChanged(evt);
            }
        });
        this.dariField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangPanel.this.dariFieldKeyPressed(evt);
            }
        });
        this.menujuField.setModel(new DefaultComboBoxModel<String>(new String[]{"GUDANG", "TOKO"}));
        this.menujuField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangPanel.this.menujuFieldKeyPressed(evt);
            }
        });
        this.jSeparator3.setOrientation(1);
        this.jLabel7.setText("Keterangan");
        this.keteranganField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangPanel.this.keteranganFieldKeyPressed(evt);
            }
        });
        this.mutasiBarangTable.setModel(this.mutasiTableModel);
        this.mutasiBarangTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangPanel.this.mutasiBarangTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.mutasiBarangTable);
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(73, 23));
        this.detailButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MutasiBarangPanel.this.detailButtonActionPerformed(evt);
            }
        });
        this.detailButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangPanel.this.detailButtonKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MutasiBarangPanel.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangPanel.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MutasiBarangPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangPanel.this.exitButtonKeyPressed(evt);
            }
        });
        this.jLabel8.setText("Total Barang");
        this.totalBarangField.setBackground(new Color(153, 255, 204));
        this.totalBarangField.setText("0");
        this.importBtn.setText("Import Excel");
        this.importBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MutasiBarangPanel.this.importBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel8)).addGap(23, 23, 23).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.idField).addComponent((Component)this.dateField, -1, 129, Short.MAX_VALUE).addComponent(this.totalBarangField)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.detailButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.importBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE).addComponent(this.jLabel3).addGap(5, 5, 5).addComponent(this.karyawanField, -2, 113, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jSeparator1, -2, 2, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5).addComponent(this.jLabel6)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.dariField, 0, 113, Short.MAX_VALUE).addComponent(this.menujuField, 0, -1, Short.MAX_VALUE)).addGap(32, 32, 32).addComponent(this.jSeparator3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel7).addGap(0, 0, Short.MAX_VALUE)).addComponent(this.keteranganField)))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent((Component)this.dateField, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel2, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.idField, -2, -1, -2).addComponent(this.jLabel1))).addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.dariField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.menujuField, -2, -1, -2))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel7).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keteranganField, -2, -1, -2).addGap(12, 12, 12)))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalBarangField, -2, -1, -2).addComponent(this.jLabel8)).addGap(0, 0, Short.MAX_VALUE)).addComponent(this.jSeparator1).addComponent(this.jSeparator3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailButton, -2, -1, -2).addComponent(this.saveButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2).addComponent(this.importBtn)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.karyawanField, -2, 19, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -2, 464, -2).addContainerGap()));
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.mutasiBarang = this.mutasiBarangService.getMutasiBarang(this.idField.getText());
            if (this.idField.getText().equals("") || this.mutasiBarang == null) {
                this.dariField.setEnabled(true);
                this.resetData();
                this.mutasiBarang = new MutasiBarang();
                this.idField.setText(Helper.getInstance().generateCodeGeneral(this.mutasiBarangService.getLastId()));
                this.dariField.requestFocus();
            } else {
                this.dariField.setEnabled(false);
                this.resetData();
                List<MutasiBarangDetail> mutasiBarangDetails = this.mutasiBarangDetailService.getMutasiBarangDetails(this.mutasiBarang.getId());
                Karyawan karyawanData = this.karyawanService.getKaryawan(this.mutasiBarang.getKaryawanId());
                this.karyawanField.setText(karyawanData.getNama());
                int totalBarang = 0;
                for (MutasiBarangDetail mutasiBarangDetail : mutasiBarangDetails) {
                    Barang dataBarang = this.barangService.getBarang(mutasiBarangDetail.getBarangId());
                    Object[] rowValue = new Object[8];
                    rowValue[0] = dataBarang.getId();
                    rowValue[1] = dataBarang.getNama();
                    rowValue[2] = mutasiBarangDetail.getJumlahBarang();
                    rowValue[3] = mutasiBarangDetail.getSatuan();
                    totalBarang += mutasiBarangDetail.getJumlahBarang();
                    this.mutasiTableModel.addRow(rowValue);
                }
                this.dariField.setSelectedItem(this.mutasiBarang.getDari());
                this.menujuField.setSelectedItem(this.mutasiBarang.getMenuju());
                this.keteranganField.setText(this.mutasiBarang.getKeterangan());
                this.totalBarangField.setText(totalBarang + "");
                this.detailButton.requestFocus();
            }
        } else if (evt.getKeyCode() == 27) {
            this.listener.exitClick();
        }
    }

    private void dariFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.keteranganField.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void menujuFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.keteranganField.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void keteranganFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.dariField.requestFocus();
        }
    }

    private void detailButtonClick() {
        this.mutasiBarangTable.requestFocus();
        if (this.mutasiTableModel.getRowCount() == 0) {
            MutasiBarangDialog mutasiBarangDialog = new MutasiBarangDialog(null, true, this.mutasiTableModel, this.dariField.getSelectedItem() + "");
            mutasiBarangDialog.addListener(new mutasiBarangDialogEventHandler());
            int w = mutasiBarangDialog.getWidth();
            int h = mutasiBarangDialog.getHeight();
            mutasiBarangDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            mutasiBarangDialog.setVisible(true);
        }
    }

    private void detailButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailButtonClick();
        } else if (evt.getKeyCode() == 39) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.keteranganField.requestFocus();
            this.keteranganField.selectAll();
        }
    }

    private void mutasiBarangTableKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 10) {
            MutasiBarangDialog mutasiBarangDialog = new MutasiBarangDialog(null, true, this.mutasiTableModel, this.dariField.getSelectedItem() + "");
            if (this.mutasiBarangTable.getSelectedRow() != -1) {
                mutasiBarangDialog.loadKodeBarang(this.mutasiTableModel.getValueAt(this.mutasiBarangTable.getSelectedRow(), 0).toString());
                mutasiBarangDialog.loadDataFromTable(this.mutasiBarangTable.getSelectedRow());
            }
            mutasiBarangDialog.escapeClick();
            mutasiBarangDialog.addListener(new mutasiBarangDialogEventHandler());
            int w = mutasiBarangDialog.getWidth();
            int h = mutasiBarangDialog.getHeight();
            mutasiBarangDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            mutasiBarangDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a2 = JOptionPane.showConfirmDialog(null, "Akhiri Entry Data?", "Akhiri Entry Data", 0, 3);
            if (a2 == 0) {
                int total = 0;
                for (int x = 0; x < this.mutasiTableModel.getRowCount(); ++x) {
                    total += Integer.parseInt(this.mutasiTableModel.getValueAt(x, 2).toString());
                }
                this.saveButton.requestFocus();
                this.totalBarangField.setText(total + "");
            }
        } else if (evt.getKeyCode() == 127 && this.mutasiBarangTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
            this.mutasiTableModel.removeRow(this.mutasiBarangTable.getSelectedRow());
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
            passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
            int w = passwordInputDialog2.getWidth();
            int h = passwordInputDialog2.getHeight();
            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            passwordInputDialog2.setJudulText("Input Password Untuk Melakukan Penyimpanan");
            passwordInputDialog2.setVisible(true);
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.listener.exitClick();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void detailButtonActionPerformed(ActionEvent evt) {
        this.detailButtonClick();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
        passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
        int w = passwordInputDialog2.getWidth();
        int h = passwordInputDialog2.getHeight();
        passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        passwordInputDialog2.setJudulText("Input Password Untuk Melakukan Penyimpanan");
        passwordInputDialog2.setVisible(true);
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void dariFieldItemStateChanged(ItemEvent evt) {
        if (evt.getItem().equals("TOKO")) {
            this.menujuField.setSelectedItem("GUDANG");
        } else if (evt.getItem().equals("GUDANG")) {
            this.menujuField.setSelectedItem("TOKO");
        }
    }

    private void importBtnActionPerformed(ActionEvent evt) {
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        File file = jfc.getSelectedFile();
        try {
            this.clearTable();
            FileInputStream fir = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fir);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rowIt = sheet.iterator();
            rowIt.next();
            while (rowIt.hasNext()) {
                Row row = (Row)rowIt.next();
                Iterator cellIterator = row.cellIterator();
                int a = 0;
                Object[] rowValue = new Object[10];
                while (cellIterator.hasNext()) {
                    Cell cell = (Cell)cellIterator.next();
                    cell.setCellType(CellType.STRING);
                    rowValue[a] = cell.getStringCellValue();
                    ++a;
                }
                this.mutasiTableModel.addRow(rowValue);
            }
            workbook.close();
            fir.close();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(StokOpnamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(StokOpnamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearTable() {
        int size = this.mutasiTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.mutasiTableModel.removeRow(0);
        }
    }

    public void setFocus() {
        this.idField.requestFocus();
    }

    private void createExcel() {
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            CreationHelper createhelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");
            Row row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("ID Barang");
            cell = row.createCell(1);
            cell.setCellValue("Nama Barang");
            cell = row.createCell(2);
            cell.setCellValue("Qty");
            cell = row.createCell(3);
            cell.setCellValue("Satuan");
            for (int i = 0; i < this.mutasiTableModel.getRowCount(); ++i) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < this.mutasiTableModel.getColumnCount(); ++j) {
                    cell = row.createCell(j);
                    cell.setCellValue(this.mutasiTableModel.getValueAt(i, j) + "");
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDateString = simpleDateFormat.format(new Date());
            String url = "docMutasi/" + nowDateString + "Mutasi.xls";
            FileOutputStream out = new FileOutputStream(url);
            wb.write((OutputStream)out);
            out.close();
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan Pada " + url);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(ReportPoinPenjualanCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(ReportPoinPenjualanCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class mutasiBarangDialogEventHandler
    implements MutasiBarangDialogListener {
        private mutasiBarangDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            boolean newData = true;
            for (a = 0; a < MutasiBarangPanel.this.mutasiTableModel.getRowCount(); ++a) {
                if (!MutasiBarangPanel.this.mutasiTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                MutasiBarangPanel.this.mutasiTableModel.addRow(rowValue);
            } else {
                MutasiBarangPanel.this.mutasiTableModel.removeRow(a);
                MutasiBarangPanel.this.mutasiTableModel.addRow(rowValue);
            }
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("INVENTORY") || karyawan.getRole().equalsIgnoreCase("STAF INVENTORY")) {
                MutasiBarangPanel.this.saveClick();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
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
            g.drawString("Mutasi Barang - " + MutasiBarangPanel.this.mutasiBarang.getId(), x + 0, y + yK);
            g.drawString(MutasiBarangPanel.this.dariField.getSelectedItem() + " - " + MutasiBarangPanel.this.menujuField.getSelectedItem(), x + 0, y + (yK += yP));
            g.drawString(MutasiBarangPanel.this.keteranganField.getText(), x + 0, y + (yK += yP));
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (int a = 0; a < MutasiBarangPanel.this.mutasiTableModel.getRowCount(); ++a) {
                Barang barang = MutasiBarangPanel.this.barangService.getBarang(MutasiBarangPanel.this.mutasiTableModel.getValueAt(a, 0).toString());
                g.drawString(barang.getNama(), x + 0, y + yK);
                float tempValue = Float.parseFloat(MutasiBarangPanel.this.mutasiTableModel.getValueAt(a, 2) + "");
                Object value = tempValue == (float)((long)tempValue) ? Long.valueOf(Long.parseLong(MutasiBarangPanel.this.mutasiTableModel.getValueAt(a, 2) + "")) : MutasiBarangPanel.this.mutasiTableModel.getValueAt(a, 2);
                g.drawString(value + MutasiBarangPanel.this.mutasiTableModel.getValueAt(a, 3).toString() + " / (T) " + barang.getStokToko() + barang.getSatuan1() + " / (G) " + barang.getStokGudang() + barang.getSatuan1(), x + 10, y + (yK += yP));
                yK += yP;
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(MutasiBarangPanel.this.mutasiBarang.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + yK);
            return 0;
        }
    }
}

