/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.PengembalianBs;
import minimart.engine.model.PengembalianBsDetail;
import minimart.engine.model.StockCommit;
import minimart.engine.model.Supplier;
import minimart.engine.service.BarangService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.PengembalianBsDetailService;
import minimart.engine.service.PengembalianBsService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PengembalianBsDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.PengembalianBsDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PengembalianBsPanel
extends JPanel {
    private JDateChooser dateField;
    private JButton detailButton;
    private JButton exitButton;
    private JTextField idField;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JScrollPane jScrollPane1;
    private JTextField jumlahBarang;
    private JTable pengembalianBsTable;
    private JComboBox pengembalianCmb;
    private JButton saveButton;
    private JTextField supplierField;
    private JLabel supplierLbl;
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat"};
    private DefaultTableModel pengembalianBsTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn);
    private int screenWidth;
    private int screenHeight;
    private PengembalianBsService pengembalianBsService = new PengembalianBsService();
    private PengembalianBsDetailService pengembalianBsDetailService = new PengembalianBsDetailService();
    private KaryawanService karyawanService = new KaryawanService();
    private BarangService barangService = new BarangService();
    private PengembalianBs pengembalianBs = new PengembalianBs();
    private Helper helper = Helper.getInstance();
    private DefaultPanelListener listener;
    private SupplierService supplierService = new SupplierService();
    private Supplier supplier;
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();

    public PengembalianBsPanel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.resetData();
        TableColumn col2 = this.pengembalianBsTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.pengembalianBsTable.getColumnModel().getColumn(3);
        int width = 20;
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
    }

    public void setDefaultFocus() {
        this.idField.requestFocusInWindow();
    }

    private void escapeClick() {
        this.idField.requestFocus();
        this.idField.selectAll();
    }

    private void saveClick() {
        PengembalianBs checkData = this.pengembalianBsService.getPengembalianBs(this.idField.getText());
        if (checkData == null) {
            this.pengembalianBs.setId(this.helper.generateCodeGeneral(this.pengembalianBsService.getLastId()));
            this.pengembalianBs.setKaryawanId(this.helper.getKaryawan().getId());
            this.pengembalianBs.setSupplierId(this.supplier.getId());
            this.pengembalianBs.setKeterangan(this.pengembalianCmb.getSelectedItem().toString());
            this.pengembalianBs.setTanggal(this.dateField.getDate());
            this.pengembalianBs.setCreated(new Date());
            this.pengembalianBs.setUpdated(new Date());
            int pengembalianBsStatus = this.pengembalianBsService.createPengembalianBs(this.pengembalianBs);
            int pengembalianBsDetailStatus = 0;
            int barangStatus = 0;
            if (pengembalianBsStatus == 1) {
                for (int a = 0; a < this.pengembalianBsTableModel.getRowCount(); ++a) {
                    PengembalianBsDetail pengembalianBsDetail = new PengembalianBsDetail();
                    pengembalianBsDetail.setPengembalianBsId(this.pengembalianBs.getId());
                    pengembalianBsDetail.setBarangId(this.pengembalianBsTableModel.getValueAt(a, 0) + "");
                    pengembalianBsDetail.setJumlahBarang(Float.parseFloat(this.pengembalianBsTableModel.getValueAt(a, 2) + ""));
                    pengembalianBsDetail.setSatuan(this.pengembalianBsTableModel.getValueAt(a, 3) + "");
                    pengembalianBsDetail.setCreated(new Date());
                    pengembalianBsDetail.setUpdated(new Date());
                    pengembalianBsDetailStatus = this.pengembalianBsDetailService.createPengembalianBsDetail(pengembalianBsDetail);
                    if (pengembalianBsDetailStatus != 1) continue;
                    StockCommit stockCommit1 = new StockCommit();
                    stockCommit1.setIdBarang(pengembalianBsDetail.getBarangId());
                    stockCommit1.setIdTransaksi(this.pengembalianBs.getId() + "");
                    stockCommit1.setQueryType("kurang");
                    stockCommit1.setStockType("bs");
                    stockCommit1.setTransactionType("Pengembalian BS");
                    stockCommit1.setJumlahBarang(pengembalianBsDetail.getJumlahBarang());
                    this.sinkronisasiBarangService.insertCommit(stockCommit1);
                    barangStatus = this.barangService.updateStokBs(-pengembalianBsDetail.getJumlahBarang(), pengembalianBsDetail.getBarangId());
                    if (!this.pengembalianCmb.getSelectedItem().equals("BARANG(GUDANG)")) continue;
                    StockCommit stockCommit2 = new StockCommit();
                    stockCommit2.setIdBarang(pengembalianBsDetail.getBarangId());
                    stockCommit2.setIdTransaksi(this.pengembalianBs.getId() + "");
                    stockCommit2.setQueryType("tambah");
                    stockCommit2.setStockType("gudang");
                    stockCommit2.setTransactionType("Pengembalian BS");
                    stockCommit2.setJumlahBarang(pengembalianBsDetail.getJumlahBarang());
                    this.sinkronisasiBarangService.insertCommit(stockCommit2);
                    barangStatus = this.barangService.updateStokBarangGudang(pengembalianBsDetail.getJumlahBarang(), pengembalianBsDetail.getBarangId());
                }
            }
            if (pengembalianBsDetailStatus == 1 && pengembalianBsStatus == 1 && barangStatus == 1) {
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                Paper p = pf.getPaper();
                p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                pf.setPaper(p);
                Book book = new Book();
                book.append(new bsPrinter(), pf);
                job.setPageable(book);
                boolean ok = job.printDialog();
                try {
                    job.print();
                }
                catch (PrinterException printerException) {
                    // empty catch block
                }
                this.resetData();
                this.escapeClick();
                this.idField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            }
        } else if (checkData != null) {
            this.pengembalianBs.setKaryawanId(this.helper.getKaryawan().getId());
            this.pengembalianBs.setSupplierId(this.supplier.getId());
            this.pengembalianBs.setKeterangan(this.pengembalianCmb.getSelectedItem().toString());
            this.pengembalianBs.setTanggal(this.dateField.getDate());
            this.pengembalianBs.setUpdated(new Date());
            this.pengembalianBsService.updatePengembalianBs(this.pengembalianBs);
            List<PengembalianBsDetail> pengembalianBsDetails = this.pengembalianBsDetailService.getPengembalianBsDetails(this.pengembalianBs.getId());
            for (PengembalianBsDetail pengembalianBsDetail : pengembalianBsDetails) {
                StockCommit stockCommit1 = new StockCommit();
                stockCommit1.setIdBarang(pengembalianBsDetail.getBarangId());
                stockCommit1.setIdTransaksi(this.pengembalianBs.getId() + "");
                stockCommit1.setQueryType("tambah");
                stockCommit1.setStockType("bs");
                stockCommit1.setTransactionType("Pengembalian BS");
                stockCommit1.setJumlahBarang(pengembalianBsDetail.getJumlahBarang());
                this.sinkronisasiBarangService.insertCommit(stockCommit1);
                this.barangService.updateStokBs(pengembalianBsDetail.getJumlahBarang(), pengembalianBsDetail.getBarangId());
                this.pengembalianBsDetailService.deletePengembalianBsDetail(pengembalianBsDetail);
            }
            int pengembalianBsDetailStatus = 0;
            boolean updateBarangStatus = false;
            for (int a = 0; a < this.pengembalianBsTableModel.getRowCount(); ++a) {
                PengembalianBsDetail pengembalianBsDetail = new PengembalianBsDetail();
                pengembalianBsDetail.setPengembalianBsId(this.pengembalianBs.getId());
                pengembalianBsDetail.setBarangId(this.pengembalianBsTableModel.getValueAt(a, 0) + "");
                pengembalianBsDetail.setJumlahBarang(Float.parseFloat(this.pengembalianBsTableModel.getValueAt(a, 2) + ""));
                pengembalianBsDetail.setSatuan(this.pengembalianBsTableModel.getValueAt(a, 3) + "");
                pengembalianBsDetail.setCreated(new Date());
                pengembalianBsDetail.setUpdated(new Date());
                pengembalianBsDetailStatus = this.pengembalianBsDetailService.createPengembalianBsDetail(pengembalianBsDetail);
                if (pengembalianBsDetailStatus != 1) continue;
                StockCommit stockCommit1 = new StockCommit();
                stockCommit1.setIdBarang(pengembalianBsDetail.getBarangId());
                stockCommit1.setIdTransaksi(this.pengembalianBs.getId() + "");
                stockCommit1.setQueryType("kurang");
                stockCommit1.setStockType("bs");
                stockCommit1.setTransactionType("Pengembalian BS");
                stockCommit1.setJumlahBarang(pengembalianBsDetail.getJumlahBarang());
                this.sinkronisasiBarangService.insertCommit(stockCommit1);
                this.barangService.updateStokBs(-pengembalianBsDetail.getJumlahBarang(), pengembalianBsDetail.getBarangId());
                if (!this.pengembalianCmb.getSelectedItem().equals("BARANG(GUDANG)")) continue;
                StockCommit stockCommit2 = new StockCommit();
                stockCommit2.setIdBarang(pengembalianBsDetail.getBarangId());
                stockCommit2.setIdTransaksi(this.pengembalianBs.getId() + "");
                stockCommit2.setQueryType("tambah");
                stockCommit2.setStockType("gudang");
                stockCommit2.setTransactionType("Pengembalian BS");
                stockCommit2.setJumlahBarang(pengembalianBsDetail.getJumlahBarang());
                this.sinkronisasiBarangService.insertCommit(stockCommit2);
                this.barangService.updateStokBarangGudang(pengembalianBsDetail.getJumlahBarang(), pengembalianBsDetail.getBarangId());
            }
            if (updateBarangStatus && pengembalianBsDetailStatus == 1) {
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                Paper p = pf.getPaper();
                p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                pf.setPaper(p);
                Book book = new Book();
                book.append(new bsPrinter(), pf);
                job.setPageable(book);
                boolean ok = job.printDialog();
                try {
                    job.print();
                }
                catch (PrinterException printerException) {
                    // empty catch block
                }
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
        int countRow = this.pengembalianBsTableModel.getRowCount();
        for (int a = 0; a < countRow; ++a) {
            this.pengembalianBsTableModel.removeRow(0);
        }
        this.jumlahBarang.setText("0");
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.idField = new JTextField();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.dateField = new JDateChooser();
        this.detailButton = new JButton();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.pengembalianBsTable = new JTable();
        this.jumlahBarang = new JTextField();
        this.jLabel10 = new JLabel();
        this.supplierField = new JTextField();
        this.supplierLbl = new JLabel();
        this.jLabel5 = new JLabel();
        this.pengembalianCmb = new JComboBox();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal");
        this.jLabel2.setText("No. Transaksi");
        this.jLabel3.setText(":");
        this.jLabel4.setText(":");
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBsPanel.this.idFieldKeyPressed(evt);
            }
        });
        this.jLabel7.setText("Jumlah Barang");
        this.jLabel8.setText(":");
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(73, 23));
        this.detailButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengembalianBsPanel.this.detailButtonActionPerformed(evt);
            }
        });
        this.detailButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBsPanel.this.detailButtonKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengembalianBsPanel.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBsPanel.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengembalianBsPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBsPanel.this.exitButtonKeyPressed(evt);
            }
        });
        this.pengembalianBsTable.setModel(this.pengembalianBsTableModel);
        this.pengembalianBsTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBsPanel.this.pengembalianBsTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.pengembalianBsTable);
        this.jLabel10.setText("Supplier");
        this.supplierField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBsPanel.this.supplierFieldKeyPressed(evt);
            }
        });
        this.supplierLbl.setText("-");
        this.jLabel5.setText("Pengembalian");
        this.pengembalianCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"TUNAI", "BARANG(GUDANG)"}));
        this.pengembalianCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBsPanel.this.pengembalianCmbKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1).addComponent(this.jLabel7)).addGap(38, 38, 38).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.dateField, -2, 155, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.idField, -2, 111, -2))).addGap(60, 60, 60).addComponent(this.jLabel10).addGap(80, 80, 80).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.supplierField, -1, 133, Short.MAX_VALUE).addComponent(this.supplierLbl, -1, -1, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(133, 133, 133).addComponent(this.detailButton, -2, -1, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.saveButton, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel5, -1, -1, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel8).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jumlahBarang, -2, 111, -2))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGap(44, 44, 44).addComponent(this.pengembalianCmb, 0, 133, Short.MAX_VALUE))))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jLabel4)).addComponent((Component)this.dateField, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel10).addComponent(this.supplierField, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.idField, -2, -1, -2)).addComponent(this.supplierLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.jLabel8)).addComponent(this.pengembalianCmb, -2, -1, -2)).addGap(0, 2, Short.MAX_VALUE)).addComponent(this.jLabel5, -1, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailButton, -2, -1, -2).addComponent(this.saveButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2))).addComponent(this.jumlahBarang, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -1, 457, Short.MAX_VALUE).addContainerGap()));
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.pengembalianBs = this.pengembalianBsService.getPengembalianBs(this.idField.getText());
            if (this.idField.getText().equals("") || this.pengembalianBs == null) {
                this.resetData();
                this.pengembalianBs = new PengembalianBs();
                this.idField.setText(Helper.getInstance().generateCodeGeneral(this.pengembalianBsService.getLastId()));
                this.supplierField.requestFocus();
            } else {
                this.resetData();
                List<PengembalianBsDetail> pengembalianBsDetails = this.pengembalianBsDetailService.getPengembalianBsDetails(this.pengembalianBs.getId());
                this.supplier = this.supplierService.getSupplier(this.pengembalianBs.getSupplierId());
                float totalBarang = 0.0f;
                for (PengembalianBsDetail pengembalianBsDetail : pengembalianBsDetails) {
                    Barang dataBarang = this.barangService.getBarang(pengembalianBsDetail.getBarangId());
                    Object[] rowValue = new Object[8];
                    rowValue[0] = dataBarang.getId();
                    rowValue[1] = dataBarang.getNama();
                    rowValue[2] = Float.valueOf(pengembalianBsDetail.getJumlahBarang());
                    rowValue[3] = pengembalianBsDetail.getSatuan();
                    totalBarang += pengembalianBsDetail.getJumlahBarang();
                    this.pengembalianBsTableModel.addRow(rowValue);
                }
                this.jumlahBarang.setText(totalBarang + "");
                this.supplierField.setText(this.supplier.getId());
                this.supplierLbl.setText(this.supplier.getNama());
                this.supplierField.requestFocus();
            }
        } else if (evt.getKeyCode() == 27) {
            this.listener.exitClick();
        }
    }

    private void detailButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.pengembalianBsTable.requestFocus();
            if (this.pengembalianBsTableModel.getRowCount() == 0) {
                PengembalianBsDialog pengembalianBsDialog = new PengembalianBsDialog(null, true, this.pengembalianBsTableModel);
                pengembalianBsDialog.addListener(new pengembalianBsDialogEventHandler());
                int w = pengembalianBsDialog.getWidth();
                int h = pengembalianBsDialog.getHeight();
                pengembalianBsDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                pengembalianBsDialog.setVisible(true);
            }
        } else if (evt.getKeyCode() == 39) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.pengembalianCmb.requestFocus();
        }
    }

    private void pengembalianBsTableKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 10) {
            PengembalianBsDialog pengembalianBsDialog = new PengembalianBsDialog(null, true, this.pengembalianBsTableModel);
            if (this.pengembalianBsTable.getSelectedRow() != -1) {
                pengembalianBsDialog.loadKodeBarang(this.pengembalianBsTableModel.getValueAt(this.pengembalianBsTable.getSelectedRow(), 0).toString());
                pengembalianBsDialog.loadDataFromTable(this.pengembalianBsTable.getSelectedRow());
            }
            pengembalianBsDialog.escapeClick();
            pengembalianBsDialog.addListener(new pengembalianBsDialogEventHandler());
            int w = pengembalianBsDialog.getWidth();
            int h = pengembalianBsDialog.getHeight();
            pengembalianBsDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            pengembalianBsDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a2 = JOptionPane.showConfirmDialog(null, "Akhiri Entry Data?", "Akhiri Entry Data", 0, 3);
            if (a2 == 0) {
                float total = 0.0f;
                for (int x = 0; x < this.pengembalianBsTableModel.getRowCount(); ++x) {
                    total += Float.parseFloat(this.pengembalianBsTableModel.getValueAt(x, 2).toString());
                }
                this.saveButton.requestFocus();
                this.jumlahBarang.setText(total + "");
            }
        } else if (evt.getKeyCode() == 127 && this.pengembalianBsTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
            float jumlahBarangBaru = Float.parseFloat(this.jumlahBarang.getText()) - Float.parseFloat(this.pengembalianBsTableModel.getValueAt(this.pengembalianBsTable.getSelectedRow(), 2).toString());
            this.jumlahBarang.setText(jumlahBarangBaru + "");
            this.pengembalianBsTableModel.removeRow(this.pengembalianBsTable.getSelectedRow());
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 37) {
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.listener.exitClick();
        } else if (evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void detailButtonActionPerformed(ActionEvent evt) {
        this.pengembalianBsTable.requestFocus();
        if (this.pengembalianBsTableModel.getRowCount() == 0) {
            PengembalianBsDialog pengembalianBsDialog = new PengembalianBsDialog(null, true, this.pengembalianBsTableModel);
            pengembalianBsDialog.addListener(new pengembalianBsDialogEventHandler());
            int w = pengembalianBsDialog.getWidth();
            int h = pengembalianBsDialog.getHeight();
            pengembalianBsDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            pengembalianBsDialog.setVisible(true);
        }
    }

    private void supplierFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.supplierField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
                sd.addSearchDialogListener(new searchSupplierDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.supplier = this.supplierService.getSupplier(this.supplierField.getText());
                if (this.supplier != null) {
                    this.supplierLbl.setText(this.supplier.getNama());
                    this.pengembalianCmb.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Data Supplier Tidak Ditemukan");
                }
            }
        } else if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
            sd.addSearchDialogListener(new searchSupplierDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void pengembalianCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.supplierField.requestFocus();
        }
    }

    class bsPrinter
    implements Printable {
        bsPrinter() {
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
            g.drawString("Pengembalian BS :" + PengembalianBsPanel.this.pengembalianCmb.getSelectedItem() + " - " + PengembalianBsPanel.this.pengembalianBs.getId(), x + 0, y + yK);
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (int a = 0; a < PengembalianBsPanel.this.pengembalianBsTableModel.getRowCount(); ++a) {
                Barang barang = PengembalianBsPanel.this.barangService.getBarang(PengembalianBsPanel.this.pengembalianBsTableModel.getValueAt(a, 0).toString());
                g.drawString(barang.getNama() + " (" + barang.getRakIdToko() + ")", x + 0, y + yK);
                float tempValue = Float.parseFloat(PengembalianBsPanel.this.pengembalianBsTableModel.getValueAt(a, 2).toString());
                g.drawString(tempValue + PengembalianBsPanel.this.pengembalianBsTableModel.getValueAt(a, 3).toString() + " / " + barang.getStokBs() + barang.getSatuan1(), x + 20, y + (yK += yP));
                yK += yP;
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(PengembalianBsPanel.this.pengembalianBs.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + yK);
            g.drawString("Jam " + PengembalianBsPanel.this.pengembalianBs.getTanggal().getHours() + ":" + PengembalianBsPanel.this.pengembalianBs.getTanggal().getMinutes() + ":" + PengembalianBsPanel.this.pengembalianBs.getTanggal().getSeconds(), x + 120, y + yK);
            return 0;
        }
    }

    private class pengembalianBsDialogEventHandler
    implements PengembalianBsDialogListener {
        private pengembalianBsDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            boolean newData = true;
            for (a = 0; a < PengembalianBsPanel.this.pengembalianBsTableModel.getRowCount(); ++a) {
                if (!PengembalianBsPanel.this.pengembalianBsTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PengembalianBsPanel.this.pengembalianBsTableModel.addRow(rowValue);
            } else {
                PengembalianBsPanel.this.pengembalianBsTableModel.removeRow(a);
                PengembalianBsPanel.this.pengembalianBsTableModel.addRow(rowValue);
            }
        }
    }

    private class searchPanelSupplierEventHandler
    implements SearchPanelListener {
        private searchPanelSupplierEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PengembalianBsPanel.this.supplierField.setText(chooseValue);
            PengembalianBsPanel.this.supplier = PengembalianBsPanel.this.supplierService.getSupplier(chooseValue);
            PengembalianBsPanel.this.supplierLbl.setText(PengembalianBsPanel.this.supplier.getNama());
            PengembalianBsPanel.this.pengembalianCmb.requestFocus();
        }
    }

    private class searchSupplierDialogEventHandler
    implements SearchDialogListener {
        private searchSupplierDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SUPPLIER", "NAMA SUPPLIER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Supplier> suppliers = PengembalianBsPanel.this.supplierService.getSuppliers(searchWord);
            for (Supplier supplier : suppliers) {
                Object[] temp = new String[]{supplier.getId(), supplier.getNama(), supplier.getAlamat(), supplier.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Supplier");
            sp.addSearchPanelListener(new searchPanelSupplierEventHandler());
            sp.setVisible(true);
        }
    }
}

