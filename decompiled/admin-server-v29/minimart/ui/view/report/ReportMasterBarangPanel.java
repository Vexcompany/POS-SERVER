/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.RBarangSupplierService;
import minimart.engine.service.StokOpnamMandiriDetailService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.report.ReportPoinPenjualanCustomer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportMasterBarangPanel
extends JPanel {
    private JButton cariButton;
    private JButton cetakButton;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JButton keluarButton;
    private JTable reportTable;
    private JTextField searchField;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Rak Toko", "Rak Gudang", "Jenis", "Sat1", "Sat2", "Sat3", "Konversi21", "Konversi32", "Harga Jual1", "Harga Jual2", "Harga Jual3", "Grosir Minimum", "Persentase Harga Ecer", "Batas Atas Stok", "Reorder Poin", "Stok Toko", "Stok Gudang", "Stok Bs", "Created", "Updated", "ParentId"};
    private String[] reportColumnSupplier = new String[]{"ID BARANG", "NAMA BARANG", " JENIS", "STOK TOKO", "STOK GUDANG", "SATUAN", "SUPPLIER"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private BarangService barangService = new BarangService();
    private StokOpnamMandiriDetailService stokOpnamMandiriDetailService = new StokOpnamMandiriDetailService();
    private static final int SOM_HIDE_N_DAYS = 1;
    private DefaultPanelListener listener;
    private SupplierService supplierService = new SupplierService();
    private String jenis;
    private String rak;
    private String supplierId;
    private RBarangSupplierService rbss = new RBarangSupplierService();
    private BarcodeService barcodeService = new BarcodeService();
    private boolean bySupplier;
    private Helper helper = Helper.getInstance();
    private String lokasi;

    public ReportMasterBarangPanel(String jenis, String rak) {
        this.initComponents();
        this.jScrollPane1.setHorizontalScrollBarPolicy(30);
        this.reportTable.setAutoResizeMode(0);
        this.jenis = jenis;
        this.rak = rak;
        this.bySupplier = false;
        this.loadBarang(jenis, rak, "");
        ((AbstractDocument)this.searchField.getDocument()).setDocumentFilter(this.filter);
    }

    public ReportMasterBarangPanel(String lokasi, String supplierId, String temp) {
        this.initComponents();
        this.lokasi = lokasi;
        this.loadBarang2(supplierId);
        this.bySupplier = true;
        this.supplierId = supplierId;
        ((AbstractDocument)this.searchField.getDocument()).setDocumentFilter(this.filter);
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void loadBarang2(String supplierId) {
        this.clearTable();
        this.reportTableModel.setColumnIdentifiers(this.reportColumnSupplier);
        List<Object[]> rbses = this.rbss.readRBSBySupplierId(supplierId);
        for (Object[] rbs : rbses) {
            this.reportTableModel.addRow(rbs);
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
            cell.setCellValue("ID Barang");
            cell = row.createCell(1);
            cell.setCellValue("Nama Barang");
            cell = row.createCell(2);
            cell.setCellValue("Rak Toko");
            cell = row.createCell(3);
            cell.setCellValue("Rak Gudang");
            cell = row.createCell(4);
            cell.setCellValue("Jenis");
            cell = row.createCell(5);
            cell.setCellValue("Satuan1");
            cell = row.createCell(6);
            cell.setCellValue("Satuan2");
            cell = row.createCell(7);
            cell.setCellValue("Satuan3");
            cell = row.createCell(8);
            cell.setCellValue("Konversi21");
            cell = row.createCell(9);
            cell.setCellValue("Konversi32");
            cell = row.createCell(10);
            cell.setCellValue("Harga Jual1");
            cell = row.createCell(11);
            cell.setCellValue("Harga Jual2");
            cell = row.createCell(12);
            cell.setCellValue("Harga Jual3");
            cell = row.createCell(13);
            cell.setCellValue("Grosir Minimum");
            cell = row.createCell(14);
            cell.setCellValue("Persentase Harga Ecer");
            cell = row.createCell(15);
            cell.setCellValue("Batas Atas Stok");
            cell = row.createCell(16);
            cell.setCellValue("Reorder Poin");
            cell = row.createCell(17);
            cell.setCellValue("Stok Toko");
            cell = row.createCell(18);
            cell.setCellValue("Stok Gudang");
            cell = row.createCell(19);
            cell.setCellValue("Stok BS");
            cell = row.createCell(20);
            cell.setCellValue("HPP");
            cell = row.createCell(21);
            cell.setCellValue("CREATED");
            cell = row.createCell(22);
            cell.setCellValue("UPDATED");
            cell = row.createCell(23);
            cell.setCellValue("PARENT ID");
            for (int i = 0; i < this.reportTableModel.getRowCount(); ++i) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < this.reportTableModel.getColumnCount(); ++j) {
                    cell = row.createCell(j);
                    cell.setCellValue(this.reportTableModel.getValueAt(i, j) + "");
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDateString = simpleDateFormat.format(new Date());
            String url = "docStok/" + nowDateString + "MasterBarang.xlsx";
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

    private void loadBarang(String jenis, String rak, String nama) {
        this.clearTable();
        this.reportTableModel.setColumnIdentifiers(this.reportColumn);
        List<Barang> barangs = this.barangService.getBarangsByJenisRakNama(jenis, rak, nama, "", true);
        HashSet<String> hiddenIds = new HashSet<String>(this.stokOpnamMandiriDetailService.getBarangIdHiddenBySom(1));
        for (Barang barang : barangs) {
            Object[] rowValue = new Object[24];
            rowValue[0] = barang.getId();
            rowValue[1] = barang.getNama();
            rowValue[2] = barang.getRakIdToko();
            rowValue[3] = barang.getRakIdGudang();
            rowValue[4] = barang.getJenis();
            rowValue[5] = barang.getSatuan1();
            rowValue[6] = barang.getSatuan2();
            rowValue[7] = barang.getSatuan3();
            rowValue[8] = barang.getKonversi21();
            rowValue[9] = barang.getKonversi32();
            rowValue[10] = barang.getHargaJual1();
            rowValue[11] = barang.getHargaJual2();
            rowValue[12] = barang.getHargaJual3();
            rowValue[13] = Float.valueOf(barang.getMinPembelian());
            rowValue[14] = Float.valueOf(barang.getMinPersen());
            rowValue[15] = Float.valueOf(barang.getBatasAtasStock());
            rowValue[16] = Float.valueOf(barang.getReorderPoint());
            if (hiddenIds.contains(barang.getId())) {
                rowValue[17] = "";
                rowValue[18] = "";
                rowValue[19] = "";
            } else {
                rowValue[17] = Float.valueOf(barang.getStokToko());
                rowValue[18] = Float.valueOf(barang.getStokGudang());
                rowValue[19] = Float.valueOf(barang.getStokBs());
            }
            rowValue[20] = barang.getCreated();
            rowValue[21] = barang.getUpdated();
            rowValue[22] = barang.getParentId();
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.keluarButton = new JButton();
        this.cariButton = new JButton();
        this.searchField = new JTextField();
        this.cetakButton = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Master Barang");
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(73, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMasterBarangPanel.this.keluarButtonActionPerformed(evt);
            }
        });
        this.cariButton.setText("Cari");
        this.cariButton.setPreferredSize(new Dimension(73, 23));
        this.cariButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMasterBarangPanel.this.cariButtonActionPerformed(evt);
            }
        });
        this.cetakButton.setText("Cetak");
        this.cetakButton.setPreferredSize(new Dimension(73, 23));
        this.cetakButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMasterBarangPanel.this.cetakButtonActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.searchField, -2, 156, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cariButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetakButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGap(288, 288, 288).addComponent(this.jLabel1).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jScrollPane1, -1, 492, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.cariButton, -2, -1, -2).addComponent(this.searchField, -2, -1, -2).addComponent(this.cetakButton, -2, -1, -2)).addGap(23, 23, 23)));
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void cariButtonActionPerformed(ActionEvent evt) {
        this.loadBarang(this.jenis, this.rak, this.searchField.getText());
    }

    private void cetakButtonActionPerformed(ActionEvent evt) {
        this.createExcel();
    }
}

