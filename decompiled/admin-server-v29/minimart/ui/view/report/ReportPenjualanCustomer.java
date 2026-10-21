/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
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
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.model.Jenis;
import minimart.engine.service.CustomerService;
import minimart.engine.service.JenisService;
import minimart.engine.service.PenjualanService;
import minimart.ui.listener.DefaultPanelListener;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ReportPenjualanCustomer
extends JPanel {
    private JDateChooser endDateField;
    private JButton excelBtn;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JScrollPane jScrollPane1;
    private JTextField limitField;
    private JTable reportTable;
    private JDateChooser startDateField;
    private JButton tampilkanBtn;
    private JTextField totalCustField;
    private JFormattedTextField totalField;
    private String[] reportColumn = new String[]{"ID", "Nama Barang", "Jumlah Penjualan"};
    private String customerId;
    private Jenis jenis;
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PenjualanService penjualanService = new PenjualanService();
    private CustomerService customerService = new CustomerService();
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();
    private JenisService jenisService = new JenisService();

    public ReportPenjualanCustomer() {
        this.initComponents();
        this.customerId = this.customerId;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void createExcel() {
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            CreationHelper createhelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");
            Row row = null;
            Cell cell = null;
            for (int i = 0; i < this.reportTableModel.getRowCount(); ++i) {
                row = sheet.createRow(i);
                for (int j = 0; j < this.reportTableModel.getColumnCount(); ++j) {
                    cell = row.createCell(j);
                    cell.setCellValue(this.reportTableModel.getValueAt(i, j) + "");
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDateString = simpleDateFormat.format(this.startDateField.getDate());
            String endDateString = simpleDateFormat.format(this.endDateField.getDate());
            String url = "docPenjualan/" + startDateString + "to" + endDateString + ".xls";
            FileOutputStream out = new FileOutputStream(url);
            wb.write((OutputStream)out);
            out.close();
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan Pada " + url);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(ReportPenjualanCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(ReportPenjualanCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadData(Date startDate, Date endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(startDate);
        String endDateString = simpleDateFormat.format(endDate);
        int limit = 0;
        limit = this.helper.isInteger(this.limitField.getText()) ? Integer.parseInt(this.limitField.getText()) : 0;
        List<Object[]> objects = this.penjualanService.getTopCustomer(startDateString, endDateString, limit);
        long total = 0L;
        int totalCust = 0;
        for (Object[] object : objects) {
            Object[] result = new Object[]{object[0], this.customerService.getCustomerById(object[0].toString()).getNama(), object[1]};
            total += Long.parseLong(result[2] + "");
            ++totalCust;
            this.reportTableModel.addRow(result);
        }
        this.totalField.setValue(total);
        this.totalCustField.setText(totalCust + "");
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.exitButton = new JButton();
        this.excelBtn = new JButton();
        this.startDateField = new JDateChooser();
        this.endDateField = new JDateChooser();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.tampilkanBtn = new JButton();
        this.jLabel4 = new JLabel();
        this.limitField = new JTextField();
        this.jLabel6 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jLabel3 = new JLabel();
        this.totalCustField = new JTextField();
        this.jLabel5 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(83, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanCustomer.this.exitButtonActionPerformed(evt);
            }
        });
        this.excelBtn.setText("Cetak Excel");
        this.excelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanCustomer.this.excelBtnActionPerformed(evt);
            }
        });
        this.jLabel1.setText("Dari Tanggal");
        this.jLabel2.setText("Sampai Tanggal");
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanCustomer.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.jLabel4.setText("Best");
        this.jLabel6.setText("0-1000, 0=semua");
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.totalField.setFont(new Font("Tahoma", 0, 14));
        this.jLabel3.setFont(new Font("Tahoma", 0, 14));
        this.jLabel3.setText("Total Penjualan");
        this.totalCustField.setFont(new Font("Tahoma", 0, 14));
        this.jLabel5.setFont(new Font("Tahoma", 0, 14));
        this.jLabel5.setText("Total Customer");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 780, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.excelBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel3)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent((Component)this.startDateField, -2, 150, -2).addGap(10, 10, 10).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent((Component)this.endDateField, -2, 150, -2).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addGap(64, 64, 64).addComponent(this.limitField, -2, 79, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel5))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.totalField, -1, 142, Short.MAX_VALUE).addComponent(this.totalCustField)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent((Component)this.endDateField, -2, -1, -2).addComponent((Component)this.startDateField, -2, -1, -2).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.limitField, -2, -1, -2).addComponent(this.jLabel6).addComponent(this.totalCustField, -2, -1, -2).addComponent(this.jLabel5))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.exitButton, -2, -1, -2).addComponent(this.excelBtn).addComponent(this.tampilkanBtn).addComponent(this.totalField, -2, -1, -2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -2, 440, -2).addGap(58, 58, 58)));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void excelBtnActionPerformed(ActionEvent evt) {
        this.createExcel();
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.clearTable();
        this.loadData(this.startDateField.getDate(), this.endDateField.getDate());
    }

    private int rataKanan(String price, int x) {
        int len = price.length();
        for (int i = 0; i < len; ++i) {
            x -= 4;
        }
        return x;
    }

    class pembelianPrint
    implements Printable {
        pembelianPrint() {
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
            Customer customer = ReportPenjualanCustomer.this.customerService.getCustomerById(ReportPenjualanCustomer.this.customerId);
            if (customer != null) {
                g.drawString("Customer:" + customer.getNama(), x + 0, y + yK);
            } else {
                g.drawString("Customer: Semua", x + 0, y + yK);
            }
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (int a = 0; a < ReportPenjualanCustomer.this.reportTableModel.getRowCount(); ++a) {
                g.drawString(ReportPenjualanCustomer.this.reportTableModel.getValueAt(a, 0).toString() + "/" + ReportPenjualanCustomer.this.reportTableModel.getValueAt(a, 1).toString() + "/" + ReportPenjualanCustomer.this.reportTableModel.getValueAt(a, 2).toString(), x + 0, y + yK);
                int xTemp = ReportPenjualanCustomer.this.rataKanan(ReportPenjualanCustomer.this.helper.formatNumber(Long.parseLong(ReportPenjualanCustomer.this.reportTableModel.getValueAt(a, 5).toString().replace(".", "")) + ""), xRataKanan);
                g.drawString(ReportPenjualanCustomer.this.reportTableModel.getValueAt(a, 5).toString(), x + xTemp, y + yK);
                yK += yP;
            }
            return 0;
        }
    }
}

