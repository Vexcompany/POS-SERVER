/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.StockCommit;
import minimart.engine.model.StokOpnam;
import minimart.engine.model.StokOpnamDetail;
import minimart.engine.model.StokOpnamMandiri;
import minimart.engine.model.StokOpnamMandiriDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PaketService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.StokOpnamDetailService;
import minimart.engine.service.StokOpnamMandiriDetailService;
import minimart.engine.service.StokOpnamMandiriOnlineService;
import minimart.engine.service.StokOpnamMandiriService;
import minimart.engine.service.StokOpnamService;
import minimart.engine.service.SyncService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.SoParsialDialogListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.StokOpnamMandiriDialog;
import minimart.ui.view.report.ReportPoinPenjualanCustomer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StokOpnamMandiriPanel
extends JPanel {
    private JButton cetakBtn;
    private JDateChooser dateField;
    private JButton excelBtn;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane2;
    private JToggleButton revisiBtn1;
    private JToggleButton revisiBtn2;
    private JToggleButton revisiBtn3;
    private JButton saveButton;
    private JComboBox<String> soIdBox;
    private JTable soParsialTable;
    private JTable soParsialTable1;
    private JLabel statusLbl;
    private JButton submitBtn;
    private JFormattedTextField totalField;
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();
    private String[] stokOpnamTMColumn = new String[]{"ID", "Kode Barang", "Nama Barang", "Lokasi", "Jenis", "Rak", "Konversi", "Stok Fisik", "Selisih", "Satuan"};
    private DefaultTableModel stokOpnamTM = new DefaultTableModel(new String[0][1], this.stokOpnamTMColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private int screenWidth;
    private int screenHeight;
    private BarangService barangService = new BarangService();
    private StokOpnam stokOpnam = new StokOpnam();
    private Helper helper = Helper.getInstance();
    private DefaultPanelListener listener;
    private StokOpnamService stokOpnamService = new StokOpnamService();
    private StokOpnamDetailService stokOpnamDetailService = new StokOpnamDetailService();
    private String[] status = new String[4];
    private StokOpnamMandiriService stokOpnamMandiriService = new StokOpnamMandiriService();
    private StokOpnamMandiriOnlineService somoService = new StokOpnamMandiriOnlineService();
    private SyncService syncService = new SyncService();
    private StokOpnamMandiriDetailService stokOpnamMandiriDetailService = new StokOpnamMandiriDetailService();
    private StokOpnamMandiri stokOpnamMandiri = null;
    private boolean canGoThrough;
    private boolean tableEdit = false;
    private boolean completeData = true;
    private ButtonGroup revisiBtnGroup = new ButtonGroup();
    private int state = 0;
    private PaketService paketService = new PaketService();
    private int qtyCounter1;
    private int qtyCounter2;
    private int currentSize;
    private int pageCounter;
    private int printCounter;
    private int jumlahPage;
    private List<Object[]> datas;
    private KonstantaService konstantaService = new KonstantaService();
    private String blind;

    public StokOpnamMandiriPanel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.status[0] = "Tekan Tombol Input1 untuk memulai Stok Opnam";
        this.status[1] = "Tekan Tombol Input2 untuk melakukan revisi1 SO";
        this.status[2] = "Tekan Tombol Input3 untuk melakukan revisi2 ";
        this.status[3] = "Tekan Tombol Submit untuk hasil final SO";
        TableColumn col1 = this.soParsialTable.getColumnModel().getColumn(2);
        TableColumn col2 = this.soParsialTable.getColumnModel().getColumn(0);
        TableColumn col3 = this.soParsialTable.getColumnModel().getColumn(5);
        TableColumn col4 = this.soParsialTable.getColumnModel().getColumn(6);
        col1.setPreferredWidth(200);
        col2.setPreferredWidth(35);
        col3.setPreferredWidth(70);
        col4.setPreferredWidth(100);
        this.revisiBtnGroup.add(this.revisiBtn1);
        this.revisiBtnGroup.add(this.revisiBtn2);
        this.revisiBtnGroup.add(this.revisiBtn3);
        this.revisiBtn2.setEnabled(false);
        this.revisiBtn3.setEnabled(false);
        List<StokOpnamMandiri> stokOpnamMandiris = this.stokOpnamMandiriService.getStokOpnamMandiriUnSubmit();
        for (StokOpnamMandiri som : stokOpnamMandiris) {
            this.soIdBox.addItem(som.getId() + "");
        }
        this.resetData();
        if (stokOpnamMandiris.size() > 0) {
            this.revisiBtn1.doClick();
        }
        this.soParsialTable.setSelectionMode(0);
        this.soParsialTable.setCellSelectionEnabled(true);
        this.soParsialTable1.setSelectionMode(0);
        this.soParsialTable1.setCellSelectionEnabled(true);
        this.excelBtn.setVisible(false);
    }

    private void checkStatusLbl() {
        if (this.stokOpnamMandiri.getTanggalInput1() == null) {
            this.statusLbl.setText(this.status[0]);
            this.revisiBtn1.setEnabled(true);
        } else if (this.stokOpnamMandiri.getTanggalInput2() == null) {
            this.statusLbl.setText(this.status[1]);
            this.revisiBtn1.setEnabled(true);
            this.revisiBtn2.setEnabled(true);
        } else if (this.stokOpnamMandiri.getTanggalInput3() == null) {
            this.statusLbl.setText(this.status[2]);
            this.revisiBtn1.setEnabled(true);
            this.revisiBtn2.setEnabled(true);
            this.revisiBtn3.setEnabled(true);
        } else {
            this.statusLbl.setText(this.status[3]);
            this.revisiBtn1.setEnabled(true);
            this.revisiBtn2.setEnabled(true);
            this.revisiBtn3.setEnabled(true);
        }
        if (this.stokOpnamMandiri.getTanggalSubmit() != null) {
            this.submitBtn.setEnabled(false);
        }
    }

    private void simpanDataFinal() {
        this.stokOpnam = new StokOpnam();
        this.stokOpnam.setId(this.stokOpnamService.getLastId() + 1);
        this.stokOpnam.setKaryawanId(this.helper.getKaryawan().getId());
        this.stokOpnam.setLokasi("Toko");
        this.stokOpnam.setTipe("SOM");
        this.stokOpnam.setTipeName(this.stokOpnamMandiri.getId() + "");
        this.stokOpnam.setStatusOpnam("SUDAH DIPROSES");
        this.stokOpnam.setTanggal(new Date());
        this.stokOpnam.setUpdated(new Date());
        int statusOpnamSave = this.stokOpnamService.createStokOpnam(this.stokOpnam);
        if (statusOpnamSave == 1) {
            ArrayList<StokOpnamDetail> datas = new ArrayList<StokOpnamDetail>();
            List<Object[]> stokOpnamMandiriDetails = null;
            if (this.stokOpnamMandiri.getTanggalInput3() != null) {
                stokOpnamMandiriDetails = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetails(this.stokOpnamMandiri.getId(), 3, this.stokOpnamMandiri.getBlindSom());
            } else if (this.stokOpnamMandiri.getTanggalInput2() != null) {
                stokOpnamMandiriDetails = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetails(this.stokOpnamMandiri.getId(), 2, this.stokOpnamMandiri.getBlindSom());
            } else if (this.stokOpnamMandiri.getTanggalInput1() != null) {
                stokOpnamMandiriDetails = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetails(this.stokOpnamMandiri.getId(), 1, this.stokOpnamMandiri.getBlindSom());
            }
            for (Object[] stokOpnamMandiriDetail : stokOpnamMandiriDetails) {
                StockCommit stockCommit2;
                StokOpnamDetail stokOpnamDetail = new StokOpnamDetail();
                stokOpnamDetail.setStokOpnamId(this.stokOpnam.getId());
                stokOpnamDetail.setBarangId(stokOpnamMandiriDetail[1].toString());
                stokOpnamDetail.setJumlahKomp(Float.parseFloat(stokOpnamMandiriDetail[10].toString()));
                stokOpnamDetail.setJumlahToko(Float.parseFloat(stokOpnamMandiriDetail[7].toString()));
                stokOpnamDetail.setTotal((long)((Integer)stokOpnamMandiriDetail[7] - (Integer)stokOpnamMandiriDetail[10]) * (Long)stokOpnamMandiriDetail[11]);
                datas.add(stokOpnamDetail);
                float selisih = Float.parseFloat(stokOpnamMandiriDetail[7] + "") - Float.parseFloat(stokOpnamMandiriDetail[10] + "");
                if (selisih != 0.0f && this.stokOpnam.getLokasi().equalsIgnoreCase("Toko")) {
                    this.barangService.updateStokBarangToko(selisih, stokOpnamDetail.getBarangId());
                    stockCommit2 = new StockCommit();
                    stockCommit2.setIdBarang(stokOpnamDetail.getBarangId());
                    stockCommit2.setIdTransaksi(this.stokOpnam.getId() + "");
                    stockCommit2.setQueryType("tambah");
                    stockCommit2.setStockType("toko");
                    stockCommit2.setTransactionType("Stok Opnam");
                    stockCommit2.setJumlahBarang(selisih);
                    this.sinkronisasiBarangService.insertCommit(stockCommit2);
                    continue;
                }
                if (selisih == 0.0f || !this.stokOpnam.getLokasi().equalsIgnoreCase("Gudang")) continue;
                this.barangService.updateStokBarangGudang(selisih, stokOpnamDetail.getBarangId());
                stockCommit2 = new StockCommit();
                stockCommit2.setIdBarang(stokOpnamDetail.getBarangId());
                stockCommit2.setIdTransaksi(this.stokOpnam.getId() + "");
                stockCommit2.setQueryType("tambah");
                stockCommit2.setStockType("gudang");
                stockCommit2.setTransactionType("Stok Opnam");
                stockCommit2.setJumlahBarang(selisih);
                this.sinkronisasiBarangService.insertCommit(stockCommit2);
            }
            this.stokOpnamDetailService.createStokOpnamDetailBatch(datas);
            this.stokOpnamMandiri.setTanggalSubmit(new Date());
            int result = this.stokOpnamMandiriService.updateStokOpnamMandiri(this.stokOpnamMandiri);
            int somId = this.stokOpnamMandiri.getId();
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Data Stok Opname Berhasil Tersimpan");
            }
            this.resetData();
            this.somoService.uploadSOM(somId);
            this.syncService.doSyncStokOpnamAndStokOpnamUnsynced();
        }
    }

    private void checkData() {
        for (int a = 0; a < this.stokOpnamTM.getRowCount(); ++a) {
            if (!this.stokOpnamTM.getValueAt(a, 7).equals("")) continue;
            this.completeData = false;
            break;
        }
    }

    private void resetData() {
        int countRow = this.stokOpnamTM.getRowCount();
        for (int a = 0; a < countRow; ++a) {
            this.stokOpnamTM.removeRow(0);
        }
        this.completeData = true;
        this.revisiBtn2.setEnabled(false);
        this.revisiBtn3.setEnabled(false);
    }

    private void revisiBtn3Click() {
        if (this.soIdBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "Tidak Ada Stok Opnam");
        } else {
            this.resetData();
            this.stokOpnamMandiri = this.stokOpnamMandiriService.getStokOpnamMandiri(Integer.parseInt(this.soIdBox.getSelectedItem().toString()));
            this.datas = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetails(this.stokOpnamMandiri.getId(), 3, this.stokOpnamMandiri.getBlindSom());
            for (Object[] data : this.datas) {
                this.stokOpnamTM.addRow(data);
            }
            if (this.stokOpnamMandiri.getTanggalInput3() != null) {
                this.dateField.setDate(this.stokOpnamMandiri.getTanggalInput3());
                this.tableEdit = false;
                this.saveButton.setEnabled(false);
                this.submitBtn.setEnabled(true);
            } else {
                this.dateField.setDate(null);
                this.tableEdit = true;
                this.saveButton.setEnabled(true);
                this.submitBtn.setEnabled(false);
            }
            this.checkStatusLbl();
            if (this.stokOpnamMandiri.getTanggalInput3() != null) {
                this.totalField.setValue(this.stokOpnamMandiriDetailService.getSelisihRevisi3(this.stokOpnamMandiri.getId()));
            } else {
                this.totalField.setValue(0);
            }
            this.state = 3;
        }
    }

    private void revisiBtn2Click() {
        if (this.soIdBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "Tidak Ada Stok Opnam");
        } else {
            this.resetData();
            this.stokOpnamMandiri = this.stokOpnamMandiriService.getStokOpnamMandiri(Integer.parseInt(this.soIdBox.getSelectedItem().toString()));
            this.datas = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetails(this.stokOpnamMandiri.getId(), 2, this.stokOpnamMandiri.getBlindSom());
            for (Object[] data : this.datas) {
                this.stokOpnamTM.addRow(data);
            }
            if (this.stokOpnamMandiri.getTanggalInput2() != null) {
                this.dateField.setDate(this.stokOpnamMandiri.getTanggalInput2());
                this.tableEdit = false;
                this.saveButton.setEnabled(false);
                this.submitBtn.setEnabled(true);
            } else {
                this.dateField.setDate(null);
                this.tableEdit = true;
                this.saveButton.setEnabled(true);
                this.submitBtn.setEnabled(false);
            }
            this.checkStatusLbl();
            if (this.stokOpnamMandiri.getTanggalInput2() != null) {
                this.totalField.setValue(this.stokOpnamMandiriDetailService.getSelisihRevisi2(this.stokOpnamMandiri.getId()));
            } else {
                this.totalField.setValue(0);
            }
            this.state = 2;
        }
    }

    private void createExcel() {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            CreationHelper createhelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");
            Row row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("ID");
            cell = row.createCell(1);
            cell.setCellValue("Kode Barang");
            cell = row.createCell(2);
            cell.setCellValue("Nama Barang");
            cell = row.createCell(3);
            cell.setCellValue("Lokasi");
            cell = row.createCell(4);
            cell.setCellValue("Jenis");
            cell = row.createCell(5);
            cell.setCellValue("Rak");
            cell = row.createCell(6);
            cell.setCellValue("Konversi");
            cell = row.createCell(7);
            cell.setCellValue("Stok Fisik");
            cell = row.createCell(8);
            cell.setCellValue("Selisih");
            cell = row.createCell(9);
            cell.setCellValue("Satuan");
            for (int i = 0; i < this.stokOpnamTM.getRowCount(); ++i) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < this.stokOpnamTM.getColumnCount(); ++j) {
                    cell = row.createCell(j);
                    cell.setCellValue(this.stokOpnamTM.getValueAt(i, j) + "");
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDateString = simpleDateFormat.format(new Date());
            String url = "docStok/" + nowDateString + "SOM.xls";
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

    private void revisiBtn1Click() {
        if (this.soIdBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "Tidak Ada Stok Opnam");
        } else {
            this.resetData();
            this.stokOpnamMandiri = this.stokOpnamMandiriService.getStokOpnamMandiri(Integer.parseInt(this.soIdBox.getSelectedItem().toString()));
            this.datas = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetails(this.stokOpnamMandiri.getId(), 1, this.stokOpnamMandiri.getBlindSom());
            for (Object[] data : this.datas) {
                this.stokOpnamTM.addRow(data);
            }
            if (this.stokOpnamMandiri.getTanggalInput1() != null) {
                this.dateField.setDate(this.stokOpnamMandiri.getTanggalInput1());
                this.tableEdit = false;
                this.saveButton.setEnabled(false);
                this.submitBtn.setEnabled(true);
            } else {
                this.dateField.setDate(null);
                this.tableEdit = true;
                this.saveButton.setEnabled(true);
                this.submitBtn.setEnabled(false);
            }
            this.checkStatusLbl();
            if (this.stokOpnamMandiri.getTanggalInput1() != null) {
                this.totalField.setValue(this.stokOpnamMandiriDetailService.getSelisihRevisi1(this.stokOpnamMandiri.getId()));
            } else {
                this.totalField.setValue(0);
            }
            this.state = 1;
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.soParsialTable1 = new JTable();
        this.jLabel1 = new JLabel();
        this.jLabel4 = new JLabel();
        this.dateField = new JDateChooser();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.submitBtn = new JButton();
        this.statusLbl = new JLabel();
        this.jLabel2 = new JLabel();
        this.soIdBox = new JComboBox();
        this.revisiBtn1 = new JToggleButton();
        this.revisiBtn2 = new JToggleButton();
        this.revisiBtn3 = new JToggleButton();
        this.jScrollPane2 = new JScrollPane();
        this.soParsialTable = new JTable();
        this.totalField = new JFormattedTextField();
        this.jLabel3 = new JLabel();
        this.cetakBtn = new JButton();
        this.excelBtn = new JButton();
        this.soParsialTable1.setModel(this.stokOpnamTM);
        this.soParsialTable1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamMandiriPanel.this.soParsialTableKeyPressed(evt);
            }
        });
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal Input");
        this.jLabel4.setText(":");
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamMandiriPanel.this.saveButtonActionPerformed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamMandiriPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamMandiriPanel.this.exitButtonKeyPressed(evt);
            }
        });
        this.submitBtn.setText("Submit Final");
        this.submitBtn.setEnabled(false);
        this.submitBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamMandiriPanel.this.submitBtnActionPerformed(evt);
            }
        });
        this.statusLbl.setForeground(new Color(255, 0, 0));
        this.statusLbl.setText("STATUS");
        this.jLabel2.setText("ID SO Mandiri");
        this.soIdBox.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                StokOpnamMandiriPanel.this.soIdBoxItemStateChanged(evt);
            }
        });
        this.revisiBtn1.setText("Input 1");
        this.revisiBtn1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamMandiriPanel.this.revisiBtn1(evt);
            }
        });
        this.revisiBtn2.setText("Input 2");
        this.revisiBtn2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamMandiriPanel.this.revisiBtn2ActionPerformed(evt);
            }
        });
        this.revisiBtn3.setText("Input 3");
        this.revisiBtn3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamMandiriPanel.this.revisiBtn3ActionPerformed(evt);
            }
        });
        this.soParsialTable.setModel(this.stokOpnamTM);
        this.soParsialTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamMandiriPanel.this.soParsialTableKeyPressed(evt);
            }
        });
        this.jScrollPane2.setViewportView(this.soParsialTable);
        this.totalField.setBackground(new Color(255, 255, 102));
        this.jLabel3.setText("TOTAL");
        this.cetakBtn.setText("Cetak");
        this.cetakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamMandiriPanel.this.cetakBtnActionPerformed(evt);
            }
        });
        this.excelBtn.setText("Cetak Excel");
        this.excelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamMandiriPanel.this.excelBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane2, -1, 770, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(this.revisiBtn1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.revisiBtn2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.revisiBtn3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 415, Short.MAX_VALUE).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.statusLbl, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(70, 70, 70).addComponent(this.jLabel4)).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent((Component)this.dateField, -1, 155, Short.MAX_VALUE).addComponent(this.soIdBox, 0, -1, Short.MAX_VALUE)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.excelBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetakBtn, -2, 110, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.totalField).addComponent(this.submitBtn, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel3, GroupLayout.Alignment.TRAILING)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jLabel4)).addComponent((Component)this.dateField, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.submitBtn).addComponent(this.cetakBtn).addComponent(this.excelBtn))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.soIdBox, -2, -1, -2).addComponent(this.jLabel3))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.statusLbl).addComponent(this.totalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.revisiBtn1).addComponent(this.revisiBtn2).addComponent(this.revisiBtn3))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane2, -2, 420, -2).addGap(61, 61, 61)));
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.listener.exitClick();
        } else if (evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
            // empty if block
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.canGoThrough = false;
        this.completeData = true;
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Data Stok Opnam Akan Disimpan, Lanjutkan?");
        confirmationDialog.addListener(new confirmDialogEventHandler());
        int w = confirmationDialog.getWidth();
        int h = confirmationDialog.getHeight();
        confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        confirmationDialog.setVisible(true);
        if (this.canGoThrough) {
            this.checkData();
            if (!this.completeData) {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal, Stok Opnam Belum Semua Terisi");
            } else if (this.statusLbl.getText().equals(this.status[0]) && this.stokOpnamMandiri.getTanggalInput1() != null) {
                JOptionPane.showMessageDialog(null, "Input 1 sudah tersimpan, refresh data!");
            } else if (this.statusLbl.getText().equals(this.status[1]) && this.stokOpnamMandiri.getTanggalInput2() != null) {
                JOptionPane.showMessageDialog(null, "Input 2 sudah tersimpan, refresh data!");
            } else if (this.statusLbl.getText().equals(this.status[2]) && this.stokOpnamMandiri.getTanggalInput3() != null) {
                JOptionPane.showMessageDialog(null, "Input 3 sudah tersimpan, refresh data!");
            } else {
                this.stokOpnamMandiri.setUpdated(new Date());
                if (this.statusLbl.getText().equals(this.status[0])) {
                    this.stokOpnamMandiri.setTanggalInput1(new Date());
                } else if (this.statusLbl.getText().equals(this.status[1])) {
                    this.stokOpnamMandiri.setTanggalInput2(new Date());
                } else if (this.statusLbl.getText().equals(this.status[2])) {
                    this.stokOpnamMandiri.setTanggalInput3(new Date());
                }
                int result = this.stokOpnamMandiriService.updateStokOpnamMandiri(this.stokOpnamMandiri);
                int result2 = 0;
                if (this.state == 1) {
                    result2 = this.stokOpnamMandiriDetailService.updateRevisi1(this.stokOpnamMandiri.getId());
                } else if (this.state == 2) {
                    result2 = this.stokOpnamMandiriDetailService.updateRevisi2(this.stokOpnamMandiri.getId());
                } else if (this.state == 3) {
                    result2 = 1;
                }
                if (result != 0 && result2 != 0) {
                    JOptionPane.showMessageDialog(null, "Data Inputan Telah Tersimpan");
                    if (this.state == 1) {
                        this.revisiBtn1Click();
                    } else if (this.state == 2) {
                        this.revisiBtn2Click();
                    } else if (this.state == 3) {
                        this.revisiBtn3Click();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
                }
            }
        }
    }

    private void submitBtnActionPerformed(ActionEvent evt) {
        if (this.stokOpnamMandiri.getTanggalSubmit() == null) {
            if (this.statusLbl.getText().equals(this.status[0])) {
                this.revisiBtn1Click();
            } else if (this.statusLbl.getText().equals(this.status[1])) {
                this.revisiBtn2Click();
            } else if (this.statusLbl.getText().equals(this.status[2])) {
                this.revisiBtn3Click();
            }
            this.canGoThrough = false;
            ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Data Stok Opnam Akan DiSubmit, Lanjutkan?");
            confirmationDialog.addListener(new confirmDialogEventHandler());
            int w = confirmationDialog.getWidth();
            int h = confirmationDialog.getHeight();
            confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            confirmationDialog.setVisible(true);
            if (this.canGoThrough) {
                this.simpanDataFinal();
                this.soIdBox.removeAllItems();
                List<StokOpnamMandiri> stokOpnamMandiris = this.stokOpnamMandiriService.getStokOpnamMandiriUnSubmit();
                for (StokOpnamMandiri som : stokOpnamMandiris) {
                    this.soIdBox.addItem(som.getId() + "");
                }
                this.soIdBox.setSelectedIndex(0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Stok Opnam Sudah Tersubmit");
        }
    }

    private void soIdBoxItemStateChanged(ItemEvent evt) {
        if (evt.getStateChange() == 1) {
            int min = Integer.parseInt(this.soIdBox.getItemAt(0));
            for (int a = 1; a < this.soIdBox.getItemCount(); ++a) {
                if (Integer.parseInt(this.soIdBox.getItemAt(a)) >= min) continue;
                min = Integer.parseInt(this.soIdBox.getItemAt(a));
            }
            if (Integer.parseInt(this.soIdBox.getSelectedItem() + "") == min) {
                this.revisiBtn1.doClick();
            } else {
                JOptionPane.showMessageDialog(null, "Selsaikan ID SO yang lama terlebih dahulu");
                this.soIdBox.setSelectedIndex(0);
            }
        }
    }

    private void revisiBtn1(ActionEvent evt) {
        this.revisiBtn1Click();
    }

    private void revisiBtn2ActionPerformed(ActionEvent evt) {
        this.revisiBtn2Click();
    }

    private void revisiBtn3ActionPerformed(ActionEvent evt) {
        this.revisiBtn3Click();
    }

    private void soParsialTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.statusLbl.getText().equals(this.status[0]) && this.stokOpnamMandiriService.getTanggalInput(this.stokOpnamMandiri.getId(), 1) != null) {
                this.tableEdit = false;
            } else if (this.statusLbl.getText().equals(this.status[1]) && this.stokOpnamMandiriService.getTanggalInput(this.stokOpnamMandiri.getId(), 2) != null) {
                this.tableEdit = false;
            } else if (this.statusLbl.getText().equals(this.status[2]) && this.stokOpnamMandiriService.getTanggalInput(this.stokOpnamMandiri.getId(), 3) != null) {
                this.tableEdit = false;
            }
            if (this.tableEdit) {
                StokOpnamMandiriDialog stokOpnamMandiriDialog = new StokOpnamMandiriDialog(null, true, this.stokOpnamTM);
                if (this.soParsialTable.getSelectedRow() != -1) {
                    stokOpnamMandiriDialog.loadKodeBarang(this.stokOpnamTM.getValueAt(this.soParsialTable.getSelectedRow(), 1).toString());
                    stokOpnamMandiriDialog.loadDataFromTable(this.soParsialTable.getSelectedRow());
                }
                stokOpnamMandiriDialog.escapeClick();
                stokOpnamMandiriDialog.addListener(new soParsialEventHandler());
                int w = stokOpnamMandiriDialog.getWidth();
                int h = stokOpnamMandiriDialog.getHeight();
                stokOpnamMandiriDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                stokOpnamMandiriDialog.setVisible(true);
            }
        }
    }

    private void cetakBtnActionPerformed(ActionEvent evt) {
        List<Object[]> datas = null;
        if (this.revisiBtn1.isSelected()) {
            datas = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetails(this.stokOpnamMandiri.getId(), 1, this.stokOpnamMandiri.getBlindSom());
        } else if (this.revisiBtn2.isSelected()) {
            datas = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetails(this.stokOpnamMandiri.getId(), 2, this.stokOpnamMandiri.getBlindSom());
        } else if (this.revisiBtn3.isSelected()) {
            datas = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetails(this.stokOpnamMandiri.getId(), 3, this.stokOpnamMandiri.getBlindSom());
        }
        this.qtyCounter1 = 0;
        this.qtyCounter2 = 0;
        this.pageCounter = 1;
        this.currentSize = datas.size();
        this.jumlahPage = datas.size() / 50;
        if (datas.size() % 50 > 0) {
            ++this.jumlahPage;
        }
        this.qtyCounter2 = this.jumlahPage > 1 ? 50 : datas.size();
        for (int a = 0; a < this.jumlahPage; ++a) {
            this.printCounter = 0;
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
            ++this.pageCounter;
            this.qtyCounter1 += 50;
            int tempup = 0;
            this.currentSize -= 50;
            tempup = this.currentSize > 50 ? 50 : this.currentSize;
            this.qtyCounter2 += tempup;
        }
    }

    private void excelBtnActionPerformed(ActionEvent evt) {
        this.createExcel();
    }

    private class soParsialEventHandler
    implements SoParsialDialogListener {
        private soParsialEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            boolean existingDetail = false;
            for (a = 0; a < StokOpnamMandiriPanel.this.stokOpnamTM.getRowCount(); ++a) {
                if (Long.parseLong(StokOpnamMandiriPanel.this.stokOpnamTM.getValueAt(a, 0).toString()) != Long.parseLong(rowValue[0].toString())) continue;
                existingDetail = true;
                break;
            }
            if (!existingDetail) {
                return;
            }

            String selectedBarangId = rowValue[1].toString();
            for (int b = 0; b < StokOpnamMandiriPanel.this.stokOpnamTM.getRowCount(); ++b) {
                if (b != a && selectedBarangId.equals(StokOpnamMandiriPanel.this.stokOpnamTM.getValueAt(b, 1).toString())) {
                    JOptionPane.showMessageDialog(null, "Barang tersebut sudah ada di daftar SO.");
                    return;
                }
            }

            StokOpnamMandiriDetail somd = StokOpnamMandiriPanel.this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetail(Long.parseLong(rowValue[0].toString()));
            Barang tempbarang = StokOpnamMandiriPanel.this.barangService.getBarangById(selectedBarangId);
            if (somd == null || tempbarang == null) {
                JOptionPane.showMessageDialog(null, "Data barang gagal dimuat. Perubahan belum disimpan.");
                return;
            }

            // Keep the detail record, displayed product, stock snapshot, and saved product ID aligned.
            somd.setBarangId(selectedBarangId);
            if (StokOpnamMandiriPanel.this.state == 1) {
                somd.setStokKomp1((int)tempbarang.getStokToko());
                somd.setInput1(Integer.parseInt(rowValue[7].toString()));
            } else if (StokOpnamMandiriPanel.this.state == 2) {
                somd.setStokKomp2((int)tempbarang.getStokToko());
                somd.setInput2(Integer.parseInt(rowValue[7].toString()));
            } else if (StokOpnamMandiriPanel.this.state == 3) {
                somd.setStokKomp3((int)tempbarang.getStokToko());
                somd.setInput3(Integer.parseInt(rowValue[7].toString()));
            }
            somd.setHpp(tempbarang.getHpp());
            somd.setCreated(new Date());

            int updateResult = StokOpnamMandiriPanel.this.stokOpnamMandiriDetailService.updateStokOpnamMandiriDetail(somd);
            if (updateResult == 0) {
                JOptionPane.showMessageDialog(null, "Perubahan gagal disimpan. Data tabel tidak diubah.");
                return;
            }

            StokOpnamMandiriPanel.this.stokOpnamTM.removeRow(a);
            StokOpnamMandiriPanel.this.stokOpnamTM.insertRow(a, rowValue);
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            StokOpnamMandiriPanel.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            StokOpnamMandiriPanel.this.canGoThrough = false;
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
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            g.setFont(new Font("Times New Roman", 1, 7));
            g.drawString("STOK OPNAM MANDIRI (SOM)", x + 425, y + yK);
            g.setFont(new Font("Times New Roman", 0, 7));
            g.drawString("Tanggal Cetak ", x, y + yK);
            g.drawString(": " + format.format(new Date()), x + 60, y + yK);
            g.drawString("ID SOM ", x, y + (yK += yP));
            g.drawString(": " + StokOpnamMandiriPanel.this.stokOpnamMandiri.getId(), x + 60, y + yK);
            yK += yP;
            int revisiKe = 0;
            if (StokOpnamMandiriPanel.this.revisiBtn1.isSelected()) {
                revisiKe = 1;
            } else if (StokOpnamMandiriPanel.this.revisiBtn2.isSelected()) {
                revisiKe = 2;
            } else if (StokOpnamMandiriPanel.this.revisiBtn3.isSelected()) {
                revisiKe = 3;
            }
            g.drawString("Revisi Ke ", x, y + yK);
            g.drawString(": " + revisiKe, x + 60, y + yK);
            g.drawString("Dicetak Oleh ", x, y + (yK += yP));
            g.drawString(": " + StokOpnamMandiriPanel.this.helper.getKaryawan().getId(), x + 60, y + yK);
            g.drawString("Total Selisih ", x, y + (yK += yP));
            g.drawString(": " + StokOpnamMandiriPanel.this.totalField.getValue(), x + 60, y + yK);
            g.drawString("Page: " + StokOpnamMandiriPanel.this.pageCounter + "/" + StokOpnamMandiriPanel.this.jumlahPage, x + 500 + 0, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            yK += yP;
            int kol1 = 0;
            int kol2 = 70;
            int kol3 = 225;
            int kol4 = 280;
            int kol5 = 340;
            int kol6 = 410;
            int kol7 = 450;
            int kol8 = 500;
            g.drawString("Kode Barang", x + kol1, y + yK);
            g.drawString("Nama Barang", x + kol2, y + yK);
            g.drawString("Jenis", x + kol4, y + yK);
            g.drawString("Konversi", x + kol5, y + yK);
            g.drawString("Stok Fisik", x + kol6, y + yK);
            g.drawString("Selisih", x + kol7, y + yK);
            g.drawString("Satuan", x + kol8, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            for (int b = StokOpnamMandiriPanel.this.qtyCounter1; b < StokOpnamMandiriPanel.this.qtyCounter2; ++b) {
                Object[] dataOpnam = (Object[])StokOpnamMandiriPanel.this.datas.get(b);
                g.drawString(dataOpnam[1].toString(), x + kol1, y + (yK += yP));
                g.drawString(dataOpnam[2].toString(), x + kol2, y + yK);
                g.drawString(dataOpnam[4].toString(), x + kol4, y + yK);
                g.drawString(dataOpnam[6].toString(), x + kol5, y + yK);
                g.drawString(dataOpnam[7].toString(), x + kol6, y + yK);
                g.drawString(dataOpnam[8].toString(), x + kol7, y + yK);
                g.drawString(dataOpnam[9].toString(), x + kol8, y + yK);
            }
            g.drawString("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            StokOpnamMandiriPanel.this.printCounter = StokOpnamMandiriPanel.this.printCounter + 1;
            return 0;
        }
    }
}

