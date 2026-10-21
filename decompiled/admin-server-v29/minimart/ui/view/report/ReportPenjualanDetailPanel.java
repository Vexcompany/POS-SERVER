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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import minimart.engine.controller.Helper;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.PenjualanDetailService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.report.ReportPoinPenjualanCustomer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportPenjualanDetailPanel
extends JPanel {
    private JLabel barangIdLbl;
    private JLabel barangNameLbl;
    private JButton cektakBtn;
    private JLabel customerIdLbl;
    private JLabel customerNameLbl;
    private JFormattedTextField endDateField;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel5;
    private JLabel jLabel8;
    private JScrollPane jScrollPane1;
    private JTable reportTable;
    private JFormattedTextField startDateField;
    private String[] reportColumn = new String[]{"ID Penjualan", "Kasir", "Customer", "Tanggal", "ID Barang", "Nama Barang", "Jumlah Barang", "Satuan", "Discount", "Harga", "Total Harga", "Total Penjualan", "Referensi"};
    private String customerId;
    private String barangId;
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PenjualanDetailService detailService = new PenjualanDetailService();
    private KaryawanService karyawanService = new KaryawanService();
    private CustomerService customerService = new CustomerService();
    private BarangService barangService = new BarangService();
    private Date startDate;
    private Date endDate;
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();

    public ReportPenjualanDetailPanel(Date startDate, Date endDate, String customerId, String barangId) {
        this.initComponents();
        this.startDate = startDate;
        this.endDate = endDate;
        this.startDateField.setValue(startDate);
        this.endDateField.setValue(endDate);
        this.customerId = customerId;
        this.barangId = barangId;
        if (customerId.equals("")) {
            this.customerNameLbl.setText("SEMUA");
        } else {
            this.customerIdLbl.setText(customerId);
            this.customerNameLbl.setText(this.customerService.getCustomerById(customerId).getNama());
        }
        if (barangId.equals("")) {
            this.barangNameLbl.setText("SEMUA");
        } else {
            this.barangIdLbl.setText(barangId);
            this.barangNameLbl.setText(this.barangService.getBarang(barangId).getNama());
        }
        this.loadData();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.jScrollPane1.setHorizontalScrollBarPolicy(30);
        this.reportTable.setAutoResizeMode(0);
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void loadData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDate);
        String endDateString = simpleDateFormat.format(this.endDate);
        List<Object[]> objects = this.detailService.readPenjualanDetailByCustomerBarangId(startDateString, endDateString, this.customerId, this.barangId);
        for (Object[] object : objects) {
            Object[] rowValue = new Object[]{object[0], object[1], object[2], object[3], object[4], object[5], object[6], object[7], object[8], object[9], object[10], object[11], object[12]};
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.startDateField = new JFormattedTextField();
        this.endDateField = new JFormattedTextField();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.exitButton = new JButton();
        this.jLabel5 = new JLabel();
        this.jLabel2 = new JLabel();
        this.customerIdLbl = new JLabel();
        this.customerNameLbl = new JLabel();
        this.jLabel8 = new JLabel();
        this.barangIdLbl = new JLabel();
        this.barangNameLbl = new JLabel();
        this.cektakBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal");
        this.startDateField.setEditable(false);
        this.startDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.endDateField.setEditable(false);
        this.endDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(83, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanDetailPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.jLabel5.setText("-");
        this.jLabel2.setText("Customer");
        this.customerIdLbl.setText("-");
        this.customerNameLbl.setText("-");
        this.jLabel8.setText("Barang");
        this.barangIdLbl.setText("-");
        this.barangNameLbl.setText("-");
        this.cektakBtn.setText("Cetak");
        this.cektakBtn.setPreferredSize(new Dimension(72, 23));
        this.cektakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanDetailPanel.this.cektakBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(25, 25, 25).addComponent(this.startDateField, -2, 122, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.endDateField, -2, 122, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel8)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.customerIdLbl, -1, -1, Short.MAX_VALUE).addComponent(this.barangIdLbl, -1, 102, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.barangNameLbl, -1, 191, Short.MAX_VALUE).addComponent(this.customerNameLbl, -1, -1, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addComponent(this.cektakBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.startDateField, -2, -1, -2).addComponent(this.endDateField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.customerIdLbl).addComponent(this.customerNameLbl)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.barangIdLbl))).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.barangNameLbl))).addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cektakBtn, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 411, -2).addContainerGap()));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
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
            cell.setCellValue("ID Customer");
            cell = row.createCell(1);
            cell.setCellValue("Kasir");
            cell = row.createCell(2);
            cell.setCellValue("Nama Customer");
            cell = row.createCell(3);
            cell.setCellValue("Tanggal");
            cell = row.createCell(4);
            cell.setCellValue("ID Barang");
            cell = row.createCell(5);
            cell.setCellValue("Nama Barang");
            cell = row.createCell(6);
            cell.setCellValue("Jumlah Barang");
            cell = row.createCell(7);
            cell.setCellValue("Satuan");
            cell = row.createCell(8);
            cell.setCellValue("Discount");
            cell = row.createCell(9);
            cell.setCellValue("Harga");
            cell = row.createCell(10);
            cell.setCellValue("Total Harga");
            cell = row.createCell(11);
            cell.setCellValue("Total Penjualan");
            cell = row.createCell(12);
            cell.setCellValue("Referensi");
            for (int i = 0; i < this.reportTableModel.getRowCount(); ++i) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < this.reportTableModel.getColumnCount(); ++j) {
                    cell = row.createCell(j);
                    cell.setCellValue(this.reportTableModel.getValueAt(i, j) + "");
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDateString = simpleDateFormat.format(new Date());
            String url = "docStok/" + nowDateString + "DetailPenjualan" + this.barangId + ".xls";
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

    private void cektakBtnActionPerformed(ActionEvent evt) {
        this.createExcel();
    }
}

